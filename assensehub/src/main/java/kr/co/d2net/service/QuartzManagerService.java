package kr.co.d2net.service;

import java.util.List;

import kr.co.d2net.commons.exception.ServiceException;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * Cluster 환경을 구성한 Quartz(Open source Job scheduling)를 관리하기 위한 서비스.
 * Quartz는 Cluster를 구성하기 위해 DB를 이용하는데 12개의 테이블을 이용한다. 테이블이 없을경우 기본 생성이 되어야 하지만,
 * 다양한 구성환경으로 인해 문제가 발생할 확률이 높다. 결국, 현재 환경에 맞게 생성해주어야하기에 서버 구동시에 테이블이 없을경우
 * 생성해주는 로직을 추가했다.
 * 테이블명은 고정이며, 먼저 조회후 없는 테이블만 추가하도록 한다. 
 * 그리고 버그인지는 확인되지 않았지만, 스케줄러 시간이 변경되었을경우 트리거 실행 시간이 DB와의 동기화 및 추가,제거에 대한 동기화가
 * 즉시 반영되지 않았다. 결국, 서비 구동시 Quartz관련 테이블을 모두 초기화하는 부분을 추가했다.
 * </pre>
 * @author M.S. Kang
 *
 */
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
public interface QuartzManagerService {
	
	/**
	 * <pre>
	 * Quartz 관련 테이블을 모두 조회
	 * </pre>
	 * @return
	 * @throws ServiceException
	 */
	public List<String> findQuartzTables() throws ServiceException;
	
	/**
	 * 조회된 테이블의 목록을 받아서 사전에 정의된 테이블 목록에 존재하지 않는 테이블 생성
	 * @param tableNames
	 * @throws ServiceException
	 */
	@Transactional
	public void createQuartzTables(List<String> tableNames) throws ServiceException;
	
	/**
	 * Quartz관련 테이블 초기화
	 * @param params
	 * @throws ServiceException
	 */
	@Transactional
	public void quartzTableInitialize() throws ServiceException;
	
}
