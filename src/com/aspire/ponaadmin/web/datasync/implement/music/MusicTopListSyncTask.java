package com.aspire.ponaadmin.web.datasync.implement.music;

import java.util.Date;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask01;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;
/**
 * ��ͬ�������ࡣ������ֻֻ�����ڰ񵥡�
 * @author zhangwei
 *
 */
public class MusicTopListSyncTask extends DataSyncTask01
{
	int delCount;
	JLogger logger=LoggerFactory.getLogger(MusicTopListSyncTask.class);
	//��Ҫ��д������ʼ����ͷ�����
	protected void mailToAdmin(boolean result,int errorCode,String reason)
	{
		//�����ʼ�֮���ʾ���������������Ҫ��ȡ�¼ܰ񵥵ĸ�����
		MusicHotTopDealer dealer=(MusicHotTopDealer)this.dataDealer;
        delCount=dealer.getDelCount();
        //��Ҫ������������Ա��´�ͬ������ļ�����ȷ��
        dealer.clearDelCount();
		String mailTitle;
		// �����ʼ���ʾ���δ������
		Date endDate = new Date();
		StringBuffer sb = new StringBuffer();
		if (result)
		{

			// MailUtil.sen
			mailTitle = this.getDesc() + "�ɹ�";

			sb.append("��ʼʱ�䣺");
			sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",����ʱ�䣺");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("��<p>��������<br>");
			sb.append("����"+this.getDesc()+"�����ܹ��ɹ�����");
			sb.append(this.getSuccessAdd()+delCount);
			sb.append("����<br>���гɹ�����");
			sb.append(this.getSuccessAdd());
			sb.append("�����ɹ�ɾ��");
			sb.append(delCount);
			sb.append("��\r\n");
			
			if(logger.isDebugEnabled())
			{
				logger.debug("�ɹ�������"+this.getSuccessAdd()+"���ɹ�ɾ����"+this.getSuccessDelete());
			}

		}
		else
		{
			if(errorCode==DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED)
			{
				mailTitle=this.getDesc() + "ʧ�ܣ�û��ȡ����ȷ�������ļ�������ʼ�����!";//XX���ݵ���ʧ�ܣ�û��ȡ����ȷ�������ļ�������ʼ�����!
			}else
			{
				mailTitle=this.getDesc() + "ʧ�ܣ�����ʼ�����!";
			}
			mailTitle = this.getDesc() + "ʧ��";
			sb.append("��ʼʱ�䣺");
			sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",����ʱ�䣺");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("��<p>ʧ��ԭ��<br>");
			sb.append(reason);

		}
		Mail.sendMail(mailTitle, sb.toString(),this.getMailTo());
	}

}
