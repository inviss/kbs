package kr.co.d2net.dto;

import java.sql.Timestamp;

public class CodeTbl {
	
	private String clfCd;	//구분코드
	private String sclCd;	//구분상세코드	
	private String clfNm;	//구분명
	private String note;	//설명
	private String rmk1;	//비고1
	private Integer rmk2;	//비고2
	private Timestamp regDt;	//등록일시
	private String regrid;	//등록자ID
	private Timestamp modDt;	//수정일시
	private String modrid;	//수정자ID	
	private String useYn;	//사용여부
	
	
	public Integer getRmk2() {
		return rmk2;
	}
	public void setRmk2(Integer rmk2) {
		this.rmk2 = rmk2;
	}
	public String getClfCd() {
		return clfCd;
	}
	public void setClfCd(String clfCd) {
		this.clfCd = clfCd;
	}
	public String getSclCd() {
		return sclCd;
	}
	public void setSclCd(String sclCd) {
		this.sclCd = sclCd;
	}
	public String getClfNm() {
		return clfNm;
	}
	public void setClfNm(String clfNm) {
		this.clfNm = clfNm;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getRmk1() {
		return rmk1;
	}
	public void setRmk1(String rmk1) {
		this.rmk1 = rmk1;
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
	public Timestamp getModDt() {
		return modDt;
	}
	public void setModDt(Timestamp modDt) {
		this.modDt = modDt;
	}
	public String getModrid() {
		return modrid;
	}
	public void setModrid(String modrid) {
		this.modrid = modrid;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	
	


	
}
