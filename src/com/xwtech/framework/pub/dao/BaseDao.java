package com.xwtech.framework.pub.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.type.NullableType;
import java.sql.*;

import com.xwtech.framework.pub.utils.HibernateObjUtils;
import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.framework.pub.web.RequestNameConstants;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.hibernate.type.Type;

public class BaseDao extends HibernateDaoSupport
{
  protected static final Logger log = Logger.getLogger(BaseDao.class);

  public BaseDao()
  {
  }

  public List queryListForDivpage(String hSql,int firstResult)
  {
    return queryListForDivpage(hSql,null,firstResult);
  }
  public List queryListForDivpage(String hSql,Object value,int firstResult)
  {
    return queryListForDivpage(hSql,new Object[]{value},firstResult);
  }
  /**
   * 执行一个HSQL进行分页查询 (最大记录数从系统配置文件中获取)
   * @param hSql String         需要执行的HSQL
   * @param firstResult int     起始的记录数
   * @param maxResult int       需要返回的记录数
   * @return List               结果List
   */
  public List queryListForDivpage(final String hSql,final int firstResult,final int maxResult)
  {
    return queryListForDivpage(hSql,null,firstResult,maxResult);
  }
  public List queryListForDivpage(String hSql,Object value,int firstResult,int maxResult)
  {
    return queryListForDivpage(hSql,new Object[]{value},firstResult,maxResult);
  }
  /**
   * 执行一个HSQL进行分页查询
   * @param hSql String         需要执行的HSQL
   * @param firstResult int     起始的记录数
   * @param maxResult int       每页最大记录数
   * @return List               结果List
   */
  public List queryListForDivpage(final String hSql,final Object[] values,final int firstResult,final int maxResult)
  {
    return (List)getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
        Query query = session.createQuery(hSql);
        if (values!=null)
        {
          for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
          }
        }
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.list();
      }
    });
  }

  /**
   * 执行一个HSQL进行分页查询，并返回总页数和总记录数
   * @param listHSql String     需要执行的查找结果集的SQL
   * @param countHSql String    需要执行的查找记录数的SQL
   * @param values List		sql的过滤条件,由Object对象组成以及匹配位置组成，每个对象必须封装成所要查找字段的类型，再封装成Object对象（切记，不然可能查找会失败）
   * @param currentPage String  当前页，默认为1
   * @param countPerPage int    每页最大记录数，若此值为空，则默认取10页；若此值为0，则取出所有的记录
   * @return HashMap            包含结果集、总页数、当前页数及记录总数的结果
   * 
   * @example:	listHSql:select tabA from TabA where tabA.tabB.tabB=?;
   * 			countHSql:select count(tabA) from TabA where tabA.tabB.tabB=?;
   * 			values:Hash ,第一个值为("0",(Object)(new Long(tabBId))),tabBId为String类型的值
   */
  public HashMap queryResultCount(final String listHSql,final String countHSql,final List values,final String currentPage,final String countPerPage)
  {
    return (HashMap)getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
    
    	int curPage = 1;
    	if(currentPage!=null && !currentPage.equals("")){
    		curPage = Integer.parseInt(currentPage);
    	}
    	int perPage = 10;
    	//当传递每页最大记录数时，按照指定记录数取出记录
    	if(countPerPage!=null && !countPerPage.equals("")){
    		perPage = Integer.parseInt(countPerPage);
    	}
    	List resultList = new ArrayList();//取的记录
        int totalCount = 0;//总记录数
        int totalPage = 0;//总页数
        
        int stratNum = 0;//开始取记录的位置
        int fetchNum = 0;//取出记录的条数

        HashMap resultMap = new HashMap();
        
        try{
        	//if()
	        //取得记录数
	        Query query = session.createQuery(countHSql);
	        if (values!=null) {
	          for (int i = 0; i < values.size(); i++) {
	            query.setParameter(i, (Object)values.get(i));
	          }
	        }
	        List counttList = query.list();
	        if(counttList!=null && counttList.size()>0){
	        	totalCount=(new Integer(String.valueOf(counttList.get(0)))).intValue();
	        }
	        //当传递值为0时，默认取出所有记录
	        if(countPerPage!=null && countPerPage.equals("0")){
	        	perPage = totalCount;
	        }
        	if(perPage == 0){
        		totalPage = 1;
        	}else{
		        if(totalCount % perPage == 0){
		        	//总记录为0，则设总页数为1
		        	totalPage = totalCount / perPage;
		        }else{
		        	totalPage = totalCount / perPage + 1;
		        }
        	}
	        if(totalPage==0){
	        	totalPage = 1;
	        }
	        if(curPage < totalPage){
	        	stratNum = perPage*(curPage-1);
	        	fetchNum = perPage;
			}
			if(curPage == totalPage){
				stratNum = perPage*(curPage-1);
	        	fetchNum = totalCount - stratNum;
			}
	        //当前页大于总页数时，默认为取最后一页
	        if(curPage > totalPage){
	        	stratNum = perPage*(totalPage-1);
	        	fetchNum = totalCount - stratNum;
	            resultMap.put(RequestNameConstants.CURRENT_PAGE, new Integer(totalPage));
			}else{
				resultMap.put(RequestNameConstants.CURRENT_PAGE, String.valueOf(curPage));
			}
	        //取得结果集
	    	query = session.createQuery(listHSql);
	        if (values!=null) {
	        	for (int i = 0; i < values.size(); i++) {
	        		query.setParameter(i, (Object)values.get(i));
	        	}
	        }
	        query.setFirstResult(stratNum);
	        query.setMaxResults(fetchNum);
	        resultList = query.list();
        }catch(Exception e){
        	log.error("查询结果集出错！");
        	e.printStackTrace();
        }  

        resultMap.put(RequestNameConstants.TOTAL_COUNT, new Integer(totalCount));
        resultMap.put(RequestNameConstants.TOTAL_PAGE, new Integer(totalPage));
        resultMap.put(RequestNameConstants.RESULT_LIST, resultList);
        resultMap.put("hql_str", listHSql);
        return resultMap;
      }
    });
  }

  /**
   * 执行一个原生sql进行分页查询，并返回总页数和总记录数
   * @param listHSql String     需要执行的查找结果集的SQL
   * @param countHSql String    需要执行的查找记录数的SQL
   * @param values HashMap		sql的过滤条件,由Object对象组成以及匹配位置组成，每个对象必须封装成所要查找字段的类型，再封装成Object对象（切记，不然可能查找会失败）
   * @param currentPage String  当前页，默认为1
   * @param countPerPage int    每页最大记录数，若此值为空，则取出所有的记录
   * @return HashMap            包含结果集、总页数、当前页数及记录总数的结果
   * 
   * @example:	listHSql:select tabA from TabA where tabA.tabB.tabB=?;
   * 			countHSql:select count(tabA) from TabA where tabA.tabB.tabB=?;
   * 			values:Hash ,第一个值为("0",(Object)(new Long(tabBId))),tabBId为String类型的值
   */
  public HashMap queryCommonSqlResultCount(final String listSql,final String countSql,final String currentPage,final String countPerPage)
  {
    return (HashMap)getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
    
    	int curPage = 1;
    	if(currentPage!=null && !currentPage.equals("")){
    		curPage = Integer.parseInt(currentPage);
    	}
    	int perPage = 10;
    	if(countPerPage!=null && !countPerPage.equals("")){
    		perPage = Integer.parseInt(countPerPage);
    	}
    	List resultList = new ArrayList();//取的记录
        int totalCount = 0;//总记录数
        int totalPage = 0;//总页数
        
        int stratNum = 0;//开始取记录的位置
        int fetchNum = 0;//取出记录的条数

        HashMap resultMap = new HashMap();
        
        try{
	        //取得记录数
	        List counttList = FrameworkApplication.baseJdbcDAO.queryForList(countSql);
	        System.out.println(countSql.trim());
	        if(counttList!=null && counttList.size()>0){
	        	totalCount=(new Integer(String.valueOf(((ListOrderedMap)(counttList.get(0))).getValue(0)))).intValue();
	        }
            //当查询列表为空的时候，当前页的页码应该是1
            else
            {
                curPage = 1;
            }
	        if(totalCount % perPage == 0){
	        	totalPage = totalCount / perPage;
	        }else{
	        	totalPage = totalCount / perPage + 1;
	        }
	        if(totalPage==0){
	        	totalPage = 1;
	        }
	        if(curPage < totalPage){
	        	stratNum = perPage*(curPage-1);
	        	fetchNum = perPage;
			}
			if(curPage == totalPage){
				stratNum = perPage*(curPage-1);
	        	fetchNum = totalCount - stratNum;
			}
	        //当前页大于总页数时，默认为取最后一页
	        if(curPage > totalPage){
	        	stratNum = perPage*(totalPage-1);
	        	fetchNum = totalCount - stratNum;
	            resultMap.put(RequestNameConstants.CURRENT_PAGE, new Integer(totalPage));
			}else{
				resultMap.put(RequestNameConstants.CURRENT_PAGE, String.valueOf(curPage));
			}
	        
//	        query.setFirstResult(stratNum);
//	        query.setMaxResults(fetchNum);
	        String exListSql = "select * from ( select row_.*, rownum rownum_ from ("
	    			+ listSql
	    			+ ") row_ where rownum <= " + (fetchNum + stratNum) + ") where rownum_ > " + stratNum;
	        System.out.println(exListSql.trim());
	        resultList = FrameworkApplication.baseJdbcDAO.queryForList(exListSql);
	        
        }catch(Exception e){
        	log.error("查询结果集出错！");
        	e.printStackTrace();
        }  
        
        resultMap.put(RequestNameConstants.TOTAL_COUNT, new Integer(totalCount));
        resultMap.put(RequestNameConstants.TOTAL_PAGE, new Integer(totalPage));
        resultMap.put(RequestNameConstants.RESULT_LIST, resultList);
        resultMap.put("listSql", listSql);

        return resultMap;
      }
    });
  }
  
  /**
    * 执行一个HSQL
    * @param hSql String
    * @return Integer 返回执行影响的记录的个数
    */
   public Integer executeHSQL(final String hSql)
   {
     return (Integer)getHibernateTemplate().execute(new HibernateCallback() {
       public Object doInHibernate(Session session) throws HibernateException, SQLException {
         Query query = session.createQuery(hSql);
         return new Integer(query.executeUpdate());
       }
     });
   }
   public Integer executeHSQL(String hSql,String objName,Object objValue)
   {
     HashMap map = new HashMap();
     map.put(objName,objValue);
     return executeHSQL(hSql,map);
   }
  /**
   * 执行hSql   （绑定参数）
   * @param hSql String               hSql
   * @param valueMap HashMap          hSql中变量和值的对应关系
   * @return Integer
   */
  public Integer executeHSQL(final String hSql,final HashMap valueMap)
  {
   return (Integer)getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
        Query query = session.createQuery(hSql);
        Iterator it = valueMap.keySet().iterator();
        String key = "";
        Object obj = null;
        while (it.hasNext())
        {
          key = (String)it.next();
          obj = valueMap.get(key);
          if (obj instanceof java.util.ArrayList)
            query.setParameterList(key,(java.util.ArrayList)obj);
          else
            query.setParameter(key,obj);
        }
        return new Integer(query.executeUpdate());
      }
    });
  }
  public Integer executeHSQL(final String hSql,final Object param)
  {
    return executeHSQL(hSql,new Object[]{param},null);
  }
  public Integer executeHSQL(final String hSql,final Object[] param)
  {
    return executeHSQL(hSql,param,null);
  }
  /**
     * 执行hSql   （位置绑定）
     * @param hSql String                  hSql
     * @param valueList ArrayList          hSql中值的集合
     * @return Integer
     */
    public Integer executeHSQL(final String hSql,final Object[] param,final NullableType[] typeArr)
    {
     return (Integer)getHibernateTemplate().execute(new HibernateCallback() {
        public Object doInHibernate(Session session) throws HibernateException, SQLException {
          Query query = session.createQuery(hSql);
          for (int i=0;i<param.length;i++)
          {
            if (typeArr!=null&&typeArr[i]!=null)
              query.setParameter(i, param[i],typeArr[i]);
            else
              query.setParameter(i, param[i]);
          }
          return new Integer(query.executeUpdate());
        }
      });
    }

    /**
     * 执行原生sql    执行出错后扔出异常
     * @param sql String   sql语句
     * @return Integer     返回执行sql所影响的记录的个数
     */
    public Integer executeCommonSql(final String sql)
    {
      return (Integer)getHibernateTemplate().execute(new HibernateCallback() {
        public Object doInHibernate(Session session) throws HibernateException, SQLException {
          Connection conn = session.connection();
         int result = -1;
         Statement stmt = null;
         try {
           stmt = conn.createStatement();
           result = stmt.executeUpdate(sql);
         }catch (Exception ex) {
           log.error("执行sql错误"+sql,ex);
           throw new HibernateException(ex);
         }
         finally{
           try {
             if (stmt!=null)
               stmt.close();
           }
           catch (Exception ex) {
             log.error("关闭statment错误"+sql,ex);
             throw new HibernateException(ex);
           }
         }
         return new Integer(result);
       }
     });
   }
  /**
   * 从文件中读取数据并保存入库
   * @param file CommonsMultipartFile        用户数据文件
   * @param sql String
   * @param replaceObjectName String
   * @return Integer
   */
  public Integer saveObjectDataFromFile(final CommonsMultipartFile file,final String sql)
  {
    return (Integer)getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
        Connection conn = null;
        int result = -1;
        PreparedStatement preStmt = null;
        String tmpData = null;
        InputStream stream = null;
        try {
          stream =file.getInputStream();
          BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
          conn = session.connection();
          conn.setAutoCommit(false);
          preStmt = conn.prepareStatement(sql);
          int i=0;
          while ((tmpData=reader.readLine())!=null&&tmpData.trim().length()>0)
          {
            i++;
            preStmt.setObject(1,tmpData);
            result += preStmt.executeUpdate();
            if (i%1000==0)
              conn.commit();
          }
          conn.commit();
        }catch (Exception ex) {
          log.error("执行sql错误"+sql,ex);
          conn.rollback();
          throw new HibernateException(ex);
        }
        finally{
          try {
            if (preStmt!=null)
              preStmt.close();
            conn.setAutoCommit(true);
            if (stream!=null)
              stream.close();
          }
          catch (Exception ex) {
            log.error("关闭statment错误"+sql,ex);
            throw new HibernateException(ex);
          }
        }
        return new Integer(result);
      }
    });
  }
  //保存某个表对象的值
  public void saveObjectValue(Object object,Serializable id)
  {
    if(id == null)
      getHibernateTemplate().save(object);
    else
    {
        getHibernateTemplate().evict(object);   //将该对象从缓存中清楚  以避免脏数据的保存
        HibernateObjUtils objUtils = new HibernateObjUtils(object);
        executeHSQL(objUtils.getHSql(),objUtils.getValueMap());
    }
  }

  /**
     * 根据hibernate对象动态生成hSql
     * @param obj Object     hibernate对象
     * @return Integer       执行结果
     */
    public Integer saveObject(final Object obj)
   {
     return (Integer)getHibernateTemplate().execute(new HibernateCallback() {
       public Object doInHibernate(Session session) throws HibernateException, SQLException {
         org.hibernate.impl.SessionImpl s = (org.hibernate.impl.SessionImpl)session;
         Object entity = s.getPersistenceContext().unproxyAndReassociate(obj);
         org.hibernate.persister.entity.EntityPersister persister = s.getEntityPersister(null,obj);
         java.util.Vector valueV = new java.util.Vector();
         Object[] valueArr = persister.getPropertyValues(obj,session.getEntityMode());
         String[] nameArr = persister.getPropertyNames();
         Type[] typeArr = persister.getPropertyTypes();
         StringBuffer buf = new StringBuffer();
         Object tmp;
         Serializable id = null;
         for (int i=0;i<valueArr.length;i++)
         {
           if (typeArr[i].isCollectionType()||valueArr[i]==null)
             continue;
           if ((typeArr[i].getName().indexOf("integer")>=0&&((Integer)valueArr[i]).intValue() == Integer.MAX_VALUE)
               ||(typeArr[i].getName().indexOf("long")>=0&&((Long)valueArr[i]).longValue()==Long.MAX_VALUE)
               ||(typeArr[i].getName().indexOf("float")>=0&&((Float)valueArr[i]).floatValue()==Float.MAX_VALUE)
               ||(typeArr[i].getName().indexOf("fouble")>=0&&((Double)valueArr[i]).doubleValue()==Double.MAX_VALUE))
           {
             buf.append(nameArr[i]+"=?,");
             valueV.add(null);
           }

           else
           {
               if (typeArr[i].isEntityType())
               {
                 tmp = s.getPersistenceContext().unproxyAndReassociate(valueArr[i]);
                 id = s.getEntityPersister(null,valueArr[i]).getIdentifier(tmp,session.getEntityMode());
                 buf.append(nameArr[i] + "=?,");
                 valueV.add(id);
               }
               else
               {
                   buf.append(nameArr[i] + "=?,");
                   valueV.add(valueArr[i]);
               }
           }
        }
        valueV.add(persister.getIdentifier(entity,session.getEntityMode()));
        String hSql = buf.toString();
        hSql = hSql.substring(0,hSql.length()-1);
        hSql = "update "+persister.getEntityName()+" set "+hSql+" where "+persister.getIdentifierPropertyName()+"=?";
        Query query = session.createQuery(hSql);
        for (int i=0;i<valueV.size();i++)
        {
          query.setParameter(i, valueV.get(i));
        }
        return new Integer(query.executeUpdate());
     }
     },true);
   }
   /**
    * 通过普通sql查询属性值(记录唯一)
    * @param sql String                原生sql
    * @return String
    */
   protected String queryUniqueValueByCommonSql(final String sql)
   {
     return (String)getHibernateTemplate().execute(new HibernateCallback() {
        public Object doInHibernate(Session session) throws HibernateException, SQLException {
          Connection conn = session.connection();
         ResultSet rs = null;
         Statement stmt = null;
         String result = "";
         try {
           stmt = conn.createStatement();
           rs = stmt.executeQuery(sql);
           if (rs.next())
           {
             result = rs.getString(1);
           }
         }catch (Exception ex) {
           log.error("执行sql错误"+sql,ex);
           throw new HibernateException(ex);
         }
         finally{
           try {
             if (rs!=null)
               rs.close();
             if (stmt!=null)
               stmt.close();
           }
           catch (Exception ex) {
             log.error("关闭statment错误"+sql,ex);
             throw new HibernateException(ex);
           }
         }
         return result;
       }
     });
   }
	/*
	 * add by jzy 09-01-13
	 * 清理缓存
	 */
	public void flush()
	{
		try
		{
			getHibernateTemplate().flush();
		}
		catch(RuntimeException re)
		{
			throw re;
		}
	}
	
	/**
	 * add by jzy 09-02-26
	 * 调用存储过程
	 * @param proc
	 * proc: {call leatherdb.dbo.p_delallusertabledata()}
	 */
	public void CallProcedure(String proc)
	{
		Session ses=this.getHibernateTemplate().getSessionFactory().openSession(); 
		try{ 
		  Connection conn = ses.connection();   
		  conn.setAutoCommit(false); 
		  CallableStatement st = conn.prepareCall(proc); 
		  st.executeUpdate(); 
		  conn.commit(); 
		}catch(Exception e){ 
		  e.printStackTrace(); 
		} 

	}
	
	/**
	 * 
	 * @param hql
	 * @return
	 */
	public List find(String hql)
	{
		return getHibernateTemplate().find(hql.toString());
	}
}