package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.BusiPartnerTbl;
import kr.co.d2net.dto.Search;

public interface BusiPartnerDao {
	/**
	 * 사업자리스트
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */	
	public List<BusiPartnerTbl> findBusiPartner(Map<String, Object> params)throws DaoNonRollbackException;

	/**
	 * 사업자조회
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public BusiPartnerTbl getBusiPartner(Map<String, Object> params) throws DaoNonRollbackException;
	
	public BusiPartnerTbl getBusiPartnerId(Map<String, Object> params) throws DaoNonRollbackException;

	
	public void insertBusiPartner(BusiPartnerTbl busipartnerTbl) throws DaoRollbackException;
	
	public void updateBusiPartner(BusiPartnerTbl busipartnerTbl) throws DaoRollbackException;
	
	public void deleteBusiPartner(BusiPartnerTbl busipartnerTbl) throws DaoRollbackException;
	
	/**
	 * <p>
	 * 프로파일 요청 목록 조회.
	 * 페이징 처리가 필요한 메소드에 Spring JDBC를 이용하여 처리한다.
	 * </p>
	 * @param Search : 사용자가 요청한 검색조건을 담은 bean
	 * @return PaginationSupport
	 * @throws DaoNonRollbackException
	 */
	public PaginationSupport<BusiPartnerTbl> findbusipartnerList(Search search) throws DaoNonRollbackException;
}
