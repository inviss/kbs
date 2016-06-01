package kr.co.d2net.task.job;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kr.co.d2net.commons.util.SwappingFifoQueue;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dao.OptDao;
import kr.co.d2net.dao.ProOptDao;
import kr.co.d2net.dto.OptTbl;
import kr.co.d2net.dto.ProOptTbl;
import kr.co.d2net.dto.TranscorderHisTbl;
import kr.co.d2net.dto.xml.internal.Job;
import kr.co.d2net.dto.xml.internal.Option;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.service.WorkflowManagerService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("transcodeJobControl")
public class TranscodeJobControl {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private WorkflowManagerService workflowManagerService;
	@Autowired
	private OptDao optDao;
	@Autowired
	private ProOptDao proOptDao;

	private static volatile SwappingFifoQueue<Workflow> tcJobs = new SwappingFifoQueue<Workflow>();
	private static volatile SwappingFifoQueue<Workflow> acJobs = new SwappingFifoQueue<Workflow>();
	
	private static volatile Set<Long> ctiIds = new HashSet<Long>();
	
	private ExecutorService jobReq = Executors.newSingleThreadExecutor();
	private static Integer swap = 0;
	private Map<String, Object> params = new HashMap<String, Object>();

	private volatile static boolean exec = true;

	public void start() throws Exception {
		swap = Integer.valueOf(messageSource.getMessage("trcd.swap.size", null, Locale.KOREA));
		jobReq.execute(new TransferJobSwap());
	}

	public static Integer getSize() {
		return tcJobs.size();
	}
	
	public static Workflow getJob() {
		return tcJobs.get();
	}
	
	public static void putJob(Workflow workflow) {
		tcJobs.put(workflow);
	}
	
	public static Integer getAcSize() {
		return acJobs.size();
	}
	
	public static Workflow getAcJob() {
		return acJobs.get();
	}
	
	public static void putAcJob(Workflow workflow) {
		acJobs.put(workflow);
	}
	
	
	
	public static boolean isExist(Long ctiId) {
		return TranscodeJobControl.ctiIds.contains(ctiId);
	}

	public static void addCtiIds(Long ctiId) {
		TranscodeJobControl.ctiIds.add(ctiId);
	}
	
	public static void removeCtiId(Long ctiId) {
		TranscodeJobControl.ctiIds.remove(ctiId);
	}
	
	public static Integer getTmpSize() {
		return TranscodeJobControl.ctiIds.size();
	}



	/**
	 * 지금은 안씀.
	 * @author Administrator
	 *
	 */
	public class TransferJobSwap implements Runnable {

		@Override
		public void run() {
			while(exec) {
				try {
					if(tcJobs.isEmpty() || tcJobs.size() < swap) {
						logger.debug("Transcode Job - "+tcJobs.size());
						
						List<Long> ctiIds = null;
						if(tcJobs.isEmpty()) {
							ctiIds = workflowManagerService.findTraHisCtiId("000", "V");
							if(ctiIds == null || ctiIds.size() <= 0) {
								ctiIds = workflowManagerService.findTraHisCtiId("001", "V");
							}
						} else {
							ctiIds = workflowManagerService.findTraHisCtiId("000", "V");
						}
						//ctiIds = workflowManagerService.findTraHisCtiId("000");
						
						boolean header = true;
						for(Long ctiId : ctiIds) {
							Workflow workflow = new Workflow();
							workflow.setCtiId(ctiId);
							
							List<TranscorderHisTbl> transcorderHisTbls = workflowManagerService.findTraHisJobCtiId(ctiId);
							for(TranscorderHisTbl transcorderHisTbl :  transcorderHisTbls) {
								
								if(header) {
									workflow.setSourcePath(transcorderHisTbl.getFlPath().replaceAll("\\/", "\\\\"));
									workflow.setSourceFile(transcorderHisTbl.getOrgFileNm()+"." + transcorderHisTbl.getOrgFlExt());
									workflow.setTargetPath(transcorderHisTbl.getFlPath()
											.replace("mp2", "mp4")
											.replace("source", "target")
											.replaceAll("\\/", "\\\\"));
									
									header = false;
								}
								
								Job job = new Job();
								job.setQcYn("Y");
								
								job.setJobId(transcorderHisTbl.getSeq());
								
								if(transcorderHisTbl.getAvGubun().equals("V"))
									job.setJobKind("video");
								else
									job.setJobKind("audio");
								
								job.setProFlid(transcorderHisTbl.getProFlid());
								job.setServBit(transcorderHisTbl.getBitRt());
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

								if(transcorderHisTbl.getAvGubun().equals("V"))
									job.setTargetFile(transcorderHisTbl.getOrgFileNm()+"_"+transcorderHisTbl.getServBit()+"_"+transcorderHisTbl.getPicKind()+ "." + transcorderHisTbl.getNewFlExt());
								else
									job.setTargetFile(transcorderHisTbl.getOrgFileNm()+"_"+transcorderHisTbl.getServBit()+"." + transcorderHisTbl.getNewFlExt());
								
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
									workflow.setDefaultOpt(optTbl.getOptInfo());
								}

								// 프로파일과 관련된 옵션 조회
								params.put("proFlid", transcorderHisTbl.getProFlid());
								List<ProOptTbl> optTbls = proOptDao.findProOpt(params);
								if(optTbls != null && !optTbls.isEmpty()) {
									transcorderHisTbl.setOptions(optTbls);
								}
								
								// 프로파일 옵션 설정
								if (!transcorderHisTbl.getOptions().isEmpty()) {
									Option option = new Option();
									for (ProOptTbl proOptTbl : transcorderHisTbl.getOptions()) {
										Option opt = new Option();
										opt.setOptId(proOptTbl.getOptId());
										opt.setOptDesc(proOptTbl.getOptDesc());
										option.addOptions(opt);
									}
									job.setOption(option);
								}
								
								workflow.addJobList(job);
							}
							tcJobs.put(workflow);
						}
						
						
						Thread.sleep(100);
					}

				} catch (Exception e) {
					logger.error("[Transcode Job Control] TransferJobSwap Error - "+e.getMessage());
				} finally {
					try {
						Thread.sleep(1000L);
					} catch (Exception e2) {}
				}
			}
		}
	}

	public void stop() throws Exception {
		try {
			exec = false;
			
			Thread.sleep(1000L);

			if(!jobReq.isShutdown()) {
				jobReq.shutdownNow();
				if(logger.isInfoEnabled()) {
					logger.info("JobReq Thread shutdown now!!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
