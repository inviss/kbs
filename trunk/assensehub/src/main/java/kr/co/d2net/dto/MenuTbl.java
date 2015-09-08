package kr.co.d2net.dto;

import java.sql.Timestamp;

public class MenuTbl {
	private String menuId; // 메뉴ID
	private String useYn;  // 사용여부
	private String regrid; // 등록자
	private Timestamp regDt;  // 등록일
	private String modrid; // 수정자
	private Timestamp modDt;  // 수정일
	private String menuNm; // 메뉴명
	private String url;    // 사용 URL
	private Integer lft;   // 좌번호
	private Integer rgt;   // 우번호
	private Integer depth; // 깊이
	private String menuEnNm;
	
	/* 권한관련 추가 */
	private String controlGubun; //접근구분
	
	
	public String getMenuEnNm() {
		return menuEnNm;
	}
	public void setMenuEnNm(String menuEnNm) {
		this.menuEnNm = menuEnNm;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	public String getControlGubun() {
		return controlGubun;
	}
	public void setControlGubun(String controlGubun) {
		this.controlGubun = controlGubun;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
	public String getMenuNm() {
		return menuNm;
	}
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getLft() {
		return lft;
	}
	public void setLft(Integer lft) {
		this.lft = lft;
	}
	public Integer getRgt() {
		return rgt;
	}
	public void setRgt(Integer rgt) {
		this.rgt = rgt;
	}
	
	
}
