package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.UserTbl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value="userDao")
public class UserDaoImpl extends SqlMapClientDaoSupport implements UserDao {

	private static Log logger = LogFactory.getLog(UserDaoImpl.class);

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}

	@SuppressWarnings("unchecked")
	public List<UserTbl> findUsers(Map<String, Object> params)
	throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("User.findUser", params);
	}

	@Override
	public UserTbl getUser(String userId) throws DaoNonRollbackException {
		return (UserTbl)getSqlMapClientTemplate().queryForObject("User.getUser", userId);
	}

	@Override
	public void updateUser(UserTbl userTbl) throws DaoRollbackException {
		getSqlMapClientTemplate().update("User.updateUser", userTbl);
	}

	@Override
	public void insertUser(UserTbl userTbl) throws DaoRollbackException {
		getSqlMapClientTemplate().insert("User.insertUser", userTbl);
	}

	@Override
	public UserTbl getUserAuth(Map<String, Object> params) throws DaoNonRollbackException {
		return (UserTbl)getSqlMapClientTemplate().queryForObject("User.getUserAuth", params);
	}

}
