package kr.co.d2net.dao;

import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.dto.LiveTbl;

public interface LiveDao {

	/**
	 * 녹화여부 상세조회
	 * 
	 * @param liveTbl
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public LiveTbl getLive(Map<String, Object> params) throws DaoNonRollbackException;

	/**
	 * 녹화여부 입력
	 * 
	 * @param liveTbl
	 * @throws DaoNonRollbackException
	 */
	public void insertLive(LiveTbl liveTbl) throws DaoNonRollbackException;

	/**
	 * 녹화여부 수정
	 * 
	 * @param liveTbl
	 * @throws DaoNonRollbackException
	 */
	public int updateLive(LiveTbl liveTbl) throws DaoNonRollbackException;

	/**
	 * 녹화여부 삭제
	 * 
	 * @param liveTbl
	 * @throws DaoNonRollbackException
	 */
	public int deleteLive(LiveTbl liveTbl) throws DaoNonRollbackException;

}
