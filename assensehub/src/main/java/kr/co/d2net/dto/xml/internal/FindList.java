package kr.co.d2net.dto.xml.internal;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("list")
public class FindList {

	@XStreamImplicit(itemFieldName = "content")
	List<Content> contents = new ArrayList<Content>();

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public void addContents(Content content) {
		this.contents.add(content);
	}

	@XStreamImplicit(itemFieldName = "pgm")
	List<Pgm> pgms = new ArrayList<Pgm>();

	public List<Pgm> getPgms() {
		return pgms;
	}

	public void setPgms(List<Pgm> pgms) {
		this.pgms = pgms;
	}

	public void addPgms(Pgm pgm) {
		this.pgms.add(pgm);
	}

	@XStreamImplicit(itemFieldName = "sgm")
	List<Sgm> sgms = new ArrayList<Sgm>();

	public List<Sgm> getSgms() {
		return sgms;
	}

	public void setSgms(List<Sgm> sgms) {
		this.sgms = sgms;
	}

	public void addSgms(Sgm sgm) {
		this.sgms.add(sgm);
	}

	@XStreamImplicit(itemFieldName = "code")
	List<Code> codes = new ArrayList<Code>();

	public List<Code> getCodes() {
		return codes;
	}

	public void setCodes(List<Code> codes) {
		this.codes = codes;
	}

	public void addCodes(Code code) {
		this.codes.add(code);
	}

}
