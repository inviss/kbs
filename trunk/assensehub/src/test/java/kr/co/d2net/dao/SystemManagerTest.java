package kr.co.d2net.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

public class SystemManagerTest extends BaseDaoConfig {
	
	private static Log logger = LogFactory.getLog(SystemManagerTest.class);
	
	@Test
	public void thisWeek() {
		logger.debug("this test");
	}
	
	@Test
	@Transactional
	public void thisTranWeek() {
		logger.debug("this transaction");
	}
	
	@Ignore
	@Test
	public void thisIgnoreWeek() {
		logger.debug("this ignore");
	}
}
