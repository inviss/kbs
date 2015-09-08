package kr.co.d2net.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TranscorderHisTbl {

	private String useYn; // 사용여부
	private Long seq; //
	private String seq2; //
	private Timestamp regDt; // 등록일시
	private String regDate; // 등록일시
	private String regrid; // 등록자ID
	private Timestamp modDt; // 수정일시
	private String modrid; // 수정자ID
	private Timestamp workStrDt; // 작업시작일시

	private String eqPsCd; // 장비프로세스코드
	private String priority; // 우선순위
	private Integer proFlid; // 프로파일 ID
	private String deviceid; // 장비 Id

	private Long ctiId; // 콘텐츠인스턴스ID
	private String workStatcd; // 장비상태코드
	private String reqUsrid; // 요청자ID

	private String prgrs; // 진행률
	private Long downVol; // 총사이즈
	private String flPath; // 파일경로
	private Timestamp workEndDt; // 작업완료일시

	private String ctNm; // 콘텐츠 명
	private String orgFileNm; // 파일명
	private String proFlnm; // 프로파일명
	private String deviceNm; // 장비명
	private String deviceIp; // 장비IP
	private String flExt; // 확장자
	private String jobGubun; // 잡 구분

	/* 트랜스코더 작업할당 추가 */
	private String bitRt;
	private String orgFlExt;
	private String newFlExt;
	private String pgmGrpCd;
	private String pgmCd;
	private String pgmId;
	private String pgmNm;
	private String segmentId;
	private String segmentNm;
	private String vdoCodec;
	private String vdoBitRate;
	private Integer vdoHori;
	private Integer vdoVert;
	private String vdoFS;
	private String vdoSync;
	private String audCodec;
	private String audBitRate;
	private String audChan;
	private String audSRate;
	private String defaultOpt;
	private String ctiFmt;
	private String strRegDt;
	private String strModDt;

	/* job_id 조회 추가 */
	private String drpFrmYn;
	private String colorCd;
	private Long ctId;
	private String picKind;
	private String servBit;
	private String avGubun;
	
	/* 프로파일 인스턴스 ID 추가 */
	private Long wrkCtiId;
	
	/* qc 여부 */
	private Integer cnt;

	private String clfNm;
	
	private String channel;
	private String audioModeMain;
	private String startTime;
	private String endTime;
	
	private String ctTyp;
	private String local;
	
	
	public String getPgmCd() {
		return pgmCd;
	}

	public void setPgmCd(String pgmCd) {
		this.pgmCd = pgmCd;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getSeq2() {
		return seq2;
	}

	public void setSeq2(String seq2) {
		this.seq2 = seq2;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getCtTyp() {
		return ctTyp;
	}

	public void setCtTyp(String ctTyp) {
		this.ctTyp = ctTyp;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAudioModeMain() {
		return audioModeMain;
	}

	public void setAudioModeMain(String audioModeMain) {
		this.audioModeMain = audioModeMain;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getClfNm() {
		return clfNm;
	}

	public void setClfNm(String clfNm) {
		this.clfNm = clfNm;
	}
	
	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public String getAvGubun() {
		return avGubun;
	}

	public void setAvGubun(String avGubun) {
		this.avGubun = avGubun;
	}

	public Long getWrkCtiId() {
		return wrkCtiId;
	}

	public void setWrkCtiId(Long wrkCtiId) {
		this.wrkCtiId = wrkCtiId;
	}

	public String getPicKind() {
		return picKind;
	}

	public void setPicKind(String picKind) {
		this.picKind = picKind;
	}

	public String getServBit() {
		return servBit;
	}

	public void setServBit(String servBit) {
		this.servBit = servBit;
	}

	public String getStrRegDt() {
		return strRegDt;
	}

	public void setStrRegDt(String strRegDt) {
		this.strRegDt = strRegDt;
	}

	public String getStrModDt() {
		return strModDt;
	}

	public void setStrModDt(String strModDt) {
		this.strModDt = strModDt;
	}

	public String getFlExt() {
		return flExt;
	}

	public void setFlExt(String flExt) {
		this.flExt = flExt;
	}

	public String getJobGubun() {
		return jobGubun;
	}

	public void setJobGubun(String jobGubun) {
		this.jobGubun = jobGubun;
	}

	public Long getCtId() {
		return ctId;
	}

	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

	public String getDrpFrmYn() {
		return drpFrmYn;
	}

	public void setDrpFrmYn(String drpFrmYn) {
		this.drpFrmYn = drpFrmYn;
	}

	public String getColorCd() {
		return colorCd;
	}

	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}

	public String getOrgFlExt() {
		return orgFlExt;
	}

	public void setOrgFlExt(String orgFlExt) {
		this.orgFlExt = orgFlExt;
	}

	public String getNewFlExt() {
		return newFlExt;
	}

	public void setNewFlExt(String newFlExt) {
		this.newFlExt = newFlExt;
	}

	private List<ProOptTbl> options = new ArrayList<ProOptTbl>();

	public List<ProOptTbl> getOptions() {
		return options;
	}

	public void setOptions(List<ProOptTbl> options) {
		this.options = options;
	}

	public void addOptions(ProOptTbl option) {
		this.options.add(option);
	}

	public String getCtiFmt() {
		return ctiFmt;
	}

	public void setCtiFmt(String ctiFmt) {
		this.ctiFmt = ctiFmt;
	}

	public String getDefaultOpt() {
		return defaultOpt;
	}

	public void setDefaultOpt(String defaultOpt) {
		this.defaultOpt = defaultOpt;
	}

	public String getBitRt() {
		return bitRt;
	}

	public void setBitRt(String bitRt) {
		this.bitRt = bitRt;
	}

	public String getPgmGrpCd() {
		return pgmGrpCd;
	}

	public void setPgmGrpCd(String pgmGrpCd) {
		this.pgmGrpCd = pgmGrpCd;
	}

	public String getPgmId() {
		return pgmId;
	}

	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}

	public String getPgmNm() {
		return pgmNm;
	}

	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}

	public String getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}

	public String getSegmentNm() {
		return segmentNm;
	}

	public void setSegmentNm(String segmentNm) {
		this.segmentNm = segmentNm;
	}

	public String getVdoCodec() {
		return vdoCodec;
	}

	public void setVdoCodec(String vdoCodec) {
		this.vdoCodec = vdoCodec;
	}

	public String getVdoBitRate() {
		return vdoBitRate;
	}

	public void setVdoBitRate(String vdoBitRate) {
		this.vdoBitRate = vdoBitRate;
	}

	public Integer getVdoHori() {
		return vdoHori;
	}

	public void setVdoHori(Integer vdoHori) {
		this.vdoHori = vdoHori;
	}

	public Integer getVdoVert() {
		return vdoVert;
	}

	public void setVdoVert(Integer vdoVert) {
		this.vdoVert = vdoVert;
	}

	public String getVdoFS() {
		return vdoFS;
	}

	public void setVdoFS(String vdoFS) {
		this.vdoFS = vdoFS;
	}

	public String getVdoSync() {
		return vdoSync;
	}

	public void setVdoSync(String vdoSync) {
		this.vdoSync = vdoSync;
	}

	public String getAudCodec() {
		return audCodec;
	}

	public void setAudCodec(String audCodec) {
		this.audCodec = audCodec;
	}

	public String getAudBitRate() {
		return audBitRate;
	}

	public void setAudBitRate(String audBitRate) {
		this.audBitRate = audBitRate;
	}

	public String getAudChan() {
		return audChan;
	}

	public void setAudChan(String audChan) {
		this.audChan = audChan;
	}

	public String getAudSRate() {
		return audSRate;
	}

	public void setAudSRate(String audSRate) {
		this.audSRate = audSRate;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getEqPsCd() {
		return eqPsCd;
	}

	public void setEqPsCd(String eqPsCd) {
		this.eqPsCd = eqPsCd;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Integer getProFlid() {
		return proFlid;
	}

	public void setProFlid(Integer proFlid) {
		this.proFlid = proFlid;
	}

	public String getOrgFileNm() {
		return orgFileNm;
	}

	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}

	public String getProFlnm() {
		return proFlnm;
	}

	public void setProFlnm(String proFlnm) {
		this.proFlnm = proFlnm;
	}

	public String getCtNm() {
		return ctNm;
	}

	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}

	public String getDeviceNm() {
		return deviceNm;
	}

	public void setDeviceNm(String deviceNm) {
		this.deviceNm = deviceNm;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Timestamp getRegDt() {
		return regDt;
	}

	public void setRegDt(Timestamp regDt) {
		this.regDt = regDt;
	}

	public String getRegrid() {
		return regrid;
	}

	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}

	public Timestamp getModDt() {
		return modDt;
	}

	public void setModDt(Timestamp modDt) {
		this.modDt = modDt;
	}

	public String getModrid() {
		return modrid;
	}

	public void setModrid(String modrid) {
		this.modrid = modrid;
	}

	public Timestamp getWorkStrDt() {
		return workStrDt;
	}

	public void setWorkStrDt(Timestamp workStrDt) {
		this.workStrDt = workStrDt;
	}

	public Long getCtiId() {
		return ctiId;
	}

	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}

	public String getWorkStatcd() {
		return workStatcd;
	}

	public void setWorkStatcd(String workStatcd) {
		this.workStatcd = workStatcd;
	}

	public String getReqUsrid() {
		return reqUsrid;
	}

	public void setReqUsrid(String reqUsrid) {
		this.reqUsrid = reqUsrid;
	}

	public String getPrgrs() {
		return prgrs;
	}

	public void setPrgrs(String prgrs) {
		this.prgrs = prgrs;
	}

	public Long getDownVol() {
		return downVol;
	}

	public void setDownVol(Long downVol) {
		this.downVol = downVol;
	}

	public String getFlPath() {
		return flPath;
	}

	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}

	public Timestamp getWorkEndDt() {
		return workEndDt;
	}

	public void setWorkEndDt(Timestamp workEndDt) {
		this.workEndDt = workEndDt;
	}

}
