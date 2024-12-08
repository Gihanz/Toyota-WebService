package com.tl.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.jdbc.ReturningWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.tl.dao.dbDao;
import com.tl.dao.exceptions.DAOException;

@Repository
public class dbDaoImpl extends abstractWFConfigdao implements dbDao{
	
private static Logger log =LoggerFactory.getLogger(dbDaoImpl.class);
	
	public List<Map<String, String>> getAllBranches() {		
		List<Map<String, String>> rtnData= new ArrayList<Map<String, String>>();
		 	 
		    String branchQry = null;
			
			try{
				branchQry = "SELECT DISTINCT BRANCH_CODE, BRANCH_NAME, BRANCH_GRADE, BRANCH_CONTACT_NUMBER FROM WFCONFIG.BRANCH_BASE";
				log.info("Query is "+branchQry);
				Query qry = session().createSQLQuery(branchQry);
				
				List<Object[]> lstCmplnt  = qry.list();
				if(lstCmplnt !=null)
				{									
					for(Object[] cmplt:lstCmplnt)
					{											
						if(null !=cmplt[0] && null !=cmplt[1] && null !=cmplt[2] && null !=cmplt[3])
						{										
							Map<String, String> branchData = new HashMap<String, String>();
							branchData.put("BranchCode", (String)cmplt[0]);
							branchData.put("BranchName", (String)cmplt[1]);
							branchData.put("BranchGrade", (String)cmplt[2]);
							branchData.put("BranchContactNumber", (String)cmplt[3]);
							rtnData.add(branchData);
						}
					}				
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getAllBranches");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getAllBranches",hex);
			}	
			return rtnData;
	}
	
	public List<Map<String, String>> getAllDepartments() {		
		List<Map<String, String>> rtnData= new ArrayList<Map<String, String>>();
		 	 
		    String departmentQry = null;
			
			try{
				departmentQry = "SELECT DISTINCT DEPARTMENT_CODE, DEPARTMENT_NAME, DEPARTMENT_CONTACT_NUMBER FROM WFCONFIG.DEPARTMENT_BASE";
				log.info("Query is "+departmentQry);
				Query qry = session().createSQLQuery(departmentQry);
				
				List<Object[]> lstCmplnt  = qry.list();
				if(lstCmplnt !=null)
				{									
					for(Object[] cmplt:lstCmplnt)
					{											
						if(null !=cmplt[0] && null !=cmplt[1] && null !=cmplt[2])
						{										
							Map<String, String> departmentData = new HashMap<String, String>();
							departmentData.put("DepartmentCode", (String)cmplt[0]);
							departmentData.put("DepartmentName", (String)cmplt[1]);
							departmentData.put("DepartmentContactNumber", (String)cmplt[2]);
							rtnData.add(departmentData);
						}
					}					
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getAllDepartments");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getAllDepartments",hex);
			}	
			return rtnData;
	}
	
	public List<String> getAllDepartmentTypes() {		
		List<String> rtnData= new ArrayList<String>();
		 	 
		    String departmentTypeQry = null;
			
			try{
				departmentTypeQry = "SELECT DISTINCT DEPARTMENT_TYPE FROM WFCONFIG.DEPARTMENT_BASE";
				log.info("Query is "+departmentTypeQry);
				Query qry = session().createSQLQuery(departmentTypeQry);
				
				List<Object[]> lstCmplnt  = qry.list();
				if(lstCmplnt !=null)
				{
					for (Object obj : lstCmplnt) { 
						rtnData.add(obj.toString());
			        } 				
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getAllDepartmentTypes");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getAllDepartmentTypes",hex);
			}	
			return rtnData;
	}
	
	public String getReferenceNoByBranch(final String branchCode) {
		String refereneceNo=null;
		try
		{
			refereneceNo = session().doReturningWork(
			new ReturningWork<String>() {
	
				@Override
				public String execute(Connection conn) throws SQLException {
					Calendar now = Calendar.getInstance();
					int year = now.get(Calendar.YEAR);
					String yearInString = String.valueOf(year);	
					
					CallableStatement call = conn.prepareCall("{call WFCONFIG.GET_REF_SEQUENCE_NUMBER(?,?,?)}");
					call.setString(1, branchCode);			
					call.setString(2, yearInString); 			
					call.registerOutParameter(3, new Integer(1));
					call.execute();
							
					String refNo = String.valueOf(call.getObject(3));
					
					String appendBranchCode = leftPad(branchCode, 3, '0');
					String appendRefNo = leftPad(refNo, 8, '0');
					
					refNo = appendBranchCode + yearInString + appendRefNo;
					return refNo;
				}
				
			});
			
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error(hex.getMessage());
			log.error("Error ", hex.fillInStackTrace());
			throw new DAOException("er.db.getReferenceNo",hex);
		}
		return refereneceNo;
	}
	
