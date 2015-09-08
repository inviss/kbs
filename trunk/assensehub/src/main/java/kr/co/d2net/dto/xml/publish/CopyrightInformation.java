package kr.co.d2net.dto.xml.publish;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CopyrightInformation {

	@XmlElement(name="Copyright")
	private Type type;

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
}
