package kr.co.d2net.dto;




public class CornerTbl {
	
	private Long cnId; // 코너 ID
	private Long ctId; // 콘텐츠 ID
	private String audSegmentId;	// 오디오 세그먼트 ID
	private String segmentId;	// 세그먼트 ID
	private String cnNm;	// 코너명
	private String bgnTime;
	private String endTime;
	
	public Long getCnId() {
		return cnId;
	}
	public void setCnId(Long cnId) {
		this.cnId = cnId;
	}
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}
	public String getAudSegmentId() {
		return audSegmentId;
	}
	public void setAudSegmentId(String audSegmentId) {
		this.audSegmentId = audSegmentId;
	}
	public String getSegmentId() {
		return segmentId;
	}
	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}
	public String getCnNm() {
		return cnNm;
	}
	public void setCnNm(String cnNm) {
		this.cnNm = cnNm;
	}
	public String getBgnTime() {
		return bgnTime;
	}
	public void setBgnTime(String bgnTime) {
		this.bgnTime = bgnTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
