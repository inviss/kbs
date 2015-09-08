package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.dto.MenuTbl;


public interface MenuDao {
	
	/**
	 * 사용자별 접근가능 메뉴
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<MenuTbl> findUserMenus(Map<String, Object> params) throws DaoNonRollbackException;
	
	public MenuTbl getFirstMenu(Map<String, Object> params) throws DaoNonRollbackException;
}
