package com.xwtech.framework.pub.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.xwtech.framework.pub.po.FrameFieldCheck;
import com.xwtech.framework.pub.web.SystemConstants;

public class FrameFeildCheckDAO extends HibernateDaoSupport
{

  protected static final Logger logger = Logger.getLogger(FrameFeildCheckDAO.class);

  public void saveFrameFeildCheck(FrameFieldCheck frameFieldCheck)
  {
    getHibernateTemplate().saveOrUpdate(frameFieldCheck);
  }

  public FrameFieldCheck getFrameFeildCheck(Long id)
  {
    FrameFieldCheck frameFeildCheck = (FrameFieldCheck)getHibernateTemplate().get(FrameFieldCheck.class, id);
    if(frameFeildCheck == null)
    {
      logger.warn("uh oh, FrameFeildCheck " + id + "' not found...");
      throw new ObjectRetrievalFailureException(FrameFieldCheck.class, id);
    }
  return frameFeildCheck;
  }

  public List getFrameFeildChecks()
  {
    return getHibernateTemplate().loadAll(FrameFieldCheck.class);
  }

  public void removeFrameFeildCheck(Long id)
  {
    getHibernateTemplate().delete(getFrameFeildCheck(id));
  }
  /**
   * 根据checkValue获取check值的描述
   * @param tableName String
   * @param fieldName String
   * @param checkValue String
   * @return String
   */
  public String queryCheckDescByCheckValue(String tableName,String fieldName,String checkValue)
  {
    String hSql = "select a.checkDesc from FrameFieldCheck a where a.tableName=? and a.fieldName=? and a.checkValue=? and a.checkState='"+SystemConstants.STATUS_VALID+"'";
    Object[] objArr = new Object[3];
    objArr[0] = tableName;
    objArr[1] = fieldName;
    objArr[2] = checkValue;
    List list = getHibernateTemplate().find(hSql,objArr);
    return list.size()==0?"":((String)list.get(0));
  }
  
  /****
   * 根据checkValue获取check值的描述
   * @param tableName
   * @param fieldName
   * @return list
   */
  public List queryFrameFieldCheck( String tableName, String fieldName )
  {
	  List list = new ArrayList();
	  String hSql = "select a from FrameFieldCheck a where a.tableName=? and a.fieldName=? and a.checkState='"+SystemConstants.STATUS_VALID+"'";
	  Object[] parameterArray = new Object[2];
	  parameterArray[0] = tableName;
	  parameterArray[1] = fieldName;
	  
	  list = getHibernateTemplate().find( hSql , parameterArray );
	  return list.size()==0?null:list;
  }

  /********
   * 用于查询RING的价格区间(price_area)
   * @param tableName
   * @param fieldName
   * @return list
   */
  public Object[] queryRingPriceArea( String tableName, String fieldName )
  {
	  List list = new ArrayList();
	  
	  List recordList = this.queryFrameFieldCheck( tableName , fieldName );
	  if ( recordList != null && recordList.size() > 0 )
		  for ( int i = 0 ; i < recordList.size() ; i++ )
		  {
			  String checkValue = ((FrameFieldCheck)recordList.get(i)).getCheckValue();
			  String checkDesc = ((FrameFieldCheck)recordList.get(i)).getCheckDesc();
			  Object[] priceArea = checkDesc.split("\\-");
			  list.add( new Object[]{ checkValue , priceArea[0] , priceArea[1] } );
		  }
	  recordList = null;
	  
	  //test
	  for (int j = 0 ; j < list.size() ; j++ )
	  {
		  Object[] test = (Object[])list.get(j);
		  logger.info( test[0] + " // " + test[1] + " // " + test[2] );
	  }
	  
	  return list.size()==0?null:list.toArray();
  }
  
}
