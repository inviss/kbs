package kr.co.d2net.dto.xml.publish;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import kr.co.d2net.commons.adapter.JaxbDateSerializer;
import kr.co.d2net.commons.adapter.JaxbTimeSerializer;

@XmlRootElement(name="BasicDescription")
@XmlAccessorType(XmlAccessType.FIELD)
public class BasicDescription {
	
	
	@XmlElement(name="group_code")
	private String groupCode;
	
	@XmlElement(name="program_code")
	private String programCode;
	
	@XmlElement(name="program_id")
	private String programId;
	
	@XmlElement(name="program_sequence_number")
	private Integer programSequenceNumber;
	
	@XmlElement(name="main_story")
	private String mainStory;
	
	@XmlElement(name="note")
	private String note;
	
	@XmlElement(name="program_title")
	private String programTitle;
	
	@XmlElement(name="program_subtitle")
	private String programSubtitle;
	
	@XmlElement(name="part_number")
	private Integer partNumber;
	
	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	@XmlElement(name="program_start_date")
	private Date programStartDate;
	
	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	@XmlElement(name="program_end_date")
	private Date programEndDate;
	
	@XmlElement(name="program_genre_code")
	private Type programGenreCode;
	
	@XmlElement(name="program_deliberation_grade_code")
	private Type programDeliberationGradeCode;
	
	@XmlElement(name="media_type_code")
	private String mediaTypeCode;
	
	@XmlElement(name="production_video_quality_code")
	private String productionVideoQualityCode;
	
	@XmlElement(name="aspect_ratio_code")
	private String aspectRatioCode;
	
	@XmlElement(name="audio_mode_main")
	private String audioModeMain;
	
	@XmlElement(name="closed_caption_YN")
	private String closedCaptionYn;
	
	@XmlElement(name="bilinguql_language_code")
	private String bilinguqlLanguageCode;
	
	@XmlElement(name="bilinguql_language")
	private String bilinguqlLanguage;
	
	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	@XmlElement(name="broadcast_planned_date")
	private Date broadcastPlannedDate;
	
	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	@XmlElement(name="production_end_date")
	private Date productionEndDate;
	
	@XmlJavaTypeAdapter(JaxbTimeSerializer.class)
	@XmlElement(name="broadcast_planned_time")
	private Date broadcastPlannedTime;
	
	@XmlElement(name="broadcast_planned_media_code")
	private String broadcastPlannedMediaCode;
	
	@XmlElement(name="broadcast_planned_channel_code")
	private String broadcastPlannedChannelCode;
	
	@XmlElement(name="broadcast_planned_channel_name")
	private String broadcastPlannedChannelName;
	
	@XmlElement(name="outsider_production_name")
	private String outsiderProductionName;
	
	@XmlElement(name="copyright_YN_foreign")
	private String copyrightYnForeign;
	
	@XmlElement(name="cuesheet_YN")
	private String cuesheetYn;
	
	@XmlElement(name="attached_cuesheet_YN")
	private String attachedCuesheetYn;
	
	@XmlElement(name="script_YN")
	private String scriptYn;
	
	@XmlElement(name="synopsis_YN")
	private String synopsisYn;
	
	@XmlElement(name="caption_YN")
	private String captionYn;
	
	@XmlElement(name="program_intention")
	private String programIntention;
	
	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	@XmlElement(name="program_onair_day")
	private Date programOnairDay;
	
	@XmlElement(name="program_planned_duration_min")
	private String programPlannedDurationMin;
	
	@XmlElement(name="programming_local_station_code")
	private Type programmingLocalStationCode;
	
	@XmlElement(name="broadcast_complete_YN")
	private String broadcastCompleteYn;
	
	@XmlElement(name="broadcast_language_kind_code")
	private String broadcastLanguageKindCode;
	
	@XmlElement(name="channel_code")
	private Type channelCode;
	
	@XmlElement(name="child_program_YN")
	private String child_programYn;
	
	@XmlElement(name="copyright_YN")
	private String copyrightYn;
	
	@XmlElement(name="domestic_foreign_kind")
	private String domesticForeignKind;
	
	@XmlElement(name="keyword")
	private String keyword;
	
	@XmlElement(name="language_kind_code")
	private String languageKindCode;
	
	@XmlElement(name="media_code")
	private Type mediaCode;
	
	@XmlElement(name="news_flash_YN")
	private String newsFlashYn;
	
	@XmlElement(name="onair_day_code")
	private String onairDayCode;
	
	@XmlElement(name="original_title")
	private String originalTitle;
	
	@XmlElement(name="overseasProgramTitle")
	private String overseas_program_title;
	
	@XmlElement(name="production_body_classification")
	private String productionBodyClassification;
	
	@XmlElement(name="production_department_code")
	private String productionDepartmentCode;
	
	@XmlElement(name="production_type_code")
	private String productionTypeCode;
	
	@XmlElement(name="rerun_classification_code")
	private String rerunClassificationCode;
	
	@XmlElement(name="section_code")
	private String sectionCode;
	
	@XmlElement(name="synopsis")
	private String synopsis;
	
