package kr.co.d2net.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kr.co.d2net.commons.util.DateUtil;
import kr.co.d2net.commons.util.UserFilenameFilter;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.WeekSchTbl;
import kr.co.d2net.task.job.TranscodeJobControl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

public class CglibTest {

	@Test
	public void setTest() {
		TranscodeJobControl.removeCtiId(1L);
	}
	
	@Ignore
	@Test
	public void binSetterTest() {
		WeekSchTbl bean = new WeekSchTbl();

		Utility.setValue(bean, "onair_day", "20110810");

		System.out.println(bean.getOnairDay());

		String fileNm = "e4dd991e-50d4-462a-9056-1cd78085c9d5_500K_HD";
		System.out.println(fileNm.substring(0, fileNm.indexOf("_")));

	}
	
	@Ignore
	@Test
	public void prefixTest() {
		try {
			String fileNm = "DNPS_POST";
			if(fileNm.indexOf("POST") > -1) {
				System.out.println(fileNm.substring(0, fileNm.indexOf("_")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		

	}

	@Ignore
	@Test
	public void filterTest() {
		try {
			//Search search = new Search();
			//search.setType("1fm");
			//search.setKeyword("2012-02-03");

			File f = new File("D:\\tmp");
			File[] result = f.listFiles(new UserFilenameFilter("mpg", "20130410110720"));
			System.out.println(result[0].getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void dateBetweenTest() {
		try {
			//System.out.println(DateUtil.daysBetween("2012.03.01", "2012.02.28", "yyyy.MM.dd"));
			System.out.println(DateUtil.daysBetween(Utility.getDate(new Date(), 0, "yyyyMMdd"), Utility.getDate("yyyyMMdd")));
			//System.out.println(Utility.getDayBetween((Integer.valueOf(2) -1) * 7, "yyyyMMdd"));
			
			//System.out.println(Utility.getDate(new Date(), "yyyyMMdd", 1));
			//System.out.println("/mp2/hello".replace("mp2", "mp4").replace("rms", "mp4"));
			System.out.println(Utility.getDate(new Date(), "yyyyMMdd", 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testDate(){
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 

			Calendar cal = Calendar.getInstance(Locale.KOREA); 
			if(cal.get(Calendar.DAY_OF_WEEK) == 1)
				cal.add(Calendar.DATE, -7);
			else
				cal.add(Calendar.DATE, 0);

			cal.set(Calendar.DAY_OF_WEEK, 2); 
			String startDate = format.format(cal.getTime()); 
			System.out.println(startDate); 

			cal.set(Calendar.DAY_OF_WEEK, 7); 
			cal.add(Calendar.DATE, 1);
			String endDate = format.format(cal.getTime()); 
			System.out.println(endDate); 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Ignore
	@Test
	public void testIgnoreCount() {
		try {
			String aa = "00:1230";
			System.out.println(StringUtils.countMatches(aa, ":"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testDateParse() {
		try {
			String aDate = "2013-02-02 00:00:00";
			System.out.println(aDate.substring(0, 10));
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
			System.out.println(sf.format(sf.parse(aDate.substring(0, 10))));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void testSplite() {
		try {
			String aa = "CHAN|VT";
			String[] bb = aa.split("\\|");
			System.out.println(bb[0]+", "+bb[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void testSubString() {
		try {
			String[] exts = {"xml", "XML"};
			String[] fileNames = null;
			String sourceNm = "201305310263_xdcamhd422_1";
			if(sourceNm.indexOf("_") > -1) {
				fileNames = sourceNm.split("\\_");
			} else {
				fileNames = new String[]{sourceNm};
			}
			String metaXML = null;
			for(String ext : exts) {
				try {
					String fileName = "";
					for(int i=0; i < fileNames.length; i++) {
						fileName += fileName.equals("") ? fileNames[i] : "_"+fileNames[i];
						System.out.println("fileName: "+fileName);
						/*File f = new File(prefix + orgPath + File.separator
								+ fileName
								+ "."+ext);
						
						if(f.exists()) {
							metaXML = FileUtils.readFileToString(f, "UTF-8");
							break;
						}*/
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void dateTest() {
		try {
			String ymd = "20130830";
			Calendar temp = Calendar.getInstance();
			
			String time = "224229";
			String year = ymd.substring(0,4);
			String month = ymd.substring(4,6);
			String day = ymd.substring(6);
			String hour = time.substring(0,2);
			String minute = time.substring(2,4);
			String sec = time.substring(4);
			
			temp.set(Calendar.YEAR, Integer.valueOf(year));
			temp.set(Calendar.MONTH, Integer.valueOf(month)-1);
			temp.set(Calendar.DATE, Integer.valueOf(day));
			temp.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
			temp.set(Calendar.MINUTE, Integer.valueOf(minute));
			temp.set(Calendar.SECOND, Integer.valueOf(sec));
			
			System.out.println(Utility.getDate(temp.getTime(), "yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
