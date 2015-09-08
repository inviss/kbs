package kr.co.d2net.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dao.QcReportDao;
import kr.co.d2net.dto.QcReportTbl;
import kr.co.d2net.dto.Search;


@Service(value="qcreportManagerService")
public class QcReportManagerServiceImpl implements QcReportManagerService {

	@Autowired
	private QcReportDao qcreportDao;
	
	

	@SuppressWarnings("unchecked")
	public List<QcReportTbl> findQcReport(Map<String, Object> params) throws ServiceException {
		List<QcReportTbl> qcreport = qcreportDao.findQcReport(params);
		return (qcreport == null) ? Collections.EMPTY_LIST : qcreport;
	}

	@SuppressWarnings("unchecked")
	public List<QcReportTbl> allfindQc(Map<String, Object> params) throws ServiceException {
		List<QcReportTbl> qcreport = qcreportDao.allfindQc(params);
		return (qcreport == null) ? Collections.EMPTY_LIST : qcreport;
	}

	@Override
	public void insertQcReport(QcReportTbl qcReportTbl) throws ServiceException {
		qcreportDao.insertQcReport(qcReportTbl);
	}
	

}
