package kr.co.d2net.soap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.jws.WebService;

import kr.co.d2net.commons.events.CopyEventArgs;
import kr.co.d2net.commons.events.Event;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.StringUtil;
import kr.co.d2net.commons.util.UserFilenameFilter;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlFileService;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.BusiPartnerTbl;
import kr.co.d2net.dto.CodeTbl;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.CornerTbl;
import kr.co.d2net.dto.EquipmentTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.TranscorderHisTbl;
import kr.co.d2net.dto.TransferHisTbl;
import kr.co.d2net.dto.xml.aach.Corner;
import kr.co.d2net.dto.xml.dmcr.KBSExchange;
import kr.co.d2net.dto.xml.dmcr.Metadata;
import kr.co.d2net.dto.xml.internal.Content;
import kr.co.d2net.dto.xml.internal.FindList;
import kr.co.d2net.dto.xml.internal.Job;
import kr.co.d2net.dto.xml.internal.Status;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.dto.xml.meta.Nodes;
import kr.co.d2net.dto.xml.mnc.KBSMetaData;
import kr.co.d2net.listeners.ContentsCopyListener;
import kr.co.d2net.service.BusiPartnerManagerService;
import kr.co.d2net.service.CodeManagerService;
import kr.co.d2net.service.ContentsInstManagerService;
import kr.co.d2net.service.ContentsManagerService;
import kr.co.d2net.service.HttpRequestService;
import kr.co.d2net.service.HttpRequestServiceImpl;
import kr.co.d2net.service.MediaToolInterfaceService;
import kr.co.d2net.service.ProBusiManagerService;
import kr.co.d2net.service.ProFlManagerService;
import kr.co.d2net.service.WorkflowManagerService;
import kr.co.d2net.task.job.TranscodeJobControl;
import kr.co.d2net.task.job.TransferJobControl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

@WebService(endpointInterface = "kr.co.d2net.soap.Navigator")
public class ServiceNavigator implements Navigator {

	private static Log logger = LogFactory.getLog(ServiceNavigator.class);

	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private XmlFileService xmlFileService;
	@Autowired
	private MediaToolInterfaceService mediaToolInterfaceService;
	@Autowired
	private WorkflowManagerService workflowManagerService;
	@Autowired
	private ContentsManagerService contentsManagerService;
	@Autowired
	private ContentsInstManagerService contentsInstManagerService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private CodeManagerService codeManagerService;
	//	@Autowired
	//	private UciRegisterService uciRegisterService;
	//@Autowired
	//private QcReportManagerService qcReportManagerService;
	@Autowired
	private ProBusiManagerService proBusiManagerService;
	@Autowired
	private ProFlManagerService proFlManagerService;
	@Autowired
	private BusiPartnerManagerService busiPartnerManagerService;

	public final static Object o = new Object();

	@Override
	public String soapTest(String xml) throws RemoteException {
		if (logger.isDebugEnabled()) {
			logger.debug("soapTest request xml - " + xml);
		}

		return Boolean.valueOf("true").toString();
	}

