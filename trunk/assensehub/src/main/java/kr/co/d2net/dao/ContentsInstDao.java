package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.ContentsInstTbl;

public interface ContentsInstDao {
	/**
	 * 콘텐츠 인스턴스 정보를 조회한다.
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsInstTbl> findContentsInst(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * 콘텐츠 인스턴스 정보를 상세조회한다.
	 * 
	 * @param
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentsInstTbl getContentsInst(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * 콘텐츠 인스턴스 간략정보를 조회한다.
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsInstTbl> findContentsInstSummary(
			Map<String, Object> params) throws DaoNonRollbackException;
	
	/**
	 * 콘텐츠 원본 간략정보를 조회한다.
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsInstTbl> findContentsSummary(
			Map<String, Object> params) throws DaoNonRollbackException;
	
	public List<ContentsInstTbl> findDelContents(
			Map<String, Object> params) throws DaoNonRollbackException;
	
	/**
	 * 스토리지 추가에 따른 자동삭제 될 에센스 정보 조회
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsInstTbl> findDefaultDeleteContentsInst(Map<String,Object> params) throws DaoNonRollbackException;
	
	
	/**
	 * RMS 콘텐츠 인스턴스 간략정보를 조회한다.
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsInstTbl> findContentsRms(
			Map<String, Object> params) throws DaoNonRollbackException;

	/**
	 * 삭제 콘텐츠 인스턴스 정보 요청
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsInstTbl> findDeleteContentsInst(
			Map<String, Object> params) throws DaoNonRollbackException;

	/**
	 * 콘텐츠 인스턴스 정보를 등록한다.
	 * 
	 * @param contentsinstTbl
	 * @throws DaoRollbackException
	 */
	public Long insertContentsInst(ContentsInstTbl contentsinstTbl)
			throws DaoRollbackException;

	/**
	 * 콘텐츠 인스턴스 정보 삭제
	 * 
	 * @param contentsinstTbl
	 * @throws DaoRollbackException
	 */
	public void deleteContentsInst(ContentsInstTbl contentsinstTbl)
			throws DaoRollbackException;

	/**
	 * 콘텐츠 인스턴스 조회 미디어 툴
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsInstTbl> findContentsInstSummaryByMediaTool(
			Map<String, Object> params) throws DaoNonRollbackException;
	
	
	
	public void updateContentsInst(ContentsInstTbl contentsinstTbl)
	throws DaoRollbackException;
	
	/**
	 * 다시보기 'URL' 연동에 따른 콘텐츠 및 인스턴스 정보 조회
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentsInstTbl getContentsInstForwardMeta(Map<String, Object> params)
			throws DaoNonRollbackException;

}
