package kr.co.d2net.commons.exceptions;

import org.springframework.core.ErrorCoded;

public class ApplicationException extends Exception implements ErrorCoded {

	private static final long serialVersionUID = 1L;

	public String getErrorCode() {
		return getErrorCode();
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}
}
