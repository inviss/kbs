package kr.co.d2net.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.dto.AuthTbl;

@Repository(value="authDao")
public class AuthDaoImpl extends SqlMapClientDaoSupport implements AuthDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@Override
	public List<AuthTbl> findAuth() throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("Auth.findAuth");
	}

}
