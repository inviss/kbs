package kr.co.d2net.service;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import kr.co.d2net.dto.Transfer;
import kr.co.d2net.exception.TransferException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferCancelledException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class FtpClientServiceImpl implements FtpClientService {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MessageSource messageSource;
	
	
	
	
	/**
	 *FTP를 이용해 smi 파일을 upload하는 method.
	 */
	@Override
	public void upload(Transfer transfer) throws Exception {
		
		FTPClient ftpClient = ftpConnect(transfer);
		
		if(ftpClient.connected()){
			logger.debug("FTP connect");
		}else{
			logger.debug("FTP not connect");
		}
		
		
		try {

			if(logger.isDebugEnabled()) {
				logger.debug("local file path: "+transfer.getFilePath());
			}

			File f = new File(transfer.getFilePath());
			if(f.exists()) {
				transfer.setFileSize(f.length());

				/* 원격지에 저장할 디렉토리 생성 */
				String remoteDir = "";
				if(StringUtils.isNotBlank(transfer.getRemoteDir())) {
					remoteDir = transfer.getRemoteDir().trim();
					if(remoteDir.length() != 1 && !remoteDir.equals("/")) {
						if(remoteDir.startsWith("/"))
							remoteDir = remoteDir.substring(1);
						if(remoteDir.endsWith("/"))
							remoteDir = remoteDir.substring(0, remoteDir.lastIndexOf("/"));

						if(remoteDir.indexOf("/") > -1) {
							String[] dirs = remoteDir.split("\\/");
							for(String dir : dirs) {
								if(!ftpClient.existsDirectory(dir)) {
									ftpClient.mkdir(dir);
									ftpClient.chdir(dir);
								}else{
									ftpClient.chdir(dir);
								}
							}
						}else{
							if(!ftpClient.existsDirectory(remoteDir)) {
								ftpClient.mkdir(remoteDir);
								ftpClient.chdir(remoteDir);
							}else{
								ftpClient.chdir(remoteDir);
							}
						}
					}
				}

				String tmpFilePath = transfer.getFilePath().replaceAll("\\\\", "/");
				String tmpFileNm = tmpFilePath.substring(tmpFilePath.lastIndexOf("/") + 1);
				
				//한글 파일 이름 관련 인코딩 설정.
				//ftpClient.setControlEncoding("euc-kr");
				//ftpClient.put(f.getAbsolutePath(), transfer.getWrkFileNm()+"."+transfer.getFileExt(), false); // append false
				ftpClient.put(f.getAbsolutePath(), tmpFileNm, false); // append false
				long remoteSize = ftpClient.size(tmpFileNm);

				if(transfer.getFileSize() != remoteSize) {
					if(logger.isInfoEnabled()) {
						logger.info("local size: "+transfer.getFileSize()+", remote file size: "+remoteSize);
					}
					ftpClient.delete(transfer.getWrkFileNm());
					// Remote Write Error
					throw new TransferException('6', "Local and Remote file's size are different", null);
				}

				ftpClient.quit();
			} else throw new TransferException('4', "File Not Found", null);
		} catch (IOException e) {
			if(e instanceof UnknownHostException) {
				throw new TransferException('1', e);
			} else if(e instanceof SocketTimeoutException) {
				// Remote Read Error
				throw new TransferException('2', e);
			} else if(e instanceof SocketException) {
				// Remote Write Error
				throw new TransferException('3', e);
			}
		} catch (FTPException e) {
			if(e instanceof FTPTransferCancelledException) {
				// Transfer Canceled
				throw new TransferException('8', e);
			} else throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(ftpClient != null) {
				if (ftpClient.connected()) {
					ftpClient.quitImmediately();
				}
			}
			ftpClient = null;
		}
		
		//return status;
	}
	
	
	
	
	/**
	 * FTP 연결하는 method.
	 * @param transfer
	 * @return
	 * @throws Exception
	 */
	private FTPClient ftpConnect(Transfer transfer) throws Exception {
		FTPClient ftpClient = new FTPClient();

		try {
			ftpClient.setRemoteHost(transfer.getFtpIp());
			ftpClient.setRemotePort(transfer.getFtpPort());
			ftpClient.setAutoPassiveIPSubstitution(false);

			if(StringUtils.defaultString(transfer.getConnectMode(), "P").toLowerCase().startsWith("p"))
				ftpClient.setConnectMode(FTPConnectMode.PASV);
			else
				ftpClient.setConnectMode(FTPConnectMode.ACTIVE);

			ftpClient.setDetectTransferMode(true);
			ftpClient.setStrictReturnCodes(false);
			ftpClient.setControlEncoding("euc-kr");
			//ftpClient.setControlEncoding("utf-8");

			ftpClient.connect();
			ftpClient.login(transfer.getUserId(), transfer.getUserPass());
			
			if(StringUtils.defaultString(transfer.getTransferMode(), "B").toLowerCase().startsWith("b"))
				ftpClient.setType(FTPTransferType.BINARY);
			else
				ftpClient.setType(FTPTransferType.ASCII);
		} catch (ConnectException e) {
			throw new TransferException('0', e);
		} catch (Exception e) {
			throw e;
		}

		return ftpClient;
	}

}
