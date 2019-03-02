package com.aspire.ponaadmin.common.ressync;

public class RsyncStatusVO
{
  private String tableName;
  private String fieldName;
  private String okStatus;
  private String failStatus;
  private int serverNum;
  private int serverTestNum;
  private int spaceTime;
  private String logFilePath;

  public RsyncStatusVO()
  {
    this.tableName = "test";
    this.fieldName = "test";
    this.okStatus = "Y";
    this.failStatus = "N";
    this.serverNum = 8;
    this.serverTestNum = 1;
  }

  public String getTableName() {
    return this.tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getFieldName() {
    return this.fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getOkStatus() {
    return this.okStatus;
  }

  public void setOkStatus(String okStatus) {
    this.okStatus = okStatus;
  }

  public String getFailStatus() {
    return this.failStatus;
  }

  public void setFailStatus(String failStatus) {
    this.failStatus = failStatus;
  }

  public int getServerNum() {
    return this.serverNum;
  }

  public void setServerNum(int serverNum) {
    this.serverNum = serverNum;
  }

  public String toString()
  {
    return this.tableName + "," + this.logFilePath;
  }

  public int getSpaceTime()
  {
    return this.spaceTime;
  }

  public void setSpaceTime(int spaceTime)
  {
    this.spaceTime = spaceTime;
  }

  public String getLogFilePath()
  {
    return this.logFilePath;
  }

  public void setLogFilePath(String logFilePath)
  {
    this.logFilePath = logFilePath;
  }
  public int getServerTestNum() {
    return this.serverTestNum;
  }
  public void setServerTestNum(int serverTestNum) {
    this.serverTestNum = serverTestNum;
  }
}