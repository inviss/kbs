package kr.co.d2net.util;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kr.co.d2net.commons.util.DateUtil;
import kr.co.d2net.commons.util.UserFileFilter;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dao.BaseDaoConfig;
import kr.co.d2net.service.ProFlManagerService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class FileDeleteTest extends BaseDaoConfig {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ProFlManagerService proFlManagerService;

	@Ignore
	@Test
	public void checkFileList() {
		try {

			List<String> exts = proFlManagerService.findProflExt("high");
			File f = new File("D:/tmp/exts");
			File[] files = f.listFiles(new UserFileFilter(exts));
			if(files != null) {
				for(File file : files) {
					System.out.println(file.getAbsolutePath() +"-"+Utility.getDate(new Date(file.lastModified()), "yyyy-MM-dd"));
				}
			}
			System.out.println(Utility.getDate(Utility.getDay(-31), "yyyy-MM-dd"));

			//FileUtils.forceDelete(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void deletefiles(){
		try {
			logger.debug("##### DeleteTempWorker work START!!!! #####");
			Integer vsDay = Integer.valueOf(messageSource.getMessage(
					"media.audio.safe.days", null, Locale.KOREA)) * -1;

			String mainDrive = messageSource.getMessage(
					"mam.mount.prefix", null, Locale.KOREA);

			String target = messageSource.getMessage(
					"manual.target.audio", null, null);

			String dir_list = messageSource.getMessage("manual.target.audio.folder.list",
					null, Locale.KOREA);


			String[] dirs = dir_list.split(",");
			/*
			 * NAS Storage /mnt/manual/audio/main,/mnt/manual/audio/backup 컨텐츠를 임의 날짜 이상 지났을 경우 삭제한다.
			 */
			String delDt = Utility.getDate(vsDay);


			for(String dir_sub:dirs){
				String deletePath = "";
				File[] fList=null;

				String tmp = "D:\\FUNNY\\MP3";

				deletePath=tmp+mainDrive+File.separator+target+File.separator+dir_sub;


				if (SystemUtils.IS_OS_WINDOWS) {
					if (deletePath.indexOf("/") > -1)
						deletePath = deletePath.replaceAll("\\/", "\\\\");
				} else {
					deletePath = File.separator + deletePath;
				}

				logger.debug("1==>>"+deletePath);
				File targetFolder = new File(deletePath);
				if (targetFolder.isDirectory()) {
					logger.debug("2==>>"+deletePath);
					fList = targetFolder.listFiles();
					for(File f:fList){
						//에센스 영상 삭제
						Date date = new Date(f.lastModified());
						logger.debug("###### f.lastModified() :"+Utility.getDate(date, vsDay,"yyyyMMdd")+"######");
						logger.debug("###### delDt:"+Utility.getTimestamp("yyyyMMdd")+"#####");

						logger.debug("######### between:"+DateUtil.daysBetween(Utility.getDate(date, vsDay,"yyyyMMdd"), Utility.getTimestamp("yyyyMMdd")));

						if (f.exists()&&
								DateUtil.daysBetween(Utility.getDate(date, vsDay,"yyyyMMdd"), Utility.getTimestamp("yyyyMMdd"))>-1
								) {
							FileUtils.forceDelete(f);
							if (logger.isDebugEnabled()) {
								logger.debug("File Path: "
										+ f.getName() +  " DELETED!!");
							}
						}

					}
				}


			}

			logger.debug("##### DeleteTempWorker work END !!!! #####");

		} catch (Exception e) {
			logger.error("DeleteTempWorker", e);
		}
	}
	
	@Test
	public void stringPatternTest() {
		try {
			String name = "A2013-A015";
			System.out.println(name.matches("^[A-Z]{1}[0-9]{4}-[0-9]{4}"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
