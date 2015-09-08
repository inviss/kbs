package kr.co.d2net.dto.xml.archive;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("EXCHANGE_METADATA_INFO")
public class ExchangeMetadataInfo {
	@XStreamAlias("ARCHIVE_ID")
	private String archiveId;
	@XStreamAlias("CLIP_SOURCE_SYSTEM")
	private String clipSourceSystem;
	@XStreamAlias("REQUEST_USER_ID")
	private String requestUserId;
	@XStreamAlias("PD_ID")
	private String pdId;
	@XStreamAlias("REGIST_PROGRAM_CODE")
	private String registProgramCode;
	@XStreamAlias("EXCHANGE_TYPE")
	private String exchangeType;
	@XStreamAlias("EXCHANGE_FILE_KIND")
	private String exchangeFileKind;
	@XStreamAlias("EXCHANGE_FILE_START_TIMECODE")
	private String exchangeFileStartTimecode;
	@XStreamAlias("EXCHANGE_FILE_END_TIMECODE")
	private String exchangeFileEndTimecode;
	@XStreamAlias("EXCHANGE_FILE_NAME")
	private String exchangeFileName;
	@XStreamAlias("EXCHANGE_FILE_SIZE")
	private String exchangeFileSize;

	public String getArchiveId() {
		return archiveId;
	}
	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}
	public String getClipSourceSystem() {
		return clipSourceSystem;
	}
	public void setClipSourceSystem(String clipSourceSystem) {
		this.clipSourceSystem = clipSourceSystem;
	}
	public String getRequestUserId() {
		return requestUserId;
	}
	public void setRequestUserId(String requestUserId) {
		this.requestUserId = requestUserId;
	}
	public String getPdId() {
		return pdId;
	}
	public void setPdId(String pdId) {
		this.pdId = pdId;
	}
	public String getRegistProgramCode() {
		return registProgramCode;
	}
	public void setRegistProgramCode(String registProgramCode) {
		this.registProgramCode = registProgramCode;
	}
	public String getExchangeType() {
		return exchangeType;
	}
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}
	public String getExchangeFileKind() {
		return exchangeFileKind;
	}
	public void setExchangeFileKind(String exchangeFileKind) {
		this.exchangeFileKind = exchangeFileKind;
	}
	public String getExchangeFileStartTimecode() {
		return exchangeFileStartTimecode;
	}
	public void setExchangeFileStartTimecode(String exchangeFileStartTimecode) {
		this.exchangeFileStartTimecode = exchangeFileStartTimecode;
	}
	public String getExchangeFileEndTimecode() {
		return exchangeFileEndTimecode;
	}
	public void setExchangeFileEndTimecode(String exchangeFileEndTimecode) {
		this.exchangeFileEndTimecode = exchangeFileEndTimecode;
	}
	public String getExchangeFileName() {
		return exchangeFileName;
	}
	public void setExchangeFileName(String exchangeFileName) {
		this.exchangeFileName = exchangeFileName;
	}
	public String getExchangeFileSize() {
		return exchangeFileSize;
	}
	public void setExchangeFileSize(String exchangeFileSize) {
		this.exchangeFileSize = exchangeFileSize;
	}
	
}
