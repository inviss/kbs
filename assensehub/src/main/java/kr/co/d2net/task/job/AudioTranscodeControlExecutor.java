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
import kr.co.d2net.dto.TranscorderHisTbl;
import kr.co.d2net.dto.xml.internal.Job;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.service.WorkflowManagerService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("audioTranscodeControlExecutor")
public class AudioTranscodeControlExecutor {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private ExecutorService thread = Executors.newSingleThreadExecutor();
	private final long THREAD_WAIT_TIME = 1000L * 30;
	
	//파일 없을 경우 관리하는 map
	private Map<Long, Object> existFileMap = new HashMap<Long, Object>();

	@Autowired
	private WorkflowManagerService workflowManagerService;
	@Autowired
	private MessageSource messageSource;

	@PostConstruct
	public void start() {
		thread.execute(new AudioTranscodeJob());
		if(logger.isInfoEnabled()) {
			logger.info("AudioTranscodeControlExecutor Thread start !!");
		}
	}

	public class AudioTranscodeJob implements Runnable {
		
		private List<Long> ctiIds = new ArrayList<Long>();
		private boolean isPutAcJob = true;
		
		@Override
		public void run() {
			while(true) {
				
				ctiIds.clear();
				
				int size = TranscodeJobControl.getAcSize();
				logger.debug("Transcode Audio Job size - "+size);

				try {
					if(size <= 0) {
						ctiIds = workflowManagerService.findTraHisCtiId("001", "A");
						if(ctiIds == null || ctiIds.size() <= 0) {
							ctiIds = workflowManagerService.findTraHisCtiId("000", "A");
						}
					} else {
						ctiIds = workflowManagerService.findTraHisCtiId("000", "A");
					}
				} catch (Exception e) {
					logger.error("findTraHisCtiId error", e);
				}

				String mnt2 = messageSource.getMessage("mnt2.mount.drive", null, null);
				String mnt = messageSource.getMessage("mnt.mount.drive", null, null);
				String mammnt = messageSource.getMessage("mam.mount.prefix", null, null);

				File f = null;
				boolean header = true;
				for(Long ctiId : ctiIds) {
					try {
						Workflow workflow = new Workflow();
						workflow.setCtiId(ctiId);

						List<TranscorderHisTbl> transcorderHisTbls = workflowManagerService.findTraHisJobCtiId(ctiId);
						for(TranscorderHisTbl transcorderHisTbl :  transcorderHisTbls) {

							if(header) {

								f = new File(mammnt+transcorderHisTbl.getFlPath().replaceAll("\\\\", "\\/"), transcorderHisTbl.getOrgFileNm()+"." + transcorderHisTbl.getOrgFlExt());
								if(!f.exists()) {
									if(logger.isInfoEnabled()) {
										logger.info("new job orgfile not exist!! break."+f.getAbsolutePath());
										
										if(existFileMap.get(transcorderHisTbl.getSeq()) != null){
											logger.info("reg_id from : " + transcorderHisTbl.getRegrid());
											
											existFileMap.remove(transcorderHisTbl.getSeq());
											
											transcorderHisTbl.setWorkStatcd("900"); 
											transcorderHisTbl.setModDt(Utility.getTimestamp());
											
											workflowManagerService.updateTranscorderHisState(transcorderHisTbl);
											
											//queue에 job 삭제
											TranscodeJobControl.getAcJob();
											
											isPutAcJob = false;
										}else{
											existFileMap.put(transcorderHisTbl.getSeq(), "");
										}
									}
									break;
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

							logger.debug("job id: "+job.getJobId()+", ========>"+job.getTargetFile());

							/* 요청 추가된 작업에 대한 상태 변경 */
							transcorderHisTbl.setModDt(Utility.getTimestamp());
							transcorderHisTbl.setWorkStatcd("001"); // 대기상태[00] -> 요청상태 변경[01]
							workflowManagerService.updateTranscorderHisState(transcorderHisTbl);

							workflow.addJobList(job);
						}
						
						if(isPutAcJob){
							TranscodeJobControl.putAcJob(workflow);
						}
						
						isPutAcJob = true;
						header = true;
						
					} catch (Exception e) {
						logger.error("[Transcode Audio Job Control] TransferJob Generation Error", e);
					}

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
				logger.info("AudioTranscodeControlExecutor shutdown now!!");
			}
		}
	}
}
