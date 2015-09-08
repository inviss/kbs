package kr.co.d2net.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
@XmlRootElement(name="digitalsystem")
@XmlAccessorType(XmlAccessType.FIELD)
public class Digitalsystem {
	
	@XmlElement(name="request_system_ID")
	@JsonProperty("request_system_ID")
	private String requestSystemId;
	
	@XmlElement(name="request_system_password")
	@JsonProperty("request_system_password")
	private String requestSystemPassword;
	
	@XmlElement(name="user_ID")
	@JsonProperty("user_ID")
	private String userId;
	
	@XmlElement(name="program_ID")
	@JsonProperty("program_ID")
	private String programId;
	
	@XmlElement(name="caption_language_kind")
	@JsonProperty("caption_language_kind")
	private String captionLanguageKind;
	
	@XmlElement(name="file_format")
	@JsonProperty("file_format")
	private String fileFormat;
	
	@XmlElement(name="caption_file_path")
	@JsonProperty("caption_file_path")
	private String captionFilePath;
	
	@XmlElement(name="copyright_special_note")
	@JsonProperty("copyright_special_note")
	private String copyrightSpecialNote;
	
	@XmlElement(name="caption_file_description")
	@JsonProperty("caption_file_description")
	private String captionFileDescription;
	
	@XmlElement(name="synchronized_YN")
	@JsonProperty("synchronized_YN")
	private String synchronizedYn;

	
	
	
	
	public String getRequestSystemId() {
		return requestSystemId;
	}

	public void setRequestSystemId(String requestSystemId) {
		this.requestSystemId = requestSystemId;
	}

	public String getRequestSystemPassword() {
		return requestSystemPassword;
	}

	public void setRequestSystemPassword(String requestSystemPassword) {
		this.requestSystemPassword = requestSystemPassword;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getCaptionLanguageKind() {
		return captionLanguageKind;
	}

	public void setCaptionLanguageKind(String captionLanguageKind) {
		this.captionLanguageKind = captionLanguageKind;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getCaptionFilePath() {
		return captionFilePath;
	}

	public void setCaptionFilePath(String captionFilePath) {
		this.captionFilePath = captionFilePath;
	}

	public String getCopyrightSpecialNote() {
		return copyrightSpecialNote;
	}

	public void setCopyrightSpecialNote(String copyrightSpecialNote) {
		this.copyrightSpecialNote = copyrightSpecialNote;
	}

	public String getCaptionFileDescription() {
		return captionFileDescription;
	}

	public void setCaptionFileDescription(String captionFileDescription) {
		this.captionFileDescription = captionFileDescription;
	}

	public String getSynchronizedYn() {
		return synchronizedYn;
	}

	public void setSynchronizedYn(String synchronizedYn) {
		this.synchronizedYn = synchronizedYn;
	}

}
