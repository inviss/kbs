package kr.co.d2net.ftp;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import kr.co.d2net.commons.dto.Job;
import kr.co.d2net.commons.exceptions.FtpLoginException;
import kr.co.d2net.commons.exceptions.TransferException;
import kr.co.d2net.commons.utils.CmsRequestService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class FtpTransferImpl implements FtpTransferService {

	//@Autowired
	private CmsRequestService cmsReqService;
	//@Autowired
	private MessageSource messageSource;

	final Logger logger = LoggerFactory.getLogger(getClass());

	public FtpTransferImpl(MessageSource messageSource, CmsRequestService cmsReqService) {
		this.messageSource = messageSource;
		this.cmsReqService = cmsReqService;
	}

	@Override
	public void uploadFile(Job job) throws Exception {
		//FileTransferClient ftp = null;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.setRemoteHost(job.getFtpIp()); //
			ftpClient.setRemotePort(job.getFtpPort()); //
			ftpClient.setAutoPassiveIPSubstitution(false);

			if(StringUtils.defaultString(job.getMethod(), "P").toLowerCase().startsWith("p"))
				ftpClient.setConnectMode(FTPConnectMode.PASV);
			else
				ftpClient.setConnectMode(FTPConnectMode.ACTIVE);

			ftpClient.setDetectTransferMode(true);
			ftpClient.setStrictReturnCodes(false);
			ftpClient.setControlEncoding("euc-kr");

			ftpClient.connect();
			ftpClient.login(job.getFtpUser(), job.getFtpPass());
		} catch (Exception e) {
			throw new FtpLoginException("ftp login error", e);
		}
		/*
			ftp = new FileTransferClient();
			ftp.setRemoteHost(job.getFtpIp());
			ftp.setRemotePort(job.getFtpPort());
			ftp.setUserName(job.getFtpUser());
			ftp.setPassword(job.getFtpPass());

			TransferAdapter listener = new TransferAdapter(ftp, job, cmsReqService);
			ftp.setEventListener(listener);

			ftp.setContentType(FTPTransferType.BINARY);
			ftp.getAdvancedSettings().setTransferBufferSize(65536);
			ftp.getAdvancedSettings().setTransferNotifyInterval(1500);
			ftp.getAdvancedSettings().setControlEncoding("euc-kr");


			if(StringUtils.defaultString(job.getMethod(), "P").toLowerCase().startsWith("p"))
				ftp.getAdvancedFTPSettings().setConnectMode(FTPConnectMode.PASV);
			else
				ftp.getAdvancedFTPSettings().setConnectMode(FTPConnectMode.ACTIVE);

			ftp.connect();
			if(logger.isDebugEnabled()) {
				logger.debug("[upload] ftp conneted!! - ip:"+job.getFtpIp()+", user: "+job.getFtpUser());
			}
		 */
		try {
			String prefix = messageSource.getMessage("ftp.localDir", null, null);

			if(logger.isDebugEnabled())
				logger.debug("fromDir : "+prefix+"/"+job.getSourcePath() + job.getSourceFile());
			String fromDir = prefix+"/"+job.getSourcePath().trim() + job.getSourceFile().trim(); 

			File f = new File(fromDir);
			if(!f.exists()) {
				throw new Exception("Local File Not Found! - "+f.getAbsolutePath());
			} else {
				job.setFlSz(f.length());

				if(logger.isDebugEnabled()) {
					logger.debug("remote dir : "+job.getRemoteDir());
				}
				try {
					String remoteDir = "";
					if(StringUtils.isNotBlank(job.getRemoteDir())) {
						remoteDir = job.getRemoteDir().trim();
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
				} catch (Exception e) {
					throw e;
				}

				/*
				 * 사업자가 프로그램 그룹코드 형태로 받지 않을경우 프로그램 [영문,한글]명으로 대체하여 폴더를 생성한다.
				 * 프로그램 [영문,한글]명은 메타허브에 사전에 정의가 되어 있어야하고
				 * 에센스허브의 사업자 관리에서 '프로그램 [영문,한글]명 사용'에 체크가 되어 있을경우 
				 * 그룹코드 대신에 [영문,한글]명으로 폴더를 사용하여 하위에 프로그램을 업로드 한다.
				 */
				if(StringUtils.isNotBlank(job.getProEngNm()) && StringUtils.defaultString(job.getProEngYn(), "N").equals("Y")) {
					job.setPgmGrpCd(job.getProEngNm());
				}
				if(logger.isDebugEnabled()) {
					logger.debug("getProEngYn: "+job.getProEngYn());
					logger.debug("getProEngNm: "+job.getProEngNm());
					logger.debug("getPgmGrpCd: "+job.getPgmGrpCd());
				}
				if(!ftpClient.existsDirectory(job.getPgmGrpCd())) {
					ftpClient.mkdir(job.getPgmGrpCd());
					ftpClient.chdir(job.getPgmGrpCd());
				}else{
					ftpClient.chdir(job.getPgmGrpCd());
				}

				/*
				if(StringUtils.isNotBlank(job.getPgmGrpCd())) {
					try {
						ftp.changeDirectory(job.getPgmGrpCd());
					} catch (FTPException fe) {
						try {
							try {
								ftp.createDirectory(job.getPgmGrpCd());
							} catch (Exception e) {
								logger.error("Program GroupCode Create Error", e);
							}
							ftp.changeDirectory(job.getPgmGrpCd());
						} catch (Exception e) {
							logger.error("Program GroupCode directory Change Error", e);
						}
					}
				} else {
					logger.info("Program Group Code is not exists! - "+job.getProFlnm());
				}
				 */
				String smilFileNm = job.getSourceFile().substring(0, job.getSourceFile().indexOf("_"));

				int i=0;
				File f1 = null;
				if(StringUtils.defaultString(job.getVodSmil(), "N").equals("Y")) {
					//if(!ftp.exists(smilFileNm+".smil")) {
					f1 = new File(prefix+"/"+job.getSourcePath()+smilFileNm+".smil");

					if(f1 != null && f1.exists()) {
						// 오류 발생시 3회 재시도
						while(i<3) {
							try {
								if(!ftpClient.connected()) {
									ftpClient.connect();
								}
								if(logger.isDebugEnabled())
									logger.debug("smil filename : "+prefix+"/"+job.getSourcePath()+smilFileNm+".smil");
								/*
								ftp.uploadFile(prefix+"/"+job.getSourcePath()+smilFileNm+".smil", 
										job.getTargetFile().substring(0, job.getTargetFile().lastIndexOf("_"))+".smil", WriteMode.OVERWRITE);
								 */
								ftpClient.put(prefix+"/"+job.getSourcePath()+smilFileNm+".smil",
										job.getTargetFile().substring(0, job.getTargetFile().lastIndexOf("_"))+".smil");

								break;
							} catch (Exception e) {
								logger.error("smil upload error and retry : ("+(i+1)+")", e);
								i++;
								try {
									Thread.sleep(1000L);
								} catch (Exception e2) {}
							}
						}
					} else {
						logger.error(prefix+"/"+job.getSourcePath()+smilFileNm+".smil Not Exist!!");
					}
				} else {
					f1 = new File(fromDir+".xml");
					if(f1 != null && f1.exists()) {
						while(i<3) {
							try {
								if(!ftpClient.connected()) {
									ftpClient.connect();
								}
								if(logger.isDebugEnabled())
									logger.debug(fromDir+".xml");

								//ftp.uploadFile(fromDir+".xml", job.getTargetFile()+".xml", WriteMode.OVERWRITE);
								ftpClient.put(fromDir+".xml", job.getTargetFile()+".xml");

								break;
							} catch (Exception e) {
								logger.error("xml upload error and retry : ("+(i+1)+")", e);
								i++;
								try {
									Thread.sleep(1000L);
								} catch (Exception e2) {}
							}
						}
					} else {
						logger.error(fromDir+".xml contentML Not Exist!!");
					}
				}

				//String msg = ftp.uploadFile(fromDir, job.getTargetFile(), WriteMode.OVERWRITE);

				TransferProgressMonitor progressMonitor = new TransferProgressMonitor(job, cmsReqService);
				ftpClient.setProgressMonitor(progressMonitor, 5000);

				ftpClient.setType(FTPTransferType.BINARY);

				if(logger.isInfoEnabled()) {
					logger.info("binary upload: "+fromDir);
				}
				String msg = ftpClient.put(fromDir, job.getTargetFile());

				try {
					long localSize = f.length();
					long remoteSize = ftpClient.size(job.getTargetFile());

					if(logger.isDebugEnabled()) {
						logger.debug("localSize : "+localSize+", dir: "+fromDir);
						logger.debug("remoteSize : "+remoteSize+", dir: "+job.getTargetFile());
					}

					if(localSize != remoteSize) {
						if(logger.isInfoEnabled()){
							logger.info("ftp transfer reply start!! - "+job.getTargetFile());
						}

						ftpClient.delete(job.getTargetFile());

						msg = ftpClient.put(fromDir, job.getTargetFile());
						if(logger.isInfoEnabled()){
							logger.info("ftp transfer replied!! - "+job.getTargetFile());
						}

						remoteSize = ftpClient.size(job.getTargetFile());
						if(localSize != remoteSize) {
							if(logger.isDebugEnabled()) {
								logger.debug("replied localSize : "+localSize+", dir: "+fromDir);
								logger.debug("replied remoteSize : "+remoteSize+", dir: "+job.getTargetFile());
							}
							throw new FTPException("Local and remote file size is different");
						}
					}

				} catch (Exception e) {
					logger.error("ftp transfer reply error", e);
				}

				if(logger.isDebugEnabled()) {
					logger.debug("msg : "+msg);
				}

			}

			//ftp.disconnect(true);
			ftpClient.quit();

		} catch (ConnectException e) {
			logger.error("ConnectException Error", e);
			throw new FTPException(e.getMessage());
		} catch (IOException e) {
			if(e instanceof UnknownHostException) {
				logger.error("Host Not Found", e);
				throw new UnknownHostException(job.getFtpIp());
			} else if(e instanceof SocketTimeoutException) {
				logger.error("FTP Read Error", e);
				throw new TransferException("600", String.valueOf(job.getJobId()));
			} else if(e instanceof SocketException) {
				logger.error("FTP Writing Error", e);
				throw new TransferException("800", String.valueOf(job.getJobId()));
			} else {
				throw e;
			}
		} catch (FTPException e) {
			/*if(e.getReplyCode() == 550) {
				if(e.getMessage().indexOf("IP") > -1) {
					logger.error(e.getMessage());
					throw e;
				} else if(e.getMessage().indexOf("permission") > -1) {
					throw new TransferException("800", String.valueOf(job.getJobId()));
				} else {
					logger.error("Directory Change Error, job_id :"+job.getJobId(), e);
				}
			} else if(e.getReplyCode() == 501) {
				logger.error("Argument Syntax error, job_id :"+job.getJobId(), e);
			} else {
				throw e;
			}*/
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if (ftpClient.connected()) {
				try {
					ftpClient.quitImmediately();
				} catch (IOException e) {
					logger.error("quit error", e);
				} catch (FTPException e) {
					logger.error("quit error", e);
				}
			}
			ftpClient = null;
			/*try {
				if(ftp.isConnected()) {
					ftp.disconnect(true);
				}
				ftp = null;
			} catch (Exception e2) {
			}*/
		}
	}

	public void uploadXml(Job job) throws Exception {

	}
	/*
	@Override
	public void uploadXml(Job job) throws Exception {
		FileTransferClient ftp = null;

		try {
			ftp = new FileTransferClient();
			ftp.setRemoteHost(job.getFtpIp());
			ftp.setRemotePort(job.getFtpPort());
			ftp.setUserName(job.getFtpUser());
			ftp.setPassword(job.getFtpPass());

			ftp.connect();
			if(logger.isDebugEnabled()) {
				logger.debug("[upload] ftp conneted!! - ip:"+job.getFtpIp()+", user: "+job.getFtpUser());
			}

			String prefix = messageSource.getMessage("ftp.localDir", null, null);
			logger.debug("fromDir : "+prefix+"/"+job.getSourcePath() + job.getSourceFile());
			String fromDir = prefix+"/"+job.getSourcePath() + job.getSourceFile();

			logger.debug("remote dir : "+job.getRemoteDir());
			try {
				if(StringUtils.isNotBlank(job.getRemoteDir())) {
					String remoteDir = "";
					if(job.getRemoteDir().startsWith("/")) {
						remoteDir = job.getRemoteDir().substring(1);
					} else remoteDir = job.getRemoteDir();

					if(remoteDir.indexOf("/") > -1) {
						String[] paths = remoteDir.split("\\/");
						for(String path : paths) {
							if(StringUtils.isNotBlank(path)) {
								try {
									ftp.createDirectory(path);
								} catch (Exception e) {}

								ftp.changeDirectory(path);
							}
						}
					} else {
						if(StringUtils.isNotBlank(remoteDir)) {
							try {
								ftp.createDirectory(remoteDir);
							} catch (Exception e) {}

							ftp.changeDirectory(remoteDir);
						}
					}
				}
			} catch (Exception e) {
				throw e;
			}

			ftp.setContentType(FTPTransferType.BINARY);
			ftp.getAdvancedSettings().setTransferBufferSize(1024);

			if(StringUtils.defaultString(job.getMethod(), "P").toLowerCase().startsWith("p"))
				ftp.getAdvancedFTPSettings().setConnectMode(FTPConnectMode.PASV);
			else
				ftp.getAdvancedFTPSettings().setConnectMode(FTPConnectMode.ACTIVE);


	 * 사업자가 프로그램 그룹코드 형태로 받지 않을경우 프로그램 영문명으로 대체하여 폴더를 생성한다.
	 * 프로그램 영문명은 메타허브에 사전에 정의가 되어 있어야하고
	 * 에센스허브의 사업자 관리에서 '프로그램 영문명 사용'에 체크가 되어 있을경우 
	 * 그룹코드 대신에 영문명으로 폴더를 사용하여 하위에 프로그램을 업로드 한다.

			if(StringUtils.isNotBlank(job.getProEngNm()) && StringUtils.defaultString(job.getProEngYn(), "N").equals("Y")) {
				job.setPgmGrpCd(job.getProEngNm());
			}

			if(StringUtils.isNotBlank(job.getPgmGrpCd())) {
				try {
					ftp.changeDirectory(job.getPgmGrpCd());
				} catch (FTPException fe) {
					try {
						ftp.createDirectory(job.getPgmGrpCd());
						ftp.changeDirectory(job.getPgmGrpCd());
					} catch (Exception e) {
						logger.error("Program GroupCode directory Create Error", e);
					}
				}
			} else {
				logger.info("Program Group Code is not exists! - "+job.getProFlnm());
			}

			try {

				String smilFileNm = job.getSourceFile().substring(0, job.getSourceFile().indexOf("_"));

				if(!ftp.exists(smilFileNm+".smil")) {
					File f1 = new File(prefix+"/"+job.getSourcePath()+smilFileNm+".smil");
					if(f1.exists()) {
						logger.debug("smil filename : "+prefix+"/"+job.getSourcePath()+smilFileNm+".smil");
						ftp.uploadFile(prefix+"/"+job.getSourcePath()+smilFileNm+".smil", smilFileNm+".smil", WriteMode.OVERWRITE);
						logger.debug("smil uploaded!");
					} else {
						logger.debug("smil not exists!");
						f1 = new File(fromDir+".xml");
						if(f1.exists()) {
							ftp.uploadFile(fromDir+".xml", job.getTargetFile()+".xml", WriteMode.OVERWRITE);
							logger.debug("contentML uploaded!");
						} else {
							logger.debug("contentML not exists!");
						}
					}
				} else {
					logger.debug(smilFileNm+".smil existed !! ");
				}
			} catch (Exception e) {
				logger.error("XML Upload Error", e);
			}

			ftp.disconnect();
		} catch (ConnectException e) {
			logger.error("ConnectException error------------------------------------------"+e.getMessage());
			throw e;
		} catch (IOException e) {
			logger.error("IOException error------------------------------------------"+e.getMessage());
			throw e;
		} catch (FTPException e) {
			logger.error("FTPException error------------------------------------------"+e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Exception error------------------------------------------"+e.getMessage());
			throw e;
		} finally {
			try {
				if(ftp.isConnected()) {
					ftp.disconnect();
				}
				ftp = null;
			} catch (Exception e2) {
			}
		}
	}*/

}
