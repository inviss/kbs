package kr.co.d2net.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dao.QuartzManagerDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="quartzManagerService")
public class QuartzManagerServiceImpl implements QuartzManagerService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private QuartzManagerDao quartzManagerDao;
	
	private final String QRTZ_TRIGGERS = "NEXT_FIRE_TIME|TRIGGER_STATE|NEXT_FIRE_TIME,TRIGGER_STATE|IS_VOLATILE";
	private final String QRTZ_FIRED_TRIGGERS = "TRIGGER_NAME|TRIGGER_GROUP|TRIGGER_NAME,TRIGGER_GROUP|IS_VOLATILE|INSTANCE_NAME|JOB_NAME|JOB_GROUP|IS_STATEFUL|REQUESTS_RECOVERY";
	
	@SuppressWarnings("serial")
	final List<String> QRTZ_TABLE_LIST = new ArrayList<String>() {
		{
			add("QRTZ_JOB_DETAILS");
			add("QRTZ_JOB_LISTENERS");
			add("QRTZ_TRIGGERS");
			add("QRTZ_SIMPLE_TRIGGERS");
			add("QRTZ_CRON_TRIGGERS");
			add("QRTZ_BLOB_TRIGGERS");
			add("QRTZ_TRIGGER_LISTENERS");
			add("QRTZ_CALENDARS");
			add("QRTZ_FIRED_TRIGGERS");
			add("QRTZ_PAUSED_TRIGGER_GRPS");
			add("QRTZ_SCHEDULER_STATE");
			add("QRTZ_LOCKS");
		}
	};
	
	@SuppressWarnings("serial")
	final List<String> LOCK_NAMES = new ArrayList<String>() {
		{
			add("TRIGGER_ACCESS");
			add("JOB_ACCESS");
			add("CALENDAR_ACCESS");
			add("STATE_ACCESS");
			add("MISFIRE_ACCESS");
		}
	};
	

	public List<String> findQuartzTables() throws ServiceException {
		return quartzManagerDao.findQuartzTables();
	}

	public void createQuartzTables(List<String> tableNames)
			throws ServiceException {
		
		Map<String, String> params = new HashMap<String, String>();
		
		for(String tableName : QRTZ_TABLE_LIST) {
			if(!tableNames.contains(tableName)) {
				if(logger.isInfoEnabled()) {
					logger.info("Quartz Table Create: "+tableName);
				}
				params.clear();
				params.put("tableName", tableName);
				
				quartzManagerDao.createQuartzTables(params);
				if(tableName.equals("QRTZ_LOCKS")) {
					for(String lockName : LOCK_NAMES) {
						quartzManagerDao.insertLockName(lockName);
					}
				}
				
				if(tableName.equals("QRTZ_JOB_DETAILS") || tableName.equals("QRTZ_TRIGGERS") || tableName.equals("QRTZ_FIRED_TRIGGERS")) {
					List<String> indexs = quartzManagerDao.findTableIndexs(tableName);
					if(indexs.isEmpty()) {
						if(tableName.equals("QRTZ_JOB_DETAILS")) {
							params.put("idx", String.valueOf(0));
							params.put("columnName", "REQUESTS_RECOVERY");
							quartzManagerDao.createTableIndex(params);
						} else {
							String[] idxs = null;
							
							if(tableName.equals("QRTZ_TRIGGERS")) {
								idxs =  QRTZ_TRIGGERS.split("\\|");
							} else {
								idxs =  QRTZ_FIRED_TRIGGERS.split("\\|");
							}
							
							for(int i=0; i<idxs.length; i++) {
								params.put("idx", String.valueOf(i));
								params.put("columnName", idxs[i]);
								quartzManagerDao.createTableIndex(params);
							}
						}
					}
				}
				
			}
		}
	}

	public void quartzTableInitialize()
			throws ServiceException {
		
		/*
		 * Quartz 관련 테이블중 몇몇 테이블에서 Forein key 가 설정되어 있으므로 
		 * 데이타를 삭제를 할 경우에는 생성된 테이블의 역순으로 삭제처리를 진행해야 한다.
		 */
		List<String> tableNames = QRTZ_TABLE_LIST;
		Collections.reverse(tableNames);

		for(String tableName : tableNames) {
			if(!tableName.equals("QRTZ_LOCKS"))
				quartzManagerDao.quartzTableInitialize(tableName);
		}
	}
	
	
}
