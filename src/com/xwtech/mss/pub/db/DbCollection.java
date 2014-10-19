package com.xwtech.mss.pub.db;

import org.springframework.transaction.support.TransactionTemplate;

import com.xwtech.mss.pub.dao.business.CityDAO;
import com.xwtech.mss.pub.dao.business.ClientDAO;
import com.xwtech.mss.pub.dao.business.ClientGatewayDAO;
import com.xwtech.mss.pub.dao.business.ClientGroupDAO;
import com.xwtech.mss.pub.dao.business.ClientGroupMappingDAO;
import com.xwtech.mss.pub.dao.business.ClientInfoDAO;
import com.xwtech.mss.pub.dao.business.ClientServerMappingDAO;
import com.xwtech.mss.pub.dao.business.ClientStatusDAO;
import com.xwtech.mss.pub.dao.business.ClientWebsiteHistoryDAO;
import com.xwtech.mss.pub.dao.business.CountryDAO;
import com.xwtech.mss.pub.dao.business.GatewayDAO;
import com.xwtech.mss.pub.dao.business.GoodsInfoDAO;
import com.xwtech.mss.pub.dao.business.GoodsRecordDAO;
import com.xwtech.mss.pub.dao.business.GoodsTypeDAO;
import com.xwtech.mss.pub.dao.business.MpmsAuthorizationDAO;
import com.xwtech.mss.pub.dao.business.ProvinceDAO;
import com.xwtech.mss.pub.dao.business.RegionDAO;
import com.xwtech.mss.pub.dao.business.ServerGroupDAO;
import com.xwtech.mss.pub.dao.business.ServerGroupMappingDAO;
import com.xwtech.mss.pub.dao.business.ServerStatusDAO;
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
	
	private CityDAO cityDAO;
	
	private ClientDAO clientDAO;
	
	private ClientGatewayDAO clientGatewayDAO;
	
	private ClientGroupDAO clientGroupDAO;
	
	private ClientGroupMappingDAO clientGroupMappingDAO;
	
	private ClientServerMappingDAO clientServerMappingDAO;
	
	private ClientStatusDAO clientStatusDAO;
	
	private ClientWebsiteHistoryDAO clientWebsiteHistoryDAO;
	
	private CountryDAO countryDAO;
	
	private GatewayDAO gatewayDAO;
	
	private ProvinceDAO provinceDAO;
	
	private RegionDAO regionDAO;
	
	private ServerGroupDAO serverGroupDAO;
	
	private ServerGroupMappingDAO serverGroupMappingDAO;
	
	private ServerStatusDAO serverStatusDAO;
	
	
	
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

	public CityDAO getCityDAO() {
		return cityDAO;
	}

	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	public ClientDAO getClientDAO() {
		return clientDAO;
	}

	public void setClientDAO(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	public ClientGatewayDAO getClientGatewayDAO() {
		return clientGatewayDAO;
	}

	public void setClientGatewayDAO(ClientGatewayDAO clientGatewayDAO) {
		this.clientGatewayDAO = clientGatewayDAO;
	}

	public ClientGroupDAO getClientGroupDAO() {
		return clientGroupDAO;
	}

	public void setClientGroupDAO(ClientGroupDAO clientGroupDAO) {
		this.clientGroupDAO = clientGroupDAO;
	}

	public ClientGroupMappingDAO getClientGroupMappingDAO() {
		return clientGroupMappingDAO;
	}

	public void setClientGroupMappingDAO(ClientGroupMappingDAO clientGroupMappingDAO) {
		this.clientGroupMappingDAO = clientGroupMappingDAO;
	}

	public ClientServerMappingDAO getClientServerMappingDAO() {
		return clientServerMappingDAO;
	}

	public void setClientServerMappingDAO(
			ClientServerMappingDAO clientServerMappingDAO) {
		this.clientServerMappingDAO = clientServerMappingDAO;
	}

	public ClientStatusDAO getClientStatusDAO() {
		return clientStatusDAO;
	}

	public void setClientStatusDAO(ClientStatusDAO clientStatusDAO) {
		this.clientStatusDAO = clientStatusDAO;
	}

	public ClientWebsiteHistoryDAO getClientWebsiteHistoryDAO() {
		return clientWebsiteHistoryDAO;
	}

	public void setClientWebsiteHistoryDAO(
			ClientWebsiteHistoryDAO clientWebsiteHistoryDAO) {
		this.clientWebsiteHistoryDAO = clientWebsiteHistoryDAO;
	}

	public CountryDAO getCountryDAO() {
		return countryDAO;
	}

	public void setCountryDAO(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}

	public GatewayDAO getGatewayDAO() {
		return gatewayDAO;
	}

	public void setGatewayDAO(GatewayDAO gatewayDAO) {
		this.gatewayDAO = gatewayDAO;
	}

	public ProvinceDAO getProvinceDAO() {
		return provinceDAO;
	}

	public void setProvinceDAO(ProvinceDAO provinceDAO) {
		this.provinceDAO = provinceDAO;
	}

	public RegionDAO getRegionDAO() {
		return regionDAO;
	}

	public void setRegionDAO(RegionDAO regionDAO) {
		this.regionDAO = regionDAO;
	}

	public ServerGroupDAO getServerGroupDAO() {
		return serverGroupDAO;
	}

	public void setServerGroupDAO(ServerGroupDAO serverGroupDAO) {
		this.serverGroupDAO = serverGroupDAO;
	}

	public ServerGroupMappingDAO getServerGroupMappingDAO() {
		return serverGroupMappingDAO;
	}

	public void setServerGroupMappingDAO(ServerGroupMappingDAO serverGroupMappingDAO) {
		this.serverGroupMappingDAO = serverGroupMappingDAO;
	}

	public ServerStatusDAO getServerStatusDAO() {
		return serverStatusDAO;
	}

	public void setServerStatusDAO(ServerStatusDAO serverStatusDAO) {
		this.serverStatusDAO = serverStatusDAO;
	}

}
