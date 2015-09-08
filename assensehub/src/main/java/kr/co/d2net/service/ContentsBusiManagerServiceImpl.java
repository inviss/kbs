package kr.co.d2net.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dao.ContentsBusiDao;
import kr.co.d2net.dto.ContentsBusiTbl;


@Service(value="contentsbusiManagerService")
public class ContentsBusiManagerServiceImpl implements ContentsBusiManagerService {
	
	private Log logger = LogFactory.getLog(ContentsBusiManagerServiceImpl.class);

	@Autowired
	private ContentsBusiDao contentsbusiDao;
	
	@Override
	public ContentsBusiTbl getContentsBusi(Map<String, Object> params) throws ServiceException {
		return contentsbusiDao.getContentsBusi(params);
	}

	@SuppressWarnings("unchecked")
	public List<ContentsBusiTbl> findContentsBusi(Map<String, Object> params) throws ServiceException {
		List<ContentsBusiTbl> contentsbusiTbls = contentsbusiDao.findContentsBusi(params);
		return (contentsbusiTbls == null) ? Collections.EMPTY_LIST : contentsbusiTbls;
	}

	@Override
	public void insertContentsBusi(ContentsBusiTbl contentsbusiTbl) throws ServiceException {
		contentsbusiDao.insertContentsBusi(contentsbusiTbl);
	}

}
