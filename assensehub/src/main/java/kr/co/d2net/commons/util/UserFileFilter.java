package kr.co.d2net.commons.util;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class UserFileFilter implements FileFilter {

	private final String[] useFileExtensions = new String[] {"xml", "XML"};
	
	private List<String> exts = null;
	
	public UserFileFilter(List<String> exts) {
		this.exts = exts;
	}

	public boolean accept(File file) {
		if(exts != null && !exts.isEmpty()) {
			for(String extension : exts) {
				if (file.getName().toLowerCase().endsWith(extension)) {
					return true;
				}
			}
		} else {
			for (String extension : useFileExtensions) {
				if (file.getName().toLowerCase().endsWith(extension)) {
					return true;
				}
			}
		}
		return false;
	}

}
