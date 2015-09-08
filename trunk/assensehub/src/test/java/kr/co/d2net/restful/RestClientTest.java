package kr.co.d2net.restful;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlFileService;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dao.MediaToolDao;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.DairyOrderTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.xml.internal.FindList;
import kr.co.d2net.dto.xml.internal.Pgm;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.dto.xml.meta.Node;
import kr.co.d2net.dto.xml.meta.Nodes;
import kr.co.d2net.dto.xml.meta.Properties;
import kr.co.d2net.service.ContentsInstManagerService;
import kr.co.d2net.service.ContentsManagerService;
import kr.co.d2net.service.DairyOrderManagerService;
import kr.co.d2net.service.HttpRequestService;
import kr.co.d2net.service.HttpRequestServiceImpl;
import kr.co.d2net.service.MediaToolInterfaceService;
import kr.co.d2net.service.ProFlManagerService;
import kr.co.d2net.service.WeekSchManagerService;
import kr.co.d2net.task.diagram.WeekDiagramRequestJob;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author dekim
 *
 */
public class RestClientTest extends BaseRestConfig {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private MediaToolDao mediaToolDao;
	@Autowired
	private WeekSchManagerService weekSchManagerService;
	@Autowired
	private DairyOrderManagerService dairyOrdeManagerService;
	@Autowired
	private MediaToolInterfaceService mediaToolInterfaceService;
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private ContentsInstManagerService contentsInstManagerService;
	@Autowired
	private ProFlManagerService proFlManagerService;
	@Autowired
	private ContentsManagerService contentsManagerService;
	@Autowired
	private XmlFileService xmlFileService;

