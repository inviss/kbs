/*
 * (@)# RequiredFieldException.java
 * ==================================================================
 * <<D2net>>., Software Licence, Version 1.0
 * 
 * Copyright (c) 1998-2009 <<디투넷>>.,
 * <<서울 영등포구 양평동 3가 16번지 우림이비즈센타 812>> * All reght reserved.
 * 
 * 이 소스의 저작권은 디투넷에 있습니다.
 * url: http://www.d2net.co.kr
 * ==================================================================
 */
package kr.co.d2net.commons.exceptions;


/**
 *
 * @author Kang Myeong Seong
 * @version 1.0
 */

public class XmlWriteException extends BaseApplicationException {

	private static final long serialVersionUID = 1L;

	public XmlWriteException(String errorCode) {
		super(errorCode);
	}
	
	public XmlWriteException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
	public XmlWriteException(String errorCode, String errorMessage, String xml) {
		super(errorCode, errorMessage, xml);
	}
	
	public XmlWriteException(String errorCode, String errorMessage, String xml, String[] args) {
		super(errorCode, errorMessage, xml, args);
	}
	
	public XmlWriteException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}
	
	public XmlWriteException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
	
	public XmlWriteException(String errorCode, String message, Throwable cause, String xml) {
		super(errorCode, message, cause, xml);
	}
	
}
