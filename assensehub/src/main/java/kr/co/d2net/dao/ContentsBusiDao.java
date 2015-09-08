package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.ContentsBusiTbl;


public interface ContentsBusiDao {
	/**
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */	
	public List<ContentsBusiTbl> findContentsBusi(Map<String, Object> params)throws DaoNonRollbackException;

	/**
	 * 
	 * @param brdDd
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentsBusiTbl getContentsBusi(Map<String, Object> params) throws DaoNonRollbackException;

	
	public void insertContentsBusi(ContentsBusiTbl contentsbusiTbl) throws DaoRollbackException;
}
