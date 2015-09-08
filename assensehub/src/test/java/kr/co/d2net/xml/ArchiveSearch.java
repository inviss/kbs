package kr.co.d2net.xml;

import java.net.URL;
import java.net.URLDecoder;

import kr.co.wisenut.colvo.xsd.SearchCollection;

import com.malgn.kbs_va.openapi.KDASUserIFStub;
import com.malgn.kbs_va.openapi.SearchBySearchEngine;
import com.malgn.kbs_va.openapi.SearchBySearchEngineResponse;
import com.malgn.kbs_va.response.xsd.SearchResponse;

public class ArchiveSearch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//KDASUserIFStub stub = new KDASUserIFStub();
			KDASUserIFStub stub = new KDASUserIFStub("http://kdas.kbs.co.kr/services/KDASUserIF.KDASUserIFHttpSoap12Endpoint/");
			//KDASUserIFStub stub = new KDASUserIFStub("http://10.112.4.35/services/KDASUserIF.KDASUserIFHttpSoap12Endpoint/");

			SearchBySearchEngine soapRequest = new SearchBySearchEngine();
			soapRequest.setRequest_system_ID("esh");
			soapRequest.setRequest_system_password("esh");
			soapRequest.setUser_ID("KdasTest");
			soapRequest.setCurrent_page(1);
			soapRequest.setEnd_date("");
			//soapRequest.setGenre1_code("");
			//soapRequest.setGenre2_code("");
			soapRequest.setItem_count(10);
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
			
			SearchBySearchEngineResponse response = stub.searchBySearchEngine(soapRequest);
			SearchResponse searchResponse = response.get_return();
			
			System.out.println("===================== 프로그램 =============================================");
			
			SearchCollection[]  searchCollections = searchResponse.getPro_st_result();
			
			if(searchCollections != null) {
				for(SearchCollection searchCollection : searchCollections) {
					System.out.println("searchCollection.getLength() : "+URLDecoder.decode(searchCollection.getLength(), "UTF-8"));
					System.out.println("searchCollection.getShootingLocation() : "+searchCollection.getShootingLocation());
					System.out.println("searchCollection.getLinkId() : "+searchCollection.getLinkId());
					System.out.println("searchCollection.getLinkIdSegmentId() : "+searchCollection.getLinkIdSegmentId());
					System.out.println("searchCollection.getProgramTitle() : "+URLDecoder.decode(searchCollection.getProgramName(), "UTF-8"));
					System.out.println("searchCollection.getViewTitle() : "+URLDecoder.decode(searchCollection.getViewTitle(), "UTF-8"));
					System.out.println("searchCollection.getChannel() : "+searchCollection.getChannel());
					System.out.println("searchCollection.getOnairDate() : "+searchCollection.getOnairDate());
					System.out.println("searchCollection.getCountry() : "+searchCollection.getCountry());
					System.out.println("#######################################################");
				}
			}
			
			System.out.println("===================== 뉴스 =============================================");
			
			searchCollections = searchResponse.getNews_st_result();
			if(searchCollections != null) {
				for(SearchCollection searchCollection : searchCollections) {
					System.out.println("searchCollection.getLength() : "+URLDecoder.decode(searchCollection.getLength(), "UTF-8"));
					System.out.println("searchCollection.getShootingLocation() : "+searchCollection.getShootingLocation());
					System.out.println("searchCollection.getLinkId() : "+searchCollection.getLinkId());
					System.out.println("searchCollection.getLinkIdSegmentId() : "+searchCollection.getLinkIdSegmentId());
					System.out.println("searchCollection.getProgramTitle() : "+URLDecoder.decode(searchCollection.getProgramName(), "UTF-8"));
					System.out.println("searchCollection.getViewTitle() : "+URLDecoder.decode(searchCollection.getViewTitle(), "UTF-8"));
					System.out.println("searchCollection.getChannel() : "+searchCollection.getChannel());
					System.out.println("searchCollection.getOnairDate() : "+searchCollection.getOnairDate());
					System.out.println("searchCollection.getCountry() : "+searchCollection.getCountry());
					
					System.out.println("searchCollection.getControl() : "+searchCollection.getControl());
					System.out.println("searchCollection.getCountry() : "+searchCollection.getDirect1());
					System.out.println("searchCollection.getCountry() : "+searchCollection.getFileCueSheet());
					System.out.println("searchCollection.getCountry() : "+URLDecoder.decode(searchCollection.getOrganize(), "UTF-8")); // 부제
					System.out.println("searchCollection.getCountry() : "+searchCollection.getTextSummary());
					System.out.println("searchCollection.getCountry() : "+searchCollection.getTextContent());
					System.out.println("#######################################################");
				}
			}
			/*
			System.out.println("===================== 촬영본 =============================================");
			
			searchCollections = searchResponse.getShooting_st_result();
			if(searchCollections != null) {
				for(SearchCollection searchCollection : searchCollections) {
					System.out.println("searchCollection.getLength() : "+URLDecoder.decode(searchCollection.getLength(), "UTF-8"));
					System.out.println("searchCollection.getShootingLocation() : "+searchCollection.getShootingLocation());
					System.out.println("searchCollection.getLinkId() : "+searchCollection.getLinkId());
					System.out.println("searchCollection.getLinkIdSegmentId() : "+searchCollection.getLinkIdSegmentId());
					System.out.println("searchCollection.getProgramTitle() : "+URLDecoder.decode(searchCollection.getProgramTitle(), "UTF-8"));
					System.out.println("searchCollection.getViewTitle() : "+URLDecoder.decode(searchCollection.getViewTitle(), "UTF-8"));
					System.out.println("searchCollection.getChannel() : "+searchCollection.getChannel());
					System.out.println("searchCollection.getOnairDate() : "+searchCollection.getOnairDate());
					System.out.println("searchCollection.getCountry() : "+searchCollection.getCountry());
					System.out.println("#######################################################");
				}
			}
			
			System.out.println("===================== 취재본 =============================================");
			searchCollections = searchResponse.getCover_st_result();
			if(searchCollections != null) {
				for(SearchCollection searchCollection : searchCollections) {
					System.out.println("searchCollection.getLength() : "+URLDecoder.decode(searchCollection.getLength(), "UTF-8"));
					System.out.println("searchCollection.getShootingLocation() : "+searchCollection.getShootingLocation());
					System.out.println("searchCollection.getLinkId() : "+searchCollection.getLinkId());
					System.out.println("searchCollection.getLinkIdSegmentId() : "+searchCollection.getLinkIdSegmentId());
					System.out.println("searchCollection.getProgramTitle() : "+URLDecoder.decode(searchCollection.getProgramTitle(), "UTF-8"));
					System.out.println("searchCollection.getViewTitle() : "+URLDecoder.decode(searchCollection.getViewTitle(), "UTF-8"));
					System.out.println("searchCollection.getChannel() : "+searchCollection.getChannel());
					System.out.println("searchCollection.getOnairDate() : "+searchCollection.getOnairDate());
					System.out.println("searchCollection.getCountry() : "+searchCollection.getCountry());
					System.out.println("#######################################################");
				}
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
