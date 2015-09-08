package kr.co.d2net.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class DisuseInfoTbl {
	
	private String disuseNo;   //폐기번호
	private String dataNm;     //자료명
	private Long dataId;     //자료ID
	private String dataClfCd; //자료구분코드
	private Date disuseDd;   //폐기일
	private String disuseRsl;  //폐기사유
	private String disuseClf;  //폐기구분
	private Timestamp regDt;      //등록일시
	private String regrid;     //등록자ID
	private Timestamp modDt;      //수정일시
	private String modrid;     //수정자ID
	private String extsRsl;    //연장사유
	private String extsDt;     //연장일자
	private String extsCd;     //연장코드
	private String pgmNm;
	private Long ctId; 
	private String ctNm;
	private String ctCla;
	
	private Long ctiId;
	private String proFlid;
	private String ctiFmt;
	private String orgFileNm;
	
	
	
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
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
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
	public String getCtCla() {
		return ctCla;
	}
	public void setCtCla(String ctCla) {
		this.ctCla = ctCla;
	}
	public String getDisuseNo() {
		return disuseNo;
	}
	public void setDisuseNo(String disuseNo) {
		this.disuseNo = disuseNo;
	}
	public String getDataNm() {
		return dataNm;
	}
	public void setDataNm(String dataNm) {
		this.dataNm = dataNm;
	}
	public Long getDataId() {
		return dataId;
	}
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	public String getDataClfCd() {
		return dataClfCd;
	}
	public void setDataClfCd(String dataClfCd) {
		this.dataClfCd = dataClfCd;
	}
	public Date getDisuseDd() {
		return disuseDd;
	}
	public void setDisuseDd(Date disuseDd) {
		this.disuseDd = disuseDd;
	}
	public String getDisuseRsl() {
		return disuseRsl;
	}
	public void setDisuseRsl(String disuseRsl) {
		this.disuseRsl = disuseRsl;
	}
	public String getDisuseClf() {
		return disuseClf;
	}
	public void setDisuseClf(String disuseClf) {
		this.disuseClf = disuseClf;
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
	public String getExtsRsl() {
		return extsRsl;
	}
	public void setExtsRsl(String extsRsl) {
		this.extsRsl = extsRsl;
	}
	public String getExtsDt() {
		return extsDt;
	}
	public void setExtsDt(String extsDt) {
		this.extsDt = extsDt;
	}
	public String getExtsCd() {
		return extsCd;
	}
	public void setExtsCd(String extsCd) {
		this.extsCd = extsCd;
	}
	
	
	
}
