package kr.co.d2net.dto.xml.meta;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("node")
public class Node {
	
	@XmlAttribute(name="tid")
	@XStreamAsAttribute
	@XStreamAlias("tid")
	@XStreamConverter(TextConverter.class)
	private String tid;
	
	@XmlAttribute(name="nid")
	@XStreamAsAttribute
	@XStreamAlias("nid")
	@XStreamConverter(TextConverter.class)
	private String nid;
	
	@XmlAttribute(name="iconclass")
	@XStreamAsAttribute
	@XStreamAlias("iconclass")
	@XStreamConverter(TextConverter.class)
	private String iconclass;

	
	public String getIconclass() {
		return iconclass;
	}

	public void setIconclass(String iconclass) {
		this.iconclass = iconclass;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}
	
	@XmlElement(name="property")
	@XStreamImplicit(itemFieldName="property")
	List<Properties> properties = new ArrayList<Properties>();
	public List<Properties> getProperties() {
		return properties;
	}
	public void setProperties(List<Properties> properties) {
		this.properties = properties;
	}
	public void addProperties(Properties properties) {
		this.properties.add(properties);
	}
	
}
