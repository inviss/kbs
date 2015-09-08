package kr.co.d2net.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dao.EquipmentDao;
import kr.co.d2net.dao.OptDao;
import kr.co.d2net.dao.ProOptDao;
import kr.co.d2net.dao.QcReportDao;
import kr.co.d2net.dao.TranscorderHisDao;
import kr.co.d2net.dao.TransferHisDao;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.EquipmentTbl;
import kr.co.d2net.dto.OptTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.ProOptTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.TranscorderHisTbl;
import kr.co.d2net.dto.TransferHisTbl;
import kr.co.d2net.dto.xml.internal.Status;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "WorkflowManagerService")
public class WorkflowManagerServiceImpl implements WorkflowManagerService {

	private Log logger = LogFactory.getLog(WorkflowManagerServiceImpl.class);
	
	@Autowired
	private TransferHisDao transferHisDao;
	@Autowired
	private TranscorderHisDao transcorderHisDao;
	@Autowired
	private EquipmentDao equipmentDao;
	@Autowired
	private OptDao optDao;
	@Autowired
	private ProOptDao proOptDao;
	@Autowired
	private QcReportDao qcReportDao;


	@SuppressWarnings("unchecked")
	@Override
	public PaginationSupport<TranscorderHisTbl> findTranscorderHis(Search search)
			throws ServiceException {

		PaginationSupport<TranscorderHisTbl> trsHisList = transcorderHisDao.findTranscorderHisWork(search);

		return (PaginationSupport<TranscorderHisTbl>) ((trsHisList == null) ? Collections.EMPTY_LIST
				: trsHisList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationSupport<TransferHisTbl> findTransferHis(Search search)
			throws ServiceException {
		PaginationSupport<TransferHisTbl> traHisList = transferHisDao
				.findTransferHisWork(search);
		return (PaginationSupport<TransferHisTbl>) ((traHisList == null) ? Collections.EMPTY_LIST
				: traHisList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EquipmentTbl> findEquipment(Map<String, Object> params)
			throws ServiceException {

		List<EquipmentTbl> contents = equipmentDao.findEquipment(params);

		return (contents == null) ? Collections.EMPTY_LIST : contents;
	}

	@Override
	public void insertTranscorderHis(TranscorderHisTbl transcorderHisTbl)
			throws ServiceException {

		transcorderHisDao.insertTrancorderHisWork(transcorderHisTbl);
	}

	@Override
	public int updateTranscorderHis(TranscorderHisTbl transcorderHisTbl)
			throws ServiceException {
		return transcorderHisDao.updateTranscorderHisWork(transcorderHisTbl);
	}

	@Override
	public void insertTransferHis(TransferHisTbl transferHisTbl)
			throws ServiceException {

		transferHisDao.insertTransferHisWork(transferHisTbl);
	}

	@Override
	public int updateTransferHis(TransferHisTbl transferHisTbl)
			throws ServiceException {
		return transferHisDao.updateTransferHisWork(transferHisTbl);
	}

	@Override
	public TransferHisTbl getTransferHisJob(Status status)
			throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();

		TransferHisTbl transferHisTbl = transferHisDao.getTransferHisJob(params);

		/*
		 * SourcePath, SourceFile, TargetPath[Profile Path], TargetFile[WrkFileNm]
		 * 
		 */
		if(transferHisTbl != null) {
			String[] eq = status.getEqId().split("\\_");
			transferHisTbl.setModDt(Utility.getTimestamp());
			transferHisTbl.setDeviceid(eq[0]);
			transferHisTbl.setEqPsCd(eq[1]);
			transferHisTbl.setWorkStatcd("001"); // 대기상태[00] -> 요청상태 변경[01]
			transferHisDao.updateTransferHisWork(transferHisTbl);
		}

		return transferHisTbl;
	}

	@Override
	public TranscorderHisTbl getTranscoderHisJob(Status status)
			throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();

		// 작업할당
		TranscorderHisTbl transcorderHisTbl = transcorderHisDao.getTranscorderHisJob(params);

		if(transcorderHisTbl != null) {

			// 트랜스코더 작업상태 변경
			String[] eq = status.getEqId().split("\\_");
			transcorderHisTbl.setModDt(Utility.getTimestamp());
			transcorderHisTbl.setDeviceid(eq[0]);
			transcorderHisTbl.setEqPsCd(eq[1]);
			transcorderHisTbl.setWorkStatcd("001"); // 대기상태[00] -> 요청상태 변경[01]
			transcorderHisDao.updateTranscorderHisState(transcorderHisTbl);

			// Default Option 조회
			params.put("defaultYn", "Y");
			OptTbl optTbl = optDao.getOpt(params);
			if(optTbl != null && StringUtils.isNotBlank(optTbl.getOptInfo())) {
				transcorderHisTbl.setDefaultOpt(optTbl.getOptInfo());
			}

			// 프로파일과 관련된 옵션 조회
			params.put("proFlid", transcorderHisTbl.getProFlid());
			List<ProOptTbl> optTbls = proOptDao.findProOpt(params);
			if(optTbls != null && !optTbls.isEmpty()) {
				transcorderHisTbl.setOptions(optTbls);
			}
		}

		return transcorderHisTbl;
	}

	@Override
	public void updateTransferHisState(TransferHisTbl traHisTbl)
			throws ServiceException {
		transferHisDao.updateTransferHisWork(traHisTbl);
	}

	@Override
	public void updateTranscorderHisState(TranscorderHisTbl trsHisTbl)
			throws ServiceException {
		transcorderHisDao.updateTranscorderHisState(trsHisTbl);
	}

	@Override
	public void updateProflRequest(TranscorderHisTbl trsHisTbl)
			throws ServiceException {
		transcorderHisDao.updateProflRequest(trsHisTbl);
	}
	
	@Override
	public void updateAllProflRequest(TranscorderHisTbl trsHisTbl)
			throws ServiceException {
		transcorderHisDao.updateAllProflRequest(trsHisTbl);
	}

	@Override
	public void updateProfl(TranscorderHisTbl trsHisTbl)
			throws ServiceException {
		transcorderHisDao.updateProfl(trsHisTbl);
	}

	@Override
	public void updateTransRequest(TransferHisTbl trsHisTbl)
			throws ServiceException {
		transferHisDao.updateTransRequest(trsHisTbl);
	}

	@Override
	public void updateTransRequest2(TransferHisTbl trsHisTbl)
			throws ServiceException {
		transferHisDao.updateTransRequest2(trsHisTbl);
	}

	@Override
	public void updateEquipmentState(EquipmentTbl equipmentTbl)
			throws ServiceException {
		equipmentDao.updateEquipment(equipmentTbl);
	}

	@Override
	public void instdeleteEquipment(EquipmentTbl equipmentTbl)
			throws ServiceException {
		equipmentDao.instdeleteEquipment(equipmentTbl);
	}

	@Override
	public void devicedeleteEquipment(EquipmentTbl equipmentTbl)
			throws ServiceException {
		equipmentDao.devicedeleteEquipment(equipmentTbl);
	}

	@Override
	public TranscorderHisTbl getTranscoderHisJobID(Long jobId)
			throws ServiceException {

		return transcorderHisDao.getTranscorderHisJobID(jobId);
	}

	@Override
	public void insertEquipment(EquipmentTbl equipmentTbl) throws ServiceException {
		equipmentDao.insertEquipment(equipmentTbl);
	}

	@Override
	public EquipmentTbl getEquipment(Map<String, Object> params) throws ServiceException {
		return equipmentDao.getEquipment(params);
	}

	@Override
	public EquipmentTbl getEquipmentCount(Map<String, Object> params) throws ServiceException {
		return equipmentDao.getEquipmentCount(params);
	}

	@Override
	public List<TranscorderHisTbl> findTranscodeJob(Integer size)
			throws ServiceException {
		return transcorderHisDao.findTranscorderHisJob(size);
	}

	@Override
	public List<TransferHisTbl> findTransferJob(Integer size, String vdoBitRate, String transferGB)
			throws ServiceException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("wrkStatCd", "000");
		params.put("size", size);
		params.put("vdoBitRate", vdoBitRate);
		params.put("transferGB", transferGB);

		return transferHisDao.findTransferHisJob(params);
	}

	@Override
	public ContentsTbl getTranscodeInfo(Long seq) throws ServiceException {
		return transcorderHisDao.getTranscodeInfo(seq);
	}

	@Override
	public List<TransferHisTbl> findTransferBusiJob(Long seq)
			throws ServiceException {

		return transferHisDao.findTransferBusiJob(seq);
	}

	@Override
	public List<TransferHisTbl> findTransferStatus(Map<String, Object> params)
			throws ServiceException {
		return transferHisDao.findTransferStatus(params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationSupport<TransferHisTbl> findMetaURLlist(Search search)
			throws ServiceException {
		PaginationSupport<TransferHisTbl> traHisList = transferHisDao
				.findMetaURLlist(search);
		return (PaginationSupport<TransferHisTbl>) ((traHisList == null) ? Collections.EMPTY_LIST
				: traHisList);
	}

	@Override
	public List<TransferHisTbl> findTransferJob(String wrkStatCd, String vdoBitRate, String transferGB)
			throws ServiceException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("wrkStatCd", wrkStatCd);
		params.put("vdoBitRate", vdoBitRate);
		params.put("transferGB", transferGB);

		return transferHisDao.findTransferHisJobMap(params);
	}

	@Override
	public List<TranscorderHisTbl> findTranscodeJob(String wrkStatCd)
			throws ServiceException {
		return transcorderHisDao.findTranscorderHisJob(wrkStatCd);
	}

	@Override
	public List<TransferHisTbl> findTrsCtiBusi(Map<String, Object> params)
			throws ServiceException {
		return transferHisDao.findTrsCtiBusi(params);
	}

	@Override
	public List<Long> findTraHisCtiId(String wrkStatCd, String avGubun)
			throws ServiceException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("wrkStatCd", wrkStatCd);
		params.put("avGubun", avGubun);

		return transcorderHisDao.findTraHisCtiId(params);
	}

	@Override
	public List<TranscorderHisTbl> findTraHisJobCtiId(Long ctiId)
			throws ServiceException {
		return transcorderHisDao.findTraHisJobCtiId(ctiId);
	}

	@Override
	public void insertTransferHis(List<TransferHisTbl> traHisTbls)
			throws ServiceException {
		transferHisDao.insertTransferHisWork(traHisTbls);
	}

	@Override
	public void insertTranscorderHis(List<TranscorderHisTbl> traHisTbls)
			throws ServiceException {
		transcorderHisDao.insertTrancorderHisWork(traHisTbls);
	}

	@Override
	public TranscorderHisTbl getTraHis(Map<String, Object> params)
			throws ServiceException {
		return transcorderHisDao.getTraHis(params);
	}
	
	@Override
	public List<TranscorderHisTbl> getTraHisPrgrs(Map<String, Object> params)
			throws ServiceException {
		return transcorderHisDao.getTraHisPrgrs(params);
	}
	
	@Override
	public List<TransferHisTbl> getTrsHisPrgrs(Map<String, Object> params)
			throws ServiceException {
		return transferHisDao.getTrsHisPrgrs(params);
	}

	@Override
	public Integer getQcCount(Long seq) throws ServiceException {
		return qcReportDao.getQcCount(seq);
	}
}
