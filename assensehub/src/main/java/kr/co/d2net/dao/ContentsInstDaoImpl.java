package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.ContentsInstTbl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value = "contentsinstDao")
public class ContentsInstDaoImpl extends SqlMapClientDaoSupport implements
		ContentsInstDao {

	private static Log logger = LogFactory.getLog(ContentsInstDaoImpl.class);

	// 리스트 목록 조회(페이징 처리)를 할때 사용
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private ContentsInstDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	// 페이징 처리를 제외한 CRUD에 모두 사용
	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<ContentsInstTbl> findContentsInst(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList(
				"ContentsInst.findContentsInst", params);
	}

	@Override
	public ContentsInstTbl getContentsInst(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (ContentsInstTbl) getSqlMapClientTemplate().queryForObject(
				"ContentsInst.getContentsInst", params);
	}

	@Override
	public Long insertContentsInst(ContentsInstTbl contentsinstTbl)
			throws DaoRollbackException {
		// logger.debug("--->"+contentsTbl.getCtNm());

		return (Long)getSqlMapClientTemplate().insert("ContentsInst.insertContentsInst",
				contentsinstTbl);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContentsInstTbl> findContentsInstSummary(
			Map<String, Object> params) throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList(
				"ContentsInst.findContents", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ContentsInstTbl> findContentsSummary(
			Map<String, Object> params) throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList(
				"ContentsInst.findOrgContents", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ContentsInstTbl> findDelContents(
			Map<String, Object> params) throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList(
				"ContentsInst.findDelContents", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<ContentsInstTbl> findContentsRms(
			Map<String, Object> params) throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList(
				"ContentsInst.findContentsRms", params);
	}

	@Override
	public void deleteContentsInst(ContentsInstTbl contentsinstTbl)
			throws DaoRollbackException {
		//
		getSqlMapClientTemplate().update("ContentsInst.deleteContentsInst",
				contentsinstTbl);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContentsInstTbl> findContentsInstSummaryByMediaTool(
			Map<String, Object> params) throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("ContentsInst.findContentsByMediaTool", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContentsInstTbl> findDeleteContentsInst(
			Map<String, Object> params) throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList(
				"ContentsInst.findDeleteContentsInst", params);
	}

	@Override
	public void updateContentsInst(ContentsInstTbl contentsinstTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().update("ContentsInst.updateContentsInst", contentsinstTbl);
	}

	@Override
	public ContentsInstTbl getContentsInstForwardMeta(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (ContentsInstTbl) getSqlMapClientTemplate().queryForObject(
				"ContentsInst.getContentsInstForwardMeta", params);
	}

	@Override
	public List<ContentsInstTbl> findDefaultDeleteContentsInst(
			Map<String, Object> params) throws DaoNonRollbackException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(
				"ContentsInst.findDefaultDeleteContentsInst", params);
	}

}
