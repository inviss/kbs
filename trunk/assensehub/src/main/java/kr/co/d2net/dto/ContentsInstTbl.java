package kr.co.d2net.dto;

import java.sql.Timestamp;
import java.util.Date;

public class ContentsInstTbl {

	private Long ctiId; // 콘텐츠인스턴스ID
	private Long ctId; // 콘텐츠ID
	private String ctiFmt; // 콘텐츠인스턴스포맷코드
	private String meCd; // ME분리코드
	private String bitRt; // 비트전송율
	private String frmPerSec; // 초당프레임
	private String drpFrmYn; // 드롭프레임여부
	private Integer vdHresol; // 비디오수평해상도
	private Integer vdVresol; // 비디오수직해상도
	private String recordTypeCd; // 녹음방식코드
	private String flPath; // 파일경로
	private Long flSz; // 파일크기
	private Timestamp regDt; // 등록일시
	private String regrid; // 등록자ID
	private Timestamp modDt; // 수정일시
	private String modrid; // 수정자ID
	private String colorCd; // 색상코드
	private String orgFileNm; // 원파일명
	private String audSampFrq; // 오디오샘플링
	private String audioBdwt; // 오디오대역폭
	private String ingestEqId; // 인제스트장치ID
	private String audioYn; // 오디오여부
	private String audioLanCd; // 오디오언어코드
	private String noiRducTypCd; // 잡음저감유형코드
	private String encQltyDesc; // 인코딩품질설명
	private String encQltyCd; // 인코딩품질코드
	private String wrkFileNm; // 작업파일명
	private String usrFileNm; // 입력파일명
	private Integer proFlid; // 프로파일 아이디
	private String pgmId; // 프로그램 ID
	private String pgmNm; // 프로그램 명
	private String ctNm; // 콘텐츠 명
	private String proFlnm; // 프로파일명
	private String useYn; // 사용여부
	private String flExt; // 확장자

	private String som;
	private String eom;
	private String ctLeng;

	private Date brdDd; // 방송일
	private String channelCode; // 채널코드
	private String pgmCd; // 프로그램코드
	private String pgmGrpCd; // 프로그램 그룹코드
	private String ctCla;
	private String ctTyp;
	private String disuseNo;
	private String vdQlty; // HD,SD
	private String avGubun;
	private String segmentId;
	private String alias;
	private String srvUrl; //메타 URL 정보 제공 여부
	
	private Long pgmNum; //프로그램 순번(중복건수)
	
	private String startTime;
	private String endTime;
	
	// 2013.04.25 메타허브에 smil 여부를 넘겨주어야 함.
	private String vodSmil;
	
	
	public String getVodSmil() {
		return vodSmil;
	}

	public void setVodSmil(String vodSmil) {
		this.vodSmil = vodSmil;
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

	public Long getPgmNum() {
		return pgmNum;
	}

	public void setPgmNum(Long pgmNum) {
		this.pgmNum = pgmNum;
	}

	public String getSrvUrl() {
		return srvUrl;
	}

	public void setSrvUrl(String srvUrl) {
		this.srvUrl = srvUrl;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}

	public String getPgmGrpCd() {
		return pgmGrpCd;
	}

	public void setPgmGrpCd(String pgmGrpCd) {
		this.pgmGrpCd = pgmGrpCd;
	}

	public String getAvGubun() {
		return avGubun;
	}

	public void setAvGubun(String avGubun) {
		this.avGubun = avGubun;
	}

	public String getVdQlty() {
		return vdQlty;
	}

	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}

	public String getDisuseNo() {
		return disuseNo;
	}

	public void setDisuseNo(String disuseNo) {
		this.disuseNo = disuseNo;
	}

	public String getCtCla() {
		return ctCla;
	}

	public void setCtCla(String ctCla) {
		this.ctCla = ctCla;
	}

	public String getCtTyp() {
		return ctTyp;
	}

	public void setCtTyp(String ctTyp) {
		this.ctTyp = ctTyp;
	}

	public String getPgmCd() {
		return pgmCd;
	}

	public void setPgmCd(String pgmCd) {
		this.pgmCd = pgmCd;
	}

	public Date getBrdDd() {
		return brdDd;
	}

