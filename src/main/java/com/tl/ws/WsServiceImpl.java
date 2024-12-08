package com.tl.ws;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tl.service.exceptions.BSLException;
import com.tl.service.impl.dbServiceImpl;

@RestController
public class WsServiceImpl extends BaseWebServiceEndPointImpl{
	
	private static Logger log =LoggerFactory.getLogger(WsServiceImpl.class);	
	@RequestMapping(value="/test")
	public String testService()
	{
		return "Service working fine";
	}
	
	@RequestMapping(value="/getAllBranches",produces="application/json")
	public List<Map<String, String>> getAllBranches()
	{		
		try
		{
			return dbService.getAllBranches();
		}		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}		
	}
	
	@RequestMapping(value="/getAllDepartments",produces="application/json")
	public List<Map<String, String>> getAllDepartments()
	{		
		try
		{
			return dbService.getAllDepartments();
		}		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}		
	}
	
	@RequestMapping(value="/getAllDepartmentTypes",produces="application/json")
	public List<String> getAllDepartmentTypes()
	{		
		try
		{
			return dbService.getAllDepartmentTypes();
		}		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}		
	}
	
	@RequestMapping(value="/getReferenceNoByBranch",produces="application/json")
	public String getReferenceNoByBranch(@RequestParam("branchCode") String branchCode)
	{		
		try
		{						
			return dbService.getReferenceNoByBranch(branchCode);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}		
	}
	
	@RequestMapping(value="/getUserInfoByUserId",produces="application/json")
	public Map<String, Object> getUserInfoByUserId(@RequestParam("userId") String userId)
	{
		try
		{
			return dbService.getUserInfoByUserId(userId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
	}
	
	@RequestMapping(value="/getBranchDeptWorklistByUserId",produces="application/json")
	public Map<String, Object> getBranchDeptWorklistByUserId(@RequestParam("userId") String userId)
	{
		try
		{
			return dbService.getBranchDeptWorklistByUserId(userId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
	}
	
	@RequestMapping(value="/getAllCPUProcClerks",produces="application/json")
	public List<Map<String, String>> getAllCPUProcClerks()
	{		
		try
		{
			return dbService.getAllCPUProcClerks();
		}		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}		
	}
	
	@RequestMapping(value="/getAllCPUProcOfficers",produces="application/json")
	public List<Map<String, String>> getAllCPUProcOfficers()
	{		
		try
		{
			return dbService.getAllCPUProcOfficers();
		}		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}		
	}
	
	@RequestMapping(value="/getAllDivisionalClerks",produces="application/json")
	public List<Map<String, String>> getAllDivisionalClerks()
	{		
		try
		{
			return dbService.getAllDivisionalClerks();
		}		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}		
	}
	
	@RequestMapping(value="/getAllGMs",produces="application/json")
	public List<Map<String, String>> getAllGMs()
	{		
		try
		{
			return dbService.getAllGMs();
		}		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}		
	}
	
	@RequestMapping(value="/getPurchaseCombinationByUserId",produces="application/json")
	public Map<String, Object> getPurchaseCombinationByUserId(@RequestParam("userId") String userId)
	{
		try
		{
			return dbService.getPurchaseCombinationByUserId(userId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
	}
	
	@RequestMapping(value="/getPurchaseCategoriesByType",produces="application/json")
	public Map<String, Object> getPurchaseCategoriesByType(@RequestParam("purchaseType") String purchaseType)
	{
		try
		{
			return dbService.getPurchaseCategoriesByType(purchaseType);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
	}
	
	@RequestMapping(value="/getGoodsCategoriesByPCategory",produces="application/json")
	public Map<String, Object> getGoodsCategoriesByPCategory(@RequestParam("purchaseCategory") String purchaseCategory)
	{		
		try
		{
			return dbService.getGoodsCategoriesByPCategory(purchaseCategory);
		}		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}		
	}
	
	@RequestMapping(value="/getBranchApprovalPath",produces="application/json")
	public Map<String, Object> getBranchApprovalPath(@RequestParam("branchCode") String branchCode, @RequestParam("departmentType") String departmentType)
	{
		try
		{
			return dbService.getBranchApprovalPath(branchCode, departmentType);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
	}
	
	@RequestMapping(value="/getCPUHODEmails",produces="application/json")
	public List<String> getCPUHODEmails()
	{		
		try
		{
			return dbService.getCPUHODEmails();
		}		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}		
	}
	
}

