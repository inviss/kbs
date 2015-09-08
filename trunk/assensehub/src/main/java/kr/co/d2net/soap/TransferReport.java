package kr.co.d2net.soap;

import java.rmi.RemoteException;

import javax.jws.WebService;

@WebService
public interface TransferReport {
	public String jobStatus(String caller, String jobId, String pgmCd, String pgmId, String status) throws RemoteException;
}
