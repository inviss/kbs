package kr.co.d2net.xml;

import java.io.File;
import java.net.URLDecoder;
import java.util.Locale;

import kr.co.d2net.commons.util.XmlStream;
import kr.co.d2net.dto.xml.archive.ArchiveReq;
import kr.co.d2net.dto.xml.archive.ArchiveRes;
import kr.co.d2net.dto.xml.archive.ExchangeMetadataInfo;
import kr.co.d2net.dto.xml.archive.ForeignNewsInfo;
import kr.co.d2net.dto.xml.archive.KBSExchandeMetadata;
import kr.co.d2net.dto.xml.archive.MediaInfo;
import kr.co.d2net.dto.xml.archive.ProgramInfo;
import kr.co.d2net.dto.xml.archive.RawMeterialInfo;
import kr.co.d2net.dto.xml.dmcr.KBSExchange;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.wisenut.colvo.xsd.SearchCollection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.malgn.aesDemo.AESUtil;
import com.malgn.kbs_va.openapi.KDASUserIFStub;
import com.malgn.kbs_va.openapi.SearchBySearchEngine;
import com.malgn.kbs_va.openapi.SearchBySearchEngineResponse;
import com.malgn.kbs_va.response.xsd.SearchResponse;

public class VideoArchiveTest extends BaseXmlConfig {

	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private MessageSource messageSource;

