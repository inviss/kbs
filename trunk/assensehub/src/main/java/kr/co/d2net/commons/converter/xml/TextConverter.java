package kr.co.d2net.commons.converter.xml;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * 문자열에 대해서 URLEncode 처리를 하지않고 Null 처리만 담당하는 클래스
 */
public class TextConverter implements Converter {

	private Logger logger = Logger.getLogger(TextConverter.class);

	public void marshal(Object obj, HierarchicalStreamWriter writer,
			MarshallingContext context) {

		if (obj != null){
			if(logger.isDebugEnabled()) {
//				logger.debug("marshal value: "+String.valueOf(obj));
			}
			writer.setValue((String)obj);
		} else { 
			writer.startNode(""); 
			writer.endNode(); 
		}
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		if ( StringUtils.isNotBlank(reader.getValue())) {
			if(logger.isDebugEnabled()) {
//				logger.debug("unmarshal value: "+reader.getValue());
			}
			return reader.getValue(); 
		} else { 
			return ""; 
		}
	}

	public boolean canConvert(Class cls) {
		return cls.equals(String.class); 
	}

}
