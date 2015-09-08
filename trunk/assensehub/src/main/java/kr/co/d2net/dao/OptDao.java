package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.OptTbl;
import kr.co.d2net.dto.Search;

public interface OptDao {
	/**
	 * 프로파일리스트
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */	
	public List<OptTbl> findOpt(Map<String, Object> params)throws DaoNonRollbackException;

	/**
	 * 프로파일조회
	 * @param brdDd
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public OptTbl getOpt(Map<String, Object> params) throws DaoNonRollbackException;

	
	public void insertOpt(OptTbl opt) throws DaoRollbackException;
	
	
	public void updateOpt(OptTbl opt) throws DaoRollbackException;
	
	public void updateOpt2(OptTbl opt) throws DaoRollbackException;
	
	public void updateOpt3(OptTbl opt) throws DaoRollbackException;
	
	public void deleteOpt(OptTbl opt) throws DaoRollbackException;
	
	/**
	 * <p>
	 * 프로파일 요청 목록 조회.
	 * 페이징 처리가 필요한 메소드에 Spring JDBC를 이용하여 처리한다.
	 * </p>
	 * @param Search : 사용자가 요청한 검색조건을 담은 bean
	 * @return PaginationSupport
	 * @throws DaoNonRollbackException
	 */
	public PaginationSupport<OptTbl> findoptList(Search search) throws DaoNonRollbackException;
	
}
