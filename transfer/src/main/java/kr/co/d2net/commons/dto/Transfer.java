package kr.co.d2net.commons.dto;

import java.sql.Timestamp;

public class Transfer {
	
	private Long tfId;
	private String status;
	private Integer progress;
	private Integer recount;
	private Timestamp regDtm;
	private String regUsrid;
	private Timestamp modDtm;
	private String modUsrid;
	private String useYn;
	private Integer priors;
	private String camCd;
	private String ctiId;
	private String tfGb;
	private String ctNm;
	private String flPath;
	private Long flSize;
	private String eqId;
	private String tfCd;
	private String flNm;
	
	private String pgmCd;
	private String pgmId;
	private String pgmNm;
	private String segmentId;
	private String segmentNm;
	
	private String ftpIp;
	private String ftpPort;
	private String ftpUser;
	private String ftpPass;
	private String transMode;
	private String localPath;
	private String remotePath;

	public String getFtpIp() {
		return ftpIp;
	}
	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}
	public String getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getFtpUser() {
		return ftpUser;
	}
	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	public String getFtpPass() {
		return ftpPass;
	}
	public void setFtpPass(String ftpPass) {
		this.ftpPass = ftpPass;
	}
	public String getTransMode() {
		return transMode;
	}
	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}
	public String getCtiId() {
		return ctiId;
	}
	public void setCtiId(String ctiId) {
		this.ctiId = ctiId;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	public String getRemotePath() {
		return remotePath;
	}
	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}
	public String getFlNm() {
		return flNm;
	}
	public void setFlNm(String flNm) {
		this.flNm = flNm;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getPgmCd() {
		return pgmCd;
	}
	public void setPgmCd(String pgmCd) {
		this.pgmCd = pgmCd;
	}
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	public String getSegmentId() {
		return segmentId;
	}
	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}
	public String getSegmentNm() {
		return segmentNm;
	}
	public void setSegmentNm(String segmentNm) {
		this.segmentNm = segmentNm;
	}
	public String getTfCd() {
		return tfCd;
	}
	public void setTfCd(String tfCd) {
		this.tfCd = tfCd;
	}
	public String getEqId() {
		return eqId;
	}
	public void setEqId(String eqId) {
		this.eqId = eqId;
	}
	public String getTfGb() {
		return tfGb;
	}
	public void setTfGb(String tfGb) {
		this.tfGb = tfGb;
	}
	public Long getFlSize() {
		return flSize;
	}
	public void setFlSize(Long flSize) {
		this.flSize = flSize;
	}
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public Long getTfId() {
		return tfId;
	}
	public void setTfId(Long tfId) {
		this.tfId = tfId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	public Integer getRecount() {
		return recount;
	}
	public void setRecount(Integer recount) {
		this.recount = recount;
	}
	public Timestamp getRegDtm() {
		return regDtm;
	}
	public void setRegDtm(Timestamp regDtm) {
		this.regDtm = regDtm;
	}
	public String getRegUsrid() {
		return regUsrid;
	}
	public void setRegUsrid(String regUsrid) {
		this.regUsrid = regUsrid;
	}
	public Timestamp getModDtm() {
		return modDtm;
	}
	public void setModDtm(Timestamp modDtm) {
		this.modDtm = modDtm;
	}
	public String getModUsrid() {
		return modUsrid;
	}
	public void setModUsrid(String modUsrid) {
		this.modUsrid = modUsrid;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public Integer getPriors() {
		return priors;
	}
	public void setPriors(Integer priors) {
		this.priors = priors;
	}
	public String getCamCd() {
		return camCd;
	}
	public void setCamCd(String camCd) {
		this.camCd = camCd;
	}
	
}
