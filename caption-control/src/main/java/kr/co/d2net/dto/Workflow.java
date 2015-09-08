package kr.co.d2net.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kr.co.d2net.xml.adapter.JsonUrlEndodeDeserializer;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
@XmlRootElement(name="workflow")
@XmlAccessorType(XmlAccessType.FIELD)
public class Workflow {
	
	@XmlElement(name="system_ID")
	@JsonProperty("system_ID")
	private String systemId;
	
	@XmlElement(name="watchfolder")
	@JsonProperty("watchfolder")
	private String watchFolder;
	
	@XmlElement(name="cc_location")
	@JsonDeserialize(using=JsonUrlEndodeDeserializer.class)
	@JsonProperty("cc_location")
	private String ccLocation;
	
	@XmlElement(name="video_location")
	@JsonDeserialize(using=JsonUrlEndodeDeserializer.class)
	@JsonProperty("video_location")
	private String videoLocation;
	
	@XmlElement(name="program_id")
	@JsonProperty("program_id")
	private String programId;
	
	@XmlElement(name="cc_file_name")
	@JsonDeserialize(using=JsonUrlEndodeDeserializer.class)
	@JsonProperty("cc_file_name")
	private String ccFileName;
	
	@XmlElement(name="sync_cc_location")
	@JsonDeserialize(using=JsonUrlEndodeDeserializer.class)
	@JsonProperty("sync_cc_location")
	private String syncCcLocation;
	
	@XmlElement(name="upload_system")
	@JsonProperty("upload_system")
	private String uploadSystem;
	
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getWatchFolder() {
		return watchFolder;
	}
	public void setWatchFolder(String watchFolder) {
		this.watchFolder = watchFolder;
	}
	public String getCcLocation() {
		return ccLocation;
	}
	public void setCcLocation(String ccLocation) {
		this.ccLocation = ccLocation;
	}
	public String getVideoLocation() {
		return videoLocation;
	}
	public void setVideoLocation(String videoLocation) {
		this.videoLocation = videoLocation;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getCcFileName() {
		return ccFileName;
	}
	public void setCcFileName(String ccFileName) {
		this.ccFileName = ccFileName;
	}
	public String getSyncCcLocation() {
		return syncCcLocation;
	}
	public void setSyncCcLocation(String syncCcLocation) {
		this.syncCcLocation = syncCcLocation;
	}
	public String getUploadSystem() {
		return uploadSystem;
	}
	public void setUploadSystem(String uploadSystem) {
		this.uploadSystem = uploadSystem;
	}
	
	
}
