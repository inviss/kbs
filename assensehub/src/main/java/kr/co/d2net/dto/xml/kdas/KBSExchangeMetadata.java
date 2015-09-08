package kr.co.d2net.dto.xml.kdas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="KBS_Exchange_Metadata")
@XmlAccessorType(XmlAccessType.FIELD)
public class KBSExchangeMetadata {

	@XmlElement(name="EXCHANGE_FILE_START_TIMECODE")
	private ExchangeMetadataInfo exchangeMetadataInfo;
	@XmlElement(name="MEDIA_INFO")
	private MediaInfo mediaInfo;
	@XmlElement(name="PROGRAM_INFO")
	private ProgramInfo programInfo;
	@XmlElement(name="EPISODE_INFO")
	private EpisodeInfo episodeInfo;
	
	public ExchangeMetadataInfo getExchangeMetadataInfo() {
		return exchangeMetadataInfo;
	}
	public void setExchangeMetadataInfo(ExchangeMetadataInfo exchangeMetadataInfo) {
		this.exchangeMetadataInfo = exchangeMetadataInfo;
	}
	public MediaInfo getMediaInfo() {
		return mediaInfo;
	}
	public void setMediaInfo(MediaInfo mediaInfo) {
		this.mediaInfo = mediaInfo;
	}
	public ProgramInfo getProgramInfo() {
		return programInfo;
	}
	public void setProgramInfo(ProgramInfo programInfo) {
		this.programInfo = programInfo;
	}
	public EpisodeInfo getEpisodeInfo() {
		return episodeInfo;
	}
	public void setEpisodeInfo(EpisodeInfo episodeInfo) {
		this.episodeInfo = episodeInfo;
	}
	
}
