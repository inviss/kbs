package kr.co.d2net.commons.exceptions;



public class DaoNonRollbackException extends ServiceException {
	private static final long serialVersionUID = 1L;

	public DaoNonRollbackException(String errorCode) {
		super(errorCode);
	}
	
	public DaoNonRollbackException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
	public DaoNonRollbackException(String errorCode, String errorMessage, String xml) {
		super(errorCode, errorMessage, xml);
	}
	
	public DaoNonRollbackException(String errorCode, String errorMessage, String[] args) {
		super(errorCode, errorMessage, args);
	}
	
	public DaoNonRollbackException(String errorCode, String errorMessage, String xml, String[] args) {
		super(errorCode, errorMessage, xml, args);
	}
	
	public DaoNonRollbackException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}
	
	public DaoNonRollbackException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
	
	public DaoNonRollbackException(String errorCode, String message, Throwable cause, String[] args) {
		super(errorCode, message, cause, args);
	}
	
	public DaoNonRollbackException(String errorCode, String message, Throwable cause, String xml) {
		super(errorCode, message, cause, xml);
	}
}
