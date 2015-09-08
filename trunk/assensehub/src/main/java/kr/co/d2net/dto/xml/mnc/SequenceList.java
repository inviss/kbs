package kr.co.d2net.dto.xml.mnc;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("sequence_list")
public class SequenceList {
	
	@XStreamImplicit(itemFieldName="sequence")
	List<Sequence> sequences = new ArrayList<Sequence>();

	public List<Sequence> getSequences() {
		return sequences;
	}
	public void setSequences(List<Sequence> sequences) {
		this.sequences = sequences;
	}
	public void addSequence(Sequence sequence) {
		this.sequences.add(sequence);
	}
	
}
