package kr.co.d2net.dto.xml;

import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("GroupInformation")
public class GroupInformation {
	
	@XStreamAsAttribute
	@XStreamAlias("groupId")
	@XStreamConverter(TextConverter.class)
	private String groupId;
	
	@XStreamAlias("GroupType")
	@XStreamConverter(TextConverter.class)
	private String groupType;
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	@XStreamAlias("BasicDescription")
	private BasicDescription basicDescription;
	
	public BasicDescription getBasicDescription() {
		return basicDescription;
	}

	public void setBasicDescription(BasicDescription basicDescription) {
		this.basicDescription = basicDescription;
	}
	
}