	@Override
	public String saveStatus(String xml) throws RemoteException {
		if (logger.isDebugEnabled()) {
			logger.debug("saveStatus request xml - " + xml);
		}
		Workflow workflow = null;
		try {
			workflow = (Workflow) xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("saveStatus xml parsing Error", e);
			Boolean.valueOf("false").toString();
		}

		if (workflow.getStatus() == null
				|| StringUtils.isBlank(workflow.getStatus().getEqState())) {
			logger.error("status is null or eq_state is null!!");
			Boolean.valueOf("false").toString();
		}

		Status status = workflow.getStatus();

		EquipmentTbl equipmentTbl = new EquipmentTbl();
		equipmentTbl.setModDt(Utility.getTimestamp());

		String[] eqId = status.getEqId().split("\\_");
		equipmentTbl.setDeviceid(eqId[0]);
		equipmentTbl.setEqPsCd(eqId[1]);

		String retMsg = "";
		// 장비가 유휴상태일경우 작업할당
		if (status.getEqState().equals("W")) {
			synchronized (o) {
				Job job = new Job();
				if (status.getEqId().startsWith("TF")) { // 트랜스퍼 상태
					try {

						TransferHisTbl transferHisTbl = null;
						if(StringUtils.isNotBlank(status.getProFlid()) && status.getProFlid().equals("35M")) {
							transferHisTbl = TransferJobControl.getHighJob();
						} else {
							transferHisTbl = TransferJobControl.getJob1();
						}

						if (transferHisTbl != null) {
							job.setJobId(transferHisTbl.getSeq());
							job.setFtpPort(transferHisTbl.getPort());
							job.setFtpIp(transferHisTbl.getIp());
							job.setFtpUser(transferHisTbl.getFtpid());
							job.setFtpPass(transferHisTbl.getPassword());
							job.setRemoteDir(transferHisTbl.getRemoteDir());
							job.setFlSz(transferHisTbl.getFlSz());
							job.setSourcePath(transferHisTbl.getFlPath());
							job.setSourceFile(transferHisTbl.getOrgFileNm()+"."+transferHisTbl.getFlExt());

							// /<그룹코드>가 와야함.
							job.setPgmGrpCd(transferHisTbl.getPgmGrpCd());
							job.setPgmCd(transferHisTbl.getPgmCd());
							job.setPgmNm(transferHisTbl.getPgmNm());
							job.setTargetPath(transferHisTbl.getFlPath());
							job.setTargetFile(transferHisTbl.getWrkFileNm()+"."+transferHisTbl.getFlExt());
							job.setMethod(transferHisTbl.getTransMethod());
							job.setCompany(transferHisTbl.getCompany());
							job.setProFlnm(transferHisTbl.getProFlnm());
							job.setVodSmil(transferHisTbl.getVodSmil());

							// 프로그램 Alias 사용 유무 세팅

							Map<String,Object> params = new HashMap<String,Object>();
							params.put("busiPartnerid", transferHisTbl.getBusiPartnerId());
							logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
							logger.debug(">>>>>>>>>>>>>>"+transferHisTbl.getBusiPartnerId());
							logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
							BusiPartnerTbl busiPartnerTbl = busiPartnerManagerService.getBusiPartner(params);
							if(busiPartnerTbl.getProEngYn().equals("N")) // 프로그램 Alias 정보(N: 그룹코드 , K: 한글 ,  E: 영문)
								job.setProEngYn("N");
							else
								job.setProEngYn("Y");

							// 프로그램 Alias 정보 세팅
							if(StringUtils.isNotBlank(transferHisTbl.getPgmGrpCd())){
								String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
								String programGroupMethod = messageSource.getMessage("meta.system.search.group", null, Locale.KOREA);

								Map<String,Object> element = new HashMap<String, Object>();
								Map<String,Object> setEle = new HashMap<String, Object>();

								element.put("group_code_equals", transferHisTbl.getPgmGrpCd());          // 프로그램 코드
								element.put("pids","alias_kor,alias_eng");  // 실제로 받아와야 할 컬럼들.
								String proGroupXml ="";
								try {
									HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
									proGroupXml = httpRequestService.findData(domain+programGroupMethod, Utility.convertNameValue(element));
									//proGroupXml = Utility.connectHttpPostMethod(domain+programGroupMethod, Utility.convertNameValue(element));
								}catch (Exception e) {
									logger.error("Get programGroupXml by MetaHub System createContentML Error", e);
								}
								if(StringUtils.isNotBlank(proGroupXml) && StringUtils.startsWith(proGroupXml, "<?xml")){
									setEle =mediaToolInterfaceService.forwardMetaInfo(proGroupXml);
								}
								if(busiPartnerTbl.getProEngYn().equals("K"))
									job.setProEngNm(StringUtil.removeFormat(String.valueOf(setEle.get("alias_kor"))));
								else
									job.setProEngNm(StringUtil.removeFormat(String.valueOf(setEle.get("alias_eng"))));
							}

							workflow.setJob(job);

							try {
								transferHisTbl.setDeviceid(eqId[0]);
								transferHisTbl.setEqPsCd(eqId[1]);
								transferHisTbl.setModDt(Utility.getTimestamp());
								transferHisTbl.setWorkStatcd("002");
								transferHisTbl.setTrsStrDt(Utility.getTimestamp());

								if(logger.isInfoEnabled()) {
									logger.info("device_id: "+eqId[0]+", eq_ps_cd : "+eqId[1]+", stat_cd: 002");
								}
								workflowManagerService.updateTransferHisState(transferHisTbl);
							} catch (Exception e) {
								logger.error("transfer update error", e);
							}

						}
					} catch (Exception e) {
						logger.error("getTransferHisJob error!", e);
					}
				} else if (status.getEqId().startsWith("TC") || status.getEqId().startsWith("AC")) { // 트랜스코더 상태
					try {
						Workflow workJob = null;
						if(status.getEqId().startsWith("TC"))
							workJob = TranscodeJobControl.getJob();
						else
							workJob = TranscodeJobControl.getAcJob();

						if(workJob != null && workJob.getCtiId() != null && workJob.getCtiId() > 0) {
							equipmentTbl.setCtiId(workJob.getCtiId());

							if(!SystemUtils.IS_OS_WINDOWS) {
								String mnt = messageSource.getMessage("mam.mount.prefix2", null, null);
								int m = 0;
								String curPath = "";
								String[] paths = (mnt+workJob.getTargetPath()).split("\\/");

								File dirF = null;
								for(int i=0; i<paths.length; i++) {
									curPath += paths[i]+"/";
									if(paths[i].equals("mp4")) m = i;
									if(m != i && m > 0) {
										try {
											dirF = new File(curPath);
											if(!dirF.exists()) {
												dirF.mkdirs();

												Runtime r = Runtime.getRuntime();
												Process p = r.exec("chmod -R 777 "+curPath);
												p.waitFor();

												logger.debug("folder created and chmod 777 apply =======>"+curPath);
											}
										} catch (InterruptedException e) {
											logger.error("chmod Error", e);
										} catch (IOException e) {
											logger.error("chmod Error", e);
										}
									}
								}
							}

							// 트랜스코더 작업상태 변경
							for(Job ctiJob : workJob.getJobList()) {
								TranscorderHisTbl transcorderHisTbl = new TranscorderHisTbl();
								transcorderHisTbl.setModDt(Utility.getTimestamp());
								transcorderHisTbl.setDeviceid(eqId[0]);
								transcorderHisTbl.setEqPsCd(eqId[1]);
								transcorderHisTbl.setWorkStatcd("002"); // 요청상태[001] -> 진행상태 변경[002]
								transcorderHisTbl.setSeq(ctiJob.getJobId());
								transcorderHisTbl.setWorkStrDt(Utility.getTimestamp());

								workflowManagerService.updateTranscorderHisState(transcorderHisTbl);

								logger.debug("seq job get Status update - "+ctiJob.getJobId());
							}
							workflow = workJob;
						}


					} catch (ServiceException e) {
						logger.error("getTranscoderHisJob error!", e);
					}
				} else if (status.getEqId().startsWith("VT") || status.getEqId().startsWith("AT")) { // 비디오, 오디오 작업상태 변경
					if(status.getJobId() != null && status.getJobId() > 0) {
						Map<String, Object> params = new HashMap<String, Object>();
						ContentsTbl contentsTbl = null;
						try {
							params.put("ctId", status.getJobId());
							contentsTbl = contentsManagerService.getContents(params);
							if(contentsTbl != null) {

								contentsTbl.setDataStatCd(status.getJobState());
								contentsTbl.setOutSystemId(status.getEqId());

								contentsManagerService.updateContents(contentsTbl);
							} else {
								logger.error("Content Instance Not Found!! - cti_id: "+status.getJobId());
							}
						} catch (ServiceException e) {
							logger.error("Content Inst Status Update Error", e);
						}

					}
				}

			}

			workflow.setStatus(null);

			retMsg = xmlStream.toXML(workflow);
			logger.debug(retMsg);

			equipmentTbl.setWorkStatcd("000");
			equipmentTbl.setPrgrs("0");
			try {
				workflowManagerService.updateEquipmentState(equipmentTbl);
			} catch (ServiceException e) {
				logger.error("Equipment saveStatues Error", e);
			}
		} else if (status.getEqState().equals("B")) { // 작업이 진행중일경우
			if (status.getEqId().startsWith("TF")) { // 트랜스퍼 상태
				TransferHisTbl transferHisTbl = new TransferHisTbl();
				try {
					transferHisTbl.setDeviceid(eqId[0]);
					transferHisTbl.setEqPsCd(eqId[1]);
					transferHisTbl.setModDt(Utility.getTimestamp());
					transferHisTbl.setSeq(status.getJobId());

					if (status.getJobState().equals("S")) {
						transferHisTbl.setPrgrs(status.getProgress());
						transferHisTbl.setWorkStatcd("002");
					} else if (status.getJobState().equals("E")) {
						transferHisTbl.setPrgrs(status.getProgress());
						transferHisTbl.setWorkStatcd(StringUtils.defaultString(status.getErrorCd(), "400"));
						transferHisTbl.setTrsEndDt(Utility.getTimestamp());

						equipmentTbl.setCtiId(0L);
						equipmentTbl.setWorkStatcd("000");
						equipmentTbl.setPrgrs("0");
					} else if (status.getJobState().equals("C")) {
						transferHisTbl.setPrgrs("100");
						transferHisTbl.setWorkStatcd("200");
						transferHisTbl.setTrsEndDt(Utility.getTimestamp());

						try {
							/*
							 * 완료일경우 메타허브에 서비스URL을 등록요청
							 * 트랜스퍼 상태 저장
							 */
							Map<String,Object> params = new HashMap<String, Object>();
							ContentsInstTbl contentsInstTbl = new ContentsInstTbl();
							ProFlTbl proFlTbl = new ProFlTbl();

							params.put("seq", status.getJobId());

							contentsInstTbl = contentsInstManagerService.getContentsInstForwardMeta(params);
							if(StringUtils.defaultString(contentsInstTbl.getSrvUrl(), "N").equals("Y")){
								if(contentsInstTbl.getProFlid() != null) {
									params.put("proFlid", contentsInstTbl.getProFlid());
									proFlTbl = proFlManagerService.getProFl(params);
								}

								params.clear();
								params.put("essence_id",String.valueOf(status.getJobId()));                     //에센스 허브 아이디                                  

								params.put("program_code",contentsInstTbl.getPgmCd());                          //프로그램코드               
								params.put("program_id",contentsInstTbl.getPgmId());                           //회별 프로그램 아이디       
								params.put("file_size",String.valueOf(contentsInstTbl.getFlSz()));                        //용량           
								params.put("filename",contentsInstTbl.getWrkFileNm()+"."+proFlTbl.getExt());                         //파일이름       
								params.put("segment_id",contentsInstTbl.getPgmId()+"-"+contentsInstTbl.getSegmentId());                     //TV일 경우 세그먼트 회차    
								params.put("essence_type1",contentsInstTbl.getCtCla());                  //에센스 타입1               
								params.put("essence_type2",contentsInstTbl.getCtTyp());                  //에센스 타입2               
								params.put("service_biz",contentsInstTbl.getAlias());                      //서비스 사업자              

								params.put("program_id_key","");                 //주간 편성 아이디           
								params.put("segment_code",contentsInstTbl.getPgmCd()+"-"+contentsInstTbl.getSegmentId());                   //세그먼트 프로그램 코드
								params.put("segment_code_radio",contentsInstTbl.getPgmCd()+"-"+contentsInstTbl.getSegmentId());                   //세그먼트 프로그램 코드  
								params.put("segment_id_radio",contentsInstTbl.getPgmId()+"-"+contentsInstTbl.getSegmentId());               //라디오일 경우 세그먼트 회차
								params.put("service_status","Y");                 //서비스 상태                
								params.put("audio_bitrate",proFlTbl.getAudBitRate());                  //오디오 비트레이트          
								params.put("audio_channel",proFlTbl.getAudChan());                  //오디오 채널                
								params.put("audio_codec",proFlTbl.getAudCodec());                    //오디오 코덱                
								params.put("audio_ext",proFlTbl.getExt());                      //오디오 확장자              
								params.put("audio_keyframe","");                 //keyframe                   
								params.put("audio_sampling",proFlTbl.getAudSRate());                   //오디오 샘플링  
								params.put("bitrate",proFlTbl.getServBit());                          //비트레이트     
								params.put("bitrate_code",proFlTbl.getFlNameRule());                     //비트레이트코드 
								params.put("file_format",proFlTbl.getExt());                      //파일 타입      
								params.put("framecount","");                       //비디오 F/S     
								params.put("height",proFlTbl.getVdoVert());                           //비디오 세로    
								params.put("video_aspect_ratio","");               //화면 비율      
								params.put("video_codec",proFlTbl.getVdoCodec());                      //비디오 코덱    
								params.put("video_quality_condition",proFlTbl.getPicKind());          //화질표시       
								params.put("video_resoution","");                  //해상도         
								params.put("whaline",proFlTbl.getVdoSync());                          //비디오 종횡맞춤
								params.put("width",proFlTbl.getVdoHori()); //비디오 가로

								// 메타허브에 SMIL 여부를 넘겨줘야 함 2013.04.25
								if(StringUtils.defaultIfEmpty(contentsInstTbl.getVodSmil(), "N").equals("Y")) {
									String smilName = contentsInstTbl.getWrkFileNm().substring(0, contentsInstTbl.getWrkFileNm().lastIndexOf("_"))+".smil";
									if(logger.isDebugEnabled()) {
										logger.debug("smil_file: "+smilName);
									}
									params.put("smil_file", smilName);
								}
								
								if(logger.isInfoEnabled()) {
									logger.info("essence_id: "+params.get("essence_id"));
									logger.info("program_id: "+params.get("program_id"));
									logger.info("program_code: "+params.get("program_code"));
									logger.info("segment_id: "+params.get("segment_id"));
									logger.info("segment_code: "+params.get("segment_code"));
								}

								String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
								String createMethod = messageSource.getMessage("meta.system.url.forward.create", null, Locale.KOREA);

								String rXml = "";

								HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
								rXml = httpRequestService.findData(domain+createMethod, Utility.convertNameValue(params));
								//rXml = Utility.connectHttpPostMethod(domain+createMethod, Utility.convertNameValue(params));
								if(logger.isDebugEnabled())
									logger.debug("rXml"+rXml);

								if(rXml != null && rXml.indexOf("SUCCESS") > -1){
									transferHisTbl.setMetaIns("C");
								}else{
									transferHisTbl.setMetaIns("E");
								}

							}
						} catch (Exception e) {
							logger.error("MetaHub Service URL Insert Error", e);
						}

						equipmentTbl.setCtiId(0L);
						equipmentTbl.setWorkStatcd("000");
						equipmentTbl.setPrgrs("0");

					} else {
						// 트랜스퍼 상태 저장
						transferHisTbl.setPrgrs(status.getProgress());
						transferHisTbl.setWorkStatcd("002");

						equipmentTbl.setWorkStatcd("002");
						equipmentTbl.setPrgrs(status.getProgress());
					}

					workflowManagerService.updateTransferHisState(transferHisTbl);
					if(logger.isDebugEnabled()) {
						logger.debug("seq: "+transferHisTbl.getSeq()+", cti_id : "+transferHisTbl.getCtiId()+", stat_cd: "+transferHisTbl.getWorkStatcd()+", progress "+transferHisTbl.getPrgrs());
					}
				} catch (Exception e) {
					logger.error("saveStatues Error", e);
				}

				try {
					workflowManagerService.updateEquipmentState(equipmentTbl);
				} catch (ServiceException e) {
					logger.error("Equipment saveStatues Error", e);
				}

			} else if (status.getEqId().startsWith("TC") || status.getEqId().startsWith("AC")) { // 트랜스코더 상태

				TranscorderHisTbl transcorderHisTbl = new TranscorderHisTbl();

				// 트랜스코더 상태 저장
				try {
					transcorderHisTbl.setDeviceid(eqId[0]);
					transcorderHisTbl.setEqPsCd(eqId[1]);
					transcorderHisTbl.setModDt(Utility.getTimestamp());
					transcorderHisTbl.setSeq(status.getJobId());

					if (status.getJobState().equals("C")) {
						transcorderHisTbl.setPrgrs("100");
						transcorderHisTbl.setWorkStatcd("200");
						transcorderHisTbl.setWorkEndDt(Utility.getTimestamp());

						equipmentTbl.setCtiId(0L);
						equipmentTbl.setWorkStatcd("000");
						equipmentTbl.setPrgrs("0");
					} else if (status.getJobState().equals("E")) {
						transcorderHisTbl.setPrgrs(status.getProgress());
						transcorderHisTbl.setWorkStatcd(StringUtils.defaultString(status.getErrorCd(), "500"));

						equipmentTbl.setCtiId(0L);
						equipmentTbl.setWorkStatcd("000");
						equipmentTbl.setPrgrs("0");
					} else {
						transcorderHisTbl.setPrgrs(status.getProgress());
						transcorderHisTbl.setWorkStatcd("002");

						equipmentTbl.setWorkStatcd("002");
						equipmentTbl.setPrgrs(status.getProgress());
					}

					workflowManagerService.updateTranscorderHisState(transcorderHisTbl);
				} catch (ServiceException e) {
					logger.error("saveStatues Error", e);
				}

			}

			try {
				workflowManagerService.updateEquipmentState(equipmentTbl);
			} catch (ServiceException e) {
				logger.error("Equipment saveStatues Error", e);
			}
		}

		return retMsg;
	}

