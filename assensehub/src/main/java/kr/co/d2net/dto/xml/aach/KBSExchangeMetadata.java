package kr.co.d2net.dto.xml.aach;

import java.util.Date;

import kr.co.d2net.commons.converter.xml.DateConverter;
import kr.co.d2net.commons.converter.xml.IntegerConverter;
import kr.co.d2net.commons.converter.xml.LongConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.commons.converter.xml.TextUTF8Converter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("KBS_Exchange_Metadata")
public class KBSExchangeMetadata {
	
	@XStreamAlias("channel_cd")
	@XStreamConverter(TextConverter.class)
	private String channelCd;
	@XStreamAlias("program_code")
	@XStreamConverter(TextConverter.class)
	private String programCode;
	@XStreamAlias("program_ID")
	@XStreamConverter(TextConverter.class)
	private String programId;
	@XStreamAlias("program_title")
	@XStreamConverter(TextUTF8Converter.class)
	private String programTitle;
	@XStreamAlias("program_subtitle")
	@XStreamConverter(TextUTF8Converter.class)
	private String programSubtitle;
	@XStreamAlias("program_sequence_number")
	@XStreamConverter(IntegerConverter.class)
	private Integer programSequenceNumber;
	@XStreamAlias("broadcast_planned_date")
	@XStreamConverter(DateConverter.class)
	private Date broadcastPlannedDate;
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
	@XStreamAlias("clip_ID")
	@XStreamConverter(TextConverter.class)
	private String clipId;
	@XStreamAlias("clip_SIZE")
	@XStreamConverter(LongConverter.class)
	private Long clipSize;
	
	
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
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
	public Integer getProgramSequenceNumber() {
		return programSequenceNumber;
	}
	public void setProgramSequenceNumber(Integer programSequenceNumber) {
		this.programSequenceNumber = programSequenceNumber;
	}
	public Date getBroadcastPlannedDate() {
		return broadcastPlannedDate;
	}
	public void setBroadcastPlannedDate(Date broadcastPlannedDate) {
		this.broadcastPlannedDate = broadcastPlannedDate;
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
	public String getClipId() {
		return clipId;
	}
	public void setClipId(String clipId) {
		this.clipId = clipId;
	}
	public Long getClipSize() {
		return clipSize;
	}
	public void setClipSize(Long clipSize) {
		this.clipSize = clipSize;
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
