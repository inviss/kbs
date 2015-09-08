package kr.co.d2net.task.diagram;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.service.DairyOrderManagerService;
import kr.co.d2net.task.Worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dailyDiagramRequestWorker")
public class DailyDiagramRequestWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DairyOrderManagerService dairyOrderManagerService;

	public void work() {
		if(logger.isInfoEnabled()) {
			logger.debug("DailyDiagramRequestWorker is start."); 
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
			logger.info("DailyDiagramRequestWorker has completed work."); 
		}

	}
}
