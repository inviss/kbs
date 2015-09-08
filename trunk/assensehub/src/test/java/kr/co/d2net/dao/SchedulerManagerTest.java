package kr.co.d2net.dao;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.converter.xml.PropertyConverter;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.DairyOrderTbl;
import kr.co.d2net.dto.LiveTbl;
import kr.co.d2net.dto.WeekSchTbl;
import kr.co.d2net.dto.xml.meta.Node;
import kr.co.d2net.dto.xml.meta.Nodes;
import kr.co.d2net.dto.xml.meta.Properties;
import kr.co.d2net.service.DairyOrderManagerService;
import kr.co.d2net.service.LiveManagerService;
import kr.co.d2net.service.MediaToolInterfaceService;
import kr.co.d2net.service.WeekSchManagerService;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class SchedulerManagerTest extends BaseDaoConfig {

	@Autowired
	private WeekSchManagerService weekSchManagerService;

	@Autowired
	private DairyOrderManagerService dairyOrderManagerService;

	@Autowired
	private LiveManagerService liveManagerService;

	@Autowired
	private MediaToolInterfaceService mediaToolInterfaceService;

	@Autowired
	private XmlStream xmlStream;

	@Before
	public void init() {
		try {
			xmlStream.setAnnotationAlias(Node.class);
			xmlStream.setAnnotationAlias(Nodes.class);
			xmlStream.setAnnotationAlias(Properties.class);
			xmlStream.setConverter(new PropertyConverter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void updateLiveTest() {
		try {

			LiveTbl liveTbl = new LiveTbl();
			Calendar cal = Calendar.getInstance();
			liveTbl.setBgnTime(cal.getTime());
			liveTbl.setEndTime(cal.getTime());
			liveTbl.setProgramCode("T104-10001");
			liveTbl.setRecyn("Y");

			liveManagerService.updateLiveTbl(liveTbl);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void getLiveTest() {
		try {

			//LiveTbl liveTbl = new LiveTbl();
			//liveTbl.setProgramCode("t7393-0001");
			//liveManagerService.getLiveTbl(liveTbl.getProgramCode());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void findWeekSchTest() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();

			// 검색조건 : 채널코드, 주간시작일, 주간종료일

			Calendar cal = Calendar.getInstance();

			params.put("channelCode", "12");
			params.put("sdate", Utility.getDateOfWeek(2, 0)); // 첫번째 인자
			params.put("sdate", Utility.getDateOfWeek(2, 0, cal.getTime())); // 첫번째
																				// 인자
			// 월요일(2),
			// // 두번째 인자 이번주(0)
			// // 다음주(7)
			params.put("edate", Utility.getDateOfWeek(8, 0)); // 첫번째 인자
			// 일요일(8),
			// // 두번째 인자 이번주(0)
			// // 다음주(7)
			System.out.println("#LIST size : "
					+ weekSchManagerService.findWeekSchTbl(params).size());

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	@Ignore
	// @Transactional
	@Test
	public void insertWeekSchTest() {
		try {
			WeekSchTbl weekSchTbl = new WeekSchTbl();

			weekSchTbl.setChannelCode("12");
			weekSchTbl.setProgramPlannedStartTime("1700");
			weekSchTbl.setOnairDay("20111118");

			// weekSchManagerService.
			weekSchManagerService.insertWeekSchTbl(weekSchTbl);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	@Ignore
	@Transactional
	@Test
	public void deleteWeekSchTest() {
		try {
			// WeekSchTbl weekSchTbl = new WeekSchTbl();
			//
			// weekSchTbl.setChannelCode("11");
			// weekSchTbl.setProgramPlannedStartTime("1700");
			// weekSchTbl.setOnairDay("20111114");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Ignore
	@Transactional
	@Test
	public void getWeekSchtest() {
		try {

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("channelCode", "12");
			params.put("onairDay", "20111111");
			params.put("programPlannedStartTime", "1700");

			weekSchManagerService.getWeekSchTbl(params);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Ignore
	@Test
	public void weekXmlTest() {
		try {

			String weekXml = FileUtils.readFileToString(new File(
					"D:\\tmp\\week.xml"), "UTF-8");
			Nodes nodes = (Nodes) xmlStream.fromXML(weekXml);
			System.out.println(nodes.getNodeList().size());
			for (Node node : nodes.getNodeList()) {
				List<Properties> propertyList = node.getProperties();

				WeekSchTbl bean = new WeekSchTbl();
				for (Properties properties : propertyList) {
					if ((properties.getType().equals("CODE")
							|| properties.getType().equals("DATE") || properties
							.getPid().endsWith("code"))
							&& !properties.getPid().equals("onair_day_code"))
						Utility.setValue(bean, properties.getPid(),
								properties.getValue());
					else
						Utility.setValue(bean, properties.getPid(),
								properties.getEleValue());
				}
				weekSchManagerService.insertWeekSchTbl(bean);
				// break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void dairyOrderXmlTest() {
		try {

			String dairyOrderXml = FileUtils.readFileToString(new File(
					"D:\\tmp\\dairyOrder.xml"), "UTF-8");
			Nodes nodes = (Nodes) xmlStream.fromXML(dairyOrderXml);
			System.out.println(nodes.getNodeList().size());
			for (Node node : nodes.getNodeList()) {
				List<Properties> propertyList = node.getProperties();

				DairyOrderTbl bean = new DairyOrderTbl();
				for (Properties properties : propertyList) {
					if (properties.getPid().equals("running_start_time")
							|| properties.getPid().equals("running_end_time")) {

						Utility.setValue(bean, properties.getPid(), Utility
								.convertStamp(Long.parseLong(properties
										.getEleValue())));
					} else {
						Utility.setValue(bean, properties.getPid(),
								properties.getEleValue());
					}
				}
				dairyOrderManagerService.insertDairyOrderService(bean);
				// break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 미디어 툴 인터페이스
	 * 
	 * @param xml
	 */
	// @Ignore
	// @Test
	// public void getWorkflowXml(String xml) {
	// Workflow workflow = (Workflow) xmlStream.fromXML(xml);
	//
	// if (workflow.getGubun().equals("P")) { // 프로그램
	// connectToMetaHubRestFul_programNum(workflow);
	// } else if (workflow.getGubun().equals("C")) { // 콘텐츠
	//
	// } else if (workflow.getGubun().equals("S")) { // 세그먼트
	// connectToMetaHubRestFul_segment(workflow);
	// }
	//
	// }

	/**
	 * 주간편성표 정보 콜
	 */
	@Ignore
	@Test
	public void connectToMetaHubRestFul_weekly() {

		String URL = "http://kbs.i-on.net:8089/kbs/api/mh_weekly_schedule/list.xml";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pagesize", "10");
		params.put("pagecount", "10");
		params.put("program_planned_date_morethan", "20111204");
		params.put("program_planned_date_lessthan", "20111212");

		String rtnValue = "";

		try {
			rtnValue = Utility.connectHttpPostMethod(URL,
					Utility.convertNameValue(params));
			System.out.println("trnValue : " + rtnValue);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 일일운행표 정보 콜
	 */
	@Ignore
	@Test
	public void connectToMetaHubRestFul_dairy() {

		String URL = "http://kbs.i-on.net:8089/kbs/api/mh_daily_running/list.xml";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pagesize", "10");
		params.put("pagecount", "10");
		params.put("running_date_equals", "20111204"); // morethan >

		String rtnValue = "";

		try {
			rtnValue = Utility.connectHttpPostMethod(URL,
					Utility.convertNameValue(params));
			System.out.println("trnValue : " + rtnValue);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/*
	@Test
	@Ignore
	public void testMetaHubTest() {

		Workflow workflow = new Workflow();
		workflow.setGubun("C");
		workflow.setKeyword("해피");
		try {
			FindList fList = mediaToolInterfaceService.reqMediaInfo(workflow);
			for (Content content : fList.getContents()) {
				System.out.println(content.getPgmNm());
				System.out.println(content.getCtNm());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	public void testTest(){

	}
}
