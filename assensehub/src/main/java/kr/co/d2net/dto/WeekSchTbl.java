package kr.co.d2net.dto;

import java.sql.Date;

public class WeekSchTbl {

	private Long seq;
	private String aspectRatio; // 화면비
	private String aspectRatioCode; // 화면비코드
	private String channelCode; // 채널코드
	private String channelName; // 채널명
	private String created; // 등록일
	private String mediaCode; // 매체코드
	private String onairDay; // 방송요일
	private String onairDayCode; // 방송요일코드
	private String parentProgramScheduleUnique; // 모프로그램스케줄번호
	private Integer partTotalCount; // 부순번호
	private String productionType; // 제작형태
	private String productionTypeCode; // 제작형태코드
	private String programId; // 프로그램아이디 ex) programId, value: KBS 바둑왕전
								// (2011-11-28)
	private String programIdKey; // 프로그램 아이디KEY ex)programIdKey, value:
									// PS-2011120546-01-000 (2011-11-28)
	private String programClassification; // 프로그램구분
	private String programClassificationCode; // 프로그램구분코드
	private String programCode; // 프로그램코드 ex) programCode, value: T2010-0015
								// (2011-11-28)
	private String programGenre; // 장르구분
	private String programGenreCode; // 장르코드
	private String programPlannedDate; // 편성일자
	private String programPlannedDurationMinut; // 편성길이_분
	private String programPlannedEndTime; // 편성종료시각
	private String programPlannedStartTime; // 편성시작시각
	private String programSequenceNumber; // 프로그램회차
	private String programTitle; // 프로그래명
	private String programSubtitle; // 프로그램 서브 타이틀명
	private Date registrationDateTime; // 등록일시
	private String rerunClassification; // 본재방구분
	private String rerunClassificationCode; // 본재방구분코드
	private String sdConvertingAspectRatio; // SD전환화면비
	private String sdConvertingAspectRatioCo; // SD전환화면비코드
	private String mediaName; // 매체명
	private String productionVideoQuality; // 제작품질(SD,HD)
	private String groupCode; // 프로그래 그룹코드
	
	// 2차 고도화 추가사항
	private String programmingLocalStationCode;  // 지역총국코드
	private String audioModeMain;	// 오디오 모드(Stereo, Dolby5.1, Mono)
	private Date changed; //변경일자
	private String lastConfirmDate;
	private String programPlannedStartDate; // 편성시작일자
	private String programPlannedEndDate;	// 편성종료일자
	private String recyn; // 녹화여부

	private String programCopyrightRelationCode; // 프로그램권리 관계코드
	private String copyrightYn;	// 저작권 유무
	private String radioOpenStudioYn;	// 보이는 라디오 여부

	private String descriptiveVideoServiceYn; // 화면해설 방송여부
	private String transmissionAudioModeMain; // 디편에서 사용하는 오디오모드
	
	
	public String getProgramSubtitle() {
		return programSubtitle;
	}
	public void setProgramSubtitle(String programSubtitle) {
		this.programSubtitle = programSubtitle;
	}
	public String getTransmissionAudioModeMain() {
		return transmissionAudioModeMain;
	}
	public void setTransmissionAudioModeMain(String transmissionAudioModeMain) {
		this.transmissionAudioModeMain = transmissionAudioModeMain;
	}
	public String getDescriptiveVideoServiceYn() {
		return descriptiveVideoServiceYn;
	}
	public void setDescriptiveVideoServiceYn(String descriptiveVideoServiceYn) {
		this.descriptiveVideoServiceYn = descriptiveVideoServiceYn;
	}
	public String getProgramCopyrightRelationCode() {
		return programCopyrightRelationCode;
	}

	public void setProgramCopyrightRelationCode(String programCopyrightRelationCode) {
		this.programCopyrightRelationCode = programCopyrightRelationCode;
	}

	public String getRadioOpenStudioYn() {
		return radioOpenStudioYn;
	}

	public void setRadioOpenStudioYn(String radioOpenStudioYn) {
		this.radioOpenStudioYn = radioOpenStudioYn;
	}

	public String getCopyrightYn() {
		return copyrightYn;
	}

	public void setCopyrightYn(String copyrightYn) {
		this.copyrightYn = copyrightYn;
	}
	
	public String getProgramPlannedStartDate() {
		return programPlannedStartDate;
	}

	public void setProgramPlannedStartDate(String programPlannedStartDate) {
		this.programPlannedStartDate = programPlannedStartDate;
	}

	public String getProgramPlannedEndDate() {
		return programPlannedEndDate;
	}

	public void setProgramPlannedEndDate(String programPlannedEndDate) {
		this.programPlannedEndDate = programPlannedEndDate;
	}

	public String getLastConfirmDate() {
		return lastConfirmDate;
	}

	public void setLastConfirmDate(String lastConfirmDate) {
		this.lastConfirmDate = lastConfirmDate;
	}

	public Date getChanged() {
		return changed;
	}

	public void setChanged(Date changed) {
		this.changed = changed;
	}

	public String getAudioModeMain() {
		return audioModeMain;
	}

