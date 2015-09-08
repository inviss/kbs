package kr.co.d2net.dto;

public class ProOptTbl {

	private Integer optId; // 옵션 ID
	private String optDesc; // 옵션 내용
	private String proFlid;
	private String useYn; // 사용여부
	
	public String getOptDesc() {
		return optDesc;
	}
	public void setOptDesc(String optDesc) {
		this.optDesc = optDesc;
	}
	public Integer getOptId() {
		return optId;
	}
	public void setOptId(Integer optId) {
		this.optId = optId;
	}
	public String getProFlid() {
		return proFlid;
	}
	public void setProFlid(String proFlid) {
		this.proFlid = proFlid;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	
	

}
