package kr.co.d2net.dto.xml.kdas;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import kr.co.d2net.commons.adapter.JaxbDateTimeSerializer;

@XmlRootElement(name="EPISODE_INFO")
@XmlAccessorType(XmlAccessType.FIELD)
public class EpisodeInfo {

	@XmlElement(name="PROGRAM_ID")
	private String programId;	//
	@XmlJavaTypeAdapter(JaxbDateTimeSerializer.class)
	@XmlElement(name="PROGRAM_ONAIR_DATE")
	private Date programOnairDate;	//
	@XmlElement(name="PROGRAMMING_TABLE_TITLE")
	private String programmingTableTitle;	//
	@XmlElement(name="RERUN_CLASSIFICATION")
	private String rerunClassification; // 본방, 재방 구분
	@XmlElement(name="PRODUCTION_VIDEO_QULITY")
	private String productionVideoQulity; // HD, SD
	
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public Date getProgramOnairDate() {
		return programOnairDate;
	}
	public void setProgramOnairDate(Date programOnairDate) {
		this.programOnairDate = programOnairDate;
	}
	public String getProgrammingTableTitle() {
		return programmingTableTitle;
	}
	public void setProgrammingTableTitle(String programmingTableTitle) {
		this.programmingTableTitle = programmingTableTitle;
	}
	public String getRerunClassification() {
		return rerunClassification;
	}
	public void setRerunClassification(String rerunClassification) {
		this.rerunClassification = rerunClassification;
	}
	public String getProductionVideoQulity() {
		return productionVideoQulity;
	}
	public void setProductionVideoQulity(String productionVideoQulity) {
		this.productionVideoQulity = productionVideoQulity;
	}
	
}
