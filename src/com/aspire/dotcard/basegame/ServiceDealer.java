package com.aspire.dotcard.basegame;

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
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GAppContent;
import com.aspire.dotcard.gcontent.GAppGame;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.dotcard.syncData.tactic.TacticBO;
import com.aspire.dotcard.syncData.tactic.TacticConstants;
import com.aspire.dotcard.syncData.tactic.TacticVO;
import com.aspire.dotcard.syncData.util.SyncDataConstant;
import com.aspire.ponaadmin.web.baserecomm.basegame.BlackBO;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;
import com.aspire.ponaadmin.web.datasync.implement.game.GameServiceVO;
import com.aspire.ponaadmin.web.datasync.implement.game.GameSyncTools;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;
import com.aspire.ponaadmin.web.repository.goods.GoodsVO;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;


public class ServiceDealer implements DataDealer
{
	private JLogger LOG=LoggerFactory.getLogger(ServiceDealer.class);
	private Map oldKeyIds;
	private Set twGameKeys;
	private Set pkgGameRefKeys;

	// 资源内容存储分类的根节点
	private static Category contentRoot;// 用于缓存
	// 用于缓存,货架的根分类701节点
	private static Category categoryRoot;
	// 上架过游戏的货架缓存，需要时线程安全的。
	private static Map cateCache = Collections.synchronizedMap(new HashMap());
	private static List TACTICLIST;
	private static List TACTICLIST_Android;//add by aiyan 为了商品库优化，这里是要把android应用上架。
	private Set androidContentSet;//add by aiyan 为了商品库优化，当一个内容存在android的虚拟机型，则可以上架。

	public void clearDirtyData()
	{
		oldKeyIds=null;//内容表t_r_gcontent里面的游戏主键集合，新增修改，依靠这个集合了。
		twGameKeys = null;//图文表的记录是新增还是修改，依靠这个集合了。
		pkgGameRefKeys = null;//游戏包商品表的记录是新增还是修改，依靠这个集合了。
		
		TACTICLIST = null;
		TACTICLIST_Android = null;
		androidContentSet = null;
		cateCache.clear();
		BlackBO.getInstance().delReference();

	}
	
