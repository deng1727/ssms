package com.aspire.ponaadmin.web.mail ;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class Mail
{

    static JLogger logger = LoggerFactory.getLogger(Mail.class) ;

    private Mail ()
    {

    }

    /**
     * ����һ���ʼ�
     * @param mailVO MailVO
     * @throws Exception
     */
    private static void send (MailVO mailVO) throws Exception
    {

        // ���ϵͳProperties
        Properties props = new Properties() ;

        // �����ʼ�������
        MailConfig config = MailConfig.getInstance() ;

        props.put("mail.smtp.host", config.getSmtpHost()) ;
        props.put("mail.smtp.auth", "true") ; //��������ͨ����֤

        //��öԻ� session
        Session session = Session.getDefaultInstance(props) ;
        session.setDebug(false) ;

        // ���� message
        MimeMessage message = new MimeMessage(session) ;
        logger.debug("mail content is:" + mailVO.getContent()) ;
        message.setDataHandler(new DataHandler(mailVO.getContent(),
                                               "text/html; charset=gb2312")) ;
        //���÷�����
        message.setFrom(new InternetAddress(mailVO.getFrom())) ;
        //�����ռ��ˣ������ж��
        Vector tmp = mailVO.getTo() ;
        java.util.Iterator arr = tmp.iterator() ;
        while (arr.hasNext())
        {
            message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress((String.valueOf(arr.next())))) ;
        }
        //�����ʼ�����
        message.setSubject(mailVO.getSubject(), "gb2312") ;
        Multipart multipart = new MimeMultipart() ;
        MimeBodyPart messagePart = new MimeBodyPart() ;
        //�����ʼ��ı�����
        messagePart.setContent(mailVO.getContent(),
                               "text/html; charset=gb2312") ;

        multipart.addBodyPart(messagePart) ;
        //�����ʼ������������ж��

        message.setContent(multipart) ;
        message.setSentDate(new Date()) ;

        // ����message
        message.saveChanges() ;

        //��÷��Ͷ˿�
        Transport transport = session.getTransport("smtp") ;
        transport.connect(config.getSmtpHost(), config.getUser(),
                          config.getPassword()) ;
        //�����ʼ�
        transport.sendMessage(message, message.getAllRecipients()) ;
        transport.close() ;
    }

    private static Vector parseMailto(String[] mailtoArray)
    {
        Vector mailtoVector = new Vector();
        for (int i = 0; i < mailtoArray.length; i++)
        {
            mailtoVector.add(mailtoArray[i]);
        }

        return mailtoVector;
    }
    
    /**
     * �����ṩ�ķ����ʼ��ķ���
     * @param mailTitle �ʼ�����
     * @param mailContent �ʼ�����
     * @param mailto �ʼ�����������
     */
    public static void sendMail(String mailTitle, String mailContent, String[] mailto)
    {
        {
            if (logger.isDebugEnabled())
            {
                logger.debug( "sendMail(" + mailTitle + "," + mailContent + ")");
            }
            if(mailContent.length() > 3500000){
            	mailContent = mailContent.substring(0,3500000);
            	mailContent = mailContent+"..............";
            }
            Vector mailtoVector = parseMailto(mailto);
            String from = MailConfig.getInstance().getFromMail();

            MailVO mailVO = new MailVO(mailtoVector, from, mailTitle, mailContent);
            try
            {
                Mail.send(mailVO);
            }
            catch (Exception ex)
            {
                logger.error(ex);
            }
        }
        
    }
    
    /**
     * �����ṩ�ķ����ʼ��ķ���
     * @param mailTitle �ʼ�����
     * @param mailContent �ʼ�����
     * @param mailto �ʼ�����������
     */
    public static void sendMail(String mailTitle, String mailContent, String[] mailto,File[] attaches)
    {
        {
            if (logger.isDebugEnabled())
            {
                logger.debug( "sendMail(" + mailTitle + "," + mailContent + ")");
            }
            
            Vector mailtoVector = parseMailto(mailto);
            String from = MailConfig.getInstance().getFromMail();

            MailVO mailVO = new MailVO(mailtoVector, from, mailTitle, mailContent);
            try
            {
                Mail.send(mailVO,attaches);
            }
            catch (Exception ex)
            {
            	ex.printStackTrace();
                logger.error(ex);
            }
        }
        
    }  
    
    /**
     * �����ṩ�ķ����ʼ��ķ���
     * @param mailTitle �ʼ�����
     * @param mailContent �ʼ�����
     * @param mailto �ʼ�����������
     * @param mailcc �ʼ�����������
     * @param attaches �ʼ������ļ��б�
     */
    public static void sendMail(String mailTitle, String mailContent, String[] mailto,String[] mailcc,File[] attaches)
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug( "sendMail(" + mailTitle + "," + mailContent + ")");
        }
        
        Vector mailtoVector = parseMailto(mailto);
        Vector mailccVector = parseMailto(mailcc);
        String from = MailConfig.getInstance().getFromMail();

        MailVO mailVO = new MailVO(mailtoVector,mailccVector, from, mailTitle, mailContent);
        try
        {
            Mail.send(mailVO,attaches);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
            logger.error(ex);
        }
    } 
    
    /**
     * ����һ���ʼ�
     * @param mailVO MailVO
     * @throws Exception
     */
    private static void send (MailVO mailVO,File[] attaches) throws Exception
    {

        // ���ϵͳProperties
        Properties props = new Properties() ;

        // �����ʼ�������
        MailConfig config = MailConfig.getInstance() ;

        props.put("mail.smtp.host", config.getSmtpHost()) ;
        props.put("mail.smtp.auth", "true") ; //��������ͨ����֤

        //��öԻ� session
        Session session = Session.getDefaultInstance(props) ;
        session.setDebug(false) ;

        // ���� message
        MimeMessage message = new MimeMessage(session) ;
        logger.debug("mail content is:" + mailVO.getContent()) ;
        message.setDataHandler(new DataHandler(mailVO.getContent(),
                                               "text/html; charset=gb2312")) ;
        //���÷�����
        message.setFrom(new InternetAddress(mailVO.getFrom())) ;
        //�����ռ��ˣ������ж��
        Vector tmp = mailVO.getTo() ;
        java.util.Iterator arr = tmp.iterator() ;
        while (arr.hasNext())
        {
            message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress((String.valueOf(arr.next())))) ;
        }
        
        //��ӳ�����
        Vector cc = mailVO.getCc() ;
        if(cc != null){    	
        	java.util.Iterator ccarr = cc.iterator() ;
        	while (ccarr.hasNext())
        	{
        		message.addRecipient(Message.RecipientType.CC,
        				new InternetAddress((String.valueOf(ccarr.next())))) ;
        	}
        }
        //�����ʼ�����
        message.setSubject(mailVO.getSubject(), "gb2312") ;
        Multipart multipart = new MimeMultipart() ;
        MimeBodyPart messagePart = new MimeBodyPart() ;
        //�����ʼ��ı�����
        messagePart.setContent(mailVO.getContent(),
                               "text/html; charset=gb2312") ;

        multipart.addBodyPart(messagePart) ;
        //�����ʼ������������ж��
        if(null!=attaches&&attaches.length>0){
        	for(int i=0;i<attaches.length;i++){
        		messagePart = new MimeBodyPart();
                FileDataSource fds=new FileDataSource(attaches[i]); //�õ�����Դ   
                messagePart.setDataHandler(new DataHandler(fds)); //�õ�������������BodyPart   
                messagePart.setFileName(MimeUtility.encodeText(attaches[i].getName()));  //�õ��ļ���ͬ������BodyPart   
                multipart.addBodyPart(messagePart);     		
        	}
        }
        message.setContent(multipart) ;
        message.setSentDate(new Date()) ;

        // ����message
        message.saveChanges() ;

        //��÷��Ͷ˿�
        Transport transport = session.getTransport("smtp") ;
        transport.connect(config.getSmtpHost(), config.getUser(),
                          config.getPassword()) ;
        //�����ʼ�
        transport.sendMessage(message, message.getAllRecipients()) ;
        transport.close() ;
    }   
    
    
    /**
     * ����һ���ʼ�
     * @param mailVO MailVO
     * @throws Exception
     */
    public static void testSend (MailVO mailVO) throws Exception
    {

        // ���ϵͳProperties
        Properties props = new Properties() ;

        // �����ʼ�������
        //MailConfig config = MailConfig.getInstance() ;

        props.put("mail.smtp.host", "szmail.aspire-tech.com") ;
        props.put("mail.smtp.auth", "true") ; //��������ͨ����֤

        //��öԻ� session
        Session session = Session.getDefaultInstance(props) ;
        session.setDebug(false) ;
        
        // ���� message
        MimeMessage message = new MimeMessage(session) ;
        logger.debug("mail content is:" + mailVO.getContent()) ;
        message.setDataHandler(new DataHandler(mailVO.getContent(),
                                               "text/html; charset=gb2312")) ;
        //���÷�����
        message.setFrom(new InternetAddress(mailVO.getFrom())) ;
        //�����ռ��ˣ������ж��
        Vector tmp = mailVO.getTo() ;
        java.util.Iterator arr = tmp.iterator() ;
        while (arr.hasNext())
        {
            message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress((String.valueOf(arr.next())))) ;
        }
        //�����ʼ�����
        message.setSubject(mailVO.getSubject(), "gb2312") ;
        Multipart multipart = new MimeMultipart() ;
        MimeBodyPart messagePart = new MimeBodyPart() ;
        //�����ʼ��ı�����
        messagePart.setContent(mailVO.getContent(),
                               "text/html; charset=gb2312") ;

        multipart.addBodyPart(messagePart) ;
        //�����ʼ������������ж��

        message.setContent(multipart) ;
        message.setSentDate(new Date()) ;

        // ����message
        message.saveChanges() ;

        //��÷��Ͷ˿�
        Transport transport = session.getTransport("smtp") ;
        transport.connect("szmail.aspire-tech.com", "baojun",
                          "2013$aspire") ;
        //�����ʼ�
        transport.sendMessage(message, message.getAllRecipients()) ;
        transport.close() ;
    }
    
    public static void main(String[] args){
    	Vector mailtoVector = new Vector();
    	mailtoVector.add("576918869@qq.com");
    	mailtoVector.add("baojun_2008@126.com");
        MailVO mailVO = new MailVO(mailtoVector, "baojun@aspirecn.com", "test", "1231");
        try
        {
            testSend(mailVO);
            System.out.println("123123123");
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
            logger.error(ex);
        }
    }
}

