package kr.co.d2net.dao;

import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.UserAuthTbl;

public interface UserAuthDao {
	/**
	 * <p>
	 * 사용자별 권한 등록
	 * <p>
	 * 
	 * 
	 * @param userAuthTbl
	 * @throws DaoRollbackException
	 */
	public void insertUserAuth(UserAuthTbl userAuthTbl)
			throws DaoRollbackException;
	
	public void updateUserAuth(UserAuthTbl userAuthTbl) throws DaoRollbackException;
}
