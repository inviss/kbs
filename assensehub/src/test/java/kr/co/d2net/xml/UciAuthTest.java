package kr.co.d2net.xml;

import kr.co.d2net.dto.TransferHisTbl;
import kr.co.d2net.service.UciRegisterService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UciAuthTest extends BaseXmlConfig {
	
	@Autowired
	private UciRegisterService uciRegisterService;

	@Test
	public void archiveSoapTest() {
		try {
			TransferHisTbl transferHisTbl = new TransferHisTbl();
			transferHisTbl.setWrkFileNm("PS-2012156823_TEST2");
			transferHisTbl.setExt("avi");
			transferHisTbl.setSegmentNm("UCI 인증 테스트");
			transferHisTbl.setPgmNm("UCI 인증 DESC");
			transferHisTbl.setFlSz(2020L);
			
			uciRegisterService.addRegiester(transferHisTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
