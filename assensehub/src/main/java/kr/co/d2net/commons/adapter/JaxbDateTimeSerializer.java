package kr.co.d2net.commons.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang.StringUtils;

public class JaxbDateTimeSerializer extends XmlAdapter<String, Date> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public Date unmarshal(String date) throws Exception {
		if(StringUtils.isNotBlank(date)) {
			return dateFormat.parse(date);
		} else {
			return null;
		}
	}

	@Override
	public String marshal(Date date) throws Exception {
		if(date != null) {
			return dateFormat.format(date);
		} else {
			return "";
		}
	}

}
