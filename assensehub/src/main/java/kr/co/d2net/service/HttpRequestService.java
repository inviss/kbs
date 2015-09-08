package kr.co.d2net.service;

import org.apache.commons.httpclient.NameValuePair;


public interface HttpRequestService<T> {
	public String findData(String url, NameValuePair[] postParameters) throws Exception;
	public T findData(String url, NameValuePair[] postParameters, Class<?> clazz) throws Exception;
}
