package kr.co.d2net.dto.xml.dmcr;

import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("generator")
public class Generator {

	@XStreamAlias("generator_name")
	@XStreamConverter(TextConverter.class)
	private String generatorName;
	
	@XStreamAlias("generator_version")
	@XStreamConverter(TextConverter.class)
	private String generatorVersion;

	public String getGeneratorName() {
		return generatorName;
	}
	public void setGeneratorName(String generatorName) {
		this.generatorName = generatorName;
	}
	public String getGeneratorVersion() {
		return generatorVersion;
	}
	public void setGeneratorVersion(String generatorVersion) {
		this.generatorVersion = generatorVersion;
	}
	
}
