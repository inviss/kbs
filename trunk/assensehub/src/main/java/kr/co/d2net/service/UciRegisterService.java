package kr.co.d2net.service;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dto.TransferHisTbl;

public interface UciRegisterService {
	
	/**
	 * 하나의 프로파일 영상을 사업자에게 모두 전송하고 나면 전송된 영상에대해 UCI를 등록해야 함. 
	 * @param transferHisTbl
	 * @throws ServiceException
	 */
	public void addRegiester(TransferHisTbl transferHisTbl) throws ServiceException;
}
