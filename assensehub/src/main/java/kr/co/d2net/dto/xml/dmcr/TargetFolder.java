package kr.co.d2net.dto.xml.dmcr;

import kr.co.d2net.commons.converter.xml.IntegerConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("target_folder")
public class TargetFolder {
	
	@XStreamAlias("storage_id")
	@XStreamConverter(IntegerConverter.class)
	private Integer storageId;
	@XStreamAlias("folder_id")
	@XStreamConverter(TextConverter.class)
	private String folderId;
	
	public Integer getStorageId() {
		return storageId;
	}
	public void setStorageId(Integer storageId) {
		this.storageId = storageId;
	}
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
}