	@Test
	@Ignore 
	public void restResponseTest() {
		try {
			HttpEntity<String> entity = prepareGet(MediaType.APPLICATION_XML);
			ResponseEntity<Node> response = restTemplate
					.exchange(
							"http://211.233.93.8:80/api/mh_program_num/read.xml?program_id=PS-2012053614-01-000",
							//"http://kbs.i-on.net:8089/kbs/api/mh_program/read.xml?program_code",
							// "http://kbs.i-on.net:8089/kbs/api/mh_program_num/read.xml?program_id",
							HttpMethod.POST, entity, Node.class);
			Node node = response.getBody();
			List<Properties> properties = node.getProperties();
			System.out.println(properties.get(0).getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HttpEntity<String> prepareGet(MediaType type) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(type);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return entity;
	}

	/**
	 * 김정미 차장 인터페이스 타겟 주소
	 * 아카이브 XML정보 전달 테스트 
	 * 특이사항 : 김정비 차장 도메인 개발서버로 연동 필요
	 */
	@Test
	@Ignore
	public void testVdasMetaForward() {
		String xml = "";
		String rtnValue = "";
		String domain = messageSource.getMessage("meta.system.domain", null,
				Locale.KOREA);

		String method = messageSource.getMessage("meta.system.xml.vdas.forward",
				null, Locale.KOREA); // 비디오 아카이브 

		//		String method = messageSource.getMessage("meta.system.xml.nps.forward",
		//				null, Locale.KOREA); // nps 

		String URL = domain + method;
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			xml = FileUtils.readFileToString(
					new File("D:\\tmp\\1108290023.xml"), "UTF-8"); // 비디오 아카이브
			// xml = FileUtils.readFileToString(new File(
			// "D:\\tmp\\201112150074.xml"), "UTF-8"); // NPS 아카이브 xml
			// Sample

			params.put("contents", xml);

			rtnValue = Utility.connectHttpPostMethod(URL,
					Utility.convertNameValue(params));

			System.out.println("rtnValue :" + rtnValue);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	/**
	 * 김정미 차장 인터페이스 타겟 주소
	 * NPS XML전달 테스트 
	 */
	@Test
	@Ignore
	public void testNpsMetaForward() {
		String xml = "";
		String rtnValue = "";
		String domain = messageSource.getMessage("meta.system.domain", null,
				Locale.KOREA);

		String method = messageSource.getMessage("meta.system.xml.nps.forward",
				null, Locale.KOREA); // nps 

		String URL = domain + method;
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			xml = FileUtils.readFileToString(new File(
					"D:\\tmp\\201112150074.xml"), "UTF-8"); // NPS 아카이브 xml
			// Sample

			params.put("contents", xml);

			rtnValue = Utility.connectHttpPostMethod(URL,
					Utility.convertNameValue(params));

			System.out.println("rtnValue :" + rtnValue);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	/**
	 * 김정미 차장 인터페이스 타겟
	 * 다시보기 정보 조회
	 * @throws ServiceException 
	 */
	@Test
	@Ignore
	public void testMetaHubURL() throws ServiceException {
		try {
			/*
			 * 완료일경우 메타허브에 서비스URL을 등록요청
			 * 트랜스퍼 상태 저장
			 */
			Map<String,Object> params = new HashMap<String, Object>();
			ContentsInstTbl contentsInstTbl = new ContentsInstTbl();
			ProFlTbl proFlTbl = new ProFlTbl();

			params.put("seq", 1627);

			contentsInstTbl = contentsInstManagerService.getContentsInstForwardMeta(params);
			if(StringUtils.defaultString(contentsInstTbl.getSrvUrl(), "N").equals("Y")){
				if(contentsInstTbl.getProFlid() != null) {
					params.put("proFlid", contentsInstTbl.getProFlid());
					proFlTbl = proFlManagerService.getProFl(params);
				}

				params.clear();
				params.put("essence_id",String.valueOf("1627"));                     //에센스 허브 아이디                                  

				params.put("pcode",contentsInstTbl.getPgmCd());                          //프로그램코드               
				params.put("p_id",contentsInstTbl.getPgmId());                           //회별 프로그램 아이디       
				params.put("program_id",contentsInstTbl.getPgmId());                     //프로그램 아이디
				params.put("file_size",String.valueOf(contentsInstTbl.getFlSz()));                        //용량           
				params.put("filename",contentsInstTbl.getWrkFileNm()+"."+contentsInstTbl.getFlExt());     //파일이름       
				params.put("segment_id",contentsInstTbl.getPgmCd()+"-"+contentsInstTbl.getSegmentId());                     //TV일 경우 세그먼트 회차    
				params.put("essence_type1",contentsInstTbl.getCtCla());                  //에센스 타입1               
				params.put("essence_type2",contentsInstTbl.getCtTyp());                  //에센스 타입2               
				params.put("service_biz",contentsInstTbl.getAlias());                      //서비스 사업자              

				params.put("program_id_key","");                 //주간 편성 아이디           
				params.put("segment_code","");                   //세그먼트 프로그램 코드     
				params.put("segment_id_radio","");               //라디오일 경우 세그먼트 회차
				params.put("service_status","Y");                 //서비스 상태                
				params.put("audio_bitrate",proFlTbl.getAudBitRate());                  //오디오 비트레이트          
				params.put("audio_channel",proFlTbl.getAudChan());                  //오디오 채널                
				params.put("audio_codec",proFlTbl.getAudCodec());                    //오디오 코덱                
				params.put("audio_ext",proFlTbl.getExt());                      //오디오 확장자              
				params.put("audio_keyframe","");                 //keyframe                   
				params.put("audio_sampling",proFlTbl.getAudSRate());                   //오디오 샘플링  
				params.put("bitrate",proFlTbl.getServBit());                          //비트레이트     
				params.put("bitrate_code",proFlTbl.getFlNameRule());                     //비트레이트코드 
				params.put("file_format",proFlTbl.getExt());                      //파일 타입      
				params.put("framecount","");                       //비디오 F/S     
				params.put("height",proFlTbl.getVdoVert());                           //비디오 세로    
				params.put("video_aspect_ratio","");               //화면 비율      
				params.put("video_codec",proFlTbl.getVdoCodec());                      //비디오 코덱    
				params.put("video_quality_condition",proFlTbl.getPicKind());          //화질표시       
				params.put("video_resoution","");                  						//해상도         
				params.put("whaline",proFlTbl.getVdoSync());       //비디오 종횡맞춤
				params.put("width",proFlTbl.getVdoHori()); //비디오 가로    


				String domain = messageSource.getMessage("meta.system.domain", null,
						Locale.KOREA);
				String createMethod = messageSource.getMessage("meta.system.url.forward.create",
						null, Locale.KOREA);
				//				String updateMethod = messageSource.getMessage("meta.system.url.forward.update",
				//						null, Locale.KOREA);

				String rXml = "";
				try {
					//					rXml = Utility.connectHttpPostMethod(domain+createMethod,
					//							Utility.convertNameValue(params));
					System.out.println("rXml"+rXml);
				}catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 주간편성표 연동 
	 * http://118.221.109.4:8080/kbs/api/mh_weekly_schedule/list.xml?program_planned_date_morethan=20120130&program_planned_date_lessthan=20120213
	 * http://118.221.109.4:8080/kbs/api/mh_weekly_schedule/list.xml?program_planned_date_morethan=20120220&program_planned_date_lessthan=20120302&channel_code_equals=11
	 * 
	 */
	@Test
	//@Ignore
	public void testWeekSch(){

		//주간편성표 정보 연동
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("date_morethan", Utility.getDateOfWeek(1, -1, "yyyyMMdd")); // 금주 일요일
		params.put("date_lessthan", Utility.getDateOfWeek(7, 0, "yyyyMMdd")); // 다음주 월요일
		
		params.put("weekly_choice", "0");
		try {
			System.out.println("########################################");
			weekSchManagerService.connectToMetaHubRestFul_weekly(params);
			System.out.println("########################################");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testWeekSchConfirm(){

		Map<String, Object> params = new HashMap<String, Object>();

		for(int i=1; i<=2; i++) {
			params.clear();
			
			String dayBetween = Utility.getDayBetween((i-1) * 7, "yyyyMMdd");
			String[] days = dayBetween.split("\\,");

			//params.put("date_morethan", days[0]); // 월요일
			//params.put("date_lessthan", days[1]); // 일요일
			params.put("date_morethan", "20130520"); // 월요일
			params.put("date_lessthan", "20130524"); // 일요일
			
			params.put("weekly_choice", String.valueOf(i-1));

			System.out.println("start_date: "+days[0]+", end_date: "+days[1]);
			try { 
				weekSchManagerService.connectToMetaHubRestFul_weekly(params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			/*
			String channelCode = "11";
			String localCode = "10";

			//주간편성표 정보 연동
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("channel_code", channelCode); 	// 채널코드
			params.put("pls_code", localCode);			// 지역국 코드
			params.put("weekly_choice", "0");			// -1: 지난주, 0:이번주, 1: 다음주

			String change_method = messageSource.getMessage("meta.system.last.changed",null, Locale.KOREA);
			String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
			String confirmDate = null;

			String URL = domain + change_method;
			String rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));
			System.out.println(rtnValue);

			confirmDate = weekSchManagerService.forwardMetaXml(rtnValue, confirmDate, channelCode, localCode);
			System.out.println(confirmDate);
			 */

			/*
			WeekDiagramRequestJob.confirmDate.put(channelCode+localCode, confirmDate);

			String method = messageSource.getMessage("meta.system.search.weekly", null, Locale.KOREA);
			URL = domain + method;

			params.put("program_planned_date_morethan", "20130311"); // 금주 일요일
			params.put("program_planned_date_lessthan", "20130317"); // 다음주 월요일
			params.put("double_programming_type_code_equals","1");  // 이중편성중 1안 만 수용(2012-05-17)
			params.put("channel_code_equals", channelCode);
			params.put("programming_local_station_code_equals", localCode);
			rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));
			confirmDate = weekSchManagerService.forwardMetaXml(rtnValue, WeekDiagramRequestJob.confirmDate.get(channelCode+localCode), channelCode, localCode);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * 일일운행표 연동 
	 * 김정미 차장 서버 ( http://jmkim.i-on.net:8080/kcms/api )
	 * URL
	 * http://jmkim.i-on.net:8080/kcms/api/mh_daily_running/list.xml?running_date_equals=20120109
	 * http://118.221.109.4:8080/kbs/api/mh_daily_running/list.xml?running_date_equals=20120305
	 */
	@Ignore
	@Test
	public void testDairyOrder(){
		try {
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("running_date_equals", Utility.getTimestamp("20130227")); // 금일
			//params.put("broadcast_event_kind_equals", "프로그램"); // broadcast_event_kind.

			dairyOrdeManagerService.connectToMetaHubRestFul_dairy(params);

		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testDairyXml() {

		String path1tv = "1TV";
		String path2tv = "2TV";

		// LiveIngest에 주기적으로 xml 생성
		try {

			Map<String,Object> params = new HashMap<String,Object>();
			params.put("sdate", Utility.getDate(Utility.getTimestamp(), "yyyyMMdd"));
			params.put("edate", Utility.getDate(Utility.getTimestamp(), "yyyyMMdd"));
			List<DairyOrderTbl> dairyOrderTbls = dairyOrdeManagerService.findDairyOrderService(params);
			//List<DairyOrderTbl> dairyOrderTbls = dairyOrderManagerService.findDairyOrderervice(params);
			Workflow workflow_1tv = new Workflow();
			Workflow workflow_2tv = new Workflow();

			Pgm pgm = null;
			for (DairyOrderTbl dairyOrderTbl : dairyOrderTbls) {
				if(dairyOrderTbl.getChannelName().equals("1TV")){
					pgm = new Pgm();
					pgm.setPgmGrpCd(StringUtils.isEmpty(dairyOrderTbl.getGroupCode())?"":dairyOrderTbl.getGroupCode());
					pgm.setPgmCd(StringUtils.isEmpty(dairyOrderTbl.getProgramCode())?"":dairyOrderTbl.getProgramCode());
					pgm.setPgmId(StringUtils.isEmpty(dairyOrderTbl.getProgramId())?"":dairyOrderTbl.getProgramId());
					pgm.setPgmNm(StringUtils.isEmpty(dairyOrderTbl.getProgramTitle())?"":dairyOrderTbl.getProgramTitle());
					pgm.setStartTime(StringUtils.isEmpty(dairyOrderTbl.getRunningStartTime())?"":
						dairyOrderTbl.getRunningStartTime().substring(0, 2)+":"+
						dairyOrderTbl.getRunningStartTime().substring(2, 4)+":"+
						dairyOrderTbl.getRunningStartTime().substring(4, 6)
							);
					pgm.setEndTime(
							StringUtils.isEmpty(dairyOrderTbl.getRunningEndTime())?"":
								dairyOrderTbl.getRunningEndTime().substring(0, 2)+":"+
								dairyOrderTbl.getRunningEndTime().substring(2, 4)+":"+
								dairyOrderTbl.getRunningEndTime().substring(4, 6)
							);
					pgm.setRegYn(StringUtils.isEmpty(dairyOrderTbl.getRecyn())?"N":dairyOrderTbl.getRecyn());
					pgm.setVdQlty(dairyOrderTbl.getProductionVideoQuality());
					pgm.setRerunClassfication(dairyOrderTbl.getRerunClassification());

					workflow_1tv.addPgmList(pgm);
				}else if(dairyOrderTbl.getChannelName().equals("2TV")){
					pgm = new Pgm();
					pgm.setPgmGrpCd(StringUtils.isEmpty(dairyOrderTbl.getGroupCode())?"":dairyOrderTbl.getGroupCode());
					pgm.setPgmCd(StringUtils.isEmpty(dairyOrderTbl.getProgramCode())?"":dairyOrderTbl.getProgramCode());
					pgm.setPgmId(StringUtils.isEmpty(dairyOrderTbl.getProgramId())?"":dairyOrderTbl.getProgramId());
					pgm.setPgmNm(StringUtils.isEmpty(dairyOrderTbl.getProgramTitle())?"":dairyOrderTbl.getProgramTitle());
					pgm.setStartTime(StringUtils.isEmpty(dairyOrderTbl.getRunningStartTime())?"":
						dairyOrderTbl.getRunningStartTime().substring(0, 2)+":"+
						dairyOrderTbl.getRunningStartTime().substring(2, 4)+":"+
						dairyOrderTbl.getRunningStartTime().substring(4, 6)
							);
					pgm.setEndTime(
							StringUtils.isEmpty(dairyOrderTbl.getRunningEndTime())?"":
								dairyOrderTbl.getRunningEndTime().substring(0, 2)+":"+
								dairyOrderTbl.getRunningEndTime().substring(2, 4)+":"+
								dairyOrderTbl.getRunningEndTime().substring(4, 6)
							);
					pgm.setRegYn(StringUtils.isEmpty(dairyOrderTbl.getRecyn())?"N":dairyOrderTbl.getRecyn());
					pgm.setVdQlty(dairyOrderTbl.getProductionVideoQuality());
					pgm.setRerunClassfication(dairyOrderTbl.getRerunClassification());

					workflow_2tv.addPgmList(pgm);
				}
			}

			String xmlLoc = "E:\\tmp\\";

			// 1TV 용
			xmlFileService.StringToFile(xmlStream.toXML(workflow_1tv), xmlLoc+File.separator+path1tv,
					Utility.getTimestamp("yyyyMMdd") + ".xml");

			// 2TV 용
			xmlFileService.StringToFile(xmlStream.toXML(workflow_2tv), xmlLoc+File.separator+path2tv,
					Utility.getTimestamp("yyyyMMdd") + ".xml");

			// 1TV & 2TV log append
			xmlFileService.StringToFile(xmlStream.toXML(workflow_1tv), xmlLoc+File.separator+path1tv+File.separator+"log",
					Utility.getTimestamp("yyyyMMddHHmmss") + ".xml");
			xmlFileService.StringToFile(xmlStream.toXML(workflow_2tv), xmlLoc+File.separator+path2tv+File.separator+"log",
					Utility.getTimestamp("yyyyMMddHHmmss") + ".xml");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 프로그램 회차 정보 조회
	 * 김정미 차장 인터페이스
	 * http://jmkim.i-on.net:8080/kcms/api/mh_program_num/list.xml?program_title_contains=KBS
	 * http://118.221.109.4:8080/kbs/api/mh_program_num/list.xml?program_title_contains=KBS
	 * http://118.221.109.4:8080/kbs/api/mh_program_num/read.xml?
	 * 
	 */
	@Test
	@Ignore
	public void testProgramInfo(){
		try {
			Workflow workflow = new Workflow();

			workflow.setGubun("P");
			workflow.setKeyword("KBS");
			//			workflow.setPgmId("PS-2011126683-01-000");
			workflow.setPgmId("PS-2011177509-01-000");

			FindList list = mediaToolInterfaceService.reqMediaInfo(workflow);
			workflow.setList(list);

			list.getPgms();

			String retXml = xmlStream.toXML(workflow);
			System.out.println("retXml: "+retXml);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 세그먼트 정보 조회
	 * http://jmkim.i-on.net:8080/kcms/api/mh_segment_id/list.xml?program_id_equals=PS-2011126683-01-000
	 * http://10.30.14.173:8080/kbs/api/mh_segment_id/list.xml?program_id_equals=PS-2011126683-01-000
	 */
	@Test
	@Ignore
	public void testSegmentInfo(){
		try {
			Workflow workflow = new Workflow();

			workflow.setGubun("A");
			//			workflow.setKeyword("KBS");
			//workflow.setPgmId("PS-2011140035-01-000");
			workflow.setPgmId("PS-2012062056-01-000");

			FindList list = mediaToolInterfaceService.reqMediaInfo(workflow);
			workflow.setList(list);

			String retXml = xmlStream.toXML(workflow);
			System.out.println("retXml: "+retXml);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Test	
	@Ignore
	public void testProgramsList(){
		Map<String,Object> params = new HashMap<String,Object>();

		params.put("pagecount","20");
		params.put("pagesize","5");
		params.put("pagestart","1");
		params.put("pagecurrent","1");
		params.put("position",null);

		params.put("broadcast_planned_date_morethan", "20120423");
		params.put("broadcast_planned_date_lessthan", "20121029");
		//params.put("program_title_contains", "스포츠 하이라이트");
		params.put("searchvalue", "스포츠 하이라이트");
		params.put("pids","pcode,program_title,group_code,channel_code,broadcast_planned_date,broadcast_planned_time");  // 실제로 받아와야 할 컬럼들.
		//params.put("sorting", "broadcast_planned_date asc"); 

		String domain = messageSource.getMessage("meta.system.domain.sub", null,
				Locale.KOREA);
		String programMethod = messageSource.getMessage("meta.system.search.program",
				null, Locale.KOREA);
		/**
		 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
		 * param  pgmId;
		 */
		String programXml = "";

		// 시작시간
		long start = System.currentTimeMillis();
		try {
			programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params));
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("programXml:"+programXml);

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();

		if(StringUtils.isNotBlank(programXml))
			try {
				contentsTbl =mediaToolDao.forwardProgramsInfo(programXml);
			} catch (DaoNonRollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// 종료시간
		long end = System.currentTimeMillis();

		System.out.println( "실행 시간 : " + ( end - start )/1000.0 ); 

		for (ContentsTbl contentsTbl2 : contentsTbl) {
			System.out.println("***********");
			System.out.println(contentsTbl2.getChannelCode());
			System.out.println(contentsTbl2.getPgmGrpCd());
			System.out.println(contentsTbl2.getPgmNm());
			System.out.println(contentsTbl2.getBrdtime());
			System.out.println(contentsTbl2.getTime());
			System.out.println(contentsTbl2.getPgmCd());
			System.out.println();
		}
	}

	@Test
	@Ignore
	public void testProgramList() {
		Map<String,Object> params = new HashMap<String,Object>();

		//http://118.221.109.16:8080/kbs/api/program_num_view/list.xml?
		//pagesize=10&searchvalue=5&pagestart=1&
		//pids=pcode,program_id,program_title,group_code,channel_code,broadcast_planned_date,broadcast_planned_time&
		//broadcast_planned_date_lessthan=20130207&broadcast_planned_date_morethan=20130130&
		//pagecount=20&pagecurrent=1&program_id_equals=PS-2013012782-01-000
		params.put("pagecount","20");
		params.put("pagesize","5");
		params.put("pagestart","1");
		params.put("pagecurrent","1");
		params.put("searchvalue", "5");

		params.put("broadcast_planned_date_morethan", "2013013");
		params.put("broadcast_planned_date_lessthan", "20130207");
		//		//params.put("program_title_contains", "스포츠 하이라이트");
		//		params.put("searchvalue", "굿모닝 대한민국");
		//		params.put("pids","program_id,program_title,broadcast_planned_date,group_code,channel_code,pcode");  // 실제로 받아와야 할 컬럼들.
		//      params.put("sorting", "broadcast_planned_date asc"); 

		params.put("program_id_equals", "PS-2013012782-01-000");
		params.put("pids", "pcode,program_id,program_title,group_code,channel_code,broadcast_planned_date,broadcast_planned_time");  // 프로그램 코드 조회

		String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
		String programMethod = messageSource.getMessage("meta.system.search.program", null, Locale.KOREA);
		/**
		 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
		 * param  pgmId;
		 */
		String programXml = "";

		// 시작시간
		long start = System.currentTimeMillis();
		try {
			programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params));
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("programXml:"+programXml);

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();

		if(StringUtils.isNotBlank(programXml))
			try {
				mediaToolDao.forwardProgramInfo(programXml);
				//contentsTbl =mediaToolDao.forwardProgramsInfo(programXml);
			} catch (DaoNonRollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// 종료시간
		long end = System.currentTimeMillis();

		System.out.println( "실행 시간 : " + ( end - start )/1000.0 ); 

		/*for (ContentsTbl contentsTbl2 : contentsTbl) {
			System.out.println("***********");
			System.out.println(contentsTbl2.getChannelCode());
			System.out.println(contentsTbl2.getPgmGrpCd());
			System.out.println(contentsTbl2.getPgmNm());
			System.out.println(contentsTbl2.getBrdtime());
			System.out.println(contentsTbl2.getTime());
			System.out.println(contentsTbl2.getPgmCd());
			System.out.println();
		}*/
	}

	@Test
	@Ignore
	public void testProgramView(){
		try {
			Map<String,Object> params = new HashMap<String,Object>();

			params.put("program_id", "PS-2013012782-01-000");

			String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
			String programMethod = messageSource.getMessage("meta.system.search.program.read", null, Locale.KOREA);
			/**
			 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
			 * param  pgmId;
			 */
			String programXml = "";
			try {
				programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params));
				Map<String, Object> metaData = mediaToolDao.forwardContentsMLNodeInfo(programXml);

				Set<String> els = metaData.keySet();
				for(String key : els) {
					System.out.println(key+": "+metaData.get(key));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testContentsML(){
		try {
			String pgmId = "PS-2012053614-01-000";
			int proFlid = 373;
			String rTmp = "";

			rTmp = contentsManagerService.createContentML(pgmId, proFlid);
			System.out.println("rTmp:"+rTmp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testPeopleMapping(){
		Map<String, Object> params = new HashMap<String, Object>();
		//params.put("textsearchkeys","name_korea,casting_name");          // 프로그램 코드
		//params.put("textsearchvalue", "최은서");
		params.put("s_name_equals", "유재석");
		//params.put("pids","s_seq,name_korea,casting_name,image_url");  // 실제로 받아와야 할 컬럼들.
		//params.put("sorting", "name_korea asc");          // 배우명 소팅

		String domain = messageSource.getMessage("meta.system.domain.sub", null,
				Locale.KOREA);
		String programMethod = messageSource.getMessage("meta.system.search.person",
				null, Locale.KOREA);

		/**
		 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
		 * param  pgmId;
		 */
		String programXml = "";

		try {
			programXml = Utility.connectHttpPostMethod(domain+programMethod,
					Utility.convertNameValue(params));
			System.out.println("programXml:"+programXml);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testGroupINfo(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("group_code_equals", "R2012-0033");          // 프로그램 코드
		params.put("pids","alias_kor,alias_eng");  // 실제로 받아와야 할 컬럼들.

		String domain = messageSource.getMessage("meta.system.domain", null,
				Locale.KOREA);
		String programMethod = messageSource.getMessage("meta.system.search.group",
				null, Locale.KOREA);

		/**
		 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
		 * param  pgmId;
		 */
		String programXml = "";


		try {
			Map<String,Object> element = new HashMap<String,Object>();
			programXml = Utility.connectHttpPostMethod(domain+programMethod,
					Utility.convertNameValue(params));
			element =mediaToolInterfaceService.forwardMetaInfo(programXml);

			System.out.println("programXml:"+programXml);
			System.out.println("element.alias_kor:"+element.get("alias_kor"));
			System.out.println("element.alias_eng:"+element.get("alias_eng"));


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testDairyConfirm(){

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("service_type_equals", "mh_daily_running");
		params.put("pids", "last_confirm_date");
		params.put("channel_code_equals", "11");
		params.put("date_equals", Utility.getDate(new Date(), "yyyyMMdd"));

		String domain = messageSource.getMessage("meta.system.domain.sub", null, Locale.KOREA);
		String method = messageSource.getMessage("meta.system.last.confirm", null, Locale.KOREA);
		
		HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
		try {
			Nodes nodes = httpRequestService.findData(domain+method, Utility.convertNameValue(params), Nodes.class);
			
			String confirmDate = null;
			System.out.println(dairyOrdeManagerService.forwardMetaXml(nodes, confirmDate, "11"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
		 * param  pgmId;
		 */
		/*
		String programXml = "";
		String URL = domain + method;
		try {
			Map<String,Object> element = new HashMap<String,Object>();
			String rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));

			String confirmDate = null;
			confirmDate = dairyOrdeManagerService.forwardMetaXml(rtnValue, confirmDate, "11");
			System.out.println(confirmDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

}