	/**
	 * 在处理单条记录的时候按照业务所协带的内容实体的内容类型处理
	 * 1，单个游戏；
	 * 2，图文游戏 
	 * 3，游戏包（包含多个游戏）
	 * @throws BOException 
	 */
 	public int dealDataRecrod(DataRecord record){
		try {
			GameService service = new GameService();
			turnFields(record,service);
			GameContent content = GameSyncDAO.getInstance().getGameContentById(service.getContentCode());

			if(content==null){
				LOG.error("内容为空,处理游戏ID:"+service.getContentCode()+"跳过这个业务："+service.getServiceCode());
				return DataSyncConstants.FAILURE;
			}
			if ("1".equals(content.getContentType())) {// 普通
				int ret=  dealSingleGame(service, content);
				if (!"".equals(service.getPkgId())&&ret!=DataSyncConstants.FAILURE) {
					dealPkgGameRef(service.getPkgId(), service.getServiceCode());
				}
				return ret;

			} else if ("2".equals(content.getContentType())) {// 图文
				return dealTwGame(service, content);

			}
			//removed by aiyan for gamepkg handing in DataSyncTaskForContent,not here. 2012-10-13
//			else if ("3".equals(content.getContentType())) {// 游戏包 
//				return dealPkgGame(service, content);
//			} 
			
			else {
				throw new BOException("基地给的内容类型不正确,内容类型："
						+ content.getGameTypeId());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("处理游戏业务时出错！",e);
			return DataSyncConstants.FAILURE;
			
		}
		
		
		
	}
	
	public int dealTwGame(GameService service,GameContent content){
		try{
			
			HashMap gameTypeMap = GameSyncTools.getInstance().getGameTypeMap();
			GameTypeEntity entity = (GameTypeEntity)gameTypeMap.get(content.getGameTypeId());
			
			//add by aiyan 2012-10-19 当导入的游戏分类没有在T_GAME_TYPE配MM二级分类的话，就不入了。
//			if(entity==null||entity.getMmid()==null||entity.getMmname()==null){
//				LOG.error("t_game_type表中不存在游戏分类标识为:"+content.getGameTypeId()+"  no_game_type hehe ...");
//				throw new BOException("t_game_type表中不存在游戏分类标识为:"+content.getGameTypeId()+"  no_game_type hehe ...");
//			}
			
			if(entity==null||entity.getMmname()==null||entity.getMmid()==null)
			{entity.setMmname("其他游戏");
			//entity.setMmid("37");
			entity.setName( content.getName());
			}//游戏分类标识	  	


			
			
			//图文表的主键就是业务代码，当这个业务代码集合含有当前记录就修改，否则新增。
			if(twGameKeys.contains(service.getServiceCode())){
				GameSyncDAO.getInstance().updateTWGame(service,content,entity);
				twGameKeys.remove(service.getServiceCode());
				return DataSyncConstants.SUCCESS_UPDATE;
			}else{
				GameSyncDAO.getInstance().insertTWGame(service,content,entity);
				return DataSyncConstants.SUCCESS_ADD;
			}
			
		}catch(Exception e){
			LOG.error("处理图文游戏出错！",e);
			return DataSyncConstants.FAILURE;
		}
		
		
	}
	
	private int dealPkgGameRef(String pkgId, String serviceCode) {
		try {
			// 添加T_GAME_PKG_ref表
			if (!pkgGameRefKeys.contains(pkgId + "," + serviceCode)) {
				GameSyncDAO.getInstance().insertPkgGameRef(pkgId, serviceCode);
				pkgGameRefKeys.remove(pkgId + "," + serviceCode);//为删除做的准备工作
				return DataSyncConstants.SUCCESS_ADD;
			} else {
				pkgGameRefKeys.remove(pkgId + "," + serviceCode);
				return DataSyncConstants.SUCCESS_UPDATE;
			}
		} catch (Exception e) {
			LOG.error("添加T_GAME_PKG_ref表出错！",e);
			return DataSyncConstants.FAILURE;
		}

	}

	public int dealSingleGame(GameService service,GameContent content)
	{
		LOG.info("开始新游戏基地单个游戏入库。");
		try{
			GameServiceVO servo = new GameServiceVO();
			GAppGame game = new GAppGame();
			game.setSubType("1");
			game.setContentTag(content.getContentCode());
			
			
			setGameAndServo(game,servo,content,service);
			
			if (oldKeyIds.keySet().contains(service.getServiceCode()))
			{
				if (game.getFulldeviceID() == null || game.getFulldeviceID().trim().length() <= 0)
				{
					LOG.error("基地游戏适配机型为空，不入库；basegame  device is null"
							+ service.getServiceCode());
					throw new BOException("基地游戏适配机型为空，不入库；basegame  device is null"
							+ service.getServiceCode());
				}
				
				else
				{
					//单机游戏(普通上下架),是否过期(暂未考虑)
					game.setId(String.valueOf(oldKeyIds.get(service.getServiceCode())));
					// 新增业务信息
					GameSyncTools.getInstance().saveGameService2(servo);
					game.save();
					//商品库优化的 add by aiyan 2013-04-22 
					if(androidContentSet.contains(game.getContentTag())){
						PPMSDAO.addMessagesStatic(MSGType.ContentModifyReq,null,game.getContentID()+":1");//应用信息更新
						//t_a_cm_device_resource
						setDeviceResource(game.getContentID(),game.getContentTag());
						//removed by aiyan 2013-06-01 终端门户以及对适配的处理又把他当做应用变更处理。这样就重复做事情了，故丢掉。
						//PPMSDAO.addMessagesStatic(MSGType.ContentModifyReq,null,game.getContentID()+":2");//应用适配更新
						
						
						setFulldevice(game.getContentID(),game.getContentTag()); // add by aiyan 2013-07-02
					}
					
					oldKeyIds.remove(service.getServiceCode());
					return DataSyncConstants.SUCCESS_UPDATE;
					
				}
			}else{
				if(game.getFulldeviceID() == null || game.getFulldeviceID().trim().length() <= 0)
				{
					LOG.error("基地游戏适配机型为空，不入库；basegame  device is null"
							+ service.getServiceCode());
					throw new BOException("基地游戏适配机型为空，不入库；basegame  device is null"
							+ service.getServiceCode());
				}
				
				GameSyncTools.getInstance().saveGameService2(servo);
				// 第一次插入写入创建时间
				game.setCreateDate(PublicUtil.getCurDateTime());
				// 保存内容到数据库
				contentRoot.addNode(game);
				contentRoot.saveNode();
				//LOG.debug("AIYAN...HEHE..2013");
				if("-1".equals(game.getProgramID())){//不是包内游戏才能走分类上下架。 add by aiyan 2012-10-17
					checkAndAddCategory(game, TACTICLIST);
					
					//商品库优化的 add by aiyan 2013-04-22 当该业务所对应的内容id有ANDROID适配机型则可以上架。
					LOG.debug("androidContentSet.contains(game.getContentTag()) IS:"+androidContentSet.contains(game.getContentTag())+"--"+game.getContentTag());
					if(androidContentSet.contains(game.getContentTag())){

						PPMSDAO.addMessagesStatic(MSGType.ContentModifyReq,null,game.getContentID()+":0");//应用新上线
						LOG.debug("START INTO TACTICLIST_Android"+game.getContentID());
						checkAndAddCategory(game, TACTICLIST_Android);
						setDeviceResource(game.getContentID(),game.getContentTag());//加适配关系
						PPMSDAO.addMessagesStatic(MSGType.ContentModifyReq,null,game.getContentID()+":2");//应用适配更新
						
						setFulldevice(game.getContentID(),game.getContentTag()); // add by aiyan 2013-07-02
						

			            
					}
				}
				

				
				
				return DataSyncConstants.SUCCESS_ADD;
			}
			
			}catch(Exception e){
				LOG.error("处理基地游戏内容出现异常",e);
				return DataSyncConstants.FAILURE;
			}

	}
	
	private void setFulldevice(String serviceid,String contentid) {
		// TODO Auto-generated method stub
		Map<String,String> map = GameSyncDAO.getInstance().getFullDevice(serviceid);
		Map<String,List<String>> map_append =  GameSyncDAO.getInstance().getFullDeviceAppend(contentid);
		List<String> fullDeviceIdList = map_append.get("fullDeviceIdList");
		List<String> fullDeviceNameList = map_append.get("fullDeviceNameList");
		
		String fullDeviceid = list2String(fullDeviceIdList);
        String fulldevicename = PublicUtil.filterMbrandEspecialChar(list2String(fullDeviceNameList));

        if(fullDeviceid.length()>0){
        	//fullDeviceid = (String)map.get("fulldeviceid")+","+fullDeviceid;
        	//fulldevicename = (String)map.get("fulldevicename")+","+fulldevicename;
        	
        	fullDeviceid = delRep((String)map.get("fulldeviceid"),fullDeviceid);
        	fulldevicename = delRep((String)map.get("fulldevicename"),fulldevicename);
        }else{
        	LOG.error("这个情况几乎是不能发生的。heehee!!");
        	LOG.error("serviceid"+serviceid+"contentid"+contentid);
        }
        
		GameSyncDAO.getInstance().updateFullDevice(serviceid,fullDeviceid,fulldevicename);
		
	}

	/**
	 * 去重
	 * @param old
	 * @param newls
	 * @return
	 */
	public String delRep(String old, String newls) {

		String olds[] = old.split(",");
		String newlss[] = newls.split(",");
		HashMap hm = new HashMap();
		String ss = "";
		if (olds.length > 0) {
			for (int i = 0; i < olds.length; i++) {
				hm.put((String) olds[i], "");
			}
		}
		if (newlss.length > 0) {
			for (int i = 0; i < newlss.length; i++) {
				hm.put((String) newlss[i], "");
			}
		}
		StringBuffer res = new StringBuffer();
		Set ts = hm.entrySet();
		Iterator it = ts.iterator();
		while (it.hasNext()) {
			Entry ey = (Entry) it.next();
			res.append(ey.getKey() + ",");
		}
		ss = res.substring(0, res.length() - 1);
		return ss;
	}
    /**
     * 将list中的数据拼装为 {devicename},{devicename}… 的样式
     * 
     * @param list 保存deviceName的集合
     * @return 形如：{devicename1},{devicename2}… 的字符串
     */
    private String list2String(Collection collection)
    {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = collection.iterator();
        boolean dotFlag = false;// 第一次不需要插入逗号
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
	//把这个应用的contentid和seriesid放到表t_a_cm_device_resource（contentid,device_id）
	private void setDeviceResource(String serviceid,String contentid) {
		// TODO Auto-generated method stub
		GameSyncDAO.getInstance().setDeviceResource(serviceid,contentid);
		
	}

	private void setGameAndServo(GAppGame game, GameServiceVO servo,
			GameContent content, GameService service) throws BOException {
		game.setContentID(service.getServiceCode());//内容代码/套餐包
		//game.setName(service.getServiceName());//内容名称
		game.setName(content.getName());//内容名称
		game.setIntroduction(content.getDesc());//内容简介
//		内容简称
		game.setIcpCode(content.getCpId());//CP代码
		game.setSpName(content.getCpName());//CP名称
		game.setIcpServId(service.getServiceCode());
		//操作简介
		game.setCreateDate(content.getEffectiveDate());//产品生效日期
		//game.setLupdDate(content.getInvalidDate());//失效日期 removed by aiyan 2012-11-1 终端门户对这个字段很敏感，故丢掉。改用t_game_statistcs(STARTTIME)更新这个字段。
		

		if(content.getFileSize()!=null){
			try{
				game.setProgramSize(Integer.parseInt(content.getFileSize()));//文件大小
			}catch(Exception e){
				LOG.error("出错，游戏内容的文件大小不是数字！");
			}
		}
		
		if(!"".equals(service.getPkgId().trim())){
			game.setProgramID(service.getPkgId());
		}else{
			game.setProgramID("-1");
		}
		
		
		
		game.setCateName("基地游戏");
		
		HashMap gameTypeMap = GameSyncTools.getInstance().getGameTypeMap();
		GameTypeEntity entity = (GameTypeEntity)gameTypeMap.get(content.getGameTypeId());
		
		//add by aiyan 2012-10-19 当导入的游戏分类没有在T_GAME_TYPE配MM二级分类的话，就不入了。
//		if(entity==null||entity.getMmname()==null||entity.getMmid()==null){
//			LOG.error("t_game_type表中不存在游戏分类标识为:"+content.getGameTypeId()+"  no_game_type hehe ...");
//			throw new BOException("t_game_type表中不存在游戏分类标识为:"+content.getGameTypeId()+"  no_game_type hehe ...");
//		}
		if(entity==null||entity.getMmname()==null||entity.getMmid()==null)
		{game.setAppCateName("其他游戏");
		game.setAppCateID("37");

		}//游戏分类标识		
		else
		{game.setAppCateName(entity.getMmname());//游戏分类标识
		game.setAppCateID(entity.getMmid());
}
		
		game.setPlupdDate(PublicUtil.getCurDateTime());
		game.setProvider("B");
		game.setLanguage("1");//设置简体中文。
		game.setKeywords('{' + service.getServiceName() + '}');//设置关键字
		game.setServAttr("G");
		game.setPvcID("0000");
		game.setCityID("{0000}");
		game.setContentTag(content.getContentCode());
		
		
		
		//构造商品编码需要的字段。
		game.setCompanyID(game.getIcpCode());
		game.setProductID(game.getIcpServId());
		game.setContentID(game.getIcpServId());
		
		
		
		game.setLOGO1(content.getLogo1());
		game.setLOGO2(content.getLogo2());
		game.setLOGO3(content.getLogo3());
		game.setLOGO4(content.getLogo4());
		game.setLOGO6(content.getLogo6());
//		//WWW大广告图地址
//		game.setWWWPropaPicture1(content.getWWWPropaPicture1());
//		//WWW列表小图标地址
//		game.setWWWPropaPicture2(content.getWWWPropaPicture2());
//		//WWW标准展示图
//		game.setWWWPropaPicture3(content.getWWWPropaPicture3());

//		game.setClientPreviewPicture1(content.getClientPreviewPicture1());
//		game.setClientPreviewPicture2(content.getClientPreviewPicture2());
//		game.setClientPreviewPicture3(content.getClientPreviewPicture3());
//		game.setClientPreviewPicture4(content.getClientPreviewPicture4());

		game.setPicture1(content.getPicture1());
		game.setPicture2(content.getPicture2());
		game.setPicture3(content.getPicture3());
		game.setPicture4(content.getPicture4());
		game.setPicture5(content.getPicture5());
		
		
		game.setFulldeviceID(content.getFulldeviceid());
		game.setBrand(content.getBrand());
		game.setFulldeviceName(content.getFulldevicename());
		
		//add by aiyan 2012-11-1接入方式
//		0:其他
//		1:CMWAP
//		2:CMNET
//		3:CMWAP,CMNET
		game.setOtherNet(service.getConnectionType());
		
		game.setFuncdesc(service.getScale());//add  by aiyan 2012-11-3 分成比例（字段复用了，以前是新功能介绍）
		game.setCountFlag(service.getCountFlag());//add by aiyan 2012-11-4

		servo.setIcpCode(service.getCpId());
		servo.setSpName(service.getCpName());
		servo.setIcpServid(service.getServiceCode());
		
		servo.setServName(service.getServiceName());
		servo.setChargeType(service.getBillType());
		servo.setChargDesc(service.getFeeType());
		servo.setMobilePrice(Integer.parseInt(service.getPrice()));
		servo.setServType(Integer.parseInt(service.getServiceType()));
		if(!"".equals(service.getDiscountType().trim())){
			servo.setServFlag(Integer.parseInt(service.getDiscountType()));
		}
		servo.setPtypeId(Integer.parseInt(service.getPayType()));
		servo.setContentid(service.getServiceCode());

		servo.setOldprice(service.getOldPrice());
		servo.setFirsttype(service.getFirstType());
		servo.setContenttag(content.getContentCode());
		
	}

	private void turnFields(DataRecord record,
			GameService service) {
		// TODO Auto-generated method stub
		service.setServiceCode((String)record.get(1));
		service.setContentCode((String)record.get(2));
		service.setContentName((String)record.get(3));
		service.setServiceName((String)record.get(4));
		service.setCpId((String)record.get(5));
		service.setCpName((String)record.get(6));
		service.setServiceType((String)record.get(7));
		service.setTwUrl((String)record.get(8));
		service.setPayType((String)record.get(9));
		service.setOldPrice((String)record.get(10));
		service.setPrice((String)record.get(11));
		service.setFeeType((String)record.get(12));
		service.setBillType((String)record.get(13));
		service.setPkgId((String)record.get(14));
		service.setFirstType((String)record.get(15));
		service.setDiscountType((String)record.get(16));
		service.setChargeType((String)record.get(17));
		service.setScale((String)record.get(18));
		service.setConnectionType((String)record.get(19));
		service.setCountFlag((String)record.get(20));
	}
	
	public void prepareData() throws Exception
	{
//		GameSyncTools.getInstance().initGameUAMapping();
//		
		String sqlCode="com.aspire.dotcard.basegame.ServiceDealer.prepareData.getAllGameServiceMap";
		oldKeyIds = GameSyncDAO.getInstance().getAllMap(sqlCode);
		
		sqlCode="com.aspire.dotcard.basegame.ServiceDealer.prepareData.getTWGameKeys";
		twGameKeys = GameSyncDAO.getInstance().getAllKeyId(sqlCode);
		
		sqlCode="com.aspire.dotcard.basegame.ServiceDealer.prepareData.getPkgGameRefKeys";
		pkgGameRefKeys = GameSyncDAO.getInstance().getAllKeyId(sqlCode);
		
		GameSyncTools.getInstance().initGameCateMapping2();
		GameSyncTools.getInstance().initGameUAMapping();
		TACTICLIST = GameSyncTools.getInstance().getSyncTactic();
		
		TACTICLIST_Android = new TacticBO().queryAndroidAll();
		androidContentSet =  GameSyncDAO.getInstance().getAndroidContentSet();
		
 
		

		
	}

	public void init(DataSyncConfig config) throws Exception
	{
		try
		{
			contentRoot = (Category) Repository.getInstance().getNode(
					RepositoryConstants.ROOT_CONTENT_ID,
					RepositoryConstants.TYPE_CATEGORY);
			
			categoryRoot = (Category) Repository.getInstance().getNode(
					RepositoryConstants.ROOT_CATEGORY_ID,
					RepositoryConstants.TYPE_CATEGORY);
			
		} catch (BOException e)
		{
			LOG.error(e);
		}
	}
	
	/**
	 * 匹配内容上架策略。只是匹配策略中的两个属性。1.内容类型，2 分类名称
	 * 
	 * @param contentType
	 * @param gc
	 */
	private void checkAndAddCategory(GContent gc, List tacticList) throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("DataSyncBO.checkAndAddCategory(). contentType="+gc.getContentID()+"--------start-----------" + gc.getType());
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
					LOG.error("目前没有该分类，无法完成上架。id=" + categoryID);
					throw new BOException("不存在当前货架，id=" + categoryID);
				}
				cateCache.put(pCate.getId(), pCate);

			}
			addCategory(gc, categoryID);
			LOG.debug("DataSyncBO.checkAndAddCategory(). contentType="+gc.getContentID()+"-------end-----------" + gc.getType());
		}
	}
	
	/**
	 * 将内容上架到货架货架下
	 * 
	 * @param gc
	 * @param categoryID
	 */
	private void addCategory(GContent gc, String categoryID) throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("=====addCategory: categoryID=" + categoryID + " contentID="
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
			
			//商品库优化 add by aiyan 2013-04-23
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
			String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
			LOG.debug("categoryID|rootCategoryId:"+categoryID+"|"+rootCategoryId);
            if(SSMSDAO.getInstance().isAndroidCategory(categoryID, rootCategoryId)){
            	//PPMSDAO.addMessagesStatic(MSGType.RefModifyReq,null,goodsID+":0");//搞一个不带事务的上架，呵呵。。。
            	//大胖说要把上下架消息变大,所以注销上面的，改下以下。 add by aiyan 2013-04-27
//            	Goodsid	必须	String	reference表中的goodsid
//            	Categoryid	可选	String	货架categoryid，新建时必须有
//            	Id	可选	String	货架categoryid对应的Id，新建时必须有
//            	Refnodeid	可选	String	应用ID，新建时必须有
//            	Sortid	可选	String	排序字段，新建时必须有
//            	Loaddate	可选	String	更新时间，新建时必须有
//            	Action	必须	String	0：新建
//            	9：删除
//            	Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。

            	PPMSDAO.addMessagesStatic(MSGType.RefModifyReq,
            			null,
            			goodsID+":"+category.getCategoryID()+":"+category.getId()+":"+ref.getRefNodeID()+":"+ref.getSortID()+":"+DateUtil.formatDate(goodsVO.getChangeDate(), "yyyyMMddHHmmss")+":0");//搞一个不带事务的上架，呵呵。。。
            }
			
		} catch (Exception e)
		{
			throw new BOException("内容上架出错！", e);
		}
	}


	/**
	 * 删除所有下线的游戏但需要去除检查不通过的游戏。
	 * 需要把全量数据导入后执行。
	 * @param List failureChecked 检查失败的游戏列表。
	 * @return int[] 数据第一值表示成功删除的个数，第二个删除失败的个数。
	 */
	private int[] deleteOldGame(List failureChecked)
	{
		LOG.debug("开始清理游戏内容（t_r_gcontent）、商品！(t_r_reference)");
		int result[]= {0,0};//定义返回结果。数据第一值表示成功删除的个数，第二个删除失败的个数。
		Set delSet = oldKeyIds.keySet();
		for(int i=0;i<failureChecked.size();i++)//检查没有通过的游戏，禁止删除。
		{
			delSet.remove(failureChecked.get(i));
			
		}
		
		// 成功删除的计数器
		int count = 0;
		for(Iterator it =delSet.iterator();it.hasNext();)
		{
			
			String gameId = (String)oldKeyIds.get((String)it.next());
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
					
					//商品库优化 add by aiyan 2013-04-23
					ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
					String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
		            if(SSMSDAO.getInstance().isAndroidCategory(categoryRoot.getId(), rootCategoryId)){
		            	//PPMSDAO.addMessagesStatic(MSGType.RefModifyReq,null,ref.getGoodsID()+":9");//商品下架
		    	    	//大胖说要把上下架消息变大,所以注销上面的，改下以下。 add by aiyan 2013-04-27
//		    	    	Goodsid	必须	String	reference表中的goodsid
//		    	    	Categoryid	可选	String	货架categoryid，新建时必须有
//		    	    	Id	可选	String	货架categoryid对应的Id，新建时必须有
//		    	    	Refnodeid	可选	String	应用ID，新建时必须有
//		    	    	Sortid	可选	String	排序字段，新建时必须有
//		    	    	Loaddate	可选	String	更新时间，新建时必须有
//		    	    	Action	必须	String	0：新建
//		    	    	9：删除
//		    	    	Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。
		            	PPMSDAO.addMessagesStatic(MSGType.RefModifyReq,null,ref.getGoodsID()+"::::::9");//删除这些可选项就可以空字符串了。呵呵
		            }
		            // end 商品库优化
					
				}
				// 一次保存操作
				categoryRoot.saveNode();

				GAppGame game = (GAppGame) Repository.getInstance().getNode(gameId,
						RepositoryConstants.TYPE_APPGAME);

				String contentid = game.getContentID();
				
				// 调用资源管理的删除内容接口将该内容删除
				contentRoot.delNode(game);
				contentRoot.saveNode();
				
				//商品库优化 add by aiyan 2013-04-23
	            //if(androidContentSet.contains(contentid)){
	            	PPMSDAO.addMessagesStatic(MSGType.ContentModifyReq,null,contentid+":9");//应用下线，发消息。
	            	LOG.info("下架内容表中的游戏数据");
	            //}
	            // end 商品库优化
	            
				GameSyncDAO.getInstance().delGameService(game.getIcpServId());

				// 需要删除资源服务器上图片信息。
				//deleteRemotePic(game.getIcpServId()); 这个还搞不定的。
				if (LOG.isDebugEnabled())
				{
					LOG.debug("删除此游戏成功，gameId=" + gameId);
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
				LOG.error("删除游戏出错，gameId=" + gameId, e);
			}
			

		}
		result[0]=count;
		result[1]=delSet.size()-count;
		return result;
	}
	
	private int[] delOldTWGame(){
		LOG.debug("开始清理图文游戏。。。");
		int result[]= {0,0};//定义返回结果。数据第一值表示成功删除的个数，第二个删除失败的个数。
		if(twGameKeys.size()==0){
			return result;
		}
		Object[] arr = twGameKeys.toArray();
		int size = arr.length;
		int start=0;int end =0;
		String sql=null;
		try {
			sql = DB.getInstance().getSQLByCode("com.aspire.dotcard.basegame.delOldTWGame");
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			LOG.error("com.aspire.dotcard.basegame.delOldTWGame没有在sql.properties配置。",e1);
			return result;
		}
		do{
			end=end+100;
			if(end>size) end=size;
			
			StringBuffer sb = new StringBuffer();
			for(int i=start;i<end;i++){
				sb.append(",'").append(arr[i]).append("'");
			}
			if(sb.length()>0){
				try {
					String whereStr = " where cpserviceid in ("+sb.substring(1)+")";
					GameSyncDAO.getInstance().delOldData(sql+whereStr);
					result[0]+=(end-start);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					LOG.error("游戏图文删除旧数据出错！",e);
					result[1]+=(end-start);
				}
			}
			
			start = end;
		}while(end<size);
		return result;
	}
	
	private int[] delOldPKGGameRef(){
		int result[]= {0,0};//定义返回结果。数据第一值表示成功删除的个数，第二个删除失败的个数。
		LOG.debug("开始清理游戏包商品！");
		if(pkgGameRefKeys.size()==0){
			return result;
		}
		
		String sqlCode = "com.aspire.dotcard.basegame.delOldPKGGameRef";
		String[] arr = new String[pkgGameRefKeys.size()];
		pkgGameRefKeys.toArray(arr);
        String[] mutisql = new String[arr.length];
        Object paras[][] = new String[arr.length][2];
        
        for (int i = 0; i < arr.length; i++)
        {
            String str = arr[i];
            mutisql[i] = sqlCode;
            paras[i][0] = str.split(",")[0];
            paras[i][1] = str.split(",")[1];
        }
        
        try
        {
            DB.getInstance().executeMutiBySQLCode(mutisql, paras);
            result[0]=arr.length;
        }
        catch (DAOException e)
        {
        	result[1]=arr.length;
            LOG.error("游戏包商品删除旧数据出错！", e);
            
        }
        return result;
		
	}
	

	public void delOldData(DataSyncTask task,List failureChecked){
		// TODO Auto-generated method stub
		int result[]=deleteOldGame(failureChecked);
		task.addSuccessDelete(result[0]);
		task.addFailureProcess(result[1]);
		
		result = delOldTWGame();
		task.addSuccessDelete(result[0]);
		task.addFailureProcess(result[1]);

		result = delOldPKGGameRef();
		task.addSuccessDelete(result[0]);
		task.addFailureProcess(result[1]);
	}
	public void delOldData() throws Exception {
		
		
	}
	




}
