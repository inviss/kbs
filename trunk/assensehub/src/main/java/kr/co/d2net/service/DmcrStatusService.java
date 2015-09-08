package kr.co.d2net.service;


public interface DmcrStatusService {
	public void getJobStatus(Long ctId, int jobID) throws java.rmi.RemoteException;
}
