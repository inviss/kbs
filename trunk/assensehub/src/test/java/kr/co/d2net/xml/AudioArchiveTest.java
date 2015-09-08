package kr.co.d2net.xml;

import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.xml.aach.Corner;
import kr.co.d2net.dto.xml.aach.Corners;
import kr.co.d2net.dto.xml.internal.Content;
import kr.co.d2net.dto.xml.internal.Workflow;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AudioArchiveTest extends BaseXmlConfig {
	
	@Autowired
	private XmlStream xmlStream;
	
	@Before
	public void init() {
		try {
			xmlStream.setAnnotationAlias(Workflow.class);
			xmlStream.setAnnotationAlias(Content.class);
			xmlStream.setAnnotationAlias(Corners.class);
			xmlStream.setAnnotationAlias(Corner.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void createXML() {
		try {
			Workflow workflow = new Workflow();
			
			Content content = new Content();
			content.setCtId(12345L);
			
			Corners corners = new Corners();
			Corner corner = new Corner();
			corner.setSegmentId("S00001");
			corner.setCornerStartTime("00:00:30");
			corner.setCornerEndTime("00:30:30");
			corners.addCornerList(corner);
			
			corner = new Corner();
			corner.setSegmentId("S00002");
			corner.setCornerStartTime("00:00:30");
			corner.setCornerEndTime("00:30:30");
			corners.addCornerList(corner);
			
			corner = new Corner();
			corner.setSegmentId("S00003");
			corner.setCornerStartTime("00:00:30");
			corner.setCornerEndTime("00:30:30");
			corners.addCornerList(corner);
			
			corner = new Corner();
			corner.setSegmentId("S00004");
			corner.setCornerStartTime("00:00:30");
			corner.setCornerEndTime("00:30:30");
			corners.addCornerList(corner);
			
			content.setCorners(corners);
			workflow.setContent(content);
			
			System.out.println(xmlStream.toXML(workflow));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
