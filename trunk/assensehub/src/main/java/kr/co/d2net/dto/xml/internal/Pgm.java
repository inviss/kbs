package kr.co.d2net.dto.xml.internal;

import java.util.Date;

import kr.co.d2net.commons.converter.xml.DateConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.commons.converter.xml.TextUTF8Converter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("pgm")
public class Pgm {

	@XStreamAlias("pgm_cd")
	@XStreamConverter(TextConverter.class)
	private String pgmCd;

	@XStreamAlias("pgm_id")
	@XStreamConverter(TextConverter.class)
	private String pgmId;

	@XStreamAlias("pgm_nm")
	@XStreamConverter(TextUTF8Converter.class)
	private String pgmNm;

	@XStreamAlias("channel_cd")
	@XStreamConverter(TextConverter.class)
	private String channel;
	
	@XStreamAlias("start_time")
	@XStreamConverter(TextConverter.class)
	private String startTime;
	
	@XStreamAlias("end_time")
	@XStreamConverter(TextConverter.class)
	private String endTime;

	@XStreamAlias("brd_dd")
	@XStreamConverter(DateConverter.class)
	private Date onairDt;
	
	@XStreamAlias("reg_yn")
	@XStreamConverter(TextConverter.class)
	private String regYn;
	
	@XStreamAlias("vd_qlty")
	@XStreamConverter(TextConverter.class)
	private String vdQlty;

	@XStreamAlias("pgm_grp_cd")
	@XStreamConverter(TextConverter.class)
	private String pgmGrpCd;
	
	@XStreamAlias("rerun_classfication")
	@XStreamConverter(TextUTF8Converter.class)
	private String rerunClassfication;

	
	public String getRerunClassfication() {
		return rerunClassfication;
	}

	public void setRerunClassfication(String rerunClassfication) {
		this.rerunClassfication = rerunClassfication;
	}

	public String getPgmGrpCd() {
		return pgmGrpCd;
	}

	public void setPgmGrpCd(String pgmGrpCd) {
		this.pgmGrpCd = pgmGrpCd;
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

	public String getRegYn() {
		return regYn;
	}

	public void setRegYn(String regYn) {
		this.regYn = regYn;
	}

	public String getVdQlty() {
		return vdQlty;
	}

	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}

	public Date getOnairDt() {
		return onairDt;
	}

	public void setOnairDt(Date onairDt) {
		this.onairDt = onairDt;
	}

	public String getPgmCd() {
		return pgmCd;
	}

	public void setPgmCd(String pgmCd) {
		this.pgmCd = pgmCd;
	}

	public String getPgmId() {
		return pgmId;
	}

	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}

	public String getPgmNm() {
		return pgmNm;
	}

	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	
}
