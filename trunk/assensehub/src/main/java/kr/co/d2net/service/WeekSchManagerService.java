package kr.co.d2net.service;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dto.WeekSchTbl;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WeekSchManagerService {

	/**
	 * 주간편성표 상세조회
	 * 
	 * @param weekSchTbl
	 * @return
	 * @throws ServiceException
	 */
	
	public WeekSchTbl getWeekSchTbl(Map<String, Object> weekSchTbl)
			throws ServiceException;

	/**
	 * 주간편성표 정보 입력
	 * 
	 * @param weekSchTbl
	 * @throws ServiceException
	 */
	@Transactional
	public void insertWeekSchTbl(WeekSchTbl weekSchTbl) throws ServiceException;

	/**
	 * 주간편성표 정보 삭제
	 * @param weekSchTbl
	 * @throws ServiceException
	 */
	@Transactional
	public void deleteWeekSchTbl(WeekSchTbl weekSchTbl) throws ServiceException;
	
	/**
	 * 주간편성표 정보 조회
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<WeekSchTbl> findWeekSchTbl(Map<String, Object> params)
			throws ServiceException;

	/**
	 * 주간편성표 연동 (메타허브 연동)
	 * 
	 * @param xml
	 * @return
	 * @throws ServiceException
	 */
	public boolean connectToMetaHubRestFul_weekly(Map<String, Object> element)
			throws ServiceException;

	/**
	 * 주간편성표 연동 (메타허브 연동) - 수동갱신
	 * 
	 * @param xml
	 * @return
	 * @throws ServiceException
	 */
	public boolean connectToMetaHubRestFul_manualWeekly(Map<String, Object> element)
			throws ServiceException;
	
	/**
	 * 주간편성표 정보 입력 (메타허브 연동)
	 * 
	 * @param xml
	 * @return
	 * @throws ServiceException
	 */
	@Transactional
	public String forwardMetaXml(String xml, String confirmDate, String channel, String local) throws ServiceException;

}
