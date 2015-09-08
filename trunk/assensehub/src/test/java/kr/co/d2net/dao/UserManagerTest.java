package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.MenuTbl;
import kr.co.d2net.dto.UserTbl;
import kr.co.d2net.service.CodeManagerService;
import kr.co.d2net.service.UserManagerService;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserManagerTest extends BaseDaoConfig {
	
	@Autowired
	private UserManagerService userManagerService;
	
	@Autowired
	private CodeManagerService codeManagerService;
	
	//@Ignore
	@Test
	public void userInsertTest() {
		try {
			UserTbl userTbl = new UserTbl();
			userTbl.setUserId("esh");
			userTbl.setUserNm("운영자");
			userTbl.setRegDt(Utility.getTimestamp());
			userTbl.setRegrid("esh");
			userTbl.setUseYn("Y");
			userTbl.setUserPass(Utility.encodeMD5("esh"));
			
			userTbl.setAuthId(1);
			
			userManagerService.insertUser(userTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void getUserTest() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", "asehub");
			
			UserTbl userTbl = userManagerService.getUserAuth(params);
			System.out.println("userNm: "+userTbl.getUserNm());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void userFindTest() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			
			List<UserTbl> userTbls = userManagerService.findUsers(params);
			System.out.println("user count: "+userTbls.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void userFindMenus() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", "asehub");
			
			List<MenuTbl> menuTbls = userManagerService.findUserMenus(params);
			System.out.println("user count: "+menuTbls.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void getPgmNum(){
		
		ContentsTbl contentsTbl = new ContentsTbl();
		contentsTbl.setPgmId("PS-2012017760-01-001");
		contentsTbl.setCtCla("02");
		contentsTbl.setCtTyp("05");
		
		/**
		 * 네이밍 수정순번 적용
		 */
		try {
			System.out.println(codeManagerService.getContentsPgmNum(contentsTbl));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
