package kr.co.d2net.dto.xml.kdas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MEDIA_INFO")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaInfo {

	@XmlElement(name="FILE_FORMAT")
	private String fileFormat;	//MXF
	@XmlElement(name="VIDEO_CODEC")
	private String videoCodec; //Apple XDCAM HD422 1080i60 (50 Mb/s CBR)
	@XmlElement(name="VIDEO_RESOLUTION")
	private String videoResolution; //1920x1080
	@XmlElement(name="VIDEO_FPS")
	private String videoFps; //29.9
	@XmlElement(name="VIDEO_BIT_RATE")
	private String videoBitRate; //52.31
	@XmlElement(name="AUDIO_CODEC")
	private String audioCodec; //24-bit Integer (Little Endian)
	@XmlElement(name="AUDIO_SAMPLE_RATE")
	private String audioSampleRate; //48
	@XmlElement(name="AUDIO_CHANNEL_COUNT")
	private Integer audioChannelCount; //4
	
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	public String getVideoCodec() {
		return videoCodec;
	}
	public void setVideoCodec(String videoCodec) {
		this.videoCodec = videoCodec;
	}
	public String getVideoResolution() {
		return videoResolution;
	}
	public void setVideoResolution(String videoResolution) {
		this.videoResolution = videoResolution;
	}
	public String getVideoFps() {
		return videoFps;
	}
	public void setVideoFps(String videoFps) {
		this.videoFps = videoFps;
	}
	public String getVideoBitRate() {
		return videoBitRate;
	}
	public void setVideoBitRate(String videoBitRate) {
		this.videoBitRate = videoBitRate;
	}
	public String getAudioCodec() {
		return audioCodec;
	}
	public void setAudioCodec(String audioCodec) {
		this.audioCodec = audioCodec;
	}
	public String getAudioSampleRate() {
		return audioSampleRate;
	}
	public void setAudioSampleRate(String audioSampleRate) {
		this.audioSampleRate = audioSampleRate;
	}
	public Integer getAudioChannelCount() {
		return audioChannelCount;
	}
	public void setAudioChannelCount(Integer audioChannelCount) {
		this.audioChannelCount = audioChannelCount;
	}
	
}
