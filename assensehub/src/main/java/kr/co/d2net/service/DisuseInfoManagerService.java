package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.DisuseInfoTbl;
import kr.co.d2net.dto.Search;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DisuseInfoManagerService {

	/**
	 * 폐기리스트
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<DisuseInfoTbl> findDisuseInfo(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 폐기조회
	 * 
	 * @param brdDd
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public DisuseInfoTbl getDisuseInfo(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 폐기정보 수정
	 * 
	 * @param disuseInfoTbl
	 * @throws ServiceException
	 */
	public void updateDisuseInfo(DisuseInfoTbl disuseInfoTbl)
			throws ServiceException;

	public void insertDisuseInfo(DisuseInfoTbl disuseinfoTbl)
			throws ServiceException;

	public PaginationSupport<DisuseInfoTbl> findDisuseList(Search search)
			throws ServiceException;

	public PaginationSupport<DisuseInfoTbl> findDisuseList2(Search search)
			throws ServiceException;

	public PaginationSupport<DisuseInfoTbl> finddisuseStatusList(Search search)
			throws ServiceException;

	public PaginationSupport<DisuseInfoTbl> finddisuseRenewalList(Search search)
			throws ServiceException;
}
