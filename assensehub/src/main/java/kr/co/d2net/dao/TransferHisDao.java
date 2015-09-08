package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.TransferHisTbl;

public interface TransferHisDao {
	/**
	 * <p>
	 * 트랜스퍼 요청 목록 조회. 페이징 처리가 필요한 메소드에 Spring JDBC를 이용하여 처리한다.
	 * </p>
	 * 
	 * @param Search
	 *            : 사용자가 요청한 검색조건을 담은 bean
	 * @return PaginationSupport
	 * @throws DaoNonRollbackException
	 */
	public PaginationSupport<TransferHisTbl> findTransferHisWork(Search search)
			throws DaoNonRollbackException;
	
	/**
	 * 외부 장비에 트랜스퍼 작업을 할당하기위해 단일건을 조회하여 반환한다.
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public TransferHisTbl getTransferHisJob(Map<String, Object> params) throws DaoNonRollbackException;
	
	public List<TransferHisTbl> findTransferHisJob(Map<String, Object> params) throws DaoNonRollbackException;
	
	
	public List<TransferHisTbl> findTransferHisJobMap(Map<String, Object> params) throws DaoNonRollbackException;
	
	/**
	 * <p>
	 * 트랜스퍼 요청 입력
	 * <P>
	 * 
	 * @param trsHisTbl
	 * @throws DaoNonRollbackException
	 */
	public void insertTransferHisWork(TransferHisTbl trsHisTbl)
			throws DaoRollbackException;
	
	
	public void insertTransferHisWork(List<TransferHisTbl> trsHisTbls)
			throws DaoRollbackException;

	/**
	 * <p>
	 * 트랜스퍼 요청 수정
	 * <p>
	 * 
	 * @param trsHisTbl
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public int updateTransferHisWork(TransferHisTbl trsHisTbl)
			throws DaoRollbackException;
	
	public List<TransferHisTbl> findTransferBusiJob(Long seq) throws DaoNonRollbackException;
	
	public List<TransferHisTbl> findTransferStatus(Map<String, Object> params) throws DaoNonRollbackException;
	
	/**
	 * <p>
	 * 메타URL 리스트
	 * </p>
	 * 
	 * @param Search
	 *            
	 * @return PaginationSupport
	 * @throws DaoNonRollbackException
	 */
	public PaginationSupport<TransferHisTbl> findMetaURLlist(Search search)
			throws DaoNonRollbackException;
	
	public List<TransferHisTbl> findTrsCtiBusi(Map<String, Object> params) throws DaoNonRollbackException;

	/**
	 * <p>
	 * 프로파일 재전송 요청
	 * <p>
	 * 
	 * @param trsHisTbl
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public int updateTransRequest(TransferHisTbl transferHisTbl)
			throws DaoRollbackException;
	
	public int updateTransRequest2(TransferHisTbl transferHisTbl)
	throws DaoRollbackException;
	
	public List<TransferHisTbl>  getTrsHisPrgrs(Map<String, Object> params) throws DaoNonRollbackException;
	
}
