package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.OutSystemInfoTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.Search;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OutSystemInfoManagerService {
	
	
	public List<OutSystemInfoTbl> findOutSystemInfo(Map<String, Object> params)throws ServiceException;

	public OutSystemInfoTbl getOutSystemInfo(Map<String, Object> params) throws ServiceException;
		
	public void insertOutSystemInfo(OutSystemInfoTbl outsystemTbl) throws ServiceException;
	
	public void updateOutSystemInfo(OutSystemInfoTbl outsystemTbl) throws ServiceException;
	
	public void deleteOutSystemInfo(OutSystemInfoTbl outsystemTbl) throws ServiceException;
	
	public PaginationSupport<OutSystemInfoTbl> findOutSystemList(Search search) throws ServiceException;
}
