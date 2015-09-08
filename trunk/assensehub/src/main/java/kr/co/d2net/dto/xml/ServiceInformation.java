package kr.co.d2net.dto.xml;

import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("ServiceInformation")
public class ServiceInformation {
	
	@XStreamAsAttribute
	@XStreamAlias("serviceId")
	@XStreamConverter(TextConverter.class)
	private String serviceId;
	
	@XStreamAlias("Name")
	@XStreamConverter(TextConverter.class)
	private String Name;
	
	@XStreamAlias("Owner")
	@XStreamConverter(TextConverter.class)
	private String Owner;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getOwner() {
		return Owner;
	}
	public void setOwner(String owner) {
		Owner = owner;
	}
	
}
