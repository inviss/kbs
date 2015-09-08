package kr.co.d2net.task.diagram;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kr.co.d2net.commons.util.DateUtil;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlFileService;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.service.DairyOrderManagerService;
import kr.co.d2net.service.WeekSchManagerService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class DailyDiagramCheck {
	private static Log logger = LogFactory.getLog(DailyDiagramCheck.class);

	private ExecutorService dailyCheck = Executors.newSingleThreadExecutor();

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private XmlFileService xmlFileService;
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private WeekSchManagerService weekSchManagerService;
	@Autowired
	private DairyOrderManagerService dairyOrderManagerService;
	
	private volatile static boolean exec = true;

	public void start() {
		 dailyCheck.execute(new CheckThread());
		if (logger.isInfoEnabled()) {
			logger.info("dailyCheck Thread Start!!");
		}
	}

	class CheckThread implements Runnable {

		@Override
		public void run() {
			while (exec) {
				try {
					logger.debug(" DiagramRequestWorker info START! ");
					
					 // MetaHub에서 주기적으로 체크
					logger.debug("DiagramRequestWorker has completed work."); 
					
					 Map<String, Object> element = new HashMap<String, Object>();
					  
						
					  element.put("program_planned_date_morethan", Utility.getDateOfWeek(1,
					  0, "yyyyMMdd")); // 금주 일요일
					  
					  
					  element.put("program_planned_date_lessthan", Utility.getDateOfWeek(16,
					  0, "yyyyMMdd")); // 금주 월요일
					  
//					  element.put("running_date_equals","20120111");
//					  DateUtil.getCurrentDateString("yyyyMMdd")); // // "YYYYMMDD"
					  
					  element.put("running_date_equals",
							  DateUtil.getCurrentDateString("yyyyMMdd")); // // "YYYYMMDD"
					  
					  element.put("broadcast_event_kind_equals", "프로그램"); // broadcast_event_kind.
					  
					  //일일운행 정보 조회
					  try {
						  dairyOrderManagerService.connectToMetaHubRestFul_dairy(element); 
						  }
					  catch (Exception e) {
						  logger.error("dailyDiagramRequestWorker.dairyOrderManagerService :"+
								  e); }


					  //주간편성표 정보 연동
					  try { 
						  weekSchManagerService.connectToMetaHubRestFul_weekly(element);
					  } catch (Exception e) {
						  logger.error("dailyDiagramRequestWorker.weekSchManagerService :" +
								  e); }
					  
					  
					  if (logger.isDebugEnabled()) {
					  logger.debug("DiagramRequestWorker has completed work."); }
					  
					logger.debug("Dairy Order info END! ");
				} catch (Exception e) {
					logger.error("CheckThread Error", e);
				}
				try {
					// 600초 = 10분 주기
					Thread.sleep(600000L);
				
				} catch (Exception e) {
				}
			}
		}
	}

	

	public void stop() throws Exception{
		exec = false;
		
		Thread.sleep(1000L);
		if (!dailyCheck.isShutdown()) {
			dailyCheck.shutdownNow();
			if (logger.isInfoEnabled()) {
				logger.info("CheckThread Exec Thread shutdown now!!");
			}
		}
	}
}
