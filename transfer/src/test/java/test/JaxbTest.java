package test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(namespace="http://www.example.org/type")
@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbTest {

	@XmlElement(name="first-name", namespace="http://www.example.org/property")
	private String firstName;

	@XmlElement(name="last-name")
	private String lastName;

	@XmlAttribute(namespace="http://www.example.org/default")
	private String type;

	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
