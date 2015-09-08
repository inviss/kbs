package kr.co.d2net.ftp;

import kr.co.d2net.commons.dto.Job;


/*
 * (@)# FtpTransfer.java
 * ==================================================================
 * <<D2net>>., Software Licence, Version 1.0
 * 
 * Copyright (c) 1998-2009 <<디투넷>>.,
 * <<서울 영등포구 양평동 3가 16번지 우림이비즈센타 812>> * All reght reserved.
 * 
 * 이 소스의 저작권은 디투넷에 있습니다.
 * url: www.d2net.co.kr
 * ==================================================================
 */

/**
 * <p>
 * Transfer Manager로부터 Job을 전달받아 요청된 Ftp Server로 콘텐츠를  전달하거나 전송받는다.</br>
 * </p>
 */

public interface FtpTransferService {
	
	public void uploadFile(Job job) throws Exception;
	
	public void uploadXml(Job job) throws Exception;
	
}