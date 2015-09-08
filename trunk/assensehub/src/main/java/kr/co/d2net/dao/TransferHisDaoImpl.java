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
import kr.co.d2net.dto.BusiPartnerTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.TranscorderHisTbl;
import kr.co.d2net.dto.TransferHisTbl;
import kr.co.d2net.service.BusiPartnerManagerService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value = "trsHisDao")
public class TransferHisDaoImpl extends SqlMapClientDaoSupport implements
		TransferHisDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private TransferHisDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@Autowired
	private BusiPartnerManagerService busiPartnerManagerService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<TransferHisTbl> findTransferHisWork(Search search)
			throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<TransferHisTbl> ps = null;

		StringBuffer query = new StringBuffer();
		query.append("")
				.append(" FROM TRS_HIS_TBL trs                                                            ")
				.append(" 	LEFT OUTER JOIN CONTENTS_INST_TBL cti ON trs.CTI_ID = cti.CTI_ID              ")
				.append("   LEFT OUTER JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID                       ")
				.append("   LEFT OUTER JOIN PRO_FL_TBL PFL ON PFL.PRO_FLID = TRS.PRO_FLID                  ")
				.append("   LEFT OUTER JOIN BUSI_PARTNER_TBL BPT ON BPT.BUSI_PARTNERID =  trs.busi_partner_id  ")
				.append("   LEFT OUTER JOIN CODE_TBL CTL ON CTL.SCL_CD =  trs.WORK_STATCD AND CTL.CLF_CD = 'WORK'  ")
				.append(" WHERE trs.USE_YN = 'Y'                                                            ");
		if (search.getStartDt() != null) {
			if(search.getSearchDayName().equals("ct.BRD_DD")){
				query.append(" AND TO_CHAR(ct.BRD_DD,'YYYY-MM-DD') BETWEEN :startDt AND :endDt");
			}else{
				query.append(" AND TO_CHAR(trs.REG_DT,'YYYY-MM-DD') BETWEEN :startDt AND :endDt");
			}
			params.put("startDt", Utility.getDate(search.getStartDt(), "yyyy-MM-dd"));
			params.put("endDt", Utility.getDate(search.getEndDt(), "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append(" AND ct.PGM_NM like :ctNm ");
			params.put("ctNm", "%" + search.getKeyword() + "%");
		}
		if (StringUtils.isNotBlank(search.getWorkStat())&& !search.getWorkStat().equals("111")) {
			if(search.getWorkStat().equals("999")){
				query.append(" AND trs.WORK_STATCD not like '200' and trs.WORK_STATCD not like '002' and trs.WORK_STATCD not like '001' and trs.WORK_STATCD not like '000' ");
			}else{
				query.append(" AND trs.WORK_STATCD like :pgmId ");
				params.put("pgmId", search.getWorkStat());
			}	
		}
		if (StringUtils.isNotBlank(search.getChannelCode())&& !search.getChannelCode().equals("0")) {
			query.append(" AND ct.CHANNEL_CODE like :channelCode ");
			params.put("channelCode", search.getChannelCode());
		}

		String countSql = "select count(*) ";
		String dataSql = "select TO_CHAR(trs.REG_DT,'YYYY-MM-DD') as REG_DATE,ct.BRD_DD, ct.CHANNEL_CODE,CTL.CLF_NM, TRS.SEQ, TO_CHAR(trs.TRS_STR_DT,'HH24:MI') as STR_REG_DT, TO_CHAR(trs.TRS_END_DT,'HH24:MI') as STR_MOD_DT, trs.WORK_STATCD, ct.CT_ID,PFL.PRO_FLNM,ct.PGM_NM, ct.SEGMENT_ID, ct.SEGMENT_NM, ct.CT_NM, cti.CTI_ID, trs.FL_PATH, trs.URL, trs.RETRY_CNT, trs.PRGRS,BPT.COMPANY , CTI.WRK_FILE_NM||'.'||CTI.FL_EXT AS WRK_FILE_NM,  "+
						" (SELECT co.CLF_NM FROM CODE_TBL co WHERE co.SCL_CD = ct.LOCAL_CODE AND co.CLF_CD = 'DOAD') local,"+
						" (SELECT co.CLF_NM FROM CODE_TBL co WHERE co.SCL_CD = ct.CHANNEL_CODE AND co.CLF_CD = 'CHAN') channel";
		String orderSql = " ORDER BY trs.seq DESC ";

		try {
			PaginationHelper<TransferHisTbl> ph = new PaginationHelper<TransferHisTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 20,

					new BeanPropertyRowMapper(TransferHisTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"TrsHisDaoImpl - findTransfer Error", dae);
		}
		return ps;
	}

	@Override
	public void insertTransferHisWork(TransferHisTbl trasferHisTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().insert("TrsHis.insertTrsHis", trasferHisTbl);
	}

	@Override
	public int updateTransferHisWork(TransferHisTbl trasferHisTbl)
			throws DaoRollbackException {
		return getSqlMapClientTemplate().update("TrsHis.updateTrsHisState", trasferHisTbl);
	}

	@Override
	public TransferHisTbl getTransferHisJob(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (TransferHisTbl) getSqlMapClientTemplate().queryForObject(
				"TrsHis.getTrsHisJob", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TransferHisTbl> findTransferHisJob(Map<String, Object> params)
			throws DaoNonRollbackException {
		String transferGB = (String)params.get("transferGB");
		return getSqlMapClientTemplate().queryForList("TrsHis.find"+transferGB+"StatCd", params); // Start 2014.02.21 변경
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TransferHisTbl> findTransferHisJobMap(Map<String, Object> params) throws DaoNonRollbackException {
		String transferGB = (String)params.get("transferGB");
		return getSqlMapClientTemplate().queryForList("TrsHis.find"+transferGB+"StatCd", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransferHisTbl> findTransferBusiJob(Long seq)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("TrsHis.findTrsHisBusi", seq);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransferHisTbl> findTransferStatus(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("TrsHis.findTrsHisStatus", params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<TransferHisTbl> findMetaURLlist(Search search)
			throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<TransferHisTbl> ps = null;

		StringBuffer query = new StringBuffer();
		query.append("")
				.append(" FROM TRS_HIS_TBL trs                                                            ")
				.append(" 	LEFT OUTER JOIN CONTENTS_INST_TBL cti ON trs.CTI_ID = cti.CTI_ID              ")
				.append("   LEFT OUTER JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID                       ")
				.append("   LEFT OUTER JOIN PRO_FL_TBL PFL ON PFL.PRO_FLID = TRS.PRO_FLID                  ")
				.append("   LEFT OUTER JOIN BUSI_PARTNER_TBL BPT ON BPT.BUSI_PARTNERID = trs.busi_partner_id  ")
				.append(" WHERE trs.USE_YN = 'Y' AND (trs.META_INS = 'S' OR trs.META_INS = 'E' OR trs.META_INS = 'C')    ");
		if (search.getStartDt() != null) {
			query.append(" AND trs.REG_DT BETWEEN to_date(:startDt, 'YYYY-MM-DD') AND to_date(:endDt, 'YYYY-MM-DD') ");
			params.put("startDt", Utility.getDate(search.getStartDt(),0));
			params.put("endDt", Utility.getDate(search.getEndDt(), 1));
		}
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append(" AND ct.CT_NM like :ctNm ");
			params.put("ctNm", "%" + search.getStartDt() + "%");
		}
		if (StringUtils.isNotBlank(search.getPgmId())) {
			query.append(" AND ct.PGM_ID like :pgmId ");
			params.put("pgmId", search.getPgmId());
		}

		String countSql = "select count(*) ";
		String dataSql = "select TRS.SEQ, ct.PGM_ID, ct.SEGMENT_ID, TO_CHAR(ct.BRD_DD,'yyyy-MM-dd') AS BRD, ct.CT_ID,PFL.PRO_FLNM,ct.PGM_NM, ct.CT_NM, cti.CTI_ID, trs.FL_PATH, trs.URL, trs.RETRY_CNT, trs.PRGRS, trs.META_INS, trs.META_UPD, trs.PRGRS,trs.WORK_STATCD,BPT.COMPANY ";
		// String dataSql =
		// "select trs.FL_PATH, trs.URL, trs.RETRY_CNT, trs.PRGRS, trs.TRS_STR_DT ";
		String orderSql = "ORDER BY TRS.SEQ DESC ";

		try {
			PaginationHelper<TransferHisTbl> ph = new PaginationHelper<TransferHisTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 20,

					new BeanPropertyRowMapper(TransferHisTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"TrsHisDaoImpl - findTransfer Error", dae);
		}
		return ps;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TransferHisTbl> findTrsCtiBusi(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("TrsHis.findCtiBusi", params);
	}
	
	@Override
	public int updateTransRequest(TransferHisTbl transferHisTbl)
			throws DaoRollbackException {
		return getSqlMapClientTemplate().update("TrsHis.updateTransRequest",
				transferHisTbl);
	}
	
	@Override
	public int updateTransRequest2(TransferHisTbl transferHisTbl)
			throws DaoRollbackException {
		return getSqlMapClientTemplate().update("TrsHis.updateTransRequest2",
				transferHisTbl);
	}

	@Override
	public void insertTransferHisWork(List<TransferHisTbl> trsHisTbls)
			throws DaoRollbackException {
		try {
			getSqlMapClientTemplate().getSqlMapClient().startTransaction() ;
			getSqlMapClientTemplate().getSqlMapClient().getCurrentConnection().setAutoCommit(false) ;

			getSqlMapClientTemplate().getSqlMapClient().startBatch() ;
			
			//MySQL에서는 아래 부분이 항상 실행되어야 함. 오라클은 없어도 됨
			//getSqlMapClientTemplate().getSqlMapClient().commitTransaction() ;

			for(TransferHisTbl transferHisTbl : trsHisTbls) {
				String busiPartnerId = transferHisTbl.getBusiPartnerId();
				
				Map<String, Object> params = new HashMap<String, Object>();
				
				params.put("busiPartnerid", busiPartnerId);

				BusiPartnerTbl busipartnerTbl = busiPartnerManagerService.getBusiPartner(params);
				
				if(busipartnerTbl.getFtpServYn().equals("Y")){
					getSqlMapClientTemplate().insert("TrsHis.insertTrsHis", transferHisTbl);
				}else{
					
				}
			}
			
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();
			getSqlMapClientTemplate().getSqlMapClient().getCurrentConnection().commit() ;
			getSqlMapClientTemplate().getSqlMapClient().endTransaction() ;

		} catch (Exception e) {
			throw new DaoRollbackException("insertTransferHisWork Error", e);
		} finally {
			try {
				getSqlMapClientTemplate().getSqlMapClient().getCurrentConnection().setAutoCommit(true) ;
				getSqlMapClientTemplate().getSqlMapClient().getCurrentConnection().commit() ;
				getSqlMapClientTemplate().getSqlMapClient().endTransaction() ;
				
			} catch (Exception e2) {}
		}
		
	}
	
	@Override
	public List<TransferHisTbl>  getTrsHisPrgrs(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("TrsHis.getTrsHisPrgrs", params);
	
	}
}
