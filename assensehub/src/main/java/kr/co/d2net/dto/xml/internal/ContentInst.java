package kr.co.d2net.dto.xml.internal;

import kr.co.d2net.commons.converter.xml.IntegerConverter;
import kr.co.d2net.commons.converter.xml.LongConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("content_inst")
public class ContentInst {

	@XStreamAlias("cti_id")
	@XStreamConverter(LongConverter.class)
	private Long ctiId;

	@XStreamAlias("vd_qlty")
	@XStreamConverter(TextConverter.class)
	private String vdQlty;

	@XStreamAlias("cti_fmt")
	@XStreamConverter(TextConverter.class)
	private String ctiFmt;

	@XStreamAlias("fl_path")
	@XStreamConverter(TextConverter.class)
	private String flPath;

	@XStreamAlias("fl_size")
	@XStreamConverter(LongConverter.class)
	private Long flSize;

	@XStreamAlias("fl_nm")
	@XStreamConverter(TextConverter.class)
	private String flNm;

	@XStreamAlias("som")
	@XStreamConverter(TextConverter.class)
	private String som;

	@XStreamAlias("eom")
	@XStreamConverter(TextConverter.class)
	private String eom;

	@XStreamAlias("ct_leng")
	@XStreamConverter(TextConverter.class)
	private String ctLeng;

	@XStreamAlias("vd_hresol")
	@XStreamConverter(IntegerConverter.class)
	private Integer vdHresol;

	@XStreamAlias("vd_vresol")
	@XStreamConverter(IntegerConverter.class)
	private Integer vdVresol;

	@XStreamAlias("pgm_id")
	@XStreamConverter(TextConverter.class)
	private String pgmId;

	@XStreamAlias("pgm_nm")
	@XStreamConverter(TextConverter.class)
	private String pgmNm;

	@XStreamAlias("ct_id")
	@XStreamConverter(LongConverter.class)
	private Long ctId;

	@XStreamAlias("ct_nm")
	@XStreamConverter(TextConverter.class)
	private String ctNm;
	
	@XStreamAlias("bit_rt")
	@XStreamConverter(TextConverter.class)
	private String bitRt;
	
	@XStreamAlias("color")
	@XStreamConverter(TextConverter.class)
	private String color;
	
	@XStreamAlias("drp_frm_yn")
	@XStreamConverter(TextConverter.class)
	private String drpFrmYn;

	
	public String getBitRt() {
		return bitRt;
	}

	public void setBitRt(String bitRt) {
		this.bitRt = bitRt;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDrpFrmYn() {
		return drpFrmYn;
	}

	public void setDrpFrmYn(String drpFrmYn) {
		this.drpFrmYn = drpFrmYn;
	}

	public String getCtNm() {
		return ctNm;
	}

	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}

	public Long getCtId() {
		return ctId;
	}

	public void setCtId(Long ctId) {
		this.ctId = ctId;
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

	public Long getCtiId() {
		return ctiId;
	}

	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}

	public String getVdQlty() {
		return vdQlty;
	}

	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}

	public String getCtiFmt() {
		return ctiFmt;
	}

	public void setCtiFmt(String ctiFmt) {
		this.ctiFmt = ctiFmt;
	}

	public String getFlPath() {
		return flPath;
	}

	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}

	public Long getFlSize() {
		return flSize;
	}

	public void setFlSize(Long flSize) {
		this.flSize = flSize;
	}

	public String getFlNm() {
		return flNm;
	}

	public void setFlNm(String flNm) {
		this.flNm = flNm;
	}

	public String getSom() {
		return som;
	}

	public void setSom(String som) {
		this.som = som;
	}

	public String getEom() {
		return eom;
	}

	public void setEom(String eom) {
		this.eom = eom;
	}

	public String getCtLeng() {
		return ctLeng;
	}

	public void setCtLeng(String ctLeng) {
		this.ctLeng = ctLeng;
	}

	public Integer getVdHresol() {
		return vdHresol;
	}

	public void setVdHresol(Integer vdHresol) {
		this.vdHresol = vdHresol;
	}

	public Integer getVdVresol() {
		return vdVresol;
	}

	public void setVdVresol(Integer vdVresol) {
		this.vdVresol = vdVresol;
	}

}
