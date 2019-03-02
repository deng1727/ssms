package com.aspire.common.config;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;

import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class ServerInfo
{
  private static JLogger logger = LoggerFactory.getLogger(ServerInfo.class);

  public static String FS = System.getProperty("file.separator");

  private static String hostName = null;

  private static String localIP = null;

  private static String serverName = null;
  private static int serverPort;
  private static String userName = null;

  private static String password = null;

  private static String systemName = null;

  private static String systemId = null;

  private static String localMISCID = null;

  private static String configFileBakPath = null;

  private static String portalConfigFileName = null;

  private static String systemConfigFileName = null;

  private static String marcoDefFileName = null;

  private static boolean standAlone = false;

  private static String appRootPath = null;

  public static String configFileName = null;

  public static void setStandAlone(boolean sAlone)
  {
    standAlone = sAlone;
  }

  public static void setAppRootPath(String path)
  {
    appRootPath = path;
  }

  public static void setLocalMISCID(String miscId)
  {
    localMISCID = miscId;
  }

  public static String getLocalMISCID()
  {
    return localMISCID;
  }

  public static void setSystemName(String sysName)
  {
    systemName = sysName;
  }

  public static String getSystemName()
  {
    return systemName;
  }

  public static String getSystemId()
  {
    return systemId;
  }

  public static void setSystemId(String sysId)
  {
    systemId = sysId;
  }

  public static String getHostName()
  {
    try
    {
      hostName = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException ex) {
      logger.error("get host name error: " + ex.toString());
    }
    return hostName;
  }

  public static String getLoaclIP()
  {
    try
    {
      localIP = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException ex) {
      logger.error("get local IP error: " + ex.toString());
    }
    return localIP;
  }

  public static String getUserName()
  {
    if (userName == null) {
      ModuleConfig common = null;
      try {
        common = ConfigFactory.getSystemConfig().getModuleConfig(
          "Common");
      } catch (Exception ex) {
        logger.error("Common模块的key值配置有误或者漏配置该模块." + ex.toString());
      }
      try
      {
        userName = common.getItemValue("username");
      } catch (Exception ex) {
        logger.error("Common模块的username的key值配置有误或者漏配置该配置项." + 
          ex.toString());
      }
    }
    return userName;
  }

  public static String getPassword()
  {
    if (password == null) {
      ModuleConfig common = null;
      try {
        common = ConfigFactory.getSystemConfig().getModuleConfig(
          "Common");
      } catch (Exception ex) {
        logger.error("Common模块的key值配置有误或者漏配置该模块." + ex.toString());
      }
      try
      {
        password = common.getItemValue("password");
      } catch (Exception ex) {
        logger.error("Common模块的password的key值配置有误或者漏配置该配置项." + 
          ex.toString());
      }
    }

    return password;
  }

  public static String getServerName()
  {
    try
    {
      serverName = System.getProperty("jboss.server.name");
    } catch (Exception ex) {
      logger.error("get server name error: ", ex);
    }
    return serverName;
  }

//  public static int getServerPort()
//  {
//    if (serverPort == 0) {
//      new ServerInfo().readServerInfo();
//    }
//    return serverPort;
//  }

  public static String getServerPath()
  {
    String path = null;
    File file = new File(".");
    try
    {
      path = file.getCanonicalPath();
    }
    catch (IOException ex) {
      logger.error("Get Server Path error:" + ex.toString());
    }
    return path;
  }

  public static String getAppRootPath()
  {
    if (standAlone) {
      if (appRootPath == null) {
        File file = new File(".");
        try {
          appRootPath = file.getCanonicalPath();
        }
        catch (IOException ex)
        {
          logger.error("Get app root path error:" + ex.toString());
        }
      }
    }
    else {
      appRootPath = getServerPath() + FS + getSystemName().toLowerCase();
    }

    return appRootPath;
  }

  public static String[] getServerIpAndPort()
  {
    String[] addrs = (String[])null;
    ModuleConfig mcfg = null;
    try {
      mcfg = ConfigFactory.getSystemConfig().getModuleConfig("OAM");
    } catch (Exception ex) {
      logger.error("OAM模块的key值配置有误或者漏配置该模块." + ex.toString());
    }
    try {
      addrs = mcfg.getArrayItem("DomainServers").getValueList();
    } catch (Exception ex) {
      logger.error("OAM模块的DomainServers的key值配置有误或者漏配置." + ex.toString());
    }
    return addrs;
  }

//  private void readServerInfo()
//  {
//    try
//    {
//      MBeanServer server = MBeanRegister.getLocalMBeanServer();
//      ObjectName mbeanName = new ObjectName("jboss:service=Naming");
//      serverPort = ((Integer)server.getAttribute(mbeanName, "Port"))
//        .intValue();
//    }
//    catch (Exception e)
//    {
//      logger.error("Error: Get current server Name and Port failed.", 
//        e);
//    }
//  }

  public static int getOpenedSocketCount()
  {
    return -1;
  }

//  public static Hashtable getAllAppRootPath()
//  {
//    Hashtable serversAppRoots = new Hashtable();
//    RemoteOperations ro = new RemoteOperations();
//    String[] ipAndPorts = ro.getServerIpAndPort();
//    String remoteAppRootPaht = null;
//    for (int i = 0; i < ipAndPorts.length; i++) {
//      ro.setMBeanServerAndDomain(ipAndPorts[i]);
//      remoteAppRootPaht = (String)ro.doOperation(
//        "Name=SystemConfig,Type=SystemConfig", "getLocalAppRootPath", 
//        null, null);
//      serversAppRoots.put(ipAndPorts[i], remoteAppRootPaht);
//    }
//    return serversAppRoots;
//  }

  public static String getConfigFileBakPath()
  {
    return configFileBakPath;
  }

  public static void setConfigFileBakPath(String configFileBakPath)
  {
    configFileBakPath = configFileBakPath;
  }

  public static String getPortalConfigFileName()
  {
    return portalConfigFileName;
  }

  public static void setPortalConfigFileName(String portalConfigFileName)
  {
    portalConfigFileName = portalConfigFileName;
  }

  public static String getSystemConfigFileName()
  {
    return systemConfigFileName;
  }

  public static void setSystemConfigFileName(String systemConfigFileName)
  {
    systemConfigFileName = systemConfigFileName;
  }

  public static String getMarcoDefFileName()
  {
    return marcoDefFileName;
  }

  public static void setMarcoDefFileName(String marcoDefFileName)
  {
    marcoDefFileName = marcoDefFileName;
  }
  public static MBeanServerConnection getRemoteMBeanServer(String ipAndPort) {
    Hashtable env = new Hashtable();
    env.put("java.naming.factory.initial", 
      "org.jnp.interfaces.NamingContextFactory");
    env.put("java.naming.provider.url", ipAndPort);
    MBeanServerConnection server = null;
    try {
      Context ctx = new InitialContext(env);
      server = (MBeanServerConnection)ctx
        .lookup("jmx/invoker/RMIAdaptor");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return server;
  }
}