package com.aspire.ponaadmin.common.ressync;

public class RsyncLogVO
{
  public static int IS_PRESEVER = 0;
  public static int IS_SERVER = 1;
  private String ID;
  private String status;
  private String content;
  private String fromIP;
  private String toIP;
  private int serverType;

  public String getID()
  {
    return this.ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getFromIP() {
    return this.fromIP;
  }

  public void setFromIP(String fromIP) {
    this.fromIP = fromIP;
  }

  public String getToIP() {
    return this.toIP;
  }

  public void setToIP(String toIP) {
    this.toIP = toIP;
  }

  public String toString()
  {
    return this.ID + "," + this.toIP;
  }
  public int getServerType() {
    return this.serverType;
  }
  public void setServerType(int serverType) {
    this.serverType = serverType;
  }
}