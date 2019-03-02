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

	// ��Դ���ݴ洢����ĸ��ڵ�
	private static Category contentRoot;// ���ڻ���
	// ���ڻ���,���ܵĸ�����701�ڵ�
	private static Category categoryRoot;
	// �ϼܹ���Ϸ�Ļ��ܻ��棬��Ҫʱ�̰߳�ȫ�ġ�
	private static Map cateCache = Collections.synchronizedMap(new HashMap());
	private static List TACTICLIST;
	private static List TACTICLIST_Android;//add by aiyan Ϊ����Ʒ���Ż���������Ҫ��androidӦ���ϼܡ�
	private Set androidContentSet;//add by aiyan Ϊ����Ʒ���Ż�����һ�����ݴ���android��������ͣ�������ϼܡ�

	public void clearDirtyData()
	{
		oldKeyIds=null;//���ݱ�t_r_gcontent�������Ϸ�������ϣ������޸ģ�������������ˡ�
		twGameKeys = null;//ͼ�ı�ļ�¼�����������޸ģ�������������ˡ�
		pkgGameRefKeys = null;//��Ϸ����Ʒ��ļ�¼�����������޸ģ�������������ˡ�
		
		TACTICLIST = null;
		TACTICLIST_Android = null;
		androidContentSet = null;
		cateCache.clear();
		BlackBO.getInstance().delReference();

	}
	
	/**
	 * �ڴ�������¼��ʱ����ҵ����Э��������ʵ����������ʹ���
	 * 1��������Ϸ��
	 * 2��ͼ����Ϸ 
	 * 3����Ϸ�������������Ϸ��
	 * @throws BOException 
	 */
 	public int dealDataRecrod(DataRecord record){
		try {
			GameService service = new GameService();
			turnFields(record,service);
			GameContent content = GameSyncDAO.getInstance().getGameContentById(service.getContentCode());

			if(content==null){
				LOG.error("����Ϊ��,������ϷID:"+service.getContentCode()+"�������ҵ��"+service.getServiceCode());
				return DataSyncConstants.FAILURE;
			}
			if ("1".equals(content.getContentType())) {// ��ͨ
				int ret=  dealSingleGame(service, content);
				if (!"".equals(service.getPkgId())&&ret!=DataSyncConstants.FAILURE) {
					dealPkgGameRef(service.getPkgId(), service.getServiceCode());
				}
				return ret;

			} else if ("2".equals(content.getContentType())) {// ͼ��
				return dealTwGame(service, content);

			}
			//removed by aiyan for gamepkg handing in DataSyncTaskForContent,not here. 2012-10-13
//			else if ("3".equals(content.getContentType())) {// ��Ϸ�� 
//				return dealPkgGame(service, content);
//			} 
			
			else {
				throw new BOException("���ظ����������Ͳ���ȷ,�������ͣ�"
						+ content.getGameTypeId());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("������Ϸҵ��ʱ����",e);
			return DataSyncConstants.FAILURE;
			
		}
		
		
		
	}
	
	public int dealTwGame(GameService service,GameContent content){
		try{
			
			HashMap gameTypeMap = GameSyncTools.getInstance().getGameTypeMap();
			GameTypeEntity entity = (GameTypeEntity)gameTypeMap.get(content.getGameTypeId());
			
			//add by aiyan 2012-10-19 ���������Ϸ����û����T_GAME_TYPE��MM��������Ļ����Ͳ����ˡ�
//			if(entity==null||entity.getMmid()==null||entity.getMmname()==null){
//				LOG.error("t_game_type���в�������Ϸ�����ʶΪ:"+content.getGameTypeId()+"  no_game_type hehe ...");
//				throw new BOException("t_game_type���в�������Ϸ�����ʶΪ:"+content.getGameTypeId()+"  no_game_type hehe ...");
//			}
			
			if(entity==null||entity.getMmname()==null||entity.getMmid()==null)
			{entity.setMmname("������Ϸ");
			//entity.setMmid("37");
			entity.setName( content.getName());
			}//��Ϸ�����ʶ	  	


			
			
			//ͼ�ı����������ҵ����룬�����ҵ����뼯�Ϻ��е�ǰ��¼���޸ģ�����������
			if(twGameKeys.contains(service.getServiceCode())){
				GameSyncDAO.getInstance().updateTWGame(service,content,entity);
				twGameKeys.remove(service.getServiceCode());
				return DataSyncConstants.SUCCESS_UPDATE;
			}else{
				GameSyncDAO.getInstance().insertTWGame(service,content,entity);
				return DataSyncConstants.SUCCESS_ADD;
			}
			
		}catch(Exception e){
			LOG.error("����ͼ����Ϸ����",e);
			return DataSyncConstants.FAILURE;
		}
		
		
	}
	
	private int dealPkgGameRef(String pkgId, String serviceCode) {
		try {
			// ���T_GAME_PKG_ref��
			if (!pkgGameRefKeys.contains(pkgId + "," + serviceCode)) {
				GameSyncDAO.getInstance().insertPkgGameRef(pkgId, serviceCode);
				pkgGameRefKeys.remove(pkgId + "," + serviceCode);//Ϊɾ������׼������
				return DataSyncConstants.SUCCESS_ADD;
			} else {
				pkgGameRefKeys.remove(pkgId + "," + serviceCode);
				return DataSyncConstants.SUCCESS_UPDATE;
			}
		} catch (Exception e) {
			LOG.error("���T_GAME_PKG_ref�����",e);
			return DataSyncConstants.FAILURE;
		}

	}

	public int dealSingleGame(GameService service,GameContent content)
	{
		LOG.info("��ʼ����Ϸ���ص�����Ϸ��⡣");
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
					LOG.error("������Ϸ�������Ϊ�գ�����⣻basegame  device is null"
							+ service.getServiceCode());
					throw new BOException("������Ϸ�������Ϊ�գ�����⣻basegame  device is null"
							+ service.getServiceCode());
				}
				
				else
				{
					//������Ϸ(��ͨ���¼�),�Ƿ����(��δ����)
					game.setId(String.valueOf(oldKeyIds.get(service.getServiceCode())));
					// ����ҵ����Ϣ
					GameSyncTools.getInstance().saveGameService2(servo);
					game.save();
					//��Ʒ���Ż��� add by aiyan 2013-04-22 
					if(androidContentSet.contains(game.getContentTag())){
						PPMSDAO.addMessagesStatic(MSGType.ContentModifyReq,null,game.getContentID()+":1");//Ӧ����Ϣ����
						//t_a_cm_device_resource
						setDeviceResource(game.getContentID(),game.getContentTag());
						//removed by aiyan 2013-06-01 �ն��Ż��Լ�������Ĵ����ְ�������Ӧ�ñ�������������ظ��������ˣ��ʶ�����
						//PPMSDAO.addMessagesStatic(MSGType.ContentModifyReq,null,game.getContentID()+":2");//Ӧ���������
						
						
						setFulldevice(game.getContentID(),game.getContentTag()); // add by aiyan 2013-07-02
					}
					
					oldKeyIds.remove(service.getServiceCode());
					return DataSyncConstants.SUCCESS_UPDATE;
					
				}
			}else{
				if(game.getFulldeviceID() == null || game.getFulldeviceID().trim().length() <= 0)
				{
					LOG.error("������Ϸ�������Ϊ�գ�����⣻basegame  device is null"
							+ service.getServiceCode());
					throw new BOException("������Ϸ�������Ϊ�գ�����⣻basegame  device is null"
							+ service.getServiceCode());
				}
				
				GameSyncTools.getInstance().saveGameService2(servo);
				// ��һ�β���д�봴��ʱ��
				game.setCreateDate(PublicUtil.getCurDateTime());
				// �������ݵ����ݿ�
				contentRoot.addNode(game);
				contentRoot.saveNode();
				//LOG.debug("AIYAN...HEHE..2013");
				if("-1".equals(game.getProgramID())){//���ǰ�����Ϸ�����߷������¼ܡ� add by aiyan 2012-10-17
					checkAndAddCategory(game, TACTICLIST);
					
					//��Ʒ���Ż��� add by aiyan 2013-04-22 ����ҵ������Ӧ������id��ANDROID�������������ϼܡ�
					LOG.debug("androidContentSet.contains(game.getContentTag()) IS:"+androidContentSet.contains(game.getContentTag())+"--"+game.getContentTag());
					if(androidContentSet.contains(game.getContentTag())){

						PPMSDAO.addMessagesStatic(MSGType.ContentModifyReq,null,game.getContentID()+":0");//Ӧ��������
						LOG.debug("START INTO TACTICLIST_Android"+game.getContentID());
						checkAndAddCategory(game, TACTICLIST_Android);
						setDeviceResource(game.getContentID(),game.getContentTag());//�������ϵ
						PPMSDAO.addMessagesStatic(MSGType.ContentModifyReq,null,game.getContentID()+":2");//Ӧ���������
						
						setFulldevice(game.getContentID(),game.getContentTag()); // add by aiyan 2013-07-02
						

			            
					}
				}
				

				
				
				return DataSyncConstants.SUCCESS_ADD;
			}
			
			}catch(Exception e){
				LOG.error("���������Ϸ���ݳ����쳣",e);
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
        	LOG.error("�����������ǲ��ܷ����ġ�heehee!!");
        	LOG.error("serviceid"+serviceid+"contentid"+contentid);
        }
        
		GameSyncDAO.getInstance().updateFullDevice(serviceid,fullDeviceid,fulldevicename);
		
	}

	/**
	 * ȥ��
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
     * ��list�е�����ƴװΪ {devicename},{devicename}�� ����ʽ
     * 
     * @param list ����deviceName�ļ���
     * @return ���磺{devicename1},{devicename2}�� ���ַ���
     */
    private String list2String(Collection collection)
    {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = collection.iterator();
        boolean dotFlag = false;// ��һ�β���Ҫ���붺��
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
	//�����Ӧ�õ�contentid��seriesid�ŵ���t_a_cm_device_resource��contentid,device_id��
	private void setDeviceResource(String serviceid,String contentid) {
		// TODO Auto-generated method stub
		GameSyncDAO.getInstance().setDeviceResource(serviceid,contentid);
		
	}

	private void setGameAndServo(GAppGame game, GameServiceVO servo,
			GameContent content, GameService service) throws BOException {
		game.setContentID(service.getServiceCode());//���ݴ���/�ײͰ�
		//game.setName(service.getServiceName());//��������
		game.setName(content.getName());//��������
		game.setIntroduction(content.getDesc());//���ݼ��
//		���ݼ��
		game.setIcpCode(content.getCpId());//CP����
		game.setSpName(content.getCpName());//CP����
		game.setIcpServId(service.getServiceCode());
		//�������
		game.setCreateDate(content.getEffectiveDate());//��Ʒ��Ч����
		//game.setLupdDate(content.getInvalidDate());//ʧЧ���� removed by aiyan 2012-11-1 �ն��Ż�������ֶκ����У��ʶ���������t_game_statistcs(STARTTIME)��������ֶΡ�
		

		if(content.getFileSize()!=null){
			try{
				game.setProgramSize(Integer.parseInt(content.getFileSize()));//�ļ���С
			}catch(Exception e){
				LOG.error("������Ϸ���ݵ��ļ���С�������֣�");
			}
		}
		
		if(!"".equals(service.getPkgId().trim())){
			game.setProgramID(service.getPkgId());
		}else{
			game.setProgramID("-1");
		}
		
		
		
		game.setCateName("������Ϸ");
		
		HashMap gameTypeMap = GameSyncTools.getInstance().getGameTypeMap();
		GameTypeEntity entity = (GameTypeEntity)gameTypeMap.get(content.getGameTypeId());
		
		//add by aiyan 2012-10-19 ���������Ϸ����û����T_GAME_TYPE��MM��������Ļ����Ͳ����ˡ�
//		if(entity==null||entity.getMmname()==null||entity.getMmid()==null){
//			LOG.error("t_game_type���в�������Ϸ�����ʶΪ:"+content.getGameTypeId()+"  no_game_type hehe ...");
//			throw new BOException("t_game_type���в�������Ϸ�����ʶΪ:"+content.getGameTypeId()+"  no_game_type hehe ...");
//		}
		if(entity==null||entity.getMmname()==null||entity.getMmid()==null)
		{game.setAppCateName("������Ϸ");
		game.setAppCateID("37");

		}//��Ϸ�����ʶ		
		else
		{game.setAppCateName(entity.getMmname());//��Ϸ�����ʶ
		game.setAppCateID(entity.getMmid());
}
		
		game.setPlupdDate(PublicUtil.getCurDateTime());
		game.setProvider("B");
		game.setLanguage("1");//���ü������ġ�
		game.setKeywords('{' + service.getServiceName() + '}');//���ùؼ���
		game.setServAttr("G");
		game.setPvcID("0000");
		game.setCityID("{0000}");
		game.setContentTag(content.getContentCode());
		
		
		
		//������Ʒ������Ҫ���ֶΡ�
		game.setCompanyID(game.getIcpCode());
		game.setProductID(game.getIcpServId());
		game.setContentID(game.getIcpServId());
		
		
		
		game.setLOGO1(content.getLogo1());
		game.setLOGO2(content.getLogo2());
		game.setLOGO3(content.getLogo3());
		game.setLOGO4(content.getLogo4());
		game.setLOGO6(content.getLogo6());
//		//WWW����ͼ��ַ
//		game.setWWWPropaPicture1(content.getWWWPropaPicture1());
//		//WWW�б�Сͼ���ַ
//		game.setWWWPropaPicture2(content.getWWWPropaPicture2());
//		//WWW��׼չʾͼ
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
		
		//add by aiyan 2012-11-1���뷽ʽ
//		0:����
//		1:CMWAP
//		2:CMNET
//		3:CMWAP,CMNET
		game.setOtherNet(service.getConnectionType());
		
		game.setFuncdesc(service.getScale());//add  by aiyan 2012-11-3 �ֳɱ������ֶθ����ˣ���ǰ���¹��ܽ��ܣ�
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
	 * ƥ�������ϼܲ��ԡ�ֻ��ƥ������е��������ԡ�1.�������ͣ�2 ��������
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
		// ������ȥƥ��ÿһ������
		for (int i = 0; i < size; i++)
		{
			vo = (TacticVO) tacticList.get(i);
			categoryID = vo.getCategoryID();

			temp = vo.getContentType();

			// ��һ����������������Ƿ�ƥ��
			if (!(TacticConstants.CONTENT_TYPE_ALL.equals(temp) || gc.getType().equals(
					temp)))
			{
				// ���Ͳ�ƥ��,�����һ������
				continue;
			}
			// �ڶ��������Ӧ�÷��������Ƿ�ƥ��
			if (gc instanceof GAppContent)
			{
				// ��gcת����Ӧ�����ݻ�ȡӦ�÷�������
				GAppContent app = (GAppContent) gc;
				// �ж�Ӧ�÷��������Ƿ���ͬ�������е�Ӧ�÷�������ƥ��
				if (!app.getAppCateName().equals(vo.getAppCateName()))
				{
					continue;
				}
			}

			// �����������м��ͨ�����������ϼܵ����Զ�Ӧ�Ļ��ܻ�����
			Category pCate = (Category) cateCache.get(categoryID);
			if (pCate == null)// ��������Ҫ���浽������
			{
				pCate = (Category) Repository.getInstance().getNode(categoryID,
						RepositoryConstants.TYPE_CATEGORY);
				if (pCate == null)
				{
					LOG.error("Ŀǰû�и÷��࣬�޷�����ϼܡ�id=" + categoryID);
					throw new BOException("�����ڵ�ǰ���ܣ�id=" + categoryID);
				}
				cateCache.put(pCate.getId(), pCate);

			}
			addCategory(gc, categoryID);
			LOG.debug("DataSyncBO.checkAndAddCategory(). contentType="+gc.getContentID()+"-------end-----------" + gc.getType());
		}
	}
	
	/**
	 * �������ϼܵ����ܻ�����
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

			// ������Ʒ���룽���ܱ��룫��ҵ����(6λ)��ҵ������(12λ)�����ݱ���(12λ)
			String goodsID = category.getCategoryID()
					+ PublicUtil.lPad(gc.getCompanyID(), 6)
					+ PublicUtil.lPad(gc.getProductID(), 12)
					+ PublicUtil.lPad(gc.getContentID(), 12);
			// �ŵ�Ŀ�������

			// ����һ���µ���Դid
			String newNodeID = category.getNewAllocateID();

			// ����һ�����ýڵ㣬��������������
			ReferenceNode ref = new ReferenceNode();
			ref.setId(newNodeID);
			ref.setParentID(category.getId());
			ref.setPath(category.getPath() + ".{" + newNodeID + "}");
			ref.setRefNodeID(gc.getId());
			ref.setSortID(0);
			ref.setGoodsID(goodsID);
			ref.setCategoryID(category.getCategoryID());
			ref.setVariation(0);

			// ������Ʒ��ϢVO�࣬��������������
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

			// ����GoodsBO�е�addNodeAndInsertGoodsInfor����ڵ㲢������Ʒ��Ϣ
			GoodsBO.addNodeAndInsertGoodsInfo(ref, goodsVO);
			
			//��Ʒ���Ż� add by aiyan 2013-04-23
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
			String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
			LOG.debug("categoryID|rootCategoryId:"+categoryID+"|"+rootCategoryId);
            if(SSMSDAO.getInstance().isAndroidCategory(categoryID, rootCategoryId)){
            	//PPMSDAO.addMessagesStatic(MSGType.RefModifyReq,null,goodsID+":0");//��һ������������ϼܣ��Ǻǡ�����
            	//����˵Ҫ�����¼���Ϣ���,����ע������ģ��������¡� add by aiyan 2013-04-27
//            	Goodsid	����	String	reference���е�goodsid
//            	Categoryid	��ѡ	String	����categoryid���½�ʱ������
//            	Id	��ѡ	String	����categoryid��Ӧ��Id���½�ʱ������
//            	Refnodeid	��ѡ	String	Ӧ��ID���½�ʱ������
//            	Sortid	��ѡ	String	�����ֶΣ��½�ʱ������
//            	Loaddate	��ѡ	String	����ʱ�䣬�½�ʱ������
//            	Action	����	String	0���½�
//            	9��ɾ��
//            	Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�

            	PPMSDAO.addMessagesStatic(MSGType.RefModifyReq,
            			null,
            			goodsID+":"+category.getCategoryID()+":"+category.getId()+":"+ref.getRefNodeID()+":"+ref.getSortID()+":"+DateUtil.formatDate(goodsVO.getChangeDate(), "yyyyMMddHHmmss")+":0");//��һ������������ϼܣ��Ǻǡ�����
            }
			
		} catch (Exception e)
		{
			throw new BOException("�����ϼܳ���", e);
		}
	}


	/**
	 * ɾ���������ߵ���Ϸ����Ҫȥ����鲻ͨ������Ϸ��
	 * ��Ҫ��ȫ�����ݵ����ִ�С�
	 * @param List failureChecked ���ʧ�ܵ���Ϸ�б�
	 * @return int[] ���ݵ�һֵ��ʾ�ɹ�ɾ���ĸ������ڶ���ɾ��ʧ�ܵĸ�����
	 */
	private int[] deleteOldGame(List failureChecked)
	{
		LOG.debug("��ʼ������Ϸ���ݣ�t_r_gcontent������Ʒ��(t_r_reference)");
		int result[]= {0,0};//���巵�ؽ�������ݵ�һֵ��ʾ�ɹ�ɾ���ĸ������ڶ���ɾ��ʧ�ܵĸ�����
		Set delSet = oldKeyIds.keySet();
		for(int i=0;i<failureChecked.size();i++)//���û��ͨ������Ϸ����ֹɾ����
		{
			delSet.remove(failureChecked.get(i));
			
		}
		
		// �ɹ�ɾ���ļ�����
		int count = 0;
		for(Iterator it =delSet.iterator();it.hasNext();)
		{
			
			String gameId = (String)oldKeyIds.get((String)it.next());
			Searchor searchor2 = new Searchor();
			// ��Ҫ�ݹ��ѯ
			searchor2.setIsRecursive(true);
			searchor2.getParams().add(
					new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, gameId));
			
			try
			{
				List refList = categoryRoot.searchNodes(
						RepositoryConstants.TYPE_REFERENCE, searchor2, null);
				// ����Ʒȫ���¼�
				for (int j = 0; j< refList.size(); j++)
				{
					ReferenceNode ref = (ReferenceNode) refList.get(j);
					categoryRoot.delNode(ref);
					
					//��Ʒ���Ż� add by aiyan 2013-04-23
					ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
					String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
		            if(SSMSDAO.getInstance().isAndroidCategory(categoryRoot.getId(), rootCategoryId)){
		            	//PPMSDAO.addMessagesStatic(MSGType.RefModifyReq,null,ref.getGoodsID()+":9");//��Ʒ�¼�
		    	    	//����˵Ҫ�����¼���Ϣ���,����ע������ģ��������¡� add by aiyan 2013-04-27
//		    	    	Goodsid	����	String	reference���е�goodsid
//		    	    	Categoryid	��ѡ	String	����categoryid���½�ʱ������
//		    	    	Id	��ѡ	String	����categoryid��Ӧ��Id���½�ʱ������
//		    	    	Refnodeid	��ѡ	String	Ӧ��ID���½�ʱ������
//		    	    	Sortid	��ѡ	String	�����ֶΣ��½�ʱ������
//		    	    	Loaddate	��ѡ	String	����ʱ�䣬�½�ʱ������
//		    	    	Action	����	String	0���½�
//		    	    	9��ɾ��
//		    	    	Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
		            	PPMSDAO.addMessagesStatic(MSGType.RefModifyReq,null,ref.getGoodsID()+"::::::9");//ɾ����Щ��ѡ��Ϳ��Կ��ַ����ˡ��Ǻ�
		            }
		            // end ��Ʒ���Ż�
					
				}
				// һ�α������
				categoryRoot.saveNode();

				GAppGame game = (GAppGame) Repository.getInstance().getNode(gameId,
						RepositoryConstants.TYPE_APPGAME);

				String contentid = game.getContentID();
				
				// ������Դ�����ɾ�����ݽӿڽ�������ɾ��
				contentRoot.delNode(game);
				contentRoot.saveNode();
				
				//��Ʒ���Ż� add by aiyan 2013-04-23
	            //if(androidContentSet.contains(contentid)){
	            	PPMSDAO.addMessagesStatic(MSGType.ContentModifyReq,null,contentid+":9");//Ӧ�����ߣ�����Ϣ��
	            	LOG.info("�¼����ݱ��е���Ϸ����");
	            //}
	            // end ��Ʒ���Ż�
	            
				GameSyncDAO.getInstance().delGameService(game.getIcpServId());

				// ��Ҫɾ����Դ��������ͼƬ��Ϣ��
				//deleteRemotePic(game.getIcpServId()); ������㲻���ġ�
				if (LOG.isDebugEnabled())
				{
					LOG.debug("ɾ������Ϸ�ɹ���gameId=" + gameId);
				}
                
                try
                {
                    DataSyncDAO.getInstance().addContentIdHis(contentid, SyncDataConstant.DEL_CONTENT_TYPE_GAME);
                }
                catch (DAOException e)
                {
                    throw new BOException("�����¼�ʱ����������Ӧ�ü�¼�����", e);
                }
                
				count++;
			} catch (Exception e)
			{
				LOG.error("ɾ����Ϸ����gameId=" + gameId, e);
			}
			

		}
		result[0]=count;
		result[1]=delSet.size()-count;
		return result;
	}
	
	private int[] delOldTWGame(){
		LOG.debug("��ʼ����ͼ����Ϸ������");
		int result[]= {0,0};//���巵�ؽ�������ݵ�һֵ��ʾ�ɹ�ɾ���ĸ������ڶ���ɾ��ʧ�ܵĸ�����
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
			LOG.error("com.aspire.dotcard.basegame.delOldTWGameû����sql.properties���á�",e1);
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
					LOG.error("��Ϸͼ��ɾ�������ݳ���",e);
					result[1]+=(end-start);
				}
			}
			
			start = end;
		}while(end<size);
		return result;
	}
	
	private int[] delOldPKGGameRef(){
		int result[]= {0,0};//���巵�ؽ�������ݵ�һֵ��ʾ�ɹ�ɾ���ĸ������ڶ���ɾ��ʧ�ܵĸ�����
		LOG.debug("��ʼ������Ϸ����Ʒ��");
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
            LOG.error("��Ϸ����Ʒɾ�������ݳ���", e);
            
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
