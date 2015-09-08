package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.service.ProFlManagerService;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProFlManagerTest extends BaseDaoConfig {
	
	@Autowired
	private ProFlManagerService proflManagerService;
	
	
	@Ignore
	@Test
	public void proflSelectTest() {
		try {
			ProFlTbl proflTbl = new ProFlTbl();
			proflTbl.setProFlid("333");
			proflTbl.setExt("wmv");
			
			
			
			proflManagerService.insertProFl(proflTbl);
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ext", "%wmv%");
			List<ProFlTbl> proflTbls = proflManagerService.findProFl(params);
			for(ProFlTbl proflTbl2 : proflTbls) {
				System.out.println("profl_id : "+proflTbl2.getProFlid());
				System.out.println("ext : "+proflTbl2.getExt());
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
	
	@Ignore
	@Test
	public void findProflExt() {
		try {
			List<String> exts = proflManagerService.findProflExt("row");
			for(String ext : exts) {
				System.out.println(ext);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
