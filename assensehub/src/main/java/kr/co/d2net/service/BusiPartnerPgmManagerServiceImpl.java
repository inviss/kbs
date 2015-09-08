package kr.co.d2net.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dao.BusiPartnerPgmDao;
import kr.co.d2net.dto.BusiPartnerPgm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "busipartnerpgmManagerService")
public class BusiPartnerPgmManagerServiceImpl implements
		BusiPartnerPgmManagerService {

	@Autowired
	private BusiPartnerPgmDao busipartnerpgmDao;

	@Override
	public BusiPartnerPgm getBusiPartnerPgm(Map<String, Object> params)
			throws ServiceException {
		return busipartnerpgmDao.getBusiPartnerPgm(params);
	}
	
	@Override
	public String getPgmAudioMode(String pgmCode, String ctTyp)
			throws ServiceException {
		return busipartnerpgmDao.getPgmAudioMode(pgmCode,ctTyp);
	}

	@SuppressWarnings("unchecked")
	public List<BusiPartnerPgm> findBusiPartnerPgm(Map<String, Object> params)
			throws ServiceException {
		List<BusiPartnerPgm> busipartnerPgms = busipartnerpgmDao
				.findBusiPartnerPgm(params);
		return (busipartnerPgms == null) ? Collections.EMPTY_LIST
				: busipartnerPgms;
	}

	@Override
	public void insertBusiPartnerPgm(BusiPartnerPgm busipartnerPgm)
			throws ServiceException {
		busipartnerpgmDao.insertBusiPartnerPgm(busipartnerPgm);
	}

	@Override
	public void deleteBusiPartnerPgm(BusiPartnerPgm busipartnerPgm)
			throws ServiceException {
		busipartnerpgmDao.deleteBusiPartnerPgm(busipartnerPgm);

	}

}
