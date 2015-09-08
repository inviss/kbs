package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.RoleAuthTbl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value = "roleAuthDao")
public class RoleAuthDaoImpl extends SqlMapClientDaoSupport implements
		RoleAuthDao {

	private static Log logger = LogFactory.getLog(RoleAuthDaoImpl.class);

	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@Override
	public void insertRoleAuth(RoleAuthTbl roleAuthTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate()
				.insert("RoleAuth.insertRoleAuth", roleAuthTbl);
	}

	@SuppressWarnings("unchecked")
	public List<RoleAuthTbl> findRoleAuth(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("RoleAuth.findRoleAuth",
				params);
	}

	@Override
	public RoleAuthTbl getRoleAuth(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (RoleAuthTbl) getSqlMapClientTemplate().queryForObject(
				"RoleAuth.getRoleAuth", params);

	}

	@Override
	public void deleteRoleAuth(RoleAuthTbl roleAuthTbl)
			throws DaoRollbackException {
		// logger.debug("--->"+proflTbl.getCtNm());

		getSqlMapClientTemplate()
				.update("RoleAuth.deleteRoleAuth", roleAuthTbl);
	}
	
	@Override
	public void deleteRoleAuth2(RoleAuthTbl roleAuthTbl) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().update("RoleAuth.deleteRoleAuth2", roleAuthTbl);
	}

	@Override
	public String getUserAuth(Map<String, Object> params)
			throws DaoRollbackException {
		return (String) getSqlMapClientTemplate().queryForObject(
				"RoleAuth.getUserAuth", params);
	}
}
