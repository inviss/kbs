package kr.co.d2net.dto;

import java.sql.Timestamp;

public class RoleAuthTbl {
	
	private Integer menuId;       //매뉴ID
	private String modrid;       //수정자ID
	private Timestamp modDt;        //수정일시
	private String controlGubun; //접근구분
	private Integer authId;       //권한ID
	
	
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
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
	public String getControlGubun() {
		return controlGubun;
	}
	public void setControlGubun(String controlGubun) {
		this.controlGubun = controlGubun;
	}
	public Integer getAuthId() {
		return authId;
	}
	public void setAuthId(Integer authId) {
		this.authId = authId;
	}
	
	
	
}
