package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dto.BusiPartnerPgm;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BusiPartnerPgmManagerService {

	/**
	 * 프로그램별 사업자 정보 조회
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<BusiPartnerPgm> findBusiPartnerPgm(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 프로그램별 사업자 정보 상세조회
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public BusiPartnerPgm getBusiPartnerPgm(Map<String, Object> params)
			throws ServiceException;
	
	public String getPgmAudioMode(String pgmCode,String ctTyp)
			throws ServiceException;

	/**
	 * 프로그램별 사업자 정보 입력
	 * 
	 * @param busipartnerPgm
	 * @throws ServiceException
	 */
	public void insertBusiPartnerPgm(BusiPartnerPgm busipartnerPgm)
			throws ServiceException;

	/**
	 * 프로그램별 사업자 정보 삭제
	 * 
	 * @param busipartnerPgm
	 * @throws ServiceException
	 */
	public void deleteBusiPartnerPgm(BusiPartnerPgm busipartnerPgm)
			throws ServiceException;
}
