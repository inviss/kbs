package kr.co.d2net.dto.xml.internal;

import kr.co.d2net.commons.converter.xml.LongConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("status")
public class Status {
	
	@XStreamAlias("eq_id")
	@XStreamConverter(TextConverter.class)
	private String eqId;
	
	@XStreamAlias("job_gb")
	@XStreamConverter(TextConverter.class)
	private String jobGb = "V";
	
	@XStreamAlias("job_id")
	@XStreamConverter(LongConverter.class)
	private Long jobId;
	
	@XStreamAlias("pro_flid")
	@XStreamConverter(TextConverter.class)
	private String proFlid;
	
	@XStreamAlias("job_state")
	@XStreamConverter(TextConverter.class)
	private String jobState;
	
	@XStreamAlias("progress")
	@XStreamConverter(TextConverter.class)
	private String progress;
	
	@XStreamAlias("eq_state")
	@XStreamConverter(TextConverter.class)
	private String eqState;
	
	@XStreamAlias("pgm_nm")
	@XStreamConverter(TextConverter.class)
	private String pgmNm;
	
	@XStreamAlias("company")
	@XStreamConverter(TextConverter.class)
	private String company;
	
	@XStreamAlias("pro_flNm")
	@XStreamConverter(TextConverter.class)
	private String proFlNm;
	
	@XStreamAlias("wrk_file_nm")
	@XStreamConverter(TextConverter.class)
	private String wrkFileNm;
	
	@XStreamAlias("error_cd")
	@XStreamConverter(TextConverter.class)
	private String errorCd;
	
	
	public String getErrorCd() {
		return errorCd;
	}
	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getProFlNm() {
		return proFlNm;
	}
	public void setProFlNm(String proFlNm) {
		this.proFlNm = proFlNm;
	}
	public String getWrkFileNm() {
		return wrkFileNm;
	}
	public void setWrkFileNm(String wrkFileNm) {
		this.wrkFileNm = wrkFileNm;
	}
	public String getJobGb() {
		return jobGb;
	}
	public void setJobGb(String jobGb) {
		this.jobGb = jobGb;
	}
	public String getEqId() {
		return eqId;
	}
	public void setEqId(String eqId) {
		this.eqId = eqId;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getProFlid() {
		return proFlid;
	}
	public void setProFlid(String proFlid) {
		this.proFlid = proFlid;
	}
	public String getJobState() {
		return jobState;
	}
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getEqState() {
		return eqState;
	}
	public void setEqState(String eqState) {
		this.eqState = eqState;
	}
	
	
}
