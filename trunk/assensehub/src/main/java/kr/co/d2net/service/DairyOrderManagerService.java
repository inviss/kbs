package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dto.DairyOrderTbl;
import kr.co.d2net.dto.xml.meta.Nodes;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DairyOrderManagerService {

	/**
	 * <p>
	 * 일일운행정보 입력
	 * <p>
	 * 
	 * @param dairyOrderTbl
	 * @throws ServiceException
	 */
	public void insertDairyOrderService(DairyOrderTbl dairyOrderTbl)
			throws ServiceException;

	/**
	  * <p>
	 * 기존 일일운행정보 삭제
	 * <p>
	 * @param dairyOrderTbl
	 * @throws ServiceException
	 */
	public void deleteDaireyOrderservice(DairyOrderTbl dairyOrderTbl) throws ServiceException;

	/**
	 * <p>
	 * 일일운행정보 상세조회
	 * <p>
	 * 
	 * @param dairyOrderTbl
	 * @return
	 * @throws ServiceException
	 */
	public DairyOrderTbl getDairyOrderService(Map<String, Object> params)
			throws ServiceException;

	/**
	 * <p>
	 * 일일운행정보 조회
	 * <p>
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<DairyOrderTbl> findDairyOrderService(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 일일운행표 연동 (메타허브 연동)
	 * 
	 * @param xml
	 * @return
	 * @throws ServiceException
	 */
	public boolean connectToMetaHubRestFul_dairy(Map<String, Object> element)
			throws ServiceException;

	/**
	 * 일일운행표 정보 입력 (메타허브 연동)
	 * 
	 * @param xml
	 * @return
	 * @throws ServiceException
	 */
	public String forwardMetaXml(String xml, String confirmDate, String channel) throws ServiceException;
	
	public String forwardMetaXml(Nodes nodes, String confirmDate, String channel) throws ServiceException;

}
