package kr.co.d2net.commons.converter.xml;

import java.net.URLDecoder;
import java.sql.Timestamp;

import kr.co.d2net.commons.util.DateUtil;
import kr.co.d2net.commons.util.Utility;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TimestampConverter implements Converter {

	private Logger logger = Logger.getLogger(TimestampConverter.class);
	
	public void marshal(Object obj, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		if (obj != null){ 
			try {
				writer.setValue(Utility.getTimestamp((Timestamp)(obj),"yyyy-MM-dd HH:mm"));
			} catch (Exception e) {
			}
		} else { 
			writer.startNode(""); 
			writer.endNode();
		}
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Object o = null;
		if (StringUtils.isNotBlank(reader.getValue())) {
			try {
				if(reader.getValue().indexOf("-") > -1 && reader.getValue().indexOf(":") > -1) {
					o = java.sql.Timestamp.valueOf(URLDecoder.decode(reader.getValue(), "UTF-8")); 
				} else {
					o = DateUtil.convertToTimestampHMS(reader.getValue()); 
				}
			} catch (Exception e) {
				logger.error("TimeStamp Error value: "+reader.getValue());
			}
			
		}
		return o;
	}

	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class cls) {
		return cls.equals(java.sql.Timestamp.class);
	}

}
