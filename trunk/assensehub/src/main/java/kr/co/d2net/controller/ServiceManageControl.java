package kr.co.d2net.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.BusiPartnerTbl;
import kr.co.d2net.dto.OptTbl;
import kr.co.d2net.dto.ProBusiTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.ProOptTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.service.BusiPartnerManagerService;
import kr.co.d2net.service.OptManagerService;
import kr.co.d2net.service.ProBusiManagerService;
import kr.co.d2net.service.ProFlManagerService;
import kr.co.d2net.service.ProOptManagerService;

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

@Controller
public class ServiceManageControl {

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private ProFlManagerService proflManagerService;
	@Autowired
	private BusiPartnerManagerService busipartnerManagerService;
	@Autowired
	private ProBusiManagerService probusiManagerService;
	@Autowired
	private OptManagerService optManagerService;
	@Autowired
	private ProOptManagerService prooptManagerService;

	final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/service/ProfileOpt.ssc", method = RequestMethod.GET)
	public ModelMap editList2(@ModelAttribute("search") Search search,
			ModelMap map) {

		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("14");
		}
		search.setMenuId("14");


		try {

			PaginationSupport<OptTbl> contents = optManagerService.findOptList(search);


			map.addAttribute("contents", contents);	


		} catch (Exception e) {
			logger.error("프로파일 옵션 목록  에러", e);
		}

		map.addAttribute("search", search);

