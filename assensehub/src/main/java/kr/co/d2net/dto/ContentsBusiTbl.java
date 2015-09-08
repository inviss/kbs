package kr.co.d2net.dto;

import java.sql.Timestamp;

public class ContentsBusiTbl {
	
	private Long ctiId;		//콘텐츠인스턴스ID
	private String busiPartnerId;	//사업자ID
	private String servUrl;		//서비스URL
	private Timestamp regDt;		//등록일시
	private String regrid;		//등록자ID	
	private String modDt;		//수정일시
	private Timestamp modrid;		//수정자ID	
	private String useYn;		//사용여부
	
	
	public Long getCtiId() {
		return ctiId;
	}
	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}
	public String getBusiPartnerId() {
		return busiPartnerId;
	}
	public void setBusiPartnerId(String busiPartnerId) {
		this.busiPartnerId = busiPartnerId;
	}
	public String getServUrl() {
		return servUrl;
	}
	public void setServUrl(String servUrl) {
		this.servUrl = servUrl;
	}
	public Timestamp getRegDt() {
		return regDt;
	}
	public void setRegDt(Timestamp regDt) {
		this.regDt = regDt;
	}
	public String getRegrid() {
		return regrid;
	}
	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public Timestamp getModrid() {
		return modrid;
	}
	public void setModrid(Timestamp modrid) {
		this.modrid = modrid;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	
	
	
	
	
}
