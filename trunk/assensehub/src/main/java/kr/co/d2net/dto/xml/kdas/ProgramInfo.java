package kr.co.d2net.dto.xml.kdas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PROGRAM_INFO")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProgramInfo {
	
	@XmlElement(name="PROGRAM_CODE")
	private String programCode;	//
	@XmlElement(name="PROGRAM_TITLE")
	private String programTitle;	//
	@XmlElement(name="PROGRAM_PLANNED_DURATION")
	private Integer programPlannedDuration;	//
	
	public String getProgramCode() {
		return programCode;
	}
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	public String getProgramTitle() {
		return programTitle;
	}
	public void setProgramTitle(String programTitle) {
		this.programTitle = programTitle;
	}
	public Integer getProgramPlannedDuration() {
		return programPlannedDuration;
	}
	public void setProgramPlannedDuration(Integer programPlannedDuration) {
		this.programPlannedDuration = programPlannedDuration;
	}
	
}
