package kr.co.d2net.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dao.WeekSchDao;
import kr.co.d2net.dto.WeekSchTbl;
import kr.co.d2net.dto.xml.meta.Node;
import kr.co.d2net.dto.xml.meta.Nodes;
import kr.co.d2net.dto.xml.meta.Properties;
import kr.co.d2net.task.diagram.WeekDiagramRequestJob;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service(value = "weekSchManagerService")
public class WeekSchManagerServiceImpl implements WeekSchManagerService {

	private static Log logger = LogFactory.getLog(WeekSchManagerServiceImpl.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private WeekSchDao weekSchDao;

	@Autowired
	private XmlStream xmlStream;

	@Override
	public WeekSchTbl getWeekSchTbl(Map<String, Object> weekSchTbl)
	throws ServiceException {
		// TODO Auto-generated method stub
		return weekSchDao.getWeekSch(weekSchTbl);
	}

	@Override
	public void deleteWeekSchTbl(WeekSchTbl weekSchTbl)
	throws ServiceException {
		/**
		 * 처음 입력시 한번만 초기화 과정이 들어감.
		 * 채널(channelCode) & 방송일(runningDate) 기준 으로 전체 삭제 한번(20120106)
		 * 방송일(runningDate) 기준으로 전체 삭제 한번(20120216)
		 */
		weekSchDao.deleteWeekSch(weekSchTbl);
	}

	@Override
	public void insertWeekSchTbl(WeekSchTbl weekSchTbl) throws ServiceException {

		weekSchDao.insertWeekSch(weekSchTbl);
	}

	@Override
	public List<WeekSchTbl> findWeekSchTbl(Map<String, Object> params)
	throws ServiceException {
		return weekSchDao.findWeekSchs(params);
	}

	@Override
	public boolean connectToMetaHubRestFul_weekly(Map<String, Object> element)
	throws ServiceException {
		String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
		String method = messageSource.getMessage("meta.system.search.weekly", null, Locale.KOREA);

		String channel_list 	= messageSource.getMessage("meta.system.channel.list",null, Locale.KOREA);
		String local_list 		= messageSource.getMessage("meta.system.local.list", null, Locale.KOREA);
		String change_method 	= messageSource.getMessage("meta.system.last.changed",null, Locale.KOREA);

		//params.put("program_planned_date_morethan", days[0]); // 금주
		//params.put("program_planned_date_lessthan", days[1]); // 다음주
		
		Map<String, Object> params = new HashMap<String, Object>();

		String rtnValue = "";
		String[] channels = channel_list.split(",");
		String[] locals = local_list.split(",");

		String URL = "";
		for(String channelCode : channels) {
			params.clear();

			// 지역코드가 본사가 아닌경우에는 1TV, 2TV, 1Radio 의 주간편성표만 조회한다.
			if(channelCode.equals("11") || channelCode.equals("12") || channelCode.equals("21")) {
				for(String localCode : locals) {

					// 지역코드별 채널변경일자 조회
					try {
						URL = domain + change_method;
						params.put("channel_code", channelCode); 					// 채널코드
						params.put("pls_code", localCode);							// 지역국 코드
						params.put("weekly_choice", element.get("weekly_choice"));	// -1: 지난주, 0:이번주, 1: 다음주

						String confirmDate = null;
						HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
						rtnValue = httpRequestService.findData(URL, Utility.convertNameValue(params));
						//rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));
						if(logger.isInfoEnabled()) {
							logger.info("connectToMetaHubRestFul_weekly week("+element.get("weekly_choice")+") rtnValue: "+rtnValue);
						}

						if(StringUtils.isNotBlank(rtnValue)) {
							confirmDate = forwardMetaXml(rtnValue, confirmDate, channelCode, localCode);
							if(logger.isInfoEnabled()) {
								logger.info("channelCd: "+channelCode+", localCode: "+localCode+", confirmDate: "+confirmDate);
							}
							if(StringUtils.isNotBlank(confirmDate)) {
								if(logger.isInfoEnabled()) {
									logger.info("weekly_choice: "+element.get("weekly_choice")+channelCode+localCode);
								}
								boolean change = false;
								if(StringUtils.isBlank(WeekDiagramRequestJob.confirmDate.get(element.get("weekly_choice")+channelCode+localCode))) {
									change = true;
									if(logger.isInfoEnabled()) {
										logger.info("################## first changed!! ####################");
									}
								} else {
									Timestamp oldT = Utility.getTimestamp(WeekDiagramRequestJob.confirmDate.get(element.get("weekly_choice")+channelCode+localCode), "yyyyMMddHHmmss");
									Timestamp newT = Utility.getTimestamp(confirmDate, "yyyyMMddHHmmss");
									logger.debug("oldT.getTime() - newT.getTime()====>"+(oldT.getTime() - newT.getTime()));
									if((oldT.getTime() - newT.getTime()) < 0) change = true;

									if(change) {
										if(logger.isInfoEnabled()) {
											logger.info("################## confirm date change start!! ####################");
											logger.info("channel: "+channelCode+"("+localCode+")"+"[old: "+WeekDiagramRequestJob.confirmDate.get(element.get("weekly_choice")+channelCode+localCode)+", new: "+confirmDate+"]");
										}
									} else {
										if(logger.isInfoEnabled()) {
											logger.info("################## confirm date equals ####################");
											logger.info("channel: "+channelCode+"("+localCode+")"+"[old: "+WeekDiagramRequestJob.confirmDate.get(element.get("weekly_choice")+channelCode+localCode)+", new: "+confirmDate+"]");
										}
									}
								}

								if(change) {
									URL = domain + method;

									params.remove("channel_code");
									params.remove("pls_code");
									params.put("date_morethan",element.get("date_morethan")); // 금주 일요일
									params.put("date_lessthan",element.get("date_lessthan")); // 다음주 월요일
									params.put("double_programming_type_code_equals","1");  // 이중편성중 1안 만 수용(2012-05-17)
									params.put("channel_code_equals", channelCode);
									params.put("programming_local_station_code_equals", localCode);
									params.put("handler", "WEEKLYDATE");

									if(logger.isInfoEnabled()) {
										logger.info("week_request_url: "+URL+"?handler=WEEKLYDATE&date_morethan="+element.get("date_morethan")
												+"&date_lessthan="+element.get("date_lessthan")+"&channel_code_equals="+channelCode
												+"&programming_local_station_code_equals="+localCode+"&double_programming_type_code_equals=1");
									}
									try {
										httpRequestService = HttpRequestServiceImpl.getInstance();
										rtnValue = httpRequestService.findData(URL, Utility.convertNameValue(params));
										
										//Nodes nodes = httpRequestService.findData(URL, Utility.convertNameValue(params), Nodes.class);
										//rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));
										forwardMetaXml(rtnValue, confirmDate, channelCode, localCode);

										WeekDiagramRequestJob.confirmDate.put(element.get("weekly_choice")+channelCode+localCode, confirmDate);
									} catch (Exception e) {
										logger.error("connectToMetaHubRestFul_weekly" + e);
									}
								}
							}
						}
					} catch (Exception e) {
						logger.error("connectToMetaHubRestFul_weekly" + e);
					}
				}
			} else {

				// 지역코드별 채널변경일자 조회
				try {
					URL = domain + change_method;
					params.put("channel_code", channelCode); 	// 채널코드
					params.put("pls_code", "00");				// 지역국 코드
					params.put("weekly_choice", "0");			// -1: 지난주, 0:이번주, 1: 다음주

					String localCode = "00"; // 본사
					String confirmDate = null;
					
					HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
					rtnValue = httpRequestService.findData(URL, Utility.convertNameValue(params));
					//rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));
					if(logger.isDebugEnabled()) {
						logger.debug("confirm date rtnValue: "+rtnValue);
					}
					confirmDate = forwardMetaXml(rtnValue, confirmDate, channelCode, localCode);
					if(logger.isInfoEnabled()) {
						logger.info("channelCd: "+channelCode+", localCode: "+localCode+", confirmDate: "+confirmDate);
					}
					if(StringUtils.isNotBlank(confirmDate)) {
						boolean change = false;
						if(StringUtils.isBlank(WeekDiagramRequestJob.confirmDate.get(element.get("weekly_choice")+channelCode+localCode))) {
							change = true;
							if(logger.isInfoEnabled()) {
								logger.info("################## first changed!! ####################");
							}
						} else {
							Timestamp oldT = Utility.getTimestamp(WeekDiagramRequestJob.confirmDate.get(element.get("weekly_choice")+channelCode+localCode), "yyyyMMddHHmmss");
							Timestamp newT = Utility.getTimestamp(confirmDate, "yyyyMMddHHmmss");
							logger.info(oldT.getTime() - newT.getTime());
							if((oldT.getTime() - newT.getTime()) < 0) change = true;

							if(change) {
								if(logger.isInfoEnabled()) {
									logger.info("################## confirm date change start!! ####################");
									logger.info("channel: "+channelCode+"("+localCode+")"+"[old: "+WeekDiagramRequestJob.confirmDate.get(element.get("weekly_choice")+channelCode+localCode)+", new: "+confirmDate+"]");
								}
							} else {
								if(logger.isInfoEnabled()) {
									logger.info("################## confirm date equals ####################");
									logger.info("channel: "+channelCode+"("+localCode+")"+"[old: "+WeekDiagramRequestJob.confirmDate.get(element.get("weekly_choice")+channelCode+localCode)+", new: "+confirmDate+"]");
								}
							}
						}

						if(change) {
							URL = domain + method;

							params.remove("channel_code");
							params.remove("pls_code");
							params.put("date_morethan",element.get("date_morethan")); // 월요일
							params.put("date_lessthan",element.get("date_lessthan")); // 일요일
							params.put("double_programming_type_code_equals","1");  // 이중편성중 1안 만 수용(2012-05-17)
							params.put("channel_code_equals", channelCode);
							params.put("programming_local_station_code_equals", localCode);
							params.put("handler", "WEEKLYDATE");
							try {
								if(logger.isInfoEnabled()) {
									logger.info("week_request_url: "+URL+"?handler=WEEKLYDATE&date_morethan="+element.get("date_morethan")
											+"&date_lessthan="+element.get("date_lessthan")+"&channel_code_equals="+channelCode
											+"&programming_local_station_code_equals="+localCode+"&double_programming_type_code_equals=1");
								}
								
								httpRequestService = HttpRequestServiceImpl.getInstance();
								rtnValue = httpRequestService.findData(URL, Utility.convertNameValue(params));
								//rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));
								
								forwardMetaXml(rtnValue, confirmDate, channelCode, localCode);

								WeekDiagramRequestJob.confirmDate.put(element.get("weekly_choice")+channelCode+localCode, confirmDate);
							} catch (Exception e) {
								logger.error("connectToMetaHubRestFul_weekly" + e);
							}
						}
					}
				} catch (Exception e) {
					logger.error("connectToMetaHubRestFul_weekly" + e);
				}
			}
		}
		return false;
	}

	@Override
	public boolean connectToMetaHubRestFul_manualWeekly(Map<String, Object> element)
	throws ServiceException {
		String domain = messageSource.getMessage("meta.system.domain", null,Locale.KOREA);
		String method = messageSource.getMessage("meta.system.search.weekly", null, Locale.KOREA);

		String URL = domain + method;

		Map<String, Object> params = new HashMap<String, Object>();

		String rtnValue = "";

		String channelCode = (String) element.get("channel_code");
		String localCode = (String) element.get("local_code");

		if(logger.isDebugEnabled()) {
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			logger.debug("channelCode: "+channelCode);
			logger.debug("localCode: "+localCode);
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}

		params.clear();

		params.put("date_morethan", element.get("date_morethan")); // 금주 일요일
		params.put("date_lessthan", element.get("date_lessthan")); // 다음주 월요일
		params.put("double_programming_type_code_equals","1");  // 이중편성중 1안 만 수용(2012-05-17)
		params.put("channel_code_equals", channelCode);
		params.put("programming_local_station_code_equals", localCode);
		params.put("handler", "WEEKLYDATE");

		try {
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			rtnValue = httpRequestService.findData(URL, Utility.convertNameValue(params));
			//rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));
			forwardMetaXml(rtnValue, WeekDiagramRequestJob.confirmDate.get(channelCode+localCode), channelCode, localCode);
		} catch (Exception e) {
			logger.error("connectToMetaHubRestFul_weekly: " + e);
		}

		return false;
	}

	@Override
	public String forwardMetaXml(String xml, String confirmDate, String channel, String local) throws ServiceException {
		Nodes nodes = null;
		try {
			nodes = (Nodes) xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error(xml);
			logger.error("xml parsing error", e);
		}

		if(nodes != null) {

			String onAirDate = "";
			//boolean deleted = false;
			Map<String,Object> element = new HashMap<String,Object>();
			for (Node node : nodes.getNodeList()) {
				List<Properties> propertyList = node.getProperties();

				WeekSchTbl bean = new WeekSchTbl();
				bean.setProgrammingLocalStationCode(local);
				for (Properties properties : propertyList) {
					if (properties.getType().equals("CODE")||properties.getType().equals("REFERENCE")
							||properties.getType().equals("DATE")) {
						Utility.setValue(bean, properties.getPid(),properties.getValue());
					} else {
						Utility.setValue(bean, properties.getPid(),properties.getEleValue());
					}
				}

				if(StringUtils.isBlank(confirmDate) && StringUtils.isNotBlank(bean.getLastConfirmDate())) {
					return bean.getLastConfirmDate();
				} else {
					if(bean.getProgramPlannedDate().length() > 8) {
						if(logger.isInfoEnabled()) {
							logger.info("getProgramPlannedDate: "+bean.getProgramPlannedDate());
						}
						bean.setProgramPlannedDate(bean.getProgramPlannedDate().substring(0, 8));
					}

					if(!onAirDate.equals(bean.getProgramPlannedDate())){
						Object o = element.get(bean.getProgramPlannedDate());
						if(o == null) {
							weekSchDao.deleteWeekSch(bean);
							onAirDate = bean.getProgramPlannedDate();
							logger.debug(bean.getChannelCode()+": "+bean.getProgramPlannedDate()+" deleted!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
							
							element.put(bean.getProgramPlannedDate(), "true");
						}
					}
					if(!element.containsKey(bean.getChannelCode()+bean.getProgramPlannedDate()+bean.getProgramPlannedStartTime())){
						// 2013.02.22 오디오 모드 추가
						if(logger.isDebugEnabled()) {
							logger.debug("audio_mode_main: "+bean.getAudioModeMain());
							logger.debug("descriptive_video_service_yn: "+bean.getDescriptiveVideoServiceYn());
						}
						bean.setDescriptiveVideoServiceYn(StringUtils.defaultIfEmpty(bean.getDescriptiveVideoServiceYn(), "N"));
						if(bean.getDescriptiveVideoServiceYn().equals("Y")) {
							bean.setAudioModeMain("Comment");
						} else {
							bean.setAudioModeMain(StringUtils.defaultIfEmpty(bean.getTransmissionAudioModeMain(), StringUtils.defaultIfEmpty(bean.getAudioModeMain(), "Stereo")));
						}
						
						// 프로그램 타이틀이 없을경우 서브 타이틀로 대체 (2013.10.11)
						if(StringUtils.isBlank(bean.getProgramTitle())) {
							bean.setProgramTitle(bean.getProgramSubtitle());
						}
						weekSchDao.insertWeekSch(bean);
					}
				}
			}
		}
		return "";
	}
	
	public String forwardMetaXml(Nodes nodes, String confirmDate, String channel, String local) throws ServiceException {
		if(nodes != null) {

			String onAirDate = "";
			//boolean deleted = false;
			Map<String,Object> element = new HashMap<String,Object>();
			for (Node node : nodes.getNodeList()) {
				List<Properties> propertyList = node.getProperties();

				WeekSchTbl bean = new WeekSchTbl();
				bean.setProgrammingLocalStationCode(local);
				for (Properties properties : propertyList) {
					if (properties.getType().equals("CODE")||properties.getType().equals("REFERENCE")
							||properties.getType().equals("DATE")) {
						Utility.setValue(bean, properties.getPid(),properties.getValue());
					} else {
						Utility.setValue(bean, properties.getPid(),properties.getEleValue());
					}
				}

				if(StringUtils.isBlank(confirmDate) && StringUtils.isNotBlank(bean.getLastConfirmDate())) {
					return bean.getLastConfirmDate();
				} else {
					if(bean.getProgramPlannedDate().length() > 8) {
						if(logger.isInfoEnabled()) {
							logger.info("getProgramPlannedDate: "+bean.getProgramPlannedDate());
						}
						bean.setProgramPlannedDate(bean.getProgramPlannedDate().substring(0, 8));
					}

					if(!onAirDate.equals(bean.getProgramPlannedDate())){
						Object o = element.get(bean.getProgramPlannedDate());
						if(o == null) {
							weekSchDao.deleteWeekSch(bean);
							onAirDate = bean.getProgramPlannedDate();
							logger.debug(bean.getChannelCode()+": "+bean.getProgramPlannedDate()+" deleted!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
							
							element.put(bean.getProgramPlannedDate(), "true");
						}
					}
					if(!element.containsKey(bean.getChannelCode()+bean.getProgramPlannedDate()+bean.getProgramPlannedStartTime())){
						// 2013.02.22 오디오 모드 추가
						if(logger.isDebugEnabled()) {
							logger.debug("audio_mode_main: "+bean.getAudioModeMain());
							logger.debug("descriptive_video_service_yn: "+bean.getDescriptiveVideoServiceYn());
						}
						bean.setDescriptiveVideoServiceYn(StringUtils.defaultIfEmpty(bean.getDescriptiveVideoServiceYn(), "N"));
						if(bean.getDescriptiveVideoServiceYn().equals("Y")) {
							bean.setAudioModeMain("Comment");
						} else {
							bean.setAudioModeMain(StringUtils.defaultIfEmpty(bean.getTransmissionAudioModeMain(), StringUtils.defaultIfEmpty(bean.getAudioModeMain(), "Stereo")));
						}
						
						// 프로그램 타이틀이 없을경우 서브 타이틀로 대체 (2013.10.11)
						if(StringUtils.isBlank(bean.getProgramTitle())) {
							bean.setProgramTitle(bean.getProgramSubtitle());
						}
						weekSchDao.insertWeekSch(bean);
					}
				}
			}
		}
		return "";
	}

}
