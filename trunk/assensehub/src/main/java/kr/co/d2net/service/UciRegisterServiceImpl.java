package kr.co.d2net.service;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dto.TransferHisTbl;
import kr.co.ybtech.md.KidentificationMetadata;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPBodyElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nca.uci.md.Contribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class UciRegisterServiceImpl implements UciRegisterService {
	
	private static Log logger = LogFactory.getLog(UciRegisterServiceImpl.class);

	@Autowired
	private MessageSource messageSource;

	private Call call;
	private SOAPBodyElement[] sbe;

	private Map<String, String> formatList;

	public void setFormatList(Map<String, String> formatList) {
		this.formatList = formatList;
	}

	public void uciInit() throws Exception {
		Service service = new Service();
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(messageSource.getMessage(	"uci.wsdl.url", null, Locale.KOREA)));
			call.setUsername(messageSource.getMessage("uci.user.id", null,Locale.KOREA));
			call.setPassword(messageSource.getMessage("uci.user.pass", null,Locale.KOREA));

			sbe = new SOAPBodyElement[1];
			sbe[0] = new SOAPBodyElement(new QName("store"));
		} catch (javax.xml.rpc.ServiceException e) {
			throw e;
		}
	}

	@Override
	public void addRegiester(TransferHisTbl transferHisTbl)
			throws ServiceException {
		if (formatList.containsKey(transferHisTbl.getFlExt().toLowerCase())) {
			KidentificationMetadata md = new KidentificationMetadata();
			md.setUCI("I002-" + transferHisTbl.getWrkFileNm()); // 필수
			md.setFormat(transferHisTbl.getFlExt().toLowerCase()); // 필수
			md.setMode("시각"); // 고정값
			md.addTitle(transferHisTbl.getPgmNm()); // 필수
			md.addContribution(new Contribution("kbsi").addRole("Production")); // 기여자
																				// 정보(중복가능)
																				// kbsi,
																				// 제작사[Production]
			md.setType("디지털"); // 고정값
			md.setC_type("M"); // 콘텐츠 종류
			md.setDesc(transferHisTbl.getCtNm()); // 줄거리 [필수]
			md.setH_size(String.valueOf(transferHisTbl.getFlSz())); // 해상도
			md.addUrlList("url", messageSource.getMessage("uci.service.url", null, null)); // 변환서비스 URL

			sbe[0] = new SOAPBodyElement(md.toDocument().getDocumentElement());
			try {
				call.invoke(sbe);
				logger.debug("UCI : "+md.getUCI());
			} catch (RemoteException e) {
				throw new ServiceException("UCI Call Error!", e);
			}
		} else {
			throw new ServiceException("Format Not Found! - "
					+ transferHisTbl.getFlExt().toLowerCase());
		}
	}

}
