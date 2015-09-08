package kr.co.d2net.dto.xml.internal;

import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.commons.converter.xml.TextUTF8Converter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("mnc")
public class Mnc {
	
	@XStreamAlias("source_gb")
	@XStreamConverter(TextConverter.class)
	private String sourceGb;
	
	@XStreamAlias("source_path")
	@XStreamConverter(TextUTF8Converter.class)
	private String sourcePath;
	
	@XStreamAlias("source_nm")
	@XStreamConverter(TextUTF8Converter.class)
	private String sourceNm;
	
	@XStreamAlias("source_ext")
	@XStreamConverter(TextConverter.class)
	private String sourceExt;
	
	@XStreamAlias("status")
	@XStreamConverter(TextConverter.class)
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSourceExt() {
		return sourceExt;
	}
	public void setSourceExt(String sourceExt) {
		this.sourceExt = sourceExt;
	}
	public String getSourceGb() {
		return sourceGb;
	}
	public void setSourceGb(String sourceGb) {
		this.sourceGb = sourceGb;
	}
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public String getSourceNm() {
		return sourceNm;
	}
	public void setSourceNm(String sourceNm) {
		this.sourceNm = sourceNm;
	}
	
}
