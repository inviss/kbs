package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dao.CodeDao;
import kr.co.d2net.dao.MediaToolDao;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.xml.internal.FindList;
import kr.co.d2net.dto.xml.internal.Workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "MediaToolInterfaceService")
public class MediaToolInterfaceServiceImpl implements MediaToolInterfaceService {

	private static Log logger = LogFactory
			.getLog(MediaToolInterfaceServiceImpl.class);

	@Autowired
	private MediaToolDao mediaTooldao;
	@Autowired
	private CodeDao codeDao;

	@Override
	public FindList reqMediaInfo(Workflow workflow) throws ServiceException {

		FindList findList = null;
		char gubun = workflow.getGubun().charAt(0);

		switch (gubun) {
		case 'P':  // 프로그램 조회
			findList = mediaTooldao.connectToMetaHubRestFul_programNum(workflow);
			break;
		case 'S':  // 세스먼트 조회
			findList = mediaTooldao.connectToMetaHubRestFul_segment(workflow);
			break;
		
		case 'A':  // 세스먼트 조회
			findList = mediaTooldao.connectToMetaHubRestFul_segment_radio(workflow);
			break;	
			
		case 'C':  // 콘텐츠
			findList = mediaTooldao.findContentsInfo(workflow);
			break;
		case 'I':  // 코드
			findList = codeDao.findCodeList(workflow);
			break;
		default:
			break;
		}

		return findList;
	}

	@Override
	public String insertContentsInfo(String xml) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> forwardMetaInfo(String xml)
			throws ServiceException {
		return mediaTooldao.forwardMetaInfo(xml);
	}

	@Override
	public Map<String, Object> forwardContentsMLNodeInfo(String xml)
			throws ServiceException {
		return mediaTooldao.forwardContentsMLNodeInfo(xml);
	}

	@Override
	public List<ContentsTbl> forwardPeopleInfo(String xml)
			throws ServiceException {
		// TODO Auto-generated method stub
		return mediaTooldao.forwardPeopleInfo( xml);
	}

	@Override
	public List<ContentsTbl> forwardSearchPeopleInfo(String xml)
			throws ServiceException {
		// TODO Auto-generated method stub
		return mediaTooldao.forwardSearchPeopleInfo( xml);
	}
	
	@Override
	public List<ContentsTbl> forwardSegmentsInfo(String xml)
			throws ServiceException {
		// TODO Auto-generated method stub
		return mediaTooldao.forwardSegmentsInfo( xml);
	}

	@Override
	public List<ContentsTbl> forwardProgramsInfo(String xml)
			throws ServiceException {
		// TODO Auto-generated method stub
		return mediaTooldao.forwardProgramsInfo( xml);
	}

}
