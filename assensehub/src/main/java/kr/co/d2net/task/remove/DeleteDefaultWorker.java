package kr.co.d2net.task.remove;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.service.ContentsInstManagerService;
import kr.co.d2net.task.Worker;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("deleteDefaultWorker")
public class DeleteDefaultWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ContentsInstManagerService contentsInstManagerService;

	public void work() {
		
		if(logger.isInfoEnabled()) {
			logger.info("##### DeleteDefaultWorker work START!!!! #####");
		}

		Integer live = Integer.valueOf(messageSource.getMessage(
				"media.live.delete.days", null, Locale.KOREA))*-1;

		Integer kdas = Integer.valueOf(messageSource.getMessage(
				"media.kdas.delete.days", null, Locale.KOREA))*-1;

		Integer nps = Integer.valueOf(messageSource.getMessage(
				"media.nps.delete.days", null, Locale.KOREA))*-1;

		Integer nas = Integer.valueOf(messageSource.getMessage(
				"media.nas.delete.days", null, Locale.KOREA))*-1;
		
		// 추가됨
		Integer dmcr = Integer.valueOf(messageSource.getMessage(
				"media.dmcr.delete.days", null, Locale.KOREA))*-1;
		
		Integer aach = Integer.valueOf(messageSource.getMessage(
				"media.aach.delete.days", null, Locale.KOREA))*-1;
		// 여기까지 추가됨

		Integer atrim = Integer.valueOf(messageSource.getMessage(
				"media.atrim.delete.days", null, Locale.KOREA))*-1;

		Integer vtrim = Integer.valueOf(messageSource.getMessage(
				"media.vtrim.delete.days", null, Locale.KOREA))*-1;

		Integer rms = Integer.valueOf(messageSource.getMessage(
				"media.rms.delete.days", null, Locale.KOREA))*-1;

		Integer service = Integer.valueOf(messageSource.getMessage(
				"media.service.delete.days", null, Locale.KOREA))*-1;


		String mainDrive = messageSource.getMessage(
				"mam.mount.prefix", null, Locale.KOREA);   // 신규 10TB : mp2

		String subDrive = messageSource.getMessage(
				"mam.mount.prefix2", null, Locale.KOREA);   // 기존 100TB  :mp4

		/*
		 * NAS Storage 컨텐츠 삭제 현재일을 기준으로 보존기간일을 뺀 날짜보다 이전에 등록된 컨텐츠를 삭제한다.
		 */
		// String delDt = Utility.getDate(vsDay);
		Map<String,Object> element = new HashMap<String,Object>();
		element.put("live", live);
		element.put("kdas", kdas);
		element.put("nps", nps);
		element.put("nas", nas);
		element.put("atrim", atrim);
		element.put("vtrim", vtrim);
		element.put("rms", rms);
		element.put("service", service);
		element.put("dmcr", dmcr);
		element.put("aach", aach);

		try {
			
			List<ContentsInstTbl> contentInfoTbls = contentsInstManagerService.findDefaultDeleteContentsInst(element);
			for (ContentsInstTbl contentsInstTbl : contentInfoTbls) {
				// MAIN 스토리지 삭제
				// 고해상도 및 서비스 파일 자동 삭제
				File f = null;
				if (StringUtils.isNotBlank(contentsInstTbl.getFlPath())) {
					if (!contentsInstTbl.getFlPath().endsWith("/")) {
						contentsInstTbl.setFlPath(contentsInstTbl.getFlPath() + "/");
					}
					if(contentsInstTbl.getFlPath().indexOf("mp2") > -1)
						f = new File(mainDrive + StringUtils.trimToEmpty(contentsInstTbl.getFlPath()),
								contentsInstTbl.getOrgFileNm() + "."
										+ contentsInstTbl.getFlExt());
					else
						f = new File(subDrive + StringUtils.trimToEmpty(contentsInstTbl.getFlPath()),
								contentsInstTbl.getOrgFileNm() + "."
										+ contentsInstTbl.getFlExt());
					//에센스 영상 삭제
					if (f.exists()) {
						FileUtils.forceDelete(f);
						if (logger.isDebugEnabled()) {
							logger.debug("File Path: "+f.getAbsoluteFile()+" DELETED!!");
						}
					} else {
						if (logger.isDebugEnabled()) {
							logger.debug("File Path: "+f.getAbsoluteFile()+ " Not Exists !!");
						}
					}
				}
				
				// 삭제일 등록
				if (StringUtils.isNotBlank(contentsInstTbl.getFlPath())) {
					ContentsInstTbl resultContentsinstTbl = new ContentsInstTbl();
					resultContentsinstTbl.setUseYn("N");
					resultContentsinstTbl.setModDt(Utility.getTimestamp());
					resultContentsinstTbl.setCtiId(contentsInstTbl.getCtiId());
					contentsInstManagerService.updateContentsInst(resultContentsinstTbl);
				}

			}
			
			if(logger.isInfoEnabled())
				logger.info("##### DeleteDefaultWorker work END!!!! #####");
		} catch (Exception e) {
			logger.error("DeleteDefaultWorker",e);
		}
	}
}
