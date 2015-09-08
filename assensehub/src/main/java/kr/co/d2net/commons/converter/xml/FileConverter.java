package kr.co.d2net.commons.converter.xml;

import kr.co.d2net.dto.xml.dmcr.File;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class FileConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		return type.equals(File.class);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		File file = (File)source;
		writer.addAttribute("isfolder", file.getIsfolder());  
		writer.addAttribute("source", file.getSource());
		writer.addAttribute("size", String.valueOf(file.getSize()));
	    writer.setValue(file.getValue());  
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		File file = new File();
		file.setIsfolder(reader.getAttribute("isfolder"));
		file.setSource(reader.getAttribute("source"));
		file.setSize(Long.valueOf(StringUtils.defaultIfEmpty(reader.getAttribute("size"), "0")));
		
		file.setValue(reader.getValue());
		return file;
	}

}
