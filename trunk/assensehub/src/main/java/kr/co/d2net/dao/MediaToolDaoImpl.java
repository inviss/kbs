package kr.co.d2net.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.CornerTbl;
import kr.co.d2net.dto.xml.aach.Corner;
import kr.co.d2net.dto.xml.aach.Corners;
import kr.co.d2net.dto.xml.internal.Content;
import kr.co.d2net.dto.xml.internal.FindList;
import kr.co.d2net.dto.xml.internal.Pgm;
import kr.co.d2net.dto.xml.internal.Sgm;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.dto.xml.meta.Node;
import kr.co.d2net.dto.xml.meta.Nodes;
import kr.co.d2net.dto.xml.meta.Properties;
import kr.co.d2net.service.HttpRequestService;
import kr.co.d2net.service.HttpRequestServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

@Repository(value = "mediaToolDao")
public class MediaToolDaoImpl implements MediaToolDao {

	private static Log logger = LogFactory.getLog(MediaToolDaoImpl.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private ContentsInstDao contentsInstDao;
	@Autowired
	private CornerDao cornerDao;

	@Override
	public String connectToMetaHubRestFul_weekly(String xml)
			throws DaoNonRollbackException {

		String domain = messageSource.getMessage("meta.system.domain", null,
				Locale.KOREA);
		String method = messageSource.getMessage("meta.system.search.weekly",
				null, Locale.KOREA);

		String URL = domain + method;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("program_planned_date_morethan", Utility.getDateOfWeek(5, 0));  // Utility.getDateOfWeek(2, 0) : 요번주 월요일 일자 (yyyyMMdd)
		params.put("program_planned_date_lessthan", Utility.getDateOfWeek(12, 0));  // Utility.getDateOfWeek(8, 0) : 요번주 일요일 일자 (yyyyMMdd)

		String rtnValue = "";

		try {
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			rtnValue = httpRequestService.findData(URL, Utility.convertNameValue(params));
			//rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));

		} catch (Exception e) {
			logger.error("connectToMetaHubRestFul_weekly" + e);
		}
		return null;
	}

	@Override
	public String connectToMetaHubRestFul_dairy(String xml)
			throws DaoNonRollbackException {
		String domain = messageSource.getMessage("meta.system.domain", null,
				Locale.KOREA);

		String method = messageSource.getMessage("meta.system.search.dairy",
				null, Locale.KOREA);

		String URL = domain + method;
		// "/kbs/api/mh_daily_running/list.xml";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pagesize", "10");
		params.put("pagecount", "10");
		params.put("running_date_equals", Utility.getDate(Utility.getTimestamp(), "yyyyMMdd")); // morethan >

		String rtnValue = "";

		try {
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			rtnValue = httpRequestService.findData(URL, Utility.convertNameValue(params));
			//rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));
			if(logger.isDebugEnabled()) {
				logger.debug("trnValue : " + rtnValue);
			}
		} catch (Exception e) {
			logger.error("connectToMetaHubRestFul_dairy :" + e);
		}
		return null;
	}

	@Override
	public FindList connectToMetaHubRestFul_programNum(Workflow workflow)
			throws DaoNonRollbackException {

		String domain = messageSource.getMessage("meta.system.domain", null,
				Locale.KOREA);
		String method = messageSource.getMessage("meta.system.search.program",
				null, Locale.KOREA);

		String URL = domain + method;

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("count","20");
		if(StringUtils.isNotBlank(workflow.getKeyword()))
			params.put("searchvalue",workflow.getKeyword());
		if(StringUtils.isNotBlank(workflow.getPgmId()))
			params.put("program_id_equals",workflow.getPgmId());

		params.put("pids","program_id,program_title,program_planned_date,group_code,channel_code,program_code");

		if (workflow.getStartDt() != null) {
			logger.debug("workflow.getStartDt():"+workflow.getStartDt());
			logger.debug("workflow.getEndDt():"+workflow.getEndDt());

			params.put("broadcast_planned_date_morethan", Utility.getDate(workflow.getStartDt(),"yyyyMMdd"));
			params.put("broadcast_planned_date_lessthan", Utility.getDate(workflow.getEndDt(),"yyyyMMdd", 1));
		}

		String rtnValue = "";
		try {
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			rtnValue = httpRequestService.findData(URL, Utility.convertNameValue(params));
			//rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));
			logger.debug(rtnValue);	
		} catch (Exception e) {
			logger.error("program num search error", e);
		}

		return forwardProgramInfo(rtnValue);
	}

	@Override
	public FindList connectToMetaHubRestFul_segment(Workflow workflow)
			throws DaoNonRollbackException {

		String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
		String method=messageSource.getMessage("meta.system.search.segment", null, Locale.KOREA);

		String URL = domain + method;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("count", "20");
		if(StringUtils.isNotBlank(workflow.getKeyword()))
			params.put("segment_title_contains",workflow.getKeyword()); // 검색어

		params.put("pids", "segment_title,program_id,segment_id");
		if(StringUtils.isNotBlank(workflow.getPgmId()))
			params.put("program_id_equals",workflow.getPgmId());  // 20120111 수정완료.

		if (workflow.getStartDt() != null) {
			params.put("broadcast_planned_date_morethan",
					Utility.getDate(workflow.getStartDt(), -1, ""));
			params.put("broadcast_planned_date_lessthan",
					Utility.getDate(workflow.getEndDt(), 0, "")); // 방송종료일
		}

		String rtnValue = "";

		try {
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			rtnValue = httpRequestService.findData(URL, Utility.convertNameValue(params));
			//rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));
		} catch (Exception e) {
			logger.error("segment search error", e);
		}
		
		return forwardSegmentInfo(rtnValue);
	}

	@Override
	public FindList connectToMetaHubRestFul_segment_radio(Workflow workflow)
			throws DaoNonRollbackException {

		String domain = messageSource.getMessage("meta.system.domain", null,
				Locale.KOREA);
		String method=messageSource.getMessage("meta.system.search.segment.radio",
				null, Locale.KOREA);

		String URL = domain + method;
		// "/kbs/api/mh_segment_id/list.xml";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("count", "20");
		if(StringUtils.isNotBlank(workflow.getKeyword()))
			params.put("segment_title_contains",workflow.getKeyword()); // 검색어

		params.put("pids", "segment_title,program_id,segment_id");
		if(StringUtils.isNotBlank(workflow.getPgmId()))
			params.put("program_id_equals",workflow.getPgmId());  // 20120111 수정완료.
		
			//추가해야 할 사항 
			//pps_segment_id(오디오 아카이브에서 받아온 세그먼트) : 메타허브에 조회를 해 우리가 사용하는 세그먼트아이디를 받아올때 사용

		if (workflow.getStartDt() != null) {
			params.put("broadcast_planned_date_morethan",
					Utility.getDate(workflow.getStartDt(), -1, ""));
			params.put("broadcast_planned_date_lessthan",
					Utility.getDate(workflow.getEndDt(), 0, "")); // 방송종료일
		}

		String rtnValue = "";

		try {
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			rtnValue = httpRequestService.findData(URL, Utility.convertNameValue(params));
			//rtnValue = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));
		} catch (Exception e) {
			logger.error("segment audio search error", e);
		}
		
		return forwardSegmentInfo(rtnValue);
	}

	@Override
	public FindList findContentsInfo(Workflow workflow)
			throws DaoNonRollbackException {
		FindList conList = new FindList();
		Map<String, Object> params = new HashMap<String, Object>();

		if (workflow.getEqId().startsWith("VT")) {
			params.put("ctiFmt", "101"); // 100: 고해상도 , 200: 저해상도, 300: 오디오
		} else if (workflow.getEqId().startsWith("AT")) {
			params.put("ctiFmt", "3"); // 100 : 고해상도 , 200: 저해상도 , 300: 오디오
		}
		params.put("trimmSte", "N"); // 트링밍 필요 : N
		params.put("pgmNm", workflow.getKeyword());
		params.put("sdate", workflow.getStartDt());
		params.put("edate", (workflow.getEndDt() != null) ? DateUtils.addDays(workflow.getEndDt(), 1) : workflow.getEndDt());
		params.put("channelCd", workflow.getChannelCd()); //2012.02.20 add

		try {
			Content content = null;
			List<ContentsInstTbl> contentsInstTbls = contentsInstDao.findContentsInstSummaryByMediaTool(params);

			for (ContentsInstTbl contentsInstTbl : contentsInstTbls) {
				content = new Content();

				content.setPgmId(contentsInstTbl.getPgmId().toUpperCase());
				content.setPgmNm(contentsInstTbl.getPgmNm());
				content.setCtId(contentsInstTbl.getCtId());
				content.setCtNm(contentsInstTbl.getCtNm());
				content.setCtiFmt(contentsInstTbl.getCtiFmt());
				content.setCtiId(contentsInstTbl.getCtiId());
				content.setFlPath(contentsInstTbl.getFlPath());
				content.setFlExt(contentsInstTbl.getFlExt());
				content.setFlNm(contentsInstTbl.getOrgFileNm());
				content.setBrdDd(contentsInstTbl.getBrdDd());
				content.setChannelCd(contentsInstTbl.getChannelCode());
				content.setPgmCd(contentsInstTbl.getPgmCd().toUpperCase());
				content.setCtCla(contentsInstTbl.getCtCla());
				content.setCtTyp(contentsInstTbl.getCtTyp());
				content.setVdQlty(contentsInstTbl.getVdQlty());
				content.setPgmGrpCd(StringUtils.defaultString(contentsInstTbl.getPgmGrpCd(), contentsInstTbl.getPgmCd()).toUpperCase());
				content.setPgmNum(contentsInstTbl.getPgmNum());
				content.setRegrid(contentsInstTbl.getRegrid());

				/*
				 * 오디오 컨텐츠에 대한 코너 정보가 있다면 리스트 정보를 담아서 전달한다.
				 * 
				 */
				params.put("ctId", contentsInstTbl.getCtId());
				List<CornerTbl> cornerTbls = cornerDao.findCorner(params);
				if(cornerTbls != null && cornerTbls.size() > 0) {
					Corners corners = new Corners();
					for(CornerTbl cornerTbl : cornerTbls) {
						Corner corner = new Corner();
						corner.setSegmentId(cornerTbl.getAudSegmentId());
						corner.setCornerStartTime(String.valueOf(Utility.timeToSec(cornerTbl.getBgnTime())));
						corner.setCornerEndTime(String.valueOf(Utility.timeToSec(cornerTbl.getEndTime())));
						corners.addCornerList(corner);
					}
					content.setCorners(corners);
				}

				conList.addContents(content);
			}
		} catch (Exception e) {
			logger.error("findContentsInfo", e);
		}
		return conList;
	}


	@Override
	public FindList forwardProgramInfo(String xml)
			throws DaoNonRollbackException {
		FindList pgmList = new FindList();
		try {

			Nodes nodes = (Nodes) xmlStream.fromXML(xml);
			for (Node node : nodes.getNodeList()) {
				List<Properties> propertyList = node.getProperties();

				Pgm pgm = new Pgm();
				for (Properties properties : propertyList) {
					if (properties.getPid().equals("program_id")) {
						pgm.setPgmId(properties.getEleValue().toUpperCase());
					} else if(properties.getPid().equals("program_title")){
						pgm.setPgmNm(properties.getEleValue());
					} else if(properties.getPid().equals("program_planned_date")){
						pgm.setOnairDt(Utility.getDate(properties.getEleValue(),"yyyy/MM/dd HH:mm:ss"));
					} else if(properties.getPid().equals("group_code")){
						pgm.setPgmGrpCd(properties.getValue().toUpperCase());
					} else if(properties.getPid().equals("channel_code")){
						pgm.setChannel(properties.getValue());
					} else if(properties.getPid().equals("program_code")){
						pgm.setPgmCd(properties.getValue().toUpperCase());
					}
				}
				pgmList.addPgms(pgm);
			}


		} catch (Exception e) {
			logger.error("forwardProgramInfo", e);
		}

		return pgmList;
	}

	@Override
	public FindList forwardSegmentInfo(String xml)
			throws DaoNonRollbackException {
		FindList sgmList = new FindList();
		try {

			Nodes nodes = (Nodes) xmlStream.fromXML(xml);
			for (Node node : nodes.getNodeList()) {
				List<Properties> propertyList = node.getProperties();

				Sgm sgm = new Sgm();
				for (Properties properties : propertyList) {
					if (properties.getPid().equals("segment_id")) {
						//sgm.setSgmId((properties.getEleValue().substring(properties.getEleValue().lastIndexOf("-")+1)==null)?"":properties.getEleValue().substring(properties.getEleValue().lastIndexOf("-")+1));
						String sid = properties.getEleValue();
						//System.out.println("?>>>>>>>>>>>>>>>>>>>>>>"+sid);
						if(properties.getEleValue().length()>24)
							sid= sid.substring(21, 25);
						else
							sid="";

						sgm.setSgmId(sid);
					} else if(properties.getPid().equals("segment_title")){
						sgm.setSgmNm(properties.getEleValue());
					}
				}
				sgmList.addSgms(sgm);

				// break;
			}
			//			if(sgmList.getSgms().size()<1){
			Sgm sgmDev = new Sgm();
			sgmDev.setSgmId("S000");
			sgmDev.setSgmNm("DEFAULT");
			sgmList.addSgms(sgmDev);
			//			}

			// 세그먼트 조회 테스트 생성
			//			Sgm sgm = null;
			//
			//			sgm = new Sgm();
			//			sgm.setSgmId("S000");
			//			sgm.setSgmNm("S000");
			//			sgmList.addSgms(sgm);

		} catch (Exception e) {
			logger.error("forwardSegmentInfo", e);
		}

		return sgmList;
	}

	@Override
	public List<ContentsTbl> forwardSegmentsInfo(String xml)
			throws DaoNonRollbackException {
		try {

			Nodes nodes = (Nodes) xmlStream.fromXML(xml);
			List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();
			for (Node node : nodes.getNodeList()) {
				List<Properties> propertyList = node.getProperties();
				ContentsTbl contentTbl = new ContentsTbl();
				for (Properties properties : propertyList) {


					if (StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("segment_title")) {

						contentTbl.setSegmentNm(properties.getEleValue());
						//logger.debug("key:"+properties.getPid()+", value:"+properties.getEleValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("segment_id")){
						String sid = properties.getEleValue();
						//System.out.println("?>>>>>>>>>>>>>>>>>>>>>>"+sid);
						if(properties.getEleValue().length()>24)
							sid= sid.substring(21, 25);
						else
							sid="";
						//System.out.println("?>>>>>>>>>>>>>>>>>>>>>>"+sid2);
						contentTbl.setSegmentId(sid);
						//logger.debug("key:"+properties.getPid()+", value:"+properties.getEleValue());
					}
				}
				contentsTbl.add(contentTbl);
			}

			return contentsTbl;
		} catch (Exception e) {
			logger.error("forwardSegmentsInfo", e);
		}
		return null;
	}

	@Override
	public List<ContentsTbl> forwardProgramsInfo(String xml)
			throws DaoNonRollbackException {
		try {
			Nodes nodes = (Nodes) xmlStream.fromXML(xml);
			List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();
			for (Node node : nodes.getNodeList()) {
				List<Properties> propertyList = node.getProperties();
				ContentsTbl contentTbl = new ContentsTbl();
				for (Properties properties : propertyList) {
					if (StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("program_title")) {

						contentTbl.setPgmNm(properties.getEleValue());
						logger.debug("key:"+properties.getPid()+", value:"+properties.getEleValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("program_code")){
						contentTbl.setPgmCd(properties.getValue());
						logger.debug("key:"+properties.getPid()+", value:"+properties.getValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("group_code")){
						contentTbl.setPgmGrpCd(properties.getValue());
						logger.debug("key:"+properties.getPid()+", value:"+properties.getValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("channel_code")){
						contentTbl.setChannelCode(properties.getValue());
						logger.debug("key:"+properties.getPid()+", value:"+properties.getValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("program_planned_date")){
						contentTbl.setBrdtime(properties.getEleValue());
						logger.debug("key:"+properties.getPid()+", value:"+properties.getEleValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("program_planned_start_time")){
						contentTbl.setTime(properties.getEleValue());
						logger.debug("key:"+properties.getPid()+", value:"+properties.getEleValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("program_id")){
						contentTbl.setPgmId(properties.getEleValue());
						logger.debug("key:"+properties.getPid()+", value:"+properties.getEleValue());
					}
					else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("transmission_audio_mode_main")){
						contentTbl.setAudioModeMain(StringUtils.defaultIfEmpty(properties.getEleValue(), "Stereo"));
						logger.debug("transmission_audio_mode_main >> key:"+properties.getPid()+", value:"+properties.getEleValue());
					}
					else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("audio_mode_main")){
						if(StringUtils.isBlank(contentTbl.getAudioModeMain())) {
							contentTbl.setAudioModeMain(StringUtils.defaultIfEmpty(properties.getEleValue(), "Stereo"));
						}
						logger.debug("audio_mode_main >> key:"+properties.getPid()+", value:"+properties.getEleValue());
					}
					// 화면해설방송 여부
					else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("descriptive_video_service_yn")){
						contentTbl.setDescriptiveVideoServiceYn(StringUtils.defaultIfEmpty(properties.getValue(), "N"));
						logger.debug("key:"+properties.getPid()+", value:"+properties.getValue());
					}
					// 본방,재방 구분
					else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("rerun_classification")){
						contentTbl.setRerunClassification(StringUtils.defaultIfEmpty(properties.getEleValue(), ""));
						logger.debug("key:"+properties.getPid()+", value:"+properties.getEleValue());
					}
				}
				contentsTbl.add(contentTbl);
			}

			return contentsTbl;
		} catch (Exception e) {
			logger.error("forwardContentsMLInfo", e);
		}
		return null;
	}

	@Override
	public Map<String, Object> forwardMetaInfo(String xml)
			throws DaoNonRollbackException {
		Map<String,Object> element = new HashMap<String,Object>();
		try {

			Nodes nodes = (Nodes) xmlStream.fromXML(xml);
			for (Node node : nodes.getNodeList()) {
				List<Properties> propertyList = node.getProperties();
				for (Properties properties : propertyList) {
					if (StringUtils.isNotBlank(properties.getPid())&&properties.getType().equals("REFERENCE")) {
						element.put(properties.getPid(), properties.getValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getType().equals("NAME")){
						element.put(properties.getPid(), properties.getEleValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getType().equals("TEXT")){
						element.put(properties.getPid(), properties.getEleValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getType().equals("URL")){
						element.put(properties.getPid(), properties.getEleValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getType().equals("REFERENCED")){
						element.put(properties.getPid(), properties.getValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getType().equals("NUMBER")){
						element.put(properties.getPid(), properties.getEleValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getType().equals("CODE")){
						element.put(properties.getPid(), properties.getValue());
					}
				}
			}
			return element;
		} catch (Exception e) {
			logger.error("forwardContentsMLInfo", e);
		}
		return null;
	}

	@Override
	public Map<String, Object> forwardContentsMLNodeInfo(String xml)
			throws DaoNonRollbackException {
		Map<String,Object> element = new HashMap<String,Object>();
		try {
			Node node = (Node) xmlStream.fromXML(xml);
			List<Properties> propertyList = node.getProperties();

			for (Properties properties : propertyList) {
				if (StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("group_code")) {
					element.put(properties.getPid(), properties.getValue());
					logger.debug("key:"+properties.getPid()+", value:"+properties.getValue());
				}else if (StringUtils.isNotBlank(properties.getPid())&&properties.getType().equals("CODE")) {
					element.put(properties.getPid(), properties.getValue());
					logger.debug("key:"+properties.getPid()+", value:"+properties.getValue());
				}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("program_code")){
					element.put(properties.getPid(), properties.getValue());
					logger.debug("key:"+properties.getPid()+", value:"+properties.getValue());
				}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("alias_eng")){
					element.put(properties.getPid(), properties.getEleValue());
					logger.debug("key:"+properties.getPid()+", value:"+properties.getEleValue());
				}else{
					element.put(properties.getPid(), properties.getEleValue());
					logger.debug("key:"+properties.getPid()+", value:"+properties.getEleValue());
				}
			}
		} catch (Exception e) {
			logger.error("forwardContentsMLInfo", e);
		}
		return element;
	}

	@Override
	public Map<String, Object> forwardContentsMLNodesInfo(String xml)
			throws DaoNonRollbackException {
		Map<String,Object> element = new HashMap<String,Object>();
		try {

			Nodes nodes = (Nodes) xmlStream.fromXML(xml);
			System.out.println(nodes.getNodeList().size());
			for (Node node : nodes.getNodeList()) {
				List<Properties> propertyList = node.getProperties();

				for (Properties properties : propertyList) {
					if (StringUtils.isNotBlank(properties.getPid())&&properties.getType().equals("CODE")) {
						element.put(properties.getPid(), properties.getValue());
						logger.debug("key:"+properties.getPid()+", value:"+properties.getValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("program_code")){
						element.put(properties.getPid(), properties.getValue());
						logger.debug("key:"+properties.getPid()+", value:"+properties.getValue());
					}else{
						element.put(properties.getPid(), properties.getEleValue());
						logger.debug("key:"+properties.getPid()+", value:"+properties.getEleValue());
					}
				}
			}
		} catch (Exception e) {
			logger.error("forwardContentsMLInfo", e);
		}
		return element;
	}

	@Override
	public List<ContentsTbl> forwardPeopleInfo(String xml)
			throws DaoNonRollbackException {
		try {

			Nodes nodes = (Nodes) xmlStream.fromXML(xml);
			List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();
			for (Node node : nodes.getNodeList()) {
				List<Properties> propertyList = node.getProperties();
				ContentsTbl contentTbl = new ContentsTbl();
				for (Properties properties : propertyList) {
					if (StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("s_seq")) {
						contentTbl.setsSeq(properties.getValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("name_korea")){
						contentTbl.setNameKorea(properties.getEleValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("casting_name")){
						contentTbl.setCastingName(properties.getEleValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("image_url")){
						contentTbl.setImageUrl(properties.getEleValue());
					}
				}
				contentsTbl.add(contentTbl);
			}

			return contentsTbl;
		} catch (Exception e) {
			logger.error("forwardPeopleInfo", e);
		}
		return null;
	}

	@Override
	public List<ContentsTbl> forwardSearchPeopleInfo(String xml)
			throws DaoNonRollbackException {
		try {

			Nodes nodes = (Nodes) xmlStream.fromXML(xml);
			List<ContentsTbl> contentsTbl = new ArrayList<ContentsTbl>();
			for (Node node : nodes.getNodeList()) {
				List<Properties> propertyList = node.getProperties();
				ContentsTbl contentTbl = new ContentsTbl();
				for (Properties properties : propertyList) {
					if (StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("s_seq")) {
						contentTbl.setsSeq(properties.getEleValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("s_name")){
						contentTbl.setNameKorea(properties.getEleValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("s_sphere")){
						contentTbl.setCastingName(properties.getEleValue());
					}else if(StringUtils.isNotBlank(properties.getPid())&&properties.getPid().equals("s_upfile")){
						contentTbl.setImageUrl(properties.getEleValue());
					}
				}
				contentsTbl.add(contentTbl);
			}

			return contentsTbl;
		} catch (Exception e) {
			logger.error("forwardPeopleInfo", e);
		}
		return null;
	}


}
