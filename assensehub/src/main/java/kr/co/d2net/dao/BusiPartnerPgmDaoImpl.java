package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.BusiPartnerPgm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value = "busipartnerpgmDao")
public class BusiPartnerPgmDaoImpl extends SqlMapClientDaoSupport implements
		BusiPartnerPgmDao {

	private static Log logger = LogFactory.getLog(BusiPartnerPgmDaoImpl.class);

	// 리스트 목록 조회(페이징 처리)를 할때 사용
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private BusiPartnerPgmDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	// 페이징 처리를 제외한 CRUD에 모두 사용
	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<BusiPartnerPgm> findBusiPartnerPgm(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList(
				"BusiPartnerPgm.findBusiPartnerPgm", params);
	}

	@Override
	public BusiPartnerPgm getBusiPartnerPgm(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (BusiPartnerPgm) getSqlMapClientTemplate().queryForObject(
				"BusiPartnerPgm.getBusiPartnerPgm", params);
	}
	
	@Override
	public String getPgmAudioMode(String pgmCode, String ctTyp)
			throws DaoNonRollbackException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pgmCode", pgmCode);
		params.put("ctTyp", ctTyp);
		
		return (String) getSqlMapClientTemplate().queryForObject("BusiPartnerPgm.getAudioMode", params);
	}

	@Override
	public void insertBusiPartnerPgm(BusiPartnerPgm busipartnerPgm)
			throws DaoRollbackException {
		// logger.debug("--->"+proflTbl.getCtNm());

		getSqlMapClientTemplate().insert("BusiPartnerPgm.insertBusiPartnerPgm", busipartnerPgm);
	}

	@Override
	public void deleteBusiPartnerPgm(BusiPartnerPgm busipartnerPgm)
			throws DaoRollbackException {

		getSqlMapClientTemplate().delete("BusiPartnerPgm.deleteBusiPartnerPgm",
				busipartnerPgm);
	}

}
