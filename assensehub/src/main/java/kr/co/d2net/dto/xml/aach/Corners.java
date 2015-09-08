package kr.co.d2net.dto.xml.aach;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("corners")
public class Corners {
	
	@XStreamImplicit(itemFieldName="corner")
	List<Corner> cornerList = new ArrayList<Corner>();

	public List<Corner> getCornerList() {
		return cornerList;
	}
	public void setCornerList(List<Corner> cornerList) {
		this.cornerList = cornerList;
	}
	public void addCornerList(Corner corner) {
		this.cornerList.add(corner);
	}
	
}
