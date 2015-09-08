package kr.co.d2net.service;

import kr.co.d2net.dto.Transfer;

public interface FtpClientService {
	public void upload(Transfer transfer) throws Exception;
}
