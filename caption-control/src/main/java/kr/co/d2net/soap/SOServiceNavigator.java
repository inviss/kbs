package kr.co.d2net.soap;

import java.rmi.RemoteException;

import javax.jws.WebService;

import kr.co.d2net.dto.Workflow;
import kr.co.d2net.service.XmlConvertorService;
import kr.co.d2net.service.XmlConvertorServiceImpl;

@WebService(endpointInterface = "kr.co.d2net.soap.Navigator")
public class SOServiceNavigator implements Navigator {
	
	XmlConvertorService<Workflow> xmlConvertorService = new XmlConvertorServiceImpl<Workflow>();
	
	public String soapTest(String xml) throws RemoteException {
		return Boolean.valueOf("true").toString();
	}

	@Override
	public String revise(String xml) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String upload(String xml) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
