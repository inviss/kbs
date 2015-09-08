package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.CodeTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.xml.internal.FindList;
import kr.co.d2net.dto.xml.internal.Workflow;

public interface CodeDao {
	/**
	 * 코드 목록을 조회
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<CodeTbl> findCode(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * 단일 코드 조회
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public CodeTbl getCode(Map<String, Object> params)
			throws DaoNonRollbackException;
	
	/**
	 * 구분코드조회
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<CodeTbl> findCodeClfCd(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * 장비추가시 rmk2를 +1
	 * 
	 * @param
	 * @throws ServiceException
	 */
	public void updateCode(CodeTbl codeTbl) throws DaoRollbackException;

	/**
	 * 미디어 툴에 코드셋 정보 전달
	 * 
	 * @param workflow
	 * @return
	 * @throws DaoRollbackException
	 */
	public FindList findCodeList(Workflow workflow) throws DaoRollbackException;
	
	public void insertCode(CodeTbl codeTbl) throws DaoRollbackException;
	
	public PaginationSupport<CodeTbl> findcodeListView(Search search) throws DaoNonRollbackException;
	

}
