package kr.co.d2net.commons.dto;

import java.util.ArrayList;
import java.util.List;

import kr.co.d2net.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("workflow")
public class Workflow {
	
	@XStreamAlias("gubun")
	@XStreamConverter(TextConverter.class)
	private String gubun;
	
	
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	
	@XStreamAlias("job")
	private Job job;
	
	@XStreamAlias("status")
	private Status status;

	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@XStreamImplicit(itemFieldName="content")
	List<Content> contentList = new ArrayList<Content>();

	public List<Content> getContentList() {
		return contentList;
	}
	public void setContentList(List<Content> contentList) {
		this.contentList = contentList;
	}
	public void addContentList(Content content) {
		this.contentList.add(content);
	}
	
}
