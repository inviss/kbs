package kr.co.d2net.dto.xml;

import kr.co.d2net.commons.converter.xml.TextConverter;

import com.sun.xml.txw2.annotation.XmlCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

public class Type {
	
	@XStreamAsAttribute
	@XStreamAlias("type")
	@XStreamConverter(TextConverter.class)
	private String type;
	
	private String value;
	
	@XmlCDATA
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