	@Override
	public String findDailySchedule(String xml) throws RemoteException {
		if (logger.isDebugEnabled()) {
			logger.debug("findDailySchedule request xml - " + xml);
		}
		/*Workflow workflow = null;
		try {
			workflow = (Workflow) xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("findDailySchedule xml parsing Error", e);
			Boolean.valueOf("false").toString();
		}*/
		return null;
	}

	@Override
	public String saveContents(String xml) throws RemoteException {
		if (logger.isInfoEnabled()) {
			logger.info("saveContents request xml - " + xml);
		}
		Workflow workflow = null;
		try {
			workflow = (Workflow) xmlStream.fromXML(xml);
			if (workflow.getPgm() != null && workflow.getSgm() != null
					&& workflow.getContent() != null
					&& workflow.getContentInst() != null) {

				Map<String, Object> params = new HashMap<String, Object>();

				ContentsTbl contentsTbl = null;
				if (workflow.getContent().getCtId() != null && workflow.getContent().getCtId() > 0) {
					params.put("ctId", workflow.getContent().getCtId());

					contentsTbl = contentsManagerService.getContents(params);
					contentsTbl.setCtId(null);
				} else {
					contentsTbl = new ContentsTbl();
					contentsTbl.setBrdDd(workflow.getContent().getBrdDd());
					contentsTbl.setUseYn("Y");
				}
				
				/*
				 * 2014.06.28
				 * 비디오 트리머에서 프로그램을 조회했을경우 변경된 프로그램ID, 프로그램명을 사용해야 함.
				 * 이전엔 원본의 프로그램 정보를 그대로 이용했기때문에 변경된 프로그램으로 적용되지 않았음.
				 */
				contentsTbl.setPgmCd(workflow.getPgm().getPgmCd());
				contentsTbl.setPgmGrpCd(workflow.getPgm().getPgmGrpCd());
				contentsTbl.setPgmId(workflow.getPgm().getPgmId());
				contentsTbl.setPgmNm(workflow.getPgm().getPgmNm());

				/**
				 * 프로그램 코드를 기준으로 메타허브에 프로그램 영문명 조회
				 * 데이타 저장시 공백, 여백 처리
				 */
				/** 에센스를 전송할때 Alias 정보를 확인하는것으로 로직 변경 (2012-03-21)
				if(StringUtils.isNotBlank(contentsTbl.getPgmCd())){
					String domain = messageSource.getMessage("meta.system.domain", null,
							Locale.KOREA);
					String programGroupMethod = messageSource.getMessage("meta.system.search.group.program.read",
							null, Locale.KOREA);

					Map<String,Object> element = new HashMap<String, Object>();
					Map<String,Object> setEle = new HashMap<String, Object>();
						element.put("program_code",contentsTbl.getPgmCd());

						String proGroupXml ="";
						try {
							proGroupXml = Utility.connectHttpPostMethod(domain+programGroupMethod, Utility.convertNameValue(element));
						}catch (Exception e) {
							e.printStackTrace();
							logger.error("Get programGroupXml by MetaHub System createContentML Error", e);
						}
						if(StringUtils.isNotBlank(proGroupXml)&&StringUtils.startsWith(proGroupXml, "<?xml")){
							setEle = mediaToolInterfaceService.forwardContentsMLNodeInfo(proGroupXml);
					}
						contentsTbl.setGrpAliasEng(
								StringUtil.removeFormat(String.valueOf(setEle.get("alias_eng"))));
				}
				 **/

				if(workflow.getEqId().startsWith("VT")){
					contentsTbl.setTrimmSte("Y");
					contentsTbl.setCtLeng(workflow.getContentInst().getCtLeng());
					
					/*
					 * 트리밍된 영상에 사용된 LIVE, DMCR 원본 영상의 ID를 저장하여
					 * 전송 조회시 원본 영상의 출처를 검색 조건으로 사용하도록 한다.
					 * 2014-02-21
					 */
					if(workflow.getContent().getCtId() != null && workflow.getContent().getCtId() > 0) {
						contentsTbl.setCtSeq(workflow.getContent().getCtId().intValue());
					}
				}else if(workflow.getEqId().startsWith("AT")){
					contentsTbl.setTrimmSte("Y");
				}else{
					contentsTbl.setTrimmSte("N");
				}

				/**
				 *  KBS미디어 주상필K 정리(2012-02-09)
				 * - 에센스타입1(ctCla) : 00-방송본, 01-촬영본
				 * - 에센스타입2(ctTyp) : 00-본방, 01-예고편, 02-타이틀, 03-인터뷰, 04-현장스케치, 05-보이는라디오, 06-뮤직비디오, 07-기타
				 */
				contentsTbl.setCtCla(workflow.getContent().getCtCla());
				contentsTbl.setCtTyp(workflow.getContent().getCtTyp());

				contentsTbl.setChannelCode(workflow.getPgm().getChannel());
				contentsTbl.setSegmentId(workflow.getSgm().getSgmId());
				contentsTbl.setSegmentNm(workflow.getSgm().getSgmNm());
				contentsTbl.setCtNm(workflow.getContent().getCtNm());
				contentsTbl.setRegDt(Utility.getTimestamp());

				if(StringUtils.isBlank(workflow.getEqId())){
					contentsTbl.setRegrid("");
				}else{
					if(workflow.getEqId().startsWith("LI")){
						contentsTbl.setRegrid("LIVE");
					}else if(workflow.getEqId().startsWith("VT")){
						contentsTbl.setRegrid("VTRIM");
					}else if(workflow.getEqId().startsWith("AT")){
						contentsTbl.setRegrid("ATRIM");
					}else if(workflow.getEqId().startsWith("VI")){
						contentsTbl.setRegrid("VCR");
					}else{
						contentsTbl.setRegrid("");
					}
				}

				ContentsInstTbl contentsInstTbl = null;
				if (workflow.getContent().getCtId() != null && workflow.getContent().getCtId() > 0) {
					contentsInstTbl = contentsInstManagerService.getContentsInst(params);
					contentsInstTbl.setCtId(null);
					contentsInstTbl.setCtiId(null);
				} else {
					contentsInstTbl = new ContentsInstTbl();

					contentsInstTbl.setCtiFmt(workflow.getContentInst().getCtiFmt());
					contentsInstTbl.setBitRt(workflow.getContentInst().getBitRt());
					contentsInstTbl.setColorCd(workflow.getContentInst().getColor());
					contentsInstTbl.setDrpFrmYn(workflow.getContentInst().getDrpFrmYn());
					contentsInstTbl.setUseYn("Y");
				}

				contentsInstTbl.setFlPath(workflow.getContentInst().getFlPath().replaceAll("\\\\", "/"));
				contentsInstTbl.setFlSz(workflow.getContentInst().getFlSize());

				String flNm = workflow.getContentInst().getFlNm();
				if(flNm.indexOf(".") > -1) {
					String[] names = flNm.split("\\.");
					contentsInstTbl.setOrgFileNm(names[0]);
					contentsInstTbl.setFlExt(names[1]);
				} else {
					contentsInstTbl.setOrgFileNm(workflow.getContentInst().getFlNm());
				}

				if(workflow.getEqId().startsWith("AT"))
					contentsInstTbl.setAvGubun("A");
				else
					contentsInstTbl.setAvGubun("V");

				contentsInstTbl.setSom(workflow.getContentInst().getSom());
				contentsInstTbl.setEom(workflow.getContentInst().getEom());
				contentsInstTbl.setCtLeng(workflow.getContentInst().getCtLeng());
				contentsInstTbl.setVdHresol(workflow.getContentInst().getVdHresol());
				contentsInstTbl.setVdVresol(workflow.getContentInst().getVdVresol());
				contentsInstTbl.setRegDt(Utility.getTimestamp());
				contentsInstTbl.setRegrid(workflow.getEqId());
				contentsInstTbl.setCtiFmt(workflow.getContentInst().getCtiFmt());
				/**
				 * 네이밍 수정순번 적용
				 */
				contentsTbl.setPgmNum(codeManagerService.getContentsPgmNum(contentsTbl));

				if(logger.isInfoEnabled()) {
					logger.info("ct_Id: "+contentsTbl.getCtId());
					logger.info("pgm_id: "+contentsTbl.getPgmId());
					logger.info("pgm_cd: "+contentsTbl.getPgmCd());
					logger.info("pgm_num: "+contentsTbl.getPgmNum());
				}
				contentsManagerService.insertContents(contentsTbl, contentsInstTbl);

				// 트리밍 영상은 트랜스코드 요청이 즉시 되어야한다.
				if(StringUtils.defaultIfEmpty(contentsTbl.getTrimmSte(), "N").equals("Y")) {
					params.clear();
					try {
						List<TranscorderHisTbl> traHisTbls = new ArrayList<TranscorderHisTbl>();
						params.put("pgmCd", contentsTbl.getPgmCd());
						params.put("ctTyp", contentsTbl.getCtTyp());
						List<ProFlTbl> proFlTbls = proBusiManagerService.findPgmProBusi(params);
						for (ProFlTbl proFlTbl : proFlTbls) {

							TranscorderHisTbl traHisTbl = new TranscorderHisTbl();

							traHisTbl.setCtiId(contentsInstTbl.getCtiId());
							traHisTbl.setProFlid(Integer.parseInt(proFlTbl.getProFlid()));
							traHisTbl.setFlExt(proFlTbl.getExt());
							traHisTbl.setUseYn("Y");
							traHisTbl.setWorkStatcd("000");
							if(workflow.getEqId().startsWith("AT"))
								traHisTbl.setJobGubun("A"); // Video : V , Audio : A
							else
								traHisTbl.setJobGubun("V");
							traHisTbl.setRegDt(Utility.getTimestamp());

							if(StringUtils.isNotBlank(proFlTbl.getVdoBitRate()) && proFlTbl.getVdoBitRate().equals("35000")) {
								traHisTbl.setPriority("1");
							} else {
								traHisTbl.setPriority("5");
							}

							traHisTbls.add(traHisTbl);
						}
						workflowManagerService.insertTranscorderHis(traHisTbls);
					} catch (Exception e) {
						logger.error("트리밍 후 트랜스코딩 요청 에러", e);
					}
				}
			}
		} catch (Exception e) {
			logger.error("saveContents xml parsing Error", e);
			Boolean.valueOf("false").toString();
		}
		return Boolean.valueOf("true").toString();
	}

