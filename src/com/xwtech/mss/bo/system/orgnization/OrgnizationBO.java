package com.xwtech.mss.bo.system.orgnization;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.xwtech.mss.formBean.OrgnizationSearchForm;
import com.xwtech.mss.pub.constants.SpmsConstants;
import com.xwtech.mss.pub.dao.system.FrameOrgCityRelationDAO;
import com.xwtech.mss.pub.dao.system.OperationLogDAO;
import com.xwtech.mss.pub.dao.system.OrgnizationDAO;
import com.xwtech.mss.pub.po.FrameOrgCityRelation;
import com.xwtech.mss.pub.po.OperationLog;
import com.xwtech.mss.pub.po.Orgnization;
import com.xwtech.mss.pub.web.SysOperLog;

public class OrgnizationBO
{

	private OrgnizationDAO orgnizationDAO;

	private FrameOrgCityRelationDAO FrameOrgCityRelationDAO;

	private TransactionTemplate transTemplate;

	private SysOperLog sysOperLog;

	public void setOrgnizationDAO(OrgnizationDAO orgnizationDAO)
	{
		this.orgnizationDAO = orgnizationDAO;
	}

	public void setFrameOrgCityRelationDAO(FrameOrgCityRelationDAO FrameOrgCityRelationDAO)
	{
		this.FrameOrgCityRelationDAO = FrameOrgCityRelationDAO;
	}

	public Orgnization findById(java.lang.Long id)
	{
		return orgnizationDAO.findById(id);
	}

	public void setTransTemplate(TransactionTemplate transTemplate)
	{
		this.transTemplate = transTemplate;
	}

	public void setSysOperLog(SysOperLog sysOperLog)
	{
		this.sysOperLog = sysOperLog;
	}

	/**
	 * 根据OrgnizationSearchForm 查询组织信息后返回查询结果
	 * @param OrgnizationSearchForm
	 * @param currentPage
	 * @param countPerPage
	 * @return
	 */
	public Map getOrgnizationListByForm(OrgnizationSearchForm OrgnizationSearchForm, String currentPage, String countPerPage) throws Exception
	{
		return orgnizationDAO.getOrgnizationListByForm(OrgnizationSearchForm, currentPage, countPerPage);
	}

