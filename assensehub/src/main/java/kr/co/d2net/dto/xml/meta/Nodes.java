package kr.co.d2net.dto.xml.meta;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XmlRootElement(name="nodes")
@XStreamAlias("nodes")
public class Nodes {
	
	@XmlElement(name="node")
	@XStreamImplicit(itemFieldName="node")
	List<Node> nodeList = new ArrayList<Node>();

	public List<Node> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}
	public void addNode(Node node) {
		this.nodeList.add(node);
	}
	
	
}
