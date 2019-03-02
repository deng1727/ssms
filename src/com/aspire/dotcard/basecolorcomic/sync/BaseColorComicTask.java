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
        // ͬ�����ֱ� 
        StringBuffer mailText = new StringBuffer();
        
        // �����������ݵ���
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseColorComicConfig.FILE_TYPE_CATEGORY)
                                       .execution(false));
        
        // ������Դ���ݵ���
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseColorComicConfig.FILE_TYPE_CONTENT)
                                       .execution(false));

        // ������Ʒ���ݵ���
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseColorComicConfig.FILE_TYPE_REFERENCE)
                                       .execution(false));

        // �����Ƽ����ݵ���
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseColorComicConfig.FILE_TYPE_RECOMMEND)
                                       .execution(false));

        // �����Ƽ��ӿ����ݵ���
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseColorComicConfig.FILE_TYPE_RECOMMEND_LINK)
                                       .execution(false));

        sendResultMail("���ز�������ͬ������ʼ�", mailText);
        
        
        updateCategoryNodeNum();
    }
    
    
	/**
	 * ����ͳ������Ŀ������Ŀ�½�Ŀ��
	 */
	public void updateCategoryNodeNum()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("ͳ�Ʋ�����������Ʒ��������Ʒ��, ��ʼ");
		}

		StringBuffer mailText = new StringBuffer();
		int status = 0;

		status = BaseColorComicFileDAO.getInstance().callUpdateCategoryNum();

		if (logger.isDebugEnabled())
		{
			logger.debug("ͳ�Ʋ�����������Ʒ��������Ʒ��, ����,ͳ�ƽ��status=" + status);
		}
		if (status == 0)
		{
			mailText.append("ͳ�Ʋ�����������Ʒ��������Ʒ������ɹ�success��");
		}
		else
		{
			mailText.append("ͳ�Ʋ�����������Ʒ��������Ʒ�����ʧ�ܣ�failed����鿴�洢������־");
		}

		sendResultMail("ͳ�Ʋ�����������Ʒ��������Ʒ������ʼ�", mailText);
	}
	
	/**
	 * ���ͽ���ʼ���
	 */
	public void sendResultMail(String mailTitle, StringBuffer mailText)
	{
		logger.info(mailText.toString());
		Mail.sendMail(mailTitle, mailText.toString(), BaseColorComicConfig.mailTo);
	}
	
}
