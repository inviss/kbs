package kr.co.d2net.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dao.ProFlDao;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.Search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value="proflManagerService")
public class ProFlManagerServiceImpl implements ProFlManagerService {

	@Autowired
	private ProFlDao proflDao;
	
	@Override
	public ProFlTbl getProFl(Map<String, Object> params) throws ServiceException {
		return proflDao.getProFl(params);
	}
	
	@Override
	public ProFlTbl getProFlId(Map<String, Object> params) throws ServiceException {
		return proflDao.getProFlId(params);
	}

	@SuppressWarnings("unchecked")
	public List<ProFlTbl> findProFl(Map<String, Object> params) throws ServiceException {
		List<ProFlTbl> proflTbls = proflDao.findProFl(params);
		return (proflTbls == null) ? Collections.EMPTY_LIST : proflTbls;
	}
	

	@Override
	public void insertProFl(ProFlTbl proflTbl) throws ServiceException {
		proflDao.insertProFl(proflTbl);
	}
	
	@Override
	public void updateProFl(ProFlTbl proflTbl) throws ServiceException {
		proflDao.updateProFl(proflTbl);
	}
	
	@Override
	public void deleteProFl(ProFlTbl proflTbl) throws ServiceException {
		proflDao.deleteProFl(proflTbl);
	}

	@Override
	public PaginationSupport<ProFlTbl> findProFlList(Search search) throws ServiceException {
		PaginationSupport<ProFlTbl> proflList = proflDao.findproflList(search);

		return proflList;
	}

	@Override
	public List<String> findProflExt(String mediaCla) throws ServiceException {
		return proflDao.findProflExt(mediaCla);
	}

}
