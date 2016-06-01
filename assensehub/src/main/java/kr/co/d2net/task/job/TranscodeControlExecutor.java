package kr.co.d2net.task.job;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dao.OptDao;
import kr.co.d2net.dao.ProOptDao;
import kr.co.d2net.dto.OptTbl;
import kr.co.d2net.dto.ProOptTbl;
import kr.co.d2net.dto.TranscorderHisTbl;
import kr.co.d2net.dto.xml.internal.Job;
import kr.co.d2net.dto.xml.internal.Option;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.service.BusiPartnerPgmManagerService;
import kr.co.d2net.service.WorkflowManagerService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("transcodeControlExecutor")
public class TranscodeControlExecutor {
	final Logger logger = LoggerFactory.getLogger(getClass());

	private ExecutorService thread = Executors.newSingleThreadExecutor();
	private final long THREAD_WAIT_TIME = 1000L * 30;

	@Autowired
	private WorkflowManagerService workflowManagerService;
	@Autowired
	private OptDao optDao;
	@Autowired
	private ProOptDao proOptDao;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private BusiPartnerPgmManagerService busiPartnerPgmManagerService;

	@PostConstruct
	public void start() {
		thread.execute(new TranscodeJob());
		if(logger.isInfoEnabled()) {
			logger.info("TranscodeControlExecutor Thread start !!");
		}
	}

	public class TranscodeJob implements Runnable {

		private Map<String, Object> params = new HashMap<String, Object>();
		private List<Long> ctiIds = new ArrayList<Long>();
		
		private boolean isPutJob = true;
		

		//파일 없을 경우 관리하는 map
		private Map<Long, Object> existFileMap = new HashMap<Long, Object>();