	@Before
	public void init() {
		try {
			xmlStream.setAnnotationAlias(Workflow.class);
			xmlStream.setAnnotationAlias(ArchiveReq.class);
			xmlStream.setAnnotationAlias(ArchiveRes.class);
			xmlStream.setAnnotationAlias(KBSExchandeMetadata.class);
			xmlStream.setAnnotationAlias(ExchangeMetadataInfo.class);
			xmlStream.setAnnotationAlias(ForeignNewsInfo.class);
			xmlStream.setAnnotationAlias(MediaInfo.class);
			xmlStream.setAnnotationAlias(ProgramInfo.class);
			xmlStream.setAnnotationAlias(RawMeterialInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void archiveXmlTest() {
		try {
			String weekXml = FileUtils.readFileToString(new File("d:/kdas.xml"), "UTF-8"); // Y12130003741.xml
			KBSExchandeMetadata kbsExchandeMetadata = (KBSExchandeMetadata)xmlStream.fromXML(weekXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void archiveSoapTest() {
		try {
			KDASUserIFStub stub = new KDASUserIFStub();
			
			String aseKey = messageSource.getMessage("aes.key", null, Locale.KOREA);
			String aseIv = messageSource.getMessage("aes.iv", null, Locale.KOREA);
			
			AESUtil aesUtil = new AESUtil();
			//String userId = aesUtil.doEncrypt("KdasTest", aseKey, aseIv);
			//String userPass = aesUtil.doEncrypt("kd@sTest", aseKey, aseIv);
			
			String userId = aesUtil.doEncrypt("D1238", aseKey, aseIv);
			String userPass = aesUtil.doEncrypt("000D1238", aseKey, aseIv);

			SearchBySearchEngine soapRequest = new SearchBySearchEngine();
			soapRequest.setRequest_system_ID("esh");
			soapRequest.setRequest_system_password("esh");
			soapRequest.setUser_ID("kbsi1");
			soapRequest.setCurrent_page(1);
			soapRequest.setEnd_date("");
			//soapRequest.setGenre1_code("");
			//soapRequest.setGenre2_code("");
			soapRequest.setItem_count(1);
			soapRequest.setSearch_column("coverItem,coverSt,foreignItem,foreignSt,newsItem,newsSt,proSegment,proSt,shootingSegment,shootingSt,contentsSt");
			soapRequest.setSearch_field("programName,subtitle,segmentItem,organize,material,textContent,textSummary,textFiled,keyworkd,control,mds");
			soapRequest.setSearch_field2("TITLE,subtitle,segmentItem,organize,material,CONTENTS,keyworkd,control,mds");
			soapRequest.setSearch_string("kbs");
			soapRequest.setSort_column("");
			soapRequest.setSort_order("");
			soapRequest.setStart_date("");
			soapRequest.setMorpheme("");
			//soapRequest.setGenre(new String[] { "", "", "", "", "", "", "", "","", "PS-2011039125-01-000" });
			soapRequest.setGenre(new String[] { "", "", "", "", "", "", "", "","", "" });

			Workflow workflow = new Workflow();
			try {
				ArchiveRes archiveRes = new ArchiveRes();
				
				SearchBySearchEngineResponse response = stub.searchBySearchEngine(soapRequest);
				SearchResponse searchResponse = response.get_return();
				ArchiveRes result = null;
				
				String pageIp = "10.112.4.36";
				String pagePort = "80";
				
				// 회별 프로그램
				int count = searchResponse.getPro_st_result_count();
				archiveRes.setProCount(count);
				SearchCollection[]  searchCollections = searchResponse.getPro_st_result();
				if(searchCollections != null) {
					for(SearchCollection searchCollection : searchCollections) {
						result = new ArchiveRes();
						result.setLength(searchCollection.getLength());
						result.setShootingLocation(searchCollection.getShootingLocation());
						result.setProgramIdlinkId(searchCollection.getLinkId());
						result.setLinkIdSegmentId(searchCollection.getLinkIdSegmentId());
						result.setProgramTitle(URLDecoder.decode(StringUtils.defaultString(searchCollection.getProgramTitle(), ""), "UTF-8"));
						result.setViewTitle(URLDecoder.decode(StringUtils.defaultString(searchCollection.getViewTitle(), ""), "UTF-8"));
						result.setChannel(searchCollection.getChannel());
						result.setOnairDate(searchCollection.getOnairDate());
						result.setCountry(searchCollection.getCountry());
						result.setPageURL("http://"+pageIp+":"+pagePort+"/GEN/VGEN0500SC?type=broadcastProgram&id="+searchCollection.getLinkId()+"&typeCode=20&cat=overview&userId="+userId+"&userPassword="+userPass);
						System.out.println(result.getPageURL());
						archiveRes.addProResult(result);
					}
				}
				
				// 회별 뉴스
				count = searchResponse.getNews_st_result_count();
				archiveRes.setNewsCount(count);
				searchCollections = searchResponse.getNews_st_result();
				if(searchCollections != null) {
					for(SearchCollection searchCollection : searchCollections) {
						result = new ArchiveRes();
						result.setLength(searchCollection.getLength());
						result.setShootingLocation(searchCollection.getShootingLocation());
						result.setProgramIdlinkId(searchCollection.getLinkId());
						result.setLinkIdSegmentId(searchCollection.getLinkIdSegmentId());
						result.setProgramTitle(URLDecoder.decode(StringUtils.defaultString(searchCollection.getProgramTitle(), ""), "UTF-8"));
						result.setViewTitle(URLDecoder.decode(StringUtils.defaultString(searchCollection.getViewTitle(), ""), "UTF-8"));
						result.setChannel(searchCollection.getChannel());
						result.setOnairDate(searchCollection.getOnairDate());
						result.setCountry(searchCollection.getCountry());
						result.setPageURL("http://"+pageIp+":"+pagePort+"/GEN/VGEN0500SC?type=broadcastNews&id="+searchCollection.getLinkId()+"&typeCode=20&cat=overview&userId="+userId+"&userPassword="+userPass);
						
						System.out.println(result.getPageURL());
						archiveRes.addNewsResult(result);
					}
				}
				
				workflow.setArchiveRes(archiveRes);
				workflow.setArchiveReq(null);
				
				String retXml = xmlStream.toXML(workflow);
				System.out.println(retXml);
			} catch (Exception e) { 
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
