package kr.co.d2net.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.commons.util.StringUtil;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlConvertorService;
import kr.co.d2net.commons.util.XmlConvertorServiceImpl;
import kr.co.d2net.dao.ContentsDao;
import kr.co.d2net.dao.ContentsInstDao;
import kr.co.d2net.dao.CornerDao;
import kr.co.d2net.dao.MediaToolDao;
import kr.co.d2net.dao.ProFlDao;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.CornerTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.TransferHisTbl;
import kr.co.d2net.dto.xml.kdas.EpisodeInfo;
import kr.co.d2net.dto.xml.kdas.ExchangeMetadataInfo;
import kr.co.d2net.dto.xml.kdas.KBSExchangeMetadata;
import kr.co.d2net.dto.xml.kdas.MediaInfo;
import kr.co.d2net.dto.xml.kdas.ProgramInfo;
import kr.co.d2net.dto.xml.meta.Nodes;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

@Service(value = "contentsManagerService")
public class ContentsManagerServiceImpl implements ContentsManagerService {

	private Log logger = LogFactory.getLog(ContentsManagerServiceImpl.class);

	@Autowired
	private ContentsDao contentsDao;
	@Autowired
	private ContentsInstDao contentsInstDao;
	@Autowired
	private ProFlDao proFlDao;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private MediaToolDao mediaToolDao;
	@Autowired
	private CornerDao cornerDao;

	@Override
	public ContentsTbl getContents(Map<String, Object> params)
			throws ServiceException {
		return contentsDao.getContents(params);
	}

	@SuppressWarnings("unchecked")
	public List<ContentsTbl> findContents(Map<String, Object> params)
			throws ServiceException {
		List<ContentsTbl> contentsTbls = contentsDao.findContents(params);
		return (contentsTbls == null) ? Collections.EMPTY_LIST : contentsTbls;
	}

	@SuppressWarnings("unchecked")
	public List<ContentsTbl> getContentsPrgrs(Map<String, Object> params)
			throws ServiceException {
		List<ContentsTbl> contentsTbls = contentsDao.getContentsPrgrs(params);
		return (contentsTbls == null) ? Collections.EMPTY_LIST : contentsTbls;
	}

	@Override
	public void insertContents(ContentsTbl contentsTbl,
			ContentsInstTbl contentsInstTbl) throws ServiceException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pgmCd", contentsTbl.getPgmCd());
		params.put("pgmId", contentsTbl.getPgmId());
		params.put("regrid", contentsTbl.getRegrid());

		ContentsTbl contentsTbl2 = contentsDao.getContents(params);

		Long ctId = 0L;
		if(contentsTbl2 != null && contentsTbl2.getCtId() > 0) {
			ctId = contentsTbl2.getCtId();
			
			contentsTbl2.setPgmGrpCd(contentsTbl.getPgmGrpCd());
			contentsTbl2.setPgmNm(contentsTbl.getPgmNm());
			contentsTbl2.setChannelCode(contentsTbl.getChannelCode());
			contentsTbl2.setDuration(contentsTbl.getDuration());
			contentsTbl2.setTrimmSte(contentsTbl.getTrimmSte());
			contentsTbl2.setPgmNum(contentsTbl.getPgmNum());
			contentsTbl2.setCtCla(contentsTbl.getCtCla());
			contentsTbl2.setCtTyp(contentsTbl.getCtTyp());
			contentsTbl2.setBrdDd(contentsTbl.getBrdDd());
			contentsTbl2.setLocalCode(StringUtils.defaultIfEmpty(contentsTbl2.getLocalCode(), "00"));
			contentsTbl2.setCtLeng(contentsTbl.getCtLeng());

			if(StringUtils.isNotBlank(contentsTbl.getDataStatCd()) && Integer.valueOf(contentsTbl.getDataStatCd()) > 200) {
				contentsTbl2.setDataStatCd(contentsTbl.getDataStatCd());
				contentsTbl2.setPrgrs("-1");
			} else {
				contentsTbl2.setPrgrs("100");
				contentsTbl2.setDataStatCd("200");
			}
			
			if(contentsTbl.getCornerTbls() != null && contentsTbl.getCornerTbls().size() > 0) {
				contentsTbl2.setCtNm("(코)"+StringUtils.defaultIfEmpty(contentsTbl.getCtNm(), ""));
			}

			contentsDao.updateContentsInfo(contentsTbl2);
		} else {
			contentsTbl.setPrgrs("100");
			contentsTbl.setDataStatCd("200");
			contentsTbl.setLocalCode(StringUtils.defaultIfEmpty(contentsTbl.getLocalCode(), "00"));
			
			if(contentsTbl.getCornerTbls() != null && contentsTbl.getCornerTbls().size() > 0) {
				contentsTbl.setCtNm("(코)"+StringUtils.defaultIfEmpty(contentsTbl.getCtNm(), ""));
			}
			ctId = contentsDao.insertContents(contentsTbl);
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("ct_id: "+ctId);
		}

