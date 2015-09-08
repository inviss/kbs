package kr.co.d2net.dao;

import java.util.List;
import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import kr.co.d2net.dto.EquipmentTbl;
import kr.co.d2net.dto.ProFlTbl;

public interface EquipmentDao {

	/**
	 * 디바이스 정보 조회/검색
	 * 
	 * @param search
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<EquipmentTbl> findEquipment(Map<String, Object> params)
			throws DaoNonRollbackException;

	/**
	 * 디바이스 정보 수정
	 * 
	 * @param equipmentTbl
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public int updateEquipment(EquipmentTbl equipmentTbl)
			throws DaoNonRollbackException;
	
	/**
	 * 디바이스 정보 삭제
	 * 
	 * @param equipmentTbl
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public int instdeleteEquipment(EquipmentTbl equipmentTbl)
			throws DaoNonRollbackException;
	
	/**
	 * 디바이스 정보 삭제
	 * 
	 * @param equipmentTbl
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public int devicedeleteEquipment(EquipmentTbl equipmentTbl)
			throws DaoNonRollbackException;
	
	/**
	 * 디바이스 정보 등록
	 * 
	 * @param equipmentTbl
	 * @return
	 * @throws DaoRollbackException
	 */
	public void insertEquipment(EquipmentTbl equipmentTbl) throws DaoRollbackException;
	
	/**
	 * 디바이스 정보 상세보기
	 * 
	 * @param 
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public EquipmentTbl getEquipment(Map<String, Object> params) throws DaoNonRollbackException;
	
	/**
	 * 디바이스카운트
	 * 
	 * @param 
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public EquipmentTbl getEquipmentCount(Map<String, Object> params) throws DaoNonRollbackException;
}
