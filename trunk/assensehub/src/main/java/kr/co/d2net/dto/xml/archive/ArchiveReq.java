package kr.co.d2net.dto.xml.archive;

import kr.co.d2net.commons.converter.xml.IntegerConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.commons.converter.xml.TextUTF8Converter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("request")
public class ArchiveReq {
	
	@XStreamAlias("system_id")
	@XStreamConverter(TextConverter.class)
	private String systemId;
	
	@XStreamAlias("system_pass")
	@XStreamConverter(TextConverter.class)
	private String systemPass;
	
	@XStreamAlias("user_id")
	@XStreamConverter(TextConverter.class)
	private String userId;
	
	@XStreamAlias("user_pass")
	@XStreamConverter(TextConverter.class)
	private String userPass;
	
	@XStreamAlias("curr_page")
	@XStreamConverter(IntegerConverter.class)
	private Integer currPage;
	
	@XStreamAlias("end_date")
	@XStreamConverter(TextConverter.class)
	private String endDate;
	
	@XStreamAlias("item_count")
	@XStreamConverter(IntegerConverter.class)
	private Integer itemCount;
	
	@XStreamAlias("search_column")
	@XStreamConverter(TextConverter.class)
	private String searchColumn;
	
	@XStreamAlias("search_field")
	@XStreamConverter(TextConverter.class)
	private String searchField;
	
	@XStreamAlias("keyword")
	@XStreamConverter(TextUTF8Converter.class)
	private String keyword;
	
	@XStreamAlias("start_date")
	@XStreamConverter(TextConverter.class)
	private String startDate;
	
	@XStreamAlias("pgm_id")
	@XStreamConverter(TextConverter.class)
	private String pgmId;

	
	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSystemPass() {
		return systemPass;
	}

	public void setSystemPass(String systemPass) {
		this.systemPass = systemPass;
	}

	public Integer getCurrPage() {
		return currPage;
	}

	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public String getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getPgmId() {
		return pgmId;
	}

	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	
}
