package kr.co.d2net.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContentsTbl {

	private Long ctId; // 콘텐츠ID
	private String ctId2; // 콘텐츠ID
	private String ctTyp; // 콘텐츠유형코드 '예고:01', '본방:02'
	private String ctCla; // 콘텐츠구분코드 '원본:01', '편집완성본:02', '방송본:03'
	private String ctNm; // 콘텐츠명
	private String cont; // 내용
	private String ctOwnDeptNm; // 주관부서명
	private String vdQlty; // 화질코드
	private String aspRtoCd; // 종횡비코드
	private String edtrid; // 편집자ID
	private String keyWords; // 키워드
	private String kfrmPath; // 키프레임경로
	private Integer kfrmPxCd; // 키프레임해상코드
	private Integer totKfrmNums; // 총키프레임수
	private String useYn; // 사용여부
	private Timestamp regDt; // 등록일시 
	private String regrid; // 등록자ID
	private String ctOwnDeptCd; // 주관부서코드
	private String modrid; // 수정자ID
	private Timestamp modDt; // 수정일시
	private Date delDd; // 삭제일자
	private String dataStatCd; // 자료상태코드
	private String ctLeng; // 콘텐츠길이
	private Integer ctSeq; // 콘텐츠일련번호
	private Integer duration; //
	private String pgmId; // 프로그램ID
	private String pgmCd; // 프로그램코드
	private String pgmNm; // 프로그램명
	private String segmentNm; // 세그먼트명
	private String segmentId; // 세크먼트ID
	private Date brdDd; // 방송일자
	private String outSystemId; //

	private String pgmGrpCd; // 프로그램그룹코드
	private String channelCode; // 채널코드
	private String trimmSte; // 트리밍 여부 'Y':트리밍완료, 'N':트리밍필요, 'P':트리밍패스
	private int delayDay; // 연장일 '99999999': 무기한
	private String format; // 포맷

	private Date disuseDd;
	private Date delayDt; // 연장 신청일
	
	/* WrkFileNm 생성시 필요 */
	private String wrkFileNm;
	
	private Integer pgmNum; // 컨텐츠 순차번호
	private String flExt;
	private String rate;
	private String servBit;
	private Long flSz;
	
	private String brdtime; // 방송일 (메타허브연동) test
	private String time; // 방송시각
	
	private String personInfo;   // 인물정보
	private String part; //회차

	//인물정보
	private String sSeq;         // 인물ID
	private String nameKorea;    // 성명
	private String castingName;  // 배역명
	private String imageUrl;    // 대표이미지
	
	private String nid;
	private String usrFileNm;
	
	private String grpAliasEng;
	
	private Long ctiId;
	private String proFlid;
	private String ctiFmt;
	private String orgFileNm;
	private String proFlnm;
	
	private String disuseNo;
	private String dataId;
	
	private String count;
	private String jobId;	// CTS Noti ID
	
	private String prgrs;
	private String audioModeMain;
	private String localCode;
	
	private String descriptiveVideoServiceYn;
	private String transmissionAudioModeMain;
	private String rerunClassification;
	
	// 2016.03.21 add
	private String brdKd;
	
	// 2013.04.04 오디오 코너 추가
	List<CornerTbl> cornerTbls = new ArrayList<CornerTbl>();
	
	
	public String getBrdKd() {
		return brdKd;
	}
	public void setBrdKd(String brdKd) {
		this.brdKd = brdKd;
	}
	public String getRerunClassification() {
		return rerunClassification;
	}
	public void setRerunClassification(String rerunClassification) {
		this.rerunClassification = rerunClassification;
	}
	public String getTransmissionAudioModeMain() {
		return transmissionAudioModeMain;
	}
	public void setTransmissionAudioModeMain(String transmissionAudioModeMain) {
		this.transmissionAudioModeMain = transmissionAudioModeMain;
	}
	public String getDescriptiveVideoServiceYn() {
		return descriptiveVideoServiceYn;
	}
	public void setDescriptiveVideoServiceYn(String descriptiveVideoServiceYn) {
		this.descriptiveVideoServiceYn = descriptiveVideoServiceYn;
	}
	public String getLocalCode() {
		return localCode;
	}
	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}
	public String getCtId2() {
		return ctId2;
	}
	public void setCtId2(String ctId2) {
		this.ctId2 = ctId2;
	}
	public List<CornerTbl> getCornerTbls() {
		return cornerTbls;
	}
	public void setCornerTbls(List<CornerTbl> cornerTbls) {
		this.cornerTbls = cornerTbls;
	}
	public void addCornerTbl(CornerTbl cornerTbl) {
		this.cornerTbls.add(cornerTbl);
	}
	public String getAudioModeMain() {
		return audioModeMain;
	}
	public void setAudioModeMain(String audioModeMain) {
		this.audioModeMain = audioModeMain;
	}
	public String getPrgrs() {
		return prgrs;
	}
	public void setPrgrs(String prgrs) {
		this.prgrs = prgrs;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getDisuseNo() {
		return disuseNo;
	}
	public void setDisuseNo(String disuseNo) {
		this.disuseNo = disuseNo;
	}
	public String getProFlnm() {
		return proFlnm;
	}
	public void setProFlnm(String proFlnm) {
		this.proFlnm = proFlnm;
	}
	public Long getCtiId() {
		return ctiId;
	}
	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}
	public String getProFlid() {
		return proFlid;
	}
	public void setProFlid(String proFlid) {
		this.proFlid = proFlid;
	}
	public String getCtiFmt() {
		return ctiFmt;
	}
	public void setCtiFmt(String ctiFmt) {
		this.ctiFmt = ctiFmt;
	}
	public String getOrgFileNm() {
		return orgFileNm;
	}
	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}
	
	public Long getFlSz() {
		return flSz;
	}
	public void setFlSz(Long flSz) {
		this.flSz = flSz;
	}
	public String getGrpAliasEng() {
		return grpAliasEng;
	}
	public void setGrpAliasEng(String grpAliasEng) {
		this.grpAliasEng = grpAliasEng;
	}
	public String getUsrFileNm() {
		return usrFileNm;
	}
	public void setUsrFileNm(String usrFileNm) {
		this.usrFileNm = usrFileNm;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getsSeq() {
		return sSeq;
	}
	public void setsSeq(String sSeq) {
		this.sSeq = sSeq;
	}
	public String getNameKorea() {
		return nameKorea;
	}
	public void setNameKorea(String nameKorea) {
		this.nameKorea = nameKorea;
	}
	public String getCastingName() {
		return castingName;
	}
	public void setCastingName(String castingName) {
		this.castingName = castingName;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(String personInfo) {
		this.personInfo = personInfo;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getBrdtime() {
		return brdtime;
	}
	public void setBrdtime(String brdtime) {
		this.brdtime = brdtime;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getServBit() {
		return servBit;
	}
	public void setServBit(String servBit) {
		this.servBit = servBit;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getFlExt() {
		return flExt;
	}
	public void setFlExt(String flExt) {
		this.flExt = flExt;
	}
	public Integer getPgmNum() {
		return pgmNum;
	}
	public void setPgmNum(Integer pgmNum) {
		this.pgmNum = pgmNum;
	}

	public Date getDelayDt() {
		return delayDt;
	}

	public void setDelayDt(Date delayDt) {
		this.delayDt = delayDt;
	}

	public String getWrkFileNm() {
		return wrkFileNm;
	}

	public void setWrkFileNm(String wrkFileNm) {
		this.wrkFileNm = wrkFileNm;
	}

	public Long getCtId() {
		return ctId;
	}

	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

	public String getCtTyp() {
		return ctTyp;
	}

	public void setCtTyp(String ctTyp) {
		this.ctTyp = ctTyp;
	}

	public String getCtCla() {
		return ctCla;
	}

	public void setCtCla(String ctCla) {
		this.ctCla = ctCla;
	}

	public String getCtNm() {
		return ctNm;
	}

	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public String getCtOwnDeptNm() {
		return ctOwnDeptNm;
	}

	public void setCtOwnDeptNm(String ctOwnDeptNm) {
		this.ctOwnDeptNm = ctOwnDeptNm;
	}

	public String getVdQlty() {
		return vdQlty;
	}

	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}

	public String getAspRtoCd() {
		return aspRtoCd;
	}

	public void setAspRtoCd(String aspRtoCd) {
		this.aspRtoCd = aspRtoCd;
	}

	public String getEdtrid() {
		return edtrid;
	}

	public void setEdtrid(String edtrid) {
		this.edtrid = edtrid;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getKfrmPath() {
		return kfrmPath;
	}

	public void setKfrmPath(String kfrmPath) {
		this.kfrmPath = kfrmPath;
	}

	public Integer getKfrmPxCd() {
		return kfrmPxCd;
	}

	public void setKfrmPxCd(Integer kfrmPxCd) {
		this.kfrmPxCd = kfrmPxCd;
	}

	public Integer getTotKfrmNums() {
		return totKfrmNums;
	}

	public void setTotKfrmNums(Integer totKfrmNums) {
		this.totKfrmNums = totKfrmNums;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
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

	public String getCtOwnDeptCd() {
		return ctOwnDeptCd;
	}

	public void setCtOwnDeptCd(String ctOwnDeptCd) {
		this.ctOwnDeptCd = ctOwnDeptCd;
	}

	public String getModrid() {
		return modrid;
	}

	public void setModrid(String modrid) {
		this.modrid = modrid;
	}

	public Timestamp getModDt() {
		return modDt;
	}

	public void setModDt(Timestamp modDt) {
		this.modDt = modDt;
	}

	public Date getDelDd() {
		return delDd;
	}

	public void setDelDd(Date delDd) {
		this.delDd = delDd;
	}

	public String getDataStatCd() {
		return dataStatCd;
	}

	public void setDataStatCd(String dataStatCd) {
		this.dataStatCd = dataStatCd;
	}

	public String getCtLeng() {
		return ctLeng;
	}

	public void setCtLeng(String ctLeng) {
		this.ctLeng = ctLeng;
	}

	public Integer getCtSeq() {
		return ctSeq;
	}

	public void setCtSeq(Integer ctSeq) {
		this.ctSeq = ctSeq;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getPgmId() {
		return pgmId;
	}

	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}

	public String getPgmCd() {
		return pgmCd;
	}

	public void setPgmCd(String pgmCd) {
		this.pgmCd = pgmCd;
	}

	public String getPgmNm() {
		return pgmNm;
	}

	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}

	public String getSegmentNm() {
		return segmentNm;
	}

	public void setSegmentNm(String segmentNm) {
		this.segmentNm = segmentNm;
	}

	public String getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}

	public Date getBrdDd() {
		return brdDd;
	}

	public void setBrdDd(Date brdDd) {
		this.brdDd = brdDd;
	}

	public String getOutSystemId() {
		return outSystemId;
	}

	public void setOutSystemId(String outSystemId) {
		this.outSystemId = outSystemId;
	}

	public String getPgmGrpCd() {
		return pgmGrpCd;
	}

	public void setPgmGrpCd(String pgmGrpCd) {
		this.pgmGrpCd = pgmGrpCd;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getTrimmSte() {
		return trimmSte;
	}

	public void setTrimmSte(String trimmSte) {
		this.trimmSte = trimmSte;
	}

	public int getDelayDay() {
		return delayDay;
	}

	public void setDelayDay(int delayDay) {
		this.delayDay = delayDay;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Date getDisuseDd() {
		return disuseDd;
	}

	public void setDisuseDd(Date disuseDd) {
		this.disuseDd = disuseDd;
	}

}
