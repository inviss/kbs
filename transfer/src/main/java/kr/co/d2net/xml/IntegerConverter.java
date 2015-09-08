package kr.co.d2net.xml;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class IntegerConverter implements Converter {

	private Logger logger = Logger.getLogger(IntegerConverter.class);

	public void marshal(Object obj, HierarchicalStreamWriter writer,
			MarshallingContext context) {

		if (obj != null){
			writer.setValue(String.valueOf(obj));
		} else { 
			writer.startNode(""); 
			writer.endNode(); 
		}
		if(logger.isDebugEnabled()) {
			logger.debug("Integer value: "+obj);
		}
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Integer value: "+reader.getValue());
		}
		
		if ((reader.getValue() != null) && (!reader.getValue().equals(""))) { 
			return Integer.valueOf(reader.getValue()); 
		} else { 
			return Integer.valueOf(0); 
		}
	}

	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class cls) {
		return cls.equals(Integer.class); 
	}

}
