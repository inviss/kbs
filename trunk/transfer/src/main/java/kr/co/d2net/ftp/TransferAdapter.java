package kr.co.d2net.ftp;

/*
 * (@)# TransferAdapter.java
 * ==================================================================
 * <<D2net>>., Software Licence, Version 1.0
 * 
 * Copyright (c) 1998-2009 <<디투넷>>.,
 * <<서울 영등포구 양평동 3가 16번지 우림이비즈센타 812>> * All reght reserved.
 * 
 * 이 소스의 저작권은 디투넷에 있습니다.
 * url: www.d2net.co.kr
 * ==================================================================
 */

import kr.co.d2net.commons.dto.Job;
import kr.co.d2net.commons.dto.Status;
import kr.co.d2net.commons.dto.Workflow;
import kr.co.d2net.commons.utils.CmsRequestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enterprisedt.net.ftp.EventListener;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FileTransferClient;
import com.enterprisedt.net.ftp.FileTransferClientInterface;
import com.enterprisedt.net.ftp.test.FileTransferClientAdapter;

public class TransferAdapter implements EventListener {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private Workflow workflow = new Workflow();
	private Job job;
	private Status status;
	private int percent;
	private FileTransferClient ftp;

	private CmsRequestService cmsReqService;
	public TransferAdapter(FileTransferClient ftp, Job job, CmsRequestService cmsReqService) {
		this.ftp = ftp;
		this.job = job;
		this.cmsReqService = cmsReqService;
		this.status = new Status();
		this.status.setEqId(job.getEqId());
		this.status.setJobId(job.getJobId());
		this.status.setPgmNm(job.getPgmNm());
		this.status.setCompany(job.getCompany());
		this.status.setWrkFileNm(job.getWrkFileNm());
		this.status.setProFlNm(job.getProFlnm());
	}

	public void bytesTransferred(String remoteFilename, String connId, long bytes) {
		if(!remoteFilename.endsWith("xml") && !remoteFilename.endsWith("smil")) {
			int i = (int)(((double)bytes / (double)job.getFlSz())*100);
			if(i != percent) {
				percent = i;
				status.setProgress(percent);
				if(percent <= 99) {
					status.setEqState("B");
					status.setJobState("I");
				}
				FtpTransferControl.currTransfer.put(status);

				workflow.setStatus(status);
				try {
					cmsReqService.saveStatus(workflow);
					
					if(percent == 90) {
						FileTransferClientAdapter client = new FileTransferClientAdapter(ftp);
						FileTransferClientInterface ftc = client.getFileTransferClient();
						ftc.connect();
					}
				} catch (FTPException fe) {
					if(fe.getReplyCode() != -1)
						logger.error("Ftp ReConnected Error", fe);
				} catch (Exception e) {
					logger.error("Status Save Error", e);
				}
			}
		}
	}

	public void commandSent(String connId, String cmd) {
	}

	public void downloadCompleted(String connId, String remoteFilename) {

		try {
			// 진행상태값이 100은 없으므로 99를 완료로 정하고 99보다 적은 값일경우 진행 취소로 판단됨
			if(percent <= 99){
				status.setJobState("E");
			} else {
				status.setJobState("C");
			}
			FtpTransferControl.currTransfer.put(status);
		} catch (Exception e) {
			logger.error("Download Error - "+e.getCause());
		}

	}

	public void downloadStarted(String connId, String remoteFilename) {
		if (logger.isInfoEnabled()) {
			logger.info(remoteFilename+" Download Started!");
		}
		try {
			status.setJobState("I");
		} catch (Exception e) {
			logger.error("Download Status Update Error", e);
		}
	}

	public void replyReceived(String connId, String remoteFilename) {

	}

	public void uploadCompleted(String connId, String remoteFilename) {
		if (logger.isInfoEnabled()) {
			logger.debug("percent : "+percent+", "+status.getJobId());
			logger.info(remoteFilename+" uploadCompleted!");
		}
		if(!remoteFilename.endsWith("xml") && !remoteFilename.endsWith("smil")) {
			// 진행상태값이 100은 없으므로 99를 완료로 정하고 99보다 적은 값일경우 진행 취소로 판단됨
			if((percent > 1) && (percent < 99)){
				logger.debug("percent : "+percent+", "+status.getJobId()+" error!!");
				status.setEqState("B");
				status.setJobState("E");
				status.setProgress(percent);
			} else {
				status.setEqState("B");
				status.setJobState("C");
				status.setProgress(100);
			}

			FtpTransferControl.currTransfer.put(status);

			workflow.setStatus(status);

			int i=0;

			while(i < 3) {
				try {
					cmsReqService.saveStatus(workflow);
					break;
				} catch (Exception e) {
					logger.error("Complete Status Save Error", e);
					try {
						Thread.sleep(2000L);
					}catch(Exception e2){}
					i++;
				}
			}

		}
	}

	public void uploadStarted(String connId, String remoteFilename) {
		if (logger.isInfoEnabled()) {
			logger.debug("percent : "+percent+", "+status.getJobId());
			logger.info(remoteFilename+" uploadStarted!");
		}
		/*if(!remoteFilename.endsWith("xml") && !remoteFilename.endsWith("smil")) {
			status.setProgress(0);
			status.setEqState("B");
			status.setJobState("S");
			FtpTransferControl.currTransfer.put(status);

			workflow.setStatus(status);
			try {
				cmsReqService.saveStatus(workflow);
			} catch (ServiceException e) {
				logger.error("Status Save Error", e);
			}
		}*/
	}

}
