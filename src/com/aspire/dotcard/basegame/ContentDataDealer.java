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
	 * 获取游戏文件根目录的路径
	 */
	private String gameFileRootPath;
	
	/**
	 * 该游戏终端适配的UA信息文件的编码方式。
	 */
	private String fileEncoding;
	/**
	 * 目前MM系统的终端库。devicename和brand的映射
	 */
	private static HashMap deviceNameMap;
	
	private static HashMap seriesidMap;
	
	public void clearDirtyData()
	{
		//删除解压缩的文件夹
		IOUtil.deleteFile(new File(gameFileRootPath));
	}

	public int dealDataRecrod(DataRecord record)
	{
		//LOG.info("开始处理信息。");
		try{
			GameContent content = new GameContent();			
			GAppGame game = new GAppGame();
			turnFieldsToEntity(record, content,game);
			// 解压该游戏的图片信息。
			String pictureRootPath = gameFileRootPath + File.separator + content.getContentCode();
			//setGamePic(game, pictureRootPath);
			if("1".equals(content.getContentType())){//1:单机游戏，2:图文游戏，3:游戏包。在游戏包这个情况下，基地是不提供UA的，我们根据该包下的单机适配做并集。
				// 读取终端适配的信息
				List deviceList = getDeviceNames(pictureRootPath + ".ua", content.getContentCode());
				setDeviceNameAndBrand(deviceList, game);
				
				//商品库优化的动作：这里是需要把该内容对应的android下的虚拟机型ID记录下来。放到t_a_content_seriesid
				setSeriesId(content.getContentCode(),deviceList);
			}
			GameSyncDAO.getInstance().insertGameContent(content,game);
			return DataSyncConstants.SUCCESS_ADD;
		}catch(Exception e){
			LOG.error("处理基地游戏内容出现异常",e);
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
				if(seriesidSet.contains(seriesid)){//添加了的就不能再加了。
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
		
		if(count>0){//确实已经导入了本次业务的数据。于是可以删除首发以前的数据了。这样做的目的是怕基地给的是空文件或者没有导入进数据，
			//盲目删除之前的数据，形成门户无数据的可能性。
			
			TransactionDB tdb = null;
			try{
				//sql="delete from t_game_content where status=0";
				String sqlCode1 = "com.aspire.dotcard.basegame.ContentDataDealer.delOldData.DELETE";
				//sql="update t_game_content set status=0 where status=1";
				String sqlCode2 = "com.aspire.dotcard.basegame.ContentDataDealer.delOldData.UPDATE";
				
				//事务模式
				tdb = TransactionDB.getTransactionInstance();
				tdb.executeBySQLCode(sqlCode1, null);
				tdb.executeBySQLCode(sqlCode2, null);
				tdb.commit();

				//非事务模式
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
		//取数据库中支持的终端类型DeviceDAO
		deviceNameMap = DeviceDAO.getInstance().getDeviceNameMapBrand();
		seriesidMap = DeviceDAO.getInstance().getDeviceNameMapSeriesid();//add by aiyan 因为商品库优化需要虚拟机型 2013-04-22
		GameSyncTools.getInstance().initGameUAMapping();
		//分析TXT前，先清理T_GAME_CONTENT的垃圾数据。
		GameSyncDAO.getInstance().clearGameContent();
		GameSyncDAO.getInstance().clearContentSeries();//add by aiyan 因为商品库优化需要把t_a_content_seriesid的数据清空 2013-04-22
		
	}

	public void init(DataSyncConfig config) throws Exception
	{
		this.fileEncoding = config.get("task.file-encoding");
		this.gameFileRootPath = config.get("ftp.localDir");
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
	 * 设置图片的信息。把图片上传到资源服务器，然后把访问地址写入对应字段。
	 * @param game  游戏对象
	 * @param pictureRootPath 图片的存放地址。
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
			// 设置图片规则
			File pictureRootPathFile=new File(pictureRootPath);
			String picNames[] =pictureRootPathFile.list();
			//定义logo图片地址
			String logoPicPath=null;
			String logoPicPath130 = null;
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
				}else if(picNames[i].startsWith("130logo"))
				{
					logoPicPath130 = temp;
				}
			}
			Collections.sort(detailedPicPathList);//默认按照字母升序排列
			//logoPicPath = pictureRootPath + File.separator + "logo.jpg";
			//该游戏图片的保存目录。
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
			
			
			
			//为了性能，特注销下面的 removed by aiyan 2012-10-13
//			//WWW大广告图地址
//			game.setWWWPropaPicture1(this.getPicURL(logoPicPath, 360, 274, ftp,saveDir));
//			//WWW列表小图标地址
//			game.setWWWPropaPicture2(this.getPicURL(logoPicPath, 32, 32, ftp,saveDir));
//			//WWW标准展示图
//			game.setWWWPropaPicture3(this.getPicURL(logoPicPath, 107, 143, ftp,saveDir));

//			game.setClientPreviewPicture1(this
//					.getPicURL(logoPicPath, 150, 160, ftp,saveDir));
//			game.setClientPreviewPicture2(this
//					.getPicURL(logoPicPath, 210, 220, ftp,saveDir));
//			game.setClientPreviewPicture3(this
//					.getPicURL(logoPicPath, 180, 180, ftp,saveDir));
//			game.setClientPreviewPicture4(this
//					.getPicURL(logoPicPath, 240, 300, ftp,saveDir));
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
					LOG.error(e1);
				}
			}
			//throw new BOException("转化图片出错，icpservId="+icpservId,e);
			throw new BOException("转化图片出错，contentCode="+contentCode,e);
		}
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
					+ ",toHeight=" + toHeight,e);
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
       // HashMap deviceMap = new HashMap();// 机型需要去重
        
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
                	//deviceMap.put(deviceName, "");
                }
                else
                {
                    illegalDeviceName.append(lineText);// 保存非法的ua信息。
                    illegalDeviceName.append(',');
                }
            }
          //去重，将map的数据转存入list
//            Set ts = deviceMap.entrySet();
//            Iterator it = ts.iterator();
//            while (it.hasNext())
//            {
//                Entry ey = ( Entry ) it.next();
//                deviceList.add(ey.getKey());
//                
//            }
            // 删除最后一个逗号
            if (illegalDeviceName.length() > 1)
            {
                illegalDeviceName.deleteCharAt(illegalDeviceName.length() - 1);
                LOG.debug("业务代码为：" + icpservId + "的游戏，含有以下MM无法识别的	UA信息："
                             + illegalDeviceName);
                LOG.debug("请在货架系统t_game_ua_mapping中新增上述无法识别的UA信息");
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
            LOG.error("当前MM终端库的devicename不存在。deviceName="
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
	
	/**
		需要把_OAS,WQ_OAW 等等不雅后缀去掉。add by aiyan 2012-11-25
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
//		String a="月光公主之吻WQ";
//		String b="三国地主杀_OAS";
//		String c="超级大明星麻将2W";
//		String d ="VRMission_OAW";
//		//d = d.replaceFirst("_?\\w+$", "");
//		//d = delSomeStr(a);
//		System.out.println(d);
//		
//	}


}
