package kr.co.d2net.dto.xml.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.d2net.commons.converter.xml.DateConverter;
import kr.co.d2net.commons.converter.xml.LongConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.commons.converter.xml.TextUTF8Converter;
import kr.co.d2net.dto.xml.aach.Corners;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("content")
public class Content {

	@XStreamAlias("ct_id")
	@XStreamConverter(LongConverter.class)
	private Long ctId;

	@XStreamAlias("ct_nm")
	@XStreamConverter(TextUTF8Converter.class)
	private String ctNm;

	@XStreamAlias("brd_dd")
	@XStreamConverter(DateConverter.class)
	private Date brdDd;

	@XStreamAlias("ct_cla")
	@XStreamConverter(TextConverter.class)
	private String ctCla;

	@XStreamAlias("ct_typ")
	@XStreamConverter(TextConverter.class)
	private String ctTyp;

	@XStreamAlias("pgm_id")
	@XStreamConverter(TextConverter.class)
	private String pgmId;

	@XStreamAlias("pgm_cd")
	@XStreamConverter(TextConverter.class)
	private String pgmCd;
	
	@XStreamAlias("pgm_grp_cd")
	@XStreamConverter(TextConverter.class)
	private String pgmGrpCd;

	@XStreamAlias("pgm_nm")
	@XStreamConverter(TextUTF8Converter.class)
	private String pgmNm;

	@XStreamAlias("fl_path")
	@XStreamConverter(TextConverter.class)
	private String flPath;

	@XStreamAlias("cti_id")
	@XStreamConverter(LongConverter.class)
	private Long ctiId;

	@XStreamAlias("cti_fmt")
	@XStreamConverter(TextConverter.class)
	private String ctiFmt;

	@XStreamAlias("job_id")
	@XStreamConverter(LongConverter.class)
	private Long jobId;
	
	@XStreamAlias("job_gb")
	@XStreamConverter(TextConverter.class)
	private String jobGb;

	@XStreamAlias("job_state")
	@XStreamConverter(TextConverter.class)
	private String jobState;

	
	@XStreamAlias("file_size")
	@XStreamConverter(LongConverter.class)
	private Long fileSize;

	@XStreamAlias("pgm_num")
	@XStreamConverter(LongConverter.class)
	private Long pgmNum;
	
	@XStreamAlias("regrid")
	@XStreamConverter(TextConverter.class)
	private String regrid;
	
	@XStreamAlias("frm_per_sec")
	@XStreamConverter(TextConverter.class)
	private String frmPerSec;

	@XStreamAlias("job_err_code")
	@XStreamConverter(TextConverter.class)
	private String jobErrCode;

	@XStreamAlias("trimm_ste")
	@XStreamConverter(TextConverter.class)
	private String trimmSte;

	@XStreamAlias("channel_cd")
	@XStreamConverter(TextConverter.class)
	private String channelCd;

	@XStreamAlias("fl_nm")
	@XStreamConverter(TextConverter.class)
	private String flNm;

	@XStreamAlias("fl_ext")
	@XStreamConverter(TextConverter.class)
	private String flExt;

	@XStreamAlias("vd_qlty")
	@XStreamConverter(TextConverter.class)
	private String vdQlty;
	
	@XStreamAlias("company")
	@XStreamConverter(TextConverter.class)
	private String company;
	
	@XStreamAlias("bit_rate")
	@XStreamConverter(TextConverter.class)
	private String bitRate;
	
	@XStreamAlias("pro_flnm")
	@XStreamConverter(TextConverter.class)
	private String proFlnm;
	
	@XStreamAlias("corners")
	private Corners corners;
	

	public Corners getCorners() {
		return corners;
	}
	public void setCorners(Corners corners) {
		this.corners = corners;
	}

	public String getBitRate() {
		return bitRate;
	}

	public void setBitRate(String bitRate) {
		this.bitRate = bitRate;
	}

	public String getProFlnm() {
		return proFlnm;
	}

	public void setProFlnm(String proFlnm) {
		this.proFlnm = proFlnm;
	}

	public Long getPgmNum() {
		return pgmNum;
	}

	public void setPgmNum(Long pgmNum) {
		this.pgmNum = pgmNum;
	}

	public String getRegrid() {
		return regrid;
	}

	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}

	public String getPgmGrpCd() {
		return pgmGrpCd;
	}

	public void setPgmGrpCd(String pgmGrpCd) {
		this.pgmGrpCd = pgmGrpCd;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJobGb() {
		return jobGb;
	}

	public void setJobGb(String jobGb) {
		this.jobGb = jobGb;
	}

	public String getVdQlty() {
		return vdQlty;
	}

	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}

	public String getPgmCd() {
		return pgmCd;
	}

	public void setPgmCd(String pgmCd) {
		this.pgmCd = pgmCd;
	}

	public String getChannelCd() {
		return channelCd;
	}

	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}

	public String getFlExt() {
		return flExt;
	}

	public void setFlExt(String flExt) {
		this.flExt = flExt;
	}

	public String getFlNm() {
		return flNm;
	}

	public void setFlNm(String flNm) {
		this.flNm = flNm;
	}

	public String getTrimmSte() {
		return trimmSte;
	}

	public void setTrimmSte(String trimmSte) {
		this.trimmSte = trimmSte;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobState() {
		return jobState;
	}

	public void setJobState(String jobState) {
		this.jobState = jobState;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFrmPerSec() {
		return frmPerSec;
	}

	public void setFrmPerSec(String frmPerSec) {
		this.frmPerSec = frmPerSec;
	}

	public String getJobErrCode() {
		return jobErrCode;
	}

	public void setJobErrCode(String jobErrCode) {
		this.jobErrCode = jobErrCode;
	}

	public String getPgmId() {
		return pgmId;
	}

	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}

	public String getPgmNm() {
		return pgmNm;
	}

	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}

	public String getFlPath() {
		return flPath;
	}

	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}

	public Long getCtiId() {
		return ctiId;
	}

	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}

	public String getCtiFmt() {
		return ctiFmt;
	}

	public void setCtiFmt(String ctiFmt) {
		this.ctiFmt = ctiFmt;
	}

	public Long getCtId() {
		return ctId;
	}

	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

	public String getCtNm() {
		return ctNm;
	}

	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}

	public Date getBrdDd() {
		return brdDd;
	}

	public void setBrdDd(Date brdDd) {
		this.brdDd = brdDd;
	}

	public String getCtCla() {
		return ctCla;
	}

	public void setCtCla(String ctCla) {
		this.ctCla = ctCla;
	}

	public String getCtTyp() {
		return ctTyp;
	}

	public void setCtTyp(String ctTyp) {
		this.ctTyp = ctTyp;
	}

}
