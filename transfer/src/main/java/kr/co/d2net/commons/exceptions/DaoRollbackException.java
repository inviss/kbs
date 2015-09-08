package kr.co.d2net.commons.exceptions;


public class DaoRollbackException extends ServiceException {

	private static final long serialVersionUID = 1L;
	
	public DaoRollbackException(String errorCode) {
		super(errorCode);
	}
	
	public DaoRollbackException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
	public DaoRollbackException(String errorCode, String errorMessage, String xml) {
		super(errorCode, errorMessage, xml);
	}
	
	public DaoRollbackException(String errorCode, String errorMessage, String[] args) {
		super(errorCode, errorMessage, args);
	}
	
	public DaoRollbackException(String errorCode, String errorMessage, String xml, String[] args) {
		super(errorCode, errorMessage, xml, args);
	}
	
	public DaoRollbackException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}
	
	public DaoRollbackException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
	
	public DaoRollbackException(String errorCode, String message, Throwable cause, String[] args) {
		super(errorCode, message, cause, args);
	}
	
	public DaoRollbackException(String errorCode, String message, Throwable cause, String xml) {
		super(errorCode, message, cause, xml);
	}
	
}
