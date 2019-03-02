package com.aspire.dotcard.basegame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.dao.BaseComicDAO;
import com.aspire.dotcard.gcontent.DeviceDAO;
import com.aspire.dotcard.gcontent.GAppGame;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTools;
import com.aspire.ponaadmin.web.datasync.implement.game.GameConfig;
import com.aspire.ponaadmin.web.datasync.implement.game.GameSyncTools;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;

public class ContentDataDealer implements DataDealer
{
	private JLogger LOG=LoggerFactory.getLogger(ContentDataDealer.class);
	/**
	 * ��ȡ��Ϸ�ļ���Ŀ¼��·��
	 */
	private String gameFileRootPath;
	
	/**
	 * ����Ϸ�ն������UA��Ϣ�ļ��ı��뷽ʽ��
	 */
	private String fileEncoding;
	/**
	 * ĿǰMMϵͳ���ն˿⡣devicename��brand��ӳ��
	 */
	private static HashMap deviceNameMap;
	
	private static HashMap seriesidMap;
	
	public void clearDirtyData()
	{
		//ɾ����ѹ�����ļ���
		IOUtil.deleteFile(new File(gameFileRootPath));
	}

	public int dealDataRecrod(DataRecord record)
	{
		//LOG.info("��ʼ������Ϣ��");
		try{
			GameContent content = new GameContent();			
			GAppGame game = new GAppGame();
			turnFieldsToEntity(record, content,game);
			// ��ѹ����Ϸ��ͼƬ��Ϣ��
			String pictureRootPath = gameFileRootPath + File.separator + content.getContentCode();
			//setGamePic(game, pictureRootPath);
			if("1".equals(content.getContentType())){//1:������Ϸ��2:ͼ����Ϸ��3:��Ϸ��������Ϸ���������£������ǲ��ṩUA�ģ����Ǹ��ݸð��µĵ���������������
				// ��ȡ�ն��������Ϣ
				List deviceList = getDeviceNames(pictureRootPath + ".ua", content.getContentCode());
				setDeviceNameAndBrand(deviceList, game);
				
				//��Ʒ���Ż��Ķ�������������Ҫ�Ѹ����ݶ�Ӧ��android�µ��������ID��¼�������ŵ�t_a_content_seriesid
				setSeriesId(content.getContentCode(),deviceList);
			}
			GameSyncDAO.getInstance().insertGameContent(content,game);
			return DataSyncConstants.SUCCESS_ADD;
		}catch(Exception e){
			LOG.error("���������Ϸ���ݳ����쳣",e);
			return DataSyncConstants.FAILURE;
		}

	}
	

	
	private void setSeriesId(String contentCode,List deviceList) {
		// TODO Auto-generated method stub
		String devicename,seriesid;
		Set seriesidSet = new HashSet();
		for(int i=0;i<deviceList.size();i++){
			devicename = (String)deviceList.get(i);
			seriesid = (String)seriesidMap.get(devicename);
			if(seriesid!=null){
				if(seriesidSet.contains(seriesid)){//����˵ľͲ����ټ��ˡ�
					continue;
				}else{
					GameSyncDAO.getInstance().insertContentSeries(contentCode, seriesid);
					seriesidSet.add(seriesid);
				}
			}
		}
	}

