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
import kr.co.d2net.dto.ProFlTbl;
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

@Repository(value="proflDao")
public class ProFlDaoImpl extends SqlMapClientDaoSupport implements ProFlDao {
	
	private static Log logger = LogFactory.getLog(ProFlDaoImpl.class);

	// 리스트 목록 조회(페이징 처리)를 할때 사용
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	private ProFlDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	// 페이징 처리를 제외한 CRUD에 모두 사용
	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<ProFlTbl> findProFl(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("ProFl.findProFl", params);
	}
 
	@Override
	public ProFlTbl getProFl(Map<String, Object> params) throws DaoNonRollbackException {
		return (ProFlTbl)getSqlMapClientTemplate().queryForObject("ProFl.getProFl", params);
	}
	
	@Override
	public ProFlTbl getProFlId(Map<String, Object> params) throws DaoNonRollbackException {
		return (ProFlTbl)getSqlMapClientTemplate().queryForObject("ProFl.getProFlId", params);
	}

	@Override
	public void insertProFl(ProFlTbl proflTbl) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().insert("ProFl.insertProFl", proflTbl);
	}
	
	@Override
	public void updateProFl(ProFlTbl proflTbl) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().update("ProFl.updateProFl", proflTbl);
	}
	
	@Override
	public void deleteProFl(ProFlTbl proflTbl) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().update("ProFl.deleteProFl", proflTbl);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<ProFlTbl> findproflList(Search search) throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<ProFlTbl> ps = null;
		StringBuffer query = new StringBuffer();
		query.append("")
				.append(" FROM PRO_FL_TBL pf                                                      ")
				//.append(" 	inner JOIN CONTENTS_INST_TBL cti ON trs.CTI_ID = cti.CTI_ID             ")
				//.append("   inner JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID                      ")
				.append(" WHERE pf.USE_YN = 'Y'                                                    ");
		if (search.getStartDt() != null) {
			System.out.println(">>>"+Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
			query.append(" AND TO_CHAR(REG_DT+0,'YYYY-MM-DD') BETWEEN :startDt AND :endDt ");
			params.put("startDt", Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
			params.put("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
			
		}
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append(" AND pf.PRO_FLNM like :proFlnm ");
			params.put("proFlnm", "%" + search.getKeyword() + "%");
		}

		String countSql = "select count(*) ";
		String dataSql = "select pf.PRO_FLNM, pf.PRO_FLID, pf.REGRID, pf.VDO_CODEC, pf.REG_DT, pf.VDO_BIT_RATE, pf.VDO_F_S, pf.AUD_CODEC, pf.AUD_BIT_RATE, pf.AUD_CHAN";
		String orderSql = "ORDER BY pf.REG_DT DESC, pf.PRO_FLID DESC ";

		try {
			PaginationHelper<ProFlTbl> ph = new PaginationHelper<ProFlTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 20,
					
					new BeanPropertyRowMapper(ProFlTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"ProFlDaoImpl - findproflList Error", dae);
		}
		return ps;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findProflExt(String mediaCla) throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("ProFl.findProflExt", mediaCla);
	}
}
