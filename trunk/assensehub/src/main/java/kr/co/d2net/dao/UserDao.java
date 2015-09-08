package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.UserTbl;

public interface UserDao {
	/**
	 * <p>
	 * 사용자 목록 조회
	 * <p>
	 * 
	 * @param java
	 *            .util.Map(Key, Value)
	 * @return Array Users
	 * @throws DaoNonRollbackException
	 */
	public List<UserTbl> findUsers(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * <p>
	 * 사용자 조회
	 * <p>
	 * 
	 * @param userId
	 * @return User
	 * @throws DaoNonRollbackException
	 */
	public UserTbl getUser(String userId) throws DaoNonRollbackException;

	/**
	 * <p>
	 * 사용자와 권한ID 포함 조회
	 * <p>
	 * 
	 * @param userId
	 * @return User
	 * @throws DaoNonRollbackException
	 */
	public UserTbl getUserAuth(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * <p>
	 * 사용자 정보 변경
	 * <p>
	 * 
	 * @param userTbl
	 * @throws DaoRollbackException
	 */
	public void updateUser(UserTbl userTbl) throws DaoRollbackException;

	/**
	 * <p>
	 * 사용자 정보 등록
	 * <p>
	 * 
	 * @param userTbl
	 * @throws DaoRollbackException
	 */
	public void insertUser(UserTbl userTbl) throws DaoRollbackException;

}
