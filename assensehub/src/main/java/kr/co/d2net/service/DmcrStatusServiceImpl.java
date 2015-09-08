package kr.co.d2net.service;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.holders.IntHolder;
import javax.xml.rpc.holders.IntegerWrapperHolder;

import kr.co.d2net.dto.ContentsTbl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icube.kmnc.oa.MNCCTSOAWS;
import com.icube.kmnc.oa.MNCCTSOAWSService;
import com.icube.kmnc.oa.MNCCTSOAWSServiceLocator;

@Service(value="dmcrStatusService")
public class DmcrStatusServiceImpl implements DmcrStatusService {

	private Log logger = LogFactory.getLog(DmcrStatusServiceImpl.class);

	@Autowired
	private ContentsManagerService contentsManagerService;

	@Override
	public void getJobStatus(Long ctId, int jobID) throws RemoteException {

		try {
			MNCCTSOAWSService mncctsoawsService = new MNCCTSOAWSServiceLocator();
			MNCCTSOAWS mncctsoaws = mncctsoawsService.getMNCCTSOAWSPort();

			IntHolder _return = new IntHolder();
			IntegerWrapperHolder jobStatus = new IntegerWrapperHolder();
			IntegerWrapperHolder jobProgress = new IntegerWrapperHolder();
			IntegerWrapperHolder errorOut = new IntegerWrapperHolder();

			mncctsoaws.getJobStatus(jobID, _return, jobStatus, jobProgress, errorOut);

			if(logger.isDebugEnabled()) {
				logger.debug("_return : "+_return.value);
				logger.debug("jobStatus : "+jobStatus.value);
				logger.debug("jobProgress : "+jobProgress.value);
				logger.debug("errorOut : "+errorOut.value);
			}

			if(jobProgress.value != null && jobProgress.value > 0) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("ctId", ctId);

				ContentsTbl contentsTbl = contentsManagerService.getContents(params);
				if(contentsTbl != null) {
					contentsTbl.setPrgrs(String.valueOf(jobProgress.value));

					if(errorOut.value == 1) {
						contentsTbl.setDataStatCd("200");
					} else {
						contentsTbl.setDataStatCd("500");
					}

					contentsManagerService.updateContents(contentsTbl);
				}
			}

		} catch (Exception e) {
			logger.error("getJobStatus error", e);
		}
	}

}
