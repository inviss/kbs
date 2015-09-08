package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.Map;

import kr.co.d2net.commons.util.DateUtil;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.DairyOrderTbl;
import kr.co.d2net.service.DairyOrderManagerService;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class DairyOrderManagerTest extends BaseDaoConfig {

	@Autowired
	private DairyOrderManagerService dairyOrderManagerService;

	@Autowired
	private XmlStream xmlStream;

	@Ignore
	@Transactional
	@Test
	public void insertDairyOrderTest() {
		try {
			DairyOrderTbl dairyOrderTbl = new DairyOrderTbl();

			dairyOrderTbl.setChannelCode("12");
			dairyOrderTbl.setRunningDate("20111117"); // char 8
			dairyOrderTbl.setRunningStartTime("1710"); // char 8

			dairyOrderManagerService.insertDairyOrderService(dairyOrderTbl);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void findDairyOrderTest() {

		try {
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("channelCode", "12");
			params.put("runningDate", DateUtil.getCurrentDateString());
			// params.put("runningStartTime", "2011/11/10 10:10:10");

			System.out.println(dairyOrderManagerService.findDairyOrderService(
					params).size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Ignore
	@Transactional
	@Test
	public void getDairyOrderTest() {

		try {
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("channelCode", "12");
			params.put("runningDate", DateUtil.getCurrentDateString());
			params.put("runningStartTime", "1200");

			dairyOrderManagerService.getDairyOrderService(params);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
