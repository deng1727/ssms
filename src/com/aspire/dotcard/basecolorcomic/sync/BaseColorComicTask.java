package com.aspire.dotcard.basecolorcomic.sync;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecolorcomic.conf.BaseColorComicConfig;
import com.aspire.dotcard.basecolorcomic.dao.BaseColorComicFileDAO;
import com.aspire.dotcard.basecolorcomic.exportfile.BaseFileFactory;
import com.aspire.ponaadmin.web.mail.Mail;

public class BaseColorComicTask extends TimerTask
{
    protected static JLogger logger = LoggerFactory.getLogger(BaseColorComicTask.class);
    
    public void run()
    {
        // 同步各种表 
        StringBuffer mailText = new StringBuffer();
        
        // 彩漫分区数据导入
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseColorComicConfig.FILE_TYPE_CATEGORY)
                                       .execution(false));
        
        // 彩漫资源数据导入
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseColorComicConfig.FILE_TYPE_CONTENT)
                                       .execution(false));

        // 彩漫商品数据导入
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseColorComicConfig.FILE_TYPE_REFERENCE)
                                       .execution(false));

        // 彩漫推荐数据导入
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseColorComicConfig.FILE_TYPE_RECOMMEND)
                                       .execution(false));

        // 彩漫推荐接口数据导入
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseColorComicConfig.FILE_TYPE_RECOMMEND_LINK)
                                       .execution(false));

        sendResultMail("基地彩漫数据同步结果邮件", mailText);
        
        
        updateCategoryNodeNum();
    }
    
    
	/**
	 * 用于统计子栏目数与栏目下节目数
	 */
	public void updateCategoryNodeNum()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("统计彩漫货架下商品数与总商品数, 开始");
		}

		StringBuffer mailText = new StringBuffer();
		int status = 0;

		status = BaseColorComicFileDAO.getInstance().callUpdateCategoryNum();

		if (logger.isDebugEnabled())
		{
			logger.debug("统计彩漫货架下商品数与总商品数, 结束,统计结果status=" + status);
		}
		if (status == 0)
		{
			mailText.append("统计彩漫货架下商品数与总商品数结果成功success！");
		}
		else
		{
			mailText.append("统计彩漫货架下商品数与总商品数结果失败！failed！请查看存储过程日志");
		}

		sendResultMail("统计彩漫货架下商品数与总商品数结果邮件", mailText);
	}
	
	/**
	 * 发送结果邮件。
	 */
	public void sendResultMail(String mailTitle, StringBuffer mailText)
	{
		logger.info(mailText.toString());
		Mail.sendMail(mailTitle, mailText.toString(), BaseColorComicConfig.mailTo);
	}
	
}
