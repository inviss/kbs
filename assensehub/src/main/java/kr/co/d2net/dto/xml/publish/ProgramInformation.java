package kr.co.d2net.dto.xml.publish;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProgramInformation {

	@XStreamAlias("BasicDescription")
	private BasicDescription basicDescription;

	public BasicDescription getBasicDescription() {
		return basicDescription;
	}
	public void setBasicDescription(BasicDescription basicDescription) {
		this.basicDescription = basicDescription;
	}
	
	
}
