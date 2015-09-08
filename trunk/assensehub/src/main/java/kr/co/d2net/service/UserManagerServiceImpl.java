package kr.co.d2net.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dao.AuthDao;
import kr.co.d2net.dao.MenuDao;
import kr.co.d2net.dao.RoleAuthDao;
import kr.co.d2net.dao.UserAuthDao;
import kr.co.d2net.dao.UserDao;
import kr.co.d2net.dto.AuthTbl;
import kr.co.d2net.dto.MenuTbl;
import kr.co.d2net.dto.RoleAuthTbl;
import kr.co.d2net.dto.UserAuthTbl;
import kr.co.d2net.dto.UserTbl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userManagerService")
public class UserManagerServiceImpl implements UserManagerService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserAuthDao userAuthDao;
	@Autowired
	private RoleAuthDao roleAuthDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private AuthDao authDao;

	@SuppressWarnings("unchecked")
	public List<UserTbl> findUsers(Map<String, Object> params)
			throws ServiceException {
		List<UserTbl> userTbls = userDao.findUsers(params);
		return (userTbls == null) ? Collections.EMPTY_LIST : userTbls;
	}

	@Override
	public UserTbl getUser(String userId) throws ServiceException {
		return userDao.getUser(userId);
	}

	@Override
	public void insertUser(UserTbl userTbl) throws ServiceException {
		userTbl.setUseYn("Y");
		userTbl.setRegDt(Utility.getTimestamp());
		userDao.insertUser(userTbl);

		UserAuthTbl userAuthTbl = new UserAuthTbl();
		userAuthTbl.setUserId(userTbl.getUserId());
		userAuthTbl.setAuthId(userTbl.getAuthId());

		userAuthDao.insertUserAuth(userAuthTbl);
	}

	@Override
	public UserTbl getUserAuth(Map<String, Object> params)
			throws ServiceException {
		return userDao.getUserAuth(params);
	}

	@Override
	public RoleAuthTbl getRoleAuth(Map<String, Object> params)
			throws ServiceException {
		return roleAuthDao.getRoleAuth(params);
	}

	@Override
	public void insertAuthMenu(RoleAuthTbl roleAuthTbl) throws ServiceException {
		roleAuthDao.insertRoleAuth(roleAuthTbl);
	}

	@Override
	public List<MenuTbl> findUserMenus(Map<String, Object> params)
			throws ServiceException {
		return menuDao.findUserMenus(params);
	}

	@Override
	public List<AuthTbl> findAuths() throws ServiceException {
		List<AuthTbl> auths = authDao.findAuth();
		return (auths == null) ? Collections.EMPTY_LIST : auths;
	}

	@Override
	public void updateUser(UserTbl userTbl) throws ServiceException {
		UserAuthTbl userAuthTbl = new UserAuthTbl();
		userAuthTbl.setUserId(userTbl.getUserId());
		userAuthTbl.setAuthId(userTbl.getAuthId());
		
		UserTbl userTbls = new UserTbl();
		userTbls.setUserId(userTbl.getUserId());
		userTbls.setUserPass(Utility.encodeMD5(userTbl.getUserPass()));
		
		userAuthDao.updateUserAuth(userAuthTbl);
		userDao.updateUser(userTbls);
	}

	@Override
	public void deleteUser(String[] userIds) throws ServiceException {
		for (String userId : userIds) {

			UserTbl userTbl = new UserTbl();
			userTbl.setUserId(userId);
			userTbl.setUseYn("N");
			userTbl.setModDt(Utility.getTimestamp());

			userDao.updateUser(userTbl);
		}
	}

	@Override
	public void insertRoleAuth(RoleAuthTbl roleAuthTbl) throws ServiceException {
		roleAuthDao.insertRoleAuth(roleAuthTbl);

	}

	@Override
	public List<RoleAuthTbl> findRoleAuth(Map<String, Object> params)
			throws ServiceException {
		List<RoleAuthTbl> roleauths = roleAuthDao.findRoleAuth(params);
		return (roleauths == null) ? Collections.EMPTY_LIST : roleauths;
	}

	@Override
	public void deleteRoleAuth(RoleAuthTbl roleAuthTbl) throws ServiceException {
		roleAuthDao.deleteRoleAuth(roleAuthTbl);
	}

	
	@Override
	public void deleteRoleAuth2(RoleAuthTbl roleAuthTbl) throws ServiceException {
		roleAuthDao.deleteRoleAuth2(roleAuthTbl);
	}


	@Override
	public String getUserControlAuth(Map<String, Object> params)
			throws ServiceException {
		return roleAuthDao.getUserAuth(params);
	}

	@Override
	public MenuTbl getFirstMenu(Map<String, Object> params)
			throws ServiceException {
		return menuDao.getFirstMenu(params);
	}


}
