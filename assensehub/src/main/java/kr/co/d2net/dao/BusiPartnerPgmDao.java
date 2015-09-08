package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.BusiPartnerPgm;

public interface BusiPartnerPgmDao {
	/**
	 * 프로그램 사업자 정보를 조회
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<BusiPartnerPgm> findBusiPartnerPgm(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * 프로그램 사업자 정보를 상세조회
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public BusiPartnerPgm getBusiPartnerPgm(Map<String, Object> params)
			throws DaoNonRollbackException;
	
	public String getPgmAudioMode(String pgmCode, String ctTyp)
			throws DaoNonRollbackException;

	/**
	 * 프로그램 사업자 정보를 입력
	 * 
	 * @param busipartnerPgm
	 * @throws DaoRollbackException
	 */
	public void insertBusiPartnerPgm(BusiPartnerPgm busipartnerPgm)
			throws DaoRollbackException;

	/**
	 * 프로그램 사업자 정보를 삭제
	 * 
	 * @param busipartnerPgm
	 * @throws DaoRollbackException
	 */
	public void deleteBusiPartnerPgm(BusiPartnerPgm busipartnerPgm)
			throws DaoRollbackException;
}
