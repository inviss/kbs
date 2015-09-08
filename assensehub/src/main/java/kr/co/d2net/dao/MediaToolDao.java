package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.xml.internal.FindList;
import kr.co.d2net.dto.xml.internal.Workflow;

public interface MediaToolDao {

	/**
	 * 주간 편성표 조회
	 * 
	 * @param xml
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public String connectToMetaHubRestFul_weekly(String xml)
			throws DaoNonRollbackException;

	/**
	 * 일일운행표 조회
	 * 
	 * @param xml
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public String connectToMetaHubRestFul_dairy(String xml)
			throws DaoNonRollbackException;

	/**
	 * 프로그램 정보 조회 ( 회차단위)
	 * 
	 * @param xml
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public FindList connectToMetaHubRestFul_programNum(Workflow workflow)
			throws DaoNonRollbackException;

	/**
	 * 세그먼트 정보 조회
	 * 
	 * @param xml
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public FindList connectToMetaHubRestFul_segment(Workflow workflow)
			throws DaoNonRollbackException;
	
	/**
	 * 세그먼트 정보 조회 라디오
	 * 
	 * @param xml
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public FindList connectToMetaHubRestFul_segment_radio(Workflow workflow)
			throws DaoNonRollbackException;

	/**
	 * 콘텐츠 정보 조회
	 * 
	 * @param workflow
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public FindList findContentsInfo(Workflow workflow)
			throws DaoNonRollbackException;

	/**
	 * 미디어툴에 전달할 프로그램 정보 정제
	 * 
	 * @param xml
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public FindList forwardProgramInfo(String xml)
			throws DaoNonRollbackException;
	
	/**
	 * CDN 전달 XML생성을 위한 프로그램 정보
	 * @param xml
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public Map<String,Object> forwardContentsMLNodeInfo(String xml) throws DaoNonRollbackException;
	
	/**
	 * CDN 전달 XML생성을 위한 프로그램 정보
	 * @param xml
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public Map<String,Object> forwardContentsMLNodesInfo(String xml) throws DaoNonRollbackException;
	
	/**
	 * 메타허브 XML 정보를 Map에 셋.
	 * @param xml 메타허브 XML
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public Map<String,Object> forwardMetaInfo(String xml) throws DaoNonRollbackException;
	
	/**
	 * 프로그램정보 검색(메타허브 연동)
	 * @param xml 메타허브 xml
	 * @return List<ContentsTbl>
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsTbl> forwardProgramsInfo(String xml) throws DaoNonRollbackException;
	
	/**
	 * 미디어툴에 전달할 세그먼트 정보 정제
	 * 
	 * @param xml
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public FindList forwardSegmentInfo(String xml)
			throws DaoNonRollbackException;
	
	/**
	 * 미디어툴에 전달할 세그먼트 정보 정제
	 * 
	 * @param xml
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsTbl> forwardSegmentsInfo(String xml)
			throws DaoNonRollbackException;

	/**
	 * 인물정보 검색-프로그램코드에 해당하는 인물검색(메타허브 연동)
	 * @param xml
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsTbl> forwardPeopleInfo(String xml) throws DaoNonRollbackException;
	
	/**
	 * 인물정보 검색-인물명으로 인물검색(메타허브 연동)
	 * @param xml
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsTbl> forwardSearchPeopleInfo(String xml) throws DaoNonRollbackException;
}
