package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.BusiPartnerTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.Search;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BusiPartnerManagerService {
	
	/**
	 * 사업자리스트
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<BusiPartnerTbl> findBusiPartner(Map<String, Object> params)throws ServiceException;

	/**
	 * 사업자조회
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public BusiPartnerTbl getBusiPartner(Map<String, Object> params) throws ServiceException;

	public BusiPartnerTbl getBusiPartnerId(Map<String, Object> params) throws ServiceException;
	
	public void insertBusiPartner(BusiPartnerTbl busipartnerTbl) throws ServiceException; 
	
	public void updateBusiPartner(BusiPartnerTbl busipartnerTbl) throws ServiceException;
	
	public void deleteBusiPartner(BusiPartnerTbl busipartnerTbl) throws ServiceException;
	
	public PaginationSupport<BusiPartnerTbl> findBusiPartnerList(Search search) throws ServiceException;
}
