package com.aspire.ponaadmin.web.datasync.implement.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.DeviceDAO;
import com.aspire.dotcard.gcontent.GAppContent;
import com.aspire.dotcard.gcontent.GAppGame;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.dotcard.syncData.tactic.TacticConstants;
import com.aspire.dotcard.syncData.tactic.TacticVO;
import com.aspire.dotcard.syncData.util.SyncDataConstant;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTools;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;
import com.aspire.ponaadmin.web.repository.goods.GoodsVO;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;

public class GameDealer implements DataDealer
{
	private static JLogger logger = LoggerFactory.getLogger(GameDealer.class);
	/**
	 * 当前数据库game的游戏id的集合。key 表示游戏的主键，使用BaseGameKeyVO类表示，value标志游戏更新的状态。
	 * 0表示从同步前的最初状态，1表示本次同步新增的值，2表示本次同步更新的值.
	 * 该Map必须是线程安全的。
	 */
	private static Map oldGameMap;
	/**
	 * 目前MM系统的终端库。devicename和brand的映射
	 */
	private static HashMap deviceNameMap;
	/**
	 * 获取游戏文件根目录的路径
	 */
	private String gameFileRootPath;
	// 资源内容存储分类的根节点
	private static Category contentRoot;// 用于缓存
	// 用于缓存,货架的根分类701节点
	private static Category categoryRoot;
	// 上架过游戏的货架缓存，需要时线程安全的。
	private static Map cateCache = Collections.synchronizedMap(new HashMap());
	private static List TACTICLIST;
	
	/**
	 * 基地游戏内容同步策略
	 */
	private static Map gameTacticMap;
	
	//基地游戏 内容二级分类 同步策略开关
	private String gameTatictype;
	/**
	 * 该游戏终端适配的UA信息文件的编码方式。
	 */
	private String fileEncoding;

	/**
	 * 任务开始前需要把当前数据的游戏提取出来，用于比较
	 */
	public void prepareData() throws BOException, DAOException
	{
		GameSyncTools.getInstance().initGameCateMapping();
		GameSyncTools.getInstance().initGameUAMapping();
		TACTICLIST = GameSyncTools.getInstance().getSyncTactic();
		oldGameMap = GameSyncTools.getInstance().getAllBaseGames();
		//取数据库中支持的终端类型DeviceDAO
		deviceNameMap = DeviceDAO.getInstance().getDeviceNameMapBrand();	
		//获取基地游戏单机精品二级分类同步策略
		gameTacticMap =  GameSyncTools.getInstance().getSyncGameTactic();
		
		
	
		gameTatictype = ConfigFactory.getSystemConfig()
        .getModuleConfig("ssms")
                                      .getItemValue("gameTatictype");
	
	}

	public void clearDirtyData()
	{

		TACTICLIST = null;
		oldGameMap = null;
		deviceNameMap = null;
		gameTacticMap = null;
		cateCache.clear();
		gameTatictype = null;
		//删除解压缩的文件夹
		IOUtil.deleteFile(new File(gameFileRootPath));

	}

