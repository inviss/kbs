package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.DisuseInfoTbl;
import kr.co.d2net.dto.Search;

public interface DisuseInfoDao {
	/**
	 * 폐기리스트
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<DisuseInfoTbl> findDisuseInfo(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * 폐기조회
	 * 
	 * @param brdDd
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public DisuseInfoTbl getDisuseInfo(Map<String, Object> params)
			throws DaoNonRollbackException;

	public void insertDisuseInfo(DisuseInfoTbl disuseinfoTbl)
			throws DaoRollbackException;

	/**
	 * <p>
	 * 폐기 대상 목록 조회. 페이징 처리가 필요한 메소드에 Spring JDBC를 이용하여 처리한다.
	 * </p>
	 * 
	 * @param Search
	 *            : 사용자가 요청한 검색조건을 담은 bean
	 * @return PaginationSupport
	 * @throws DaoNonRollbackException
	 */
	public PaginationSupport<DisuseInfoTbl> finddisuseList(Search search)
			throws DaoNonRollbackException;

	public PaginationSupport<DisuseInfoTbl> finddisuseList2(Search search)
			throws DaoNonRollbackException;

	public PaginationSupport<DisuseInfoTbl> finddisuseStatusList(Search search)
			throws DaoNonRollbackException;

	public PaginationSupport<DisuseInfoTbl> finddisuseRenewalList(Search search)
			throws DaoNonRollbackException;

	/**
	 * 삭제 정보 수정
	 * 
	 * @param disuseinfoTbl
	 * @throws DaoRollbackException
	 */
	public void updateDisuseInfo(DisuseInfoTbl disuseinfoTbl)
			throws DaoRollbackException;
}
