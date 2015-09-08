package kr.co.d2net.dao;

import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.BusiPartnerPgm;
import kr.co.d2net.dto.LiveTbl;
import kr.co.d2net.service.BusiPartnerPgmManagerService;
import kr.co.d2net.service.LiveManagerService;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BusiPartnerPgmManagerTest extends BaseDaoConfig {

	@Autowired
	private BusiPartnerPgmManagerService busiPartnerPgmManagerService;
	@Autowired
	private LiveManagerService liveManagerService;

	@Test
	public void busipartnerpgmInsertTest() {
		LiveTbl live = new LiveTbl();
		// 녹화정보 수정과 관련한 내용 입력부
		// 시작
		live.setProgramCode("R2012-0231");
		// live.setBgnTime(Utility.getDate(bgnTime, "yyyyMMdd"));
		live.setBgnTime(Utility.getTimestamp());
		live.setRecyn("Y");
		// live.setEndTime(Utility.getDate(endTime, "yyyyMMdd"));
		live.setEndTime(Utility.getTimestamp());
		live.setRerunCode("01");
		// 끝

		String busiPartnerid = "355,353";
		String[] iBusiPartnerid = null;
		if (!StringUtils.isEmpty(busiPartnerid))
			iBusiPartnerid = busiPartnerid.split(",");

		try {
			liveManagerService.updateLiveTbl(live);
			BusiPartnerPgm bpp = new BusiPartnerPgm();
			bpp.setProgramCode(live.getProgramCode());
			bpp.setCtTyp("00");
			busiPartnerPgmManagerService.deleteBusiPartnerPgm(bpp);

			if (iBusiPartnerid != null) {
				for (int i = 0; i < iBusiPartnerid.length; i++) {
					BusiPartnerPgm busipartnerPgm = new BusiPartnerPgm();
					busipartnerPgm.setProgramCode(live.getProgramCode());
					busipartnerPgm.setBusiPartnerid(iBusiPartnerid[i]);
					busipartnerPgm.setCtTyp("00");
					busiPartnerPgmManagerService.insertBusiPartnerPgm(busipartnerPgm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
