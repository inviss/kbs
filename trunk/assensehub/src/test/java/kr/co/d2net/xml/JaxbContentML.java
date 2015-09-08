package kr.co.d2net.xml;

import kr.co.d2net.commons.util.XmlConvertorService;
import kr.co.d2net.commons.util.XmlConvertorServiceImpl;
import kr.co.d2net.dto.xml.publish.BasicDescription;
import kr.co.d2net.dto.xml.publish.CopyrightDescription;
import kr.co.d2net.dto.xml.publish.CopyrightInformation;
import kr.co.d2net.dto.xml.publish.CopyrightInformationTable;
import kr.co.d2net.dto.xml.publish.GroupInformation;
import kr.co.d2net.dto.xml.publish.GroupInformationTable;
import kr.co.d2net.dto.xml.publish.ProgramDescription;
import kr.co.d2net.dto.xml.publish.ProgramInfoarmationTable;
import kr.co.d2net.dto.xml.publish.ProgramInformation;
import kr.co.d2net.dto.xml.publish.TvaMain;
import kr.co.d2net.dto.xml.publish.Type;

import org.junit.Test;

public class JaxbContentML {
	@Test
	public void createPublish() {
		
		XmlConvertorService<TvaMain> convertorService = XmlConvertorServiceImpl.getInstance();
		try {
			TvaMain main = new TvaMain();
			ProgramDescription programDescription = new ProgramDescription();

			GroupInformationTable groupInformationTable = new GroupInformationTable();
			GroupInformation groupInformation = new GroupInformation();
			BasicDescription basicDescription = new BasicDescription();
			//basicDescription.setProgramCode("T2000-0032");
			//basicDescription.setProgramId("PS-2012120262-01-000");
			basicDescription.setProgramTitle("http://www.freesnap.kr 아침마당");
			basicDescription.setProgramIntention("http://www.google.co.kr 즐거운 아침, 건강한 아침, 감동의 향기가 넘치는 아침을 준비합니다.월요일 코너 <명물열전 당신이 최고야>");

			Type mediaCode = new Type();
			mediaCode.setType("qcode:MediaCode");
			mediaCode.setValue("01");
			basicDescription.setMediaCode(mediaCode);

			groupInformation.setBasicDescription(basicDescription);
			groupInformationTable.setGroupInformation(groupInformation);
			programDescription.setGroupInformationTable(groupInformationTable);

			ProgramInfoarmationTable programInfoarmationTable = new ProgramInfoarmationTable();
			ProgramInformation programInformation = new ProgramInformation();
			basicDescription = new BasicDescription();
			basicDescription.setProgramCode("T2000-0032");
			basicDescription.setProgramId("PS-2012120262-01-000");
			basicDescription.setProgramTitle("아침마당");
			basicDescription.setDomesticForeignKind("국내");

			programInformation.setBasicDescription(basicDescription);
			programInfoarmationTable.setProgramInformation(programInformation);
			programDescription.setProgramInfoarmationTable(programInfoarmationTable);
			main.setProgramDescription(programDescription);

			CopyrightDescription copyrightDescription = new CopyrightDescription();
			CopyrightInformationTable copyrightInformationTable = new CopyrightInformationTable();

			CopyrightInformation copyrightInformation = new CopyrightInformation();
			Type type = new Type();
			type.setType("qcode:Copyright");
			type.setValue("100");
			copyrightInformation.setType(type);

			copyrightInformationTable.setCopyrightInformation(copyrightInformation);
			copyrightDescription.setCopyrightInformationTable(copyrightInformationTable);
			main.setCopyrightDescription(copyrightDescription);

			//System.out.println(xmlStream.toXML(main));

			System.out.println(convertorService.createMarshaller(main));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
