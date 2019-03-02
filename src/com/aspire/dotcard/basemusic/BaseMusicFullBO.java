package com.aspire.dotcard.basemusic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basemusic.config.BaseMusicConfig;
import com.aspire.ponaadmin.web.mail.Mail;

public class BaseMusicFullBO {
	
protected static JLogger logger = LoggerFactory.getLogger(BaseMusicLoadTask.class);
public void run() throws BOException{
    /** ********************�����������������ݵ�WAP�Ż�********************** */
    /**
     * 0,�������������1���ɹ�������2���ɹ��޸ģ�3���ɹ�ɾ����4�����ݼ�鲻�Ϸ���5�����ݴ���ʧ��
     */
    Integer newmusicmailInfo[] = new Integer[6];
    // 1,��ȡ�������嵥�ļ�����
    /**
     * �¼�����Ʒ���
     */
    Integer[] delNewRefmailInfo = new Integer[1];
    String synBaseMusicSubject = BaseMusicConfig.get("synBaseFullMusicSubject");
    String[] BaseMusicSynMailto = BaseMusicConfig.get("BaseFullMusicSynMailto")
                                                 .split(",");
    /**
     * ���»�����Ʒ����
     */
    Integer charRefmailInfo[] = new Integer[6];

    /**
     * �¼���Ʒ���
     */
    Integer[] delRefmailInfo = new Integer[1];

    /**
     * ���»�����Ʒ����
     */
    Integer[] updatecrInfo = new Integer[1];

    /**
     * ���ڱ���У��ʧ�ܵ���������Ϣ
     */
    StringBuffer checkFailureRow = new StringBuffer();

    /**
     * ���ڱ���������У��ʧ�ܵ���������Ϣ
     */
    StringBuffer checkFailureRowByNew = new StringBuffer();

    /**
     * ���ڱ���У��ʧ�ܵ���������Ϣ
     */
    StringBuffer checkFailureRowByLatest = new StringBuffer();

    /**
     * ���ڱ���������У��ʧ�ܵ���������Ϣ
     */
    HashMap existsNewMusicSinger = new HashMap();
    /**
     * ���ڱ���������У��ʧ�ܵ���������Ϣ
     */
    StringBuffer errorFailureRowByNew = new StringBuffer();
    StringBuffer msgInfo = new StringBuffer();
    Integer[] updateNewcrInfo = new Integer[1];

    String[] newFNameRegex = BaseMusicConfig.get("NewFullMusicNameRegex")
                                            .split(",");
    NewBaseMusicFtpProcessor newbp = new NewBaseMusicFtpProcessor();
    // �Ѵ��ڵ�ȫ����������id�嵥
    HashMap existsNewMusic = new HashMap();
    try
    { // ��ȡ���е��Ѿ����ڵ�����
        existsNewMusic = BMusicDAO.getInstance()
                                  .getAllexistNewMusicID();
    }
    catch (DAOException e1)
    {
        logger.error("�����ݿ��л�ȡ���л��� �� ����IDʱ�������ݿ��쳣��", e1);
        throw new BOException("�����ݿ��л�ȡ���л��� �� ����IDʱ�������ݿ��쳣", e1);
    }
    msgInfo.append("<br>");
    msgInfo.append("�������ָ�������ȫ�����ݵ�������");
    msgInfo.append("<br>");
    try
    {
        String newLocalFileNames[] = newbp.process(newFNameRegex);// ftp
        // ���ص�����
        this.importNewBaseMusicData(existsNewMusic,existsNewMusicSinger,
                                    newmusicmailInfo,
                                    newLocalFileNames,
                                    checkFailureRowByNew,
                                    errorFailureRowByNew);// ִ�������嵥����
//        Iterator iterMap=existsNewMusic.entrySet().iterator();  
//        while(iterMap.hasNext()){                         
//            Map.Entry  strMap=(Map.Entry)iterMap.next();                          
//            if((new Integer(0)).equals(strMap.getValue()));  
//            iterMap.remove();                                               
//        }  

        if (newmusicmailInfo[0] != null)
        {
            msgInfo.append("������������" + newmusicmailInfo[0].intValue());
            msgInfo.append(";<br>�ɹ�������"
                           + newmusicmailInfo[1].intValue());
            msgInfo.append(";<br>�ɹ��޸ģ�"
                           + newmusicmailInfo[2].intValue());
            msgInfo.append(";<br>�ɹ�ɾ����"
                           + newmusicmailInfo[3].intValue());
            msgInfo.append(";<br>���ݼ�鲻�Ϸ���"
                           + newmusicmailInfo[4].intValue());
            if (newmusicmailInfo[4].intValue() > 0)
            {
                msgInfo.append(";<br>���ݼ���������У�")
                       .append(checkFailureRowByNew );
            }
            msgInfo.append(";<br>���ݴ���ʧ�ܣ�"
                           + newmusicmailInfo[5].intValue());
            if (newmusicmailInfo[5].intValue() > 0)
            {
                msgInfo.append(";<br>���ݴ���ʧ�ܾ����У���")
                       .append(errorFailureRowByNew.substring(0, errorFailureRowByNew.length()-2))
                       .append("��<br>");
            }
            msgInfo.append("<br>");
        }

    }
    catch (BOException e)
    {
        // TODO Auto-generated catch block
        logger.error("�������ָ�������ȫ���������ʧ��" + e);
        e.printStackTrace();
        msgInfo.append("�������ָ�������ȫ���������ʧ��" + e);
        msgInfo.append("<br>");
    }
    // 4,�¼�ȫ�����ڻ���������Ʒ
    // this.delInvalBMusicRef(delNewRefmailInfo);
    BaseMusicBO.getInstance().delInvalNewBMusicRef(delRefmailInfo);
    if (delNewRefmailInfo != null && delNewRefmailInfo[0] != null)
    {
        msgInfo.append("<br>");
        msgInfo.append("�¼ܹ��ڻ��� �� ������Ʒ����Ϊ��"
                       + delNewRefmailInfo[0].intValue());
        msgInfo.append("<br>");
    }
    // 5,���»�����Ʒ����
    // this.updateCategoryRefSum(updateNewcrInfo);
    BaseMusicBO.getInstance().updateAllNewCategoryRefSum(updatecrInfo);
    if (updateNewcrInfo != null && updateNewcrInfo[0] != null)
    {
        msgInfo.append("<br>");
        msgInfo.append("���� �� ���ֻ�������Ϊ��" + updateNewcrInfo[0].intValue());
        msgInfo.append("<br>");
    }
        logger.error("�������ָ�������ȫ�����룺" + msgInfo);
        this.sendMail(msgInfo.toString(),
                      BaseMusicSynMailto,
                      synBaseMusicSubject);

    /** *******************�����������������ݵ�WAP�Ż� ����******************* */
}
/**
 * �����ʼ�
 * 
 * @param mailContent,�ʼ�����
 */
private void sendMail(String mailContent, String[] mailTo, String subject)
{
    if (logger.isDebugEnabled())
    {
        logger.debug("sendMail(" + mailContent + ")");
    }
    // �õ��ʼ�����������
    // String[] mailTo = MailConfig.getInstance().getMailToArray();
    // String subject = "�����������ݵ���";
    if (logger.isDebugEnabled())
    {
        logger.debug("mailTo Array is:" + Arrays.asList(mailTo));
        logger.debug("mail subject is:" + subject);
        logger.debug("mailContent is:" + mailContent);
    }
    Mail.sendMail(subject, mailContent, mailTo);
}
/**
 * ������ �����嵥
 * 
 * @param msgInfo
 * @param RefDataTime
 * @param nameList
 * @return
 * @throws BOException
 */
private void importNewBaseMusicData(HashMap existsMusic,
									HashMap  existsMusicSingers,
                                    Integer musicmailInfo[],
                                    String[] nameList,
                                    StringBuffer checkFailureRowByNew, StringBuffer errorFailureRowByNew)
                throws BOException
{
	BaseMusicBO.getInstance().importNewBaseFullMusic(existsMusic,existsMusicSingers,
                                                 musicmailInfo,nameList, checkFailureRowByNew, errorFailureRowByNew);
}
}
