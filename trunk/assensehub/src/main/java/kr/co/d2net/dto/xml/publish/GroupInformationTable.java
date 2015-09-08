package kr.co.d2net.dto.xml.publish;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class GroupInformationTable {
	
	@XmlElement(name="GroupInformation")
	private GroupInformation groupInformation;

	public GroupInformation getGroupInformation() {
		return groupInformation;
	}

	public void setGroupInformation(GroupInformation groupInformation) {
		this.groupInformation = groupInformation;
	}
	
}
