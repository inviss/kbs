package kr.co.d2net.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.DateUtil;
import kr.co.d2net.commons.util.DoadFilenameFilter;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.commons.util.StringUtil;
import kr.co.d2net.commons.util.UserFilenameFilter;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlFileService;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dao.CodeDao;
import kr.co.d2net.dto.Attatch;
import kr.co.d2net.dto.BusiPartnerPgm;
import kr.co.d2net.dto.BusiPartnerTbl;
import kr.co.d2net.dto.CodeTbl;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.DisuseInfoTbl;
import kr.co.d2net.dto.ProBusiTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.TranscorderHisTbl;
import kr.co.d2net.dto.TransferHisTbl;
import kr.co.d2net.dto.UserTbl;
import kr.co.d2net.dto.WeekSchTbl;
import kr.co.d2net.dto.xml.archive.ArchiveReq;
import kr.co.d2net.dto.xml.archive.ArchiveRes;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.dto.xml.meta.Nodes;
import kr.co.d2net.gate1.soap.Navigator;
import kr.co.d2net.gate1.soap.ServiceNavigatorService;
import kr.co.d2net.gate1.soap.ServiceNavigatorServiceLocator;
import kr.co.d2net.service.BusiPartnerManagerService;
import kr.co.d2net.service.BusiPartnerPgmManagerService;
import kr.co.d2net.service.CodeManagerService;
import kr.co.d2net.service.ContentsInstManagerService;
import kr.co.d2net.service.ContentsManagerService;
import kr.co.d2net.service.DisuseInfoManagerService;
import kr.co.d2net.service.HttpRequestService;
import kr.co.d2net.service.HttpRequestServiceImpl;
import kr.co.d2net.service.MediaToolInterfaceService;
import kr.co.d2net.service.ProBusiManagerService;
import kr.co.d2net.service.ProFlManagerService;
import kr.co.d2net.service.WeekSchManagerService;
import kr.co.d2net.service.WorkflowManagerService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.malgn.aesDemo.AESUtil;

@Controller
public class ContentManageControl {

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private ContentsManagerService contentsManagerService;
	@Autowired
	private ContentsInstManagerService contentsInstManagerService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private XmlFileService xmlFileService;
	@Autowired
	private DisuseInfoManagerService disuseinfoManagerService;
	@Autowired
	private WeekSchManagerService weekSchManagerService;
	@Autowired
	private ProBusiManagerService proBusiManagerService;
	@Autowired
	private ProFlManagerService proFlManagerService;
	@Autowired
	private BusiPartnerManagerService busiPartnerManagerService;
	@Autowired
	private WorkflowManagerService workflowManagerService;
	@Autowired
	private CodeManagerService codeManagerService;
	@Autowired
	private CodeDao codeDao;
	@Autowired
	private BusiPartnerPgmManagerService busiPartnerPgmManagerService;
	@Autowired
	private MediaToolInterfaceService mediaToolInterfaceService;

	final Logger logger = LoggerFactory.getLogger(getClass());

	final static Object o = new Object();

	@RequestMapping(value = "/content/ContentSearch.ssc")
	public ModelMap contentSearchList(@ModelAttribute("search") Search search,
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("7");
		}

		logger.debug("####search.getStartDt():" + search.getStartDt());
		logger.debug("####search.getEndDt():" + search.getEndDt());
		logger.debug("####search.getChannelCode():" + search.getChannelCode());
		logger.debug("####search.getPageNo():" + search.getPageNo());

