package kr.co.d2net.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.EquipmentTbl;
import kr.co.d2net.dto.ProOptTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.TranscorderHisTbl;
import kr.co.d2net.dto.TransferHisTbl;
import kr.co.d2net.dto.xml.internal.FindList;
import kr.co.d2net.dto.xml.internal.Job;
import kr.co.d2net.dto.xml.internal.Option;
import kr.co.d2net.dto.xml.internal.Status;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.service.ContentsInstManagerService;
import kr.co.d2net.service.ContentsManagerService;
import kr.co.d2net.service.MediaToolInterfaceService;
import kr.co.d2net.service.WorkflowManagerService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class WorkflowManagerTest extends BaseDaoConfig {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TransferHisDao trsHisDao;

	@Autowired
	private TranscorderHisDao traHisDao;

	@Autowired
	private EquipmentDao equipmentDao;

	@Autowired
	private WorkflowManagerService workflowManagerService;

	@Autowired
	private XmlStream xmlStream;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ContentsInstManagerService contentsInstManagerService;
	
	@Autowired
	private ContentsManagerService contentsManagerService;
	
	@Autowired
	private MediaToolInterfaceService mediaToolInterfaceService;
	
	@Ignore
	@Test
	public void findDeleteDefaultList(){

		Integer live = Integer.valueOf(messageSource.getMessage(
				"media.live.delete.days", null, Locale.KOREA))*-1;

		Integer kdas = Integer.valueOf(messageSource.getMessage(
				"media.kdas.delete.days", null, Locale.KOREA))*-1;

		Integer nps = Integer.valueOf(messageSource.getMessage(
				"media.nps.delete.days", null, Locale.KOREA))*-1;

		Integer nas = Integer.valueOf(messageSource.getMessage(
				"media.nas.delete.days", null, Locale.KOREA))*-1;

		Integer atrim = Integer.valueOf(messageSource.getMessage(
				"media.atrim.delete.days", null, Locale.KOREA))*-1;

		Integer vtrim = Integer.valueOf(messageSource.getMessage(
				"media.vtrim.delete.days", null, Locale.KOREA))*-1;

		Integer rms = Integer.valueOf(messageSource.getMessage(
				"media.rms.delete.days", null, Locale.KOREA))*-1;

		Integer service = Integer.valueOf(messageSource.getMessage(
				"media.service.delete.days", null, Locale.KOREA))*-1;
		
		
		String mainDrive = messageSource.getMessage(
				"mam.mount.prefix", null, Locale.KOREA);   // 신규 10TB : mp2

		String subDrive = messageSource.getMessage(
				"mam.mount.prefix2", null, Locale.KOREA);   // 기존 100TB  :mp4

		/*
		 * NAS Storage 컨텐츠 삭제 현재일을 기준으로 보존기간일을 뺀 날짜보다 이전에 등록된 컨텐츠를 삭제한다.
		 */
		// String delDt = Utility.getDate(vsDay);
		Map<String,Object> element = new HashMap<String,Object>();
		element.put("live", live);
		element.put("kdas", kdas);
		element.put("nps", nps);
		element.put("nas", nas);
		element.put("atrim", atrim);
		element.put("vtrim", vtrim);
		element.put("rms", rms);
		element.put("service", service);
		
		System.out.println("1");
		try {
			List<ContentsInstTbl> contentInfoTbls = contentsInstManagerService
					.findDefaultDeleteContentsInst(element);
			System.out.println("2");
			logger.debug("contentInfoTbls.size():"+contentInfoTbls.size());
			for (ContentsInstTbl contentsInstTbl : contentInfoTbls) {
				logger.debug("콘텐츠 정보:"+contentsInstTbl.getFlPath()+contentsInstTbl.getOrgFileNm()+"."+contentsInstTbl.getFlExt());
			}
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	@Ignore
	@Test
	public void findTransferTest() {
		Search search = new Search();
		search.setPageNo(1);
		try {
			PaginationSupport<TransferHisTbl> paginationSupport = trsHisDao
					.findTransferHisWork(search);
			System.out.println(paginationSupport.getItems());
			for (TransferHisTbl hisTbl : paginationSupport.getItems()) {
				System.out.println("ctNm : " + hisTbl.getCtNm());
			}
		} catch (DaoNonRollbackException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void findTranscorderHisTest() {
		Search search = new Search();
		search.setPageNo(1);
		try {
			PaginationSupport<TranscorderHisTbl> paginationSupport = traHisDao
					.findTranscorderHisWork(search);
			System.out.println(paginationSupport.getItems());
			for (TranscorderHisTbl hisTbl : paginationSupport.getItems()) {
				System.out.println("ctNm : " + hisTbl.getCtNm());
			}
		} catch (DaoNonRollbackException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void insertTransferHisTest() {
		try {
			List<TransferHisTbl> transferHisTbls = workflowManagerService.findTransferBusiJob(5299L);

			List<TransferHisTbl> transferHisList = new ArrayList<TransferHisTbl>();
			for(TransferHisTbl transferHisTbl : transferHisTbls) {

				// CDN 전송요청 등록
				transferHisTbl.setRegDt(Utility.getTimestamp());
				transferHisTbl.setCtiId(7280L);
				transferHisTbl.setCtId(3245L);

				//String flPath = prefix+transferHisTbl.getFlPath()+transferHisTbl.getOrgFileNm()+"."+contentsInstTbl.getFlExt();
				String flPath = transferHisTbl.getFlPath()+transferHisTbl.getOrgFileNm()+"."+transferHisTbl.getFlExt();

				transferHisTbl.setFlPath(flPath.replace("mp2", "mp4"));
				transferHisTbl.setWorkStatcd("000");
				transferHisTbl.setPrgrs("0");
				transferHisTbl.setRetryCnt(0);
				transferHisTbl.setUseYn("Y");
				transferHisTbl.setPriority(5);
				transferHisTbl.setRegDt(Utility.getTimestamp());

				transferHisList.add(transferHisTbl);
			}
			workflowManagerService.insertTransferHis(transferHisList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getTranscoderHisJob() {
		Workflow workflow = new Workflow();
		try {
			Job job = new Job();
			Status status = new Status();
			status.setEqId("TC01_01");
			status.setEqState("W");
			TranscorderHisTbl transcorderHisTbl = workflowManagerService.getTranscoderHisJob(status);

			if(transcorderHisTbl != null) {
				job.setJobId(transcorderHisTbl.getSeq());
				if(transcorderHisTbl.getCtiFmt().startsWith("1")) {
					job.setJobKind("video");
				} else {
					job.setJobKind("audio");
				}
				job.setProFlid(transcorderHisTbl.getProFlid());
				job.setServBit(transcorderHisTbl.getBitRt());
				job.setExt(transcorderHisTbl.getNewFlExt());
				job.setVdoCodec(transcorderHisTbl.getVdoCodec());
				job.setVdoBitRate(transcorderHisTbl.getVdoBitRate());
				job.setVdoHori(transcorderHisTbl.getVdoHori());
				job.setVdoVeri(transcorderHisTbl.getVdoVert());
				job.setVdoFS(transcorderHisTbl.getVdoFS());
				job.setVdoSync(transcorderHisTbl.getVdoSync());
				job.setAudCodec(transcorderHisTbl.getAudCodec());
				job.setAudBitRate(transcorderHisTbl.getAudBitRate());
				job.setAudChan(transcorderHisTbl.getAudChan());
				job.setAudSRate(transcorderHisTbl.getAudSRate());
				job.setDefaultOpt(transcorderHisTbl.getDefaultOpt());

				// 프로파일 옵션 설정
				if(!transcorderHisTbl.getOptions().isEmpty()) {
					Option option = new Option();
					for(ProOptTbl proOptTbl : transcorderHisTbl.getOptions()) {
						Option opt = new Option();
						opt.setOptId(proOptTbl.getOptId());
						opt.setOptDesc(proOptTbl.getOptDesc());
						option.addOptions(opt);
					}
					job.setOption(option);
				}

				job.setSourcePath(transcorderHisTbl.getFlPath().replaceAll("\\/", "\\\\"));
				job.setSourceFile(transcorderHisTbl.getOrgFileNm()+"."+transcorderHisTbl.getOrgFlExt());
				job.setTargetPath(transcorderHisTbl.getFlPath().replace("mp2", "mp4").replace("source", "target").replaceAll("\\/", "\\\\"));
				job.setTargetFile(transcorderHisTbl.getOrgFileNm()+"."+transcorderHisTbl.getNewFlExt());
				job.setQcYn("Y");
			}
			workflow.setJob(job);

			System.out.println(xmlStream.toXML(workflow));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void findWorkflowTest() {
		Map<String, Object> params = new HashMap<String, Object>();
		try {

			List<EquipmentTbl> contents = equipmentDao.findEquipment(params);
			System.out.println("equipmentDao.findEquipment(params):"
					+ contents.size());
		} catch (DaoNonRollbackException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void updateWorkflowTest() {

		try {
			int tmpI;
			EquipmentTbl equipmentTbl = new EquipmentTbl();
			equipmentTbl.setModDt(Utility.getTimestamp());
			equipmentTbl.setModrid("asehub");
			equipmentTbl.setDeviceid("020");
			equipmentTbl.setEqPsCd("001");
			equipmentTbl.setPrgrs("20");
			tmpI = equipmentDao.updateEquipment(equipmentTbl);
			System.out.println("tmpI:" + tmpI);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void updateCtiFmtTest() {

		Integer delDays = Integer.valueOf(messageSource.getMessage(
				"media.dafault.delete.days", null, Locale.KOREA))*-1;

		String mainDrive = messageSource.getMessage(
				"mam.mount.prefix2", null, Locale.KOREA);

		/*
		 * NAS Storage 컨텐츠 삭제 현재일을 기준으로 보존기간일을 뺀 날짜보다 이전에 등록된 컨텐츠를 삭제한다.
		 */
		// String delDt = Utility.getDate(vsDay);
		Map<String,Object> element = new HashMap<String,Object>();
		element.put("delDays", delDays);
		try {
			List<ContentsInstTbl> contentInfoTbls = contentsInstManagerService
					.findDefaultDeleteContentsInst(element);
			for (ContentsInstTbl contentsInstTbl : contentInfoTbls) {
				//
				// MAIN 스토리지 삭제
				// MP2 삭제
				File f = null;
				File fLog = null;
				File fXml = null;
				File fSmil = null;
				if (StringUtils.isNotBlank(contentsInstTbl.getFlPath())) {
					if (!contentsInstTbl.getFlPath().endsWith("/")) {
						contentsInstTbl.setFlPath(contentsInstTbl.getFlPath() + "/");
					}
					f = new File(mainDrive + contentsInstTbl.getFlPath(),
							contentsInstTbl.getOrgFileNm() + "."
									+ contentsInstTbl.getFlExt());
					//에센스 영상 삭제
					if (f.exists()) {
						FileUtils.forceDelete(f);
						if (logger.isDebugEnabled()) {

							logger.debug("File Path: "
									+ mainDrive + contentsInstTbl.getFlPath()
									+ contentsInstTbl.getOrgFileNm() + "."
									+ contentsInstTbl.getFlExt() + " DELETED!!");
						}
					} else {
						if (logger.isDebugEnabled()) {

							logger.debug("File Path: "
									+ mainDrive + contentsInstTbl.getFlPath()
									+ contentsInstTbl.getOrgFileNm() + "."
									+ contentsInstTbl.getFlExt() + " Not Exists !!");
						}
					}
				}
				// 삭제일 등록
				if (StringUtils.isNotBlank(contentsInstTbl.getFlPath())) {
					ContentsInstTbl resultContentsinstTbl = new ContentsInstTbl();
					resultContentsinstTbl.setUseYn("N");
					resultContentsinstTbl.setModDt(Utility.getTimestamp());
					resultContentsinstTbl.setCtiId(contentsInstTbl.getCtiId());
					contentsInstManagerService.updateContentsInst(resultContentsinstTbl);

				}

			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	@Ignore
	public void findTrsCtiBusiTest() {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("busiPartnerId", "262");
		params.put("ctiId", 12952);
		try {
			List<TransferHisTbl> hisTbls = workflowManagerService.findTrsCtiBusi(params);

			//LinkedList<TransferHisTbl> list = new LinkedList<TransferHisTbl>();
			/*for(TransferHisTbl transferHisTbl : hisTbls) {
				logger.debug("sort before - "+transferHisTbl.getBitRate()+" : "+transferHisTbl.getWrkFileNm());
			}
			Collections.sort(hisTbls, getCompare());
			
			for(TransferHisTbl transferHisTbl : hisTbls) {
				logger.debug("sort after - "+transferHisTbl.getBitRate()+" : "+transferHisTbl.getWrkFileNm());
			}*/
			
			String smilXml = contentsManagerService.createSMIL(hisTbls);
			logger.debug(smilXml);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void findCode() {
		try {
			String xml = "<workflow><gubun>I</gubun><channel_cd></channel_cd><keyword>CHAN|VT</keyword><eq_id>VT01_01</eq_id><pgm_id></pgm_id></workflow>";
			Workflow workflow = (Workflow) xmlStream.fromXML(xml);
			FindList list = mediaToolInterfaceService.reqMediaInfo(workflow);
			
			workflow.setList(list);
			
			System.out.println(xmlStream.toXML(workflow));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
