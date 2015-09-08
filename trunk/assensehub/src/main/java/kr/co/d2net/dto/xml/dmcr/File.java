package kr.co.d2net.dto.xml.dmcr;

import kr.co.d2net.commons.converter.xml.LongConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("file")
public class File {
	
	@XStreamAsAttribute
	@XStreamAlias("isfolder")
	@XStreamConverter(TextConverter.class)
	private String isfolder;
	@XStreamAsAttribute
	@XStreamAlias("source")
	@XStreamConverter(TextConverter.class)
	private String source;
	@XStreamAsAttribute
	@XStreamAlias("size")
	@XStreamConverter(LongConverter.class)
	private Long size;
	
	private String value;

	public String getIsfolder() {
		return isfolder;
	}
	public void setIsfolder(String isfolder) {
		this.isfolder = isfolder;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
