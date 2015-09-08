package kr.co.d2net.commons.util;

import java.io.File;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.scope.SessionScope;
import com.ibatis.sqlmap.engine.scope.StatementScope;

public class Utility {

	final static Logger logger = LoggerFactory.getLogger(Utility.class);

	public static String[] getDayOfWeek() {
		return new String[] { "일", "월", "화", "수", "목", "금", "토" };
	}

	public static String[] getDayOfWeekSun() {
		return new String[] { "월", "화", "수", "목", "금", "토", "일" };
	}

	/*
	 * 현재일을 Timestamp값으로 구한다.
	 * 
	 * @param format
	 * 
	 * @return
	 */
	public static java.sql.Timestamp getTimestamp() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.KOREA);
		Calendar cal = Calendar.getInstance();

		return Timestamp.valueOf(sdf.format(cal.getTime()));
	}

	public static java.sql.Timestamp getTimestamp(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.KOREA);
		return Timestamp.valueOf(sdf.format(timestamp));
	}

	/*
	 * 현재일을 사용자 포맷에 맞춰서 표현한다.
	 * 
	 * @param format
	 * 
	 * @return
	 */
	public static String getTimestamp(String format) {
		String timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = new Date(System.currentTimeMillis());
			timestamp = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	public static Timestamp getTimestamp(String timestring, String format) {
		Timestamp timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(timestring);
			timestamp = new Timestamp(date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	public static String getTimestamp(Timestamp reqTimestamp, String format) {
		String timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			timestamp = sdf.format(reqTimestamp.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	public static Date getDate(String date, String format) {
		Date d = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
			d = sdf.parse(date);
		} catch (Exception e) {
			logger.error("Utility getDate Error", e);
		}
		return d;
	}

	public static String getDate(String format) {
		String date_str = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date_str = sdf.format(new Date().getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date_str;
	}

	public static String getDate(Date date, String format) {
		String date_str = "";
		try {
			if(date == null) return "";
			else {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				date_str = sdf.format(date.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date_str;
	}

	public static String getDate(Date date, String format, int day) {
		String dateString = null;

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		try {
			dateString = sdf.format(date.getTime());
			calendar.setTime(sdf.parse(dateString));

			calendar.add(java.util.Calendar.DAY_OF_MONTH, day);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}

	public static String getDate(int iDay) {
		Calendar temp = Calendar.getInstance();

		temp.add(Calendar.DAY_OF_MONTH, iDay);

		String nYear = String.valueOf(temp.get(Calendar.YEAR));
		String nMonth = StringUtil.padLeft(
				String.valueOf(temp.get(Calendar.MONTH) + 1), "0", 2);
		String nDay = StringUtil.padLeft(
				String.valueOf(temp.get(Calendar.DAY_OF_MONTH)), "0", 2);

		return nYear + "-" + nMonth + "-" + nDay;
	}

	public static Date getDay(int iDay) {
		Calendar temp = Calendar.getInstance();
		temp.add(Calendar.DAY_OF_MONTH, iDay);

		return temp.getTime();
	}

	public static String getTime(int iTime) {
		Calendar temp = Calendar.getInstance();

		temp.add(Calendar.MINUTE, iTime);

		String hour = StringUtil.padLeft(String.valueOf(temp.get(Calendar.HOUR_OF_DAY)), "0", 2);
		String minute = StringUtil.padLeft(String.valueOf(temp.get(Calendar.MINUTE)), "0", 2);
		String second = StringUtil.padLeft(String.valueOf(temp.get(Calendar.SECOND)), "0", 2);

		return hour + ":" + minute + ":" + second;
	}

	public static String getDate(Date d, int iDay) {
		Calendar temp = Calendar.getInstance();
		temp.setTime(d);

		temp.add(Calendar.DAY_OF_MONTH, iDay);

		String nYear = String.valueOf(temp.get(Calendar.YEAR));
		String nMonth = StringUtil.padLeft(
				String.valueOf(temp.get(Calendar.MONTH) + 1), "0", 2);
		String nDay = StringUtil.padLeft(
				String.valueOf(temp.get(Calendar.DAY_OF_MONTH)), "0", 2);

		return nYear + "-" + nMonth + "-" + nDay;
	}

	public static String getDate(Date d, int iDay, String format) {
		Calendar temp = Calendar.getInstance();
		temp.setTime(d);

		temp.add(Calendar.DAY_OF_MONTH, iDay);

		String nYear = String.valueOf(temp.get(Calendar.YEAR));
		String nMonth = StringUtil.padLeft(
				String.valueOf(temp.get(Calendar.MONTH) + 1), "0", 2);
		String nDay = StringUtil.padLeft(
				String.valueOf(temp.get(Calendar.DAY_OF_MONTH)), "0", 2);

		return nYear  + nMonth  + nDay;
	}

	public static String getDate(String date, int iDay) {
		Calendar temp = Calendar.getInstance();
		temp.setTimeInMillis(getDate(date, "yyyy-MM-dd").getTime());

		temp.add(Calendar.DAY_OF_MONTH, iDay);

		String nYear = String.valueOf(temp.get(Calendar.YEAR));
		String nMonth = StringUtil.padLeft(
				String.valueOf(temp.get(Calendar.MONTH) + 1), "0", 2);
		String nDay = StringUtil.padLeft(
				String.valueOf(temp.get(Calendar.DAY_OF_MONTH)), "0", 2);

		return nYear + "-" + nMonth + "-" + nDay;
	}

	public static String getDate(Long iTime, int iDay) {
		Calendar temp = Calendar.getInstance();
		temp.setTimeInMillis(iTime);

		temp.add(Calendar.DAY_OF_MONTH, iDay);

		String nYear = String.valueOf(temp.get(Calendar.YEAR));
		String nMonth = StringUtil.padLeft(
				String.valueOf(temp.get(Calendar.MONTH) + 1), "0", 2);
		String nDay = StringUtil.padLeft(
				String.valueOf(temp.get(Calendar.DAY_OF_MONTH)), "0", 2);

		return nYear + "-" + nMonth + "-" + nDay;
	}

	public static String getDate(Long iTime, int iDay, String format) {
		Calendar temp = Calendar.getInstance();
		temp.setTimeInMillis(iTime);

		temp.add(Calendar.DAY_OF_MONTH, iDay);

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String newDate = "";
		try {
			newDate = sdf.format(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return newDate;
	}

	public static String escapeTag(String value) {
		// & has to be checked and replaced before others
		if (value.matches(".*&.*")) {
			value = value.replaceAll("&", "&amp;");
		}
		/*
		 * if (value.matches(".*'.*")) { value = value.replaceAll("\''",
		 * "&apos;"); }
		 */
		if (value.matches(".*<.*")) {
			value = value.replaceAll("<", "&lt;");
		}
		if (value.matches(".*>.*")) {
			value = value.replaceAll(">", "&gt;");
		}
		if (value.matches(".*\".*")) {
			value = value.replaceAll("'", "&quot;");
		}
		return value;
	}

	public static String escapeXml(String value) {
		// & has to be checked and replaced before others
		if (value.matches(".*&.*")) {
			value = value.replaceAll("&", "&amp;");
		}
		if (value.matches(".*\'.*")) {
			value = value.replaceAll("'", "&apos;");
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

	public static String unescapeXml(String value) {
		// value = value.replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
		// & has to be checked and replaced before others
		if (value.matches(".*&amp;.*")) {
			value = value.replaceAll("&amp;", "&");
		}
		if (value.matches(".*&apos;.*")) {
			value = value.replaceAll("&apos;", "'");
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
	 * 한글 여부 체크
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isHangul(char c) {
		return isHangulSyllables(c) || isHangulJamo(c)
		|| isHangulCompatibilityJamo(c);
	}

	// 완성된 한글인가? 참조: http://www.unicode.org/charts/PDF/UAC00.pdf
	public static boolean isHangulSyllables(char c) {
		return (c >= (char) 0xAC00 && c <= (char) 0xD7A3);
	}

	// (현대 및 고어) 한글 자모? 참조: http://www.unicode.org/charts/PDF/U1100.pdf
	public static boolean isHangulJamo(char c) {
		return (c >= (char) 0x1100 && c <= (char) 0x1159)
		|| (c >= (char) 0x1161 && c <= (char) 0x11A2)
		|| (c >= (char) 0x11A8 && c <= (char) 0x11F9);
	}

	// (현대 및 고어) 한글 자모? 참조: http://www.unicode.org/charts/PDF/U3130.pdf
	public static boolean isHangulCompatibilityJamo(char c) {
		return (c >= (char) 0x3131 && c <= (char) 0x318E);
	}

	public static void responseXml(HttpServletResponse res, String msg)
	throws Exception {
		if (msg != null && msg.length() > 0) {
			res.setContentType("text/xml;charset=UTF-8");
			res.setHeader("Cache-Control", "no-cache");

			res.getWriter().write(msg);
		}
	}

	public static void responseText(HttpServletResponse res, String msg)
	throws Exception {
		if (msg != null && msg.length() > 0) {
			PrintWriter out = res.getWriter();
			if (msg != null && msg.length() > 0) {
				res.setContentType("text/html;charset=UTF-8");
				res.setHeader("Cache-Control", "no-cache");

				out.println(URLEncoder.encode(msg, "UTF-8"));
				out.close();
			}
		}
	}

	public static int objCompare(Object o1, Object o2) {
		int val = 0;
		if (o1 instanceof String) {
			val = (((String) o1).equals((String) o2)) ? 1 : 0;
		} else if (o1 instanceof Character) {

		} else if (o1 instanceof Integer) {

		} else if (o1 instanceof Long) {

		} else if (o1 instanceof Timestamp) {

		} else if (o1 instanceof Date) {

		} else {

		}
		return val;
	}

	public static String encodeMD5(String msg) {
		String tmp = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(msg.getBytes());

			byte byteData[] = md.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			tmp = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return tmp;
	}

	public static void readFiles(File file, List<String> fileList) {
		if (file == null || !file.exists()) {
			return;
		}

		if (file.isDirectory()) {
			String[] files = file.list();

			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					readFiles(new File(file, files[i]), fileList);
				}
			}
		} else {
			try {
				String name = file.getAbsolutePath();
				if (name.endsWith("wmv"))
					fileList.add(name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String getSqlString(SqlMapClient client, String id,
			Object param) {
		ExtendedSqlMapClient extended = (ExtendedSqlMapClient) client;
		MappedStatement stmt = extended.getMappedStatement(id);

		SessionScope sessionScope = new SessionScope();
		StatementScope statementScope = new StatementScope(sessionScope);

		stmt.initRequest(statementScope);

		return "[" + id + "]" + stmt.getSql().getSql(statementScope, param)
		+ "\n" + param;
	}

	/**
	 * 입력된 문자열에 '_'이 존재한다면 분리하여 각 문장의 첫글자만 대문자로 변환하여 병합하고 없다면 해당 문장의 첫글자만 대문자로
	 * 변환한 후 맨 첫부분에 prefix 문자열을 합해 setter 메소드명 을 만들어서 반환한다.
	 * 
	 * @param name
	 * @return
	 */
	public static String capitalize(String prefix, String name) {
		String tmp = StringUtils.isEmpty(prefix) ? "" : prefix;
		if (name.indexOf("_") > 0) {
			String[] tmp2 = name.split("\\_");
			for (int j = 0; j < tmp2.length; j++)
				if (j == 0)
					tmp += tmp2[j].toLowerCase();
				else
					tmp += StringUtils.capitalize(tmp2[j].toLowerCase());
		} else {
			tmp = name;
		}
		return tmp;
	}

	public static <T> T setValue(T t, String name, String value) {
		BeanMap map = BeanMap.create(t);
		String key = capitalize(null, name);
		if (logger.isDebugEnabled()) {
			logger.debug("key: " + key + ", value: " + value);
		}
		if (map.containsKey(key)) {
			if (StringUtils.isBlank(value))
				return t;
			if (map.getPropertyType(key).equals(String.class)) {
				map.put(key, value);
			} else if (map.getPropertyType(key).equals(Long.TYPE)) {
				map.put(key, Long.valueOf(value));
			} else if (map.getPropertyType(key).equals(Integer.TYPE)) {
				map.put(key, Integer.valueOf(value));
			} else if (map.getPropertyType(key).equals(Date.class)) {
				map.put(key, getDate(value, "yyyyMMdd"));
			} else if (map.getPropertyType(key).equals(Timestamp.class)) {
				map.put(key, Timestamp.valueOf(value));
			} else if (map.getPropertyType(key).equals(Time.class)) {
				map.put(key, Time.valueOf(value));
			}
		}
		return t;
	}

	/**
	 * 해당일 주의 일자 (1:일요일 2:월요일 ~ 8: 다음주 일요일)
	 * 
	 * @param dayOfWeek
	 * 
	 * @param day
	 * @return
	 */
	public static String getDateOfWeek(int dayOfWeek, int day) {
		Calendar c = Calendar.getInstance(Locale.KOREA); 
		c.add(Calendar.DATE, (dayOfWeek+1)*7);

		if(c.get(Calendar.DAY_OF_WEEK) == 1)
			c.add(Calendar.DATE, -7);

		c.set(Calendar.DAY_OF_WEEK, 1); 
		c.add(Calendar.DATE, 1+(day-2));

		return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
		+ c.get(Calendar.DAY_OF_MONTH) + "";
	}

	public static String getDateOfWeekAll(int dayOfWeek, int day) {
		// 주차의 월요일(2) 계산
		// 주차의 일요일(8) 계산

		Calendar c = Calendar.getInstance(Locale.KOREA);
		c.add(Calendar.DAY_OF_MONTH, day);

		if(c.get(Calendar.DAY_OF_WEEK) != 1 && day == 0)
			dayOfWeek += 7;
		else 
			dayOfWeek += 7*day;

		//if(day == -2) dayOfWeek -= 7;
		c.add(Calendar.DAY_OF_MONTH, (dayOfWeek - c.get(Calendar.DAY_OF_WEEK)));
		//c.add(Calendar.DAY_OF_MONTH, (dayOfWeek - c.get(Calendar.DAY_OF_WEEK)));

		return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
		+ c.get(Calendar.DAY_OF_MONTH) + "";
	}

	public static String getDateOfWeek(int dayOfWeek, int day, String format) {
		// 주차의 월요일(2) 계산
		// 주차의 일요일(8) 계산
		String timestamp = null;

		try {

			Calendar c = Calendar.getInstance(Locale.KOREA);
			c.add(Calendar.DAY_OF_MONTH, day);

			//System.out.println(c.get(Calendar.DAY_OF_WEEK));
			if(c.get(Calendar.DAY_OF_WEEK) != 1 && day == 0){

				dayOfWeek += 7;
				//c.add(Calendar.DAY_OF_MONTH, (dayOfWeek - c.get(Calendar.DAY_OF_WEEK)));

			}else if(day == -2){
				dayOfWeek -= 0;
				//c.add(Calendar.DAY_OF_MONTH, (dayOfWeek - c.get(Calendar.DAY_OF_WEEK)));
			}else{

				//c.add(Calendar.DAY_OF_MONTH, (dayOfWeek - c.get(Calendar.DAY_OF_WEEK)));
			}


			c.add(Calendar.DAY_OF_MONTH, (dayOfWeek - c.get(Calendar.DAY_OF_WEEK)));
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			timestamp = sdf.format(c.getTime());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	public static String getDayBetween(int day, String format) {
		String between = "";
		try {
			DateFormat sdf = new SimpleDateFormat(format); 

			Calendar cal = Calendar.getInstance(Locale.KOREA); 
			cal.add(Calendar.DATE, day);
			if(cal.get(Calendar.DAY_OF_WEEK) == 1)
				cal.add(Calendar.DATE, -7);

			cal.set(Calendar.DAY_OF_WEEK, 2); 
			String startDate = sdf.format(cal.getTime()); 

			cal.set(Calendar.DAY_OF_WEEK, 7); 
			cal.add(Calendar.DATE, 1);
			String endDate = sdf.format(cal.getTime()); 

			between = startDate+","+endDate;
		} catch (Exception e) {
			logger.error("getDayBetween error", e);
		}

		return between;
	}

	/**
	 * 선택일 주의 일자 (1:일요일 2:월요일 ~ 8: 다음주 일요일)
	 * 
	 * @param dayOfWeek
	 *            주차 시작일
	 * @param day
	 *            종료일
	 * @param date
	 *            금일
	 * @return
	 */
	public static String getDateOfWeek(int dayOfWeek, int day, Date date) {
		// 주차의 월요일(2) 계산
		// 주차의 일요일(8) 계산

		Calendar c = Calendar.getInstance(Locale.KOREA);
		c.setTime(date);
		c.add(Calendar.DAY_OF_WEEK, 7);
		c.add(Calendar.DAY_OF_MONTH, day);
		c.add(Calendar.DAY_OF_MONTH, (dayOfWeek - c.get(Calendar.DAY_OF_WEEK)));

		return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
		+ c.get(Calendar.DAY_OF_MONTH) + "";
	}

	public static String getDayOfWeek(Date date) {

		String[] week = { "일", "월", "화", "수", "목", "금", "토" };
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return week[c.get(Calendar.DAY_OF_WEEK) - 1];
	}

	public static Date addDate(int iDay) {
		Calendar temp = Calendar.getInstance();

		temp.add(Calendar.DAY_OF_MONTH, iDay);

		return temp.getTime();
	}

	public static int whichDay(String s, String format) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format,
					Locale.KOREA);
			Date date = check(s, format);
			Calendar calendar = formatter.getCalendar();
			calendar.setTime(date);
			return calendar.get(7);
		} catch (Exception e) {
			e.printStackTrace();
			return -10000000;
		}
	}

	private static Date check(String s) throws ParseException {
		return check(s, "yyyyMMdd");
	}

	private static Date check(String s, String format) throws ParseException {
		if (s == null)
			throw new ParseException("date string to check is null", 0);
		if (format == null)
			throw new ParseException("format string to check date is null", 0);
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
		Date date = null;
		try {
			date = formatter.parse(s);
		} catch (ParseException e) {
			throw new ParseException(" wrong date:\"" + s + "\" with format \""
					+ format + "\"", 0);
		}
		if (!formatter.format(date).equals(s))
			throw new ParseException("Out of bound date:\"" + s
					+ "\" with format \"" + format + "\"", 0);
		else
			return date;
	}

	public static String convertStamp(long time) {
		Timestamp t = new Timestamp(time);
		SimpleDateFormat sf = new SimpleDateFormat("HHmmss");
		System.out.println("start time: " + sf.format(t));
		return sf.format(t);
	}
	
	public static boolean connectHttpPostStatus(String url,
			NameValuePair[] postParameters) throws Exception {
		
		if (url == null || url.length() < 1)
			throw new IllegalArgumentException("Agent URL is NULL");

		PostMethod post = null;
		try {
			String target = url;

			// 재시도 없음
			DefaultHttpMethodRetryHandler retryHandler = new DefaultHttpMethodRetryHandler(0, false);
			HttpClientParams clientParams = new HttpClientParams();
			clientParams.setParameter(HttpMethodParams.RETRY_HANDLER, retryHandler);

			HttpClient httpClient = new HttpClient(clientParams);
			/* 20초 제한설정이 있는듯..20초 이하는 적용이 되는데 이상은 안됨.
			httpClient.getParams().setParameter("http.protocol.expect-continue", false);
			httpClient.getParams().setParameter("http.connection.timeout", 30000);
			httpClient.getParams().setParameter("http.socket.timeout", 30000);
			 */
			//HttpClient httpClient = new HttpClient();

			post = new PostMethod(target);
			post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=euc-kr");

			if (postParameters != null && postParameters.length > 0)
				post.setRequestBody(postParameters);

			int iGetResultCode = httpClient.executeMethod(post);

			// 실행 성공
			if (iGetResultCode >= 200 && iGetResultCode <= 299) {
				logger.debug("[BaseHttpClient.class:::connectHttpPostMethod][iGetResultCode]"+ iGetResultCode + " :연동 성공 ");
				return true;
			} else {
				logger.error("[BaseHttpClient.class:::connectHttpPostMethod]연동 실패 코드 : "+ iGetResultCode);
				return false;
			}
		} catch (SocketTimeoutException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if (post != null) {
				try {
					post.releaseConnection();
				} catch (Exception ex) {
					logger.error("[BaseHttpClient.class:::connectHttpPostMethod]Exception:: ", ex);
				}
			}
		}

	}

	/**
	 * 원격지 호스트에 접속하여 POST방식으로 해당 파일을 실행하고 얻은 결과를 리턴
	 * 
	 * @param param
	 *            검색조건
	 * @return String
	 * @throws DataAccessException
	 */
	public static String connectHttpPostMethod(String url,
			NameValuePair[] postParameters) throws Exception {
		PostMethod post = null;
		if (url == null || url.length() < 1)
			throw new IllegalArgumentException("Agent URL is NULL");

		String rtnValue = null;
		try {
			String target = url;

			// 재시도 없음
			DefaultHttpMethodRetryHandler retryHandler = new DefaultHttpMethodRetryHandler(0, false);
			HttpClientParams clientParams = new HttpClientParams();
			clientParams.setParameter(HttpMethodParams.RETRY_HANDLER, retryHandler);

			HttpClient httpClient = new HttpClient(clientParams);
			/* 20초 제한설정이 있는듯..20초 이하는 적용이 되는데 이상은 안됨.
			httpClient.getParams().setParameter("http.protocol.expect-continue", false);
			httpClient.getParams().setParameter("http.connection.timeout", 30000);
			httpClient.getParams().setParameter("http.socket.timeout", 30000);
			 */
			//HttpClient httpClient = new HttpClient();

			post = new PostMethod(target);
			post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=euc-kr");

			if (postParameters != null && postParameters.length > 0)
				post.setRequestBody(postParameters);

			int iGetResultCode = httpClient.executeMethod(post);

			// 실행 성공
			if (iGetResultCode >= 200 && iGetResultCode <= 299) {
				logger.debug("[BaseHttpClient.class:::connectHttpPostMethod][iGetResultCode]"+ iGetResultCode + " :연동 성공 ");
				rtnValue = post.getResponseBodyAsString().trim();
			} else {
				logger.error("[BaseHttpClient.class:::connectHttpPostMethod]연동 실패 코드 : "+ iGetResultCode);
			}
		} catch (SocketTimeoutException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if (post != null) {
				try {
					post.releaseConnection();
					post = null;
				} catch (Exception ex) {
					logger.error("[BaseHttpClient.class:::connectHttpPostMethod]Exception:: ", ex);
				}
			}
		}

		return rtnValue;
	}

	/**
	 * Map 을 NameValuePair로 변환하여 저장 전달함.
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static NameValuePair[] convertNameValue(Map<String, Object> entity) throws Exception {
		if (entity == null) {
			return null;
		}

		NameValuePair[] postParam = null;
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if(!entity.isEmpty()) {
			Set<String> keys = entity.keySet();
			for(String key : keys) {
				nameValuePairs.add(new NameValuePair(key, (String)entity.get(key)));
			}
			
			postParam = new NameValuePair[nameValuePairs.size()];
			nameValuePairs.toArray(postParam);
		}
		
		return postParam;
	}

	/**
	 * <pre>
	 * string에 저장된 xml 정보를 DOM에 올림
	 * </pre>
	 * 
	 * @param xmlString
	 *            에 로딩할 XML 스트링
	 * @return XML 정보를 로딩한 DOM 객체
	 */
	public static Document stringToDom(String xmlString) {
		Document document = null;

		if (xmlString == null)
			return null;

		try {

			// String에 저장된 XML을 InputStream에 설정한뒤 DOM에 로딩
			byte[] xmlStringByte = xmlString.getBytes("UTF-8");
			java.io.InputStream inputStream = new java.io.ByteArrayInputStream(
					xmlStringByte);

			DocumentBuilderFactory factory = DocumentBuilderFactory
			.newInstance();
			factory.setIgnoringComments(false);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setNamespaceAware(true);
			factory.setValidating(false);

			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return document;
	}

	/**
	 * DOM을 받아서 파라미터로 받은 하위의 엘리먼트명이 childEleName 인자명과 동일한 첫번째 Element를 반환한다.
	 * 
	 * @param ele
	 *            the DOM
	 * @param childEleName
	 *            the child element name to look for
	 * @return the <code>org.w3c.dom.Element</code> instance, or
	 *         <code>null</code> if none found
	 */
	public static Element getChildElementByTagName(Document doc,
			String childEleName) {
		NodeList nl = doc.getChildNodes();
		System.out.println(nl.getLength());
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			System.out.println("name: " + node.getNodeName());
			if (node instanceof Element && nodeNameEquals(node, childEleName)) {
				return (Element) node;
			}
		}
		return null;
	}

	public static List<Node> getChildElementByTagNameList(Document doc,
			String childEleName) {

		List<Node> nodeList = new ArrayList<Node>();

		NodeList nl = doc.getChildNodes().item(0).getChildNodes();

		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element && nodeNameEquals(node, childEleName)) {
				nodeList.add(node);
			}
		}
		return nodeList;
	}

	/**
	 * DOM Node와 비교명이 서로 같은지 비교하여 boolean값을 반환한다.
	 */
	public static boolean nodeNameEquals(Node node, String desiredName) {
		return desiredName.equals(node.getNodeName())
		|| desiredName.equals(node.getLocalName());
	}

	/**
	 * DOM Element를 받아서 파라미터로 받은 하위의 엘리먼트명이 childEleName 인자명과 동일한 첫번째 Element의
	 * value name을 반환한다.
	 * 
	 * @param ele
	 *            the DOM element to analyze
	 * @param childEleName
	 *            the child element name to look for
	 * @return the extracted text value, or <code>null</code> if no child
	 *         element found
	 */
	public static String getChildElementValueByTagName(Element ele,
			String childEleName) {
		Element child = getChildElementByTagName(ele, childEleName);
		return (child != null ? getTextValue(child) : null);
	}

	/**
	 * DOM Element를 받아서 파라미터로 받은 하위의 엘리먼트명이 childEleName 인자명과 동일한 첫번째 Element를
	 * 반환한다.
	 * 
	 * @param ele
	 *            the DOM element to analyze
	 * @param childEleName
	 *            the child element name to look for
	 * @return the <code>org.w3c.dom.Element</code> instance, or
	 *         <code>null</code> if none found
	 */
	public static Element getChildElementByTagName(Element ele,
			String childEleName) {
		NodeList nl = ele.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element && nodeNameEquals(node, childEleName)) {
				return (Element) node;
			}
		}
		return null;
	}

	/**
	 * DOM Element에서 문자열 Value를 반환한다.
	 * 
	 * @see CharacterData
	 * @see EntityReference
	 * @see Comment
	 */
	public static String getTextValue(Element valueEle) {
		if (valueEle == null)
			return "";
		else {
			StringBuffer value = new StringBuffer();
			NodeList nl = valueEle.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node item = nl.item(i);
				if ((item instanceof CharacterData && !(item instanceof Comment))
						|| item instanceof EntityReference) {
					value.append(item.getNodeValue());
				}
			}
			// System.out.println("value: "+value.toString());
			return value.toString();
		}
	}

	public static Integer timeToSec(String time) {
		int _t = 0;
		try {
			if(time.indexOf(":") > -1) {
				String[] timecode = null;
				int match = StringUtils.countMatches(time, ":");
				switch(match) {
				case 1: // 00:35 -> 00 * 60 + 35
					timecode = time.split(":");
					_t = (Integer.valueOf(timecode[0]) * 60) + Integer.valueOf(timecode[1]);
					break;
				case 2: // 00:25:30 -> 00*60*60 + 25*60 + 30
					timecode = time.split(":");
					_t = (Integer.valueOf(timecode[0]) * 60 * 60) + (Integer.valueOf(timecode[1]) * 60) + Integer.valueOf(timecode[2]);
					break;
				}
			} else {
				String timecode = StringUtils.defaultIfEmpty(time, "0");
				if(timecode.length() == 4) {
					_t = (Integer.valueOf(time.substring(0, 2)) * 60) + Integer.valueOf(time.substring(2, 4));
				} else if(time.length() == 8) {
					_t = (Integer.valueOf(time.substring(0, 2)) * 60 * 60) + (Integer.valueOf(time.substring(2, 4)) * 60) + Integer.valueOf(time.substring(4, 6));
				} else {
					_t = Integer.valueOf(time);
				}
			}
		} catch (Exception e) {
			logger.error("time convert error", e);
		}
		return _t;
	}
}
