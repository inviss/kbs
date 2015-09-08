package kr.co.d2net.dto;

import java.sql.Timestamp;

public class ErrorRgstTbl {

	private Long ctiId; // 콘텐츠인스턴스ID
	private String wrt; // 작성자
	private String workClf; // 작업구분
	private String cont; // 내용
	private float img; // 이미지
	private String workCmCont; // 작업지시내용
	private String workSeq; // 작업순서
	private String imgPath; // 이미지파일경로
	private String regrid; // 등록자ID
	private Timestamp regDt; // 등록일시
	private String modrid; // 수정자ID
	private Timestamp modDt; // 수정일시

	public Long getCtiId() {
		return ctiId;
	}

	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}

	public String getWrt() {
		return wrt;
	}

	public void setWrt(String wrt) {
		this.wrt = wrt;
	}

	public String getWorkClf() {
		return workClf;
	}

	public void setWorkClf(String workClf) {
		this.workClf = workClf;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public float getImg() {
		return img;
	}

	public void setImg(float img) {
		this.img = img;
	}

	public String getWorkCmCont() {
		return workCmCont;
	}

	public void setWorkCmCont(String workCmCont) {
		this.workCmCont = workCmCont;
	}

	public String getWorkSeq() {
		return workSeq;
	}

	public void setWorkSeq(String workSeq) {
		this.workSeq = workSeq;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getRegrid() {
		return regrid;
	}

	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}

	public Timestamp getRegDt() {
		return regDt;
	}

	public void setRegDt(Timestamp regDt) {
		this.regDt = regDt;
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

}
