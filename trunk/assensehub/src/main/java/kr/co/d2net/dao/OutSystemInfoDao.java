package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.OutSystemInfoTbl;
import kr.co.d2net.dto.Search;

public interface OutSystemInfoDao {
	
	public List<OutSystemInfoTbl> findOutSystemInfo(Map<String, Object> params)throws DaoNonRollbackException;

	public OutSystemInfoTbl getOutSystemInfo(Map<String, Object> params) throws DaoNonRollbackException;
		
	public void insertOutSystemInfo(OutSystemInfoTbl outsystemTbl) throws DaoRollbackException;
	
	public void updateOutSystemInfo(OutSystemInfoTbl outsystemTbl) throws DaoRollbackException;
	
	public void deleteOutSystemInfo(OutSystemInfoTbl outsystemTbl) throws DaoRollbackException;
	
	public PaginationSupport<OutSystemInfoTbl> findoutsystemList(Search search) throws DaoNonRollbackException;
}
