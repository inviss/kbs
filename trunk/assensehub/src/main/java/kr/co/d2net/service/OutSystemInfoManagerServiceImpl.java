package kr.co.d2net.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dao.OutSystemInfoDao;
import kr.co.d2net.dao.ProFlDao;
import kr.co.d2net.dto.OutSystemInfoTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.Search;


@Service(value="outsysteminfoManagerService")
public class OutSystemInfoManagerServiceImpl implements OutSystemInfoManagerService {

	@Autowired
	private OutSystemInfoDao outsysteminfoDao;
	
	@Override
	public OutSystemInfoTbl getOutSystemInfo(Map<String, Object> params) throws ServiceException {
		return outsysteminfoDao.getOutSystemInfo(params);
	}
	

	@SuppressWarnings("unchecked")
	public List<OutSystemInfoTbl> findOutSystemInfo(Map<String, Object> params) throws ServiceException {
		List<OutSystemInfoTbl> ousysteminfoTbls = outsysteminfoDao.findOutSystemInfo(params);
		return (ousysteminfoTbls == null) ? Collections.EMPTY_LIST : ousysteminfoTbls;
	}
	

	@Override
	public void insertOutSystemInfo(OutSystemInfoTbl outsystemTbl) throws ServiceException {
		outsysteminfoDao.insertOutSystemInfo(outsystemTbl);
	}
	
	@Override
	public void updateOutSystemInfo(OutSystemInfoTbl outsystemTbl) throws ServiceException {
		outsysteminfoDao.updateOutSystemInfo(outsystemTbl);
	}
	
	@Override
	public void deleteOutSystemInfo(OutSystemInfoTbl outsystemTbl) throws ServiceException {
		outsysteminfoDao.deleteOutSystemInfo(outsystemTbl);
	}

	@Override
	public PaginationSupport<OutSystemInfoTbl> findOutSystemList(Search search) throws ServiceException {
		PaginationSupport<OutSystemInfoTbl> ousysteminfoTbls = outsysteminfoDao.findoutsystemList(search);

		return ousysteminfoTbls;
	}

}
