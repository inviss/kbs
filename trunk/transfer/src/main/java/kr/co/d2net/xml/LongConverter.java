package kr.co.d2net.xml;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class LongConverter implements Converter {
	
	private Logger logger = Logger.getLogger(LongConverter.class);

	public void marshal(Object obj, HierarchicalStreamWriter writer,
			MarshallingContext context) {

		if (obj != null){
			writer.setValue(String.valueOf(obj));
			if(logger.isDebugEnabled()) {
				logger.debug("marshal value: "+String.valueOf(obj));
			}
		} else { 
			writer.startNode(""); 
			writer.endNode();
		}
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		if ((reader.getValue() != null) && (!reader.getValue().equals(""))) { 
			if(logger.isDebugEnabled()) {
				logger.debug("unmarshal value: "+reader.getValue());
			}
			return Long.valueOf(reader.getValue()); 
		} else { 
			return Long.valueOf(0); 
		}
	}

	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class cls) {
		return cls.equals(Long.class); 
	}

}