	public static String leftPad(String txt, int length, char pad){
		while(txt.length() < length){
			txt = pad + txt;
		}
		
		return txt;
	}
	
	public Map<String, Object> getUserInfoByUserId(final String userId) {		
		Map<String, Object> rtnData= new HashMap<String, Object>();
		 	 
		    String productQryString = null;
		    String roleQryString = null;
		    String userDataQryString = null;
		    String rptOfcDataQryString = null;
			
			try{
				productQryString = "SELECT DISTINCT productBase.Product from WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProduct join WFCONFIG.PRODUCT_BASE productBase on userRoleProduct.PRODUCT_ID=productBase.PRODUCT_ID where userRoleProduct.urpid in (select product.URPID from WFCONFIG.USER_ROLE_PRODUCT_MAPPING product join WFCONFIG.USR_BASE usrBase on product.UID = usrBase.UID where lower(usrBase.NT_ID)=lower('"+userId+"'))";
				log.info("Query is "+productQryString);
				Query productQry = session().createSQLQuery(productQryString);
				
				List<Object[]> productList  = productQry.list();
				if(productList !=null)
				{
					rtnData.put("Product", productList);	
				}
				
				roleQryString = "SELECT DISTINCT roleBase.role_name from WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProduct join WFCONFIG.ROLE_BASE roleBase on userRoleProduct.RID=roleBase.RID where userRoleProduct.urpid in (select product.URPID from WFCONFIG.USER_ROLE_PRODUCT_MAPPING product join WFCONFIG.USR_BASE usrBase on product.UID = usrBase.UID where lower(usrBase.NT_ID)=lower('"+userId+"'))";
				log.info("Query is "+roleQryString);
				Query roleQry = session().createSQLQuery(roleQryString);
				
				List<Object[]> roleList  = roleQry.list();
				if(roleList !=null)
				{
					rtnData.put("Role", roleList);	
				}
				
				userDataQryString = "SELECT DISTINCT usrBase.FIRST_NAME, usrBase.MIDDLE_NAME, usrBase.LAST_NAME, branchBase.BRANCH_CODE, branchBase.BRANCH_NAME, branchBase.BRANCH_GRADE, departmentBase.DEPARTMENT_CODE, departmentBase.DEPARTMENT_NAME, departmentBase.DEPARTMENT_TYPE, usrBase.CONTACT_NUMBER, usrBase.EMAIL_ADDRESS from WFCONFIG.BRANCH_BASE branchBase join WFCONFIG.USR_BASE usrBase on branchBase.BID=usrBase.BID join WFCONFIG.DEPARTMENT_BASE departmentBase on departmentBase.DID=usrBase.DID where lower(usrBase.NT_ID)=lower('"+userId+"')";
				log.info("Query is "+userDataQryString);
				Query userDataQry = session().createSQLQuery(userDataQryString);
				
				List<Object[]> userDataList  = userDataQry.list();
				if(userDataList !=null)
				{
					rtnData.put("FirstName", userDataList.get(0)[0]);
					rtnData.put("MiddleName", userDataList.get(0)[1]);
					rtnData.put("LastName", userDataList.get(0)[2]);
					rtnData.put("BranchCode", userDataList.get(0)[3]);
					rtnData.put("BranchName", userDataList.get(0)[4]);
					rtnData.put("BranchType", userDataList.get(0)[5]);
					rtnData.put("DepartmentCode", userDataList.get(0)[6]);
					rtnData.put("DepartmentName", userDataList.get(0)[7]);
					rtnData.put("DepartmentType", userDataList.get(0)[8]);
					rtnData.put("ContactNumber", userDataList.get(0)[9]);
					rtnData.put("EmailAddress", userDataList.get(0)[10]);
				}
				
				rptOfcDataQryString = "SELECT DISTINCT NT_ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, CONTACT_NUMBER from WFCONFIG.USR_BASE where uid in (select REPORTING_OFFICER_UID from WFCONFIG.USR_BASE where lower(nt_id)=lower('"+userId+"'))";
				log.info("Query is "+rptOfcDataQryString);
				Query rptOfcDataQry = session().createSQLQuery(rptOfcDataQryString);
				
				List<Object[]> rptOfcDataList  = rptOfcDataQry.list();
				if(rptOfcDataList !=null)
				{
					Map<String, String> rptOfcData = new HashMap<String, String>();
					rptOfcData.put("UserId", rptOfcDataList.get(0)[0].toString());
					rptOfcData.put("FirstName", rptOfcDataList.get(0)[1].toString());
					rptOfcData.put("MiddleName", rptOfcDataList.get(0)[2].toString());
					rptOfcData.put("LastName", rptOfcDataList.get(0)[3].toString());
					rptOfcData.put("ContactNumber", rptOfcDataList.get(0)[4].toString());
					rtnData.put("ReportingOfficer", rptOfcData);
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getUserInfoByUserId");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getUserInfoByUserId",hex);
			}	
			return rtnData;
	}
	
