package kr.co.d2net.task.diagram;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlFileService;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.DairyOrderTbl;
import kr.co.d2net.dto.xml.internal.Pgm;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.service.DairyOrderManagerService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class DailyDiagramXmlWrite {
	private static Log logger = LogFactory.getLog(DailyDiagramCheck.class);

	private ExecutorService xmlWrite = Executors.newSingleThreadExecutor();

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private XmlFileService xmlFileService;
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private DairyOrderManagerService dairyOrderManagerService;
	
	private volatile static boolean exec = true;

	public void start() {
		xmlWrite.execute(new CheckThread());
		if (logger.isInfoEnabled()) {
			logger.info("xmlWrite Thread Start!!");
		}
	}

	class CheckThread implements Runnable {
		/**
		 * 일일운행표 상에서 데이타를 가지고 오는 경우.
		 */
		@Override
		public void run(){
			/*
			while (exec) {
				try {
					logger.debug("DairyDiagramXmlWrite START! ");
					String path1tv = "1TV";
					String path2tv = "2TV";

					// LiveIngest에 주기적으로 xml 생성
					try {
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("sdate", Utility.getDate(Utility.getTimestamp(), "yyyyMMdd"));
						params.put("edate", Utility.getDate(Utility.getTimestamp(), "yyyyMMdd"));
						List<DairyOrderTbl> dairyOrderTbls = dairyOrderManagerService.findDairyOrderService(params);
						Workflow workflow_1tv = new Workflow();
						Workflow workflow_2tv = new Workflow();

						Pgm pgm = null;
						for (DairyOrderTbl dairyOrderTbl : dairyOrderTbls) {
							if(dairyOrderTbl.getChannelName().equals("1TV")){
								pgm = new Pgm();
								pgm.setPgmGrpCd(StringUtils.isEmpty(dairyOrderTbl.getGroupCode())?"":dairyOrderTbl.getGroupCode());
								pgm.setPgmCd(StringUtils.isEmpty(dairyOrderTbl.getProgramCode())?"":dairyOrderTbl.getProgramCode());
								pgm.setPgmId(StringUtils.isEmpty(dairyOrderTbl.getProgramId())?"":dairyOrderTbl.getProgramId());
								pgm.setPgmNm(StringUtils.isEmpty(dairyOrderTbl.getProgramTitle())?"":dairyOrderTbl.getProgramTitle());
								pgm.setStartTime(StringUtils.isEmpty(dairyOrderTbl.getRunningStartTime())?"":
									dairyOrderTbl.getRunningStartTime().substring(0, 2)+":"+
									dairyOrderTbl.getRunningStartTime().substring(2, 4)+":"+
									dairyOrderTbl.getRunningStartTime().substring(4, 6)
										);
								pgm.setEndTime(
										StringUtils.isEmpty(dairyOrderTbl.getRunningEndTime())?"":
											dairyOrderTbl.getRunningEndTime().substring(0, 2)+":"+
											dairyOrderTbl.getRunningEndTime().substring(2, 4)+":"+
											dairyOrderTbl.getRunningEndTime().substring(4, 6)
										);
								pgm.setRegYn(StringUtils.isEmpty(dairyOrderTbl.getRecyn())?"N":dairyOrderTbl.getRecyn());
								pgm.setVdQlty(dairyOrderTbl.getProductionVideoQuality());

								workflow_1tv.addPgmList(pgm);
							}else if(dairyOrderTbl.getChannelName().equals("2TV")){
								pgm = new Pgm();
								pgm.setPgmGrpCd(StringUtils.isEmpty(dairyOrderTbl.getGroupCode())?"":dairyOrderTbl.getGroupCode());
								pgm.setPgmCd(StringUtils.isEmpty(dairyOrderTbl.getProgramCode())?"":dairyOrderTbl.getProgramCode());
								pgm.setPgmId(StringUtils.isEmpty(dairyOrderTbl.getProgramId())?"":dairyOrderTbl.getProgramId());
								pgm.setPgmNm(StringUtils.isEmpty(dairyOrderTbl.getProgramTitle())?"":dairyOrderTbl.getProgramTitle());
								pgm.setStartTime(StringUtils.isEmpty(dairyOrderTbl.getRunningStartTime())?"":
									dairyOrderTbl.getRunningStartTime().substring(0, 2)+":"+
									dairyOrderTbl.getRunningStartTime().substring(2, 4)+":"+
									dairyOrderTbl.getRunningStartTime().substring(4, 6)
										);
								pgm.setEndTime(
										StringUtils.isEmpty(dairyOrderTbl.getRunningEndTime())?"":
											dairyOrderTbl.getRunningEndTime().substring(0, 2)+":"+
											dairyOrderTbl.getRunningEndTime().substring(2, 4)+":"+
											dairyOrderTbl.getRunningEndTime().substring(4, 6)
										);
								pgm.setRegYn(StringUtils.isEmpty(dairyOrderTbl.getRecyn())?"N":dairyOrderTbl.getRecyn());
								pgm.setVdQlty(dairyOrderTbl.getProductionVideoQuality());
								workflow_2tv.addPgmList(pgm);
							}
						}

						String xmlLoc = messageSource.getMessage("live.ingest.xml", null,
								Locale.KOREA);
					
						// 1TV 용
						xmlFileService.StringToFile(xmlStream.toXML(workflow_1tv), xmlLoc+File.separator+path1tv,
								Utility.getTimestamp("yyyyMMdd") + ".xml");

						// 1TV 용
						xmlFileService.StringToFile(xmlStream.toXML(workflow_2tv), xmlLoc+File.separator+path2tv,
								Utility.getTimestamp("yyyyMMdd") + ".xml");

					} catch (Exception e) {
						e.printStackTrace();
					}

					logger.debug("DairyDiagramXmlWrite END! ");
				} catch (Exception e) {
					logger.error("CheckThread Error", e);
				}
				try {
					Thread.sleep(10000L);
				} catch (Exception e) {
				}
				
			}
			*/
		}

		/**
		 * 주간편성표 데이타로 XML 데이타를 만듬.
		 */
		/*
		@Override
		public void run() {
			while (exec) {
				try {
					logger.debug("DairyDiagramXmlWrite START! ");
					String path1tv = "1TV";
					String path2tv = "2TV";

					// LiveIngest에 주기적으로 xml 생성
					try {

						Map<String,Object> params = new HashMap<String,Object>();
						params.put("sdate", Utility.getDate(Utility.getTimestamp(), "yyyyMMdd"));
						params.put("edate", Utility.getDate(Utility.getTimestamp(), "yyyyMMdd"));
						List<WeekSchTbl> weekSchTbls =  weekSchManagerService.findWeekSchTbl(params);
						Workflow workflow_1tv = new Workflow();
						Workflow workflow_2tv = new Workflow();

						Pgm pgm = null;

						for (WeekSchTbl weekSchTbl : weekSchTbls) {
							if(weekSchTbl.getChannelName().equals("1TV")){
								pgm = new Pgm();
								pgm.setGroupCode(StringUtils.isEmpty(weekSchTbl.getGroupCode())?"":weekSchTbl.getGroupCode());
								pgm.setPgmCd(StringUtils.isEmpty(weekSchTbl.getProgramCode())?"":weekSchTbl.getProgramCode());
								pgm.setPgmId(StringUtils.isEmpty(weekSchTbl.getProgramId())?"":weekSchTbl.getProgramId());
								pgm.setPgmNm(StringUtils.isEmpty(weekSchTbl.getProgramTitle())?"":weekSchTbl.getProgramTitle());
								pgm.setStartTime(StringUtils.isEmpty(weekSchTbl.getProgramPlannedStartTime())?"":
									weekSchTbl.getProgramPlannedStartTime().substring(0, 2)+":"+
									weekSchTbl.getProgramPlannedStartTime().substring(2, 4)+":"+
									weekSchTbl.getProgramPlannedStartTime().substring(4, 6)
										);
								pgm.setEndTime(
										StringUtils.isEmpty(weekSchTbl.getProgramPlannedEndTime())?"":
											weekSchTbl.getProgramPlannedEndTime().substring(0, 2)+":"+
											weekSchTbl.getProgramPlannedEndTime().substring(2, 4)+":"+
											weekSchTbl.getProgramPlannedEndTime().substring(4, 6)
										);
								pgm.setRegYn(StringUtils.isEmpty(weekSchTbl.getRecyn())?"N":weekSchTbl.getRecyn());
								pgm.setVdQlty(weekSchTbl.getProductionVideoQuality());

								workflow_1tv.addPgmList(pgm);
							}else if(weekSchTbl.getChannelName().equals("2TV")){
								pgm = new Pgm();
								pgm.setGroupCode(StringUtils.isEmpty(weekSchTbl.getGroupCode())?"":weekSchTbl.getGroupCode());
								pgm.setPgmCd(StringUtils.isEmpty(weekSchTbl.getProgramCode())?"":weekSchTbl.getProgramCode());
								pgm.setPgmId(StringUtils.isEmpty(weekSchTbl.getProgramId())?"":weekSchTbl.getProgramId());
								pgm.setPgmNm(StringUtils.isEmpty(weekSchTbl.getProgramTitle())?"":weekSchTbl.getProgramTitle());
								pgm.setStartTime(StringUtils.isEmpty(weekSchTbl.getProgramPlannedStartTime())?"":
									weekSchTbl.getProgramPlannedStartTime().substring(0, 2)+":"+
									weekSchTbl.getProgramPlannedStartTime().substring(2, 4)+":"+
									weekSchTbl.getProgramPlannedStartTime().substring(4, 6)
										);
								pgm.setEndTime(
										StringUtils.isEmpty(weekSchTbl.getProgramPlannedEndTime())?"":
											weekSchTbl.getProgramPlannedEndTime().substring(0, 2)+":"+
											weekSchTbl.getProgramPlannedEndTime().substring(2, 4)+":"+
											weekSchTbl.getProgramPlannedEndTime().substring(4, 6)
										);
								pgm.setRegYn(StringUtils.isEmpty(weekSchTbl.getRecyn())?"N":weekSchTbl.getRecyn());
								pgm.setVdQlty(weekSchTbl.getProductionVideoQuality());
								workflow_2tv.addPgmList(pgm);
							}
						}

						String xmlLoc = messageSource.getMessage("live.ingest.xml", null,
								Locale.KOREA);


						// 1TV 용
						xmlFileService.StringToFile(xmlStream.toXML(workflow_1tv), xmlLoc+File.separator+path1tv,
								Utility.getTimestamp("yyyyMMdd") + ".xml");

						// 1TV 용
						xmlFileService.StringToFile(xmlStream.toXML(workflow_2tv), xmlLoc+File.separator+path2tv,
								Utility.getTimestamp("yyyyMMdd") + ".xml");


					} catch (Exception e) {
						e.printStackTrace();
					}

					logger.debug("DairyDiagramXmlWrite END! ");
				} catch (Exception e) {
					logger.error("CheckThread Error", e);
				}
				try {
					Thread.sleep(5000L);
				} catch (Exception e) {
				}
			}
		}
		*/
	}

	

	public void stop() throws Exception{
		exec = false;
		
		Thread.sleep(1000L);
		if (!xmlWrite.isShutdown()) {
			xmlWrite.shutdownNow();
			if (logger.isInfoEnabled()) {
				logger.info("xmlWrite Exec Thread shutdown now!!");
			}
		}
	}
}
