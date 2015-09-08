package kr.co.d2net.dto.xml.internal;

import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("report")
public class Report {
	
	@XStreamAlias("qc_success")
	@XStreamConverter(TextConverter.class)
	private String qcSuccess;
	
	@XStreamAlias("qc_rate")
	@XStreamConverter(TextConverter.class)
	private String qcRate;
	
	@XStreamAlias("qc_video_err")
	@XStreamConverter(TextConverter.class)
	private String qcVideoErr;
	
	@XStreamAlias("qc_audio_err")
	@XStreamConverter(TextConverter.class)
	private String qcAudioErr;
	
	@XStreamAlias("qc_path")
	@XStreamConverter(TextConverter.class)
	private String qcPath;
	

	public String getQcSuccess() {
		return qcSuccess;
	}

	public void setQcSuccess(String qcSuccess) {
		this.qcSuccess = qcSuccess;
	}

	public String getQcRate() {
		return qcRate;
	}

	public void setQcRate(String qcRate) {
		this.qcRate = qcRate;
	}

	public String getQcVideoErr() {
		return qcVideoErr;
	}

	public void setQcVideoErr(String qcVideoErr) {
		this.qcVideoErr = qcVideoErr;
	}

	public String getQcAudioErr() {
		return qcAudioErr;
	}

	public void setQcAudioErr(String qcAudioErr) {
		this.qcAudioErr = qcAudioErr;
	}

	public String getQcPath() {
		return qcPath;
	}

	public void setQcPath(String qcPath) {
		this.qcPath = qcPath;
	}
	
}
