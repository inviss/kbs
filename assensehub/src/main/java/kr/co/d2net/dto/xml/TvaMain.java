package kr.co.d2net.dto.xml;

import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("KBSiContentML")
public class TvaMain {
	
	@XStreamAsAttribute
	@XStreamAlias("publisher")
	@XStreamConverter(TextConverter.class)
	private String publisher = "KBS";
	
	@XStreamAsAttribute
	@XStreamAlias("publicationTime")
	@XStreamConverter(TextConverter.class)
	private String publicationTime;
	
	@XStreamAsAttribute
	@XStreamAlias("rightsOwner")
	@XStreamConverter(TextConverter.class)
	private String rightsOwner = "KBS";
	
	@XStreamAsAttribute
	@XStreamAlias("version")
	@XStreamConverter(TextConverter.class)
	private String version = "1";
	
	@XStreamAlias("CopyrightNotice")
	@XStreamConverter(TextConverter.class)
	private String CopyrightNotice = "KBS";
	
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPublicationTime() {
		return publicationTime;
	}
	public void setPublicationTime(String publicationTime) {
		this.publicationTime = publicationTime;
	}
	public String getRightsOwner() {
		return rightsOwner;
	}
	public void setRightsOwner(String rightsOwner) {
		this.rightsOwner = rightsOwner;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCopyrightNotice() {
		return CopyrightNotice;
	}
	public void setCopyrightNotice(String copyrightNotice) {
		CopyrightNotice = copyrightNotice;
	}
	
	@XStreamAlias("ProgramDescription")
	private ProgramDescription programDescription;

	public ProgramDescription getProgramDescription() {
		return programDescription;
	}
	public void setProgramDescription(ProgramDescription programDescription) {
		this.programDescription = programDescription;
	}
	
	
}
