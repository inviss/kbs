package kr.co.d2net.dto.xml.publish;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name="KBSiContentML")
@XmlAccessorType(XmlAccessType.FIELD)
public class TvaMain {
	
	@XmlAttribute(name="publisher")
	private String publisher = "KBS";
	
	@XmlAttribute(name="publicationTime")
	private String publicationTime;
	
	@XmlAttribute(name="rightsOwner")
	private String rightsOwner = "KBS";
	
	@XmlAttribute(name="version")
	private String version = "1";
	
	@XmlAttribute(name="CopyrightNotice")
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

	@XStreamAlias("CopyrightDescription")
	private CopyrightDescription copyrightDescription;

	public CopyrightDescription getCopyrightDescription() {
		return copyrightDescription;
	}
	public void setCopyrightDescription(CopyrightDescription copyrightDescription) {
		this.copyrightDescription = copyrightDescription;
	}
	
	
}
