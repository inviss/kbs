package kr.co.d2net.commons.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.d2net.commons.util.FreeMarkerTemplateMethod;
import kr.co.d2net.dto.UserTbl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FreeMarkerTemplateMethod freeMarkerTemplateMethod;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		UserTbl user = (UserTbl)WebUtils.getSessionAttribute(request, "user");
		if(user != null) {
			// 처리를 끝냄 - 컨트롤로 요청이 가지 않음.
			if(request.getAttribute("tpl") == null) {
				request.setAttribute("tpl", freeMarkerTemplateMethod);
			}
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		request.setAttribute("ctx", request.getContextPath());

		response.setHeader("Cache-Control","no-store"); //HTTP 1.1
		response.setHeader("Pragma","no-cache"); //HTTP 1.0
		response.setDateHeader ("Expires", 0);
	}
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		/*
		 Map<String, Object> tplMap = (Map<String, Object>) WebUtils.getSessionAttribute(request, "tpl");
			if (tplMap != null)
			tplMap.clear();
		 */
	}
	 
}
