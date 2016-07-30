package kr.co.d2net.dto.xml.dmcr;

import java.sql.Timestamp;
import java.util.Date;

import kr.co.d2net.commons.converter.xml.DateConverter;
import kr.co.d2net.commons.converter.xml.IntegerConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.commons.converter.xml.TextUTF8Converter;
import kr.co.d2net.commons.converter.xml.TimestampConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("metadata")
public class Metadata {
	
	@XStreamAlias("programming_local_station_code")
	@XStreamConverter(TextConverter.class)
	private String programmingLocalStationCode;		// 지역코드
	@XStreamAlias("programming_local_station_name")
	@XStreamConverter(TextUTF8Converter.class)
	private String programmingLocalStationName;		// 지역코드
	@XStreamAlias("channel_code")
	@XStreamConverter(TextConverter.class)
	private String channelCode;
	@XStreamAlias("channel_name")
	@XStreamConverter(TextUTF8Converter.class)
	private String channelName;
	@XStreamAlias("program_ID")
	@XStreamConverter(TextConverter.class)
	private String programId;
	@XStreamAlias("part_number")
	@XStreamConverter(IntegerConverter.class)
	private Integer partNumber;
	@XStreamAlias("program_title")
	@XStreamConverter(TextUTF8Converter.class)
	private String programTitle;
	@XStreamAlias("subject_name")
	@XStreamConverter(TextUTF8Converter.class)
	private String subjectName;
	@XStreamAlias("program_subtitle")
	@XStreamConverter(TextUTF8Converter.class)
	private String programSubTitle;			
	@XStreamAlias("producer_name")
	@XStreamConverter(TextConverter.class)
	private String producerName;					// 제작자명
	@XStreamAlias("broadcast_planned_date")
	@XStreamConverter(DateConverter.class)
	private Date broadcastPlannedDate;			// 방송예정일자
	@XStreamAlias("broadcast_event_kind")
	@XStreamConverter(TextConverter.class)
	private String broadcastEventKind;				// 에센스 구분정보(Y:예고, 1:전타, 2:후타, P:본방)
	@XStreamAlias("trailer_title")
	@XStreamConverter(TextUTF8Converter.class)
	private String tailerTitle;						// 스팟,예고등 내용을 대표하는 제목
	@XStreamAlias("spot_start_date")
	@XStreamConverter(DateConverter.class)
	private Date spotStartDate; 					// 운행요청 시작일자
	@XStreamAlias("spot_end_date")
	@XStreamConverter(DateConverter.class)
	private Date spotEndDate;						// 운행요청 종료일자
	@XStreamAlias("spot_title")
	@XStreamConverter(TextUTF8Converter.class)
	private String spotTitle;
	@XStreamAlias("spot_subtitle")
	@XStreamConverter(TextUTF8Converter.class)
	private String spotSubtitle;
	@XStreamAlias("material_ID")
	@XStreamConverter(TextConverter.class)
	private String materialId;						// UMID를 포함한 방송소재물 ID
	@XStreamAlias("clip_ID")
	@XStreamConverter(TextConverter.class)
	private String clipId;							// 에센스 클립ID
	@XStreamAlias("start_of_mark1")
	@XStreamConverter(TextConverter.class)
	private String startOfMark1;					// 프로그램 및 스파트 등 각 방송 소재의 파일내 송출 시작점
	@XStreamAlias("production_duration")
	@XStreamConverter(TextConverter.class)
	private String productionDuration;				// 프로그램의 실제 제작 길이
	@XStreamAlias("production_video_quality")
	@XStreamConverter(TextConverter.class)
	private String productionVideoQualty;			// 제작되는 영상의 해상도
	@XStreamAlias("aspect_ratio")
	@XStreamConverter(TextConverter.class)
	private String aspectRatio;						// 가로, 세로 비율
	@XStreamAlias("SD_converting_aspect_ratio")
	@XStreamConverter(TextConverter.class)
	private String sdConvertingAspectRatio;			// HD 방송본에서 SD 방송본으로 전환된 화면 비율 [HD, SD]
	@XStreamAlias("audio_transmission_type")
	@XStreamConverter(TextConverter.class)
	private String audioTransmissionType;			// 오디오 스트림의 송출 형태 [01(한국어), 03(음성다중), 05(화면해설), 07(음성다중화면해설)]
	@XStreamAlias("transmission_audio_mode_main")
	@XStreamConverter(TextConverter.class)
	private String trasmissionAudioModeMain;		// 송출 오디오 스트림의 주 모드 [01(Stereo), 02(Mono), 03(다중), 04(5.1채널)]
	@XStreamAlias("transmission_audio_mode_sub")
	@XStreamConverter(TextConverter.class)
	private String transmissionAudioModeSub;		// 송출 오디오 스트림의 부 모드
	@XStreamAlias("production_material_type")
	@XStreamConverter(TextConverter.class)
	private String productionMaterialtype;			// 제작물 형태 [File, Tape1, Tape2]
	@XStreamAlias("NLE_number")
	@XStreamConverter(IntegerConverter.class)
	private Integer nleNumber;						// NLE 번호
	@XStreamAlias("production_producer_name")
	@XStreamConverter(TextConverter.class)
	private String productionProducerName;			// 연출 담당자 이름
	@XStreamAlias("production_technical_director_name")
	@XStreamConverter(TextConverter.class)
	private String productionTechnicalDirectorName;	// TD 이름
	@XStreamAlias("production_date")
	@XStreamConverter(DateConverter.class)
	private Date productionDate;					// 프로그램 제작일자 (YYYYMMDD)
	@XStreamAlias("production_place")
	@XStreamConverter(TextConverter.class)
	private String productionPlace;					// 촬영,녹화 장소
	@XStreamAlias("transmission_datetime")
	@XStreamConverter(TimestampConverter.class)
	private Timestamp transmissionDatetime;				// 파일 전송일시 (YYYYMMDDHHMM)
	@XStreamAlias("transmission_employee_name")
	@XStreamConverter(TextConverter.class)
	private String transmissionEmployeeName;		// 전송자명
	@XStreamAlias("production_department_code")
	@XStreamConverter(TextConverter.class)
	private String productionDepartmentCode;		// 제작을 담당하는 연출부서 코드(UNIPS 부서코드 사용)
	@XStreamAlias("spot_sponsor_YN")
	@XStreamConverter(TextConverter.class)
	private String spotSponsorYn;					// 예고/스폿(SPOT)의 '협찬(제작/송출)' 약정 여부
	@XStreamAlias("production_detail_type")
	@XStreamConverter(TextConverter.class)
	private String productionDetailType;			// 녹화, 편집, 더빙,컨버팅,복사 구분 [02(녹화), 05(편집), 06(더빙), 07(컨버팅), 08(복사)]
	@XStreamAlias("VCR_number")
	@XStreamConverter(TextConverter.class)
	private String vcrNumber;						// 녹화 VCR의 번호
	@XStreamAlias("program_deliberation_grade")
	@XStreamConverter(TextConverter.class)
	private String programDeliberationGrade;		// 시청 적정 연령 등급 [01=일반,02=7세,03=12세,04=15세,05=19세]
	@XStreamAlias("caption_language_kind")
	@XStreamConverter(TextConverter.class)
	private String captionLanguageKind;				// 자막언어 [KOR=한국어,ENG=영어,JPN=일본어,CHI=중국어]
	@XStreamAlias("preview_video_file_path")
	@XStreamConverter(TextConverter.class)
	private String previewVideoFilePath;			// 비디오 미리보기용 파일 경로
	@XStreamAlias("ingest_date_time")
	@XStreamConverter(TimestampConverter.class)
	private Timestamp ingestDateTime;					// 인제스트 일자 (YYYYMMDDHHMMSS)
	@XStreamAlias("ingest_status")
	@XStreamConverter(TextConverter.class)
	private String ingestStatus;					// 인제스트 상태
	@XStreamAlias("playout_transmission_YN")
	@XStreamConverter(TextConverter.class)
	private String playoutTransmissionYn;			// 송출 비디오 서버로의 전송 여부 [Y, N]
	@XStreamAlias("encoding_request_YN")
	@XStreamConverter(TextConverter.class)
	private String encodingRequestYn;				// Tape 소재에 대한 인코딩 요청 여부
	@XStreamAlias("program_copyright_relation")
	@XStreamConverter(TextConverter.class)
	private String programCopyrightRelation;		// 프로그램 저작권 보유 범위 [01(KBS 모든 권리), 02(KBS 모든 권리(일부화면 제외)), 03(KBS 일부 권리), 04(재사용금지)]
	@XStreamAlias("clip_retransmission_YN")
	@XStreamConverter(TextConverter.class)
	private String clipRetransmissionYn;			// 클립의 재전송 여부
	
