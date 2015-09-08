package kr.co.d2net.dto.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ServiceInformationTable")
public class ServiceInformationTable {

	@XStreamAlias("serviceInformation")
	private ServiceInformation serviceInformation;

	public ServiceInformation getServiceInformation() {
		return serviceInformation;
	}
	public void setServiceInformation(ServiceInformation serviceInformation) {
		this.serviceInformation = serviceInformation;
	}
	
}
