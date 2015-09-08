package kr.co.d2net.restful;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:/spring/spring.cfg.xml",
		"classpath:/spring/spring.oxm.xml",
		"classpath:/spring/spring.dao.xml"
})
public class BaseRestConfig {
	@Autowired
	private ApplicationContext context;

	public ApplicationContext getContext() {
		return context;
	}
}
