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
import kr.co.d2net.dto.CodeTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.xml.internal.Code;
import kr.co.d2net.dto.xml.internal.FindList;
import kr.co.d2net.dto.xml.internal.Workflow;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value = "codeDao")
public class CodeDaoImpl extends SqlMapClientDaoSupport implements CodeDao {
	
	// 리스트 목록 조회(페이징 처리)를 할때 사용
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	private CodeDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	
	
	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<CodeTbl> findCode(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("Code.findCode", params);
	}

	@Override
	public CodeTbl getCode(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (CodeTbl) getSqlMapClientTemplate().queryForObject(
				"Code.getCode", params);
	}

	@SuppressWarnings("unchecked")
	public List<CodeTbl> findCodeClfCd(Map<String, Object> params)
		throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("Code.findCodeClfCd", params);
	}
	
	@Override
	public void updateCode(CodeTbl codeTbl) throws DaoRollbackException {
		getSqlMapClientTemplate().update("Code.updateCode", codeTbl);
	}
	
	@Override
	public void insertCode(CodeTbl codeTbl) throws DaoRollbackException {
		getSqlMapClientTemplate().update("Code.insertCode", codeTbl);
	}

	@SuppressWarnings("unchecked")
	@Override
	public FindList findCodeList(Workflow workflow) throws DaoRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		if(logger.isDebugEnabled()) {
			logger.debug("keyword : "+workflow.getKeyword());
		}
		if(StringUtils.isNotBlank(workflow.getKeyword())){
			if(workflow.getKeyword().indexOf("|") > -1) {
				String[] tmp = workflow.getKeyword().split("\\|");
				params.put("clfCd", tmp[0]);
				params.put("eqId", tmp[1]);
			} else {
				params.put("clfCd", workflow.getKeyword());
			}
		}
		if(logger.isDebugEnabled()) {
			logger.debug("params : "+params);
		}
		
		List<CodeTbl> codeTbls = getSqlMapClientTemplate().queryForList("Code.findCode", params);

		FindList codeList = new FindList();
		Code code = null;
		try {
			for (CodeTbl codeTbl : codeTbls) {
				code = new Code();
				code.setClfCd(codeTbl.getClfCd());
				code.setClfNm(codeTbl.getClfNm());
				code.setNote(codeTbl.getNote());
				code.setRmk1(codeTbl.getRmk1());
				code.setSclCd(codeTbl.getSclCd());

				codeList.addCodes(code);
			}

			return codeList;
		} catch (Exception e) {
			logger.error("findCodeList error", e);
		}

		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationSupport<CodeTbl> findcodeListView(Search search)
			throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		PaginationSupport<CodeTbl> ps = null;
		
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+search.getChannelCode());
		
		StringBuffer query = new StringBuffer();
		query.append("")
				.append(" FROM CODE_TBL co                                                      ")
				.append(" WHERE (co.USE_YN = 'Y' OR co.USE_YN='N')                                                   ");
		
		if (StringUtils.isNotBlank(search.getKeyword())) {
			query.append(" AND co.CLF_NM like :clfNm ");
			params.put("clfNm", "%" + search.getKeyword() + "%");
		}
		
		if (StringUtils.isNotBlank(search.getChannelCode()) || search.getChannelCode().equals("0")) {
			
			String select = search.getChannelCode();
			String sv[]=select.split(",");
			
			query.append(" AND co.CLF_CD in (");

			int i=0;
			for(String sCd : sv) {
				if(i==0)
					query.append("'"+sCd+"'");
				else
					query.append(",'"+sCd+"'");
				
				i++;
			}
			query.append(")");
		}
		
		String countSql = "select count(*) ";
		String dataSql = "select co.CLF_CD, co.SCL_CD, co.CLF_NM, co.RMK_1, co.RMK_2, co.USE_YN";
		String orderSql = "ORDER BY co.CLF_CD DESC, co.SCL_CD DESC  ";
		
		try {
			PaginationHelper<CodeTbl> ph = new PaginationHelper<CodeTbl>();
			ps = ph.fetchPage(jdbcTemplate, countSql + query.toString(),
					dataSql + query.toString() + orderSql, params,
					search.getPageNo(), 12,

					new BeanPropertyRowMapper(CodeTbl.class));
		} catch (DataAccessException dae) {
			throw new DaoNonRollbackException(
					"CodeDaoImpl - findcodeList Error", dae);
		}
		return ps;

	}

}
