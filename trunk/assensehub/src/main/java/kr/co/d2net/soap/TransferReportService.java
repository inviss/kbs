package kr.co.d2net.soap;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.service.ContentsManagerService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@WebService(endpointInterface = "kr.co.d2net.soap.TransferReport")
public class TransferReportService implements TransferReport {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ContentsManagerService contentsManagerService;

	@Override
	public String jobStatus(String caller, String jobId, String pgmCd, String pgmId, String status) throws RemoteException {
		if(logger.isDebugEnabled()) {
			logger.debug("caller: "+caller);
			logger.debug("jobId: "+jobId);
			logger.debug("pgmCd: "+pgmCd);
			logger.debug("pgmId: "+pgmId);
			logger.debug("status: "+status);
		}

		if(StringUtils.isBlank(caller) || StringUtils.isBlank(jobId)
				|| StringUtils.isBlank(pgmCd) || StringUtils.isBlank(pgmId)) {
			throw new RemoteException("parameter value is blank!");
		}

		if("null".equals(pgmCd.trim())) {
			logger.error("program_code value is null!");
			throw new RemoteException("program_code value is null!");
		}
		if("null".equals(pgmId.trim())) {
			logger.error("program_id value is null!");
			throw new RemoteException("program_id value is null!");
		}

		caller = caller.toUpperCase();

		ContentsTbl contentsTbl = null;

		String dataStatCd = "";
		if("S".equals(status)) dataStatCd = "002";
		else if("C".equals(status)) dataStatCd = "200";
		else dataStatCd = "500";

		try {
			// 최초 등록시에만 프로그램 그룹코드 조회 및 컨텐츠 신규등록
			if("002".equals(dataStatCd)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("jobId", jobId);

				contentsTbl = contentsManagerService.getContents(params);
				if(contentsTbl == null) {
					contentsTbl = new ContentsTbl();
					contentsTbl.setPgmCd(pgmCd);
					contentsTbl.setPgmId(pgmId);
					contentsTbl.setJobId(jobId);
					contentsTbl.setUseYn("Y");
					contentsTbl.setRegDt(Utility.getTimestamp());
					contentsTbl.setRegrid(caller);
					contentsTbl.setPrgrs("0");
					contentsTbl.setTrimmSte("N");
					contentsTbl.setDataStatCd(dataStatCd);

					contentsManagerService.insertContents(contentsTbl);
				}
			} else { // 컨텐츠 정보 변경
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("jobId", jobId);
				ContentsTbl contentsTbl2 = contentsManagerService.getContents(params);
				if(contentsTbl2 != null) {
					if(dataStatCd.equals("200"))
						contentsTbl2.setPrgrs("100");
					contentsTbl2.setDataStatCd(dataStatCd);
					contentsManagerService.updateContents(contentsTbl2);
				}
			}
		} catch (Exception e) {
			logger.error("jobStatus", e);
			throw new RemoteException("content new insert error");
		}


		return Boolean.valueOf(true).toString();
	}

}
