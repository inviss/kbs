package kr.co.d2net.dto.xml;

import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("MemberOf")
public class MemberOf {
	
	@XStreamAlias("grid")
	@XStreamConverter(TextConverter.class)
	private String grid;

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}
	
}
