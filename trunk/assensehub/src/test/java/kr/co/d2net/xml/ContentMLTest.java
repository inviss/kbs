package kr.co.d2net.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.converter.xml.TypeConverter;
import kr.co.d2net.commons.util.XmlFileService;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.TransferHisTbl;
import kr.co.d2net.dto.xml.BasicDescription;
import kr.co.d2net.dto.xml.GroupInformation;
import kr.co.d2net.dto.xml.GroupInformationTable;
import kr.co.d2net.dto.xml.InstanceDescription;
import kr.co.d2net.dto.xml.MemberOf;
import kr.co.d2net.dto.xml.ProgramDescription;
import kr.co.d2net.dto.xml.ProgramInfoarmationTable;
import kr.co.d2net.dto.xml.ProgramInformation;
import kr.co.d2net.dto.xml.ServiceInformation;
import kr.co.d2net.dto.xml.ServiceInformationTable;
import kr.co.d2net.dto.xml.TvaMain;
import kr.co.d2net.service.ContentsManagerService;
import kr.co.d2net.service.WorkflowManagerService;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ContentMLTest extends BaseXmlConfig {
	
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private ContentsManagerService contentsManagerService;
	@Autowired
	private XmlFileService xmlFileService;
	@Autowired
	private WorkflowManagerService workflowManagerService;
	
	@Before
	public void init() {
		try {
			xmlStream.setAnnotationAlias(TvaMain.class);
			xmlStream.setAnnotationAlias(BasicDescription.class);
			xmlStream.setAnnotationAlias(GroupInformation.class);
			xmlStream.setAnnotationAlias(GroupInformationTable.class);
			xmlStream.setAnnotationAlias(InstanceDescription.class);
			xmlStream.setAnnotationAlias(MemberOf.class);
			xmlStream.setAnnotationAlias(ProgramDescription.class);
			xmlStream.setAnnotationAlias(ProgramInfoarmationTable.class);
			xmlStream.setAnnotationAlias(ProgramInformation.class);
			xmlStream.setAnnotationAlias(ServiceInformation.class);
			xmlStream.setAnnotationAlias(ServiceInformationTable.class);
			xmlStream.setConverter(new TypeConverter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void createContentMLTest() {
		try {
			// 600k[373], 500k[386], 4M[374]
			String contentML = contentsManagerService.createContentML("PS-2013068351-01-000", 373);
			
			xmlFileService.StringToFile(contentML, "D:\\tmp", "aaaa.xml");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore                                         
	@Test
	public void mapContainTest() {
		try {
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("aa", "bb");
			
			if(maps.containsKey("aa"))
				System.out.println(maps.get("aa"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void createSMIL() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("busiPartnerId", "544"); // 사업자 ID
			params.put("ctiId", 227837); // 원본영상 ID
			
			List<TransferHisTbl> transferHisTbls = workflowManagerService.findTrsCtiBusi(params);
			String smilXml = contentsManagerService.createSMIL(transferHisTbls);
			System.out.println(smilXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
