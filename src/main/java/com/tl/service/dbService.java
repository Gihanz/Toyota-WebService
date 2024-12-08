package com.tl.service;

import java.util.List;
import java.util.Map;

public interface dbService {
	List<Map<String, String>> getAllBranches() throws Exception;
	String getReferenceNoByBranch(String branchCode) throws Exception;
	Map<String, Object> getUserInfoByUserId(String userId) throws Exception;
	Map<String, Object> getBranchDeptWorklistByUserId(String userId) throws Exception;
	List<Map<String, String>> getAllDepartments() throws Exception;
	List<String> getAllDepartmentTypes() throws Exception;
	List<Map<String, String>> getAllCPUProcClerks() throws Exception;
	List<Map<String, String>> getAllCPUProcOfficers() throws Exception;
	List<Map<String, String>> getAllDivisionalClerks() throws Exception;
	List<Map<String, String>> getAllGMs() throws Exception;
	Map<String, Object> getPurchaseCombinationByUserId(String userId) throws Exception;
	Map<String, Object> getPurchaseCategoriesByType(String purchaseType) throws Exception;
	Map<String, Object> getGoodsCategoriesByPCategory(String purchaseCategory) throws Exception;
	Map<String, Object> getBranchApprovalPath(String branchCode, String departmentType) throws Exception;
	List<String> getCPUHODEmails() throws Exception;
}