	@Override
	public String saveReports(String xml) throws RemoteException {
		if (logger.isInfoEnabled()) {
			logger.info("saveReports request xml - " + xml);
		}
		Workflow workflow = null;
		try {
			workflow = (Workflow) xmlStream.fromXML(xml);
			Content content = workflow.getContent();

			EquipmentTbl equipmentTbl = new EquipmentTbl();

			TranscorderHisTbl transcorderHisTbl = workflowManagerService.getTranscoderHisJobID(content.getJobId());
			transcorderHisTbl.setSeq(content.getJobId());
			transcorderHisTbl.setModDt(Utility.getTimestamp());

			// 트랜스코드 장비 초기화
			equipmentTbl.setDeviceid(transcorderHisTbl.getDeviceid());
			equipmentTbl.setEqPsCd(transcorderHisTbl.getEqPsCd());
			equipmentTbl.setWorkStatcd("000");
			equipmentTbl.setCtiId(0L);
			equipmentTbl.setPrgrs("0");
			workflowManagerService.updateEquipmentState(equipmentTbl);

			if (transcorderHisTbl != null && transcorderHisTbl.getCtId() > 0) {

				// 에러가 없거나 QC Report 에러라면 영상정보는 저장
				//if (StringUtils.isBlank(content.getJobErrCode()) || content.getJobErrCode().equals("Q")) {

				if(content.getJobState().equals("C")) {

					ContentsInstTbl contentsInstTbl = new ContentsInstTbl();
					contentsInstTbl.setAudSampFrq(transcorderHisTbl.getAudSRate());
					contentsInstTbl.setBitRt(transcorderHisTbl.getVdoBitRate());
					contentsInstTbl.setColorCd(StringUtils.defaultString(transcorderHisTbl.getColorCd(), "100"));
					contentsInstTbl.setCtId(transcorderHisTbl.getCtId());
					contentsInstTbl.setFlPath(transcorderHisTbl.getFlPath().replace("mp2", "mp4").replace("rms", "mp4").replace("\\\\", "/"));
					contentsInstTbl.setFlExt(transcorderHisTbl.getNewFlExt().toLowerCase());
					contentsInstTbl.setVdVresol(transcorderHisTbl.getVdoVert());
					contentsInstTbl.setVdHresol(transcorderHisTbl.getVdoHori());
					contentsInstTbl.setProFlid(transcorderHisTbl.getProFlid());
					contentsInstTbl.setFlSz(content.getFileSize());
					contentsInstTbl.setFrmPerSec(content.getFrmPerSec());
					contentsInstTbl.setAvGubun(content.getJobGb());

					/*
					 * 영상에대한 포맷코드를 조회할때 비디오, 오디오를 구분하여야한다.
					 * 비디오, 오디오가 확장자가 동일한 포맷이 존재한다. 예) mp4
					 * 작업완료 메세지에 '작업구분' 태그를 추가 (2012.01.05) => job_gb 비디오[V], 오디오[A]
					 */

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("clfCd", "FMAT");

					if(content.getJobGb().equals("A")) {
						params.put("sclCd", contentsInstTbl.getFlExt()+"4");
						logger.debug("saveReport sclCd : "+contentsInstTbl.getFlExt()+"4");
					} else {
						transcorderHisTbl.getAudBitRate();
						params.put("sclCd", contentsInstTbl.getFlExt());
						logger.debug("saveReport sclCd : "+contentsInstTbl.getFlExt());
					}
					CodeTbl codeTbl = codeManagerService.getCode(params);
					contentsInstTbl.setCtiFmt(codeTbl.getClfNm());  

					String prefix = messageSource.getMessage("mam.mount.prefix2", null, null);
					String wrkFileNm = "";
					String segmentNm = "";
					String pgmNm = "";
					try {
						ContentsTbl contentsTbl = workflowManagerService.getTranscodeInfo(content.getJobId());
						if(content.getJobGb().equals("A")) {
							contentsInstTbl.setOrgFileNm(transcorderHisTbl.getOrgFileNm()+"_"+transcorderHisTbl.getServBit());
							contentsTbl.setFlExt(contentsInstTbl.getFlExt()+"4");
						} else {
							contentsInstTbl.setOrgFileNm(transcorderHisTbl.getOrgFileNm()+"_"+transcorderHisTbl.getServBit()+"_"+transcorderHisTbl.getPicKind());
							contentsTbl.setFlExt(contentsInstTbl.getFlExt());
						}

						/*
						 * 그룹코드가 없다면 메타허브에서 pgm_id를 이용하여 조회하여 재맵핑한다.
						 * 그룹코드가 없다면 프로그램 코드로 맵핑한 후 파일네임 규칙을 생성한다.
						 */
						if(StringUtils.isBlank(contentsTbl.getPgmGrpCd())) {
							if(logger.isInfoEnabled()) {
								logger.info("pgm_grp_cd is null meta req: ct_id: "+contentsTbl.getCtId()+", pgm_id: "+contentsTbl.getPgmId());
							}
							params.clear();

							params.put("program_id", contentsTbl.getPgmId());

							String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);
							String method = messageSource.getMessage("meta.system.search.program.read", null, Locale.KOREA);

							/*
							 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
							 * param  pgmId;
							 */
							/***************테스트할 때 try 구문 전체 주석. ***************/
							Map<String, Object> element = null;
							try {
								HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
								String programXml = httpRequestService.findData(domain+method, Utility.convertNameValue(params));
								//String programXml = Utility.connectHttpPostMethod(domain+method, Utility.convertNameValue(params));
								element = mediaToolInterfaceService.forwardContentsMLNodeInfo(programXml);

								if(element.get("group_code") != null && StringUtils.isNotBlank(String.valueOf(element.get("group_code")))){
									contentsTbl.setPgmGrpCd(String.valueOf(element.get("group_code")));
								}else{
									contentsTbl.setPgmGrpCd(contentsTbl.getPgmCd());
								}
							}catch (Exception e) {
								logger.error("회차정보조회 오류", e);
							}
							
							/* 테스트할 때 아래 주석 제거 */
							//contentsTbl.setPgmGrpCd(contentsTbl.getPgmCd());
						}

						try {
							contentsManagerService.updateContents(contentsTbl);
						} catch (Exception e) {
							logger.error("contents group_code update error", e);
						}


						wrkFileNm = codeManagerService.getWrkFileName(contentsTbl,contentsInstTbl);
						if(logger.isInfoEnabled()) {
							logger.info("wrkFileNm==============================>"+wrkFileNm);
						}
						contentsInstTbl.setWrkFileNm(wrkFileNm);

						segmentNm = contentsTbl.getSegmentNm();
						pgmNm = contentsTbl.getPgmNm();

					} catch (Exception e) {
						logger.error("wrk_file_nm create error", e);
					}

					Long ctiId = contentsInstManagerService.insertContentsInst(contentsInstTbl);
					// 프로파일 히스토리에 생성된 프로파일의 인스턴스 ID를 업데이트한다.
					transcorderHisTbl.setWrkCtiId(ctiId);
					transcorderHisTbl.setWorkStatcd("200");

					workflowManagerService.updateTranscorderHisState(transcorderHisTbl);

					// ContentML 생성
					/***************테스트할 때 try 구문 전체 주석 */
					try {
						String contentML = contentsManagerService.createContentML(transcorderHisTbl.getPgmId(), transcorderHisTbl.getProFlid());
						if(logger.isInfoEnabled()) {
							logger.info("contentML path : "+prefix+contentsInstTbl.getFlPath()+contentsInstTbl.getOrgFileNm()+"."+contentsInstTbl.getFlExt()+".xml");
						}
						xmlFileService.StringToFile(contentML, prefix+contentsInstTbl.getFlPath(), contentsInstTbl.getOrgFileNm()+"."+contentsInstTbl.getFlExt()+".xml");
					} catch (Exception e) {
						logger.error("saveReport - ContentML 생성 에러", e);
					}
					
					/*
					 * Audio Profile 96k 파일을 클로즈드캡션 시스템과 연동을 위해 
					 * 스토리지 특정 위치로 복사를 해준다.
					 */

					boolean isCopy = false;
					if(logger.isInfoEnabled()) {
						logger.info("aud bitrate: "+transcorderHisTbl.getAudBitRate());
						logger.info("new fl_ext: "+transcorderHisTbl.getNewFlExt().toLowerCase());
						logger.info("av_gubun: "+transcorderHisTbl.getAvGubun());
					}
					if(transcorderHisTbl.getAudBitRate().equals("96") && transcorderHisTbl.getNewFlExt().toLowerCase().equals("mp3")
							&& transcorderHisTbl.getAvGubun().equals("V")) {
						isCopy = true;
					}
					if(isCopy) {
						try {
							if(logger.isInfoEnabled()) {
								logger.info("copy fl_path: "+contentsInstTbl.getFlPath());
								logger.info("copy org_file_nm: "+contentsInstTbl.getOrgFileNm());
								logger.info("copy wrk_file_nm: "+wrkFileNm);
							}
							Event<CopyEventArgs> copyEvent = new Event<CopyEventArgs>();
							copyEvent.addEventHandler(new ContentsCopyListener());

							TransferHisTbl transferHisTbl = new TransferHisTbl();
							transferHisTbl.setSeq(content.getJobId());
							transferHisTbl.setFlPath(contentsInstTbl.getFlPath());
							transferHisTbl.setOrgFileNm(contentsInstTbl.getOrgFileNm());
							transferHisTbl.setFlExt(contentsInstTbl.getFlExt());
							transferHisTbl.setWrkFileNm(wrkFileNm);
							copyEvent.raiseEvent(null, new CopyEventArgs(transferHisTbl));
						} catch (Exception e) {
							logger.error("Caption Event Add Error", e);
						}
					}

					boolean smil = false;
					// 서비스 파일명 생성
					try {
						Map<String, Object> vodParams = new HashMap<String, Object>();
						List<TransferHisTbl> transferHisList = new ArrayList<TransferHisTbl>();

						List<TransferHisTbl> transferHisTbls = workflowManagerService.findTransferBusiJob(content.getJobId());
						for(TransferHisTbl transferHisTbl : transferHisTbls) {

							// Adaptive Streamming Service XML Create
							if(!smil && StringUtils.defaultString(transferHisTbl.getVodSmil(), "N").equals("Y")) {

								vodParams.put("busiPartnerId", transferHisTbl.getBusiPartnerId());
								vodParams.put("ctiId", transferHisTbl.getCtiId());
								smil = true;
							}

							// CDN 전송요청 등록
							transferHisTbl.setRegDt(Utility.getTimestamp());
							transferHisTbl.setCtiId(ctiId);
							transferHisTbl.setCtId(contentsInstTbl.getCtId());

							//String flPath = prefix+transferHisTbl.getFlPath()+transferHisTbl.getOrgFileNm()+"."+contentsInstTbl.getFlExt();
							String flPath = transferHisTbl.getFlPath()+transferHisTbl.getOrgFileNm()+"."+transferHisTbl.getFlExt();
							if(logger.isInfoEnabled()) {
								logger.debug("ftp file path : "+flPath);
							}

							transferHisTbl.setFlPath(flPath.replace("mp2", "mp4").replace("rms", "mp4"));
							transferHisTbl.setFlSz(content.getFileSize());
							transferHisTbl.setWrkFileNm(wrkFileNm);
							transferHisTbl.setSegmentNm(segmentNm);
							transferHisTbl.setPgmNm(pgmNm);
							transferHisTbl.setWorkStatcd("000");
							transferHisTbl.setPrgrs("0");
							transferHisTbl.setRetryCnt(0);
							transferHisTbl.setUseYn("Y");
							transferHisTbl.setPriority(5);
							transferHisTbl.setRegDt(Utility.getTimestamp());

							if(contentsInstTbl.getCtiFmt().startsWith("1"))
								transferHisTbl.setJobGubun("V");
							else if(contentsInstTbl.getCtiFmt().startsWith("3"))
								transferHisTbl.setJobGubun("A");

							transferHisList.add(transferHisTbl);
						}

						workflowManagerService.insertTransferHis(transferHisList);

						/* 지속적으로 오류가 발생하므로 임시 제거 [2012.07.20]
						for(TransferHisTbl transferHisTbl : transferHisList) {
							try {
								// 완료일경우 파일명을 UCI에 등록요청
								uciRegisterService.addRegiester(transferHisTbl);
								logger.debug("UCI Registerd!! "+transferHisTbl.getPgmNm());
							} catch (Exception e) {
								logger.error("UCI Registerd Error", e);
							}
						}
						 */

						transferHisList.clear();

						// K-Player에 전송할 SMIL(XML) 생성
						try {
							logger.debug("SMIL Create Value : "+smil+", vodParams: "+vodParams.toString());
							if(smil) {
								List<TransferHisTbl> transferHisTbls2 = workflowManagerService.findTrsCtiBusi(vodParams);
								String smilXml = contentsManagerService.createSMIL(transferHisTbls2);

								xmlFileService.StringToFile(smilXml, prefix+contentsInstTbl.getFlPath(), contentsInstTbl.getOrgFileNm().substring(0, contentsInstTbl.getOrgFileNm().indexOf("_"))+".smil");
								if(logger.isInfoEnabled()) {
									logger.info("SMIL Create file : "+contentsInstTbl.getOrgFileNm().substring(0, contentsInstTbl.getOrgFileNm().indexOf("_"))+".smil");
								}
							}
						} catch (Exception e) {
							logger.error("K-Player smile XML Create Error", e);
						}

					} catch (Exception e) {
						logger.error("getWrapperInfo Error", e);
					}

					/*
					 * 8,0,00:00:11.410,V,E,58.50,29.25,Y:1.#J U:1.#J V:1.#J *:1.#J  
					 * [0] : index
					 * [1] : frame
					 * [2] : timecode
					 * [3] : V[Video], A[Audio]
					 * [4] : error code {B: black, N:noise, M:mutt, C:color, E:etc}
					 * [5] : qlty
					 * [6] : psnr
					 */
					/*
					try {
						Report report = workflow.getReport();
						if(report != null && StringUtils.isNotBlank(report.getQcSuccess()) && report.getQcSuccess().equals("Y")) {
							File f = new File(prefix+contentsInstTbl.getFlPath() + contentsInstTbl.getOrgFileNm()
									+ "."+contentsInstTbl.getFlExt()
									+ "_qc.log");

							BufferedReader in = new BufferedReader(new FileReader(f));
							String line = "";
							while((line = in.readLine()) != null) {
								if(line.startsWith("#") || line.startsWith("[error]")) {
									continue;
								} else if(line.startsWith("[success]")) {
									break;
								} else {
									String[] lines = line.split("\\,");
									QcReportTbl qcReportTbl = new QcReportTbl();
									qcReportTbl.setSeq(content.getJobId());
									qcReportTbl.setReportId(lines[0]);
									qcReportTbl.setFrameNo(lines[1]);
									qcReportTbl.setRtTcd(lines[2]);
									qcReportTbl.setRtGb(lines[3]);
									qcReportTbl.setErrGb(lines[4]);
									qcReportTbl.setQlty(lines[5]);
									qcReportTbl.setPsnr(lines[6]);

									if(StringUtils.isNotBlank(qcReportTbl.getErrGb())) {
										if(qcReportTbl.getErrGb().equals("B")) {
											qcReportTbl.setReInfo("블링크");
										} else if(qcReportTbl.getErrGb().equals("C")) {
											qcReportTbl.setReInfo("화면조정");
										} else if(qcReportTbl.getErrGb().equals("N")) {
											qcReportTbl.setReInfo("노이즈");
										} else if(qcReportTbl.getErrGb().equals("M")) {
											qcReportTbl.setReInfo("소리없는 구간");
										} else {
											qcReportTbl.setReInfo("기타오류");
										}
										qcReportManagerService.insertQcReport(qcReportTbl);
									}
								}
							}
						}
					} catch (Exception e) {
						logger.error("qc reqport insert error", e);
					}
					 */
				} else if(content.getJobState().equals("E")){
					// 트랜스코드 에러
					transcorderHisTbl.setWorkStatcd(StringUtils.defaultString(content.getJobErrCode(), "500"));
					workflowManagerService.updateTranscorderHisState(transcorderHisTbl);
				}

			}
		} catch (Exception e) {
			logger.error("saveReports xml parsing Error", e);
			return Boolean.valueOf("false").toString();
		}

