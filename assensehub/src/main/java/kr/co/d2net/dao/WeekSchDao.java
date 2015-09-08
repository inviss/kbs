package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.dto.WeekSchTbl;

public interface WeekSchDao {

	/**
	 * <p>
	 * 주간평성표 상세조회
	 * <p>
	 * 
	 * @param weekSchTbl
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public WeekSchTbl getWeekSch(Map<String, Object> weekSchTbl)
			throws DaoNonRollbackException;

	/**
	 * <p>
	 * 주간편성표 입력
	 * <p>
	 * 
	 * @param weekSchTbl
	 * @throws DaoNonRollbackException
	 */
	public void insertWeekSch(WeekSchTbl weekSchTbl)
			throws DaoNonRollbackException;

	/**
	 * <p>
	 * 주간편성표 수정
	 * <p>
	 * 
	 * @param weekSchTbl
	 * @throws DaoNonRollbackException
	 */
	public void updateWeekSch(WeekSchTbl weekSchTbl)
			throws DaoNonRollbackException;

	/**
	 * <p>
	 * 주간편성표 리스트 조회
	 * <p>
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<WeekSchTbl> findWeekSchs(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * <p>
	 * 주간편성표 삭제
	 * <p>
	 * 
	 * @param weekSchTbl
	 * @throws DaoNonRollbackException
	 */
	public void deleteWeekSch(WeekSchTbl weekSchTbl)
			throws DaoNonRollbackException;
}
