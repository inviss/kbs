package kr.co.d2net.commons.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import kr.co.d2net.dto.xml.kdas.KBSExchangeMetadata;
import kr.co.d2net.dto.xml.meta.Nodes;
import kr.co.d2net.dto.xml.publish.TvaMain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

public class XmlConvertorServiceImpl<T> implements XmlConvertorService<T> {

	final static Logger logger = LoggerFactory.getLogger(XmlConvertorServiceImpl.class);

	private static final JAXBContext jaxbContext = initContext();
	public static<V> XmlConvertorService<V> getInstance() {
		return new XmlConvertorServiceImpl<V>();
	}
	
	private static JAXBContext initContext() {
		try {
			Map<String, Object> oxm = new HashMap<String, Object>(1);
			oxm.put("kr.co.d2net.dto.xml.publish", new StreamSource(ClassLoader.class.getResourceAsStream("/config/oxm_publish.xml")));
			oxm.put("kr.co.d2net.dto.xml.meta", new StreamSource(ClassLoader.class.getResourceAsStream("/config/oxm_meta.xml")));

			Map<String, Object> props = new HashMap<String, Object>(1);
			props.put("eclipselink-oxm-xml", oxm);

			Class[] classes = {TvaMain.class, KBSExchangeMetadata.class, Nodes.class};
			
			return JAXBContext.newInstance(
					classes, props
					//classes
					);
		} catch (JAXBException e) {
			logger.error("initContext error", e);
			return null;
		}
	}

	private XmlConvertorServiceImpl() {
		if(jaxbContext == null) initContext();
	}

	public String createMarshaller(T t) throws JAXBException {
		StringWriter writer = new StringWriter();

		Marshaller m = jaxbContext.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		/*
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		CDataXMLStreamWriter cdataStreamWriter = null;
		try {
			XMLStreamWriter streamWriter = xof.createXMLStreamWriter(writer);
			cdataStreamWriter = new CDataXMLStreamWriter( streamWriter );
			
			m.marshal(t, cdataStreamWriter);
			
			cdataStreamWriter.flush();
			cdataStreamWriter.close();
		} catch (XMLStreamException e) {
			throw new JAXBException(e);
		}
		*/
		m.marshal(t, writer);
		
		return writer.toString();
	}

	@SuppressWarnings("unchecked")
	public T unMarshaller(String xml) throws JAXBException {
		Unmarshaller unmarshal = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(xml);
		InputSource source = new InputSource(reader);
		T t = (T)unmarshal.unmarshal(source);
		
		return t;
	}

}
