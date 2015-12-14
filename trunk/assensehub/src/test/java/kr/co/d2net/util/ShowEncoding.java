package kr.co.d2net.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;

import kr.co.d2net.commons.util.Utility;

public class ShowEncoding {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			/*
			File f = new File("D:\\Workspace\\pia\\nhn\\pia\\WebContent\\black\\project\\0_MAIN\\main.jsp");
			InputStream is = new FileInputStream(f);
			System.out.println((new InputStreamReader(is)).getEncoding());
			*/
			/*
			System.out.println(Utility.getDateOfWeek(2, -1, "yyyyMMdd"));
			System.out.println(Utility.getDateOfWeek(8, -1, "yyyyMMdd"));
			System.out.println(Utility.getDateOfWeek(2, 0, "yyyyMMdd"));
			System.out.println(Utility.getDateOfWeek(8, 0, "yyyyMMdd"));
			
			String aa = "Y";
			System.out.println(aa.charAt(0));
			*/
			String df = "192.168.10.85:/cms01 11189546144 4234746464 7018752032  38% /mnt";
			String[] gu = StringUtils.splitByWholeSeparator(df, null	, 0);
			for (int k = 0; k < gu.length; k++) {
				System.out.println(gu[k]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
