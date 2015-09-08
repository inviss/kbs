package kr.co.d2net.xml;

import java.io.File;

import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.xml.dmcr.KBSExchange;
import kr.co.d2net.dto.xml.mnc.KBSMetaData;
import kr.co.d2net.dto.xml.mnc.Sequence;
import kr.co.d2net.dto.xml.mnc.SequenceList;
import kr.co.d2net.service.ContentsManagerService;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MncParseTest extends BaseXmlConfig {
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private ContentsManagerService contentsManagerService;

	@Before
	public void init() {
		try {
			xmlStream.setAnnotationAlias(KBSExchange.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void xmlParseTest() {
		try {
			KBSMetaData kbsMetaData = new KBSMetaData();
			kbsMetaData.setClipId("김대중 자료");
			kbsMetaData.setProductionDepartmentName("김대중 자료 3");

			SequenceList sequenceList = new SequenceList();

			Sequence sequence = new Sequence();
			sequence.setIndex(0);
			sequenceList.addSequence(sequence);

			sequence = new Sequence();
			sequence.setIndex(1);
			sequenceList.addSequence(sequence);

			kbsMetaData.setSequenceList(sequenceList);

			System.out.println(xmlStream.toXML(kbsMetaData));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void mncXmlTest() {
		try {
			String xml = FileUtils.readFileToString(new File("D:/kdas.xml"), "UTF-8");
			ContentsTbl contentsTbl = new ContentsTbl();
			ContentsInstTbl contentsInstTbl = new ContentsInstTbl();
			contentsManagerService.archiveXMLPasing(xml, contentsTbl, contentsInstTbl);

			System.out.println(contentsTbl.getPgmNm()+", duration: "+contentsTbl.getPgmId());
			//XmlConvertorService<KBSExchangeMetadata> xmlConvertorService = XmlConvertorServiceImpl.getInstance();
			//KBSExchangeMetadata kbsExchange = xmlConvertorService.unMarshaller(xml);
			//System.out.println(kbsExchange.getProgramInfo().getProgramTitle()+", onAirDate: "+kbsExchange.getEpisodeInfo().getProgramOnairDate());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void dnpsPostTest() {
		
		try {
			String xml = FileUtils.readFileToString(new File("D:/tmp/1311220200.xml"), "UTF-8");
			KBSExchange kbsExchange = (KBSExchange)xmlStream.fromXML(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
