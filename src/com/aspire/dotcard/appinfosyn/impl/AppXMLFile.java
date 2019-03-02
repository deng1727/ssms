package com.aspire.dotcard.appinfosyn.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.aspire.common.exception.BOException;
import com.aspire.dotcard.appinfosyn.AppInfoVO;
import com.aspire.dotcard.appinfosyn.BaseExportXmlFile;
import com.aspire.dotcard.appinfosyn.BaseFileTools;
import com.aspire.dotcard.appinfosyn.config.AppInfoConfig;
import com.aspire.dotcard.appinfosyn.dao.AppInfoSynDAO;
import com.aspire.dotcard.baseVideoNew.bo.BaseVideoNewFileBO;
import com.aspire.dotcard.baseVideoNew.dao.BaseVideoNewFileDAO;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.vo.ProgramVO;


public class AppXMLFile extends BaseExportXmlFile{

	private String[] sqlCodes = null;
	
	public AppXMLFile()
	{
		//this.tableName = "t_v_hotcontent";
		this.tarFileName = "APP_~Dyyyy-MM-dd\\yyyy-MM-dd~_NNNN.xml";
	//	this.mailTitle = "������Ƶ�ȵ������б�����ͬ�����";
		this.ftpFileDirectory = AppInfoConfig.ftpFileDirectory;
		this.fileDir = "full";
	}
	/**
	 * ����д��Ԥɾ������
	 */
	protected void destroy()
	{
		// ���ִ�й��������ݵ��룬��ִ�������߼�
		if (isImputDate)
		{
			Iterator<String> ite = appKeyMap.keySet().iterator();
			
			while (ite.hasNext())
			{
				String key = ite.next();
				if (!"0".equals(appKeyMap.get(key)))
				{
					// ������¼Ϊû��ʹ�ù��ģ���������ɾ������
					BaseVideoNewFileBO.getInstance().delDataByKey(
							getDelSqlCode(), getDelKey(key));
				
				}
			}
		}
	}
	
