package kr.co.d2net.dto.xml;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.d2net.commons.converter.xml.DateConverter;
import kr.co.d2net.commons.converter.xml.IntegerConverter;
import kr.co.d2net.commons.converter.xml.LongConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.commons.converter.xml.TimeConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("BasicDescription")
public class BasicDescription {
	
	@XStreamAlias("VodCnt")
	@XStreamConverter(IntegerConverter.class)
	private Integer VodCnt;
	
	@XStreamAlias("ProgramCode")
	@XStreamConverter(TextConverter.class)
	private String ProgramCode;
	
	@XStreamAlias("ProgramId")
	@XStreamConverter(TextConverter.class)
	private String ProgramId;
	
	@XStreamImplicit(itemFieldName="Title")
	private List<Type> title = new ArrayList<Type>();
	
	@XStreamImplicit(itemFieldName="MediaCode")
	private List<Type> mediaCode = new ArrayList<Type>();
	
	@XStreamAlias("ProgrammingLocalStationName")
	@XStreamConverter(TextConverter.class)
	private String ProgrammingLocalStationName;
	
	@XStreamAlias("NewsKind")
	@XStreamConverter(TextConverter.class)
	private String NewsKind;
	
	@XStreamAlias("DomesticForeignKind")
	@XStreamConverter(TextConverter.class)
	private String DomesticForeignKind;
	
	@XStreamAlias("ProductionBodyClassification")
	@XStreamConverter(TextConverter.class)
	private String ProductionBodyClassification;
	
	@XStreamAlias("CmProgramYn")
	@XStreamConverter(TextConverter.class)
	private String CmProgramYn;
	
	@XStreamAlias("BroadcastLanguageKind")
	@XStreamConverter(TextConverter.class)
	private String BroadcastLanguageKind;
	
	@XStreamAlias("ChannelCode")
	@XStreamConverter(IntegerConverter.class)
	private Integer ChannelCode;
	
	@XStreamImplicit(itemFieldName="Genre")
	private List<Type> genre = new ArrayList<Type>();
	
	@XStreamAlias("Intention")
	@XStreamConverter(TextConverter.class)
	private String Intention;
	
	@XStreamAlias("OutsiderProductionName")
	@XStreamConverter(TextConverter.class)
	private String OutsiderProductionName;
	
	@XStreamAlias("ProgramPlannedDurationMin")
	@XStreamConverter(IntegerConverter.class)
	private Integer ProgramPlannedDurationMin;
	
	@XStreamAlias("ProgramSequenceNumber")
	@XStreamConverter(LongConverter.class)
	private Long ProgramSequenceNumber;
	
	@XStreamAlias("OnairDate")
	@XStreamConverter(DateConverter.class)
	private Date OnairDate;
	
	@XStreamAlias("PartNumber")
	@XStreamConverter(IntegerConverter.class)
	private Integer PartNumber;
	
	@XStreamAlias("AudioModeMain")
	@XStreamConverter(TextConverter.class)
	private String AudioModeMain;
	
	@XStreamAlias("ProgramDeliberationGrade")
	@XStreamConverter(TextConverter.class)
	private String ProgramDeliberationGrade;
	
	@XStreamAlias("ProductionVideoQuality")
	@XStreamConverter(TextConverter.class)
	private String ProductionVideoQuality;
	
	@XStreamAlias("ProgramStartDate")
	@XStreamConverter(DateConverter.class)
	private Date ProgramStartDate;
	
	@XStreamAlias("ProgramEndDate")
	@XStreamConverter(DateConverter.class)
	private Date ProgramEndDate;
	
	@XStreamAlias("ProgramPlannedStartTime")
	@XStreamConverter(TimeConverter.class)
	private Time ProgramPlannedStartTime;
	
	@XStreamAlias("RegularSpecialClassification")
	@XStreamConverter(TextConverter.class)
	private String RegularSpecialClassification;
	
	@XStreamAlias("BroadcastCompleteYn")
	@XStreamConverter(TextConverter.class)
	private String BroadcastCompleteYn;
	
	@XStreamAlias("ThumnailImage")
	@XStreamConverter(TextConverter.class)
	private String ThumnailImage;
	
	@XStreamAlias("ProgramOnairDay")
	@XStreamConverter(TextConverter.class)
	private String ProgramOnairDay;
	
	@XStreamAlias("CopyrightYn")
	@XStreamConverter(TextConverter.class)
	private String CopyrightYn;
	
	@XStreamAlias("Synopsis")
	@XStreamConverter(TextConverter.class)
	private String Synopsis;
	
	@XStreamAlias("Keywords")
	@XStreamConverter(TextConverter.class)
	private String Keywords;
	
	
	public void setMediaCode(List<Type> mediaCode) {
		this.mediaCode = mediaCode;
	}
	public List<Type> getMediaCode() {
		return mediaCode;
	}
	public void addMediaCode(Type type) {
		this.mediaCode.add(type);
	}
	