		logger.debug("saveReport success! job_id : "+workflow.getContent().getJobId());

		return Boolean.valueOf("true").toString();
	}

	@Override
	public String findContents(String xml) throws RemoteException {
		if (logger.isInfoEnabled()) {
			logger.info("findContents request xml - " + xml);
		}
		Workflow workflow = null;
		try {
			workflow = (Workflow) xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("findContents xml parsing Error", e);
			Boolean.valueOf("false").toString();
		}

		if (StringUtils.isBlank(workflow.getGubun())
				|| workflow.getGubun().length() != 1) {
			throw new RemoteException(
					"gubun element is blank or gubun value length is not 1 : "
							+ workflow.getGubun());
		}

		String retXml = "";
		try {
			FindList list = mediaToolInterfaceService.reqMediaInfo(workflow);
			workflow.setList(list);

			retXml = xmlStream.toXML(workflow);
		} catch (Exception e) {
			logger.error("findContents error", e);
			Boolean.valueOf("false").toString();
		}

		return retXml;
	}

	@Override
	public String saveFileIngest(String xml) throws RemoteException {
		if (logger.isInfoEnabled()) {
			logger.info("saveFileIngest request xml - " + xml);
		}
		Workflow workflow = null;
		try {

			String prefix = messageSource.getMessage("mam.mount.prefix", null,Locale.KOREA);
			String highPath = messageSource.getMessage("mnc.source.target", null, Locale.KOREA);

			workflow = (Workflow) xmlStream.fromXML(xml);
			if (workflow.getMnc() != null) {

				ContentsTbl contentsTbl = null;
				ContentsInstTbl contentsInstTbl = null;

				String orgPath = highPath + File.separator+ workflow.getMnc().getSourcePath().replaceAll("\\\\", "/");

				try {
					Runtime r = Runtime.getRuntime();
					Process p = r.exec("chmod -R 777 "+prefix+orgPath);
					p.waitFor();
					
					p = r.exec("chown -R root:root "+prefix+orgPath);
					p.waitFor();
				} catch (InterruptedException e) {
					logger.error("chmod Error", e);
				} catch (IOException e) {
					logger.error("chmod Error", e);
				}

				String[] exts = {"xml", "XML"};
				String[] fileNames = null;
				if(workflow.getMnc().getSourceNm().indexOf("_") > -1) {
					fileNames = workflow.getMnc().getSourceNm().split("\\_");
				} else {
					fileNames = new String[]{workflow.getMnc().getSourceNm()};
				}

				boolean skip = false;
				String metaXML = null;
				for(String ext : exts) {
					if(!skip) {
						try {
							String fileName = "";
							for(int i=0; i < fileNames.length; i++) {
								fileName += fileName.equals("") ? fileNames[i] : "_"+fileNames[i];

								File f = new File(prefix + orgPath + File.separator
										+ fileName
										+ "."+ext);

								if(f.exists()) {
									metaXML = FileUtils.readFileToString(f, "UTF-8");
									skip = true;
									break;
								}
							}
						} catch (Exception e) {
							logger.error("xml read error - "+e.getMessage());
							throw new IOException("xml file not found - "+prefix + orgPath + File.separator + workflow.getMnc().getSourceNm()+".(xml or XML)");
						}
					}
				}

				if(StringUtils.isBlank(metaXML)) {
					throw new IOException("xml file not found - "+prefix + orgPath + File.separator + workflow.getMnc().getSourceNm()+".(xml or XML)");
				}

				String domain = messageSource.getMessage("meta.system.domain", null, Locale.KOREA);

				if (workflow.getMnc().getSourceGb().toUpperCase().equals("NPS") || workflow.getMnc().getSourceGb().toUpperCase().equals("DNPS")) { // NPS, DNPS
					KBSMetaData kbsMetaData = (KBSMetaData)xmlStream.fromXML(metaXML);

					contentsTbl = new ContentsTbl();
					contentsInstTbl = new ContentsInstTbl();

					contentsTbl.setPgmCd(kbsMetaData.getProgramCode());
					contentsTbl.setPgmId(kbsMetaData.getProgramId());
					contentsTbl.setPgmNm(kbsMetaData.getProgramTitle());
					contentsTbl.setCtNm(kbsMetaData.getClipTitle());
					contentsTbl.setBrdDd(Utility.getDate(kbsMetaData.getBroadcastPlannedDate(), "yyyyMMdd"));
					contentsTbl.setCtLeng(kbsMetaData.getClipDuration());
					contentsTbl.setVdQlty(kbsMetaData.getProductionVideoQuality());
					/**
					 *  KBS미디어 주상필K 정리(20120209)
					 * - 에센스타입1(ctCla) : 00-방송본, 01-촬영본
					 * - 에센스타입2(ctTyp) : 00-본방, 01-예고편, 02-타이틀, 03-인터뷰, 04-현장스케치, 05-보이는라디오, 06-뮤직비디오, 07-기타
					 */
					contentsTbl.setCtCla("00");
					contentsTbl.setCtTyp("01");

					contentsTbl.setRegrid(workflow.getMnc().getSourceGb());
					contentsTbl.setKeyWords(kbsMetaData.getKeyword());

					/**
					 * NPS meta forward METAHUB
					 */
					String method = messageSource.getMessage("meta.system.xml.nps.forward", null, Locale.KOREA); // nps 

					String URL = domain + method;
					Map<String, Object> params = new HashMap<String, Object>();
					try {
						params.put("contents", metaXML);

						HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
						httpRequestService.findData(URL, Utility.convertNameValue(params));
						//Utility.connectHttpPostMethod(URL,Utility.convertNameValue(params));
					} catch (Exception e) {
						logger.error("saveFileIngest (D)NPS insert error", e);
					}
				} else if (workflow.getMnc().getSourceGb().toUpperCase().equals("DMCR") || workflow.getMnc().getSourceGb().toUpperCase().indexOf("POST") > -1) { // 주조 녹화 영상

					KBSExchange kbsExchange = (KBSExchange)xmlStream.fromXML(metaXML);

					contentsTbl = new ContentsTbl();
					contentsInstTbl = new ContentsInstTbl();

					Metadata metadata = kbsExchange.getMetadata();

					char kind = metadata.getBroadcastEventKind().charAt(0);
					try {
						switch(kind) {
						case 'Y':	// 예고
							contentsTbl.setCtNm(metadata.getTailerTitle());
							contentsTbl.setCtTyp("05");
							break;
						case '1':	// 전타
							contentsTbl.setCtNm(metadata.getProgramSubTitle());
							contentsTbl.setCtTyp("01");
							break;
						case '2':	// 후타
							contentsTbl.setCtNm(metadata.getProgramSubTitle());
							contentsTbl.setCtTyp("04");
							break;
						case 'P':	// 프로그램
							contentsTbl.setCtNm(metadata.getProgramSubTitle());
							contentsTbl.setCtTyp("00");
							break;
						}

						contentsTbl.setPgmCd(metadata.getProgramCode());
						contentsTbl.setPgmId(metadata.getProgramId());
						contentsTbl.setPgmNm(metadata.getProgramTitle());
						contentsTbl.setBrdDd(metadata.getBroadcastPlannedDate());
						contentsTbl.setVdQlty(metadata.getProductionVideoQualty());
						contentsTbl.setCtCla("00");
						contentsTbl.setChannelCode(metadata.getChannelCode());
						contentsTbl.setBrdDd(metadata.getBroadcastPlannedDate());
						if(workflow.getMnc().getSourceGb().indexOf("POST") > -1) {
							contentsTbl.setRegrid(workflow.getMnc().getSourceGb().substring(0, workflow.getMnc().getSourceGb().indexOf("_")));
						} else 
							contentsTbl.setRegrid(workflow.getMnc().getSourceGb());
					} catch (Exception e) {
						throw new ServiceException("Metahub Search Error", e);
					}

				} else if (workflow.getMnc().getSourceGb().toUpperCase().equals("AACH")) { // 오디오 아카이브
					KBSMetaData kbsExchangeMetadata = (KBSMetaData)xmlStream.fromXML(metaXML);
					contentsTbl = new ContentsTbl();
					contentsInstTbl = new ContentsInstTbl();

					contentsTbl.setChannelCode(kbsExchangeMetadata.getChannelCd());
					contentsTbl.setPgmCd(kbsExchangeMetadata.getProgramCode());
					contentsTbl.setPgmId(kbsExchangeMetadata.getProgramId());
					contentsTbl.setPgmNm(kbsExchangeMetadata.getProgramTitle());
					if(StringUtils.isNotBlank(kbsExchangeMetadata.getBroadcastPlannedDate())) {
						Date brdDd = Utility.getDate(kbsExchangeMetadata.getBroadcastPlannedDate(), "yyyyMMdd");
						contentsTbl.setBrdDd(brdDd);
					}
					contentsTbl.setCtCla(StringUtils.defaultIfEmpty(kbsExchangeMetadata.getCtCla(), "00"));
					contentsTbl.setCtTyp("00");
					contentsTbl.setRegrid(workflow.getMnc().getSourceGb());

					contentsInstTbl.setCtLeng(kbsExchangeMetadata.getProductionDuration());
					// 녹화 시작시간, 종료시간도 추가해야할지 고민...오디오 아카이브에서는 받을 수 있게 해놓음.
					// 추가한다면 ContentTbl에 넣어야하는데... DB 컬럼도 없고 보여주는 화면은 주간편성표에서 가져옴.

					// 오디오 코너 정보가 존재한다면 추가저장
					if(kbsExchangeMetadata.getCorners() != null && kbsExchangeMetadata.getCorners().getCornerList().size() > 0) {
						for(Corner corner : kbsExchangeMetadata.getCorners().getCornerList()) {
							CornerTbl cornerTbl = new CornerTbl();
							cornerTbl.setAudSegmentId(corner.getSegmentId());
							cornerTbl.setCnNm(corner.getCornerTitle());
							if(StringUtils.isNotBlank(corner.getCornerTitle())) {
								contentsTbl.setCtNm(corner.getCornerTitle());
							}
							cornerTbl.setBgnTime(corner.getCornerStartTime());
							cornerTbl.setEndTime(corner.getCornerEndTime());
							contentsTbl.addCornerTbl(cornerTbl);
						}
					}
				} else { // KDAS
					contentsTbl = new ContentsTbl();
					contentsInstTbl = new ContentsInstTbl();

					/**
					 * KDAS 용 XML parsing
					 */
					contentsManagerService.archiveXMLPasing(metaXML, contentsTbl, contentsInstTbl);


					/**
					 * VKDAS meta forward METAHUB
					 */

					String method = messageSource.getMessage("meta.system.xml.vdas.forward", null, Locale.KOREA); // 비디오 아카이브 

					if(contentsTbl != null && contentsInstTbl != null) {
						String URL = domain + method;
						Map<String, Object> params = new HashMap<String, Object>();
						try {
							params.put("contents", metaXML);

							HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
							httpRequestService.findData(URL, Utility.convertNameValue(params));
							//Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));
						} catch (Exception e) {
							logger.error("MetaHub insert error", e);
						}
					}
				}

				if (contentsTbl != null && contentsInstTbl != null) {
					/**
					 *  KBS미디어 주상필K 정리(20120209)
					 * - 에센스타입1(ctCla) : 00-방송본, 01-촬영본
					 * - 에센스타입2(ctTyp) : 00-본방, 01-예고편, 02-타이틀, 03-인터뷰, 04-현장스케치, 05-보이는라디오, 06-뮤직비디오, 07-기타
					 */
					contentsTbl.setRegrid(workflow.getMnc().getSourceGb());

					contentsTbl.setUseYn("Y");
					contentsTbl.setRegDt(Utility.getTimestamp());
					contentsInstTbl.setUseYn("Y");

					/*
					 * 프로그램 코드로 프로그램 그룹코드를 가지고 온다.
					 */
					if(StringUtils.isNotBlank(contentsTbl.getPgmId()) && !"null".equals(contentsTbl.getPgmId().trim())) {

						Map<String,Object> params = new HashMap<String, Object>();

						params.put("program_id", contentsTbl.getPgmId());
						String method = messageSource.getMessage("meta.system.search.program.read", null, Locale.KOREA);

						/*
						 * 메타허브 쪽에 프로그램 회차 정보 조회 값을 받아와서 bean set
						 * param  pgmId;
						 */
						Map<String, Object> element = null;
						try {
							HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
							String programXml = httpRequestService.findData(domain+method, Utility.convertNameValue(params));
							//String programXml = Utility.connectHttpPostMethod(domain+method, Utility.convertNameValue(params));
							element = mediaToolInterfaceService.forwardContentsMLNodeInfo(programXml);

							if(StringUtils.isBlank(contentsTbl.getPgmCd()) || contentsTbl.getPgmCd().length() != 10) {
								contentsTbl.setPgmCd(String.valueOf(element.get("program_code")));
							}

							if(element.get("group_code") != null && StringUtils.isNotBlank(String.valueOf(element.get("group_code")))){
								contentsTbl.setPgmGrpCd(String.valueOf(element.get("group_code")));
							}else{
								contentsTbl.setPgmGrpCd(contentsTbl.getPgmCd());
							}
						}catch (Exception e) {
							logger.error("회차정보조회 오류", e);
						}

						if(StringUtils.isBlank(contentsTbl.getChannelCode()))
							contentsTbl.setChannelCode(String.valueOf(element.get("channel_code")));

						contentsTbl.setDataStatCd("200");
					} else {
						/*
						 * 예고는 program_cd만 존재하고 program_id는 존재하지 않음.
						 * 메타허브 api를 이용하여 프로그램코드와 방송예정일자를 파라미터로 넘겨서 최종 회차정보를 받아옴.
						 * http://118.221.109.16/api/mh_program_num/list.xml?handler=DATE_PCODE&program_code=R2008-0160&program_planned_date=20130318
						 */
						if(contentsTbl.getCtTyp().equals("01")) {

							if(StringUtils.isBlank(contentsTbl.getPgmCd()) || "null".equals(contentsTbl.getPgmCd().trim())) {
								logger.error("program_code is null = " +contentsTbl.getPgmCd());
								contentsTbl.setDataStatCd("300");
							} else {
								String method = messageSource.getMessage("meta.system.last.pgmid", null, Locale.KOREA);
								String URL = domain + method;

								Map<String, Object> params = new HashMap<String, Object>();
								try {
									params.put("handler", "DATE_PCODE");
									params.put("program_code", contentsTbl.getPgmCd());
									params.put("program_planned_date", Utility.getDate(contentsTbl.getBrdDd(), "yyyyMMdd"));

									if(logger.isDebugEnabled()) {
										logger.debug("url: "+URL);
										logger.debug("params: "+params);
									}

									HttpRequestService<Nodes> httpRequestService = HttpRequestServiceImpl.getInstance();
									String programXml = httpRequestService.findData(URL, Utility.convertNameValue(params));
									//String programXml = Utility.connectHttpPostMethod(URL, Utility.convertNameValue(params));

									Map<String, Object> element = mediaToolInterfaceService.forwardContentsMLNodeInfo(programXml);

									contentsTbl.setPgmGrpCd(String.valueOf(element.get("group_code")));
									contentsTbl.setPgmId(String.valueOf(element.get("program_id")));
									contentsTbl.setDataStatCd("200");
								} catch (Exception e) {
									contentsTbl.setDataStatCd("300");
									contentsTbl.setPrgrs("-1");
								}
							}
						} else {
							contentsTbl.setDataStatCd("300");
							contentsTbl.setPrgrs("-1");
						}
					}

					/*
					 * 프로그램 코드 및 프로그램ID가 없을경우 등록을 못하도록 조치 2013.05.31
					 */
					if(StringUtils.isBlank(contentsTbl.getPgmId()) || StringUtils.isBlank(contentsTbl.getPgmCd())) {
						logger.error("program_code:"+contentsTbl.getPgmCd()+", program_id:"+contentsTbl.getPgmId());
						throw new RemoteException("parameter value is blank!");
					}

					if("null".equals(contentsTbl.getPgmId().trim())) {
						logger.error("program_id value is null!");
						throw new RemoteException("program_id value is null!");
					}
					if("null".equals(contentsTbl.getPgmCd().trim())) {
						logger.error("program_code value is null!");
						throw new RemoteException("program_code value is null!");
					}

					/*
					 * 2013.04.24 추가
					 * 수신서버에서 체크한결과 상태값을 status 태그에 넣어서 보낸다.
					 * 완료[C], 오류[E] 오류는 전송오류임
					 */
					if(StringUtils.isNotBlank(workflow.getMnc().getStatus()) && workflow.getMnc().getStatus().equals("E")) {
						contentsTbl.setDataStatCd("500");
						contentsTbl.setPrgrs("-1");
					}

					if(Integer.valueOf(contentsTbl.getDataStatCd()) <= 200) {

						try {
							/* 
							 * 메타허브에서 [프로그램코드] 를 조회하여 파일경로 생성
							 * 기존경로에서 신규생성된 경로로 파일을 이동시킨다.
							 * 메타허브 연동은 다음주에 처리하고 임시로 처리함.
							 */
							String destPath = "/mp2/"+contentsTbl.getPgmGrpCd()+"/"+contentsTbl.getPgmId()+"/";

							// 개발관련 임시로 주석처리 운영시 필 주석제거
							String uuid = UUID.randomUUID().toString();

							int m = 0;
							String paths[] = destPath.split("\\/");
							String curPath = prefix;
							File dirF = null;
							for(int i=0; i<paths.length; i++) {
								curPath += paths[i]+"/";
								if(paths[i].equals("mp2")) m = i;
								if(m != i && m > 0) {
									try {
										dirF = new File(curPath);
										if(!dirF.exists()) {
											dirF.mkdirs();

											Runtime r = Runtime.getRuntime();
											Process p = r.exec("chmod -R 777 "+curPath);
											p.waitFor();
											if(logger.isDebugEnabled()) {
												logger.debug("folder created and chmod 777 apply =======>"+curPath);
											}
										}
									} catch (InterruptedException e) {
										logger.error("chmod Error", e);
									} catch (IOException e) {
										logger.error("chmod Error", e);
									}

								}
							}
							contentsInstTbl.setFlSz(dirF.length());

							String orgPathNm ="";

							File fOrgNm = new File(prefix + orgPath, workflow.getMnc().getSourceNm()+"."+workflow.getMnc().getSourceExt());
							if(fOrgNm.exists())
								orgPathNm = fOrgNm.getAbsolutePath();
							else {
								for(int i=0; i<2; i++) {
									try {
										Thread.sleep(1000L);
										File orgDir = new File(prefix + orgPath);
										File[] files = orgDir.listFiles(new UserFilenameFilter(workflow.getMnc().getSourceExt(), workflow.getMnc().getSourceNm()));
										logger.debug("UserFilenameFilter files "+ files.length);
										if(files.length > 0) {
											orgPathNm = files[0].getAbsolutePath();
										} else {
											throw new FileNotFoundException("assence file not found : "+fOrgNm.getAbsolutePath());
										}
									} catch (Exception e) {
										if(logger.isInfoEnabled()) {
											logger.info("assence retry - "+ (i+1), e);
										}
										if(i == 1) {
											throw new FileNotFoundException("assence file not found : "+fOrgNm.getAbsolutePath());
										}
									}
								}
							}

							String destPathNm = prefix+ destPath + uuid+"."+workflow.getMnc().getSourceExt();

							if(logger.isInfoEnabled()) {
								logger.info("File MOVE ================= mv "+ orgPathNm+" "+destPathNm);
							}
							Runtime r = Runtime.getRuntime();
							Process p = r.exec("mv "+orgPathNm+" "+destPathNm);
							try {
								p.waitFor();

								p = r.exec("chmod -R 777 "+destPathNm);
								p.waitFor();
							} catch (InterruptedException e) {
								logger.error("File Move Error", e);
							}

							// 확장자를 이용하여 영상 포맷을 구한다.
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("clfCd", "FMAT");

							if(workflow.getMnc().getSourceGb().equals("AACH")) {
								params.put("sclCd", workflow.getMnc().getSourceExt().toLowerCase()+"3");
								contentsInstTbl.setAvGubun("A");
							} else {
								params.put("sclCd", workflow.getMnc().getSourceExt().toLowerCase()+"1");
								contentsInstTbl.setAvGubun("V");
							}
							CodeTbl codeTbl = codeManagerService.getCode(params);

							contentsInstTbl.setCtiFmt(codeTbl.getClfNm());
							contentsInstTbl.setFlPath(destPath);
							contentsInstTbl.setUsrFileNm(workflow.getMnc().getSourceNm());
							contentsInstTbl.setOrgFileNm(uuid);
							contentsInstTbl.setFlExt(workflow.getMnc().getSourceExt());

							contentsTbl.setTrimmSte("N");   // 트링밍 필요 : N
							contentsTbl.setPgmNum(codeManagerService.getContentsPgmNum(contentsTbl)); // 네이밍 수정순번 적용
							contentsTbl.setPrgrs("100");

						} catch (Exception e) {
							contentsTbl.setDataStatCd("300");
							contentsTbl.setPrgrs("-1");
							logger.error("saveFileIngest File Check Error", e);
						}

					}

					contentsManagerService.insertContents(contentsTbl, contentsInstTbl);
				} else {
					logger.error("Content metadata is null!!");
					return Boolean.valueOf("false").toString();
				}

			}
		} catch (Exception e) {
			logger.error("saveFileIngest xml parsing Error", e);
			Boolean.valueOf("false").toString();
		}
		return Boolean.valueOf("true").toString();
	}

	@Override
	public String findTransfers(String xml) throws RemoteException {
		if (logger.isInfoEnabled()) {
			logger.info("findContents request xml - " + xml);
		}

		Workflow workflow = null;
		try {
			workflow = (Workflow) xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("findTransfers xml parsing Error", e);
			Boolean.valueOf("false").toString();
		}

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("size", "20");

			// 대기 리스트 조회
			if(workflow.getGubun().equals("W")) {
				params.put("workStatcd", "'000', '001', '002'");
				params.put("desc", "regDt");
			} else { // 완료 리스트 조회
				params.put("workStatcd", "'200'");
				params.put("desc", "trsEndDt");
			}

			Content content = null;
			List<TransferHisTbl> transferHisTbls = workflowManagerService.findTransferStatus(params);
			for(TransferHisTbl transferHisTbl : transferHisTbls) {
				content = new Content();
				content.setPgmCd(transferHisTbl.getPgmCd());
				content.setPgmNm(transferHisTbl.getPgmNm());
				content.setPgmId(transferHisTbl.getPgmId());
				content.setCtNm(transferHisTbl.getCtNm());
				content.setCtiId(transferHisTbl.getCtiId());
				content.setFlPath(transferHisTbl.getFlPath());
				content.setFlExt(transferHisTbl.getFlExt());
				content.setCompany(transferHisTbl.getCompany());
				content.setBitRate(transferHisTbl.getBitRate());
				content.setProFlnm(transferHisTbl.getProFlnm());
				workflow.addContentList(content);
			}

		} catch (Exception e) {
			logger.error("findTransfers xml parsing Error", e);
		}


		return xmlStream.toXML(workflow);
	}

}
