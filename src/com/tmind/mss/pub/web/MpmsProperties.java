package com.tmind.mss.pub.web;

import com.tmind.framework.pub.web.BaseFrameworkApplication;

public class MpmsProperties {
  private String queryConfigFile;
  
  private String queryStyleConfigsFile;
  
  public MpmsProperties() {
	  
  }

  public void setQueryConfigFile(String queryConfigFile) {
    this.queryConfigFile = queryConfigFile;
    BaseFrameworkApplication.queryConfigFilePath = this.queryConfigFile;
  }

  public String getQueryConfigFile() { 
    return this.queryConfigFile;
  }

  public void setQueryStyleConfigsFile(String queryStyleConfigsFile) {
    this.queryStyleConfigsFile = queryStyleConfigsFile;
    BaseFrameworkApplication.queryStyleConfigFilePath = this.queryStyleConfigsFile;
  }

  public String getQueryStyleConfigsFile() {
    return this.queryStyleConfigsFile;
  }

}
