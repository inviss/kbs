package kr.co.d2net.dto;

public class OptTbl {

	private Integer optId; // 옵션 ID
	private String optDesc;// 옵션 설명
	private String optInfo;// 옵션 정보
	private String defaultYn;// 디폴트 여부
	private String useYn; // 사용여부

	

	public Integer getOptId() {
		return optId;
	}

	public void setOptId(Integer optId) {
		this.optId = optId;
	}

	public String getOptDesc() {
		return optDesc;
	}

	public void setOptDesc(String optDesc) {
		this.optDesc = optDesc;
	}

	public String getOptInfo() {
		return optInfo;
	}

	public void setOptInfo(String optInfo) {
		this.optInfo = optInfo;
	}

	public String getDefaultYn() {
		return defaultYn;
	}

	public void setDefaultYn(String defaultYn) {
		this.defaultYn = defaultYn;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

}
