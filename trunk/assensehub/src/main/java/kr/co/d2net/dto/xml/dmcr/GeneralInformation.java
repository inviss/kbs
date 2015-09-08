package kr.co.d2net.dto.xml.dmcr;

import kr.co.d2net.commons.converter.xml.LongConverter;
import kr.co.d2net.commons.converter.xml.TextConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("general_information")
public class GeneralInformation {

	@XStreamAlias("asset_id")
	@XStreamConverter(LongConverter.class)
	private Long assetId;
	@XStreamAlias("asset_type")
	@XStreamConverter(TextConverter.class)
	private String assetType;
	
	@XStreamAlias("target_folder")
	private TargetFolder targetFolder;
	
	@XStreamAlias("files")
	private Files files;

	
	public Files getFiles() {
		return files;
	}
	public void setFiles(Files files) {
		this.files = files;
	}
	public Long getAssetId() {
		return assetId;
	}
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public TargetFolder getTargetFolder() {
		return targetFolder;
	}
	public void setTargetFolder(TargetFolder targetFolder) {
		this.targetFolder = targetFolder;
	}
	
}
