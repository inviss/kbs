package kr.co.d2net.dao;

import java.util.Map;

import javax.sql.DataSource;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.dto.LiveTbl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value = "liveDao")
public class LiveDaoImpl extends SqlMapClientDaoSupport implements LiveDao {

	private static Log logger = LogFactory.getLog(WeekSchDaoImpl.class);

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private LiveDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@Override
	public LiveTbl getLive(Map<String, Object> params) throws DaoNonRollbackException {

		return (LiveTbl) getSqlMapClientTemplate().queryForObject(
				"Live.getLive", params);
	}

	@Override
	public void insertLive(LiveTbl liveTbl) throws DaoNonRollbackException {

		getSqlMapClientTemplate().insert("Live.insertLive", liveTbl);
	}

	@Override
	public int updateLive(LiveTbl liveTbl) throws DaoNonRollbackException {
		return getSqlMapClientTemplate().update("Live.updateLive", liveTbl);
	}

	@Override
	public int deleteLive(LiveTbl liveTbl) throws DaoNonRollbackException {
		return getSqlMapClientTemplate().delete("Live.deleteLive", liveTbl);
	}

}
