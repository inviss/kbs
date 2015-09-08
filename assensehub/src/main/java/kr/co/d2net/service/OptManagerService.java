package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;

import kr.co.d2net.dto.OptTbl;
import kr.co.d2net.dto.Search;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OptManagerService {
	
	/**
	 * 프로파일옵션리스트
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */	
	public List<OptTbl> findOpt(Map<String, Object> params)throws ServiceException;

	/**
	 * 프로파일옵션조회
	 * @param brdDd
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public OptTbl getOpt(Map<String, Object> params) throws ServiceException;

	
	public void insertOpt(OptTbl opt) throws ServiceException;
	
	public void updateOpt(OptTbl opt) throws ServiceException;
	
	public void updateOpt2(OptTbl opt) throws ServiceException;
	
	public void updateOpt3(OptTbl opt) throws ServiceException;
	
	public void deleteOpt(OptTbl opt) throws ServiceException;
	
	public PaginationSupport<OptTbl> findOptList(Search search) throws ServiceException;
}
