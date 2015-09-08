package kr.co.d2net.dto.xml.internal;

import java.util.ArrayList;
import java.util.List;

import kr.co.d2net.commons.converter.xml.IntegerConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("option")
public class Option {
	
	@XStreamAlias("opt_id")
	@XStreamConverter(IntegerConverter.class)
	private Integer optId;
	
	@XStreamAlias("opt_desc")
	@XStreamConverter(TextConverter.class)
	private String optDesc;
	
	public Integer getOptId() {
		return optId;
	}
	public void setOptId(Integer optId) {
		this.optId = optId;
	}
	public String getOptDesc() {
		return optDesc;
	}
	public void setOptDesc(String optDesc) {
		this.optDesc = optDesc;
	}
	
	@XStreamImplicit(itemFieldName="option")
	List<Option> options = new ArrayList<Option>();

	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	public void addOptions(Option option) {
		this.options.add(option);
	}
	
}
