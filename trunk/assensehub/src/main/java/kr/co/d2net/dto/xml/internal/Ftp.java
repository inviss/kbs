package kr.co.d2net.dto.xml.internal;

import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.commons.converter.xml.TextUTF8Converter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("ftp")
public class Ftp {
	
	
	@XStreamAlias("ftp_ip")
	@XStreamConverter(TextConverter.class)
	private String ftpIp;
	
	@XStreamAlias("ftp_port")
	@XStreamConverter(TextConverter.class)
	private String ftpPort;
	
	@XStreamAlias("ftp_user")
	@XStreamConverter(TextConverter.class)
	private String ftpUser;
	
	@XStreamAlias("ftp_pass")
	@XStreamConverter(TextUTF8Converter.class)
	private String ftpPass;
	
	@XStreamAlias("remote_dir")
	@XStreamConverter(TextConverter.class)
	private String remoteDir;
	
	@XStreamAlias("trans_mode")
	@XStreamConverter(TextConverter.class)
	private String transMode;

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

	public String getRemoteDir() {
		return remoteDir;
	}

	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}
	
}
