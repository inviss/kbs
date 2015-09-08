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
import kr.co.d2net.dto.OptTbl;
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

@Repository(value="optDao")
public class OptDaoImpl extends SqlMapClientDaoSupport implements OptDao {
	
	private static Log logger = LogFactory.getLog(OptDaoImpl.class);

	// 리스트 목록 조회(페이징 처리)를 할때 사용
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	private OptDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	// 페이징 처리를 제외한 CRUD에 모두 사용
	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<OptTbl> findOpt(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("Opt.findOpt", params);
	}
 
	@Override
	public OptTbl getOpt(Map<String, Object> params) throws DaoNonRollbackException {
		return (OptTbl)getSqlMapClientTemplate().queryForObject("Opt.getOpt", params);
	}

	@Override
	public void insertOpt(OptTbl opt) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().insert("Opt.insertOpt", opt);
	}
	
	@Override
	public void updateOpt(OptTbl opt) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().update("Opt.updateOpt", opt);
	}
	
	@Override
	public void updateOpt2(OptTbl opt) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().update("Opt.updateOpt2", opt);
	}
	
	@Override
	public void updateOpt3(OptTbl opt) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().update("Opt.updateOpt3", opt);
	}
	
	@Override
	public void deleteOpt(OptTbl opt) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().update("Opt.deleteOpt", opt);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<OptTbl> findoptList(Search search) throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<OptTbl> ps = null;
		
		
		
		StringBuffer query = new StringBuffer();
		query.append("")
				.append(" FROM OPT o                                                      ")
				//.append(" 	inner JOIN CONTENTS_INST_TBL cti ON trs.CTI_ID = cti.CTI_ID             ")
				//.append("   inner JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID                      ")
				.append(" WHERE o.USE_YN = 'Y'                                                    ");
		//if (search.getStartDt() != null) {
		//	query.append(" WHERE pf.MOD_DT BETWEEN :startDt AND :endDt ");
		//	params.put("startDt", search.getStartDt());
		//	params.put("endDt", Utility.getDate(search.getEndDt(), 1));
		//}
		//if (StringUtils.isNotBlank(search.getKeyword())) {
		//	query.append(" AND pf.REGRID like :regrid ");
		//	params.put("regrid", "%" + search.getKeyword() + "%");
		//}
		//if (StringUtils.isNotBlank(search.getPgmId())) {
		//	query.append(" AND pf.PGM_ID like :pgmId ");
		//	params.put("pgmId", search.getPgmId());
		//}

		String countSql = "select count(*) ";
		String dataSql = "select o.OPT_ID, o.OPT_INFO, o.OPT_DESC,o.DEFAULT_YN";
		String orderSql = "ORDER BY o.OPT_ID DESC";

		try {
			PaginationHelper<OptTbl> ph = new PaginationHelper<OptTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 7,
					
					new BeanPropertyRowMapper(OptTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"OptDaoImpl - findoptList Error", dae);
		}
		return ps;
	
	}
}
