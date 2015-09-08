package kr.co.d2net.test;

import org.junit.Ignore;
import org.junit.Test;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;


public class test {

	@Test
	@Ignore
	public void test(){

		String storageDrive = "W,X,Y";

		String tmpDrive[] = storageDrive.split(",");

		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.setRemoteHost("192.168.0.112"); //
			ftpClient.setRemotePort(21); //
			ftpClient.setAutoPassiveIPSubstitution(false);

			//			if(StringUtils.defaultString(job.getMethod(), "P").toLowerCase().startsWith("p"))
			//				ftpClient.setConnectMode(FTPConnectMode.PASV);
			//			else
			ftpClient.setConnectMode(FTPConnectMode.ACTIVE);

			ftpClient.setDetectTransferMode(true);
			ftpClient.setStrictReturnCodes(false);
			ftpClient.setControlEncoding("euc-kr");

			ftpClient.connect();
			ftpClient.login("d2net", "elxnspt");

			if(!ftpClient.existsDirectory("/mp4")){
				ftpClient.mkdir("/mp4");
				ftpClient.chdir("/mp4");
			}



			String tmpFolder = "c:/Wildlife.wmv";
			String tmpFileNm = "/mp4/Wildlife.wmv";

			ftpClient.put(tmpFolder, tmpFileNm);


		} catch (Exception e) {
			System.out.println("ftp login error" + e);
		}

	}


}
