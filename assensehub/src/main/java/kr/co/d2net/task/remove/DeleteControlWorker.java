package kr.co.d2net.task.remove;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.DisuseInfoTbl;
import kr.co.d2net.service.ContentsInstManagerService;
import kr.co.d2net.service.ContentsManagerService;
import kr.co.d2net.service.DisuseInfoManagerService;
import kr.co.d2net.task.Worker;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("deleteControlWorker")
public class DeleteControlWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ContentsInstManagerService contentsInstManagerService;
	@Autowired
	private ContentsManagerService contentsManagerService;
	@Autowired
	private DisuseInfoManagerService disuseInfoManagerService;
	
	public void work() {

		if(logger.isInfoEnabled()) {
			logger.info("##### DeleteControlWorker work START!!!! #####");
		}
		try {
			String mainDrive = messageSource.getMessage("mam.mount.prefix", null, Locale.KOREA);
			
			/*
			 * NAS Storage 컨텐츠 삭제 현재일을 기준으로 보존기간일을 뺀 날짜보다 이전에 등록된 컨텐츠를 삭제한다.
			 */
			Map<String,Object> element = new HashMap<String,Object>();
			List<ContentsInstTbl> contentInfoTbls = contentsInstManagerService
					.findDeleteContentsInst(element);

			if (contentInfoTbls != null && !contentInfoTbls.isEmpty()) {
				for (ContentsInstTbl infoTbl : contentInfoTbls) {
					DisuseInfoTbl disuseInfoTbl = new DisuseInfoTbl();

					// MAIN 스토리지 삭제
					// MP2 삭제
					File f = null;
					File fLog = null;
					File fXml = null;
					File fSmil = null;
					if (StringUtils.isNotBlank(infoTbl.getFlPath())) {
						if (!infoTbl.getFlPath().endsWith("/")) {
							infoTbl.setFlPath(infoTbl.getFlPath() + "/");
						}
						
						f = new File(mainDrive + infoTbl.getFlPath(), infoTbl.getOrgFileNm() + "."+ infoTbl.getFlExt());
						fLog = new File(mainDrive + infoTbl.getFlPath(), infoTbl.getOrgFileNm() + "_qc.log");
						fXml = new File(mainDrive + infoTbl.getFlPath(), infoTbl.getOrgFileNm() + "."+ infoTbl.getFlExt()
										+ "."+ "xml");
						fSmil = new File(mainDrive + infoTbl.getFlPath(), infoTbl.getOrgFileNm() + "."+"smil");
						
						//에센스 영상 삭제
						if (f.exists()) {
							FileUtils.forceDelete(f);
							if (logger.isDebugEnabled()) {
								logger.debug("File Path: "
										+ mainDrive + infoTbl.getFlPath()
										+ infoTbl.getOrgFileNm() + "."
										+ infoTbl.getFlExt() + " DELETED!!");
							}
						} else {
							if (logger.isDebugEnabled()) {
								logger.debug("File Path: "
										+ mainDrive + infoTbl.getFlPath()
										+ infoTbl.getOrgFileNm() + "."
										+ infoTbl.getFlExt() + " Not Exists !!");
							}
						}
						
						//영상생성 로그 삭제
						if(fLog.exists()) FileUtils.forceDelete(fLog);
						//xml정보 삭제
						if(fXml.exists()) FileUtils.forceDelete(fXml);
						//smil 정보 삭제
						if(fSmil.exists()) FileUtils.forceDelete(fSmil);
						
					}

					// 삭제일 등록
					if (StringUtils.isNotBlank(infoTbl.getFlPath())) {
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("ctId", infoTbl.getCtId());
						
						ContentsTbl contentsTbl = contentsManagerService.getContents(params);
						if(StringUtils.isNotEmpty(contentsTbl.getUseYn())&&contentsTbl.getUseYn().equals("N")){
							disuseInfoTbl.setDataId(infoTbl.getCtId());
							disuseInfoTbl.setDisuseClf("C");
							
							disuseInfoManagerService.updateDisuseInfo(disuseInfoTbl);
						}
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("DeleteControlWorker", e);
		}
		if(logger.isInfoEnabled()) {
			logger.info("##### DeleteControlWorker work END !!!! #####");
		}
	}
}
