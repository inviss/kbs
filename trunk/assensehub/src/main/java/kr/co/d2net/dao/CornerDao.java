package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.CornerTbl;

public interface CornerDao {
	/**
	 * 코너목록 조회
	 * @return List<AuthTbl>
	 * @throws DaoNonRollbackException
	 */
	public List<CornerTbl> findCorner(Map<String, Object> params) throws DaoNonRollbackException;
	
	public CornerTbl getCorner(Map<String, Object> params) throws DaoNonRollbackException;
	
	public void updateCorner(CornerTbl cornerTbl) throws DaoRollbackException;
	
	public void insertCorner(CornerTbl cornerTbl) throws DaoRollbackException;
}