	@XStreamAlias("program_code")
	@XStreamConverter(TextConverter.class)
	private String programCode;
	@XStreamAlias("programming_code")				
	@XStreamConverter(TextConverter.class)
	private String programmingCode;					// 편성시스템에서 한 프로그램의 각 회별 프로그램을 구별하기 위해서 사용하는 코드
	@XStreamAlias("program_sequence_number")
	@XStreamConverter(IntegerConverter.class)
	private Integer programSequenceNumber;			// 동일한 프로그램 그룹 또는 시리즈에서 방송되는 순서대로 부여되는 일련번호
	@XStreamAlias("program_onair_start_time")
	@XStreamConverter(TextConverter.class)
	private String programOnairStartTime;			// 방송 시작 시간, 편성 시각 [HH:MM:SS]
	@XStreamAlias("return_classification")
	@XStreamConverter(TextConverter.class)			
	private String returnClassification;			// 본방송인지 재방송인지 구분
	@XStreamAlias("schedule_unique_ID")
	@XStreamConverter(TextConverter.class)
	private String scheduleUniqueId;				// 주간편성 스케줄의 각 밴드(띠)에 부여되는 유일한 일련번호
	@XStreamAlias("pre_sponsor_caption_SOM")
	@XStreamConverter(TextConverter.class)
	private String preSponsorCaptionSom;			// 프로그램 시작전에 고지하는 제공자막 클립의 시작점
	@XStreamAlias("program_start_of_mark1")
	@XStreamConverter(TextConverter.class)
	private String programStartOfMark1;				// 본 프로그램의 클립에서의 시작점 [hh:mm:ss:ff]
	@XStreamAlias("closed_caption_YN")
	@XStreamConverter(TextConverter.class)
	private String closedCaptionYn;					// 청각장애인을 위한 자막방송 서비스 여부 [Y,N]
	@XStreamAlias("bilingual_language")
	@XStreamConverter(TextConverter.class)
	private String bilingualLanguage;				// 음성다중 방송 시 한국어 외에 추가로 방송되는 언어 [01(영어), 03(일본어)]
	@XStreamAlias("slip_CG_ID")
	@XStreamConverter(TextConverter.class)
	private String slipCgId;						// 슬립광고 CG의 ID
	@XStreamAlias("main_story")
	@XStreamConverter(TextUTF8Converter.class)
	private String mainStory;						// 영화나 회별 프로그램의 줄거리
	
