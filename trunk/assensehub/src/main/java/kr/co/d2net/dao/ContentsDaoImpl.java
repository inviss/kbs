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
import kr.co.d2net.dto.ContentsTbl;
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

@Repository(value = "contentsDao")
public class ContentsDaoImpl extends SqlMapClientDaoSupport implements
ContentsDao {

	private static Log logger = LogFactory.getLog(ContentsDaoImpl.class);

	// 리스트 목록 조회(페이징 처리)를 할때 사용
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private ContentsDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	// 페이징 처리를 제외한 CRUD에 모두 사용
	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<ContentsTbl> findContents(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("Contents.findContents",
				params);
	}

	@SuppressWarnings("unchecked")
	public List<ContentsTbl> getContentsPrgrs(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("Contents.getContentsPrgrs",
				params);
	}

	@Override
	public ContentsTbl getContents(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (ContentsTbl) getSqlMapClientTemplate().queryForObject(
				"Contents.getContents", params);
	}

	@Override
	public Long insertContents(ContentsTbl contentsTbl)
			throws DaoRollbackException {
		return (Long) getSqlMapClientTemplate().insert("Contents.insertContents", contentsTbl);
	}

	@Override
	public Long updateContentsRms(ContentsTbl contentsTbl)
			throws DaoRollbackException {
		return (Long) getSqlMapClientTemplate().insert("Contents.updateContentsRms", contentsTbl);
	}




	@Override
	public void deleteContents(ContentsTbl contentsTbl)
			throws DaoRollbackException {
		// logger.debug("--->"+proflTbl.getCtNm());

		getSqlMapClientTemplate()
		.update("Contents.deleteContents", contentsTbl);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<ContentsTbl> findcontentsList(Search search)
			throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<ContentsTbl> ps = null;

		StringBuffer query = new StringBuffer();
		query.append("")
		.append(" FROM CONTENTS_TBL ct                                                      ")
		// .append(" 	inner JOIN CONTENTS_INST_TBL cti ON trs.CTI_ID = cti.CTI_ID             ")
		// .append("   inner JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID                      ")
		.append(" WHERE ct.USE_YN = 'Y' AND (ct.TRIMM_STE='Y' OR ct.TRIMM_STE='P')  AND pgm_id IS NOT NULL  AND brd_dd IS NOT NULL            ");
		//.append(" WHERE ct.USE_YN = 'Y'            ");
		if (search.getStartDt() != null) {
			query.append(" AND TO_CHAR(ct.BRD_DD,'YYYY-MM-DD') BETWEEN :startDt AND :endDt ");
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
		if (StringUtils.isNotBlank(search.getChannelCode())
				&& !search.getChannelCode().equals("0")) {
			query.append(" AND ct.CHANNEL_CODE like :channelCode ");
			params.put("channelCode", search.getChannelCode());
		}
		String countSql = "select count(*) ";
		String dataSql = "select ct.CT_ID, ct.CT_NM, ct.PGM_ID, ct.CT_TYP, ct.CT_CLA, ct.CHANNEL_CODE, ct.BRD_DD, ct.PGM_NM, ct.REG_DT, ct.REGRID, ct.LOCAL_CODE";
		String orderSql = "ORDER BY ct.CT_ID DESC ";

		try {
			PaginationHelper<ContentsTbl> ph = new PaginationHelper<ContentsTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 20,
					new BeanPropertyRowMapper(ContentsTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"ContentsDaoImpl - findcontentsList Error", dae);
		}
		return ps;

	}

	public PaginationSupport<ContentsTbl> findstandbycontentsList(Search search)
			throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<ContentsTbl> ps = null;

		StringBuffer query = new StringBuffer();
		query.append("")
		.append(" FROM CONTENTS_TBL ct                                                      ")
		// .append(" 	inner JOIN CONTENTS_INST_TBL cti ON trs.CTI_ID = cti.CTI_ID             ")
		// .append("   inner JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID                      ")
		.append(" WHERE ct.USE_YN = 'Y' AND  CT.TRIMM_STE='N'                  ");
		if (search.getStartDt() != null) {
			query.append(" AND TO_CHAR(ct.BRD_DD,'YYYY-MM-DD') BETWEEN :startDt AND :endDt ");
			params.put("startDt", Utility.getDate(search.getStartDt(), 0));
			params.put("endDt", Utility.getDate(search.getEndDt(), 0));
		}
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append(" AND ct.PGM_NM like :pgmNm ");
			params.put("pgmNm", "%" + search.getKeyword() + "%");
		}

		if (StringUtils.isNotBlank(search.getChannelCode())
				&& !search.getChannelCode().equals("0")) {
			query.append(" AND ct.CHANNEL_CODE like :channelCode ");
			params.put("channelCode", search.getChannelCode());
		}
		String countSql = "select count(*) ";
		String dataSql = "select ct.CT_ID, ct.CT_NM, ct.PGM_ID, ct.CT_TYP, ct.CT_CLA, ct.CHANNEL_CODE, ct.BRD_DD, ct.REG_DT,ct.REGRID,ct.PRGRS,ct.JOB_ID,"+
				"(CASE WHEN (SELECT COUNT(*) FROM CORNER_TBL CN WHERE ct.CT_ID = cn.CT_ID) > 0 THEN '[코]'||ct.CT_NM ELSE ct.PGM_NM end) PGM_NM";
		String orderSql = "ORDER BY ct.CT_ID DESC ";

		try {
			PaginationHelper<ContentsTbl> ph = new PaginationHelper<ContentsTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 20,
					new BeanPropertyRowMapper(ContentsTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"ContentsDaoImpl - findcontentsList Error", dae);
		}
		return ps;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<ContentsTbl> findrmscontentsList(Search search)
			throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<ContentsTbl> ps = null;


		StringBuffer query = new StringBuffer();
		query.append("")
		.append(" FROM CONTENTS_TBL ct                                                      ")
		.append(" 	inner JOIN CONTENTS_INST_TBL cti ON ct.CT_ID = cti.CT_ID             ")
		.append(" WHERE ct.USE_YN = 'Y' AND ct.REGRID='RMS' AND (cti.CTI_FMT LIKE '1%' or cti.CTI_FMT LIKE '3%')           ");
		if (search.getStartDt() != null) {
			query.append(" AND TO_CHAR(ct.BRD_DD,'YYYY-MM-DD') BETWEEN :startDt AND :endDt ");
			params.put("startDt", Utility.getDate(search.getStartDt(), 0));
			params.put("endDt", Utility.getDate(search.getEndDt(), 0));
		}
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append(" AND cti.USR_FILE_NM like :ctNm ");
			params.put("ctNm", "%" + search.getKeyword() + "%");
		}
		if (StringUtils.isNotBlank(search.getPgmCd())) {
			query.append(" AND ct.PGM_CD like :pgmCd ");
			params.put("pgmCd", search.getPgmCd());
		}
		if (StringUtils.isNotBlank(search.getNid())) {
			query.append(" AND ct.NID like :nid ");
			params.put("nid", search.getNid());
		}
		if (StringUtils.isNotBlank(search.getPersonInfo3())) {
			query.append(" AND ct.S_SEQ like :personInfo3 ");
			params.put("personInfo3", "%" + search.getPersonInfo3() + "%");
		}
		if (StringUtils.isNotBlank(search.getPart2())) {
			query.append(" AND ct.PART like :part ");
			params.put("part", search.getPart2());
		}
		String countSql = "select count(*) ";
		String dataSql = "select ct.PGM_CD,ct.PART, ct.CT_ID, ct.CT_NM, ct.PGM_ID, ct.CT_TYP, ct.CT_CLA, ct.CHANNEL_CODE, ct.BRD_DD, ct.PGM_NM, ct.REG_DT,ct.REGRID,ct.PERSON_INFO,cti.USR_FILE_NM,ct.TRIMM_STE";
		String orderSql = "ORDER BY ct.CT_ID DESC ";

		try {
			PaginationHelper<ContentsTbl> ph = new PaginationHelper<ContentsTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 20,
					new BeanPropertyRowMapper(ContentsTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"ContentsDaoImpl - findcontentsList Error", dae);
		}
		return ps;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<ContentsTbl> findrmscontentsUd(Search search)
			throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<ContentsTbl> ps = null;


		StringBuffer query = new StringBuffer();
		query.append("")
		.append(" FROM CONTENTS_TBL ct                                                      ")
		.append(" 	inner JOIN CONTENTS_INST_TBL cti ON ct.CT_ID = cti.CT_ID             ")
		// .append("   inner JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID                      ")
		.append(" WHERE ct.USE_YN = 'Y' AND ct.REGRID='RMS' AND (cti.CTI_FMT LIKE '1%' or cti.CTI_FMT LIKE '3%')           ");
		/**
		if (search.getStartDt() != null) {
			query.append(" AND TO_CHAR(ct.BRD_DD,'YYYY-MM-DD') BETWEEN :startDt AND :endDt ");
			params.put("startDt", Utility.getDate(search.getStartDt(), 0));
			params.put("endDt", Utility.getDate(search.getEndDt(), 0));
		}
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append(" AND ct.CT_NM like :ctNm ");
			params.put("ctNm", "%" + search.getKeyword() + "%");
		}
		 */
		if (StringUtils.isNotBlank(search.getPgmCd())) {
			query.append(" AND ct.PGM_CD like :pgmCd ");
			params.put("pgmCd", search.getPgmCd());
		}
		if (StringUtils.isNotBlank(search.getNid())) {
			query.append(" AND ct.NID like :nid ");
			params.put("nid", search.getNid());
		}
		/**
		if (StringUtils.isNotBlank(search.getPersonInfo3())) {
			query.append(" AND ct.S_SEQ like :personInfo3 ");
			params.put("personInfo3", "%" + search.getPersonInfo3() + "%");
		}
		if (StringUtils.isNotBlank(search.getPart2())) {
			query.append(" AND ct.PART like :part ");
			params.put("part", search.getPart2());
		}
		 */
		String countSql = "select count(*) ";
		String dataSql = "select ct.PGM_CD,ct.PART, ct.CT_ID, ct.CT_NM, ct.PGM_ID, ct.CT_TYP, ct.CT_CLA, ct.CHANNEL_CODE, ct.BRD_DD, ct.PGM_NM, ct.REG_DT,ct.REGRID,ct.PERSON_INFO,cti.USR_FILE_NM,ct.TRIMM_STE";
		String orderSql = "ORDER BY ct.CT_ID DESC ";

		try {
			PaginationHelper<ContentsTbl> ph = new PaginationHelper<ContentsTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 16,
					new BeanPropertyRowMapper(ContentsTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"ContentsDaoImpl - findcontentsList Error", dae);
		}
		return ps;

	}
	@Override
	public int getCtId() throws DaoNonRollbackException {
		// ((Integer) getSqlMapClientTemplate().queryForObject(
		// "Contents.getContents")).intValue();
		return 0;
	}

	@Override
	public ContentsTbl getRmsContentsCount(Map<String, Object> params) throws DaoNonRollbackException {
		// ((Integer) getSqlMapClientTemplate().queryForObject(
		// "Contents.getContents")).intValue();
		return (ContentsTbl) getSqlMapClientTemplate().queryForObject("Contents.getRmsContentsCount", params);
	}

	@Override
	public ContentsTbl getContents2(Map<String, Object> params) throws DaoNonRollbackException {
		return (ContentsTbl)getSqlMapClientTemplate().queryForObject("Contents.getContents2", params);
	}

	@Override
	public ContentsTbl getContents3(Map<String, Object> params) throws DaoNonRollbackException {
		return (ContentsTbl)getSqlMapClientTemplate().queryForObject("Contents.getContents3", params);
	}

	@Override
	public ContentsTbl getContents4(Map<String, Object> params) throws DaoNonRollbackException {
		return (ContentsTbl)getSqlMapClientTemplate().queryForObject("Contents.getContents4", params);
	}

	@Override
	public void updateContentsInfo(ContentsTbl contentsTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().update("Contents.updateContents", contentsTbl);

	}

	@Override
	public void updateContents2(ContentsTbl contentsTbl) throws DaoRollbackException {
		getSqlMapClientTemplate().update("Contents.updateContents2", contentsTbl);
	}

	@Override
	public ContentsTbl getContentsPgmNum(Map<String, Object> params)
			throws DaoNonRollbackException {

		return (ContentsTbl)getSqlMapClientTemplate().queryForObject("Contents.getContentsPgmNum", params);
	}


}
