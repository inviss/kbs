package kr.co.d2net.commons.utils;


import java.util.Iterator;
import java.util.List;

import kr.co.d2net.commons.dto.Content;
import kr.co.d2net.commons.dto.Job;
import kr.co.d2net.commons.dto.Status;
import kr.co.d2net.commons.dto.Workflow;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * <p>Object를 기준으로 XML 생성</p>
 * @author Kang Myeong Seong
 * <pre>
 * 
 * </pre>
 */
public class XmlStreamImpl implements XmlStream {

	private Logger logger = Logger.getLogger(getClass());
	
	private XStream xstream;

	public XmlStreamImpl() {
		/*
		 * XML Parsing 할때 일반적으로 DomDriver를 사용하지만, XppDriver가 속도면에서 좀더 빠르단다.
		 * alias명으로 '_' 언더바(underscore)가 존재하면 '__'로 두개가 출력이 된다. 치환을 해줘야할 필요가 있다.
		 */
		xstream = new XStream(new PureJavaReflectionProvider(), new XppDriver(new XmlFriendlyReplacer("__", "_")));
		xstream.autodetectAnnotations(true);
		
		xstream.processAnnotations(Workflow.class);
		xstream.processAnnotations(Status.class);
		xstream.processAnnotations(Job.class);
		xstream.processAnnotations(Content.class);
	}

	/**
	 * xml을 받아서 파라미터로 Object로 변환
	 */
	public Object fromXML(String xml) {
		return xstream.fromXML(xml);
	}
	
	/**
	 * xml을 받아서 파라미터로 Object로 변환
	 */
	public Object fromXML(String xml, Class cls) {
		setAnnotationAlias(cls);
		return xstream.fromXML(xml);
	}

	/**
	 * 클래스 리스트를 받아서 xml로 생성
	 */
	public Object fromXML(String xml, List<Class> clsList)
	throws ClassNotFoundException {
		setAnnotationAlias(clsList);
		return xstream.fromXML(xml);
	}

	public String toXML(Object obj) {
		return xstream.toXML(obj);
	}
	
	public String toXML(Object obj, Class cls) {
		setAnnotationAlias(cls);
		return xstream.toXML(obj);
	}
	
	public String toXML(Object obj, List<Class> clsList) throws ClassNotFoundException {
		setAnnotationAlias(clsList);
		return xstream.toXML(obj);
	}

	public void setAlias(String name, Class cls) {
		xstream.alias(name, cls);
	}

	public void setAnnotationAlias(Class cls) {
		xstream.processAnnotations(cls);
	}

	public void setAnnotationAlias(List<Class> clsList)
	throws ClassNotFoundException {
		Iterator<Class> it = clsList.iterator();
		while(it.hasNext()){
			Class cls = it.next();
			if(logger.isDebugEnabled()) {
				logger.debug("class_to_alias - "+cls.getName());
			}
			xstream.processAnnotations(cls);
		}
	}

}