	public Map<String, Object> getBranchDeptWorklistByUserId(final String userId) {		
		Map<String, Object> rtnData= new HashMap<String, Object>();
		 	 
		    String productQryString = null;
		    String roleQryString = null;
		    String deptQryString = null;
		    String userDataQryString = null;
			
			try{
				productQryString = "SELECT DISTINCT productBase.Product from WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProduct join WFCONFIG.PRODUCT_BASE productBase on userRoleProduct.PRODUCT_ID=productBase.PRODUCT_ID where userRoleProduct.urpid in (select product.URPID from WFCONFIG.USER_ROLE_PRODUCT_MAPPING product join WFCONFIG.USR_BASE usrBase on product.UID = usrBase.UID where lower(usrBase.NT_ID)=lower('"+userId+"'))";
				log.info("Query is "+productQryString);
				Query productQry = session().createSQLQuery(productQryString);
				
				List<Object[]> productList  = productQry.list();
				if(productList !=null)
				{
					rtnData.put("Product", productList);	
				}
				
				roleQryString = "SELECT DISTINCT roleBase.role_name from WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProduct join WFCONFIG.ROLE_BASE roleBase on userRoleProduct.RID=roleBase.RID where userRoleProduct.urpid in (select product.URPID from WFCONFIG.USER_ROLE_PRODUCT_MAPPING product join WFCONFIG.USR_BASE usrBase on product.UID = usrBase.UID where lower(usrBase.NT_ID)=lower('"+userId+"'))";
				log.info("Query is "+roleQryString);
				Query roleQry = session().createSQLQuery(roleQryString);
				
				List<Object[]> roleList  = roleQry.list();
				if(roleList !=null)
				{
					rtnData.put("Role", roleList);	
				}
				
				deptQryString = "select departmentBase.DEPARTMENT_CODE, departmentBase.DEPARTMENT_NAME, departmentBase.DEPARTMENT_TYPE from WFCONFIG.DEPARTMENT_BASE departmentBase where departmentBase.DID in (select userRoleProductDepartmentMapping.DID from WFCONFIG.USER_ROLE_PRODUCT_DEPARTMENT_MAPPING userRoleProductDepartmentMapping join WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProductMapping on userRoleProductDepartmentMapping.URPID = userRoleProductMapping.URPID join WFCONFIG.USR_BASE usrBase on userRoleProductMapping.UID = usrBase.UID where lower(usrBase.NT_ID)=lower('"+userId+"'))";
				log.info("Query is "+deptQryString);
				Query deptQry = session().createSQLQuery(deptQryString);
				
				List<Object[]> deptList  = deptQry.list();
				if(deptList !=null)
				{
					List<String> departmentCodeList = new ArrayList<String>();
					List<String> departmentNameList = new ArrayList<String>();
					List<String> departmentTypeList = new ArrayList<String>();
					
					for(Object[] cmplt:deptList){
						departmentCodeList.add((String) cmplt[0]);
						departmentNameList.add((String) cmplt[1]);
						departmentTypeList.add((String) cmplt[2]);						
					}					
					rtnData.put("DepartmentCode", departmentCodeList);
					rtnData.put("DepartmentName", departmentNameList);
					rtnData.put("DepartmentType", departmentTypeList);
				}
				
				userDataQryString = "SELECT DISTINCT usrBase.FIRST_NAME, usrBase.MIDDLE_NAME, usrBase.LAST_NAME, branchBase.BRANCH_CODE, branchBase.BRANCH_NAME, branchBase.BRANCH_GRADE, departmentBase.DEPARTMENT_CODE, departmentBase.DEPARTMENT_NAME, departmentBase.DEPARTMENT_TYPE, usrBase.CONTACT_NUMBER from WFCONFIG.BRANCH_BASE branchBase join WFCONFIG.USR_BASE usrBase on branchBase.BID=usrBase.BID join WFCONFIG.DEPARTMENT_BASE departmentBase on departmentBase.DID=usrBase.DID where lower(usrBase.NT_ID)=lower('"+userId+"')";
				log.info("Query is "+userDataQryString);
				Query userDataQry = session().createSQLQuery(userDataQryString);
				
				List<Object[]> userDataList  = userDataQry.list();
				if(userDataList !=null)
				{
					rtnData.put("FirstName", userDataList.get(0)[0]);
					rtnData.put("MiddleName", userDataList.get(0)[1]);
					rtnData.put("LastName", userDataList.get(0)[2]);
					rtnData.put("BranchCode", userDataList.get(0)[3]);
					rtnData.put("BranchName", userDataList.get(0)[4]);
					rtnData.put("BranchType", userDataList.get(0)[5]);
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getBranchDeptWorklistByUserId");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getBranchDeptWorklistByUserId",hex);
			}	
			return rtnData;
	}
	
