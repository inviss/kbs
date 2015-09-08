package kr.co.d2net.service;

import kr.co.d2net.commons.util.XmlConvertorService;
import kr.co.d2net.commons.util.XmlConvertorServiceImpl;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestServiceImpl<T> implements HttpRequestService<T> {

	final static Logger logger = LoggerFactory.getLogger(HttpRequestServiceImpl.class);

	private HttpClient httpClient = null;
	
	public static<V> HttpRequestService<V> getInstance() {
		return new HttpRequestServiceImpl<V>();
	}

	private HttpClient initContext() {
		DefaultHttpMethodRetryHandler retryHandler = new DefaultHttpMethodRetryHandler(0, false);
		HttpClientParams clientParams = new HttpClientParams();
		clientParams.setParameter(HttpMethodParams.RETRY_HANDLER, retryHandler);

		return new HttpClient(clientParams);
	}

	private HttpRequestServiceImpl() {
		httpClient = initContext();
	}

	@Override
	public String findData(String url, NameValuePair[] postParameters) throws Exception {
		if(logger.isDebugEnabled()) {
			logger.debug("url: "+url);
		}
		
		/*
		DefaultHttpMethodRetryHandler retryHandler = new DefaultHttpMethodRetryHandler(0, false);
		HttpClientParams clientParams = new HttpClientParams();
		clientParams.setParameter(HttpMethodParams.RETRY_HANDLER, retryHandler);
		
		HttpClient httpClient = new HttpClient(clientParams);
		*/
		
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=euc-kr");

		String msg = null;
		try {
			if(postParameters != null && postParameters.length > 0) {
				if(logger.isDebugEnabled()) {
					for(NameValuePair nameValuePair : postParameters) {
						logger.debug(nameValuePair.getName()+"="+nameValuePair.getValue());
					}
				}
				postMethod.setRequestBody(postParameters);
			}
			
			int result = httpClient.executeMethod(postMethod);

			if (result >= 200 && result <= 299) {
				msg = postMethod.getResponseBodyAsString();
				if(logger.isDebugEnabled()) {
					logger.debug("json msg: "+msg);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			postMethod.releaseConnection();
			postMethod = null;
		}

		return msg;
	}

	@Override
	public T findData(String url, NameValuePair[] postParameters, Class<?> clazz) throws Exception {
		
		/*
		DefaultHttpMethodRetryHandler retryHandler = new DefaultHttpMethodRetryHandler(0, false);
		HttpClientParams clientParams = new HttpClientParams();
		clientParams.setParameter(HttpMethodParams.RETRY_HANDLER, retryHandler);
		
		HttpClient httpClient = new HttpClient(clientParams);
		*/
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=euc-kr");

		try {
			if(postParameters != null && postParameters.length > 0) {
				if(logger.isDebugEnabled()) {
					for(NameValuePair nameValuePair : postParameters) {
						logger.debug(nameValuePair.getName()+"="+nameValuePair.getValue());
					}
				}
				postMethod.setRequestBody(postParameters);
			}
			
			int result = httpClient.executeMethod(postMethod);

			if (result >= 200 && result <= 299) {
				String msg = postMethod.getResponseBodyAsString();
				if(logger.isDebugEnabled()) {
					logger.debug("retVal: "+msg);
				}
				if(StringUtils.isNotBlank(msg)) {
					XmlConvertorService<T> convertorService = XmlConvertorServiceImpl.getInstance();
					return convertorService.unMarshaller(msg);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(postMethod != null) postMethod.releaseConnection();
		}

		return null;
	}

}
