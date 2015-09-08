package kr.co.d2net.soap;

import java.rmi.RemoteException;

import javax.jws.WebService;

@WebService
public interface Navigator {
	public String soapTest(String xml) throws RemoteException;
	
	/**
	 * 요청시 원본파일을 watcher 폴더에 복사하는 기능
	 * @param xml (target 폴더경로, 원본자막경로, 동영상경로, PID, 업로드 파일명)
	 * @return 성공여부(S: 성공, F: 실패)
	 * @throws RemoteException
	 */
	public String revise(String xml) throws RemoteException;
	
	/**
	 * watcher 폴더에서 특정 시스템에 자막을 업로드함.
	 * 업로드시 원본파일명을 업로드파일명으로 교체해서 보내야 함.
	 * @param xml (보정자막경로, 업로드 시스템, PID, 업로드 파일명)
	 * @return 성공여부(S: 성공, F: 실패)
	 * @throws RemoteException
	 */
	public String upload(String xml) throws RemoteException;
}
