package kr.co.d2net.xml;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * 문자열에 대해서 URLEncode 처리 및 Null 처리를 담당하는 클래스
 */
public class TextUTF8Converter implements Converter {

	private Logger logger = Logger.getLogger(TextUTF8Converter.class);

	public void marshal(Object obj, HierarchicalStreamWriter writer,
			MarshallingContext context) {

		if (obj != null){
			try {
				writer.setValue(unescapeXml(URLEncoder.encode(String.valueOf(obj), "UTF-8")));
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else { 
			writer.startNode(""); 
			writer.endNode(); 
		}
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		if ( StringUtils.isNotBlank(reader.getValue())) {
			try {
				return URLDecoder.decode(escapeXml(reader.getValue()), "UTF-8"); 
			} catch (Exception e) {
				return reader.getValue();
			}
			
		} else { 
			return ""; 
		}
	}

	public boolean canConvert(Class cls) {
		return cls.equals(String.class); 
	}
	
	private String escapeXml(String value) {
		// & has to be checked and replaced before others
		if (value.matches(".*&.*")) {
			value = value.replaceAll("&", "&amp;");
		}
		if (value.matches(".*'.*")) {
			value = value.replaceAll("\''", "&apos;");
		}
		if (value.matches(".*<.*")) {
			value = value.replaceAll("<", "&lt;");
		}
		if (value.matches(".*>.*")) {
			value = value.replaceAll(">", "&gt;");
		}
		if (value.matches(".*\".*")) {
			value = value.replaceAll("'", "&quot;");
		}
		return value;
	}

	private String unescapeXml(String value) {
		// & has to be checked and replaced before others
		if (value.matches(".*&amp;.*")) {
			value = value.replaceAll("&amp;", "&");
		}
		if (value.matches(".*&apos;.*")) {
			value = value.replaceAll("&apos;", "\'");
		}
		if (value.matches(".*&lt;.*")) {
			value = value.replaceAll("&lt;", "<");
		}
		if (value.matches(".*&gt;.*")) {
			value = value.replaceAll("&gt;", ">");
		}
		if (value.matches(".*&quot;.*")) {
			value = value.replaceAll("&quot;", "\"");
		}
		return value;
	}

}
