package com.aspire.ponaadmin.web.mail ;

import java.util.Vector ;

/**
 *
 * <p>Title: mail��vo��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MailVO
{
    //���巢���ˡ��ռ��ˡ������
    private Vector to ;

    //������
    private Vector cc;

	private String from;

    private String subject;

    private String content;

    public MailVO (Vector to, String from, String subject ,String content)
    {
        //��ʼ�������ˡ��ռ��ˡ������
        this.to = to ;
        this.from = from ;
        this.subject = subject ;
        this.content = content ;
    }
    
    public MailVO (Vector to,Vector cc, String from, String subject ,String content)
    {
        //��ʼ�������ˡ������ˡ��ռ��ˡ������
        this.to = to ;
        this.cc = cc ;
        this.from = from ;
        this.subject = subject ;
        this.content = content ;
    }

    public String getFrom ()
    {
        return from ;
    }

    public String getSubject ()
    {
        return subject ;
    }

    public Vector getTo ()
    {
        return to ;
    }
    
    public Vector getCc ()
    {
        return cc ;
    }

    public String getContent ()
    {
        return content ;
    }
}

