package kr.co.d2net.commons.converter.xml;

import kr.co.d2net.dto.xml.meta.Properties;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PropertyConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		return type.equals(Properties.class);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Properties properties = (Properties)source;
		writer.addAttribute("value", properties.getValue());  
		writer.addAttribute("type", properties.getType());
		writer.addAttribute("pid", properties.getPid());
		writer.addAttribute("name", properties.getName());
		writer.addAttribute("default", properties.getBase());
	    writer.setValue(properties.getEleValue());  
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Properties properties = new Properties();
		properties.setValue(reader.getAttribute("value"));
		properties.setPid(reader.getAttribute("pid"));
		properties.setType(reader.getAttribute("type"));
		properties.setName(reader.getAttribute("name"));
		properties.setBase(reader.getAttribute("default"));
		
		properties.setEleValue(reader.getValue());
		return properties;
	}

}
