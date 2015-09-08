package kr.co.d2net.task.diagram;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlFileService;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.DairyOrderTbl;
import kr.co.d2net.dto.xml.internal.Pgm;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.service.DairyOrderManagerService;
import kr.co.d2net.task.Worker;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("dailyDiagramXmlWorker")
public class DailyDiagramXmlWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DairyOrderManagerService dairyOrderManagerService;
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private XmlFileService xmlFileService;

	public void work() {

		logger.info("DairyDiagramXmlWrite START! ");
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
					pgm.setRerunClassfication(dairyOrderTbl.getRerunClassification());

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
					pgm.setRerunClassfication(dairyOrderTbl.getRerunClassification());
					
					workflow_2tv.addPgmList(pgm);
				}
			}
			
			String xmlLoc = messageSource.getMessage("live.ingest.xml", null, Locale.KOREA);
		
			// 1TV 용
			xmlFileService.StringToFile(xmlStream.toXML(workflow_1tv), xmlLoc+File.separator+path1tv,
					Utility.getTimestamp("yyyyMMdd") + ".xml");

			// 2TV 용
			xmlFileService.StringToFile(xmlStream.toXML(workflow_2tv), xmlLoc+File.separator+path2tv,
					Utility.getTimestamp("yyyyMMdd") + ".xml");
			
			// 1TV & 2TV log append
			xmlFileService.StringToFile(xmlStream.toXML(workflow_1tv), xmlLoc+File.separator+path1tv+File.separator+"log",
					Utility.getTimestamp("yyyyMMddHHmmss") + ".xml");
			xmlFileService.StringToFile(xmlStream.toXML(workflow_2tv), xmlLoc+File.separator+path2tv+File.separator+"log",
					Utility.getTimestamp("yyyyMMddHHmmss") + ".xml");

		} catch (Exception e) {
			logger.error("DairyDiagramXmlWrite error", e);
		}

		logger.info("DairyDiagramXmlWrite END! ");

	}
}
