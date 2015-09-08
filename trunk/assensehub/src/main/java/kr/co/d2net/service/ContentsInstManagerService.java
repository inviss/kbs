package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dto.ContentsInstTbl;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ContentsInstManagerService {

	/**
	 * 컨텐츠 인스턴스 정보 요청
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<ContentsInstTbl> findContentsInst(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 컨텐츠 인스턴스 요약정보 요청
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ContentsInstTbl> findContentsInstSummary(
			Map<String, Object> params) throws ServiceException;
	
	/**
	 * 컨텐츠 원본 요약정보 요청
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ContentsInstTbl> findContentsSummary(
			Map<String, Object> params) throws ServiceException;
	
	public List<ContentsInstTbl> findDelContents(
			Map<String, Object> params) throws ServiceException;

	/**
	 * RMS컨텐츠 인스턴스 요약정보 요청
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ContentsInstTbl> findContentsRms(
			Map<String, Object> params) throws ServiceException;

	/**
	 * 삭제 될 인스터스 정보 요청
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ContentsInstTbl> findDeleteContentsInst(
			Map<String, Object> params) throws ServiceException;

	/**
	 * 컨텐츠 인스턴스 상세정보 요청
	 * 
	 * @param
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentsInstTbl getContentsInst(Map<String, Object> params)
			throws ServiceException;


	/**
	 * 컨텐츠 인스턴스 정보 입력
	 * 
	 * @param contentsinstTbl
	 * @throws ServiceException
	 */
	public Long insertContentsInst(ContentsInstTbl contentsinstTbl)
			throws ServiceException;

	/**
	 * 콘텐츠 인스턴스 정보 삭제
	 * 
	 * @param contentsinstTbl
	 * @throws ServiceException
	 */
	public void deleteContentsInst(ContentsInstTbl contentsinstTbl) throws ServiceException;

	public void updateContentsInst(ContentsInstTbl contentsinstTbl) throws ServiceException;

	/**
	 * 다시보기 'URL' 연동에 따른 콘텐츠 및 인스턴스 정보 조회
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public ContentsInstTbl getContentsInstForwardMeta(Map<String, Object> params)
			throws ServiceException;


	/**
	 * 신규 스토리지(LIVE, VCR, 트리밍 용)에 따른 자동삭제 콘텐츠 리스트 조회
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<ContentsInstTbl> findDefaultDeleteContentsInst(Map<String,Object> params) throws ServiceException;
}
