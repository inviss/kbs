package kr.co.d2net.service;

import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dto.LiveTbl;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LiveManagerService {

	/**
	 * 녹화정보 입력 및 수정
	 * 
	 * @param live
	 * @throws ServiceException
	 */
	public void updateLiveTbl(LiveTbl live) throws ServiceException;

	/**
	 * 녹화정보에 대한 조회
	 * 
	 * @param pgmId
	 * @return 녹화정보
	 * @throws ServiceException
	 */
	public LiveTbl getLiveTbl(Map<String, Object> params) throws ServiceException;

}
