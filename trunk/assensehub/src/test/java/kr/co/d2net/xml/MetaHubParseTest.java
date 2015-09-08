package kr.co.d2net.xml;

import java.io.File;
import java.net.URLDecoder;
import java.util.List;

import kr.co.d2net.commons.converter.xml.PropertyConverter;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.WeekSchTbl;
import kr.co.d2net.dto.xml.meta.Node;
import kr.co.d2net.dto.xml.meta.Nodes;
import kr.co.d2net.dto.xml.meta.Properties;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MetaHubParseTest extends BaseXmlConfig {
	@Autowired
	private XmlStream xmlStream;
	
	@Before
	public void init() {
		try {
			xmlStream.setAnnotationAlias(Node.class);
			xmlStream.setAnnotationAlias(Nodes.class);
			xmlStream.setAnnotationAlias(Properties.class);
			xmlStream.setConverter(new PropertyConverter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void weekPaseTest() {
		try {
			
			Nodes nodes = new Nodes();
			Node node = new Node();
			node.setTid("mh_weekly_schedule");
			node.setNid("51100012062201");
			
			Properties properties = new Properties();
			properties.setValue("T2010-0015");
			properties.setType("REFERENCE");
			properties.setPid("program_code");
			properties.setName("프로그램코드");
			properties.setBase("");
			properties.setEleValue("KBS 바둑왕전");
			node.addProperties(properties);
			
			properties = new Properties();
			properties.setValue("");
			properties.setType("TEXT");
			properties.setPid("program_id_key");
			properties.setName("프로그램 아이디 키");
			properties.setBase("");
			properties.setEleValue("PS-2011120546-01-000");
			node.addProperties(properties);
			
			properties = new Properties();
			properties.setValue("PS-2011120546-01-000");
			properties.setType("REFERENCE");
			properties.setPid("program_id");
			properties.setName("프로그램아이디");
			properties.setBase("");
			properties.setEleValue("KBS 바둑왕전");
			node.addProperties(properties);
			
			nodes.addNode(node);
			
			node = new Node();
			node.setTid("mh_weekly_schedule");
			node.setNid("51100012062200");
			
			properties = new Properties();
			properties.setValue("T2008-0637");
			properties.setType("REFERENCE");
			properties.setPid("program_code");
			properties.setName("프로그램코드");
			properties.setBase("");
			properties.setEleValue("KBS 뉴스(2450)");
			node.addProperties(properties);
			
			nodes.addNode(node);
			
			System.out.println(xmlStream.toXML(nodes));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void weekXmlTest() {
		try {
			String weekXml = FileUtils.readFileToString(new File("D:\\tmp\\aaaa.xml"), "UTF-8");
			Nodes nodes = (Nodes)xmlStream.fromXML(weekXml);
			System.out.println(nodes.getNodeList().size());
			for(Node node : nodes.getNodeList()) {
				List<Properties> propertyList = node.getProperties();
				
				WeekSchTbl bean = new WeekSchTbl();
				for(Properties properties : propertyList) {
					if(properties.getType().equals("CODE") || properties.getType().equals("DATE") || properties.getPid().endsWith("code"))
						Utility.setValue(bean, properties.getPid(), properties.getValue());
					else
						Utility.setValue(bean, properties.getPid(), properties.getEleValue());
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void pgmXmlTest() {
		try {
			String weekXml = FileUtils.readFileToString(new File("D:\\tmp\\pgm.xml"), "UTF-8");
			Node node = (Node)xmlStream.fromXML(weekXml);
			for(Properties properties : node.getProperties()) {
				System.out.println("nid : "+properties.getPid());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void subXmlTest() {
		try {
			String weekXml = FileUtils.readFileToString(new File("D:\\tmp\\sub.xml"), "UTF-8");
			Node node = (Node)xmlStream.fromXML(weekXml);
			
			WeekSchTbl bean = new WeekSchTbl();
			for(Properties properties : node.getProperties()) {
				if(properties.getType().equals("CODE") || properties.getType().equals("DATE") || properties.getPid().endsWith("code"))
					Utility.setValue(bean, properties.getPid(), properties.getValue());
				else
					Utility.setValue(bean, properties.getPid(), properties.getEleValue());
				System.out.println("nid : "+properties.getPid());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void urlDecodeTest() {
		try {
			System.out.println(URLDecoder.decode("%ed%95%b4%ed%94%bc%ed%88%ac%ea%b2%8c%eb%8d%94%5c", "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
