package kr.co.d2net.task.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dao.OptDao;
import kr.co.d2net.dao.ProOptDao;
import kr.co.d2net.dto.OptTbl;
import kr.co.d2net.dto.ProOptTbl;
import kr.co.d2net.dto.TranscorderHisTbl;
import kr.co.d2net.dto.xml.internal.Job;
import kr.co.d2net.dto.xml.internal.Option;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.service.BusiPartnerPgmManagerService;
import kr.co.d2net.service.WorkflowManagerService;
import kr.co.d2net.task.Worker;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("transcodeControlWorker")
public class TranscodeControlWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WorkflowManagerService workflowManagerService;
	@Autowired
	private OptDao optDao;
	@Autowired
	private ProOptDao proOptDao;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private BusiPartnerPgmManagerService busiPartnerPgmManagerService;

	private Map<String, Object> params = new HashMap<String, Object>();

	@Override
	public void work() {

		try {

			int size = TranscodeJobControl.getSize();
			if(logger.isInfoEnabled()) {
				logger.info("Transcode Video Job size - "+size);
			}

			List<Long> ctiIds = null;
			if(size <= 0) {
				ctiIds = workflowManagerService.findTraHisCtiId("001", "V");
				if(ctiIds == null || ctiIds.size() <= 0) {
					ctiIds = workflowManagerService.findTraHisCtiId("000", "V");
				}
			} else {
				ctiIds = workflowManagerService.findTraHisCtiId("000", "V");
			}

			String mnt2 = messageSource.getMessage("mnt2.mount.drive", null, null);
			String mnt = messageSource.getMessage("mnt.mount.drive", null, null);

			boolean header = true;
			
			String audioModeMain = null;
			for(Long ctiId : ctiIds) {
				Workflow workflow = new Workflow();
				workflow.setCtiId(ctiId);

				List<TranscorderHisTbl> transcorderHisTbls = workflowManagerService.findTraHisJobCtiId(ctiId);
				for(TranscorderHisTbl transcorderHisTbl :  transcorderHisTbls) {

					if(header) {

						if(transcorderHisTbl.getRegrid().equals("RMS")) {
							workflow.setSourcePath(mnt2+":\\"+transcorderHisTbl.getFlPath().replaceAll("\\/", "\\\\"));
						} else {
							workflow.setSourcePath(mnt+":\\"+transcorderHisTbl.getFlPath().replaceAll("\\/", "\\\\"));
						}

						workflow.setSourceFile(transcorderHisTbl.getOrgFileNm()+"." + transcorderHisTbl.getOrgFlExt());
						if(transcorderHisTbl.getRegrid().equals("RMS")) {
							workflow.setTargetPath(mnt2+":\\"+transcorderHisTbl.getFlPath()
									.replace("rms", "mp4")
									.replace("source", "target")
									.replaceAll("\\/", "\\\\"));
						} else {
							workflow.setTargetPath(mnt2+":\\"+transcorderHisTbl.getFlPath()
									.replace("mp2", "mp4")
									.replace("source", "target")
									.replaceAll("\\/", "\\\\"));
						}
						
						// 2013.02.22 추가
						// 스트레오일 경우 1~4 채널을 모두 muxing할때 약간의 오차때문에 잡음이 생김. 처리를 위해 모드 추가
						// 스트레오는 1,2 만 처리, 5.1의 경우 1~6만 처리함 5.1의 경우 스트레오가 7,8 채널에 담겨옴.
						if(StringUtils.defaultString(transcorderHisTbl.getAvGubun(), "V").equals("V")) {
							audioModeMain = busiPartnerPgmManagerService.getPgmAudioMode(transcorderHisTbl.getPgmCd(),transcorderHisTbl.getCtTyp());
							audioModeMain = StringUtils.defaultIfEmpty(audioModeMain, "02"); // Stereo
							workflow.setAudioMode(audioModeMain);
						}

						// 지역국 영상 처리를 위해 추가
						if(StringUtils.isNotBlank(transcorderHisTbl.getStartTime())) {
							workflow.setStartTime(transcorderHisTbl.getStartTime());
						}
						if(StringUtils.isNotBlank(transcorderHisTbl.getEndTime())) {
							workflow.setEndTime(transcorderHisTbl.getEndTime());
						}
						
						header = false;
					}

					Job job = new Job();
					job.setJobId(transcorderHisTbl.getSeq());

					if(StringUtils.defaultString(transcorderHisTbl.getAvGubun(), "V").equals("V")) {
						if((transcorderHisTbl.getVdoHori() == null || transcorderHisTbl.getVdoHori() <= 0) 
								&& (transcorderHisTbl.getVdoVert() == null || transcorderHisTbl.getVdoVert()  <= 0) )
							job.setJobKind("audio");
						else 
							job.setJobKind("video");
					} else
						job.setJobKind("audio");

					job.setProFlid(transcorderHisTbl.getProFlid());
					job.setServBit(transcorderHisTbl.getServBit());
					job.setExt(transcorderHisTbl.getNewFlExt());
					job.setVdoCodec(transcorderHisTbl.getVdoCodec());
					job.setVdoBitRate(transcorderHisTbl.getVdoBitRate());
					job.setVdoHori(transcorderHisTbl.getVdoHori());
					job.setVdoVeri(transcorderHisTbl.getVdoVert());
					job.setVdoFS(transcorderHisTbl.getVdoFS());
					job.setVdoSync(transcorderHisTbl.getVdoSync());
					job.setAudCodec(transcorderHisTbl.getAudCodec());
					job.setAudBitRate(transcorderHisTbl.getAudBitRate());
					job.setAudChan(transcorderHisTbl.getAudChan());
					job.setAudSRate(transcorderHisTbl.getAudSRate());
					job.setDefaultOpt(transcorderHisTbl.getDefaultOpt());

					if(job.getJobKind().equals("video")) {
						job.setQcYn("Y");
						job.setTargetFile(transcorderHisTbl.getOrgFileNm()+"_"+transcorderHisTbl.getServBit()+"_"+transcorderHisTbl.getPicKind()+ "." + transcorderHisTbl.getNewFlExt());
					} else {
						job.setQcYn("N");
						job.setTargetFile(transcorderHisTbl.getOrgFileNm()+"_"+transcorderHisTbl.getServBit()+"." + transcorderHisTbl.getNewFlExt());
					}

					logger.debug("job id: "+job.getJobId()+", ========>"+job.getTargetFile());

					/* 요청 추가된 작업에 대한 상태 변경 */
					transcorderHisTbl.setModDt(Utility.getTimestamp());
					transcorderHisTbl.setWorkStatcd("001"); // 대기상태[00] -> 요청상태 변경[01]
					workflowManagerService.updateTranscorderHisState(transcorderHisTbl);

					params.clear();

					// Default Option 조회
					params.put("defaultYn", "Y");
					OptTbl optTbl = optDao.getOpt(params);
					if(optTbl != null && StringUtils.isNotBlank(optTbl.getOptInfo())) {
						workflow.setDefaultOpt(optTbl.getOptDesc());
					}

					// 프로파일과 관련된 옵션 조회 및 설정
					params.put("proFlid", transcorderHisTbl.getProFlid());
					List<ProOptTbl> optTbls = proOptDao.findProOpt(params);
					if(optTbls != null && !optTbls.isEmpty()) {
						transcorderHisTbl.setOptions(optTbls);
						Option option = new Option();
						for(ProOptTbl proOptTbl : optTbls) {
							Option o = new Option();
							o.setOptId(proOptTbl.getOptId());
							o.setOptDesc(proOptTbl.getOptDesc());
							option.addOptions(o);
						}
						job.setOption(option);
					}
					
					workflow.addJobList(job);
				}

				TranscodeJobControl.putJob(workflow);

				header = true;
			}

		} catch (Exception e) {
			logger.error("[Transcode Job Control] TransferJobSwap Error", e);
		}

	}
}
