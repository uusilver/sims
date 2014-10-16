package com.xwtech.mss.formBean;

/**
 * 组织机构操作所用form 
 * @author jzy
 *
 */
public class OrgnizationSearchForm implements IGetFormByReq
{
	//组织模糊查询名称
	private String fuzzyOrgnizationName = "";

	//上级组织id
	private String upOrgId = "";
	
	//备注
	private String description = "";
	
	//部门机构名称-用于查询组织
	private String orgNameForSelect = "";
	
	//部门机构名称-用于新增组织
	private String orgName = "";
	
	//是否分页
	private String isPaging = "N";

	//查询条件-不包含该部门id
	private String notExistOrgId = "";
	
	//部门id-用于更新部门
	private String orgId = "";
	
	//状态-用于更新部门状态
	private String state = "";
	
	//南京
	private String nj="";
	//无锡
	private String wx="";
	//徐州
	private String xz="";
	//常州
	private String cz="";
	//苏州
	private String sz="";
	//南通
	private String nt="";
	//连云港
	private String lyg="";
	//淮安
	private String ha="";
	//盐城
	private String yc="";
	//扬州
	private String yz="";
	//镇江
	private String zj="";
	//泰州
	private String tz="";
	//宿迁
	private String sq="";

	public String getIsPaging()
	{
		return isPaging;
	}

	public void setIsPaging(String isPaging)
	{
		this.isPaging = isPaging;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName.trim();
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description.trim();
	}

	public String getUpOrgId()
	{
		return upOrgId;
	}

	public void setUpOrgId(String upOrgId)
	{
		this.upOrgId = upOrgId;
	}

	public String getFuzzyOrgnizationName()
	{
		return fuzzyOrgnizationName;
	}
	           
	public void setFuzzyOrgnizationName(String fuzzyOrgnizationName)
	{
		this.fuzzyOrgnizationName = fuzzyOrgnizationName.trim();
	}

	public String getCz()
	{
		return cz;
	}

	public void setCz(String cz)
	{
		this.cz = cz;
	}

	public String getHa()
	{
		return ha;
	}

	public void setHa(String ha)
	{
		this.ha = ha;
	}

	public String getLyg()
	{
		return lyg;
	}

	public void setLyg(String lyg)
	{
		this.lyg = lyg;
	}

	public String getNj()
	{
		return nj;
	}

	public void setNj(String nj)
	{
		this.nj = nj;
	}

	public String getNt()
	{
		return nt;
	}

	public void setNt(String nt)
	{
		this.nt = nt;
	}

	public String getSq()
	{
		return sq;
	}

	public void setSq(String sq)
	{
		this.sq = sq;
	}

	public String getSz()
	{
		return sz;
	}

	public void setSz(String sz)
	{
		this.sz = sz;
	}

	public String getTz()
	{
		return tz;
	}

	public void setTz(String tz)
	{
		this.tz = tz;
	}

	public String getWx()
	{
		return wx;
	}

	public void setWx(String wx)
	{
		this.wx = wx;
	}

	public String getXz()
	{
		return xz;
	}

	public void setXz(String xz)
	{
		this.xz = xz;
	}

	public String getYc()
	{
		return yc;
	}

	public void setYc(String yc)
	{
		this.yc = yc;
	}

	public String getYz()
	{
		return yz;
	}

	public void setYz(String yz)
	{
		this.yz = yz;
	}

	public String getZj()
	{
		return zj;
	}

	public void setZj(String zj)
	{
		this.zj = zj;
	}

	public String getOrgNameForSelect()
	{
		return orgNameForSelect;
	}

	public void setOrgNameForSelect(String orgNameForSelect)
	{
		this.orgNameForSelect = orgNameForSelect;
	}

	public String getNotExistOrgId()
	{
		return notExistOrgId;
	}

	public void setNotExistOrgId(String notExistOrgId)
	{
		this.notExistOrgId = notExistOrgId;
	}

	public String getOrgId()
	{
		return orgId;
	}

	public void setOrgId(String orgId)
	{
		this.orgId = orgId;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

}
