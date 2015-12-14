package kr.co.d2net.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.EquipmentTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.QcReportTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.TranscorderHisTbl;
import kr.co.d2net.dto.TransferHisTbl;
import kr.co.d2net.dto.xml.meta.Nodes;
import kr.co.d2net.service.CodeManagerService;
import kr.co.d2net.service.ContentsInstManagerService;
import kr.co.d2net.service.HttpRequestService;
import kr.co.d2net.service.HttpRequestServiceImpl;
import kr.co.d2net.service.ProFlManagerService;
import kr.co.d2net.service.QcReportManagerService;
import kr.co.d2net.service.WorkflowManagerService;
import kr.co.d2net.task.diagram.StorageCheckControlWorker;

import org.apache.commons.lang.StringUtils;
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

@Controller
public class WorkflowManageControl {

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private WorkflowManagerService workflowManagerService;
	@Autowired
	private QcReportManagerService qcreportManagerService;
	@Autowired
	private ContentsInstManagerService contentsInstManagerService;
	@Autowired
	private CodeManagerService codeManagerService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ProFlManagerService proFlManagerService;

	final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/workflow/Work.ssc", method = RequestMethod.GET)
	public ModelMap findTranscorderHisList(
			@ModelAttribute("search") Search search, 
			ModelMap map) {
		//페이지 검색초기값 설정
		search.setChannelCode("11");

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		Date today = new Date();
		String fromDay = sf.format(new Date(today.getTime()-(long)(1000*60*60*24*3)));
		String tDay = sf.format(new Date(today.getTime()));

		search.setStartDt(Utility.getDate(fromDay, "yyyy-MM-dd"));
		search.setEndDt(Utility.getDate(tDay, "yyyy-MM-dd"));

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		//기간설정
		if (search.getStartDt() != null){
			map.addAttribute("startDt",
					Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		}else{
			search.setStartDt(null);
		}

		if (search.getEndDt() != null){
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		}else{
			search.setEndDt(null);
		}

		// 키워드 설정
		if (search.getKeyword() != null){ 
			map.addAttribute("keyword", search.getKeyword());
		}

		if (search.getWorkStat() != null){ 
			map.addAttribute("workStat", search.getWorkStat());
		}else{
			search.setWorkStat("111");
		}

		if (search.getChannelCode() != null){ 
			map.addAttribute("channelCode", search.getChannelCode());	
		}else{
			search.setChannelCode("0");
		}


		if(search.getSearchDayName() != null){
			map.addAttribute("searchDayName", search.getSearchDayName());	
		}else{
			search.setSearchDayName("tra.REG_DT");
		}
		// 기간검색 설정 skipseq
		// if(search.getStartDt() != null)
		// map.addAttribute("startDt", Utility.getDate(search.getStartDt(),
		// "yyyy.MM.dd"));
		// if(search.getEndDt() != null)
		// map.addAttribute("endDt", Utility.getDate(search.getEndDt(),
		// "yyyy.MM.dd"));

		try {


			PaginationSupport<TranscorderHisTbl> contents = workflowManagerService
			.findTranscorderHis(search);
			map.addAttribute("contents", contents);
		} catch (Exception e) {
			logger.error("트랜스코더 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		return map;
	}

	@RequestMapping(value = "/workflow/Work.ssc", method = RequestMethod.POST)
	public ModelMap searchTranscorderHisList(
			@ModelAttribute("search") Search search, 
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		if (search.getStartDt() != null){
			map.addAttribute("startDt",
					Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		}else{
			search.setStartDt(null);
		}
		if (search.getEndDt() != null){
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		}else{
			search.setEndDt(null);
		}

		// 키워드 설정
		if (search.getKeyword() != null){ 
			map.addAttribute("keyword", search.getKeyword());
		}

		if (search.getWorkStat() != null){ 
			map.addAttribute("workStat", search.getWorkStat());
		}else{
			search.setWorkStat("111");
		}

		if (search.getChannelCode() != null){ 
			map.addAttribute("channelCode", search.getChannelCode());	
		}else{
			search.setChannelCode("0");
		}

		if(search.getSearchDayName() != null){
			map.addAttribute("searchDayName", search.getSearchDayName());	
		}else{
			search.setSearchDayName("tra.REG_DT");
		}
		// 기간검색 설정 skipseq
		// if(search.getStartDt() != null)
		// map.addAttribute("startDt", Utility.getDate(search.getStartDt(),
		// "yyyy.MM.dd"));
		// if(search.getEndDt() != null)
		// map.addAttribute("endDt", Utility.getDate(search.getEndDt(),
		// "yyyy.MM.dd"));

		try {


			PaginationSupport<TranscorderHisTbl> contents = workflowManagerService
			.findTranscorderHis(search);
			map.addAttribute("contents", contents);
		} catch (Exception e) {
			logger.error("트랜스코더 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		return map;
	}

	@RequestMapping(value = {"/workflow/ajaxProfileAllInitialize.ssc"}, method = RequestMethod.POST)
	public ModelAndView ajaxProfileAllInitialize (
			@ModelAttribute("search") Search search,
			ModelMap map) {

		ModelAndView view = new ModelAndView();

		TranscorderHisTbl trsHisTbl = new TranscorderHisTbl();
		try {
			trsHisTbl.setCtiId(search.getCtiId());
			trsHisTbl.setWorkStrDt(null);
			trsHisTbl.setWorkEndDt(null);
			trsHisTbl.setWorkStatcd("000");
			trsHisTbl.setPrgrs("0");
			workflowManagerService.updateProflRequest(trsHisTbl);
			
			
		} catch (Exception e) {
			logger.error("ajaxProfileAllInitialize", e);
			view.addObject("msg", "N");
		}
		
		view.addObject("msg", "Y");
		view.setViewName("jsonView");

		return view;
	}

	@RequestMapping(value = {"/workflow/ajaxWork.ssc","/ajax/workflow/ajaxWork.ssc"}, method = RequestMethod.POST)
	public ModelAndView ajaxFindTranscorderHisList(
			@ModelAttribute("search") Search search,
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		if (search.getStartDt() != null){
			map.addAttribute("startDt",
					Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		}else{
			search.setStartDt(null);
		}
		if (search.getEndDt() != null){
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		}else{
			search.setEndDt(null);
		}

		// 키워드 설정
		if (search.getKeyword() != null){ 
			map.addAttribute("keyword", search.getKeyword());
		}

		if (search.getWorkStat() != null){ 
			map.addAttribute("workStat", search.getWorkStat());
		}else{
			search.setWorkStat("111");
		}

		if (search.getChannelCode() != null){ 
			map.addAttribute("channelCode", search.getChannelCode());	
		}else{
			search.setChannelCode("0");
		}

		if(search.getSearchDayName() != null){
			map.addAttribute("searchDayName", search.getSearchDayName());	
		}else{
			search.setSearchDayName("tra.REG_DT");
		}

		Map<String, Object> params = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();
		try {

			PaginationSupport<TranscorderHisTbl> contents = workflowManagerService.findTranscorderHis(search);

			view.addObject("contents", contents.getItems());
			view.addObject("search", search);

			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("트랜스코더 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		return view;
	}

	@RequestMapping(value = {"/workflow/ajaxWorkPrgrs.ssc"}, method = RequestMethod.POST)
	public ModelAndView ajaxFindTranscorderPrgrs(
			@RequestParam(value = "traSeq", required = false) String traSeq,
			ModelMap map) {



		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seq2", traSeq);
		ModelAndView view = new ModelAndView();
		try {

			List<TranscorderHisTbl> contents = workflowManagerService.getTraHisPrgrs(params);

			view.addObject("contents", contents);
			//view.addObject("search", search);

			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("트랜스코더 prgrs 조회 에러", e);
		}

		//map.addAttribute("search", search);
		return view;
	}

	@RequestMapping(value = {"/workflow/ajaxTransmissionPrgrs.ssc"}, method = RequestMethod.POST)
	public ModelAndView ajaxTransmissionPrgrs(
			@RequestParam(value = "trsSeq", required = false) String trsSeq,
			ModelMap map) {



		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seq2", trsSeq);
		ModelAndView view = new ModelAndView();
		try {

			List<TransferHisTbl> contents = workflowManagerService.getTrsHisPrgrs(params);

			view.addObject("contents", contents);
			//view.addObject("search", search);

			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("트랜스퍼 prgrs 조회 에러", e);
		}

		//map.addAttribute("search", search);
		return view;
	}

	@RequestMapping(value = {"/workflow/ajaxQcReport.ssc"}, method = RequestMethod.GET)
	public ModelAndView ajaxQcReport(
			@RequestParam(value = "seq", required = false) String seq,
			ModelMap map) {

		ModelAndView view = new ModelAndView();
		try {
			if(StringUtils.isNotBlank(seq)) {
				Integer qcCount = workflowManagerService.getQcCount(Long.valueOf(seq));
				logger.debug("qcCount: "+qcCount);
				view.addObject("qcCount", qcCount);
			} else {
				view.addObject("qcCount", 0);
			}
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("트랜스코더 목록 조회 에러", e);
		}

		return view;
	}

	@RequestMapping(value = "/workflow/Transmission.ssc", method = RequestMethod.GET)
	public ModelMap findTransferHisList(
			@ModelAttribute("search") Search search, ModelMap map) {
		//페이지 검색초기값 설정
		search.setChannelCode("11");

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		Date today = new Date();
		String fromDay = sf.format(new Date(today.getTime()-(long)(1000*60*60*24*3)));
		String tDay = sf.format(new Date(today.getTime()));

		search.setStartDt(Utility.getDate(fromDay, "yyyy-MM-dd"));
		search.setEndDt(Utility.getDate(tDay, "yyyy-MM-dd"));

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("10");
		}

		if (search.getStartDt() != null){
			map.addAttribute("startDt",
					Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		}else{
			search.setStartDt(null);
		}
		if (search.getEndDt() != null){
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		}else{
			search.setEndDt(null);
		}

		// 키워드 설정
		if (search.getKeyword() != null){ 
			map.addAttribute("keyword", search.getKeyword());
		}

		if (search.getWorkStat() != null){ 
			map.addAttribute("workStat", search.getWorkStat());
		}else{
			search.setWorkStat("111");
		}

		if (search.getChannelCode() != null){ 
			map.addAttribute("channelCode", search.getChannelCode());	
		}else{
			search.setChannelCode("0");
		}

		if(search.getSearchDayName() != null){
			map.addAttribute("searchDayName", search.getSearchDayName());	
		}else{
			search.setSearchDayName("trs.REG_DT");
		}

		try {
			PaginationSupport<TransferHisTbl> contents = workflowManagerService.findTransferHis(search);

			map.addAttribute("contents", contents);
		} catch (Exception e) {
			logger.error("트랜스퍼 목록 조회 에러", e);
		}
		return map.addAttribute("search", search);
	}

	@RequestMapping(value = {"/workflow/ajaxTransmission.ssc", "/ajax/workflow/ajaxTransmission.ssc"}, method = RequestMethod.POST)
	public ModelAndView ajaxFindTransferHisList(
			@ModelAttribute("search") Search search, ModelMap map) {
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("10");
		}

		if (search.getStartDt() != null){
			map.addAttribute("startDt",Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		}else{
			search.setStartDt(null);
		}
		if (search.getEndDt() != null){
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		}else{
			search.setEndDt(null);
		}

		// 키워드 설정
		if (search.getKeyword() != null){ 
			map.addAttribute("keyword", search.getKeyword());
		}

		if (search.getWorkStat() != null){ 
			map.addAttribute("workStat", search.getWorkStat());
		}else{
			search.setWorkStat("111");
		}

		if (search.getChannelCode() != null){ 
			map.addAttribute("channelCode", search.getChannelCode());	
		}else{
			search.setChannelCode("0");
		}

		if(search.getSearchDayName() != null){
			map.addAttribute("searchDayName", search.getSearchDayName());	
		}else{
			search.setSearchDayName("trs.REG_DT");
		}

		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			view.addObject("search", search);

			PaginationSupport<TransferHisTbl> contents = workflowManagerService
			.findTransferHis(search);

			view.addObject("contents", contents.getItems());

			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("트랜스퍼 목록 조회 에러", e);
		}
		return view;
	}

	@RequestMapping(value = "/workflow/Workflow.ssc", method = RequestMethod.GET)
	public ModelMap findWorkflowList(ModelMap map) {

		try {
			List<EquipmentTbl> contents = workflowManagerService
			.findEquipment(map);
			map.addAttribute("contents", contents);
		} catch (Exception e) {
			logger.error("워크플로우 목록 조회 에러", e);
		}
		return map;
	}

	@RequestMapping(value = "/workflow/ajaxWorkflow.ssc", method = RequestMethod.POST)
	public ModelAndView ajaxFindWorkflowList(ModelMap map) {

		ModelAndView view = new ModelAndView();
		map.put("useYn", "Y");
		try {
			List<EquipmentTbl> contents = workflowManagerService
			.findEquipment(map);
			view.addObject("contents", contents);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("워크플로우 목록 조회 에러", e);
		}
		return view;
	}

	@RequestMapping(value = {"/workflow/ajaxWorkflowAv.ssc", "/ajax/workflow/ajaxWorkflowAv.ssc"}, method = RequestMethod.POST)
	public ModelAndView ajaxFindWorkflowList(ModelMap map,
			@RequestParam(value = "avGubun", required = false) String avGubun
	) {

		logger.debug("###avGubun:"+avGubun);
		ModelAndView view = new ModelAndView();
		map.put("useYn", "Y");
		if(StringUtils.isBlank(avGubun)) avGubun="Video";
		try {
			List<EquipmentTbl> contents = workflowManagerService.findEquipment(map);
			view.addObject("contents", contents);
			view.setViewName("jsonView");
			view.addObject("avGubun",avGubun);

		} catch (Exception e) {
			logger.error("워크플로우 목록 조회 에러", e);
		}
		return view;
	}

	@RequestMapping(value = {"/workflow/ajaxStorageAvailable.ssc","/ajax/workflow/ajaxStorageAvailable.ssc"}, method = RequestMethod.POST)
	public ModelAndView ajaxStorageAvailavle(ModelMap map,
			@RequestParam(value = "targetValue", required = false) String target) {


		ModelAndView view = new ModelAndView();
		try {
			if(null==StorageCheckControlWorker.params.get("contents"))
				StorageCheckControlWorker.init();
			logger.debug("storage: "+StorageCheckControlWorker.params.get("contents")[4]);
			view.addObject("contents", StorageCheckControlWorker.params.get("contents"));
			view.addObject("contents2", StorageCheckControlWorker.params.get("contents2"));
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("Check available NAS storage", e);
		}
		return view;
	}

	@RequestMapping(value = "/workflow/qcView.ssc", method = RequestMethod.POST)
	public ModelAndView qcView(@ModelAttribute("search") Search search,
			@RequestParam(value = "seq", required = false) String seq,
			ModelMap map) {

		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			params.put("seq", seq);

			List<QcReportTbl> qc = qcreportManagerService.findQcReport(params);

			view.addObject("qc", qc);
			view.addObject("seq", seq);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" qc 결과보기를 할 수 없음", e);
		}

		return view;
	}

	/**
	@RequestMapping(value = "/workflow/skip.ssc", method = RequestMethod.POST)
	public ModelAndView workSkip(
			@RequestParam(value = "skipseq", required = false) Long skipseq,
			ModelMap map) {

		ModelAndView view = new ModelAndView();
		TranscorderHisTbl transcorderhis = new TranscorderHisTbl();

		transcorderhis.setSeq(skipseq);
		transcorderhis.setUseYn("N");


		map.addAttribute("menuId", 11);
		try {
			//ProFlTbl proflTbl = proflManagerService.getProFlId(params);
			//System.out.println(">>>>>>>>>>>>>>>>>>>>>"+proflTbl.getNextval());

			//profile.setProFlid(proflTbl.getNextval());

			workflowManagerService.updateTranscorderHisState(transcorderhis);



		} catch (Exception e) {
			logger.error("작업관리 SKIP 오류", e);
			view.addObject("result", "N");
		}

		view.addObject("result", "Y");
		view.setViewName("jsonView");
		return view;
	}

	@RequestMapping(value = "/workflow/transmissionSkip.ssc", method = RequestMethod.POST)
	public ModelAndView transmissionSkip(
			@RequestParam(value = "skipseq", required = false) Long skipseq,
			ModelMap map) {

		ModelAndView view = new ModelAndView();
		TransferHisTbl transferhis = new TransferHisTbl();

		transferhis.setSeq(skipseq);
		transferhis.setUseYn("N");


		map.addAttribute("menuId", 10);
		try {
			//ProFlTbl proflTbl = proflManagerService.getProFlId(params);
			//System.out.println(">>>>>>>>>>>>>>>>>>>>>"+proflTbl.getNextval());

			//profile.setProFlid(proflTbl.getNextval());

			workflowManagerService.updateTransferHisState(transferhis);



		} catch (Exception e) {
			logger.error("전송관리 SKIP 오류", e);
			view.addObject("result", "N");
		}

		view.addObject("result", "Y");
		view.setViewName("jsonView");
		return view;
	}
	 */
	@RequestMapping(value = "/workflow/Transmission.ssc", method = RequestMethod.POST)
	public ModelMap findTabTransferHisList(
			@ModelAttribute("search") Search search, ModelMap map) {
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("10");
		}

		if (search.getStartDt() != null){
			map.addAttribute("startDt",Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		}else{
			search.setStartDt(null);
		}
		if (search.getEndDt() != null){
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		}else{
			search.setEndDt(null);
		}
		// 키워드 설정
		if (search.getKeyword() != null){ 
			map.addAttribute("keyword", search.getKeyword());
		}

		if (search.getWorkStat() != null){ 
			map.addAttribute("workStat", search.getWorkStat());
		}else{
			search.setWorkStat("111");
		}

		if (search.getChannelCode() != null){ 
			map.addAttribute("channelCode", search.getChannelCode());	
		}else{
			search.setChannelCode("0");
		}

		if(search.getSearchDayName() != null){
			map.addAttribute("searchDayName", search.getSearchDayName());	
		}else{
			search.setSearchDayName("trs.REG_DT");
		}

		try {
			PaginationSupport<TransferHisTbl> contents = workflowManagerService
			.findTransferHis(search);

			map.addAttribute("contents", contents);
		} catch (Exception e) {
			logger.error("트랜스퍼 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		return map;
	}

	@RequestMapping(value = "/workflow/MetaURL.ssc", method = RequestMethod.POST)
	public ModelMap metaURLList(@ModelAttribute("search") Search search,
			ModelMap map) {
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("10");
		}


		try {
			PaginationSupport<TransferHisTbl> contents = workflowManagerService
			.findMetaURLlist(search);

			map.addAttribute("contents", contents);
		} catch (Exception e) {
			logger.error("메타URL 목록  에러", e);
		}

		map.addAttribute("search", search);
		return map;
	}

	@RequestMapping(value = "/workflow/ajaxMetaURL.ssc", method = RequestMethod.POST)
	public ModelAndView ajaxMetaURLList(
			@ModelAttribute("search") Search search,
			ModelMap map) {
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("10");
		}

		ModelAndView view = new ModelAndView();
		try {
			view.addObject("search", search);

			PaginationSupport<TransferHisTbl> contents = workflowManagerService.findMetaURLlist(search);
			logger.debug("contents size: "+contents.getItems().size());
			map.addAttribute("contents", contents.getItems());

			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("메타URL 목록  에러", e);
		}

		map.addAttribute("search", search);
		return view;
	}

	@RequestMapping(value = "/workflow/request.ssc", method = RequestMethod.POST)
	public ModelAndView request(@ModelAttribute("search") Search search,
			@RequestParam(value = "seq", required = false) String seq,
			ModelMap map) {

		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();



		try {


			Timestamp date = new Timestamp(0);
			Timestamp date2 = null;
			//SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			TransferHisTbl trshis = new TransferHisTbl();

			trshis.setSeq(Long.valueOf(seq));
			trshis.setWorkStatcd("000");
			//trshis.setTrsEndDt();
			//trshis.setTrsStrDt();
			trshis.setPrgrs("0");
			trshis.setModDt(date);
			trshis.setMetaIns("C");
			//trshis.setTrsStrDt(date2);
			//trshis.setTrsEndDt(date2);
			//contentsTbl.setBrdDd(sf.parse(DateUtil.convertFormat(weekSchTbls.get(0).getProgramPlannedDate(), "yyyy-MM-dd")));
			workflowManagerService.updateTransRequest2(trshis);
			//workflowManagerService.updateTransferHisState(trshis);

			//ContentsTbl contentTbl = contentsManagerService.getContents(params);


			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 전송 재요청 에러", e);
		}


		return view;
	}

	@RequestMapping(value = "/workflow/proflRequest.ssc", method = RequestMethod.POST)
	public ModelAndView proflRequest(@ModelAttribute("search") Search search,
			@RequestParam(value = "seq", required = false) String seq,
			ModelMap map) {

		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		try {
			/*
			logger.debug("################################################");
			logger.debug("################################################");
			logger.debug("##############"+Long.valueOf(seq));
			logger.debug("################################################");
			logger.debug("################################################");

			params.put("seq",Long.valueOf(seq));
			TranscorderHisTbl trancorder = workflowManagerService.getTraHis(params);

			params2.put("ctiId",trancorder.getCtiId());
			params2.put("ctiFmt2","Y");


			logger.debug("################################################");
			logger.debug("################################################");
			logger.debug("##############"+trancorder.getCtiId());
			logger.debug("################################################");
			logger.debug("################################################");
			ContentsInstTbl contentsin = contentsInstManagerService.getContentsInst(params2);

			if(contentsin.getUseYn().equals("N")){
				view.addObject("result", "N");
			}else{
			 */
			//ContentsInstTbl contentsinst = new ContentsInstTbl();

			//contentsinst.setCtiId(Long.valueOf(seq));
			//contentsinst.setUseYn("N");
			//contentsInstManagerService.deleteContentsInst(contentsinst);

			Timestamp date = new Timestamp(0);
			Timestamp date2 = null;
			//SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			TranscorderHisTbl traHis = new TranscorderHisTbl();

			traHis.setWorkStatcd("000");
			traHis.setPrgrs("0");

			traHis.setSeq(Long.valueOf(seq));


			//trshis.setTrsStrDt(date2);
			//trshis.setTrsEndDt(date2);
			//contentsTbl.setBrdDd(sf.parse(DateUtil.convertFormat(weekSchTbls.get(0).getProgramPlannedDate(), "yyyy-MM-dd")));
			workflowManagerService.updateProflRequest(traHis);
			//workflowManagerService.updateTransferHisState(trshis);

			//ContentsTbl contentTbl = contentsManagerService.getContents(params);


			view.setViewName("jsonView");

		} catch (Exception e) {
			logger.error(" 프로파일 재요청 에러", e);
		}


		return view;
	}

	@RequestMapping(value = "/workflow/metaRequest.ssc", method = RequestMethod.POST)
	public ModelAndView metaRequest(@ModelAttribute("search") Search search,
			@RequestParam(value = "seq", required = false) String seq,
			ModelMap map) {

		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();



		try {

			ContentsInstTbl contentsInstTbl = new ContentsInstTbl();
			ProFlTbl proFlTbl = new ProFlTbl();

			params.put("seq", seq);

			contentsInstTbl = contentsInstManagerService.getContentsInstForwardMeta(params);
			if(StringUtils.defaultString(contentsInstTbl.getSrvUrl(), "N").equals("Y")){
				if(contentsInstTbl.getProFlid() != null) {
					params.put("proFlid", contentsInstTbl.getProFlid());
					proFlTbl = proFlManagerService.getProFl(params);
				}

				params.clear();
				params.put("essence_id",String.valueOf(seq));                     //에센스 허브 아이디                                  

				params.put("pcode",contentsInstTbl.getPgmCd());                          //프로그램코드               
				params.put("p_id",contentsInstTbl.getPgmId());                           //회별 프로그램 아이디       
				params.put("program_id",contentsInstTbl.getPgmId());                     //프로그램 아이디       \
				params.put("file_size",String.valueOf(contentsInstTbl.getFlSz()));                        //용량           
				params.put("filename",contentsInstTbl.getWrkFileNm()+"."+proFlTbl.getExt());                         //파일이름       
				params.put("segment_id",contentsInstTbl.getPgmId()+"-"+contentsInstTbl.getSegmentId());                     //TV일 경우 세그먼트 회차    
				params.put("essence_type1",contentsInstTbl.getCtCla());                  //에센스 타입1               
				params.put("essence_type2",contentsInstTbl.getCtTyp());                  //에센스 타입2               
				params.put("service_biz",contentsInstTbl.getAlias());                      //서비스 사업자              

				params.put("program_id_key","");                 //주간 편성 아이디           
				params.put("segment_code",contentsInstTbl.getPgmCd()+"-"+contentsInstTbl.getSegmentId());                   //세그먼트 프로그램 코드
				params.put("segment_code_radio",contentsInstTbl.getPgmCd()+"-"+contentsInstTbl.getSegmentId());                   //세그먼트 프로그램 코드  
				params.put("segment_id_radio",contentsInstTbl.getPgmId()+"-"+contentsInstTbl.getSegmentId());               //라디오일 경우 세그먼트 회차
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
				params.put("video_resoution","");                  //해상도         
				params.put("whaline",proFlTbl.getVdoSync());                          //비디오 종횡맞춤
				params.put("width",proFlTbl.getVdoHori()); //비디오 가로    


				String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
				String createMethod = messageSource.getMessage("meta.system.url.forward.create", null, Locale.KOREA);

				String rXml = "";
				String rXml2 = "";


				try {
					HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
					rXml = httpRequestService.findData(domain+createMethod, Utility.convertNameValue(params));
					//rXml = Utility.connectHttpPostMethod(domain+createMethod, Utility.convertNameValue(params));
					logger.debug("rXml"+rXml);
				}catch (Exception e) {
					logger.error("MetaHub Service URL Insert Error", e);
				}
				
				TransferHisTbl transferHisTbl = new TransferHisTbl();
				transferHisTbl.setSeq(Long.parseLong(seq));

				if(rXml.indexOf("SUCCESS")> -1){
					transferHisTbl.setMetaIns("C");
				}else{
					transferHisTbl.setMetaIns("E");
				}

				workflowManagerService.updateTransferHisState(transferHisTbl);	
			}
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" MeteURL 재요청 에러", e);
		}


		return view;
	}
}
