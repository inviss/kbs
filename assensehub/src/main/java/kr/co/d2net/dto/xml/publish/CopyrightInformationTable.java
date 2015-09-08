package kr.co.d2net.dto.xml.publish;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CopyrightInformationTable {
	
	@XmlElement(name="CopyrightInformation")
	private CopyrightInformation copyrightInformation;

	public CopyrightInformation getCopyrightInformation() {
		return copyrightInformation;
	}
	public void setCopyrightInformation(CopyrightInformation copyrightInformation) {
		this.copyrightInformation = copyrightInformation;
	}
	
}
