package kr.co.d2net.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Search {
	private Long ctId;
	private Long ctiId;
	private String pgmId;
	private Date startDt;
	private Date endDt;
	private String keyword="";
	private Integer pageNo;
	private String userId;
	private String channelCode;
	private String menuId;
	private String tabGubun;
	private List<String> ctNms = new ArrayList<String>();
	private List<Long> ctIds = new ArrayList<Long>();
	private String gubun;
	private String popupGB;
	private String pgmCd;
	private String ctCla2;
	private String ctTyp2;
	private String part2;
	private String personInfo2;
	private String personInfo3;
	private Date brdDd2;
	private String channelCode2;
	private String nid;
	private String type;
	private String msg;
	private String dirGubun; //main, backup
	private String workStat; //작업관리, 전송관리 상태 검색 값
	private String fileType; //파일유형
	private String startTime; //시작시간
	private String endTime; //종료시간
	private String sourceGb; // 소스구분 'V: Video, A: Audio'
	
	private String localCode;
	private String searchDayName;
	private String pgmGrpCd;
	
	
	public String getPgmGrpCd() {
		return pgmGrpCd;
	}

	public void setPgmGrpCd(String pgmGrpCd) {
		this.pgmGrpCd = pgmGrpCd;
	}

	public String getSearchDayName() {
		return searchDayName;
	}

	public void setSearchDayName(String searchDayName) {
		this.searchDayName = searchDayName;
	}

	public String getSourceGb() {
		return sourceGb;
	}

	public void setSourceGb(String sourceGb) {
		this.sourceGb = sourceGb;
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

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	
	public String getLocalCode() {
		return localCode;
	}

	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}

	public String getWorkStat() {
		return workStat;
	}

	public void setWorkStat(String workStat) {
		this.workStat = workStat;
	}

	public String getDirGubun() {
		return dirGubun;
	}

	public void setDirGubun(String dirGubun) {
		this.dirGubun = dirGubun;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getChannelCode2() {
		return channelCode2;
	}

	public void setChannelCode2(String channelCode2) {
		this.channelCode2 = channelCode2;
	}

	public String getCtCla2() {
		return ctCla2;
	}

	public void setCtCla2(String ctCla2) {
		this.ctCla2 = ctCla2;
	}

	public String getCtTyp2() {
		return ctTyp2;
	}

	public void setCtTyp2(String ctTyp2) {
		this.ctTyp2 = ctTyp2;
	}

	public String getPart2() {
		return part2;
	}

	public void setPart2(String part2) {
		this.part2 = part2;
	}

	public String getPersonInfo2() {
		return personInfo2;
	}

	public void setPersonInfo2(String personInfo2) {
		this.personInfo2 = personInfo2;
	}

	public String getPersonInfo3() {
		return personInfo3;
	}

	public void setPersonInfo3(String personInfo3) {
		this.personInfo3 = personInfo3;
	}

	public Date getBrdDd2() {
		return brdDd2;
	}

	public void setBrdDd2(Date brdDd2) {
		this.brdDd2 = brdDd2;
	}

	public String getPgmCd() {
		return pgmCd;
	}

	public void setPgmCd(String pgmCd) {
		this.pgmCd = pgmCd;
	}

	public String getPopupGB() {
		return popupGB;
	}

	public void setPopupGB(String popupGB) {
		this.popupGB = popupGB;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getTabGubun() {
		return tabGubun;
	}

	public void setTabGubun(String tabGubun) {
		this.tabGubun = tabGubun;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public List<String> getCtNms() {
		return ctNms;
	}

	public void setCtNms(List<String> ctNms) {
		this.ctNms = ctNms;
	}

	public Long getCtId() {
		return ctId;
	}

	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

	public Long getCtiId() {
		return ctiId;
	}

	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}

	public List<Long> getCtIds() {
		return ctIds;
	}

	public void setCtIds(List<Long> ctIds) {
		this.ctIds = ctIds;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getPgmId() {
		return pgmId;
	}

	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getEndDt() {
		// if (endDt != null)
		// return DateUtils.addDays(endDt, 1);
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

}
