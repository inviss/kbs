package kr.co.d2net.commons.dto;

import javax.swing.text.html.Option;

import kr.co.d2net.xml.IntegerConverter;
import kr.co.d2net.xml.LongConverter;
import kr.co.d2net.xml.TextConverter;
import kr.co.d2net.xml.TextUTF8Converter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("job")
public class Job {
	
	@XStreamAlias("job_id")
	@XStreamConverter(LongConverter.class)
	private Long jobId;
	
	@XStreamAlias("job_kind")
	@XStreamConverter(TextConverter.class)
	private String jobKind;
	
	@XStreamAlias("pro_flid")
	@XStreamConverter(IntegerConverter.class)
	private Integer proFlid;
	
	@XStreamAlias("serv_bit")
	@XStreamConverter(TextConverter.class)
	private String servBit;
	
	@XStreamAlias("ext")
	@XStreamConverter(TextConverter.class)
	private String ext;
	
	@XStreamAlias("vdo_codec")
	@XStreamConverter(TextConverter.class)
	private String vdoCodec;
	
	@XStreamAlias("vdo_bit_rate")
	@XStreamConverter(TextConverter.class)
	private String vdoBitRate;
	
	@XStreamAlias("vdo_hori")
	@XStreamConverter(IntegerConverter.class)
	private Integer vdoHori;
	
	@XStreamAlias("vdo_vert")
	@XStreamConverter(IntegerConverter.class)
	private Integer vdoVeri;
	
	@XStreamAlias("vdo_f_s")
	@XStreamConverter(TextConverter.class)
	private String vdoFS;
	
	@XStreamAlias("vdo_sync")
	@XStreamConverter(TextConverter.class)
	private String vdoSync;
	
	@XStreamAlias("aud_codec")
	@XStreamConverter(TextConverter.class)
	private String audCodec;
	
	@XStreamAlias("aud_bit_rate")
	@XStreamConverter(TextConverter.class)
	private String audBitRate;
	
	@XStreamAlias("aud_chan")
	@XStreamConverter(TextConverter.class)
	private String audChan;
	
	@XStreamAlias("aud_s_rate")
	@XStreamConverter(TextConverter.class)
	private String audSRate;
	
	@XStreamAlias("default_opt")
	@XStreamConverter(TextConverter.class)
	private String defaultOpt;
	
	@XStreamAlias("source_path")
	@XStreamConverter(TextConverter.class)
	private String sourcePath;
	
	@XStreamAlias("source_file")
	@XStreamConverter(TextConverter.class)
	private String sourceFile;
	
	@XStreamAlias("target_path")
	@XStreamConverter(TextConverter.class)
	private String targetPath;
	
	@XStreamAlias("target_file")
	@XStreamConverter(TextConverter.class)
	private String targetFile;
	
	@XStreamAlias("qc_yn")
	@XStreamConverter(TextConverter.class)
	private String qcYn;
	
	@XStreamAlias("gcode_use_yn")
	@XStreamConverter(TextConverter.class)
	private String gcodeUseYn;
	
	/* Transfer Job */
	@XStreamAlias("pgm_grp_cd")
	@XStreamConverter(TextConverter.class)
	private String pgmGrpCd;
	@XStreamAlias("pgm_cd")
	@XStreamConverter(TextConverter.class)
	private String pgmCd;
	@XStreamAlias("pgm_id")
	@XStreamConverter(TextConverter.class)
	private String pgmId;
	@XStreamAlias("pgm_nm")
	@XStreamConverter(TextUTF8Converter.class)
	private String pgmNm;
	@XStreamAlias("ct_nm")
	@XStreamConverter(TextUTF8Converter.class)
	private String ctNm;
	@XStreamAlias("ftp_ip")
	@XStreamConverter(TextConverter.class)
	private String ftpIp;
	@XStreamAlias("ftp_port")
	@XStreamConverter(IntegerConverter.class)
	private Integer ftpPort;
	@XStreamAlias("ftp_user")
	@XStreamConverter(TextConverter.class)
	private String ftpUser;
	@XStreamAlias("ftp_pass")
	@XStreamConverter(TextUTF8Converter.class)
	private String ftpPass;
	@XStreamAlias("remote_dir")
	@XStreamConverter(TextConverter.class)
	private String remoteDir;
	@XStreamAlias("method")
	@XStreamConverter(TextConverter.class)
	private String method;
	@XStreamAlias("fl_sz")
	@XStreamConverter(LongConverter.class)
	private Long flSz;
	@XStreamAlias("eq_id")
	@XStreamConverter(TextConverter.class)
	private String eqId;
	@XStreamAlias("company")
	@XStreamConverter(TextConverter.class)
	private String company;
	@XStreamAlias("wrk_file_nm")
	@XStreamConverter(TextConverter.class)
	private String wrkFileNm;
	@XStreamAlias("pro_flnm")
	@XStreamConverter(TextConverter.class)
	private String proFlnm;
	@XStreamAlias("pro_eng_nm")
	@XStreamConverter(TextConverter.class)
	private String proEngNm;
	@XStreamAlias("pro_eng_Yn")
	@XStreamConverter(TextConverter.class)
	private String proEngYn;
	@XStreamAlias("vod_smil")
	@XStreamConverter(TextConverter.class)
	private String vodSmil;
	
	
	public String getGcodeUseYn() {
		return gcodeUseYn;
	}
	public void setGcodeUseYn(String gcodeUseYn) {
		this.gcodeUseYn = gcodeUseYn;
	}
	public String getVodSmil() {
		return vodSmil;
	}
	public void setVodSmil(String vodSmil) {
		this.vodSmil = vodSmil;
	}
	public String getProEngYn() {
		return proEngYn;
	}
	public void setProEngYn(String proEngYn) {
		this.proEngYn = proEngYn;
	}
	public String getProEngNm() {
		return proEngNm;
	}
	public void setProEngNm(String proEngNm) {
		this.proEngNm = proEngNm;
	}
	public String getProFlnm() {
		return proFlnm;
	}
	public void setProFlnm(String proFlnm) {
		this.proFlnm = proFlnm;
	}
	
