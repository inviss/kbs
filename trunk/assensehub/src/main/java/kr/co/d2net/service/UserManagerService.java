package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dto.AuthTbl;
import kr.co.d2net.dto.MenuTbl;
import kr.co.d2net.dto.RoleAuthTbl;
import kr.co.d2net.dto.UserTbl;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserManagerService {

	/**
	 * 권한별 버튼 활성화/비활성화
	 * 
	 * @author dekim
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public String getUserControlAuth(Map<String, Object> params)
			throws ServiceException;

	public RoleAuthTbl getRoleAuth(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 사용자 목록 조회
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<UserTbl> findUsers(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 개별 사용자 조회
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public UserTbl getUser(String userId) throws ServiceException;

	/**
	 * 개별 사용자와 권한 조회
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public UserTbl getUserAuth(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 
	 * @param userTbl
	 * @throws ServiceException
	 */
	@Transactional
	public void insertUser(UserTbl userTbl) throws ServiceException;

	@Transactional
	public void updateUser(UserTbl userTbl) throws ServiceException;

	/**
	 * 권한별 접근 가능 메뉴 등록
	 * 
	 * @param roleAuthTbl
	 * @throws ServiceException
	 */
	@Transactional
	public void insertAuthMenu(RoleAuthTbl roleAuthTbl) throws ServiceException;

	/**
	 * 유저별 접근 가능 메뉴
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<MenuTbl> findUserMenus(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 권한 리스트
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<AuthTbl> findAuths() throws ServiceException;

	public void deleteUser(String[] userIds) throws ServiceException;

	public void deleteRoleAuth(RoleAuthTbl roleAuthTbl) throws ServiceException;

	public void deleteRoleAuth2(RoleAuthTbl roleAuthTbl) throws ServiceException;
	
	public void insertRoleAuth(RoleAuthTbl roleAuthTbl) throws ServiceException;

	public List<RoleAuthTbl> findRoleAuth(Map<String, Object> params)
			throws ServiceException;
	
	public MenuTbl getFirstMenu(Map<String, Object> params) throws ServiceException;

}
