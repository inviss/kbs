package kr.co.d2net.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dao.BusiPartnerDao;
import kr.co.d2net.dto.BusiPartnerTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.Search;


@Service(value="busipartnerManagerService")
public class BusiPartnerManagerServiceImpl implements BusiPartnerManagerService {

	@Autowired
	private BusiPartnerDao busipartnerDao;
	
	@Override
	public BusiPartnerTbl getBusiPartner(Map<String, Object> params) throws ServiceException {
		return busipartnerDao.getBusiPartner(params);
	}
	
	@Override
	public BusiPartnerTbl getBusiPartnerId(Map<String, Object> params) throws ServiceException {
		return busipartnerDao.getBusiPartnerId(params);
	}

	@SuppressWarnings("unchecked")
	public List<BusiPartnerTbl> findBusiPartner(Map<String, Object> params) throws ServiceException {
		List<BusiPartnerTbl> busipartnerTbls = busipartnerDao.findBusiPartner(params);
		return (busipartnerTbls == null) ? Collections.EMPTY_LIST : busipartnerTbls;
	}

	@Override
	public void insertBusiPartner(BusiPartnerTbl busipartnerTbl) throws ServiceException {
		busipartnerDao.insertBusiPartner(busipartnerTbl);
	}

	@Override
	public void updateBusiPartner(BusiPartnerTbl busipartnerTbl) throws ServiceException {
		busipartnerDao.updateBusiPartner(busipartnerTbl);
	}
	
	@Override
	public void deleteBusiPartner(BusiPartnerTbl busipartnerTbl) throws ServiceException {
		busipartnerDao.deleteBusiPartner(busipartnerTbl);
	}
	
	@Override
	public PaginationSupport<BusiPartnerTbl> findBusiPartnerList(Search search) throws ServiceException {
		PaginationSupport<BusiPartnerTbl> busipartnerList = busipartnerDao.findbusipartnerList(search);

		return busipartnerList;
	}
}
