package kr.co.d2net.dto;

import java.sql.Timestamp;
import java.util.Date;

public class LiveTbl {

	private String programCode; // 프로그램코드
	private String recyn; // 녹화여부
	private Date bgnTime; // 시작시간
	private Date endTime; // 종료시간
	private String regrid; // 등록자ID
	private Timestamp regDt; // 등록일시
	private String modrid; // 수정자ID
	private Timestamp modDt; // 수정일시
	private String rerunCode; //본방_재방코드
	
	
	public String getRerunCode() {
		return rerunCode;
	}

	public void setRerunCode(String rerunCode) {
		this.rerunCode = rerunCode;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getRecyn() {
		return recyn;
	}

	public void setRecyn(String recyn) {
		this.recyn = recyn;
	}

	public Date getBgnTime() {
		return bgnTime;
	}

	public void setBgnTime(Date bgnTime) {
		this.bgnTime = bgnTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRegrid() {
		return regrid;
	}

	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}

	public Timestamp getRegDt() {
		return regDt;
	}

	public void setRegDt(Timestamp regDt) {
		this.regDt = regDt;
	}

	public String getModrid() {
		return modrid;
	}

	public void setModrid(String modrid) {
		this.modrid = modrid;
	}

	public Timestamp getModDt() {
		return modDt;
	}

	public void setModDt(Timestamp modDt) {
		this.modDt = modDt;
	}

}
