package kr.co.d2net.commons.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.d2net.commons.util.FreeMarkerTemplateMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class PopupCheckInterceptor extends HandlerInterceptorAdapter {
	
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FreeMarkerTemplateMethod freeMarkerTemplateMethod;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Map<String, Object> tplMap = (Map<String, Object>)WebUtils.getSessionAttribute(request, "tpl");
		if (tplMap == null)
			tplMap = new HashMap<String, Object>();
		
		if (!tplMap.containsKey("tpl") || tplMap.get("tpl") == null) {
			freeMarkerTemplateMethod.init(tplMap);
		}
		
		request.setAttribute("tpl", tplMap);
		
		return true;
	}
}
