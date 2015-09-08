package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.DisuseInfoTbl;
import kr.co.d2net.service.ContentsInstManagerService;
import kr.co.d2net.service.DisuseInfoManagerService;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class DisuseInfoManagerTest extends BaseDaoConfig {
	
	@Autowired
	private DisuseInfoManagerService disuseinfoManagerService;
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ContentsInstManagerService contentsInstManagerService;
	
	
	@Test
	@Ignore
	public void disuseinfoInsertTest() {
		try {
			DisuseInfoTbl disuseinfoTbl = new DisuseInfoTbl();
			disuseinfoTbl.setDisuseNo("5");
			disuseinfoTbl.setDataNm("비타민5");
			disuseinfoTbl.setDataId((long) 14);
			
			
			disuseinfoManagerService.insertDisuseInfo(disuseinfoTbl);
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("dataNm", "%타%");
			List<DisuseInfoTbl> disuseinfoTbls = disuseinfoManagerService.findDisuseInfo(params);
			for(DisuseInfoTbl disuseinfoTbl2 : disuseinfoTbls) {
				System.out.println("NO : "+disuseinfoTbl2.getDisuseNo());
				System.out.println("NM : "+disuseinfoTbl2.getDataNm());
			}
			//Map<String, Object> params = new HashMap<String, Object>();
			//params.put("ctId", 11114);
			//ProFlTbl proflTbl = proflManagerService.getProFl(params);
			//System.out.println("콘텐츠명: "+contentTbl.getCtNm());
			//System.out.println("프로파일아이디: "+proflTbl.getProFlid());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelete(){
		try {
			Integer vsDay = Integer.valueOf(messageSource.getMessage(
					"media.safe.days", null, Locale.KOREA)) * -1;

			Map<String, Object> params = new HashMap<String, Object>();
		
			/*
			 * NAS Storage 컨텐츠 삭제 현재일을 기준으로 보존기간일을 뺀 날짜보다 이전에 등록된 컨텐츠를 삭제한다.
			 */
			// String delDt = Utility.getDate(vsDay);
			List<ContentsInstTbl> contentInfoTbls = contentsInstManagerService
					.findDeleteContentsInst(params);
			System.out.println("contentInfoTbls.size()"+contentInfoTbls.size());
			if (contentInfoTbls != null && !contentInfoTbls.isEmpty()) {
				for (ContentsInstTbl infoTbl : contentInfoTbls) {
				}
			}
	
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
}