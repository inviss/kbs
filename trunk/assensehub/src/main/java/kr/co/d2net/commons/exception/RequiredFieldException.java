package kr.co.d2net.commons.exception;


/**
 * <pre>
 * XML Element에서 필수 요소값이 없을경우 반환하는 에러
 * </pre>
 * @author Administrator
 *
 */
public class RequiredFieldException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public RequiredFieldException(String message) {
		super(message);
	}

	public RequiredFieldException(Throwable cause) {
		super(cause);
	}

	public RequiredFieldException(String errorCode, String message) {
		super(errorCode, message);
	}

	public RequiredFieldException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RequiredFieldException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
}
