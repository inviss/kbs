package kr.co.d2net.commons.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * <p>ViewResolver auto Mapping</p>
 * @author Kang Myeong Seong
 * <pre>
 * URL Extension을 parsing해서 해당되는 Content Type뷰를
 * 리턴해서 찾아가는 방법입니다.
 * 예) http://localhost/foo.json이면 jsonView를 찾아가고
 * http://localhost/foo.do면  jstlView로 맵핑 되도록 합니다.
 * 해당 servlet mapping은 web.xml에 추가되어야 합니다.
 * </pre>
 */
public class URLViewNameResolver {
	private Map<String, AbstractView> resolverMap;

	private URLViewNameResolver() {
	}

	public void setResolverMap(Map<String, AbstractView> resolverMap) {
		this.resolverMap = resolverMap;
	}

	public final String getForward(final HttpServletRequest request) {
		String url = request.getServletPath();
		return getConvetViewName(request, url);
	}

	public final String getForward(final HttpServletRequest request,
			final String url) {
		return getConvetViewName(request, url);
	}

	private final String getConvetViewName(final HttpServletRequest request,
			final String forwardURL) {
		String extension = FilenameUtils.getExtension(forwardURL);
		AbstractView view = this.resolverMap.get(extension);
		if (view != null) {
			return view.getBeanName();
		}
		return convetURL(forwardURL);
	}

	private String convetURL(final String url) {
		if (url == null || StringUtils.contains(url, ":redirect")) {
			return url;
		}
		if (url.lastIndexOf('.') > -1) {
			return StringUtils.trimToEmpty(url.substring(0, url
					.lastIndexOf('.')));
		}
		return url;
	}

}
