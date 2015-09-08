package kr.co.d2net.ftp;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kr.co.d2net.commons.ServiceConstants;
import kr.co.d2net.commons.dto.EqTbl;
import kr.co.d2net.commons.dto.Job;
import kr.co.d2net.commons.dto.Status;
import kr.co.d2net.commons.dto.Transfer;
import kr.co.d2net.commons.dto.Workflow;
import kr.co.d2net.commons.exceptions.FtpLoginException;
import kr.co.d2net.commons.exceptions.ServiceException;
import kr.co.d2net.commons.exceptions.TransferException;
import kr.co.d2net.commons.exceptions.XmlParsingException;
import kr.co.d2net.commons.utils.CmsRequestService;
import kr.co.d2net.commons.utils.StringUtil;
import kr.co.d2net.commons.utils.SwappingFifoQueue;
import kr.co.d2net.commons.utils.XmlStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.enterprisedt.net.ftp.FTPException;

public class FtpTransferControl {

	final Logger logger = LoggerFactory.getLogger(getClass());

	//@Autowired
	//private FtpTransferService ftpTransferService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private CmsRequestService cmsReqService;

	public static SwappingFifoQueue<Status> currTransfer = new SwappingFifoQueue<Status>();
	public static volatile boolean cancelled = false;

	private volatile ExecutorService jobReq = Executors.newSingleThreadExecutor();
	private volatile ExecutorService putPool = Executors.newFixedThreadPool(ServiceConstants.FTP_POOL_SIZE);

	public void start() throws Exception {

		String uploadThread = messageSource.getMessage("ftp.upload.thread", null, Locale.KOREA);
		String deviceId = messageSource.getMessage("ftp.device.id", null, Locale.KOREA);
		String iptvOnly = messageSource.getMessage("iptv.only", null, Locale.KOREA);

		EqTbl eqTbl = null;
		int ftpCount = 1;
		for(int i=ftpCount; i<=Integer.valueOf(uploadThread); i++) {
			eqTbl = new EqTbl();
			eqTbl.setEqId(deviceId+"_"+StringUtil.padLeft(String.valueOf(i), "0", 2));
			eqTbl.setEqNm("Transfer : "+deviceId+"_"+StringUtil.padLeft(String.valueOf(i), "0", 2));
			eqTbl.setHigh(Boolean.valueOf(iptvOnly));

			putPool.execute(new TransferUpload(eqTbl));
			ftpCount++;
		}
	}

	class TransferUpload implements Runnable {

		private final FtpTransferService ftpTransferService = new FtpTransferImpl(messageSource, cmsReqService);
		private EqTbl eqTbl;
		public TransferUpload(EqTbl eqTbl) {
			this.eqTbl = eqTbl;
		}

		@Override
		public void run() {
			if(logger.isDebugEnabled()) {
				logger.debug("####################### "+eqTbl.getEqNm()+" Started ##################");
				logger.debug("####################### "+ftpTransferService+" Started ##################");
			}

			Workflow workflow = new Workflow();
			Status status = null;
			while(!cancelled) {
				try {
					status = new Status();
					status.setEqId(eqTbl.getEqId());
					status.setEqState("W");

					if(eqTbl.isHigh()) {
						status.setProFlid("35M");
					}

					workflow.setStatus(status);

					String retXml = cmsReqService.saveStatus(xmlStream.toXML(workflow));
					if(logger.isDebugEnabled()) {
						logger.debug("retXml : "+retXml);
					}
					if(StringUtils.isNotBlank(retXml)) {
						try {
							workflow = (Workflow)xmlStream.fromXML(retXml);
						} catch (Exception e) {
							throw new XmlParsingException("", e);
						}
						
						if(workflow.getJob() != null && workflow.getJob().getJobId() != null) {

							Job job = workflow.getJob();
							if(logger.isDebugEnabled()) {
								logger.debug("################ ftp put data start ################");
								logger.debug("eq_id			: "+eqTbl.getEqId());
								logger.debug("job_id 		: "+job.getJobId());
								logger.debug("pgm_grp_cd	: "+job.getPgmGrpCd());
								logger.debug("pgm_cd 		: "+job.getPgmCd());
								logger.debug("ftp_ip		: "+job.getFtpIp());
								logger.debug("fl_path		: "+job.getSourcePath());
								logger.debug("fl_name		: "+job.getSourceFile());
								logger.debug("target_path	: "+job.getTargetPath());
								logger.debug("target_file	: "+job.getTargetFile());
								logger.debug("method		: "+job.getMethod());
								logger.debug("vod_smil		: "+job.getVodSmil());
								logger.debug("################ ftp put data end ################");
							}

							job.setEqId(eqTbl.getEqId());
							status.setJobId(job.getJobId());
							
							if(StringUtils.isBlank(job.getTargetFile()) || job.getTargetFile().indexOf("null") > -1) {
								throw new ServiceException("Service File Name is Blank - "+job.getTargetFile());
							}

							ftpTransferService.uploadFile(job);
						}
					}
				} catch (ConnectException e) {
					logger.error("Server connection error!!", e);
					status.setErrorCd("400");
					try {
						workflow.setJob(null);
						
						status.setJobState("E");
						status.setEqState("B");
						status.setProFlid(null);
						workflow.setStatus(status);
						cmsReqService.saveStatus(workflow);
					} catch (Exception e2) {
						logger.error("Error Save error", e2);
					}
				} catch (Exception e) {
					if(e instanceof UnknownHostException) {
						status.setErrorCd("400");
						logger.error("UnknownHostException!!", e);
					} else if(e instanceof IOException) {
						status.setErrorCd("500");
						logger.error("IOException!!", e);
					} else if(e instanceof FTPException) {
						status.setErrorCd("500");
						logger.error("FTPException!!", e);
					} else if(e instanceof FtpLoginException) {
						status.setErrorCd("600");
						logger.error("FtpLoginException!!", e);
					} else if(e instanceof TransferException) {
						TransferException te = (TransferException)e;
						status.setErrorCd(te.getErrorCode());
						logger.error("TransferException!!", e);
					} else {
						status.setErrorCd("900");
						logger.error("Exception!!", e);
					}
					
					try {
						workflow.setJob(null);
						
						status.setJobState("E");
						status.setEqState("B");
						status.setProFlid(null);
						workflow.setStatus(status);
						cmsReqService.saveStatus(workflow);
					} catch (Exception e2) {
						logger.error("Error Save error", e2);
					}
				} finally {
					workflow.setJob(null);
				}

				try {
					Thread.sleep(5000L);
				} catch (Exception e) {}
			}

			try {
				stop();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void stop() throws Exception {
		try {
			if(!putPool.isShutdown()) {
				putPool.shutdownNow();
				if(logger.isInfoEnabled()) {
					logger.info("PutPool Thread shutdown now!!");
				}
			}
			if(!jobReq.isShutdown()) {
				jobReq.shutdownNow();
				if(logger.isInfoEnabled()) {
					logger.info("JobReq Thread shutdown now!!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * Queue 등록시 요청시간을 비교해서 순위를 정한다.
	 * 요청시간은 사용자별 삭제 요청시간이다.
	 * </pre>
	 * @return
	 */
	private static Comparator<? super Transfer> getCompare() {
		return new Comparator<Transfer>() {
			public int compare(Transfer d1, Transfer d2) {
				if(d1.getPriors() == d2.getPriors()) {
					if(d1.getRegDtm().getTime() < d2.getRegDtm().getTime()) return -1;
					else return 1;
				} else {
					if(d1.getPriors() < d2.getPriors()) return -1;
					else return 1;
				}
			}
		};
	}
}
