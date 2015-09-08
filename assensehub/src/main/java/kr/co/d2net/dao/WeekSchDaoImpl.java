package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.dto.WeekSchTbl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value = "weekSchDao")
public class WeekSchDaoImpl extends SqlMapClientDaoSupport implements
		WeekSchDao {

	private static Log logger = LogFactory.getLog(WeekSchDaoImpl.class);

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private WeekSchDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@Override
	public WeekSchTbl getWeekSch(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (WeekSchTbl) getSqlMapClientTemplate().queryForObject(
				"WeekSch.getWeekSch", params);
	}

	@Override
	public void insertWeekSch(WeekSchTbl weekSchTbl)
			throws DaoNonRollbackException {
		getSqlMapClientTemplate().insert("WeekSch.insertWeekSch", weekSchTbl);
	}

	@Override
	public void updateWeekSch(WeekSchTbl weekSchTbl)
			throws DaoNonRollbackException {
		// TODO Auto-generated method stub

	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<WeekSchTbl> findWeekSchs(Map<String, Object> params)
			throws DaoNonRollbackException {
		
		return getSqlMapClientTemplate().queryForList("WeekSch.findWeekSch", params);
	}

	@Override
	public void deleteWeekSch(WeekSchTbl weekSchTbl)
			throws DaoNonRollbackException {
		getSqlMapClientTemplate().delete("WeekSch.deleteWeekSch", weekSchTbl);
	}
}
