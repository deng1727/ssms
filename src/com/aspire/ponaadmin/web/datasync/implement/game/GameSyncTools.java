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
	//保存基地游戏分类到mm分类的映射
	private static HashMap CATEmAPPING=new HashMap();
	
	//保存基地游戏分类到mm分类的映射（新的游戏基地） add by aiyan
	private static HashMap GameTypeMap=new HashMap();
	
	//remove by aiyan 2012-10-19
//	//游戏基地id 和游戏基地NAME的对应。
//	private static HashMap CATEmAPPING3 = new HashMap();
	
	//找不到MM对应分类的情况下，MM存放的目录
	private static String OTHERS="其他";
	//保存基地游戏ua信息和终端名称的对应关系。
	private static HashMap UAMAPPING=new HashMap();
    
    /**
     * 存放机型名称与id对应关系
     */
    private static HashMap DEVICEMAP = new HashMap();
	
	private static GameSyncTools dao=new GameSyncTools();
	/**
	 * 初始化Game分类到MM分类的映射关系。
	 */
	public void initGameCateMapping()throws BOException
	
	{
		
        logger.info("初始化Game分类到MM分类的映射关系");
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
			throw new BOException("初始化Game分类到MM分类的映射关系失败",e);
		}finally
		{
			DB.close(rs);
		}
	}
	
	/**
	 * 初始化Game分类到MM分类的映射关系。(新的游戏基地)
	 */
	public void initGameCateMapping2()throws BOException
	
	{
		
        logger.info("初始化Game分类到MM分类的映射关系");
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
			throw new BOException("初始化Game分类到MM分类的映射关系失败",e);
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
	 * 获取游戏分类。
	 * @param gameJDCate 基地游戏分类名称
	 * @return MM游戏货架名称
	 * @throws NullPointerException 当初始化失败后调用。
	 */
	public  String getMMCateName(String gameJDCate)
	{
		String mmCate=(String)CATEmAPPING.get(gameJDCate);
		if(mmCate==null)//如果找不到相应的分类，则默认为"其他"分类。
		{
			return OTHERS;
		}
		return mmCate;
	}
	

	//remove by aiyan 2012-10-19
//	/**
//	 * 获取游戏分类。  (新基地游戏) add by aiyan
//	 * @param gameJDCate 基地游戏分类名称
//	 * @return MM游戏货架名称
//	 * @throws NullPointerException 当初始化失败后调用。
//	 */
//	public  String getMMCateName2(String gameJDCate)
//	{
//		String mmCate=(String)CATEmAPPING2.get(gameJDCate);
//		if(mmCate==null)//如果找不到相应的分类，则默认为"其他"分类。
//		{
//			return OTHERS;
//		}
//		return mmCate;
//	}
//	
//	public  String getBaseName(String id)
//	{
//		String name=(String)CATEmAPPING3.get(id);
//		if(name==null)//如果找不到相应的分类，则默认为"其他"分类。
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
	 * 初始化基地UA和MM的deviceName的对应关系。
	 * @throws BOException
	 */
	public void initGameUAMapping()throws BOException
	{
		logger.info("开始初始化基地UA和MM的deviceName的对应关系。");
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
					continue; //如果没有设置映射关系，把不放入mapping中。
				}
				UAMAPPING.put(rs.getString(1), deviceName);
                DEVICEMAP.put(deviceName, String.valueOf(rs.getObject(3)));
			}
		} catch (Exception e)
		{
			throw new BOException("读取游戏UA映射关系错误",e);
		}finally
		{
			DB.close(rs);
		}
	}
	/**
	 * 获取 baseUA 对应MM devicename
	 * @param baseUA 基地UA信息
	 * @return MM定义的devicename
	 */
	public String getMMUAMapping(String baseUA)
	{
		return (String)UAMAPPING.get(baseUA);
	}
    
    /**
     * 获取 MM devicename 对应MM deviceID
     * @param deviceName
     * @return
     */
    public String getMMDeviceMapping(String deviceName)
    {
        return (String)DEVICEMAP.get(deviceName);
    }
    
	/**
	 * 获取当前UA的mapping。在同步游戏字典时使用。
	 * @return
	 */
	public HashMap getAllBaseUA()throws BOException
	{
		logger.info("开始获取当前系统所有baseUA信息。");
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
			throw new BOException("读取系统所有baseUA信息错误",e);
		}finally
		{
			DB.close(rs);
		}
		
		return map;
	}
	/**
	 * 获取游戏上架的策略
	 * @return
	 */
	public List getSyncTactic() throws BOException
	{
		logger.info("开始获取同步策略信息。");
		// 获取所有的CMS内容同步策略
		List tacticList = new TacticBO().queryAll();

		if (logger.isDebugEnabled())
		{
			logger.debug("CMS内容同步策略:");
			for (int i = 0; i < tacticList.size(); i++)
			{
				logger.debug("[" + (i + 1) + " ] " + (TacticVO) tacticList.get(i));
			}
		}
		return tacticList;
	}
	/**
	 * 获取基地游戏二级分类 上架的策略
	 * @return
	 */
	public Map getSyncGameTactic() throws BOException
	{
		logger.info("开始获取同步基地游戏策略信息。");
		// 获取所有的CMS内容同步策略


		HashMap map;
		try
		{
			map = GameSyncDAO.getInstance().getAllBaseGameTactic();
		} catch (DAOException e)
		{
			throw new BOException("获取当前库中已存在游戏列表出错！", e);
		}
		
		if (logger.isDebugEnabled())
		{
			Set set = map.entrySet();
			Iterator it = set.iterator();
			logger.debug("基地单机游戏同步策略:");
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
	 * 获取当前系统所有游戏的HashMap。key 表示游戏的主键，使用BaseGameKeyVO类表示，value标志游戏更新的状态。 0表示从同步前的最初状态，1表示本次同步新增的值，2表示本次同步更新的值
	 * @return 当前所有游戏的集合
	 */
	public Map getAllBaseGames()throws BOException
	{
		List list;
		try
		{
			list = GameSyncDAO.getInstance().getAllBaseGames();
		} catch (DAOException e)
		{
			throw new BOException("获取当前库中已存在游戏列表出错！", e);
		}
		//转化成hashmap的形式，在后续比较的效率会比list高
		HashMap oldGameMap=new HashMap();
		for (int i = 0; i < list.size(); i++)
		{
			BaseGameKeyVO gameKeyVO= (BaseGameKeyVO) list.get(i);
			oldGameMap.put(gameKeyVO.getIcpServid(), gameKeyVO);
		}
		return Collections.synchronizedMap(oldGameMap);
	}
	
	 /**
		 * 获取资源服务器ftp连接，并进入当前图片保存根目录
		 * @return FTPClient
		 * @throws FTPException
		 * @throws IOException
		 * @throws BOException
		 */
    public  FTPClient getResourceServerFtp() throws IOException, FTPException
    {
        // 从配置项读取ftp连接的地址
        String sourceServerIP = GameConfig.IP;
        // 从配置项读取ftp连接传输的端口号
        int sourceServerPort = GameConfig.PORT;
        // 从配置项读取FTP服务器的登录用户名
        String sourceServerUser = GameConfig.USER;
        // 从配置项读取FTP服务器的登录密码
        String sourceServerPassword = GameConfig.PWD;

        FTPClient ftp = new FTPClient(sourceServerIP, sourceServerPort);
        // 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
        ftp.setConnectMode(FTPConnectMode.PASV);
        ftp.login(sourceServerUser, sourceServerPassword);
        // 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
        ftp.setType(FTPTransferType.BINARY);
        
        if(!"".equals(GameConfig.BaseFolder))//进入当前资源服务器目录。
        {
        	ftp.chdir(GameConfig.BaseFolder);
        }
        return ftp;
    }
    /**
     * 由于需要 t_r_gcontent 和 t_game_service 游戏数据需要一一对应，但是在保存时没有使用事务
     * 只能使用这个笨方法，尽量可能的保持数据的一致性。
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
			throw new BOException("保存游戏业务信息失败！",e);
		}
    }
    
    
    /**  
     * 由于需要 t_r_gcontent 和 t_game_service 游戏数据需要一一对应，但是在保存时没有使用事务
     * 只能使用这个笨方法，尽量可能的保持数据的一致性。
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
			throw new BOException("保存游戏业务信息失败！",e);
		}
    }

}
