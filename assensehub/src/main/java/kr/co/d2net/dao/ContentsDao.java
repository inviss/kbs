package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.Search;

public interface ContentsDao {

	/**
	 * 콘텐츠리스트
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsTbl> findContents(Map<String, Object> params)
			throws DaoNonRollbackException;
	
	/**
	 * 콘텐츠 prges 리스트
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsTbl> getContentsPrgrs(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * 콘텐츠조회
	 * 
	 * @param brdDd
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentsTbl getContents(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * 콘텐츠 정보 입력
	 * 
	 * @param contentsTbl
	 * @throws DaoRollbackException
	 */
	public Long insertContents(ContentsTbl contentsTbl)
			throws DaoRollbackException;

	/**
	 * RMS콘텐츠 정보 수정
	 * 
	 * @param contentsTbl
	 * @throws DaoRollbackException
	 */
	public Long updateContentsRms(ContentsTbl contentsTbl)
			throws DaoRollbackException;
	
	/**
	 * 콘텐츠 정보 삭제
	 * 
	 * @param contentsTbl
	 * @throws DaoRollbackException
	 */
	public void deleteContents(ContentsTbl contentsTbl)
			throws DaoRollbackException;

	/**
	 * <p>
	 * 서비스 콘텐츠 요청 목록 조회. 페이징 처리가 필요한 메소드에 Spring JDBC를 이용하여 처리한다.
	 * </p>
	 * 
	 * @param Search
	 *            : 사용자가 요청한 검색조건을 담은 bean
	 * @return PaginationSupport
	 * @throws DaoNonRollbackException
	 */
	public PaginationSupport<ContentsTbl> findcontentsList(Search search)
			throws DaoNonRollbackException;
	
	/**
	 * <p>
	 * 대기 콘텐츠 요청 목록 조회. 페이징 처리가 필요한 메소드에 Spring JDBC를 이용하여 처리한다.
	 * </p>
	 * 
	 * @param Search
	 *            : 사용자가 요청한 검색조건을 담은 bean
	 * @return PaginationSupport
	 * @throws DaoNonRollbackException
	 */
	public PaginationSupport<ContentsTbl> findstandbycontentsList(Search search)
			throws DaoNonRollbackException;
	
	/**
	 * <p>
	 * 보관 콘텐츠 요청 목록 조회. 페이징 처리가 필요한 메소드에 Spring JDBC를 이용하여 처리한다.
	 * </p>
	 * 
	 * @param Search
	 *            : 사용자가 요청한 검색조건을 담은 bean
	 * @return PaginationSupport
	 * @throws DaoNonRollbackException
	 */
	public PaginationSupport<ContentsTbl> findrmscontentsList(Search search)
			throws DaoNonRollbackException;
	
	/**
	 * <p>
	 * 보관 콘텐츠 요청 목록 조회. 페이징 처리가 필요한 메소드에 Spring JDBC를 이용하여 처리한다.
	 * </p>
	 * 
	 * @param Search
	 *            : 사용자가 요청한 검색조건을 담은 bean
	 * @return PaginationSupport
	 * @throws DaoNonRollbackException
	 */
	public PaginationSupport<ContentsTbl> findrmscontentsUd(Search search)
			throws DaoNonRollbackException;

	/**
	 * 콘텐츠 정보 수정.
	 * 
	 * @param contentsTbl
	 * @throws DaoRollbackException
	 */
	public void updateContentsInfo(ContentsTbl contentsTbl)
			throws DaoRollbackException;

	/**
	 * 콘텐츠 아이디 발급.
	 * 
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public int getCtId() throws DaoNonRollbackException;
	
	/**
	 * rms 콘텐츠 count.
	 * 
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentsTbl getRmsContentsCount(Map<String, Object> params) throws DaoNonRollbackException;

	/**
	 * 폐기현황 상세보기
	 * 
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentsTbl getContents2(Map<String, Object> params)
			throws DaoNonRollbackException;
	
	/**
	 * 폐기검색, 폐기연장 상세보기
	 * 
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentsTbl getContents3(Map<String, Object> params)
	throws DaoNonRollbackException;
	
	/**
	 * RMS 상세보기
	 * 
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentsTbl getContents4(Map<String, Object> params)
	throws DaoNonRollbackException;

	public void updateContents2(ContentsTbl contentsTbl)
			throws DaoRollbackException;
	
	/**
	 * 동영상 네임밍 체계에 따른 수정 순번 체번.
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentsTbl getContentsPgmNum(Map<String, Object> params) throws DaoNonRollbackException;

}