	public void  delOldData() throws Exception{
		//String sql ="select count(1) from t_game_content where status=1";
		String sqlCode = "com.aspire.dotcard.basegame.ContentDataDealer.delOldData.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode,null);
		
		if(count>0){//ȷʵ�Ѿ������˱���ҵ������ݡ����ǿ���ɾ���׷���ǰ�������ˡ���������Ŀ�����»��ظ����ǿ��ļ�����û�е�������ݣ�
			//äĿɾ��֮ǰ�����ݣ��γ��Ż������ݵĿ����ԡ�
			
			TransactionDB tdb = null;
			try{
				//sql="delete from t_game_content where status=0";
				String sqlCode1 = "com.aspire.dotcard.basegame.ContentDataDealer.delOldData.DELETE";
				//sql="update t_game_content set status=0 where status=1";
				String sqlCode2 = "com.aspire.dotcard.basegame.ContentDataDealer.delOldData.UPDATE";
				
				//����ģʽ
				tdb = TransactionDB.getTransactionInstance();
				tdb.executeBySQLCode(sqlCode1, null);
				tdb.executeBySQLCode(sqlCode2, null);
				tdb.commit();

				//������ģʽ
//				//sql="delete from t_gamestop where status=0";
//				String sqlCode1 = "com.aspire.dotcard.basegame.StopDealer.delOldData.DELETE";
//				//sql="update t_gamestop set status=0 where status=1";
//				String sqlCode2 = "com.aspire.dotcard.basegame.StopDealer.delOldData.UPDATE";
//				BaseComicDAO.getInstance().executeBySQLCode(sqlCode1,null);
//				BaseComicDAO.getInstance().executeBySQLCode(sqlCode2,null);
			}catch(DAOException e){
				LOG.error(e);
			}finally{
				if(tdb!=null){
					tdb.close();
				}
			}
				
			
		}		
	}
	

	
	private void turnFieldsToEntity(DataRecord record,
			GameContent content,GAppGame game) {
		// TODO Auto-generated method stub
		content.setContentCode((String)record.get(1));
		content.setName(delSomeStr((String)record.get(2)));
		content.setShortName(delSomeStr((String)record.get(3)));
		content.setDesc((String)record.get(4));
		content.setCpId((String)record.get(5));
		content.setCpName((String)record.get(6));
		content.setOpera((String)record.get(7));
		content.setEffectiveDate((String)record.get(8));
		content.setInvalidDate((String)record.get(9));
		content.setLogo((String)record.get(10));
		content.setDeviceua((String)record.get(11));
		content.setGameTypeId((String)record.get(12));
		content.setFileSize((String)record.get(13));
		content.setContentType((String)record.get(14));
		content.setPkgType((String)record.get(15));
		content.setGamePool((String)record.get(16));
		content.setFreeDownloadNum((String)record.get(17));
		content.setChargeType((String)record.get(18));
		content.setScale((String)record.get(19));
		content.setPrice((String)record.get(20));
		game.setContentTag(content.getContentCode());
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"BaseGameInfoConfig");
		if(record.get(21) != null && !"".equals(record.get(21))){
			game.setLOGO1((String)record.get(21));
			game.setLOGO2((String)record.get(21));
			game.setLOGO3((String)record.get(21));
			game.setLOGO4((String)record.get(21));
		}else{
			game.setLOGO1(module.getItemValue("GameInfoAndroidPicDir"));
			game.setLOGO2(module.getItemValue("GameInfoAndroidPicDir"));
			game.setLOGO3(module.getItemValue("GameInfoAndroidPicDir"));
			game.setLOGO4(module.getItemValue("GameInfoAndroidPicDir"));
		}
		
		if(record.get(22) != null && !"".equals(record.get(22))){
			game.setLOGO5((String)record.get(22));
			game.setLOGO6((String)record.get(22));
		}else{
			game.setLOGO5(module.getItemValue("GameInfoAndroidPicDir"));
			game.setLOGO6(module.getItemValue("GameInfoAndroidPicDir"));
		}
		
		if(record.get(23) != null && !"".equals(record.get(23))){
			game.setPicture1((String)record.get(23));
		}else{
			game.setPicture1(module.getItemValue("GameInfoPicDir"));
		}
		
		if(record.get(24) != null && !"".equals(record.get(24))){
			game.setPicture2((String)record.get(24));
		}else{
			game.setPicture2(module.getItemValue("GameInfoPicDir"));
		}
		
		if(record.get(25) != null && !"".equals(record.get(25))){
			game.setPicture3((String)record.get(25));
		}else{
			game.setPicture3(module.getItemValue("GameInfoPicDir"));
		}
		
		if(record.get(26) != null && !"".equals(record.get(26))){
			game.setPicture4((String)record.get(26));
		}else{
			game.setPicture4(module.getItemValue("GameInfoPicDir"));
		}
		
		if(record.get(27) != null && !"".equals(record.get(27))){
			game.setPicture5((String)record.get(27));
		}else{
			game.setPicture5(module.getItemValue("GameInfoPicDir"));
		}
		
