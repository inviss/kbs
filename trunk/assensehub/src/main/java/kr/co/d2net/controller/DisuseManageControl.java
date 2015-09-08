package kr.co.d2net.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import javax.servlet.ServletContext;

import kr.co.d2net.commons.util.DateUtil;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.CodeTbl;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.DisuseInfoTbl;
import kr.co.d2net.dto.OptTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.ProOptTbl;
import kr.co.d2net.service.CodeManagerService;
import kr.co.d2net.service.ContentsManagerService;
import kr.co.d2net.service.DisuseInfoManagerService;
import kr.co.d2net.service.ContentsInstManagerService;
import kr.co.d2net.dto.Search;

import org.springframework.context.MessageSource;
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

@Controller
public class DisuseManageControl {
	
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private DisuseInfoManagerService disuseinfoManagerService;
	@Autowired
	private ContentsManagerService contentsManagerService;
	@Autowired
	private ContentsInstManagerService contentsInstManagerService;
	@Autowired
	private CodeManagerService codeManagerService;
	@Autowired
	private MessageSource messageSource;
	
	final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value="/disuse/DisuseSearch.ssc", method = RequestMethod.GET)
	public ModelMap editList(@ModelAttribute("search") Search search, ModelMap map) {
		// 페이지 설정
		if(search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		Date today = new Date();
		String tomorrow = sf.format(new Date(today.getTime()+(long)(1000*60*60*24)));
		
		try {
			PaginationSupport<DisuseInfoTbl> contents = disuseinfoManagerService.findDisuseList(search);
			
			map.addAttribute("contents", contents);
			search.setStartDt(Utility.getDate(tomorrow, "yyyy-MM-dd"));
			search.setEndDt(Utility.getDate(tomorrow, "yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("폐기 검색 목록  에러", e);
		}
		
		map.addAttribute("search", search);
		return map;
	}
	
	@RequestMapping(value="/disuse/DisuseSearch.ssc", method = RequestMethod.POST)
	public ModelMap disuseSearchList(@ModelAttribute("search") Search search, ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		// 기간검색 설정
		if(search.getStartDt() != null)
			map.addAttribute("startDt", Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		if(search.getEndDt() != null)
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		
		//키워드 설정
		if(search.getKeyword()!=null)

		map.addAttribute("ketword", search.getKeyword());


		try {
			PaginationSupport<DisuseInfoTbl> contents = disuseinfoManagerService.findDisuseList2(search);
			
			
			map.addAttribute("contents", contents);

		} catch (Exception e) {
			logger.error("폐기 검색 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		return map;

	}
	
	@RequestMapping(value = "/disuse/selectContent.ssc", method = RequestMethod.POST)
	public ModelAndView selectContent(@ModelAttribute("search") Search search,
			@RequestParam(value = "id", required = false) String id,
			ModelMap map) {


		
		
		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();
		int date = Integer.parseInt(messageSource.getMessage("media.safe.days", null, null));

		try {
			params.put("ctId", id);

			ContentsTbl contentTbl = contentsManagerService.getContents3(params);
			
			String regDate= Utility.getDate(contentTbl.getRegDt(),"yyyy-MM-dd");
			int delayDay= contentTbl.getDelayDay();
			int date2= date + delayDay;
			
			String delDate= Utility.getDate(regDate,date2);
			String brdDate= Utility.getDate(contentTbl.getBrdDd(),"yyyy-MM-dd");
			
			// params.put("ctiFmt", "1"); // 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
			// ,오디오 원본:'3XX')
			params.put("notCtiFmt", "1");// 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
			// ,오디오 원본:'3XX')
			List<ContentsInstTbl> contentsInstTbls = contentsInstManagerService.findContentsInstSummary(params);
			view.addObject("contentsInstTbls", contentsInstTbls);
			view.addObject("contentTbl", contentTbl);
			view.addObject("regDate", regDate);
			view.addObject("delDate", delDate);
			view.addObject("brdDate", brdDate);
			
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 폐기 검색 콘텐츠 상세보기를 할 수 없음", e);
		}
		

		return view;
	}
	
	@RequestMapping(value = "/disuse/selectContent2.ssc", method = RequestMethod.POST)
	public ModelAndView selectContent2(@ModelAttribute("search") Search search,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "no", required = false) String no,
			ModelMap map) {


		
		
		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();


		try {
			
			params.put("disuseNo", id);
			//params.put("disuseNo", no);
			ContentsTbl contentTbl = contentsManagerService.getContents2(params);
			
			String regDate= Utility.getDate(contentTbl.getRegDt(),"yyyy-MM-dd");
			String delDate= Utility.getDate(contentTbl.getDisuseDd(),"yyyy-MM-dd");
			String brdDate="";
			if(contentTbl.getBrdDd() != null){
				brdDate= Utility.getDate(contentTbl.getBrdDd(),"yyyy-MM-dd");
				
			}
			view.addObject("contentTbl", contentTbl);
			view.addObject("regDate", regDate);
			view.addObject("delDate", delDate);
			view.addObject("brdDate", brdDate);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 폐기 현황 콘텐츠 상세보기를 할 수 없음", e);
		}
		
		return view;
	}
	
	@RequestMapping(value = "/disuse/selectContent3.ssc", method = RequestMethod.POST)
	public ModelAndView selectContent3(@ModelAttribute("search") Search search,
			@RequestParam(value = "id", required = false) String id,
			ModelMap map) {


		
		
		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();
		int date = Integer.parseInt(messageSource.getMessage("media.safe.days", null, null));
		String delay = messageSource.getMessage("media.safe.days", null,Locale.KOREA);
		
		int delaydate=Integer.parseInt(delay);
		try {
			params.put("ctId", id);

			ContentsTbl contentTbl = contentsManagerService.getContents3(params);
			
			String regDate= Utility.getDate(contentTbl.getRegDt(),"yyyy-MM-dd");
			
			String delDate= Utility.getDate(regDate, delaydate + contentTbl.getDelayDay());
			String brdDate= Utility.getDate(contentTbl.getBrdDd(),"yyyy-MM-dd");
			
			// params.put("ctiFmt", "1"); // 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
			// ,오디오 원본:'3XX')
			params.put("notCtiFmt", "1");// 콘텐츠 구분 (비디오 원본 : '1XX' , 서비스 영상 : '2XX'
			// ,오디오 원본:'3XX')
			List<ContentsInstTbl> contentsInstTbls = contentsInstManagerService.findContentsInstSummary(params);
			
			view.addObject("contentsInstTbls", contentsInstTbls);
			view.addObject("contentTbl", contentTbl);
			view.addObject("regDate", regDate);
			view.addObject("delDate", delDate);
			view.addObject("brdDate", brdDate);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 폐기 연장 콘텐츠 상세보기를 할 수 없음", e);
		}

		return view;
	}
	
	@RequestMapping(value = "/disuse/disuseDelay.ssc", method = RequestMethod.POST)
	public ModelAndView disuseDelay(@ModelAttribute("search") Search search,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "delayDay", required = false) Integer delayDay,
			
			ModelMap map) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		ModelAndView view = new ModelAndView();
		
		ContentsTbl content = new ContentsTbl();
		content.setCtId(id);
		
		map.addAttribute("menuId", 17);
		try {
			params.put("ctId", id);

			ContentsTbl contentTbl = contentsManagerService.getContents(params);
			
			int delay = contentTbl.getDelayDay();
			
			if(delay == 1){
				content.setDelayDay(delayDay);
			}else{
				content.setDelayDay(delay + delayDay);
			}
			
			contentsManagerService.updateContents2(content);
			
			view.addObject("search", search);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 폐기 연장을 할 수 없음", e);
		}

		return view;
	}
	
	@RequestMapping(value = "/disuse/contentdelete.ssc", method = RequestMethod.POST)
	public ModelAndView contentdelete(@ModelAttribute("search") Search search,
			@RequestParam(value = "id", required = false) Long id,

			ModelMap map) {
		
		ModelAndView view = new ModelAndView();
		
		ContentsTbl content = new ContentsTbl();
		content.setCtId(id);
		content.setUseYn("N");
		

		map.addAttribute("menuId", 17);
		try {
			contentsManagerService.deleteContents(content);
			
			DisuseInfoTbl disuseinfoTbl = new DisuseInfoTbl();
			disuseinfoTbl.setDisuseClf("U");
			disuseinfoTbl.setDataId(id);
			disuseinfoManagerService.insertDisuseInfo(disuseinfoTbl);
			
			view.addObject("search", search);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 콘텐츠 사용자 삭제을 할 수 없음", e);
		}

		return view;
	}
	
	@RequestMapping(value = "/disuse/contentsdelete.ssc", method = RequestMethod.POST)
	public ModelAndView contentsdelete(@ModelAttribute("search") Search search,
			@RequestParam(value = "check", required = false) String check,

			ModelMap map) {

		ModelAndView view = new ModelAndView();
		
		String checks[] = check.split(",");

		map.addAttribute("menuId", 17);
		try {
			for (int i = 0; i < checks.length; i++) {
				ContentsTbl content = new ContentsTbl();
				content.setCtId(Long.parseLong(checks[i]));
				content.setUseYn("N");
				
				contentsManagerService.deleteContents(content);
				
				DisuseInfoTbl disuseinfoTbl = new DisuseInfoTbl();
				disuseinfoTbl.setDisuseClf("U");
				disuseinfoTbl.setDataId(Long.parseLong(checks[i]));
				
				disuseinfoManagerService.insertDisuseInfo(disuseinfoTbl);
				
				view.addObject("search", search);
				view.setViewName("jsonView");
			}

		} catch (Exception e) {
			logger.error(" 콘텐츠 사용자 삭제을 할 수 없음", e);
		}

		return view;
	}
	
	@RequestMapping(value="/disuse/DisuseStatus.ssc", method = RequestMethod.GET)
	public ModelMap editList2(@ModelAttribute("search") Search search, ModelMap map) {
		// 페이지 설정
		if(search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}
		
		 //기간검색 설정
		if(search.getStartDt() != null)
			map.addAttribute("startDt", Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		if(search.getEndDt() != null)
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		
		try {
			PaginationSupport<DisuseInfoTbl> contents = disuseinfoManagerService.finddisuseStatusList(search);
			map.addAttribute("contents", contents);
		} catch (Exception e) {
			logger.error("폐기 현황 목록  에러", e);
		}
		
		map.addAttribute("search", search);
		return map;
	}
	
	@RequestMapping(value="/disuse/DisuseStatus.ssc", method = RequestMethod.POST)
	public ModelMap DisuseStatusList(@ModelAttribute("search") Search search, ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		// 기간검색 설정
		if(search.getStartDt() != null)
			map.addAttribute("startDt", Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		if(search.getEndDt() != null)
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		
		//키워드 설정
		if(search.getKeyword()!=null)

		map.addAttribute("ketword", search.getKeyword());

		try {
			PaginationSupport<DisuseInfoTbl> contents = disuseinfoManagerService.finddisuseStatusList(search);
			map.addAttribute("contents", contents);
		} catch (Exception e) {
			logger.error("폐기 현황 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		return map;

	}
	
	@RequestMapping(value="/disuse/DisuseRenewal.ssc", method = RequestMethod.GET)
	public ModelMap editList3(@ModelAttribute("search") Search search, ModelMap map) {
		
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		// 기간검색 설정

		if(search.getStartDt() != null)
			map.addAttribute("startDt", Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		if(search.getEndDt() != null)
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		
		
		
		
		//키워드 설정
		if(search.getKeyword()!=null)

		map.addAttribute("ketword", search.getKeyword());
		
		try {
			PaginationSupport<DisuseInfoTbl> contents = disuseinfoManagerService.finddisuseRenewalList(search);
			map.addAttribute("contents", contents);

		} catch (Exception e) {
			logger.error("폐기 현황 목록  에러", e);
		}

		map.addAttribute("search", search);
		return map;
	}
	
	@RequestMapping(value="/disuse/DisuseRenewal.ssc", method = RequestMethod.POST)
	public ModelMap DisuseRenewalList(@ModelAttribute("search") Search search, ModelMap map) {
		
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		// 기간검색 설정

		if(search.getStartDt() != null)
			map.addAttribute("startDt", Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		if(search.getEndDt() != null)
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		
		
		
		
		//키워드 설정
		if(search.getKeyword()!=null)

		map.addAttribute("ketword", search.getKeyword());
		
		try {
			PaginationSupport<DisuseInfoTbl> contents = disuseinfoManagerService.finddisuseRenewalList(search);
			map.addAttribute("contents", contents);

		} catch (Exception e) {
			logger.error("폐기 현황 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		return map;
	}
	
	
}
