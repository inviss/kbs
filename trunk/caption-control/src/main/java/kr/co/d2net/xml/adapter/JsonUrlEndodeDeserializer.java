package kr.co.d2net.xml.adapter;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUrlEndodeDeserializer extends JsonDeserializer<String> {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public String deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		try {
			if(logger.isDebugEnabled()) {
				logger.debug(jp.getText());
			}
			if(StringUtils.isNotBlank(jp.getText())) {
				return URLDecoder.decode(jp.getText(), "utf-8");
			}
		} catch (Exception e) {
			logger.error("deserialize", e);
		}
		return null;
	}

}
