package com.xwtech.mss.pub.db;

import org.springframework.transaction.support.TransactionTemplate;

import com.xwtech.mss.pub.dao.business.ClientInfoDAO;
import com.xwtech.mss.pub.dao.business.GoodsInfoDAO;
import com.xwtech.mss.pub.dao.business.GoodsRecordDAO;
import com.xwtech.mss.pub.dao.business.GoodsTypeDAO;
import com.xwtech.mss.pub.dao.business.MpmsAuthorizationDAO;
import com.xwtech.mss.pub.dao.system.FrameFieldCheckDAO;
import com.xwtech.mss.pub.dao.system.FrameLoginLogDAO;
import com.xwtech.mss.pub.dao.system.FrameOrgCityRelationDAO;
import com.xwtech.mss.pub.dao.system.FrameResultDAO;
import com.xwtech.mss.pub.dao.system.FrameUrlDAO;
import com.xwtech.mss.pub.dao.system.MenuDAO;
import com.xwtech.mss.pub.dao.system.OperationLogDAO;
import com.xwtech.mss.pub.dao.system.OrgnizationDAO;
import com.xwtech.mss.pub.dao.system.RoleDAO;
import com.xwtech.mss.pub.dao.system.UserInfoDAO;
import com.xwtech.mss.pub.dao.system.UserPropertyDAO;

public class DbCollection {
	private TransactionTemplate transTemplate;

	private FrameFieldCheckDAO frameFieldCheckDAO;

	private FrameLoginLogDAO frameLoginLogDAO;

	private FrameResultDAO frameResultDAO;

	private FrameUrlDAO frameUrlDAO;

	private MenuDAO menuDAO;

	private OperationLogDAO operationLogDAO;

	private RoleDAO roleDAO;

	private UserInfoDAO userInfoDAO;

	private UserPropertyDAO userPropertyDAO;

	private OrgnizationDAO orgnizationDAO;

	private FrameOrgCityRelationDAO frameOrgCityRelationDAO;

	private GoodsTypeDAO goodsTypeDAO;

	private GoodsInfoDAO goodsInfoDAO;

	private GoodsRecordDAO goodsRecordDAO;

	private ClientInfoDAO clientInfoDAO;
	
	private MpmsAuthorizationDAO mpmsAuthorizationDAO;
	
	
	public DbCollection() {
	}

	public void setTransTemplate(TransactionTemplate transTemplate) {
		this.transTemplate = transTemplate;
	}

	public TransactionTemplate getTransTemplate() {
		return transTemplate;
	}

	public FrameFieldCheckDAO getFrameFieldCheckDAO() {
		return frameFieldCheckDAO;
	}

	public void setFrameFieldCheckDAO(FrameFieldCheckDAO frameFieldCheckDAO) {
		this.frameFieldCheckDAO = frameFieldCheckDAO;
	}

	public FrameLoginLogDAO getFrameLoginLogDAO() {
		return frameLoginLogDAO;
	}

	public void setFrameLoginLogDAO(FrameLoginLogDAO frameLoginLogDAO) {
		this.frameLoginLogDAO = frameLoginLogDAO;
	}

	public FrameResultDAO getFrameResultDAO() {
		return frameResultDAO;
	}

	public void setFrameResultDAO(FrameResultDAO frameResultDAO) {
		this.frameResultDAO = frameResultDAO;
	}

	public FrameUrlDAO getFrameUrlDAO() {
		return frameUrlDAO;
	}

	public void setFrameUrlDAO(FrameUrlDAO frameUrlDAO) {
		this.frameUrlDAO = frameUrlDAO;
	}

	public MenuDAO getMenuDAO() {
		return menuDAO;
	}

	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	public OperationLogDAO getOperationLogDAO() {
		return operationLogDAO;
	}

	public void setOperationLogDAO(OperationLogDAO operationLogDAO) {
		this.operationLogDAO = operationLogDAO;
	}

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public UserInfoDAO getUserInfoDAO() {
		return userInfoDAO;
	}

	public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
	}

	public UserPropertyDAO getUserPropertyDAO() {
		return userPropertyDAO;
	}

	public void setUserPropertyDAO(UserPropertyDAO userPropertyDAO) {
		this.userPropertyDAO = userPropertyDAO;
	}

	public OrgnizationDAO getOrgnizationDAO() {
		return orgnizationDAO;
	}

	public void setOrgnizationDAO(OrgnizationDAO orgnizationDAO) {
		this.orgnizationDAO = orgnizationDAO;
	}

	public FrameOrgCityRelationDAO getFrameOrgCityRelationDAO()
	{
		return frameOrgCityRelationDAO;
	}

	public void setFrameOrgCityRelationDAO(FrameOrgCityRelationDAO frameOrgCityRelationDAO)
	{
		this.frameOrgCityRelationDAO = frameOrgCityRelationDAO;
	}

	public GoodsTypeDAO getGoodsTypeDAO() {
		return goodsTypeDAO;
	}

	public void setGoodsTypeDAO(GoodsTypeDAO goodsTypeDAO) {
		this.goodsTypeDAO = goodsTypeDAO;
	}

	public GoodsInfoDAO getGoodsInfoDAO() {
		return goodsInfoDAO;
	}

	public void setGoodsInfoDAO(GoodsInfoDAO goodsInfoDAO) {
		this.goodsInfoDAO = goodsInfoDAO;
	}

	public GoodsRecordDAO getGoodsRecordDAO() {
		return goodsRecordDAO;
	}

	public void setGoodsRecordDAO(GoodsRecordDAO goodsRecordDAO) {
		this.goodsRecordDAO = goodsRecordDAO;
	}

	public ClientInfoDAO getClientInfoDAO() {
		return clientInfoDAO;
	}

	public void setClientInfoDAO(ClientInfoDAO clientInfoDAO) {
		this.clientInfoDAO = clientInfoDAO;
	}

	public MpmsAuthorizationDAO getMpmsAuthorizationDAO() {
		return mpmsAuthorizationDAO;
	}

	public void setMpmsAuthorizationDAO(MpmsAuthorizationDAO mpmsAuthorizationDAO) {
		this.mpmsAuthorizationDAO = mpmsAuthorizationDAO;
	}

}