	public String getWrkFileNm() {
		return wrkFileNm;
	}
	public void setWrkFileNm(String wrkFileNm) {
		this.wrkFileNm = wrkFileNm;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEqId() {
		return eqId;
	}
	public void setEqId(String eqId) {
		this.eqId = eqId;
	}
	public Long getFlSz() {
		return flSz;
	}
	public void setFlSz(Long flSz) {
		this.flSz = flSz;
	}
	public Integer getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(Integer ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getPgmGrpCd() {
		return pgmGrpCd;
	}
	public void setPgmGrpCd(String pgmGrpCd) {
		this.pgmGrpCd = pgmGrpCd;
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
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public String getFtpIp() {
		return ftpIp;
	}
	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
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
	public String getRemoteDir() {
		return remoteDir;
	}
	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getTargetPath() {
		return targetPath;
	}
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getJobKind() {
		return jobKind;
	}
	public void setJobKind(String jobKind) {
		this.jobKind = jobKind;
	}
	public Integer getProFlid() {
		return proFlid;
	}
	public void setProFlid(Integer proFlid) {
		this.proFlid = proFlid;
	}
	public String getServBit() {
		return servBit;
	}
	public void setServBit(String servBit) {
		this.servBit = servBit;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getVdoCodec() {
		return vdoCodec;
	}
	public void setVdoCodec(String vdoCodec) {
		this.vdoCodec = vdoCodec;
	}
	public String getVdoBitRate() {
		return vdoBitRate;
	}
	public void setVdoBitRate(String vdoBitRate) {
		this.vdoBitRate = vdoBitRate;
	}
	public Integer getVdoHori() {
		return vdoHori;
	}
	public void setVdoHori(Integer vdoHori) {
		this.vdoHori = vdoHori;
	}
	public Integer getVdoVeri() {
		return vdoVeri;
	}
	public void setVdoVeri(Integer vdoVeri) {
		this.vdoVeri = vdoVeri;
	}
	public String getVdoFS() {
		return vdoFS;
	}
	public void setVdoFS(String vdoFS) {
		this.vdoFS = vdoFS;
	}
	public String getVdoSync() {
		return vdoSync;
	}
	public void setVdoSync(String vdoSync) {
		this.vdoSync = vdoSync;
	}
	public String getAudCodec() {
		return audCodec;
	}
	public void setAudCodec(String audCodec) {
		this.audCodec = audCodec;
	}
	public String getAudBitRate() {
		return audBitRate;
	}
	public void setAudBitRate(String audBitRate) {
		this.audBitRate = audBitRate;
	}
	public String getAudChan() {
		return audChan;
	}
	public void setAudChan(String audChan) {
		this.audChan = audChan;
	}
	public String getAudSRate() {
		return audSRate;
	}
	public void setAudSRate(String audSRate) {
		this.audSRate = audSRate;
	}
	public String getDefaultOpt() {
		return defaultOpt;
	}
	public void setDefaultOpt(String defaultOpt) {
		this.defaultOpt = defaultOpt;
	}
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public String getSourceFile() {
		return sourceFile;
	}
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	public String getTargetFile() {
		return targetFile;
	}
	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}
	public String getQcYn() {
		return qcYn;
	}
	public void setQcYn(String qcYn) {
		this.qcYn = qcYn;
	}
	
	@XStreamAlias("options")
	private Option option;

	public Option getOption() {
		return option;
	}
	public void setOption(Option option) {
		this.option = option;
	}
	
	@XStreamAlias("ftp")
	private Ftp ftp;

	public Ftp getFtp() {
		return ftp;
	}
	public void setFtp(Ftp ftp) {
		this.ftp = ftp;
	}
	
	
}
