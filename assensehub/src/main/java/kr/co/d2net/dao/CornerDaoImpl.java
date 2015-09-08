package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.CornerTbl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value = "cornerDao")
public class CornerDaoImpl extends SqlMapClientDaoSupport implements CornerDao {

	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CornerTbl> findCorner(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("Corner.findCorner", params);
	}

	@Override
	public CornerTbl getCorner(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (CornerTbl)getSqlMapClientTemplate().queryForObject("Corner.getCorner", params);
	}

	@Override
	public void updateCorner(CornerTbl cornerTbl) throws DaoRollbackException {
		getSqlMapClientTemplate().update("Corner.updateCorner", cornerTbl);
	}

	@Override
	public void insertCorner(CornerTbl cornerTbl) throws DaoRollbackException {
		getSqlMapClientTemplate().insert("Corner.insertCorner", cornerTbl);
	}

}
