package kr.co.d2net.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.ServiceException;
import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.commons.util.Utility;
import kr.co.d2net.dao.CodeDao;
import kr.co.d2net.dao.ContentsDao;
import kr.co.d2net.dao.ProFlDao;
import kr.co.d2net.dto.CodeTbl;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.dto.ProFlTbl;
import kr.co.d2net.dto.Search;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freemarker.template.utility.StringUtil;

@Service(value="codeManagerService")
public class CodeManagerServiceImpl implements CodeManagerService {
	
	private Log logger = LogFactory.getLog(CodeManagerServiceImpl.class);
	
	@Autowired
	private CodeDao codeDao;
	@Autowired
	private ProFlDao proFlDao;
	@Autowired
	private ContentsDao contentsDao;

	@SuppressWarnings("unchecked")
	public List<CodeTbl> findCode(Map<String, Object> params)
			throws ServiceException {
		List<CodeTbl> codeTbls = codeDao.findCode(params);
		return (codeTbls == null) ? Collections.EMPTY_LIST : codeTbls;
	}

	@Override
	public CodeTbl getCode(Map<String, Object> params) throws ServiceException {
		return codeDao.getCode(params);
	}
	
	@SuppressWarnings("unchecked")
	public List<CodeTbl> findCodeClfCd(Map<String, Object> params) throws ServiceException {
		List<CodeTbl> codeTbls = codeDao.findCodeClfCd(params);
		return (codeTbls == null) ? Collections.EMPTY_LIST : codeTbls;
	}

	@Override
	public void updateCode(CodeTbl codeTbl) throws ServiceException {
		codeDao.updateCode(codeTbl);
		
	}
	
	@Override
	public void insertCode(CodeTbl codeTbl) throws ServiceException {
		codeDao.insertCode(codeTbl);
		
	}

	
	@Override
	public String getWrkFileName(ContentsTbl contentsTbl,ContentsInstTbl contentsInstTbl)
			throws ServiceException {
		String wrkFileNm = "";
		
		try {
			if(StringUtils.isBlank(contentsTbl.getPgmGrpCd()) || StringUtils.isBlank(contentsTbl.getSegmentId()) || StringUtils.isBlank(contentsTbl.getPgmId())) {
				throw new ServiceException("Service Code Not Found - pgmGrpCd: "+contentsTbl.getPgmGrpCd()+", segmentId: "+contentsTbl.getSegmentId()+", pgmId: "+contentsTbl.getPgmId());

			} else {
				if(StringUtils.isBlank(contentsTbl.getCtCla()) || StringUtils.isBlank(contentsTbl.getCtTyp())) {
					throw new ServiceException("Content Code Not Found - ctCla: "+contentsTbl.getCtCla()+", ctTyp: "+contentsTbl.getCtTyp());
				} else {
						wrkFileNm = contentsTbl.getPgmGrpCd()+"_"+contentsTbl.getSegmentId()+"_"+Utility.getDate(contentsTbl.getBrdDd(), "yyyyMMdd")+"_"+contentsTbl.getPgmId();

						if(contentsTbl.getPgmNum() == null || contentsTbl.getPgmNum() <= 0) {
							contentsTbl.setPgmNum(0);
						}

						if(contentsTbl.getPgmNum() != null)
							wrkFileNm += "_"+StringUtil.leftPad(String.valueOf(contentsTbl.getPgmNum()), 2, "0");
						else 
							wrkFileNm += "_"+StringUtil.leftPad(String.valueOf(0), 2, "0");

						// 방송본이 아닐경우 콘텐츠유형[ctCla], 콘텐츠구분[ctTyp] 을 추가한다.
						// cla (00:방송본)

						
						logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						logger.debug(">>>>>>>>>>"+contentsTbl.getCtTyp());
						logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						if(!contentsTbl.getCtTyp().equals("00")) {
							if( !contentsTbl.getCtTyp().equals("12")){ // 본방VOD 유형은 본방과 동일한 파일네임 규칙을 적용한다.
								wrkFileNm += "_"+contentsTbl.getCtCla()+"_"+contentsTbl.getCtTyp();
							}
						}
						
						Map<String, Object> params = new HashMap<String, Object>();
						ProFlTbl proFlTbl = new ProFlTbl();
						params.put("proFlid", contentsInstTbl.getProFlid());
					
						proFlTbl = proFlDao.getProFl(params);
						
						wrkFileNm += "_"+proFlTbl.getFlNameRule();
				}
			}
		} catch (Exception e) {
			logger.error("getWrkFileName Create Error", e);
		}
		return wrkFileNm;
	}
	
	@Override
	public PaginationSupport<CodeTbl> findCodeListView(Search search) throws ServiceException {
		PaginationSupport<CodeTbl> codeList = codeDao.findcodeListView(search);

		return codeList;
	}

	@Override
	public int getContentsPgmNum(ContentsTbl contentsTbl)
			throws ServiceException {
		Map<String,Object> params = new HashMap<String, Object>();
		ContentsTbl tbl = new ContentsTbl();
		if(StringUtils.isBlank(contentsTbl.getCtCla())
				||StringUtils.isBlank(contentsTbl.getCtTyp())
				||StringUtils.isBlank(contentsTbl.getPgmId()))
			return 0;
		
		params.put("ctCla", contentsTbl.getCtCla());
		params.put("ctTyp", contentsTbl.getCtTyp());
		params.put("pgmId", contentsTbl.getPgmId());
		params.put("segmentId", contentsTbl.getSegmentId());
		
		tbl = contentsDao.getContentsPgmNum(params);
		
		if(tbl==null){
			return 0;
		}
		
		return tbl.getPgmNum();
	}



}
