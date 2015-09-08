package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dto.QcReportTbl;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface QcReportManagerService {
	
	
	public List<QcReportTbl> findQcReport(Map<String, Object> params)throws ServiceException;
	
	public List<QcReportTbl> allfindQc(Map<String, Object> params)throws ServiceException;

	public void insertQcReport(QcReportTbl qcReportTbl) throws ServiceException;
}
