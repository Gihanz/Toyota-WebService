package com.tl.dao;

import java.util.List;
import java.util.Map;

public interface dbDao {
	List<Map<String, String>> getAllBranches();
	String getReferenceNoByBranch(String branchCode);
	Map<String, Object> getUserInfoByUserId(String userId);
	Map<String, Object> getBranchDeptWorklistByUserId(String userId);
	List<Map<String, String>> getAllDepartments();
	List<String> getAllDepartmentTypes();
	List<Map<String, String>> getAllCPUProcClerks();
	List<Map<String, String>> getAllCPUProcOfficers();
	List<Map<String, String>> getAllDivisionalClerks();
	List<Map<String, String>> getAllGMs();
	Map<String, Object> getPurchaseCombinationByUserId(String userId);
	Map<String, Object> getPurchaseCategoriesByType(String purchaseType);
	Map<String, Object> getGoodsCategoriesByPCategory(String purchaseCategory);
	Map<String, Object> getBranchApprovalPath(String branchCode, String departmentType);
	List<String> getCPUHODEmails();
}
