package kr.co.d2net.dao;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.service.ContentsInstManagerService;
import kr.co.d2net.service.ProFlManagerService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class InterfaceTest_ extends BaseDaoConfig {

	private static Log logger = LogFactory.getLog(InterfaceTest_.class);
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ContentsInstManagerService contentsInstManagerService;
	@Autowired
	private ProFlManagerService proFlManagerService;

	
	@Test
	@Ignore
	public void restMetaHubURLTest() throws ServiceException {
		/*
		 * 완료일경우 메타허브에 서비스URL을 등록요청
		 * 트랜스퍼 상태 저장
		 */
		Map<String,Object> params = new HashMap<String, Object>();
		ContentsInstTbl contentsInstTbl = new ContentsInstTbl();
		ProFlTbl proFlTbl = new ProFlTbl();
		
		params.put("seq", Long.parseLong("0"));
		 
		contentsInstTbl = contentsInstManagerService.getContentsInstForwardMeta(params);
		System.out.println("contentsInstTbl.getPgmId():"+contentsInstTbl.getPgmId());
		if(StringUtils.isNotBlank(contentsInstTbl.getAlias())){
			if(contentsInstTbl.getProFlid()!=null) {
				params.put("proFlid", contentsInstTbl.getProFlid());
				proFlTbl = proFlManagerService.getProFl(params);
			}

			params.clear();
			params.put("essence_id","0");                     //에센스 허브 아이디                                  

			params.put("pcode",contentsInstTbl.getPgmCd());                          //프로그램코드               
			params.put("p_id",contentsInstTbl.getPgmId());                           //회별 프로그램 아이디       
			params.put("program_id",contentsInstTbl.getPgmId());                     //프로그램 아이디       \
			System.out.println("contentsInstTbl.getPgmId():"+contentsInstTbl.getPgmId());
			
			params.put("file_size",contentsInstTbl.getFlSz()+"");                        //용량           
			params.put("filename",contentsInstTbl.getWrkFileNm());                         //파일이름       
			params.put("segment_id",contentsInstTbl.getPgmCd()+"-"+contentsInstTbl.getSegmentId());                     //TV일 경우 세그먼트 회차    
			params.put("essence_type1",contentsInstTbl.getCtCla());                  //에센스 타입1               
			params.put("essence_type2",contentsInstTbl.getCtTyp());                  //에센스 타입2               
			params.put("service_biz",contentsInstTbl.getAlias());                      //서비스 사업자              
			
			params.put("program_id_key","");                 //주간 편성 아이디           
			params.put("segment_code","");                   //세그먼트 프로그램 코드     
			params.put("segment_id_radio","");               //라디오일 경우 세그먼트 회차
			params.put("service_status","N");                 //서비스 상태                
			params.put("audio_bitrate",proFlTbl.getAudBitRate());                  //오디오 비트레이트          
			params.put("audio_channel",proFlTbl.getAudChan());                  //오디오 채널                
			params.put("audio_codec",proFlTbl.getAudCodec());                    //오디오 코덱                
			params.put("audio_ext",proFlTbl.getExt());                      //오디오 확장자              
			params.put("audio_keyframe","");                 //keyframe                   
			params.put("audio_sampling",proFlTbl.getAudSRate());                   //오디오 샘플링  
			params.put("bitrate",proFlTbl.getServBit());                          //비트레이트     
			params.put("bitrate_code",proFlTbl.getVdoBitRate());                     //비트레이트코드 
			params.put("file_format",proFlTbl.getExt());                      //파일 타입      
			params.put("framecount","");                       //비디오 F/S     
			params.put("height",proFlTbl.getVdoVert());                           //비디오 세로    
			params.put("video_aspect_ratio","");               //화면 비율      
			params.put("video_codec",proFlTbl.getVdoCodec());                      //비디오 코덱    
			params.put("video_quality_condition",proFlTbl.getPicKind());          //화질표시       
			params.put("video_resoution","");                  //해상도         
			params.put("whaline",proFlTbl.getVdoSync());                          //비디오 종횡맞춤
			params.put("width",proFlTbl.getVdoHori()); //비디오 가로    


			String domain = messageSource.getMessage("meta.system.domain", null,
					Locale.KOREA);
			String createMethod = messageSource.getMessage("meta.system.url.forward.create",
					null, Locale.KOREA);

			String rXml = "";
			try {
				rXml = Utility.connectHttpPostMethod(domain+createMethod,
						Utility.convertNameValue(params));
				System.out.println("return Xml:"+rXml);
			}catch (Exception e) {
				e.printStackTrace();
			}
			if(rXml.indexOf("SUCCESS")>0){
				params.put("metaIns", "C");
			}else{
				params.put("metaIns", "E");
			}

		}

	}

}