	public Integer getVodCnt() {
		return VodCnt;
	}
	public void setVodCnt(Integer vodCnt) {
		VodCnt = vodCnt;
	}
	public String getProgramId() {
		return ProgramId;
	}
	public void setProgramId(String programId) {
		ProgramId = programId;
	}
	public Long getProgramSequenceNumber() {
		return ProgramSequenceNumber;
	}
	public void setProgramSequenceNumber(Long programSequenceNumber) {
		ProgramSequenceNumber = programSequenceNumber;
	}
	public Date getOnairDate() {
		return OnairDate;
	}
	public void setOnairDate(Date onairDate) {
		OnairDate = onairDate;
	}
	public Integer getPartNumber() {
		return PartNumber;
	}
	public void setPartNumber(Integer partNumber) {
		PartNumber = partNumber;
	}
	public String getProgramCode() {
		return ProgramCode;
	}
	public void setProgramCode(String programCode) {
		ProgramCode = programCode;
	}
	public List<Type> getTitle() {
		return title;
	}
	public void setTitle(List<Type> title) {
		this.title = title;
	}
	public void addTitle(Type title) {
		this.title.add(title);
	}
	public String getProgrammingLocalStationName() {
		return ProgrammingLocalStationName;
	}
	public void setProgrammingLocalStationName(String programmingLocalStationName) {
		ProgrammingLocalStationName = programmingLocalStationName;
	}
	public String getNewsKind() {
		return NewsKind;
	}
	public void setNewsKind(String newsKind) {
		NewsKind = newsKind;
	}
	public String getDomesticForeignKind() {
		return DomesticForeignKind;
	}
	public void setDomesticForeignKind(String domesticForeignKind) {
		DomesticForeignKind = domesticForeignKind;
	}
	public String getProductionBodyClassification() {
		return ProductionBodyClassification;
	}
	public void setProductionBodyClassification(String productionBodyClassification) {
		ProductionBodyClassification = productionBodyClassification;
	}
	public String getCmProgramYn() {
		return CmProgramYn;
	}
	public void setCmProgramYn(String cmProgramYn) {
		CmProgramYn = cmProgramYn;
	}
	public String getBroadcastLanguageKind() {
		return BroadcastLanguageKind;
	}
	public void setBroadcastLanguageKind(String broadcastLanguageKind) {
		BroadcastLanguageKind = broadcastLanguageKind;
	}
	public Integer getChannelCode() {
		return ChannelCode;
	}
	public void setChannelCode(Integer channelCode) {
		ChannelCode = channelCode;
	}
	public List<Type> getGenre() {
		return genre;
	}
	public void setGenre(List<Type> genre) {
		this.genre = genre;
	}
	public void addGenre(Type genre) {
		this.genre.add(genre);
	}
	public String getIntention() {
		return Intention;
	}
	public void setIntention(String intention) {
		Intention = intention;
	}
	public String getOutsiderProductionName() {
		return OutsiderProductionName;
	}
	public void setOutsiderProductionName(String outsiderProductionName) {
		OutsiderProductionName = outsiderProductionName;
	}
	public Integer getProgramPlannedDurationMin() {
		return ProgramPlannedDurationMin;
	}
	public void setProgramPlannedDurationMin(Integer programPlannedDurationMin) {
		ProgramPlannedDurationMin = programPlannedDurationMin;
	}
	public String getAudioModeMain() {
		return AudioModeMain;
	}
	public void setAudioModeMain(String audioModeMain) {
		AudioModeMain = audioModeMain;
	}
	public String getProgramDeliberationGrade() {
		return ProgramDeliberationGrade;
	}
	public void setProgramDeliberationGrade(String programDeliberationGrade) {
		ProgramDeliberationGrade = programDeliberationGrade;
	}
	public String getProductionVideoQuality() {
		return ProductionVideoQuality;
	}
	public void setProductionVideoQuality(String productionVideoQuality) {
		ProductionVideoQuality = productionVideoQuality;
	}
	public Date getProgramStartDate() {
		return ProgramStartDate;
	}
	public void setProgramStartDate(Date programStartDate) {
		ProgramStartDate = programStartDate;
	}
	public Date getProgramEndDate() {
		return ProgramEndDate;
	}
	public void setProgramEndDate(Date programEndDate) {
		ProgramEndDate = programEndDate;
	}
	public Time getProgramPlannedStartTime() {
		return ProgramPlannedStartTime;
	}
	public void setProgramPlannedStartTime(Time programPlannedStartTime) {
		ProgramPlannedStartTime = programPlannedStartTime;
	}
	public String getRegularSpecialClassification() {
		return RegularSpecialClassification;
	}
	public void setRegularSpecialClassification(String regularSpecialClassification) {
		RegularSpecialClassification = regularSpecialClassification;
	}
	public String getBroadcastCompleteYn() {
		return BroadcastCompleteYn;
	}
	public void setBroadcastCompleteYn(String broadcastCompleteYn) {
		BroadcastCompleteYn = broadcastCompleteYn;
	}
	public String getThumnailImage() {
		return ThumnailImage;
	}
	public void setThumnailImage(String thumnailImage) {
		ThumnailImage = thumnailImage;
	}
	public String getProgramOnairDay() {
		return ProgramOnairDay;
	}
	public void setProgramOnairDay(String programOnairDay) {
		ProgramOnairDay = programOnairDay;
	}
	public String getCopyrightYn() {
		return CopyrightYn;
	}
	public void setCopyrightYn(String copyrightYn) {
		CopyrightYn = copyrightYn;
	}
	public String getSynopsis() {
		return Synopsis;
	}
	public void setSynopsis(String synopsis) {
		Synopsis = synopsis;
	}
	public String getKeywords() {
		return Keywords;
	}
	public void setKeywords(String keywords) {
		Keywords = keywords;
	}
	
}
