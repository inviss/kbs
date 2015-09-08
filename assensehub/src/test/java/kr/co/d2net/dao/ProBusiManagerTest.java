package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import kr.co.d2net.dto.ProBusiTbl;
import kr.co.d2net.service.ProBusiManagerService;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProBusiManagerTest extends BaseDaoConfig {
	
	@Autowired
	private ProBusiManagerService probusiManagerService;
	
	
	
	@Test
	public void probusiInsertTest() {
		try {
			ProBusiTbl probusiTbl = new ProBusiTbl();
			probusiTbl.setProFlid("333");
			probusiTbl.setBusiPartnerid("444");
			probusiTbl.setRemark("가나다");
			
			
			probusiManagerService.insertProBusi(probusiTbl);
			
			
			//Map<String, Object> params = new HashMap<String, Object>();
			//params.put("dataNm", "%탈%");
			//List<ProFlTbl> proflTbls = proflManagerService.findProFl(params);
			//for(ProFlTbl proflTbl2 : proflTbls) {
			//	System.out.println("profl_id : "+proflTbl2.getProFlid());
			//	System.out.println("ext : "+proflTbl2.getExt());
			//}
			//Map<String, Object> params = new HashMap<String, Object>();
			//params.put("ctId", 11114);
			//ProFlTbl proflTbl = proflManagerService.getProFl(params);
			//System.out.println("콘텐츠명: "+contentTbl.getCtNm());
			//System.out.println("프로파일아이디: "+proflTbl.getProFlid());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}