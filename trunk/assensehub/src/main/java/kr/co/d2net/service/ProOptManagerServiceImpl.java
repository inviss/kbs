package kr.co.d2net.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dao.ProOptDao;
import kr.co.d2net.dto.ProOptTbl;
import kr.co.d2net.dto.Search;


@Service(value="prooptManagerService")
public class ProOptManagerServiceImpl implements ProOptManagerService {

	@Autowired
	private ProOptDao prooptDao;
	
	@Override
	public ProOptTbl getProOpt(Map<String, Object> params) throws ServiceException {
		return prooptDao.getProOpt(params);
	}

	@SuppressWarnings("unchecked")
	public List<ProOptTbl> findProOpt(Map<String, Object> params) throws ServiceException {
		List<ProOptTbl> proopts = prooptDao.findProOpt(params);
		return (proopts == null) ? Collections.EMPTY_LIST : proopts;
	}
	

	@Override
	public void insertProOpt(ProOptTbl proopt) throws ServiceException {
		prooptDao.insertProOpt(proopt);
	}
	
	@Override
	public void deleteProOpt(ProOptTbl proopt) throws ServiceException {
		prooptDao.deleteProOpt(proopt);
	}

	@Override
	public PaginationSupport<ProOptTbl> findProOptList(Search search) throws ServiceException {
		PaginationSupport<ProOptTbl> prooptList = prooptDao.findprooptList(search);

		return prooptList;
	}

}
