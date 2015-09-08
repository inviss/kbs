package kr.co.d2net.commons.dto;

import java.sql.Timestamp;

public class FtpConfig {
	
	private String ftpIp;
	private String ftpId;
	private String ftpUser;
	private String ftpPwd;
	private String remoteDir;
	private String localDir;
	private String eqId;
	private String eqGb;
	private Timestamp regDtm;
	private String ctId;
	private Long tfId;
	
	public String getFtpUser() {
		return ftpUser;
	}
	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	public Long getTfId() {
		return tfId;
	}
	public void setTfId(Long tfId) {
		this.tfId = tfId;
	}
	public String getCtId() {
		return ctId;
	}
	public void setCtId(String ctId) {
		this.ctId = ctId;
	}
	public String getFtpIp() {
		return ftpIp;
	}
	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}
	public String getFtpId() {
		return ftpId;
	}
	public void setFtpId(String ftpId) {
		this.ftpId = ftpId;
	}
	public String getFtpPwd() {
		return ftpPwd;
	}
	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}
	public String getRemoteDir() {
		return remoteDir;
	}
	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}
	public String getLocalDir() {
		return localDir;
	}
	public void setLocalDir(String localDir) {
		this.localDir = localDir;
	}
	public String getEqId() {
		return eqId;
	}
	public void setEqId(String eqId) {
		this.eqId = eqId;
	}
	public String getEqGb() {
		return eqGb;
	}
	public void setEqGb(String eqGb) {
		this.eqGb = eqGb;
	}
	public Timestamp getRegDtm() {
		return regDtm;
	}
	public void setRegDtm(Timestamp regDtm) {
		this.regDtm = regDtm;
	}
	
}
