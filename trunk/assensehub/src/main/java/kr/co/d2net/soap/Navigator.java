package kr.co.d2net.soap;

import java.rmi.RemoteException;

import javax.jws.WebService;

@WebService
public interface Navigator {
	/**
	 * 인터페이스 연결 테스트
	 * 
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String soapTest(String xml) throws RemoteException;

	/**
	 * 클라이언트 현재 상태를 저장
	 * 트랜스코더, 트랜스퍼에서 현재상태(W[대기], B[진행], E[에러], C[완료]) 등을 받는다.
	 * W 상태일경우 Queue에 작업 요청된 데이타가 있을경우 작업을 가져와서 클라이언트에 반환해준다.
	 * 
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String saveStatus(String xml) throws RemoteException;

	/**
	 * 일일운행표를 반환한다.
	 * 
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String findDailySchedule(String xml) throws RemoteException;

	/**
	 * [TRIM, VCR, Live] 인제스트 및 트리밍에서 생성된 컨텐츠의 메타정보를 저장한다.
	 * 
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String saveContents(String xml) throws RemoteException;

	/**
	 * 트랜스코딩된 결과를 저장하고 QC Report 경로를 받아서 txt 정보를 얻는다.
	 * 
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String saveReports(String xml) throws RemoteException;

	/**
	 * 프로그램, 세그먼트, 컨텐츠 목록을 조회
	 * <p>
	 * gubun값의 인자에 따라 검색 분류를 나뉜다. P: 프로그램 조회, S: 세그먼트 조회, C: 컨텐츠 조회
	 * </p>
	 * 
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String findContents(String xml) throws RemoteException;

	/**
	 * 파일인제스트에서 MNC를 통해서 전달되는 영상정보 저장
	 * 
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String saveFileIngest(String xml) throws RemoteException;
	
	/**
	 * 트랜스퍼 [대기, 완료] 리스트를 조회한다.
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String findTransfers(String xml) throws RemoteException;

}
