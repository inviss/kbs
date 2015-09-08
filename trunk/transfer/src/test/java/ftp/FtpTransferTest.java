package ftp;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPClientInterface;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPProgressMonitor;
import com.enterprisedt.net.ftp.FTPTransferType;

public class FtpTransferTest {
	private int percent = 0;
	//@Ignore
	@Test
	public void testFtpUpload() {
		FTPClient ftp = new FTPClient();
		try {
			ftp.setRemoteHost("211.106.66.163"); //112.175.248.36
			ftp.setRemotePort(21); //7777
			ftp.setAutoPassiveIPSubstitution(false);
			ftp.setConnectMode(FTPConnectMode.ACTIVE);
			ftp.setDetectTransferMode(true);
			ftp.setStrictReturnCodes(false);
			ftp.setControlEncoding("euc-kr");

			ftp.connect();	
			ftp.login("vmvideo", "video!#%&"); //kbs-cms,kbs-cms!@#
			/*
			if(!ftp.existsDirectory("한글")) {
				ftp.mkdir("한글");
				ftp.chdir("한글");
			}else{
				ftp.chdir("한글");
			}
			*/
			ftp.mkdir("test");
			ftp.chdir("test");
			
			File f = new File("D:\\torrent\\down\\E46.120722.HDTV.H264.720p-WITH.mp4");
			for(int i=0; i<10; i++) {
				TestProgressMonitor monitor = new TestProgressMonitor(ftp, f.length());
				ftp.setProgressMonitor(monitor, 5000);

				//ftp.put("D:\\torrent\\download\\Videos\\Add_Chord.wmv", "Add_Chord.wmv");
				ftp.put("D:\\torrent\\down\\E46.120722.HDTV.H264.720p-WITH.mp4", "test-"+i+".mp4");
				ftp.delete("test-"+i+".mp4");
				
				monitor = new TestProgressMonitor(ftp, f.length());
				ftp.setProgressMonitor(monitor, 5000);
				
				ftp.put("D:\\torrent\\down\\E46.120722.HDTV.H264.720p-WITH.mp4", "test2-"+i+".mp4");
				Thread.sleep(1000);
				
				ftp.delete("test2-"+i+".mp4");
			}
			ftp.cdup();
			ftp.delete("test");
			ftp.quit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftp.connected()) {
                try {
					ftp.quitImmediately();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (FTPException e) {
					e.printStackTrace();
				}
            }
		}
	}

	class TestProgressMonitor implements FTPProgressMonitor {

		/**
		 * FTPClient reference
		 */
		private FTPClientInterface ftpClient;

		private FTPTransferType type;
		
		private long size;

		/**
		 * Constructor
		 * 
		 * @param ftp   FTP client reference
		 */
		public TestProgressMonitor(FTPClientInterface ftp, long size) {
			this.ftpClient = ftp;
			this.size = size;
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
			int i = (int)(((double)count / (double)size)*100);
			if(i != percent) {
				percent = i;
				System.out.println("Saving transfer type (count=" + percent + ")");
			}
			type = ftpClient.getType();		
		}            
	}

}
