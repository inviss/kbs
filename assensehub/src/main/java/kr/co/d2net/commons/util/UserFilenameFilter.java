package kr.co.d2net.commons.util;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.d2net.dto.Search;

/**
 * <pre>
 * 파일 목록을 String으로 반환할때 지정된 파일명만 반환한다.
 * </pre>
 * @author M.S. Kang
 *
 */
public class UserFilenameFilter implements FilenameFilter {
	
	final Logger logger = LoggerFactory.getLogger(getClass());

	private Search search;

	public UserFilenameFilter(Search search) {
		this.search = search;
	}
	public UserFilenameFilter(String type, String keyword) {
		search = new Search();
		search.setType(type);
		search.setKeyword(keyword);
	}

	public boolean accept(File dir, String name) {
		if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(search.getType())) {
			if (name.toLowerCase().indexOf(search.getType().toLowerCase()) > -1) {
				if(StringUtils.isNotBlank(search.getKeyword())) {
					if (name.toLowerCase().indexOf(search.getKeyword().toLowerCase()) > -1) {
						return true;
					} else return false;
				}
				return true;
			}
		}
		return false;
	}

}
