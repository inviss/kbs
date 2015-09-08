package kr.co.d2net.commons.converter.xml;

import kr.co.d2net.dto.xml.Type;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TypeConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		// TODO Auto-generated method stub
		return type.equals(Type.class);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Type type = (Type)source;
		writer.addAttribute("type", type.getType());   
	    writer.setValue(type.getValue());  
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Type type = new Type();
		type.setType(reader.getAttribute(0));
		type.setValue(reader.getValue());
		return type;
	}

}
