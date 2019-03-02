package com.aspire.ponaadmin.web.newmusic139.bo;

import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.ftpfile.FtpGetFileProcessor;
import com.aspire.common.ftpfile.ParseFtpFileUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.music139.Music139Dao;
import com.aspire.ponaadmin.web.newmusic139.NewMusic139Config;
import com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO;


public class New139BaseSynBO  {
	private static final JLogger logger = LoggerFactory
			.getLogger(New139BaseSynBO.class);

	//文件名数组
	protected String [] fileNameRex;
	protected String [] mailto;
	
	public static final String KEY_MUSIC_NAME = "musicname";

	public static final String KEY_MUSIC_ID = "musicid";

	public static final String KEY_SINGER = "singer";
	
	public static final String VALIDITY = "validity";
	
	/**
	 * 
	 *@desc 数据接入的基方法
	 *@author dongke
	 *Apr 30, 2011
	 * @throws Exception 
	 */
	public List input139Data() throws Exception
	{
		//String albumFileNames = NewMusic139Config.getInstance().getNewAlbumFileName();
		String ftpip = NewMusic139Config.getInstance().getSourceFTPServerIP();
		String ftpport = NewMusic139Config.getInstance().getSourceFTPServerPort();
		String ftpuser = NewMusic139Config.getInstance().getSourceFTPServerUser();
		String ftppass = NewMusic139Config.getInstance().getSourceFTPServerPassword();
		String ftpdir = NewMusic139Config.getInstance().getNewFileFTPDir();
		String localdir = NewMusic139Config.getInstance().getLocalDir();
		String coumsplit = NewMusic139Config.getInstance().getCloumsplit();
		String encoding = NewMusic139Config.getInstance().getEncoding();
		 mailto = NewMusic139Config.getInstance().getMailTo();
		
	//	String[] filenames = { albumFileNames };

		FtpGetFileProcessor fp = new FtpGetFileProcessor();
		String[] localtempfileNames = fp.process(fileNameRex, ftpdir, localdir, ftpip,
				ftpport, ftpuser, ftppass);
		List lines = new ArrayList();
		if (localtempfileNames != null && localtempfileNames.length > 0)
		{
			for (int i = 0; i < localtempfileNames.length; i++)
			{
				ParseFtpFileUtil pf = new ParseFtpFileUtil();
				lines.addAll(pf.parseFileContent(localtempfileNames[i], coumsplit,
						encoding));
				//lines = pf.parseFileContent(localtempfileNames[i], coumsplit,
				//		encoding);
			}

		}else{
			throw new BOException("没有找到文件");
		}
		return lines;
	}
	
	
	public  String queryNewMusic(String musicname, String singer, OutputStream out) {
		StringBuffer sb = new StringBuffer("");
		List musicList = New139BaseSyncDAO.getInstance().listNewMusicInfo(musicname, singer);
		boolean flag = true;
			if(musicList != null && musicList.size()>0){
				sb.append("[");
				for(int i = 0 ; i < musicList.size();i ++) {
					String [] musicline = (String[])musicList.get(i);
					if (!flag) {
						sb.append(",");
					}
					sb.append("{\"");
					sb.append(KEY_MUSIC_ID).append("\":\"").append(musicline[1])
							.append("\",\"");
					sb.append(KEY_MUSIC_NAME).append("\":\"").append(musicline[0])
							.append("\",\"");
					sb.append(KEY_SINGER).append("\":\"").append(musicline[2])
											.append("\",\"");
					sb.append(VALIDITY).append("\":\"").append(musicline[3])
							.append("\"");
					sb.append("}");
					flag = false;
				}
				sb.append("]");
			}	
		
		return sb.toString();
	}
	
	/**
	 * 
	 *@desc 下线的过期音乐
	 *@author dongke
	 *May 5, 2011
	 * @return
	 */
	public  String delInvalNewBMusicRef(){
		String sb = "";
		try
		{
			int delSum = New139BaseSyncDAO.getInstance().delInvalNewBMusicRef();
			sb = "成功下架了"+delSum+"首过期的 新 基地音乐";
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("下架过期音乐失败",e);
			sb = "下架过期音乐失败"+e;
		}
		return sb;
	}
	
	/**
	 * 
	 *@desc 计算所有货架上音乐数量
	 *@author dongke
	 *May 5, 2011
	 * @return
	 */
	public  String updateAllNewCategoryRefSum(){
		
		String sb = "";
		try
		{
			int delSum = New139BaseSyncDAO.getInstance().updateAllNewCategoryRefSum();
			sb = "成功更新了"+delSum+"个 新 基地音乐货架商品数量";
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("更新 新 基地音乐货架商品数量失败",e);
			sb = "更新 新 基地音乐货架商品数量失败"+e;
		}
		return sb;
	}
	 /**
     * 发送邮件
     * 
     * @param mailContent,邮件内容
     */
	protected void sendMail(String mailContent,  String subject)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("sendMail(" + mailContent + ")");
        }
        // 得到邮件接收者数组
        // String[] mailTo = MailConfig.getInstance().getMailToArray();
        // String subject = "基地音乐数据导入";
        if (logger.isDebugEnabled())
        {
            logger.debug("mailTo Array is:" + Arrays.asList(mailto));
            logger.debug("mail subject is:" + subject);
            logger.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailto);
    }
}