	@XStreamAlias("pre_title_SOM")
	@XStreamConverter(TextConverter.class)
	private String preTitleSom;						// 프로그램 시작전에 고지하는 타이틀 클립의 시작점 [hh:mm:ss:ff]
	@XStreamAlias("pre_title_duration")
	@XStreamConverter(TextConverter.class)
	private String preTitleDuration;				// 프로그램 시작전에 고지하는 타이틀 클립의 길이 [hhmmssff]
	@XStreamAlias("program_duration1")
	@XStreamConverter(TextConverter.class)
	private String programDuration1;				// 프로그램1의 제작 길이 [hhmmssff]
	@XStreamAlias("post_title_SOM")
	@XStreamConverter(TextConverter.class)
	private String postTitleSom;					// 프로그램 종료 직후 고지하는 타이틀 클립의 시작점 [hh:mm:ss:ff]
	@XStreamAlias("post_title_duration")
	@XStreamConverter(TextConverter.class)
	private String postTitleDuration;				// 프로그램 종료 직후 고지하는 타이틀 클립의 길이 [hhmmssff]
	@XStreamAlias("post_sponsor_caption_SOM")
	@XStreamConverter(TextConverter.class)			
	private String postSponsorCaptionSom;			// 프로그램 종료 직후 고지하는 제공자막 클립의 시작점 [hh:mm:ss:ff]
	@XStreamAlias("end_title_ID")
	@XStreamConverter(TextConverter.class)
	private String endTitleId;						// 텔레비전 프로그램이나 영화의 끝 장면에 나오는 타이틀 ID
	@XStreamAlias("program_internal_CM_start_of_mark1")	
	@XStreamConverter(TextConverter.class)
	private String programInternalCmStartOfMark1;		//프로그램 클립 상의 중간광고 시작시점1
	@XStreamAlias("program_internal_CM_start_of_mark2")
	@XStreamConverter(TextConverter.class)
	private String programInternalCmStartOfMark2;		//프로그램 클립 상의 중간광고 시작시점2
	@XStreamAlias("program_internal_CM_start_of_mark3")
	@XStreamConverter(TextConverter.class)
	private String programInternalCmStartOfMark3;		//프로그램 클립 상의 중간광고 시작시점3
	@XStreamAlias("program_internal_CM_start_of_mark4")
	@XStreamConverter(TextConverter.class)
	private String programInternalCmStartOfMark4;		//프로그램 클립 상의 중간광고 시작시점4
	@XStreamAlias("program_internal_CM_start_of_mark5")
	@XStreamConverter(TextConverter.class)
	private String programInternalCmStartOfMark5;		//프로그램 클립 상의 중간광고 시작시점5
	@XStreamAlias("program_internal_CM_start_of_mark6")
	@XStreamConverter(TextConverter.class)
	private String programInternalCmStartOfMark6;		//프로그램 클립 상의 중간광고 시작시점6
	
