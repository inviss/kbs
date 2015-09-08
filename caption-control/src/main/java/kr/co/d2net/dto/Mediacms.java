package kr.co.d2net.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
@XmlRootElement(name="mediacms")
@XmlAccessorType(XmlAccessType.FIELD)
public class Mediacms {
	
	@XmlElement(name="program_id")
	@JsonProperty("program_id")
	private String programId;
	
	@XmlElement(name="segment_id")
	@JsonProperty("segment_id")
	private String segmentId;
	
	@XmlElement(name="caption_language_code")
	@JsonProperty("caption_language_code")
	private String captionLanguageCode;
	
	@XmlElement(name="file_format")
	@JsonProperty("file_format")
	private String fileFormat;
	
	@XmlElement(name="caption_version")
	@JsonProperty("caption_version")
	private String captionVersion;
	
	@XmlElement(name="copyright_special_note")
	@JsonProperty("copyright_special_note")
	private String copyrightSpecialNote;
	
	@XmlElement(name="caption_file_full_path")
	@JsonProperty("caption_file_full_path")
	private String captionFileFullPath;
	
	@XmlElement(name="caption_max_width")
	@JsonProperty("caption_max_width")
	private String captionMaxWidth;
	
	@XmlElement(name="caption_max_height")
	@JsonProperty("caption_max_height")
	private String captionMaxHeight;

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}

	public String getCaptionLanguageCode() {
		return captionLanguageCode;
	}

	public void setCaptionLanguageCode(String captionLanguageCode) {
		this.captionLanguageCode = captionLanguageCode;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getCaptionVersion() {
		return captionVersion;
	}

	public void setCaptionVersion(String captionVersion) {
		this.captionVersion = captionVersion;
	}

	public String getCopyrightSpecialNote() {
		return copyrightSpecialNote;
	}

	public void setCopyrightSpecialNote(String copyrightSpecialNote) {
		this.copyrightSpecialNote = copyrightSpecialNote;
	}

	public String getCaptionFileFullPath() {
		return captionFileFullPath;
	}

	public void setCaptionFileFullPath(String captionFileFullPath) {
		this.captionFileFullPath = captionFileFullPath;
	}

	public String getCaptionMaxWidth() {
		return captionMaxWidth;
	}

	public void setCaptionMaxWidth(String captionMaxWidth) {
		this.captionMaxWidth = captionMaxWidth;
	}

	public String getCaptionMaxHeight() {
		return captionMaxHeight;
	}

	public void setCaptionMaxHeight(String captionMaxHeight) {
		this.captionMaxHeight = captionMaxHeight;
	}
	
	
}
