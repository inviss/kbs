package kr.co.d2net.commons.ext;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

public class ExtFreeMarkerViewResolver extends FreeMarkerViewResolver {
	public ExtFreeMarkerViewResolver() { 
		setViewClass( requiredViewClass() ); 
	} 

	/** 
	 * Requires {@link FreeMarkerView}. 
	 */ 
	@SuppressWarnings("rawtypes")
	protected Class requiredViewClass() { 
		return ExtFreeMarkerView.class; 
	}
}