	@XmlElement(name="thumnail_image")
	private String thumnailImage;
	
	@XmlElement(name="program_planned_start_time")
	private String programPlannedStartTime;
	
	@XmlElement(name="regular_special_classification")
	private String regularSpecialClassification;
	

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public Integer getProgramSequenceNumber() {
		return programSequenceNumber;
	}

	public void setProgramSequenceNumber(Integer programSequenceNumber) {
		this.programSequenceNumber = programSequenceNumber;
	}

	public String getMainStory() {
		return mainStory;
	}

	public void setMainStory(String mainStory) {
		this.mainStory = mainStory;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public Integer getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(Integer partNumber) {
		this.partNumber = partNumber;
	}

	public Date getProgramStartDate() {
		return programStartDate;
	}

	public void setProgramStartDate(Date programStartDate) {
		this.programStartDate = programStartDate;
	}

	public Date getProgramEndDate() {
		return programEndDate;
	}

	public void setProgramEndDate(Date programEndDate) {
		this.programEndDate = programEndDate;
	}

	public Type getProgramGenreCode() {
		return programGenreCode;
	}

	public void setProgramGenreCode(Type programGenreCode) {
		this.programGenreCode = programGenreCode;
	}

	public Type getProgramDeliberationGradeCode() {
		return programDeliberationGradeCode;
	}

	public void setProgramDeliberationGradeCode(Type programDeliberationGradeCode) {
		this.programDeliberationGradeCode = programDeliberationGradeCode;
	}

	public String getMediaTypeCode() {
		return mediaTypeCode;
	}

	public void setMediaTypeCode(String mediaTypeCode) {
		this.mediaTypeCode = mediaTypeCode;
	}

	public String getProductionVideoQualityCode() {
		return productionVideoQualityCode;
	}

	public void setProductionVideoQualityCode(String productionVideoQualityCode) {
		this.productionVideoQualityCode = productionVideoQualityCode;
	}

	public String getAspectRatioCode() {
		return aspectRatioCode;
	}

	public void setAspectRatioCode(String aspectRatioCode) {
		this.aspectRatioCode = aspectRatioCode;
	}

	public String getAudioModeMain() {
		return audioModeMain;
	}

	public void setAudioModeMain(String audioModeMain) {
		this.audioModeMain = audioModeMain;
	}

	public String getClosedCaptionYn() {
		return closedCaptionYn;
	}

	public void setClosedCaptionYn(String closedCaptionYn) {
		this.closedCaptionYn = closedCaptionYn;
	}

	public String getBilinguqlLanguageCode() {
		return bilinguqlLanguageCode;
	}

	public void setBilinguqlLanguageCode(String bilinguqlLanguageCode) {
		this.bilinguqlLanguageCode = bilinguqlLanguageCode;
	}

	public String getBilinguqlLanguage() {
		return bilinguqlLanguage;
	}

	public void setBilinguqlLanguage(String bilinguqlLanguage) {
		this.bilinguqlLanguage = bilinguqlLanguage;
	}

	public Date getBroadcastPlannedDate() {
		return broadcastPlannedDate;
	}

	public void setBroadcastPlannedDate(Date broadcastPlannedDate) {
		this.broadcastPlannedDate = broadcastPlannedDate;
	}

	public Date getProductionEndDate() {
		return productionEndDate;
	}

	public void setProductionEndDate(Date productionEndDate) {
		this.productionEndDate = productionEndDate;
	}

	public Date getBroadcastPlannedTime() {
		return broadcastPlannedTime;
	}

	public void setBroadcastPlannedTime(Date broadcastPlannedTime) {
		this.broadcastPlannedTime = broadcastPlannedTime;
	}

	public String getBroadcastPlannedMediaCode() {
		return broadcastPlannedMediaCode;
	}

	public void setBroadcastPlannedMediaCode(String broadcastPlannedMediaCode) {
		this.broadcastPlannedMediaCode = broadcastPlannedMediaCode;
	}

	public String getBroadcastPlannedChannelCode() {
		return broadcastPlannedChannelCode;
	}

	public void setBroadcastPlannedChannelCode(String broadcastPlannedChannelCode) {
		this.broadcastPlannedChannelCode = broadcastPlannedChannelCode;
	}

	public String getBroadcastPlannedChannelName() {
		return broadcastPlannedChannelName;
	}

	public void setBroadcastPlannedChannelName(String broadcastPlannedChannelName) {
		this.broadcastPlannedChannelName = broadcastPlannedChannelName;
	}

	public String getOutsiderProductionName() {
		return outsiderProductionName;
	}

	public void setOutsiderProductionName(String outsiderProductionName) {
		this.outsiderProductionName = outsiderProductionName;
	}

	public String getCopyrightYnForeign() {
		return copyrightYnForeign;
	}

	public void setCopyrightYnForeign(String copyrightYnForeign) {
		this.copyrightYnForeign = copyrightYnForeign;
	}

	public String getCuesheetYn() {
		return cuesheetYn;
	}

	public void setCuesheetYn(String cuesheetYn) {
		this.cuesheetYn = cuesheetYn;
	}

	public String getAttachedCuesheetYn() {
		return attachedCuesheetYn;
	}

	public void setAttachedCuesheetYn(String attachedCuesheetYn) {
		this.attachedCuesheetYn = attachedCuesheetYn;
	}

	public String getScriptYn() {
		return scriptYn;
	}

	public void setScriptYn(String scriptYn) {
		this.scriptYn = scriptYn;
	}

	public String getSynopsisYn() {
		return synopsisYn;
	}

	public void setSynopsisYn(String synopsisYn) {
		this.synopsisYn = synopsisYn;
	}

	public String getCaptionYn() {
		return captionYn;
	}

	public void setCaptionYn(String captionYn) {
		this.captionYn = captionYn;
	}

	public String getProgramIntention() {
		return programIntention;
	}

	public void setProgramIntention(String programIntention) {
		this.programIntention = programIntention;
	}

	public Date getProgramOnairDay() {
		return programOnairDay;
	}

	public void setProgramOnairDay(Date programOnairDay) {
		this.programOnairDay = programOnairDay;
	}

	public String getProgramPlannedDurationMin() {
		return programPlannedDurationMin;
	}

	public void setProgramPlannedDurationMin(String programPlannedDurationMin) {
		this.programPlannedDurationMin = programPlannedDurationMin;
	}

	public Type getProgrammingLocalStationCode() {
		return programmingLocalStationCode;
	}

	public void setProgrammingLocalStationCode(Type programmingLocalStationCode) {
		this.programmingLocalStationCode = programmingLocalStationCode;
	}

	public String getBroadcastCompleteYn() {
		return broadcastCompleteYn;
	}

	public void setBroadcastCompleteYn(String broadcastCompleteYn) {
		this.broadcastCompleteYn = broadcastCompleteYn;
	}

	public String getBroadcastLanguageKindCode() {
		return broadcastLanguageKindCode;
	}

	public void setBroadcastLanguageKindCode(String broadcastLanguageKindCode) {
		this.broadcastLanguageKindCode = broadcastLanguageKindCode;
	}

	public Type getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(Type channelCode) {
		this.channelCode = channelCode;
	}

	public String getChild_programYn() {
		return child_programYn;
	}

	public void setChild_programYn(String child_programYn) {
		this.child_programYn = child_programYn;
	}

	public String getCopyrightYn() {
		return copyrightYn;
	}

	public void setCopyrightYn(String copyrightYn) {
		this.copyrightYn = copyrightYn;
	}

	public String getDomesticForeignKind() {
		return domesticForeignKind;
	}

	public void setDomesticForeignKind(String domesticForeignKind) {
		this.domesticForeignKind = domesticForeignKind;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLanguageKindCode() {
		return languageKindCode;
	}

	public void setLanguageKindCode(String languageKindCode) {
		this.languageKindCode = languageKindCode;
	}

	public Type getMediaCode() {
		return mediaCode;
	}

	public void setMediaCode(Type mediaCode) {
		this.mediaCode = mediaCode;
	}

	public String getNewsFlashYn() {
		return newsFlashYn;
	}

	public void setNewsFlashYn(String newsFlashYn) {
		this.newsFlashYn = newsFlashYn;
	}

	public String getOnairDayCode() {
		return onairDayCode;
	}

	public void setOnairDayCode(String onairDayCode) {
		this.onairDayCode = onairDayCode;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getOverseas_program_title() {
		return overseas_program_title;
	}

	public void setOverseas_program_title(String overseas_program_title) {
		this.overseas_program_title = overseas_program_title;
	}

	public String getProductionBodyClassification() {
		return productionBodyClassification;
	}

	public void setProductionBodyClassification(String productionBodyClassification) {
		this.productionBodyClassification = productionBodyClassification;
	}

	public String getProductionDepartmentCode() {
		return productionDepartmentCode;
	}

	public void setProductionDepartmentCode(String productionDepartmentCode) {
		this.productionDepartmentCode = productionDepartmentCode;
	}

	public String getProductionTypeCode() {
		return productionTypeCode;
	}

	public void setProductionTypeCode(String productionTypeCode) {
		this.productionTypeCode = productionTypeCode;
	}

	public String getRerunClassificationCode() {
		return rerunClassificationCode;
	}

	public void setRerunClassificationCode(String rerunClassificationCode) {
		this.rerunClassificationCode = rerunClassificationCode;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getThumnailImage() {
		return thumnailImage;
	}

	public void setThumnailImage(String thumnailImage) {
		this.thumnailImage = thumnailImage;
	}
/*
	public String getProgramPlannedStartTime() {
		return programPlannedStartTime;
	}

	public void setProgramPlannedStartTime(String programPlannedStartTime) {
		this.programPlannedStartTime = programPlannedStartTime;
	}
*/
	public String getRegularSpecialClassification() {
		return regularSpecialClassification;
	}

	public void setRegularSpecialClassification(String regularSpecialClassification) {
		this.regularSpecialClassification = regularSpecialClassification;
	}
	
	
}
