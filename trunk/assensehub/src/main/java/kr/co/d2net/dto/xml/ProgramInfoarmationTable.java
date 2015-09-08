package kr.co.d2net.dto.xml;

import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("ProgramInfoarmationTable")
public class ProgramInfoarmationTable {
	
	@XStreamAsAttribute
	@XStreamAlias("programId")
	@XStreamConverter(TextConverter.class)
	private String programId;
	
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}

	@XStreamAlias("ProgramInformation")
	private ProgramInformation programInformation;

	public ProgramInformation getProgramInformation() {
		return programInformation;
	}
	public void setProgramInformation(ProgramInformation programInformation) {
		this.programInformation = programInformation;
	}

}
