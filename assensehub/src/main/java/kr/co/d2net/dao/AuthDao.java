package kr.co.d2net.dao;

import java.util.List;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.dto.AuthTbl;

public interface AuthDao {
	/**
	 * 권한목록 조회
	 * @return List<AuthTbl>
	 * @throws DaoNonRollbackException
	 */
	public List<AuthTbl> findAuth() throws DaoNonRollbackException;
}
