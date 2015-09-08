package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.Search;
import kr.co.d2net.dto.TransferHisTbl;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ContentsManagerService {

	/**
	 * 콘텐츠리스트
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ContentsTbl> findContents(Map<String, Object> params)
			throws ServiceException;
	
	/**
	 * 콘텐츠 prgrs리스트
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ContentsTbl> getContentsPrgrs(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 콘텐츠 단일건 조회
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public ContentsTbl getContents(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 콘텐츠 정보 등록
	 * 
	 * @param contentsTbl
	 * @throws ServiceException
	 */
	public void insertContents(ContentsTbl contentsTbl,
			ContentsInstTbl contentsInstTbl) throws ServiceException;
	
	/**
	 * RMS콘텐츠 정보 수정
	 * 
	 * @param contentsTbl
	 * @throws ServiceException
	 */
	public void updateContentsRms(ContentsTbl contentsTbl) throws ServiceException;

	/**
	 * 콘텐츠 삭제
	 * 
	 * @param contentsTbl
	 * @throws ServiceException
	 */
	public void deleteContents(ContentsTbl contentsTbl) throws ServiceException;

	/**
	 * 서비스 콘텐츠 정보 조회(페이징)
	 * 
	 * @param search
	 * @return
	 * @throws ServiceException
	 */
	public PaginationSupport<ContentsTbl> findContentsList(Search search)
			throws ServiceException;
	
	/**
	 * 서비스 콘텐츠 정보 조회(페이징)
	 * 
	 * @param search
	 * @return
	 * @throws ServiceException
	 */
	public PaginationSupport<ContentsTbl> findStandbycontentsList(Search search)
			throws ServiceException;
	
	/**
	 * 보관 콘텐츠 정보 조회(페이징)
	 * 
	 * @param search
	 * @return
	 * @throws ServiceException
	 */
	public PaginationSupport<ContentsTbl> findRmscontentsList(Search search)
			throws ServiceException;
	
	/**
	 * 보관 콘텐츠 정보 조회(페이징)
	 * 
	 * @param search
	 * @return
	 * @throws ServiceException
	 */
	public PaginationSupport<ContentsTbl> findRmscontentsUd(Search search)
			throws ServiceException;

	/**
	 * 콘텐츠 정보 수정
	 * 
	 * @param contentsTbl
	 * @throws ServiceException
	 */
	public void updateContents(ContentsTbl contentsTbl) throws ServiceException;

	/**
	 * 콘텐츠 아이디 발급
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public int getCtId() throws ServiceException;
	
	public ContentsTbl getRmsContentsCount(Map<String, Object> params) throws ServiceException;

	
	public ContentsTbl getContents2(Map<String, Object> params) throws ServiceException;
	
	public ContentsTbl getContents3(Map<String, Object> params) throws ServiceException;

	public ContentsTbl getContents4(Map<String, Object> params) throws ServiceException;
	
	public void insertContents(ContentsTbl contentsTbl) throws ServiceException;
	
	public void updateContents2(ContentsTbl contentsTbl) throws ServiceException;
	
	public void archiveXMLPasing(String xml, ContentsTbl contentsTbl, ContentsInstTbl contentsInstTbl) throws ServiceException;
	
	public String createContentML(String pgmId, Integer proFlid) throws ServiceException;
	
	public String createSMIL(List<TransferHisTbl> transferHisTbls) throws ServiceException;
}
