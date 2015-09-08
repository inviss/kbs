package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.xml.internal.FindList;
import kr.co.d2net.dto.xml.internal.Workflow;

public interface MediaToolInterfaceService {

	/**
	 * 메티허브를 통한 미디어 정보를 조회
	 * 
	 * @param xml
	 * @return
	 * @throws ServiceException
	 */
	public FindList reqMediaInfo(Workflow workflow) throws ServiceException;

	/**
	 * 미디어 툴에서 콘텐츠 등록을 요청
	 * 
	 * @param xml
	 * @return
	 * @throws ServiceException
	 */
	public String insertContentsInfo(String xml) throws ServiceException;
	
	/**
	 * 메타허브 XML 정보를 Map 으로 변환(Node list)
	 * @param xml 메타허브 xml
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> forwardMetaInfo(String xml) throws ServiceException;
	
	/**
	 * 메타허브 XML 정보를 Map 으로 변환(Node info)
	 * @param xml 메타허브 xml
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> forwardContentsMLNodeInfo(String xml)	throws ServiceException;
	
	/**
	 * 메타허브 XML 정보를 Get List
	 * @param xml
	 * @return
	 * @throws ServiceException
	 */
	public List<ContentsTbl>	forwardPeopleInfo(String xml) throws ServiceException;
	
	/**
	 * 메타허브 XML 정보를 Get List
	 * @param xml
	 * @return
	 * @throws ServiceException
	 */
	public List<ContentsTbl>	forwardSearchPeopleInfo(String xml) throws ServiceException;
	
	/**
	 * 
	 * @param xml
	 * @return
	 * @throws ServiceException
	 */
	public List<ContentsTbl> forwardSegmentsInfo(String xml)
			throws ServiceException;
	
	/**
	 * 프로그램정보 검색(메타허브 연동)
	 * @param xml 메타허브 xml
	 * @return List<ContentsTbl>
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsTbl> forwardProgramsInfo(String xml) throws ServiceException;

}
