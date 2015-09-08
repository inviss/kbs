package kr.co.d2net.dto.xml.internal;

import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.commons.converter.xml.TextUTF8Converter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("code")
public class Code {

	@XStreamAlias("clf_cd")
	@XStreamConverter(TextConverter.class)
	private String clfCd;

	@XStreamAlias("scl_cd")
	@XStreamConverter(TextConverter.class)
	private String sclCd;

	@XStreamAlias("clf_nm")
	@XStreamConverter(TextUTF8Converter.class)
	private String clfNm;

	@XStreamAlias("note")
	@XStreamConverter(TextConverter.class)
	private String note;

	@XStreamAlias("rmk_1")
	@XStreamConverter(TextConverter.class)
	private String rmk1;

	public String getClfCd() {
		return clfCd;
	}

	public void setClfCd(String clfCd) {
		this.clfCd = clfCd;
	}

	public String getSclCd() {
		return sclCd;
	}

	public void setSclCd(String sclCd) {
		this.sclCd = sclCd;
	}

	public String getClfNm() {
		return clfNm;
	}

	public void setClfNm(String clfNm) {
		this.clfNm = clfNm;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRmk1() {
		return rmk1;
	}

	public void setRmk1(String rmk1) {
		this.rmk1 = rmk1;
	}

}
