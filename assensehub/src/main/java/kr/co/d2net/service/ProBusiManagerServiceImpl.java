package kr.co.d2net.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dao.ProBusiDao;
import kr.co.d2net.dto.ProBusiTbl;
import kr.co.d2net.dto.ProFlTbl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "probusiManagerService")
public class ProBusiManagerServiceImpl implements ProBusiManagerService {

	@Autowired
	private ProBusiDao probusiDao;

	@Override
	public ProBusiTbl getProBusi(Map<String, Object> params)
			throws ServiceException {
		return probusiDao.getProBusi(params);
	}

	@SuppressWarnings("unchecked")
	public List<ProBusiTbl> findProBusis(Map<String, Object> params)
			throws ServiceException {
		List<ProBusiTbl> probusiTbls = probusiDao.findProBusis(params);
		return (probusiTbls == null) ? Collections.EMPTY_LIST : probusiTbls;
	}

	@SuppressWarnings("unchecked")
	public List<ProBusiTbl> findProBusi(Map<String, Object> params)
			throws ServiceException {
		List<ProBusiTbl> probusiTbls = probusiDao.findProBusi(params);
		return (probusiTbls == null) ? Collections.EMPTY_LIST : probusiTbls;
	}

	@SuppressWarnings("unchecked")
	public List<ProBusiTbl> findProBusi2(Map<String, Object> params)
			throws ServiceException {
		List<ProBusiTbl> probusiTbls = probusiDao.findProBusi2(params);
		return (probusiTbls == null) ? Collections.EMPTY_LIST : probusiTbls;
	}

	@Override
	public void insertProBusi(ProBusiTbl probusiTbl) throws ServiceException {
		probusiDao.insertProBusi(probusiTbl);
	}

	@Override
	public void deleteProBusi(ProBusiTbl probusiTbl) throws ServiceException {
		probusiDao.deleteProBusi(probusiTbl);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProFlTbl> findPgmProBusi(Map<String, Object> params)
			throws ServiceException {
		List<ProFlTbl> proFlTbls = probusiDao.findPgmProBusi(params);
		return (proFlTbls == null) ? Collections.EMPTY_LIST : proFlTbls;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProFlTbl> findNewPgmProBusi(Map<String, Object> params)
			throws ServiceException {
		List<ProFlTbl> proFlTbls = probusiDao.findNewPgmProBusi(params);
		return (proFlTbls == null) ? Collections.EMPTY_LIST : proFlTbls;
	}

}
