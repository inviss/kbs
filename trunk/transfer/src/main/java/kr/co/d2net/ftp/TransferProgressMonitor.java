package kr.co.d2net.ftp;

import kr.co.d2net.commons.dto.Job;
import kr.co.d2net.commons.dto.Status;
import kr.co.d2net.commons.dto.Workflow;
import kr.co.d2net.commons.utils.CmsRequestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enterprisedt.net.ftp.FTPProgressMonitor;

public class TransferProgressMonitor implements FTPProgressMonitor {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private Job job;
	private CmsRequestService cmsReqService;

	private Workflow workflow = new Workflow();
	private int percent;
	private Status status;

	public TransferProgressMonitor(Job job, CmsRequestService cmsReqService) {
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

	@Override
	public void bytesTransferred(long bytes) {
		int i = (int)(((double)bytes / (double)job.getFlSz())*100);
		if(i != percent && (i%2==0)) {
			if(percent != 100) {
				percent = i;
				status.setProgress(percent);
				status.setEqState("B");

				if(percent == 100) {
					status.setJobState("C");
				} else {
					status.setJobState("I");
				}

				FtpTransferControl.currTransfer.put(status);

				workflow.setStatus(status);
				try {
					cmsReqService.saveStatus(workflow);
				} catch (Exception e) {
					logger.error("Status Save Error", e);
				}
			}
		}
	}

}
