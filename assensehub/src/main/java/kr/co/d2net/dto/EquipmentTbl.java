package kr.co.d2net.dto;

import java.sql.Timestamp;

public class EquipmentTbl {

	private String deviceNm; // 장비명
	private String deviceclfCd; // 장비구분코드
	private String deviceIp; // 장비사용IP
	private String devicePort; // 장비사용PORT
	private String useYn; // 사용여부
	private String deviceid; // 장비ID
	private Timestamp regDt; // 등록일시
	private String regrid; // 등록자ID
	private Timestamp modDt; // 수정일시
	private String modrid; // 수정자ID
	private Integer eqSeq; // 장비번호
	private String eqPsCd; // 장비종류코드
	private String eqUseUrl; // 장비사용URL
	private String eqUseId; // 장비사용ID
	private String eqUsePasswd; // 장비사용비밀번호
	private Long ctiId; // 콘텐츠인스턴트ID
	private String workStatcd; // 작업상태코드
	private String outSystemId; // 외부시스템ID
	private String reqUsrid; // 요청자ID
	private String prgrs; // 진행률
	private Long downVol; // 총사이즈
	private Long totalSize; //
	private Integer logRcdPeriod; // 로그기록주기
	private String flPath; // 파일경로

	private String ctNm; // 콘텐츠명
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getCtNm() {
		return ctNm;
	}

	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}

	public String getDeviceNm() {
		return deviceNm;
	}

	public void setDeviceNm(String deviceNm) {
		this.deviceNm = deviceNm;
	}

	public String getDeviceclfCd() {
		return deviceclfCd;
	}

	public void setDeviceclfCd(String deviceclfCd) {
		this.deviceclfCd = deviceclfCd;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getDevicePort() {
		return devicePort;
	}

	public void setDevicePort(String devicePort) {
		this.devicePort = devicePort;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
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

	public Integer getEqSeq() {
		return eqSeq;
	}

	public void setEqSeq(Integer eqSeq) {
		this.eqSeq = eqSeq;
	}

	public String getEqPsCd() {
		return eqPsCd;
	}

	public void setEqPsCd(String eqPsCd) {
		this.eqPsCd = eqPsCd;
	}

	public String getEqUseUrl() {
		return eqUseUrl;
	}

	public void setEqUseUrl(String eqUseUrl) {
		this.eqUseUrl = eqUseUrl;
	}

	public String getEqUseId() {
		return eqUseId;
	}

	public void setEqUseId(String eqUseId) {
		this.eqUseId = eqUseId;
	}

	public String getEqUsePasswd() {
		return eqUsePasswd;
	}

	public void setEqUsePasswd(String eqUsePasswd) {
		this.eqUsePasswd = eqUsePasswd;
	}

	public Long getCtiId() {
		return ctiId;
	}

	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}

	public String getWorkStatcd() {
		return workStatcd;
	}

	public void setWorkStatcd(String workStatcd) {
		this.workStatcd = workStatcd;
	}

	public String getOutSystemId() {
		return outSystemId;
	}

	public void setOutSystemId(String outSystemId) {
		this.outSystemId = outSystemId;
	}

	public String getReqUsrid() {
		return reqUsrid;
	}

	public void setReqUsrid(String reqUsrid) {
		this.reqUsrid = reqUsrid;
	}

	public String getPrgrs() {
		return prgrs;
	}

	public void setPrgrs(String prgrs) {
		this.prgrs = prgrs;
	}

	public Long getDownVol() {
		return downVol;
	}

	public void setDownVol(Long downVol) {
		this.downVol = downVol;
	}

	public Long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	public Integer getLogRcdPeriod() {
		return logRcdPeriod;
	}

	public void setLogRcdPeriod(Integer logRcdPeriod) {
		this.logRcdPeriod = logRcdPeriod;
	}

	public String getFlPath() {
		return flPath;
	}

	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}

}
