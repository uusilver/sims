package com.xwtech.mss.bo.system.operator;

import java.util.HashMap;
import java.util.List;

import com.xwtech.mss.formBean.OperLogSearForm;
import com.xwtech.mss.pub.dao.system.OperationLogDAO;
import com.xwtech.mss.pub.po.OperationLog;

public class OperLogBO
{

	private OperationLogDAO operationLogDAO;
	public OperLogBO()
	{
		// TODO Auto-generated constructor stub
	}

	public OperationLogDAO getOperationLogDAO() 
	{
		return operationLogDAO;
	}



	public void setOperationLogDAO(OperationLogDAO operationLogDAO)
	{
		this.operationLogDAO = operationLogDAO;
	}

	
    public void save(OperationLog transientInstance) {
    	this.operationLogDAO.save(transientInstance);
    }
	
/**
 * 所有的日志查询
 * 
 */
	
	public List operLogListAll()
	{
		List operLogList=operationLogDAO.operLogListAll();
		
		return operLogList;
	}
	
	
	
	
	
	
/**
 * 条件日志查询
*/
	public HashMap operLogSear(OperLogSearForm operLogSearForm)
	{
		HashMap operLogList=operationLogDAO.operLogSear(operLogSearForm);
		
		return operLogList;
	}



	
}
