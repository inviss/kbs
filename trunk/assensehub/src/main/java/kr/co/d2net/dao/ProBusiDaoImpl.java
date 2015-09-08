package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.ProBusiTbl;
import kr.co.d2net.dto.ProFlTbl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value = "probusiDao")
public class ProBusiDaoImpl extends SqlMapClientDaoSupport implements
		ProBusiDao {

	private static Log logger = LogFactory.getLog(ProBusiDaoImpl.class);

	// 리스트 목록 조회(페이징 처리)를 할때 사용
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private ProBusiDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	// 페이징 처리를 제외한 CRUD에 모두 사용
	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<ProBusiTbl> findProBusis(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("ProBusi.findProBusis",
				params);
	}

	@SuppressWarnings("unchecked")
	public List<ProBusiTbl> findProBusi(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("ProBusi.findProBusi",
				params);
	}

	@SuppressWarnings("unchecked")
	public List<ProBusiTbl> findProBusi2(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("ProBusi.findProBusi2",
				params);
	}

	@Override
	public ProBusiTbl getProBusi(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (ProBusiTbl) getSqlMapClientTemplate().queryForObject(
				"ProBusi.getProBusi", params);
	}

	@Override
	public void deleteProBusi(ProBusiTbl probusiTbl)
			throws DaoRollbackException {
		// logger.debug("--->"+proflTbl.getCtNm());

		getSqlMapClientTemplate().update("ProBusi.deleteProBusi", probusiTbl);
	}

	@Override
	public void insertProBusi(ProBusiTbl probusiTbl)
			throws DaoRollbackException {
		// logger.debug("--->"+proflTbl.getCtNm());

		getSqlMapClientTemplate().insert("ProBusi.insertProBusi", probusiTbl);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProFlTbl> findPgmProBusi(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("ProBusi.findPgmProBusi",
				params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProFlTbl> findNewPgmProBusi(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("ProBusi.findNewPgmProBusi",
				params);
	}
}
