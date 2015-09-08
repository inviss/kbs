package kr.co.d2net.dto;

import java.sql.Timestamp;

public class UserAuthTbl {
	
	private Integer authId; //권한ID  
	private String modrid; //수정자ID
	private Timestamp modDt;  //수정일시
	private String userId; //작업자ID
	
	
	public Integer getAuthId() {
		return authId;
	}
	public void setAuthId(Integer authId) {
		this.authId = authId;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	
}
