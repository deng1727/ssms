package com.aspire.ponaadmin.common.ressync;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class RsyncLogBO
  implements RsyncLog
{
  public static final String SYNC_STATUS_SESUSS_OK = "Y";
  public static final String SYNC_STATUS_SESUSS_FAIL = "N";
  public static RsyncStatusVO statusVO = null;

  private static JLogger log = LoggerFactory.getLogger(RsyncLogBO.class);

  public void initStatus(RsyncStatusVO vo)
  {
    statusVO = vo;
    if (log.isDebugEnabled()) {
      log.debug("initStatus==" + statusVO);
    }
    RsyncLogThread.setSpaceTime(vo.getSpaceTime());
    RsyncLogThread.setLogFilePath(vo.getLogFilePath());

    RsyncLogThread log = new RsyncLogThread();
  }

  public void analysisLog(String fileName)
  {
    HashMap map = parseXML(fileName);

    if (map != null)
    {
      Set entries = map.keySet();
      Iterator iter = entries.iterator();
      ArrayList logList = new ArrayList();
      while (iter.hasNext())
      {
        Object key = iter.next();
        RsyncLogVO logVO = (RsyncLogVO)map.get(key);
        logList.add(logVO);
      }
      if (log.isDebugEnabled()) {
        log.debug(fileName + " logList==" + logList.size());
      }
      RsyncLogDAO logDAO = new RsyncLogDAO();
      logDAO.batchInsert(logList);
    }
  }

  private HashMap parseXML(String fileName)
  {
    HashMap map = null;
    try
    {
      Document doc = null;
      SAXBuilder sb = new SAXBuilder();
      doc = sb.build(new FileInputStream(fileName));

      Element serverLog = doc.getRootElement();

      String ip = serverLog.getAttribute("ip").getValue();

      String id = serverLog.getAttribute("id").getValue();

      String sType = null;
      if (serverLog.getAttribute("serverType") != null)
      {
        sType = serverLog.getAttribute("serverType").getValue();
      }
      if (sType == null)
      {
        sType = "1";
      }
      int serverType = 1;
      try {
        serverType = Integer.parseInt(sType);
      }
      catch (NumberFormatException ex1) {
        log.error("NumberFormatException==" + sType, ex1);
        serverType = 1;
      }

      List pieceChildren = serverLog.getChildren("piece");
      List flagChildren = serverLog.getChildren("flag");

      int size = pieceChildren.size();

      map = new HashMap();
      RsyncLogVO logVO = null;

      for (int i = 0; i < size; i++)
      {
        Element piece = (Element)pieceChildren.get(i);
        logVO = new RsyncLogVO();
        logVO.setID(id);
        logVO.setFromIP(ip);
        logVO.setServerType(serverType);

        String toIP = piece.getChild("ip").getText();
        logVO.setToIP(toIP);
        logVO.setStatus(piece.getChild("status").getText());
        logVO.setContent(piece.getChild("note").getText());
        map.put(toIP, logVO);
      }

      size = flagChildren.size();

      for (int i = 0; i < size; i++)
      {
        Element flag = (Element)flagChildren.get(i);

        String toIP = flag.getChild("ip").getText();

        String flagStatus = flag.getChild("status").getText();
        if (flagStatus.compareTo("N") != 0)
        {
          logVO = (RsyncLogVO)map.get(toIP);
          logVO.setStatus(flagStatus);

          String node = flag.getChild("note").getText();
          logVO.setContent(logVO.getContent() + " , " + node);
        }
      }
    }
    catch (FileNotFoundException ex)
    {
      log.error("FileNotFoundException==" + fileName, ex);
    }
    catch (Exception ex)
    {
      log.error("Exception==" + ex.getMessage(), ex);
    }

    return map;
  }

  public static List getRsyncLogById(String id)
    throws DAOException
  {
    if (log.isDebugEnabled())
      log.debug("execute method getById(String id=" + id + ") begin");
    return new RsyncLogDAO().getById(id);
  }

  public static void main(String[] args)
  {
    String fileName = 
      "D:\\ftp\\rsyncwork\\log\\server.20050620001-10.1.3.163.log";
    RsyncLogBO bo = new RsyncLogBO();
    bo.analysisLog(fileName);
  }
}