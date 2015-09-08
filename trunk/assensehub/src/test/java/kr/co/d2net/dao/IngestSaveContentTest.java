package kr.co.d2net.dao;

import java.io.File;

import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.xml.internal.Content;
import kr.co.d2net.dto.xml.internal.Workflow;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IngestSaveContentTest extends BaseDaoConfig {
	
	@Autowired
	private XmlStream xmlStream;
	@Test
	public void saveContent() {
		try {
			String xml = FileUtils.readFileToString(new File("D:\\live.xml"), "UTF-8");
			System.out.println(xml);
			Workflow workflow = (Workflow)xmlStream.fromXML(xml);
			Content content = workflow.getContent();
			System.out.println(content.getCtNm());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
