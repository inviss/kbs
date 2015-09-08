package kr.co.d2net.dto;

public class QcReportTbl {

	private String reportId;
	private String frameNo;
	private String rtTcd;
	private String rtGb;
	private String errGb;
	private String reInfo;
	private Long seq;
	private String qlty;
	private String psnr;
	
	
	public String getErrGb() {
		return errGb;
	}
	public void setErrGb(String errGb) {
		this.errGb = errGb;
	}
	public String getFrameNo() {
		return frameNo;
	}
	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}
	public String getQlty() {
		return qlty;
	}
	public void setQlty(String qlty) {
		this.qlty = qlty;
	}
	public String getPsnr() {
		return psnr;
	}
	public void setPsnr(String psnr) {
		this.psnr = psnr;
	}
	public String getRtGb() {
		return rtGb;
	}
	public void setRtGb(String rtGb) {
		this.rtGb = rtGb;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReInfo() {
		return reInfo;
	}
	public void setReInfo(String reInfo) {
		this.reInfo = reInfo;
	}
	public String getRtTcd() {
		return rtTcd;
	}
	public void setRtTcd(String rtTcd) {
		this.rtTcd = rtTcd;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}

	

}
