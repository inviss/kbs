package kr.co.d2net.commons;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
	private static SpringApplication springApplication;
	private static ApplicationContext ctx;
	
	private SpringApplication() {
		if(ctx == null)
			ctx = new ClassPathXmlApplicationContext(
				new String[] {
						"/spring/applicationContext.xml"
				}
			);
	}
	
	public static SpringApplication getInstance() {
		if(springApplication == null)
			springApplication = new SpringApplication();
		
		return springApplication;
	}
	
	public Object get(String key) throws BeansException {
		return ctx.getBean(key);
	}
	
	
}