	/**
	 * 寻找具有该部门名称的记录，存在返回true,不存在返回false
	 * @param orgName
	 * @param notExistOrgId 需要过滤的部门id(用于更新的时候校验部门名称唯一性)
	 * @return
	 */
	public boolean checkOrgNameExist(String orgName, String notExistOrgId) throws Exception
	{
		OrgnizationSearchForm OrgnizationSearchForm = new OrgnizationSearchForm();
		OrgnizationSearchForm.setOrgNameForSelect(orgName);
		OrgnizationSearchForm.setNotExistOrgId(notExistOrgId);
		//
		List listResult = (List) orgnizationDAO.getOrgnizationListByForm(OrgnizationSearchForm, null, null).get("orgList");

		if (listResult.size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 新增部门
	 * @param orgnizationSearchForm
	 * @throws Exception
	 */
	public void orgnizationAdd(final OrgnizationSearchForm orgnizationSearchForm, final HttpServletRequest request) throws Exception
	{
		transTemplate.execute(new TransactionCallbackWithoutResult()
		{
			protected void doInTransactionWithoutResult(TransactionStatus status)
			{

				//************创建保存部门信息******************
				//新增部门
				Orgnization orgnization = new Orgnization();
				//新增部门的上级部门
				Orgnization upOrgnization;

				if (!orgnizationSearchForm.getUpOrgId().equals(""))
				{
					//得到上级部门
					upOrgnization = orgnizationDAO.findById(Long.valueOf(orgnizationSearchForm.getUpOrgId()));
					//关联上级部门
					orgnization.setOrgnization(upOrgnization);
					//组织等级 上级等级+1
					orgnization.setOrgLevel(new Long(upOrgnization.getOrgLevel().intValue() + 1));
				}
				else
				{
					orgnization.setOrgLevel(new Long(0));
				}
				//记录部门名称
				orgnization.setOrgName(orgnizationSearchForm.getOrgName());

				//记录备注
				orgnization.setDescription(orgnizationSearchForm.getDescription());
				//状态 新增时有效
				orgnization.setState(SpmsConstants.STATE_A);

				//保存组织机构信息
				orgnizationDAO.save(orgnization);

				//记录新增部门日志
				sysOperLog.saveOperLog(request, SpmsConstants.DO_TYPE_ADD, SpmsConstants.TABLE_NAME_FRAME_ORGNIZATION, orgnization.getOrgId(),
						SpmsConstants.OBJ_TYPE_ORG, SpmsConstants.DESC_ORG_ADD);

				//***********创建保存部门地市之间关系信息************
				if (!orgnizationSearchForm.getNj().equals(""))
				{ //南京
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getNj());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getWx().equals(""))
				{ //无锡
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getWx());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getXz().equals(""))
				{ //徐州
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getXz());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getCz().equals(""))
				{ //常州
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getCz());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getSz().equals(""))
				{ //苏州
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getSz());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getNt().equals(""))
				{ //南通
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getNt());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getLyg().equals(""))
				{ //连云港
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getLyg());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getHa().equals(""))
				{ //淮安
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getHa());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getYc().equals(""))
				{ //盐城
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getYc());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getYz().equals(""))
				{ //扬州
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getYz());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getZj().equals(""))
				{ //镇江
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getZj());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getTz().equals(""))
				{ //泰州
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getTz());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getSq().equals(""))
				{ //宿迁
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getSq());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				FrameOrgCityRelationDAO.flush();

			}
		});

	}

	/**
	 * 更新部门
	 * @param orgnizationSearchForm
	 * @throws Exception
	 */
	public boolean orgnizationUpdate(final OrgnizationSearchForm orgnizationSearchForm, final HttpServletRequest request) throws Exception
	{
		//得到需要更新的对象
		final Orgnization orgnization = findById(Long.valueOf(orgnizationSearchForm.getOrgId()));

		if (!orgnizationSearchForm.getUpOrgId().equals(""))
		{
			//得到上级部门
			Orgnization upOrgnization = orgnizationDAO.findById(Long.valueOf(orgnizationSearchForm.getUpOrgId()));
			
			if(checkUpOrgIsSelf(upOrgnization,orgnization.getOrgId()))
			{
				//如果更新的上级对象包含自己 返回错误
				return false;
				
			}
		}
		transTemplate.execute(new TransactionCallbackWithoutResult()
		{
			protected void doInTransactionWithoutResult(TransactionStatus status)
			{
				//上级部门
				if (!orgnizationSearchForm.getUpOrgId().equals(""))
				{
					//得到上级部门
					Orgnization upOrgnization = orgnizationDAO.findById(Long.valueOf(orgnizationSearchForm.getUpOrgId()));
					//关联上级部门
					orgnization.setOrgnization(upOrgnization);
					//组织等级 上级等级+1
					orgnization.setOrgLevel(new Long(upOrgnization.getOrgLevel().intValue() + 1));
				}
				else
				{
					orgnization.setOrgLevel(new Long(0));
				}
				//记录部门名称
				orgnization.setOrgName(orgnizationSearchForm.getOrgName());

				//记录备注
				orgnization.setDescription(orgnizationSearchForm.getDescription());
				//状态 
				orgnization.setState(SpmsConstants.STATE_A);

				//更新组织机构信息
				orgnizationDAO.save(orgnization);

//				记录更新部门日志
				sysOperLog.saveOperLog(request, SpmsConstants.DO_TYPE_UPDATE, SpmsConstants.TABLE_NAME_FRAME_ORGNIZATION, orgnization.getOrgId(),
						SpmsConstants.OBJ_TYPE_ORG, SpmsConstants.DESC_ORG_UPDATE);

				
/*				if (orgnizationSearchForm.getState().equals(SpmsConstants.STATE_U))
				{
					//记录删除部门日志   09-02-16删除操作在列表页面实现 修改部门时不会再进入次分支执行删除
					sysOperLog.saveOperLog(request, SpmsConstants.DO_TYPE_DEL, SpmsConstants.TABLE_NAME_FRAME_ORGNIZATION, orgnization.getOrgId(),
							SpmsConstants.OBJ_TYPE_ORG, SpmsConstants.DESC_ORG_DEL);
				}
				else
				{
					//记录更新部门日志
					sysOperLog.saveOperLog(request, SpmsConstants.DO_TYPE_UPDATE, SpmsConstants.TABLE_NAME_FRAME_ORGNIZATION, orgnization.getOrgId(),
							SpmsConstants.OBJ_TYPE_ORG, SpmsConstants.DESC_ORG_UPDATE);
				}*/

				//更新下级组织层级
				updateLowOrgnizationLevel(orgnization);

				OperationLog operationLog = new OperationLog();
				//	operationLogDAO

				//********************删除并重新建立部门地市信息*******************
				//删除该部门所有地市信息
				FrameOrgCityRelationDAO.deleteAll(orgnization.getFrameOrgCityRelations());

				//创建保存部门地市之间关系信息
				if (!orgnizationSearchForm.getNj().equals(""))
				{ //南京
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getNj());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getWx().equals(""))
				{ //无锡
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getWx());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getXz().equals(""))
				{ //徐州
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getXz());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getCz().equals(""))
				{ //常州
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getCz());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getSz().equals(""))
				{ //苏州
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getSz());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getNt().equals(""))
				{ //南通
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getNt());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getLyg().equals(""))
				{ //连云港
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getLyg());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getHa().equals(""))
				{ //淮安
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getHa());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getYc().equals(""))
				{ //盐城
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getYc());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getYz().equals(""))
				{ //扬州
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getYz());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getZj().equals(""))
				{ //镇江
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getZj());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getTz().equals(""))
				{ //泰州
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getTz());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				if (!orgnizationSearchForm.getSq().equals(""))
				{ //宿迁
					FrameOrgCityRelation FrameOrgCityRelation = new FrameOrgCityRelation(orgnization, orgnizationSearchForm.getSq());
					FrameOrgCityRelationDAO.save(FrameOrgCityRelation);
				}
				FrameOrgCityRelationDAO.flush();
			}
		});
		return true;
	}

	/**
	 * 更新组织等级时，更新该组织下级组织的等级
	 * （防止中间层级组织变更上级时，其下级组织层级错误）
	 * @param orgnization
	 */
	public void updateLowOrgnizationLevel(Orgnization orgnization)
	{
		java.util.Iterator it = orgnization.getOrgnizations().iterator();

		while (it.hasNext())
		{
			Orgnization lowOrgnization = (Orgnization) it.next();
			//更新下级部门等级为上级部门等级加1
			lowOrgnization.setOrgLevel(new Long(orgnization.getOrgLevel().intValue() + 1));
			orgnizationDAO.save(lowOrgnization);

			if (lowOrgnization.getOrgnizations().size() > 0)
			{
				//如果下级还有下级 迭代更新
				updateLowOrgnizationLevel(lowOrgnization);
			}
		}

	}
	
