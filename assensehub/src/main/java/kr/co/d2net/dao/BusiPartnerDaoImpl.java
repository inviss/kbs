package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.commons.util.PaginationHelper;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.BusiPartnerTbl;
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

@Repository(value="busipartnerDao")
public class BusiPartnerDaoImpl extends SqlMapClientDaoSupport implements BusiPartnerDao {
	
	private static Log logger = LogFactory.getLog(BusiPartnerDaoImpl.class);

	// 리스트 목록 조회(페이징 처리)를 할때 사용
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	private BusiPartnerDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	// 페이징 처리를 제외한 CRUD에 모두 사용
	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<BusiPartnerTbl> findBusiPartner(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("BusiPartner.findBusiPartner", params);
	}
 
	@Override
	public BusiPartnerTbl getBusiPartner(Map<String, Object> params) throws DaoNonRollbackException {
		return (BusiPartnerTbl)getSqlMapClientTemplate().queryForObject("BusiPartner.getBusiPartner", params);
	}
	
	@Override
	public BusiPartnerTbl getBusiPartnerId(Map<String, Object> params) throws DaoNonRollbackException {
		return (BusiPartnerTbl)getSqlMapClientTemplate().queryForObject("BusiPartner.getBusiPartnerId", params);
	}

	@Override
	public void insertBusiPartner(BusiPartnerTbl busipartnerTbl) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().insert("BusiPartner.insertBusiPartner", busipartnerTbl);
	}
	
	@Override
	public void updateBusiPartner(BusiPartnerTbl busipartnerTbl) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().update("BusiPartner.updateBusiPartner", busipartnerTbl);
	}
	
	@Override
	public void deleteBusiPartner(BusiPartnerTbl busipartnerTbl) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().update("BusiPartner.deleteBusiPartner", busipartnerTbl);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<BusiPartnerTbl> findbusipartnerList(Search search) throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<BusiPartnerTbl> ps = null;

		StringBuffer query = new StringBuffer();
		query.append("")
				.append(" FROM BUSI_PARTNER_TBL bp ");
				//.append(" 	inner JOIN BUSI_PARTNER_TBL bp ON bp.BUSI_PARTNERID = pb.BUSI_PARTNERID             ")
				//.append("   inner JOIN PRO_FL_TBL pf ON pb.PRO_FLID = pf.PRO_FLID)                      ")
				//.append(" WHERE SERVYN = 'Y' ");
		//if (search.getStartDt() != null) {
		//	query.append(" AND REG_DT BETWEEN :startDt AND :endDt ");
		//	params.put("startDt", search.getStartDt());
		//	params.put("endDt", Utility.getDate(search.getEndDt(), 1));
		//}
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append("where bp.COMPANY like :company ");
			params.put("company", "%" + search.getKeyword() + "%");
		}
		//if (StringUtils.isNotBlank(search.getPgmId())) {
		//	query.append(" AND pf.PGM_ID like :pgmId ");
		//	params.put("pgmId", search.getPgmId());
		//}

		String countSql = "select count(*) ";
		String dataSql = "SELECT bp.BUSI_PARTNERID, bp.COMPANY, bp.IP, bp.SERVYN";
		String orderSql = "ORDER BY bp.REG_DT DESC, bp.BUSI_PARTNERID DESC ";

		try {
			PaginationHelper<BusiPartnerTbl> ph = new PaginationHelper<BusiPartnerTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 20,
					
					new BeanPropertyRowMapper(BusiPartnerTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"BusiPartnerImpl - findbusipartnerList Error", dae);
		}
		return ps;
	
	}
}
