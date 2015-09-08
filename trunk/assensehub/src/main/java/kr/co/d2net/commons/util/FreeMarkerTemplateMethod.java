package kr.co.d2net.commons.util;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.dto.CodeTbl;
import kr.co.d2net.dto.MenuTbl;
import kr.co.d2net.service.CodeManagerService;
import kr.co.d2net.service.UserManagerService;
import kr.co.d2net.task.diagram.DailyDiagramXmlJob;
import kr.co.d2net.task.diagram.WeekDiagramRequestJob;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * <p>
 * FreeMarker Template Method
 * </p>
 * 
 * @author Myeong-Seong. Kang.
 * 
 *         <pre>
 * 화면에서 빈번하게 사용할 Function 모음
 * </pre>
 * @created: 2010. 10. 9.
 * @변경이력:
 * 
 */
public class FreeMarkerTemplateMethod {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserManagerService userManagerService;
	@Autowired
	private CodeManagerService codeManagerService;

	public class commonCode implements TemplateMethodModel {

		@SuppressWarnings("rawtypes")
		public TemplateModel exec(List args) throws TemplateModelException {
			if (args.get(0) == null || args.get(1) == null) {
				return  new SimpleScalar("");
			}
			String clfCd = (String) args.get(0);
			String sclCd = (String) args.get(1);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clfCd", clfCd);
			params.put("sclCd", sclCd);

			if (StringUtils.isBlank(sclCd)) {
				return new SimpleScalar("");
			}
			CodeTbl codeTbl = null;
			try {
				codeTbl = codeManagerService.getCode(params);
			} catch (ServiceException e) {
				logger.error("commonCode", e);
			}

			String cdNm = "";
			if (codeTbl != null)
				cdNm = codeTbl.getClfNm();

			return new SimpleScalar(cdNm);
		}
	}

	/**
	 * <p>
	 * 권한별 메뉴 리스트
	 * </p>
	 * 
	 * @author Kang Myeong Seong
	 * 
	 *         <pre>
	 * 사번을 입력받아 접근권한이 가능한 메뉴 리스트를 돌려준다.
	 * </pre>
	 */
	public class findUserMenus implements TemplateMethodModel {
		@SuppressWarnings("rawtypes")
		public TemplateModel exec(List arg0) throws TemplateModelException {
			List<MenuTbl> menuList = new ArrayList<MenuTbl>();
			Map<String, Object> params = new HashMap<String, Object>();
			try {
				String userId = (String) arg0.get(0);
				params.put("userId", userId);
				menuList = userManagerService.findUserMenus(params);
			} catch (ServiceException e) {
				logger.error("findUserMenus", e);
			}

			return (TemplateModel) BeansWrapper.getDefaultInstance().wrap(
					menuList);
		}
	}

	public class getAccessRule implements TemplateMethodModel {

		public TemplateModel exec(List args) throws TemplateModelException {

			String userId = (String) args.get(0);
			String menuId = (String) args.get(1);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("menuId", menuId);
			String auth = "";
			try {
				auth = userManagerService.getUserControlAuth(params);
				logger.debug("getAccessRule:" + auth);
			} catch (Exception e) {
				logger.error("getAccessRule", e);
			}
			return new SimpleScalar(auth);
		}
	}
	
	public class getCodeDesc implements TemplateMethodModel {

		public TemplateModel exec(List args) throws TemplateModelException {

			String clfCd = (String) args.get(0);
			String sclCd = (String) args.get(1);

			if(StringUtils.isBlank(clfCd)||StringUtils.isBlank(sclCd)){
				return new SimpleScalar("");
			}
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("clfCd", clfCd);
			params.put("sclCd", sclCd);
			CodeTbl codeTbl = new CodeTbl();

			try {
				codeTbl = codeManagerService.getCode(params);				
			} catch (Exception e) {
				logger.error("getCodeDesc", e);
			}
			return new SimpleScalar((codeTbl==null)?"":codeTbl.getClfNm());
		}
	}
	
	public class getWrapperRmk implements TemplateMethodModel {

