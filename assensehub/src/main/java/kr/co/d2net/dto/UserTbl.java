package kr.co.d2net.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserTbl {
	
	private String userId;  // 사용자 ID
	private String userNm;  // 사용자명
	private String userPass; // 비밀번호
	private String regrid;  // 사용자 ID
	private Timestamp regDt;   // 등록일
	private String modrid;  // 수정자
	private Timestamp modDt;   // 수정일
	private String useYn;   // 사용여부
	
	/* 사용자별 권한에 사용 */
	private Integer authId; // 권한 ID
	private String authNm;
	private String controlGubun;
	
	/* 아카이브 검색 관련 사용 */
	private String password;
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	private String[] userIds;
	
	public String[] getUserIds() {
		return userIds;
	}
	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}
	public String getAuthNm() {
		return authNm;
	}
	public void setAuthNm(String authNm) {
		this.authNm = authNm;
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
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
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
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	
	private List<MenuTbl> menus = new ArrayList<MenuTbl>();

	public List<MenuTbl> getMenus() {
		return menus;
	}
	public void setMenus(List<MenuTbl> menus) {
		this.menus = menus;
	}
	
	
}
