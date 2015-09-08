package kr.co.d2net.task.remove;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kr.co.d2net.commons.util.GroupFileFilter;
import kr.co.d2net.commons.util.UserFileFilter;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.service.ProFlManagerService;
import kr.co.d2net.task.Worker;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("deleteHighWorker")
public class DeleteHighWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ProFlManagerService proFlManagerService;

	public void work() {

		try {
			logger.debug("##### DeleteHighWorker work START!!!! #####");

			String mainDrive = messageSource.getMessage("mam.mount.prefix", null,null);

			Integer safeDay = Integer.valueOf(StringUtils.defaultIfEmpty(messageSource.getMessage("media.dafault.delete.days", null,null), "7"));

			Date saveDd = Utility.getDay(-(safeDay));

			Calendar fileTm = Calendar.getInstance();
			File sub = new File(mainDrive+File.separator+"mp2");
			if(sub.exists()) {
				List<String> exts = proFlManagerService.findProflExt("high");
				File[] groups = sub.listFiles(new GroupFileFilter());
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

			logger.debug("##### DeleteHighWorker work END !!!! #####");

		} catch (Exception e) {
			logger.error("DeleteTempWorker", e);
		}

	}
}
