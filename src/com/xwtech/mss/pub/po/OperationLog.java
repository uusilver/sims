package com.xwtech.mss.pub.po;



/**
 * MssOperationLog generated by MyEclipse - Hibernate Tools
 */

public class OperationLog  implements java.io.Serializable {


    // Fields    

     private Long logId;
     
     //操作用户名
     private String loginName;
     
     //操作类型：1-增加/2-修改/3-删除
     private Long doType;
     
     //操作的对象，具体内容常见code_book表中的配置信息
     private Long doObject;
     
     //操作对象类型，具体内容常见code_book表中的配置信息
     private Long objType;
     
     //操作的主表
     private String tableName;
     
     private String description;
     
     private String doTime;


    // Constructors

    /** default constructor */
    public OperationLog() {
    }

	/** minimal constructor */
    public OperationLog(Long logId, String loginName, Long doType, Long doObject, String tableName, String doTime) {
        this.logId = logId;
        this.loginName = loginName;
        this.doType = doType;
        this.doObject = doObject;
        this.tableName = tableName;
        this.doTime = doTime;
    }
    
    /** full constructor */
    public OperationLog(Long logId, String loginName, Long doType, Long doObject, Long objType, String tableName, String description, String doTime) {
        this.logId = logId;
        this.loginName = loginName;
        this.doType = doType;
        this.doObject = doObject;
        this.objType = objType;
        this.tableName = tableName;
        this.description = description;
        this.doTime = doTime;
    }

   
    // Property accessors

    public Long getLogId() {
        return this.logId;
    }
    
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getLoginName() {
        return this.loginName;
    }
    
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getDoType() {
        return this.doType;
    }
    
    public void setDoType(Long doType) {
        this.doType = doType;
    }

    public Long getDoObject() {
        return this.doObject;
    }
    
    public void setDoObject(Long doObject) {
        this.doObject = doObject;
    }

    public Long getObjType() {
        return this.objType;
    }
    
    public void setObjType(Long objType) {
        this.objType = objType;
    }

    public String getTableName() {
        return this.tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoTime() {
        return this.doTime;
    }
    
    public void setDoTime(String doTime) {
        this.doTime = doTime;
    }
   








}