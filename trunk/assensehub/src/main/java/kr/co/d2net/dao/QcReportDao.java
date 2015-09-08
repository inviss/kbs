package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.QcReportTbl;
import kr.co.d2net.dto.Search;

public interface QcReportDao {

	/**
	 * QcReport 조회
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<QcReportTbl> findQcReport(Map<String, Object> params)
			throws DaoNonRollbackException;
	
	public List<QcReportTbl> allfindQc(Map<String, Object> params)
	throws DaoNonRollbackException;

	public void insertQcReport(QcReportTbl qcReportTbl) throws DaoRollbackException;
	
	public Integer getQcCount(Long seq) throws DaoNonRollbackException;
}
