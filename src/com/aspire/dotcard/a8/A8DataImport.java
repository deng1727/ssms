

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
 * 从报表系统导入数据的公共父类。实现导入过程重用。子类实现具体类型解析和保存数据库的操作。
 * 
 * @author zhangwei
 * 
 */
public abstract class A8DataImport
{

    private  final JLogger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 本次同步的类型，用于发邮件时标记
     */
    protected String title;

    /**
     * ftp服务器上文件存放目录,注意：文件分割符只能是'/'
     */
    protected String ftpDir;

    /**
     * 本地保存从ftp服务器上下载的临时文件(绝对目录)
     */
    protected String localDir;

    /**
     * 用户匹配获取文件名的正则表达式
     */
    protected String regex;

    /**
     * 保存ftp下载文件的列表
     */
    private List fileList = new ArrayList();
    /**
     * 用于对文件中的数据去重,使用hashmap是为了更有效率的比较数据是否重复
     */
    protected HashMap removeDuplicate =new HashMap(); 

    /**
     * 失败处理条数
     */
    protected int failureCount = 0;
    /**
     * 成功更新的个数
     */
    protected int successCount=0;

    /**
     * 成功更新的个数
     */
    protected int successUpdate=0;
    /**
     * 成功新增的个数
     */
    protected int successAdd=0;
    /**
     * 成功删除的个数
     */
    protected int successDelete=0;
    

    protected Date startDate;

    protected Date endDate;
    /**
     * 报表数据时间
     */
    protected String reportDate;
    /**
     * 用于存储当前数据库库中数据值
     */
    List dbList;

