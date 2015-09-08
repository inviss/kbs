package kr.co.d2net.dto.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ProgramInformation")
public class ProgramInformation {
	
	@XStreamAlias("OtherIdentifier")
	private List<String> otherIdentifier = new ArrayList<String>();
	
	@XStreamAlias("MemberOf")
	private MemberOf memberOf;

	public MemberOf getMemberOf() {
		return memberOf;
	}
	public void setMemberOf(MemberOf memberOf) {
		this.memberOf = memberOf;
	}
	public List<String> getOtherIdentifier() {
		return otherIdentifier;
	}
	public void setOtherIdentifier(List<String> otherIdentifier) {
		this.otherIdentifier = otherIdentifier;
	}
	public void addOtherIdentifier(String otherIdentifier) {
		this.otherIdentifier.add(otherIdentifier);
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