	public List<Map<String, String>> getAllCPUProcClerks() {		
		List<Map<String, String>> rtnData= new ArrayList<Map<String, String>>();
		 	 
		    String clerkQry = null;
			
			try{
				clerkQry = "SELECT DISTINCT usrBase.NT_ID, usrBase.FIRST_NAME, usrBase.MIDDLE_NAME, usrBase.LAST_NAME, usrBase.CONTACT_NUMBER from WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProduct join WFCONFIG.USR_BASE usrBase on userRoleProduct.UID=usrBase.UID join WFCONFIG.ROLE_BASE roleBase on userRoleProduct.RID=roleBase.RID where roleBase.ROLE_NAME='CPUProcClerk'";
				log.info("Query is "+clerkQry);
				Query qry = session().createSQLQuery(clerkQry);
				
				List<Object[]> lstCmplnt  = qry.list();
				if(lstCmplnt !=null)
				{									
					for(Object[] cmplt:lstCmplnt)
					{											
						if(null !=cmplt[0] && null !=cmplt[1] && null !=cmplt[2] && null !=cmplt[3])
						{										
							Map<String, String> clerkData = new HashMap<String, String>();
							clerkData.put("UserID", (String)cmplt[0]);
							clerkData.put("FirstName", (String)cmplt[1]);
							clerkData.put("MiddleName", (String)cmplt[2]);
							clerkData.put("LastName", (String)cmplt[3]);
							clerkData.put("ContactNumber", (String)cmplt[4]);
							rtnData.add(clerkData);
						}
					}					
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getAllCPUProcClerks");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getAllCPUProcClerks",hex);
			}	
			return rtnData;
	}
	
	public List<Map<String, String>> getAllCPUProcOfficers() {		
		List<Map<String, String>> rtnData= new ArrayList<Map<String, String>>();
		 	 
		    String officerQry = null;
			
			try{
				officerQry = "SELECT DISTINCT usrBase.NT_ID, usrBase.FIRST_NAME, usrBase.MIDDLE_NAME, usrBase.LAST_NAME, usrBase.CONTACT_NUMBER from WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProduct join WFCONFIG.USR_BASE usrBase on userRoleProduct.UID=usrBase.UID join WFCONFIG.ROLE_BASE roleBase on userRoleProduct.RID=roleBase.RID where roleBase.ROLE_NAME='CPUProcOfficer'";
				log.info("Query is "+officerQry);
				Query qry = session().createSQLQuery(officerQry);
				
				List<Object[]> lstCmplnt  = qry.list();
				if(lstCmplnt !=null)
				{									
					for(Object[] cmplt:lstCmplnt)
					{											
						if(null !=cmplt[0] && null !=cmplt[1] && null !=cmplt[2] && null !=cmplt[3])
						{										
							Map<String, String> clerkData = new HashMap<String, String>();
							clerkData.put("UserID", (String)cmplt[0]);
							clerkData.put("FirstName", (String)cmplt[1]);
							clerkData.put("MiddleName", (String)cmplt[2]);
							clerkData.put("LastName", (String)cmplt[3]);
							clerkData.put("ContactNumber", (String)cmplt[4]);
							rtnData.add(clerkData);
						}
					}					
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getAllCPUProcOfficers");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getAllCPUProcOfficers",hex);
			}	
			return rtnData;
	}
	
