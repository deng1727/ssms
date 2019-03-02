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
     * 发送一封邮件
     * @param mailVO MailVO
     * @throws Exception
     */
    private static void send (MailVO mailVO) throws Exception
    {

        // 获得系统Properties
        Properties props = new Properties() ;

        // 设置邮件服务器
        MailConfig config = MailConfig.getInstance() ;

        props.put("mail.smtp.host", config.getSmtpHost()) ;
        props.put("mail.smtp.auth", "true") ; //这样才能通过验证

        //获得对话 session
        Session session = Session.getDefaultInstance(props) ;
        session.setDebug(false) ;

        // 设置 message
        MimeMessage message = new MimeMessage(session) ;
        logger.debug("mail content is:" + mailVO.getContent()) ;
        message.setDataHandler(new DataHandler(mailVO.getContent(),
                                               "text/html; charset=gb2312")) ;
        //设置发件人
        message.setFrom(new InternetAddress(mailVO.getFrom())) ;
        //设置收件人，可以有多个
        Vector tmp = mailVO.getTo() ;
        java.util.Iterator arr = tmp.iterator() ;
        while (arr.hasNext())
        {
            message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress((String.valueOf(arr.next())))) ;
        }
        //设置邮件主题
        message.setSubject(mailVO.getSubject(), "gb2312") ;
        Multipart multipart = new MimeMultipart() ;
        MimeBodyPart messagePart = new MimeBodyPart() ;
        //设置邮件文本内容
        messagePart.setContent(mailVO.getContent(),
                               "text/html; charset=gb2312") ;

        multipart.addBodyPart(messagePart) ;
        //设置邮件附件，可以有多个

        message.setContent(multipart) ;
        message.setSentDate(new Date()) ;

        // 保存message
        message.saveChanges() ;

        //获得发送端口
        Transport transport = session.getTransport("smtp") ;
        transport.connect(config.getSmtpHost(), config.getUser(),
                          config.getPassword()) ;
        //发送邮件
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
     * 对外提供的发送邮件的方法
     * @param mailTitle 邮件标题
     * @param mailContent 邮件内容
     * @param mailto 邮件接收人数组
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
     * 对外提供的发送邮件的方法
     * @param mailTitle 邮件标题
     * @param mailContent 邮件内容
     * @param mailto 邮件接收人数组
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
     * 对外提供的发送邮件的方法
     * @param mailTitle 邮件标题
     * @param mailContent 邮件内容
     * @param mailto 邮件接收人数组
     * @param mailcc 邮件抄送人数组
     * @param attaches 邮件附件文件列表
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
     * 发送一封邮件
     * @param mailVO MailVO
     * @throws Exception
     */
    private static void send (MailVO mailVO,File[] attaches) throws Exception
    {

        // 获得系统Properties
        Properties props = new Properties() ;

        // 设置邮件服务器
        MailConfig config = MailConfig.getInstance() ;

        props.put("mail.smtp.host", config.getSmtpHost()) ;
        props.put("mail.smtp.auth", "true") ; //这样才能通过验证

        //获得对话 session
        Session session = Session.getDefaultInstance(props) ;
        session.setDebug(false) ;

        // 设置 message
        MimeMessage message = new MimeMessage(session) ;
        logger.debug("mail content is:" + mailVO.getContent()) ;
        message.setDataHandler(new DataHandler(mailVO.getContent(),
                                               "text/html; charset=gb2312")) ;
        //设置发件人
        message.setFrom(new InternetAddress(mailVO.getFrom())) ;
        //设置收件人，可以有多个
        Vector tmp = mailVO.getTo() ;
        java.util.Iterator arr = tmp.iterator() ;
        while (arr.hasNext())
        {
            message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress((String.valueOf(arr.next())))) ;
        }
        
        //添加抄送人
        Vector cc = mailVO.getCc() ;
        if(cc != null){    	
        	java.util.Iterator ccarr = cc.iterator() ;
        	while (ccarr.hasNext())
        	{
        		message.addRecipient(Message.RecipientType.CC,
        				new InternetAddress((String.valueOf(ccarr.next())))) ;
        	}
        }
        //设置邮件主题
        message.setSubject(mailVO.getSubject(), "gb2312") ;
        Multipart multipart = new MimeMultipart() ;
        MimeBodyPart messagePart = new MimeBodyPart() ;
        //设置邮件文本内容
        messagePart.setContent(mailVO.getContent(),
                               "text/html; charset=gb2312") ;

        multipart.addBodyPart(messagePart) ;
        //设置邮件附件，可以有多个
        if(null!=attaches&&attaches.length>0){
        	for(int i=0;i<attaches.length;i++){
        		messagePart = new MimeBodyPart();
                FileDataSource fds=new FileDataSource(attaches[i]); //得到数据源   
                messagePart.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart   
                messagePart.setFileName(MimeUtility.encodeText(attaches[i].getName()));  //得到文件名同样至入BodyPart   
                multipart.addBodyPart(messagePart);     		
        	}
        }
        message.setContent(multipart) ;
        message.setSentDate(new Date()) ;

        // 保存message
        message.saveChanges() ;

        //获得发送端口
        Transport transport = session.getTransport("smtp") ;
        transport.connect(config.getSmtpHost(), config.getUser(),
                          config.getPassword()) ;
        //发送邮件
        transport.sendMessage(message, message.getAllRecipients()) ;
        transport.close() ;
    }   
    
    
    /**
     * 发送一封邮件
     * @param mailVO MailVO
     * @throws Exception
     */
    public static void testSend (MailVO mailVO) throws Exception
    {

        // 获得系统Properties
        Properties props = new Properties() ;

        // 设置邮件服务器
        //MailConfig config = MailConfig.getInstance() ;

        props.put("mail.smtp.host", "szmail.aspire-tech.com") ;
        props.put("mail.smtp.auth", "true") ; //这样才能通过验证

        //获得对话 session
        Session session = Session.getDefaultInstance(props) ;
        session.setDebug(false) ;
        
        // 设置 message
        MimeMessage message = new MimeMessage(session) ;
        logger.debug("mail content is:" + mailVO.getContent()) ;
        message.setDataHandler(new DataHandler(mailVO.getContent(),
                                               "text/html; charset=gb2312")) ;
        //设置发件人
        message.setFrom(new InternetAddress(mailVO.getFrom())) ;
        //设置收件人，可以有多个
        Vector tmp = mailVO.getTo() ;
        java.util.Iterator arr = tmp.iterator() ;
        while (arr.hasNext())
        {
            message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress((String.valueOf(arr.next())))) ;
        }
        //设置邮件主题
        message.setSubject(mailVO.getSubject(), "gb2312") ;
        Multipart multipart = new MimeMultipart() ;
        MimeBodyPart messagePart = new MimeBodyPart() ;
        //设置邮件文本内容
        messagePart.setContent(mailVO.getContent(),
                               "text/html; charset=gb2312") ;

        multipart.addBodyPart(messagePart) ;
        //设置邮件附件，可以有多个

        message.setContent(multipart) ;
        message.setSentDate(new Date()) ;

        // 保存message
        message.saveChanges() ;

        //获得发送端口
        Transport transport = session.getTransport("smtp") ;
        transport.connect("szmail.aspire-tech.com", "baojun",
                          "2013$aspire") ;
        //发送邮件
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

