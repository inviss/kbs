package kr.co.d2net.service;

import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dao.LiveDao;
import kr.co.d2net.dto.LiveTbl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "liveManagerService")
public class LiveManagerServiceImpl implements LiveManagerService {

	@Autowired
	private LiveDao liveDao;

	@Override
	public void updateLiveTbl(LiveTbl liveTbl) throws ServiceException {

		if (StringUtils.isNotBlank(liveTbl.getProgramCode())) {
			liveDao.deleteLive(liveTbl);
			liveDao.insertLive(liveTbl);
		}
	}

	@Override
	public LiveTbl getLiveTbl(Map<String, Object> params) throws ServiceException {

		return liveDao.getLive(params);
	}
}
