package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.dto.DairyOrderTbl;

public interface DairyOrderDao {

	/**
	 * <p>
	 * 일일운행표 입력
	 * <p>
	 * 
	 * @param dairyOrderTbl
	 * @throws DaoNonRollbackException
	 */
	public void insertDairyOrder(DairyOrderTbl dairyOrderTbl)
			throws DaoNonRollbackException;

	/**
	 * <p>
	 * 일일운행표 수정
	 * <p>
	 * 
	 * @param dairyOrderTbl
	 * @throws DaoNonRollbackException
	 */
	public void updateDairyOrder(DairyOrderTbl dairyOrderTbl)
			throws DaoNonRollbackException;

	/**
	 * <p>
	 * 일일운행표 삭제
	 * <p>
	 * 
	 * @param dairyOrderTbl
	 * @throws DaoNonRollbackException
	 */
	public void deleteDairyOrder(DairyOrderTbl dairyOrderTbl)
			throws DaoNonRollbackException;

	/**
	 * <p>
	 * 일일운행표 상세조회
	 * <p>
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public DairyOrderTbl getDairyOrderTbl(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * <p>
	 * 일일운행표 조회
	 * <p>
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<DairyOrderTbl> findDairyOrderTbl(Map<String, Object> params)
			throws DaoNonRollbackException;

}
