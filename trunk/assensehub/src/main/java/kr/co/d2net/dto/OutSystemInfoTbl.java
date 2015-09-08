package kr.co.d2net.dto;

import java.sql.Timestamp;
import java.util.Date;

public class OutSystemInfoTbl {
	
	private String sysInfoId;  //타시스템ID
	private String sysNm;      //시스템명
	private Date bgnTime;    //시작시간
	private Date endTime;    //종료시간
	private String regrid;     //등록자ID
	private Timestamp regDt;      //등록일시
	private String modrid;     //수정자ID
	private Timestamp modDt;      //수정일시
	private String ip;         //아이피
	
	private String endPoint;   //웹서버엔트포인트
	private String wsdlUrl;    //
	private String rem;
	private String useYn;
	private String port;
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getRem() {
		return rem;
	}
	public void setRem(String rem) {
		this.rem = rem;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getSysInfoId() {
		return sysInfoId;
	}
	public void setSysInfoId(String sysInfoId) {
		this.sysInfoId = sysInfoId;
	}
	public String getSysNm() {
		return sysNm;
	}
	public void setSysNm(String sysNm) {
		this.sysNm = sysNm;
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public String getWsdlUrl() {
		return wsdlUrl;
	}
	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}
	
	
	
}