	public int dealDataRecrod(DataRecord record)
	{
		//最后一个值保存当前处理行数，这样为了在多线程下，利于日志的查看。
		Object lineNumber=record.get(record.size());
		if(logger.isDebugEnabled())
		{
			logger.debug("开始保存第"+lineNumber+"行游戏数据。");
		}
		//需要先确定该游戏主键。
		BaseGameKeyVO gameKey = new BaseGameKeyVO();
		gameKey.setIcpCode((String) record.get(1));
		gameKey.setIcpServid((String) record.get(3));

		GAppGame game = new GAppGame();
		game.setSubType("1");
		GameServiceVO servo = new GameServiceVO();
		try
		{
			turnFieldsToGameInfo(record, servo, game);
			String icpservId = game.getIcpServId();
			// 解压该游戏的图片信息。
			String pictureRootPath = gameFileRootPath + File.separator + icpservId;
			setGamePic(game, pictureRootPath);
			// 读取终端适配的信息
			List deviceList = getDeviceNames(pictureRootPath + ".ua", icpservId);
//			if(deviceList == null || deviceList.size()<=0){
//				throw new BOException("基地游戏适配机型为空，不入库；basegame  device is null");
//			}
			setDeviceNameAndBrand(deviceList, game);
			// 比对,id的value值为0表示，第一次操作
			if (oldGameMap.containsKey(gameKey.getIcpServid()))
			{

				if (deviceList == null || deviceList.size() <= 0)
				{
					// throw new BOException("基地游戏适配机型为空，不入库；basegame device is
					// null");
					logger.error("基地游戏适配机型为空，不入库；basegame  device is null"
							+ gameKey.getIcpServid());
					return DataSyncConstants.SUCCESS_UPDATE;
				}
				else
				{
					// 需要获取系统
					gameKey = (BaseGameKeyVO) oldGameMap.get(gameKey.getIcpServid());
					if (gameKey.getStatus() == 0)// 出示状态，既第一次更新。
					{
						if (logger.isDebugEnabled())
						{
							logger.debug("需要更新基地游戏:icpcode=" + gameKey.getIcpCode()
									+ ",icpservid=" + gameKey.getIcpServid());
						}

						game.setId(String.valueOf(gameKey.getId()));
						game.save();
						// oldGameMap.put(gameKey, "2");
						gameKey.setStatus(2);
						// 新增业务信息
						GameSyncTools.getInstance().saveGameService(servo);

						if (gameTatictype.equals("true"))
						{
							// 内容同步策略
							if (gameKey.getExpPrice() != game.getExpPrice()
									|| !gameKey.getAppcateName().equals(
											game.getAppCateName())
									|| !gameKey.getChargeTime().equals(
											game.getChargeTime())
									|| gameKey.getOnlineType() != game.getOnlineType())
							{// 特价资费有变更 或者 二级分类有变更 或者 单机 试玩有变更，或者单机网游 有变更
								if (gameKey.getExpPrice() != game.getExpPrice())
								{// 特价资费变更
									if (gameKey.getExpPrice() == 0)
									{// 原来是非 特价 变为特价：下架所有货架该商品，上架特价专区
										deleteRefrenceAllCategory(gameKey.getId());
										logger
												.debug("---------非特价变为特价，下架所有货架该商品，有自动更新上架特价专区--------");
										// 上架 特价专区 由自动更新规则控制
									}
									else if (game.getExpPrice() == 0)
									{// 原来是特价，变为 非特价：单机精品上架
										if (game.getChargeTime().equals("1")
												&& game.getOnlineType() == 1)
										{// 单机精品
											logger
													.debug("-----------------特价变更：修改上架单机精品分类--------");
											checkAndAddGameCategory(game, gameTacticMap);
										}
									}

								}
								if (game.getChargeTime().equals("1")
										&& game.getOnlineType() == 1
										&& game.getExpPrice() == 0
										&& !gameKey.getAppcateName().equals(
												game.getAppCateName()))
								{
									// 是单机精品并且不是 特价并且
									// 二级分类有变更：下架原来的分类货架，上架新的二级分类货架
									String cid = (String) gameTacticMap.get(gameKey
											.getAppcateName());
									if (cid != null && !cid.equals(""))
									{// 下架旧分类货架商品
										deleteRefrenceAndCategory(game.getId(), cid);
									}
									logger
											.debug("-----------------二级分类变更：上架单机精品新的二级分类--------");
									checkAndAddGameCategory(game, gameTacticMap);
								}
								if (!gameKey.getChargeTime().equals(game.getChargeTime()))
								{ // 单机 试玩有变更
									if (game.getExpPrice() == 0)
									{// 不是特价的,不是网游
										if (gameKey.getChargeTime().equals("2")
												&& game.getChargeTime().equals("1"))
										{
											// 原来是试玩，转成单机，并且不是特价的：要上架单机分类货架
											// 非特价 单机精品：单机精品分类货架上架
											logger
													.debug("-----------------应用 原来是试玩，转成单机，并且不是特价的：新增上架单机精品分类--------");
											checkAndAddGameCategory(game, gameTacticMap);
										}
										if (gameKey.getChargeTime().equals("1")
												&& game.getChargeTime().equals("2"))
										{
											// 原来是单机，转成试玩，并且不是特价的：要下架单机分类货架
											// 非特价 单机精品：单机精品分类货架下架

											String cid = (String) gameTacticMap
													.get(gameKey.getAppcateName());
											if (cid != null && !cid.equals(""))
											{// 下架分类货架商品
												logger
														.debug("-----------------应用 原来是单机，转成试玩，并且不是特价的：下架单机精品分类--------");
												deleteRefrenceAndCategory(game.getId(),
														cid);
											}
										}
									}

								}
								if (gameKey.getOnlineType() != game.getOnlineType())
								{// -单机 网游有变更
									if (game.getExpPrice() == 0)
									{// 不是特价的
										if (gameKey.getOnlineType() == 1
												&& game.getOnlineType() == 2)
										{
											// 原来的是离线单机应用，变为 网游：需要下架 原来的单机分类货架商品
											String cid = (String) gameTacticMap
													.get(gameKey.getAppcateName());
											if (cid != null && !cid.equals(""))
											{// 下架分类货架商品
												logger
														.debug("-----------------原来的是离线单机应用，变为 网游：需要下架 原来的单机分类货架商品--------");
												deleteRefrenceAndCategory(game.getId(),
														cid);
											}
										}
										if (gameKey.getOnlineType() == 2
												&& game.getOnlineType() == 1)
										{
											// 原来的是网游，变为 离线单机应用：需要上架单机分类货架商品
											logger
													.debug("-----------------原来的是网游，变为 离线单机应用：需要上架单机分类货架商品--------");
											checkAndAddGameCategory(game, gameTacticMap);
										}
									}
								}
								/*
								 * // 特价有变更
								 * logger.debug("特价有变更,gameKey.getExpPrice()="+gameKey.getExpPrice()+";game.getExpPrice()="+game.getExpPrice());
								 * if (gameKey.getExpPrice() == 0) { //
								 * 由普通商品变成特价商品，则下架原来的 单机精品/试玩；上架特价专区 if
								 * (game.getChargeTime().equals("1")) {
								 * if(game.getOnlineType()== 1){ // 下架单机精品分类 //
								 * 获取该应用原来上架的基地单机游戏二级分类货架ID String cid =
								 * (String) gameTacticMap.get(game
								 * .getAppCateName()); // 货架主键ID
								 * 
								 * Category category = (Category)
								 * Repository.getInstance().getNode(cid,
								 * RepositoryConstants.TYPE_CATEGORY);
								 * 
								 * if (cid != null && !cid.equals("")) {
								 * deleteRefrenceAndCategory(game.getId(), cid); }
								 * else { logger.error("cid is null "); } }else
								 * if(game.getOnlineType()== 2){ //网游
								 * if(netCategoryId != null &&!
								 * netCategoryId.equals("")){
								 * deleteRefrenceAndCategory(game.getId(),
								 * netCategoryId); } } // 上架 特价专区 if
								 * (expCategoryId != null &&
								 * !expCategoryId.equals("")) { //
								 * 将gc转化成应用内容获取应用分类属性 GAppContent app =
								 * (GAppContent) game; addCategory(app,
								 * expCategoryId); } else {
								 * logger.error("expCategoryId is null "); } }
								 * else if (game.getChargeTime().equals("2"))
								 * {// 下架试玩 if (trainCategoryId != null &&
								 * !trainCategoryId.equals("")) {
								 * deleteRefrenceAndCategory(game.getId(),
								 * trainCategoryId); } else {
								 * logger.error("trainCategoryId is null "); } //
								 * 上架 特价专区 if (expCategoryId != null &&
								 * !expCategoryId.equals("")) { //
								 * 将gc转化成应用内容获取应用分类属性 GAppContent app =
								 * (GAppContent) game; addCategory(app,
								 * expCategoryId); } else {
								 * logger.error("expCategoryId is null "); } } }
								 * else if (game.getExpPrice() == 0) { //
								 * 由特价商品变为普通商品；下架原来的特价专区，上架单机精品/试玩 // 下架 特价专区 if
								 * (expCategoryId != null &&
								 * !expCategoryId.equals("")) {
								 * deleteRefrenceAndCategory(game.getId(),
								 * trainCategoryId); } else {
								 * logger.error("expCategoryId is null "); } if
								 * (game.getChargeTime().equals("1")) {// 下载计费
								 * if( game.getOnlineType()== 1){ //
								 * 单机精品，上架单机精品分类
								 * logger.debug("-----------------上架单机精品分类--------");
								 * checkAndAddGameCategory(game, gameTacticMap);
								 * }else if(game.getOnlineType()== 2){ //网游专区
								 * if(netCategoryId != null &&
								 * !netCategoryId.equals("")){ //
								 * 将gc转化成应用内容获取应用分类属性 GAppContent app =
								 * (GAppContent) game; addCategory(app,
								 * netCategoryId); } // }else{
								 * logger.error("game.getOnlineType() is illage
								 * "+game.getOnlineType()); } } else if
								 * (game.getChargeTime().equals("2")) {// 上架试玩
								 * if (trainCategoryId != null &&
								 * !trainCategoryId.equals("")) { //
								 * 将gc转化成应用内容获取应用分类属性 GAppContent app =
								 * (GAppContent) game; addCategory(app,
								 * trainCategoryId); } else {
								 * logger.error("trainCategoryId is null "); } }
								 * else { logger.error("game.getChargeTime() is
								 * elegall " + game.getChargeTime()); } } else {
								 * logger.error("game.getExpPrice() is " +
								 * game.getExpPrice() + ";gameKey.getExpPrice() =" +
								 * gameKey.getExpPrice()); }
								 */}
							else
							{
								// 特价没有变更
								logger.debug("特价没有变更 ");
							}
						}
						else
						{
							logger.debug("gameTatictype 开关 没有打开 " + gameTatictype);
						}

						return DataSyncConstants.SUCCESS_UPDATE;

					}
					else
					{
						// value 为2表示已经更新或者新增过了，数据文件有重复元素
						logger.error("数据文件中出现重复游戏，忽略该数据。icpcode:" + gameKey.getIcpCode()
								+ ",icpservid=" + gameKey.getIcpServid());
						return DataSyncConstants.FAILURE;
					}

				}
			}
			else
			{
				if(deviceList == null || deviceList.size()<=0){
					throw new BOException("基地游戏适配机型为空，不入库；basegame  device is null");
				}
				// 不存在就插入
				if (logger.isDebugEnabled())
				{
					logger.debug("需要新增基地游戏:icpcode=" + gameKey.getIcpCode()
							+ ",icpservid=" + gameKey.getIcpServid());
				}
				// 第一次插入写入创建时间
				game.setCreateDate(PublicUtil.getCurDateTime());
				// 保存内容到数据库
				contentRoot.addNode(game);
				contentRoot.saveNode();
				game.setCateName(GameSyncTools.getInstance().getMMCateName(
						game.getAppCateName()));
				checkAndAddCategory(game, TACTICLIST);
				GameSyncTools.getInstance().saveGameService(servo);
				// oldGameMap.put(gameKey, "1");
				gameKey.setStatus(1);// 此时无需设置id，因为也用不到id
				oldGameMap.put(gameKey.getIcpServid(), gameKey);

				if (gameTatictype.equals("true"))
				{
					if (game.getExpPrice() <= 0)
					{
						// 非特价
						if (game.getChargeTime().equals("1") && game.getOnlineType() == 1)
						{
							// 非特价 单机精品：单机精品分类货架上架
							logger.debug("-----------------应用新增：新增上架单机精品分类--------");
							checkAndAddGameCategory(game, gameTacticMap);
						}
					}
					/*
					 * logger.debug("-----------------开始基地单机游戏分类上架--------"); //
					 * 内容同步策略 if (game.getExpPrice() > 0) {// 特价游戏，上架特价专区 // 上架
					 * 特价专区
					 * 
					 * if (expCategoryId != null && !expCategoryId.equals("")) {
					 * logger.debug("-----------------上架 特价专区--------"); //
					 * 将gc转化成应用内容获取应用分类属性 GAppContent app = (GAppContent) game;
					 * addCategory(app, expCategoryId); } else {
					 * logger.error("expCategoryId is null "); } } else { if
					 * (game.getChargeTime().equals("1")) {if(
					 * game.getOnlineType()== 1){ // 单机精品，上架单机精品分类
					 * logger.debug("-----------------上架单机精品分类--------");
					 * checkAndAddGameCategory(game, gameTacticMap); }else
					 * if(game.getOnlineType()== 2){ //网游专区 if(netCategoryId !=
					 * null && !netCategoryId.equals("")){ // 将gc转化成应用内容获取应用分类属性
					 * GAppContent app = (GAppContent) game; addCategory(app,
					 * netCategoryId); } }else{
					 * logger.error("game.getOnlineType() is illage
					 * "+game.getOnlineType()); }
					 * 
					 *  } else if (game.getChargeTime().equals("2")) {// 上架试玩 if
					 * (trainCategoryId != null && !trainCategoryId.equals("")) { //
					 * 将gc转化成应用内容获取应用分类属性 GAppContent app = (GAppContent) game;
					 * addCategory(app, trainCategoryId); } else {
					 * logger.error("trainCategoryId is null "); } }
					 *  }
					 * 
					 */}

				return DataSyncConstants.SUCCESS_ADD;
			}
		} catch (Exception e)
		{
			//出现处理异常，则记录该错误，防止最后删除的时候删除。
			if (oldGameMap.containsKey(gameKey.getIcpServid()))
			{
				gameKey = (BaseGameKeyVO) oldGameMap.get(gameKey.getIcpServid());
				gameKey.setStatus(5);//如果原有库中存在需要修改相应的状态。
			}
			else
			{
				gameKey.setStatus(5);
				oldGameMap.put(gameKey.getIcpServid(), gameKey);
			}
			logger.error("处理基地游戏出现异常。icpcode=" + gameKey.getIcpCode() + ",icpservid="
					+ gameKey.getIcpServid(), e);
			return DataSyncConstants.FAILURE;
		}
	}

