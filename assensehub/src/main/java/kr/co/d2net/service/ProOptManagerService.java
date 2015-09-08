package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;

import kr.co.d2net.dto.ProOptTbl;
import kr.co.d2net.dto.Search;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProOptManagerService {
	
	/**
	 * 프로파일옵션리스트
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */	
	public List<ProOptTbl> findProOpt(Map<String, Object> params)throws ServiceException;

	/**
	 * 프로파일옵션조회
	 * @param brdDd
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ProOptTbl getProOpt(Map<String, Object> params) throws ServiceException;

	
	public void insertProOpt(ProOptTbl proopt) throws ServiceException;
	
	public void deleteProOpt(ProOptTbl proopt) throws ServiceException;
	
	public PaginationSupport<ProOptTbl> findProOptList(Search search) throws ServiceException;
}
