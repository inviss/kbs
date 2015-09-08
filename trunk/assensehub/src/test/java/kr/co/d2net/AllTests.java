package kr.co.d2net;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	kr.co.d2net.dao.SystemManagerTest.class,
	kr.co.d2net.xml.MetaHubParseTest.class
})
public class AllTests {
	
	public void test(){
	}
}
