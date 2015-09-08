package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.EquipmentTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.TranscorderHisTbl;
import kr.co.d2net.dto.TransferHisTbl;
import kr.co.d2net.dto.xml.internal.Status;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WorkflowManagerService {

	/**
	 * <p>
	 * 트랜스코더 작업 히스토리 조회
	 * <p>
	 * 
	 * @param search
	 * @return
	 * @throws ServiceException
	 */
	public PaginationSupport<TranscorderHisTbl> findTranscorderHis(Search search)
			throws ServiceException;

	/**
	 * <p>
	 * 트랜스퍼 작업 히스토리 조회
	 * <p>
	 * 
	 * @param search
	 * @return
	 * @throws ServiceException
	 */
	public PaginationSupport<TransferHisTbl> findTransferHis(Search search)
			throws ServiceException;

	/**
	 * <p>
	 * 디바이스 작업 조회
	 * <p>
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<EquipmentTbl> findEquipment(Map<String, Object> params)
			throws ServiceException;

	/**
	 * <p>
	 * 트랜스코더 작업 내역 입력
	 * <p>
	 * 
	 * @param trsHisTbl
	 * @throws ServiceException
	 */
	public void insertTranscorderHis(TranscorderHisTbl traHisTbl)
			throws ServiceException;
	
	public void insertTranscorderHis(List<TranscorderHisTbl> traHisTbls)
			throws ServiceException;

	/**
	 * <p>
	 * 트랜스코더 작업 내역 수정
	 * <p>
	 * 
	 * @param trsHisTbl
	 * @return
	 * @throws ServiceException
	 */
	public int updateTranscorderHis(TranscorderHisTbl traHisTbl)
			throws ServiceException;

	/**
	 * <p>
	 * 트랜스퍼 작업 히스토리 입력
	 * <p>
	 * 
	 * @param traHisTbl
	 * @throws ServiceException
	 */
	public void insertTransferHis(TransferHisTbl traHisTbl)
			throws ServiceException;
	
	public void insertTransferHis(List<TransferHisTbl> traHisTbls)
			throws ServiceException;

	/**
	 * <p>
	 * 트랜스퍼 작업 히스토리 수정
	 * <p>
	 * 
	 * @param traHisTbl
	 * @return
	 * @throws ServiceException
	 */
	public int updateTransferHis(TransferHisTbl traHisTbl)
			throws ServiceException;

	/**
	 * 트랜스퍼 인스턴스별로 할당할 작업을 조회한다.
	 * 
	 * @param status
	 * @return
	 * @throws ServiceException
	 */
	public TransferHisTbl getTransferHisJob(Status status)
			throws ServiceException;

	/**
	 * 트랜스코더 인스턴스별로 할당할 작업을 조회한다.
	 * 
	 * @param status
	 * @return
	 * @throws ServiceException
	 */
	public TranscorderHisTbl getTranscoderHisJob(Status status)
			throws ServiceException;

	@Transactional
	public void updateTransferHisState(TransferHisTbl traHisTbl)
			throws ServiceException;

	@Transactional
	public void updateTranscorderHisState(TranscorderHisTbl trsHisTbl)
			throws ServiceException;
	
	public void updateTransRequest(TransferHisTbl trsHisTbl)
	throws ServiceException;
	
	public void updateTransRequest2(TransferHisTbl trsHisTbl)
	throws ServiceException;
	
	public void updateProflRequest(TranscorderHisTbl trsHisTbl)
	throws ServiceException;
	
	public void updateAllProflRequest(TranscorderHisTbl trsHisTbl)
	throws ServiceException;
	
	public void updateProfl(TranscorderHisTbl trsHisTbl)
	throws ServiceException;

	public void updateEquipmentState(EquipmentTbl equipmentTbl)
			throws ServiceException;
	
	public void instdeleteEquipment(EquipmentTbl equipmentTbl)
	throws ServiceException;
	
	public void devicedeleteEquipment(EquipmentTbl equipmentTbl)
	throws ServiceException;

	/**
	 * 트랜스코더 인스턴스별로 할당할 작업을 조회한다.
	 * 
	 * @param status
	 * @return
	 * @throws ServiceException
	 */
	public TranscorderHisTbl getTranscoderHisJobID(Long jobId)
			throws ServiceException;

	/**
	 * 디바이스 정보 입력
	 * 
	 * @param equipmentTbl
	 * @return
	 * @throws ServiceException
	 */
	public void insertEquipment(EquipmentTbl equipmentTbl)
			throws ServiceException;

	/**
	 * 디바이스 정보 상세보기
	 * 
	 * @param
	 * @return
	 * @throws ServiceException
	 */
	public EquipmentTbl getEquipment(Map<String, Object> params)
			throws ServiceException;
	
	/**
	 * 디바이스 카운트
	 * @param 
	 * @return
	 * @throws ServiceException
	 */
	public EquipmentTbl getEquipmentCount(Map<String, Object> params) throws ServiceException;
	
	public List<TranscorderHisTbl> findTranscodeJob(Integer size) throws ServiceException;
	
	public List<TranscorderHisTbl> findTranscodeJob(String wrkStatCd) throws ServiceException;
	
	public List<TransferHisTbl> findTransferJob(Integer size, String vdoBitRate, String transferGB) throws ServiceException;
	
	public List<TransferHisTbl> findTransferJob(String wrkStatCd, String vdoBitRate, String transferGB) throws ServiceException;
	
	public ContentsTbl getTranscodeInfo(Long seq) throws ServiceException;
	
	public List<TransferHisTbl> findTransferBusiJob(Long seq) throws ServiceException;
	
	public List<TransferHisTbl> findTransferStatus(Map<String, Object> params) throws ServiceException;
	
	/**
	 * <p>
	 * 메타URL 리스트
	 * <p>
	 * 
	 * @param search
	 * @return
	 * @throws ServiceException
	 */
	public PaginationSupport<TransferHisTbl> findMetaURLlist(Search search)
			throws ServiceException;
	
	public List<TransferHisTbl> findTrsCtiBusi(Map<String, Object> params) throws ServiceException;
	
	public List<Long> findTraHisCtiId(String wrkStatCd, String avGubun) throws ServiceException;
	
	public List<TranscorderHisTbl> findTraHisJobCtiId(Long ctiId) throws ServiceException;
	
	public TranscorderHisTbl getTraHis(Map<String, Object> params) throws ServiceException;
	
	public List<TranscorderHisTbl> getTraHisPrgrs(Map<String, Object> params) throws ServiceException;

	/**
	 * 트랜스퍼 prgrs 가져오기
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<TransferHisTbl> getTrsHisPrgrs(Map<String, Object> params) throws ServiceException;


	public Integer getQcCount(Long seq) throws ServiceException;
}
