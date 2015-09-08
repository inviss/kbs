package kr.co.d2net.dto.xml.archive;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("KBS_Exchange_Metadata")
public class KBSExchandeMetadata {
	
	@XStreamAlias("EXCHANGE_METADATA_INFO")
	private ExchangeMetadataInfo exchangeMetadataInfo;
	@XStreamAlias("MEDIA_INFO")
	private MediaInfo mediaInfo;
	@XStreamAlias("PROGRAM_INFO")
	private ProgramInfo programInfo;
	@XStreamAlias("EPISODE_INFO")
	private EpisodeInfo episodeInfo;
	@XStreamAlias("RAW_MATERIAL_INFO")
	private RawMeterialInfo rawMeterialInfo;
	@XStreamAlias("FOREIGN_NEWS_INFO")
	private ForeignNewsInfo foreignNewsInfo;
	
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
	public RawMeterialInfo getRawMeterialInfo() {
		return rawMeterialInfo;
	}
	public void setRawMeterialInfo(RawMeterialInfo rawMeterialInfo) {
		this.rawMeterialInfo = rawMeterialInfo;
	}
	public ForeignNewsInfo getForeignNewsInfo() {
		return foreignNewsInfo;
	}
	public void setForeignNewsInfo(ForeignNewsInfo foreignNewsInfo) {
		this.foreignNewsInfo = foreignNewsInfo;
	}
	
}
