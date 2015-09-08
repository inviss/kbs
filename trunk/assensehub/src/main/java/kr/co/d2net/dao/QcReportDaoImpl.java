package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.commons.util.PaginationHelper;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.ProOptTbl;
import kr.co.d2net.dto.QcReportTbl;
import kr.co.d2net.dto.Search;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value="qcreportDao")
public class QcReportDaoImpl extends SqlMapClientDaoSupport implements QcReportDao {
	
	private static Log logger = LogFactory.getLog(ProFlDaoImpl.class);

	// 리스트 목록 조회(페이징 처리)를 할때 사용
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	private QcReportDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	// 페이징 처리를 제외한 CRUD에 모두 사용
	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}

	@SuppressWarnings("unchecked")
	public List<QcReportTbl> findQcReport(Map<String, Object> params)
			throws DaoNonRollbackException {
		
		return getSqlMapClientTemplate().queryForList("QcReport.findQcReport", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<QcReportTbl> allfindQc(Map<String, Object> params)
			throws DaoNonRollbackException {
		
		return getSqlMapClientTemplate().queryForList("QcReport.allfindQc", params);
	}

	@Override
	public void insertQcReport(QcReportTbl qcReportTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().insert("QcReport.deleteQcReport", qcReportTbl);
		getSqlMapClientTemplate().insert("QcReport.insertQcReport", qcReportTbl);
	}

	@Override
	public Integer getQcCount(Long seq) throws DaoNonRollbackException {
		return (Integer)getSqlMapClientTemplate().queryForObject("QcReport.getQcCount", seq);
	}
	
}
