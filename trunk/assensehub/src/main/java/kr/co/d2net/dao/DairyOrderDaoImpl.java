package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.dto.DairyOrderTbl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value = "dairyOrderDao")
public class DairyOrderDaoImpl extends SqlMapClientDaoSupport implements
		DairyOrderDao {

	private static Log logger = LogFactory.getLog(DairyOrderDaoImpl.class);

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private DairyOrderDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@Override
	public void insertDairyOrder(DairyOrderTbl dairyOrderTbl)
			throws DaoNonRollbackException {
		getSqlMapClientTemplate().insert("DairyOrder.insertDairyOrder",
				dairyOrderTbl);
	}

	@Override
	public void updateDairyOrder(DairyOrderTbl dairyOrderTbl)
			throws DaoNonRollbackException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteDairyOrder(DairyOrderTbl dairyOrderTbl)
			throws DaoNonRollbackException {
		getSqlMapClientTemplate().delete("DairyOrder.deleteDairyOrder",
				dairyOrderTbl);

	}

	@Override
	public DairyOrderTbl getDairyOrderTbl(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (DairyOrderTbl) getSqlMapClientTemplate().queryForObject(
				"DairyOrder.getDairyOrder", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DairyOrderTbl> findDairyOrderTbl(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList(
				"DairyOrder.findDairyOrder", params);
	}

}
