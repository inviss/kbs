package kr.co.d2net.task.diagram;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.service.DairyOrderManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//@Component("dailyDiagramRequestExecutor")
public class StorageCheckControlExecutor {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private ExecutorService thread = Executors.newSingleThreadExecutor();
	private final long THREAD_WAIT_TIME = 1000L * 30;

	@Autowired
	private DairyOrderManagerService dairyOrderManagerService;

	@PostConstruct
	public void start() {
		thread.execute(new DailyDiagramRequestJob());
		if(logger.isInfoEnabled()) {
			logger.info("DailyDiagramRequestExecutor Thread start !!");
		}
	}

	public class DailyDiagramRequestJob implements Runnable {

		@Override
		public void run() {
			while(true) {

				if(logger.isInfoEnabled()) {
					logger.debug("DailyDiagramRequestExecutor is start."); 
				}

				//일일운행 정보 조회

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("broadcast_event_kind_equals", "프로그램"); // broadcast_event_kind.

				try {
					// 오늘, 내일
					for(int i=0; i<=2; i++) {
						params.put("daykind", i);
						params.put("running_date_equals", Utility.getDate(new Date(), "yyyyMMdd", (i-1)));
						dairyOrderManagerService.connectToMetaHubRestFul_dairy(params); 
					}
				} catch (Exception e) {
					logger.error("dailyDiagramRequestWorker.dairyOrderManagerService :"+e); 
				}

				if (logger.isInfoEnabled()) {
					logger.info("DailyDiagramRequestExecutor has completed work."); 
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
				logger.info("DailyDiagramRequestExecutor shutdown now!!");
			}
		}
	}
}
