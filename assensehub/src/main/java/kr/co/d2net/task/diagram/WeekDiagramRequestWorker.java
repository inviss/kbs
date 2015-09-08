package kr.co.d2net.task.diagram;

import java.util.HashMap;
import java.util.Map;

import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.service.WeekSchManagerService;
import kr.co.d2net.task.Worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("weekDiagramRequestWorker")
public class WeekDiagramRequestWorker implements Worker {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WeekSchManagerService weekSchManagerService;

	@Override
	public void work() {
		logger.debug("WeekDiagramRequestWorker has start work."); 

		//주간편성표 정보 연동
		Map<String, Object> params = new HashMap<String, Object>();

		String dayBetween = null;
		for(int i=1; i<=2; i++) {  // [1: 이번주, 2: 다음주]
			params.clear();
			
			dayBetween = Utility.getDayBetween((Integer.valueOf(i) -1) * 7, "yyyyMMdd");
			String[] days = dayBetween.split("\\,");
			
			params.put("date_morethan", days[0]); // 금주
			params.put("date_lessthan", days[1]); // 다음주
			
			params.put("weekly_choice", String.valueOf(i-1));
			
			//params.put("program_planned_date_morethan", Utility.getDateOfWeek(1, -1, "yyyyMMdd")); // 금주 일요일
			//params.put("program_planned_date_lessthan", Utility.getDateOfWeek(7, 0, "yyyyMMdd")); // 다음주 월요일

			if(logger.isInfoEnabled()) {
				logger.info("start_date: "+days[0]+", end_date: "+days[1]);
			}
			try { 
				weekSchManagerService.connectToMetaHubRestFul_weekly(params);
			} catch (Exception e) {
				logger.error("WeekDiagramRequestWorker.weekSchManagerService :" +e);
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("WeekDiagramRequestWorker has completed work."); 
		}

	}

}
