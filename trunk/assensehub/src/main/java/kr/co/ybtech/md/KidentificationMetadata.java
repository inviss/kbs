package kr.co.ybtech.md;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;



import org.apache.log4j.Logger;
import org.nca.uci.md.Classification;
import org.nca.uci.md.Contribution;
import org.nca.uci.md.Identifier;
import org.nca.uci.md.Metadata;
import org.nca.uci.md.MetadataVerifyException;
import org.nca.uci.md.Validator;
import org.nca.uci.resolution.UCIParser;
import org.nca.uci.resolution.UCISyntaxException;
import org.nca.uci.util.StringUtil;
import org.nca.util.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 식별메타데이터(Identification Metadata).
 */
public class KidentificationMetadata implements Metadata {

	private static Logger logger = Logger.getLogger(KidentificationMetadata.class);

	public final static String URI_UCI = "http://www.uci.or.kr/terms/";

	public final static String URI_DC = "http://purl.org/dc/elements/1.1/";
	
	public final static String UCI_XSI = "http://www.w3.org/2001/XMLSchema-instance";

	final static String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

	final static String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

	final static String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	/**
	 * 식별메타데이터 검증을 위한 XML 스키마 정의의 경로.
	 *
	 * http://www.uci.or.kr/terms/uci_meta.xsd
	 */
	// final static String XML_SCHEMA =
	// "file:///C:/workspace/uci/schema/uci_meta.xsd";
	final static String XML_SCHEMA = "http://www.uci.or.kr/terms/uci_metadata_2.0.wtc.xsd";

	/**
	 * 표현형태의 기본값(Visual).
	 */
	final static String DEFAULT_MODE = "시각";

	/**
	 * 구조적 유형의 기본값(Digital).
	 */
	final static String DEFAULT_TYPE = "디지털";

	private Vector identifiers = new Vector();

	private Vector titles = new Vector();

	private Vector contributors = new Vector();

	private Vector classifications = new Vector();
	
	private Vector urlLists = new Vector(); //변환 url리스트

	private String mode = DEFAULT_MODE;

	private String format;

	private String type = DEFAULT_TYPE;

	private String uci;
	
	private String c_type; //컨텐츠타입
	
	private String desc;
	
	private String h_size; //컨텐츠타입
	
	private UCIParser parser;

	public KidentificationMetadata() {
	}

	public KidentificationMetadata(String xml) throws MetadataVerifyException {
		parse(xml);
	}

	public Classification[] getClassification() {
		int size = classifications.size();
		if (size == 0)
			return null;
		Classification[] result = new Classification[size];
		classifications.toArray(result);
		return result;
	}
	
	//추가.. 변환URL
	public resUrl[] getUrlList(){
		int size = urlLists.size();
		resUrl[] result = new resUrl[size];
		urlLists.toArray(result);
		resUrl re = (resUrl) urlLists.get(0);
		return result;
	}
	//추가
	public void addUrlList(String url_type,String url) {
		addUrlList(new resUrl(url_type,url));
	}
	//추가
	public void addUrlList(resUrl resUrl) {
		urlLists.add(resUrl);
	}

	/**
	 * 기여자.
	 *
	 * @return Returns the contributor.
	 */
	public Contribution[] getContribution() {
		int size = contributors.size();
		if (size == 0)
			return null;
		Contribution[] result = new Contribution[size];
		contributors.toArray(result);
		return result;
	}

	/**
	 * 기여자.
	 *
	 * @param contributor
	 *            The contributor to set.
	 */
	public void addContribution(Contribution contributor) {
		this.contributors.add(contributor);
	}

	/**
	 * 파일형식.
	 *
	 * @return Returns the format.
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * 파일형식.
	 *
	 * @param format
	 *            The format to set.
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * 기존식별자.
	 *
	 * @return Returns the identifer.
	 */
	public Identifier[] getIdentifier() {
		int size = identifiers.size();
		Identifier[] result = new Identifier[size];
		identifiers.toArray(result);
		return result;
	}

	/**
	 * 기존식별자.
	 *
	 * @param scheme
	 *            기존식별자 스킴.
	 * @param value
	 *            기존식별자 값.
	 */
	public void addIdentifier(String scheme, String value) {
		addIdentifier(new Identifier(scheme, value));
	}

	/**
	 * 기존식별자.
	 *
	 * @param identifer
	 *            The identifer to set.
	 */
	public void addIdentifier(Identifier identifer) {
		identifiers.add(identifer);
	}

	public void addClassification(String classScheme, String className, String classCode) {
		classifications.add(new Classification(classScheme, className, classCode));
	}

	public void addClassification(Classification classification) {
		classifications.add(classification);
	}

