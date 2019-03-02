

package com.aspire.dotcard.a8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;

/**
 * �ӱ���ϵͳ�������ݵĹ������ࡣʵ�ֵ���������á�����ʵ�־������ͽ����ͱ������ݿ�Ĳ�����
 * 
 * @author zhangwei
 * 
 */
public abstract class A8DataImport
{

    private  final JLogger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * ����ͬ�������ͣ����ڷ��ʼ�ʱ���
     */
    protected String title;

    /**
     * ftp���������ļ����Ŀ¼,ע�⣺�ļ��ָ��ֻ����'/'
     */
    protected String ftpDir;

    /**
     * ���ر����ftp�����������ص���ʱ�ļ�(����Ŀ¼)
     */
    protected String localDir;

    /**
     * �û�ƥ���ȡ�ļ�����������ʽ
     */
    protected String regex;

    /**
     * ����ftp�����ļ����б�
     */
    private List fileList = new ArrayList();
    /**
     * ���ڶ��ļ��е�����ȥ��,ʹ��hashmap��Ϊ�˸���Ч�ʵıȽ������Ƿ��ظ�
     */
    protected HashMap removeDuplicate =new HashMap(); 

    /**
     * ʧ�ܴ�������
     */
    protected int failureCount = 0;
    /**
     * �ɹ����µĸ���
     */
    protected int successCount=0;

    /**
     * �ɹ����µĸ���
     */
    protected int successUpdate=0;
    /**
     * �ɹ������ĸ���
     */
    protected int successAdd=0;
    /**
     * �ɹ�ɾ���ĸ���
     */
    protected int successDelete=0;
    

    protected Date startDate;

    protected Date endDate;
    /**
     * ��������ʱ��
     */
    protected String reportDate;
    /**
     * ���ڴ洢��ǰ���ݿ��������ֵ
     */
    List dbList;

    /**
     * ������Ҫ��ʼ�����༸��������ԣ�����ftpDir��localDir��regex
     */
    public A8DataImport()throws BOException
    {

        init();

        IOUtil.checkAndCreateDir(this.localDir);
    }
    abstract void init()throws BOException;

    public void process()
    {
        startDate = new Date();
        if (logger.isDebugEnabled())
        {
            logger.debug(this.title + ",��ȡA8���ݿ�ʼ....");
        }
        try
        {
            this.downloadFiles(this.regex);
        }
        catch (Exception e)
        {
            String message = "��A8ftp�����������ļ�ʱ�����������ݵ�����ֹ";
            logger.error(message);
            logger.error(e);
            this.mailToAdmin(message, false);
            return;
        }
        if (this.fileList.size() == 0)
        {
            String message = "û���ҵ����������ļ�������" + this.title + "ʧ��";
            logger.error(message);
            this.mailToAdmin(message, false);
            return;
        }
        boolean res=this.saveDataByFiles();
        if(res)
        {
        	// ά������Ŀ¼
            this.keepLocalDirCapacity();
            this.mailToAdmin(null, true);	
        }
        
    }

    /**
     * �ӱ���ϵͳ��ȡ�����ļ�
     * 
     * @param regex �ļ���ƥ���ַ���
     */
    protected void downloadFiles(String regex) throws IOException, FTPException
    {
       //ȷ���ö�����ִ�����ز�������������
        fileList.clear();
        // ȡ��Զ��Ŀ¼���ļ��б�
        FTPClient ftp = new A8Ftp().getFtpClient();
        if(!"".equals(this.ftpDir.trim()))
        {
        	ftp.chdir(this.ftpDir);
        }
        String[] Remotefiles = ftp.dir();
        // ���������ļ�
        // List matchedFileList = new ArrayList();
        if (logger.isDebugEnabled())
        {
            logger.debug("ƥ���ļ�����ʼ��" + regex);
        }
        for (int j = 0; j < Remotefiles.length; j++)
        {
            String RemotefileName =Remotefiles[j]; //Remotefiles[j].substring(Remotefiles[j].lastIndexOf("/") + 1);
            if (RemotefileName.matches(regex))
            {
                String absolutePath = this.localDir + File.separator
                                      + RemotefileName;
                absolutePath=absolutePath.replace('\\', '/');
                ftp.get(absolutePath, Remotefiles[j]);
                this.fileList.add(absolutePath);
                if (logger.isDebugEnabled())
                {
                    logger.debug("�ɹ������ļ���" + absolutePath);
                }
            }

        }
        ftp.quit();

    }

