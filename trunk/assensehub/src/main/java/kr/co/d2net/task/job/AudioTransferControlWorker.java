package kr.co.d2net.task.job;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.TransferHisTbl;
import kr.co.d2net.service.WorkflowManagerService;
import kr.co.d2net.task.Worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("audioTransferControlWorker")
public class AudioTransferControlWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private WorkflowManagerService workflowManagerService;

	private Map<String, Object> params = new HashMap<String, Object>();
	
	final String transferGB = "Audio";

	@Override
	public void work() {
		try {

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

			if(true) {
				
				params.clear();
				List<TransferHisTbl> transferHisTbls = null;
				
				/*
				 * 예약전송이 적용되는 프로그램 본방 프로파일 영상 조회
				 */
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

				for(TransferHisTbl transferHisTbl :  transferHisTbls) {
					if(logger.isInfoEnabled()) {
						logger.info("work_statcd: "+transferHisTbl.getWorkStatcd());
						logger.info("work_fileNm: "+transferHisTbl.getWrkFileNm());
						logger.info("pgm_nm: "+transferHisTbl.getPgmNm());
					}
					if(transferHisTbl != null) {
						params.clear();

						transferHisTbl.setModDt(Utility.getTimestamp());
						transferHisTbl.setWorkStatcd("001"); // 대기상태[00] -> 요청상태 변경[01]
						workflowManagerService.updateTransferHisState(transferHisTbl);

						if(logger.isInfoEnabled()) {
							logger.info("TransferControlWorker put job : seq["+transferHisTbl.getSeq()+"], cti_id["+
									transferHisTbl.getCtiId()+"], statcd["+transferHisTbl.getWorkStatcd()+"]");
						}
							
						TransferJobControl.putJob1(transferHisTbl);
					}
				}
				
			}
			
		} catch (Exception e) {
			logger.error("[Transfer Job Control] TransferJobSwap Error", e);
		}
	}
}
