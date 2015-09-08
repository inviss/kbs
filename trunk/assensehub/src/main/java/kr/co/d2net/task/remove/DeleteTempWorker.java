package kr.co.d2net.task.remove;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kr.co.d2net.commons.util.DateUtil;
import kr.co.d2net.commons.util.UserFileFilter;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.service.ProFlManagerService;
import kr.co.d2net.task.Worker;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("deleteTempWorker")
public class DeleteTempWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ProFlManagerService proFlManagerService;

	public void work() {

		try {
			logger.debug("##### DeleteTempWorker work START!!!! #####");

			Integer vsDay = Integer.valueOf(messageSource.getMessage("media.audio.safe.days", null, null));
			String mainDrive = messageSource.getMessage("mam.mount.prefix", null,null);
			String target = messageSource.getMessage("manual.target.audio", null, null);
			String xmlDeleteDays = messageSource.getMessage("live.dairyorder.xml.days",null, null);

			String tv1Log = messageSource.getMessage("1tv.xml.log",null, null);
			String tv2Log = messageSource.getMessage("2tv.xml.log",null, null);
			String dir_list = messageSource.getMessage("manual.target.audio.folder.list",null, null);

			String subDrive = messageSource.getMessage("mam.mount.prefix2", null,null);
			Integer safeDay = Integer.valueOf(StringUtils.defaultIfEmpty(messageSource.getMessage("media.service.delete.days", null,null), "180"));

			Date saveDd = Utility.getDay(-(safeDay));

			Calendar fileTm = Calendar.getInstance();
			File sub = new File(subDrive+File.separator+"mp4");
			if(sub.exists()) {
				List<String> exts = proFlManagerService.findProflExt("row");
				File[] groups = sub.listFiles();
				if(groups != null) {
					for(File group : groups) {
						File[] programs = group.listFiles();
						if(programs != null) {
							for(File program : programs) {
								File[] profiles = program.listFiles(new UserFileFilter(exts));
								if(profiles != null) {
									for(File profile : profiles) {
										// 보관일보다 이전 파일이라면 삭제대상

										fileTm.setTimeInMillis(profile.lastModified());
										Date fileDd = fileTm.getTime(); 
										
										if(fileDd.before(saveDd)) {
											FileUtils.forceDelete(profile);
											logger.info("Garbege File Delete: "+ profile.getAbsolutePath() +  " DELETED!! - " +
													"created: "+Utility.getDate(fileDd, "yyyy-MM-dd"));
										}
									}
								}

								// 하위 폴더에 영상이 없다면 폴더를 삭제한다.
								if(profiles == null || profiles.length <= 0) {
									FileUtils.forceDelete(program);
									logger.info("Garbege Folder Delete: "+ program.getAbsolutePath() +  " DELETED!!");
								}
							}
						}

						// 하위 폴더에 프로그램 폴더가 없다면 그룹폴더를 삭제한다.
						if(programs == null || programs.length <= 0) {
							FileUtils.forceDelete(group);
							logger.info("Garbege Folder Delete: "+ group.getName() +  " DELETED!!");
						}
					}
				}
			}


			String[] dirs = dir_list.split(",");
			/*
			 * NAS Storage /mnt/mp2/manual/audio/main , /mnt/mp2/manual/audio/backup 컨텐츠를 임의 날짜 이상 지났을 경우 삭제한다.
			 */

			for(String dir_sub : dirs){
				String deletePath = "";
				File[] fList=null;

				String tmp="";
				if (SystemUtils.IS_OS_WINDOWS) {
					tmp = "D:\\tmp";
				}else{
					tmp = "";
				}

				deletePath=tmp+mainDrive+File.separator+target+File.separator+dir_sub;


				if (SystemUtils.IS_OS_WINDOWS) {
					if (deletePath.indexOf("/") > -1)
						deletePath = deletePath.replaceAll("\\/", "\\\\");
				} else {
					deletePath = File.separator + deletePath;
				}

				File targetFolder = new File(deletePath);
				if (targetFolder.isDirectory()) {
					fList = targetFolder.listFiles();
					for(File f : fList){
						//에센스 영상 삭제
						Date date = new Date(f.lastModified());
						if (f.exists() &&
								DateUtil.daysBetween(
										Utility.getDate(date, vsDay, "yyyyMMdd"), Utility.getDate("yyyyMMdd")) > -1) {
							FileUtils.forceDelete(f);
							if (logger.isInfoEnabled()) {
								logger.info("File Path: "+ f.getAbsolutePath() +  " DELETED!!");
							}
						}
					}
				}
			}


			//------------live xml log 파일 삭제--------------
			if(tv1Log!=null&&!tv1Log.equals("")){
				String deletePath = "";
				File[] fList=null;

				String tmp="";
				if (SystemUtils.IS_OS_WINDOWS) {
					tmp = "D:\\tmp";
				}else{
					tmp = "";
				}

				deletePath=tmp+mainDrive+tv1Log;


				if (SystemUtils.IS_OS_WINDOWS) {
					if (deletePath.indexOf("/") > -1)
						deletePath = deletePath.replaceAll("\\/", "\\\\");
				} else {
					deletePath = File.separator + deletePath;
				}

				File targetFolder = new File(deletePath);
				if (targetFolder.isDirectory()) {
					fList = targetFolder.listFiles();
					for(File f:fList){
						//에센스 영상 삭제
						Date date = new Date(f.lastModified());
						if (f.exists()&&
								DateUtil.daysBetween(Utility.getDate(date, Integer.parseInt(xmlDeleteDays),"yyyyMMdd"), Utility.getTimestamp("yyyyMMdd"))>-1
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

			if(tv2Log!=null&&!tv2Log.equals("")){
				String deletePath = "";
				File[] fList=null;

				String tmp="";
				if (SystemUtils.IS_OS_WINDOWS) {
					tmp = "D:\\tmp";
				}else{
					tmp = "";
				}

				deletePath=tmp+mainDrive+tv2Log;


				if (SystemUtils.IS_OS_WINDOWS) {
					if (deletePath.indexOf("/") > -1)
						deletePath = deletePath.replaceAll("\\/", "\\\\");
				} else {
					deletePath = File.separator + deletePath;
				}

				File targetFolder = new File(deletePath);
				if (targetFolder.isDirectory()) {
					fList = targetFolder.listFiles();
					for(File f:fList){
						//에센스 영상 삭제
						Date date = new Date(f.lastModified());
						if (f.exists() && DateUtil.daysBetween(Utility.getDate(date, Integer.parseInt(xmlDeleteDays),"yyyyMMdd"), 
								Utility.getTimestamp("yyyyMMdd")) > -1) {
							FileUtils.forceDelete(f);
							if (logger.isDebugEnabled()) {
								logger.debug("File Path: "+ f.getName() +  " DELETED!!");
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
}
