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
import kr.co.d2net.dto.TranscorderHisTbl;

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

@Repository(value = "traHisDao")
public class TranscorderHisDaoImpl extends SqlMapClientDaoSupport implements
		TranscorderHisDao {

	private static Log logger = LogFactory.getLog(TranscorderHisDaoImpl.class);
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private TranscorderHisDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationSupport<TranscorderHisTbl> findTranscorderHisWork(
			Search search) throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<TranscorderHisTbl> ps = null;

		StringBuffer query = new StringBuffer();
		query.append("")
				.append(" FROM TRA_HIS_TBL tra                                                      ")
				.append(" 	LEFT OUTER JOIN CONTENTS_INST_TBL cti ON tra.CTI_ID = cti.CTI_ID    ")
				.append("   LEFT OUTER JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID                 ")
				.append("   LEFT OUTER JOIN PRO_FL_TBL PFL ON PFL.PRO_FLID = TRA.PRO_FLID        ")
				.append("   LEFT OUTER JOIN EQUIPMENT_TBL EQ ON EQ.EQ_PS_CD = TRA.EQ_PS_CD AND EQ.DEVICEID = TRA.DEVICEID      ")
				.append("   LEFT OUTER JOIN CODE_TBL CTL ON CTL.SCL_CD =  tra.WORK_STATCD  AND CTL.CLF_CD = 'WORK'  ")
				.append(" WHERE tra.USE_YN = 'Y'                                                    ")
//				.append("      AND tra.WORK_STATCD<>'200' ")
//				.append("      AND tra.REG_DT > SYSDATE-1  ")
				.append("      AND 1=1  ");
//		if (search.getStartDt() != null) {
//			query.append(" AND REG_DT BETWEEN :startDt AND :endDt ");
//			params.put("startDt", search.getStartDt());
//			params.put("endDt", Utility.getDate(search.getEndDt(), 1));
//		}
//		if (StringUtils.isNotBlank(search.getKeyword())) {
//			query.append(" AND ct.CT_NM like :ctNm ");
//			params.put("ctNm", "%" + search.getStartDt() + "%");
//		}
	
		if (search.getStartDt() != null) {
			if(search.getSearchDayName().equals("ct.BRD_DD")){
				query.append(" AND TO_CHAR(ct.BRD_DD,'YYYY-MM-DD') BETWEEN :startDt AND :endDt");
			}else{
				query.append(" AND TO_CHAR(tra.REG_DT,'YYYY-MM-DD') BETWEEN :startDt AND :endDt");
			}
			//params.put("searchDayName", search.getSearchDayName());
			params.put("startDt", Utility.getDate(search.getStartDt(), 0));
			params.put("endDt", Utility.getDate(search.getEndDt(), 0));
			//params.put("startDt2", Utility.getDate(search.getStartDt(), 1));
		}
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append(" AND ct.PGM_NM like :ctNm ");
			params.put("ctNm", "%" + search.getKeyword() + "%");
		}
		if (StringUtils.isNotBlank(search.getWorkStat())&& !search.getWorkStat().equals("111")) {
			if(search.getWorkStat().equals("999")){
				query.append(" AND tra.WORK_STATCD not like '200' and tra.WORK_STATCD not like '002' and tra.WORK_STATCD not like '001' and tra.WORK_STATCD not like '000' ");
			}else{
				query.append(" AND tra.WORK_STATCD like :pgmId ");
				params.put("pgmId", search.getWorkStat());
			}	
		}
		if (StringUtils.isNotBlank(search.getChannelCode())&& !search.getChannelCode().equals("0")) {
			query.append(" AND ct.CHANNEL_CODE like :channelCode ");
			params.put("channelCode", search.getChannelCode());
		}

		String countSql = "select count(*) ";
		String dataSql =  "select tra.DEVICEID, tra.EQ_PS_CD, TO_CHAR(tra.REG_DT,'YYYY-MM-DD') as REG_DATE, ct.BRD_DD, ct.CHANNEL_CODE,tra.SEQ,cti.ORG_FILE_NM,CT.PGM_NM,CT.SEGMENT_ID,CT.SEGMENT_NM,PFL.PRO_FLNM, ct.CT_ID, ct.CT_NM, cti.CTI_ID, tra.FL_PATH, tra.DOWN_VOL, tra.PRGRS, tra.WRK_CTI_ID, "+
						  " EQ.DEVICE_NM, CTL.CLF_NM, EQ.DEVICE_IP,tra.REG_DT,tra.WORK_STATCD,tra.MOD_DT, TO_CHAR(tra.WORK_STR_DT,'HH24:MI:SS') as STR_REG_DT, TO_CHAR(tra.WORK_END_DT,'HH24:MI:SS') AS STR_MOD_DT,"+
				          //" (select count(*) from qc_report qc where qc.seq = tra.seq) cnt, "+
						  " (SELECT co.CLF_NM FROM CODE_TBL co WHERE co.SCL_CD = ct.LOCAL_CODE AND co.CLF_CD = 'DOAD') local,"+
						  " (SELECT co.CLF_NM FROM CODE_TBL co WHERE co.SCL_CD = ct.CHANNEL_CODE AND co.CLF_CD = 'CHAN') channel";
		String orderSql = " ORDER BY tra.seq desc";

		try {
			PaginationHelper<TranscorderHisTbl> ph = new PaginationHelper<TranscorderHisTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params, search
							.getPageNo(), 20, new BeanPropertyRowMapper(
							TranscorderHisTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"TraHisDaoImpl - findTransfer Error", dae);
		}
		return ps;
	}

	@Override
	public void insertTrancorderHisWork(TranscorderHisTbl transcorderHisTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().insert("TraHis.insertTraHis",
				transcorderHisTbl);
	}

	@Override
	public int updateTranscorderHisWork(TranscorderHisTbl transcorderHisTbl)
			throws DaoRollbackException {
		return getSqlMapClientTemplate().update("TraHis.updateTraHis",
				transcorderHisTbl);
	}
	
	@Override
	public int updateProflRequest(TranscorderHisTbl transcorderHisTbl)
			throws DaoRollbackException {
		return getSqlMapClientTemplate().update("TraHis.updateProflRequest",
				transcorderHisTbl);
	}
	
	@Override
	public int updateAllProflRequest(TranscorderHisTbl transcorderHisTbl)
			throws DaoRollbackException {
		return getSqlMapClientTemplate().update("TraHis.updateAllProflRequest",
				transcorderHisTbl);
	}
	
	@Override
	public int updateProfl(TranscorderHisTbl transcorderHisTbl)
			throws DaoRollbackException {
		return getSqlMapClientTemplate().update("TraHis.updateProfl",
				transcorderHisTbl);
	}

	@Override
	public TranscorderHisTbl getTranscorderHisJob(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (TranscorderHisTbl) getSqlMapClientTemplate().queryForObject(
				"TraHis.getTraHisJob", params);
	}

	@Override
	public TranscorderHisTbl getTraHis(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (TranscorderHisTbl) getSqlMapClientTemplate().queryForObject(
				"TraHis.getTraHis", params);
	}
	
	@Override
	public List<TranscorderHisTbl>  getTraHisPrgrs(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("TraHis.getTraHisPrgrs", params);
	
	}
	
	@Override
	public int updateTranscorderHisState(TranscorderHisTbl transcorderHisTbl)
			throws DaoRollbackException {
		return getSqlMapClientTemplate().update("TraHis.updateTraHisState",
				transcorderHisTbl);
	}

	@Override
	public TranscorderHisTbl getTranscorderHisJobID(Long jobId)
			throws DaoNonRollbackException {
		return (TranscorderHisTbl) getSqlMapClientTemplate().queryForObject(
				"TraHis.getTraHisJobID", jobId);
	}

	@SuppressWarnings("unchecked")
	public List<TranscorderHisTbl> findTranscorderHisJob(Integer size)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("TraHis.findTraHisJob", size);
	}

	@Override
	public ContentsTbl getTranscodeInfo(Long seq)
			throws DaoNonRollbackException {
		return (ContentsTbl)getSqlMapClientTemplate().queryForObject("TraHis.getTraWapper", seq);
	}

	@Override
	public List<TranscorderHisTbl> findTranscorderHisJob(String wrkStatCd)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("TraHis.findTraHisJobIng", wrkStatCd);
	}

	@Override
	public List<Long> findTraHisCtiId(Map<String, Object> params)
			throws DaoNonRollbackException {
		
		return getSqlMapClientTemplate().queryForList("TraHis.findTraHisCtiId", params);
	}

	@Override
	public List<TranscorderHisTbl> findTraHisJobCtiId(Long ctiId)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("TraHis.findTraHisJobCtiId", ctiId);
	}

	@Override
	public void insertTrancorderHisWork(
			List<TranscorderHisTbl> transcorderHisTbls)
			throws DaoRollbackException {
		try {
			getSqlMapClientTemplate().getSqlMapClient().startTransaction() ;
			getSqlMapClientTemplate().getSqlMapClient().getCurrentConnection().setAutoCommit(false) ;

			getSqlMapClientTemplate().getSqlMapClient().startBatch() ;
			
			//MySQL에서는 아래 부분이 항상 실행되어야 함. 오라클은 없어도 됨
			//getSqlMapClientTemplate().getSqlMapClient().commitTransaction() ;

			for(TranscorderHisTbl transcorderHisTbl : transcorderHisTbls) {
				getSqlMapClientTemplate().insert("TraHis.insertTraHis", transcorderHisTbl);
			}
			
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();
			getSqlMapClientTemplate().getSqlMapClient().getCurrentConnection().commit() ;
			getSqlMapClientTemplate().getSqlMapClient().endTransaction() ;

		} catch (Exception e) {
			throw new DaoRollbackException("insertTrancorderHisWork Error", e);
		} finally {
			try {
				getSqlMapClientTemplate().getSqlMapClient().getCurrentConnection().setAutoCommit(true) ;
				getSqlMapClientTemplate().getSqlMapClient().getCurrentConnection().commit() ;
				getSqlMapClientTemplate().getSqlMapClient().endTransaction() ;
				
			} catch (Exception e2) {}
		}
	}

}
