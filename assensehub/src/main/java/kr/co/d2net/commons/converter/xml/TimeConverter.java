package kr.co.d2net.commons.converter.xml;

import java.net.URLDecoder;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TimeConverter implements Converter {

	private Logger logger = Logger.getLogger(TimeConverter.class);
	
	public void marshal(Object obj, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		if (obj != null){ 
			try {
				writer.setValue(String.valueOf(obj));
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
		if ((reader.getValue() != null) && (!reader.getValue().equals(""))) { 
			try {
				o = java.sql.Time.valueOf(URLDecoder.decode(reader.getValue(), "UTF-8")); 
			} catch (Exception e) {
				logger.debug("Time Error value: "+reader.getValue());
			}
			
		}
		return o;
	}

	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class cls) {
		return cls.equals(java.sql.Time.class);
	}

}
