package kr.co.d2net.dto;

import java.sql.Timestamp;

public class BusiPartnerTbl {

	private String proFlid;  // 프로파일 ID
	private String busiPartnerid; // 사업자ID
	private String regrid; // 등록자ID
	private Timestamp regDt; // 등록일시
	private String modrid; // 수정자ID
	private Timestamp modDt; // 수정일시
	private String password; // 비밀번호
	private String company; // 업체명
	private String servyn; // 서비스여부
	private String ftpServYn; // FTP서비스여부
	private String folderRule; // 폴더규칙
	private String ip; // 아이피
	private String port; // 포트
	private String transMethod; // 전송방식
	private String proFlnm;
	private String remoteDir; // 전송 타겟 디렉토리
	private String nextval;
	private String ftpid; //FTP 아이디
	private String srvUrl;
	private String contentML;
	private String vodSmil;
	private String alias;
	private String proEngYn; // 프로그램 영문 사용여부
	private String gcodeUseYn; // 그룹코드 사용 여부
	
	
	public String getGcodeUseYn() {
		return gcodeUseYn;
	}

	public void setGcodeUseYn(String gcodeUseYn) {
		this.gcodeUseYn = gcodeUseYn;
	}

	public String getProEngYn() {
		return proEngYn;
	}

	public void setProEngYn(String proEngYn) {
		this.proEngYn = proEngYn;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSrvUrl() {
		return srvUrl;
	}

	public void setSrvUrl(String srvUrl) {
		this.srvUrl = srvUrl;
	}

	public String getContentML() {
		return contentML;
	}

	public void setContentML(String contentML) {
		this.contentML = contentML;
	}

	public String getVodSmil() {
		return vodSmil;
	}

	public void setVodSmil(String vodSmil) {
		this.vodSmil = vodSmil;
	}

	public String getProFlid() {
		return proFlid;
	}

	public void setProFlid(String proFlid) {
		this.proFlid = proFlid;
	}

	public String getFtpid() {
		return ftpid;
	}

	public void setFtpid(String ftpid) {
		this.ftpid = ftpid;
	}

	public String getRemoteDir() {
		return remoteDir;
	}

	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}

	public String getNextval() {
		return nextval;
	}

	public void setNextval(String nextval) {
		this.nextval = nextval;
	}

	public String getProFlnm() {
		return proFlnm;
	}

	public void setProFlnm(String proFlnm) {
		this.proFlnm = proFlnm;
	}

	public String getBusiPartnerid() {
		return busiPartnerid;
	}

	public void setBusiPartnerid(String busiPartnerid) {
		this.busiPartnerid = busiPartnerid;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getServyn() {
		return servyn;
	}

	public void setServyn(String servyn) {
		this.servyn = servyn;
	}

	public String getFtpServYn() {
		return ftpServYn;
	}

	public void setFtpServYn(String ftpServYn) {
		this.ftpServYn = ftpServYn;
	}

	public String getFolderRule() {
		return folderRule;
	}

	public void setFolderRule(String folderRule) {
		this.folderRule = folderRule;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTransMethod() {
		return transMethod;
	}

	public void setTransMethod(String transMethod) {
		this.transMethod = transMethod;
	}

}
