package kr.co.d2net.commons.utils;

import kr.co.d2net.commons.dto.Workflow;
import kr.co.d2net.commons.exceptions.ServiceException;

public interface CmsRequestService {
	
	public String saveStatus(String xml) throws ServiceException;
	
	public String saveStatus(Workflow workflow) throws ServiceException;
	
	public String saveContents(String xml) throws ServiceException;
	
	public String findTransfers(String xml) throws ServiceException;
}
