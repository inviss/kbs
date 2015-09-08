package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;

public interface QuartzManagerDao {
	public List<String> findQuartzTables() throws DaoNonRollbackException;
	
	public List<String> findTableIndexs(String tableName) throws DaoNonRollbackException;
	
	public void createQuartzTables(Map<String, String> params) throws DaoRollbackException;
	
	public void createTableIndex(Map<String, String> params) throws DaoRollbackException;
	
	public void quartzTableInitialize(String lockName) throws DaoRollbackException;
	
	public void insertLockName(String lockName) throws DaoRollbackException;
}
