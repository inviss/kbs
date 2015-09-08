package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.TranscorderHisTbl;

public interface TranscorderHisDao {

	/**
	 * <p>
	 * 트랜스코딩 작업 현황 및 히스토리 조회
	 * <p>
	 * 
	 * @param search
	 *            사용자가 요청한 검색조건을 담은 bean
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public PaginationSupport<TranscorderHisTbl> findTranscorderHisWork(
			Search search) throws DaoNonRollbackException;
	
	/**
	 * 외부 장비에 트랜스퍼 작업을 할당하기위해 단일건을 조회하여 반환한다.
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public TranscorderHisTbl getTranscorderHisJob(Map<String, Object> params) throws DaoNonRollbackException;

	/**
	 * <p>
	 * 트랜스코딩 작업 내용 입력
	 * <p>
	 * 
	 * @param traHisTbl
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public void insertTrancorderHisWork(TranscorderHisTbl transcorderHisTbl)
			throws DaoRollbackException;
	
	public void insertTrancorderHisWork(List<TranscorderHisTbl> transcorderHisTbls)
			throws DaoRollbackException;

	/**
	 * <p>
	 * 트랜스코딩 작업내용 수정
	 * <p>
	 * 
	 * @param traHisTbl
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public int updateTranscorderHisWork(TranscorderHisTbl transcorderHisTbl)
			throws DaoRollbackException;
	
	/**
	 * <p>
	 * 서비스 콘텐츠에서 프로파일 재요청
	 * <p>
	 * 
	 * @param traHisTbl
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public int updateProflRequest(TranscorderHisTbl transcorderHisTbl)
			throws DaoRollbackException;
	
	/**
	 * <p>
	 * 서비스 콘텐츠에서 프로파일 재요청
	 * <p>
	 * 
	 * @param traHisTbl
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public int updateAllProflRequest(TranscorderHisTbl transcorderHisTbl)
			throws DaoRollbackException;
	
	/**
	 * <p>
	 * 작업관리에서 프로파일 재요청
	 * <p>
	 * 
	 * @param traHisTbl
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public int updateProfl(TranscorderHisTbl transcorderHisTbl)
			throws DaoRollbackException;
	
	/**
	 * 상태 및 진행률 변경
	 * @param transcorderHisTbl
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public int updateTranscorderHisState(TranscorderHisTbl transcorderHisTbl)
	throws DaoRollbackException;
	
	/**
	 * 외부 장비에 트랜스퍼 작업을 할당하기위해 단일건을 조회하여 반환한다.
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public TranscorderHisTbl getTranscorderHisJobID(Long jobId) throws DaoNonRollbackException;
	
	public List<TranscorderHisTbl> findTranscorderHisJob(Integer size) throws DaoNonRollbackException;
	
	public List<TranscorderHisTbl> findTranscorderHisJob(String wrkStatCd) throws DaoNonRollbackException;
	
	public ContentsTbl getTranscodeInfo(Long seq) throws DaoNonRollbackException;
	
	public TranscorderHisTbl getTraHis(Map<String, Object> params) throws DaoNonRollbackException;
	
	public List<Long> findTraHisCtiId(Map<String, Object> params) throws DaoNonRollbackException;
	
	public List<TranscorderHisTbl> findTraHisJobCtiId(Long ctiId) throws DaoNonRollbackException;
	
	public List<TranscorderHisTbl>  getTraHisPrgrs(Map<String, Object> params) throws DaoNonRollbackException;
	
}