	public List<Map<String, String>> getAllDivisionalClerks() {		
		List<Map<String, String>> rtnData= new ArrayList<Map<String, String>>();
		 	 
		    String clerkQry = null;
			
			try{
				clerkQry = "SELECT DISTINCT usrBase.NT_ID, usrBase.FIRST_NAME, usrBase.MIDDLE_NAME, usrBase.LAST_NAME, usrBase.CONTACT_NUMBER from WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProduct join WFCONFIG.USR_BASE usrBase on userRoleProduct.UID=usrBase.UID join WFCONFIG.ROLE_BASE roleBase on userRoleProduct.RID=roleBase.RID where roleBase.ROLE_NAME='DivisionalClerk'";
				log.info("Query is "+clerkQry);
				Query qry = session().createSQLQuery(clerkQry);
				
				List<Object[]> lstCmplnt  = qry.list();
				if(lstCmplnt !=null)
				{									
					for(Object[] cmplt:lstCmplnt)
					{											
						if(null !=cmplt[0] && null !=cmplt[1] && null !=cmplt[2] && null !=cmplt[3])
						{										
							Map<String, String> clerkData = new HashMap<String, String>();
							clerkData.put("UserID", (String)cmplt[0]);
							clerkData.put("FirstName", (String)cmplt[1]);
							clerkData.put("MiddleName", (String)cmplt[2]);
							clerkData.put("LastName", (String)cmplt[3]);
							clerkData.put("ContactNumber", (String)cmplt[4]);
							rtnData.add(clerkData);
						}
					}					
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getAllDivisionalClerks");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getAllDivisionalClerks",hex);
			}	
			return rtnData;
	}
	
	public List<Map<String, String>> getAllGMs() {		
		List<Map<String, String>> rtnData= new ArrayList<Map<String, String>>();
		 	 
		    String gMQry = null;
			
			try{
				gMQry = "SELECT DISTINCT usrBase.NT_ID, usrBase.FIRST_NAME, usrBase.MIDDLE_NAME, usrBase.LAST_NAME, usrBase.CONTACT_NUMBER from WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProduct join WFCONFIG.USR_BASE usrBase on userRoleProduct.UID=usrBase.UID join WFCONFIG.ROLE_BASE roleBase on userRoleProduct.RID=roleBase.RID where roleBase.ROLE_NAME='GM'";
				log.info("Query is "+gMQry);
				Query qry = session().createSQLQuery(gMQry);
				
				List<Object[]> lstCmplnt  = qry.list();
				if(lstCmplnt !=null)
				{									
					for(Object[] cmplt:lstCmplnt)
					{											
						if(null !=cmplt[0] && null !=cmplt[1] && null !=cmplt[2] && null !=cmplt[3])
						{										
							Map<String, String> clerkData = new HashMap<String, String>();
							clerkData.put("UserID", (String)cmplt[0]);
							clerkData.put("FirstName", (String)cmplt[1]);
							clerkData.put("MiddleName", (String)cmplt[2]);
							clerkData.put("LastName", (String)cmplt[3]);
							clerkData.put("ContactNumber", (String)cmplt[4]);
							rtnData.add(clerkData);
						}
					}					
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getAllGMs");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getAllGMs",hex);
			}	
			return rtnData;
	}
	
