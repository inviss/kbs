package kr.co.d2net.dto.xml.publish;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProgramDescription {
	
	@XmlElement(name="GroupInformationTable")
	private GroupInformationTable groupInformationTable;

	public GroupInformationTable getGroupInformationTable() {
		return groupInformationTable;
	}

	public void setGroupInformationTable(GroupInformationTable groupInformationTable) {
		this.groupInformationTable = groupInformationTable;
	}
	
	@XmlElement(name="ProgramInfoarmationTable")
	private ProgramInfoarmationTable programInfoarmationTable;

	public ProgramInfoarmationTable getProgramInfoarmationTable() {
		return programInfoarmationTable;
	}

	public void setProgramInfoarmationTable(
			ProgramInfoarmationTable programInfoarmationTable) {
		this.programInfoarmationTable = programInfoarmationTable;
	}
	
	
}
