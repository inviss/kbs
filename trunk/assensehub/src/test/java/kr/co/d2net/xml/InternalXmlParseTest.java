package kr.co.d2net.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import kr.co.d2net.commons.util.StringUtil;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.commons.util.XmlFileService;
import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.xml.internal.Ftp;
import kr.co.d2net.dto.xml.internal.Job;
import kr.co.d2net.dto.xml.internal.Option;
import kr.co.d2net.dto.xml.internal.Pgm;
import kr.co.d2net.dto.xml.internal.Status;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.service.ContentsManagerService;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InternalXmlParseTest extends BaseXmlConfig {
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private XmlFileService xmlFileService;
	//@Autowired
	//private ContentsManagerService contentsManagerService;
	
	@Before
	public void init() {
		try {
			xmlStream.setAnnotationAlias(Workflow.class);
			xmlStream.setAnnotationAlias(Status.class);
			xmlStream.setAnnotationAlias(Job.class);
			xmlStream.setAnnotationAlias(Option.class);
			xmlStream.setAnnotationAlias(Ftp.class);
			xmlStream.setAnnotationAlias(Pgm.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void statusXmlTest() {
		try {
			String status = FileUtils.readFileToString(new File("D:\\tmp\\status.xml"), "UTF-8");
			System.out.println(status);
			xmlStream.fromXML(status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void jobPublishTest() {
		try {
			Workflow workflow = new Workflow();
			
			Job job = new Job();
			job.setJobId(1L);
			job.setJobKind("video");
			job.setProFlid(1);
			job.setVdoCodec("1");
			job.setVdoBitRate("1");
			job.setVdoHori(800);
			job.setVdoVeri(600);
			job.setVdoFS("1");
			job.setVdoSync("1");
			job.setAudChan("1");
			job.setAudCodec("1");
			job.setAudBitRate("1");
			job.setAudChan("1");
			job.setAudSRate("1");
			job.setDefaultOpt("-ttt=TTT -fff=FFF");
			job.setSourcePath("1");
			job.setSourceFile("1");
			job.setTargetPath("1");
			job.setTargetFile("1");
			job.setQcYn("Y");
			
			Option option = new Option();
			
			Option option1 = new Option();
			option1.setOptId(1);
			option1.setOptDesc("-aaa=AAA -bbb=BBB -ccc=CCC");
			option.addOptions(option1);
			
			option1 = new Option();
			option1.setOptId(1);
			option1.setOptDesc("-ddd=DDD -eee=EEE");
			option.addOptions(option1);
			
			job.setOption(option);
			
			workflow.setJob(job);
			
			System.out.println(xmlStream.toXML(workflow));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void jobPublishTest2() {
		try {
			Workflow workflow = new Workflow();
			
			Job job = new Job();
			job.setJobId(1L);
			job.setJobKind("video");
			job.setSourceFile("D:\\aaaa");
			job.setTargetPath("/aaa/bbb");
			
			Ftp ftp = new Ftp();
			ftp.setFtpIp("11111");
			ftp.setFtpPort("21");
			ftp.setFtpUser("aaaa");
			job.setFtp(ftp);
			
			workflow.setJob(job);
			
			System.out.println(xmlStream.toXML(workflow));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void scheduleXML() {
		try {
			Workflow workflow = new Workflow();
			Pgm pgm = null;
			
			int m= 0;
			for(int i=0; i<5; i++) {
				pgm = new Pgm();
				pgm.setPgmCd(StringUtil.padLeft(String.valueOf(i+1), "0", 5));
				pgm.setPgmId(StringUtil.padLeft(String.valueOf(i+1), "1", 5));
				pgm.setPgmNm("남자의 자격"+i);
				
				m += 10;
				pgm.setStartTime(Utility.getTime(m));
				
				m += 40;
				pgm.setEndTime(Utility.getTime(m));
				
				pgm.setRegYn("Y");
				pgm.setVdQlty("HD");
				
				workflow.addPgmList(pgm);
				
			}
			
			xmlFileService.StringToFile(xmlStream.toXML(workflow), "D:\\", "schedule.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void qcLogCheck() {
		try {
			File f = new File("D:\\tmp\\7ef92557-c64f-45fb-9aa5-b016e34f133e.mp4_qc.log");
			BufferedReader in = new BufferedReader(new FileReader(f));
			String line = "";
			while((line = in.readLine()) != null) {
				if(line.startsWith("[error]")) {
					continue;
				} else if(line.startsWith("[success]")) {
					break;
				} else {
					System.out.println(line);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void jobListTest() {
		try {
			Workflow workflow = new Workflow();
			workflow.setSourcePath("/mp2/sdfsdfsdfs/sdfsdfsf");
			workflow.setSourceFile("sdfsdfsdf.mxf");
			workflow.setTargetPath("/mp4/sdfsdfsdfsdf/sdfsdfsdf");
			workflow.setQcYn("Y");
			workflow.setDefaultOpt("aa=AA");
			
			Job job = new Job();
			job.setJobId(1L);
			job.setJobKind("video");
			job.setProFlid(1);
			job.setVdoCodec("1");
			job.setVdoBitRate("1");
			job.setVdoHori(800);
			job.setVdoVeri(600);
			job.setVdoFS("1");
			job.setVdoSync("1");
			job.setAudChan("1");
			job.setAudCodec("1");
			job.setAudBitRate("1");
			job.setAudChan("1");
			job.setAudSRate("1");
			//job.setDefaultOpt("-ttt=TTT -fff=FFF");
			//job.setSourcePath("1");
			//job.setSourceFile("1");
			//job.setTargetPath("1");
			job.setTargetFile("1");
			//job.setQcYn("Y");
			
			Option option = new Option();
			
			Option option1 = new Option();
			option1.setOptId(1);
			option1.setOptDesc("-aaa=AAA -bbb=BBB -ccc=CCC");
			option.addOptions(option1);
			
			option1 = new Option();
			option1.setOptId(1);
			option1.setOptDesc("-ddd=DDD -eee=EEE");
			option.addOptions(option1);
			
			job.setOption(option);
			
			workflow.addJobList(job);
			
			job = new Job();
			job.setJobId(1L);
			job.setJobKind("video");
			job.setProFlid(1);
			job.setVdoCodec("1");
			job.setVdoBitRate("1");
			job.setVdoHori(800);
			job.setVdoVeri(600);
			job.setVdoFS("1");
			job.setVdoSync("1");
			job.setAudChan("1");
			job.setAudCodec("1");
			job.setAudBitRate("1");
			job.setAudChan("1");
			job.setAudSRate("1");
			//job.setDefaultOpt("-ttt=TTT -fff=FFF");
			//job.setSourcePath("1");
			//job.setSourceFile("1");
			//job.setTargetPath("1");
			job.setTargetFile("1");
			//job.setQcYn("Y");
			
			option = new Option();
			
			option1 = new Option();
			option1.setOptId(1);
			option1.setOptDesc("-aaa=AAA -bbb=BBB -ccc=CCC");
			option.addOptions(option1);
			
			option1 = new Option();
			option1.setOptId(1);
			option1.setOptDesc("-ddd=DDD -eee=EEE");
			option.addOptions(option1);
			
			job.setOption(option);
			
			workflow.addJobList(job);
			
			job = new Job();
			job.setJobId(1L);
			job.setJobKind("video");
			job.setProFlid(1);
			job.setVdoCodec("1");
			job.setVdoBitRate("1");
			job.setVdoHori(800);
			job.setVdoVeri(600);
			job.setVdoFS("1");
			job.setVdoSync("1");
			job.setAudChan("1");
			job.setAudCodec("1");
			job.setAudBitRate("1");
			job.setAudChan("1");
			job.setAudSRate("1");
			//job.setDefaultOpt("-ttt=TTT -fff=FFF");
			//job.setSourcePath("1");
			//job.setSourceFile("1");
			//job.setTargetPath("1");
			job.setTargetFile("1");
			//job.setQcYn("Y");
			
			option = new Option();
			
			option1 = new Option();
			option1.setOptId(1);
			option1.setOptDesc("-aaa=AAA -bbb=BBB -ccc=CCC");
			option.addOptions(option1);
			
			option1 = new Option();
			option1.setOptId(1);
			option1.setOptDesc("-ddd=DDD -eee=EEE");
			option.addOptions(option1);
			
			job.setOption(option);
			
			workflow.addJobList(job);
			
			System.out.println(xmlStream.toXML(workflow));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
}
