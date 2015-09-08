package test;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import kr.co.d2net.soap.Navigator;
import kr.co.d2net.soap.ServiceNavigatorService;
import kr.co.d2net.soap.ServiceNavigatorServiceLocator;

public class AssenseHubTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServiceNavigatorService navigatorService = new ServiceNavigatorServiceLocator();
			Navigator navigator = navigatorService.getServiceNavigatorPort(new URL("http://127.0.0.1/services/ServiceNavigator"));
			//Navigator navigator = navigatorService.getServiceNavigatorPort();
			
			//System.out.println(navigator.findContents("<workflow><gubun>P</gubun><keyword>해피투게더</keyword></workflow>"));
			
//			String xml = FileUtils.readFileToString(new File("D:\\test\\mov_wmv.xml"), "UTF-8");
//			String xml = FileUtils.readFileToString(new File("D:\\test\\mov_mp4.xml"), "UTF-8");
//			String xml = FileUtils.readFileToString(new File("D:\\test\\mov_avi.xml"), "UTF-8");
//			String xml = FileUtils.readFileToString(new File("D:\\test\\mov_mpg.xml"), "UTF-8");
//			String xml = FileUtils.readFileToString(new File("D:\\test\\mpeg_wmv.xml"), "UTF-8");
//			String xml = FileUtils.readFileToString(new File("D:\\test\\mpeg_mp4.xml"), "UTF-8");
//			String xml = FileUtils.readFileToString(new File("D:\\test\\mpeg_avi.xml"), "UTF-8");
//			String xml = FileUtils.readFileToString(new File("D:\\test\\mxf_wmv.xml"), "UTF-8");
//			String xml = FileUtils.readFileToString(new File("D:\\test\\mxf_mp4.xml"), "UTF-8");
//			String xml = FileUtils.readFileToString(new File("D:\\test\\mxf_avi.xml"), "UTF-8");
//			String xml = FileUtils.readFileToString(new File("D:\\test\\mxf_mpg.xml"), "UTF-8");
			
//			navigator.soapTest(xml);
			
			//String status = FileUtils.readFileToString(new File("D:\\tmp\\status.xml"), "UTF-8");
			
			System.out.println(navigator.findTransfers("<workflow><gubun>W</gubun></workflow>"));
			//System.out.println(navigator.saveStatus("<workflow><status><eq_state>W</eq_state><eq_id>TF01_01</eq_id></status></workflow>"));

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*try {
			kr.co.d2net.gate1.soap.ServiceNavigatorService navigatorService = new kr.co.d2net.gate1.soap.ServiceNavigatorServiceLocator();
			kr.co.d2net.gate1.soap.Navigator navigator = navigatorService.getServiceNavigatorPort(new URL("http://210.115.198.109:8080/services/ServiceNavigator"));
			//System.out.println(navigator.soapTest("aaa"));
			System.out.println(navigator.archiveSearch("<workflow><request><system_id>esh</system_id><system_pass>esh</system_pass><user_id>KdasTest</user_id><curr_page>1</curr_page><item_count>5</item_count><search_field>programName,subtitle,segmentItem</search_field><keyword>kbs</keyword></request></workflow>"));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		/*
		try {
			kr.co.d2net.gate2.soap.ServiceNavigatorService navigatorService = new kr.co.d2net.gate2.soap.ServiceNavigatorServiceLocator();
			kr.co.d2net.gate2.soap.Navigator navigator = navigatorService.getServiceNavigatorPort(new URL("http://10.112.4.251:8080/services/ServiceNavigator"));
			//kr.co.d2net.gate2.soap.Navigator navigator = navigatorService.getServiceNavigatorPort(new URL("http://localhost:8080/services/ServiceNavigator"));
			//System.out.println(navigator.soapTest("aaa"));
			System.out.println(navigator.archiveSearch("<workflow><request><system_id>esh</system_id><system_pass>esh</system_pass><user_id>KdasTest</user_id><curr_page>1</curr_page><item_count>5</item_count><search_field>programName,subtitle,segmentItem</search_field><keyword>kbs</keyword></request></workflow>"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

}