	public void setBrdDd(Date brdDd) {
		this.brdDd = brdDd;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getSom() {
		return som;
	}

	public void setSom(String som) {
		this.som = som;
	}

	public String getEom() {
		return eom;
	}

	public void setEom(String eom) {
		this.eom = eom;
	}

	public String getCtLeng() {
		return ctLeng;
	}

	public void setCtLeng(String ctLeng) {
		this.ctLeng = ctLeng;
	}

	public String getUseYn() {
		return useYn;
	}

	public String getFlExt() {
		return flExt;
	}

	public void setFlExt(String flExt) {
		this.flExt = flExt;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public Long getFlSz() {
		return flSz;
	}

	public void setFlSz(Long flSz) {
		this.flSz = flSz;
	}

	public String getUsrFileNm() {
		return usrFileNm;
	}

	public void setUsrFileNm(String usrFileNm) {
		this.usrFileNm = usrFileNm;
	}

	public String getProFlnm() {
		return proFlnm;
	}

	public void setProFlnm(String proFlnm) {
		this.proFlnm = proFlnm;
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

	public String getCtNm() {
		return ctNm;
	}

	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}

	public Long getCtiId() {
		return ctiId;
	}

	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}

	public Long getCtId() {
		return ctId;
	}

	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

	public String getCtiFmt() {
		return ctiFmt;
	}

	public void setCtiFmt(String ctiFmt) {
		this.ctiFmt = ctiFmt;
	}

	public String getMeCd() {
		return meCd;
	}

	public void setMeCd(String meCd) {
		this.meCd = meCd;
	}

	public String getBitRt() {
		return bitRt;
	}

	public void setBitRt(String bitRt) {
		this.bitRt = bitRt;
	}

	public String getFrmPerSec() {
		return frmPerSec;
	}

	public void setFrmPerSec(String frmPerSec) {
		this.frmPerSec = frmPerSec;
	}

	public String getDrpFrmYn() {
		return drpFrmYn;
	}

	public void setDrpFrmYn(String drpFrmYn) {
		this.drpFrmYn = drpFrmYn;
	}

	public Integer getVdHresol() {
		return vdHresol;
	}

	public void setVdHresol(Integer vdHresol) {
		this.vdHresol = vdHresol;
	}

	public Integer getVdVresol() {
		return vdVresol;
	}

	public void setVdVresol(Integer vdVresol) {
		this.vdVresol = vdVresol;
	}

	public String getRecordTypeCd() {
		return recordTypeCd;
	}

	public void setRecordTypeCd(String recordTypeCd) {
		this.recordTypeCd = recordTypeCd;
	}

	public String getFlPath() {
		return flPath;
	}

	public void setFlPath(String flPath) {
		this.flPath = flPath;
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

	public String getColorCd() {
		return colorCd;
	}

	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}

	public String getOrgFileNm() {
		return orgFileNm;
	}

	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}

	public String getAudSampFrq() {
		return audSampFrq;
	}

	public void setAudSampFrq(String audSampFrq) {
		this.audSampFrq = audSampFrq;
	}

	public String getAudioBdwt() {
		return audioBdwt;
	}

	public void setAudioBdwt(String audioBdwt) {
		this.audioBdwt = audioBdwt;
	}

	public String getIngestEqId() {
		return ingestEqId;
	}

	public void setIngestEqId(String ingestEqId) {
		this.ingestEqId = ingestEqId;
	}

	public String getAudioYn() {
		return audioYn;
	}

	public void setAudioYn(String audioYn) {
		this.audioYn = audioYn;
	}

	public String getAudioLanCd() {
		return audioLanCd;
	}

	public void setAudioLanCd(String audioLanCd) {
		this.audioLanCd = audioLanCd;
	}

	public String getNoiRducTypCd() {
		return noiRducTypCd;
	}

	public void setNoiRducTypCd(String noiRducTypCd) {
		this.noiRducTypCd = noiRducTypCd;
	}

	public String getEncQltyDesc() {
		return encQltyDesc;
	}

	public void setEncQltyDesc(String encQltyDesc) {
		this.encQltyDesc = encQltyDesc;
	}

	public String getEncQltyCd() {
		return encQltyCd;
	}

	public void setEncQltyCd(String encQltyCd) {
		this.encQltyCd = encQltyCd;
	}

	public String getWrkFileNm() {
		return wrkFileNm;
	}

	public void setWrkFileNm(String wrkFileNm) {
		this.wrkFileNm = wrkFileNm;
	}

	public Integer getProFlid() {
		return proFlid;
	}

	public void setProFlid(Integer proFlid) {
		this.proFlid = proFlid;
	};

}
