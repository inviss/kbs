package kr.co.d2net.dto.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ProgramDescription")
public class ProgramDescription {
	
	@XStreamAlias("GroupInformationTable")
	private GroupInformationTable groupInformationTable;

	public GroupInformationTable getGroupInformationTable() {
		return groupInformationTable;
	}

	public void setGroupInformationTable(GroupInformationTable groupInformationTable) {
		this.groupInformationTable = groupInformationTable;
	}
	
	@XStreamAlias("ProgramInfoarmationTable")
	private ProgramInfoarmationTable programInfoarmationTable;

	public ProgramInfoarmationTable getProgramInfoarmationTable() {
		return programInfoarmationTable;
	}

	public void setProgramInfoarmationTable(
			ProgramInfoarmationTable programInfoarmationTable) {
		this.programInfoarmationTable = programInfoarmationTable;
	}
	
	
}
