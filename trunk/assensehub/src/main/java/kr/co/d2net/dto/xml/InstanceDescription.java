package kr.co.d2net.dto.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("InstanceDescription")
public class InstanceDescription {
	
	@XStreamAlias("ServiceInformationTable")
	private ServiceInformationTable serviceInformationTable;

	public ServiceInformationTable getServiceInformationTable() {
		return serviceInformationTable;
	}

	public void setServiceInformationTable(
			ServiceInformationTable serviceInformationTable) {
		this.serviceInformationTable = serviceInformationTable;
	}
	
}
