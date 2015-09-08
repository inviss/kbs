package kr.co.d2net.dao;

import com.ibatis.sqlmap.client.SqlMapClient;

public class BaseDao {

	public BaseDao() {
	}

	/**
	 * 요청 쿼리정보 조회
	 * 
	 * @param client
	 * @param id
	 * @param param
	 * @return
	 */
	public String getSqlString(SqlMapClient client, String id, Object param) {
		// ExtendedSqlMapClient extended = (ExtendedSqlMapClient)client;
		// MappedStatement stmt = extended.getMappedStatement(id);
		//
		// SessionScope sessionScope = new SessionScope();
		// StatementScope statementScope = new StatementScope(sessionScope);
		//
		// stmt.initRequest(statementScope);
		//
		// return "["+id+"]"+stmt.getSql().getSql(statementScope, param) + "\n"
		// + param;
		return null;
	}
}