	/**
	 * 표현형태.
	 *
	 * @return Returns the mode.
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * 표현형태.
	 *
	 * @param mode
	 *            The mode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * 제목.
	 *
	 * @return Returns the title.
	 */
	public String[] getTitle() {
		int size = titles.size();
		if (size == 0)
			return null;
		String[] result = new String[size];
		titles.toArray(result);
		return result;
	}

	/**
	 * 제목.
	 *
	 * @param title
	 *            The title to set.
	 */
	public void addTitle(String title) {
		this.titles.add(title);
	}

	/**
	 * UCI
	 *
	 * @return Returns the uCI.
	 */
	public String getUCI() {
		return uci;
	}

	/**
	 * UCI
	 *
	 * @param uci
	 *            The uCI to set.
	 */
	public void setUCI(String uci) {
		this.uci = uci;
	}

	public void clear() {
		uci = null;
		parser = null;
		identifiers.clear();
		titles.clear();
		urlLists.clear();
		contributors.clear();
		classifications.clear();
		mode = DEFAULT_MODE;
		format = null;
		type = DEFAULT_TYPE;
		c_type = null;
		desc = null;
		h_size = null;
		titles.clear();
		license = null;
		orgdata = null;
		ra_sign = null;
	}

	public void verify() throws MetadataVerifyException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);

		try {
			factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		} catch (IllegalArgumentException e) {
			// java.lang.IllegalArgumentException:
			// http://java.sun.com/xml/jaxp/properties/schemaLanguage
			logger.error(e.getMessage(),e);
		}
		try {
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Validator handler = new Validator();
			documentBuilder.setErrorHandler(handler);
			factory.setAttribute(JAXP_SCHEMA_SOURCE, XML_SCHEMA);
			InputSource input = new InputSource(new StringReader(toString()));
			documentBuilder.parse(input);
			if (handler.validationError) {
				throw new MetadataVerifyException(handler.saxParseException.getMessage(), handler.saxParseException);
			}
		} catch (Exception e) {
			throw new MetadataVerifyException(e.getMessage(), e);
		}
	}

	/**
	 * 구조적유형.
	 *
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * 구조적유형.
	 *
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	public void parse(String xml) throws MetadataVerifyException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);

		try {
			factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		} catch (IllegalArgumentException e) {
			// java.lang.IllegalArgumentException:
			// http://java.sun.com/xml/jaxp/properties/schemaLanguage
			logger.error(e.getMessage(), e);
		}
		try {
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Validator handler = new Validator();
			documentBuilder.setErrorHandler(handler);
			factory.setAttribute(JAXP_SCHEMA_SOURCE, XML_SCHEMA);
			InputSource input = new InputSource(new StringReader(xml));
			Document document = documentBuilder.parse(input);
			set(document);
			if (handler.validationError) {
				throw new MetadataVerifyException(handler.saxParseException.getMessage(), handler.saxParseException);
			}
		} catch (Exception e) {
			throw new MetadataVerifyException(e.getMessage(), e);
		}
	}

	public void set(org.w3c.dom.Document document) throws MetadataVerifyException {
		try {
			clear();
			Element root = document.getDocumentElement();
			Element instance = getChild(root, "Instance");
			if (instance == null)
				throw new MetadataVerifyException("Instance not found.");
			setUCI(getChildText(instance, "uci:UCI"));
			NodeList identifiers = instance.getElementsByTagName("dc:identifier");
			int count;
			count = identifiers.getLength();
			for (int i = 0; i < count; i++) {
				Element e = (Element) identifiers.item(i);
				addIdentifier(new Identifier(e.getAttribute("scheme"), XMLUtil.getTextContent(e)));
			}
			NodeList titles = instance.getElementsByTagName("dc:title");
			count = titles.getLength();
			for (int i = 0; i < count; i++) {
				Element e = (Element) titles.item(i);
				addTitle(XMLUtil.getTextContent(e));
			}
			
			NodeList urls = instance.getElementsByTagName("uci:url");
			count = urls.getLength();
			for (int i = 0; i < count; i++) {
				Element e = (Element) urls.item(i);
				addUrlList(new resUrl(e.getAttribute("type"), XMLUtil.getTextContent(e)));
			}
			setType(getChildText(instance, "uci:type"));
			setMode(getChildText(instance, "uci:mode"));
			setFormat(getChildText(instance, "dc:format"));
			NodeList classifications = instance.getElementsByTagName("uci:classification");
			count = classifications.getLength();
			for (int i = 0; i < count; i++) {
				Element e = (Element) classifications.item(i);
				addClassification(getChildText(e, "uci:classScheme"), getChildText(e, "uci:className"), getChildText(e, "uci:classCode"));
			}
			NodeList contributions = instance.getElementsByTagName("uci:contribution");
			count = contributions.getLength();
			for (int i = 0; i < count; i++) {
				Element e = (Element) contributions.item(i);
				Contribution contribution = new Contribution(getChildText(e, "uci:contributor"));
				NodeList roles = e.getElementsByTagName("uci:contributorRole");
				int roleCount = roles.getLength();
				for (int r = 0; r < roleCount; r++) {
					Element role = (Element) roles.item(r);
					contribution.addRole(XMLUtil.getTextContent(role));
				}
				addContribution(contribution);
			}
			
			//////////////////////////////////////////////////////////////////
			setC_type(getChildText(instance, "uci:c_type")); //컨텐츠 타입
			setDesc(getChildText(instance, "uci:desc")); 
			setH_size(getChildText(instance, "uci:h_size"));
			setLicense(getChildText(instance, "uci:license"));
			setOrgdata(getChildText(instance, "uci:orgdata"));
			setRa_sign(getChildText(instance, "uci:ra_sign"));
			
		} catch (Exception e) {
			throw new MetadataVerifyException(e.getMessage(), e);
		}
	}

	public org.w3c.dom.Document toDocument() {
		int count;
		Document document = XMLUtil.newDocument();
		document.appendChild(document.createElement("UCIdata"));
		Element root = document.getDocumentElement();
		root.setAttribute("xmlns:uci", "http://www.uci.or.kr/terms/");
		root.setAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
		root.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:noNamespaceSchemaLocation", "http://www.uci.or.kr/terms/uci_metadata_2.0.wtc.xsd");

		org.w3c.dom.Element idmd = document.createElement("Instance");
		root.appendChild(idmd);

		if (getUCI() != null) {
			org.w3c.dom.Element uci = document.createElementNS(URI_UCI, "uci:UCI");
			uci.appendChild(document.createTextNode(getUCI()));
			idmd.appendChild(uci);
		}

		count = getIdentifier().length;
		for (int i = 0; i < count; i++) {
			Identifier identifier = getIdentifier()[i];
			Element e = document.createElementNS(URI_DC, "dc:identifier");
			if (identifier.getScheme() != null)
				e.setAttribute("scheme", identifier.getScheme());
			if (identifier.getValue() != null)
				e.appendChild(document.createTextNode(identifier.getValue()));
			idmd.appendChild(e);
		}

		count = getTitle().length;
		for (int i = 0; i < count; i++) {
			String title = getTitle()[i];
			Element e = document.createElementNS(URI_DC, "dc:title");
			if (title != null)
				e.appendChild(document.createTextNode(title));
			idmd.appendChild(e);
		}

		if (StringUtil.assigned(getType())) {
			Element e = document.createElementNS(URI_UCI, "uci:type");
			e.appendChild(document.createTextNode(getType()));
			idmd.appendChild(e);
		}

		if (StringUtil.assigned(getMode())) {
			Element e = document.createElementNS(URI_UCI, "uci:mode");
			e.appendChild(document.createTextNode(getMode()));
			idmd.appendChild(e);
		}

		if (StringUtil.assigned(getFormat())) {
			Element e = document.createElementNS(URI_DC, "dc:format");
			e.appendChild(document.createTextNode(getFormat()));
			idmd.appendChild(e);
		}
		
		count = (getClassification() == null) ? 0 : getClassification().length;
		for (int i = 0; i < count; i++) {
			Classification c = getClassification()[i];
			Element classification = document.createElementNS(URI_UCI, "uci:classification");
			if (c.getClassScheme() != null) {
				Element e = document.createElementNS(URI_UCI, "uci:classScheme");
				e.appendChild(document.createTextNode(c.getClassScheme()));
				classification.appendChild(e);
			}
			if (c.getClassName() != null) {
				Element e = document.createElementNS(URI_UCI, "uci:className");
				e.appendChild(document.createTextNode(c.getClassName()));
				classification.appendChild(e);
			}
			if (c.getClassCode() != null) {
				Element e = document.createElementNS(URI_UCI, "uci:classCode");
				e.appendChild(document.createTextNode(c.getClassCode()));
				classification.appendChild(e);
			}
			idmd.appendChild(classification);
		}

		Contribution[] contributions = getContribution();
		count = contributions == null ? 0 : getContribution().length;

		for (int i = 0; i < count; i++) {
			Contribution c = getContribution()[i];
			if (c != null && c.getContributor() != null) {
				String[] roles = c.getRole();
				for (int r = 0; roles != null && r < roles.length; r++) {
					Element contribution = document.createElementNS(URI_UCI, "uci:contribution");
					Element contributor = document.createElementNS(URI_UCI, "uci:contributor");
					contributor.appendChild(document.createTextNode(c.getContributor()));
					contribution.appendChild(contributor);

					Element contributorRole = document.createElementNS(URI_UCI, "uci:contributorRole");
					contributorRole.appendChild(document.createTextNode(roles[r]));
					contribution.appendChild(contributorRole);
					idmd.appendChild(contribution);
				}
			}
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
		
		if (StringUtil.assigned(getC_type())){ //추가
			Element e = document.createElementNS(URI_UCI,"uci:c_type");
			e.appendChild(document.createTextNode(getC_type()));
			idmd.appendChild(e);
		}
		
		if (StringUtil.assigned(getDesc())){ //추가
			Element e = document.createElementNS(URI_UCI,"uci:desc");
			e.appendChild(document.createTextNode(getDesc()));
			idmd.appendChild(e);
		}
		
		if (StringUtil.assigned(getH_size())){ //추가
			Element e = document.createElementNS(URI_UCI,"uci:h_size");
			e.appendChild(document.createTextNode(getH_size()));
			idmd.appendChild(e);
		}
		
		count = getUrlList().length; //추가
		for (int i = 0; i < count; i++) {
			resUrl url = getUrlList()[i];
			Element e = document.createElementNS(URI_UCI,"uci:url");
			if (url.getUrl_type() != null)
				e.setAttribute("type", url.getUrl_type());
			if(url.getUrl() != null)
				e.appendChild(document.createTextNode(url.getUrl()));
			idmd.appendChild(e);
		}		
		if (StringUtil.assigned(getLicense())) {
			Element e = document.createElementNS(URI_UCI, "uci:license");
			e.appendChild(document.createTextNode(getLicense()));
			idmd.appendChild(e);
		}
 
		if (StringUtil.assigned(getOrgdata())) {
			Element e = document.createElementNS(URI_UCI, "uci:orgdata");
			e.appendChild(document.createTextNode(getOrgdata()));
			idmd.appendChild(e);
		}
 
		if (StringUtil.assigned(getRa_sign())) {
			Element e = document.createElementNS(URI_UCI, "uci:ra_sign");
			e.appendChild(document.createTextNode(getRa_sign()));
			idmd.appendChild(e);
		}
 
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		return document;
	}

	public UCIParser getParser() throws UCISyntaxException {
		if (parser == null)
			parser = new UCIParser(uci);
		return parser;
	}

	public String getPrefix() throws UCISyntaxException {
		return getParser().getPrefix();
	}

	protected Element getChild(Element parent, String tagName) {
		return XMLUtil.getChild(parent, tagName);
	}

	protected String getChildText(Element parent, String tagName) {
		return XMLUtil.getChildText(parent, tagName);
	}

	public String toString() {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(toDocument());
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			transformer.transform(source, result);
			return sw.toString();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private String license;//이용허락구분
	private String orgdata;//진본확인서비스
	private String ra_sign; //RA 전자서명값

	/**
	 * 이용허락구분.
	 *
	 * @return Returns the license.
	 */
	public String getLicense () {
		return license;
	}

	/**
	 * 이용허락구분.
	 *
	 * @param license
	 *            The license to set.
	 */
	public void setLicense(String license) {
		this.license = license;
	}

 

	/**
	 * 진본확인서비스.
	 *
	 * @return Returns the original_data.
	 */
	public String getOrgdata () {
		return orgdata;
	}

	/**
	 * 진본확인서비스.
	 *
	 * @param original_source
	 *            The original_source to set.
	 */
	public void setOrgdata(String orgdata) {
		this.orgdata = orgdata;
	}

	/**
	 * RA 전자서명값.
	 * RA의 전자서명값은 UCI$^^$기관Prefix$^^$콘텐츠hash -> $^^$를 구분자로 전자서면한 값임.
	 * 총괄기구에서 전자서명값은 UCI$^^$UCI총괄기구$^^$콘텐츠hash$^^$등록시점 --> $^^$를 구분자로 하여 전자서명값을 전송
	 *
	 * @return Returns the ra_sign.
	 */
	public String getRa_sign () {
		return ra_sign;
	}

	/**
	 * RA 전자서명값.
	 * RA의 전자서명값은 UCI$^^$기관Prefix$^^$콘텐츠hash -> $^^$를 구분자로 전자서면한 값임.
	 * 총괄기구에서 전자서명값은 UCI$^^$UCI총괄기구$^^$콘텐츠hash$^^$등록시점 --> $^^$를 구분자로 하여 전자서명값을 전송
	 * @param ra_sign
	 *            The ra_sign to set.
	 */
	public void setRa_sign(String ra_sign) {
		this.ra_sign = ra_sign;
	}

	public String getC_type() {
		return c_type;
	}

	public void setC_type(String c_type) {
		this.c_type = c_type;
	}

	public String getH_size() {
		return h_size;
	}

	public void setH_size(String h_size) {
		this.h_size = h_size;
	}
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
}
