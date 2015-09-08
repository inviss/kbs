package kr.co.d2net.dto.xml.mnc;

import kr.co.d2net.commons.converter.xml.IntegerConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("sequence")
public class Sequence {
	
	@XStreamAlias("index")
	@XStreamConverter(IntegerConverter.class)
	private Integer index;
	
	@XStreamAlias("start_frame")
	@XStreamConverter(TextConverter.class)
	private String startFrame;
	
	@XStreamAlias("duration")
	@XStreamConverter(TextConverter.class)
	private String duration;
	
	@XStreamAlias("annotation")
	@XStreamConverter(TextConverter.class)
	private String annotation;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getStartFrame() {
		return startFrame;
	}

	public void setStartFrame(String startFrame) {
		this.startFrame = startFrame;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	
}
