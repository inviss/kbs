package kr.co.d2net.dto;

import java.sql.Timestamp;

public class DairyOrderTbl {

	private Long dairyOrderId; // 일일운행ID
	private String mediaName; // 매체명
	private String mediaCode; // 매체코드
	private String channelName; // 채널명
	private String channelCode; // 채널코드
	private String runningDate; // 운행일자
	private String runningStartTime; // 운행시작시간
	private String dailyRunningKind; // 일일구분
	private String runningEndTime; // 운행종료시간
	private String programTitle; // 프로그램명
	private String programSubtitle; // 부제
	private String programCode; // 프로그램코드
	private Integer programSequenceNumber; // 프로그램회차
	private String partName; // 부순명칭
	private String rerunClassification; // 본재방구분
	private String note; // 비고
	private String programId; // 프로그램ID
	private String productionVideoQuality; // 제작품질(SD,HD)
	private String productionType; // 제작형태 (생방, 녹화)
	private String productionTypeCode; // 제작형태 코드 ( 01,02)
	private String recyn; // 녹화여부
	private String groupCode; // 프로그래 그룹코드
	private String audioModeMain;	// 오디오 모드 (Stereo, Dolby5.1, Mono)
	
	private String lastConfirmDate; // 최근변경일자 (20130205004227)
	private String descriptiveVideoServiceYn; // 화면해설 방송여부
	private String transmissionAudioModeMain; // 디편에서 사용하는 오디오모드
	private Timestamp endDatetime;	// 방송종료시간
	
	public Timestamp getEndDatetime() {
		return endDatetime;
	}
	public void setEndDatetime(Timestamp endDatetime) {
		this.endDatetime = endDatetime;
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
	public String getAudioModeMain() {
		return audioModeMain;
	}
	public void setAudioModeMain(String audioModeMain) {
		this.audioModeMain = audioModeMain;
	}
	public String getLastConfirmDate() {
		return lastConfirmDate;
	}
	public void setLastConfirmDate(String lastConfirmDate) {
		this.lastConfirmDate = lastConfirmDate;
	}
	public Long getDairyOrderId() {
		return dairyOrderId;
	}
	public void setDairyOrderId(Long dairyOrderId) {
		this.dairyOrderId = dairyOrderId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
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


	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getMediaCode() {
		return mediaCode;
	}

	public void setMediaCode(String mediaCode) {
		this.mediaCode = mediaCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getRunningDate() {
		return runningDate;
	}

	public void setRunningDate(String runningDate) {
		this.runningDate = runningDate;
	}

	public String getDailyRunningKind() {
		return dailyRunningKind;
	}

	public void setDailyRunningKind(String dailyRunningKind) {
		this.dailyRunningKind = dailyRunningKind;
	}

	public String getRunningStartTime() {
		return runningStartTime;
	}

	public void setRunningStartTime(String runningStartTime) {
		this.runningStartTime = runningStartTime;
	}

	public String getRunningEndTime() {
		return runningEndTime;
	}

	public void setRunningEndTime(String runningEndTime) {
		this.runningEndTime = runningEndTime;
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

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public Integer getProgramSequenceNumber() {
		return programSequenceNumber;
	}

	public void setProgramSequenceNumber(Integer programSequenceNumber) {
		this.programSequenceNumber = programSequenceNumber;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getRerunClassification() {
		return rerunClassification;
	}

	public void setRerunClassification(String rerunClassification) {
		this.rerunClassification = rerunClassification;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
