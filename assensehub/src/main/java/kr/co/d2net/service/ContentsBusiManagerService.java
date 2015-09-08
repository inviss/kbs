package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dto.ContentsBusiTbl;


import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ContentsBusiManagerService {
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */	
	public List<ContentsBusiTbl> findContentsBusi(Map<String, Object> params)throws ServiceException;

	/**
	 * 
	 * @param 
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentsBusiTbl getContentsBusi(Map<String, Object> params) throws ServiceException;

	
	public void insertContentsBusi(ContentsBusiTbl contentsbusiTbl) throws ServiceException;
}
