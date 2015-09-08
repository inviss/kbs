package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value="quartzManagerDao")
public class QuartzManagerDaoImpl extends SqlMapClientDaoSupport implements QuartzManagerDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findQuartzTables() throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("Quartz.findTables");
	}

	public void createQuartzTables(Map<String, String> params)
			throws DaoRollbackException {
		getSqlMapClientTemplate().queryForObject("Quartz.createTables", params);
	}

	public void quartzTableInitialize(String lockName) throws DaoRollbackException {
		getSqlMapClientTemplate().delete("Quartz.tableInitializ", lockName);
	}

	public void insertLockName(String lockName) throws DaoRollbackException {
		getSqlMapClientTemplate().insert("Quartz.insertLocks", lockName);
	}

	@SuppressWarnings("unchecked")
	public List<String> findTableIndexs(String tableName) throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("Quartz.findTableIndex", tableName);
	}

	@Override
	public void createTableIndex(Map<String, String> params)
			throws DaoRollbackException {
		getSqlMapClientTemplate().insert("Quartz.createIndexs", params);
	}

}
