package kr.co.d2net.task.diagram;

import java.util.HashMap;
import java.util.Map;

import kr.co.d2net.task.Worker;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DailyDiagramXmlJob extends QuartzJobBean implements
		StatefulJob {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static volatile Map<String, String> confirmDate = new HashMap<String, String>();

	private Worker worker;

	private ApplicationContext ctx;
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.ctx = applicationContext;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		worker = (Worker) ctx.getBean("dailyDiagramXmlWorker");
		worker.work();

	}
	 
}
