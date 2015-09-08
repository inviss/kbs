package kr.co.d2net.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.BusiPartnerPgm;
import kr.co.d2net.dto.BusiPartnerTbl;
import kr.co.d2net.dto.CodeTbl;
import kr.co.d2net.dto.DairyOrderTbl;
import kr.co.d2net.dto.LiveTbl;
import kr.co.d2net.dto.ProBusiTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.UserTbl;
import kr.co.d2net.dto.WeekSchTbl;
import kr.co.d2net.service.BusiPartnerManagerService;
import kr.co.d2net.service.BusiPartnerPgmManagerService;
import kr.co.d2net.service.CodeManagerService;
import kr.co.d2net.service.DairyOrderManagerService;
import kr.co.d2net.service.LiveManagerService;
import kr.co.d2net.service.ProBusiManagerService;
import kr.co.d2net.service.ProFlManagerService;
import kr.co.d2net.service.WeekSchManagerService;
import kr.co.d2net.task.diagram.WeekDiagramRequestJob;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

@Controller
public class ScheduleManageControl {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private WeekSchManagerService weekSchManagerService;
	@Autowired
	private DairyOrderManagerService dairyOrdermanagerService;
	@Autowired
	private LiveManagerService liveManagerService;
	@Autowired
	private ProBusiManagerService proBusiManagerService;
	@Autowired
	private ProFlManagerService proFlManagerService;
	@Autowired
	private BusiPartnerManagerService busiPartnerManagerService;
	@Autowired
	private BusiPartnerPgmManagerService busiPartnerPgmManagerService;
	@Autowired
	private CodeManagerService codeManagerService;

	@RequestMapping(value = "/schedule/ThisWeek.ssc", method = RequestMethod.GET)
	public ModelMap findWeekList(@ModelAttribute("search") Search search,
			ModelMap map) {
		// 페이지 설정

		logger.debug("################################");
		logger.debug("## tabGubun:" + search.getTabGubun());
		logger.debug("## menuId:" + search.getMenuId());
		logger.debug("## channelCode:" + search.getChannelCode());
		logger.debug("## localCode:" + search.getLocalCode());
		logger.debug("################################");


		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}
		if (StringUtils.isBlank(search.getMenuId())) {
			search.setMenuId("3"); // 로그인 에서 리다이렉트 처리
		}
		if (StringUtils.isBlank(search.getTabGubun())) {
			search.setTabGubun("1"); // 로그인 에서 리다이렉트 처리
		}
		Map<String, Object> params = new HashMap<String, Object>();

		if (StringUtils.isBlank(search.getChannelCode())) {
			params.put("channelCode", "11"); // 1TV:11 , 2TV:12
			search.setChannelCode("11");
		} else {
			params.put("channelCode", search.getChannelCode());
		}

		if (StringUtils.isBlank(search.getLocalCode())) {
			params.put("localCode", "00"); 
			search.setLocalCode("00");
		} else {
			params.put("localCode", search.getLocalCode());

		}

		String tabGubun = StringUtils.defaultIfEmpty(search.getTabGubun(), "1");
		params.put("tabGubun", tabGubun); // 1: 이번주 , 2: 다음주, 3:오늘, 4:내일

		String dayBetween = Utility.getDayBetween((Integer.valueOf(tabGubun) -1) * 7, "yyyyMMdd");
		if(StringUtils.isNotBlank(dayBetween) && dayBetween.indexOf(",") > -1) {
			String[] days = dayBetween.split("\\,");

			logger.debug("startDate: " +days[0]+", endDate: "+days[1]);

			params.put("sdate", days[0]);
			params.put("edate", days[1]);
		}
		/*
		if (params.get("tabGubun").equals("1")) {
			params.put("sdate", Utility.getDateOfWeek(2, -1, "yyyyMMdd"));
			params.put("edate", Utility.getDateOfWeek(8, -1, "yyyyMMdd"));

		} else {
			params.put("sdate", Utility.getDateOfWeek(2, 0, "yyyyMMdd"));
			params.put("edate", Utility.getDateOfWeek(8, 0, "yyyyMMdd"));
		}
		 */
		map.addAttribute("search", search);

