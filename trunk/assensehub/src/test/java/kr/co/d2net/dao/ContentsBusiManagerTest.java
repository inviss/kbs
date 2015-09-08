package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.d2net.dto.ContentsBusiTbl;
import kr.co.d2net.service.ContentsBusiManagerService;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ContentsBusiManagerTest extends BaseDaoConfig {
	
	@Autowired
	private ContentsBusiManagerService contentsbusiManagerService;
	
	
	@Test
	public void contentsbusiInsertTest() {
		try {
			ContentsBusiTbl contentbusiTbl = new ContentsBusiTbl();
			
			contentbusiTbl.setCtiId((long) 2222);
			contentbusiTbl.setBusiPartnerId("id");
			contentbusiTbl.setServUrl("url");
			
			
			
			
			contentsbusiManagerService.insertContentsBusi(contentbusiTbl);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
