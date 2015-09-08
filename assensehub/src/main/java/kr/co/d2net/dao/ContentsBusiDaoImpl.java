package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.ContentsBusiTbl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value="contentsbusiDao")
public class ContentsBusiDaoImpl extends SqlMapClientDaoSupport implements ContentsBusiDao {
	
	private static Log logger = LogFactory.getLog(ContentsBusiDaoImpl.class);

	// 리스트 목록 조회(페이징 처리)를 할때 사용
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	private ContentsBusiDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	// 페이징 처리를 제외한 CRUD에 모두 사용
	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<ContentsBusiTbl> findContentsBusi(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("ContentsBusi.findContentsBusi", params);
	}

	@Override
	public ContentsBusiTbl getContentsBusi(Map<String, Object> params) throws DaoNonRollbackException {
		return (ContentsBusiTbl)getSqlMapClientTemplate().queryForObject("ContentsBusi.getContentsBusi", params);
	}

	@Override
	public void insertContentsBusi(ContentsBusiTbl contentsbusiTbl) throws DaoRollbackException {
		//logger.debug("--->"+contentsTbl.getCtNm());
		
		getSqlMapClientTemplate().insert("ContentsBusi.insertContentsBusi", contentsbusiTbl);
	}

}
