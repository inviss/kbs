package kr.co.d2net.dto;

import java.sql.Timestamp;
import java.util.Date;

public class TransferHisTbl {

	private Long dairyOrderId;
	private String useYn; // 사용유무
	private Long seq; //
	private String seq2; //
	private Timestamp regDt; // 등록일시
	private String regDate; // 등록일시
	private String regrid; // 등록자ID
	private Timestamp modDt; // 수정일시
	private String modrid; // 수정자ID
	private Timestamp trsStrDt; // 전송시작일시
	// private String eqUseId; // 장비ID
	private Long ctiId; // 콘텐츠인스턴스ID
	private String workStatcd; // 작업상태코드
	private String reqUsrid; // 요청자ID
	private String prgrs; // 진행률
	private Long downVol; // 총사이즈
	private String flPath; // 파일경로
	private Timestamp trsEndDt; // 전송완료일시
	private String busiPartnerId; // 사업자ID
	private String url; //
	private Integer retryCnt; // 전송실패횟수

	private String deviceid; // /장비 ID
	private String eqPsCd; // 장비코드
	private String proFlid; // 프로파일 ID

	private String deviceNm; // 장비명
	private String deviceIp; // 장비IP
	private String company; // 사업자명
	private String proFlnm; // 프로파일명
	private String pgmNm; // 프로그램명
	private Integer priority; // 우선순위
	
	/* 트랜스퍼 Job 분배시 사용 */
	private String pgmGrpCd;
	private String pgmId;
	private String pgmCd;
	private String segmentId;
	private String segmentNm;
	private String brd; // 방송일자
	
	private String channel;
	private String local;
	
	
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	// 2013.04.29
	// smil 생성시 오디오여부를 판단해야 함
	private String avGubun;
	

	public String getAvGubun() {
		return avGubun;
	}

	public void setAvGubun(String avGubun) {
		this.avGubun = avGubun;
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

	public Long getDairyOrderId() {
		return dairyOrderId;
	}

	public void setDairyOrderId(Long dairyOrderId) {
		this.dairyOrderId = dairyOrderId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getBrd() {
		return brd;
	}

	public void setBrd(String brd) {
		this.brd = brd;
	}

	/* find 생성시 필요 */
	private Long ctId;
	private String ctNm;
	
	/* 추가 */
	private String orgFileNm; 	// 물리파일명
	private String wrkFileNm;	// 서비스파일명
	private String ext;
	private String flExt;
	private Long flSz;
	private String ip;
	private String ftpid;
	private String password;
	private String port;
	private String remoteDir;
	private String transMethod;
	private String ftpip;
	private String jobGubun;
	private String metaIns;
	private String metaUpd;
	
	private String strRegDt;
	private String strModDt;
	
	private String srvUrl;
	private String contentML;
	private String vodSmil;
	private String bitRate;
	private String picKind;
	private String clfNm;
	
	
	
	

	public String getClfNm() {
		return clfNm;
	}

	public void setClfNm(String clfNm) {
		this.clfNm = clfNm;
	}

	public String getPicKind() {
		return picKind;
	}

	public void setPicKind(String picKind) {
		this.picKind = picKind;
	}

	public String getBitRate() {
		return bitRate;
	}

	public void setBitRate(String bitRate) {
		this.bitRate = bitRate;
	}

	public String getSrvUrl() {
		return srvUrl;
	}

	public void setSrvUrl(String srvUrl) {
		this.srvUrl = srvUrl;
	}

	public String getContentML() {
		return contentML;
	}

	public void setContentML(String contentML) {
		this.contentML = contentML;
	}

	public String getVodSmil() {
		return vodSmil;
	}

	public void setVodSmil(String vodSmil) {
		this.vodSmil = vodSmil;
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

	public String getMetaIns() {
		return metaIns;
	}

	public void setMetaIns(String metaIns) {
		this.metaIns = metaIns;
	}

	public String getMetaUpd() {
		return metaUpd;
	}

	public void setMetaUpd(String metaUpd) {
		this.metaUpd = metaUpd;
	}

	public String getJobGubun() {
		return jobGubun;
	}

	public void setJobGubun(String jobGubun) {
		this.jobGubun = jobGubun;
	}

	public String getFtpip() {
		return ftpip;
	}

	public void setFtpip(String ftpip) {
		this.ftpip = ftpip;
	}

	public String getFlExt() {
		return flExt;
	}

	public void setFlExt(String flExt) {
		this.flExt = flExt;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getFtpid() {
		return ftpid;
	}

	public void setFtpid(String ftpid) {
		this.ftpid = ftpid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getRemoteDir() {
		return remoteDir;
	}

	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}

	public String getTransMethod() {
		return transMethod;
	}

	public void setTransMethod(String transMethod) {
		this.transMethod = transMethod;
	}

	public String getPgmCd() {
		return pgmCd;
	}

	public void setPgmCd(String pgmCd) {
		this.pgmCd = pgmCd;
	}

	public Long getFlSz() {
		return flSz;
	}

	public void setFlSz(Long flSz) {
		this.flSz = flSz;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getWrkFileNm() {
		return wrkFileNm;
	}

	public void setWrkFileNm(String wrkFileNm) {
		this.wrkFileNm = wrkFileNm;
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

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getEqPsCd() {
		return eqPsCd;
	}

	public void setEqPsCd(String eqPsCd) {
		this.eqPsCd = eqPsCd;
	}

	public String getProFlid() {
		return proFlid;
	}

	public void setProFlid(String proFlid) {
		this.proFlid = proFlid;
	}

	public String getPgmNm() {
		return pgmNm;
	}

	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}

	public String getOrgFileNm() {
		return orgFileNm;
	}

	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getProFlnm() {
		return proFlnm;
	}

	public void setProFlnm(String proFlnm) {
		this.proFlnm = proFlnm;
	}

	public Long getCtId() {
		return ctId;
	}

	public void setCtId(Long ctId) {
		this.ctId = ctId;
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

	public Timestamp getTrsStrDt() {
		return trsStrDt;
	}

	public void setTrsStrDt(Timestamp trsStrDt) {
		this.trsStrDt = trsStrDt;
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

	public Timestamp getTrsEndDt() {
		return trsEndDt;
	}

	public void setTrsEndDt(Timestamp trsEndDt) {
		this.trsEndDt = trsEndDt;
	}

	public String getBusiPartnerId() {
		return busiPartnerId;
	}

	public void setBusiPartnerId(String busiPartnerId) {
		this.busiPartnerId = busiPartnerId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getRetryCnt() {
		return retryCnt;
	}

	public void setRetryCnt(Integer retryCnt) {
		this.retryCnt = retryCnt;
	}

}
