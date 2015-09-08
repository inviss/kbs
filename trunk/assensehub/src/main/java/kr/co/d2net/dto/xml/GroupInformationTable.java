package kr.co.d2net.dto.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("GroupInformationTable")
public class GroupInformationTable {
	
	@XStreamAlias("GroupInformation")
	private GroupInformation groupInformation;

	public GroupInformation getGroupInformation() {
		return groupInformation;
	}

	public void setGroupInformation(GroupInformation groupInformation) {
		this.groupInformation = groupInformation;
	}
	
}
