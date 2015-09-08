package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.commons.util.PaginationHelper;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.DisuseInfoTbl;
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

import org.springframework.context.MessageSource;

@Repository(value = "disuseinfoDao")
public class DisuseInfoDaoImpl extends SqlMapClientDaoSupport implements
		DisuseInfoDao {

	private static Log logger = LogFactory.getLog(DisuseInfoDaoImpl.class);

	// 리스트 목록 조회(페이징 처리)를 할때 사용
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private DisuseInfoDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	// 페이징 처리를 제외한 CRUD에 모두 사용
	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<DisuseInfoTbl> findDisuseInfo(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList(
				"DisuseInfo.findDisuseInfo", params);
	}

	@Override
	public DisuseInfoTbl getDisuseInfo(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (DisuseInfoTbl) getSqlMapClientTemplate().queryForObject(
				"DisuseInfo.getDisuseInfo", params);
	}

	@Override
	public void insertDisuseInfo(DisuseInfoTbl disuseinfoTbl)
			throws DaoRollbackException {
		// logger.debug("--->"+proflTbl.getCtNm());

		getSqlMapClientTemplate().insert("DisuseInfo.insertDisuseInfo",
				disuseinfoTbl);
	}

	@Override
	public void updateDisuseInfo(DisuseInfoTbl disuseinfoTbl)
			throws DaoRollbackException {

		getSqlMapClientTemplate().update("DisuseInfo.updateDisuseInfo",
				disuseinfoTbl);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<DisuseInfoTbl> finddisuseList(Search search)
			throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<DisuseInfoTbl> ps = null;

		String date = messageSource.getMessage("media.safe.days", null,Locale.KOREA);
		
		StringBuffer query = new StringBuffer();
		query.append("")
				.append(" FROM CONTENTS_TBL ct                                                      ")
				// .append(" 	inner JOIN CONTENTS_TBL ct ON ct.CT_ID = cti.CT_ID             ")
				// .append("   inner JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID                      ")
				.append(" WHERE ct.USE_YN = 'Y' AND (ct.TRIMM_STE='Y' OR ct.TRIMM_STE='P') AND TO_CHAR(REG_DT+DELAY_DAY+:date,'YYYY-MM-DD')=TO_CHAR(SYSDATE+1,'YYYY-MM-DD')  ");
		
				params.put("date", date);
		if (search.getStartDt() != null) {
			query.append(" AND REG_DT BETWEEN :startDt AND :endDt ");
			params.put("startDt", Utility.getDate(search.getStartDt(), 0));
			params.put("endDt", Utility.getDate(search.getEndDt(), 0));
		}
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append(" AND ct.PGM_NM like :pgmNm ");
			params.put("pgmNm", "%" + search.getKeyword() + "%");
		}
		if (StringUtils.isNotBlank(search.getPgmId())) {
			query.append(" AND ct.PGM_ID like :pgmId ");
			params.put("pgmId", search.getPgmId());
		}

		String countSql = "select count(*) ";
		String dataSql = "select ct.REGRID,ct.CT_ID, ct.REG_DT+:date as REG_DT, ct.CT_NM, ct.PGM_NM, ct.CT_CLA";
		params.put("date", date);
		String orderSql = "ORDER BY ct.REG_DT ASC, ct.CT_ID DESC ";

		try {
			PaginationHelper<DisuseInfoTbl> ph = new PaginationHelper<DisuseInfoTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 22,

					new BeanPropertyRowMapper(ContentsTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"DisuseDaoImpl - finddisuseList Error", dae);
		}
		return ps;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<DisuseInfoTbl> finddisuseList2(Search search)
			throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<DisuseInfoTbl> ps = null;

		String date = messageSource.getMessage("media.safe.days", null,Locale.KOREA);
		
		StringBuffer query = new StringBuffer();
		query.append("")
				.append(" FROM CONTENTS_TBL ct                                                      ")
				// .append(" 	inner JOIN CONTENTS_TBL ct ON ct.CT_ID = cti.CT_ID             ")
				// .append("   inner JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID                      ")
				.append(" WHERE ct.USE_YN = 'Y' AND (ct.TRIMM_STE='Y' OR ct.TRIMM_STE='P')                    ");
		if (search.getStartDt() != null) {
			//query.append(" AND TO_CHAR(REG_DT+:date,'YYYY-MM-DD') BETWEEN :startDt AND :endDt ");
			query.append(" AND TO_CHAR(REG_DT+DELAY_DAY+:date,'YYYY-MM-DD') BETWEEN :startDt AND :endDt ");
			params.put("date", date);
			params.put("startDt", Utility.getDate(search.getStartDt(), 0));
			params.put("endDt", Utility.getDate(search.getEndDt(), 0));
		}
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append(" AND ct.PGM_NM like :pgmNm ");
			params.put("pgmNm", "%" + search.getKeyword() + "%");
		}
		if (StringUtils.isNotBlank(search.getPgmId())) {
			query.append(" AND ct.PGM_ID like :pgmId ");
			params.put("pgmId", search.getPgmId());
		}

		String countSql = "select count(*) ";
		String dataSql = "select ct.REGRID,ct.CT_ID, ct.REG_DT+DELAY_DAY+:date as REG_DT, ct.CT_NM, ct.PGM_NM, ct.CT_CLA";
		params.put("date", date);
		String orderSql = "ORDER BY ct.REG_DT ASC, ct.CT_ID DESC ";

		try {
			PaginationHelper<DisuseInfoTbl> ph = new PaginationHelper<DisuseInfoTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 22,

					new BeanPropertyRowMapper(ContentsTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"DisuseDaoImpl - finddisuseList2 Error", dae);
		}
		return ps;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<DisuseInfoTbl> finddisuseStatusList(Search search)
			throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<DisuseInfoTbl> ps = null;

		StringBuffer query = new StringBuffer();
		query.append("")
				.append(" FROM CONTENTS_TBL ct                                                      ")
				.append(" 	inner JOIN DISUSE_INFO_TBL di ON di.DATA_ID = ct.CT_ID             ")
				//.append(" 	inner JOIN contents_inst_TBL cti ON cti.CT_ID = ct.CT_ID             ")
				//.append(" 	LEFT OUTER JOIN PRO_FL_TBL PFT ON PFT.PRO_FLID = CTI.PRO_FLID           ")
				// .append("   inner JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID                      ")
				.append(" WHERE ct.USE_YN = 'N'              ");
		if (search.getStartDt() != null) {
			query.append(" AND TO_CHAR(di.DISUSE_DD,'YYYY-MM-DD') BETWEEN :startDt AND :endDt ");
			params.put("startDt", Utility.getDate(search.getStartDt(), 0));
			params.put("endDt", Utility.getDate(search.getEndDt(), 0));
		}
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append(" AND ct.PGM_NM like :pgmNm ");
			params.put("pgmNm", "%" + search.getKeyword() + "%");
		}
		if (StringUtils.isNotBlank(search.getPgmId())) {
			query.append(" AND ct.PGM_ID like :pgmId ");
			params.put("pgmId", search.getPgmId());
		}
 
		String countSql = "select count(*) ";
		//String dataSql = "select di.DATA_ID,di.DISUSE_NO, ct.REGRID,ct.CT_ID, di.DISUSE_DD, ct.CT_NM, ct.PGM_NM, ct.CT_CLA,cti.CTI_ID,PFT.PRO_FLNM,cti.ORG_FILE_NM";
		String dataSql = "select di.DATA_ID,di.DISUSE_NO, ct.REGRID,ct.CT_ID, di.DISUSE_DD, ct.CT_NM, ct.PGM_NM, ct.CT_CLA";
		
		String orderSql = "ORDER BY di.DISUSE_DD DESC, di.DISUSE_NO DESC ";

		try {
			PaginationHelper<DisuseInfoTbl> ph = new PaginationHelper<DisuseInfoTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 22,

					new BeanPropertyRowMapper(ContentsTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"DisuseDaoImpl - finddisuseStatusList Error", dae);
		}
		return ps;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<DisuseInfoTbl> finddisuseRenewalList(Search search)
			throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<DisuseInfoTbl> ps = null;
		
		String date = messageSource.getMessage("media.safe.days", null,Locale.KOREA);
		
		StringBuffer query = new StringBuffer();
		query.append("")
				.append(" FROM CONTENTS_TBL ct                                                      ")
				// .append(" 	inner JOIN CONTENTS_TBL ct ON ct.CT_ID = cti.CT_ID             ")
				// .append("   inner JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID                      ")
				.append(" WHERE ct.USE_YN = 'Y' AND ct.DELAY_DAY != 0                   ");
		if (search.getStartDt() != null) {
			query.append(" AND TO_CHAR(ct.REG_DT+ct.DELAY_DAY+:date,'YYYY-MM-DD') BETWEEN :startDt AND :endDt ");
			// System.out.println("==================");
			params.put("date", date);
			params.put("startDt", Utility.getDate(search.getStartDt(), 0));
			params.put("endDt", Utility.getDate(search.getEndDt(), 0));
		}
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append(" AND ct.PGM_NM like :pgmNm ");
			params.put("pgmNm", "%" + search.getKeyword() + "%");
		}
		if (StringUtils.isNotBlank(search.getPgmId())) {
			query.append(" AND ct.PGM_ID like :pgmId ");
			params.put("pgmId", search.getPgmId());
		}

		String countSql = "select count(*) ";
		String dataSql = "select ct.REGRID,ct.CT_ID, ct.CT_NM, ct.PGM_NM, ct.CT_CLA, TO_CHAR(ct.REG_DT+ct.DELAY_DAY+:date,'YYYY-MM-DD') AS DISUSE_DD";
		params.put("date", date);
		String orderSql = "ORDER BY ct.DELAY_DT DESC, ct.CT_ID DESC ";

		try {
			PaginationHelper<DisuseInfoTbl> ph = new PaginationHelper<DisuseInfoTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 22,

					new BeanPropertyRowMapper(DisuseInfoTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"DisuseDaoImpl - finddisuseRenewalList Error", dae);
		}
		return ps;

	}
}
