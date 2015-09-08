package kr.co.d2net.commons.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.co.d2net.commons.converter.xml.FileConverter;
import kr.co.d2net.commons.converter.xml.PropertyConverter;
import kr.co.d2net.dto.xml.aach.Corner;
import kr.co.d2net.dto.xml.aach.Corners;
import kr.co.d2net.dto.xml.aach.KBSExchangeMetadata;
import kr.co.d2net.dto.xml.archive.ArchiveReq;
import kr.co.d2net.dto.xml.archive.ArchiveRes;
import kr.co.d2net.dto.xml.dmcr.File;
import kr.co.d2net.dto.xml.dmcr.Files;
import kr.co.d2net.dto.xml.dmcr.GeneralInformation;
import kr.co.d2net.dto.xml.dmcr.Generator;
import kr.co.d2net.dto.xml.dmcr.KBSExchange;
import kr.co.d2net.dto.xml.dmcr.Metadata;
import kr.co.d2net.dto.xml.dmcr.TargetFolder;
import kr.co.d2net.dto.xml.internal.Content;
import kr.co.d2net.dto.xml.internal.ContentInst;
import kr.co.d2net.dto.xml.internal.FindList;
import kr.co.d2net.dto.xml.internal.Ftp;
import kr.co.d2net.dto.xml.internal.Job;
import kr.co.d2net.dto.xml.internal.Mnc;
import kr.co.d2net.dto.xml.internal.Option;
import kr.co.d2net.dto.xml.internal.Pgm;
import kr.co.d2net.dto.xml.internal.Report;
import kr.co.d2net.dto.xml.internal.Sgm;
import kr.co.d2net.dto.xml.internal.Status;
import kr.co.d2net.dto.xml.internal.Workflow;
import kr.co.d2net.dto.xml.meta.Node;
import kr.co.d2net.dto.xml.meta.Nodes;
import kr.co.d2net.dto.xml.meta.Properties;
import kr.co.d2net.dto.xml.mnc.KBSMetaData;
import kr.co.d2net.dto.xml.mnc.Sequence;
import kr.co.d2net.dto.xml.mnc.SequenceList;

import org.apache.log4j.Logger;
import org.springframework.webflow.conversation.ConversationException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

/**
 * <p>
 * Object를 기준으로 XML 생성
 * </p>
 * 
 * @author Kang Myeong Seong
 * 
 * <pre>
 * 
 * </pre>
 */
public class XmlStreamImpl implements XmlStream {

	private Logger logger = Logger.getLogger(getClass());

	private List<Class> clsList = new ArrayList<Class>();
	private XStream xstream;

	public XmlStreamImpl() {
		/*
		 * XML Parsing 할때 일반적으로 DomDriver를 사용하지만, XppDriver가 속도면에서 좀더 빠르단다.
		 * alias명으로 '_' 언더바(underscore)가 존재하면 '__'로 두개가 출력이 된다. 치환을 해줘야할 필요가 있다.
		 */
		xstream = new XStream(new PureJavaReflectionProvider(), new CDATASupportXppDriver(
				new XmlFriendlyReplacer("__", "_")));
		xstream.autodetectAnnotations(true);

		/** AssenseHub에서 사용하는 Class를 모두 등록 **/
		xstream.processAnnotations(Workflow.class);
		xstream.processAnnotations(Status.class);
		xstream.processAnnotations(Job.class);
		xstream.processAnnotations(Option.class);
		xstream.processAnnotations(Content.class);
		xstream.processAnnotations(ContentInst.class);
		xstream.processAnnotations(Pgm.class);
		xstream.processAnnotations(Sgm.class);
		xstream.processAnnotations(FindList.class);
		xstream.processAnnotations(ArchiveReq.class);
		xstream.processAnnotations(ArchiveRes.class);
		xstream.processAnnotations(Ftp.class);
		xstream.processAnnotations(Node.class);
		xstream.processAnnotations(Nodes.class);
		xstream.processAnnotations(Properties.class);
		xstream.processAnnotations(KBSMetaData.class);
		xstream.processAnnotations(Sequence.class);
		xstream.processAnnotations(SequenceList.class);
		xstream.processAnnotations(Report.class);
		xstream.processAnnotations(Mnc.class);

		xstream.registerConverter(new PropertyConverter());
		
		/* 2013.03.12 주조 XML 관련 추가*/
		xstream.processAnnotations(File.class);
		xstream.processAnnotations(Files.class);
		xstream.processAnnotations(GeneralInformation.class);
		xstream.processAnnotations(Generator.class);
		xstream.processAnnotations(KBSExchange.class);
		xstream.processAnnotations(Metadata.class);
		xstream.processAnnotations(TargetFolder.class);
		//xstream.processAnnotations(KBSExchangeMetadata.class);
		xstream.processAnnotations(Corners.class);
		xstream.processAnnotations(Corner.class);
		
		xstream.registerConverter(new FileConverter());

		try {
			setAnnotationAlias(clsList);
		} catch (Exception e) {
			logger.error("###################### [" + e.getMessage()+ "] ###########################");
		}

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

	public String toXML(Object obj, List<Class> clsList)
			throws ClassNotFoundException {
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
		while (it.hasNext()) {
			Class cls = it.next();
			if (logger.isDebugEnabled()) {
				logger.debug("class_to_alias - " + cls.getName());
			}
			xstream.processAnnotations(cls);
		}
	}

	@Override
	public void setConverter(Converter converter) throws ConversationException {
		xstream.registerConverter(converter);
	}

}
