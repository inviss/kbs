package kr.co.d2net.dto.xml.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.d2net.commons.converter.xml.DateConverter;
import kr.co.d2net.commons.converter.xml.IntegerConverter;
import kr.co.d2net.commons.converter.xml.LongConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.commons.converter.xml.TextUTF8Converter;
import kr.co.d2net.dto.xml.archive.ArchiveReq;
import kr.co.d2net.dto.xml.archive.ArchiveRes;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("workflow")
public class Workflow {
	
	@XStreamAlias("source_path")
	@XStreamConverter(TextConverter.class)
	private String sourcePath;
	
	@XStreamAlias("source_file")
	@XStreamConverter(TextConverter.class)
	private String sourceFile;
	
	@XStreamAlias("target_path")
	@XStreamConverter(TextConverter.class)
	private String targetPath;
	
	@XStreamAlias("qc_yn")
	@XStreamConverter(TextConverter.class)
	private String qcYn;
	
	@XStreamAlias("default_opt")
	@XStreamConverter(TextConverter.class)
	private String defaultOpt;
	
	@XStreamAlias("gubun")
	@XStreamConverter(TextConverter.class)
	private String gubun;
	
	@XStreamAlias("keyword")
	@XStreamConverter(TextUTF8Converter.class)
	private String keyword;
	
	@XStreamAlias("eq_id")
	@XStreamConverter(TextConverter.class)
	private String eqId;
	
	@XStreamAlias("pgm_id")
	@XStreamConverter(TextConverter.class)
	private String pgmId;
	
	@XStreamAlias("start_dt")
	@XStreamConverter(DateConverter.class)
	private Date startDt;
	
	@XStreamAlias("end_dt")
	@XStreamConverter(DateConverter.class)
	private Date endDt;
	
	@XStreamAlias("count")
	@XStreamConverter(IntegerConverter.class)
	private Integer count;
	
	@XStreamAlias("cti_Id")
	@XStreamConverter(LongConverter.class)
	private Long ctiId;
	
	@XStreamAlias("channel_cd")
	@XStreamConverter(TextConverter.class)
	private String channelCd;
	
	@XStreamAlias("audio_mode")
	@XStreamConverter(TextConverter.class)
	private String audioMode;
	@XStreamAlias("start_time")
	@XStreamConverter(TextConverter.class)
	private String startTime;
	@XStreamAlias("end_time")
	@XStreamConverter(TextConverter.class)
	private String endTime;
	
	public String getAudioMode() {
		return audioMode;
	}
	public void setAudioMode(String audioMode) {
		this.audioMode = audioMode;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}
	public Long getCtiId() {
		return ctiId;
	}
	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public String getSourceFile() {
		return sourceFile;
	}
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	public String getTargetPath() {
		return targetPath;
	}
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	public String getQcYn() {
		return qcYn;
	}
	public void setQcYn(String qcYn) {
		this.qcYn = qcYn;
	}
	public String getDefaultOpt() {
		return defaultOpt;
	}
	public void setDefaultOpt(String defaultOpt) {
		this.defaultOpt = defaultOpt;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getEqId() {
		return eqId;
	}
	public void setEqId(String eqId) {
		this.eqId = eqId;
	}
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	public Date getStartDt() {
		return startDt;
	}
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}
	public Date getEndDt() {
		return endDt;
	}
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	@XStreamAlias("job")
	private Job job;
	
	@XStreamAlias("status")
	private Status status;
	
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	@XStreamAlias("content")
	private Content content;
	
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	
	@XStreamAlias("content_inst")
	private ContentInst contentInst;
	
	public ContentInst getContentInst() {
		return contentInst;
	}
	public void setContentInst(ContentInst contentInst) {
		this.contentInst = contentInst;
	}
	
	@XStreamAlias("pgm")
	private Pgm pgm;
	
	public Pgm getPgm() {
		return pgm;
	}
	public void setPgm(Pgm pgm) {
		this.pgm = pgm;
	}
	
	@XStreamAlias("sgm")
	private Sgm sgm;

	public Sgm getSgm() {
		return sgm;
	}
	public void setSgm(Sgm sgm) {
		this.sgm = sgm;
	}
	
	@XStreamAlias("list")
	private FindList list;

	public FindList getList() {
		return list;
	}
	public void setList(FindList list) {
		this.list = list;
	}
	
	@XStreamAlias("request")
	private ArchiveReq archiveReq;
	public ArchiveReq getArchiveReq() {
		return archiveReq;
	}
	public void setArchiveReq(ArchiveReq archiveReq) {
		this.archiveReq = archiveReq;
	}

	@XStreamAlias("response")
	private ArchiveRes archiveRes;
	public ArchiveRes getArchiveRes() {
		return archiveRes;
	}
	public void setArchiveRes(ArchiveRes archiveRes) {
		this.archiveRes = archiveRes;
	}
	
	@XStreamAlias("report")
	private Report report;

	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	
	@XStreamAlias("mnc")
	private Mnc mnc;

	public Mnc getMnc() {
		return mnc;
	}
	public void setMnc(Mnc mnc) {
		this.mnc = mnc;
	}
	
	@XStreamImplicit(itemFieldName="pgm")
	List<Pgm> pgmList = new ArrayList<Pgm>();

	public List<Pgm> getPgmList() {
		return pgmList;
	}
	public void setPgmList(List<Pgm> pgmList) {
		this.pgmList = pgmList;
	}
	public void addPgmList(Pgm pgm) {
		this.pgmList.add(pgm);
	}
	
	@XStreamImplicit(itemFieldName="content")
	List<Content> contentList = new ArrayList<Content>();

	public List<Content> getContentList() {
		return contentList;
	}
	public void setContentList(List<Content> contentList) {
		this.contentList = contentList;
	}
	public void addContentList(Content content) {
		this.contentList.add(content);
	}
	
	@XStreamImplicit(itemFieldName="job")
	List<Job> jobList = new ArrayList<Job>();

	public List<Job> getJobList() {
		return jobList;
	}
	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}
	public void addJobList(Job job) {
		this.jobList.add(job);
	}
	
}
