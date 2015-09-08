package kr.co.d2net.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dao.DisuseInfoDao;
import kr.co.d2net.dto.DisuseInfoTbl;
import kr.co.d2net.dto.Search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "disuseinfoManagerService")
public class DisuseInfoManagerServiceImpl implements DisuseInfoManagerService {

	@Autowired
	private DisuseInfoDao disuseinfoDao;

	@Override
	public DisuseInfoTbl getDisuseInfo(Map<String, Object> params)
			throws ServiceException {
		return disuseinfoDao.getDisuseInfo(params);
	}

	@SuppressWarnings("unchecked")
	public List<DisuseInfoTbl> findDisuseInfo(Map<String, Object> params)
			throws ServiceException {
		List<DisuseInfoTbl> disuseinfoTbls = disuseinfoDao
				.findDisuseInfo(params);
		return (disuseinfoTbls == null) ? Collections.EMPTY_LIST
				: disuseinfoTbls;
	}

	@Override
	public void insertDisuseInfo(DisuseInfoTbl disuseinfoTbl)
			throws ServiceException {
		disuseinfoDao.insertDisuseInfo(disuseinfoTbl);
	}

	@Override
	public PaginationSupport<DisuseInfoTbl> findDisuseList(Search search)
			throws ServiceException {

		PaginationSupport<DisuseInfoTbl> disuseList = disuseinfoDao
				.finddisuseList(search);

		return disuseList;
	}

	@Override
	public PaginationSupport<DisuseInfoTbl> findDisuseList2(Search search)
			throws ServiceException {

		PaginationSupport<DisuseInfoTbl> disuseList = disuseinfoDao
				.finddisuseList2(search);

		return disuseList;
	}

	@Override
	public PaginationSupport<DisuseInfoTbl> finddisuseStatusList(Search search)
			throws ServiceException {

		PaginationSupport<DisuseInfoTbl> disuseList = disuseinfoDao
				.finddisuseStatusList(search);

		return disuseList;
	}

	@Override
	public PaginationSupport<DisuseInfoTbl> finddisuseRenewalList(Search search)
			throws ServiceException {

		PaginationSupport<DisuseInfoTbl> disuseList = disuseinfoDao
				.finddisuseRenewalList(search);

		return disuseList;
	}

	@Override
	public void updateDisuseInfo(DisuseInfoTbl disuseInfoTbl)
			throws ServiceException {
		disuseinfoDao.updateDisuseInfo(disuseInfoTbl);
	}
}
