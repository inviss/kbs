package kr.co.d2net.dto.xml.internal;

import kr.co.d2net.commons.converter.xml.TextConverter;
import kr.co.d2net.commons.converter.xml.TextUTF8Converter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("sgm")
public class Sgm {

	@XStreamAlias("sgm_id")
	@XStreamConverter(TextConverter.class)
	private String sgmId;
	
	@XStreamAlias("sgm_nm")
	@XStreamConverter(TextUTF8Converter.class)
	private String sgmNm;

	public String getSgmId() {
		return sgmId;
	}
	public void setSgmId(String sgmId) {
		this.sgmId = sgmId;
	}
	public String getSgmNm() {
		return sgmNm;
	}
	public void setSgmNm(String sgmNm) {
		this.sgmNm = sgmNm;
	}
	
}