		if(record.get(28) != null && !"".equals(record.get(28))){
			game.setPicture6((String)record.get(28));
		}else{
			game.setPicture6(module.getItemValue("GameInfoPicDir"));
		}
	}

	public void prepareData() throws Exception
	{
		//ȡ���ݿ���֧�ֵ��ն�����DeviceDAO
		deviceNameMap = DeviceDAO.getInstance().getDeviceNameMapBrand();
		seriesidMap = DeviceDAO.getInstance().getDeviceNameMapSeriesid();//add by aiyan ��Ϊ��Ʒ���Ż���Ҫ������� 2013-04-22
		GameSyncTools.getInstance().initGameUAMapping();
		//����TXTǰ��������T_GAME_CONTENT���������ݡ�
		GameSyncDAO.getInstance().clearGameContent();
		GameSyncDAO.getInstance().clearContentSeries();//add by aiyan ��Ϊ��Ʒ���Ż���Ҫ��t_a_content_seriesid��������� 2013-04-22
		
	}

	public void init(DataSyncConfig config) throws Exception
	{
		this.fileEncoding = config.get("task.file-encoding");
		this.gameFileRootPath = config.get("ftp.localDir");
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
	 * ����ͼƬ����Ϣ����ͼƬ�ϴ�����Դ��������Ȼ��ѷ��ʵ�ַд���Ӧ�ֶΡ�
	 * @param game  ��Ϸ����
	 * @param pictureRootPath ͼƬ�Ĵ�ŵ�ַ��
	 * @throws BOException
	 */
	private void setGamePic(GAppGame game, String pictureRootPath)
			throws BOException
	{
		//String icpservId=game.getIcpServId();
		String contentCode = game.getContentTag();
		FTPClient ftp=null;
		try
		{
			DataSyncTools.ungzip(pictureRootPath + ".tar.gz");
			//DataSyncTools.unZip(pictureRootPath + ".zip");
			// ����ͼƬ����
			File pictureRootPathFile=new File(pictureRootPath);
			String picNames[] =pictureRootPathFile.list();
			//����logoͼƬ��ַ
			String logoPicPath=null;
			String logoPicPath130 = null;
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
				}else if(picNames[i].startsWith("130logo"))
				{
					logoPicPath130 = temp;
				}
			}
			Collections.sort(detailedPicPathList);//Ĭ�ϰ�����ĸ��������
			//logoPicPath = pictureRootPath + File.separator + "logo.jpg";
			//����ϷͼƬ�ı���Ŀ¼��
			//String saveDir=this.getPicSaveDir(icpservId);
			String saveDir=this.getPicSaveDir(contentCode);
			ftp=this.getFtpWithSaveDir(saveDir);
			
			game.setLOGO1(this.getPicURL(logoPicPath, 30, 30, ftp,saveDir));
			game.setLOGO2(this.getPicURL(logoPicPath, 34, 34, ftp,saveDir));
			game.setLOGO3(this.getPicURL(logoPicPath, 50, 50, ftp,saveDir));
			game.setLOGO4(this.getPicURL(logoPicPath, 65, 65, ftp,saveDir));
			if(logoPicPath130 != null && !logoPicPath130.equals("")){
				game.setLOGO6(this.uploadPic(logoPicPath130, ftp, saveDir));
			}
			
			
			
			//Ϊ�����ܣ���ע������� removed by aiyan 2012-10-13
//			//WWW����ͼ��ַ
//			game.setWWWPropaPicture1(this.getPicURL(logoPicPath, 360, 274, ftp,saveDir));
//			//WWW�б�Сͼ���ַ
//			game.setWWWPropaPicture2(this.getPicURL(logoPicPath, 32, 32, ftp,saveDir));
//			//WWW��׼չʾͼ
//			game.setWWWPropaPicture3(this.getPicURL(logoPicPath, 107, 143, ftp,saveDir));

//			game.setClientPreviewPicture1(this
//					.getPicURL(logoPicPath, 150, 160, ftp,saveDir));
//			game.setClientPreviewPicture2(this
//					.getPicURL(logoPicPath, 210, 220, ftp,saveDir));
//			game.setClientPreviewPicture3(this
//					.getPicURL(logoPicPath, 180, 180, ftp,saveDir));
//			game.setClientPreviewPicture4(this
//					.getPicURL(logoPicPath, 240, 300, ftp,saveDir));
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
					LOG.error(e1);
				}
			}
			//throw new BOException("ת��ͼƬ����icpservId="+icpservId,e);
			throw new BOException("ת��ͼƬ����contentCode="+contentCode,e);
		}
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
					+ ",toHeight=" + toHeight,e);
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
       // HashMap deviceMap = new HashMap();// ������Ҫȥ��
        
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
                	//deviceMap.put(deviceName, "");
                }
                else
                {
                    illegalDeviceName.append(lineText);// ����Ƿ���ua��Ϣ��
                    illegalDeviceName.append(',');
                }
            }
          //ȥ�أ���map������ת����list
//            Set ts = deviceMap.entrySet();
//            Iterator it = ts.iterator();
//            while (it.hasNext())
//            {
//                Entry ey = ( Entry ) it.next();
//                deviceList.add(ey.getKey());
//                
//            }
            // ɾ�����һ������
            if (illegalDeviceName.length() > 1)
            {
                illegalDeviceName.deleteCharAt(illegalDeviceName.length() - 1);
                LOG.debug("ҵ�����Ϊ��" + icpservId + "����Ϸ����������MM�޷�ʶ���	UA��Ϣ��"
                             + illegalDeviceName);
                LOG.debug("���ڻ���ϵͳt_game_ua_mapping�����������޷�ʶ���UA��Ϣ");
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
            LOG.error("��ǰMM�ն˿��devicename�����ڡ�deviceName="
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
	
	/**
		��Ҫ��_OAS,WQ_OAW �ȵȲ��ź�׺ȥ����add by aiyan 2012-11-25
	*/
	private String delSomeStr(String temp){
		if(temp.replaceFirst("_?\\w+$", "").trim().length()>0){
			return temp.replaceFirst("_?\\w+$", "").trim();
		}else if(temp.lastIndexOf("_")>0){
			return temp.substring(0,temp.lastIndexOf("_"));
		}
		return temp;
		
	}

	//add by aiyan 2012-11-21
//	public static void main(String[] argv){
//		String a="�¹⹫��֮��WQ";
//		String b="��������ɱ_OAS";
//		String c="�����������齫2W";
//		String d ="VRMission_OAW";
//		//d = d.replaceFirst("_?\\w+$", "");
//		//d = delSomeStr(a);
//		System.out.println(d);
//		
//	}


}
