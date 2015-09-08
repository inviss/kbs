package kr.co.d2net.dto.xml.meta;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("property")
public class Properties {
	
	@XmlAttribute(name="value")
	@XStreamAsAttribute
	@XStreamAlias("value")
	@XStreamConverter(TextConverter.class)
	private String value;
	
	@XmlAttribute(name="type")
	@XStreamAsAttribute
	@XStreamAlias("type")
	@XStreamConverter(TextConverter.class)
	private String type;
	
	@XmlAttribute(name="pid")
	@XStreamAsAttribute
	@XStreamAlias("pid")
	@XStreamConverter(TextConverter.class)
	private String pid;
	
	@XmlAttribute(name="name")
	@XStreamAsAttribute
	@XStreamAlias("name")
	@XStreamConverter(TextConverter.class)
	private String name;
	
	@XmlAttribute(name="default")
	@XStreamAsAttribute
	@XStreamAlias("default")
	@XStreamConverter(TextConverter.class)
	private String base;
	
	@XmlValue
	private String eleValue;

	public String getEleValue() {
		return eleValue;
	}
	public void setEleValue(String eleValue) {
		this.eleValue = eleValue;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	
	@XmlElement(name="node")
	@XStreamImplicit(itemFieldName="node")
	List<Node> nodeList = new ArrayList<Node>();

	public List<Node> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}
	
}
