package kr.co.d2net.rest;

import java.util.HashMap;
import java.util.Map;

import kr.co.d2net.dto.Method;
import kr.co.d2net.dto.Workflow;
import kr.co.d2net.service.RestfulService;
import kr.co.d2net.service.RestfulServiceImpl;

public class RestCallTest {

	public static void main(String[] args) {
		try {
			RestfulService restfulService  = new RestfulServiceImpl();
			if(args[0].equals("get"))
				restfulService.get("http://"+args[1]+":"+args[2]+"/services/"+args[3], null, Workflow.class);
			else {
				
				Workflow workflow = new Workflow();
				workflow.setCcLocation("http://ppsnas.kbs.co.kr/ppsfs/PPS/PD/201311/e8cedab4-90df-45cb-b8a8-6322f6552f19.smi");
				workflow.setSystemId("VAS0000001");
				workflow.setWatchFolder("c:/video_archive");
				workflow.setVideoLocation("/KBS_VA_LIBRARY/PROGRAM/201409/412013500/PS-2014169105-01-000_다큐공감.mp4");
				workflow.setProgramId("PS-2014169105-01-000");
				workflow.setCcFileName("11201600사랑의 가족PS-2014169105-01-000.smi");
				workflow.setSyncCcLocation("C:\\video_archive\\PS-2014169105-01-000_FIXED.smi");
				workflow.setUploadSystem("01");
				restfulService.method(Method.JSON);
				
				restfulService.post("http://"+args[1]+":"+args[2]+"/services/"+args[3], null, workflow);
				
				
				//
				//								Map<String, Object> params = new HashMap<String, Object>();
				//								params.put("request_system_ID",  "VAS0000001");
				//								params.put("request_system_password",  "1111");
				//								params.put("user_ID",  "");
				//								params.put("caption_language_kind",  "K");
				//								params.put("program_ID",  "PS-2013210672-01-000"); 
				//								params.put("file_format",  "01");
				//								params.put("caption_file_path",  "/");
				//								params.put("copyright_special_note",  "");
				//								params.put("caption_file_description",  "TEST");
				//								params.put("synchronized_YN",  "Y");
				//								//restfulService.method(Method.JSON);
				//								restfulService.post("http://ppsx.kbs.co.kr/KBS_PPS_IF.asmx/PPS_INSERT_CAPTION_V1", params, null);


				//				Map<String, Object> params = new HashMap<String, Object>();
				//				params.put("program_id",  "ps-2013210672-01-000");
				//				params.put("segment_id",  "");
				//				params.put("caption_language_code",  "K");
				//				params.put("file_format",  "SMI");
				//				params.put("caption_version",  "1.0a");
				//				params.put("copyright_special_note",  "");
				//				params.put("caption_file_full_path",  "");
				//				params.put("caption_max_width",  "20");
				//				params.put("caption_max_height",  "1");
				//				
				//				restfulService.method(Method.JSON);
				//				restfulService.post("http://211.233.93.58/api/mh_closed_caption/caption.json", params, null);


				//restfulService.post("http://"+args[1]+":"+args[2]+"/services/"+args[3], params, null);
				//kbs = euc-kr
				//
				//restfulService.post("http://ppsx.kbs.co.kr/KBS_PPS_IF.asmx?op=PPS_INSERT_CAPTION_V1", params, null);
			}
		} catch (Exception e) {
			System.out.print("main " + e);
		}

	}

}
