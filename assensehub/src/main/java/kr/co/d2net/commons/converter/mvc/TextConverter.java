package kr.co.d2net.commons.converter.mvc;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

public class TextConverter implements Converter<String, String> {
	
	final Logger logger = LoggerFactory.getLogger(TextConverter.class);
			
	@Override
	public String convert(String source) {
		if(logger.isDebugEnabled()) {
			logger.debug("MVC Converter is String value: "+source);
		}
		return (StringUtils.isBlank(source)) ? "" : source;
	}

}
