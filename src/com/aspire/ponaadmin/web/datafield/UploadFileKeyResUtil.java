/**
 * com.aspire.ponaadmin.web.datafield UploadFileKeyResUtil.java
 * Aug 6, 2011
 *<p>
 * Copyright (c) 2003-2011 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * @author dongke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.datafield;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.resourceftp.FtpVO;
import com.aspire.dotcard.resourceftp.ResourceFtp;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.system.SystemConfig;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;

/**
 * @author tungke
 *�ϴ���չ�ֶ��ļ����ͨ�ô�����
 */
public class UploadFileKeyResUtil
{

	  /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(UploadFileKeyResUtil.class);

	 /**
     * singletonģʽ��ʵ��
     */
    private static UploadFileKeyResUtil instance = new UploadFileKeyResUtil();

    /**
     * ���췽������singletonģʽ����
     */
    private UploadFileKeyResUtil ()
    {
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    public static UploadFileKeyResUtil getInstance()
    {
        return instance;
    }
	
    
    /**
     * 
     *@desc ������չ�ֶ��ļ�URL
     *@author dongke
     *Aug 6, 2011
     * @param fileForm
     * @param resServerPath
     * @param resourceId
     * @param hm
     * @throws BOException
     */
	public String getFileUrl(FileForm fileForm, String resServerPath,
			String resourceId,String keyname,String servicePath) throws BOException{	
		Hashtable  files = fileForm.getMultipartRequestHandler().getFileElements();
		String fileUrl = null;
		for (Enumeration e = files.keys(); e.hasMoreElements();)
		{
			String filein = (String) e.nextElement();
			LOG.debug("filein=" + filein);
	
				if (filein.equals(keyname))
				{
					FormFile file = (FormFile) files.get(filein);
					 fileUrl = this.upLoadfileToResServer(file, resServerPath,
							resourceId, filein,servicePath);
				
			}
		}
		return fileUrl;
	}
	
	
	/**
	 * 
	 *@desc �ϴ��ļ�����Դ������
	 *@author dongke
	 *Aug 6, 2011
	 * @param uploadFile
	 * @param resServerPath
	 * @param resourceId
	 * @throws BOException 
	 */
	public String upLoadfileToResServer(FormFile uploadFile, String resServerPath,
			String resourceId,String filein,String servicePath) throws BOException
	{
		String tempDir = ServerInfo.getAppRootPath() + File.separator + "temp"
				+ File.separator;
		//String ftpDir = resServerPath+resourceId;
		IOUtil.checkAndCreateDir(tempDir);
		// ����ļ���������ļ�������·����
		String fileName = uploadFile.getFileName();

		if(fileName != null && fileName.indexOf('.')>0){
		
			String suffex = fileName.substring(fileName.lastIndexOf('.'));
		String oldFileName = tempDir + "temp" + suffex;// ƴװ��ʱ�ļ���Ϊ temp.png
		try
		{
			IOUtil.writeToFile(oldFileName, uploadFile.getFileData());
		} catch (Exception e1)
		{
			throw new BOException("д����ʱ�ļ�ʧ�ܡ�tempFilePath=" + oldFileName,
					RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
		}
		String localfilePath = tempDir +  "temp"  + suffex;
		LOG.debug("localfilePath="+localfilePath);
		FTPClient ftp = null;
		FtpVO ftpVo =  null;
		try
		{
//			ftp = PublicUtil.getFTPClient(SystemConfig.SOURCESERVERIP,
//					SystemConfig.SOURCESERVERPORT, SystemConfig.SOURCESERVERUSER,
//					SystemConfig.SOURCESERVERPASSWORD, resServerPath);
			ftpVo =   ResourceFtp.getInstance().getResourceServerFtp();
			ftp = ftpVo.getFtp();
			
			if(resServerPath.endsWith("/"))
				resServerPath = resServerPath.substring(0, resServerPath.lastIndexOf("/"));
			String dires[] = resServerPath.split("/");
			for(int i=0; i < dires.length ; i++){
				PublicUtil.checkAndCreateDir(ftp,dires[i]);
				ftp.chdir(dires[i]);
			}
			//PublicUtil.checkAndCreateDirs(ftp, resServerPath);
			//ftp.chdir(resServerPath);
			
			if(servicePath != null &&!"".equals(servicePath)){
				PublicUtil.checkAndCreateDir(ftp, servicePath);
				ftp.chdir(servicePath);
			}
			if(resourceId != null &&!"".equals(resourceId)){
				PublicUtil.checkAndCreateDir(ftp, resourceId);
				ftp.chdir(resourceId);
			}
			
			ftp.put(localfilePath, filein + suffex);
		} catch (Exception e)
		{
			throw new BOException("�ϴ�����Դ�����������쳣��", e,
					RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
		} finally
		{
			if (ftp != null)
			{
				try
				{
					ftp.quit();
				} catch (Exception e)
				{
				}
			}
		}
		
		StringBuffer filename = new StringBuffer();
			if (ftpVo.getWwwUrl().endsWith("/"))
			{
				filename.append(ftpVo.getWwwUrl());
			}
			else
			{
				filename.append(ftpVo.getWwwUrl() + "/");
			}
            if(ftpVo.getResroot().endsWith("/")){
				
				filename.append(ftpVo.getResroot());
			}else{
				filename.append(ftpVo.getResroot() + "/");
			}
			//filename.append(ftpVo.getResroot());
			if (resServerPath != null && !"".equals(resServerPath))
			{
				if (resServerPath.endsWith("/"))
				{
					filename.append(resServerPath);
				}
				else
				{
					filename.append(resServerPath + "/");
				}
			}
			if (servicePath != null && !"".equals(servicePath))
			{
				if (servicePath.endsWith("/"))
				{
					filename.append(servicePath );
				}
				else
				{
					filename.append(servicePath + "/");
				}
				
			}
			if (resourceId != null && !"".equals(resourceId))
			{

				filename.append(resourceId + "/");
			}
			filename.append(filein + suffex);
		return filename.toString();  //SystemConfig.URL_PIC_VISIT + resServerPath  + "/"+ servicePath+ "/"+resourceId+ "/"+filein
				//+ suffex;
		}else{
			return null;
		}
	}
	/**
	 * 
	 * @param dataFile
	 * @param id
	 */
	public void importData(FormFile dataFile,String id ){

        if(dataFile != null && dataFile.getFileName() != null && !"".equals(dataFile.getFileName()))
        {
            String fileName = dataFile.getFileName();
           
            try
            {
                //����EXECL�ļ�����ȡָ���ֻ�����
            	String file = new String(fileName.getBytes("GBK"),"utf-8");
               
                
                String filePath = ServerInfo.getAppRootPath() + File.separator + "resource"+File.separator+"backstageResident" + File.separator + id + File.separator;
                IOUtil.checkAndCreateDir(filePath);
                String distFile = filePath + file;
                File parentFile = new File(filePath);
                if(parentFile.isDirectory()) {
                    File[] filelist = parentFile.listFiles();
                    for(int i = 0; i < filelist.length; i ++) {
                        filelist[i].delete();
                    }
                }
                else {
                	LOG.info("T_Push_MSISDN file not exist !");
                } 
                
                
                try
				{
					IOUtil.writeToFile(distFile, dataFile.getFileData());
				}
				catch (Exception e)
				{
					
				}
                try {
                	if(dataFile!=null){
                        List<String> list = this.parseData(dataFile);
                        PushAdvDAO.getInstance().addMsisdn(list, id);
                        }else{
                        	PushAdvDAO.getInstance().addMsisdn(id);
                        }
				} catch (Exception e) {
					// TODO: handle exception
					LOG.debug("����ʧ��");
				}
               
                
            }
            catch (UnsupportedEncodingException e)
            {

            }
        }
       
    
    }
    
	  /**
     * ����EXECL�ļ�����ȡ��Ϣ
     * 
     * @param in
     * @return
     * @throws BiffException
     * @throws IOException
     */
    private List<String> parseData(FormFile dataFile)
                    throws BOException

    {

      
        List<String> list = new ArrayList<String>();
        Workbook book = null;

        try
        {
            book = Workbook.getWorkbook(dataFile.getInputStream());
            Sheet[] sheets = book.getSheets();
            int sheetNum = 1;
           
            for (int i = 0; i < sheetNum; i++)
            {
                int rows = sheets[i].getRows();
                int columns = sheets[i].getColumns();
                //ģ���У������п�ʼ��ҵ�����ݣ���˴ӵ����п�ʼ��ȡ����
                if (rows > 3)
                {
                    for (int j = 3; j < rows; j++)
                    {         
                        String value = sheets[i].getCell(0, j).getContents().trim();   
                        list.add(value);  
                    }
                }
            }
        }
        catch (Exception e)
        {
           
            throw new BOException("���������ļ������쳣", e);
        }

        finally
        {
            book.close();
        }
        return list;
    }
}