		@Override
		public void run() {
			while(true) {

				//프로파일 포함된 job 갯수
				int size = TranscodeJobControl.getSize();
				if(logger.isInfoEnabled()) {
					logger.info("Transcode Video Job size - "+size);
					logger.info("Transcode Video CTI_ID Job size - " + TranscodeJobControl.getTmpSize());
				}

				ctiIds.clear();
				try {
					if(size <= 0) {
						ctiIds = workflowManagerService.findTraHisCtiId("001", "V");
						if(ctiIds == null || ctiIds.size() <= 0) {
							ctiIds = workflowManagerService.findTraHisCtiId("000", "V");
						}
					} else {
						ctiIds = workflowManagerService.findTraHisCtiId("000", "V");
					}
				} catch (Exception e) {
					logger.error("findTraHisCtiId error", e);
				}


				String mnt2 = messageSource.getMessage("mnt2.mount.drive", null, null); // Z
				String mnt = messageSource.getMessage("mnt.mount.drive", null, null); // X
				String mammnt = messageSource.getMessage("mam.mount.prefix", null, null); // /mnt

				File f = null;
				boolean header = true;

				String audioModeMain = null;
				for(Long ctiId : ctiIds) {
					Workflow workflow = new Workflow();
					workflow.setCtiId(ctiId);
					try {
						List<TranscorderHisTbl> transcorderHisTbls = workflowManagerService.findTraHisJobCtiId(ctiId);
						for(TranscorderHisTbl transcorderHisTbl :  transcorderHisTbls) {

							if(header) {
								f = new File(mammnt+transcorderHisTbl.getFlPath().replaceAll("\\\\", "\\/"), transcorderHisTbl.getOrgFileNm()+"." + transcorderHisTbl.getOrgFlExt().toLowerCase());
								if(!f.exists()) {
									f = new File(mammnt+transcorderHisTbl.getFlPath().replaceAll("\\\\", "\\/"), transcorderHisTbl.getOrgFileNm()+"." + transcorderHisTbl.getOrgFlExt().toUpperCase());
									if(!f.exists()) {
										//스토리지 동기화 약간의 delay 시간이 필요
										//최소 한번 재시도는 해야함 ex) 최소 동기화 시간 5초
										//16.5.30 vayne
										if(logger.isInfoEnabled()) {
											logger.info("new job orgfile not exist!! break."+f.getAbsolutePath());
											
											if(existFileMap.get(transcorderHisTbl.getSeq()) != null){
												logger.info("reg_id from : " + transcorderHisTbl.getRegrid());
												
												existFileMap.remove(transcorderHisTbl.getSeq());
												
												transcorderHisTbl.setWorkStatcd("900"); 
												transcorderHisTbl.setModDt(Utility.getTimestamp());
												
												workflowManagerService.updateTranscorderHisState(transcorderHisTbl);
												
												//queue에 job 삭제
												TranscodeJobControl.getJob();
												
												isPutJob = false;
												
											}else{
												existFileMap.put(transcorderHisTbl.getSeq(), "");
											}
										}
										break;
									}
								}
								
								//16.5.30 vayne
								TranscodeJobControl.addCtiIds(ctiId);
								
								if(logger.isInfoEnabled())
									logger.info("addCtiIds : " + ctiId);

								if(logger.isInfoEnabled()) {
									logger.info("new job org path: "+f.getAbsolutePath());
								}

								if(transcorderHisTbl.getRegrid().equals("RMS")) {
									workflow.setSourcePath(mnt2+":\\"+transcorderHisTbl.getFlPath().replaceAll("\\/", "\\\\"));
								} else {
									workflow.setSourcePath(mnt+":\\"+transcorderHisTbl.getFlPath().replaceAll("\\/", "\\\\"));
								}

								workflow.setSourceFile(transcorderHisTbl.getOrgFileNm()+"." + transcorderHisTbl.getOrgFlExt());
								if(transcorderHisTbl.getRegrid().equals("RMS")) {
									workflow.setTargetPath(mnt2+":\\"+transcorderHisTbl.getFlPath()
											.replace("rms", "mp4")
											.replace("source", "target")
											.replaceAll("\\/", "\\\\"));
								} else {
									workflow.setTargetPath(mnt2+":\\"+transcorderHisTbl.getFlPath()
											.replace("mp2", "mp4")
											.replace("source", "target")
											.replaceAll("\\/", "\\\\"));
								}

								// 2013.02.22 추가
								// 스트레오일 경우 1~4 채널을 모두 muxing할때 약간의 오차때문에 잡음이 생김. 처리를 위해 모드 추가
								// 스트레오는 1,2 만 처리, 5.1의 경우 1~6만 처리함 5.1의 경우 스트레오가 7,8 채널에 담겨옴.
								if(StringUtils.defaultString(transcorderHisTbl.getAvGubun(), "V").equals("V")) {
									audioModeMain = busiPartnerPgmManagerService.getPgmAudioMode(transcorderHisTbl.getPgmCd(),transcorderHisTbl.getCtTyp());
									audioModeMain = StringUtils.defaultIfEmpty(audioModeMain, "02"); // Stereo
									workflow.setAudioMode(audioModeMain);
								}

								// 지역국 영상 처리를 위해 추가
								if(StringUtils.isNotBlank(transcorderHisTbl.getStartTime())) {
									workflow.setStartTime(transcorderHisTbl.getStartTime());
								}
								if(StringUtils.isNotBlank(transcorderHisTbl.getEndTime())) {
									workflow.setEndTime(transcorderHisTbl.getEndTime());
								}

								header = false;
							}

							Job job = new Job();
							job.setJobId(transcorderHisTbl.getSeq());

							if(StringUtils.defaultString(transcorderHisTbl.getAvGubun(), "V").equals("V")) {
								if((transcorderHisTbl.getVdoHori() == null || transcorderHisTbl.getVdoHori() <= 0) 
										&& (transcorderHisTbl.getVdoVert() == null || transcorderHisTbl.getVdoVert()  <= 0) )
									job.setJobKind("audio");
								else 
									job.setJobKind("video");
							} else
								job.setJobKind("audio");

							job.setProFlid(transcorderHisTbl.getProFlid());
							job.setServBit(transcorderHisTbl.getServBit());
							job.setExt(transcorderHisTbl.getNewFlExt());
							job.setVdoCodec(transcorderHisTbl.getVdoCodec());
							job.setVdoBitRate(transcorderHisTbl.getVdoBitRate());
							job.setVdoHori(transcorderHisTbl.getVdoHori());
							job.setVdoVeri(transcorderHisTbl.getVdoVert());
							job.setVdoFS(transcorderHisTbl.getVdoFS());
							job.setVdoSync(transcorderHisTbl.getVdoSync());
							job.setAudCodec(transcorderHisTbl.getAudCodec());
							job.setAudBitRate(transcorderHisTbl.getAudBitRate());
							job.setAudChan(transcorderHisTbl.getAudChan());
							job.setAudSRate(transcorderHisTbl.getAudSRate());
							job.setDefaultOpt(transcorderHisTbl.getDefaultOpt());

							if(job.getJobKind().equals("video")) {
								job.setQcYn("Y");
								job.setTargetFile(transcorderHisTbl.getOrgFileNm()+"_"+transcorderHisTbl.getServBit()+"_"+transcorderHisTbl.getPicKind()+ "." + transcorderHisTbl.getNewFlExt());
							} else {
								job.setQcYn("N");
								job.setTargetFile(transcorderHisTbl.getOrgFileNm()+"_"+transcorderHisTbl.getServBit()+"." + transcorderHisTbl.getNewFlExt());
							}

							if(logger.isDebugEnabled())
								logger.debug("job id: "+job.getJobId()+", ========>"+job.getTargetFile());

							/* 요청 추가된 작업에 대한 상태 변경 */
							transcorderHisTbl.setModDt(Utility.getTimestamp());
							transcorderHisTbl.setWorkStatcd("001"); // 대기상태[00] -> 요청상태 변경[01]
							workflowManagerService.updateTranscorderHisState(transcorderHisTbl);

							params.clear();

							// Default Option 조회
							params.put("defaultYn", "Y");
							OptTbl optTbl = optDao.getOpt(params);
							if(optTbl != null && StringUtils.isNotBlank(optTbl.getOptInfo())) {
								workflow.setDefaultOpt(optTbl.getOptDesc());
							}

							// 프로파일과 관련된 옵션 조회 및 설정
							params.put("proFlid", transcorderHisTbl.getProFlid());
							List<ProOptTbl> optTbls = proOptDao.findProOpt(params);
							if(optTbls != null && !optTbls.isEmpty()) {
								transcorderHisTbl.setOptions(optTbls);
								Option option = new Option();
								for(ProOptTbl proOptTbl : optTbls) {
									Option o = new Option();
									o.setOptId(proOptTbl.getOptId());
									o.setOptDesc(proOptTbl.getOptDesc());
									option.addOptions(o);
								}
								job.setOption(option);
							}

							workflow.addJobList(job);
						}
						
						if(isPutJob){
							TranscodeJobControl.putJob(workflow);
						}
						isPutJob = true;
						
					} catch (Exception e) {
						logger.error("[Transcode Job Control] TransferJobSwap Error", e);
					}

					header = true;
				}

				try {
					Thread.sleep(THREAD_WAIT_TIME);
				} catch (Exception e) {}
			}
		}

	}

	@PreDestroy
	public void stop() {
		if(!thread.isShutdown()) {
			thread.shutdownNow();
			if(logger.isInfoEnabled()) {
				logger.info("TranscodeControlExecutor shutdown now!!");
			}
		}
	}
}
