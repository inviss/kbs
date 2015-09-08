package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.CodeTbl;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.Search;

public interface CodeManagerService {
	
	/**
	 * 코드 목록을 조회
	 * 
	 * @param 
	 * @throws ServiceException
	 */
	public List<CodeTbl> findCode(Map<String, Object> params) throws ServiceException;
	
	
	/**
	 * 단일 코드 조회
	 * 
	 * @param 
	 * @throws ServiceException
	 */
	public CodeTbl getCode(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 구분코드 조회
	 * 
	 * @param 
	 * @throws ServiceException
	 */
	public List<CodeTbl> findCodeClfCd(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 장비추가시 rmk2를 +1
	 * 
	 * @param 
	 * @throws ServiceException
	 */
	public void updateCode(CodeTbl codeTbl) throws ServiceException;
	
	/**
	 * 서비스에 사용할 파일명을 생성한다.
	 * @param contentsInstTbl
	 * @return
	 * @throws ServiceException
	 */
	public String getWrkFileName(ContentsTbl contentsTbl,ContentsInstTbl contentsInstTbl) throws ServiceException;
	
	public void insertCode(CodeTbl codeTbl) throws ServiceException;
	
	public PaginationSupport<CodeTbl> findCodeListView(Search search) throws ServiceException;
	
	/**
	 * 동영상 네임밍 체계에 따른 수정 순번 체번.
	 * @param contentsTbl
	 * @return
	 * @throws ServiceException
	 */
	public int getContentsPgmNum(ContentsTbl contentsTbl) throws ServiceException;
}
