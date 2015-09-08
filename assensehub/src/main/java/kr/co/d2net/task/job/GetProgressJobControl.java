package kr.co.d2net.task.job;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.service.ContentsManagerService;
import kr.co.d2net.service.DmcrStatusService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

//@Component("getProgressJobControl")
public class GetProgressJobControl {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ContentsManagerService contentsManagerService;
	@Autowired
	private DmcrStatusService dmcrStatusService;
	
	private ExecutorService thread = Executors.newSingleThreadExecutor();
	
	@PostConstruct
	public void start() throws Exception {
		thread.execute(new GetProgressJob());
		if(logger.isInfoEnabled()) {
			logger.info("GetProgressJob Thread start !!");
		}
	}
	
	
	public class GetProgressJob implements Runnable {

		@Override
		public void run() {
			while(true) {
				try {
					Map<String, Object> params = new HashMap<String, Object>();
					
					String regrids = messageSource.getMessage("cts.noti.system", null, Locale.KOREA);
					params.put("regrids", regrids);
					params.put("dataStatCd", "002");
					
					List<ContentsTbl> contentsTbls = contentsManagerService.findContents(params);
					for(ContentsTbl contentsTbl : contentsTbls) {
						dmcrStatusService.getJobStatus(contentsTbl.getCtId(), Integer.valueOf(contentsTbl.getJobId()));
					}
				} catch (Exception e) {
					logger.error("[GetProgressJob] Error - "+e.getMessage());
				} finally {
					try {
						Thread.sleep(1000L);
					} catch (Exception e2) {}
				}
			}
		}
		
	}
	
	@PreDestroy
	public void stop() throws Exception {
		try {
			if(!thread.isShutdown()) {
				thread.shutdownNow();
				if(logger.isInfoEnabled()) {
					logger.info("GetProgressJob Thread shutdown now!!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