	// 향후 광고분리송출관련 태그 추가예정
	@XStreamAlias("CM_separation_YN")
	@XStreamConverter(TextConverter.class)
	private String cmSeparationYn;
	
	/* DNPS에서 사용하는 비표준 태그 추가 (2014.01.07) */
	@XStreamAlias("program_planned_date")
	@XStreamConverter(DateConverter.class)
	private Date programPlannedDate;
	@XStreamAlias("program_planned_start_time")
	@XStreamConverter(TextConverter.class)
	private String programPlannedStartTime;
	@XStreamAlias("production_department_name")
	@XStreamConverter(TextConverter.class)
	private String productionDepartmentName;
	@XStreamAlias("ingest_status_name")
	@XStreamConverter(TextConverter.class)
	private String ingestStatusName;
	@XStreamAlias("ingest_status_code")
	@XStreamConverter(TextConverter.class)
	private String ingestStatusCode;
	@XStreamAlias("program_SOM")
	@XStreamConverter(TextConverter.class)
	private String programSom;
	@XStreamAlias("program_duration")
	@XStreamConverter(TextConverter.class)
	private String programDuration;
	
	/* 그외 추가항목 */
	@XStreamAlias("use_start_date")
	@XStreamConverter(DateConverter.class)
	private Date useStartDate;
	@XStreamAlias("use_expiration_date")
	@XStreamConverter(DateConverter.class)
	private Date useExpirationDate;
	@XStreamAlias("playout_transmissino_YN")
	@XStreamConverter(TextConverter.class)
	private String playoutTransmissinoYn;
	
