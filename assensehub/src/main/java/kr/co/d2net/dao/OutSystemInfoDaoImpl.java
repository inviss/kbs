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
import kr.co.d2net.dto.EquipmentTbl;
import kr.co.d2net.dto.OutSystemInfoTbl;
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

@Repository(value="outsysteminfoDao")
public class OutSystemInfoDaoImpl extends SqlMapClientDaoSupport implements OutSystemInfoDao {
	
	private static Log logger = LogFactory.getLog(ProFlDaoImpl.class);

	// 리스트 목록 조회(페이징 처리)를 할때 사용
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	private OutSystemInfoDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	// 페이징 처리를 제외한 CRUD에 모두 사용
	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<OutSystemInfoTbl> findOutSystemInfo(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("OutSystemInfo.findOutSystemInfo", params);
	}
 
	@Override
	public OutSystemInfoTbl getOutSystemInfo(Map<String, Object> params) throws DaoNonRollbackException {
		return (OutSystemInfoTbl)getSqlMapClientTemplate().queryForObject("OutSystemInfo.getOutSystemInfo", params);
	}

	@Override
	public void insertOutSystemInfo(OutSystemInfoTbl outsystemTbl) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().insert("OutSystemInfo.insertOutSystemInfo", outsystemTbl);
	}
	
	@Override
	public void updateOutSystemInfo(OutSystemInfoTbl outsystemTbl) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().update("OutSystemInfo.updateOutSystemInfo", outsystemTbl);
	}
	
	@Override
	public void deleteOutSystemInfo(OutSystemInfoTbl outsystemTbl) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().update("OutSystemInfo.deleteOutSystemInfo", outsystemTbl);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<OutSystemInfoTbl> findoutsystemList(Search search) throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<OutSystemInfoTbl> ps = null;
		 
		
		
		StringBuffer query = new StringBuffer();
		query.append("")
				.append(" FROM EQUIPMENT_TBL eq                                                      ")
				//.append(" 	inner JOIN CONTENTS_INST_TBL cti ON trs.CTI_ID = cti.CTI_ID             ")
				//.append("   inner JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID                      ")
				.append(" WHERE eq.USE_YN = 'Y'                                                    ");
		//if (search.getStartDt() != null) {
		//	query.append(" AND pf.MOD_DT BETWEEN :startDt AND :endDt ");
		//	params.put("startDt", Utility.getDate(search.getStartDt(),0));
		//	params.put("endDt", Utility.getDate(search.getEndDt(), 0));
		//}
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append(" AND eq.DEVICE_NM like :deviceNm ");
			params.put("deviceNm", "%" + search.getKeyword() + "%");
		}
		//if (StringUtils.isNotBlank(search.getPgmId())) {
		//	query.append(" AND pf.PGM_ID like :pgmId ");
		//	params.put("pgmId", search.getPgmId());  
		//}

		String countSql = "select count(*) ";
		String dataSql = "select eq.DEVICEID, eq.EQ_PS_CD, eq.DEVICE_NM, eq.DEVICE_IP";
		String orderSql = "ORDER BY eq.REG_DT DESC, eq.DEVICEID DESC ";

		try {
			PaginationHelper<OutSystemInfoTbl> ph = new PaginationHelper<OutSystemInfoTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 12,
					
					new BeanPropertyRowMapper(EquipmentTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"OutSystemDaoImpl - findoutsystemList Error", dae);
		}
		return ps;
	
	}
}
