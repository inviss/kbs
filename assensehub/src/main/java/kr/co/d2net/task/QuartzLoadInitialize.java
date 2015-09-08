package kr.co.d2net.task;

import java.util.List;

import kr.co.d2net.commons.exception.ApplicationException;
import kr.co.d2net.service.QuartzManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * <pre>
 * Quartz 테이블 정보를 조회하여 존재하지 않는 테이블은 생성하고 기존 테이블에서 존재하는 정보는 구동전에 모두 초기화를 한다.
 * 서버 구동시에는 항상 스케줄러의 정보는 초기화를 하게 되고, 최신의 정보를 유지하도록 한다.
 * </pre>
 * @author M.S. Kang
 *
 */
public class QuartzLoadInitialize extends SqlMapClientDaoSupport {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private QuartzManagerService quartzManagerService;

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}

	public void init() {
		if(logger.isDebugEnabled()) {
			logger.debug("QuartzLoadInitialize Starting...");
		}

		try {
			// Quartz Table find
			List<String> tables = quartzManagerService.findQuartzTables();

			// Quartz 에서 사용하는 테이블 총 개수는 12개
			if(tables.size() < 12) {
				// 조회된 테이블 리스트중에서 빠진 테이블이 있다면 생성
				quartzManagerService.createQuartzTables(tables);
			}
			

			// 모든 테이블 초기화
			quartzManagerService.quartzTableInitialize();
		} catch (ApplicationException ae) {
			logger.error("QuartzLoadInitialize Error", ae);
		}
		if(logger.isDebugEnabled()) {
			logger.debug("QuartzLoadInitialize end");
		}
	}
}