		// 기간검색 설정
		if (search.getStartDt() != null) {
			map.addAttribute("startDt", Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		}
		if (search.getEndDt() != null) {
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		}

		// 키워드 설정
		if (search.getKeyword() != null || search.getKeyword() != "") 
			map.addAttribute("keyword", search.getKeyword());


		try {
			PaginationSupport<ContentsTbl> contents = contentsManagerService.findContentsList(search);
			map.addAttribute("contents", contents);
		} catch (Exception e) {
			logger.error("콘텐츠 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		return map;
	}

	@RequestMapping(value = "/content/ContentSearch2.ssc")
	public ModelMap contentSearchList_bak(@ModelAttribute("search") Search search,
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("7");
		}

		logger.debug("####search.getStartDt():" + search.getStartDt());
		logger.debug("####search.getEndDt():" + search.getEndDt());
		logger.debug("####search.getChannelCode():" + search.getChannelCode());
		logger.debug("####search.getKeyword():" + search.getKeyword());
		logger.debug("####search.getPageNo():" + search.getPageNo());

		// 기간검색 설정
		if (search.getStartDt() != null)
			map.addAttribute("startDt",
					Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		if (search.getEndDt() != null)
			map.addAttribute("endDt", 
					Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));

		// 키워드 설정
		if (search.getKeyword() != null) 
			map.addAttribute("keyword", search.getKeyword());


		try {
			PaginationSupport<ContentsTbl> contents = contentsManagerService
					.findContentsList(search);
			map.addAttribute("contents", contents);
		} catch (Exception e) {
			logger.error("콘텐츠 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		return map;
	}

	@RequestMapping(value = "/content/StandbyContent.ssc")
	public ModelMap standbycontentSearchList(@ModelAttribute("search") Search search,
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("7");
		}

		logger.debug("####search.getStartDt():" + search.getStartDt());
		logger.debug("####search.getEndDt():" + search.getEndDt());
		logger.debug("####search.getChannelCode():" + search.getChannelCode());
		logger.debug("####search.getKeyword():" + search.getKeyword());
		logger.debug("####search.getPageNo():" + search.getPageNo());

		// 기간검색 설정
		if (search.getStartDt() != null)
			map.addAttribute("startDt",
					Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		if (search.getEndDt() != null)
			map.addAttribute("endDt", 
					Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));

		// 키워드 설정
		if (search.getKeyword() != null) 
			map.addAttribute("keyword", search.getKeyword());


		try {
			PaginationSupport<ContentsTbl> contents = contentsManagerService
					.findStandbycontentsList(search);
			map.addAttribute("contents", contents);
		} catch (Exception e) {
			logger.error("콘텐츠 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		return map;
	}

	@RequestMapping(value = "/content/ajaxStandbyContent.ssc", method = RequestMethod.POST)
	public ModelAndView ajaxStandbycontent(@RequestParam(value = "ctIdPrgrs", required = false) String ctIdPrgrs,
			ModelMap map) {

		ModelAndView view = new ModelAndView();




		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctId2", ctIdPrgrs);

		try {
			List<ContentsTbl> contents = contentsManagerService.getContentsPrgrs(params);
			view.addObject("contents", contents);


			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("콘텐츠 prgrs 목록 조회 에러", e);
		}


		return view;
	}

	@RequestMapping(value = {"/content/RMSContent.ssc","popup/MRmsContent.ssc"})
	public ModelMap rmscontentSearchList(@ModelAttribute("search") Search search,
			@RequestParam(value = "ctId", required = false, defaultValue="0" ) String ctId,
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("7");
		}

		logger.debug("####search.getStartDt():" + search.getStartDt());
		logger.debug("####search.getEndDt():" + search.getEndDt());
		logger.debug("####search.getChannelCode():" + search.getChannelCode());
		logger.debug("####search.getKeyword():" + search.getKeyword());
		logger.debug("####search.getPageNo():" + search.getPageNo());

		// 기간검색 설정
		if (search.getStartDt() != null)
			map.addAttribute("startDt",
					Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		if (search.getEndDt() != null)
			map.addAttribute("endDt", 
					Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		search.setPgmCd("");
		search.setNid("");
		// 키워드 설정
		if(logger.isDebugEnabled())
			logger.debug("keyword: "+search.getKeyword());
		if (search.getKeyword() != null){ 
			map.addAttribute("keyword", search.getKeyword());
		}else if (search.getKeyword() ==""){ 
		}



		try {
			PaginationSupport<ContentsTbl> contents = contentsManagerService
					.findRmscontentsList(search);

			map.addAttribute("contents", contents);

		} catch (Exception e) {
			logger.error("콘텐츠 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		map.addAttribute("ctId", ctId);
		return map;
	}


	@RequestMapping(value = "/popup/getMappingPeople.ssc")
	public ModelAndView getMappingPeople(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "pgmCd", required = true) String pcode,
			ModelMap map) {

		ModelAndView view = new ModelAndView();

		logger.debug("##############################");
		logger.debug("###### pcode: " + pcode);
		logger.debug("##############################");


		//		String id="PS-0001048462-01-000";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("program_code_equals", pcode);          // 프로그램 코드
		params.put("pids","s_seq,name_korea,casting_name,image_url");  // 실제로 받아와야 할 컬럼들.
		params.put("sorting", "name_korea asc");          // 배우명 소팅

		String domain = messageSource.getMessage("meta.system.domain", null,
				Locale.KOREA);
		String programMethod = messageSource.getMessage("meta.system.search.people",
				null, Locale.KOREA);

		/**
		 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
		 * param  pgmId;
		 */
		String programXml = "";

		try {
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params));
			//programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params));
		} catch (Exception e) {
			logger.error("프로그램 회차 조회 오류", e);
		}

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();

		if(StringUtils.isNotBlank(programXml)) {
			try{
				contentsTbl = mediaToolInterfaceService.forwardPeopleInfo(programXml);

				view.addObject("contentsTbl",contentsTbl);
				view.setViewName("jsonView");
			}catch (Exception e) {
				view.addObject("result", "");
				logger.error(" 인물정보 가져오기 오류 : ", e);
			}
		}
		return view;
	}

	@RequestMapping(value = "/popup/getMappingPeople2.ssc")
	public ModelAndView getMappingPeople2(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "pcode", required = true) String pcode,
			ModelMap map) {

		ModelAndView view = new ModelAndView();

		logger.debug("##############################");
		logger.debug("###### pcode: " + pcode);
		logger.debug("##############################");


		//		String id="PS-0001048462-01-000";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("program_code_equals", pcode);          // 프로그램 코드
		params.put("pids","s_seq,name_korea,casting_name,image_url");  // 실제로 받아와야 할 컬럼들.
		params.put("sorting", "name_korea asc");          // 배우명 소팅

		String domain = messageSource.getMessage("meta.system.domain", null,
				Locale.KOREA);
		String programMethod = messageSource.getMessage("meta.system.search.people",
				null, Locale.KOREA);

		/**
		 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
		 * param  pgmId;
		 */
		String programXml = "";

		try {
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params));
			//programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params));
		} catch (Exception e) {
			logger.error("프로그램 회차 조회 오류", e);
		}

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();

		if(StringUtils.isNotBlank(programXml))
			try{
				contentsTbl = mediaToolInterfaceService.forwardPeopleInfo(programXml);

				view.addObject("contentsTbl",contentsTbl);
				view.setViewName("jsonView");
			}catch (Exception e) {
				view.addObject("result", "");
				logger.error(" 인물정보 가져오기 오류 : ", e);
			}
		return view;
	}

	@RequestMapping(value = "/popup/getSearchPeople.ssc")
	public ModelAndView getSearchPeople(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "personInfo2", required = true) String personInfo2,
			ModelMap map) {

		ModelAndView view = new ModelAndView();

		logger.debug("##############################");
		logger.debug("###### personInfo2: " + personInfo2);
		logger.debug("##############################");


		//		String id="PS-0001048462-01-000";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("s_name_equals", personInfo2);          // 프로그램 코드
		params.put("pids","s_seq,s_name,s_sphere,s_upfile");  // 실제로 받아와야 할 컬럼들.
		params.put("sorting", "name_korea asc");          // 배우명 소팅

		String domain = messageSource.getMessage("meta.system.domain", null,
				Locale.KOREA);
		String programMethod = messageSource.getMessage("meta.system.search.person",
				null, Locale.KOREA);

		/**
		 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
		 * param  pgmId;
		 */
		String programXml = "";

		try {
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params));
			//programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params));
		} catch (Exception e) {
			logger.error("프로그램 회차 조회 오류", e);
		}

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();

		if(StringUtils.isNotBlank(programXml))
			try{
				contentsTbl = mediaToolInterfaceService.forwardSearchPeopleInfo(programXml);

				view.addObject("contentsTbl",contentsTbl);
				view.setViewName("jsonView");
			}catch (Exception e) {
				view.addObject("result", "");
				logger.error(" 인물정보 찾기 오류 : ", e);
			}
		return view;
	}

	@RequestMapping(value = "/content/ArchiveSearch.ssc")
	public ModelMap archiveSearch(HttpServletRequest request,
			@ModelAttribute("search") Search search,
			@RequestParam(value = "name", required = false) String name,
			ModelMap map) {
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		Workflow workflow = new Workflow();
		ArchiveReq req = new ArchiveReq();

		String systemId = messageSource.getMessage("archive.system.id", null,
				Locale.KOREA);
		String systemPass = messageSource.getMessage("archive.system.pass",
				null, Locale.KOREA);

		UserTbl user = (UserTbl) WebUtils.getSessionAttribute(request, "user");
		String userId = user.getUserId();
		String userPass = user.getPassword();

		String searchField = messageSource.getMessage("archive.search.field",
				null, Locale.KOREA);
		String itemCount = messageSource.getMessage("archive.item.count", null,
				Locale.KOREA);

		req.setSystemId(systemId);
		req.setSystemPass(systemPass);

		String aseKey = messageSource.getMessage("aes.key", null, Locale.KOREA);
		String aseIv = messageSource.getMessage("aes.iv", null, Locale.KOREA);

		AESUtil aesUtil = new AESUtil();
		req.setUserId(aesUtil.doEncrypt(userId, aseKey, aseIv));
		req.setUserPass(aesUtil.doEncrypt(userPass, aseKey, aseIv));

		req.setSearchField(searchField);
		req.setCurrPage(search.getPageNo());
		req.setItemCount(Integer.valueOf(itemCount));

		boolean archiveSearch = false;

		// 키워드 검색
		if (StringUtils.isNotBlank(search.getKeyword())) {
			req.setKeyword(search.getKeyword());

			archiveSearch = true;
		}

		// 방송일 기간 검색
		if (search.getStartDt() != null && search.getEndDt() != null) {
			req.setStartDate(Utility.getDate(search.getStartDt(), "yyyy/MM/dd"));
			req.setEndDate(Utility.getDate(search.getEndDt(), "yyyy/MM/dd"));

			archiveSearch = true;
		} else {
			req.setStartDate("");
			req.setEndDate("");
		}

		// 프로그램 ID 검색
		if (StringUtils.isNotBlank(search.getPgmId())) {
			req.setPgmId(search.getPgmId());
		}

		workflow.setArchiveReq(req);

		String gate1Ip = messageSource.getMessage("gate1.ip", null,
				Locale.KOREA);
		String gate1Port = messageSource.getMessage("gate1.port", null,
				Locale.KOREA);

		if (archiveSearch) {
			try {
				ServiceNavigatorService navigatorService = new ServiceNavigatorServiceLocator();
				Navigator navigator = navigatorService
						.getServiceNavigatorPort(new URL("http://" + gate1Ip
								+ ":" + gate1Port
								+ "/services/ServiceNavigator"));
				String retXml = navigator.archiveSearch(xmlStream
						.toXML(workflow));
				if (logger.isDebugEnabled()) {
					logger.debug("retXml : " + retXml);
				}
				if (retXml != null && retXml.length() > 0) {
					workflow = (Workflow) xmlStream.fromXML(retXml);

					map.addAttribute("proCount", workflow.getArchiveRes()
							.getProCount());
					PaginationSupport<ArchiveRes> proResult = new PaginationSupport<ArchiveRes>(
							workflow.getArchiveRes().getProResult(), workflow
							.getArchiveRes().getProCount(),
							search.getPageNo(), 10);
					map.addAttribute("proResult", proResult);

					map.addAttribute("newsCount", workflow.getArchiveRes()
							.getNewsCount());
					PaginationSupport<ArchiveRes> newsResult = new PaginationSupport<ArchiveRes>(
							workflow.getArchiveRes().getNewsResult(), workflow
							.getArchiveRes().getNewsCount(),
							search.getPageNo(), 10);
					map.addAttribute("newsResult", newsResult);
					map.addAttribute("name", name);
					// workflow.getArchiveRes().getProResult();
					// map.addAttribute("contents", workflow.getArchiveRes());
				} else {
					List<ArchiveRes> items = new ArrayList<ArchiveRes>();

					PaginationSupport<ArchiveRes> noResult = new PaginationSupport<ArchiveRes>(
							items, 0, search.getPageNo(), 10);
					map.addAttribute("proCount", 0);
					map.addAttribute("newsCount", 0);
					map.addAttribute("proResult", noResult);
					map.addAttribute("newsResult", noResult);
					map.addAttribute("name", 0);
				}
			} catch (Exception e) {
				logger.error("ArchiveSearch Error", e);
			}
		} else {
			List<ArchiveRes> items = new ArrayList<ArchiveRes>();

			PaginationSupport<ArchiveRes> noResult = new PaginationSupport<ArchiveRes>(
					items, 0, search.getPageNo(), 10);
			map.addAttribute("proCount", 0);
			map.addAttribute("newsCount", 0);
			map.addAttribute("proResult", noResult);
			map.addAttribute("newsResult", noResult);
			map.addAttribute("name", 0);
		}

		return map;
	}

	@RequestMapping(value = "/content/WebReg.ssc")
	public ModelMap webRegister(@ModelAttribute("search") Search search,
			@RequestParam(value = "fileNm", required = false) String fileNm,
			@RequestParam(value = "result", required = false) String result,
			ModelMap map) {
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("7");
		}


		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		params.put("clfCd", "CCLA");
		params2.put("clfCd", "CTYP");
		try {
			List<CodeTbl> clas = codeManagerService.findCode(params);
			List<CodeTbl> typs = codeManagerService.findCode(params2);

			map.addAttribute("clas", clas);
			map.addAttribute("typs", typs);
		} catch (Exception e) {
			logger.error("code값 select 오류", e);
		}


		map.addAttribute("contents", new ContentsTbl());
		map.addAttribute("result", result);

		return map;
	}

	@RequestMapping(value = "/content/ManualReg.ssc")
	public ModelMap manualRegister(@ModelAttribute("search") Search search,
			ModelMap map) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		Map<String, Object> params3 = new HashMap<String, Object>();
		params.put("clfCd", "CCLA");
		params2.put("clfCd", "CTYP");
		params3.put("clfCd", "DOAD");
		params3.put("useYn", "Y");
		try {
			List<CodeTbl> clas = codeManagerService.findCode(params);
			List<CodeTbl> typs = codeManagerService.findCode(params2);
			List<CodeTbl> doads = codeManagerService.findCode(params3);

			map.addAttribute("clas", clas);
			map.addAttribute("typs", typs);
			map.addAttribute("doads", doads);
		} catch (Exception e) {
			logger.error("code값 select 오류", e);
		}

		if (StringUtils.isBlank(search.getLocalCode())) 
			search.setLocalCode("00");

		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("7");
		}

		List<File> fileList = new ArrayList<File>();
		String target="";


		logger.debug(">>>>>>>>>>>"+search.getGubun());
		logger.debug(">>>>>>>>>>>"+search.getLocalCode());

		// config.properties 파일에 real path 경로 설정 필요
		if (StringUtils.isBlank(search.getGubun())||search.getGubun().equals("video") ) {
			target = messageSource.getMessage("manual.target.video", null, null);
			search.setGubun("video");
		} else	if (search.getGubun().equals("doad") ) {
			target = messageSource.getMessage("manual.target.doad", null, null);
			search.setGubun("doad");
			if(logger.isDebugEnabled()) {
				logger.debug("msg)target : " + target);
				logger.debug("msg)search.getDirGubun() : " + search.getDirGubun());
				logger.debug("msg)search.getLocalCode() : " + search.getLocalCode());
			}
		} else {
			target = messageSource.getMessage("manual.target.audio", null, null);
			search.setGubun("audio");

		}

		/* 오디오 파일명 검색을 위해 채널을 임시로 입력함 */
		if(StringUtils.isBlank(search.getDirGubun()))
			search.setDirGubun("main");
		if(StringUtils.isBlank(search.getType()))
			search.setType("1fm");

		String savePath = "";
		if (SystemUtils.IS_OS_WINDOWS) {
			savePath = target;
			if (savePath.indexOf("/") > -1)
				savePath = savePath.replaceAll("\\/", "\\\\");
		} else {
			savePath = servletContext.getRealPath("/") + target;
		}

		if (StringUtils.isBlank(search.getGubun()) || search.getGubun().equals("video") || search.getGubun().equals("doad") ) {
			if (SystemUtils.IS_OS_WINDOWS) {
				savePath = "D:\\"+savePath;
			}
		}else{
			if (SystemUtils.IS_OS_WINDOWS) {
				savePath = "D:\\"+savePath;
			}
			savePath= savePath + File.separator  + search.getDirGubun();
		}
		if(logger.isDebugEnabled()) {
			logger.debug("savePath : " + savePath);
		}
		File[] fileLists = null;
		File targetFolder = new File(savePath);

		if (targetFolder.isDirectory()) {
			if(search.getGubun().equals("audio")) {
				fileLists = targetFolder.listFiles(new UserFilenameFilter(search));
			} else if(search.getGubun().equals("doad")){
				fileLists = targetFolder.listFiles(new DoadFilenameFilter(search));
			} else {
				fileLists = targetFolder.listFiles();
			}
			Arrays.sort(fileLists, new ModifiedDate());
		} else {
			fileLists = targetFolder.listFiles(new UserFilenameFilter(search));
			return map.addAttribute("fileList", Collections.EMPTY_LIST);
		}

		for(File f : fileLists) {
			fileList.add(f);
		}

		map.addAttribute("fileList", fileList);
		map.addAttribute("search", search);

		return map;
	}

	private class ModifiedDate implements Comparator<File>{
		public int compare(File f1, File f2) {

			//수정날짜의 값이 더크면 -1리턴, -1을 리턴하면 첫번째것이 앞으로 간다.
			if(f1.lastModified()>f2.lastModified())
				return 1;

			//같으면 0
			if(f1.lastModified()==f2.lastModified())
				return 0;

			//작으면 1
			return -1;
		}
	}

	@RequestMapping(value = "/content/deleteFiles.ssc")
	public String deleteFiles(@ModelAttribute("search") Search search,
			@RequestParam(value = "local", required = false) String local,
			@RequestParam(value = "va", required = false) String vaGubun,
			@RequestParam(value = "type", required = false) String type,
			ModelMap map) {
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("7");
		}

		if(logger.isDebugEnabled()) {
			logger.debug("###############ctIds:" + search.getCtIds().size());
			logger.debug("local: "+local);
			logger.debug("va: "+vaGubun);
			logger.debug("type: "+type);
		}

		// config.properties 파일에 real path 경로 설정 필요
		String target ="";

		if (StringUtils.isBlank(search.getGubun())||search.getGubun().equals("video") ) {
			target = messageSource.getMessage("manual.target.video", null, null);
			map.addAttribute("gubun", "video");

		}else if(search.getGubun().equals("audio")){
			target = messageSource.getMessage("manual.target.audio", null, null)+ File.separator  + search.getDirGubun();
			map.addAttribute("gubun", "audio");
			map.addAttribute("dirGubun", search.getDirGubun());
			map.addAttribute("type", type);
		}else{
			//103에서 test 필요
			target = messageSource.getMessage("manual.target.doad", null, null);
			map.addAttribute("gubun", "doad");
			map.addAttribute("localCode", local);
		}

		String savePath = servletContext.getRealPath("/") + target;
		//String savePath =  target;
		if (SystemUtils.IS_OS_WINDOWS) {
			if (savePath.indexOf("/") > -1)
				savePath = savePath.replaceAll("\\/", "\\\\");
		}


		logger.debug("savePath:" + savePath);

		for (String fileNm : search.getCtNms()) {
			File deleteFile = new File(savePath + File.separator + fileNm);
			//File deleteFile = new File(savePath +"\\\\" + fileNm);

			logger.debug("savePath + File.separator + fileNm:" + savePath
					+ File.separator + fileNm);
			if (deleteFile.exists()) {
				logger.debug("savePath + File.separator + fileNm:" + savePath
						+ File.separator + fileNm);
				try {
					FileUtils.forceDelete(deleteFile);
				} catch (IOException e) {
					logger.error("파일 삭제 오류", e);
				}
			}
		}

		return "redirect:/content/ManualReg.ssc";
	}

	@RequestMapping(value = "/content/manualSave.ssc")
	public ModelAndView manualSave(@ModelAttribute("search") Search search,
			@RequestParam(value = "channelCode", required = false) String channelCode,
			@RequestParam(value = "brdDd", required = false) String brdDd,
			@RequestParam(value = "spGubun", required = false) String spGubun,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "fTyp", required = false) String fTyp,
			@ModelAttribute("contentsTbl") ContentsTbl contentsTbl, ModelMap map) {

		ModelAndView view = new ModelAndView();

		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("7");
		}

		/*if(StringUtils.isNotBlank(contentsTbl.getSegmentNm())) {
			try {
				contentsTbl.setSegmentNm(URLDecoder.decode(contentsTbl.getSegmentNm(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error("segment_nm url decoder error", e.getMessage());
			}
		}*/
		if(logger.isInfoEnabled()) {
			logger.info("pgm_id: "+contentsTbl.getPgmId());
			logger.info("localCode: "+search.getLocalCode());
			logger.info("segmentId: "+contentsTbl.getSegmentId());
			logger.info("segmentNm: "+contentsTbl.getSegmentNm());
		}

		/*
		 * 사용자가 입력한 타임코드값을 초단위로 변환
		 * 00:25:30 -> 00*60*60 + 25*60 + 30
		 */
		int start_t = 0;
		int end_t = 0;
		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			search.setGubun("doad");

			start_t = Utility.timeToSec(startTime);
			end_t = Utility.timeToSec(endTime);

			if(logger.isDebugEnabled()) {
				logger.debug("start_t: "+start_t+", end_t: "+end_t);
			}
		}

		String target="";

		// config.properties 파일에 real path 경로 설정 필요
		if (StringUtils.isBlank(search.getGubun()) || search.getGubun().equals("video") ) {
			target = messageSource.getMessage("manual.target.video", null, null);
			search.setGubun("video");
		} else if (search.getGubun().equals("doad")) {
			target = messageSource.getMessage("manual.target.doad", null, null);
		} else {
			target = messageSource.getMessage("manual.target.audio", null, null);
			search.setGubun("audio");
		}

		String targetDest = messageSource.getMessage("high.resolution.target", null, null);
		String savePath = servletContext.getRealPath("/") + target;
		String destPath = servletContext.getRealPath("/") + targetDest;

		if (SystemUtils.IS_OS_WINDOWS) {
			savePath = target;
			destPath = targetDest;
			if (savePath.indexOf("/") > -1)
				savePath = savePath.replaceAll("\\/", "\\\\");
			if (destPath.indexOf("/") > -1)
				destPath = destPath.replaceAll("\\/", "\\\\");
		} else {
			savePath = servletContext.getRealPath("/") + target;
			destPath = servletContext.getRealPath("/") + targetDest;
		}

		if (StringUtils.isBlank(search.getGubun())||search.getGubun().equals("video")||search.getGubun().equals("doad") ) {
		} else {
			savePath= savePath + File.separator  + search.getDirGubun();
		}

		logger.debug("####################################################################################");
		logger.debug("###### moveFile sourcePath :" + savePath + File.separator + contentsTbl.getCtNm());
		logger.debug("####################################################################################");
		File f = new File(savePath + File.separator + contentsTbl.getCtNm());

		Map<String, Object> params = new HashMap<String, Object>();
		ContentsInstTbl contentsInstTbl = new ContentsInstTbl();
		List<WeekSchTbl> weekSchTbls = new ArrayList<WeekSchTbl>();
		List<ProFlTbl> proFlTbls = new ArrayList<ProFlTbl>();

		logger.debug("contentsTbl.getPgmId():" + contentsTbl.getPgmId());
		try {
			params.put("pgmCd", contentsTbl.getPgmCd());

			if (!f.exists() || StringUtils.isBlank(contentsTbl.getCtNm())) {
				return view;
			}

			if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				contentsInstTbl.setStartTime(String.valueOf(start_t));
				contentsInstTbl.setEndTime(String.valueOf(end_t));
			}

			contentsInstTbl.setFlSz(f.length());

			if (StringUtils.isNotBlank(contentsTbl.getPgmId())) {
				params.put("programId", contentsTbl.getPgmId());

				if (spGubun.equals("s")) {
					weekSchTbls = weekSchManagerService.findWeekSchTbl(params);
					destPath += 
							weekSchTbls.get(0).getProgramCode()
							+ File.separator
							+ weekSchTbls.get(0).getProgramId();

					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					try{
						contentsTbl.setBrdDd(sf.parse(DateUtil.convertFormat(weekSchTbls.get(0).getProgramPlannedDate(), "yyyy-MM-dd")));
					}catch(Exception e){
						logger.error("수동등록 (편성표로 조회) content TBL 방송일 입력 오류", e);

						view.addObject("result", "N");
						view.setViewName("jsonView");
						return view;
					}
				} else {
					if(logger.isDebugEnabled()) {
						logger.debug("input brd_dd: "+brdDd);
					}
					destPath +=  contentsTbl.getPgmCd() + File.separator + contentsTbl.getPgmId();

					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					try{
						contentsTbl.setBrdDd(sf.parse(brdDd.trim().substring(0, 10)));
					}catch(Exception e){
						logger.error("웹등록 (프로그램정보 조회) content TBL 방송일 입력 오류", e);

						view.addObject("result", "N");
						view.setViewName("jsonView");
						return view;
					}
				}

				logger.info("brd_dd: "+Utility.getDate(contentsTbl.getBrdDd(), "yyyy-MM-dd"));

				contentsTbl.setChannelCode(channelCode);	
				contentsTbl.setUseYn("Y");
				contentsTbl.setCont(contentsTbl.getCtNm());
				contentsTbl.setPgmGrpCd(contentsTbl.getPgmGrpCd());
				contentsTbl.setRegrid("NAS");

				/**
				 * 프로그램 코드를 기준으로 메타허브에 프로그램 영문명 조회
				 * 데이타 저장시 공백, 여백 처리
				 */
				if(logger.isDebugEnabled()) {
					logger.debug("Program Code ===== "+contentsTbl.getPgmCd());
				}
/* 본사 테스트용 운영에는 제거해야 함
				if(StringUtils.isNotBlank(contentsTbl.getPgmCd())){
					String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
					String programGroupMethod = messageSource.getMessage("meta.system.search.group",null, Locale.KOREA);


					Map<String,Object> element = new HashMap<String, Object>();
					element.put("group_code_equals", contentsTbl.getPgmCd());          // 프로그램 코드
					element.put("pids","alias_eng");  // 실제로 받아와야 할 컬럼들.
					String proGroupXml ="";
					//본사 테스트 임시용
					//추후에 주석 풀어야함.
//					try {
//						HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
//						proGroupXml = httpRequestService.findData(domain+programGroupMethod, Utility.convertNameValue(element));
//						//proGroupXml = Utility.connectHttpPostMethod(domain+programGroupMethod, Utility.convertNameValue(element));
//					}catch (Exception e) {
//						logger.error("Get programGroupXml by MetaHub System createContentML Error", e);
//					}

					Map<String,Object> setEle = new HashMap<String, Object>();
					if(StringUtils.isNotBlank(proGroupXml)&&StringUtils.startsWith(proGroupXml, "<?xml")){
						setEle = mediaToolInterfaceService.forwardMetaInfo(proGroupXml);
					}
					contentsTbl.setGrpAliasEng(setEle.get("alias_eng") != null ? StringUtil.removeFormat(String.valueOf(setEle.get("alias_eng"))) : null);
					if(logger.isInfoEnabled())
						logger.info("alias_eng: "+contentsTbl.getGrpAliasEng());
				}
*/
				if(search.getGubun().equals("doad")) {
					contentsTbl.setLocalCode(StringUtils.defaultIfEmpty(search.getLocalCode(), "00"));
					contentsInstTbl.setAvGubun(fTyp);
				} else {
					contentsTbl.setLocalCode("00");
					if(search.getGubun().equals("video")){
						contentsInstTbl.setAvGubun("V");
					}else{
						contentsInstTbl.setAvGubun("A");
					}
				}

				// 저장되는 파일경로는 /mp2 이전 정보는 저장하지 않아야 함. 이전 정보는 얼마든지 바뀔 수가 있고 파일경로는
				// 트리밍, 트랜스코딩, 트랜스퍼에 전달해야하는데 /mp2 이전 정보는 의미가 없으므로 빼고 전달해야 함

				contentsInstTbl.setFlPath(destPath.substring(destPath.indexOf(targetDest) - 1) + File.separator);
				contentsInstTbl.setUsrFileNm(contentsTbl.getCtNm().substring(0, contentsTbl.getCtNm().lastIndexOf(".")));
				contentsInstTbl.setFlExt(contentsTbl.getCtNm().substring(contentsTbl.getCtNm().lastIndexOf(".")+1));
				contentsInstTbl.setWrkFileNm("");

				Map<String, Object> codeParam = new HashMap<String, Object>();
				codeParam.put("clfCd", "FMAT");

				if (contentsInstTbl.getAvGubun().equals("V") ){
					codeParam.put("sclCd", contentsInstTbl.getFlExt().toLowerCase()+"1");
				} else {
					codeParam.put("sclCd", contentsInstTbl.getFlExt().toLowerCase()+"3");
				}

				CodeTbl codeTbl = codeDao.getCode(codeParam);
				if(logger.isDebugEnabled()) {
					logger.debug("getClfNm: "+codeTbl.getClfNm());
				}

				contentsInstTbl.setCtiFmt(codeTbl.getClfNm());
				contentsInstTbl.setRegDt(Utility.getTimestamp());

				if(contentsTbl.getCtTyp().equals("11")){
					contentsTbl.setPgmNum(02);
				}else{
					contentsTbl.setPgmNum(codeManagerService.getContentsPgmNum(contentsTbl));
				}

				if(logger.isDebugEnabled()) {
					logger.debug("getPgmNum: "+contentsTbl.getPgmNum());
				}

				// Random ID 발급
				String uuid = UUID.randomUUID().toString();
				String restNm = destPath
						+ File.separator
						+ uuid
						+ "."
						+ contentsTbl.getCtNm().substring(contentsTbl.getCtNm().lastIndexOf(".")+1);

				contentsInstTbl.setOrgFileNm(uuid);

				File dest = new File(restNm);

				logger.debug("##############################################################");
				logger.debug("###### moveFile Destination: "+restNm);
				logger.debug("##############################################################");

				if(!dest.getParentFile().exists()) dest.getParentFile().mkdirs();

				int m = 0;
				String curPath = "";
				String[] paths = dest.getParentFile().getAbsolutePath().split("\\/");

				File dirF = null;
				for(int i=0; i<paths.length; i++) {
					curPath += paths[i]+"/";
					if(paths[i].equals("mp2")) m = i;
					if(m != i && m > 0) {
						try {
							dirF = new File(curPath);
							if(!dirF.exists()) {
								dirF.mkdirs();

								Runtime r = Runtime.getRuntime();
								Process p = r.exec("chmod -R 777 "+curPath);
								p.waitFor();

								logger.debug("folder created and chmod 777 apply =======>"+curPath);
							}
						} catch (InterruptedException e) {
							logger.error("chmod Error", e);
						} catch (IOException e) {
							logger.error("chmod Error", e);
						} catch (Exception e){
							logger.error("chmod Error",e);
						}
					}
				}

				File orgFile = new File(savePath + File.separator + contentsTbl.getCtNm());
				if(orgFile.exists()) {
					synchronized (o) {
						if(SystemUtils.IS_OS_WINDOWS) {
							FileUtils.moveFile(f, dest);
						} else {
							logger.debug("File MOVE ================= mv "+savePath + File.separator + contentsTbl.getCtNm()+" "+restNm);
							Runtime r = Runtime.getRuntime();

							Process p = r.exec("mv "+savePath + File.separator + contentsTbl.getCtNm()+" "+restNm);
							try {
								p.waitFor();

								p = r.exec("chmod -R 777 "+restNm);
								p.waitFor();
								logger.info("File MOVE Completed ================= mv "+savePath + File.separator + contentsTbl.getCtNm()+" "+restNm);
								Thread.sleep(1000L);
							} catch (InterruptedException e) {
								logger.error("File Move Error", e);
								view.addObject("result", "N");
								view.setViewName("jsonView");
							}
						}

						if(!dest.exists()) {
							view.addObject("result", "N");
							view.setViewName("jsonView");
							throw new Exception("Move File not exists "+dest.getAbsolutePath());
						}
					}

					contentsManagerService.insertContents(contentsTbl, contentsInstTbl);

					if(contentsTbl.getTrimmSte().equals("P")){
						List<TranscorderHisTbl> traHisTbls = new ArrayList<TranscorderHisTbl>();

						// 컨텐츠 유형별 프로파일 설정 조회
						// 2012-04-23
						params.put("ctTyp", contentsTbl.getCtTyp());
						proFlTbls = proBusiManagerService.findPgmProBusi(params);

						for (ProFlTbl proFlTbl : proFlTbls) {

							TranscorderHisTbl traHisTbl = new TranscorderHisTbl();

							traHisTbl.setCtiId(contentsInstTbl.getCtiId());
							traHisTbl.setProFlid(Integer.parseInt(proFlTbl.getProFlid()));
							traHisTbl.setFlExt(proFlTbl.getExt());
							traHisTbl.setUseYn("Y");
							traHisTbl.setWorkStatcd("000");

							if(search.getGubun().equals("doad")) {

								traHisTbl.setJobGubun(fTyp);
							}else{
								if(search.getGubun().equals("video")){
									traHisTbl.setJobGubun("V"); // Video : V , Audio : A
								}else{
									traHisTbl.setJobGubun("A"); // Video : V , Audio : A
								}
							}

							traHisTbl.setRegDt(Utility.getTimestamp());
							traHisTbls.add(traHisTbl);

							if(logger.isDebugEnabled()) {
								logger.debug("pro_flid: "+proFlTbl.getProFlid());
								logger.debug("cti_id: "+contentsInstTbl.getCtiId());
							}
						}
						workflowManagerService.insertTranscorderHis(traHisTbls);
					}
				} else {
					if(logger.isInfoEnabled()) {
						logger.info("File Not Exists - "+savePath + File.separator + contentsTbl.getCtNm());
					}
				}

			}

			view.addObject("result", "Y");
			view.setViewName("jsonView");

		} catch (Exception e) {
			logger.error("수동 등록 입력 실패",  e);

			view.addObject("result", "N");
			view.setViewName("jsonView");
		}

		return view;
	}


	@RequestMapping(value = "/popup/Schedule.ssc")
	public ModelMap getScheduleInfo(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "channelCode", required = false) String channelCode,
			@RequestParam(value = "menuId", required = false) String menuId,
			@RequestParam(value = "tabGubun", required = false) String tabGubun,
			@RequestParam(value = "channel", required = false) String channel,
			@RequestParam(value = "gubun", required = true) String gubun,
			@RequestParam(value = "ctTyp", required = true) String ctTyp,
			@RequestParam(value = "sGubun", required = false) String sGubun,
			@RequestParam(value = "localCode", required = false) String localCode,
			@RequestParam(value = "fTyp", required = false) String fTyp,
			ModelMap map) {
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		logger.debug("########## channelCode:" + channelCode);
		Map<String, Object> params = new HashMap<String, Object>();

		if (StringUtils.isBlank(channelCode)) {
			if(gubun.equals("video") || gubun.equals("doad") ){
				if(fTyp.equals("A") || fTyp == "A"){
					params.put("channelCode", "21");
				}else{
					params.put("channelCode", "11"); // 1TV:11 , 2TV:12
				}
			}else{
				params.put("channelCode", "21");
			}
		} else {
			params.put("channelCode", channelCode);
		}

		if (StringUtils.isBlank(tabGubun)) {
			params.put("tabGubun", "1"); // 1: 이번주 , 2: 다음주, 3:오늘, 4:내일
		} else {
			params.put("tabGubun", tabGubun);
		}

		if (StringUtils.isBlank(localCode)) {
			params.put("localCode", "00");
			search.setLocalCode("00");
		} else {

			params.put("localCode", localCode); 
			search.setLocalCode(localCode);
		}

		sGubun = StringUtils.defaultIfEmpty(sGubun, "-1");
		String dayBetween = Utility.getDayBetween((Integer.valueOf(sGubun)+1) * 7, "yyyyMMdd");
		if(StringUtils.isNotBlank(dayBetween) && dayBetween.indexOf(",") > -1) {
			String[] days = dayBetween.split("\\,");

			logger.debug("startDate: " +days[0]+", endDate: "+days[1]);

			params.put("sdate", days[0]);
			params.put("edate", days[1]);
		} else {
			return map;
		}

		map.addAttribute("channelCode", params.get("channelCode"));
		map.addAttribute("tabGubun", params.get("tabGubun"));
		map.addAttribute("search", search);
		map.addAttribute("fTyp", fTyp);
		try {

			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<WeekSchTbl> contents = weekSchManagerService.findWeekSchTbl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			logger.debug("sGubun :" + sGubun);
			logger.debug("proBusiTbls size :" + proBusiTbls.size());
			logger.debug("proFlTbls size :" + proFlTbls.size());
			logger.debug("busiPartnerTbls size :" + busiPartnerTbls.size());

			map.addAttribute("proBusiTbls", proBusiTbls);
			map.addAttribute("proFlTbls", proFlTbls);
			map.addAttribute("contents", contents);
			map.addAttribute("busiPartnerTbls", busiPartnerTbls);
			map.addAttribute("gubun", gubun);
			map.addAttribute("ctTyp", ctTyp);
			map.addAttribute("sGubun", sGubun);
		} catch (ServiceException e) {
			logger.error(" -  팝업 편성표를 조회할 수 없음.", e);
		}
		return map;
	}

	@RequestMapping(value = "/popup/Schedule.ssc", method = RequestMethod.POST)
	public ModelMap getSchedule(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "channelCode", required = false) String channelCode,
			@RequestParam(value = "menuId", required = false) String menuId,
			@RequestParam(value = "tabGubun", required = false) String tabGubun,
			@RequestParam(value = "channel", required = false) String channel,
			@RequestParam(value = "gubun", required = true) String gubun,
			@RequestParam(value = "ctTyp", required = true) String ctTyp,
			@RequestParam(value = "sGubun", required = false) String sGubun,
			@RequestParam(value = "localCode", required = false) String localCode,
			@RequestParam(value = "fTyp", required = false) String fTyp,
			ModelMap map) {
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		logger.debug("########## channelCode:" + channelCode);
		Map<String, Object> params = new HashMap<String, Object>();

		if (StringUtils.isBlank(channelCode)) {
			if(gubun.equals("video") || gubun.equals("doad") ){

				if(fTyp.equals("A") || fTyp=="A"){
					params.put("channelCode", "21");
				}else{
					params.put("channelCode", "11"); // 1TV:11 , 2TV:12
				}
			}else{
				params.put("channelCode", "21");
			}
		} else {
			params.put("channelCode", channelCode);
		}

		if (StringUtils.isBlank(tabGubun)) {
			params.put("tabGubun", "1"); // 1: 이번주 , 2: 다음주, 3:오늘, 4:내일
		} else {
			params.put("tabGubun", tabGubun);
		}

		if (StringUtils.isBlank(localCode)) {
			params.put("localCode", "00");
			search.setLocalCode("00");
		} else {

			params.put("localCode", localCode); 
			search.setLocalCode(localCode);
		}

		logger.debug("============ sGubun = " +sGubun);

		String dayBetween = Utility.getDayBetween((Integer.valueOf(sGubun)+1) * 7, "yyyyMMdd");
		if(StringUtils.isNotBlank(dayBetween) && dayBetween.indexOf(",") > -1) {
			String[] days = dayBetween.split("\\,");

			logger.debug("startDate: " +days[0]+", endDate: "+days[1]);

			params.put("sdate", days[0]);
			params.put("edate", days[1]);
		} else {
			return map;
		}

		/*if (sGubun.equals("-1")) {
			logger.debug("########## sGubun = " +sGubun);
			logger.debug("############################");
			logger.debug(Utility.getDateOfWeek(2, -1, "yyyyMMdd"));
			logger.debug(Utility.getDateOfWeek(8, -1, "yyyyMMdd"));
			logger.debug("############################");

			params.put("sdate", Utility.getDateOfWeek(2, -1, "yyyyMMdd"));
			params.put("edate", Utility.getDateOfWeek(8, -1, "yyyyMMdd"));
		}else if(sGubun.equals("0")){
			logger.debug("########## sGubun = " +sGubun);
			logger.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			logger.debug(Utility.getDateOfWeek(2, 0, "yyyyMMdd"));
			logger.debug(Utility.getDateOfWeek(8, 0, "yyyyMMdd"));
			logger.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

			params.put("sdate", Utility.getDateOfWeek(2, 0, "yyyyMMdd"));
			params.put("edate", Utility.getDateOfWeek(8, 0, "yyyyMMdd"));
		}else {
			logger.debug("########## sGubun = " +sGubun);
			logger.debug("***********************");
			logger.debug(Utility.getDateOfWeek(2, -2, "yyyyMMdd"));
			logger.debug(Utility.getDateOfWeek(8, -2, "yyyyMMdd"));
			logger.debug("***********************");

			params.put("sdate", Utility.getDateOfWeek(2, -2, "yyyyMMdd"));
			params.put("edate", Utility.getDateOfWeek(8, -2, "yyyyMMdd"));
		}*/

		map.addAttribute("channelCode", params.get("channelCode"));
		map.addAttribute("tabGubun", params.get("tabGubun"));
		map.addAttribute("search", search);
		map.addAttribute("fTyp", fTyp);

		try {

			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<WeekSchTbl> contents = weekSchManagerService.findWeekSchTbl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			logger.debug("proBusiTbls size :" + proBusiTbls.size());
			logger.debug("proFlTbls size :" + proFlTbls.size());
			logger.debug("busiPartnerTbls size :" + busiPartnerTbls.size());

			map.addAttribute("proBusiTbls", proBusiTbls);
			map.addAttribute("proFlTbls", proFlTbls);
			map.addAttribute("contents", contents);
			map.addAttribute("busiPartnerTbls", busiPartnerTbls);
			map.addAttribute("gubun", gubun);
			map.addAttribute("ctTyp", ctTyp);
			map.addAttribute("sGubun", sGubun);

		} catch (ServiceException e) {
			logger.error(" -  팝업 편성표를 조회할 수 없음.", e);
		}
		return map;
	}



	/**
	 * 삭제 컨텐츠
	 * 
	 * @param search
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/content/ajaxDeleteContent.ssc", method = RequestMethod.POST)
	public ModelAndView deleteContentInfo(
			@ModelAttribute("search") Search search, ModelMap map) {

		logger.debug("###############ctIds:" + search.getCtIds().size());
		logger.debug("###############ctiId:" + search.getCtiId());
		logger.debug("###############ctId:" + search.getCtId());
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();

		if(search.getCtiId() == 0){


			for (Long ctId : search.getCtIds()) {

				logger.debug("###############ctId:" + ctId);

				ContentsTbl contentsTbl = new ContentsTbl();

				try {
					if (ctId > 0) {
						contentsTbl.setCtId(ctId);
						contentsTbl.setUseYn("N");

						contentsManagerService.deleteContents(contentsTbl);

						DisuseInfoTbl disuseinfoTbl = new DisuseInfoTbl();
						disuseinfoTbl.setDisuseClf("U");
						disuseinfoTbl.setDataId(ctId);
						disuseinfoManagerService.insertDisuseInfo(disuseinfoTbl);


					}

					view.addObject("result", "Y");
					view.setViewName("jsonView");
				} catch (Exception e) {
					logger.error("delete error", e);
					view.addObject("result", "N");
				}
			}

			try{
				Long ctId = search.getCtId();	
				ContentsTbl contentsTbl = new ContentsTbl();
				if (search.getCtId() > 0) {
					contentsTbl.setCtId(ctId);
					contentsTbl.setUseYn("N");

					contentsManagerService.deleteContents(contentsTbl);

					DisuseInfoTbl disuseinfoTbl = new DisuseInfoTbl();
					disuseinfoTbl.setDisuseClf("U");
					disuseinfoTbl.setDataId(ctId);
					disuseinfoManagerService.insertDisuseInfo(disuseinfoTbl);
				}
			}catch (Exception e) {
				logger.error("delete error", e);
				view.addObject("result", "N");
			}
		}
		try {
			ContentsInstTbl contentsinstTbl = new ContentsInstTbl();
			if (search.getCtiId() > 0) {
				contentsinstTbl.setCtiId(search.getCtiId());
				contentsinstTbl.setUseYn("N");
				params.put("ctiId", search.getCtiId());
				contentsInstManagerService.getContentsInst(params);


				contentsInstManagerService.deleteContentsInst(contentsinstTbl);

				//파일 삭제 요청
				//				DeleteJobControl.putJob(contentsInstManagerService.getContentsInst(params));
				Map<String, Object> params2 = new HashMap<String, Object>();
				params2.put("ctId", search.getCtId());
				params2.put("notCtiFmt", "1");
				List<ContentsInstTbl> contentsInstTbls = contentsInstManagerService.findContentsInstSummary(params2);
				ContentsTbl contentsTbl = contentsManagerService.getContents(params2);


				view.addObject("contentsTbl", contentsTbl);
				view.addObject("contentsInstTbls", contentsInstTbls);

			}


			view.addObject("result", "Y");
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("delete error", e);
			view.addObject("result", "N");
		}

		return view;
	}

	/**
	 * 삭제 컨텐츠
	 * 
	 * @param search
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/popup/ajaxRmsDeleteContent.ssc")
	public ModelAndView rmsDeleteContentInfo(
			@ModelAttribute("search") Search search, 
			@RequestParam(value = "nid", required = true) String nid,
			@RequestParam(value = "avGubun", required = true) String avGubun,
			ModelMap map) {

		logger.debug("###############ctIds:" + search.getCtIds().size());
		logger.debug("###############ctiId:" + search.getCtiId());
		logger.debug("###############ctId:" + search.getCtId());
		logger.debug("###############nid:" + nid);
		logger.debug("###############avGubun:" + avGubun);
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();

		for (Long ctId : search.getCtIds()) {

			logger.debug("###############ctId:" + ctId);

			ContentsTbl contentsTbl = new ContentsTbl();

			try {
				if (ctId > 0) {
					contentsTbl.setCtId(ctId);
					contentsTbl.setUseYn("N");

					contentsManagerService.deleteContents(contentsTbl);

					DisuseInfoTbl disuseinfoTbl = new DisuseInfoTbl();
					disuseinfoTbl.setDisuseClf("U");
					disuseinfoTbl.setDataId(ctId);
					disuseinfoManagerService.insertDisuseInfo(disuseinfoTbl);
				}

				/**
				 * rms contents count 
				 */
				Map<String, Object> params2 = new HashMap<String, Object>();
				String contentCount = "";
				try{
					params2.put("nid", nid);	

					ContentsTbl contents  = contentsManagerService.getRmsContentsCount(params2);
					contentCount=contents.getCount();
				}catch(ServiceException e) {
					logger.error("rms content count 에러" + e);
					e.printStackTrace();
				}


				Map<String, Object> params3 = new HashMap<String, Object>();

				params3.put("nid",nid);
				params3.put("contents_no",contentCount);

				String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
				String programMethod = "";
				//properties 추가 하기   ->2013.2.18 -> 완료

				if(avGubun.equals("V") || avGubun == "V"){
					programMethod = messageSource.getMessage("meta.system.rmscontents.movie.count", null, Locale.KOREA);

				}else{
					programMethod = messageSource.getMessage("meta.system.rmscontents.audio.count", null, Locale.KOREA);
				}

				String programXml = "";
				try {
					HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
					programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params3));
					//programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params3));
				}catch (Exception e) {
					logger.error("MetaHub에 RMSContents Count 전송 오류", e);
				}

				view.addObject("result", "Y");
				view.setViewName("jsonView");
			} catch (Exception e) {
				logger.error("delete error", e);
				view.addObject("result", "N");
			}

		}

		return view;
	}

	@RequestMapping(value = "/content/ajaxProflRequest.ssc", method = RequestMethod.POST)
	public ModelAndView proflRequest(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "ctiId", required = false) String ctiId,
			@ModelAttribute("contentsTbl") ContentsTbl contentsTbl,
			ModelMap map) {


		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}


		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();

		try {

			params2.put("ctId",search.getCtId());
			params2.put("ctiFmt2","Y");
			ContentsInstTbl contentsin = contentsInstManagerService.getContentsInst(params2);

			if(contentsin.getUseYn().equals("N")){
				view.addObject("result", "N");
			}else{

				ContentsInstTbl contentsinst = new ContentsInstTbl();

				contentsinst.setCtiId(Long.valueOf(ctiId));
				contentsinst.setUseYn("N");
				contentsInstManagerService.deleteContentsInst(contentsinst);

				TranscorderHisTbl traHis = new TranscorderHisTbl();

				traHis.setWorkStatcd("000");
				traHis.setPrgrs("0");
				traHis.setWrkCtiId(Long.valueOf(ctiId));

				workflowManagerService.updateProflRequest(traHis);
				params.put("ctId", search.getCtId());
				params.put("notCtiFmt", "1");// 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
				// ,오디오 원본:'3XX')
				List<ContentsInstTbl> contentsInstTbls = contentsInstManagerService
						.findContentsInstSummary(params);

				Map<String, Object> params3 = new HashMap<String, Object>();
				List<ProFlTbl> proFlTbls = new ArrayList<ProFlTbl>();
				// 컨텐츠 유형별 프로파일 설정 조회
				// 2012-04-23
				params3.put("ctTyp", contentsTbl.getCtTyp());
				params3.put("pgmCd", contentsTbl.getPgmCd());
				params3.put("ctiId", ctiId);
				proFlTbls = proBusiManagerService.findNewPgmProBusi(params3);//새로추가된 프로파일정보

				view.addObject("proFlTbls", proFlTbls);		
				view.addObject("contentsInstTbls", contentsInstTbls);
				view.addObject("result", "Y");
				view.setViewName("jsonView");
			}
		} catch (Exception e) {
			logger.error("delete error", e);
			view.addObject("result", "N");
		}

		return view;
	}

	@RequestMapping(value = "/content/ajaxNewProflRequest.ssc", method = RequestMethod.POST)
	public ModelAndView newProflRequest(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "proFlid", required = false) String proFlid,
			@RequestParam(value = "gubun", required = false) String gubun,
			@RequestParam(value = "ctId", required = false) String ctId,
			@RequestParam(value = "nctiId", required = false) String nctiId,
			@RequestParam(value = "nctTyp", required = false) String nctTyp,
			@RequestParam(value = "npgmCd", required = false) String npgmCd,
			ModelMap map) {


		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}


		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();

		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		logger.debug("ctId>>>>>>"+ctId);
		logger.debug("gubun>>>>>>"+gubun);
		logger.debug("proFlid>>>>>>"+proFlid);
		logger.debug("nctiId>>>>>>"+nctiId);
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try {

			params2.put("ctId",ctId);
			params2.put("ctiFmt2","Y");
			ContentsInstTbl contentsin = contentsInstManagerService.getContentsInst(params2);

			if(contentsin.getUseYn().equals("N")){
				view.addObject("result", "N");
			}else{


				params.put("proFlid", proFlid);

				ProFlTbl proflTbl = proFlManagerService.getProFl(params);



				TranscorderHisTbl traHisTbl = new TranscorderHisTbl();

				traHisTbl.setCtiId(Long.parseLong(nctiId));
				traHisTbl.setProFlid(Integer.parseInt(proflTbl.getProFlid()));
				traHisTbl.setFlExt(proflTbl.getExt());
				traHisTbl.setUseYn("Y");
				traHisTbl.setWorkStatcd("000");


				traHisTbl.setJobGubun(gubun);
				traHisTbl.setRegDt(Utility.getTimestamp());




				workflowManagerService.insertTranscorderHis(traHisTbl);




				params.put("ctId", search.getCtId());
				params.put("notCtiFmt", "1");// 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
				// ,오디오 원본:'3XX')
				List<ContentsInstTbl> contentsInstTbls = contentsInstManagerService
						.findContentsInstSummary(params);

				Map<String, Object> params3 = new HashMap<String, Object>();
				List<ProFlTbl> proFlTbls = new ArrayList<ProFlTbl>();
				// 컨텐츠 유형별 프로파일 설정 조회
				// 2012-04-23
				params3.put("ctTyp", nctTyp);
				params3.put("pgmCd", npgmCd);
				params3.put("ctiId", nctiId);
				proFlTbls = proBusiManagerService.findNewPgmProBusi(params3);//새로추가된 프로파일정보

				view.addObject("proFlTbls", proFlTbls);

				view.addObject("contentsInstTbls", contentsInstTbls);
				view.addObject("result", "Y");
				view.setViewName("jsonView");
			}
		} catch (Exception e) {
			logger.error("newProFl 트랜스코더 재요청 실패", e);
			view.addObject("result", "N");
		}

		return view;
	}

	@RequestMapping(value = "/content/ajaxAllProflRequest.ssc", method = RequestMethod.POST)
	public ModelAndView allProflRequest(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "a_ctiId", required = false) Long a_ctiId,
			@RequestParam(value = "ctId", required = false) String ctId,
			@RequestParam(value = "pgmCd", required = false) String pgmCd,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			@RequestParam(value = "avGubun", required = false) String avGubun,
			ModelMap map) {


		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();

		try {

			//ContentsInstTbl contentsinst = new ContentsInstTbl();

			//contentsinst.setCtId(Long.valueOf(ctId));
			//contentsinst.setUseYn("N");
			//contentsinst.setCtiFmt("Y");
			//contentsInstManagerService.deleteContentsInst(contentsinst);

			TranscorderHisTbl traHis = new TranscorderHisTbl();

			traHis.setWorkStatcd("000");
			traHis.setPrgrs("0");
			traHis.setCtId(Long.valueOf(ctId));

			workflowManagerService.updateAllProflRequest(traHis);

			List<TranscorderHisTbl> traHisTbls = new ArrayList<TranscorderHisTbl>();
			List<ProFlTbl> proFlTbls = new ArrayList<ProFlTbl>();
			// 컨텐츠 유형별 프로파일 설정 조회
			// 2012-04-23
			params.put("ctTyp", ctTyp);
			params.put("pgmCd", pgmCd);
			params.put("ctiId", a_ctiId);
			proFlTbls = proBusiManagerService.findNewPgmProBusi(params);//새로추가된 프로파일정보

			for (ProFlTbl proFlTbl : proFlTbls) {

				TranscorderHisTbl traHisTbl = new TranscorderHisTbl();

				traHisTbl.setCtiId(a_ctiId);
				traHisTbl.setProFlid(Integer.parseInt(proFlTbl.getProFlid()));
				traHisTbl.setFlExt(proFlTbl.getExt());
				traHisTbl.setUseYn("Y");
				traHisTbl.setWorkStatcd("000");


				traHisTbl.setJobGubun(avGubun);
				traHisTbl.setRegDt(Utility.getTimestamp());
				traHisTbls.add(traHisTbl);


			}
			workflowManagerService.insertTranscorderHis(traHisTbls);




			params.put("ctId", search.getCtId());
			params.put("notCtiFmt", "1");// 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
			// ,오디오 원본:'3XX')
			List<ContentsInstTbl> contentsInstTbls = contentsInstManagerService
					.findContentsInstSummary(params);

			Map<String, Object> params3 = new HashMap<String, Object>();
			List<ProFlTbl> proFlTbls2 = new ArrayList<ProFlTbl>();
			// 컨텐츠 유형별 프로파일 설정 조회
			// 2012-04-23
			params3.put("ctTyp", ctTyp);
			params3.put("pgmCd", pgmCd);
			params3.put("ctiId", a_ctiId);
			proFlTbls2 = proBusiManagerService.findNewPgmProBusi(params3);//새로추가된 프로파일정보

			view.addObject("proFlTbls", proFlTbls2);

			view.addObject("contentsInstTbls", contentsInstTbls);
			view.addObject("result", "Y");
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("delete error", e);
			view.addObject("result", "N");
		}

		return view;
	}

	@RequestMapping(value = "/content/ajaxTransRequest.ssc", method = RequestMethod.POST)
	public ModelAndView transRequest(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "ctiId", required = false) String ctiId,
			ModelMap map) {


		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		ModelAndView view = new ModelAndView();
		try {
			TransferHisTbl trsHis = new TransferHisTbl();

			trsHis.setWorkStatcd("000");
			trsHis.setPrgrs("0");
			trsHis.setCtiId(Long.valueOf(ctiId));
			//trsHis.setTrsStrDt(date);
			//trsHis.setTrsEndDt(date);
			//setModDt(date);

			workflowManagerService.updateTransRequest(trsHis);
			//workflowManagerService.updateTransferHisState(trsHis);
			//params.put("ctId", search.getCtId());
			//params.put("notCtiFmt", "1");// 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
			// ,오디오 원본:'3XX')
			//List<ContentsInstTbl> contentsInstTbls = contentsInstManagerService
			//.findContentsInstSummary(params);

			//view.addObject("contentsInstTbls", contentsInstTbls);
			view.addObject("result", "Y");
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("delete error", e);
			view.addObject("result", "N");
		}

		return view;
	}

	@RequestMapping(value = "/content/ajaxRetryProfile.ssc", method = RequestMethod.POST)
	public ModelAndView retryProfileJob(
			@ModelAttribute("search") Search search, ModelMap map) {

		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		ModelAndView view = new ModelAndView();

		return view;
	}

	@RequestMapping(value = "/content/ajaxRetryTransfer.ssc", method = RequestMethod.POST)
	public ModelAndView retryTransferJob(
			@ModelAttribute("search") Search search, ModelMap map) {

		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		ModelAndView view = new ModelAndView();

		return view;
	}

	@RequestMapping(value = "/content/ajaxFindContentInfo.ssc", method = RequestMethod.POST)
	public ModelAndView findContentInfo(
			@ModelAttribute("search") Search search, ModelMap map) {

		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		Map<String, Object> params3 = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();

		params.put("ctId", search.getCtId());
		// params.put("ctiFmt", "1"); // 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
		// ,오디오 원본:'3XX')
		params.put("notCtiFmt", "1");// 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
		// ,오디오 원본:'3XX')

		params2.put("ctId", search.getCtId());
		params2.put("ctiFmt2", "1"); // 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
		// ,오디오 원본:'3XX')
		try {
			ContentsTbl contentsTbl = contentsManagerService.getContents(params);
			List<ContentsInstTbl> contentsInstTbls = contentsInstManagerService.findContentsInstSummary(params);
			ContentsInstTbl contentsInst = contentsInstManagerService.getContentsInst(params2);

			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			logger.debug(">>>>>"+contentsTbl.getCtTyp());
			logger.debug(">>>>>"+contentsTbl.getPgmCd());
			logger.debug(">>>>>"+contentsInst.getCtiId());
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

			List<ProFlTbl> proFlTbls = new ArrayList<ProFlTbl>();
			// 컨텐츠 유형별 프로파일 설정 조회
			// 2012-04-23
			params3.put("ctTyp", contentsTbl.getCtTyp());
			params3.put("pgmCd", contentsTbl.getPgmCd());
			params3.put("ctiId", contentsInst.getCtiId());
			proFlTbls = proBusiManagerService.findNewPgmProBusi(params3);//새로추가된 프로파일정보

			view.addObject("proFlTbls", proFlTbls);
			view.addObject("contentsTbl", contentsTbl);
			view.addObject("contentsInst", contentsInst);
			view.addObject("contentsInstTbls", contentsInstTbls);
			view.addObject("search", search);

			view.setViewName("jsonView");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("- 콘텐츠 상세정보를 조회할 수 없음.", e);
		}

		return view;
	}

	@RequestMapping(value = "/content/ajaxFindContentInfoSB.ssc", method = RequestMethod.POST)
	public ModelAndView findContentInfoSB(
			@ModelAttribute("search") Search search, ModelMap map) {

		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();

		params.put("ctId", search.getCtId());

		Map<String, Object> params2 = new HashMap<String, Object>();
		Map<String, Object> params3 = new HashMap<String, Object>();
		params2.put("clfCd", "CCLA");
		params3.put("clfCd", "CTYP");

		Map<String, Object> params4 = new HashMap<String, Object>();
		try {
			ContentsTbl contentsTbl = contentsManagerService.getContents(params);

			params.put("ctiFmt2", "Y");

			ContentsInstTbl contentsInstTbls = contentsInstManagerService.getContentsInst(params);

			if(logger.isDebugEnabled()) {
				logger.debug("program_id: "+contentsTbl.getPgmId());
			}
			params4.put("program_id_equals", contentsTbl.getPgmId());
			params4.put("pids","segment_title,segment_id");  // 실제로 받아와야 할 컬럼들.

			String domain = messageSource.getMessage("meta.system.domain", null,Locale.KOREA);

			String programMethod ="";

			if(contentsInstTbls.getAvGubun().equals("V") || contentsInstTbls.getAvGubun() == "V"){
				programMethod = messageSource.getMessage("meta.system.search.segment",null, Locale.KOREA);
			}else{
				programMethod = messageSource.getMessage("meta.system.search.segment.radio",null, Locale.KOREA);
			}
			/**
			 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
			 * param4  pgmId;
			 */
			String programXml = "";

			try {
				HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
				programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params4));
				//programXml = Utility.connectHttpPostMethod(domain+programMethod,Utility.convertNameValue(params4));
			} catch (Exception e) {
				logger.error("segment search error", e);
			}

			List<ContentsTbl> scodes = new ArrayList<ContentsTbl>();

			if(StringUtils.isNotBlank(programXml))
				try{
					scodes =mediaToolInterfaceService.forwardSegmentsInfo(programXml);
				}catch (ServiceException e) {
					logger.error(" 스케줄 팝업 세크먼트ID 가져오기 오류 : ", e);
				}

			String regDate= Utility.getDate(contentsTbl.getRegDt(),"yyyy-MM-dd");
			String brdDate= Utility.getDate(contentsTbl.getBrdDd(),"yyyy-MM-dd");

			CodeTbl codeTbl = new CodeTbl();
			if(contentsTbl.getChannelCode() != null){
				params.put("clfCd", "CHAN");
				params.put("sclCd", contentsTbl.getChannelCode());

				codeTbl = codeManagerService.getCode(params);
			}

			CodeTbl codeTbl2 = new CodeTbl();
			if(contentsTbl.getCtCla() != null){
				params.put("clfCd", "CCLA");
				params.put("sclCd", contentsTbl.getCtCla());

				codeTbl2 = codeManagerService.getCode(params);
			}

			CodeTbl codeTbl3 = new CodeTbl();
			if(contentsTbl.getCtTyp() != null){
				params.put("clfCd", "CTYP");
				params.put("sclCd", contentsTbl.getCtTyp());

				codeTbl3 = codeManagerService.getCode(params);
			}

			List<CodeTbl> clas = codeManagerService.findCode(params2);
			List<CodeTbl> typs = codeManagerService.findCode(params3);

			view.addObject("scodes",scodes);
			view.addObject("contentsTbl", contentsTbl);
			view.addObject("contentsInstTbls", contentsInstTbls);		
			view.addObject("codeTbl", codeTbl);
			view.addObject("codeTbl2", codeTbl2);
			view.addObject("codeTbl3", codeTbl3);
			view.addObject("regDate", regDate);
			view.addObject("brdDate", brdDate);
			view.addObject("clas", clas);
			view.addObject("typs", typs);
			view.addObject("search", search);

			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("- 콘텐츠 상세정보를 조회할 수 없음.", e);
		}

		return view;
	}

	@RequestMapping(value = {"/content/ajaxFindContentInfoRms.ssc","/popup/ajaxFindContentInfoRms.ssc"}, method = RequestMethod.POST)
	public ModelAndView findContentInfoRms(
			@ModelAttribute("search") Search search, 
			@RequestParam(value = "pcode", required = false) String pcode,
			ModelMap map) {

		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();

		params.put("ctId", search.getCtId());
		// params.put("ctiFmt", "1"); // 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
		// ,오디오 원본:'3XX')
		params.put("notCtiFmt", "1");// 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
		// ,오디오 원본:'3XX')

		Map<String, Object> params2 = new HashMap<String, Object>();
		Map<String, Object> params3 = new HashMap<String, Object>();
		params2.put("clfCd", "CCLA");
		params3.put("clfCd", "CTYP");

		Map<String, Object> params5 = new HashMap<String, Object>();
		params5.put("ctId", search.getCtId());
		// params.put("ctiFmt", "1"); // 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
		// ,오디오 원본:'3XX')
		//params5.put("ctiFmt", "1");// 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
		// ,오디오 원본:'3XX')
		String pid="";
		try {
			ContentsTbl contentsTbl = contentsManagerService
					.getContents(params);
			//			List<ContentsInstTbl> contentsInstTbls = contentsInstManagerService
			//			.findContentsInstSummary(params5);
			List<ContentsInstTbl> rcontentsInstTbls = contentsInstManagerService
					.findContentsRms(params5);

			String regDate= Utility.getDate(contentsTbl.getRegDt(),"yyyy-MM-dd");

			String brdDate="";
			if(contentsTbl.getBrdDd() != null)	
				brdDate= Utility.getDate(contentsTbl.getBrdDd(),"yyyy-MM-dd");

			CodeTbl codeTbl2 = new CodeTbl();
			if(contentsTbl.getCtCla() != null){
				params.put("clfCd", "CCLA");
				params.put("sclCd", contentsTbl.getCtCla());

				codeTbl2 = codeManagerService.getCode(params);
			}

			CodeTbl codeTbl3 = new CodeTbl();
			if(contentsTbl.getCtTyp() != null){
				params.put("clfCd", "CTYP");
				params.put("sclCd", contentsTbl.getCtTyp());

				codeTbl3 = codeManagerService.getCode(params);
			}
			List<CodeTbl> clas = codeManagerService.findCode(params2);
			List<CodeTbl> typs = codeManagerService.findCode(params3);

			pid= contentsTbl.getPgmId();
			view.addObject("contentsTbl", contentsTbl);
			//view.addObject("contentsInstTbls", contentsInstTbls);
			view.addObject("rcontentsInstTbls", rcontentsInstTbls);

			view.addObject("codeTbl2", codeTbl2);
			view.addObject("codeTbl3", codeTbl3);
			view.addObject("regDate", regDate);
			view.addObject("brdDate", brdDate);
			view.addObject("clas", clas);
			view.addObject("typs", typs);
			view.addObject("search", search);

			view.setViewName("jsonView");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("- 콘텐츠 상세정보를 조회할 수 없음.", e);
		}

		if(pid != null){
			Map<String, Object> params4 = new HashMap<String, Object>();
			params4.put("program_id_equals", pid);
			params4.put("pids","segment_title,s_code");  // 실제로 받아와야 할 컬럼들.

			String domain = messageSource.getMessage("meta.system.domain", null,
					Locale.KOREA);
			String programMethod = messageSource.getMessage("meta.system.search.segment",
					null, Locale.KOREA);

			/**
			 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
			 * param  pgmId;
			 */
			String programXml = "";

			try {
				HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
				programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params4));
				//programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params4));
			} catch (Exception e) {
				logger.error("program search error", e);
			}

			List<ContentsTbl> scodes = new ArrayList<ContentsTbl>();

			if(StringUtils.isNotBlank(programXml))
				try{
					scodes =mediaToolInterfaceService.forwardSegmentsInfo(programXml);
					
					view.addObject("scodes",scodes);
					view.setViewName("jsonView");
				}catch (Exception e) {
					view.addObject("result", "");
					logger.error(" 스케줄 팝업 세크먼트ID 가져오기 오류 : ", e);
				}
		}

		return view;
	}

	@RequestMapping(value = {"/content/fileUpload.ssc", "/popup/fileUpload.ssc"}, method = RequestMethod.POST)
	public ModelAndView fileUploadContent(
			@ModelAttribute("search") Search search,
			//			@RequestParam("file-upload") MultipartFile file,
			@RequestParam(value = "popup", required = false) String popup,
			@RequestParam(value = "channelCode", required = false) String channelCode,
			@RequestParam(value = "brdDd", required = false) String brdDd,
			@RequestParam("_innods_filename") String[] _innods_filename,
			@RequestParam("_innods_sendbytes") String[] _innods_sendbytes,
			@RequestParam("_innods_filesize") String[] _innods_filesize,
			@RequestParam("_innods_status") String[] _innods_status,
			@ModelAttribute("contentsTbl") ContentsTbl contentsTbl) {

		ModelAndView mnv = new ModelAndView();

		// config.properties 파일에 real path 경로 설정 필요
		String target = messageSource.getMessage("esh.file.target", null, null);
		String targetDest = messageSource.getMessage("high.resolution.target",
				null, null);

		String savePath = servletContext.getRealPath("/") + target;
		String destPath = servletContext.getRealPath("/") + targetDest;

		if (SystemUtils.IS_OS_WINDOWS) {
			savePath = target;
			destPath = targetDest;

			if (savePath.indexOf("/") > -1)
				savePath = savePath.replaceAll("\\/", "\\\\");
			if (destPath.indexOf("/") > -1)
				destPath = destPath.replaceAll("\\/", "\\\\");

		} else {
			target = messageSource.getMessage("esh.file.target", null, null);
			targetDest = messageSource.getMessage("high.resolution.target", null, null);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		ContentsInstTbl contentsInstTbl = new ContentsInstTbl();
		List<WeekSchTbl> weekSchTbls = new ArrayList<WeekSchTbl>();
		List<ProFlTbl> proFlTbls = new ArrayList<ProFlTbl>();

		logger.debug("contentsTbl.getPgmId():" + contentsTbl.getPgmId());

		try {
			params.put("pgmCd", contentsTbl.getPgmCd());	
			if (StringUtils.isNotBlank(contentsTbl.getPgmId())) {
				params.put("programId", contentsTbl.getPgmId());

				weekSchTbls = weekSchManagerService.findWeekSchTbl(params);
			}

			String wrkFile = savePath;
			if (weekSchTbls.size() > 0) {
				destPath += weekSchTbls.get(0).getProgramCode() + File.separator + weekSchTbls.get(0).getProgramId();

				contentsTbl.setChannelCode(weekSchTbls.get(0).getChannelCode());

				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				try{
					contentsTbl.setBrdDd(sf.parse(DateUtil.convertFormat(weekSchTbls.get(0).getProgramPlannedDate(), "yyyy-MM-dd")));
				}catch(Exception e){
					logger.debug("웹등록 (편성표로 조회) content TBL 방송일 입력 오류");
				}
			} else {
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

				destPath += contentsTbl.getPgmCd() + File.separator + contentsTbl.getPgmId();

				contentsTbl.setChannelCode(channelCode);

				logger.debug("입력 방송일=======>"+brdDd);

				try{
					contentsTbl.setBrdDd(sf.parse(DateUtil.convertFormat(brdDd, "yyyy-MM-dd")));
				}catch(Exception e){
					logger.debug("수동등록 (프로그램정보 조회) content TBL 방송일 입력 오류");
				}
			}


			if (logger.isDebugEnabled()) {
				logger.debug("filename   : " + _innods_filename[0]);
				logger.debug("usrFileNm   : " + _innods_filename[0]);
			}

			mnv.addObject("target", wrkFile);
			mnv.addObject("_innods_filename", _innods_filename[0]);
			mnv.addObject("message", "");
			mnv.addObject("_innods_filesize", _innods_filesize[0]);


			contentsTbl.setUseYn("Y");
			contentsTbl.setRegrid("WEB");

			contentsInstTbl.setFlPath(destPath.substring(destPath.indexOf(targetDest) - 1));
			if(!contentsInstTbl.getFlPath().endsWith("/"))
				contentsInstTbl.setFlPath(contentsInstTbl.getFlPath()+"/");


			contentsInstTbl.setFlSz(Long.parseLong(_innods_filesize[0]));
			contentsInstTbl.setOrgFileNm(_innods_filename[0].substring(0,
					_innods_filename[0].lastIndexOf(".")));
			contentsInstTbl.setUsrFileNm(_innods_filename[0].substring(0,
					_innods_filename[0].lastIndexOf(".")));
			contentsInstTbl.setFlExt(_innods_filename[0].substring(
					_innods_filename[0].lastIndexOf(".") + 1, _innods_filename[0].length()));
			contentsInstTbl.setWrkFileNm("");
			contentsInstTbl.setBitRt("-1");
			contentsInstTbl.setColorCd("");

			contentsInstTbl.setRegDt(Utility.getTimestamp());
			params.put("clfCd", "FMAT");
			params.put("sclCd", _innods_filename[0].substring(
					_innods_filename[0].lastIndexOf(".") + 1, _innods_filename[0].length()).toLowerCase()+"1");

			logger.debug("params.get(\"sclCd\"):"+params.get("sclCd"));
			CodeTbl codeTbl = new CodeTbl();
			codeTbl = codeManagerService.getCode(params);

			contentsInstTbl.setCtiFmt((codeTbl.getClfNm()==null)?"":codeTbl.getClfNm());

			contentsInstTbl.setDrpFrmYn("N");
			contentsInstTbl.setVdHresol(-1);
			contentsInstTbl.setVdVresol(-1);
			contentsInstTbl.setAvGubun("V");


			/**
			 * 네이밍 수정순번 적용
			 */
			contentsTbl.setPgmNum(codeManagerService.getContentsPgmNum(contentsTbl));

			contentsManagerService.insertContents(contentsTbl, contentsInstTbl);

			if(contentsTbl.getTrimmSte().equals("P")){
				List<TranscorderHisTbl> traHisTbls = new ArrayList<TranscorderHisTbl>();

				proFlTbls = proBusiManagerService.findPgmProBusi(params);
				for (ProFlTbl proFlTbl : proFlTbls) {

					TranscorderHisTbl traHisTbl = new TranscorderHisTbl();

					traHisTbl.setCtiId(contentsInstTbl.getCtiId());
					traHisTbl.setProFlid(Integer.parseInt(proFlTbl.getProFlid()));
					traHisTbl.setFlExt(proFlTbl.getExt());
					traHisTbl.setUseYn("Y");
					traHisTbl.setWorkStatcd("000");

					traHisTbl.setJobGubun("V");

					traHisTbl.setRegDt(Utility.getTimestamp());

					traHisTbls.add(traHisTbl);
				}
				workflowManagerService.insertTranscorderHis(traHisTbls);
			}

		} catch (ServiceException e) {
			logger.error("파일 정보 입력 실패" + e);
			e.printStackTrace();
		}

		if(popup.equals("Y")){
			mnv.setViewName("redirect:/popup/WebRegPopup.ssc?result=Y");
		}else{
			mnv.setViewName("redirect:/content/WebReg.ssc?result=Y");
		}
		return mnv;
	}

	@RequestMapping(value = "/popup/mfileUpload.ssc", method = RequestMethod.POST)
	public ModelAndView fileMetaUploadContent(
			@ModelAttribute("search") Search search,
			//			@RequestParam("file-upload") MultipartFile file,
			@RequestParam(value = "popup", required = false) String popup,
			@RequestParam(value = "pcode", required = false) String pcode,
			@RequestParam(value = "nid", required = false) String nid,
			@RequestParam(value = "avGubun", required = false) String avGubun,
			@RequestParam(value = "channelCode", required = false) String channelCode,
			@RequestParam(value = "brdDd", required = false) String brdDd,
			@RequestParam(value = "cttyp", required = false) String cttyp,
			@RequestParam("_innods_filename") String[] _innods_filename,
			@RequestParam("_innods_sendbytes") String[] _innods_sendbytes,
			@RequestParam("_innods_filesize") String[] _innods_filesize,
			@RequestParam("_innods_status") String[] _innods_status,
			@RequestParam("_origon_fname") String _origon_fname,
			@ModelAttribute("contentsTbl") ContentsTbl contentsTbl) {


		ModelAndView mnv = new ModelAndView();
		String[] orgFileNm=null;
		if(_origon_fname.length()>0)
			orgFileNm = _origon_fname.split(",");


		for(int i=0 ; i < _innods_filename.length -1 ; i++){

			// config.properties 파일에 real path 경로 설정 필요
			String target = messageSource.getMessage("meta.manual.target", null, null);
			String targetDest = messageSource.getMessage("meta.manual.target",
					null, null);

			String savePath = servletContext.getRealPath("/") + target;
			String destPath = servletContext.getRealPath("/") + targetDest;

			if (SystemUtils.IS_OS_WINDOWS) {
				if (targetDest.indexOf("/") > -1)
					targetDest = targetDest.replaceAll("\\/", "\\\\");
				savePath = target;
				destPath = targetDest;

				if (savePath.indexOf("/") > -1)
					savePath = savePath.replaceAll("\\/", "\\\\");
				if (destPath.indexOf("/") > -1)
					destPath = destPath.replaceAll("\\/", "\\\\");

			} else {
				target = messageSource.getMessage("meta.manual.target", null, null);
				targetDest = messageSource.getMessage("meta.manual.target", null, null);
			}

			Map<String, Object> params = new HashMap<String, Object>();
			ContentsInstTbl contentsInstTbl = new ContentsInstTbl();
			List<WeekSchTbl> weekSchTbls = new ArrayList<WeekSchTbl>();

			logger.debug("contentsTbl.getPgmId():" + contentsTbl.getPgmId());

			try {
				params.put("pgmCd", contentsTbl.getPgmCd());	
				if (StringUtils.isNotBlank(contentsTbl.getPgmId())) {
					params.put("programId", contentsTbl.getPgmId());

					weekSchTbls = weekSchManagerService.findWeekSchTbl(params);
				}

				String wrkFile = savePath;
				if (weekSchTbls.size() > 0) {
					//wrkFile += File.separator+target+File.separator+weekSchTbls.get(0).getProgramCode() + File.separator
					//+ weekSchTbls.get(0).getProgramId() + File.separator;
					destPath += pcode;

					contentsTbl.setChannelCode(weekSchTbls.get(0).getChannelCode());

					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					try{
						contentsTbl.setBrdDd(sf.parse(DateUtil.convertFormat(weekSchTbls.get(0).getProgramPlannedDate(), "yyyy-MM-dd")));
					}catch(Exception e){
						logger.debug("웹등록 (편성표로 조회) content TBL 방송일 입력 오류");
					}
				} else {
					SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

					destPath += pcode;

					contentsTbl.setChannelCode(channelCode);

					logger.debug("입력 방송일=======>"+brdDd);

					try{
						//logger.debug(">>>>>>>>>>>>>>>>>>"+sf.parse(Utility.getDate(contentsTbl.getBrdDd(), "yyyy-MM-dd")));
						contentsTbl.setBrdDd(sf.parse(DateUtil.convertFormat(brdDd, "yyyy-MM-dd")));
					}catch(Exception e){
						logger.debug("수동등록 (프로그램정보 조회) content TBL 방송일 입력 오류");
					}
				}

				if (logger.isDebugEnabled()) {
					logger.debug("filename   : " + _innods_filename[i]);
					logger.debug("usrFileNm   : " + _innods_filename[i]);
				}

				mnv.addObject("target", wrkFile);
				mnv.addObject("_innods_filename", _innods_filename[i]);
				mnv.addObject("message", "");
				mnv.addObject("_innods_filesize", _innods_filesize[i]);

				contentsTbl.setCtNm(_innods_filename[i].substring(0,
						_innods_filename[i].lastIndexOf(".")));
				contentsTbl.setUseYn("Y");
				contentsTbl.setRegrid("RMS");
				contentsTbl.setTrimmSte("N");
				contentsTbl.setNid(nid);
				contentsTbl.setPgmCd(pcode);


				//contentsTbl.setPgmNm(orgFileNm[orgFileNm.length-i-1]);


				contentsInstTbl.setAvGubun(avGubun);

				if(cttyp.equals("201") || cttyp =="201"){
					contentsTbl.setCtTyp("01");
				}else if(cttyp.equals("202") || cttyp =="202"){
					contentsTbl.setCtTyp("02");
				}else if(cttyp.equals("203") || cttyp =="203"){
					contentsTbl.setCtTyp("03");
				}else if(cttyp.equals("204") || cttyp =="204"){
					contentsTbl.setCtTyp("04");
				}else if(cttyp.equals("205") || cttyp =="205"){
					contentsTbl.setCtTyp("05");
				}else if(cttyp.equals("206") || cttyp =="206"){
					contentsTbl.setCtTyp("06");
				}else if(cttyp.equals("207") || cttyp =="207"){
					contentsTbl.setCtTyp("99");
				}else{

				}

				contentsInstTbl.setFlSz(Long.parseLong(_innods_filesize[i]));
				contentsInstTbl.setOrgFileNm(_innods_filename[i].substring(0,
						_innods_filename[i].lastIndexOf(".")));

				contentsInstTbl.setUsrFileNm(orgFileNm[orgFileNm.length-i-1].substring(0,
						orgFileNm[orgFileNm.length-i-1].lastIndexOf(".")));
				contentsInstTbl.setFlExt(_innods_filename[i].substring(
						_innods_filename[i].lastIndexOf(".") + 1, _innods_filename[i].length()));
				contentsInstTbl.setWrkFileNm("");
				contentsInstTbl.setBitRt("-1");
				contentsInstTbl.setColorCd("");

				contentsInstTbl.setRegDt(Utility.getTimestamp());
				params.put("clfCd", "FMAT");

				if(avGubun.equals("A"))
					params.put("sclCd", _innods_filename[i].substring(
							_innods_filename[i].lastIndexOf(".") + 1, _innods_filename[i].length()).toLowerCase()+"3");
				else
					params.put("sclCd", _innods_filename[i].substring(
							_innods_filename[i].lastIndexOf(".") + 1, _innods_filename[i].length()).toLowerCase()+"1");

				logger.debug("params.get(\"sclCd\"):"+params.get("sclCd"));
				logger.debug(destPath + File.separator);
				CodeTbl codeTbl = new CodeTbl();
				codeTbl = codeManagerService.getCode(params);

				logger.debug("destPath =======>"+destPath+", targetDest =====>"+targetDest);

				if(destPath.indexOf(targetDest) == 0) {
					destPath = File.separator + destPath;
				}

				logger.debug("###destPath.substring(destPath.indexOf(targetDest) - 1) + File.separator: "+destPath.substring(destPath.indexOf(targetDest) - 1) + File.separator);
				contentsInstTbl.setCtiFmt(StringUtils.defaultString(codeTbl.getClfNm(), ""));
				contentsInstTbl.setFlPath(destPath.substring(destPath.indexOf(targetDest) - 1) + File.separator);
				contentsInstTbl.setDrpFrmYn("N");
				contentsInstTbl.setVdHresol(-1);
				contentsInstTbl.setVdVresol(-1);

				/**
				 * 네이밍 수정순번 적용
				 */
				contentsTbl.setPgmNum(codeManagerService.getContentsPgmNum(contentsTbl));

				contentsManagerService.insertContents(contentsTbl, contentsInstTbl);


				/**
				 * rms contents count 
				 */
				Map<String, Object> params2 = new HashMap<String, Object>();
				String contentCount = "";
				try{
					params2.put("nid", nid);	


					ContentsTbl contents  = contentsManagerService.getRmsContentsCount(params2);

					contentCount=contents.getCount();
				}catch(ServiceException e) {
					logger.error("rms content count 에러" + e);
					e.printStackTrace();
				}

				Map<String, Object> params3 = new HashMap<String, Object>();

				params3.put("nid",nid);
				params3.put("contents_no",contentCount);

				String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
				String programMethod = "";
				//properties 추가 하기   ->2013.2.18 -> 완료

				if(avGubun.equals("V") || avGubun == "V"){
					programMethod = messageSource.getMessage("meta.system.rmscontents.movie.count", null, Locale.KOREA);

				}else{
					programMethod = messageSource.getMessage("meta.system.rmscontents.audio.count", null, Locale.KOREA);

				}

				String programXml = "";
				try {
					HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
					programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params3));
					//programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params3));
				}catch (Exception e) {
					logger.error("MetaHub에 RMSContents Count 전송 오류", e);
				}

				mnv.setViewName("jsonView");
				mnv.addObject("search", search);
			} catch (ServiceException e) {
				logger.error("파일 정보 입력 실패" + e);
				e.printStackTrace();
			}

		}

		return mnv;
	}

	@RequestMapping(value = {"/content/filedownload.ssc","/popup/filedownload.ssc"})
	public ModelAndView FileDownload(@ModelAttribute("search") Search search,
			@RequestParam("downFile") String downFile,
			@RequestParam("downFileNm") String downFileNm) {
		ModelAndView view = new ModelAndView();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("search", search);

		String baseDir = servletContext.getRealPath("/");

		try {
			downFile = downFile.replaceAll("\\\\", "/");
			String fileName = downFile.substring(downFile.lastIndexOf("/") + 1);

			String[] ext = fileName.split("\\.");
			if(logger.isDebugEnabled()) {
				logger.debug("ext[1]: "+ext[1]);
				logger.debug("downFileNm: "+downFileNm);
			}


			Attatch attatch = new Attatch();
			attatch.setRealNm(downFileNm+"."+ext[1]);
			
			if(logger.isDebugEnabled())
				logger.debug("real_nm: "+attatch.getRealNm());
			
			File f = new File(baseDir + downFile);
			if (logger.isDebugEnabled()) {
				logger.debug("source file : " + baseDir + downFile);
				logger.debug("fileName : " + fileName);
			}
			if (f.exists()) {
				params.put("file", f);
				params.put("attatch", attatch);
				view.addObject("result", "Y");
				view.setViewName("fileView");
			} else {
				view.addObject("result", "N");
			}
			view.addAllObjects(params);
			// }
		} catch (Exception e) {
			logger.error("FileDownload Error", e);
			view.addObject("result", "N");
		}
		return view;
	}

	@RequestMapping(value = {"/content/reContentML.ssc"})
	public ModelAndView ReContentMl(@ModelAttribute("search") Search search,
			@RequestParam("re_ctiId") Long ctiId,
			@RequestParam("re_downFileNm") String downFileNm) {

		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>");
		logger.debug(">>>>>>>>>>"+ctiId);
		logger.debug(">>>>>>>>>>"+downFileNm);
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>");
		ModelAndView view = new ModelAndView();

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("ctiId", ctiId);

		params.put("notCtiFmt", "1");

		List<ContentsInstTbl> contentsInstTbls = null;
		try {
			contentsInstTbls = contentsInstManagerService.findContentsInstSummary(params);
		} catch (ServiceException e1) {
			logger.error("contentsInst 정보 오류", e1);
		}

		String prefix = messageSource.getMessage("mam.mount.prefix2", null, null);

		try {
			String contentML = contentsManagerService.createContentML(contentsInstTbls.get(0).getPgmId(), contentsInstTbls.get(0).getProFlid());
			if(logger.isInfoEnabled()) {
				logger.info("contentML path : "+prefix+contentsInstTbls.get(0).getFlPath()+contentsInstTbls.get(0).getOrgFileNm()+"."+contentsInstTbls.get(0).getFlExt()+".xml");
			}
			xmlFileService.StringToFile(contentML, prefix+contentsInstTbls.get(0).getFlPath(), contentsInstTbls.get(0).getOrgFileNm()+"."+contentsInstTbls.get(0).getFlExt()+".xml");
		} catch (Exception e) {
			logger.error("saveReport - ContentML 생성 에러", e);
		}

		String downFile= contentsInstTbls.get(0).getFlPath()+contentsInstTbls.get(0).getOrgFileNm()+"."+contentsInstTbls.get(0).getFlExt()+".xml";

		//String downFileNm= null;
		String baseDir = servletContext.getRealPath("/");

		try {
			downFile = downFile.replaceAll("\\\\", "/");
			String fileName = downFile.substring(downFile.lastIndexOf("/") + 1);

			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>");
			logger.debug(">>>>>>>>>>"+downFile);
			logger.debug(">>>>>>>>>>"+fileName);
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>");

			String[] ext = fileName.split("\\.");

			Attatch attatch = new Attatch();
			attatch.setRealNm(downFileNm+"."+ext[1]+"."+ext[2]);
			File f = new File(baseDir + downFile);
			if (logger.isDebugEnabled()) {
				logger.debug("source file : " + baseDir + downFile);
				logger.debug("fileName : " + fileName);
			}
			if (f.exists()) {
				params.put("file", f);
				params.put("attatch", attatch);
				view.addObject("result", "Y");
				view.setViewName("fileView");
			} else {
				view.addObject("result", "N");
			}
			view.addAllObjects(params);
			// }
		} catch (Exception e) {
			logger.error("FileDownload Error", e);
			view.addObject("result", "N");
		}

		return view;
	}

	@RequestMapping(value = {"/content/newContentML.ssc"})
	public ModelAndView NewContentMl(@ModelAttribute("search") Search search,
			@RequestParam("proFlid") String proFlid,
			@RequestParam("downFlNm") String downFlNm,
			@RequestParam("pgmId") String pgmId,
			@RequestParam("fPath") String fPath,
			@RequestParam("orgNm") String orgNm,
			@RequestParam("proFlnm") String proFlnm,
			@RequestParam("ext") String ext,
			@RequestParam("flNameRule") String flNameRule) {

		ModelAndView view = new ModelAndView();

		Map<String, Object> params = new HashMap<String, Object>();



		String[] orgFileNm = orgNm.split("_");
		String[] proFileNm = proFlnm.split("_");
		String[] downFileNm = downFlNm.split("_");

		String prefix = messageSource.getMessage("mam.mount.prefix2", null, null);

		try {
			String contentML = contentsManagerService.createContentML(pgmId, Integer.parseInt(proFlid));
			if(logger.isInfoEnabled()) {
				logger.info("contentML path : "+prefix+fPath+orgFileNm[0]+"_"+proFileNm[2]+"_"+proFileNm[1]+"."+ext+".xml");
			}
			xmlFileService.StringToFile(contentML, prefix+fPath, orgFileNm[0]+"_"+proFileNm[2]+"_"+proFileNm[1]+"."+ext+".xml");
		} catch (Exception e) {
			logger.error("saveReport - ContentML 생성 에러", e);
		}

		String downFile= fPath+orgFileNm[0]+"_"+proFileNm[2]+"_"+proFileNm[1]+"."+ext+".xml";

		//String downFileNm= null;
		String baseDir = servletContext.getRealPath("/");

		try {
			downFile = downFile.replaceAll("\\\\", "/");
			String fileName = downFile.substring(downFile.lastIndexOf("/") + 1);

			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>");
			logger.debug(">>>>>>>>>>"+downFile);
			logger.debug(">>>>>>>>>>"+fileName);
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>");

			String[] exts = fileName.split("\\.");

			Attatch attatch = new Attatch();

			String downNm = "";
			for(int i=0; i < downFileNm.length -1 ; i++){
				if(downNm==""){
					downNm = downFileNm[i];
				}else{
					downNm = downNm +"_"+ downFileNm[i];
				}

			}
			downNm = downNm +"_"+flNameRule;
			logger.debug(">>>"+downNm+"."+exts[1]+"."+exts[2]);
			attatch.setRealNm(downNm+"."+exts[1]+"."+exts[2]);
			File f = new File(baseDir + downFile);
			if (logger.isDebugEnabled()) {
				logger.debug("source file : " + baseDir + downFile);
				logger.debug("fileName : " + fileName);
			}
			if (f.exists()) {
				params.put("file", f);
				params.put("attatch", attatch);
				view.addObject("result", "Y");
				view.setViewName("fileView");
			} else {
				view.addObject("result", "N");
			}
			view.addAllObjects(params);
			// }
		} catch (Exception e) {
			logger.error("FileDownload Error", e);
			view.addObject("result", "N");
		}

		return view;
	}

	@RequestMapping(value = {"/content/contentsCopy.ssc"})
	public ModelAndView contentsCopy(@ModelAttribute("search") Search search,
			@RequestParam("re_ctiId") Long ctiId) {

		ModelAndView view = new ModelAndView();

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("ctiId", ctiId);

		//params.put("ctiFmt", "1");

		List<ContentsInstTbl> contentsInstTbls = null;
		try {
			contentsInstTbls = contentsInstManagerService.findContentsSummary(params);
		} catch (ServiceException e1) {
			logger.error("contentsInst 정보 오류", e1);
		}

		String target="";

		// config.properties 파일에 real path 경로 설정 필요
		if (contentsInstTbls.get(0).getAvGubun().equals("V")) {
			target = messageSource.getMessage("manual.target.video", null, null);
			search.setGubun("video");
		} else {
			target = messageSource.getMessage("manual.target.audio", null, null);
			search.setGubun("audio");
		}


		String targetDest = messageSource.getMessage("high.resolution.target", null, null);
		String savePath = servletContext.getRealPath("/") + target;
		String destPath = servletContext.getRealPath("/") + targetDest;


		if (SystemUtils.IS_OS_WINDOWS) {
			savePath = target;
			destPath = targetDest;
			if (savePath.indexOf("/") > -1)
				savePath = savePath.replaceAll("\\/", "\\\\");
			if (destPath.indexOf("/") > -1)
				destPath = destPath.replaceAll("\\/", "\\\\");
		} else {
			savePath = servletContext.getRealPath("/") + target;
			destPath = servletContext.getRealPath("/") + targetDest;
		}


		if (contentsInstTbls.get(0).getAvGubun().equals("V")) {
		} else {
			savePath= savePath + File.separator  + "main";
		}

		String path=servletContext.getRealPath("/");
		path= path.substring(0, path.length()-1);

		String sourcePath = path + contentsInstTbls.get(0).getFlPath() + contentsInstTbls.get(0).getOrgFileNm()+"."+contentsInstTbls.get(0).getFlExt(); 
		//sourcePath = sourcePath. replaceAll("\\/", "\\\\");

		logger.debug("####################################################################################");
		logger.debug("###### moveFile sourcePath :" + sourcePath);
		logger.debug("####################################################################################");

		File f = new File(sourcePath);

		String restNm = savePath + File.separator + contentsInstTbls.get(0).getWrkFileNm()+"."+contentsInstTbls.get(0).getFlExt();

		logger.debug("####################################################################################");
		logger.debug("###### moveFile Destination :"  + restNm);
		logger.debug("####################################################################################");

		File f2 = new File(restNm);

		try {
			if(SystemUtils.IS_OS_WINDOWS) {
				FileUtils.copyFile(f, f2);
			} else {
				logger.debug("File COPY ================= cp "+sourcePath+" "+restNm);
				Runtime r = Runtime.getRuntime();
				try {
					Process p = r.exec("cp "+sourcePath+" "+restNm);
					p.waitFor();

					p = r.exec("chmod -R 777 "+restNm);
					p.waitFor();
					logger.info("File COPY Completed ================= cp "+sourcePath+" "+restNm);
				} catch (InterruptedException e) {
					logger.error("File COPY Error", e);
				}
			}
			view.addObject("result", "Y");
		}catch (Exception e) {
			view.addObject("result", "N");
			logger.error("수동등록으로 복사 에러" + e);
		}

		view.setViewName("jsonView");
		return view;
	}

	@RequestMapping(value = "/content/audioFiledownload.ssc")
	public ModelAndView AudioFileDownload(@ModelAttribute("search") Search search,
			@RequestParam("downFile") String downFile) {
		ModelAndView view = new ModelAndView();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("search", search);

		String baseDir = servletContext.getRealPath("/");

		try {
			downFile = downFile.replaceAll("\\\\", "/");
			String fileName = downFile.substring(downFile.lastIndexOf("/") + 1);

			Attatch attatch = new Attatch();
			attatch.setRealNm(fileName);
			File f = new File(baseDir + downFile);
			if (logger.isDebugEnabled()) {
				logger.debug("source file : " + baseDir + downFile);
				logger.debug("fileName : " + fileName);
			}
			if (f.exists()) {
				params.put("file", f);
				params.put("attatch", attatch);
				view.addObject("result", "Y");
				view.setViewName("fileView");
			} else {
				view.addObject("result", "N");
			}
			view.addAllObjects(params);
			// }
		} catch (Exception e) {
			logger.error("FileDownload Error", e);
			view.addObject("result", "N");
		}
		return view;
	}

	@RequestMapping(value = "/content/ajaxUpdateContentInfo.ssc", method = RequestMethod.POST)
	public ModelAndView updateContentInfo(
			@ModelAttribute("search") Search search,
			@RequestParam("pgmId") String pgmId,
			@RequestParam("pgmNm") String pgmNm,
			@RequestParam("pgmCd") String pgmCd,
			@RequestParam("ctId") String ctId,
			@RequestParam("pgmGrpCd") String pgmGrpCd) {
		ModelAndView view = new ModelAndView();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("search", search);

		ContentsTbl contentsTbl = new ContentsTbl();
		contentsTbl.setPgmId(pgmId);
		contentsTbl.setPgmNm(pgmNm);
		contentsTbl.setPgmCd(pgmCd);
		contentsTbl.setCtId(Long.parseLong(ctId));

		try {
			contentsManagerService.updateContents(contentsTbl);

			view.addObject("result", "Y");
			view.setViewName("jsonView");
		} catch (Exception e) {
			view.addObject("result", "");
			logger.error("updateContentInfo Error", e);
		}
		return view;
	}

	@RequestMapping(value = {"/content/findLive.ssc", "/popup/findLive.ssc"}, method = RequestMethod.POST)
	public ModelAndView findLIveList(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "programCode", required = false) String programCode,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			ModelMap map) {


		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}
		Map<String, Object> params = new HashMap<String, Object>();

		ModelAndView view = new ModelAndView();
		try {

			params.put("programCode", programCode);
			params.put("ctTyp", ctTyp);

			logger.debug("####################################");
			logger.debug("###### programCode: " + programCode);
			logger.debug("####################################");

			//LiveTbl liveTbl = liveManagerService.getLiveTbl(programCode);

			List<BusiPartnerPgm> busiPartnerPgm = busiPartnerPgmManagerService
					.findBusiPartnerPgm(params);

			// logger.debug("liveTbl recyn :" + liveTbl.getRecyn());
			// logger.debug("busiPartnerPgm size:" + busiPartnerPgm.size());

			//view.addObject("liveTbl", liveTbl);
			view.addObject("busiPartnerPgm", busiPartnerPgm);

			view.setViewName("jsonView");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			logger.error(" - 프로그램정보조회 팝업 화면: 프로파일 보기 할 수 없음.", e);
		}
		return view;
	}

	@RequestMapping(value = {"/content/ProgramInfoSave.ssc", "/popup/ProgramInfoSave.ssc"}, method = RequestMethod.POST)
	public ModelAndView updateLiveInfo(
			HttpServletRequest request,
			@ModelAttribute("search") Search search,
			@RequestParam(value = "programCode", required = false) String programCode,
			@RequestParam(value = "busiPartnerid", required = false) String busiPartnerid,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			@RequestParam(value = "audioModeMain", required = false) String audioModeMain,
			ModelMap map) {

		ModelAndView view = new ModelAndView();
		view.setViewName("jsonView");

		logger.debug("##############################");
		logger.debug("###### programGroupCode: " + search.getPgmGrpCd());
		logger.debug("###### programCode: " + programCode);
		logger.debug("###### programId: " + search.getPgmId());
		logger.debug("###### busiPartnerid: " + busiPartnerid);
		logger.debug("###### ctTyp: " + ctTyp);
		logger.debug("##############################");

		String[] iBusiPartnerid = null;
		if (!StringUtils.isEmpty(busiPartnerid))
			iBusiPartnerid = busiPartnerid.split(",");
		logger.debug("##############################");


		try {

			if(StringUtils.isNotBlank(search.getPgmGrpCd()) && StringUtils.isNotBlank(search.getPgmGrpCd())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("pgmId", search.getPgmId());
				List<ContentsTbl> contentsTbls = contentsManagerService.findContents(params);

				if(contentsTbls != null && contentsTbls.size() > 0) {
					for(ContentsTbl contentsTbl : contentsTbls) {
						contentsTbl.setPgmGrpCd(search.getPgmGrpCd());
						contentsManagerService.updateContents(contentsTbl);
					}
				}
			}

			BusiPartnerPgm bpp = new BusiPartnerPgm();
			bpp.setProgramCode(programCode);
			bpp.setCtTyp(ctTyp);
			busiPartnerPgmManagerService.deleteBusiPartnerPgm(bpp);

			UserTbl user = (UserTbl)WebUtils.getSessionAttribute(request, "user");
			if(logger.isInfoEnabled()) {
				logger.info("updateLiveInfo userId: "+user.getUserId());
				logger.info("updateLiveInfo programCode: " + programCode);
				logger.info("updateLiveInfo audioModeMain: "+audioModeMain);
			}
			if (iBusiPartnerid != null) {
				for (int i = 0; i < iBusiPartnerid.length; i++) {
					BusiPartnerPgm busipartnerPgm = new BusiPartnerPgm();
					busipartnerPgm.setProgramCode(programCode);
					busipartnerPgm.setCtTyp(ctTyp);
					busipartnerPgm.setBusiPartnerid(iBusiPartnerid[i]);
					busipartnerPgm.setAudioModeCode(audioModeMain);
					busipartnerPgm.setRegrid(user.getUserId());
					busipartnerPgm.setRegDt(Utility.getTimestamp());
					busiPartnerPgmManagerService.insertBusiPartnerPgm(busipartnerPgm);
				}
			}
			view.addObject("programCode", programCode);
			view.addObject("result", "Y");
			logger.debug("##############################");
			logger.debug("##############################");
			logger.debug("##############################");
			logger.debug("##############################");
		} catch (Exception e) {
			view.addObject("result", "");
			logger.error(" 프로그램정보조회 팝업 화면 : 프로파일 저장 오류", e);
		}
		return view;
	}

	@RequestMapping(value="/popup/TimeProgramSearch.ssc", method = RequestMethod.POST)
	public ModelAndView timeProgramSearch(@ModelAttribute("search") Search search, 
			@RequestParam(value = "pagestart", required = false) String pagestart,
			@RequestParam(value = "pagecurrent", required = false) String pagecurrent,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			ModelMap map) {

		ModelAndView view = new ModelAndView();
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		String startDt="";
		String endDt="";
		// 기간검색 설정
		if(search.getStartDt() != null){
			map.addAttribute("startDt", Utility.getDate(search.getStartDt(),"yyyy-MM-dd"));
			startDt = Utility.getDate(search.getStartDt(), "yyyyMMdd");
		}
		if(search.getEndDt() != null){
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(),"yyyy-MM-dd"));
			endDt = Utility.getDate(search.getEndDt(), "yyyyMMdd", 0);
		}		
		// 키워드 설정
		if (search.getKeyword() != null)
			map.addAttribute("ketword", search.getKeyword());

		String pagesize = "10";

		if(pagestart.equals("") || pagestart.equals(null)){
			pagestart = "1";
		}

		if(pagecurrent.equals("") || pagecurrent.equals(null)){
			pagecurrent = "1";
		}

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("pagecount","20");
		params.put("pagesize",pagesize);
		params.put("pagestart",pagestart);
		params.put("pagecurrent",pagecurrent);
		// 실제로 받아와야 할 컬럼들.
		params.put("pids","program_code,program_id,program_title,group_code,channel_code,program_planned_date,program_planned_start_time,descriptive_video_service_yn");

		if(StringUtils.isNotBlank(search.getKeyword()))
			params.put("searchvalue", search.getKeyword());
		if(StringUtils.isNotBlank(startDt))
			params.put("broadcast_planned_date_morethan", startDt);
		if(StringUtils.isNotBlank(endDt))
			params.put("broadcast_planned_date_lessthan", endDt);
		if(StringUtils.isNotBlank(search.getChannelCode2())&& !search.getChannelCode2().equals("0"))
			params.put("channel_code_equals", search.getChannelCode2());
		if(StringUtils.isNotBlank(ctTyp))
			params.put("ct_typ", ctTyp);

		String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
		String programMethod = messageSource.getMessage("meta.system.search.program", null, Locale.KOREA);

		boolean status = false;
		try {
			logger.info("domain+programMethod:"+domain+programMethod);
			status = Utility.connectHttpPostStatus(domain+programMethod, Utility.convertNameValue(params));

			view.setViewName("jsonView");
			if(!status){
				logger.error("MetaHub Search Error");
			}
		} catch (Exception e) {
			logger.error("MetaHub Search Error", e);
		}

		/* 2013
		String programXml = "";
		try {
			logger.info("domain+programMethod:"+domain+programMethod);
			programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params));

			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			map.addAttribute("proBusiTbls", proBusiTbls);
			map.addAttribute("proFlTbls", proFlTbls);
			map.addAttribute("busiPartnerTbls", busiPartnerTbls);
			map.addAttribute("ctTyp", ctTyp);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("metahub search error", e);
		}

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();

		if(StringUtils.isNotBlank(programXml)){
			try {
				contentsTbl =mediaToolInterfaceService.forwardProgramsInfo(programXml);

				map.addAttribute("contentsTbl", contentsTbl);
			} catch (ServiceException e) {
				logger.error("metahub xml parsing error", e);
			}
		}else{
			map.addAttribute("contentsTbl", contentsTbl);
		}
		int size = Integer.parseInt(pagesize);
		int start = Integer.parseInt(pagestart);
		int current = Integer.parseInt(pagecurrent);

		map.addAttribute("s_page","0");
		map.addAttribute("size", size);
		map.addAttribute("start", start);
		map.addAttribute("current", current);
		map.addAttribute("search", search);
		 */

		return view;
	}

	@RequestMapping(value="/popup/ProgramSearch.ssc", method = RequestMethod.POST)
	public ModelMap businessSearchList(@ModelAttribute("search") Search search, 
			@RequestParam(value = "pagestart", required = false) String pagestart,
			@RequestParam(value = "pagecurrent", required = false) String pagecurrent,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			ModelMap map) {


		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			//search.setMenuId("15");
		}

		String startDt="";
		String endDt="";
		// 기간검색 설정
		if(search.getStartDt() != null){
			map.addAttribute("startDt", Utility.getDate(search.getStartDt(),"yyyy-MM-dd"));
			startDt = Utility.getDate(search.getStartDt(),"yyyyMMdd");
		}
		if(search.getEndDt() != null){
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(),"yyyy-MM-dd"));
			endDt = Utility.getDate(search.getEndDt(),"yyyyMMdd", 0);
		}		
		// 키워드 설정
		if (search.getKeyword() != null)
			map.addAttribute("ketword", search.getKeyword());

		String pagesize = "10";

		if(pagestart.equals("") || pagestart.equals(null)){
			pagestart = "1";
		}

		if(pagecurrent.equals("") || pagecurrent.equals(null)){
			pagecurrent = "1";
		}

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("pagecount","20");
		params.put("pagesize",pagesize);
		params.put("pagestart",pagestart);
		params.put("pagecurrent",pagecurrent);
		//		params.put("position",null);
		params.put("pids","program_code,program_id,program_title,group_code,channel_code,program_planned_date,program_planned_start_time,audio_mode_main,descriptive_video_service_yn,rerun_classification,transmission_audio_mode_main");  // 실제로 받아와야 할 컬럼들.

		if(StringUtils.isNotBlank(search.getKeyword()))
			params.put("searchValue", search.getKeyword());
		if(StringUtils.isNotBlank(startDt))
			params.put("broadcast_planned_date_morethan", startDt);
		if(StringUtils.isNotBlank(endDt))
			params.put("broadcast_planned_date_lessthan", endDt);
		if(StringUtils.isNotBlank(search.getChannelCode2())&& !search.getChannelCode2().equals("0"))
			params.put("channel_code_equals", search.getChannelCode2());
		if(StringUtils.isNotBlank(ctTyp))
			params.put("ct_typ", ctTyp);

		String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
		String programMethod = messageSource.getMessage("meta.system.search.program", null, Locale.KOREA);


		String programXml = "";

		try {
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params));
			//programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params));

			if(logger.isDebugEnabled()) {
				logger.debug(programXml);
			}

			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			map.addAttribute("proBusiTbls", proBusiTbls);
			map.addAttribute("proFlTbls", proFlTbls);
			map.addAttribute("busiPartnerTbls", busiPartnerTbls);
			map.addAttribute("ctTyp", ctTyp);

		} catch (Exception e) {
			logger.error("metahub search error", e);
		}

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();
		//contentsTbl.add(null);
		if(StringUtils.isNotBlank(programXml)){
			try {
				contentsTbl = mediaToolInterfaceService.forwardProgramsInfo(programXml);

				map.addAttribute("contentsTbl", contentsTbl);
			} catch (ServiceException e) {
				logger.error("metahub xml parsing error", e);
			}
		}else{
			map.addAttribute("contentsTbl", contentsTbl);
		}
		int size = Integer.parseInt(pagesize);
		int start = Integer.parseInt(pagestart);
		int current = Integer.parseInt(pagecurrent);

		map.addAttribute("s_page","0");
		map.addAttribute("size", size);
		map.addAttribute("start", start);
		map.addAttribute("current", current);
		map.addAttribute("search", search);

		return map;
	}

	@RequestMapping(value="/popup/scd_ProgramSearch.ssc", method = RequestMethod.POST)
	public ModelMap scd_ProgramSearchList(@ModelAttribute("search") Search search, 
			@RequestParam(value = "pagestart", required = false) String pagestart,
			@RequestParam(value = "pagecurrent", required = false) String pagecurrent,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			ModelMap map) {


		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		String startDt="";
		String endDt="";
		// 기간검색 설정
		if(search.getStartDt() != null){
			map.addAttribute("startDt", Utility.getDate(search.getStartDt(),"yyyy-MM-dd"));
			startDt = Utility.getDate(search.getStartDt(),"yyyyMMdd");
		}
		if(search.getEndDt() != null){
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(),"yyyy-MM-dd"));
			endDt = Utility.getDate(search.getEndDt(),"yyyyMMdd", 1);
		}		
		// 키워드 설정
		if (search.getKeyword() != null)
			map.addAttribute("ketword", search.getKeyword());

		String pagesize = "10";

		if(pagestart.equals("") || pagestart.equals(null)){
			pagestart = "1";
		}

		if(pagecurrent.equals("") || pagecurrent.equals(null)){
			pagecurrent = "1";
		}

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("pagecount","20");
		params.put("pagesize",pagesize);
		params.put("pagestart",pagestart);
		params.put("pagecurrent",pagecurrent);
		params.put("pids","program_code,program_id,program_title,group_code,channel_code,program_planned_date,program_planned_start_time");  // 실제로 받아와야 할 컬럼들.

		if(StringUtils.isNotBlank(search.getKeyword()))
			params.put("searchvalue", search.getKeyword());
		if(StringUtils.isNotBlank(startDt))
			params.put("broadcast_planned_date_morethan", startDt);
		if(StringUtils.isNotBlank(endDt))
			params.put("broadcast_planned_date_lessthan", endDt);
		if(StringUtils.isNotBlank(search.getChannelCode2())&& !search.getChannelCode2().equals("0"))
			params.put("channel_code_equals", search.getChannelCode2());

		String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
		String programMethod = messageSource.getMessage("meta.system.search.program", null, Locale.KOREA);

		String programXml = "";
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("domain+programMethod:"+domain+programMethod);
			}

			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params));
			//programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params));

			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			map.addAttribute("proBusiTbls", proBusiTbls);
			map.addAttribute("proFlTbls", proFlTbls);
			map.addAttribute("busiPartnerTbls", busiPartnerTbls);
		} catch (Exception e) {
			logger.error("MetaHub request error", e);
		}

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();

		if(StringUtils.isNotBlank(programXml)){
			try {
				contentsTbl = mediaToolInterfaceService.forwardProgramsInfo(programXml);

				map.addAttribute("contentsTbl", contentsTbl);
			} catch (ServiceException e) {
				logger.error("MetaHub meta parsing error", e);
			}
		}else{
			map.addAttribute("contentsTbl", contentsTbl);
		}
		int size = Integer.parseInt(pagesize);
		int start = Integer.parseInt(pagestart);
		int current = Integer.parseInt(pagecurrent);

		map.addAttribute("s_page","0");
		map.addAttribute("size", size);
		map.addAttribute("start", start);
		map.addAttribute("current", current);
		map.addAttribute("search", search);
		map.addAttribute("ctTyp", ctTyp);
		return map;
	}

	@RequestMapping(value="/popup/RmsProgramSearch.ssc", method = RequestMethod.POST)
	public ModelMap ProgramSearchListRms(@ModelAttribute("search") Search search, 
			@RequestParam(value = "pagestart", required = false) String pagestart,
			@RequestParam(value = "pagecurrent", required = false) String pagecurrent,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			ModelMap map) {


		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		String startDt="";
		String endDt="";
		// 기간검색 설정
		if(search.getStartDt() != null){
			map.addAttribute("startDt", Utility.getDate(search.getStartDt(),"yyyy-MM-dd"));
			startDt = Utility.getDate(search.getStartDt(),"yyyyMMdd");
		}
		if(search.getEndDt() != null){
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(),"yyyy-MM-dd"));
			endDt = Utility.getDate(search.getEndDt(),"yyyyMMdd", 1);
		}		
		// 키워드 설정
		if (search.getKeyword() != null)
			map.addAttribute("ketword", search.getKeyword());

		String pagesize = "10";

		if(pagestart.equals("") || pagestart.equals(null)){
			pagestart = "1";
		}

		if(pagecurrent.equals("") || pagecurrent.equals(null)){
			pagecurrent = "1";
		}

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("pagecount","20");
		params.put("pagesize",pagesize);
		params.put("pagestart",pagestart);
		params.put("pagecurrent",pagecurrent);
		params.put("pids","program_code,program_id,program_title,group_code,channel_code,program_planned_date,program_planned_start_time");  // 실제로 받아와야 할 컬럼들.

		if(StringUtils.isNotBlank(search.getKeyword()))
			params.put("searchvalue", search.getKeyword());
		if(StringUtils.isNotBlank(startDt))
			params.put("broadcast_planned_date_morethan", startDt);
		if(StringUtils.isNotBlank(endDt))
			params.put("broadcast_planned_date_lessthan", endDt);
		if(StringUtils.isNotBlank(search.getChannelCode2())&& !search.getChannelCode2().equals("0"))
			params.put("channel_code_equals", search.getChannelCode2());

		String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
		String programMethod = messageSource.getMessage("meta.system.search.program", null, Locale.KOREA);

		String programXml = "";
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("domain+programMethod:"+domain+programMethod);
			}
			
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params));
			//programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params));

			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			map.addAttribute("proBusiTbls", proBusiTbls);
			map.addAttribute("proFlTbls", proFlTbls);
			map.addAttribute("busiPartnerTbls", busiPartnerTbls);
		} catch (Exception e) {
			logger.error("MetaHub request error", e);
		}

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();

		if(StringUtils.isNotBlank(programXml)){
			try {
				contentsTbl = mediaToolInterfaceService.forwardProgramsInfo(programXml);

				map.addAttribute("contentsTbl", contentsTbl);
			} catch (ServiceException e) {
				logger.error("MetaHub meta parsing error", e);
			}
		}else{
			map.addAttribute("contentsTbl", contentsTbl);
		}
		int size = Integer.parseInt(pagesize);
		int start = Integer.parseInt(pagestart);
		int current = Integer.parseInt(pagecurrent);

		map.addAttribute("s_page","0");
		map.addAttribute("size", size);
		map.addAttribute("start", start);
		map.addAttribute("current", current);
		map.addAttribute("search", search);
		map.addAttribute("ctTyp", ctTyp);
		return map;
	}

	@RequestMapping(value="/popup/MRmsProgramSearch.ssc", method = RequestMethod.POST)
	public ModelMap ProgramSearchListMRms(@ModelAttribute("search") Search search, 
			@RequestParam(value = "pagestart", required = false) String pagestart,
			@RequestParam(value = "pagecurrent", required = false) String pagecurrent,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		String startDt="";
		String endDt="";
		// 기간검색 설정
		if(search.getStartDt() != null){
			map.addAttribute("startDt", Utility.getDate(search.getStartDt(),"yyyy-MM-dd"));
			startDt = Utility.getDate(search.getStartDt(),"yyyyMMdd");
		}
		if(search.getEndDt() != null){
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(),"yyyy-MM-dd"));
			endDt = Utility.getDate(search.getEndDt(),"yyyyMMdd", 1);
		}		
		// 키워드 설정
		if (search.getKeyword() != null)
			map.addAttribute("ketword", search.getKeyword());

		String pagesize = "10";

		if(pagestart.equals("") || pagestart.equals(null)){
			pagestart = "1";
		}

		if(pagecurrent.equals("") || pagecurrent.equals(null)){
			pagecurrent = "1";
		}

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("pagecount","20");
		params.put("pagesize",pagesize);
		params.put("pagestart",pagestart);
		params.put("pagecurrent",pagecurrent);
		params.put("pids","program_code,program_id,program_title,group_code,channel_code,program_planned_date,program_planned_start_time");  // 실제로 받아와야 할 컬럼들.

		if(StringUtils.isNotBlank(search.getKeyword()))
			params.put("searchvalue", search.getKeyword());
		if(StringUtils.isNotBlank(startDt))
			params.put("broadcast_planned_date_morethan", startDt);
		if(StringUtils.isNotBlank(endDt))
			params.put("broadcast_planned_date_lessthan", endDt);
		if(StringUtils.isNotBlank(search.getChannelCode2())&& !search.getChannelCode2().equals("0"))
			params.put("channel_code_equals", search.getChannelCode2());

		String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
		String programMethod = messageSource.getMessage("meta.system.search.program", null, Locale.KOREA);

		String programXml = "";
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("domain+programMethod:"+domain+programMethod);
			}
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params));
			//programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params));

			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			map.addAttribute("proBusiTbls", proBusiTbls);
			map.addAttribute("proFlTbls", proFlTbls);
			map.addAttribute("busiPartnerTbls", busiPartnerTbls);
		} catch (Exception e) {
			logger.error("MetaHub request error", e);
		}

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();
		if(logger.isDebugEnabled())
			logger.debug("programXml: "+programXml);
		if(StringUtils.isNotBlank(programXml)){
			try {
				contentsTbl =mediaToolInterfaceService.forwardProgramsInfo(programXml);

				map.addAttribute("contentsTbl", contentsTbl);
			} catch (ServiceException e) {
				logger.error("MetaHub meta parsing error", e);
			}
		}else{
			map.addAttribute("contentsTbl", contentsTbl);
		}
		int size = Integer.parseInt(pagesize);
		int start = Integer.parseInt(pagestart);
		int current = Integer.parseInt(pagecurrent);

		map.addAttribute("s_page","0");
		map.addAttribute("size", size);
		map.addAttribute("start", start);
		map.addAttribute("current", current);
		map.addAttribute("search", search);
		map.addAttribute("ctTyp", ctTyp);

		return map;
	}

	@RequestMapping(value = "/popup/ProgramSearch.ssc", method = RequestMethod.GET)
	public ModelMap getProgramSearch(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}
		if (search.getChannelCode2() == null) {
			search.setChannelCode2("0");
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		Date today = new Date();
		String tomorrow = sf.format(new Date(today.getTime()+(long)(1000*60*60*-168)));
		String date = sf.format(new Date(today.getTime()));
		search.setStartDt(Utility.getDate(tomorrow, "yyyy-MM-dd"));
		search.setEndDt(Utility.getDate(date, "yyyy-MM-dd"));

		// 키워드 설정
		if (search.getKeyword() != null)
			map.addAttribute("ketword", search.getKeyword());

		Map<String, Object> params = new HashMap<String, Object>();
		try {
			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			map.addAttribute("proBusiTbls", proBusiTbls);
			map.addAttribute("proFlTbls", proFlTbls);
			map.addAttribute("busiPartnerTbls", busiPartnerTbls);
		} catch (Exception e) {
			logger.error("profile find error", e);
		}

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();

		map.addAttribute("contentsTbl", contentsTbl);
		map.addAttribute("search", search);
		map.addAttribute("ctTyp", ctTyp);
		return map;
	}

	@RequestMapping(value = "/popup/RmsProgramSearch.ssc", method = RequestMethod.GET)
	public ModelMap getProgramSearchRMS(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}
		if (search.getChannelCode2() == null) {
			search.setChannelCode2("0");
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		Date today = new Date();
		String tomorrow = sf.format(new Date(today.getTime()+(long)(1000*60*60*-168)));
		String date = sf.format(new Date(today.getTime()));
		search.setStartDt(Utility.getDate(tomorrow, "yyyy-MM-dd"));
		search.setEndDt(Utility.getDate(date, "yyyy-MM-dd"));

		// 키워드 설정
		if (search.getKeyword() != null)
			map.addAttribute("ketword", search.getKeyword());

		Map<String, Object> params = new HashMap<String, Object>();
		try {
			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			map.addAttribute("proBusiTbls", proBusiTbls);
			map.addAttribute("proFlTbls", proFlTbls);
			map.addAttribute("busiPartnerTbls", busiPartnerTbls);
		} catch (Exception e) {
			logger.error("profile find error", e);
		}

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();

		map.addAttribute("contentsTbl", contentsTbl);
		map.addAttribute("search", search);
		map.addAttribute("ctTyp", ctTyp);
		return map;
	}

	@RequestMapping(value = "/popup/MRmsProgramSearch.ssc", method = RequestMethod.GET)
	public ModelMap getProgramSearchMRMS(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}
		if (search.getChannelCode2() == null) {
			search.setChannelCode2("0");
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		Date today = new Date();
		String tomorrow = sf.format(new Date(today.getTime()+(long)(1000*60*60*-168)));
		String date = sf.format(new Date(today.getTime()));
		search.setStartDt(Utility.getDate(tomorrow, "yyyy-MM-dd"));
		search.setEndDt(Utility.getDate(date, "yyyy-MM-dd"));

		// 키워드 설정
		if (search.getKeyword() != null)
			map.addAttribute("ketword", search.getKeyword());

		Map<String, Object> params = new HashMap<String, Object>();
		try {

			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			map.addAttribute("proBusiTbls", proBusiTbls);
			map.addAttribute("proFlTbls", proFlTbls);
			map.addAttribute("busiPartnerTbls", busiPartnerTbls);
		} catch (Exception e) {
			logger.error("profile find error", e);
		}

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();

		map.addAttribute("contentsTbl", contentsTbl);
		map.addAttribute("search", search);
		map.addAttribute("ctTyp", ctTyp);
		return map;
	}

	@RequestMapping(value = "/popup/scd_ProgramSearch.ssc", method = RequestMethod.GET)
	public ModelMap getscdProgramSearch(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}
		if (search.getChannelCode2() == null) {
			search.setChannelCode2("0");
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		Date today = new Date();
		String tomorrow = sf.format(new Date(today.getTime()+(long)(1000*60*60*-168)));
		String date = sf.format(new Date(today.getTime()));
		search.setStartDt(Utility.getDate(tomorrow, "yyyy-MM-dd"));
		search.setEndDt(Utility.getDate(date, "yyyy-MM-dd"));

		// 키워드 설정
		if (search.getKeyword() != null)
			map.addAttribute("ketword", search.getKeyword());

		Map<String, Object> params = new HashMap<String, Object>();
		try {
			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			map.addAttribute("proBusiTbls", proBusiTbls);
			map.addAttribute("proFlTbls", proFlTbls);
			map.addAttribute("busiPartnerTbls", busiPartnerTbls);
		} catch (Exception e) {
			logger.error("profile find error", e);
		}

		List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();

		map.addAttribute("contentsTbl", contentsTbl);
		map.addAttribute("search", search);
		map.addAttribute("ctTyp", ctTyp);
		return map;
	}

	@RequestMapping(value = "/popup/WebRegPopup.ssc")
	public ModelMap WebRegPopup(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "fileNm", required = false) String fileNm,
			@RequestParam(value = "result", required = false) String result,
			@RequestParam(value = "cttyp", required = false) String cttyp,
			@RequestParam(value = "nid" ,required=true) String nid,
			@RequestParam(value = "pcode" ,required=true) String pcode,
			@RequestParam(value = "avGubun" ,required=true) String avGubun,
			@RequestParam(value = "groupPcode" ,required=false) String groupPcode,
			ModelMap map) {
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("7");
		}


		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		params.put("clfCd", "CCLA");
		params2.put("clfCd", "CTYP");
		try {
			List<CodeTbl> clas = codeManagerService.findCode(params);
			List<CodeTbl> typs = codeManagerService.findCode(params2);

			map.addAttribute("clas", clas);
			map.addAttribute("typs", typs);
		} catch (Exception e) {
			logger.error("code값 select 오류", e);
		}


		map.addAttribute("contents", new ContentsTbl());
		map.addAttribute("result", result);

		map.addAttribute("nid", nid);
		map.addAttribute("pcode", pcode);
		map.addAttribute("avGubun",avGubun);
		map.addAttribute("groupPcode",groupPcode);
		map.addAttribute("cttyp",cttyp);

		return map;
	}

	@RequestMapping(value = "/popup/getScode.ssc")
	public ModelAndView getscode(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "pid", required = false) String pid,
			ModelMap map) {

		ModelAndView view = new ModelAndView();

		logger.debug("##############################");
		logger.debug("###### pid: " + pid);
		logger.debug("###### Gubun: " + search.getGubun());
		logger.debug("##############################");


		Map<String, Object> params = new HashMap<String, Object>();
		params.put("program_id_equals", pid);
		params.put("pids","segment_title,segment_id");  // 실제로 받아와야 할 컬럼들.

		String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);

		String programMethod ="";
		if(search.getGubun().equals("video") || search.getGubun() == "video" || search.getGubun().equals("doad") || search.getGubun() == "doad"){
			programMethod = messageSource.getMessage("meta.system.search.segment",null, Locale.KOREA);
		}else{
			programMethod = messageSource.getMessage("meta.system.search.segment.radio",null, Locale.KOREA);
		}
		/**
		 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
		 * param  pgmId;
		 */
		String programXml = "";
		
		/* 테스트할 때 try 전체 주석 */
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("domain+programMethod:"+domain+programMethod);
			}
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params));
			//programXml = Utility.connectHttpPostMethod(domain+programMethod,Utility.convertNameValue(params));
		} catch (Exception e) {
			logger.error("Metahub search error", e);
		}

		
		List<ContentsTbl> scodes = new ArrayList<ContentsTbl>();

		if(StringUtils.isNotBlank(programXml))
			try{
				scodes = mediaToolInterfaceService.forwardSegmentsInfo(programXml);

				view.addObject("scodes", scodes);
				view.setViewName("jsonView");
			}catch (ServiceException e) {
				view.addObject("result", "");
				logger.error(" 스케줄 팝업 세크먼트ID 가져오기 오류 : ", e);
			}
		/* 테스트할 때 주석 제거
		else {
			view.addObject("scodes", scodes);
			view.setViewName("jsonView");
			
			logger.debug("ok");
		}
		*/
		return view;
	}

	@RequestMapping(value = {"/content/saveContentInfo.ssc","/popup/saveContentInfo.ssc"}, method = RequestMethod.POST)
	public ModelAndView updateContent(@ModelAttribute("search") Search search,
			@RequestParam(value = "pgmId", required = false) String pgmId,
			@RequestParam(value = "ctId", required = false) Long ctId,
			@RequestParam(value = "segmentId", required = false) String segmentId,
			@RequestParam(value = "ctCla", required = false) String ctCla,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			@RequestParam(value = "pgmNm", required = false) String pgmNm,
			@RequestParam(value = "brdDd", required = false) Date brdDd,
			@RequestParam(value = "pgmCd", required = false) String pgmCd,
			@RequestParam(value = "pgmGrpCd", required = false) String pgmGrpCd,
			@RequestParam(value = "part", required = false) String part,
			@RequestParam(value = "personInfo", required = false) String personInfo,
			@RequestParam(value = "channelCode", required = false) String channelCode,
			@RequestParam(value = "sSeq", required = false) String sSeq,
			ModelMap map) {

		ContentsTbl content = new ContentsTbl();

		if(channelCode == null || channelCode ==""){

		}else{
			content.setChannelCode(channelCode);
		}
		if(pgmCd == null || pgmCd ==""){

		}else{
			content.setPgmCd(pgmCd);
		}
		if(pgmGrpCd == null || pgmGrpCd ==""){

		}else{
			content.setPgmGrpCd(pgmGrpCd);
		}
		content.setPgmId(pgmId);
		content.setCtId(ctId);
		content.setSegmentId(segmentId);
		content.setCtCla(ctCla);
		content.setCtTyp(ctTyp);
		content.setPart(part);
		content.setPersonInfo(personInfo);
		content.setsSeq(sSeq);

		if(pgmNm != null)
			content.setPgmNm(pgmNm);

		if(brdDd == null){

		}else{
			content.setBrdDd(brdDd);
		}

		ModelAndView view = new ModelAndView();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctId", search.getCtId());
		// params.put("ctiFmt", "1"); // 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
		// ,오디오 원본:'3XX')
		params.put("notCtiFmt", "1");// 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
		// ,오디오 원본:'3XX')

		Map<String, Object> params2 = new HashMap<String, Object>();
		Map<String, Object> params3 = new HashMap<String, Object>();
		params2.put("clfCd", "CCLA");
		params3.put("clfCd", "CTYP");

		Map<String, Object> params5 = new HashMap<String, Object>();
		params5.put("ctId", search.getCtId());
		// params.put("ctiFmt", "1"); // 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
		// ,오디오 원본:'3XX')
		//params5.put("ctiFmt", "1");// 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
		// ,오디오 원본:'3XX')

		String pid="";
		try {
			contentsManagerService.updateContentsRms(content);

			ContentsTbl contentsTbl = contentsManagerService.getContents(params);
			List<ContentsInstTbl> contentsInstTbls = contentsInstManagerService.findContentsInstSummary(params5);
			List<ContentsInstTbl> rcontentsInstTbls = contentsInstManagerService.findContentsRms(params5);

			String regDate= Utility.getDate(contentsTbl.getRegDt(),"yyyy-MM-dd");

			String brdDate="";
			if(contentsTbl.getBrdDd() != null)	
				brdDate= Utility.getDate(contentsTbl.getBrdDd(),"yyyy-MM-dd");

			CodeTbl codeTbl2 = new CodeTbl();
			if(contentsTbl.getCtCla() != null){
				params.put("clfCd", "CCLA");
				params.put("sclCd", contentsTbl.getCtCla());

				codeTbl2 = codeManagerService.getCode(params);
			}

			CodeTbl codeTbl3 = new CodeTbl();
			if(contentsTbl.getCtTyp() != null){
				params.put("clfCd", "CTYP");
				params.put("sclCd", contentsTbl.getCtTyp());

				codeTbl3 = codeManagerService.getCode(params);
			}
			List<CodeTbl> clas = codeManagerService.findCode(params2);
			List<CodeTbl> typs = codeManagerService.findCode(params3);

			pid= contentsTbl.getPgmId();
			view.addObject("contentsTbl", contentsTbl);
			view.addObject("contentsInstTbls", contentsInstTbls);
			view.addObject("rcontentsInstTbls", rcontentsInstTbls);

			view.addObject("codeTbl2", codeTbl2);
			view.addObject("codeTbl3", codeTbl3);
			view.addObject("regDate", regDate);
			view.addObject("brdDate", brdDate);
			view.addObject("clas", clas);
			view.addObject("typs", typs);

			view.addObject("search", search);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" RMS 콘텐츠 정보 수정을 할 수 없음", e);
		}

		if(pid != null){
			Map<String, Object> params4 = new HashMap<String, Object>();
			params4.put("program_id_equals", pid);
			params4.put("pids","segment_title,s_code");  // 실제로 받아와야 할 컬럼들.

			String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
			String programMethod = messageSource.getMessage("meta.system.search.segment",null, Locale.KOREA);

			/**
			 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
			 * param  pgmId;
			 */
			String programXml = "";
			try {
				HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
				programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params4));
				//programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params4));
			} catch (Exception e) {
				logger.error("Metahub search error", e);
			}

			List<ContentsTbl> scodes = new ArrayList<ContentsTbl>();

			if(StringUtils.isNotBlank(programXml))
				try{
					scodes =mediaToolInterfaceService.forwardSegmentsInfo(programXml);

					view.addObject("scodes",scodes);
					view.setViewName("jsonView");
				}catch (ServiceException e) {
					view.addObject("result", "");
					logger.error(" 스케줄 팝업 세크먼트ID 가져오기 오류 : ", e);
				}
		}

		return view;
	}

	@RequestMapping(value = "/content/updateSbContent.ssc", method = RequestMethod.POST)
	public ModelAndView updateSbContent(@ModelAttribute("search") Search search,
			@RequestParam(value = "ctId", required = false) Long ctId,
			@RequestParam(value = "segmentId", required = false) String segmentId,
			@RequestParam(value = "ctCla", required = false) String ctCla,
			@RequestParam(value = "ctTyp", required = false) String ctTyp,
			@RequestParam(value = "trimmSte", required = false) String trimmSte,
			ModelMap map) {

		ContentsTbl content = new ContentsTbl();

		content.setCtId(ctId);
		content.setTrimmSte(StringUtils.defaultIfEmpty(trimmSte, "N"));
		content.setCtCla(ctCla);
		content.setCtTyp(ctTyp);
		content.setSegmentId(segmentId);

		ModelAndView view = new ModelAndView();
		try {
			contentsManagerService.updateContentsRms(content);

			view.addObject("search", search);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 대기 콘텐츠 정보 수정을 할 수 없음", e);
		}

		return view;
	}
	@RequestMapping(value = {"/content/saveContentPersoninfo.ssc","/popup/saveContentPersoninfo.ssc"}, method = RequestMethod.POST)
	public ModelAndView updateContentPersoninfo(@ModelAttribute("search") Search search,
			@RequestParam(value = "personInfo3", required = false) String personInfo,
			@RequestParam(value = "sSeq", required = false) String sSeq,
			@RequestParam(value = "ctId", required = false) Long ctId,
			ModelMap map) {

		ContentsTbl content = new ContentsTbl();

		content.setPersonInfo(personInfo);
		content.setsSeq(sSeq);
		content.setCtId(ctId);
		ModelAndView view = new ModelAndView();
		try {
			contentsManagerService.updateContentsRms(content);


			view.addObject("search", search);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" RMS 콘텐츠 정보 수정을 할 수 없음", e);
		}

		return view;
	}


	@RequestMapping(value = {"/content/reTrancoder.ssc","/popup/reTrancoder.ssc"}, method = RequestMethod.POST)
	public ModelAndView insertTrancoder(@ModelAttribute("search") Search search,
			@RequestParam(value = "ctId", required = false) Long ctId,
			@RequestParam(value = "ctiId", required = false) Long ctiId,
			@RequestParam(value = "pgmCd", required = false) String pgmCd,
			@ModelAttribute("contentsTbl") ContentsTbl contentsTbl,
			@ModelAttribute("contentsInstTbl") ContentsInstTbl contentsInstTbl,
			ModelMap map) {



		Map<String, Object> params = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();

		params.put("pgmCd", contentsTbl.getPgmCd());
		params.put("ctiId", contentsInstTbl.getCtiId());
		params.put("ctId", contentsTbl.getCtId());

		map.addAttribute("menuId", 13);
		try {

			ContentsTbl contents=new ContentsTbl();
			contents.setCtId(ctId);
			contents.setTrimmSte("Y");

			contentsManagerService.updateContentsRms(contents);

			ContentsInstTbl contentsInstTbls = new ContentsInstTbl();
			contentsInstTbls = contentsInstManagerService.getContentsInst(params);
			List<ProFlTbl> proFlTbls = new ArrayList<ProFlTbl>();

			/**
			 * 프로파일 리스트를 가지고 오는 부분
			 * RMS에게 적용되는 프로파일/사업자 리스트를 적용을 일반적인 워크플로우와 구별하자면
			 * 테이블을 하나 추가(BUSI_PARTNER_PGM:구조 동일)해서 RMS에 대한 프로그램 정보를 따로 정리하는 방법이 현실적인 방법.
			 * 
			 */
			params.put("ctTyp", contentsTbl.getCtTyp());
			proFlTbls = proBusiManagerService.findPgmProBusi(params);


			List<TranscorderHisTbl> traHisTbls = new ArrayList<TranscorderHisTbl>();
			for (ProFlTbl proFlTbl : proFlTbls) {

				TranscorderHisTbl traHisTbl = new TranscorderHisTbl();

				traHisTbl.setCtiId(contentsInstTbls.getCtiId());
				traHisTbl.setProFlid(Integer.parseInt(proFlTbl.getProFlid()));
				traHisTbl.setFlExt(proFlTbl.getExt());
				traHisTbl.setUseYn("Y");
				traHisTbl.setWorkStatcd("000");

				traHisTbl.setJobGubun(contentsInstTbls.getAvGubun()); // Video : V , Audio : A

				traHisTbl.setRegDt(Utility.getTimestamp());

				traHisTbls.add(traHisTbl);
			}

			workflowManagerService.insertTranscorderHis(traHisTbls);

			view.addObject("search", search);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 프로파일 수정을 할 수 없음", e);
		}

		return view;
	}

	@RequestMapping(value = "/content/insertTrancoder.ssc", method = RequestMethod.POST)
	public ModelAndView insertTran(@ModelAttribute("search") Search search,
			@RequestParam(value = "pgmCd", required = false) String pgmCd,
			@ModelAttribute("contentsTbl") ContentsTbl contentsTbl,
			@ModelAttribute("contentsInstTbl") ContentsInstTbl contentsInstTbl,
			ModelMap map) {



		Map<String, Object> params = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();

		params.put("pgmCd", contentsTbl.getPgmCd());
		params.put("ctiId", contentsInstTbl.getCtiId());
		params.put("ctId", contentsTbl.getCtId());

		if(logger.isDebugEnabled()) {
			logger.debug("getPgmCd >>>>>>>>>>>>"+contentsTbl.getPgmCd());
			logger.debug("getCtiId >>>>>>>>>>>>"+contentsInstTbl.getCtiId());
			logger.debug("getCtId  >>>>>>>>>>>>"+contentsTbl.getCtId());
			logger.debug("getCtTyp >>>>>>>>>>>>"+contentsTbl.getCtTyp());
		}
		map.addAttribute("menuId", 13);
		try {

			ContentsInstTbl contentsInstTbls = new ContentsInstTbl();
			contentsInstTbls = contentsInstManagerService.getContentsInst(params);
			List<ProFlTbl> proFlTbls = new ArrayList<ProFlTbl>();
			/**
			 * 프로파일 리스트를 가지고 오는 부분
			 * RMS에게 적용되는 프로파일/사업자 리스트를 적용을 일반적인 워크플로우와 구별하자면
			 * 테이블을 하나 추가(BUSI_PARTNER_PGM:구조 동일)해서 RMS에 대한 프로그램 정보를 따로 정리하는 방법이 현실적인 방법.
			 * 
			 */

			params.put("ctTyp", contentsTbl.getCtTyp());
			proFlTbls = proBusiManagerService.findPgmProBusi(params);


			List<TranscorderHisTbl> traHisTbls = new ArrayList<TranscorderHisTbl>();
			for (ProFlTbl proFlTbl : proFlTbls) {

				TranscorderHisTbl traHisTbl = new TranscorderHisTbl();

				traHisTbl.setCtiId(contentsInstTbls.getCtiId());
				traHisTbl.setProFlid(Integer.parseInt(proFlTbl.getProFlid()));
				traHisTbl.setFlExt(proFlTbl.getExt());
				traHisTbl.setUseYn("Y");
				traHisTbl.setWorkStatcd("000");

				traHisTbl.setJobGubun(contentsInstTbls.getAvGubun()); // Video : V , Audio : A

				traHisTbl.setRegDt(Utility.getTimestamp());

				traHisTbls.add(traHisTbl);
			}

			workflowManagerService.insertTranscorderHis(traHisTbls);

			view.addObject("search", search);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 프로파일 수정을 할 수 없음", e);
		}

		return view;
	}
	@RequestMapping(value = "/popup/RmsContentList.ssc")
	public ModelMap rmsContentList(@ModelAttribute("search") Search search,

			@RequestParam(value = "cttyp", required = false) String cttyp,
			@RequestParam(value = "nid" ,required=true) String nid,
			@RequestParam(value = "pcode" ,required=true) String pcode,
			//@RequestParam(value = "avGubun" ,required=true) String avGubun,
			@RequestParam(value = "groupPcode" ,required=false) String groupPcode,
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("7");
		}

		search.setNid(nid);
		//search.setPgmCd(pgmCd);
		logger.debug("####search.getStartDt():" + search.getStartDt());
		logger.debug("####search.getEndDt():" + search.getEndDt());
		logger.debug("####search.getChannelCode():" + search.getChannelCode());
		logger.debug("####search.getKeyword():" + search.getKeyword());
		logger.debug("####search.getPageNo():" + search.getPageNo());

		// 기간검색 설정
		if (search.getStartDt() != null)
			map.addAttribute("startDt",
					Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		if (search.getEndDt() != null)
			map.addAttribute("endDt", 
					Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));

		// 키워드 설정
		if (search.getKeyword() != null || search.getKeyword() != "") 
			map.addAttribute("keyword", search.getKeyword());


		try {
			PaginationSupport<ContentsTbl> contents = contentsManagerService
					.findRmscontentsList(search);
			map.addAttribute("contents", contents);

			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>"+contents.getTotalCount());
		} catch (Exception e) {
			logger.error("콘텐츠 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		map.addAttribute("nid", nid);
		map.addAttribute("pcode", pcode);
		map.addAttribute("cttyp", cttyp);
		//map.addAttribute("avGubun", avGubun);
		map.addAttribute("groupPcode", groupPcode);

		return map;
	}

	@RequestMapping(value = "/popup/RmsContentUd.ssc")
	public ModelMap rmsContentUd(@ModelAttribute("search") Search search,
			@RequestParam(value = "nid", required = true) String nid,
			@RequestParam(value = "pcode", required = true) String pcode,
			@RequestParam(value = "ctId", required = false, defaultValue="0" ) String ctId,
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("7");
		}
		search.setNid(nid);
		logger.debug("####search.getStartDt():" + search.getStartDt());
		logger.debug("####search.getEndDt():" + search.getEndDt());
		logger.debug("####search.getChannelCode():" + search.getChannelCode());
		logger.debug("####search.getKeyword():" + search.getKeyword());
		logger.debug("####search.getPageNo():" + search.getPageNo());
		logger.debug("####search.getNId():" + search.getNid());
		// 기간검색 설정
		if (search.getStartDt() != null)
			map.addAttribute("startDt",
					Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		if (search.getEndDt() != null)
			map.addAttribute("endDt", 
					Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));

		// 키워드 설정
		if (search.getKeyword() != null || search.getKeyword() != "") 
			map.addAttribute("keyword", search.getKeyword());

		Map<String, Object> params2 = new HashMap<String, Object>();
		Map<String, Object> params3 = new HashMap<String, Object>();
		params2.put("clfCd", "CCLA");
		params3.put("clfCd", "CTYP");

		try {
			PaginationSupport<ContentsTbl> contents = contentsManagerService
					.findRmscontentsUd(search);

			List<CodeTbl> clas = codeManagerService.findCode(params2);
			List<CodeTbl> typs = codeManagerService.findCode(params3);

			map.addAttribute("clas", clas);
			map.addAttribute("typs", typs);
			map.addAttribute("contents", contents);
		} catch (Exception e) {
			logger.error("콘텐츠 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		map.addAttribute("nid", nid);
		map.addAttribute("pcode", pcode);
		map.addAttribute("ctId", ctId);


		return map;
	}



	@RequestMapping(value = {"/content/updateContentRms.ssc","/popup/updateContentRms.ssc"}, method = RequestMethod.POST)
	public ModelAndView updateContentRms(@ModelAttribute("search") Search search,
			@RequestParam(value = "ctIds", required = false) String check,
			@RequestParam(value = "gubun", required = false) String gubun,
			@RequestParam(value = "sSeq", required = false) String sSeq,
			ModelMap map) {

		String checks[] = check.split(",");
		ModelAndView view = new ModelAndView();
		//search.setChannelCode("");


		try {
			for (int i = 0; i < checks.length; i++) {

				ContentsTbl content = new ContentsTbl();
				if(gubun.equals("ctCla")){
					content.setCtCla(search.getCtCla2());
				}
				if(gubun.equals("ctTyp")){
					content.setCtTyp(search.getCtTyp2());
				}
				if(gubun.equals("part")){
					content.setPart(search.getPart2());
				}
				if(gubun.equals("personInfo")){
					content.setPersonInfo(search.getPersonInfo2());
					content.setsSeq(sSeq);
				}
				if(gubun.equals("brdDd")){
					content.setBrdDd(search.getBrdDd2());
				}
				content.setCtId(Long.parseLong(checks[i]));

				contentsManagerService.updateContentsRms(content);
			}

			view.addObject("search", search);
			view.addObject("checks", checks);

			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" RMS 콘텐츠 정보 수정을 할 수 없음", e);
		}

		return view;
	}

}