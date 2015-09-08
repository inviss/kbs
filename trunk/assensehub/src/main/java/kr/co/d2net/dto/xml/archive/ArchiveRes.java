package kr.co.d2net.dto.xml.archive;

import java.util.ArrayList;
import java.util.List;

import kr.co.d2net.commons.converter.xml.IntegerConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.commons.converter.xml.TextUTF8Converter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("response")
public class ArchiveRes {
	
	@XStreamAlias("pro_count")
	@XStreamConverter(IntegerConverter.class)
	private Integer proCount = 0;
	
	@XStreamAlias("news_count")
	@XStreamConverter(IntegerConverter.class)
	private Integer newsCount = 0;
	
	@XStreamAlias("raw_pro_count")
	@XStreamConverter(IntegerConverter.class)
	private Integer rawProCount = 0;
	
	@XStreamAlias("raw_news_count")
	@XStreamConverter(IntegerConverter.class)
	private Integer rawNewsCount = 0;
	
	@XStreamAlias("foreign_count")
	@XStreamConverter(IntegerConverter.class)
	private Integer foreignCount = 0;
	
	@XStreamImplicit(itemFieldName="pro_result")
	List<ArchiveRes> proResult = new ArrayList<ArchiveRes>();
	
	@XStreamImplicit(itemFieldName="news_result")
	List<ArchiveRes> newsResult = new ArrayList<ArchiveRes>();
	
	@XStreamImplicit(itemFieldName="raw_pro_result")
	List<ArchiveRes> rawProResult = new ArrayList<ArchiveRes>();
	
	@XStreamImplicit(itemFieldName="raw_news_result")
	List<ArchiveRes> rawNewsResult = new ArrayList<ArchiveRes>();
	
	@XStreamImplicit(itemFieldName="foreign_result")
	List<ArchiveRes> foreignResult = new ArrayList<ArchiveRes>();
	

	public List<ArchiveRes> getProResult() {
		return proResult;
	}
	public void setProResult(List<ArchiveRes> proResult) {
		this.proResult = proResult;
	}
	public void addProResult(ArchiveRes archiveRes) {
		this.proResult.add(archiveRes);
	}

	public List<ArchiveRes> getNewsResult() {
		return newsResult;
	}
	public void setNewsResult(List<ArchiveRes> newsResult) {
		this.newsResult = newsResult;
	}
	public void addNewsResult(ArchiveRes archiveRes) {
		this.newsResult.add(archiveRes);
	}

	public List<ArchiveRes> getRawProResult() {
		return rawProResult;
	}
	public void setRawProResult(List<ArchiveRes> rawProResult) {
		this.rawProResult = rawProResult;
	}
	public void addRawProResult(ArchiveRes archiveRes) {
		this.rawProResult.add(archiveRes);
	}

	public List<ArchiveRes> getRawNewsResult() {
		return rawNewsResult;
	}
	public void setRawNewsResult(List<ArchiveRes> rawNewsResult) {
		this.rawNewsResult = rawNewsResult;
	}
	public void addRawNewsResult(ArchiveRes archiveRes) {
		this.rawNewsResult.add(archiveRes);
	}

	public List<ArchiveRes> getForeignResult() {
		return foreignResult;
	}
	public void setForeignResult(List<ArchiveRes> foreignResult) {
		this.foreignResult = foreignResult;
	}
	public void addForeignResult(ArchiveRes archiveRes) {
		this.foreignResult.add(archiveRes);
	}

	@XStreamAlias("shooting_location")
	@XStreamConverter(TextConverter.class)
	private String shootingLocation;
	
	@XStreamAlias("program_idlink_id")
	@XStreamConverter(TextConverter.class)
	private String programIdlinkId;
	
	@XStreamAlias("link_id_segment_id")
	@XStreamConverter(TextConverter.class)
	private String linkIdSegmentId;
	
	@XStreamAlias("program_title")
	@XStreamConverter(TextUTF8Converter.class)
	private String programTitle;
	
	@XStreamAlias("view_title")
	@XStreamConverter(TextUTF8Converter.class)
	private String viewTitle;
	
	@XStreamAlias("channel")
	@XStreamConverter(TextConverter.class)
	private String channel;
	
	@XStreamAlias("onair_date")
	@XStreamConverter(TextConverter.class)
	private String onairDate;
	
	@XStreamAlias("country")
	@XStreamConverter(TextConverter.class)
	private String country;
	
	@XStreamAlias("length")
	@XStreamConverter(TextUTF8Converter.class)
	private String length;
	
	@XStreamAlias("organize")
	@XStreamConverter(TextUTF8Converter.class)
	private String organize;
	
	@XStreamAlias("page_url")
	@XStreamConverter(TextUTF8Converter.class)
	private String pageURL;
	
	
	public String getOrganize() {
		return organize;
	}
	public void setOrganize(String organize) {
		this.organize = organize;
	}
	public Integer getProCount() {
		return proCount;
	}

	public void setProCount(Integer proCount) {
		this.proCount = proCount;
	}

	public Integer getNewsCount() {
		return newsCount;
	}

	public void setNewsCount(Integer newsCount) {
		this.newsCount = newsCount;
	}

	public Integer getRawProCount() {
		return rawProCount;
	}

	public void setRawProCount(Integer rawProCount) {
		this.rawProCount = rawProCount;
	}

	public Integer getRawNewsCount() {
		return rawNewsCount;
	}

	public void setRawNewsCount(Integer rawNewsCount) {
		this.rawNewsCount = rawNewsCount;
	}

	public Integer getForeignCount() {
		return foreignCount;
	}

	public void setForeignCount(Integer foreignCount) {
		this.foreignCount = foreignCount;
	}

	public String getPageURL() {
		return pageURL;
	}

	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	public String getShootingLocation() {
		return shootingLocation;
	}

	public void setShootingLocation(String shootingLocation) {
		this.shootingLocation = shootingLocation;
	}

	public String getProgramIdlinkId() {
		return programIdlinkId;
	}

	public void setProgramIdlinkId(String programIdlinkId) {
		this.programIdlinkId = programIdlinkId;
	}

	public String getLinkIdSegmentId() {
		return linkIdSegmentId;
	}

	public void setLinkIdSegmentId(String linkIdSegmentId) {
		this.linkIdSegmentId = linkIdSegmentId;
	}

	public String getProgramTitle() {
		return programTitle;
	}

	public void setProgramTitle(String programTitle) {
		this.programTitle = programTitle;
	}

	public String getViewTitle() {
		return viewTitle;
	}

	public void setViewTitle(String viewTitle) {
		this.viewTitle = viewTitle;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOnairDate() {
		return onairDate;
	}

	public void setOnairDate(String onairDate) {
		this.onairDate = onairDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
	
}