		// 2013.04.04 오디오 코너정보 추가
		// 2013.10.19 코너 세그먼트ID가 통합CMS 체계와 맞기 않으므로 관리에서 제거함.
		/*
		if(contentsTbl.getCornerTbls() != null && contentsTbl.getCornerTbls().size() > 0) {
			try {
				for(CornerTbl cornerTbl : contentsTbl.getCornerTbls()) {
					cornerTbl.setCtId(ctId);
					cornerDao.insertCorner(cornerTbl);
				}
			} catch (Exception e) {
				logger.error("corner insert error", e);
			}
		}
		*/

		contentsInstTbl.setCtId(ctId);
		Long ctiId = contentsInstDao.insertContentsInst(contentsInstTbl);

		if(logger.isDebugEnabled()) {
			logger.debug("cti_id: "+ctiId);
		}
	}

	@Override
	public void updateContentsRms(ContentsTbl contentsTbl) throws ServiceException {
		contentsDao.updateContentsRms(contentsTbl);

		//contentsInstTbl.setCtId(ctId);
		//contentsInstDao.insertContentsInst(contentsInstTbl);
		// cti_id = 등록
	}

	@Override
	public void deleteContents(ContentsTbl contentsTbl) throws ServiceException {
		contentsDao.deleteContents(contentsTbl);
	}

	@Override
	public PaginationSupport<ContentsTbl> findContentsList(Search search)
			throws ServiceException {

		PaginationSupport<ContentsTbl> contentsList = contentsDao
				.findcontentsList(search);

		return contentsList;
	}

	@Override
	public PaginationSupport<ContentsTbl> findStandbycontentsList(Search search)
			throws ServiceException {

		PaginationSupport<ContentsTbl> contentsList = contentsDao
				.findstandbycontentsList(search);

		return contentsList;
	}

	@Override
	public PaginationSupport<ContentsTbl> findRmscontentsList(Search search)
			throws ServiceException {

		PaginationSupport<ContentsTbl> contentsList = contentsDao
				.findrmscontentsList(search);

		return contentsList;
	}

	@Override
	public PaginationSupport<ContentsTbl> findRmscontentsUd(Search search)
			throws ServiceException {

		PaginationSupport<ContentsTbl> contentsList = contentsDao
				.findrmscontentsUd(search);

		return contentsList;
	}


	@Override
	public int getCtId() throws ServiceException {

		return 0;
	}

	@Override
	public ContentsTbl getRmsContentsCount(Map<String, Object> params) throws ServiceException {

		return contentsDao.getRmsContentsCount(params);
	}

	@Override
	public ContentsTbl getContents2(Map<String, Object> params)
			throws ServiceException {
		return contentsDao.getContents2(params);
	}

	@Override
	public ContentsTbl getContents3(Map<String, Object> params)
			throws ServiceException {
		return contentsDao.getContents3(params);
	}

	@Override
	public ContentsTbl getContents4(Map<String, Object> params)
			throws ServiceException {
		return contentsDao.getContents4(params);
	}

	@Override
	public void insertContents(ContentsTbl contentsTbl) throws ServiceException {
		contentsDao.insertContents(contentsTbl);
	}

	@Override
	public void updateContents(ContentsTbl contentsTbl) throws ServiceException {
		contentsDao.updateContentsInfo(contentsTbl);

	}

	@Override
	public void updateContents2(ContentsTbl contentsTbl) throws ServiceException {
		contentsDao.updateContents2(contentsTbl);

	}

	@Override
	public void archiveXMLPasing(String xml, ContentsTbl contentsTbl, ContentsInstTbl contentsInstTbl) throws ServiceException {
		try {
			/*
			XmlConvertorService<KBSExchangeMetadata> xmlConvertorService = XmlConvertorServiceImpl.getInstance();
			KBSExchangeMetadata metadata = xmlConvertorService.unMarshaller(xml);

			ExchangeMetadataInfo exchangeMetadataInfo = metadata.getExchangeMetadataInfo();
			if(exchangeMetadataInfo != null) {
				contentsInstTbl.setSom(exchangeMetadataInfo.getExchangeFileStartTimecode());
				contentsInstTbl.setEom(exchangeMetadataInfo.getExchangeFileEndTimecode());
				if(logger.isDebugEnabled()) {
					logger.debug("som : "+contentsInstTbl.getSom());
					logger.debug("eom : "+contentsInstTbl.getEom());
				}
			}

			MediaInfo mediaInfo = metadata.getMediaInfo();
			if(mediaInfo != null) {
				if(StringUtils.isNotBlank(mediaInfo.getVideoResolution()) && mediaInfo.getVideoResolution().indexOf("x") > -1) {
					String[] resolution = mediaInfo.getVideoResolution().split("x");
					if(resolution.length > 1) {
						if(logger.isDebugEnabled()) {
							logger.debug("resolution[0] : "+resolution[0]);
							logger.debug("resolution[1] : "+resolution[1]);
						}
						contentsInstTbl.setVdHresol(Integer.valueOf(resolution[0]));
						contentsInstTbl.setVdVresol(Integer.valueOf(resolution[1]));
					}
				}
				contentsInstTbl.setFrmPerSec(mediaInfo.getVideoFps());
				contentsInstTbl.setAudSampFrq(mediaInfo.getAudioSampleRate());
			}


			ProgramInfo programInfo = metadata.getProgramInfo();
			if(programInfo != null) {
				contentsTbl.setPgmCd(programInfo.getProgramCode());
				contentsTbl.setPgmNm(programInfo.getProgramTitle());
				contentsTbl.setCtNm(programInfo.getProgramTitle());
			}

			EpisodeInfo episodeInfo = metadata.getEpisodeInfo();
			if(episodeInfo != null) {
				contentsTbl.setPgmId(episodeInfo.getProgramId());
				contentsTbl.setBrdDd(episodeInfo.getProgramOnairDate());
				contentsTbl.setVdQlty(episodeInfo.getProductionVideoQulity());
			}

			contentsInstTbl.setRegrid("KDAS");
			contentsInstTbl.setRegDt(Utility.getTimestamp());
			contentsTbl.setRegrid("KDAS");
			*/
			
			Element root = (Element)Utility.stringToDom(xml).getChildNodes().item(0);
			
			Element element = Utility.getChildElementByTagName(root, "EXCHANGE_METADATA_INFO");
			String som = Utility.getChildElementValueByTagName(element, "EXCHANGE_FILE_START_TIMECODE");
			String eom = Utility.getChildElementValueByTagName(element, "EXCHANGE_FILE_END_TIMECODE");
			contentsInstTbl.setSom(som);
			contentsInstTbl.setEom(eom);
			if(logger.isDebugEnabled()) {
				logger.debug("som : "+som);
				logger.debug("eom : "+eom);
			}

			element = Utility.getChildElementByTagName(root, "MEDIA_INFO");
			String vCodec = Utility.getChildElementValueByTagName(element, "VIDEO_CODEC");
			if(logger.isDebugEnabled()) {
				logger.debug("v_codec : "+vCodec);
			}

			String[] resolution = Utility.getChildElementValueByTagName(element, "VIDEO_RESOLUTION").split("x");
			if(resolution.length > 0) {
				if(logger.isDebugEnabled()) {
					logger.debug("resolution[0] : "+resolution[0]);
					logger.debug("resolution[1] : "+resolution[1]);
				}
				if(StringUtils.isNotBlank(resolution[0])) {
					contentsInstTbl.setVdHresol(Integer.valueOf(resolution[0]));
					contentsInstTbl.setVdVresol(Integer.valueOf(resolution[1]));
				}
			}

			String vFps = Utility.getChildElementValueByTagName(element, "VIDEO_FPS");
			if(logger.isDebugEnabled()) {
				logger.debug("vFps : "+vFps);
			}
			contentsInstTbl.setFrmPerSec(vFps);

			String audio_samp_rate = Utility.getChildElementValueByTagName(element, "AUDIO_SAMPLE_RATE");
			if(logger.isDebugEnabled()) {
				logger.debug("audio_samp_rate : "+audio_samp_rate);
			}
			contentsInstTbl.setAudSampFrq(audio_samp_rate);
			contentsInstTbl.setRegrid("KDAS");
			contentsInstTbl.setRegDt(Utility.getTimestamp());

			element = Utility.getChildElementByTagName(root, "PROGRAM_INFO");

			String program_code = Utility.getChildElementValueByTagName(element, "PROGRAM_CODE");
			if(logger.isDebugEnabled()) {
				logger.debug("program_code : "+program_code);
			}
			contentsTbl.setPgmCd(program_code);

			String program_title = Utility.getChildElementValueByTagName(element, "PROGRAM_TITLE");
			if(logger.isDebugEnabled()) {
				logger.debug("program_title : "+program_title);
			}
			contentsTbl.setPgmNm(program_title);
			contentsTbl.setCtNm(program_title);

			element = Utility.getChildElementByTagName(root, "EPISODE_INFO");

			String program_id = Utility.getChildElementValueByTagName(element, "PROGRAM_ID");
			if(logger.isDebugEnabled()) {
				logger.debug("program_id : "+program_id);
			}
			contentsTbl.setPgmId(program_id);

			String program_onair_date = Utility.getChildElementValueByTagName(element, "PROGRAM_ONAIR_DATE");
			if(logger.isDebugEnabled()) {
				logger.debug("program_onair_date : "+program_onair_date);
			}
			if(StringUtils.isNotBlank(program_onair_date)) {
				contentsTbl.setBrdDd(Utility.getDate(program_onair_date, "yyyyMMddHHmmss"));
			}

			String vdQlty = Utility.getChildElementValueByTagName(element, "PRODUCTION_VIDEO_QULITY");
			if(logger.isDebugEnabled()) {
				logger.debug("vdQlty : "+vdQlty);
			}
			contentsTbl.setVdQlty(vdQlty);
			contentsTbl.setRegrid("KDAS");
			
		} catch (Exception e) {
			logger.error("archiveXMLPasing KDAS xml parsing error", e);
			contentsTbl = null;
			contentsInstTbl = null;
		}
	}

	@Override
	public String createContentML(String pgmId, Integer proFlid) throws ServiceException {

		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> programElement = new HashMap<String,Object>();
		Map<String,Object> proGroupElement = new HashMap<String,Object>();


		params.put("program_id",	pgmId);

		String domain = messageSource.getMessage("meta.system.domain", null,
				Locale.KOREA);
		String programMethod = messageSource.getMessage("meta.system.search.program.read",
				null, Locale.KOREA);
		String programGroupMethod = messageSource.getMessage("meta.system.search.group.program.read",
				null, Locale.KOREA);
		/**
		 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
		 * param  pgmId;
		 */
		String programXml = "";
		try {
			HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
			programXml = httpRequestService.findData(domain+programMethod, Utility.convertNameValue(params));
			//programXml = Utility.connectHttpPostMethod(domain+programMethod, Utility.convertNameValue(params));
		}catch (Exception e) {
			logger.error("Get programXml by MetaHub System createContentML Error", e);
		}

		/**
		 * 프로파일 정보를 조회하여 bean 
		 * 
		 */
		params.put("proFlid", String.valueOf(proFlid));
		ProFlTbl proFlTbl = proFlDao.getProFl(params);

		if(StringUtils.isNotBlank(programXml)&&StringUtils.startsWith(programXml, "<?xml"))
			programElement = mediaToolDao.forwardContentsMLNodeInfo(programXml);

		/**
		 * 메타허브 쪽에 프로그램 정보 조회 값을 받아와서 bean set
		 * param  프로그램그룹코드;
		 */
		if(programElement.containsKey("program_code")){

			logger.debug("#################");
			logger.debug("##### pcode :"+programElement.get("program_code"));
			logger.debug("#################");

			params.isEmpty();
			params.put("program_code",(String)programElement.get("program_code"));

			String proGroupXml ="";
			try {
				HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
				proGroupXml = httpRequestService.findData(domain+programGroupMethod, Utility.convertNameValue(params));
				//proGroupXml = Utility.connectHttpPostMethod(domain+programGroupMethod, Utility.convertNameValue(params));
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("Get programGroupXml by MetaHub System createContentML Error", e);
			}
			if(StringUtils.isNotBlank(proGroupXml)&&StringUtils.startsWith(proGroupXml, "<?xml"))
				proGroupElement = mediaToolDao.forwardContentsMLNodeInfo(proGroupXml);
		}

		StringBuffer buf = new StringBuffer();

		if(proGroupElement != null && !proGroupElement.isEmpty()) {
			buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buf.append("<KBSiContentML publisher=\"KBS\" publicationTime=\""+Utility.getTimestamp()+"\" rightsOwner=\"KBS\" version=\"1\">");
			buf.append("<ProgramDescription>");
			buf.append("<GroupInformationTable>");
			buf.append("<GroupInformation>");
			buf.append("<BasicDescription>");
			buf.append("<ProgramCode>"+StringUtil.nvl(programElement.get("group_code"), "")+"</ProgramCode>");
			buf.append("<Title type=\"main\"><![CDATA["+StringUtil.nvl(programElement.get("program_title"), "")+"]]></Title>");  
			buf.append("<Title type=\"original\"><![CDATA["+StringUtil.nvl(programElement.get("original_title"), "")+"]]></Title>");
			buf.append("<MediaCode type=\"qcode:MediaCode\">"+StringUtil.nvl(proGroupElement.get("media_code"), "")+"</MediaCode>");
			buf.append("<ProgrammingLocalStationName>"+StringUtil.nvl(proGroupElement.get("programming_local_station_code"), "")+"</ProgrammingLocalStationName>");
			buf.append("<NewsKind></NewsKind>"); 
			buf.append("<DomesticForeignKind>"+StringUtil.nvl(proGroupElement.get("domestic_foreign_kind"), "")+"</DomesticForeignKind>");
			buf.append("<ProductionBodyClassification>"+StringUtil.nvl(proGroupElement.get("production_body_classification_code"), "")+"</ProductionBodyClassification>");	
			buf.append("<CmProgramYn></CmProgramYn>");										
			buf.append("<BroadcastLanguageKind>"+StringUtil.nvl(proGroupElement.get("broadcast_language_kind_code"), "")+"</BroadcastLanguageKind>");
			buf.append("<ChannelCode type=\"qcode:ChannelCode\">"+StringUtil.nvl(proGroupElement.get("channel_code"), "")+"</ChannelCode>");
			buf.append("<Genre type=\"qcode:Genre\">"+StringUtil.nvl(proGroupElement.get("program_genre_code"), "")+"</Genre>");  
			buf.append("<Intention><![CDATA["+StringUtil.nvl(proGroupElement.get("program_intention"), "")+"]]></Intention>");
			buf.append("<OutsiderProductionName></OutsiderProductionName>");
			buf.append("<ProgramPlannedDurationMin>"+StringUtil.nvl(programElement.get("program_planned_duration_minute"), "")+"</ProgramPlannedDurationMin>");
			buf.append("<AudioModeMain>"+StringUtil.nvl(programElement.get("audio_transmission_type_code"), "")+"</AudioModeMain>");
			buf.append("<ProgramDeliberationGrade type=\"qcode:DeliberationGradeCode\">"+StringUtil.nvl(proGroupElement.get("deliberation_grade_code"), "")+"</ProgramDeliberationGrade>"); 
			buf.append("<ProductionVideoQuality></ProductionVideoQuality>");
			buf.append("<ProgramStartDate>"+StringUtil.nvl(proGroupElement.get("program_start_date"), "")+"</ProgramStartDate>");
			buf.append("<ProgramEndDate>"+StringUtil.nvl(proGroupElement.get("program_end_date"), "")+"</ProgramEndDate>");  
			buf.append("<ProgramPlannedStartTime>"+StringUtil.nvl(proGroupElement.get("program_planned_start_time"), "")+"</ProgramPlannedStartTime>");   
			buf.append("<RegularSpecialClassification>"+StringUtil.nvl(proGroupElement.get("regular_special_classification_code"), "")+"</RegularSpecialClassification>"); 
			buf.append("<BroadcastCompleteYn>"+StringUtil.nvl(proGroupElement.get("broadcast_complete_yn"), "")+"</BroadcastCompleteYn>"); 
			buf.append("<ThumnailImage><![CDATA[]]></ThumnailImage>");
			buf.append("<ProgramOnairDay>"+StringUtil.nvl(proGroupElement.get("onair_day_code"), "")+"</ProgramOnairDay>");
			buf.append("<CopyrightYn><![CDATA[]]></CopyrightYn>");
			buf.append("<Synopsis><![CDATA[]]></Synopsis>");
			buf.append("<Keywords><![CDATA["+StringUtil.nvl(proGroupElement.get("keywords"), "")+"]]></Keywords>");
			buf.append("</BasicDescription>");
			buf.append("</GroupInformation>");
			buf.append("</GroupInformationTable>");	

			buf.append("<ProgramInformationTable>");
			buf.append("<ProgramInformation>");
			buf.append("<BasicDescription>");
			buf.append("<ProgramCode>"+StringUtil.nvl(programElement.get("group_code"), "")+"</ProgramCode>");
			buf.append("<ProgramId>"+StringUtil.nvl(programElement.get("program_id"), "")+"</ProgramId>");
			buf.append("<Title type=\"main\"><![CDATA["+StringUtil.nvl(programElement.get("program_title"), "")+"]]></Title>");
			buf.append("<Title type=\"sub\"><![CDATA["+StringUtil.nvl(programElement.get("program_subtitle"), "")+"]]></Title>");
			buf.append("<Title type=\"secondary_sub\"><![CDATA[]]></Title>");
			buf.append("<ProgramSequenceNumber>"+StringUtil.nvl(programElement.get("program_sequence_number"), "")+"</ProgramSequenceNumber>");
			buf.append("<ProductionDurationMin>"+StringUtil.nvl(programElement.get("program_planned_duration_minute"), "")+"</ProductionDurationMin>");
			buf.append("<Genre type=\"code\">"+StringUtil.nvl(programElement.get("program_genre_code"), "")+"</Genre>");
			buf.append("<Genre type=\"name\"><![CDATA[]]></Genre>");
			buf.append("<RegularSpecialClassification>"+StringUtil.nvl(proGroupElement.get("regular_special_classification_code"), "")+"</RegularSpecialClassification>");
			buf.append("<ProductionVideoQuality>"+StringUtil.nvl(programElement.get("production_video_quality_code"), "")+"</ProductionVideoQuality>");
			buf.append("<Synopsis><![CDATA["+StringUtil.nvl(proGroupElement.get("ref_sysnopsis"), "")+"]]></Synopsis>");
			buf.append("<MainStory><![CDATA["+StringUtil.nvl(programElement.get("main_story"), "")+"]]></MainStory>");
			buf.append("<OnairDate><![CDATA["+StringUtil.nvl(programElement.get("program_planned_date"), "")+"]]></OnairDate>");
			buf.append("<ChannelCode type=\"qcode:ChannelCode\">"+StringUtil.nvl(programElement.get("channel_code"), "")+"</ChannelCode>");
			buf.append("<ThumnailImage><![CDATA[]]></ThumnailImage>");
			buf.append("<ProgramDeliberationGrade>"+StringUtil.nvl(programElement.get("program_deliberation_grade_code"), "")+"</ProgramDeliberationGrade>");
			buf.append("<PartNumber>"+StringUtil.nvl(programElement.get("part_number"), "")+"</PartNumber>");
			buf.append("<Keywords><![CDATA["+StringUtil.nvl(proGroupElement.get("keywords"), "")+"]]></Keywords>");
			buf.append("</BasicDescription>");
			buf.append("</ProgramInformation>");
			buf.append("</ProgramInformationTable>");
			buf.append("</ProgramDescription>");

			buf.append("<InstanceDescription>");
			buf.append("<ServiceInformationTable>");
			buf.append("<ServiceInformation serviceId=\""+StringUtil.nvl(programElement.get("channel_code"), "")+"\">");
			buf.append("<Name>KBS</Name>");
			buf.append("<Owner>KBS</Owner>");
			buf.append("</ServiceInformation>");
			buf.append("</ServiceInformationTable>");
			buf.append("</InstanceDescription>");

			buf.append("<MovieDescription>");
			buf.append("<MovieInformationTable>");

			buf.append("<MovieInformation>");
			buf.append("<ServiceBitrate>"+StringUtil.nvl(proFlTbl.getServBit(), "")+"</ServiceBitrate>");
			buf.append("<Quality>"+StringUtil.nvl(proFlTbl.getPicKind(), "")+"</Quality>");
			buf.append("<Extension>"+StringUtil.nvl(proFlTbl.getExt(), "")+"</Extension>");
			buf.append("</MovieInformation>");

			buf.append("<VideoInformation>");
			buf.append("<VideoCodec>"+StringUtil.nvl(proFlTbl.getVdoCodec(), "")+"</VideoCodec>");
			buf.append("<Bitrate>"+StringUtil.nvl(proFlTbl.getVdoBitRate(), "")+"</Bitrate>");
			buf.append("<Width>"+StringUtil.nvl(proFlTbl.getVdoHori(), "")+"</Width>");
			buf.append("<Height>"+StringUtil.nvl(proFlTbl.getVdoVert(), "")+"</Height>");
			buf.append("<Frame>"+StringUtil.nvl(proFlTbl.getVdoFS(), "")+"</Frame>");
			buf.append("<AspectRatio>"+StringUtil.nvl(proFlTbl.getVdoSync(), "")+"</AspectRatio>");
			buf.append("</VideoInformation>");

			buf.append("<AudioInformation>");
			buf.append("<AudioCodec>"+StringUtil.nvl(proFlTbl.getAudCodec(), "")+"</AudioCodec>");
			buf.append("<Bitrate>"+StringUtil.nvl(proFlTbl.getAudBitRate(), "")+"</Bitrate>");
			buf.append("<Channel>"+StringUtil.nvl(proFlTbl.getAudChan(), "")+"</Channel>");
			buf.append("<SampleRate>"+StringUtil.nvl(proFlTbl.getAudSRate(), "")+"</SampleRate>");
			buf.append("</AudioInformation>");

			buf.append("</MovieInformationTable>");
			buf.append("</MovieDescription>");
			buf.append("</KBSiContentML>");
		}
		return buf.toString();
	}

	@Override
	public String createSMIL(List<TransferHisTbl> transferHisTbls) throws ServiceException {

		StringBuffer smilXml = new StringBuffer()
		.append(" <smil> ")
		.append(" 	<head></head> ")
		.append(" 	<body> ")
		.append(" 		<switch> ");

		Collections.sort(transferHisTbls, getCompare());

		for(TransferHisTbl transferHisTbl : transferHisTbls) {
			if(transferHisTbl.getAvGubun().equals("A"))
				smilXml.append(" 			<audio src=\"mp4:"+transferHisTbl.getWrkFileNm()+".mp4\" system-bitrate=\""+transferHisTbl.getBitRate()+"\"/> ");
			else
				smilXml.append(" 			<video src=\"mp4:"+transferHisTbl.getWrkFileNm()+".mp4\" system-bitrate=\""+transferHisTbl.getBitRate()+"\"/> ");
		}

		smilXml.append(" 		</switch> ")
		.append(" 	</body> ")
		.append(" </smil> ");

		return smilXml.toString();
	}

	private static Comparator<? super TransferHisTbl> getCompare() {
		return new Comparator<TransferHisTbl>() {
			public int compare(TransferHisTbl d1, TransferHisTbl d2) {
				if(Integer.valueOf(d1.getBitRate()) < Integer.valueOf(d2.getBitRate())) return -1;
				else return 1;
			}
		};
	}
}