	/**
	 * 设置图片的信息。把图片上传到资源服务器，然后把访问地址写入对应字段。
	 * @param game  游戏对象
	 * @param pictureRootPath 图片的存放地址。
	 * @throws BOException
	 */
	private void setGamePic(GAppGame game, String pictureRootPath)
			throws BOException
	{
		String icpservId=game.getIcpServId();
		FTPClient ftp=null;
		try
		{
			DataSyncTools.ungzip(pictureRootPath + ".tar.gz");
			// 设置图片规则
			File pictureRootPathFile=new File(pictureRootPath);
			String picNames[] =pictureRootPathFile.list();
			//定义logo图片地址
			String logoPicPath=null;
			List detailedPicPathList=new  ArrayList();//去除一个logo图片
			for(int i=0;i<picNames.length;i++)
			{
				String temp=pictureRootPath + File.separator+picNames[i];//保存在文件的绝对地址
				if(picNames[i].startsWith("logo"))
				{
					logoPicPath=temp;
				}else if(picNames[i].startsWith("pic"))
				{
					detailedPicPathList.add(temp);
				}
			}
			Collections.sort(detailedPicPathList);//默认按照字母升序排列
			//logoPicPath = pictureRootPath + File.separator + "logo.jpg";
			//该游戏图片的保存目录。
			String saveDir=this.getPicSaveDir(icpservId);
			ftp=this.getFtpWithSaveDir(saveDir);
			
			game.setLOGO1(this.getPicURL(logoPicPath, 30, 30, ftp,saveDir));
			game.setLOGO2(this.getPicURL(logoPicPath, 34, 34, ftp,saveDir));
			game.setLOGO3(this.getPicURL(logoPicPath, 50, 50, ftp,saveDir));
			game.setLOGO4(this.getPicURL(logoPicPath, 65, 65, ftp,saveDir));

			//WWW大广告图地址
			game.setWWWPropaPicture1(this.getPicURL(logoPicPath, 360, 274, ftp,saveDir));
			//WWW列表小图标地址
			game.setWWWPropaPicture2(this.getPicURL(logoPicPath, 32, 32, ftp,saveDir));
			//WWW标准展示图
			game.setWWWPropaPicture3(this.getPicURL(logoPicPath, 107, 143, ftp,saveDir));

			game.setClientPreviewPicture1(this
					.getPicURL(logoPicPath, 150, 160, ftp,saveDir));
			game.setClientPreviewPicture2(this
					.getPicURL(logoPicPath, 210, 220, ftp,saveDir));
			game.setClientPreviewPicture3(this
					.getPicURL(logoPicPath, 180, 180, ftp,saveDir));
			game.setClientPreviewPicture4(this
					.getPicURL(logoPicPath, 240, 300, ftp,saveDir));
			//生成详情图片
			for(int i=0;i<detailedPicPathList.size();i++)
			{
				String temp=(String)detailedPicPathList.get(i);
				switch(i)
				{
					case 0:game.setPicture1(this.getPicURL(temp, 240, 320, ftp,saveDir));break;
					case 1:game.setPicture2(this.getPicURL(temp, 240, 320, ftp,saveDir));break;
					case 2:game.setPicture3(this.getPicURL(temp, 240, 320, ftp,saveDir));break;
					case 3:game.setPicture4(this.getPicURL(temp, 240, 320, ftp,saveDir));break;
					case 4:game.setPicture5(this.getPicURL(temp, 240, 320, ftp,saveDir));break;
					case 5:game.setPicture6(this.getPicURL(temp, 240, 320, ftp,saveDir));break;
					case 6:game.setPicture7(this.getPicURL(temp, 240, 320, ftp,saveDir));break;
					case 7:game.setPicture8(this.getPicURL(temp, 240, 320, ftp,saveDir));break;
					default:break;
				}
				
			}
			ftp.quit();
		} catch (Exception e)
		{
			if(ftp!=null)
			{
				try
				{
					ftp.quit();
				} catch (Exception e1)
				{
					logger.error(e1);
				}
			}
			throw new BOException("转化图片出错，icpservId="+icpservId,e);
		}
	}

