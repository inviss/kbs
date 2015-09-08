package kr.co.d2net.xml.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang.StringUtils;

public class DateAdapter extends XmlAdapter<String, Date> {

	private SimpleDateFormat dateFormat = null;

	@Override
	public String marshal(Date v) throws Exception {
		return new SimpleDateFormat("yyyy-MM-dd").format(v);
	}

	@Override
	public Date unmarshal(String v) throws Exception {
		if(StringUtils.isNotBlank(v)) {
			if(v.indexOf(".") > -1) {
				dateFormat = new SimpleDateFormat("yyyy.MM.dd");
			} else if(v.indexOf("-") > -1) {
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			} else if(v.indexOf("/") > -1) {
				dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			} else {
				throw new Exception("Date format error - "+v);
			}
			return dateFormat.parse(v);
		} else return null;
	}

}
