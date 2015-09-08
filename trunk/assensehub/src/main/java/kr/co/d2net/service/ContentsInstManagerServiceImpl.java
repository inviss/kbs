package kr.co.d2net.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dao.ContentsInstDao;
import kr.co.d2net.dto.ContentsInstTbl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "contentsinstManagerService")
public class ContentsInstManagerServiceImpl implements
		ContentsInstManagerService {

	@Autowired
	private ContentsInstDao contentsinstDao;

	@Override
	public ContentsInstTbl getContentsInst(Map<String, Object> params)
			throws ServiceException {
		return contentsinstDao.getContentsInst(params);
	}

	@SuppressWarnings("unchecked")
	public List<ContentsInstTbl> findContentsInst(Map<String, Object> params)
			throws ServiceException {
		List<ContentsInstTbl> contentsinstTbls = contentsinstDao
				.findContentsInst(params);
		return (contentsinstTbls == null) ? Collections.EMPTY_LIST
				: contentsinstTbls;
	}

	@Override
	public Long insertContentsInst(ContentsInstTbl contentsinstTbl)
			throws ServiceException {
		return contentsinstDao.insertContentsInst(contentsinstTbl);
	}

	@SuppressWarnings("unchecked")
	public List<ContentsInstTbl> findContentsInstSummary(
			Map<String, Object> params) throws ServiceException {
		List<ContentsInstTbl> contentsinstTbls = contentsinstDao
				.findContentsInstSummary(params);
		return (contentsinstTbls == null) ? Collections.EMPTY_LIST
				: contentsinstTbls;
	}
	
	@SuppressWarnings("unchecked")
	public List<ContentsInstTbl> findContentsSummary(
			Map<String, Object> params) throws ServiceException {
		List<ContentsInstTbl> contentsinstTbls = contentsinstDao
				.findContentsSummary(params);
		return (contentsinstTbls == null) ? Collections.EMPTY_LIST
				: contentsinstTbls;
	}
	
	@SuppressWarnings("unchecked")
	public List<ContentsInstTbl> findDelContents(
			Map<String, Object> params) throws ServiceException {
		List<ContentsInstTbl> contentsinstTbls = contentsinstDao
				.findDelContents(params);
		return (contentsinstTbls == null) ? Collections.EMPTY_LIST
				: contentsinstTbls;
	}
	
	@SuppressWarnings("unchecked")
	public List<ContentsInstTbl> findContentsRms(
			Map<String, Object> params) throws ServiceException {
		List<ContentsInstTbl> contentsinstTbls = contentsinstDao
				.findContentsRms(params);
		return (contentsinstTbls == null) ? Collections.EMPTY_LIST
				: contentsinstTbls;
	}

	@Override
	public void deleteContentsInst(ContentsInstTbl contentsinstTbl)
			throws ServiceException {
		contentsinstDao.deleteContentsInst(contentsinstTbl);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContentsInstTbl> findDeleteContentsInst(
			Map<String, Object> params) throws ServiceException {
		List<ContentsInstTbl> contentsinstTbls = contentsinstDao.findDeleteContentsInst(params);
		return (contentsinstTbls == null) ? Collections.EMPTY_LIST
				: contentsinstTbls;
	}

	@Override
	public void updateContentsInst(ContentsInstTbl contentsinstTbl)
			throws ServiceException {
		contentsinstDao.updateContentsInst(contentsinstTbl);
	}

	@Override
	public ContentsInstTbl getContentsInstForwardMeta(Map<String, Object> params)
			throws ServiceException {
		// TODO Auto-generated method stub
		return contentsinstDao.getContentsInstForwardMeta(params);
	}

	@Override
	public List<ContentsInstTbl> findDefaultDeleteContentsInst(
			Map<String, Object> params) throws ServiceException {
		List<ContentsInstTbl> contentsinstTbls = contentsinstDao
				.findDefaultDeleteContentsInst(params);
		return (contentsinstTbls == null) ? Collections.EMPTY_LIST
				: contentsinstTbls;
	}
}
