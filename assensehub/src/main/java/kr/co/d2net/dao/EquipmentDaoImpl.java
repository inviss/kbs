package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.EquipmentTbl;
import kr.co.d2net.dto.ProFlTbl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository(value = "equipmentDao")
public class EquipmentDaoImpl extends SqlMapClientDaoSupport implements
		EquipmentDao {

	private static Log logger = LogFactory.getLog(EquipmentDaoImpl.class);

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private EquipmentDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	public final void setSqlMapClientWorkaround(
			@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
	
	@Override
	public int instdeleteEquipment(EquipmentTbl equipmentTbl)
			throws DaoNonRollbackException {

		return getSqlMapClientTemplate().update("Equipment.instdeleteEquipment",
				equipmentTbl);
	}
	
	@Override
	public int devicedeleteEquipment(EquipmentTbl equipmentTbl)
			throws DaoNonRollbackException {

		return getSqlMapClientTemplate().update("Equipment.devicedeleteEquipment",
				equipmentTbl);
	}
	
	@Override
	public int updateEquipment(EquipmentTbl equipmentTbl)
			throws DaoNonRollbackException {

		return getSqlMapClientTemplate().update("Equipment.updateEquipment",
				equipmentTbl);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EquipmentTbl> findEquipment(Map<String, Object> params)
			throws DaoNonRollbackException {
		List<EquipmentTbl> cts = getSqlMapClientTemplate().queryForList(
				"Equipment.findEquipment", params);
		return cts;
	}
	
	@Override
	public void insertEquipment(EquipmentTbl equipmentTbl) throws DaoRollbackException {
		//logger.debug("--->"+proflTbl.getCtNm());
		
		getSqlMapClientTemplate().insert("Equipment.insertEquipment", equipmentTbl);
	}
	
	@Override
	public EquipmentTbl getEquipment(Map<String, Object> params) throws DaoNonRollbackException {
		return (EquipmentTbl)getSqlMapClientTemplate().queryForObject("Equipment.getEquipment", params);
	}
	
	@Override
	public EquipmentTbl getEquipmentCount(Map<String, Object> params) throws DaoNonRollbackException {
		return (EquipmentTbl)getSqlMapClientTemplate().queryForObject("Equipment.getEquipmentCount", params);
	}
}
