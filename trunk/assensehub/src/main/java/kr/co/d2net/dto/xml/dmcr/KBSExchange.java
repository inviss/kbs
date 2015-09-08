package kr.co.d2net.dto.xml.dmcr;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("kbsexchange")
public class KBSExchange {
	
	@XStreamAlias("generator")
	private Generator generator;

	public Generator getGenerator() {
		return generator;
	}
	public void setGenerator(Generator generator) {
		this.generator = generator;
	}
	
	@XStreamAlias("general_information")
	private GeneralInformation generalInformation;

	public GeneralInformation getGeneralInformation() {
		return generalInformation;
	}
	public void setGeneralInformation(GeneralInformation generalInformation) {
		this.generalInformation = generalInformation;
	}
	
	@XStreamAlias("metadata")
	private Metadata metadata;

	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	
	
	
}
