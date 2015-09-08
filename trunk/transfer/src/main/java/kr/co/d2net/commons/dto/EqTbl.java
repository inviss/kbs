package kr.co.d2net.commons.dto;

public class EqTbl {
	
	private String eqId;
	private String eqNm;
	private String eqGb;
	private String eqIp;
	private String useYn;
	private String status;
	private Integer waitQ;
	private Integer useQ;
	private String jobId;
	private boolean high;
	
	public boolean isHigh() {
		return high;
	}
	public void setHigh(boolean high) {
		this.high = high;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public Integer getWaitQ() {
		return waitQ;
	}
	public void setWaitQ(Integer waitQ) {
		this.waitQ = waitQ;
	}
	public Integer getUseQ() {
		return useQ;
	}
	public void setUseQ(Integer useQ) {
		this.useQ = useQ;
	}
	public String getEqId() {
		return eqId;
	}
	public void setEqId(String eqId) {
		this.eqId = eqId;
	}
	public String getEqNm() {
		return eqNm;
	}
	public void setEqNm(String eqNm) {
		this.eqNm = eqNm;
	}
	public String getEqGb() {
		return eqGb;
	}
	public void setEqGb(String eqGb) {
		this.eqGb = eqGb;
	}
	public String getEqIp() {
		return eqIp;
	}
	public void setEqIp(String eqIp) {
		this.eqIp = eqIp;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
