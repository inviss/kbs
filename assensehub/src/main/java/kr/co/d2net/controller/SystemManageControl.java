package kr.co.d2net.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.AuthTbl;
import kr.co.d2net.dto.CodeTbl;
import kr.co.d2net.dto.EquipmentTbl;
import kr.co.d2net.dto.MenuTbl;
import kr.co.d2net.dto.OutSystemInfoTbl;
import kr.co.d2net.dto.RoleAuthTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.UserTbl;
import kr.co.d2net.service.CodeManagerService;
import kr.co.d2net.service.OutSystemInfoManagerService;
import kr.co.d2net.service.UserManagerService;
import kr.co.d2net.service.WorkflowManagerService;

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
import org.springframework.web.util.WebUtils;

@Controller
public class SystemManageControl {

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private UserManagerService userManagerService;
	@Autowired
	private OutSystemInfoManagerService outsysteminfoManagerService;
	@Autowired
	private WorkflowManagerService workflowManagerService;
	@Autowired
	private CodeManagerService codeManagerService;
	@Autowired
	private MessageSource messageSource;
	
	final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/system/Worker.ssc", method = RequestMethod.GET)
	public ModelMap findWorkers(@ModelAttribute("search") Search search,
			@ModelAttribute("userTbl") UserTbl userTbl, ModelMap map) {
		map.addAttribute("menuId", 21);
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			List<UserTbl> userList = userManagerService.findUsers(params);
			map.addAttribute("userList", userList);

			List<AuthTbl> authTbls = userManagerService.findAuths();
			map.addAttribute("authTbls", authTbls);

		} catch (ServiceException e) {
			logger.error("사용자 조회 에러", e);
		}

		map.addAttribute("search", search);
		map.addAttribute("userTbl", userTbl);

