package kr.co.d2net.service;

import kr.co.d2net.dto.Method;
import java.util.Map;

public interface RestfulService {
	public void method(Method method);
	public <T> T  post(String url, Map<String, Object> params, T t) throws Exception;
	public <T> T  post(String url, Map<String, Object> params, String encoding, T t) throws Exception;
	public <T> T  get(String url, Map<String, Object> params, String encoding, Class<T> clazz);
	public <T> T get(String url, Map<String, Object> params, Class<T> clazz);
}