		try {

			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<WeekSchTbl> contents = weekSchManagerService.findWeekSchTbl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			params.put("clfCd", "DOAD");
			params.put("useYn", "Y");
			List<CodeTbl> codes = codeManagerService.findCode(params);

			if(logger.isDebugEnabled()) {
				logger.debug("proBusiTbls size :" + proBusiTbls.size());
				logger.debug("proFlTbls size :" + proFlTbls.size());
				logger.debug("busiPartnerTbls size :" + busiPartnerTbls.size());
				logger.debug("codes size :" + codes.size());
			}

			map.addAttribute("proBusiTbls", proBusiTbls);
			map.addAttribute("proFlTbls", proFlTbls);
			map.addAttribute("contents", contents);
			map.addAttribute("busiPartnerTbls", busiPartnerTbls);
			map.addAttribute("doads", codes);

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			logger.error(" - 주간편성표를 조회할 수 없음.", e);
		}
		return map;
	}

	@RequestMapping(value = "/schedule/Today.ssc", method = RequestMethod.GET)
	public ModelMap findTodayList(@ModelAttribute("search") Search search,
			ModelMap map) {
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}
		Map<String, Object> params = new HashMap<String, Object>();

		logger.debug("################################");
		logger.debug("## tabGubun:" + search.getTabGubun());
		logger.debug("## menuId:" + search.getMenuId());
		logger.debug("## channelCode:" + search.getChannelCode());

		logger.debug("################################");

		if (StringUtils.isBlank(search.getChannelCode())) {
			params.put("channelCode", "11"); // 1TV:11 , 2TV:12
			search.setChannelCode("11");
		} else {
			params.put("channelCode", search.getChannelCode());
		}


		if (StringUtils.isBlank(search.getTabGubun())) {
			params.put("tabGubun", "3"); // 1: 이번주 , 2: 다음주, 3:오늘, 4:내일
			search.setTabGubun("3");
		} else {
			params.put("tabGubun", search.getTabGubun());
		}

		if (params.get("tabGubun").equals("3")) {
			params.put("sdate", Utility.getDate(new Date(), "yyyyMMdd", 0));
			params.put("edate", Utility.getDate(new Date(), "yyyyMMdd", 0));
		} else {
			params.put("sdate", Utility.getDate(new Date(), "yyyyMMdd", 1));
			params.put("edate", Utility.getDate(new Date(), "yyyyMMdd", 1));
		}

		params.put("localCode", "00");
		map.addAttribute("search", search);
		try {

			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			List<WeekSchTbl> contents = weekSchManagerService.findWeekSchTbl(params);
			List<DairyOrderTbl> dairyOrderLists = dairyOrdermanagerService.findDairyOrderService(params);

			logger.debug("proBusiTbls size :" + proBusiTbls.size());
			logger.debug("proFlTbls size :" + proFlTbls.size());
			logger.debug("busiPartnerTbls size :" + busiPartnerTbls.size());

			map.addAttribute("proBusiTbls", proBusiTbls);
			map.addAttribute("proFlTbls", proFlTbls);
			map.addAttribute("contents", contents);
			map.addAttribute("busiPartnerTbls", busiPartnerTbls);
			map.addAttribute("dairyOrderLists", dairyOrderLists);

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			logger.error(" - 일일편성표를 조회할 수 없음.", e);
		}
		return map;
	}

	@RequestMapping(value = "/schedule/findLive.ssc", method = RequestMethod.POST)
	public ModelAndView findLIveList(
			@ModelAttribute("search") Search search,
			@RequestParam(value = "programCode", required = false) String programCode,
			@RequestParam(value = "rerunCode", required = false) String rerunCode,
			@RequestParam(value = "audioModeMain", required = false) String audioModeMain,
			ModelMap map) {
		
		System.out.println("audioModeMain====>"+audioModeMain);
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}
		Map<String, Object> params = new HashMap<String, Object>();

		ModelAndView view = new ModelAndView();
		try {

			params.put("programCode", programCode);
			params.put("rerunCode", rerunCode);

			// Live 전용 사업자 정보를 조회하므로 '본방'으로 fix
			// 2012-04-23
			params.put("ctTyp", "00");

			logger.debug("####################################");
			logger.debug("###### programCode: " + programCode);
			logger.debug("###### rerunCode: " + rerunCode);
			logger.debug("####################################");

			LiveTbl liveTbl = liveManagerService.getLiveTbl(params);

			List<BusiPartnerPgm> busiPartnerPgm = busiPartnerPgmManagerService.findBusiPartnerPgm(params);

			// logger.debug("liveTbl recyn :" + liveTbl.getRecyn());
			// logger.debug("busiPartnerPgm size:" + busiPartnerPgm.size());

			view.addObject("liveTbl", liveTbl);
			view.addObject("busiPartnerPgm", busiPartnerPgm);

			view.setViewName("jsonView");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			logger.error(" - 일일편성표를 조회할 수 없음.", e);
		}
		return view;
	}

	@RequestMapping(value = "/schedule/updateLiveInfo.ssc", method = RequestMethod.POST)
	public ModelAndView updateLiveInfo(
			HttpServletRequest request,
			@ModelAttribute("search") Search search,
			@RequestParam(value = "programCode", required = false) String programCode,
			@RequestParam(value = "rerunCode", required = false) String rerunCode,
			@RequestParam(value = "busiPartnerid", required = false) String busiPartnerid,
			@RequestParam(value = "audioModeMain", required = false) String audioModeMain,
			@RequestParam(value = "recyn", required = false) String recyn,
			ModelMap map) {

		ModelAndView view = new ModelAndView();
		view.setViewName("jsonView");
		LiveTbl live = new LiveTbl();

		logger.debug("##############################");
		logger.debug("###### programCode: " + programCode);
		logger.debug("###### rerunCode: " + rerunCode);
		logger.debug("###### recyn: " + recyn);
		logger.debug("###### busiPartnerid: " + busiPartnerid);
		logger.debug("###### audioModeMain: " + audioModeMain);
		logger.debug("##############################");

		// 녹화정보 수정과 관련한 내용 입력부
		// 시작
		live.setProgramCode(programCode);
		// live.setBgnTime(Utility.getDate(bgnTime, "yyyyMMdd"));
		live.setBgnTime(Utility.getTimestamp());
		live.setRecyn(recyn);
		// live.setEndTime(Utility.getDate(endTime, "yyyyMMdd"));
		live.setEndTime(Utility.getTimestamp());
		live.setRerunCode(rerunCode);
		// 끝
		String[] iBusiPartnerid = null;
		if (!StringUtils.isEmpty(busiPartnerid))
			iBusiPartnerid = busiPartnerid.split(",");

		try {
			liveManagerService.updateLiveTbl(live);
			BusiPartnerPgm bpp = new BusiPartnerPgm();
			bpp.setProgramCode(programCode);
			bpp.setCtTyp("00");
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
					busipartnerPgm.setBusiPartnerid(iBusiPartnerid[i]);
					busipartnerPgm.setCtTyp("00");
					busipartnerPgm.setAudioModeCode(audioModeMain);
					busipartnerPgm.setRegrid(user.getUserId());
					busipartnerPgm.setRegDt(Utility.getTimestamp());
					busiPartnerPgmManagerService.insertBusiPartnerPgm(busipartnerPgm);
				}
			}
			view.addObject("programCode", programCode);
			view.addObject("rerunCode", rerunCode);
			view.addObject("recyn", recyn);
			view.addObject("result", "Y");

		} catch (Exception e) {
			view.addObject("result", "");
			logger.error(" 녹화정보를 수정할 수 없음", e);
		}
		return view;
	}

	@RequestMapping(value = "/schedule/ajaxToday.ssc", method = RequestMethod.POST)
	public ModelAndView findTodayInfo(@ModelAttribute("search") Search search,
			ModelMap map) {
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();

		logger.debug("################################");
		logger.debug("## tabGubun:" + search.getTabGubun());
		logger.debug("## menuId:" + search.getMenuId());
		logger.debug("## channelCode:" + search.getChannelCode());
		logger.debug("################################");

		if (StringUtils.isBlank(search.getChannelCode())) {
			params.put("channelCode", "11"); // 1TV:11 , 2TV:12
		} else {
			params.put("channelCode", search.getChannelCode());
		}

		if (StringUtils.isBlank(search.getTabGubun())) {
			params.put("tabGubun", "1"); // 1: 이번주 , 2: 다음주, 3:오늘, 4:내일
		} else {
			params.put("tabGubun", search.getTabGubun());
		}
		if (params.get("tabGubun").equals("3")) {
			params.put("sdate", Utility.getDate(new Date(), "yyyyMMdd", -7));
			params.put("edate", Utility.getDate(new Date(), "yyyyMMdd", -7));
		} else {
			params.put("sdate", Utility.getDate(1).replace("-", ""));
			params.put("edate", Utility.getDate(1).replace("-", ""));
		}
		params.put("localCode", "00");
		map.addAttribute("channelCode", params.get("channelCode"));
		map.addAttribute("tabGubun", params.get("tabGubun"));
		map.addAttribute("search", search);

		try {

			List<ProBusiTbl> proBusiTbls = proBusiManagerService.findProBusi(params);
			List<ProFlTbl> proFlTbls = proFlManagerService.findProFl(params);
			List<BusiPartnerTbl> busiPartnerTbls = busiPartnerManagerService.findBusiPartner(params);

			List<WeekSchTbl> contents = weekSchManagerService.findWeekSchTbl(params);
			List<DairyOrderTbl> dairyOrderLists = dairyOrdermanagerService.findDairyOrderService(params);

			logger.debug("proBusiTbls size :" + proBusiTbls.size());
			logger.debug("proFlTbls size :" + proFlTbls.size());
			logger.debug("busiPartnerTbls size :" + busiPartnerTbls.size());

			view.addObject("proBusiTbls", proBusiTbls);
			view.addObject("proFlTbls", proFlTbls);
			view.addObject("contents", contents);
			view.addObject("busiPartnerTbls", busiPartnerTbls);
			view.addObject("dairyOrderLists", dairyOrderLists);

			view.addObject("search", search);

			view.setViewName("jsonView");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			logger.error(" - 일일편성표를 조회할 수 없음.", e);
		}
		return view;
	}

	@RequestMapping(value = "/schedule/ThisWeekInterlock.ssc", method = RequestMethod.POST)
	public ModelAndView thisWeekInterlock(@ModelAttribute("search") Search search,
			@RequestParam(value = "lCode", required = false) String lCode,
			ModelMap map) {
		if(logger.isInfoEnabled()) {
			logger.info("==================================");
			logger.info("주간편성 thisWeekInterlock START");
			logger.info("==================================");
		}

		ModelAndView view = new ModelAndView();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("date_morethan", Utility.getDateOfWeek(1, -1, "yyyyMMdd")); // 금주 일요일
		params.put("date_lessthan", Utility.getDateOfWeek(7, 0, "yyyyMMdd")); // 다음주 월요일
		params.put("channel_code", search.getChannelCode()); 
		params.put("local_code", lCode); 

		if(logger.isDebugEnabled()) {
			logger.debug("start date ===>"+params.get("date_morethan"));
			logger.debug("end date ===>"+params.get("date_lessthan"));
		}

		try {
			weekSchManagerService.connectToMetaHubRestFul_manualWeekly(params);

			WeekDiagramRequestJob.confirmDate.put(search.getChannelCode()+lCode, Utility.getTimestamp("yyyyMMddHHmmss"));

			view.addObject("result", "Y");
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("thisWeekInterlock error", e);
			view.addObject("result", "N");
		}


		return view;
	}

	@RequestMapping(value = "/schedule/TodayInterlock.ssc", method = RequestMethod.POST)
	public ModelAndView todayInterlock(@ModelAttribute("search") Search search, ModelMap map) {
		if(logger.isInfoEnabled()) {
			logger.debug("==================================");
			logger.debug("일일운행 즉시갱신 START");
			logger.debug("==================================");
		}

		ModelAndView view = new ModelAndView();

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("running_date_equals", Utility.getTimestamp("yyyyMMdd")); // 금일
			params.put("broadcast_event_kind_equals", "프로그램"); // broadcast_event_kind.
			params.put("channel_code_equals", search.getChannelCode()); // 채널

			dairyOrdermanagerService.connectToMetaHubRestFul_dairy(params);

			view.addObject("result", "Y");
			view.setViewName("jsonView");

		} catch (Exception e) {
			logger.error("todayInterlock error", e);
			view.addObject("result", "N");
		}

		return view;
	}
}
