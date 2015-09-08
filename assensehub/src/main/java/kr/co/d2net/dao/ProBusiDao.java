package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.ProBusiTbl;
import kr.co.d2net.dto.ProFlTbl;

public interface ProBusiDao {
	/**
	 * 사업자별프로파일리스트
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ProBusiTbl> findProBusis(Map<String, Object> params)
			throws DaoNonRollbackException;

	public List<ProBusiTbl> findProBusi(Map<String, Object> params)
			throws DaoNonRollbackException;

	public List<ProBusiTbl> findProBusi2(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * 사업자별프로파일조회
	 * 
	 * @param brdDd
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ProBusiTbl getProBusi(Map<String, Object> params)
			throws DaoNonRollbackException;

	public void deleteProBusi(ProBusiTbl probusiTbl)
			throws DaoRollbackException;

	public void insertProBusi(ProBusiTbl probusiTbl)
			throws DaoRollbackException;

	/**
	 * 프로그램별 프로파일 정보 조회
	 * 
	 * @author dekim
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ProFlTbl> findPgmProBusi(Map<String, Object> params)
			throws DaoNonRollbackException;
	
	/**
	 * 프로그램별 새로추가된 프로파일 정보 조회
	 * 
	 * @author dekim
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ProFlTbl> findNewPgmProBusi(Map<String, Object> params)
			throws DaoNonRollbackException;
}
