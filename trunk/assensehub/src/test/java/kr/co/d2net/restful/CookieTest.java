package kr.co.d2net.restful;

import javax.servlet.http.Cookie;

import kr.co.d2net.dao.BaseDaoConfig;

import org.junit.Test;

public class CookieTest extends BaseDaoConfig {
	
	@Test
	public void cookieTest(){
		System.out.print("asdfasf");
		
		Cookie cookie = new Cookie("id", "test");
	}

}
