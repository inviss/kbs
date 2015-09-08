package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.Search;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProFlManagerService {
	
	/**
	 * 프로파일리스트
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */	
	public List<ProFlTbl> findProFl(Map<String, Object> params)throws ServiceException;

	/**
	 * 프로파일조회
	 * @param brdDd
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ProFlTbl getProFl(Map<String, Object> params) throws ServiceException;

	public ProFlTbl getProFlId(Map<String, Object> params) throws ServiceException;
	
	public void insertProFl(ProFlTbl proflTbl) throws ServiceException;
	
	public void updateProFl(ProFlTbl proflTbl) throws ServiceException;
	
	public void deleteProFl(ProFlTbl proflTbl) throws ServiceException;
	
	public PaginationSupport<ProFlTbl> findProFlList(Search search) throws ServiceException;
	
	public List<String> findProflExt(String mediaCla) throws ServiceException;
}