    /**
     * ���ݶ����ļ�����������֤
     */
    protected boolean checkFiles(List fileList)
    {
        // ������ʱ��ʵ��
        return true;
    }

    /**
     * ���������������ļ���������
     */
    protected boolean saveDataByFiles()
    {
        try
        {
             dbList=this.getDBDate();
        }
        catch (DAOException e1)
        {
        	String message = "�����ݿ��л�ȡ��������ʱ�������ݿ��쳣��������" + this.title + "ʧ��";
            logger.error(message, e1);
            this.mailToAdmin(message, false);
            return false;
        }
        for (int i = 0; i < fileList.size(); i++)
        {
            String fileName = ( String ) fileList.get(i);
            this.parseFile(fileName);
        }
        //��Ҫ�ж��Ƿ�Ϊ���ļ�
        if(this.successAdd+this.successUpdate+this.failureCount>0)
        {
        	//ʣ���������Ҫ��ɾ������
            if(dbList.size()==0)
            {
            	if(logger.isDebugEnabled())
            	{
            		logger.debug("û����Ҫɾ��������");
            	}
            }else
            {
            	this.deleteFromDB(dbList);
            }
        }
        
        return true;
        
    }

    /**
     * ���ļ�����ȡ�ļ���Ϣ��ת���ļ�
     */
    protected void parseFile(String fileName)
    {

        if (logger.isDebugEnabled())
        {
            logger.error("��ʼ�����ļ�" + fileName);
        }

        String lineText = null;
        BufferedReader reader = null;
        // �����ļ��ɹ����������
        int sCount = 0;
        int lineNumeber = 0;
        try
        {
           // reader = new BufferedReader(new FileReader(fileName));
        	 reader = new BufferedReader(new InputStreamReader(new FileInputStream(
        			 fileName), "UTF-8"));
            while ((lineText = reader.readLine()) != null)
            {
                lineNumeber++;
                Object vo = transformVOByLineText(lineText, lineNumeber);
                if (vo == null)
                {
                    failureCount++;
                    continue;
                }
                //ȥ��
                Object obj=removeDuplicate.get(this.getVOKey(vo));
                if(obj!=null)
                {
                	//���ظ������ݣ�������ڱ��浽���ݿ��У�ֱ�Ӻ��Ը����ݡ�
                	logger.error("������id "+this.getVOKey(vo)+" �ļ����Ѿ����ֹ���ֱ�Ӻ��Ը�����");
                	continue;
                }
                removeDuplicate.put(getVOKey(vo), "");
                int result = this.insertDB(vo);
                if (result == -1)
                {
                    failureCount++;
                }
                else if(result==A8ParameterConfig.success_add)
                {
                    successAdd++;
                    sCount++;
                }else if(result==A8ParameterConfig.success_update)
                {
                	successUpdate++;
                    sCount++;
                }
            }
        }
        catch (IOException e)
        {
            logger.error("��ȡ�ļ������쳣,�ļ���Ϊ��" + fileName);
            logger.error(e);

        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        if (logger.isDebugEnabled())
        {
            logger.error("�ļ��й���" + lineNumeber + "�ʼ�¼��Ҫ�����ɹ���������������"+ sCount);
        }

    }

    /**
     * �����ļ���ÿһ�е�����ת��Ϊ��Ӧ��vo��
     * 
     * @param line �����ļ���ÿһ�ʼ�¼
     * @param lineCount �ü�¼�����ļ�����������һ��Ϊ1
     * @return ���ظü�¼��Ӧ��vo�࣬�������ʧ�ܷ���null
     */
    abstract Object transformVOByLineText(String line, int lineNumber);

    /**
     * �������ݵ����ݿ���
     * 
     * @param vo �ӱ����ȡ��һ����¼
     * @return Ϊ1��ʾ���������2��ʾ���²��� -1��ʾ����ʧ��
     */
    abstract int insertDB(Object object);
    /**
     * ��ȡ��ǰ���ݿ���ԭ�����ݣ����ڱȶ�
     * @return
     */
    abstract void deleteFromDB(List list);
    /**
     * ��ȡ��ǰ���ݿ����е�����
     * @return
     * @throws DAOException
     */
    abstract List getDBDate()throws DAOException;
    /**
     * ��ȡ�����͵�������������Ψһ��ʶ�����͵�ֵ
     * @param object
     * @return
     */
    abstract Object getVOKey(Object object);
    
    protected void mailToAdmin(String reason, boolean result)
    {

        String mailTitle;
        // �����ʼ���ʾ���δ������
        endDate = new Date();
        StringBuffer sb = new StringBuffer();
        if (result)
        {
        	
            // MailUtil.sen
            mailTitle = this.title + "�ɹ�";

            sb.append("��ʼʱ�䣺");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",����ʱ�䣺");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("��<p>��������<br>");
            sb.append("����"+title+"�����ܹ��ɹ�����");
            sb.append(this.successAdd+this.successDelete+this.successUpdate);
            sb.append("����<br>���гɹ�����");
            sb.append(successAdd);
            sb.append("�����ɹ�����");
            sb.append(this.successUpdate);
            sb.append("�����ɹ�ɾ��");
            sb.append(successDelete);
            sb.append("��\r\n");
            if(logger.isDebugEnabled())
            {
            	logger.debug("��������ʧ�ܴ��������Ϊ��"+failureCount);
            }

        }
        else
        {
            mailTitle = this.title + "ʧ��";
            sb.append("��ʼʱ�䣺");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",����ʱ�䣺");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("��<p>ʧ��ԭ��<br>");
            sb.append(reason);

        }
        Mail.sendMail(mailTitle, sb.toString(), A8ParameterConfig.MailTo);
    }

    /**
     * ��ֹ�����ļ�Ŀ¼����ɾ����ǰ���ļ���ϵͳ�������µ�N���ڵ��ļ���
     */
    protected void keepLocalDirCapacity()
    {

        //���������Ϊ-1��ʾ��ɾ�������ļ�
    	if(A8ParameterConfig.MaxSaveDay==-1)
    	{
    		logger.info("����Ҫɾ�������ļ���maxSaveDayֵΪ-1");
    		return ;
    	}
    	// ����ɾ��ʱ�䣬�ڸ�ʱ��֮ǰ���ļ���Ҫɾ����ɾ��ʱ�䶨��Ϊ
        Calendar deleteDate = Calendar.getInstance();
        deleteDate.add(Calendar.DATE, -A8ParameterConfig.MaxSaveDay);

        long comparedDate = deleteDate.getTimeInMillis();

        File dir = new File(this.localDir);
        if (dir.canWrite())
        {
            if (!dir.isDirectory())
            {
                return;
            }
            File files[] = dir.listFiles();
            File file;
            if (files == null)
            {
                return;
            }

            for (int i = 0; i < files.length; i++)
            {
                file = files[i];
                String fileName=file.getAbsolutePath();
                if (file.lastModified() < comparedDate)
                {
                    boolean res = file.delete();
                    if (!res)
                    {
                        logger.error(fileName + "ɾ��ʧ��");
                    }else
                    {          	
                    	logger.info("�ļ���"+fileName+"ɾ���ɹ�");
                    }
                }

            }

        }
        else
        {
            logger.error("��Ŀ¼û��ɾ��Ȩ�ޣ�ά����Ŀ¼�ļ�ʧ�ܣ�" + dir.getAbsolutePath());
        }
    }
    /**
     * ���ؽ�β��б�ܵ��ַ���
     * @param str
     * @return
     */
    protected String getPathWithSlash(String str)
    {
        
        if(str.endsWith("/"))
        {
             return str;
        }else
        {
            return str+"/";
        }
            
    }

}