	public Map<String, Object> getPurchaseCombinationByUserId(final String userId) {		
		Map<String, Object> rtnData= new HashMap<String, Object>();
		 	 
		    String purchaseCategoryQryString = null;
		    String purchaseTypeQryString = null;
		    String goodsCategoryQryString = null;
			
			try{
				purchaseCategoryQryString = "SELECT DISTINCT PURCHASE_CATEGORY FROM WFCONFIG.USER_PCATEGORY_PTYPE_GCATEGORY_MAPPING where lower(NT_ID)=lower('"+userId+"')";
				log.info("Query is "+purchaseCategoryQryString);
				Query purchaseCategoryQry = session().createSQLQuery(purchaseCategoryQryString);
				
				List<Object[]> purchaseCategoryList  = purchaseCategoryQry.list();
				if(purchaseCategoryList !=null)
				{
					rtnData.put("PurchaseCategory", purchaseCategoryList);
				}
				
				purchaseTypeQryString = "SELECT DISTINCT PURCHASE_TYPE FROM WFCONFIG.USER_PCATEGORY_PTYPE_GCATEGORY_MAPPING where lower(NT_ID)=lower('"+userId+"')";
				log.info("Query is "+purchaseTypeQryString);
				Query purchaseTypeQry = session().createSQLQuery(purchaseTypeQryString);
				
				List<Object[]> purchaseTypeList  = purchaseTypeQry.list();
				if(purchaseCategoryList !=null)
				{
					rtnData.put("PurchaseType", purchaseTypeList);
				}
				
				goodsCategoryQryString = "SELECT DISTINCT GOODS_CATEGORY FROM WFCONFIG.USER_PCATEGORY_PTYPE_GCATEGORY_MAPPING where lower(NT_ID)=lower('"+userId+"')";
				log.info("Query is "+goodsCategoryQryString);
				Query goodsCategoryQry = session().createSQLQuery(goodsCategoryQryString);
				
				List<Object[]> goodsCategoryList  = goodsCategoryQry.list();
				if(purchaseCategoryList !=null)
				{
					rtnData.put("GoodsCategory", goodsCategoryList);
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getPurchaseCombinationByUserId");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getPurchaseCombinationByUserId",hex);
			}	
			return rtnData;
	}
	
	public Map<String, Object> getPurchaseCategoriesByType(final String purchaseType) {		
		Map<String, Object> rtnData= new HashMap<String, Object>();
		 	 
		    String purchaseCategoryQryString = null;
			
			try{
				purchaseCategoryQryString = "SELECT DISTINCT PURCHASE_CATEGORY FROM WFCONFIG.USER_PCATEGORY_PTYPE_GCATEGORY_MAPPING where lower(PURCHASE_TYPE)=lower('"+purchaseType+"')";
				log.info("Query is "+purchaseCategoryQryString);
				Query purchaseCategoryQry = session().createSQLQuery(purchaseCategoryQryString);
				
				List<Object[]> purchaseCategoryList  = purchaseCategoryQry.list();
				if(purchaseCategoryList !=null)
				{
					rtnData.put("PurchaseCategory", purchaseCategoryList);	
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getPurchaseCategoriesByType");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getPurchaseCategoriesByType",hex);
			}	
			return rtnData;
	}
	
	public Map<String, Object> getGoodsCategoriesByPCategory(final String purchaseCategory) {		
		Map<String, Object> rtnData= new HashMap<String, Object>();
		 	 
		    String goodsCategoryQryString = null;
			
			try{
				goodsCategoryQryString = "SELECT DISTINCT GOODS_CATEGORY FROM WFCONFIG.USER_PCATEGORY_PTYPE_GCATEGORY_MAPPING where lower(PURCHASE_CATEGORY)=lower('"+purchaseCategory+"')";
				log.info("Query is "+goodsCategoryQryString);
				Query goodsCategoryQry = session().createSQLQuery(goodsCategoryQryString);
				
				List<Object[]> goodsCategoryList  = goodsCategoryQry.list();
				if(goodsCategoryList !=null)
				{
					rtnData.put("GoodsCategory", goodsCategoryList);	
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getGoodsCategoriesByPCategory");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getGoodsCategoriesByPCategory",hex);
			}	
			return rtnData;
	}
	
