package com.aspire.common.log.proxy.config;

/**
 * <p>Title: ConfigModifierMBean</p>
 * <p>Description: the JMX interface for configuring the config file</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author YanFeng
 * @version 1.0
 * history
 * created at 22/4/2003
 */

public interface ConfigModifierMBean
{
  public void disableFilterAndTrace();
  public void enableFilterAndTrace(String range,String userMobileID);
}