	/**
	 * 下架指定货架下指定商品
	 * @param refid
	 * @param categoryid
	 */
	public void deleteRefrenceAndCategory(String  refid,String cid){
		Searchor searchor2 = new Searchor();
		// 需要递归查询
		
		try
		{
			Category category = (Category) Repository.getInstance().getNode(cid,
					RepositoryConstants.TYPE_CATEGORY);
			searchor2.setIsRecursive(true);
			searchor2.getParams().add(
					new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, refid));
			searchor2.getParams().add(
					new SearchParam("categoryID", RepositoryConstants.OP_EQUAL, category.getCategoryID()));
			
			List refList = categoryRoot.searchNodes(
					RepositoryConstants.TYPE_REFERENCE, searchor2, null);
			// 将商品全部下架
			for (int j = 0; j< refList.size(); j++)
			{
				ReferenceNode ref = (ReferenceNode) refList.get(j);
				categoryRoot.delNode(ref);
			}
			// 一次保存操作
			categoryRoot.saveNode();
		}catch (Exception e)
		{
			logger.error("下架基地游戏出错，gameId=" + refid, e);
		}
		
			
	}
	/**
	 * 下架指定货架下指定商品
	 * @param refid
	 * @param categoryid
	 */
	public void deleteRefrenceAllCategory(String  refid){
		Searchor searchor2 = new Searchor();
		// 需要递归查询
		
		try
		{
			
			searchor2.setIsRecursive(true);
			searchor2.getParams().add(
					new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, refid));
			
			
			List refList = categoryRoot.searchNodes(
					RepositoryConstants.TYPE_REFERENCE, searchor2, null);
			// 将商品全部下架
			for (int j = 0; j< refList.size(); j++)
			{
				ReferenceNode ref = (ReferenceNode) refList.get(j);
				categoryRoot.delNode(ref);
			}
			// 一次保存操作
			categoryRoot.saveNode();
		}catch (Exception e)
		{
			logger.error("下架基地游戏出错，gameId=" + refid, e);
		}
		
			
	}
	
	/**
	 * 删除所有下线的游戏但需要去除检查不通过的游戏。
	 * 需要把全量数据导入后执行。
	 * @param List failureChecked 检查失败的游戏列表。
	 * @return int[] 数据第一值表示成功删除的个数，第二个删除失败的个数。
	 */
	public int[] deleteOldGame(List failureChecked)
	{
		int result[]= {0,0};//定义返回结果。数据第一值表示成功删除的个数，第二个删除失败的个数。
		List delList = new ArrayList();//待删除的游戏id列表。
		for(int i=0;i<failureChecked.size();i++)//检查没有通过的游戏，禁止删除。
		{
			oldGameMap.put(failureChecked.get(i), failureChecked.get(i));
		}
		
		Set ts =	oldGameMap.entrySet();
		Iterator iterator = ts.iterator();
		while (iterator.hasNext())
		{
			Entry ey = (Entry) iterator.next();
			
			//BaseGameKeyVO vo = (BaseGameKeyVO)iterator.next();
			BaseGameKeyVO vo = (BaseGameKeyVO)ey.getValue();
			// 只有value为0才表示没有别更新，新增。
			if (vo.getStatus()==0)
			{
				delList.add(vo.getId());
			}
		}
		// 成功删除的计数器
		int count = 0;
		for (int i = 0; i < delList.size(); i++)
		{
			String gameId = (String) delList.get(i);
			Searchor searchor2 = new Searchor();
			// 需要递归查询
			searchor2.setIsRecursive(true);
			searchor2.getParams().add(
					new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, gameId));
			try
			{
				List refList = categoryRoot.searchNodes(
						RepositoryConstants.TYPE_REFERENCE, searchor2, null);
				// 将商品全部下架
				for (int j = 0; j< refList.size(); j++)
				{
					ReferenceNode ref = (ReferenceNode) refList.get(j);
					categoryRoot.delNode(ref);
				}
				// 一次保存操作
				categoryRoot.saveNode();

				GAppGame game = (GAppGame) Repository.getInstance().getNode(gameId,
						RepositoryConstants.TYPE_APPGAME);

				String contentid = game.getContentID();
				
				// 调用资源管理的删除内容接口将该内容删除
				contentRoot.delNode(game);
				contentRoot.saveNode();

				GameSyncDAO.getInstance().delGameService(game.getIcpCode(),
						game.getIcpServId());

				// 需要删除资源服务器上图片信息。
				deleteRemotePic(game.getIcpServId());
				if (logger.isDebugEnabled())
				{
					logger.debug("删除此游戏成功，gameId=" + gameId);
				}
                
                try
                {
                    DataSyncDAO.getInstance().addContentIdHis(contentid, SyncDataConstant.DEL_CONTENT_TYPE_GAME);
                }
                catch (DAOException e)
                {
                    throw new BOException("内容下架时，新增下线应用记录表出错！", e);
                }
                
				count++;
			} catch (Exception e)
			{
				logger.error("删除游戏出错，gameId=" + gameId, e);
			}
			

		}
		result[0]=count;
		result[1]=delList.size()-count;
		return result;
	}

	/**
	 * 匹配内容上架策略。只是匹配策略中的两个属性。1.内容类型，2 分类名称
	 * 
	 * @param contentType
	 * @param gc
	 */
	private void checkAndAddCategory(GContent gc, List tacticList) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataSyncBO.checkAndAddCategory(). contentType="+gc.getContentID()+"--------start-----------" + gc.getType());
		}
		TacticVO vo;
		int size = tacticList.size();
		String temp;

		String categoryID;
		// 用内容去匹配每一个策略
		for (int i = 0; i < size; i++)
		{
			vo = (TacticVO) tacticList.get(i);
			categoryID = vo.getCategoryID();

			temp = vo.getContentType();

			// 第一步：检查内容类型是否匹配
			if (!(TacticConstants.CONTENT_TYPE_ALL.equals(temp) || gc.getType().equals(
					temp)))
			{
				// 类型不匹配,检查下一个策略
				continue;
			}
			// 第二步：检查应用分类名称是否匹配
			if (gc instanceof GAppContent)
			{
				// 将gc转化成应用内容获取应用分类属性
				GAppContent app = (GAppContent) gc;
				// 判断应用分类属性是否与同步策略中的应用分类名称匹配
				if (!app.getAppCateName().equals(vo.getAppCateName()))
				{
					continue;
				}
			}

			// 第三步：所有检查通过，将内容上架到策略对应的货架货架下
			Category pCate = (Category) cateCache.get(categoryID);
			if (pCate == null)// 不存在需要保存到缓存中
			{
				pCate = (Category) Repository.getInstance().getNode(categoryID,
						RepositoryConstants.TYPE_CATEGORY);
				if (pCate == null)
				{
					logger.error("目前没有该分类，无法完成上架。id=" + categoryID);
					throw new BOException("不存在当前货架，id=" + categoryID);
				}
				cateCache.put(pCate.getId(), pCate);

			}
			addCategory(gc, categoryID);
			logger.debug("DataSyncBO.checkAndAddCategory(). contentType="+gc.getContentID()+"-------end-----------" + gc.getType());
		}
	}

	/**
	 * 基地单机游戏 匹配内容上架策略。只是匹配策略中的两个属性。1.内容类型，2 分类名称
	 * 
	 * @param contentType
	 * @param gc
	 */
	private void checkAndAddGameCategory(GAppGame game, Map tacticmap) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("DataSyncBO.checkAndAddGameCategory(). contentType="+game.getContentID() + "+++++++++++++=="
					+ game.getType());
		}

		String categoryID = (String) tacticmap.get(game.getAppCateName());
		// 将gc转化成应用内容获取应用分类属性
		GAppContent app = (GAppContent) game;

		// 第三步：所有检查通过，将内容上架到策略对应的货架货架下
		Category pCate = (Category) cateCache.get(categoryID);
		if (pCate == null)// 不存在需要保存到缓存中
		{
			pCate = (Category) Repository.getInstance().getNode(categoryID,
					RepositoryConstants.TYPE_CATEGORY);
			if (pCate == null)
			{
				logger.error("目前没有该分类，无法完成上架。id=" + categoryID);
				throw new BOException("不存在当前货架，id=" + categoryID);
			}
			cateCache.put(pCate.getId(), pCate);

		}
		addCategory(app, categoryID);

	}
	/**
	 * 将内容上架到货架货架下
	 * 
	 * @param gc
	 * @param categoryID
	 */
	private void addCategory(GContent gc, String categoryID) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("=====addCategory: categoryID=" + categoryID + " contentID="
					+ gc.getContentID());
		}
		try
		{
			Category category = (Category) Repository.getInstance().getNode(categoryID,
					RepositoryConstants.TYPE_CATEGORY);

			// 构造商品编码＝货架编码＋企业内码(6位)＋业务内码(12位)＋内容编码(12位)
			String goodsID = category.getCategoryID()
					+ PublicUtil.lPad(gc.getCompanyID(), 6)
					+ PublicUtil.lPad(gc.getProductID(), 12)
					+ PublicUtil.lPad(gc.getContentID(), 12);
			// 放到目标分类中

			// 分配一个新的资源id
			String newNodeID = category.getNewAllocateID();

			// 创建一个引用节点，并设置所需属性
			ReferenceNode ref = new ReferenceNode();
			ref.setId(newNodeID);
			ref.setParentID(category.getId());
			ref.setPath(category.getPath() + ".{" + newNodeID + "}");
			ref.setRefNodeID(gc.getId());
			ref.setSortID(0);
			ref.setGoodsID(goodsID);
			ref.setCategoryID(category.getCategoryID());
			ref.setVariation(0);

			// 创建商品信息VO类，并设置所需属性
			GoodsVO goodsVO = new GoodsVO();
			goodsVO.setGoodsID(goodsID);
			goodsVO.setIcpCode(gc.getIcpCode());
			goodsVO.setIcpServId(gc.getIcpServId());
			goodsVO.setContentID(gc.getContentID());
			goodsVO.setCategoryID(category.getCategoryID());
			goodsVO.setGoodsName(gc.getName());
			goodsVO.setState(1);
			goodsVO.setChangeDate(new Date());
			goodsVO.setActionType(1);

			// 调用GoodsBO中的addNodeAndInsertGoodsInfor保存节点并储存商品信息
			GoodsBO.addNodeAndInsertGoodsInfo(ref, goodsVO);
		} catch (Exception e)
		{
			throw new BOException("内容上架出错！", e);
		}
	}

	public void init(DataSyncConfig config) throws Exception
	{
		try
		{
			contentRoot = (Category) Repository.getInstance().getNode(
					RepositoryConstants.ROOT_CONTENT_ID,
					RepositoryConstants.TYPE_CATEGORY);
			/*
			 * gameRoot = (Category) Repository.getInstance().getNode(
			 * RepositoryConstants.ROOT_CATEGORY_GAPPGAME_ID,
			 * RepositoryConstants.TYPE_CATEGORY);
			 */
			categoryRoot = (Category) Repository.getInstance().getNode(
					RepositoryConstants.ROOT_CATEGORY_ID,
					RepositoryConstants.TYPE_CATEGORY);
		} catch (BOException e)
		{
			logger.error(e);
		}
		this.fileEncoding = config.get("task.file-encoding");
		this.gameFileRootPath = config.get("ftp.localDir") + File.separator + "temp";

	}

	/**
	 * 把图片按照一定的格式上传到ftp上，并且返回获取该图片的url地址
	 * 
	 * @param srcPath
	 *            原图片绝对地址
	 * @param toWidth 目标图片的宽度
	 * @param toHeight 目标图片的高度
	 * @param FTPClient 资源服务器对象
	 * @param saveDir 图片保存目录
	 * @return 该图片在资源服务器上访问地址
	 * @throws BOException 保存失败
	 */
	private String getPicURL(String srcPath, int toWidth, int toHeight, FTPClient ftp,String saveDir)
			throws Exception
	{
		String toFormat="png";//转换的目标图片格式
		int lastSlashIndex = srcPath.lastIndexOf(File.separator);
		String srcDir = srcPath.substring(0, lastSlashIndex + 1);// 源文件的目录
		String disFileName = srcDir + toWidth + "_" + toHeight+
				  srcPath.substring(lastSlashIndex + 1,srcPath.length()-3)+toFormat;
		try
		{
			DataSyncTools.scaleIMG(srcPath, disFileName, toWidth, toHeight,toFormat);
		} catch (Exception e)
		{
			throw new BOException("转化图片出错。srcPath=" + srcPath + ",toWidth=" + toWidth
					+ ",toHeight=" + toHeight);
		}
		return uploadPic(disFileName,ftp,saveDir);
	}
	/**
	 * 上传指定的图片到ftp服务器
	 * @param ftp 资源服务器
	 * @param saveDir 图片保存目录。
	 * @return 获取访问该上传的文件的URL访问地址
	 * @throws FTPException 
	 * @throws IOException 
	 */
	private String uploadPic(String picPath,FTPClient ftp,String saveDir) throws IOException, FTPException
	{
		String saveFileName = getRemoteFileName(picPath);
		ftp.put(picPath, saveFileName, false);
		StringBuffer visitedUrl = new StringBuffer(GameConfig.sourceServerVisit);
		// 构造访问该图片的url
		visitedUrl.append(saveDir);
		visitedUrl.append('/');
		visitedUrl.append(saveFileName);
		return visitedUrl.toString();
	}

	/**
	 * 
	 * @param saveDir 该游戏图片的保存目录
	 * @return  返回进入该目录后的ftp对象
	 * @throws IOException
	 * @throws FTPException
	 */
	private FTPClient getFtpWithSaveDir(String saveDir) throws IOException, FTPException
	{
		FTPClient ftp=GameSyncTools.getInstance().getResourceServerFtp();
		String tmp[]=saveDir.split("/");
		for(int i=0;i<tmp.length;i++)
		{
			checkAndCreateDir(ftp,tmp[i]);
			ftp.chdir(tmp[i]);
		}
		return ftp;
	}
	/**
	 * 获取该图片存储目录，图片存放规则为：$ftpRoot/xx/icpservId/*.jpg
	 * 其中xx值等于icpservId%10000的值。（主要为了防止一个文件夹目录过大）
	 * @param icpservId
	 * @return 图片的存放目录
	 */
	private String getPicSaveDir(String icpservId)
	{
		String starDir = "00";
		try
		{
			starDir = String.valueOf(Long.parseLong(icpservId) / 10000);
		} catch (NumberFormatException e)
		{
			// 转换失败默认目录名为00
		}
		return starDir+"/"+icpservId;
	}
	/**
	 * 删除指定游戏的图片。
	 * 
	 * @param icpservId
	 *            游戏的业务代码
	 * @throws Exception
	 *             删除失败，或者不存在该游戏的业务代码的目录
	 */
	public void deleteRemotePic(String icpservId)
	{
		//先不要执行删除操作，因为涉及同步的问题。删除后到第二天同步这段时间，门户展示会有问题
		/*FTPClient ftp=null;
		try
		{
			ftp = GameSyncTools.getInstance().getResourceServerFtp();
			String starDir = "00";
			try
			{
				starDir = String.valueOf(Long.parseLong(icpservId) / 10000);
			} catch (NumberFormatException e)
			{
				// 转换失败默认目录名为00
			}

			ftp.chdir(starDir);
			ftp.chdir(icpservId);
			// 删除所有的图片文件
			String files[] = ftp.dir();
			for (int i = 0; i < files.length; i++)
			{
				ftp.delete(files[i]);
			}
			// 删除该游戏目录
			ftp.chdir("..");// 首先需要返回父一级目录。
			ftp.rmdir(icpservId);
			ftp.quit();
		} catch (Exception e)
		{
			if (ftp != null)
			{
				try
				{
					ftp.quit();
				} catch (Exception e1)
				{
				}
			}
			logger.error("删除图片目录出错。icpservId=" + icpservId, e);
			//该异常不必抛出。
			//throw new BOException("删除图片目录出错。icpservId=" + icpservId, e);
		}*/
	}

	/**
	 * 检查当前路径下是否含有指定的目录。如果没有则创建。该方法需要同步，防止多线程出现异常。
	 * 
	 * @param ftp
	 *            FTPClient
	 * @param dir
	 *            目录名称
	 * @throws IOException
	 * @throws FTPException
	 */
	synchronized private void checkAndCreateDir(FTPClient ftp, String dir)
			throws IOException, FTPException
	{
		/*if (ftp.dir(dir).length == 0)// 使用该方法发现会有问题。所以使用下面最原始的办法。
		{
			ftp.mkdir(dir);
		}*/
		String subFiles[] = ftp.dir();
		boolean isFound = false;//该目录是否存在dir目录
		for (int i = 0; i < subFiles.length; i++)
		{
			if (subFiles[i].equals(dir))
			{
				isFound = true;
				break;
			}
		}
		if (!isFound)//不存在该目录，就创建。
		{
			ftp.mkdir(dir);
		}
	}

	/**
	 * 根据本地文件的绝对路径，获取远程服务器上保存的文件名
	 * 
	 * @param localFileName
	 *            本地文件的绝对路径
	 * @return ftp服务器上保存的文件名
	 */
	private String getRemoteFileName(String localFileName)
	{
		return localFileName.substring(localFileName.lastIndexOf(File.separator) + 1);
	}

	private List getDeviceNames(String fileName, String icpservId) throws BOException
	{
        BufferedReader reader = null;
        List deviceList = new ArrayList();
        try
        {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),
                                                              this.fileEncoding));
            String lineText;
            // 连接deviceName
            // 用于保存无法再mm平台找到机型信息。
            StringBuffer illegalDeviceName = new StringBuffer();
            while ((lineText = reader.readLine()) != null)
            {
                lineText = lineText.trim();
                String deviceName = GameSyncTools.getInstance()
                                                 .getMMUAMapping(lineText);
                if (deviceName != null)
                {
                    deviceList.add(deviceName);
                }
                else
                {
                    illegalDeviceName.append(lineText);// 保存非法的ua信息。
                    illegalDeviceName.append(',');
                }
            }
            // 删除最后一个逗号
            if (illegalDeviceName.length() > 1)
            {
                illegalDeviceName.deleteCharAt(illegalDeviceName.length() - 1);
                logger.error("业务代码为：" + icpservId + "的游戏，含有以下MM无法识别的	UA信息："
                             + illegalDeviceName);
                logger.error("请在货架系统t_game_ua_mapping中新增上述无法识别的UA信息");
            }

        }
        catch (Exception e)
        {
            throw new BOException("读取ua文件信息信息出错。icpservId=" + icpservId, e);
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    //logger.error(e);
                }
            }
        }

        return deviceList;
    }

	/**
	 * 转化游戏信息到 游戏类和游戏业务类。
	 * 
	 * @param record
	 *            一条游戏信息
	 * @param servo
	 *            游戏业务信息
	 * @param game
	 *            游戏内容信息
	 */
	public void turnFieldsToGameInfo(DataRecord record, GameServiceVO servo, GAppGame game)
			throws BOException
	{
		String temp;
		// 1 cpId
		servo.setIcpCode((String) record.get(1));
		game.setIcpCode(servo.getIcpCode());
		// 2 cpName
		servo.setSpName((String) record.get(2));
		game.setSpName(servo.getSpName());
		// 3 cpServiceid
		servo.setIcpServid((String) record.get(3));
		game.setIcpServId(servo.getIcpServid());
		// 保存该游戏的业务代码
		servo.getIcpServid();
		// 4 serviceName
		servo.setServName((String) record.get(4));
		game.setName(servo.getServName());
		// 5 产品简称
		servo.setServDesc((String) record.get(5));
		// 6 serviceDesc
		game.setIntroduction((String) record.get(6));
		// 7 操作简介
		temp = (String) record.get(7);
		if ("".equals(temp.trim()))
		{
			temp = "该游戏为百宝箱精品游戏，下载游戏，体验前所未有的畅快吧！";
		}
		game.setHandBook(temp);
		// 8 业务类型
		servo.setServType(Integer.parseInt((String) record.get(8)));
		// 9 支付方式.目前只支持话费的形式。
		temp = (String) record.get(9);
		if (temp.equals("2"))
		{
			game.setIsSupportDotcard("0");
		}
		else if (temp.equals("1"))
		{
			game.setIsSupportDotcard("1");
		}
		;
		// 10产品生效时间。 时间格式为yyyy-MM-dd
		temp = (String) record.get(10);
		game.setMarketDate(temp.trim() + " 00:00:00");
		// 10产品生效时间。目前没有使用该字段
		// 12 业务状态
		// 13 资费
		servo.setMobilePrice(Integer.parseInt((String) record.get(13)) * 10);
		// 15 产品logo
		// vo.setServiceLogoId((String)record.get(15));
		// 16 入口URL（下载url）
		temp=(String) record.get(16);  //把最后的channelId=xxxxx换成channelId=12044000
		int channelIdIndex=temp.indexOf("&channelId=");
		if(channelIdIndex!=-1)//不含有channelId参数则不处理。
		{
			int isLast=temp.indexOf('&', channelIdIndex+1);
			if(isLast!=-1)//判断channelId是否是最后一个参数
			{
				temp=temp.substring(0,channelIdIndex)+"&channelId=12044000"+temp.substring(isLast);
				
			}else
			{
				temp=temp.substring(0,channelIdIndex)+"&channelId=12044000";
			}
		}
		game.setGameVideo(temp);
		// 17 支持的终端类型
		// vo.setServiceUA((String)record.get(17));
		// 18 计费方式
		temp = (String) record.get(18);
		if (temp.equals("2"))//先体验后付费
		{
			game.setChargeTime("2");
			servo.setChargeType("02");
			game.setOnlineType(1);	//离线应用
		}
		else if (temp.equals("5"))//下载即付费单机精品
		{
			game.setChargeTime("1");
			servo.setChargeType("02");
			game.setOnlineType(1);	//离线应用
		}else if (temp.equals("3"))//基地游戏在线网游
		{
			game.setChargeTime("1");
			servo.setChargeType("02");
			game.setOnlineType(2);	//在线应用
		}
		else
		{
			throw new BOException("不支持此计费方式异常。计费方式=" + temp);
		}
		
		// 14 资费描述  对于先体验后计费的应用，资费描述需要自己拼写
		//servo.setChargDesc((String) record.get(14));
		if(game.getChargeTime().equals("2"))
		{
			String desc="下载计费0元/次，购买计费"+servo.getMobilePrice()/1000.00+"元/次";
			servo.setChargDesc(desc);//重新拼装。
		}else
		{
			servo.setChargDesc((String) record.get(14));
		}
		
		//add by dongke  特价资费
		game.setExpPrice(Integer.parseInt((String) record.get(24)) * 10);
		
		// 19 gametype
		// 20 游戏分类名称
		game.setAppCateName(GameSyncTools.getInstance().getMMCateName(
				(String) record.get(20)));
		// 21 业务标识
		servo.setServFlag(Integer.parseInt((String) record.get(21)));
		// 22 业务推广方式
		servo.setPtypeId(Integer.parseInt((String) record.get(22)));
		// 23游戏下载量
		// 另外的信息。

		//设置游戏大类。
		game.setCateName("基地游戏");
        game.setLupdDate((String) record.get(25) + " 00:00:00");
        game.setProgramID((String) record.get(26));
        game.setProgramSize(Integer.parseInt((String) record.get(27)));
        
        if(record.size() > 28)
        {
            game.setContestYear((String) record.get(28));
        }
//        else
//        {
//            game.setLupdDate(PublicUtil.getCurDateTime());
//        }
		game.setPlupdDate(PublicUtil.getCurDateTime());
		game.setProvider("B");
		game.setLanguage("1");//设置简体中文。
		game.setKeywords('{' + servo.getServDesc() + '}');//设置关键字
		game.setServAttr("G");
		game.setPvcID("0000");
		game.setCityID("0000");

		//构造商品编码需要的字段。
		game.setCompanyID(game.getIcpCode());
		game.setProductID(game.getIcpServId());
		game.setContentID(game.getIcpServId());
		servo.setContentid(game.getContentID());
	}

	/**
	 * 获取游戏文件跟目录的路径。
	 * 
	 * @param filePathRoot
	 */
	public void setGameFileRootPath(String gameFileRootPath)
	{
		this.gameFileRootPath = gameFileRootPath;
	}

	/**
	 * 设置该游戏的支持终端名称，和终端品牌
	 * @param deviceNameList 所有支持终端名称列表
	 * @param game 游戏对象
	 */
	private void setDeviceNameAndBrand(List deviceNameList, GAppGame game)
	{
        // 获取当前系统支持的机型名称，和品牌
        List supportedDeviceNameList = new ArrayList();
        List supportedDeviceIDList = new ArrayList();

        HashMap brandMap = new HashMap();// 需要去重。
        HashMap deviceMap = new HashMap();// 机型需要去重
        // 用于保存无法再mm平台找到机型信息。
        StringBuffer illegalDeviceName = new StringBuffer();

        for (int i = 0; i < deviceNameList.size(); i++)
        {
            String deviceName = ( String ) deviceNameList.get(i);
            String brand = ( String ) deviceNameMap.get(deviceName);
            if (brand != null)
            {
                brandMap.put(brand, null);
                // supportedDeviceNameList.add(deviceName);
                deviceMap.put(deviceName, null);
            }
            else
            {
                illegalDeviceName.append(',');
                illegalDeviceName.append(deviceName);
            }
        }
        if (illegalDeviceName.length() > 0)
        {
            logger.error("当前MM终端库的devicename不存在。deviceName="
                         + illegalDeviceName.substring(1));
        }
        Set ts = deviceMap.entrySet();
        Iterator it = ts.iterator();
        while (it.hasNext())
        {
            Entry ey = ( Entry ) it.next();
            supportedDeviceNameList.add(ey.getKey());
            supportedDeviceIDList.add(GameSyncTools.getInstance()
                                                   .getMMDeviceMapping(String.valueOf(ey.getKey())));
        }
        game.setFulldeviceID(list2String(supportedDeviceIDList));
        game.setDeviceName(list2String(supportedDeviceNameList));
        game.setBrand(list2String(brandMap.keySet()));
        String fulldevice = PublicUtil.filterMbrandEspecialChar(list2String(supportedDeviceNameList));
        game.setFulldeviceName(fulldevice);//add 20101108
        //game.setFulldeviceName(list2String(supportedDeviceNameList));//add 20101108
    }

	/**
	 * 将list中的数据拼装为 {devicename},{devicename}… 的样式
	 * @param list 保存deviceName的集合
	 * @return 形如：{devicename1},{devicename2}… 的字符串
	 */
	private String list2String(Collection collection)
	{
		StringBuffer sb = new StringBuffer();
		Iterator iterator = collection.iterator();
		boolean dotFlag = false;//第一次不需要插入逗号
		while (iterator.hasNext())
		{
			if (dotFlag)
			{
				sb.append(",");
			}
			else
			{
				dotFlag = true;
			}
			sb.append("{");
			sb.append(iterator.next());
			sb.append("}");
		}
		return sb.toString();
	}
}
