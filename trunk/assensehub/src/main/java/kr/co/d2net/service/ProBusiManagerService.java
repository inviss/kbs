package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dto.ProBusiTbl;
import kr.co.d2net.dto.ProFlTbl;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProBusiManagerService {

	/**
	 * 사업자별프로파일리스트
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ProBusiTbl> findProBusis(Map<String, Object> params)
			throws ServiceException;

	public List<ProBusiTbl> findProBusi(Map<String, Object> params)
			throws ServiceException;

	

	/**
	 * 사업자별프로파일조회
	 * 
	 * @param brdDd
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ProBusiTbl> findProBusi2(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 사업자별 프로파일 정보 입력
	 * 
	 * @param probusiTbl
	 * @throws ServiceException
	 */
	public void insertProBusi(ProBusiTbl probusiTbl) throws ServiceException;

	public void deleteProBusi(ProBusiTbl probusiTbl) throws ServiceException;

	/**
	 * 프로그램별 프로파일 정보 조회
	 * 
	 * @author dekim
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ProFlTbl> findPgmProBusi(Map<String, Object> params)
			throws ServiceException;
	
	/**
	 * 프로그램별 새로추가된 프로파일 정보 조회
	 * 
	 * @author dekim
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ProFlTbl> findNewPgmProBusi(Map<String, Object> params)
			throws ServiceException;
	
	/**
	 * 사업자별 프로파일 정보 조회
	 * 
	 * @author dekim
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public ProBusiTbl getProBusi(Map<String, Object> params) throws ServiceException;
}