	/**
	 * ���ڻ�������
	 */
	protected void clear()
	{
		appKeyMap.clear();
	}
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		appKeyMap =  AppInfoSynDAO.getInstance().queryAppId();

	}
	
	protected String checkData2(AppInfoVO object) {
		String tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤Ӧ����Ϣ����xml�ļ��ֶθ�ʽ");
		}
           
    		tmp=object.getAppName();
    		if(!BaseFileTools.checkFieldLength(tmp, 100, true)||tmp==null||"".equals(tmp)){
    			logger.error("AppName=" + tmp
    					+ ",AppName��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����100�ַ���AppName="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;
    		}
    		tmp=object.getAppURL();
    		if(!BaseFileTools.checkFieldLength(tmp, 512, true)||tmp==null||"".equals(tmp)){
    			logger.error("AppURL=" + tmp
    					+ ",AppURL��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���AppURL="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppID();
    		if(!BaseFileTools.checkFieldLength(tmp, 50, true)||tmp==null||"".equals(tmp)){
    			logger.error("AppID=" + tmp
    					+ ",AppID��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����50���ַ���AppID="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getStatus();
    		if(!BaseFileTools.checkFieldLength(tmp, 1, true)||tmp==null||"".equals(tmp)){
    			logger.error("Status=" + tmp
    					+ ",Status��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����1���ַ���Status="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}	
    		if(!"3".equals(object.getStatus())){
    		tmp=object.getAppLogo();
    		if(!BaseFileTools.checkFieldLength(tmp, 60, false)){
    			logger.error("AppLogo=" + tmp
    					+ ",AppLogo��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����60���ַ���AppLogo="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppType1();
    		if(!BaseFileTools.checkFieldLength(tmp, 30, false)){
    			logger.error("AppType1=" + tmp
    					+ ",AppType1��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����30���ַ���AppType1="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppType1();
    		if(!BaseFileTools.checkFieldLength(tmp, 30, false)){
    			logger.error("AppType2=" + tmp
    					+ ",AppType2��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����30���ַ���AppType2="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppDetail();
    		if(!BaseFileTools.checkFieldLength(tmp, 2048, false)){
    			logger.error("AppDetail=" + tmp
    					+ ",AppDetail��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����2048���ַ���AppDetail="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppSp();
    		if(!BaseFileTools.checkFieldLength(tmp, 100, false)){
    			logger.error("AppSp=" + tmp
    					+ ",AppSp��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����100���ַ���AppSp="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppVersion();
    		if(!BaseFileTools.checkFieldLength(tmp, 20, false)){
    			logger.error("AppVersion=" + tmp
    					+ ",AppVersion��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����20���ַ���AppVersion="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppPrice();
    		if(!BaseFileTools.checkFieldLength(tmp, 13, false)){
    			logger.error("AppPrice=" + tmp
    					+ ",AppPrice��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����13���ַ���AppPrice="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppScore();
    		if(!BaseFileTools.checkFieldLength(tmp, 20, false)){
    			logger.error("AppScore=" + tmp
    					+ ",AppScore��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����20���ַ���AppScore="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppScoreNum();
    		if(!BaseFileTools.checkFieldLength(tmp, 10, false)){
    			logger.error("AppScoreNum=" + tmp
    					+ ",AppScoreNum��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����10���ַ���AppScoreNum="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppPic();
    		if(!BaseFileTools.checkFieldLength(tmp, 200, false)){
    			logger.error("AppPic=" + tmp
    					+ ",AppPic��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����30���ַ���AppPic="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppSize();
    		if(!BaseFileTools.checkFieldLength(tmp, 20, false)){
    			logger.error("AppSize=" + tmp
    					+ ",AppSize��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����20���ַ���AppSize="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppUpdateDate();
    		if(!BaseFileTools.checkFieldLength(tmp, 20, false)){
    			logger.error("AppUpdateDate=" + tmp
    					+ ",AppUpdateDate��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����20���ַ���AppUpdateDate="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppSupportSys();
    		if(!BaseFileTools.checkFieldLength(tmp, 50, false)){
    			logger.error("AppSupportSys=" + tmp
    					+ ",AppSupportSys��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����50���ַ���AppSupportSys="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;


    		}
    		tmp=object.getAppRelates();
    		if(!BaseFileTools.checkFieldLength(tmp, 400, false)){
    			logger.error("AppRelates=" + tmp
    					+ ",AppRelates��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����400���ַ���AppRelates="
    					+ tmp);
    			return AppInfoConfig.CHECK_FAILED;

    		}
    		}
    		
        
		return AppInfoConfig.CHECK_DATA_SUCCESS;
	}
	

	protected List<AppInfoVO> checkData(Object object) {
		List<AppInfoVO> applicationInfoList = (List<AppInfoVO>) object;
		String tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤Ӧ����Ϣ����xml�ļ��ֶθ�ʽ");
		}
        for(int i=0;i<applicationInfoList.size();i++){
    		tmp=applicationInfoList.get(i).getAppName();
    		if(!BaseFileTools.checkFieldLength(tmp, 100, true)||tmp==null||"".equals(tmp)){
    			logger.error("AppName=" + tmp
    					+ ",AppName��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����100�ַ���AppName="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;
    		}
    		tmp=applicationInfoList.get(i).getAppURL();
    		if(!BaseFileTools.checkFieldLength(tmp, 512, true)||tmp==null||"".equals(tmp)){
    			logger.error("AppURL=" + tmp
    					+ ",AppURL��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���AppURL="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppID();
    		if(!BaseFileTools.checkFieldLength(tmp, 50, true)||tmp==null||"".equals(tmp)){
    			logger.error("AppID=" + tmp
    					+ ",AppID��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����50���ַ���AppID="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getStatus();
    		if(!BaseFileTools.checkFieldLength(tmp, 1, true)||tmp==null||"".equals(tmp)){
    			logger.error("Status=" + tmp
    					+ ",Status��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����1���ַ���Status="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}	
    		tmp=applicationInfoList.get(i).getAppLogo();
    		if(!BaseFileTools.checkFieldLength(tmp, 60, true)){
    			logger.error("AppLogo=" + tmp
    					+ ",AppLogo��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����60���ַ���AppLogo="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppType1();
    		if(!BaseFileTools.checkFieldLength(tmp, 30, true)){
    			logger.error("AppType1=" + tmp
    					+ ",AppType1��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����30���ַ���AppType1="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppType1();
    		if(!BaseFileTools.checkFieldLength(tmp, 30, true)){
    			logger.error("AppType2=" + tmp
    					+ ",AppType2��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����30���ַ���AppType2="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppDetail();
    		if(!BaseFileTools.checkFieldLength(tmp, 2048, true)){
    			logger.error("AppDetail=" + tmp
    					+ ",AppDetail��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����2048���ַ���AppDetail="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppSp();
    		if(!BaseFileTools.checkFieldLength(tmp, 100, true)){
    			logger.error("AppSp=" + tmp
    					+ ",AppSp��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����100���ַ���AppSp="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppVersion();
    		if(!BaseFileTools.checkFieldLength(tmp, 20, true)){
    			logger.error("AppVersion=" + tmp
    					+ ",AppVersion��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����20���ַ���AppVersion="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppPrice();
    		if(!BaseFileTools.checkFieldLength(tmp, 13, true)){
    			logger.error("AppPrice=" + tmp
    					+ ",AppPrice��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����13���ַ���AppPrice="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppScore();
    		if(!BaseFileTools.checkFieldLength(tmp, 20, true)){
    			logger.error("AppScore=" + tmp
    					+ ",AppScore��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����20���ַ���AppScore="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppScoreNum();
    		if(!BaseFileTools.checkFieldLength(tmp, 10, true)){
    			logger.error("AppScoreNum=" + tmp
    					+ ",AppScoreNum��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����10���ַ���AppScoreNum="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppPic();
    		if(!BaseFileTools.checkFieldLength(tmp, 30, true)){
    			logger.error("AppPic=" + tmp
    					+ ",AppPic��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����30���ַ���AppPic="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppSize();
    		if(!BaseFileTools.checkFieldLength(tmp, 20, true)){
    			logger.error("AppSize=" + tmp
    					+ ",AppSize��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����20���ַ���AppSize="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppUpdateDate();
    		if(!BaseFileTools.checkFieldLength(tmp, 20, true)){
    			logger.error("AppUpdateDate=" + tmp
    					+ ",AppUpdateDate��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����20���ַ���AppUpdateDate="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppSupportSys();
    		if(!BaseFileTools.checkFieldLength(tmp, 50, true)){
    			logger.error("AppSupportSys=" + tmp
    					+ ",AppSupportSys��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����50���ַ���AppSupportSys="
    					+ tmp);
    			applicationInfoList.remove(i);
    			continue;

    		}
    		tmp=applicationInfoList.get(i).getAppRelates();
    		if(!BaseFileTools.checkFieldLength(tmp, 400, true)){
    			logger.error("AppRelates=" + tmp
    					+ ",AppRelates��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����400���ַ���AppRelates="
    					+ tmp);
    			applicationInfoList.remove(i);
    		}
    		
        }
		return applicationInfoList;
	}
	


	protected String getInsertSqlCode() {

		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile.getInsertSqlCode";
	}

	protected String getUpdateSqlCode() {

		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode() {
		// delete from t_v_dprogram p where p.programid = ?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile.getDelSqlCode";
	}
	
	protected Object[] getInsertSqlCodes() {
		return sqlCodes;
	}
	
	protected String getUpdateSqlCodes() {
		return null;
	}
	
	protected String getInsertSqlCodeByAppInfo() {
		
		return "com.aspire.dotcard.appinfosyn.impl.AppXMLFile.getInsertSqlCodeByAppInfo";
	}
	

	
	/**
	 * ������Ŀxml�ؼ���Ԫ��
	 * @param e
	 * @return
	 */
	private String getKeyWords(Element e){
		String keyWords = null;
		StringBuffer keyWordStr = null;
		StringBuffer nkeyWordStr = null;
		Element keyWordsElement = e.element("KEYWORDS");
		if(null != keyWordsElement){
			List list = keyWordsElement.elements("keyword");
			if(null != list && list.size() > 0){
				
				for(int i = 0 ; i <  list.size() ; i++){
					Element keyWord = (Element)list.get(i);
					String primaryKey = keyWord.elementText("primaryKey");
					String keywordName = keyWord.elementText("keywordName");
					if(null != keywordName){
						if(null != primaryKey  && "1".equals(primaryKey)){
							if(null == keyWordStr)
								keyWordStr = new StringBuffer();
						    keyWordStr.append(keywordName);
							keyWordStr.append("��");	
						}else{
							if(null == nkeyWordStr)
								nkeyWordStr = new StringBuffer();
							nkeyWordStr.append(keywordName);
							nkeyWordStr.append("��");
						}
					}
				}
			}
		}
		if(null != keyWordStr){
			if(null != nkeyWordStr)
				keyWordStr.append(nkeyWordStr.toString());
			keyWords = keyWordStr.toString();
		}else if(null != nkeyWordStr){
			keyWords = nkeyWordStr.toString();
		}
		if(null != keyWords)
			keyWords = keyWords.substring(0,keyWords.length()-1);
		return keyWords;
	}
	


	@Override
	protected Object getObject(Element root) {
	List<AppInfoVO> appInfoList = new ArrayList<AppInfoVO>();
	
		List list = root.elements("Application");
        if(null != list && list.size() > 0){
        	for(int i = 0 ; i <  list.size() ; i++){    
        		AppInfoVO appinfo = new AppInfoVO();

        		appinfo.setAppName(((Element)list.get(i)).elementText("APPName"));
        		appinfo.setAppURL(((Element)list.get(i)).elementText("APPURL"));
        		appinfo.setAppID(((Element)list.get(i)).elementText("APPID"));
                appinfo.setAppLogo(((Element)list.get(i)).elementText("AppLogo"));
				
        		appinfo.setAppType1(((Element)list.get(i)).elementText("AppType1"));
        		
        		appinfo.setAppType2(((Element)list.get(i)).elementText("AppType2"));
        		appinfo.setAppDetail(((Element)list.get(i)).elementText("AppDetail"));
        		appinfo.setAppSp(((Element)list.get(i)).elementText("AppSp"));
        		appinfo.setAppVersion(((Element)list.get(i)).elementText("AppVersion"));
        		appinfo.setAppPrice(((Element)list.get(i)).elementText("AppPrice"));
        		appinfo.setAppScore(((Element)list.get(i)).elementText("AppScore"));
        		appinfo.setAppScoreNum(((Element)list.get(i)).elementText("AppScoreNum"));
        		appinfo.setAppPic(((Element)list.get(i)).elementText("AppPicture"));
        		appinfo.setAppSize(((Element)list.get(i)).elementText("AppSize"));
        		appinfo.setAppUpdateDate(((Element)list.get(i)).elementText("AppUpdateDate"));
        		appinfo.setAppSupportSys(((Element)list.get(i)).elementText("AppSupportSys"));
        		appinfo.setAppRelates(((Element)list.get(i)).elementText("AppRelates"));
        		appinfo.setStatus(((Element)list.get(i)).elementText("Status"));
        		if(((Element)list.get(i)).elementText("AppPicture")==null||"".equals(((Element)list.get(i)).elementText("AppPicture"))){
            		String[] picAdress ={""};

        		}else{
        		String[] picAdress =((Element)list.get(i)).elementText("AppPicture").split(";");
        		}
				appInfoList.add(appinfo);

        		}
        }



	return appInfoList;
}

	@Override
	protected Object uplodPicture(List<AppInfoVO> appInfoList,String fileName) {
	

        if(null != appInfoList && appInfoList.size() > 0){
        	for(int i = 0 ; i <  appInfoList.size() ; i++){    
        		//AppInfoVO appinfo = new AppInfoVO();
                if(appInfoList.get(i).getAppLogo()!=null||!"".equals(appInfoList.get(i).getAppLogo())){
            		try {
            			
            			appInfoList.get(i).setAppLogo(upLoadfileToResServer(fileName,appInfoList.get(i).getAppID(), "Logo.png"));
					} catch (BOException e) {
						e.printStackTrace();
					}

                }

               if(appInfoList.get(i).getAppPic()!=null||!"".equals(appInfoList.get(i).getAppPic())){
            	   if(!"3".equals(appInfoList.get(i).getStatus())){

                	
            		String[] picAdress =appInfoList.get(i).getAppPic().split(";");
            		for(int j=0;j<picAdress.length;j++){
            			if(picAdress[j].contains("Picture1")){	
    						try {
    							appInfoList.get(i).setPic1(upLoadfileToResServer(fileName,appInfoList.get(i).getAppID(), picAdress[j]));
    						} catch (BOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}

    					}else if(picAdress[j].contains("Picture2")){
    						try {
    							appInfoList.get(i).setPic2(upLoadfileToResServer(fileName,appInfoList.get(i).getAppID(), picAdress[j]));
    						} catch (BOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}

    					}else if(picAdress[j].contains("Picture3")){
    						try {
    							appInfoList.get(i).setPic3(upLoadfileToResServer(fileName,appInfoList.get(i).getAppID(), picAdress[j]));
    						} catch (BOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}

    					}else if(picAdress[j].contains("Picture4")){
    						try {
    							appInfoList.get(i).setPic4(upLoadfileToResServer(fileName,appInfoList.get(i).getAppID(), picAdress[j]));
    						} catch (BOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}

    					}else if(picAdress[j].contains("Picture5")){
    						try {
    							appInfoList.get(i).setPic5(upLoadfileToResServer(fileName,appInfoList.get(i).getAppID(), picAdress[j]));
    						} catch (BOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}

    					}else if(picAdress[j].contains("Picture6")){
    						try {
    							appInfoList.get(i).setPic6(upLoadfileToResServer(fileName,appInfoList.get(i).getAppID(), picAdress[j]));
    						} catch (BOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}

    					}else if(picAdress[j].contains("Picture7")){
    						try {
    							appInfoList.get(i).setPic7(upLoadfileToResServer(fileName,appInfoList.get(i).getAppID(), picAdress[j]));
    						} catch (BOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}

    					}else if(picAdress[j].contains("Picture8")){
    						try {
    							appInfoList.get(i).setPic8(upLoadfileToResServer(fileName,appInfoList.get(i).getAppID(), picAdress[j]));
    						} catch (BOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}

    					}
            		}
            		
            	   }
        	
        		}

        }
	}
	return appInfoList;
}	
	
	@Override
	protected String getImageAllPath(String fileName,String image){
		if(null == image || "".equals(image))
			return "";
		StringBuffer imageAllPath = new StringBuffer();
		imageAllPath.append(AppInfoConfig.RESERVERPATH);
			imageAllPath.append(fileName);

			imageAllPath.append("/");
			imageAllPath.append(image);

	
		return imageAllPath.toString();
	}
	@Override
	protected Object[][] getObjectParas(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	public String execution(boolean isSendMail) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
