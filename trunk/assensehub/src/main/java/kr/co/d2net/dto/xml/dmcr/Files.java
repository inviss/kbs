package kr.co.d2net.dto.xml.dmcr;

import java.util.ArrayList;
import java.util.List;

import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("files")
public class Files {
	
	@XStreamAsAttribute
	@XStreamAlias("total_size")
	@XStreamConverter(TextConverter.class)
	private String totalSize;
	
	@XStreamImplicit(itemFieldName="file")
	List<File> fileList = new ArrayList<File>();

	public String getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}
	public List<File> getFileList() {
		return fileList;
	}
	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}
	public void addFileList(File file) {
		this.fileList.add(file);
	}
	
}
