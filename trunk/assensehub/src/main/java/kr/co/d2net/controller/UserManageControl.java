package kr.co.d2net.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.MenuTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.UserTbl;
import kr.co.d2net.service.UserManagerService;

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
public class UserManageControl {

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private UserManagerService userManagerService;

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@RequestMapping(value="/member/login.ssc", method = RequestMethod.GET)
	public ModelAndView userLogin(HttpServletRequest request, ModelMap map) {
		Object o = WebUtils.getSessionAttribute(request, "user");
		// 세션이 존재 첫번째 페이지로 강제이동 시킴
		if(o != null) {
			UserTbl user = null;
			try {
				user = (UserTbl)o;
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", user.getUserId());
				MenuTbl menu = userManagerService.getFirstMenu(params);
				
				return new ModelAndView("redirect:"+menu.getUrl());
			} catch (Exception e) {
				logger.error(user.getUserId()+" - 사용자를 조회할 수 없음", e);
				WebUtils.setSessionAttribute(request, "user", null);
			}
		}
		
		ModelAndView view = new ModelAndView();
		view.setViewName("member/login");
		return view;
	}
	
	@RequestMapping(value="/member/login.ssc", method = RequestMethod.POST)
	public String userLoginAction(HttpServletRequest request, @RequestParam("userId") String userId, 
			@RequestParam("userPass") String userPass) {
		
		
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(userPass)) {
			return "redirect:/member/login.ssc"; 
		}
		
		UserTbl user = null;
		
		Map<String, Object> params = new HashMap<String, Object>();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		params.put("userId", userId);
		params.put("userPass", Utility.encodeMD5(userPass));
		
		try {
			// 사용자 정보 조회
			user = userManagerService.getUserAuth(params);
			
			if(user != null) {
				user.setPassword(userPass);

				// 사용자 접근 가능메뉴 조회
				List<MenuTbl> menus = userManagerService.findUserMenus(params);
				user.setMenus(menus);

				// 세션 저장
				WebUtils.setSessionAttribute(request, "user", user);
				
				// 사용자의 첫번째 페이지로 이동
				MenuTbl menu = userManagerService.getFirstMenu(params);
				logger.debug("url: "+menu.getUrl());
				return "redirect:"+menu.getUrl();
			} else {
				logger.error(userId+" - 사용자를 조회할 수 없음");
				
				return "redirect:/member/login.ssc";
			}
		} catch (ServiceException e) {
			logger.error(userId+" - 사용자를 조회할 수 없음", e);
			
			return "redirect:/member/login.ssc";
		}
	}

	@RequestMapping(value = "/edit/list.ssc", method = RequestMethod.GET)
	public ModelMap editList(@ModelAttribute("search") Search search,
			ModelMap map) {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			List<UserTbl> userList = userManagerService.findUsers(params);
		} catch (ServiceException e) {
			logger.error("사용자 조회 에러", e);
		}


		return map;
	}

	@RequestMapping(value = "/edit/view.ssc", method = RequestMethod.GET)
	public ModelMap editView(ModelMap map) {
		return map;
	}


	@RequestMapping(value = "/member/loginout.ssc", method = RequestMethod.POST)
	public String loginout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		//map.addAttribute("pageNo", 1);s
		return "redirect:/member/login.ssc";
	}

}
