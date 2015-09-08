package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.service.ContentsInstManagerService;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ContentsInstManagerTest extends BaseDaoConfig {
	
	@Autowired
	private ContentsInstManagerService contentsInstManagerService;
	
	
	@Test
	public void contentsSelectTest() {
		try {
			ContentsInstTbl contentinstTbl = new ContentsInstTbl();
			
			contentinstTbl.setCtiId((long) 2222);
			contentinstTbl.setCtId((long) 5);
			contentinstTbl.setCtiFmt("fmt");
			contentinstTbl.setBitRt("bit");
			contentinstTbl.setDrpFrmYn("y");
			contentinstTbl.setVdHresol(10);
			contentinstTbl.setVdVresol(20);
			contentinstTbl.setFlPath("path");
			contentinstTbl.setColorCd("color");
			contentinstTbl.setOrgFileNm("file");
			
			
			
			contentsInstManagerService.insertContentsInst(contentinstTbl);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