/**
 * 检查自己的上级是否有自己，如果存在返回true不存在返回false（如果自己的上级存在自己，更新上级id时会死循环）
 * @param orgnization 参照对象，迭代的过程中 第一次orgnization是要更新orgId对象的上上级部门，第二次是要更新orgId对象的上上上级部门...（直到上级对象全部遍历）
 * 由于自己不能是自己的上级，已经在页面上校验过了，故此处不考虑，只需从上上级部门开始检查即可
 * @param orgId 需要校验的id
 * @return
 */
	public boolean checkUpOrgIsSelf(Orgnization orgnization,Long orgId)
	{
		//判断是否有上级
		if (null !=orgnization.getOrgnization())
		{
			//得到上级部门
			Orgnization upOrgnization = orgnization.getOrgnization();
			if(upOrgnization.getOrgId().intValue()==orgId.intValue())
			{
				//上级对象是自己时 返回
				return true;
			}
			
			if(null!=upOrgnization.getOrgnization())
			{ 
				//上级还有上级 迭代判断
				return checkUpOrgIsSelf(upOrgnization,orgId);
			}

		}
		else
		{
			//没有上级
			return false;
		}
		return false;
	}

	/**
	 * add by jzy 09-02-16
	 * 根据orgId列表更新部门信息为无效
	 * @param orgId
	 */
	public void delOrgByIdArray(final String[] orgId, final HttpServletRequest request )
	{
		transTemplate.execute(new TransactionCallbackWithoutResult()
		{
			protected void doInTransactionWithoutResult(TransactionStatus status)
			{
				for (int i = 0; i < orgId.length; i++)
				{
					Orgnization delOrgnization = orgnizationDAO.findById(Long.valueOf(orgId[i]));
					delOrgnization.setState(SpmsConstants.STATE_U);
					//清空上级 让上级可以删除
					delOrgnization.setOrgnization(null);
					orgnizationDAO.save(delOrgnization);
					
					//记录删除日志
					sysOperLog.saveOperLog(request, SpmsConstants.DO_TYPE_DEL, SpmsConstants.TABLE_NAME_FRAME_ORGNIZATION, delOrgnization.getOrgId(),
							SpmsConstants.OBJ_TYPE_ORG, SpmsConstants.DESC_ORG_DEL);
				}
			}
		});

	}

}
