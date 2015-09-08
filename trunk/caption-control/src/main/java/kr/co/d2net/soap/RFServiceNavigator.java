package kr.co.d2net.soap;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import kr.co.d2net.dto.Status;
import kr.co.d2net.dto.Transfer;
import kr.co.d2net.dto.Workflow;
import kr.co.d2net.exception.TransferException;
import kr.co.d2net.service.FileMoveService;
import kr.co.d2net.service.FtpClientService;
import kr.co.d2net.service.RestfulService;
import kr.co.d2net.service.RestfulServiceImpl;
import kr.co.d2net.utils.JSON;
import kr.co.d2net.utils.Utility;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Path("/")
@Service("rfServiceNavigator")
public class RFServiceNavigator {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FtpClientService ftpClientService;

	@Autowired
	private FileMoveService fileMoveService;

	@Autowired
	private MessageSource messageSource;
	
	private static final String encoding  = "UTF-8";
	
	@GET
	@Path("/test")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Workflow test() {
		Workflow workflow = new Workflow();
		return workflow;
	}


	/**
	 * 외부업체에서 call하는 함수.
	 * file(mp4,smi)을 해당 watchfolder로 copy함.
	 * @param workflow
	 * @return
	 */
	@POST
	@Path("/revise")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML + ";charset=" + encoding, MediaType.APPLICATION_JSON + ";charset=" + encoding})
	public Status revise(Workflow workflow) {

		if(logger.isDebugEnabled()){
			logger.debug("workflow.getSyncCcLocation() : " + workflow.getSyncCcLocation());
			logger.debug("workflow.getCcFileName() : " + workflow.getCcFileName());
		}
		
		Status status = new Status();
		String inTime = new java.text.SimpleDateFormat("HHmmss").format(new java.util.Date());
		
		//KBS 시스템아이디
		String systemId;
		
		try {
			systemId = messageSource.getMessage("kbscc.systemId", null, null);
		} catch (Exception e) {
			status.setErrorMessage("messageSource is empty");
			status.setResult("E");
			
			return status;
		}
		
		String[] tmpSystemId = systemId.split(",");

		boolean skip = false;
		if(tmpSystemId == null || tmpSystemId.length == 0) {
			skip = true;
		}

		if(!skip) {

			if(StringUtils.isBlank(workflow.getSystemId())) {
				status.setResult("E");
				status.setErrorMessage("systemID is blank");
				Utility.writeLogs("revise : " + inTime + " : " +  JSON.toJsonString(workflow));
				return status;
			}

			boolean perm = false;
			for(int i = 0; i < tmpSystemId.length; i++){
				if(workflow.getSystemId().equals(tmpSystemId[i])){
					perm = true;
					break;
				}
			}

			if(!perm) {
				status.setResult("E");
				status.setErrorMessage("systemID is not permit");
				Utility.writeLogs("revise : " + inTime + " : " +  JSON.toJsonString(workflow));
				return status;
			}
		}

		try {
			fileMoveService.captionDownload(workflow);
			fileMoveService.contentCopy(workflow);
			status.setResult("S");
		} catch (Exception e) {
			Utility.writeLogs("revise : " + inTime + " : " +  JSON.toJsonString(workflow));
			status.setResult("E");
			status.setErrorMessage("file move failed");
			logger.error("revise error",e);
		}

		return status;
	}


	/**
	 * 외부업체에서 call하는 함수.
	 * file(smi)을 해당 FTP로 upload함.
	 * @param workflow
	 * @return
	 */
	@POST
	@Path("/upload")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML + ";charset=" + encoding, MediaType.APPLICATION_JSON + ";charset=" + encoding})
	public Status upload(Workflow workflow) {

		RestfulService restfulService  = new RestfulServiceImpl();
		Status status = new Status();
		
		String inTime = new java.text.SimpleDateFormat("HHmmss").format(new java.util.Date());
		
		String ftpIP,ftpPort,ftpId,ftpPasswd,asmx,systemId;
		
		try {
			ftpIP = messageSource.getMessage(workflow.getUploadSystem() + ".kbscc.ip", null, null);
			ftpPort = messageSource.getMessage(workflow.getUploadSystem() + ".kbscc.port", null, null);
			ftpId = messageSource.getMessage(workflow.getUploadSystem() + ".kbscc.id", null, null);
			ftpPasswd = messageSource.getMessage(workflow.getUploadSystem() + ".kbscc.passwd", null, null);
			asmx = messageSource.getMessage("kbscc.asmx", null, null);
			systemId = messageSource.getMessage("kbscc.systemId", null, null);
		} catch (Exception e) {
			status.setErrorMessage("messageSource is empty");
			status.setResult("E");
			
			return status;
		}

		String[] tmpSystemId = systemId.split(",");

		boolean skip = false;
		if(tmpSystemId == null || tmpSystemId.length == 0) {
			skip = true;
		}

		if(!skip) {
			if(StringUtils.isBlank(workflow.getSystemId())) {
				status.setResult("E");
				status.setErrorMessage("systemID is blank");
				Utility.writeLogs("upload : " + inTime + " : " +  JSON.toJsonString(workflow));
				return status;
			}

			boolean perm = false;
			for(int i = 0; i < tmpSystemId.length; i++){
				if(workflow.getSystemId().equals(tmpSystemId[i])){
					perm = true;
					break;
				}
			}

			if(!perm) {
				status.setResult("E");
				status.setErrorMessage("systemID is not permit");
				Utility.writeLogs("upload : " + inTime + " : " +  JSON.toJsonString(workflow));
				return status;
			}
		}

		Transfer transfer = new Transfer();

		transfer.setFtpIp(ftpIP);
		transfer.setFtpPort(Integer.parseInt(ftpPort));
		transfer.setUserId(ftpId);
		transfer.setUserPass(ftpPasswd);
		transfer.setFilePath(workflow.getSyncCcLocation());
		transfer.setConnectMode("A");
		transfer.setRemoteDir("");

		try {
			
			ftpClientService.upload(transfer);
			
			if(logger.isInfoEnabled()) {
				logger.info("upload_system code: "+workflow.getUploadSystem());
			}
			
			workflow.setUploadSystem(StringUtils.defaultIfBlank(workflow.getUploadSystem(), "01"));
			
			if(workflow.getUploadSystem().startsWith("01")){
				Map<String, Object> params = new HashMap<String, Object>();
				
				String tmp = workflow.getSyncCcLocation().replaceAll("\\\\", "/");
				String tmpSync = tmp.substring(tmp.lastIndexOf("/") + 1);

				params.put("request_system_ID",  "VAS0000001");
				params.put("request_system_password",  "1111");
				params.put("user_ID",  "");
				params.put("caption_language_kind",  "K");
				params.put("program_ID",  workflow.getProgramId());
				params.put("file_format",  "01");
				params.put("caption_file_path",  tmpSync);
				params.put("copyright_special_note",  "");
				params.put("caption_file_description",  "");
				params.put("synchronized_YN",  "Y");

				//restfulService.post("http://ppsx.kbs.co.kr/KBS_PPS_IF.asmx/PPS_INSERT_CAPTION_V1", params, null);
				restfulService.post("http://" + asmx + "/KBS_PPS_IF.asmx/PPS_INSERT_CAPTION_V1", params, null);
			}else{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("program_id",  workflow.getProgramId());
				params.put("segment_id",  "");
				params.put("caption_language_code",  "K");
				params.put("file_format",  "SMI");
				params.put("caption_version",  "1.0a");
				params.put("copyright_special_note",  "");
				params.put("caption_file_full_path",  "/");
				params.put("caption_max_width",  "20");
				params.put("caption_max_height",  "1");

				//restfulService.post("http://211.233.93.58/api/mh_closed_caption/caption.json", params, null);
				restfulService.post("http://" + ftpIP + "/api/mh_closed_caption/caption.json", params, null);
			}

			status.setResult("S");

		} catch (Exception e) {
			logger.error("upload error",e);
			Utility.writeLogs("upload : " + inTime + " : " +  JSON.toJsonString(workflow));
			if(e instanceof TransferException) {
				//TransferException te = (TransferException)e;
				status.setErrorMessage("Transfer failed");
				status.setResult("E");
			}else{
				status.setErrorMessage("upload failed");
				status.setResult("E");
			}
		}

		return status;
	}

}
