package kr.co.d2net.commons.util;

import java.util.List;

import org.springframework.webflow.conversation.ConversationException;

import com.thoughtworks.xstream.converters.Converter;

/**
 * Java Object를 XML로 직렬화 하거나 XML을 Java Object로 역직렬화할때 사용.
 */
public interface XmlStream {
	/**
	 * <pre>
	 * 입력받은 XML을 Object로 역직력화할때 사용
	 * 역직렬화에 사용할 Class는 사전에 적용되어 있어야 한다.
	 * </pre>
	 * @param xml
	 * @return Class Object
	 */
	public Object fromXML(String xml) ;
	
	/**
	 * 단일 클래스를 역직렬화에 사용할경우
	 * @param xml
	 * @param cls
	 * @return Class Object
	 * @throws ClassNotFoundException
	 */
	public Object fromXML(String xml, Class cls);
	
	/**
	 * <pre>
	 * 사용할 모든 클래스를 List<Class>로 전달받아 등록한다.
	 * List<Class> clsList = new ArrayList<Class>();
	 * clsList.put(Sample1.class);
	 * clsList.put(Sample2.class);
	 * </pre>
	 * @param xml
	 * @param clsList
	 * @return Class Object
	 * @throws ClassNotFoundException
	 */
	public Object fromXML(String xml, List<Class> clsList) throws ClassNotFoundException ;
	
	/**
	 * <pre>
	 * 객체를 XML로 직렬화할때 사용.
	 * 사용할 Class는 사전에 적용되어있어야 한다.
	 * </pre>
	 * @param obj
	 * @return
	 */
	public String toXML(Object obj);
	
	/**
	 * 단일 Object를 직렬화할 경우 사용
	 * @param obj
	 * @param cls
	 * @return
	 */
	public String toXML(Object obj, Class cls);
	
	/**
	 * 직렬화에 사용할 클래스 List를 입력받아 사용할 경우
	 * @param obj
	 * @param clsList
	 * @return
	 * @throws ClassNotFoundException
	 */
	public String toXML(Object obj, List<Class> clsList) throws ClassNotFoundException;
	
	public void setAlias(String name, Class cls);
	public void setAnnotationAlias(Class cls) throws ClassNotFoundException;
	public void setAnnotationAlias(List<Class> clsList) throws ClassNotFoundException;
	
	public void setConverter(Converter converter) throws ConversationException;
}