		return map;
	}

	@RequestMapping(value = "/system/addWorker.ssc", method = RequestMethod.POST)
	public String addWorker(@ModelAttribute("search") Search search,
			@ModelAttribute("userTbl") UserTbl userTbl, ModelMap map) {
		map.addAttribute("menuId", 21);
		try {
			userTbl.setUserPass(Utility.encodeMD5(userTbl.getUserPass()));
			userManagerService.insertUser(userTbl);
		} catch (ServiceException e) {
			logger.error("사용자 추가 에러", e);
		}

		return "redirect:/system/Worker.ssc";
	}

	@RequestMapping(value = "/system/updateWorker.ssc", method = RequestMethod.POST)
	public String updateWorker(@ModelAttribute("search") Search search,
			@ModelAttribute("userTbl") UserTbl userTbl, ModelMap map) {
		map.addAttribute("menuId", 21);
		
		try {
			userManagerService.updateUser(userTbl);
		} catch (ServiceException e) {
			logger.error("사용자 업데이트 에러", e);
		}

		return "redirect:/system/Worker.ssc";
	}

	@RequestMapping(value = "/system/deleteWorker.ssc", method = RequestMethod.POST)
	public String deleteWorker(@ModelAttribute("search") Search search,
			@ModelAttribute("userTbl") UserTbl userTbl, ModelMap map) {
		map.addAttribute("menuId", 21);
		try {
			userManagerService.deleteUser(userTbl.getUserIds());
		} catch (ServiceException e) {
			logger.error("사용자 삭제 에러", e);
		}

		return "redirect:/system/Worker.ssc";
	}

	@RequestMapping(value = "/system/workerDetail.ssc", method = RequestMethod.POST)
	public ModelAndView workerDetail(@ModelAttribute("search") Search search,
			@ModelAttribute("userTbl") UserTbl userTbl) {
		
		ModelAndView view = new ModelAndView();
		try {
			view.addObject("userTbl",
					userManagerService.getUser(userTbl.getUserId()));
			List<AuthTbl> authTbls = userManagerService.findAuths();
			view.addObject("authTbls", authTbls);

		} catch (ServiceException e) {
			logger.error("사용자 상세보기 에러", e);
		}
		view.setViewName("jsonView");
		return view;
	}

	@RequestMapping(value = "/system/Part.ssc", method = RequestMethod.GET)
	public ModelMap findRoleAuth(@ModelAttribute("search") Search search,
			ModelMap map) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", "asehub");

		try {

			List<MenuTbl> menus = userManagerService.findUserMenus(params);
			map.addAttribute("menus", menus);
			List<AuthTbl> authTbls = userManagerService.findAuths();
			map.addAttribute("authTbls", authTbls);
			map.addAttribute("search", search);

		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "/system/selectPart.ssc", method = RequestMethod.POST)
	public ModelAndView selectRoleAuth(@ModelAttribute("search") Search search,
			@RequestParam(value = "authid", required = false) String authid,
			ModelMap map) {

		// System.out.println(">>>>>>>>"+authid);
		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();

		try {
			params.put("authId", authid);
			List<RoleAuthTbl> roleauth = userManagerService
					.findRoleAuth(params);

			params2.put("userId", "asehub");

			List<MenuTbl> menus = userManagerService.findUserMenus(params2);

			map.addAttribute("menus", menus);
			map.addAttribute("roleauth", roleauth);
			view.setViewName("jsonView");

		} catch (ServiceException e) {
			logger.error(" 권한별 역할 상세보기를 할 수 없음", e);
		}
		return view;
	}

	@RequestMapping(value = "/system/OtherSystem.ssc", method = RequestMethod.GET)
	public ModelMap outSystemList(@ModelAttribute("search") Search search,
			ModelMap map) {
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setPageNo(1);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		String select = messageSource.getMessage("code.select.value", null, Locale.KOREA);
		String name = messageSource.getMessage("code.select.name", null, Locale.KOREA);
		String sv[]=select.split(",");
		String nm[]=name.split(",");
			
		try {
			PaginationSupport<OutSystemInfoTbl> contents = outsysteminfoManagerService
					.findOutSystemList(search);
			
			
			params.put("clfCd", "DECD");
			params.put("useYn","Y");
			List<CodeTbl> codetbls = codeManagerService.findCode(params);
			
			map.addAttribute("codetbls",codetbls);
			map.addAttribute("contents", contents);
			map.addAttribute("search", search);
		} catch (Exception e) {
			logger.error("타시스템 목록  에러", e);
		}

		map.addAttribute("search", search);
		return map;
	}
	
	@RequestMapping(value = "/system/Code.ssc", method = RequestMethod.GET)
	public ModelMap codeList(@ModelAttribute("search") Search search,
			ModelMap map) {
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("31");
		}
		
		String select = messageSource.getMessage("code.select.value", null, Locale.KOREA);
		String sv[]=select.split(",");
		// 구분코드
		if (search.getChannelCode() == null || search.getChannelCode() == "0"|| search.getChannelCode().equals("0")){
			search.setChannelCode(select);
		}else{
			map.addAttribute("channelCode", search.getChannelCode());
		}
		
		
		try {
			PaginationSupport<CodeTbl> codes = codeManagerService.findCodeListView(search);
			
			
			
			map.addAttribute("codes", codes);
			map.addAttribute("sv", sv);
			map.addAttribute("search", search);
		} catch (Exception e) {
			logger.error("Code 목록  에러", e);
		}

		map.addAttribute("search", search);
		return map;
	}
	
	@RequestMapping(value = "/system/insertCode.ssc", method = RequestMethod.GET)
	public ModelAndView insertcode(@ModelAttribute("search") Search search,
			ModelMap map) {
		
		String select = messageSource.getMessage("code.select.value", null, Locale.KOREA);
		String sv[]=select.split(",");
		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("useYn","Y");
			List<CodeTbl> clfcds = codeManagerService.findCodeClfCd(params);
			map.addAttribute("clfcds", clfcds);
			map.addAttribute("sv", sv);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error("Code 추가버튼  에러", e);
		}

		return view;
	}
	
	@RequestMapping(value = "/system/Code.ssc", method = RequestMethod.POST)
	public ModelMap codeSearchList(
			@ModelAttribute("search") Search search, ModelMap map) {
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			search.setMenuId("31");
		}

		// 키워드 설정
		if (search.getKeyword() != null)

			map.addAttribute("ketword", search.getKeyword());
		
		String select = messageSource.getMessage("code.select.value", null, Locale.KOREA);
		String name = messageSource.getMessage("code.select.name", null, Locale.KOREA);
		String sv[]=select.split(",");
		String nm[]=name.split(",");
		// 구분코드
		if (search.getChannelCode() == null || search.getChannelCode() == "0" || search.getChannelCode().equals("0")){
			search.setChannelCode(select);
		}else{
			map.addAttribute("channelCode", search.getChannelCode());
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		try {
			PaginationSupport<CodeTbl> codes = codeManagerService
			.findCodeListView(search);
			
			map.addAttribute("codes", codes);
			map.addAttribute("sv", sv);
			map.addAttribute("nm", nm);
			map.addAttribute("search", search);
		} catch (Exception e) {
			logger.error("Code 조회 목록  에러", e);
		}

		map.addAttribute("search", search);
		return map;
	}

	@RequestMapping(value = "/system/saveCode.ssc", method = RequestMethod.POST)
	public String saveCode(@ModelAttribute("search") Search search,
			@RequestParam(value = "clfNm", required = false) String clfNm,
			@RequestParam(value = "clfCd", required = false) String clfCd,
			@RequestParam(value = "sclCd", required = false) String sclCd,
			@RequestParam(value = "rmk1", required = false) String rmk1,
			@RequestParam(value = "rmk2", required = false) String rmk2,
			@RequestParam(value = "useYn", required = false) String useYn,
			ModelMap map) {

		Map<String, Object> params = new HashMap<String, Object>();
		
		try {
			
			
			
			CodeTbl code = new CodeTbl();
			
			String CD = clfCd.toUpperCase();
			
			System.out.println(">>>>>>>>>>>>>>>>"+CD);
			
			code.setClfCd(CD);
			code.setClfNm(clfNm);
			code.setSclCd(sclCd);
			code.setRmk1(rmk1);
			if(rmk2.equals("")||rmk2.equals(null)){
				
			}else{
				code.setRmk2(Integer.parseInt(rmk2));
			}
			code.setUseYn(useYn);
			
			codeManagerService.insertCode(code);
				
			
			map.addAttribute("menuId", 31);
		} catch (Exception e) {
			logger.error(" Code 추가를 할 수 없음", e);
		}

		return "redirect:/system/Code.ssc";
	}
	
	@RequestMapping(value = "/system/OtherSystem.ssc", method = RequestMethod.POST)
	public ModelMap outSystemSearchList(
			@ModelAttribute("search") Search search, ModelMap map) {
		// 페이지 설정
		if (search.getPageNo() == null || search.getPageNo() == 0) {
			search.setPageNo(1);
			
		}
		search.setMenuId("17");
		// 키워드 설정
		if (search.getKeyword() != null)

			map.addAttribute("ketword", search.getKeyword());
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		try {
			PaginationSupport<OutSystemInfoTbl> contents = outsysteminfoManagerService
					.findOutSystemList(search);
			
			params.put("clfCd", "DECD");
			params.put("useYn","Y");
			List<CodeTbl> codetbls = codeManagerService.findCode(params);
			
			map.addAttribute("codetbls",codetbls);
		
			map.addAttribute("contents", contents);
			map.addAttribute("search", search);
		} catch (Exception e) {
			logger.error("타시스템 조회 목록  에러", e);
		}

		map.addAttribute("search", search);
		return map;
	}

	@RequestMapping(value = "/system/insertOthersystem.ssc", method = RequestMethod.GET)
	public ModelAndView insertprofileopt(
			@ModelAttribute("search") Search search, ModelMap map) {

		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();
		
		try {
			
			params.put("clfCd", "DECD");
			params.put("useYn","Y");
			List<CodeTbl> codetbls = codeManagerService.findCode(params);
			
			
			view.addObject("codetbls",codetbls); 
			
			view.setViewName("jsonView");

		} catch (Exception e) {
			logger.error("타 시스템 추가버튼 레이어  에러", e);
		}

		return view;
	}
	
	@RequestMapping(value = "/system/selectDeviceEqps.ssc", method = RequestMethod.POST)
	public ModelAndView selectDeviceEqps(
			@RequestParam(value = "device", required = false) String device,
			@ModelAttribute("search") Search search, ModelMap map) {

		//System.out.println(">>>>>>>>>>>>>>>>>>"+device);	
		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			
			params.put("sclCd", device);
			CodeTbl code = codeManagerService.getCode(params);
			
			view.addObject("code", code);
			view.setViewName("jsonView");

		} catch (Exception e) {
			logger.error("장비 추가 & 인스턴스 추가 선택  에러", e);
		}

		return view;
	}
	
	@RequestMapping(value = "/system/saveOthersystem.ssc", method = RequestMethod.POST)
	public String saveOthersystem(@ModelAttribute("search") Search search,
			@RequestParam(value = "deviceNm", required = false) String deviceNm,
			@RequestParam(value = "device", required = false) String device,
			@RequestParam(value = "deviceIp", required = false) String deviceIp,
			@RequestParam(value = "devicePort", required = false) String devicePort,
			ModelMap map) {

		Map<String, Object> params = new HashMap<String, Object>();
		
		try {
			
			params.put("sclCd", device);
			CodeTbl code = codeManagerService.getCode(params);
			//System.out.println(">>>>>>>>>>"+code.getRmk2());
			//System.out.println(">>>>>>>>>>"+code.getRmk2()+1);
			EquipmentTbl eq = new EquipmentTbl();

			eq.setDeviceNm(deviceNm);
			//eq.setDeviceid(device);
			eq.setDeviceIp(deviceIp);
			eq.setDevicePort(devicePort);
			eq.setUseYn("Y");
			eq.setWorkStatcd("000");
			
			//System.out.println(">>>>>>>>>>>>>"+"0"+code.getRmk2()+1);
			
			int co=code.getRmk2()+1;
			String co2= String.valueOf(co);
			
			System.out.println(">>>>>>>>>>>>>>>>>>>>"+co2);
			System.out.println(">>>>>>>>>>>>>>>>>>>>"+co2.length());
			if(co2.length()==2){
				//System.out.println(">>>>>>>>>>>>>>>>>>>>");
				eq.setDeviceid(device+co);
			}else{
				//System.out.println("==========================");
				eq.setDeviceid(device+"0"+co);
				
			}
			
			eq.setEqPsCd("01");
			eq.setPrgrs("0");
			workflowManagerService.insertEquipment(eq);
			
			CodeTbl code2 = new CodeTbl();
			code2.setClfCd("DECD");
			code2.setSclCd(device);
			code2.setRmk2(co);
			codeManagerService.updateCode(code2);	
			
			map.addAttribute("menuId", 23);
		} catch (Exception e) {
			logger.error(" 장비 추가를 할 수 없음", e);
		}

		return "redirect:/system/OtherSystem.ssc";
	}
	
	@RequestMapping(value = "/system/saveinstance.ssc", method = RequestMethod.POST)
	public String saveinstance(@ModelAttribute("search") Search search, 
			@RequestParam(value = "deviceNm", required = false) String deviceNm,
			@RequestParam(value = "device", required = false) String device,
			ModelMap map) {

		Map<String, Object> params = new HashMap<String, Object>();
		
		String d_id[]=deviceNm.split(",");
		
		try {
			
			int co=Integer.parseInt(d_id[1]);
			String co2=String.valueOf(co);
			
			
			if(co2.length() == 2){
				
				params.put("deviceid", d_id[0]+co);
			}else{
				params.put("deviceid", d_id[0]+"0"+co);
				
			}
			
			
			EquipmentTbl code = workflowManagerService.getEquipmentCount(params);
			
			int count=code.getCount();
			int cou=code.getCount()+1;
			String count2=String.valueOf(count);
			String cou2=String.valueOf(cou);
			
			System.out.println("cou="+cou);
			if(count2.length() == 2){
				
				params.put("eqPsCd", count);
			}else{
				params.put("eqPsCd", "0"+count);
				
			}
			
			EquipmentTbl equipment = workflowManagerService.getEquipment(params);
			
			
			String nm="";
			if(d_id[0].equals("TC")){
				nm="트랜스코더";
			}else if(d_id[0].equals("AC")){
				nm="오디오 트랜스코더";
			}else if(d_id[0].equals("LI")){
				nm="라이브 인코딩";
			}else if(d_id[0].equals("TF")){
				nm="트랜스퍼 서버";
			}else{
				nm="트리밍 서버";
			}
			
			EquipmentTbl eq = new EquipmentTbl();

			eq.setDeviceNm(nm+" #"+co+"_"+cou);
			if(co2.length() == 2){
				
				eq.setDeviceid(d_id[0]+co);
			}else{
				eq.setDeviceid(d_id[0]+"0"+co);
				
			}
			
			eq.setDeviceIp(equipment.getDeviceIp());
			eq.setDevicePort(equipment.getDevicePort());
			eq.setUseYn("Y");
			eq.setWorkStatcd("000");
			eq.setPrgrs("0");
			if(cou2.length() == 2){
				System.out.println("1");
				eq.setEqPsCd(String.valueOf(cou));
			}else{
				System.out.println("2");
				eq.setEqPsCd(String.valueOf("0"+cou));
				
			}
			map.addAttribute("menuId", 23);
			
			workflowManagerService.insertEquipment(eq);

		} catch (Exception e) {
			logger.error("인스턴스 추가를 할 수 없음", e);
		}

		return "redirect:/system/OtherSystem.ssc";
	}

	@RequestMapping(value = "/system/selectOther.ssc", method = RequestMethod.POST)
	public ModelAndView selectOsi(@ModelAttribute("search") Search search,
			@RequestParam(value = "id", required = false) String id,
			ModelMap map) {
		
		String ids[] = id.split("/");
		
		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			params.put("deviceid",ids[0]);
			params.put("eqPsCd",ids[1]);

			EquipmentTbl eq = workflowManagerService.getEquipment(params);

			view.addObject("eq", eq);

			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 타 시스템 상세보기를 할 수 없음", e);

		}

		return view;
	}

	@RequestMapping(value = "/system/selectCode.ssc", method = RequestMethod.POST)
	public ModelAndView selectCode(@ModelAttribute("search") Search search,
			@RequestParam(value = "id", required = false) String id,
			ModelMap map) {
		
		String ids[] = id.split("/");
		
		ModelAndView view = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();
		String select = messageSource.getMessage("code.select.value", null, Locale.KOREA);
		String sv[]=select.split(",");
		try {
			params.put("clfCd",ids[0]);
			params.put("sclCd",ids[1]);

			
			CodeTbl code = codeManagerService.getCode(params);
			
			view.addObject("code", code);
			view.addObject("sv", sv);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" Code 상세보기를 할 수 없음", e);

		}

		return view;
	}
	
	@RequestMapping(value = "/system/updateOsi.ssc", method = RequestMethod.POST)
	public ModelAndView updateOsi(@ModelAttribute("search") Search search,
			@RequestParam(value = "deviceNm", required = false) String deviceNm,
			@RequestParam(value = "deviceid", required = false) String deviceid,
			@RequestParam(value = "eqPsCd", required = false) String eqPsCd,
			@RequestParam(value = "deviceIp", required = false) String deviceIp,
			@RequestParam(value = "devicePort", required = false) String devicePort,

			ModelMap map) {

		ModelAndView view = new ModelAndView();
		
		EquipmentTbl eq = new EquipmentTbl();

		eq.setDeviceNm(deviceNm);
		eq.setDeviceid(deviceid);
		eq.setEqPsCd(eqPsCd);
		eq.setDeviceIp(deviceIp);
		eq.setDevicePort(devicePort);
		eq.setUseYn("Y");

		map.addAttribute("menuId", 23);
		map.addAttribute("search", search);
		try {

			workflowManagerService.updateEquipmentState(eq);

			view.addObject("search", search);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 타시스템 수정을 할 수 없음", e);
		}

		return view;
	}
	
	@RequestMapping(value = "/system/updateCode.ssc", method = RequestMethod.POST)
	public ModelAndView updateCode(@ModelAttribute("search") Search search,
			@RequestParam(value = "clfNm", required = false) String clfNm,
			@RequestParam(value = "clfCd", required = false) String clfCd,
			@RequestParam(value = "sclCd", required = false) String sclCd,
			@RequestParam(value = "rmk1", required = false) String rmk1,
			@RequestParam(value = "rmk2", required = false) String rmk2,
			@RequestParam(value = "useYn", required = false) String useYn,
			ModelMap map) {

		
		System.out.println(">>>>>>>>>>>>>>>>>>>");
		System.out.println(">>>>>>>>>>>>>>>>>>>"+clfNm);
		System.out.println(">>>>>>>>>>>>>>>>>>>"+clfCd);
		System.out.println(">>>>>>>>>>>>>>>>>>>"+sclCd);
		System.out.println(">>>>>>>>>>>>>>>>>>>"+rmk1);
		System.out.println(">>>>>>>>>>>>>>>>>>>"+rmk2);
		System.out.println(">>>>>>>>>>>>>>>>>>>"+useYn);
		
		ModelAndView view = new ModelAndView();
		CodeTbl code = new CodeTbl();
		
		code.setClfCd(clfCd);
		code.setClfNm(clfNm);
		code.setSclCd(sclCd);
		code.setRmk1(rmk1);
		if(rmk2.equals("")||rmk2.equals(null)){
			
		}else{
			code.setRmk2(Integer.parseInt(rmk2));
		}
		code.setUseYn(useYn);

		map.addAttribute("menuId", 31);
		map.addAttribute("search", search);
		try {

			codeManagerService.updateCode(code);

			view.addObject("search", search);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" Code 수정을 할 수 없음", e);
		}

		return view;
	}

	@RequestMapping(value = "/system/deleteOsi.ssc", method = RequestMethod.POST)
	public ModelAndView deleteOsi(@ModelAttribute("search") Search search,
			@RequestParam(value = "check", required = false) String check,

			ModelMap map) {

		ModelAndView view = new ModelAndView();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String checks[] = check.split(",");
		
		//for(int i=0;i<=check.length();i++){
		//	System.out.println(checks[i]);
		//}
		map.addAttribute("menuId", 23);
		map.addAttribute("search", search);
		try {
			// prooptManagerService.insertProOpt(proopt);
			for (int i = 0; i < checks.length; i++) {
				EquipmentTbl equ = new EquipmentTbl();
				
				String checks2[]=checks[i].split("/");
				
				equ.setDeviceid(checks2[0]);
				equ.setEqPsCd(checks2[1]);
				equ.setUseYn("N");
				
				if(checks2[1].equals("01")){
					workflowManagerService.instdeleteEquipment(equ);
				
					//String rmk=checks2[0];
					//String rmk2=rmk.substring(0,2);
					
					//System.out.println(">>>>>>>>>>>>>>>>>"+rmk2);
					
					
					//params.put("sclCd", rmk2);
					//CodeTbl code = codeManagerService.getCode(params);
					//int co=code.getRmk2()-1;
					
					//CodeTbl code2 = new CodeTbl();
					//code2.setClfCd("DECD");
					//code2.setSclCd(rmk2);
					//code2.setRmk2(co);
					
					//codeManagerService.updateCode(code2);
					
				}else{
					workflowManagerService.instdeleteEquipment(equ);
				}
				
			}

			view.addObject("search", search);
			view.setViewName("jsonView");
		} catch (Exception e) {
			logger.error(" 타시스템 삭제을 할 수 없음", e);
		}

		return view;
	}

	@RequestMapping(value = "/system/WorkerCheck.ssc", method = RequestMethod.POST)
	public ModelAndView checkWorker(@ModelAttribute("search") Search search) {

		search.setPageNo(1);

		ModelAndView view = new ModelAndView();

		UserTbl user = new UserTbl();
		user.setUserId("asenhub");
		view.addObject("user", user);

		view.addObject("result", "Y");

		view.addObject("search", search);

		view.setViewName("jsonView");
		// view.setViewName("system/WorkerTest");
		return view;
	}

	@RequestMapping(value = "/system/WorkerCheck.ssc", method = RequestMethod.GET)
	public ModelAndView checkWorkerGet(@ModelAttribute("search") Search search) {

		search.setPageNo(1);

		ModelAndView view = new ModelAndView();

		UserTbl user = new UserTbl();
		user.setUserId("asenhub");
		view.addObject("user", user);

		view.addObject("result", "Y");

		view.addObject("search", search);

		// view.setViewName("jsonView");
		view.setViewName("system/WorkerTest");
		return view;
	}

	@RequestMapping(value = "/system/updatePart.ssc", method = RequestMethod.POST)
	public String updateRoleAuth(@ModelAttribute("search") Search search,
			HttpServletRequest request,
			@RequestParam(value = "authid", required = false) String authid,
			ModelMap map) {

		RoleAuthTbl roleauth2 = new RoleAuthTbl();

		roleauth2.setAuthId(Integer.parseInt(authid));

		map.addAttribute("menuId", 22);
		try {

			userManagerService.deleteRoleAuth(roleauth2);

			RoleAuthTbl roleauth = null;

			for (int i = 2; i <= 31; i++) {

				String menuAuth = StringUtils.defaultString(
						WebUtils.findParameterValue(request, "a" + i), "N");
				System.out.println("auth: " + menuAuth);
				if (!menuAuth.equals("N")) {
					roleauth = new RoleAuthTbl();

					roleauth.setMenuId(i);
					roleauth.setControlGubun(menuAuth);
					roleauth.setAuthId(Integer.parseInt(authid));

					userManagerService.insertRoleAuth(roleauth);
				}
			}

			// RoleAuthTbl roleauth3= new RoleAuthTbl();

			// userManagerService.deleteRoleAuth2(roleauth3);
		} catch (Exception e) {
			logger.error(" 역할을 저장 할 수 없음", e);
		}

		return "redirect:/system/Part.ssc";
	}
	
	
}
