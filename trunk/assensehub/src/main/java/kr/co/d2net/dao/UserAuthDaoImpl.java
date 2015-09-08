package kr.co.d2net.dao;

import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.UserAuthTbl;
import kr.co.d2net.dto.UserTbl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value="userAuthDao")
public class UserAuthDaoImpl extends SqlMapClientDaoSupport implements UserAuthDao {

	private static Log logger = LogFactory.getLog(UserAuthDaoImpl.class);

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@Override
	public void insertUserAuth(UserAuthTbl userAuthTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().insert("UserAuth.insertUserAuth", userAuthTbl);
	}

	@Override
	public void updateUserAuth(UserAuthTbl userAuthTbl) throws DaoRollbackException {
		getSqlMapClientTemplate().update("UserAuth.updateUserAuth", userAuthTbl);
	}

}
