package kr.co.d2net.commons.adapter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimestampAdapter extends XmlAdapter<String, Timestamp> {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String marshal(Timestamp v) throws Exception {
        return dateFormat.format(v);
    }
    public Timestamp unmarshal(String v) throws Exception {
        return new Timestamp(dateFormat.parse(v).getTime());
    }
}
