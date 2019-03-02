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
	 * ��ǰ���ݿ�game����Ϸid�ļ��ϡ�key ��ʾ��Ϸ��������ʹ��BaseGameKeyVO���ʾ��value��־��Ϸ���µ�״̬��
	 * 0��ʾ��ͬ��ǰ�����״̬��1��ʾ����ͬ��������ֵ��2��ʾ����ͬ�����µ�ֵ.
	 * ��Map�������̰߳�ȫ�ġ�
	 */
	private static Map oldGameMap;
	/**
	 * ĿǰMMϵͳ���ն˿⡣devicename��brand��ӳ��
	 */
	private static HashMap deviceNameMap;
	/**
	 * ��ȡ��Ϸ�ļ���Ŀ¼��·��
	 */
	private String gameFileRootPath;
	// ��Դ���ݴ洢����ĸ��ڵ�
	private static Category contentRoot;// ���ڻ���
	// ���ڻ���,���ܵĸ�����701�ڵ�
	private static Category categoryRoot;
	// �ϼܹ���Ϸ�Ļ��ܻ��棬��Ҫʱ�̰߳�ȫ�ġ�
	private static Map cateCache = Collections.synchronizedMap(new HashMap());
	private static List TACTICLIST;
	
	/**
	 * ������Ϸ����ͬ������
	 */
	private static Map gameTacticMap;
	
	//������Ϸ ���ݶ������� ͬ�����Կ���
	private String gameTatictype;
	/**
	 * ����Ϸ�ն������UA��Ϣ�ļ��ı��뷽ʽ��
	 */
	private String fileEncoding;

	/**
	 * ����ʼǰ��Ҫ�ѵ�ǰ���ݵ���Ϸ��ȡ���������ڱȽ�
	 */
	public void prepareData() throws BOException, DAOException
	{
		GameSyncTools.getInstance().initGameCateMapping();
		GameSyncTools.getInstance().initGameUAMapping();
		TACTICLIST = GameSyncTools.getInstance().getSyncTactic();
		oldGameMap = GameSyncTools.getInstance().getAllBaseGames();
		//ȡ���ݿ���֧�ֵ��ն�����DeviceDAO
		deviceNameMap = DeviceDAO.getInstance().getDeviceNameMapBrand();	
		//��ȡ������Ϸ������Ʒ��������ͬ������
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
		//ɾ����ѹ�����ļ���
		IOUtil.deleteFile(new File(gameFileRootPath));

	}

	public int dealDataRecrod(DataRecord record)
	{
		//���һ��ֵ���浱ǰ��������������Ϊ���ڶ��߳��£�������־�Ĳ鿴��
		Object lineNumber=record.get(record.size());
		if(logger.isDebugEnabled())
		{
			logger.debug("��ʼ�����"+lineNumber+"����Ϸ���ݡ�");
		}
		//��Ҫ��ȷ������Ϸ������
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
			// ��ѹ����Ϸ��ͼƬ��Ϣ��
			String pictureRootPath = gameFileRootPath + File.separator + icpservId;
			setGamePic(game, pictureRootPath);
			// ��ȡ�ն��������Ϣ
			List deviceList = getDeviceNames(pictureRootPath + ".ua", icpservId);
//			if(deviceList == null || deviceList.size()<=0){
//				throw new BOException("������Ϸ�������Ϊ�գ�����⣻basegame  device is null");
//			}
			setDeviceNameAndBrand(deviceList, game);
			// �ȶ�,id��valueֵΪ0��ʾ����һ�β���
			if (oldGameMap.containsKey(gameKey.getIcpServid()))
			{

				if (deviceList == null || deviceList.size() <= 0)
				{
					// throw new BOException("������Ϸ�������Ϊ�գ�����⣻basegame device is
					// null");
					logger.error("������Ϸ�������Ϊ�գ�����⣻basegame  device is null"
							+ gameKey.getIcpServid());
					return DataSyncConstants.SUCCESS_UPDATE;
				}
				else
				{
					// ��Ҫ��ȡϵͳ
					gameKey = (BaseGameKeyVO) oldGameMap.get(gameKey.getIcpServid());
					if (gameKey.getStatus() == 0)// ��ʾ״̬���ȵ�һ�θ��¡�
					{
						if (logger.isDebugEnabled())
						{
							logger.debug("��Ҫ���»�����Ϸ:icpcode=" + gameKey.getIcpCode()
									+ ",icpservid=" + gameKey.getIcpServid());
						}

						game.setId(String.valueOf(gameKey.getId()));
						game.save();
						// oldGameMap.put(gameKey, "2");
						gameKey.setStatus(2);
						// ����ҵ����Ϣ
						GameSyncTools.getInstance().saveGameService(servo);

						if (gameTatictype.equals("true"))
						{
							// ����ͬ������
							if (gameKey.getExpPrice() != game.getExpPrice()
									|| !gameKey.getAppcateName().equals(
											game.getAppCateName())
									|| !gameKey.getChargeTime().equals(
											game.getChargeTime())
									|| gameKey.getOnlineType() != game.getOnlineType())
							{// �ؼ��ʷ��б�� ���� ���������б�� ���� ���� �����б�������ߵ������� �б��
								if (gameKey.getExpPrice() != game.getExpPrice())
								{// �ؼ��ʷѱ��
									if (gameKey.getExpPrice() == 0)
									{// ԭ���Ƿ� �ؼ� ��Ϊ�ؼۣ��¼����л��ܸ���Ʒ���ϼ��ؼ�ר��
										deleteRefrenceAllCategory(gameKey.getId());
										logger
												.debug("---------���ؼ۱�Ϊ�ؼۣ��¼����л��ܸ���Ʒ�����Զ������ϼ��ؼ�ר��--------");
										// �ϼ� �ؼ�ר�� ���Զ����¹������
									}
									else if (game.getExpPrice() == 0)
									{// ԭ�����ؼۣ���Ϊ ���ؼۣ�������Ʒ�ϼ�
										if (game.getChargeTime().equals("1")
												&& game.getOnlineType() == 1)
										{// ������Ʒ
											logger
													.debug("-----------------�ؼ۱�����޸��ϼܵ�����Ʒ����--------");
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
									// �ǵ�����Ʒ���Ҳ��� �ؼ۲���
									// ���������б�����¼�ԭ���ķ�����ܣ��ϼ��µĶ����������
									String cid = (String) gameTacticMap.get(gameKey
											.getAppcateName());
									if (cid != null && !cid.equals(""))
									{// �¼ܾɷ��������Ʒ
										deleteRefrenceAndCategory(game.getId(), cid);
									}
									logger
											.debug("-----------------�������������ϼܵ�����Ʒ�µĶ�������--------");
									checkAndAddGameCategory(game, gameTacticMap);
								}
								if (!gameKey.getChargeTime().equals(game.getChargeTime()))
								{ // ���� �����б��
									if (game.getExpPrice() == 0)
									{// �����ؼ۵�,��������
										if (gameKey.getChargeTime().equals("2")
												&& game.getChargeTime().equals("1"))
										{
											// ԭ�������棬ת�ɵ��������Ҳ����ؼ۵ģ�Ҫ�ϼܵ����������
											// ���ؼ� ������Ʒ��������Ʒ��������ϼ�
											logger
													.debug("-----------------Ӧ�� ԭ�������棬ת�ɵ��������Ҳ����ؼ۵ģ������ϼܵ�����Ʒ����--------");
											checkAndAddGameCategory(game, gameTacticMap);
										}
										if (gameKey.getChargeTime().equals("1")
												&& game.getChargeTime().equals("2"))
										{
											// ԭ���ǵ�����ת�����棬���Ҳ����ؼ۵ģ�Ҫ�¼ܵ����������
											// ���ؼ� ������Ʒ��������Ʒ��������¼�

											String cid = (String) gameTacticMap
													.get(gameKey.getAppcateName());
											if (cid != null && !cid.equals(""))
											{// �¼ܷ��������Ʒ
												logger
														.debug("-----------------Ӧ�� ԭ���ǵ�����ת�����棬���Ҳ����ؼ۵ģ��¼ܵ�����Ʒ����--------");
												deleteRefrenceAndCategory(game.getId(),
														cid);
											}
										}
									}

								}
								if (gameKey.getOnlineType() != game.getOnlineType())
								{// -���� �����б��
									if (game.getExpPrice() == 0)
									{// �����ؼ۵�
										if (gameKey.getOnlineType() == 1
												&& game.getOnlineType() == 2)
										{
											// ԭ���������ߵ���Ӧ�ã���Ϊ ���Σ���Ҫ�¼� ԭ���ĵ������������Ʒ
											String cid = (String) gameTacticMap
													.get(gameKey.getAppcateName());
											if (cid != null && !cid.equals(""))
											{// �¼ܷ��������Ʒ
												logger
														.debug("-----------------ԭ���������ߵ���Ӧ�ã���Ϊ ���Σ���Ҫ�¼� ԭ���ĵ������������Ʒ--------");
												deleteRefrenceAndCategory(game.getId(),
														cid);
											}
										}
										if (gameKey.getOnlineType() == 2
												&& game.getOnlineType() == 1)
										{
											// ԭ���������Σ���Ϊ ���ߵ���Ӧ�ã���Ҫ�ϼܵ������������Ʒ
											logger
													.debug("-----------------ԭ���������Σ���Ϊ ���ߵ���Ӧ�ã���Ҫ�ϼܵ������������Ʒ--------");
											checkAndAddGameCategory(game, gameTacticMap);
										}
									}
								}
								/*
								 * // �ؼ��б��
								 * logger.debug("�ؼ��б��,gameKey.getExpPrice()="+gameKey.getExpPrice()+";game.getExpPrice()="+game.getExpPrice());
								 * if (gameKey.getExpPrice() == 0) { //
								 * ����ͨ��Ʒ����ؼ���Ʒ�����¼�ԭ���� ������Ʒ/���棻�ϼ��ؼ�ר�� if
								 * (game.getChargeTime().equals("1")) {
								 * if(game.getOnlineType()== 1){ // �¼ܵ�����Ʒ���� //
								 * ��ȡ��Ӧ��ԭ���ϼܵĻ��ص�����Ϸ�����������ID String cid =
								 * (String) gameTacticMap.get(game
								 * .getAppCateName()); // ��������ID
								 * 
								 * Category category = (Category)
								 * Repository.getInstance().getNode(cid,
								 * RepositoryConstants.TYPE_CATEGORY);
								 * 
								 * if (cid != null && !cid.equals("")) {
								 * deleteRefrenceAndCategory(game.getId(), cid); }
								 * else { logger.error("cid is null "); } }else
								 * if(game.getOnlineType()== 2){ //����
								 * if(netCategoryId != null &&!
								 * netCategoryId.equals("")){
								 * deleteRefrenceAndCategory(game.getId(),
								 * netCategoryId); } } // �ϼ� �ؼ�ר�� if
								 * (expCategoryId != null &&
								 * !expCategoryId.equals("")) { //
								 * ��gcת����Ӧ�����ݻ�ȡӦ�÷������� GAppContent app =
								 * (GAppContent) game; addCategory(app,
								 * expCategoryId); } else {
								 * logger.error("expCategoryId is null "); } }
								 * else if (game.getChargeTime().equals("2"))
								 * {// �¼����� if (trainCategoryId != null &&
								 * !trainCategoryId.equals("")) {
								 * deleteRefrenceAndCategory(game.getId(),
								 * trainCategoryId); } else {
								 * logger.error("trainCategoryId is null "); } //
								 * �ϼ� �ؼ�ר�� if (expCategoryId != null &&
								 * !expCategoryId.equals("")) { //
								 * ��gcת����Ӧ�����ݻ�ȡӦ�÷������� GAppContent app =
								 * (GAppContent) game; addCategory(app,
								 * expCategoryId); } else {
								 * logger.error("expCategoryId is null "); } } }
								 * else if (game.getExpPrice() == 0) { //
								 * ���ؼ���Ʒ��Ϊ��ͨ��Ʒ���¼�ԭ�����ؼ�ר�����ϼܵ�����Ʒ/���� // �¼� �ؼ�ר�� if
								 * (expCategoryId != null &&
								 * !expCategoryId.equals("")) {
								 * deleteRefrenceAndCategory(game.getId(),
								 * trainCategoryId); } else {
								 * logger.error("expCategoryId is null "); } if
								 * (game.getChargeTime().equals("1")) {// ���ؼƷ�
								 * if( game.getOnlineType()== 1){ //
								 * ������Ʒ���ϼܵ�����Ʒ����
								 * logger.debug("-----------------�ϼܵ�����Ʒ����--------");
								 * checkAndAddGameCategory(game, gameTacticMap);
								 * }else if(game.getOnlineType()== 2){ //����ר��
								 * if(netCategoryId != null &&
								 * !netCategoryId.equals("")){ //
								 * ��gcת����Ӧ�����ݻ�ȡӦ�÷������� GAppContent app =
								 * (GAppContent) game; addCategory(app,
								 * netCategoryId); } // }else{
								 * logger.error("game.getOnlineType() is illage
								 * "+game.getOnlineType()); } } else if
								 * (game.getChargeTime().equals("2")) {// �ϼ�����
								 * if (trainCategoryId != null &&
								 * !trainCategoryId.equals("")) { //
								 * ��gcת����Ӧ�����ݻ�ȡӦ�÷������� GAppContent app =
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
								// �ؼ�û�б��
								logger.debug("�ؼ�û�б�� ");
							}
						}
						else
						{
							logger.debug("gameTatictype ���� û�д� " + gameTatictype);
						}

						return DataSyncConstants.SUCCESS_UPDATE;

					}
					else
					{
						// value Ϊ2��ʾ�Ѿ����»����������ˣ������ļ����ظ�Ԫ��
						logger.error("�����ļ��г����ظ���Ϸ�����Ը����ݡ�icpcode:" + gameKey.getIcpCode()
								+ ",icpservid=" + gameKey.getIcpServid());
						return DataSyncConstants.FAILURE;
					}

				}
			}
			else
			{
				if(deviceList == null || deviceList.size()<=0){
					throw new BOException("������Ϸ�������Ϊ�գ�����⣻basegame  device is null");
				}
				// �����ھͲ���
				if (logger.isDebugEnabled())
				{
					logger.debug("��Ҫ����������Ϸ:icpcode=" + gameKey.getIcpCode()
							+ ",icpservid=" + gameKey.getIcpServid());
				}
				// ��һ�β���д�봴��ʱ��
				game.setCreateDate(PublicUtil.getCurDateTime());
				// �������ݵ����ݿ�
				contentRoot.addNode(game);
				contentRoot.saveNode();
				game.setCateName(GameSyncTools.getInstance().getMMCateName(
						game.getAppCateName()));
				checkAndAddCategory(game, TACTICLIST);
				GameSyncTools.getInstance().saveGameService(servo);
				// oldGameMap.put(gameKey, "1");
				gameKey.setStatus(1);// ��ʱ��������id����ΪҲ�ò���id
				oldGameMap.put(gameKey.getIcpServid(), gameKey);

				if (gameTatictype.equals("true"))
				{
					if (game.getExpPrice() <= 0)
					{
						// ���ؼ�
						if (game.getChargeTime().equals("1") && game.getOnlineType() == 1)
						{
							// ���ؼ� ������Ʒ��������Ʒ��������ϼ�
							logger.debug("-----------------Ӧ�������������ϼܵ�����Ʒ����--------");
							checkAndAddGameCategory(game, gameTacticMap);
						}
					}
					/*
					 * logger.debug("-----------------��ʼ���ص�����Ϸ�����ϼ�--------"); //
					 * ����ͬ������ if (game.getExpPrice() > 0) {// �ؼ���Ϸ���ϼ��ؼ�ר�� // �ϼ�
					 * �ؼ�ר��
					 * 
					 * if (expCategoryId != null && !expCategoryId.equals("")) {
					 * logger.debug("-----------------�ϼ� �ؼ�ר��--------"); //
					 * ��gcת����Ӧ�����ݻ�ȡӦ�÷������� GAppContent app = (GAppContent) game;
					 * addCategory(app, expCategoryId); } else {
					 * logger.error("expCategoryId is null "); } } else { if
					 * (game.getChargeTime().equals("1")) {if(
					 * game.getOnlineType()== 1){ // ������Ʒ���ϼܵ�����Ʒ����
					 * logger.debug("-----------------�ϼܵ�����Ʒ����--------");
					 * checkAndAddGameCategory(game, gameTacticMap); }else
					 * if(game.getOnlineType()== 2){ //����ר�� if(netCategoryId !=
					 * null && !netCategoryId.equals("")){ // ��gcת����Ӧ�����ݻ�ȡӦ�÷�������
					 * GAppContent app = (GAppContent) game; addCategory(app,
					 * netCategoryId); } }else{
					 * logger.error("game.getOnlineType() is illage
					 * "+game.getOnlineType()); }
					 * 
					 *  } else if (game.getChargeTime().equals("2")) {// �ϼ����� if
					 * (trainCategoryId != null && !trainCategoryId.equals("")) { //
					 * ��gcת����Ӧ�����ݻ�ȡӦ�÷������� GAppContent app = (GAppContent) game;
					 * addCategory(app, trainCategoryId); } else {
					 * logger.error("trainCategoryId is null "); } }
					 *  }
					 * 
					 */}

				return DataSyncConstants.SUCCESS_ADD;
			}
		} catch (Exception e)
		{
			//���ִ����쳣�����¼�ô��󣬷�ֹ���ɾ����ʱ��ɾ����
			if (oldGameMap.containsKey(gameKey.getIcpServid()))
			{
				gameKey = (BaseGameKeyVO) oldGameMap.get(gameKey.getIcpServid());
				gameKey.setStatus(5);//���ԭ�п��д�����Ҫ�޸���Ӧ��״̬��
			}
			else
			{
				gameKey.setStatus(5);
				oldGameMap.put(gameKey.getIcpServid(), gameKey);
			}
			logger.error("���������Ϸ�����쳣��icpcode=" + gameKey.getIcpCode() + ",icpservid="
					+ gameKey.getIcpServid(), e);
			return DataSyncConstants.FAILURE;
		}
	}

	/**
	 * ����ͼƬ����Ϣ����ͼƬ�ϴ�����Դ��������Ȼ��ѷ��ʵ�ַд���Ӧ�ֶΡ�
	 * @param game  ��Ϸ����
	 * @param pictureRootPath ͼƬ�Ĵ�ŵ�ַ��
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
			// ����ͼƬ����
			File pictureRootPathFile=new File(pictureRootPath);
			String picNames[] =pictureRootPathFile.list();
			//����logoͼƬ��ַ
			String logoPicPath=null;
			List detailedPicPathList=new  ArrayList();//ȥ��һ��logoͼƬ
			for(int i=0;i<picNames.length;i++)
			{
				String temp=pictureRootPath + File.separator+picNames[i];//�������ļ��ľ��Ե�ַ
				if(picNames[i].startsWith("logo"))
				{
					logoPicPath=temp;
				}else if(picNames[i].startsWith("pic"))
				{
					detailedPicPathList.add(temp);
				}
			}
			Collections.sort(detailedPicPathList);//Ĭ�ϰ�����ĸ��������
			//logoPicPath = pictureRootPath + File.separator + "logo.jpg";
			//����ϷͼƬ�ı���Ŀ¼��
			String saveDir=this.getPicSaveDir(icpservId);
			ftp=this.getFtpWithSaveDir(saveDir);
			
			game.setLOGO1(this.getPicURL(logoPicPath, 30, 30, ftp,saveDir));
			game.setLOGO2(this.getPicURL(logoPicPath, 34, 34, ftp,saveDir));
			game.setLOGO3(this.getPicURL(logoPicPath, 50, 50, ftp,saveDir));
			game.setLOGO4(this.getPicURL(logoPicPath, 65, 65, ftp,saveDir));

			//WWW����ͼ��ַ
			game.setWWWPropaPicture1(this.getPicURL(logoPicPath, 360, 274, ftp,saveDir));
			//WWW�б�Сͼ���ַ
			game.setWWWPropaPicture2(this.getPicURL(logoPicPath, 32, 32, ftp,saveDir));
			//WWW��׼չʾͼ
			game.setWWWPropaPicture3(this.getPicURL(logoPicPath, 107, 143, ftp,saveDir));

			game.setClientPreviewPicture1(this
					.getPicURL(logoPicPath, 150, 160, ftp,saveDir));
			game.setClientPreviewPicture2(this
					.getPicURL(logoPicPath, 210, 220, ftp,saveDir));
			game.setClientPreviewPicture3(this
					.getPicURL(logoPicPath, 180, 180, ftp,saveDir));
			game.setClientPreviewPicture4(this
					.getPicURL(logoPicPath, 240, 300, ftp,saveDir));
			//��������ͼƬ
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
			throw new BOException("ת��ͼƬ����icpservId="+icpservId,e);
		}
	}

	/**
	 * �¼�ָ��������ָ����Ʒ
	 * @param refid
	 * @param categoryid
	 */
	public void deleteRefrenceAndCategory(String  refid,String cid){
		Searchor searchor2 = new Searchor();
		// ��Ҫ�ݹ��ѯ
		
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
			// ����Ʒȫ���¼�
			for (int j = 0; j< refList.size(); j++)
			{
				ReferenceNode ref = (ReferenceNode) refList.get(j);
				categoryRoot.delNode(ref);
			}
			// һ�α������
			categoryRoot.saveNode();
		}catch (Exception e)
		{
			logger.error("�¼ܻ�����Ϸ����gameId=" + refid, e);
		}
		
			
	}
	/**
	 * �¼�ָ��������ָ����Ʒ
	 * @param refid
	 * @param categoryid
	 */
	public void deleteRefrenceAllCategory(String  refid){
		Searchor searchor2 = new Searchor();
		// ��Ҫ�ݹ��ѯ
		
		try
		{
			
			searchor2.setIsRecursive(true);
			searchor2.getParams().add(
					new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, refid));
			
			
			List refList = categoryRoot.searchNodes(
					RepositoryConstants.TYPE_REFERENCE, searchor2, null);
			// ����Ʒȫ���¼�
			for (int j = 0; j< refList.size(); j++)
			{
				ReferenceNode ref = (ReferenceNode) refList.get(j);
				categoryRoot.delNode(ref);
			}
			// һ�α������
			categoryRoot.saveNode();
		}catch (Exception e)
		{
			logger.error("�¼ܻ�����Ϸ����gameId=" + refid, e);
		}
		
			
	}
	
	/**
	 * ɾ���������ߵ���Ϸ����Ҫȥ����鲻ͨ������Ϸ��
	 * ��Ҫ��ȫ�����ݵ����ִ�С�
	 * @param List failureChecked ���ʧ�ܵ���Ϸ�б�
	 * @return int[] ���ݵ�һֵ��ʾ�ɹ�ɾ���ĸ������ڶ���ɾ��ʧ�ܵĸ�����
	 */
	public int[] deleteOldGame(List failureChecked)
	{
		int result[]= {0,0};//���巵�ؽ�������ݵ�һֵ��ʾ�ɹ�ɾ���ĸ������ڶ���ɾ��ʧ�ܵĸ�����
		List delList = new ArrayList();//��ɾ������Ϸid�б�
		for(int i=0;i<failureChecked.size();i++)//���û��ͨ������Ϸ����ֹɾ����
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
			// ֻ��valueΪ0�ű�ʾû�б���£�������
			if (vo.getStatus()==0)
			{
				delList.add(vo.getId());
			}
		}
		// �ɹ�ɾ���ļ�����
		int count = 0;
		for (int i = 0; i < delList.size(); i++)
		{
			String gameId = (String) delList.get(i);
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
				}
				// һ�α������
				categoryRoot.saveNode();

				GAppGame game = (GAppGame) Repository.getInstance().getNode(gameId,
						RepositoryConstants.TYPE_APPGAME);

				String contentid = game.getContentID();
				
				// ������Դ�����ɾ�����ݽӿڽ�������ɾ��
				contentRoot.delNode(game);
				contentRoot.saveNode();

				GameSyncDAO.getInstance().delGameService(game.getIcpCode(),
						game.getIcpServId());

				// ��Ҫɾ����Դ��������ͼƬ��Ϣ��
				deleteRemotePic(game.getIcpServId());
				if (logger.isDebugEnabled())
				{
					logger.debug("ɾ������Ϸ�ɹ���gameId=" + gameId);
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
				logger.error("ɾ����Ϸ����gameId=" + gameId, e);
			}
			

		}
		result[0]=count;
		result[1]=delList.size()-count;
		return result;
	}

	/**
	 * ƥ�������ϼܲ��ԡ�ֻ��ƥ������е��������ԡ�1.�������ͣ�2 ��������
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
					logger.error("Ŀǰû�и÷��࣬�޷�����ϼܡ�id=" + categoryID);
					throw new BOException("�����ڵ�ǰ���ܣ�id=" + categoryID);
				}
				cateCache.put(pCate.getId(), pCate);

			}
			addCategory(gc, categoryID);
			logger.debug("DataSyncBO.checkAndAddCategory(). contentType="+gc.getContentID()+"-------end-----------" + gc.getType());
		}
	}

	/**
	 * ���ص�����Ϸ ƥ�������ϼܲ��ԡ�ֻ��ƥ������е��������ԡ�1.�������ͣ�2 ��������
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
		// ��gcת����Ӧ�����ݻ�ȡӦ�÷�������
		GAppContent app = (GAppContent) game;

		// �����������м��ͨ�����������ϼܵ����Զ�Ӧ�Ļ��ܻ�����
		Category pCate = (Category) cateCache.get(categoryID);
		if (pCate == null)// ��������Ҫ���浽������
		{
			pCate = (Category) Repository.getInstance().getNode(categoryID,
					RepositoryConstants.TYPE_CATEGORY);
			if (pCate == null)
			{
				logger.error("Ŀǰû�и÷��࣬�޷�����ϼܡ�id=" + categoryID);
				throw new BOException("�����ڵ�ǰ���ܣ�id=" + categoryID);
			}
			cateCache.put(pCate.getId(), pCate);

		}
		addCategory(app, categoryID);

	}
	/**
	 * �������ϼܵ����ܻ�����
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
		} catch (Exception e)
		{
			throw new BOException("�����ϼܳ���", e);
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
	 * ��ͼƬ����һ���ĸ�ʽ�ϴ���ftp�ϣ����ҷ��ػ�ȡ��ͼƬ��url��ַ
	 * 
	 * @param srcPath
	 *            ԭͼƬ���Ե�ַ
	 * @param toWidth Ŀ��ͼƬ�Ŀ��
	 * @param toHeight Ŀ��ͼƬ�ĸ߶�
	 * @param FTPClient ��Դ����������
	 * @param saveDir ͼƬ����Ŀ¼
	 * @return ��ͼƬ����Դ�������Ϸ��ʵ�ַ
	 * @throws BOException ����ʧ��
	 */
	private String getPicURL(String srcPath, int toWidth, int toHeight, FTPClient ftp,String saveDir)
			throws Exception
	{
		String toFormat="png";//ת����Ŀ��ͼƬ��ʽ
		int lastSlashIndex = srcPath.lastIndexOf(File.separator);
		String srcDir = srcPath.substring(0, lastSlashIndex + 1);// Դ�ļ���Ŀ¼
		String disFileName = srcDir + toWidth + "_" + toHeight+
				  srcPath.substring(lastSlashIndex + 1,srcPath.length()-3)+toFormat;
		try
		{
			DataSyncTools.scaleIMG(srcPath, disFileName, toWidth, toHeight,toFormat);
		} catch (Exception e)
		{
			throw new BOException("ת��ͼƬ����srcPath=" + srcPath + ",toWidth=" + toWidth
					+ ",toHeight=" + toHeight);
		}
		return uploadPic(disFileName,ftp,saveDir);
	}
	/**
	 * �ϴ�ָ����ͼƬ��ftp������
	 * @param ftp ��Դ������
	 * @param saveDir ͼƬ����Ŀ¼��
	 * @return ��ȡ���ʸ��ϴ����ļ���URL���ʵ�ַ
	 * @throws FTPException 
	 * @throws IOException 
	 */
	private String uploadPic(String picPath,FTPClient ftp,String saveDir) throws IOException, FTPException
	{
		String saveFileName = getRemoteFileName(picPath);
		ftp.put(picPath, saveFileName, false);
		StringBuffer visitedUrl = new StringBuffer(GameConfig.sourceServerVisit);
		// ������ʸ�ͼƬ��url
		visitedUrl.append(saveDir);
		visitedUrl.append('/');
		visitedUrl.append(saveFileName);
		return visitedUrl.toString();
	}

	/**
	 * 
	 * @param saveDir ����ϷͼƬ�ı���Ŀ¼
	 * @return  ���ؽ����Ŀ¼���ftp����
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
	 * ��ȡ��ͼƬ�洢Ŀ¼��ͼƬ��Ź���Ϊ��$ftpRoot/xx/icpservId/*.jpg
	 * ����xxֵ����icpservId%10000��ֵ������ҪΪ�˷�ֹһ���ļ���Ŀ¼����
	 * @param icpservId
	 * @return ͼƬ�Ĵ��Ŀ¼
	 */
	private String getPicSaveDir(String icpservId)
	{
		String starDir = "00";
		try
		{
			starDir = String.valueOf(Long.parseLong(icpservId) / 10000);
		} catch (NumberFormatException e)
		{
			// ת��ʧ��Ĭ��Ŀ¼��Ϊ00
		}
		return starDir+"/"+icpservId;
	}
	/**
	 * ɾ��ָ����Ϸ��ͼƬ��
	 * 
	 * @param icpservId
	 *            ��Ϸ��ҵ�����
	 * @throws Exception
	 *             ɾ��ʧ�ܣ����߲����ڸ���Ϸ��ҵ������Ŀ¼
	 */
	public void deleteRemotePic(String icpservId)
	{
		//�Ȳ�Ҫִ��ɾ����������Ϊ�漰ͬ�������⡣ɾ���󵽵ڶ���ͬ�����ʱ�䣬�Ż�չʾ��������
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
				// ת��ʧ��Ĭ��Ŀ¼��Ϊ00
			}

			ftp.chdir(starDir);
			ftp.chdir(icpservId);
			// ɾ�����е�ͼƬ�ļ�
			String files[] = ftp.dir();
			for (int i = 0; i < files.length; i++)
			{
				ftp.delete(files[i]);
			}
			// ɾ������ϷĿ¼
			ftp.chdir("..");// ������Ҫ���ظ�һ��Ŀ¼��
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
			logger.error("ɾ��ͼƬĿ¼����icpservId=" + icpservId, e);
			//���쳣�����׳���
			//throw new BOException("ɾ��ͼƬĿ¼����icpservId=" + icpservId, e);
		}*/
	}

	/**
	 * ��鵱ǰ·�����Ƿ���ָ����Ŀ¼�����û���򴴽����÷�����Ҫͬ������ֹ���̳߳����쳣��
	 * 
	 * @param ftp
	 *            FTPClient
	 * @param dir
	 *            Ŀ¼����
	 * @throws IOException
	 * @throws FTPException
	 */
	synchronized private void checkAndCreateDir(FTPClient ftp, String dir)
			throws IOException, FTPException
	{
		/*if (ftp.dir(dir).length == 0)// ʹ�ø÷������ֻ������⡣����ʹ��������ԭʼ�İ취��
		{
			ftp.mkdir(dir);
		}*/
		String subFiles[] = ftp.dir();
		boolean isFound = false;//��Ŀ¼�Ƿ����dirĿ¼
		for (int i = 0; i < subFiles.length; i++)
		{
			if (subFiles[i].equals(dir))
			{
				isFound = true;
				break;
			}
		}
		if (!isFound)//�����ڸ�Ŀ¼���ʹ�����
		{
			ftp.mkdir(dir);
		}
	}

	/**
	 * ���ݱ����ļ��ľ���·������ȡԶ�̷������ϱ�����ļ���
	 * 
	 * @param localFileName
	 *            �����ļ��ľ���·��
	 * @return ftp�������ϱ�����ļ���
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
            // ����deviceName
            // ���ڱ����޷���mmƽ̨�ҵ�������Ϣ��
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
                    illegalDeviceName.append(lineText);// ����Ƿ���ua��Ϣ��
                    illegalDeviceName.append(',');
                }
            }
            // ɾ�����һ������
            if (illegalDeviceName.length() > 1)
            {
                illegalDeviceName.deleteCharAt(illegalDeviceName.length() - 1);
                logger.error("ҵ�����Ϊ��" + icpservId + "����Ϸ����������MM�޷�ʶ���	UA��Ϣ��"
                             + illegalDeviceName);
                logger.error("���ڻ���ϵͳt_game_ua_mapping�����������޷�ʶ���UA��Ϣ");
            }

        }
        catch (Exception e)
        {
            throw new BOException("��ȡua�ļ���Ϣ��Ϣ����icpservId=" + icpservId, e);
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
	 * ת����Ϸ��Ϣ�� ��Ϸ�����Ϸҵ���ࡣ
	 * 
	 * @param record
	 *            һ����Ϸ��Ϣ
	 * @param servo
	 *            ��Ϸҵ����Ϣ
	 * @param game
	 *            ��Ϸ������Ϣ
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
		// �������Ϸ��ҵ�����
		servo.getIcpServid();
		// 4 serviceName
		servo.setServName((String) record.get(4));
		game.setName(servo.getServName());
		// 5 ��Ʒ���
		servo.setServDesc((String) record.get(5));
		// 6 serviceDesc
		game.setIntroduction((String) record.get(6));
		// 7 �������
		temp = (String) record.get(7);
		if ("".equals(temp.trim()))
		{
			temp = "����ϷΪ�ٱ��侫Ʒ��Ϸ��������Ϸ������ǰ��δ�еĳ���ɣ�";
		}
		game.setHandBook(temp);
		// 8 ҵ������
		servo.setServType(Integer.parseInt((String) record.get(8)));
		// 9 ֧����ʽ.Ŀǰֻ֧�ֻ��ѵ���ʽ��
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
		// 10��Ʒ��Чʱ�䡣 ʱ���ʽΪyyyy-MM-dd
		temp = (String) record.get(10);
		game.setMarketDate(temp.trim() + " 00:00:00");
		// 10��Ʒ��Чʱ�䡣Ŀǰû��ʹ�ø��ֶ�
		// 12 ҵ��״̬
		// 13 �ʷ�
		servo.setMobilePrice(Integer.parseInt((String) record.get(13)) * 10);
		// 15 ��Ʒlogo
		// vo.setServiceLogoId((String)record.get(15));
		// 16 ���URL������url��
		temp=(String) record.get(16);  //������channelId=xxxxx����channelId=12044000
		int channelIdIndex=temp.indexOf("&channelId=");
		if(channelIdIndex!=-1)//������channelId�����򲻴���
		{
			int isLast=temp.indexOf('&', channelIdIndex+1);
			if(isLast!=-1)//�ж�channelId�Ƿ������һ������
			{
				temp=temp.substring(0,channelIdIndex)+"&channelId=12044000"+temp.substring(isLast);
				
			}else
			{
				temp=temp.substring(0,channelIdIndex)+"&channelId=12044000";
			}
		}
		game.setGameVideo(temp);
		// 17 ֧�ֵ��ն�����
		// vo.setServiceUA((String)record.get(17));
		// 18 �Ʒѷ�ʽ
		temp = (String) record.get(18);
		if (temp.equals("2"))//������󸶷�
		{
			game.setChargeTime("2");
			servo.setChargeType("02");
			game.setOnlineType(1);	//����Ӧ��
		}
		else if (temp.equals("5"))//���ؼ����ѵ�����Ʒ
		{
			game.setChargeTime("1");
			servo.setChargeType("02");
			game.setOnlineType(1);	//����Ӧ��
		}else if (temp.equals("3"))//������Ϸ��������
		{
			game.setChargeTime("1");
			servo.setChargeType("02");
			game.setOnlineType(2);	//����Ӧ��
		}
		else
		{
			throw new BOException("��֧�ִ˼Ʒѷ�ʽ�쳣���Ʒѷ�ʽ=" + temp);
		}
		
		// 14 �ʷ�����  �����������Ʒѵ�Ӧ�ã��ʷ�������Ҫ�Լ�ƴд
		//servo.setChargDesc((String) record.get(14));
		if(game.getChargeTime().equals("2"))
		{
			String desc="���ؼƷ�0Ԫ/�Σ�����Ʒ�"+servo.getMobilePrice()/1000.00+"Ԫ/��";
			servo.setChargDesc(desc);//����ƴװ��
		}else
		{
			servo.setChargDesc((String) record.get(14));
		}
		
		//add by dongke  �ؼ��ʷ�
		game.setExpPrice(Integer.parseInt((String) record.get(24)) * 10);
		
		// 19 gametype
		// 20 ��Ϸ��������
		game.setAppCateName(GameSyncTools.getInstance().getMMCateName(
				(String) record.get(20)));
		// 21 ҵ���ʶ
		servo.setServFlag(Integer.parseInt((String) record.get(21)));
		// 22 ҵ���ƹ㷽ʽ
		servo.setPtypeId(Integer.parseInt((String) record.get(22)));
		// 23��Ϸ������
		// �������Ϣ��

		//������Ϸ���ࡣ
		game.setCateName("������Ϸ");
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
		game.setLanguage("1");//���ü������ġ�
		game.setKeywords('{' + servo.getServDesc() + '}');//���ùؼ���
		game.setServAttr("G");
		game.setPvcID("0000");
		game.setCityID("0000");

		//������Ʒ������Ҫ���ֶΡ�
		game.setCompanyID(game.getIcpCode());
		game.setProductID(game.getIcpServId());
		game.setContentID(game.getIcpServId());
		servo.setContentid(game.getContentID());
	}

	/**
	 * ��ȡ��Ϸ�ļ���Ŀ¼��·����
	 * 
	 * @param filePathRoot
	 */
	public void setGameFileRootPath(String gameFileRootPath)
	{
		this.gameFileRootPath = gameFileRootPath;
	}

	/**
	 * ���ø���Ϸ��֧���ն����ƣ����ն�Ʒ��
	 * @param deviceNameList ����֧���ն������б�
	 * @param game ��Ϸ����
	 */
	private void setDeviceNameAndBrand(List deviceNameList, GAppGame game)
	{
        // ��ȡ��ǰϵͳ֧�ֵĻ������ƣ���Ʒ��
        List supportedDeviceNameList = new ArrayList();
        List supportedDeviceIDList = new ArrayList();

        HashMap brandMap = new HashMap();// ��Ҫȥ�ء�
        HashMap deviceMap = new HashMap();// ������Ҫȥ��
        // ���ڱ����޷���mmƽ̨�ҵ�������Ϣ��
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
            logger.error("��ǰMM�ն˿��devicename�����ڡ�deviceName="
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
	 * ��list�е�����ƴװΪ {devicename},{devicename}�� ����ʽ
	 * @param list ����deviceName�ļ���
	 * @return ���磺{devicename1},{devicename2}�� ���ַ���
	 */
	private String list2String(Collection collection)
	{
		StringBuffer sb = new StringBuffer();
		Iterator iterator = collection.iterator();
		boolean dotFlag = false;//��һ�β���Ҫ���붺��
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
