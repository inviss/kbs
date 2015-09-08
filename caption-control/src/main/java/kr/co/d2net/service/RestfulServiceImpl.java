package kr.co.d2net.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.co.d2net.dto.Method;
import kr.co.d2net.utils.JSON;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("restfulService")
public class RestfulServiceImpl implements RestfulService {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private Method method;

	@Override
	public void method(Method method) {
		this.method = method;
	}
	
	public <T> T  post(String url, Map<String, Object> params, String encoding, T t) throws Exception {
		HttpClient client = new DefaultHttpClient();

		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 300000);
		HttpConnectionParams.setSoTimeout(httpParams, 300000);

		try{
			HttpPost post = new HttpPost(url);
			if(logger.isDebugEnabled()) {
				logger.debug("POST : " + post.getURI());
			}
			
			logger.debug("method : " + method);
			
			if(method != null) {
				if(Method.JSON == method) 
					post.setHeader("Content-Type", "application/json; charset="+encoding);
				else if(Method.XML == method) 
					post.setHeader("Content-Type", "application/xml; charset="+encoding);
				else if(Method.MAP == method) 
					post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset="+encoding);
				else
					post.setHeader("Content-Type", "text/html; charset="+encoding);
			} else {
				post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset="+encoding);
			}
			
			if(params != null && !params.isEmpty()) {
				List<NameValuePair> paramList = convertParam(params);
				post.setEntity(new UrlEncodedFormEntity(paramList, encoding));
			} else {
				if(Method.JSON == method) {
					StringEntity entity = new StringEntity(JSON.toString(t), encoding);
					entity.setContentType("application/json; charset="+encoding);
					post.setEntity(entity);
					
					if(logger.isDebugEnabled()) {
						logger.debug("status msg: "+JSON.toString(t));
					}
				} else {
					XmlConvertorService<T> xmlConvertorService = new XmlConvertorServiceImpl<T>();
					StringEntity entity = new StringEntity(xmlConvertorService.createMarshaller(t), encoding);
					entity.setContentType("application/xml; charset="+encoding);
					post.setEntity(entity);
					
					if(logger.isDebugEnabled()) {
						logger.debug("status msg: "+xmlConvertorService.createMarshaller(t));
					}
				}
			}
			
			ResponseHandler<String> rh = new BasicResponseHandler();
			String ret = client.execute(post, rh);
			if(logger.isDebugEnabled()) {
				logger.debug("return msg: "+ret);
			}
			
			return null;
		}catch(Exception e){
			throw new Exception("post error");
		}finally{
			client.getConnectionManager().shutdown();
		}
	}

	public <T> T  post(String url, Map<String, Object> params, T t) throws Exception {
		return post(url, params, "EUC-KR", t); // UTF-8
	}

	
	/**
	 * GET 요청
	 * POST 와 동일
	 */
	public <T> T  get(String url, Map<String, Object> params, String encoding, Class<T> clazz){
		HttpClient client = new DefaultHttpClient();

		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 300000);
		HttpConnectionParams.setSoTimeout(httpParams, 300000);

		try{
			HttpGet get = null;
			if(params != null && !params.isEmpty()) {
				List<NameValuePair> paramList = convertParam(params);
				get = new HttpGet(url+"?"+URLEncodedUtils.format(paramList, encoding));
			} else {
				get = new HttpGet(url);
			}
			if(logger.isDebugEnabled()) {
				logger.debug("GET : " + get.getURI());
			}
			
			if(method == null) {
				if(Method.JSON == method) 
					get.setHeader("Content-Type", "application/json; charset="+encoding);
				else if(Method.XML == method) 
					get.setHeader("Content-Type", "application/xml; charset="+encoding);
				else if(Method.MAP == method) 
					get.setHeader("Content-Type", "application/x-www-form-urlencoded; charset="+encoding);
				else
					get.setHeader("Content-Type", "text/html; charset="+encoding);
			} else {
				get.setHeader("Content-Type", "application/x-www-form-urlencoded; charset="+encoding);
			}

			ResponseHandler<String> rh = new BasicResponseHandler();
			String ret = client.execute(get, rh);
			
			if(StringUtils.isNotBlank(ret)) {
				if(logger.isDebugEnabled()) {
					logger.debug("return msg: "+ret);
				}
				return JSON.toObject(ret, clazz);
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			client.getConnectionManager().shutdown();
		}

		return null;
	}

	public <T> T get(String url, Map<String, Object> params, Class<T> clazz){
		return get(url, params, "EUC-KR", clazz); // UTF-8
	}



	private List<NameValuePair> convertParam(Map<String, Object> params){
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		Iterator<String> keys = params.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			paramList.add(new BasicNameValuePair(key, params.get(key).toString()));
		}

		return paramList;
	}

}
