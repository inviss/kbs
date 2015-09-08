package kr.co.d2net.dto.xml.aach;

import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.commons.converter.xml.TextUTF8Converter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("corner")
public class Corner {
	
	@XStreamAlias("corner_title")
	@XStreamConverter(TextUTF8Converter.class)
	private String cornerTitle;
	@XStreamAlias("segment_id")
	@XStreamConverter(TextConverter.class)
	private String segmentId;
	@XStreamAlias("corner_start_time")
	@XStreamConverter(TextConverter.class)
	private String cornerStartTime;
	@XStreamAlias("corner_end_time")
	@XStreamConverter(TextConverter.class)
	private String cornerEndTime;
	
	
	public String getCornerTitle() {
		return cornerTitle;
	}
	public void setCornerTitle(String cornerTitle) {
		this.cornerTitle = cornerTitle;
	}
	public String getSegmentId() {
		return segmentId;
	}
	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}
	public String getCornerStartTime() {
		return cornerStartTime;
	}
	public void setCornerStartTime(String cornerStartTime) {
		this.cornerStartTime = cornerStartTime;
	}
	public String getCornerEndTime() {
		return cornerEndTime;
	}
	public void setCornerEndTime(String cornerEndTime) {
		this.cornerEndTime = cornerEndTime;
	}
	
}
