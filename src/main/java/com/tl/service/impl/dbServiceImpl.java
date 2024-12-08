package com.tl.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tl.dao.dbDao;
import com.tl.service.dbService;

@Service
@Configurable
public class dbServiceImpl implements dbService{
	private static Logger log =LoggerFactory.getLogger(dbServiceImpl.class);
	@Autowired private dbDao dbDao = null;
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<Map<String, String>> getAllBranches() {
		return dbDao.getAllBranches();
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public String getReferenceNoByBranch(String branchCode) {
		return dbDao.getReferenceNoByBranch(branchCode);
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public Map<String, Object> getUserInfoByUserId(String userId) {
		return dbDao.getUserInfoByUserId(userId);
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public Map<String, Object> getBranchDeptWorklistByUserId(String userId) {
		return dbDao.getBranchDeptWorklistByUserId(userId);
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<Map<String, String>> getAllDepartments() {
		return dbDao.getAllDepartments();
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<String> getAllDepartmentTypes() {
		return dbDao.getAllDepartmentTypes();
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<Map<String, String>> getAllCPUProcClerks() {
		return dbDao.getAllCPUProcClerks();
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<Map<String, String>> getAllCPUProcOfficers() {
		return dbDao.getAllCPUProcOfficers();
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<Map<String, String>> getAllDivisionalClerks() {
		return dbDao.getAllDivisionalClerks();
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<Map<String, String>> getAllGMs() {
		return dbDao.getAllGMs();
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public Map<String, Object> getPurchaseCombinationByUserId(String userId) {
		return dbDao.getPurchaseCombinationByUserId(userId);
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public Map<String, Object> getPurchaseCategoriesByType(String purchaseType) {
		return dbDao.getPurchaseCategoriesByType(purchaseType);
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public Map<String, Object> getGoodsCategoriesByPCategory(String purchaseCategory) {
		return dbDao.getGoodsCategoriesByPCategory(purchaseCategory);
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public Map<String, Object> getBranchApprovalPath(String branchCode, String departmentType) {
		return dbDao.getBranchApprovalPath(branchCode, departmentType);
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<String> getCPUHODEmails() {
		return dbDao.getCPUHODEmails();
	}

}
