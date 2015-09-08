package kr.co.d2net.commons.utils;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Locale;

import kr.co.d2net.commons.dto.Workflow;
import kr.co.d2net.commons.exceptions.ServiceException;
import kr.co.d2net.soap.Navigator;
import kr.co.d2net.soap.ServiceNavigatorService;
import kr.co.d2net.soap.ServiceNavigatorServiceLocator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class CmsRequestServiceImpl implements CmsRequestService {

	private Logger logger = Logger.getLogger(getClass());

	private Navigator navigator1;
	private Navigator navigator2;

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private XmlStream xmlStream;

	public void init() {
		ServiceNavigatorService navigatorService = new ServiceNavigatorServiceLocator();

		logger.debug("messageSource: "+messageSource);

		String ip = messageSource.getMessage("mam.ip", null, Locale.KOREA);
		String port = messageSource.getMessage("mam.port", null, Locale.KOREA);

		try {
			//if(messageSource)
			if(ip.indexOf(",") > -1) {
				String[] mam = ip.split(",");
				navigator1 = navigatorService.getServiceNavigatorPort(new URL("http://"+mam[0]+":"+port+"/services/ServiceNavigator"));
				navigator2 = navigatorService.getServiceNavigatorPort(new URL("http://"+mam[1]+":"+port+"/services/ServiceNavigator"));
			} else {
				navigator1 = navigatorService.getServiceNavigatorPort(new URL("http://"+ip+":"+port+"/services/ServiceNavigator"));
			}
		} catch (Exception e) {
			logger.error("connection error", e);
		}
		
	}

	@Override
	public String saveStatus(String xml) throws ServiceException {
		String retXml = "";
		try {
			logger.debug("input xml: "+xml);
			try {
				retXml = navigator1.saveStatus(xml);
			} catch (RemoteException e) {
				if(navigator2 != null)
					retXml = navigator2.saveStatus(xml);
				else throw e;
			}
		} catch (RemoteException e) {
			logger.error("saveStatus", e);
		}

		return retXml;
	}

	@Override
	public String saveContents(String xml) throws ServiceException {
		return null;
	}

	@Override
	public String findTransfers(String xml) throws ServiceException {
		String retXml = "";
		try {
			logger.debug("input xml: "+xml);
			try {
				retXml = navigator1.findTransfers(xml);
			} catch (RemoteException e) {
				if(navigator2 != null)
					retXml = navigator2.findTransfers(xml);
				else throw e;
			}
		} catch (RemoteException e) {
			logger.error("findTransfers", e);
		}
		return retXml;
	}

	@Override
	public String saveStatus(Workflow workflow) throws ServiceException {
		String xml = "";
		try {
			try {
				xml = navigator1.saveStatus(xmlStream.toXML(workflow));
			} catch (RemoteException e) {
				if(navigator2 != null)
					xml = navigator2.saveStatus(xmlStream.toXML(workflow));
				else throw e;
			}
		} catch (RemoteException e) {
			logger.error("saveStatus", e);
		}
		return xml;
	}

}
