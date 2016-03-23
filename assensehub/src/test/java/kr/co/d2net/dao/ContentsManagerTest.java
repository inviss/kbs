package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.service.ContentsInstManagerService;
import kr.co.d2net.service.ContentsManagerService;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ContentsManagerTest extends BaseDaoConfig {

	@Autowired
	private ContentsManagerService contentsManagerService;

	@Autowired
	private ContentsInstManagerService contentsInstManagerService;
	
	@Autowired
	private MediaToolDao mediaToolDao;

	@Test
	@Ignore
	public void contentsinsertTest() {
		try {
			ContentsTbl contentTbl2 = new ContentsTbl();
			contentTbl2.setCtId((long) 22);
			contentTbl2.setCtTyp("vid");
			contentTbl2.setCtNm("도전골든벨");
			contentTbl2.setCont("내용");
			contentTbl2.setEdtrid("edtrid");

			ContentsInstTbl contentinstTbl = new ContentsInstTbl();
			contentinstTbl.setCtiId((long) 3333);
			contentinstTbl.setCtId((long) 22);
			contentinstTbl.setCtiFmt("fmt");
			contentinstTbl.setBitRt("bit");
			contentinstTbl.setDrpFrmYn("y");
			contentinstTbl.setVdHresol(10);
			contentinstTbl.setVdVresol(20);
			contentinstTbl.setFlPath("path");
			contentinstTbl.setColorCd("color");
			contentinstTbl.setOrgFileNm("file");

			contentsManagerService.insertContents(contentTbl2, contentinstTbl);
			// contentsInstManagerService.insertContentsInst(contentinstTbl);
			// Map<String, Object> params = new HashMap<String, Object>();
			// params.put("ctId", (long) 2);
			// ContentsTbl contentTbl =
			// contentsManagerService.getContents(params);
			// System.out.println("콘텐츠명: "+contentTbl.getCtNm());
			// System.out.println("콘텐츠아이디: "+contentTbl.getCtId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Ignore
	@Test
	public void findEqContents(){
		Workflow workflow = new Workflow();
		workflow.setEqId("AT01_01");
		workflow.setGubun("C");
		try {
			mediaToolDao.findContentsInfo(workflow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void findRmsContents(){
		Search search =new Search();

		try {
			PaginationSupport<ContentsTbl> contents = contentsManagerService
					.findRmscontentsList(search);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Ignore
	@Test
	public void updateContents(){

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("jobId", "12345");
			ContentsTbl contentsTbl2 = contentsManagerService.getContents(params);
			if(contentsTbl2 != null) {
				contentsTbl2.setDataStatCd("200");
				contentsManagerService.updateContents(contentsTbl2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void findContents(){
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("regrids", "'DMCR','NPS','DNPS','KDAS'");
			//params.put("dataStatCd", "002");
			//params.put("ctId", 111738);
			params.put("trimSte", "N");
			params.put("limit", 6);
			
			List<ContentsTbl> contentsTbls = contentsManagerService.findContents(params);
			
			for(ContentsTbl contentsTbl : contentsTbls) {
				System.out.print(contentsTbl.getChannelCode()+", ");
				System.out.print(contentsTbl.getPgmNum()+", ");
				System.out.println(contentsTbl.getPgmNm());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