	/* 2016.07.30 NPS 추가항목 */
	@XStreamAlias("video_source_version")
	@XStreamConverter(TextConverter.class)
	private String videoSourceVersion;
	
	
	public String getVideoSourceVersion() {
		return videoSourceVersion;
	}
	public void setVideoSourceVersion(String videoSourceVersion) {
		this.videoSourceVersion = videoSourceVersion;
	}
	public String getPlayoutTransmissinoYn() {
		return playoutTransmissinoYn;
	}
	public void setPlayoutTransmissinoYn(String playoutTransmissinoYn) {
		this.playoutTransmissinoYn = playoutTransmissinoYn;
	}
	public Date getUseExpirationDate() {
		return useExpirationDate;
	}
	public void setUseExpirationDate(Date useExpirationDate) {
		this.useExpirationDate = useExpirationDate;
	}
	public Date getUseStartDate() {
		return useStartDate;
	}
	public void setUseStartDate(Date useStartDate) {
		this.useStartDate = useStartDate;
	}
	public Date getProgramPlannedDate() {
		return programPlannedDate;
	}
	public void setProgramPlannedDate(Date programPlannedDate) {
		this.programPlannedDate = programPlannedDate;
	}
	public String getProgramPlannedStartTime() {
		return programPlannedStartTime;
	}
	public void setProgramPlannedStartTime(String programPlannedStartTime) {
		this.programPlannedStartTime = programPlannedStartTime;
	}
	public String getProductionDepartmentName() {
		return productionDepartmentName;
	}
	public void setProductionDepartmentName(String productionDepartmentName) {
		this.productionDepartmentName = productionDepartmentName;
	}
	public String getIngestStatusName() {
		return ingestStatusName;
	}
	public void setIngestStatusName(String ingestStatusName) {
		this.ingestStatusName = ingestStatusName;
	}
	public String getIngestStatusCode() {
		return ingestStatusCode;
	}
	public void setIngestStatusCode(String ingestStatusCode) {
		this.ingestStatusCode = ingestStatusCode;
	}
	public String getProgramSom() {
		return programSom;
	}
	public void setProgramSom(String programSom) {
		this.programSom = programSom;
	}
	public String getProgramDuration() {
		return programDuration;
	}
	public void setProgramDuration(String programDuration) {
		this.programDuration = programDuration;
	}
	public String getCmSeparationYn() {
		return cmSeparationYn;
	}
	public void setCmSeparationYn(String cmSeparationYn) {
		this.cmSeparationYn = cmSeparationYn;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getProgrammingLocalStationName() {
		return programmingLocalStationName;
	}
	public void setProgrammingLocalStationName(String programmingLocalStationName) {
		this.programmingLocalStationName = programmingLocalStationName;
	}
	public String getSpotSubtitle() {
		return spotSubtitle;
	}
	public void setSpotSubtitle(String spotSubtitle) {
		this.spotSubtitle = spotSubtitle;
	}
	public String getSpotTitle() {
		return spotTitle;
	}
	public void setSpotTitle(String spotTitle) {
		this.spotTitle = spotTitle;
	}
	public String getPreTitleSom() {
		return preTitleSom;
	}
	public void setPreTitleSom(String preTitleSom) {
		this.preTitleSom = preTitleSom;
	}
	public String getPreTitleDuration() {
		return preTitleDuration;
	}
	public void setPreTitleDuration(String preTitleDuration) {
		this.preTitleDuration = preTitleDuration;
	}
	public String getProgramDuration1() {
		return programDuration1;
	}
	public void setProgramDuration1(String programDuration1) {
		this.programDuration1 = programDuration1;
	}
	public String getPostTitleSom() {
		return postTitleSom;
	}
	public void setPostTitleSom(String postTitleSom) {
		this.postTitleSom = postTitleSom;
	}
	public String getPostTitleDuration() {
		return postTitleDuration;
	}
	public void setPostTitleDuration(String postTitleDuration) {
		this.postTitleDuration = postTitleDuration;
	}
	public String getPostSponsorCaptionSom() {
		return postSponsorCaptionSom;
	}
	public void setPostSponsorCaptionSom(String postSponsorCaptionSom) {
		this.postSponsorCaptionSom = postSponsorCaptionSom;
	}
	public String getEndTitleId() {
		return endTitleId;
	}
	public void setEndTitleId(String endTitleId) {
		this.endTitleId = endTitleId;
	}
	public String getProgramInternalCmStartOfMark1() {
		return programInternalCmStartOfMark1;
	}
	public void setProgramInternalCmStartOfMark1(
			String programInternalCmStartOfMark1) {
		this.programInternalCmStartOfMark1 = programInternalCmStartOfMark1;
	}
	public String getProgramInternalCmStartOfMark2() {
		return programInternalCmStartOfMark2;
	}
	public void setProgramInternalCmStartOfMark2(
			String programInternalCmStartOfMark2) {
		this.programInternalCmStartOfMark2 = programInternalCmStartOfMark2;
	}
	public String getProgramInternalCmStartOfMark3() {
		return programInternalCmStartOfMark3;
	}
	public void setProgramInternalCmStartOfMark3(
			String programInternalCmStartOfMark3) {
		this.programInternalCmStartOfMark3 = programInternalCmStartOfMark3;
	}
	public String getProgramInternalCmStartOfMark4() {
		return programInternalCmStartOfMark4;
	}
	public void setProgramInternalCmStartOfMark4(
			String programInternalCmStartOfMark4) {
		this.programInternalCmStartOfMark4 = programInternalCmStartOfMark4;
	}
	public String getProgramInternalCmStartOfMark5() {
		return programInternalCmStartOfMark5;
	}
	public void setProgramInternalCmStartOfMark5(
			String programInternalCmStartOfMark5) {
		this.programInternalCmStartOfMark5 = programInternalCmStartOfMark5;
	}
	public String getProgramInternalCmStartOfMark6() {
		return programInternalCmStartOfMark6;
	}
	public void setProgramInternalCmStartOfMark6(
			String programInternalCmStartOfMark6) {
		this.programInternalCmStartOfMark6 = programInternalCmStartOfMark6;
	}
	public String getProgramCode() {
		return programCode;
	}
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	public String getProgrammingCode() {
		return programmingCode;
	}
	public void setProgrammingCode(String programmingCode) {
		this.programmingCode = programmingCode;
	}
	public Integer getProgramSequenceNumber() {
		return programSequenceNumber;
	}
	public void setProgramSequenceNumber(Integer programSequenceNumber) {
		this.programSequenceNumber = programSequenceNumber;
	}
	public String getProgramOnairStartTime() {
		return programOnairStartTime;
	}
	public void setProgramOnairStartTime(String programOnairStartTime) {
		this.programOnairStartTime = programOnairStartTime;
	}
	public String getReturnClassification() {
		return returnClassification;
	}
	public void setReturnClassification(String returnClassification) {
		this.returnClassification = returnClassification;
	}
	public String getScheduleUniqueId() {
		return scheduleUniqueId;
	}
	public void setScheduleUniqueId(String scheduleUniqueId) {
		this.scheduleUniqueId = scheduleUniqueId;
	}
	public String getPreSponsorCaptionSom() {
		return preSponsorCaptionSom;
	}
	public void setPreSponsorCaptionSom(String preSponsorCaptionSom) {
		this.preSponsorCaptionSom = preSponsorCaptionSom;
	}
	public String getProgramStartOfMark1() {
		return programStartOfMark1;
	}
	public void setProgramStartOfMark1(String programStartOfMark1) {
		this.programStartOfMark1 = programStartOfMark1;
	}
	public String getClosedCaptionYn() {
		return closedCaptionYn;
	}
	public void setClosedCaptionYn(String closedCaptionYn) {
		this.closedCaptionYn = closedCaptionYn;
	}
	public String getBilingualLanguage() {
		return bilingualLanguage;
	}
	public void setBilingualLanguage(String bilingualLanguage) {
		this.bilingualLanguage = bilingualLanguage;
	}
	public String getSlipCgId() {
		return slipCgId;
	}
	public void setSlipCgId(String slipCgId) {
		this.slipCgId = slipCgId;
	}
	public String getMainStory() {
		return mainStory;
	}
	public void setMainStory(String mainStory) {
		this.mainStory = mainStory;
	}
	public String getProgrammingLocalStationCode() {
		return programmingLocalStationCode;
	}
	public void setProgrammingLocalStationCode(String programmingLocalStationCode) {
		this.programmingLocalStationCode = programmingLocalStationCode;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public Integer getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(Integer partNumber) {
		this.partNumber = partNumber;
	}
	public String getProgramTitle() {
		return programTitle;
	}
	public void setProgramTitle(String programTitle) {
		this.programTitle = programTitle;
	}
	public String getProgramSubTitle() {
		return programSubTitle;
	}
	public void setProgramSubTitle(String programSubTitle) {
		this.programSubTitle = programSubTitle;
	}
	public String getProducerName() {
		return producerName;
	}
	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}
	public Date getBroadcastPlannedDate() {
		return broadcastPlannedDate;
	}
	public void setBroadcastPlannedDate(Date broadcastPlannedDate) {
		this.broadcastPlannedDate = broadcastPlannedDate;
	}
	public String getBroadcastEventKind() {
		return broadcastEventKind;
	}
	public void setBroadcastEventKind(String broadcastEventKind) {
		this.broadcastEventKind = broadcastEventKind;
	}
	public String getTailerTitle() {
		return tailerTitle;
	}
	public void setTailerTitle(String tailerTitle) {
		this.tailerTitle = tailerTitle;
	}
	public Date getSpotStartDate() {
		return spotStartDate;
	}
	public void setSpotStartDate(Date spotStartDate) {
		this.spotStartDate = spotStartDate;
	}
	public Date getSpotEndDate() {
		return spotEndDate;
	}
	public void setSpotEndDate(Date spotEndDate) {
		this.spotEndDate = spotEndDate;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getClipId() {
		return clipId;
	}
	public void setClipId(String clipId) {
		this.clipId = clipId;
	}
	public String getStartOfMark1() {
		return startOfMark1;
	}
	public void setStartOfMark1(String startOfMark1) {
		this.startOfMark1 = startOfMark1;
	}
	public String getProductionDuration() {
		return productionDuration;
	}
	public void setProductionDuration(String productionDuration) {
		this.productionDuration = productionDuration;
	}
	public String getProductionVideoQualty() {
		return productionVideoQualty;
	}
	public void setProductionVideoQualty(String productionVideoQualty) {
		this.productionVideoQualty = productionVideoQualty;
	}
	public String getAspectRatio() {
		return aspectRatio;
	}
	public void setAspectRatio(String aspectRatio) {
		this.aspectRatio = aspectRatio;
	}
	public String getSdConvertingAspectRatio() {
		return sdConvertingAspectRatio;
	}
	public void setSdConvertingAspectRatio(String sdConvertingAspectRatio) {
		this.sdConvertingAspectRatio = sdConvertingAspectRatio;
	}
	public String getAudioTransmissionType() {
		return audioTransmissionType;
	}
	public void setAudioTransmissionType(String audioTransmissionType) {
		this.audioTransmissionType = audioTransmissionType;
	}
	public String getTrasmissionAudioModeMain() {
		return trasmissionAudioModeMain;
	}
	public void setTrasmissionAudioModeMain(String trasmissionAudioModeMain) {
		this.trasmissionAudioModeMain = trasmissionAudioModeMain;
	}
	public String getTransmissionAudioModeSub() {
		return transmissionAudioModeSub;
	}
	public void setTransmissionAudioModeSub(String transmissionAudioModeSub) {
		this.transmissionAudioModeSub = transmissionAudioModeSub;
	}
	public String getProductionMaterialtype() {
		return productionMaterialtype;
	}
	public void setProductionMaterialtype(String productionMaterialtype) {
		this.productionMaterialtype = productionMaterialtype;
	}
	public Integer getNleNumber() {
		return nleNumber;
	}
	public void setNleNumber(Integer nleNumber) {
		this.nleNumber = nleNumber;
	}
	public String getProductionProducerName() {
		return productionProducerName;
	}
	public void setProductionProducerName(String productionProducerName) {
		this.productionProducerName = productionProducerName;
	}
	public String getProductionTechnicalDirectorName() {
		return productionTechnicalDirectorName;
	}
	public void setProductionTechnicalDirectorName(
			String productionTechnicalDirectorName) {
		this.productionTechnicalDirectorName = productionTechnicalDirectorName;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public String getProductionPlace() {
		return productionPlace;
	}
	public void setProductionPlace(String productionPlace) {
		this.productionPlace = productionPlace;
	}
	public Timestamp getTransmissionDatetime() {
		return transmissionDatetime;
	}
	public void setTransmissionDatetime(Timestamp transmissionDatetime) {
		this.transmissionDatetime = transmissionDatetime;
	}
	public String getTransmissionEmployeeName() {
		return transmissionEmployeeName;
	}
	public void setTransmissionEmployeeName(String transmissionEmployeeName) {
		this.transmissionEmployeeName = transmissionEmployeeName;
	}
	public String getProductionDepartmentCode() {
		return productionDepartmentCode;
	}
	public void setProductionDepartmentCode(String productionDepartmentCode) {
		this.productionDepartmentCode = productionDepartmentCode;
	}
	public String getSpotSponsorYn() {
		return spotSponsorYn;
	}
	public void setSpotSponsorYn(String spotSponsorYn) {
		this.spotSponsorYn = spotSponsorYn;
	}
	public String getProductionDetailType() {
		return productionDetailType;
	}
	public void setProductionDetailType(String productionDetailType) {
		this.productionDetailType = productionDetailType;
	}
	public String getVcrNumber() {
		return vcrNumber;
	}
	public void setVcrNumber(String vcrNumber) {
		this.vcrNumber = vcrNumber;
	}
	public String getProgramDeliberationGrade() {
		return programDeliberationGrade;
	}
	public void setProgramDeliberationGrade(String programDeliberationGrade) {
		this.programDeliberationGrade = programDeliberationGrade;
	}
	public String getCaptionLanguageKind() {
		return captionLanguageKind;
	}
	public void setCaptionLanguageKind(String captionLanguageKind) {
		this.captionLanguageKind = captionLanguageKind;
	}
	public String getPreviewVideoFilePath() {
		return previewVideoFilePath;
	}
	public void setPreviewVideoFilePath(String previewVideoFilePath) {
		this.previewVideoFilePath = previewVideoFilePath;
	}
	public Timestamp getIngestDateTime() {
		return ingestDateTime;
	}
	public void setIngestDateTime(Timestamp ingestDateTime) {
		this.ingestDateTime = ingestDateTime;
	}
	public String getIngestStatus() {
		return ingestStatus;
	}
	public void setIngestStatus(String ingestStatus) {
		this.ingestStatus = ingestStatus;
	}
	public String getPlayoutTransmissionYn() {
		return playoutTransmissionYn;
	}
	public void setPlayoutTransmissionYn(String playoutTransmissionYn) {
		this.playoutTransmissionYn = playoutTransmissionYn;
	}
	public String getEncodingRequestYn() {
		return encodingRequestYn;
	}
	public void setEncodingRequestYn(String encodingRequestYn) {
		this.encodingRequestYn = encodingRequestYn;
	}
	public String getProgramCopyrightRelation() {
		return programCopyrightRelation;
	}
	public void setProgramCopyrightRelation(String programCopyrightRelation) {
		this.programCopyrightRelation = programCopyrightRelation;
	}
	public String getClipRetransmissionYn() {
		return clipRetransmissionYn;
	}
	public void setClipRetransmissionYn(String clipRetransmissionYn) {
		this.clipRetransmissionYn = clipRetransmissionYn;
	}
	
}
