package kr.co.d2net.dto.xml.publish;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CopyrightDescription {
	
	@XmlElement(name="CopyrightInformationTable")
	private CopyrightInformationTable copyrightInformationTable;

	public CopyrightInformationTable getCopyrightInformationTable() {
		return copyrightInformationTable;
	}
	public void setCopyrightInformationTable(
			CopyrightInformationTable copyrightInformationTable) {
		this.copyrightInformationTable = copyrightInformationTable;
	}
	
}