	public Map<String, Object> getBranchApprovalPath(final String branchCode, String departmentType) {		
		Map<String, Object> rtnData= new HashMap<String, Object>();
		 	 
		    String branchInchargeQryString = null;
		    String branchHODQryString = null;
			
			try{
				branchInchargeQryString = "SELECT COUNT(*) FROM WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProduct join WFCONFIG.ROLE_BASE roleBase on userRoleProduct.RID=roleBase.RID WHERE UID IN (SELECT userRoleProduct.UID FROM WFCONFIG.USR_BASE usrBase FULL OUTER JOIN WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProduct on usrBase.UID=userRoleProduct.UID FULL OUTER JOIN WFCONFIG.USER_ROLE_PRODUCT_DEPARTMENT_MAPPING userRoleProductDepartment on userRoleProduct.URPID=userRoleProductDepartment.URPID FULL OUTER JOIN WFCONFIG.DEPARTMENT_BASE departmentBase on userRoleProductDepartment.DID=departmentBase.DID FULL OUTER JOIN WFCONFIG.BRANCH_BASE branchBase on usrBase.BID=branchBase.BID WHERE branchBase.BRANCH_CODE='"+branchCode+"' AND departmentBase.DEPARTMENT_TYPE='"+departmentType+"') and roleBase.ROLE_NAME='BranchIncharge'";
				log.info("Query is "+branchInchargeQryString);
				Query branchInchargeQry = session().createSQLQuery(branchInchargeQryString);
				
				Long branchInchargeCount = ((Integer) branchInchargeQry.uniqueResult()).longValue();
				
				if(branchInchargeCount !=null)
				{
					if(branchInchargeCount>0){
						rtnData.put("BranchInchargeAvailable", true);
					}else{
						rtnData.put("BranchInchargeAvailable", false);
					}						
				}

				branchHODQryString = "SELECT COUNT(*) FROM WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProduct join WFCONFIG.ROLE_BASE roleBase on userRoleProduct.RID=roleBase.RID WHERE UID IN (SELECT userRoleProduct.UID FROM WFCONFIG.USR_BASE usrBase FULL OUTER JOIN WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProduct on usrBase.UID=userRoleProduct.UID FULL OUTER JOIN WFCONFIG.USER_ROLE_PRODUCT_DEPARTMENT_MAPPING userRoleProductDepartment on userRoleProduct.URPID=userRoleProductDepartment.URPID FULL OUTER JOIN WFCONFIG.DEPARTMENT_BASE departmentBase on userRoleProductDepartment.DID=departmentBase.DID FULL OUTER JOIN WFCONFIG.BRANCH_BASE branchBase on usrBase.BID=branchBase.BID WHERE branchBase.BRANCH_CODE='"+branchCode+"' AND departmentBase.DEPARTMENT_TYPE='"+departmentType+"') and roleBase.ROLE_NAME='BranchHOD'";
				log.info("Query is "+branchHODQryString);
				Query branchHODQry = session().createSQLQuery(branchHODQryString);				
				Long branchHODCount = ((Integer) branchHODQry.uniqueResult()).longValue();
				
				if(branchHODCount !=null)
				{
					if(branchHODCount>0){
						rtnData.put("BranchHODAvailable", true);
					}else{
						rtnData.put("BranchHODAvailable", false);
					}
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getBranchApprovalPath");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getBranchApprovalPath",hex);
			}	
			return rtnData;
	}
	
	public List<String> getCPUHODEmails() {		
		List<String> rtnData= new ArrayList<String>();
		 	 
		    String emailQry = null;
			
			try{
				emailQry = "SELECT DISTINCT usrBase.EMAIL_ADDRESS from WFCONFIG.USER_ROLE_PRODUCT_MAPPING userRoleProduct join WFCONFIG.USR_BASE usrBase on userRoleProduct.UID=usrBase.UID join WFCONFIG.ROLE_BASE roleBase on userRoleProduct.RID=roleBase.RID where roleBase.ROLE_NAME='CPUHOD'";
				log.info("Query is "+emailQry);
				Query qry = session().createSQLQuery(emailQry);
								
				List<Object[]> lstCmplnt  = qry.list();
				if(lstCmplnt !=null)
				{
					for (Object obj : lstCmplnt) { 
						rtnData.add(obj.toString());
			        } 				
				}
				
			} catch(HibernateException hex)
			{
				log.info("Error in getCPUHODEmails");
				hex.printStackTrace();
				log.error("Error ", hex.fillInStackTrace());
				log.error(hex.getMessage());
				throw new DAOException("er.db.getCPUHODEmails",hex);
			}	
			return rtnData;
	}

}