		return map;
	}

	@RequestMapping(value = "/service/ProfileOpt.ssc", method = RequestMethod.POST)
	public ModelMap editList3(@ModelAttribute("search") Search search,
			ModelMap map) {

		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("14");
		}
		search.setMenuId("14");


		try {

			PaginationSupport<OptTbl> contents = optManagerService.findOptList(search);


			map.addAttribute("contents", contents);	


		} catch (Exception e) {
			logger.error("프로파일 옵션 목록  에러", e);
		}

		map.addAttribute("search", search);

		return map;
	}

	@RequestMapping(value="/service/Profile.ssc", method = RequestMethod.GET)
	public ModelMap proFlList(@ModelAttribute("search") Search search, ModelMap map) {


		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		Map<String, Object> params = new HashMap<String, Object>();

		try {
			PaginationSupport<ProFlTbl> contents = proflManagerService.findProFlList(search);
			map.addAttribute("contents", contents);

			System.out.println("contents size: "+contents.getStartIndex());
			params.put("useYn", "Y");
			List<OptTbl> opts = optManagerService.findOpt(params);
			System.out.println("opts size: "+opts.size());

			map.addAttribute("opts", opts);

		} catch (Exception e) {
			logger.error("프로파일 목록  에러", e);
		}
		search.setMenuId("13");
		map.addAttribute("search", search);
		return map;
	}


	@RequestMapping(value="/service/insertProfile.ssc", method = RequestMethod.GET)
	public ModelAndView  insertProFl(@ModelAttribute("search") Search search, ModelMap map) {

		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		// 페이지 설정
		//if(search.getPageNo() == null || search.getPageNo() == 0) {
		//	search.setPageNo(1);
		//}

		// 기간검색 설정
		//		if(search.getStartDt() != null)
		//			map.addAttribute("startDt", Utility.getDate(search.getStartDt(), "yyyy.MM.dd"));
		//		if(search.getEndDt() != null)
		//			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy.MM.dd"));
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("useYn", "Y");

		ModelAndView view = new ModelAndView();
		try { 
			//PaginationSupport<ProFlTbl> contents = proflManagerService.findProFlList(search);
			//map.addAttribute("contents", contents);

			List<OptTbl> opts = optManagerService.findOpt(params);
			params.put("defaultYn","Y");
			OptTbl optTbl = optManagerService.getOpt(params);

			view.addObject("optTbl",optTbl);
			view.addObject("opts", opts);

			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("프로파일 추가버튼 레이어  에러", e);
		}

		//map.addAttribute("search", search);
		return view;
	}

	@RequestMapping(value="/service/Business.ssc", method = RequestMethod.GET)
	public ModelMap businessList(@ModelAttribute("search") Search search, ModelMap map) {



		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("15");
		}

		// 기간검색 설정
		// if(search.getStartDt() != null)
		// map.addAttribute("startDt", Utility.getDate(search.getStartDt(),
		// "yyyy.MM.dd"));
		// if(search.getEndDt() != null)
		// map.addAttribute("endDt", Utility.getDate(search.getEndDt(),
		// "yyyy.MM.dd"));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("useYn", "Y");
		try {
			PaginationSupport<BusiPartnerTbl> contents = busipartnerManagerService
					.findBusiPartnerList(search);
			map.addAttribute("contents", contents);


			List<ProFlTbl> profls = proflManagerService.findProFl(params);
			map.addAttribute("profls", profls);

		} catch (Exception e) {
			logger.error("사업자 목록  에러", e);
		}
		map.addAttribute("business", new BusiPartnerTbl());
		map.addAttribute("search", search);
		return map;
	}


	@RequestMapping(value="/service/insertbusipartner.ssc", method = RequestMethod.GET)
	public ModelAndView insertBusiness(@ModelAttribute("search") Search search, ModelMap map) {
		// 페이지 설정
		//if(search.getPageNo() == null || search.getPageNo() == 0) {
		//	search.setPageNo(1);
		//}

		// 기간검색 설정
		//		if(search.getStartDt() != null)
		//			map.addAttribute("startDt", Utility.getDate(search.getStartDt(), "yyyy.MM.dd"));
		//		if(search.getEndDt() != null)
		//			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy.MM.dd"));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("useYn", "Y");
		ModelAndView view = new ModelAndView();

		try {
			//PaginationSupport<BusiPartnerTbl> contents = busipartnerManagerService.findBusiPartnerList(search);
			//map.addAttribute("contents", contents);


			List<ProFlTbl> profls = proflManagerService.findProFl(params);
			view.addObject("profls", profls);		
			view.setViewName("jsonView");

		} catch (Exception e) {
			logger.error("사업자 추가버튼 레이어  에러", e);
		}

		//map.addAttribute("search", search);
		return view;
	}

	@RequestMapping(value="/service/insertprofileopt.ssc", method = RequestMethod.GET)
	public ModelAndView insertprofileopt(@ModelAttribute("search") Search search, ModelMap map) {
		// 페이지 설정
		if(search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}

		// 기간검색 설정
		if(search.getStartDt() != null)
			map.addAttribute("startDt", Utility.getDate(search.getStartDt(), "yyyy.MM.dd"));
		if(search.getEndDt() != null)
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy.MM.dd"));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("useYn", "Y");
		ModelAndView view = new ModelAndView();

		try {
			PaginationSupport<BusiPartnerTbl> contents = busipartnerManagerService.findBusiPartnerList(search);
			map.addAttribute("contents", contents);

			List<ProFlTbl> profls = proflManagerService.findProFl(params);
			view.addObject("profls", profls);		
			view.setViewName("jsonView");

		} catch (Exception e) {
			logger.error("프로파일 옵션 추가버튼 레이어  에러", e);
		}

		map.addAttribute("search", search);
		return view;
	}

	@RequestMapping(value="/service/Profile.ssc", method = RequestMethod.POST)
	public ModelMap proFlSearchList(@ModelAttribute("search") Search search, ModelMap map
			) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("13");
		}

		// 기간검색 설정

		if(search.getStartDt() != null)
			map.addAttribute("startDt", Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		if(search.getEndDt() != null)
			map.addAttribute("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));

		//키워드 설정
		if(search.getKeyword()!=null)

			map.addAttribute("ketword", search.getKeyword());

		Map<String, Object> params = new HashMap<String, Object>();

		try {
			PaginationSupport<ProFlTbl> contents = proflManagerService
					.findProFlList(search);
			map.addAttribute("contents", contents);

			params.put("useYn", "Y");
			List<OptTbl> opts = optManagerService.findOpt(params);
			map.addAttribute("opts", opts);

		} catch (Exception e) {
			logger.error("프로파일 목록 조회 에러", e);
		}

		map.addAttribute("search", search);
		return map;

	}


	@RequestMapping(value="/service/Business.ssc", method = RequestMethod.POST)
	public ModelMap businessSearchList(@ModelAttribute("search") Search search, ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("15");
		}


		// 기간검색 설정
		// if(search.getStartDt() != null)
		// map.addAttribute("startDt", Utility.getDate(search.getStartDt(),
		// "yyyy.MM.dd"));
		// if(search.getEndDt() != null)
		// map.addAttribute("endDt", Utility.getDate(search.getEndDt(),
		// "yyyy.MM.dd"));

		// 키워드 설정
		if (search.getKeyword() != null)
			map.addAttribute("ketword", search.getKeyword());

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("useYn", "Y");
		try {
			PaginationSupport<BusiPartnerTbl> contents = busipartnerManagerService
					.findBusiPartnerList(search);
			map.addAttribute("contents", contents);


			List<ProFlTbl> profls = proflManagerService.findProFl(params);
			map.addAttribute("profls", profls);

		} catch (Exception e) {
			logger.error("사업자 목록  에러", e);
		}
		map.addAttribute("business", new BusiPartnerTbl());
		map.addAttribute("search", search);
		return map;
	}

	@RequestMapping(value = "/service/saveProfileOpt.ssc", method = RequestMethod.POST)
	public String saveProfileOpt(
			@RequestParam(value = "optInfo", required = false) String optInfo,
			@RequestParam(value = "optDesc", required = false) String optDesc,
			ModelMap map) {

		OptTbl opt = new OptTbl();

		opt.setOptInfo(optInfo);
		opt.setOptDesc(optDesc);
		opt.setUseYn("Y");
		opt.setDefaultYn("N");

		map.addAttribute("menuId", 14);
		try {
			optManagerService.insertOpt(opt);

		} catch (Exception e) {
			logger.error(" 프로파일 옵션 저장을 할 수 없음", e);
		}

		return "redirect:/service/ProfileOpt.ssc";
	}

	@RequestMapping(value = "/service/saveProfile.ssc", method = RequestMethod.POST)
	public String saveProfile(
			@RequestParam(value = "proFlnm", required = false) String proFlnm,
			@RequestParam(value = "vdoCodec", required = false) String vdoCodec,
			@RequestParam(value = "vdoBitRate", required = false) String vdoBitRate,
			@RequestParam(value = "vdoHori", required = false) String vdoHori,
			@RequestParam(value = "vdoVert", required = false) String vdoVert,
			@RequestParam(value = "vdoFS", required = false) String vdoFS,
			@RequestParam(value = "vdoSync", required = false) String vdoSync,
			@RequestParam(value = "audCodec", required = false) String audCodec,
			@RequestParam(value = "audBitRate", required = false) String audBitRate,
			@RequestParam(value = "audChan", required = false) String audChan,
			@RequestParam(value = "audSRate", required = false) String audSRate,
			@RequestParam(value = "ext", required = false) String ext,
			@RequestParam(value = "priority", required = false) Integer priority,
			@RequestParam(value = "moddt", required = false) String modDt,
			@RequestParam(value = "opt", required = false) String opt,
			@RequestParam(value = "picKind", required = false) String picKind,
			@RequestParam(value = "keyFrame", required = false) String keyFrame,
			@RequestParam(value = "servBit", required = false) String servBit,
			@RequestParam(value = "flNameRule", required = false) String flNameRule,
			ModelMap map) {

		ProFlTbl profile = new ProFlTbl();

		profile.setProFlnm(proFlnm);
		profile.setVdoCodec(vdoCodec);
		profile.setVdoBitRate(vdoBitRate);
		profile.setVdoHori(StringUtils.defaultString(vdoHori, "0"));
		profile.setVdoVert(StringUtils.defaultString(vdoVert, "0"));
		profile.setVdoFS(vdoFS);
		profile.setVdoSync(vdoSync);
		profile.setAudCodec(audCodec);
		profile.setAudBitRate(audBitRate);
		profile.setAudChan(audChan);
		profile.setAudSRate(audSRate);
		profile.setExt(ext);
		profile.setFlNameRule(flNameRule);

		profile.setUseYn("Y");
		profile.setServBit(servBit);
		profile.setPicKind(picKind);
		profile.setKeyFrame(keyFrame);
		
		//Priority 관련 추가(2013-04-30)
		profile.setPriority(priority);

		String opts[] = null;
		if(opt==null){

		}else{
			opts=opt.split(",");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("flNameRule", flNameRule);
		//params.put("picKind", picKind);

		try {
			//			List<ProFlTbl> proflTbls = proflManagerService.findProFl(params);
			////			//파일네임 룰 + 규격(HD,SD) 기존 등록된 유무로 입력 가능여부 판단.
			//			if(proflTbls.size() < 1){
			ProFlTbl proflTbl = proflManagerService.getProFlId(params);

			profile.setProFlid(proflTbl.getNextval());

			proflManagerService.insertProFl(profile);

			if(opt!=null){
				ProOptTbl proopt = new ProOptTbl();
				for(int i=0; i<opts.length; i++){
					proopt.setProFlid(proflTbl.getNextval());
					proopt.setOptId(Integer.parseInt(opts[i]));
					proopt.setUseYn("Y");

					prooptManagerService.insertProOpt(proopt);
				}
			}
			map.addAttribute("menuId", 13);
			return "redirect:/service/Profile.ssc";
			//			}
			//
			//			map.addAttribute("msg", "duple");
			//			map.addAttribute("menuId", 13);
			//		return "redirect:/service/Profile.ssc";

		} catch (Exception e) {
			logger.error(" 프로파일 저장을 할 수 없음", e);
		}
		//map.addAttribute("menuId", 13);
		return "redirect:/service/Profile.ssc";
	}

	@RequestMapping(value = "/service/updateProfile.ssc", method = RequestMethod.POST)
	public ModelAndView updateProfile(@ModelAttribute("search") Search search,
			@RequestParam(value = "proFlnm", required = false) String proFlnm,
			@RequestParam(value = "vdoCodec", required = false) String vdoCodec,
			@RequestParam(value = "vdoBitRate", required = false) String vdoBitRate,
			@RequestParam(value = "vdoHori", required = false) String vdoHori,
			@RequestParam(value = "vdoVert", required = false) String vdoVert,
			@RequestParam(value = "vdoFS", required = false) String vdoFS,
			@RequestParam(value = "vdoSync", required = false) String vdoSync,
			@RequestParam(value = "audCodec", required = false) String audCodec,
			@RequestParam(value = "audBitRate", required = false) String audBitRate,
			@RequestParam(value = "audChan", required = false) String audChan,
			@RequestParam(value = "audSRate", required = false) String audSRate,
			@RequestParam(value = "ext", required = false) String ext,
			@RequestParam(value = "priority", required = false) Integer priority,
			@RequestParam(value = "moddt", required = false) String modDt,
			@RequestParam(value = "opt", required = false) String opt,
			@RequestParam(value = "proFlid", required = false) String proFlid,
			@RequestParam(value = "picKind", required = false) String picKind,
			@RequestParam(value = "keyFrame", required = false) String keyFrame,
			@RequestParam(value = "servBit", required = false) String servBit,
			@RequestParam(value = "flNameRule", required = false) String flNameRule,
			ModelMap map) {


		ProFlTbl profile = new ProFlTbl();

		profile.setProFlid(proFlid);

		profile.setProFlnm(proFlnm);
		profile.setVdoCodec(vdoCodec);
		profile.setVdoBitRate(vdoBitRate);
		profile.setVdoHori(StringUtils.defaultString(vdoHori, "0"));
		profile.setVdoVert(StringUtils.defaultString(vdoVert, "0"));
		profile.setVdoFS(vdoFS);
		profile.setVdoSync(vdoSync);
		profile.setAudCodec(audCodec);
		profile.setAudBitRate(audBitRate);
		profile.setAudChan(audChan);
		profile.setAudSRate(audSRate);
		profile.setExt(ext);
		profile.setFlNameRule(flNameRule);

		profile.setServBit(servBit);
		profile.setPicKind(picKind);
		profile.setKeyFrame(keyFrame);
		
		//Priority 관련 추가(2013-04-30)
		profile.setPriority(priority);

		String opts[]=null;

		if(opt == null ){
		}else{
			opts=opt.split(",");
		}
		ModelAndView view = new ModelAndView();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("flNameRule", flNameRule);
		params.put("picKind", picKind);

		try {


			view.addObject("search", search);
			view.setViewName("jsonView");
			List<ProFlTbl> proflTbls = proflManagerService.findProFl(params);
			//파일네임 룰 + 규격(HD,SD) 기존 등록된 유무로 입력 가능여부 판단.

			proflManagerService.updateProFl(profile);

			ProOptTbl proopt = new ProOptTbl();
			proopt.setProFlid(proFlid);
			prooptManagerService.deleteProOpt(proopt);

			if(opt !=null){

				ProOptTbl proopt2 = new ProOptTbl();


				for(int i=0; i<opts.length; i++){
					proopt2.setProFlid(proFlid);
					proopt2.setOptId(Integer.parseInt(opts[i]));
					proopt2.setUseYn("Y");

					prooptManagerService.insertProOpt(proopt2);
				}
			}
			//				view.addObject("msg", "기존 등록된 프로파일중 동일한 파일네임룰 과 규격이 존재 합니다.");
		} catch (Exception e) {
			logger.error(" 프로파일 수정을 할 수 없음", e);
		}

		return view;
	}

	@RequestMapping(value = "/service/updateOpt.ssc", method = RequestMethod.POST)
	public ModelAndView updateOpt(@ModelAttribute("search") Search search,
			@RequestParam(value = "optId", required = false) String optId,
			@RequestParam(value = "optInfo", required = false) String optInfo,
			@RequestParam(value = "optDesc", required = false) String optDesc,

			ModelMap map) {

		//System.out.println(">>>>>>>>>>>>>>>>>>>"+search.getMenuId());
		OptTbl opt= new OptTbl();

		opt.setOptId(Integer.parseInt(optId));
		opt.setOptInfo(optInfo);
		opt.setOptDesc(optDesc);


		ModelAndView view = new ModelAndView();


		try {


			optManagerService.updateOpt(opt);


			view.addObject("search", search);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 프로파일 옵션 수정을 할 수 없음", e);
		}

		return view;
	}

	@RequestMapping(value = "/service/defaultOpt.ssc", method = RequestMethod.POST)
	public String defaultOpt(
			@RequestParam(value = "check", required = false) String check,


			ModelMap map) {
		//System.out.println(">>>>>>>>>>>>>>>>>>"+check);

		OptTbl opt= new OptTbl();

		opt.setOptId(Integer.parseInt(check));
		opt.setDefaultYn("Y");





		map.addAttribute("menuId", 14);
		try {

			OptTbl opt2= new OptTbl();
			opt2.setDefaultYn("N");
			optManagerService.updateOpt3(opt2);

			optManagerService.updateOpt2(opt);



		} catch (Exception e) {
			logger.error(" 프로파일 기본옵션 설정을 할 수 없음", e);
		}

		return "redirect:/service/ProfileOpt.ssc";
	}

	@RequestMapping(value = "/service/saveBusiness.ssc", method = RequestMethod.POST)
	public String saveBusiness(
			@ModelAttribute("business") BusiPartnerTbl business,
			@RequestParam(value = "profl", required = false) String profl,
			ModelMap map) {

		String profls[]= null;
		if(profl == null){

		}else{
			profls=profl.split(",");
		}

		Map<String, Object> params = new HashMap<String, Object>();


		map.addAttribute("menuId", 14);
		try {

			BusiPartnerTbl busipartner=busipartnerManagerService.getBusiPartnerId(params);

			business.setBusiPartnerid(busipartner.getNextval());

			busipartnerManagerService.insertBusiPartner(business);


			if(profl ==null){

			}else{
				ProBusiTbl probusi = new ProBusiTbl();
				for(int i=0; i<profls.length; i++){
					probusi.setBusiPartnerid(busipartner.getNextval());
					probusi.setProFlid(profls[i]);

					probusiManagerService.insertProBusi(probusi);
				}
			}

		} catch (Exception e) {
			logger.error(" 사업자를 저장을 할 수 없음", e);
		}



		return "redirect:/service/Business.ssc";
	}

	@RequestMapping(value = "/service/updateBusiness.ssc", method = RequestMethod.POST)
	public ModelAndView updateBusiness(@ModelAttribute("search") Search search,
			@ModelAttribute("business") BusiPartnerTbl business,
			@RequestParam(value = "profl", required = false) String profl,
			ModelMap map) {
		ModelAndView view = new ModelAndView();
		String profls[]= null;
		if(profl == null){

		}else{
			profls=profl.split(",");
		}

		map.addAttribute("menuId", 14);
		try {
			logger.debug("business.proEngYn:"+business.getProEngYn());
			logger.debug("business.getBusiPartnerid:"+business.getBusiPartnerid());

			busipartnerManagerService.updateBusiPartner(business);

			//System.out.println(">>>>>>>>>>>>>>>>>>"+busiPartnerid);
			ProBusiTbl probusi = new ProBusiTbl();

			probusi.setBusiPartnerid(business.getBusiPartnerid());

			probusiManagerService.deleteProBusi(probusi);


			if(profl ==null){

			}else{
				ProBusiTbl probusi2 = new ProBusiTbl();
				for(int i=0; i<profls.length; i++){
					probusi2.setBusiPartnerid(business.getBusiPartnerid());
					probusi2.setProFlid(profls[i]);
					probusiManagerService.insertProBusi(probusi2);
				}
			}

			view.addObject("search", search);
			view.setViewName("jsonView");

		} catch (Exception e) {
			logger.error(" 사업자를 수정을 할 수 없음", e);
		}

		return view;
	}

	@RequestMapping(value = "/service/selectProfile.ssc", method = RequestMethod.POST)
	public ModelAndView selectProfile(@ModelAttribute("search") Search search,
			@RequestParam(value = "proflid", required = false) String proflid,
			ModelMap map) {



		//System.out.println("proflid=="+proflid);
		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();


		try {
			params.put("proFlid", proflid);

			ProFlTbl proflTbl = proflManagerService.getProFl(params);

			params.put("useYn","Y");
			List<ProOptTbl> proopt = prooptManagerService.findProOpt(params);

			params.put("useYn","Y");
			List<OptTbl> opts = optManagerService.findOpt(params);

			params.put("defaultYn","Y");
			OptTbl optTbl = optManagerService.getOpt(params);

			view.addObject("proflTbl", proflTbl);
			view.addObject("proopt", proopt);
			view.addObject("opts", opts);
			view.addObject("optTbl", optTbl);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 프로파일 상세보기를 할 수 없음", e);
		}


		return view;
	}

	@RequestMapping(value = "service/selectOpt.ssc", method = RequestMethod.POST)
	public ModelAndView selectOpt(@ModelAttribute("search") Search search,
			@RequestParam(value = "optId", required = false) String optId,
			ModelMap map) {




		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			params.put("optId", optId);

			OptTbl opt = optManagerService.getOpt(params);


			view.addObject("opt", opt);

			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 프로파일 옵션 상세보기를 할 수 없음", e);



		}

		return view;
	}

	@RequestMapping(value = "/service/selectbusipartner.ssc", method = RequestMethod.POST)
	public ModelAndView selectbusipartner(@ModelAttribute("search") Search search,
			@RequestParam(value = "busiPartnerid", required = false) String busiPartnerid,
			ModelMap map) {

		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
		}
		// 기간검색 설정
		if (search.getStartDt() != null)
			map.addAttribute("startDt",
					Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
		if (search.getEndDt() != null)
			map.addAttribute("endDt",
					Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));

		// 키워드 설정
		if (search.getKeyword() != null)
			map.addAttribute("ketword", search.getKeyword());


		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>"+busiPartnerid);

		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();

		try {

			params.put("busiPartnerid", busiPartnerid);



			BusiPartnerTbl busipartnerTbl = busipartnerManagerService.getBusiPartner(params);


			//params.put("useYn","Y");
			List<ProBusiTbl> probuis = probusiManagerService.findProBusi2(params);

			params.put("useYn","Y");
			List<ProFlTbl> profls = proflManagerService.findProFl(params);


			view.addObject("busipartnerTbl", busipartnerTbl);
			view.addObject("probuis", probuis);
			view.addObject("profls", profls);

			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 사업자 상세보기를 할 수 없음", e);
		}

		return view;
	}

	@RequestMapping(value = "/service/deleteProfileOpt.ssc", method = RequestMethod.POST)
	public ModelAndView deleteProfileOpt(@ModelAttribute("search") Search search,
			@RequestParam(value = "check", required = false) String check,

			ModelMap map) {

		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+check);

		ModelAndView view = new ModelAndView();

		String checks[] = check.split(",");

		map.addAttribute("menuId", 15);
		try {
			// prooptManagerService.insertProOpt(proopt);
			for (int i = 0; i < checks.length; i++) {
				OptTbl opt = new OptTbl();
				opt.setUseYn("N");
				opt.setDefaultYn("N");
				opt.setOptId(Integer.parseInt(checks[i]));

				optManagerService.deleteOpt(opt);

				view.addObject("search", search);
				view.setViewName("jsonView");
			}

		} catch (Exception e) {
			logger.error(" 프로파일 옵션 삭제을 할 수 없음", e);
		}

		return view;
	}

	@RequestMapping(value = "/service/deleteBusiness.ssc", method = RequestMethod.POST)
	public ModelAndView deleteBusiness(@ModelAttribute("search") Search search,
			@RequestParam(value = "check", required = false) String check,

			ModelMap map) {

		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+check);
		ModelAndView view = new ModelAndView();
		String checks[] = check.split(",");

		map.addAttribute("menuId", 15);
		try {
			// prooptManagerService.insertProOpt(proopt);
			for (int i = 0; i < checks.length; i++) {
				ProBusiTbl probusi = new ProBusiTbl();
				probusi.setBusiPartnerid(checks[i]);
				probusiManagerService.deleteProBusi(probusi);

				BusiPartnerTbl busipartner = new BusiPartnerTbl();
				busipartner.setServyn("N");
				busipartner.setBusiPartnerid(checks[i]);

				busipartnerManagerService.deleteBusiPartner(busipartner);

				view.addObject("search", search);
				view.setViewName("jsonView");
			}

		} catch (Exception e) {
			logger.error(" 사업자 삭제을 할 수 없음", e);
		}

		return view;
	}

	@RequestMapping(value = "/service/deleteProfile.ssc", method = RequestMethod.POST)
	public ModelAndView deleteProfile(@ModelAttribute("search") Search search,
			@RequestParam(value = "check", required = false) String check,

			ModelMap map) {

		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+check);

		ModelAndView view = new ModelAndView();

		String checks[] = check.split(",");

		map.addAttribute("menuId", 13);
		try {
			// prooptManagerService.insertProOpt(proopt);
			for (int i = 0; i < checks.length; i++) {
				ProFlTbl profl = new ProFlTbl();
				profl.setUseYn("N");
				profl.setProFlid(checks[i]);

				proflManagerService.deleteProFl(profl);

				view.addObject("search", search);
				view.setViewName("jsonView");
			}

		} catch (Exception e) {
			logger.error(" 프로파일 삭제을 할 수 없음", e);
		}

		return view;
	}
}
