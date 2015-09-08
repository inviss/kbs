package kr.co.d2net.xml;

import java.io.File;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.xml.dmcr.KBSExchange;
import kr.co.d2net.dto.xml.dmcr.Metadata;
import kr.co.d2net.service.DmcrStatusService;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.icube.kmnc.oa.MNCCTSOAWS;
import com.icube.kmnc.oa.MNCCTSOAWSService;
import com.icube.kmnc.oa.MNCCTSOAWSServiceLocator;


public class DmcrStatusTest extends BaseXmlConfig {
	
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private DmcrStatusService dmcrStatusService;
	
	@Ignore
	@Test
	public void callTest() {
		try {
			MNCCTSOAWSService mncctsoawsService = new MNCCTSOAWSServiceLocator();
			MNCCTSOAWS mncctsoaws = mncctsoawsService.getMNCCTSOAWSPort();
			/*for(String profile : mncctsoaws.getTransferProfileList()) {
				System.out.println("profile: "+profile);
			}*/
			
			//dmcrStatusService.getJobStatus(4540);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void dmcrXmlParsing() {
		
		ContentsTbl contentsTbl = null;
		ContentsInstTbl contentsInstTbl = null;
		try {
			String metaXML = FileUtils.readFileToString(new File("d:/Y12130164606.xml"), "UTF-8");
			KBSExchange kbsExchange = (KBSExchange)xmlStream.fromXML(metaXML);
			
			contentsTbl = new ContentsTbl();
			contentsInstTbl = new ContentsInstTbl();

			Metadata metadata = kbsExchange.getMetadata();

			char kind = metadata.getBroadcastEventKind().charAt(0);
			try {
				switch(kind) {
				case 'Y':	// 예고
					contentsTbl.setCtNm(metadata.getTailerTitle());
					contentsTbl.setCtTyp("05");
					break;
				case '1':	// 전타
					contentsTbl.setCtNm(metadata.getProgramSubTitle());
					contentsTbl.setCtTyp("01");
					break;
				case '2':	// 후타
					contentsTbl.setCtNm(metadata.getProgramSubTitle());
					contentsTbl.setCtTyp("04");
					break;
				case 'P':	// 프로그램
					contentsTbl.setCtNm(metadata.getProgramSubTitle());
					contentsTbl.setCtTyp("00");
					break;
				}

				contentsTbl.setPgmCd(metadata.getProgramCode());
				contentsTbl.setPgmId(metadata.getProgramId());
				contentsTbl.setPgmNm(metadata.getProgramTitle());
				contentsTbl.setBrdDd(metadata.getBroadcastPlannedDate());
				contentsTbl.setVdQlty(metadata.getProductionVideoQualty());
				contentsTbl.setCtCla("00");
				contentsTbl.setChannelCode(metadata.getChannelCode());
				contentsTbl.setBrdDd(metadata.getBroadcastPlannedDate());
				
			} catch (Exception e) {
				throw new ServiceException("Metahub Search Error", e);
			}
			
			System.out.println(contentsTbl.getCtTyp());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
