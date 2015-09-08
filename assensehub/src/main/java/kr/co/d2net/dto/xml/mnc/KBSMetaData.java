package kr.co.d2net.dto.xml.mnc;

import kr.co.d2net.commons.converter.xml.IntegerConverter;
import kr.co.d2net.commons.converter.xml.LongConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.dto.xml.aach.Corners;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("KBS_Exchange_Metadata")
public class KBSMetaData {
	
	@XStreamAlias("clip_ID")
	@XStreamConverter(TextConverter.class)
	private String clipId;
	
	@XStreamAlias("program_ID")
	@XStreamConverter(TextConverter.class)
	private String programId;
	
	@XStreamAlias("clip_title")
	@XStreamConverter(TextConverter.class)
	private String clipTitle;
	
	@XStreamAlias("program_title")
	@XStreamConverter(TextConverter.class)
	private String programTitle;
	
	@XStreamAlias("program_subtitle")
	@XStreamConverter(TextConverter.class)
	private String programSubtitle;
	
	@XStreamAlias("clip_duration")
	@XStreamConverter(TextConverter.class)
	private String clipDuration;
	
	@XStreamAlias("program_genre")
	@XStreamConverter(TextConverter.class)
	private String programGenre;
	
	@XStreamAlias("program_sequence_number")
	@XStreamConverter(IntegerConverter.class)
	private Integer programSequenceNumber;
	
	@XStreamAlias("production_department_name")
	@XStreamConverter(TextConverter.class)
	private String productionDepartmentName;
	
	@XStreamAlias("producer_reporter_role")
	@XStreamConverter(TextConverter.class)
	private String producerReporterRole;
	
	@XStreamAlias("employee_ID")
	@XStreamConverter(TextConverter.class)
	private String employeeId;
	
	@XStreamAlias("name_korean")
	@XStreamConverter(TextConverter.class)
	private String namdKorean;
	
	@XStreamAlias("telephone_number")
	@XStreamConverter(TextConverter.class)
	private String telephoneNumber;
	
	@XStreamAlias("broadcast_planned_date")
	@XStreamConverter(TextConverter.class)
	private String broadcastPlannedDate;
	
	@XStreamAlias("staff_role")
	@XStreamConverter(TextConverter.class)
	private String staffRole;
	
	@XStreamAlias("shooting_date_time")
	@XStreamConverter(TextConverter.class)
	private String shootingDateTime;
	
	@XStreamAlias("shooting_place")
	@XStreamConverter(TextConverter.class)
	private String shootingPlace;
	
	@XStreamAlias("camera_man")
	@XStreamConverter(TextConverter.class)
	private String cameraMan;
	
	@XStreamAlias("video_quality_condition")
	@XStreamConverter(TextConverter.class)
	private String videoQualityCondition;
	
	@XStreamAlias("approval_YN")
	@XStreamConverter(TextConverter.class)
	private String approvalYn;
	
	@XStreamAlias("production_video_quality")
	@XStreamConverter(TextConverter.class)
	private String productionVideoQuality;
	
	@XStreamAlias("source_media_type")
	@XStreamConverter(TextConverter.class)
	private String sourceMediaType;
	
	@XStreamAlias("video_acquisition_classification")
	@XStreamConverter(TextConverter.class)
	private String videoAcquisitionClassification;
	
	@XStreamAlias("usage_notice")
	@XStreamConverter(TextConverter.class)
	private String usageNotice;
	
	@XStreamAlias("clip_edited_status")
	@XStreamConverter(TextConverter.class)
	private String clipEditedStatus;
	
	@XStreamAlias("keyword")
	@XStreamConverter(TextConverter.class)
	private String keyword;
	
	@XStreamAlias("clip_contents")
	@XStreamConverter(TextConverter.class)
	private String clipContents;
	
	@XStreamAlias("ingest_date_time")
	@XStreamConverter(TextConverter.class)
	private String ingestDateTime;
	
	@XStreamAlias("clip_source_system")
	@XStreamConverter(TextConverter.class)
	private String clipSourceSystem;
	
	@XStreamAlias("file_size")
	@XStreamConverter(LongConverter.class)
	private Long fileSize;
	
	@XStreamAlias("video_codec")
	@XStreamConverter(TextConverter.class)
	private String videoCodec;
	
	@XStreamAlias("aspect_ratio")
	@XStreamConverter(TextConverter.class)
	private String aspectRatio;
	
	@XStreamAlias("video_frame_per_second")
	@XStreamConverter(TextConverter.class)
	private String videoFramePerSecond;
	
	@XStreamAlias("video_resolution")
	@XStreamConverter(TextConverter.class)
	private String videoResolution;
	
	@XStreamAlias("video_bandwidth")
	@XStreamConverter(TextConverter.class)
	private String videoBandwidth;
	
	@XStreamAlias("audio_mode_main")
	@XStreamConverter(TextConverter.class)
	private String audioModeMain;
	
