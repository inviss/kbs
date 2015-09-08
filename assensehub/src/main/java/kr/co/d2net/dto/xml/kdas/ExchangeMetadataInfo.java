package kr.co.d2net.dto.xml.kdas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="EXCHANGE_METADATA_INFO")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExchangeMetadataInfo {
	
	@XmlElement(name="EXCHANGE_FILE_START_TIMECODE")
	private String exchangeFileStartTimecode;
	@XmlElement(name="EXCHANGE_FILE_END_TIMECODE")
	private String exchangeFileEndTimecode;
	@XmlElement(name="EXCHANGE_FILE_SIZE")
	private Long exchangeFileSize;
	
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
	public Long getExchangeFileSize() {
		return exchangeFileSize;
	}
	public void setExchangeFileSize(Long exchangeFileSize) {
		this.exchangeFileSize = exchangeFileSize;
	}
	
}