    /**
     * 子类需要初始化父类几个相关属性，包括ftpDir，localDir，regex
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
            logger.debug(this.title + ",获取A8数据开始....");
        }
        try
        {
            this.downloadFiles(this.regex);
        }
        catch (Exception e)
        {
            String message = "从A8ftp服务器下载文件时出错。本次数据导入中止";
            logger.error(message);
            logger.error(e);
            this.mailToAdmin(message, false);
            return;
        }
        if (this.fileList.size() == 0)
        {
            String message = "没有找到今天数据文件，本次" + this.title + "失败";
            logger.error(message);
            this.mailToAdmin(message, false);
            return;
        }
        boolean res=this.saveDataByFiles();
        if(res)
        {
        	// 维护本地目录
            this.keepLocalDirCapacity();
            this.mailToAdmin(null, true);	
        }
        
    }

    /**
     * 从报表系统获取数据文件
     * 
     * @param regex 文件名匹配字符串
     */
    protected void downloadFiles(String regex) throws IOException, FTPException
    {
       //确保该对象多次执行下载操作不会有问题
        fileList.clear();
        // 取得远程目录中文件列表
        FTPClient ftp = new A8Ftp().getFtpClient();
        if(!"".equals(this.ftpDir.trim()))
        {
        	ftp.chdir(this.ftpDir);
        }
        String[] Remotefiles = ftp.dir();
        // 保存数据文件
        // List matchedFileList = new ArrayList();
        if (logger.isDebugEnabled())
        {
            logger.debug("匹配文件名开始：" + regex);
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
                    logger.debug("成功下载文件：" + absolutePath);
                }
            }

        }
        ftp.quit();

    }

    /**
     * 根据对账文件进行数据验证
     */
    protected boolean checkFiles(List fileList)
    {
        // 本次暂时不实现
        return true;
    }

    /**
     * 根据下载下来的文件导入数据
     */
    protected boolean saveDataByFiles()
    {
        try
        {
             dbList=this.getDBDate();
        }
        catch (DAOException e1)
        {
        	String message = "从数据库中获取音乐数据时发生数据库异常！，本次" + this.title + "失败";
            logger.error(message, e1);
            this.mailToAdmin(message, false);
            return false;
        }
        for (int i = 0; i < fileList.size(); i++)
        {
            String fileName = ( String ) fileList.get(i);
            this.parseFile(fileName);
        }
        //需要判断是否为空文件
        if(this.successAdd+this.successUpdate+this.failureCount>0)
        {
        	//剩余的数据需要做删除处理。
            if(dbList.size()==0)
            {
            	if(logger.isDebugEnabled())
            	{
            		logger.debug("没有需要删除的数据");
            	}
            }else
            {
            	this.deleteFromDB(dbList);
            }
        }
        
        return true;
        
    }

    /**
     * 打开文件，读取文件信息，转换文件
     */
    protected void parseFile(String fileName)
    {

        if (logger.isDebugEnabled())
        {
            logger.error("开始处理文件" + fileName);
        }

        String lineText = null;
        BufferedReader reader = null;
        // 用于文件成功处理的行数
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
                //去重
                Object obj=removeDuplicate.get(this.getVOKey(vo));
                if(obj!=null)
                {
                	//有重复的数据，不需奥在保存到数据库中，直接忽略该数据。
                	logger.error("该数据id "+this.getVOKey(vo)+" 文件中已经出现过，直接忽略该数据");
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
            logger.error("读取文件出现异常,文件名为：" + fileName);
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
            logger.error("文件中共有" + lineNumeber + "笔记录需要处理。成功处理数据条数："+ sCount);
        }

    }

    /**
     * 根据文件中每一行的数据转化为相应的vo类
     * 
     * @param line 数据文件的每一笔记录
     * @param lineCount 该记录所在文件的行数。第一行为1
     * @return 返回该记录对应的vo类，如果解析失败返回null
     */
    abstract Object transformVOByLineText(String line, int lineNumber);

    /**
     * 保存数据到数据库中
     * 
     * @param vo 从报表获取的一条记录
     * @return 为1表示插入操作，2表示更新操作 -1表示操作失败
     */
    abstract int insertDB(Object object);
    /**
     * 获取当前数据库中原有数据，用于比对
     * @return
     */
    abstract void deleteFromDB(List list);
    /**
     * 获取当前数据库所有的数据
     * @return
     * @throws DAOException
     */
    abstract List getDBDate()throws DAOException;
    /**
     * 获取该类型的主键，即可以唯一标识该类型的值
     * @param object
     * @return
     */
    abstract Object getVOKey(Object object);
    
    protected void mailToAdmin(String reason, boolean result)
    {

        String mailTitle;
        // 发送邮件表示本次处理结束
        endDate = new Date();
        StringBuffer sb = new StringBuffer();
        if (result)
        {
        	
            // MailUtil.sen
            mailTitle = this.title + "成功";

            sb.append("开始时间：");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",结束时间：");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("。<p>处理结果：<br>");
            sb.append("本次"+title+"任务总共成功处理");
            sb.append(this.successAdd+this.successDelete+this.successUpdate);
            sb.append("条。<br>其中成功新增");
            sb.append(successAdd);
            sb.append("条，成功更新");
            sb.append(this.successUpdate);
            sb.append("条，成功删除");
            sb.append(successDelete);
            sb.append("条\r\n");
            if(logger.isDebugEnabled())
            {
            	logger.debug("本次任务失败处理的条数为："+failureCount);
            }

        }
        else
        {
            mailTitle = this.title + "失败";
            sb.append("开始时间：");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",结束时间：");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("。<p>失败原因：<br>");
            sb.append(reason);

        }
        Mail.sendMail(mailTitle, sb.toString(), A8ParameterConfig.MailTo);
    }

    /**
     * 防止本地文件目录过大，删除以前的文件，系统保留最新的N天内的文件。
     */
    protected void keepLocalDirCapacity()
    {

        //如果配置项为-1表示不删除备份文件
    	if(A8ParameterConfig.MaxSaveDay==-1)
    	{
    		logger.info("不需要删除备份文件。maxSaveDay值为-1");
    		return ;
    	}
    	// 定义删除时间，在该时间之前的文件需要删除，删除时间定义为
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
                        logger.error(fileName + "删除失败");
                    }else
                    {          	
                    	logger.info("文件名"+fileName+"删除成功");
                    }
                }

            }

        }
        else
        {
            logger.error("该目录没有删除权限，维护本目录文件失败：" + dir.getAbsolutePath());
        }
    }
    /**
     * 返回结尾带斜杠的字符串
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