	@XStreamAlias("audio_codec")
	@XStreamConverter(TextConverter.class)
	private String audioCodec;
	
	@XStreamAlias("audio_channel_count")
	@XStreamConverter(TextConverter.class)
	private String audioChannelCount;
	
	@XStreamAlias("audio_sampling_rate")
	@XStreamConverter(TextConverter.class)
	private String audioSamplingRate;
	
	@XStreamAlias("audio_bandwidth")
	@XStreamConverter(TextConverter.class)
	private String audioBandwidth;

	@XStreamAlias("program_code")
	@XStreamConverter(TextConverter.class)
	private String programCode;
	
	/*
	 * 오디오 아카이브 메타태그와 NPS의 메타태그가 중복이기때문에
	 * 오디오 아카이브 메타태그를 추가하여 사용함 2013.06.01
	 */
	@XStreamAlias("channel_cd")
	@XStreamConverter(TextConverter.class)
	private String channelCd;
	@XStreamAlias("program_onair_start_time")
	@XStreamConverter(TextConverter.class)
	private String programOnairStartTime;
	@XStreamAlias("program_onair_end_time")
	@XStreamConverter(TextConverter.class)
	private String programOnairEndTime;
	@XStreamAlias("broadcast_event_kind")
	@XStreamConverter(TextConverter.class)
	private String broadcastEventKind;
	@XStreamAlias("production_duration")
	@XStreamConverter(TextConverter.class)
	private String productionDuration;
	@XStreamAlias("programming_local_station_code")
	@XStreamConverter(TextConverter.class)
	private String programmingLocalStationCode;
	@XStreamAlias("clip_SIZE")
	@XStreamConverter(LongConverter.class)
	private Long clipSize;
	@XStreamAlias("ct_cla")
	@XStreamConverter(TextConverter.class)
	private String ctCla;
	
	
	public String getCtCla() {
		return ctCla;
	}

	public void setCtCla(String ctCla) {
		this.ctCla = ctCla;
	}

