package kr.co.d2net.commons.exceptions;


public class ServiceException extends BaseApplicationException {
	private static final long serialVersionUID = 1L;

	public ServiceException(String errorCode) {
		super(errorCode);
	}
	
	public ServiceException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
	public ServiceException(String errorCode, String errorMessage, String xml) {
		super(errorCode, errorMessage, xml);
	}
	
	public ServiceException(String errorCode, String errorMessage, String[] args) {
		super(errorCode, errorMessage, args);
	}
	
	public ServiceException(String errorCode, String errorMessage, String xml, String[] args) {
		super(errorCode, errorMessage, xml, args);
	}
	
	public ServiceException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}
	
	public ServiceException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
	
	public ServiceException(String errorCode, String message, Throwable cause, String[] args) {
		super(errorCode, message, cause, args);
	}
	
	public ServiceException(String errorCode, String message, Throwable cause, String xml) {
		super(errorCode, message, cause, xml);
	}
	
}
