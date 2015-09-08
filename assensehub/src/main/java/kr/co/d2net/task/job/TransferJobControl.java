package kr.co.d2net.task.job;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kr.co.d2net.commons.util.SwappingFifoQueue;
import kr.co.d2net.dto.TransferHisTbl;
import kr.co.d2net.service.WorkflowManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("transferJobControl")
public class TransferJobControl {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private WorkflowManagerService workflowManagerService;

	private static volatile SwappingFifoQueue<TransferHisTbl> tfJobs1 = new SwappingFifoQueue<TransferHisTbl>();
	private static volatile SwappingFifoQueue<TransferHisTbl> tfJobs2 = new SwappingFifoQueue<TransferHisTbl>();
	private static volatile SwappingFifoQueue<TransferHisTbl> tfJobs3 = new SwappingFifoQueue<TransferHisTbl>();
	private static volatile SwappingFifoQueue<TransferHisTbl> highJob = new SwappingFifoQueue<TransferHisTbl>();

	private ExecutorService jobReq = Executors.newSingleThreadExecutor();
	private static Integer swap = 0;
	private Map<String, Object> params = new HashMap<String, Object>();

	private volatile static boolean exec = true;

	public void start() throws Exception {
		swap = Integer.valueOf(messageSource.getMessage("trsf.swap.size", null, Locale.KOREA));
		jobReq.execute(new TransferJobSwap());
	}
	
	public static Integer getTf1Size() {
		return tfJobs1.size();
	}
	public static Integer getTf2Size() {
		return tfJobs2.size();
	}
	public static Integer getTf3Size() {
		return tfJobs3.size();
	}
	public static Integer getHighSize() {
		return highJob.size();
	}
	
	public static TransferHisTbl getJob1() {
		return tfJobs1.get();
	}
	public static TransferHisTbl getJob2() {
		return tfJobs2.get();
	}
	public static TransferHisTbl getJob3() {
		return tfJobs3.get();
	}
	public static TransferHisTbl getHighJob() {
		return highJob.get();
	}
	
	public static void putJob1(TransferHisTbl transferHisTbl) {
		tfJobs1.put(transferHisTbl);
	}
	public static void putJob2(TransferHisTbl transferHisTbl) {
		tfJobs2.put(transferHisTbl);
	}
	public static void putJob3(TransferHisTbl transferHisTbl) {
		tfJobs3.put(transferHisTbl);
	}
	public static void putHighJob(TransferHisTbl transferHisTbl) {
		highJob.put(transferHisTbl);
	}

	public class TransferJobSwap implements Runnable {

		@Override
		public void run() {
			while(exec) {
				try {
/*
					logger.debug("Transfer Job - "+tfJobs.size());
					if(tfJobs.isEmpty() || tfJobs.size() < swap) {
						List<TransferHisTbl> transferHisTbls = null;
						if(tfJobs.isEmpty()) {
							transferHisTbls = workflowManagerService.findTransferJob("001", null);
							if(transferHisTbls == null || transferHisTbls.size() <= 0) {
								transferHisTbls = workflowManagerService.findTransferJob(swap - tfJobs.size(), null);
							}
						} else {
							transferHisTbls = workflowManagerService.findTransferJob(swap - tfJobs.size(), null);
						}
						 
						for(TransferHisTbl transferHisTbl :  transferHisTbls) {
							if(transferHisTbl != null) {
								params.clear();

								transferHisTbl.setModDt(Utility.getTimestamp());
								transferHisTbl.setWorkStatcd("001"); // 대기상태[00] -> 요청상태 변경[01]
								workflowManagerService.updateTransferHisState(transferHisTbl);
							}
							tfJobs.put(transferHisTbl);
							Thread.sleep(100);
						}
					}
*/
				} catch (Exception e) {
					logger.error("[Transfer Job Control] TransferJobSwap Error - "+e.getMessage());
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
