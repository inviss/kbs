package kr.co.d2net.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dao.DairyOrderDao;
import kr.co.d2net.dto.DairyOrderTbl;
import kr.co.d2net.dto.xml.meta.Node;
import kr.co.d2net.dto.xml.meta.Nodes;
import kr.co.d2net.dto.xml.meta.Properties;
import kr.co.d2net.task.diagram.DailyDiagramXmlJob;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service(value = "dairyorderservice")
public class DairyOrderManagerServiceImpl implements DairyOrderManagerService {

	private static Log logger = LogFactory.getLog(DairyOrderManagerServiceImpl.class);

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DairyOrderDao dairyOrderDao;

	@Autowired
	private XmlStream xmlStream;

	@Override
	public void insertDairyOrderService(DairyOrderTbl dairyOrderTbl)
			throws ServiceException {
		dairyOrderDao.insertDairyOrder(dairyOrderTbl);
	}

	@Override
	public void deleteDaireyOrderservice(DairyOrderTbl dairyOrderTbl)
			throws ServiceException {
		/**
		 * 처음 입력시 한번만 초기화 과정이 들어감.
		 * 채널(channelCode) & 방송일(runningDate) 기준 으로 전체 삭제 한번(20120106)
		 * 방송일(runningDate) 기준으로 전체 삭제 한번(20120216)
		 */
		dairyOrderDao.deleteDairyOrder(dairyOrderTbl);
	}
	@Override
	public DairyOrderTbl getDairyOrderService(Map<String, Object> params)
			throws ServiceException {
		return dairyOrderDao.getDairyOrderTbl(params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DairyOrderTbl> findDairyOrderService(Map<String, Object> params)
			throws ServiceException {
		List<DairyOrderTbl> dairyOrderList = dairyOrderDao
				.findDairyOrderTbl(params);
		return (dairyOrderList == null) ? Collections.EMPTY_LIST
				: dairyOrderList;
	}

	@Override
	public boolean connectToMetaHubRestFul_dairy(Map<String, Object> element)
			throws ServiceException {
		String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);

		Map<String, Object> params = new HashMap<String, Object>();

		/*
		 * 확정일자를 받아와서 이전 변경시간과 다르다면 일일운행정보를 갱신한다.
		 */
		String method = messageSource.getMessage("meta.system.last.confirm", null, Locale.KOREA);
		String URL = domain + method;
		
		params.put("running_date_equals", element.get("running_date_equals"));
		if(logger.isInfoEnabled()) {
			logger.info("DairyOrder daykind: "+element.get("daykind")+", running_date_equals: "+element.get("running_date_equals"));
		}

		// "20111204"
		String rtnValue = "";
		String[] channels = null;
		if(element.containsKey("channel_code_equals")) {
			channels = new String[]{(String)element.get("channel_code_equals")};
		} else {
			channels = new String[]{"11", "12"};
		}
			
		try {
			Calendar aDate = Calendar.getInstance();
			// 매일 새벽 1시 30분, 50분에는 confirmDate를 초기화한다.
			if(aDate.get(Calendar.AM_PM) == 0 && aDate.get(Calendar.HOUR) == 0) {
				if(aDate.get(Calendar.MINUTE) >= 20 && aDate.get(Calendar.MINUTE) <= 40)
					DailyDiagramXmlJob.confirmDate.clear();
			}
			for(String channel : channels) {
				
				// 2013.02.05 추가
				params.put("service_type_equals", "mh_daily_running");
				params.put("pids", "last_confirm_date");
				params.put("channel_code_equals", channel);
				params.put("date_equals", element.get("running_date_equals"));

				String confirmDate = null;
				HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
				rtnValue = httpRequestService.findData(URL, Utility.convertNameValue(params));
				//rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));
				if(logger.isDebugEnabled()) {
					logger.debug(rtnValue);
				}
				confirmDate = forwardMetaXml(rtnValue, confirmDate, channel);
				
				if(StringUtils.isNotBlank(confirmDate)) {

					boolean change = false;
					if(StringUtils.isBlank(DailyDiagramXmlJob.confirmDate.get(element.get("daykind")+channel))) {
						change = true;
						if(logger.isInfoEnabled()) {
							logger.info("################## first changed!! ####################");
						}
					} else {
						Timestamp oldT = Utility.getTimestamp(DailyDiagramXmlJob.confirmDate.get(element.get("daykind")+channel), "yyyyMMddHHmmss");
						Timestamp newT = Utility.getTimestamp(confirmDate, "yyyyMMddHHmmss");
						logger.info("oldT.getTime() - newT.getTime()====>"+(oldT.getTime() - newT.getTime()));
						if((oldT.getTime() - newT.getTime()) < 0) change = true;
						
						if(change) {
							if(logger.isInfoEnabled()) {
								logger.info("################## confirm date change start!! ####################");
								logger.info("channel: "+channel+"[old: "+DailyDiagramXmlJob.confirmDate.get(element.get("daykind")+channel)+", new: "+confirmDate+"]");
							}
						} else {
							if(logger.isInfoEnabled()) {
								logger.info("################## confirm date equals ####################");
								logger.info("channel: "+channel+"[old: "+DailyDiagramXmlJob.confirmDate.get(element.get("daykind")+channel)+", new: "+confirmDate+"]");
							}
						}
					}
					
					if(change) {
						method = messageSource.getMessage("meta.system.search.dairy", null, Locale.KOREA);
						String URL2 = domain + method;

						params.clear();
						params.put("channel_code_equals", channel);
						params.put("running_date_equals", element.get("running_date_equals")); // 금일
						params.put("broadcast_event_kind_equals", element.get("broadcast_event_kind_equals")); // broadcast_event_kind.
						
						if(logger.isDebugEnabled()) {
							logger.debug("daily["+channel+"] url: "+URL2+"?channel_code_equals="+channel+"&running_date_equals="+element.get("running_date_equals")+"&broadcast_event_kind_equals="+element.get("broadcast_event_kind_equals"));
						}

						httpRequestService = HttpRequestServiceImpl.getInstance();
						rtnValue = httpRequestService.findData(URL2, Utility.convertNameValue(params));
						//Nodes nodes = httpRequestService.findData(URL2, Utility.convertNameValue(params), Nodes.class);
						//rtnValue = Utility.connectHttpPostMethod(URL2, Utility.convertNameValue(params));
						if(logger.isDebugEnabled()) {
							logger.debug("daily["+element.get("daykind")+channel+"] rtnValue: "+rtnValue);
						}
						forwardMetaXml(rtnValue, confirmDate, channel);

						DailyDiagramXmlJob.confirmDate.put(element.get("daykind")+channel, confirmDate);
						
						if(change) {
							if(logger.isInfoEnabled()) {
								logger.info("################## confirm date change completed!! ####################");
							}
						}
					}
				} else {
					if(logger.isInfoEnabled()) {
						logger.info("################## confirm date null !! ####################");
						logger.info("old: "+DailyDiagramXmlJob.confirmDate.get(element.get("daykind")+channel)+", new: "+confirmDate);
					}
				}
			}

		} catch (Exception e) {
			logger.error("connectToMetaHubRestFul_dairy :" + e);
		}
		return false;
	}

	@Override
	public String forwardMetaXml(String xml, String confirmDate, String channel) throws ServiceException {

		Nodes nodes = (Nodes) xmlStream.fromXML(xml);
		Map<String,Object> element = new HashMap<String,Object>();
		int cnt=0;
		for (Node node : nodes.getNodeList()) {
			List<Properties> propertyList = node.getProperties();

			DairyOrderTbl bean = new DairyOrderTbl();
			for (Properties properties : propertyList) {
				if (properties.getType().equals("CODE")||properties.getType().equals("REFERENCE")||properties.getType().equals("DATE")) {
					Utility.setValue(bean, properties.getPid(), properties.getValue());
				} else {
					Utility.setValue(bean, properties.getPid(), properties.getEleValue());
				}
			}

			if(StringUtils.isBlank(confirmDate) && StringUtils.isNotBlank(bean.getLastConfirmDate())) {
				return bean.getLastConfirmDate();
			} else {
				if(logger.isDebugEnabled()) {
//					logger.debug("bean.getRunningDate()="+bean.getRunningDate());
//					logger.debug("bean.getRunningStartTime()="+bean.getRunningStartTime());
//					logger.debug("bean.getRunningEndTime()"+bean.getRunningEndTime());
//					logger.debug("bean.getAudioModeMain()"+bean.getAudioModeMain());
				}
				if(StringUtils.isNotBlank(bean.getChannelCode())
						&&StringUtils.isNotBlank(bean.getRunningDate())
						&&StringUtils.isNotBlank(bean.getRunningStartTime())){

					if(cnt==0) deleteDaireyOrderservice(bean);
					if(!element.containsKey(bean.getChannelCode()+bean.getRunningDate()+bean.getRunningStartTime())){
						
						// 2013.02.22 오디오 모드 추가
						if(logger.isDebugEnabled()) {
							logger.debug("program_id: "+bean.getProgramId());
							logger.debug("audio_mode_main: "+bean.getAudioModeMain());
							logger.debug("descriptive_video_service_yn: "+bean.getDescriptiveVideoServiceYn());
						}
						bean.setDescriptiveVideoServiceYn(StringUtils.defaultIfEmpty(bean.getDescriptiveVideoServiceYn(), "N"));
						if(bean.getDescriptiveVideoServiceYn().equals("Y")) {
							bean.setAudioModeMain("Comment");
						} else {
							bean.setAudioModeMain(StringUtils.defaultIfEmpty(bean.getTransmissionAudioModeMain(), StringUtils.defaultIfEmpty(bean.getAudioModeMain(), "Stereo")));
						}
						
						try {
							Calendar temp = Calendar.getInstance();
							
							String ymd = bean.getRunningDate();
							String time = bean.getRunningEndTime();
							
							String year = ymd.substring(0,4);
							String month = ymd.substring(4,6);
							String day = ymd.substring(6);
							String hour = time.substring(0,2);
							String minute = time.substring(2,4);
							String sec = time.substring(4,6);
							
							temp.set(Calendar.YEAR, Integer.valueOf(year));
							temp.set(Calendar.MONTH, Integer.valueOf(month)-1);
							temp.set(Calendar.DATE, Integer.valueOf(day));
							temp.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
							temp.set(Calendar.MINUTE, Integer.valueOf(minute));
							temp.set(Calendar.SECOND, Integer.valueOf(sec));
							
							bean.setEndDatetime(new Timestamp(temp.getTimeInMillis()));
						} catch (Exception e) {
							logger.error("running date: "+bean.getRunningDate()+", end time: "+bean.getRunningEndTime());
							logger.error("End Datetime parsing error", e);
						}
						
						insertDairyOrderService(bean);
					}

					element.put(bean.getChannelCode()+bean.getRunningDate()+bean.getRunningStartTime(),"");
					++cnt;
				}
			}
		}
		element.clear();

		return "";
	}

	public String forwardMetaXml(Nodes nodes, String confirmDate, String channel) throws ServiceException {

		Map<String,Object> element = new HashMap<String,Object>();
		int cnt=0;
		for (Node node : nodes.getNodeList()) {
			List<Properties> propertyList = node.getProperties();

			DairyOrderTbl bean = new DairyOrderTbl();
			for (Properties properties : propertyList) {
				if (properties.getType().equals("CODE")||properties.getType().equals("REFERENCE")||properties.getType().equals("DATE")) {
					Utility.setValue(bean, properties.getPid(), properties.getValue());
				} else {
					Utility.setValue(bean, properties.getPid(), properties.getEleValue());
				}
			}

			if(StringUtils.isBlank(confirmDate) && StringUtils.isNotBlank(bean.getLastConfirmDate())) {
				return bean.getLastConfirmDate();
			} else {
				if(StringUtils.isNotBlank(bean.getChannelCode())
						&&StringUtils.isNotBlank(bean.getRunningDate())
						&&StringUtils.isNotBlank(bean.getRunningStartTime())){

					if(cnt==0) deleteDaireyOrderservice(bean);
					if(!element.containsKey(bean.getChannelCode()+bean.getRunningDate()+bean.getRunningStartTime())){
						
						// 2013.02.22 오디오 모드 추가
						if(logger.isDebugEnabled()) {
							logger.debug("program_id: "+bean.getProgramId());
							logger.debug("audio_mode_main: "+bean.getAudioModeMain());
							logger.debug("descriptive_video_service_yn: "+bean.getDescriptiveVideoServiceYn());
						}
						bean.setDescriptiveVideoServiceYn(StringUtils.defaultIfEmpty(bean.getDescriptiveVideoServiceYn(), "N"));
						if(bean.getDescriptiveVideoServiceYn().equals("Y")) {
							bean.setAudioModeMain("Comment");
						} else {
							bean.setAudioModeMain(StringUtils.defaultIfEmpty(bean.getTransmissionAudioModeMain(), StringUtils.defaultIfEmpty(bean.getAudioModeMain(), "Stereo")));
						}
						
						try {
							Calendar temp = Calendar.getInstance();
							
							String ymd = bean.getRunningDate();
							String time = bean.getRunningEndTime();
							
							String year = ymd.substring(0,4);
							String month = ymd.substring(4,6);
							String day = ymd.substring(6);
							String hour = time.substring(0,2);
							String minute = time.substring(2,4);
							String sec = time.substring(4,6);
							
							temp.set(Calendar.YEAR, Integer.valueOf(year));
							temp.set(Calendar.MONTH, Integer.valueOf(month)-1);
							temp.set(Calendar.DATE, Integer.valueOf(day));
							temp.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
							temp.set(Calendar.MINUTE, Integer.valueOf(minute));
							temp.set(Calendar.SECOND, Integer.valueOf(sec));
							
							bean.setEndDatetime(new Timestamp(temp.getTimeInMillis()));
						} catch (Exception e) {
							logger.error("running date: "+bean.getRunningDate()+", end time: "+bean.getRunningEndTime());
							logger.error("End Datetime parsing error", e);
						}
						
						insertDairyOrderService(bean);
					}

					element.put(bean.getChannelCode()+bean.getRunningDate()+bean.getRunningStartTime(),"");
					++cnt;
				}
			}
		}
		element.clear();

		return "";
	}


}
