package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.dto.MenuTbl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class MenuDaoImpl extends SqlMapClientDaoSupport implements MenuDao {
	
	private static Log logger = LogFactory.getLog(MenuDaoImpl.class);

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<MenuTbl> findUserMenus(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("Menu.findUserMenus", params);
	}

	@Override
	public MenuTbl getFirstMenu(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (MenuTbl)getSqlMapClientTemplate().queryForObject("Menu.getFirstMenu", params);
	}
	
}
