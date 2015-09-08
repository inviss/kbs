package kr.co.d2net.task.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.TransferHisTbl;
import kr.co.d2net.service.WorkflowManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("audioTransferControlExecutor")
public class AudioTransferControlExecutor {
	final Logger logger = LoggerFactory.getLogger(getClass());

	private ExecutorService thread = Executors.newSingleThreadExecutor();
	private final long THREAD_WAIT_TIME = 1000L * 30;

	@Autowired
	private WorkflowManagerService workflowManagerService;
	@Autowired
	private MessageSource messageSource;

	@PostConstruct
	public void start() {
		thread.execute(new AudioTransferJob());
		if(logger.isInfoEnabled()) {
			logger.info("AudioTransferControlExecutor Thread start !!");
		}
	}

	public class AudioTransferJob implements Runnable {

		private final String transferGB = "Audio";
		private List<TransferHisTbl> transferHisTbls = new ArrayList<TransferHisTbl>();
		
		@Override
		public void run() {
			while(true) {

				int swap = Integer.valueOf(messageSource.getMessage("trsf.swap.size", null, Locale.KOREA));
				Boolean highUsed = Boolean.valueOf(messageSource.getMessage("trsf.high.used", null, Locale.KOREA));

				int tf1Size = TransferJobControl.getTf1Size();
				int highSize = TransferJobControl.getHighSize();

				if(logger.isInfoEnabled()) {
					logger.info("AudioTransferControlWorker Low1 Job - "+tf1Size);
					logger.info("AudioTransferControlWorker highUsed - "+highUsed.toString());
					if(highUsed.booleanValue())
						logger.info("AudioTransferControlWorker High Job - "+highSize);
				}

				transferHisTbls.clear();
				
				/*
				 * 예약전송이 적용되는 프로그램 본방 프로파일 영상 조회
				 */
				try {
					if(tf1Size == 0) {
						transferHisTbls = workflowManagerService.findTransferJob("001", null, transferGB);
						if(logger.isInfoEnabled()) {
							logger.info("001===>"+transferHisTbls.size());
						}
						if(transferHisTbls == null || transferHisTbls.size() <= 0) {
							transferHisTbls = workflowManagerService.findTransferJob(swap - tf1Size, null, transferGB);
							if(logger.isInfoEnabled()) {
								logger.info("000===>"+transferHisTbls.size());
							}
						}
					} else {
						transferHisTbls = workflowManagerService.findTransferJob(swap - tf1Size, null, transferGB);
						if(logger.isInfoEnabled()) {
							logger.info("000===>"+transferHisTbls.size());
						}
					}
				} catch (Exception e) {
					logger.error("findTransferJob error", e);
				}
				

				for(TransferHisTbl transferHisTbl :  transferHisTbls) {
					if(logger.isInfoEnabled()) {
						logger.info("work_statcd: "+transferHisTbl.getWorkStatcd());
						logger.info("work_fileNm: "+transferHisTbl.getWrkFileNm());
						logger.info("pgm_nm: "+transferHisTbl.getPgmNm());
					}
					if(transferHisTbl != null) {
						try {
							transferHisTbl.setModDt(Utility.getTimestamp());
							transferHisTbl.setWorkStatcd("001"); // 대기상태[00] -> 요청상태 변경[01]
							workflowManagerService.updateTransferHisState(transferHisTbl);

							if(logger.isInfoEnabled()) {
								logger.info("TransferControlWorker put job : seq["+transferHisTbl.getSeq()+"], cti_id["+
										transferHisTbl.getCtiId()+"], statcd["+transferHisTbl.getWorkStatcd()+"]");
							}

							TransferJobControl.putJob1(transferHisTbl);
						} catch (Exception e) {
							logger.error("updateTransferHisState error", e);
						}
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
				logger.info("AudioTransferControlExecutor shutdown now!!");
			}
		}
	}
}