	public void setAudioModeMain(String audioModeMain) {
		this.audioModeMain = audioModeMain;
	}
	
	public String getProgrammingLocalStationCode() {
		return programmingLocalStationCode;
	}
	public void setProgrammingLocalStationCode(String programmingLocalStationCode) {
		this.programmingLocalStationCode = programmingLocalStationCode;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}


	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getProgramIdKey() {
		return programIdKey;
	}

	public void setProgramIdKey(String programIdKey) {
		this.programIdKey = programIdKey;
	}

	public String getSdConvertingAspectRatioCo() {
		return sdConvertingAspectRatioCo;
	}

	public void setSdConvertingAspectRatioCo(String sdConvertingAspectRatioCo) {
		this.sdConvertingAspectRatioCo = sdConvertingAspectRatioCo;
	}

	public String getProgramPlannedEndTime() {
		return programPlannedEndTime;
	}

	public void setProgramPlannedEndTime(String programPlannedEndTime) {
		this.programPlannedEndTime = programPlannedEndTime;
	}

	public String getRecyn() {
		return recyn;
	}

	public void setRecyn(String recyn) {
		this.recyn = recyn;
	}

	public String getProductionVideoQuality() {
		return productionVideoQuality;
	}

	public void setProductionVideoQuality(String productionVideoQuality) {
		this.productionVideoQuality = productionVideoQuality;
	}

	public String getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(String aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public String getAspectRatioCode() {
		return aspectRatioCode;
	}

	public void setAspectRatioCode(String aspectRatioCode) {
		this.aspectRatioCode = aspectRatioCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getMediaCode() {
		return mediaCode;
	}

	public void setMediaCode(String mediaCode) {
		this.mediaCode = mediaCode;
	}

	public String getOnairDay() {
		return onairDay;
	}

	public void setOnairDay(String onairDay) {
		this.onairDay = onairDay;
	}

	public String getOnairDayCode() {
		return onairDayCode;
	}

	public void setOnairDayCode(String onairDayCode) {
		this.onairDayCode = onairDayCode;
	}

	public String getParentProgramScheduleUnique() {
		return parentProgramScheduleUnique;
	}

	public void setParentProgramScheduleUnique(
			String parentProgramScheduleUnique) {
		this.parentProgramScheduleUnique = parentProgramScheduleUnique;
	}

	public Integer getPartTotalCount() {
		return partTotalCount;
	}

	public void setPartTotalCount(Integer partTotalCount) {
		this.partTotalCount = partTotalCount;
	}

	public String getProductionType() {
		return productionType;
	}

	public void setProductionType(String productionType) {
		this.productionType = productionType;
	}

	public String getProductionTypeCode() {
		return productionTypeCode;
	}

	public void setProductionTypeCode(String productionTypeCode) {
		this.productionTypeCode = productionTypeCode;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getProgramClassification() {
		return programClassification;
	}

	public void setProgramClassification(String programClassification) {
		this.programClassification = programClassification;
	}

	public String getProgramClassificationCode() {
		return programClassificationCode;
	}

	public void setProgramClassificationCode(String programClassificationCode) {
		this.programClassificationCode = programClassificationCode;
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

	public String getProgramGenreCode() {
		return programGenreCode;
	}

	public void setProgramGenreCode(String programGenreCode) {
		this.programGenreCode = programGenreCode;
	}

	public String getProgramPlannedDurationMinut() {
		return programPlannedDurationMinut;
	}

	public String getProgramPlannedDate() {
		return programPlannedDate;
	}

	public void setProgramPlannedDate(String programPlannedDate) {
		this.programPlannedDate = programPlannedDate;
	}

	public void setProgramPlannedDurationMinut(
			String programPlannedDurationMinut) {
		this.programPlannedDurationMinut = programPlannedDurationMinut;
	}

	public String getProgramPlannedStartTime() {
		return programPlannedStartTime;
	}

	public void setProgramPlannedStartTime(String programPlannedStartTime) {
		this.programPlannedStartTime = programPlannedStartTime;
	}

	public String getProgramSequenceNumber() {
		return programSequenceNumber;
	}

	public void setProgramSequenceNumber(String programSequenceNumber) {
		this.programSequenceNumber = programSequenceNumber;
	}

	public String getProgramTitle() {
		return programTitle;
	}

	public void setProgramTitle(String programTitle) {
		this.programTitle = programTitle;
	}

	public Date getRegistrationDateTime() {
		return registrationDateTime;
	}

	public void setRegistrationDateTime(Date registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	public String getRerunClassification() {
		return rerunClassification;
	}

	public void setRerunClassification(String rerunClassification) {
		this.rerunClassification = rerunClassification;
	}

	public String getRerunClassificationCode() {
		return rerunClassificationCode;
	}

	public void setRerunClassificationCode(String rerunClassificationCode) {
		this.rerunClassificationCode = rerunClassificationCode;
	}

	public String getSdConvertingAspectRatio() {
		return sdConvertingAspectRatio;
	}

	public void setSdConvertingAspectRatio(String sdConvertingAspectRatio) {
		this.sdConvertingAspectRatio = sdConvertingAspectRatio;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

}
