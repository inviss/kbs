package kr.co.d2net.commons.util;

import javax.xml.bind.JAXBException;

public interface XmlConvertorService<T> {
	public String createMarshaller(T t) throws JAXBException;
	public T unMarshaller(String xml) throws JAXBException;
}
