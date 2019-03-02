package com.aspire.ponaadmin.web.datasync.implement.game;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basegame.GameTypeEntity;
import com.aspire.dotcard.syncData.tactic.TacticBO;
import com.aspire.dotcard.syncData.tactic.TacticVO;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class GameSyncTools 
{
	
	private static JLogger logger = LoggerFactory.getLogger(GameDealer.class);
	//���������Ϸ���ൽmm�����ӳ��
	private static HashMap CATEmAPPING=new HashMap();
	
	//���������Ϸ���ൽmm�����ӳ�䣨�µ���Ϸ���أ� add by aiyan
	private static HashMap GameTypeMap=new HashMap();
	
	//remove by aiyan 2012-10-19
//	//��Ϸ����id ����Ϸ����NAME�Ķ�Ӧ��
//	private static HashMap CATEmAPPING3 = new HashMap();
	
	//�Ҳ���MM��Ӧ���������£�MM��ŵ�Ŀ¼
	private static String OTHERS="����";
	//���������Ϸua��Ϣ���ն����ƵĶ�Ӧ��ϵ��
	private static HashMap UAMAPPING=new HashMap();
    
    /**
     * ��Ż���������id��Ӧ��ϵ
     */
    private static HashMap DEVICEMAP = new HashMap();
	
	private static GameSyncTools dao=new GameSyncTools();
	/**
	 * ��ʼ��Game���ൽMM�����ӳ���ϵ��
	 */
	public void initGameCateMapping()throws BOException
	
	{
		
        logger.info("��ʼ��Game���ൽMM�����ӳ���ϵ");
		CATEmAPPING.clear();
    	Object paras[]= {};
    	String sqlCode="datasync.implement.game.GameSyncTools.initGameCateMapping().SELECT";
    	ResultSet rs=null;
    	try
		{
    		rs=DB.getInstance().queryBySQLCode(sqlCode, paras);
    		
    		while(rs.next())
    		{ 
    			CATEmAPPING.put(rs.getString(1), rs.getString(2));
    		}
    		
		} catch (Exception e)
		{
			throw new BOException("��ʼ��Game���ൽMM�����ӳ���ϵʧ��",e);
		}finally
		{
			DB.close(rs);
		}
	}
	
	/**
	 * ��ʼ��Game���ൽMM�����ӳ���ϵ��(�µ���Ϸ����)
	 */
	public void initGameCateMapping2()throws BOException
	
	{
		
        logger.info("��ʼ��Game���ൽMM�����ӳ���ϵ");
//		CATEmAPPING2.clear();
//		CATEmAPPING3.clear();
        GameTypeMap.clear();
    	String sqlCode="datasync.implement.game.GameSyncTools.initGameCateMapping2";
    	ResultSet rs=null;
    	try
		{
    		rs=DB.getInstance().queryBySQLCode(sqlCode, null);
    		
    		while(rs.next())
    		{ 
    			GameTypeMap.put(rs.getString("id"), new GameTypeEntity(
    					rs.getString("id"),
    					rs.getString("name"),
    					rs.getString("mmid"),
    					rs.getString("mmname")
    					));
//    			CATEmAPPING2.put(rs.getString(1), rs.getString(2));
//    			CATEmAPPING3.put(rs.getString(1), rs.getString(3));
    		}
    		
		} catch (Exception e)
		{
			throw new BOException("��ʼ��Game���ൽMM�����ӳ���ϵʧ��",e);
		}finally
		{
			DB.close(rs);
		}
	}
	

	
	public static GameSyncTools getInstance()
	{
		return dao;
	}
	/**
	 * ��ȡ��Ϸ���ࡣ
	 * @param gameJDCate ������Ϸ��������
	 * @return MM��Ϸ��������
	 * @throws NullPointerException ����ʼ��ʧ�ܺ���á�
	 */
	public  String getMMCateName(String gameJDCate)
	{
		String mmCate=(String)CATEmAPPING.get(gameJDCate);
		if(mmCate==null)//����Ҳ�����Ӧ�ķ��࣬��Ĭ��Ϊ"����"���ࡣ
		{
			return OTHERS;
		}
		return mmCate;
	}
	

	//remove by aiyan 2012-10-19
//	/**
//	 * ��ȡ��Ϸ���ࡣ  (�»�����Ϸ) add by aiyan
//	 * @param gameJDCate ������Ϸ��������
//	 * @return MM��Ϸ��������
//	 * @throws NullPointerException ����ʼ��ʧ�ܺ���á�
//	 */
//	public  String getMMCateName2(String gameJDCate)
//	{
//		String mmCate=(String)CATEmAPPING2.get(gameJDCate);
//		if(mmCate==null)//����Ҳ�����Ӧ�ķ��࣬��Ĭ��Ϊ"����"���ࡣ
//		{
//			return OTHERS;
//		}
//		return mmCate;
//	}
//	
//	public  String getBaseName(String id)
//	{
//		String name=(String)CATEmAPPING3.get(id);
//		if(name==null)//����Ҳ�����Ӧ�ķ��࣬��Ĭ��Ϊ"����"���ࡣ
//		{
//			return "";
//		}
//		return name;
//	}
	
	public  HashMap getGameTypeMap()
	{
		return GameTypeMap;
	}
	
	/**
	 * ��ʼ������UA��MM��deviceName�Ķ�Ӧ��ϵ��
	 * @throws BOException
	 */
	public void initGameUAMapping()throws BOException
	{
		logger.info("��ʼ��ʼ������UA��MM��deviceName�Ķ�Ӧ��ϵ��");
		UAMAPPING.clear();
        DEVICEMAP.clear();
		String sqlCode="datasync.implement.game.GameSyncTools.getGameUAMapping().SELECT";
		ResultSet rs=null;
		try
		{
			rs= DB.getInstance().queryBySQLCode(sqlCode, null);
			while(rs.next())
			{
				String deviceName=rs.getString(2);
				if(deviceName==null||"".equals(deviceName.trim()))
				{
					continue; //���û������ӳ���ϵ���Ѳ�����mapping�С�
				}
				UAMAPPING.put(rs.getString(1), deviceName);
                DEVICEMAP.put(deviceName, String.valueOf(rs.getObject(3)));
			}
		} catch (Exception e)
		{
			throw new BOException("��ȡ��ϷUAӳ���ϵ����",e);
		}finally
		{
			DB.close(rs);
		}
	}
	/**
	 * ��ȡ baseUA ��ӦMM devicename
	 * @param baseUA ����UA��Ϣ
	 * @return MM�����devicename
	 */
	public String getMMUAMapping(String baseUA)
	{
		return (String)UAMAPPING.get(baseUA);
	}
    
    /**
     * ��ȡ MM devicename ��ӦMM deviceID
     * @param deviceName
     * @return
     */
    public String getMMDeviceMapping(String deviceName)
    {
        return (String)DEVICEMAP.get(deviceName);
    }
    
	/**
	 * ��ȡ��ǰUA��mapping����ͬ����Ϸ�ֵ�ʱʹ�á�
	 * @return
	 */
	public HashMap getAllBaseUA()throws BOException
	{
		logger.info("��ʼ��ȡ��ǰϵͳ����baseUA��Ϣ��");
		HashMap map=new HashMap();
		String sqlCode="datasync.implement.game.GameSyncTools.getAllBaseUA().SELECT";
		ResultSet rs=null;
		try
		{
		    rs= DB.getInstance().queryBySQLCode(sqlCode, null);
			while(rs.next())
			{
				map.put(rs.getString(1),"0");
			}
		} catch (Exception e)
		{
			throw new BOException("��ȡϵͳ����baseUA��Ϣ����",e);
		}finally
		{
			DB.close(rs);
		}
		
		return map;
	}
	/**
	 * ��ȡ��Ϸ�ϼܵĲ���
	 * @return
	 */
	public List getSyncTactic() throws BOException
	{
		logger.info("��ʼ��ȡͬ��������Ϣ��");
		// ��ȡ���е�CMS����ͬ������
		List tacticList = new TacticBO().queryAll();

		if (logger.isDebugEnabled())
		{
			logger.debug("CMS����ͬ������:");
			for (int i = 0; i < tacticList.size(); i++)
			{
				logger.debug("[" + (i + 1) + " ] " + (TacticVO) tacticList.get(i));
			}
		}
		return tacticList;
	}
	/**
	 * ��ȡ������Ϸ�������� �ϼܵĲ���
	 * @return
	 */
	public Map getSyncGameTactic() throws BOException
	{
		logger.info("��ʼ��ȡͬ��������Ϸ������Ϣ��");
		// ��ȡ���е�CMS����ͬ������


		HashMap map;
		try
		{
			map = GameSyncDAO.getInstance().getAllBaseGameTactic();
		} catch (DAOException e)
		{
			throw new BOException("��ȡ��ǰ�����Ѵ�����Ϸ�б����", e);
		}
		
		if (logger.isDebugEnabled())
		{
			Set set = map.entrySet();
			Iterator it = set.iterator();
			logger.debug("���ص�����Ϸͬ������:");
			while (it.hasNext())
			{
				int i = 0;
				Entry entry = (Entry)it.next();
				logger.debug("[" + (i + 1) + " ] key =" + entry.getKey() + "; value="+entry.getValue());
			}
		}
		return Collections.synchronizedMap(map);
		
	}
	/**
	 * ��ȡ��ǰϵͳ������Ϸ��HashMap��key ��ʾ��Ϸ��������ʹ��BaseGameKeyVO���ʾ��value��־��Ϸ���µ�״̬�� 0��ʾ��ͬ��ǰ�����״̬��1��ʾ����ͬ��������ֵ��2��ʾ����ͬ�����µ�ֵ
	 * @return ��ǰ������Ϸ�ļ���
	 */
	public Map getAllBaseGames()throws BOException
	{
		List list;
		try
		{
			list = GameSyncDAO.getInstance().getAllBaseGames();
		} catch (DAOException e)
		{
			throw new BOException("��ȡ��ǰ�����Ѵ�����Ϸ�б����", e);
		}
		//ת����hashmap����ʽ���ں����Ƚϵ�Ч�ʻ��list��
		HashMap oldGameMap=new HashMap();
		for (int i = 0; i < list.size(); i++)
		{
			BaseGameKeyVO gameKeyVO= (BaseGameKeyVO) list.get(i);
			oldGameMap.put(gameKeyVO.getIcpServid(), gameKeyVO);
		}
		return Collections.synchronizedMap(oldGameMap);
	}
	
	 /**
		 * ��ȡ��Դ������ftp���ӣ������뵱ǰͼƬ�����Ŀ¼
		 * @return FTPClient
		 * @throws FTPException
		 * @throws IOException
		 * @throws BOException
		 */
    public  FTPClient getResourceServerFtp() throws IOException, FTPException
    {
        // ���������ȡftp���ӵĵ�ַ
        String sourceServerIP = GameConfig.IP;
        // ���������ȡftp���Ӵ���Ķ˿ں�
        int sourceServerPort = GameConfig.PORT;
        // ���������ȡFTP�������ĵ�¼�û���
        String sourceServerUser = GameConfig.USER;
        // ���������ȡFTP�������ĵ�¼����
        String sourceServerPassword = GameConfig.PWD;

        FTPClient ftp = new FTPClient(sourceServerIP, sourceServerPort);
        // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
        ftp.setConnectMode(FTPConnectMode.PASV);
        ftp.login(sourceServerUser, sourceServerPassword);
        // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
        ftp.setType(FTPTransferType.BINARY);
        
        if(!"".equals(GameConfig.BaseFolder))//���뵱ǰ��Դ������Ŀ¼��
        {
        	ftp.chdir(GameConfig.BaseFolder);
        }
        return ftp;
    }
    /**
     * ������Ҫ t_r_gcontent �� t_game_service ��Ϸ������Ҫһһ��Ӧ�������ڱ���ʱû��ʹ������
     * ֻ��ʹ��������������������ܵı������ݵ�һ���ԡ�
     * @param vo
     */
    public void saveGameService(GameServiceVO vo)throws BOException
    {
    	try
		{
			if(GameSyncDAO.getInstance().isExistedGameService(vo))
			{
				GameSyncDAO.getInstance().updateGameService(vo);
			}else
			{
				GameSyncDAO.getInstance().insertGameService(vo);
			}
		} catch (DAOException e)
		{
			throw new BOException("������Ϸҵ����Ϣʧ�ܣ�",e);
		}
    }
    
    
    /**  
     * ������Ҫ t_r_gcontent �� t_game_service ��Ϸ������Ҫһһ��Ӧ�������ڱ���ʱû��ʹ������
     * ֻ��ʹ��������������������ܵı������ݵ�һ���ԡ�
     * @param vo  
     * 
     * add by aiyan 2012-09-12
     */
    public void saveGameService2(GameServiceVO vo)throws BOException
    {
    	try
		{
			if(GameSyncDAO.getInstance().isExistedGameService2(vo))
			{
				GameSyncDAO.getInstance().updateGameService2(vo);
			}else
			{
				GameSyncDAO.getInstance().insertGameService2(vo);
			}
		} catch (DAOException e)
		{
			throw new BOException("������Ϸҵ����Ϣʧ�ܣ�",e);
		}
    }

}
