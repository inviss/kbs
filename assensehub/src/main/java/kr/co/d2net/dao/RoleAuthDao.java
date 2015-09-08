package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.RoleAuthTbl;

public interface RoleAuthDao {
	/**
	 * 권한별 메뉴 등록
	 * 
	 * @param roleAuthTbl
	 * @throws DaoRollbackException
	 */
	public void insertRoleAuth(RoleAuthTbl roleAuthTbl)
			throws DaoRollbackException;

	/**
	 * 권한별 접근 메뉴목록
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<RoleAuthTbl> findRoleAuth(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * 권한별 상세조회
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public RoleAuthTbl getRoleAuth(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * 권한별 삭제
	 * 
	 * @param roleAuthTbl
	 * @throws DaoRollbackException
	 */
	public void deleteRoleAuth(RoleAuthTbl roleAuthTbl)
			throws DaoRollbackException;

	/**
	 * 사용자별 권한 조회
	 * 
	 * @author dekim
	 * @param params
	 * @return
	 * @throws DaoRollbackException
	 */
	public String getUserAuth(Map<String, Object> params)
			throws DaoRollbackException;
	
	public void deleteRoleAuth2(RoleAuthTbl roleAuthTbl) throws DaoRollbackException;
}
