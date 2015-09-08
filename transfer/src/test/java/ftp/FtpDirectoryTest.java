package ftp;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPClientInterface;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPProgressMonitor;
import com.enterprisedt.net.ftp.FTPTransferType;

public class FtpDirectoryTest {

	@Test
	public void testDirectory() {
		try {
			String aa = " /aaa/bbb/ccc";
			if(StringUtils.isNotBlank(aa)) {
				aa = aa.trim();
				if(aa.length() != 1 && !aa.equals("/")) {
					if(aa.startsWith("/"))
						aa = aa.substring(1);
					if(aa.endsWith("/"))
						aa = aa.substring(0, aa.lastIndexOf("/"));

					String[] dirs = aa.split("\\/");
					for(String dir : dirs) {
						System.out.println(dir);
					}
				}
			} else {
				System.out.println("root");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testFtpUpload() {

		try {
			FTPClient ftp = new FTPClient();
			ftp.setRemoteHost("14.36.147.23"); //
			ftp.setRemotePort(21); //
			ftp.setAutoPassiveIPSubstitution(false);
			ftp.setConnectMode(FTPConnectMode.ACTIVE);
			ftp.setDetectTransferMode(true);
			ftp.setStrictReturnCodes(false);
			ftp.setControlEncoding("euc-kr");

			ftp.connect();	
			ftp.login("d2net", "elxnspt"); //,

			//FTPClientInterface client  = ftp;

			//TestProgressMonitor monitor = new TestProgressMonitor(client);
			//client.setProgressMonitor(monitor);

			//ftp.put("Y:\\201206\\29\\723941.mxf", "723941.mxf");

		} catch (FTPException fe) {
			System.out.println(fe.getReplyCode());
			fe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class TestProgressMonitor implements FTPProgressMonitor {

		/**
		 * FTPClient reference
		 */
		private FTPClientInterface ftpClient;

		private FTPTransferType type;

		/**
		 * Constructor
		 * 
		 * @param ftp   FTP client reference
		 */
		public TestProgressMonitor(FTPClientInterface ftp) {
			this.ftpClient = ftp;
		}

		public FTPTransferType getType() {
			return type;
		}

		public void setType(FTPTransferType type) {
			this.type = type;
		}

		/* (non-Javadoc)
		 * @see com.enterprisedt.net.ftp.FTPProgressMonitor#bytesTransferred(long)
		 */
		public void bytesTransferred(long count) {
			System.out.println("Saving transfer type (count=" + count + ")");
			type = ftpClient.getType();		
		}            
	}

}
