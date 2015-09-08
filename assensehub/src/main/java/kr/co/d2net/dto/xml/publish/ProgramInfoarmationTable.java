package kr.co.d2net.dto.xml.publish;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProgramInfoarmationTable {

	@XmlElement(name="ProgramInformation")
	private ProgramInformation programInformation;

	public ProgramInformation getProgramInformation() {
		return programInformation;
	}
	public void setProgramInformation(ProgramInformation programInformation) {
		this.programInformation = programInformation;
	}

}