	public String getChannelCd() {
		return channelCd;
	}

	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}

	public String getProgramOnairStartTime() {
		return programOnairStartTime;
	}

	public void setProgramOnairStartTime(String programOnairStartTime) {
		this.programOnairStartTime = programOnairStartTime;
	}

	public String getProgramOnairEndTime() {
		return programOnairEndTime;
	}

	public void setProgramOnairEndTime(String programOnairEndTime) {
		this.programOnairEndTime = programOnairEndTime;
	}

	public String getBroadcastEventKind() {
		return broadcastEventKind;
	}

	public void setBroadcastEventKind(String broadcastEventKind) {
		this.broadcastEventKind = broadcastEventKind;
	}

	public String getProductionDuration() {
		return productionDuration;
	}

	public void setProductionDuration(String productionDuration) {
		this.productionDuration = productionDuration;
	}

	public String getProgrammingLocalStationCode() {
		return programmingLocalStationCode;
	}

	public void setProgrammingLocalStationCode(String programmingLocalStationCode) {
		this.programmingLocalStationCode = programmingLocalStationCode;
	}

	public Long getClipSize() {
		return clipSize;
	}

	public void setClipSize(Long clipSize) {
		this.clipSize = clipSize;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getProgramGenre() {
		return programGenre;
	}

	public void setProgramGenre(String programGenre) {
		this.programGenre = programGenre;
	}

	public String getClipId() {
		return clipId;
	}

	public void setClipId(String clipId) {
		this.clipId = clipId;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getClipTitle() {
		return clipTitle;
	}

	public void setClipTitle(String clipTitle) {
		this.clipTitle = clipTitle;
	}

	public String getProgramTitle() {
		return programTitle;
	}

	public void setProgramTitle(String programTitle) {
		this.programTitle = programTitle;
	}

	public String getProgramSubtitle() {
		return programSubtitle;
	}

	public void setProgramSubtitle(String programSubtitle) {
		this.programSubtitle = programSubtitle;
	}

	public String getClipDuration() {
		return clipDuration;
	}

	public void setClipDuration(String clipDuration) {
		this.clipDuration = clipDuration;
	}

	public Integer getProgramSequenceNumber() {
		return programSequenceNumber;
	}

	public void setProgramSequenceNumber(Integer programSequenceNumber) {
		this.programSequenceNumber = programSequenceNumber;
	}

	public String getProductionDepartmentName() {
		return productionDepartmentName;
	}

	public void setProductionDepartmentName(String productionDepartmentName) {
		this.productionDepartmentName = productionDepartmentName;
	}

	public String getProducerReporterRole() {
		return producerReporterRole;
	}

	public void setProducerReporterRole(String producerReporterRole) {
		this.producerReporterRole = producerReporterRole;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getNamdKorean() {
		return namdKorean;
	}

	public void setNamdKorean(String namdKorean) {
		this.namdKorean = namdKorean;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getBroadcastPlannedDate() {
		return broadcastPlannedDate;
	}

	public void setBroadcastPlannedDate(String broadcastPlannedDate) {
		this.broadcastPlannedDate = broadcastPlannedDate;
	}

	public String getStaffRole() {
		return staffRole;
	}

	public void setStaffRole(String staffRole) {
		this.staffRole = staffRole;
	}

	public String getShootingDateTime() {
		return shootingDateTime;
	}

	public void setShootingDateTime(String shootingDateTime) {
		this.shootingDateTime = shootingDateTime;
	}

	public String getShootingPlace() {
		return shootingPlace;
	}

	public void setShootingPlace(String shootingPlace) {
		this.shootingPlace = shootingPlace;
	}

	public String getCameraMan() {
		return cameraMan;
	}

	public void setCameraMan(String cameraMan) {
		this.cameraMan = cameraMan;
	}

	public String getVideoQualityCondition() {
		return videoQualityCondition;
	}

	public void setVideoQualityCondition(String videoQualityCondition) {
		this.videoQualityCondition = videoQualityCondition;
	}

	public String getApprovalYn() {
		return approvalYn;
	}

	public void setApprovalYn(String approvalYn) {
		this.approvalYn = approvalYn;
	}

	public String getProductionVideoQuality() {
		return productionVideoQuality;
	}

	public void setProductionVideoQuality(String productionVideoQuality) {
		this.productionVideoQuality = productionVideoQuality;
	}

	public String getSourceMediaType() {
		return sourceMediaType;
	}

	public void setSourceMediaType(String sourceMediaType) {
		this.sourceMediaType = sourceMediaType;
	}

	public String getVideoAcquisitionClassification() {
		return videoAcquisitionClassification;
	}

	public void setVideoAcquisitionClassification(
			String videoAcquisitionClassification) {
		this.videoAcquisitionClassification = videoAcquisitionClassification;
	}

	public String getUsageNotice() {
		return usageNotice;
	}

	public void setUsageNotice(String usageNotice) {
		this.usageNotice = usageNotice;
	}

	public String getClipEditedStatus() {
		return clipEditedStatus;
	}

	public void setClipEditedStatus(String clipEditedStatus) {
		this.clipEditedStatus = clipEditedStatus;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getClipContents() {
		return clipContents;
	}

	public void setClipContents(String clipContents) {
		this.clipContents = clipContents;
	}

	public String getIngestDateTime() {
		return ingestDateTime;
	}

	public void setIngestDateTime(String ingestDateTime) {
		this.ingestDateTime = ingestDateTime;
	}

	public String getClipSourceSystem() {
		return clipSourceSystem;
	}

	public void setClipSourceSystem(String clipSourceSystem) {
		this.clipSourceSystem = clipSourceSystem;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getVideoCodec() {
		return videoCodec;
	}

	public void setVideoCodec(String videoCodec) {
		this.videoCodec = videoCodec;
	}

	public String getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(String aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public String getVideoFramePerSecond() {
		return videoFramePerSecond;
	}

	public void setVideoFramePerSecond(String videoFramePerSecond) {
		this.videoFramePerSecond = videoFramePerSecond;
	}

	public String getVideoResolution() {
		return videoResolution;
	}

	public void setVideoResolution(String videoResolution) {
		this.videoResolution = videoResolution;
	}

	public String getVideoBandwidth() {
		return videoBandwidth;
	}

	public void setVideoBandwidth(String videoBandwidth) {
		this.videoBandwidth = videoBandwidth;
	}

	public String getAudioModeMain() {
		return audioModeMain;
	}

	public void setAudioModeMain(String audioModeMain) {
		this.audioModeMain = audioModeMain;
	}

	public String getAudioCodec() {
		return audioCodec;
	}

	public void setAudioCodec(String audioCodec) {
		this.audioCodec = audioCodec;
	}

	public String getAudioChannelCount() {
		return audioChannelCount;
	}

	public void setAudioChannelCount(String audioChannelCount) {
		this.audioChannelCount = audioChannelCount;
	}

	public String getAudioSamplingRate() {
		return audioSamplingRate;
	}

	public void setAudioSamplingRate(String audioSamplingRate) {
		this.audioSamplingRate = audioSamplingRate;
	}

	public String getAudioBandwidth() {
		return audioBandwidth;
	}

	public void setAudioBandwidth(String audioBandwidth) {
		this.audioBandwidth = audioBandwidth;
	}
	
	@XStreamAlias("sequence_list")
	private SequenceList sequenceList;

	public SequenceList getSequenceList() {
		return sequenceList;
	}
	public void setSequenceList(SequenceList sequenceList) {
		this.sequenceList = sequenceList;
	}
	
	@XStreamAlias("corners")
	private Corners corners;

	public Corners getCorners() {
		return corners;
	}
	public void setCorners(Corners corners) {
		this.corners = corners;
	}
	
	
}