		public TemplateModel exec(List args) throws TemplateModelException {

			String clfCd = (String) args.get(0);
			String sclCd = (String) args.get(1);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clfCd", clfCd);
			params.put("sclCd", sclCd);
			CodeTbl codeTbl = new CodeTbl();
	
			try {
				codeTbl = codeManagerService.getCode(params);
			} catch (Exception e) {
				logger.error("getWrapperRmk", e);
			}
			return new SimpleScalar((codeTbl.getRmk1()==null)?"":codeTbl.getRmk1());
		}
	}

	public class commonUtils implements TemplateMethodModel {
		public TemplateHashModel exec(List args) throws TemplateModelException {
			BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
			TemplateHashModel staticModels = wrapper.getStaticModels();

			return (TemplateHashModel) staticModels
					.get("kr.co.d2net.commons.util.Utility");
		}
	}

	public class getIndexOf implements TemplateMethodModel {
		public TemplateModel exec(List args) throws TemplateModelException {
			if (args.size() != 2) {
				throw new TemplateModelException("Wrong arguments");
			}
			return new SimpleNumber(
					((String) args.get(1)).indexOf((String) args.get(0)));
		}
	}
	
	public class getStringCut implements TemplateMethodModel {
		public TemplateModel exec(List args) throws TemplateModelException {

			String str = (String) args.get(0);
			Integer index = Integer.valueOf((String) args.get(1));

			StringBuffer sbStr = new StringBuffer(index);
	
			try {
				int iTotal=0;
				if(str.length() > 0) {
					for(char c: str.toCharArray()) {
						iTotal+=String.valueOf(c).getBytes().length;
						if(iTotal > index) {
							break;
						}
						sbStr.append(c);
					}
				}
			} catch (Exception e) {
				logger.error("getStringCut", e);
			}
			return new SimpleScalar(sbStr.toString());
		}
	}

	public class getUrlEncode implements TemplateMethodModel {
		public TemplateModel exec(List args) throws TemplateModelException {

			String str = (String) args.get(0);
			Integer index = Integer.valueOf((String) args.get(1));

			String sStr ="";
	
			try {
				if(str.length() > 0) {
					sStr = URLEncoder.encode(String.valueOf(str),"utf-8").toString();
				}
			} catch (Exception e) {
				logger.error("getUrlEncode", e);
			}
			return new SimpleScalar(sStr.toString());
		}
	}
	
	public class getChangeTime implements TemplateMethodModel {
		public TemplateModel exec(List args) throws TemplateModelException {

			String channelCd = (String) args.get(0);
			String localCd = (String) args.get(1);
			String searchGb = (String) args.get(2);

			String sStr ="";
			
			try {
				if(searchGb.equals("W")) { // 주간편성표 확정일자
					if(WeekDiagramRequestJob.confirmDate.containsKey(channelCd+localCd)) {
						Timestamp confirmDate = Utility.getTimestamp(WeekDiagramRequestJob.confirmDate.get(channelCd+localCd), "yyyyMMddHHmmss");
						sStr = Utility.getTimestamp(confirmDate, "yyyy-MM-dd HH:mm:ss");
					}
				} else { // 일일운행표 확정일자
					if(DailyDiagramXmlJob.confirmDate.containsKey(channelCd)) {
						Timestamp confirmDate = Utility.getTimestamp(DailyDiagramXmlJob.confirmDate.get(channelCd), "yyyyMMddHHmmss");
						sStr = Utility.getTimestamp(confirmDate, "yyyy-MM-dd HH:mm:ss");
					}
				}
			} catch (Exception e) {
				logger.error("getChangeTime", e);
			}
			return new SimpleScalar(sStr.toString());
		}
	}
	
	
	public void init(Map<String, Object> ftlMap) {
		ftlMap.put("commonCode", new commonCode());
		ftlMap.put("findUserMenus", new findUserMenus());
		ftlMap.put("commonUtils", new commonUtils());
		ftlMap.put("getIndexOf", new getIndexOf());
		ftlMap.put("getAccessRule", new getAccessRule());
		
		ftlMap.put("getUrlEncode", new getUrlEncode());
		ftlMap.put("getCodeDesc", new getCodeDesc());
		ftlMap.put("getWrapperRmk", new getWrapperRmk());
		ftlMap.put("getStringCut", new getStringCut());
		
		ftlMap.put("getChangeTime", new getChangeTime());
	}
}
