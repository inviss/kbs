package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import kr.co.d2net.dto.BusiPartnerTbl;
import kr.co.d2net.service.BusiPartnerManagerService;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BusiPartnerManagerTest extends BaseDaoConfig {
	
	@Autowired
	private BusiPartnerManagerService busipartnerManagerService;
	
	
	
	@Test
	public void busipartnerInsertTest() {
		try {
			BusiPartnerTbl busipartnerTbl = new BusiPartnerTbl();
			busipartnerTbl.setBusiPartnerid("444");
			
			
			
			
			busipartnerManagerService.insertBusiPartner(busipartnerTbl);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}