package kr.co.d2net.commons.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimeConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {

	final static Logger logger = LoggerFactory.getLogger(Utility.class);

	final static String[] FILE_EXT = new String[] {"MXF", "mxf"};
	final static String[] DELETE_EXT = new String[] {"MXF", "mxf", "xml"};

	public static java.sql.Timestamp getTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 원하는 형식으로 현재날짜를 반환한다.
	 * @param format
	 * @return
	 */
	public static String getTimestamp(String format) {
		String timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = new Date(System.currentTimeMillis());
			timestamp = sdf.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * 원하는 형식으로 현재날짜를 반환한다.
	 * @param format
	 * @return
	 */
	public static String getDate(String format) {
		String timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = new Date(System.currentTimeMillis());
			timestamp = sdf.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * 입력한 숫자를 빼거나 더한 날짜를 반환한다.
	 * @param iDay
	 * @return String
	 */
	public static String getDate ( int iDay ) {
		Calendar temp=Calendar.getInstance ( );
		StringBuffer sbDate=new StringBuffer ( );

		temp.add ( Calendar.DAY_OF_MONTH, iDay );

		int nYear = temp.get ( Calendar.YEAR );
		int nMonth = temp.get ( Calendar.MONTH ) + 1;
		int nDay = temp.get ( Calendar.DAY_OF_MONTH );

		sbDate.append ( nYear );
		sbDate.append(StringUtils.leftPad(String.valueOf(nMonth), 2, "0"));
		sbDate.append(StringUtils.leftPad(String.valueOf(nDay), 2, "0"));

		return sbDate.toString ( );
	}


	/**
	 * Timestamp 값을 원하는 포맷으로 반환한다.
	 * @param reqTimestamp
	 * @param format
	 * @return
	 */
	public static String getTimestamp(Timestamp reqTimestamp, String format) {
		String timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			timestamp = sdf.format(reqTimestamp.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return timestamp;
	}
	
	/**
	 * 입력받은 문자열 날짜를 Timestamp 형식으로 변환한다.
	 * @param reqTimestamp
	 * @param format
	 * @return
	 */
	public static Timestamp getTimestamp(String value, String format) {
		Timestamp timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			java.util.Date date = sdf.parse(value);

			timestamp = new java.sql.Timestamp(date.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return timestamp;
	}
	
	/**
	 * 입력받은 문자열 날짜를 Timestamp 형식으로 변환한다.
	 * @param reqTimestamp
	 * @param format
	 * @return
	 */
	public static String getTimestampReplace(String value, String from_format, String to_format) {
		String timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(from_format);
			java.util.Date date = sdf.parse(value);
			Timestamp t = new java.sql.Timestamp(date.getTime());

			sdf = new SimpleDateFormat(to_format);
			timestamp = sdf.format(t.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * Date 값을 원하는 포맷으로 반환한다.
	 * @param reqTimestamp
	 * @param format
	 * @return
	 */
	public static String getDate(Date reqDate, String format) {
		String timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			timestamp = sdf.format(reqDate);
		}catch(Exception e){
			timestamp = "";
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * <pre>
	 * 입력된 날짜가 지정된 포맷형식인지 판단
	 * </pre>
	 * @param format
	 * @param date
	 * @return boolean
	 */
	public static boolean isValidDate(String format, String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.parse(date);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 두 날짜 사이의 기간을 반환한다.
	 * @param startDate
	 * @param endDate
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static long getDaysBetween( String startDate, String endDate, String format ) throws ParseException{
		if (format == null) 
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date sDate;
		Date eDate;
		long day2day = 0;
		sDate = sdf.parse(startDate);
		eDate = sdf.parse(endDate);
		day2day = (eDate.getTime() - sDate.getTime()) / (1000*60*60*24);

		return day2day;
	}



	/**
	 * 특수문자를 치환한다.
	 * @param value
	 * @return
	 */
	public static String escapeXml(String value) {
		// & has to be checked and replaced before others
		if (value.matches(".*&.*")) {
			value = value.replaceAll("&", "&amp;");
		}
		if (value.matches(".*\'.*")) {
			value = value.replaceAll("\'", "&apos;");
		}
		if (value.matches(".*<.*")) {
			value = value.replaceAll("<", "&lt;");
		}
		if (value.matches(".*>.*")) {
			value = value.replaceAll(">", "&gt;");
		}
		if (value.matches(".*\".*")) {
			value = value.replaceAll("\"", "&quot;");
		}
		return value;
	}

	/**
	 * HTML escape 문자열을 역치환한다.
	 * @param value
	 * @return
	 */
	public static String unescapeXml(String value) {
		// & has to be checked and replaced before others
		if (value.matches(".*&amp;.*")) {
			value = value.replaceAll("&amp;", "&");
		}
		if (value.matches(".*&apos;.*")) {
			value = value.replaceAll("&apos;", "\'");
		}
		if (value.matches(".*&lt;.*")) {
			value = value.replaceAll("&lt;", "<");
		}
		if (value.matches(".*&gt;.*")) {
			value = value.replaceAll("&gt;", ">");
		}
		if (value.matches(".*&quot;.*")) {
			value = value.replaceAll("&quot;", "\"");
		}
		return value;
	}

	/**
	 * 입력된 Integer값이 null이면 -1을 반환한다.
	 * @param value
	 * @return Integer
	 */
	public static Integer defaultInteger(Integer value) {
		if(value == null) return -1;
		else return value;
	}

	/**
	 * 입력된 Integer값이 null이면 입력받은 defaultValue를 반환한다.
	 * @param value
	 * @return Integer
	 */
	public static Integer defaultInteger(Integer value, Integer defaultValue) {
		if(value == null) return defaultValue;
		else return value;
	}

	/**
	 * 입력된 Long값이 null이면 -1을 반환한다.
	 * @param value
	 * @return Long
	 */
	public static Long defaultLong(Long value) {
		if(value == null) return -1L;
		else return value;
	}

	/**
	 * 입력된 Long값이 null이면입력받은 defaultValue를 반환한다.
	 * @param value
	 * @return Long
	 */
	public static Long defaultLong(Long value, Long defaultValue) {
		if(value == null) return defaultValue;
		else return value;
	}

	/**
	 * DTO 객체에 담긴 데이타를 복제한다.
	 * @param target 복사될 객체
	 * @param orgin 원본객체
	 * @throws Exception
	 */
	public static void cloneObj(Object target, Object orgin) 
	throws Exception {
		try {
			BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
			beanUtilsBean.getConvertUtils().register(
					new SqlTimestampConverter(null), Timestamp.class); 
			beanUtilsBean.getConvertUtils().register(
					new SqlDateConverter(null), Date.class); 
			beanUtilsBean.getConvertUtils().register(
					new SqlTimeConverter(null), Time.class);
			beanUtilsBean.getConvertUtils().register(
					new LongConverter(0), Long.class);
			beanUtilsBean.getConvertUtils().register(
					new IntegerConverter(0), Integer.class);
			beanUtilsBean.copyProperties(target, orgin);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 기존 파일을 신규파일명으로 변경한다.
	 * @param orgFilePath
	 * @param newFilePath
	 * @throws Exception
	 */
	public static void fileRename(String orgFilePath, String newFilePath) throws Exception {
		try{
			File f1 = new File(orgFilePath);
			if(!f1.isFile()) {
				throw new Exception("변경할 파일명이 존재하지 않습니다. - "+f1.getAbsolutePath());
			}
			File f2 = new File(newFilePath);

			f1.renameTo(f2);
		}catch(Exception e){
			if(e instanceof FileNotFoundException){
				throw e;
			}else{
				throw new Exception("File Rename Error", e);
			}
		}
	}

	/**
	 * <pre>
	 * 요청한 파일 객체를 참조하여 해당 컨텐츠를 삭제처리한다.
	 * </pre>
	 * @param file
	 */
	public static void fileForceDelete(File file) {
		try {
			if(SystemUtils.IS_OS_WINDOWS) {
				logger.debug("windows file delete!! - "+file.getAbsolutePath());
			} else {
				logger.debug("unix file delete!! - "+file.getAbsolutePath());

				Process proc = null;
				if(file.isDirectory()) {
					proc = Runtime.getRuntime().exec("rm -rf "+file.getAbsolutePath());
					proc.waitFor();

					if(proc.exitValue() != 0) {
						logger.error("folder delete error! - ["+file.getAbsolutePath()+"] : ");
						BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream ()));
						while (err.ready())
							logger.error(err.readLine());
						err.close();
					}
				} else {
					if(file.getAbsolutePath().toLowerCase().endsWith(".mxf")) {
						String filePath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("."));
						for(String ext : DELETE_EXT) {
							proc = Runtime.getRuntime().exec("rm -rf "+filePath+"."+ext);
							proc.waitFor();
							if(proc.exitValue() != 0) {
								logger.error("file delete error! - ["+file.getAbsolutePath()+"] : ");
								BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream ()));
								while (err.ready())
									logger.error(err.readLine());
								err.close();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void fileForceDelete(String filePath) {
		try {
			File f = new File(filePath);
			if(f.exists())
				fileForceDelete(f);
			else
				logger.error("file or folder not exist!! - "+filePath);
		} catch (Exception e) {
			logger.error("fileForceDelete error", e);
		}
	}

	/**
	 * <pre>
	 * 파일 객체의 배열을 받아서 삭제처리한다.
	 * </pre>
	 * @param files
	 */
	public static void fileForceDelete(File[] files) {
		try {
			for(File file : files) {
				fileForceDelete(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
