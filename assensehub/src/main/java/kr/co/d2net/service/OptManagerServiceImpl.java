package kr.co.d2net.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dao.OptDao;
import kr.co.d2net.dto.OptTbl;
import kr.co.d2net.dto.Search;


@Service(value="optManagerService")
public class OptManagerServiceImpl implements OptManagerService {

	@Autowired
	private OptDao optDao;
	
	@Override
	public OptTbl getOpt(Map<String, Object> params) throws ServiceException {
		return optDao.getOpt(params);
	}

	@SuppressWarnings("unchecked")
	public List<OptTbl> findOpt(Map<String, Object> params) throws ServiceException {
		List<OptTbl> proopts = optDao.findOpt(params);
		return (proopts == null) ? Collections.EMPTY_LIST : proopts;
	}
	

	@Override
	public void insertOpt(OptTbl opt) throws ServiceException {
		optDao.insertOpt(opt);
	}
	
	@Override
	public void updateOpt(OptTbl opt) throws ServiceException {
		optDao.updateOpt(opt);
	}
	
	@Override
	public void updateOpt2(OptTbl opt) throws ServiceException {
		optDao.updateOpt2(opt);
	}
	
	@Override
	public void updateOpt3(OptTbl opt) throws ServiceException {
		optDao.updateOpt3(opt);
	}
	
	@Override
	public void deleteOpt(OptTbl opt) throws ServiceException {
		optDao.deleteOpt(opt);
	}

	@Override
	public PaginationSupport<OptTbl> findOptList(Search search) throws ServiceException {
		PaginationSupport<OptTbl> optList = optDao.findoptList(search);

		return optList;
	}

}
