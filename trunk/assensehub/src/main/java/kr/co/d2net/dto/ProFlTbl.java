package kr.co.d2net.dto;

import java.sql.Timestamp;

public class ProFlTbl {
	
	private String proFlid;      //프로파일ID
	private String regrid;       
	private String servBit;      //서비스Bit
	private String modrid;       
	private String ext;          //확장명
	private String vdoCodec;     //비디오코덱
	private String vdoBitRate;   //비디오비트레이트
	private String vdoHori;      //비디오가로
	private String vdoVert;      //비디오세로
	private String vdoFS;        //비디오FS
	private String vdoSync;      //비디오종횡맞춤
	private String audCodec;     //오디오코덱
	private String audBitRate;   //오디오비트레이트
	private String audChan;      //오디오채널
	private String audSRate;     //오디오샘플레이트
	private String keyFrame;     //key프레임
	private Integer chanpriority; //변경순위
	private Integer priority;     //순위
	private String proFlnm;
	private Timestamp modDt;
	private Timestamp regDt;
	private String picKind;
	private String nextval;
	private String useYn;
	private String flNameRule;

	public String getFlNameRule() {
		return flNameRule;
	}
	public void setFlNameRule(String flNameRule) {
		this.flNameRule = flNameRule;
	}
	public String getNextval() {
		return nextval;
	}
	public void setNextval(String nextval) {
		this.nextval = nextval;
	}
	
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	
	public Timestamp getModDt() {
		return modDt;
	}
	public void setModDt(Timestamp modDt) {
		this.modDt = modDt;
	}
	public Timestamp getRegDt() {
		return regDt;
	}
	public void setRegDt(Timestamp regDt) {
		this.regDt = regDt;
	}
	public String getPicKind() {
		return picKind;
	}
	public void setPicKind(String picKind) {
		this.picKind = picKind;
	}
	public String getProFlnm() {
		return proFlnm;
	}
	public void setProFlnm(String proFlnm) {
		this.proFlnm = proFlnm;
	}
	public String getProFlid() {
		return proFlid;
	}
	public void setProFlid(String proFlid) {
		this.proFlid = proFlid;
	}
	public String getRegrid() {
		return regrid;
	}
	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}
	public String getServBit() {
		return servBit;
	}
	public void setServBit(String servBit) {
		this.servBit = servBit;
	}
	public String getModrid() {
		return modrid;
	}
	public void setModrid(String modrid) {
		this.modrid = modrid;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
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
	public String getVdoHori() {
		return vdoHori;
	}
	public void setVdoHori(String vdoHori) {
		this.vdoHori = vdoHori;
	}
	public String getVdoVert() {
		return vdoVert;
	}
	public void setVdoVert(String vdoVert) {
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
	public String getKeyFrame() {
		return keyFrame;
	}
	public void setKeyFrame(String keyFrame) {
		this.keyFrame = keyFrame;
	}
	public Integer getChanpriority() {
		return chanpriority;
	}
	public void setChanpriority(Integer chanpriority) {
		this.chanpriority = chanpriority;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	
}
