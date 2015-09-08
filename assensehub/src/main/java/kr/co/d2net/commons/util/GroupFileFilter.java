package kr.co.d2net.commons.util;

import java.io.File;
import java.io.FileFilter;

public class GroupFileFilter implements FileFilter {

	public boolean accept(File file) {
		// 폴더가 아닐경우 skip
		// 폴더명 체계가 아닐경우 skip (R2013-0000)
		if(file.isDirectory() && file.getName().matches("^[A-Z]{1}[0-9]{4}-[0-9]{4}")) {
			return true;
		} else {
			return false;
		}
	}

}
