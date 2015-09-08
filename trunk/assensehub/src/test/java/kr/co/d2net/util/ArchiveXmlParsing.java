package kr.co.d2net.util;

import java.io.File;

import kr.co.d2net.commons.util.Utility;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Element;

public class ArchiveXmlParsing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String xml = FileUtils.readFileToString(new File("D:\\tmp\\1201190108.xml"), "UTF-8");
			Element element = Utility.getChildElementByTagName((Element)Utility.stringToDom(xml).getChildNodes().item(0), "EXCHANGE_METADATA_INFO");
			//System.out.println(element);
			//element = Utility.getChildElementByTagName(element, "EXCHANGE_METADATA_INFO");
			//System.out.println(element);
			String searchType = Utility.getChildElementValueByTagName(element, "EXCHANGE_TYPE");
			
			String som = Utility.getChildElementValueByTagName(element, "EXCHANGE_FILE_START_TIMECODE");
			String eom = Utility.getChildElementValueByTagName(element, "EXCHANGE_FILE_END_TIMECODE");
			System.out.println("som : "+som);
			System.out.println("eom : "+eom);
			
			element = Utility.getChildElementByTagName((Element)Utility.stringToDom(xml).getChildNodes().item(0), "MEDIA_INFO");
			String vCodec = Utility.getChildElementValueByTagName(element, "VIDEO_CODEC");
			System.out.println("v_codec : "+vCodec);
			
			String[] resolution = Utility.getChildElementValueByTagName(element, "VIDEO_RESOLUTION").split("x");
			System.out.println("resolution[0] : "+resolution[0]);
			System.out.println("resolution[1] : "+resolution[1]);
			
			String vFps = Utility.getChildElementValueByTagName(element, "VIDEO_FPS");
			System.out.println("vFps : "+vFps);
			
			String audio_samp_rate = Utility.getChildElementValueByTagName(element, "AUDIO_SAMPLE_RATE");
			System.out.println("audio_samp_rate : "+audio_samp_rate);
			
			element = Utility.getChildElementByTagName((Element)Utility.stringToDom(xml).getChildNodes().item(0), "PROGRAM_INFO");
			
			String program_code = Utility.getChildElementValueByTagName(element, "PROGRAM_CODE");
			System.out.println("program_code : "+program_code);
			
			String program_title = Utility.getChildElementValueByTagName(element, "PROGRAM_TITLE");
			System.out.println("program_title : "+program_title);
			
			if(searchType.equals("10") || searchType.equals("20")) {
				element = Utility.getChildElementByTagName((Element)Utility.stringToDom(xml).getChildNodes().item(0), "EPISODE_INFO");
				
				String program_id = Utility.getChildElementValueByTagName(element, "PROGRAM_ID");
				System.out.println("program_id : "+program_id);
				
				String vdQlty = Utility.getChildElementValueByTagName(element, "PRODUCTION_VIDEO_QULITY");
				System.out.println("vdQlty : "+vdQlty);
				
			} else if(searchType.equals("30") || searchType.equals("40")) {
				element = Utility.getChildElementByTagName((Element)Utility.stringToDom(xml).getChildNodes().item(0), "RAW_MATERIAL_INFO");
				
				program_title = Utility.getChildElementValueByTagName(element, "PROGRAM_TITLE");
				System.out.println("program_title : "+program_title);
				
				String vdQlty = Utility.getChildElementValueByTagName(element, "PRODUCTION_VIDEO_QUALITY");
				System.out.println("vdQlty : "+vdQlty);
			}
			System.out.println(searchType);
			//System.out.println(Utility.getChildElementValueByTagName(element, "EXCHANGE_FILE_START_TIMECODE"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
