package kr.co.d2net.commons.util;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.lang.StringUtils;

import kr.co.d2net.dto.Search;

/**
 * <pre>
 * 파일 목록을 String으로 반환할때 지정된 파일명만 반환한다.(DOAD 관련 지역코드 필터링)
 * </pre>
 * @author M.S. Kang
 *
 */
public class DoadFilenameFilter implements FilenameFilter {

	private Search search;

	public DoadFilenameFilter(Search search) {
		this.search = search;
	}
	public DoadFilenameFilter(String type, String keyword) {
		search = new Search();
		search.setType(type);
		search.setKeyword(keyword);
	}

	public boolean accept(File dir, String name) {
		
		if(StringUtils.isNotBlank(search.getLocalCode())) {
			if (name.toLowerCase().startsWith(search.getLocalCode())) {
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
