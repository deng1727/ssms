package com.aspire.ponaadmin.web.repository ;

/**
 * <p>��Դ���е����ݽڵ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class Content extends EntityNode
{

    /**
     * �����ַ��������λ��
     */
    private static final int SECOND_POS = 14;
    
    /**
     * �����ַ����зֵ�λ��
     */
    private static final int MINUTE_POS = 12;
    
    /**
     * ������Դ���ͣ�ͼƬ
     */
    public static final String TYPE_PIC = "pic";

    /**
     * ������Դ���ͣ�����
     */
    public static final String TYPE_SOUND = "sound";

    /**
     * ������Դ���ͣ���Ƶ
     */
    public static final String TYPE_VIDEO = "video";

    /**
     * ������Դ���ͣ�flash
     */
    public static final String TYPE_FLASH = "flash";

    /**
     * ������Դ���ͣ�����
     */
    public static final String TYPE_CODE = "code";

    /**
     * ������Դ���ͣ�����
     */
    public static final String TYPE_OTHER = "other";

    /**
     * ���췽��
     */
    public Content()
    {
        this.type = "nt:content";
        this.setContext("");
        this.setSystem("MAXPortal");
    }

    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public Content(String nodeID)
    {
        super(nodeID);
        this.type = "nt:content";
        this.setContext("");
        this.setSystem("MAXPortal");
    }

    /**
     * ������Դ���ļ�����
     * @return��������Դ���ļ�����
     */
    public String getURL ()
    {
        String ext = this.getExt() ;
        if (ext != null)
        {
            return this.getId() + '.' + ext ;
        }
        return this.getId() ;
    }

    /**
     * ����������Դ����
     * @param value��������Դ����
     */
    public void setName(String value)
    {
        Property pro = new Property("name", value);
        this.setProperty(pro);
    }

    /**
     * ����������Դ����
     * @return��������Դ����
     */
    public String getName()
    {
        return (String) this.getProperty("name").getValue();
    }

    /**
     * ������Դ
     * @param value����Դ
     */
    public void setSource(String value)
    {
        Property pro = new Property("source", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ��Դ
     * @return����Դ
     */
    public String getSource()
    {
        return (String) this.getProperty("source").getValue();
    }

    /**
     * ��������
     * @param value������
     */
    public void setAuthor(String value)
    {
        Property pro = new Property("author", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ����
     * @return������
     */
    public String getAuthor()
    {
        return (String) this.getProperty("author").getValue();
    }

    /**
     * ���ùؼ���
     * @param value���ؼ���
     */
    public void setKeywords(String value)
    {
        Property pro = new Property("keywords", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ�ؼ���
     * @return���ؼ���
     */
    public String getKeywords()
    {
        return (String) this.getProperty("keywords").getValue();
    }

    /**
     * ��������
     * @param value������
     */
    public void setDesc(String value)
    {
        Property pro = new Property("desc", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ����
     * @return������
     */
    public String getDesc()
    {
        return (String) this.getProperty("desc").getValue();
    }

    /**
     * ���ô���ʱ��
     * @param value������ʱ��
     */
    public void setCreateTime(String value)
    {
        Property pro = new Property("createTime", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ����ʱ��
     * @return������ʱ��
     */
    public String getCreateTime()
    {
        return (String) this.getProperty("createTime").getValue();
    }

    /**
     * ��ȡ�������ڵ���ʾ
     * @return String �������ڵ���ʾ
     */
    public String getDisplayCreateTime()
    {
        try
        {
            String createTime = this.getCreateTime();
            String display = createTime.substring(0, 4) + '-' +
                createTime.substring(4, 6) + '-'
                + createTime.substring(6, 8) + ' ' +
                createTime.substring(8, 10)
                + ':' + createTime.substring(10, MINUTE_POS)
                + ':' + createTime.substring(MINUTE_POS, SECOND_POS) ;
            return display;
        }
        catch(Exception e)
        {
            return "";
        }
    }

    /**
     * �����޸�ʱ��
     * @param value���޸�ʱ��
     */
    public void setUpdateTime(String value)
    {
        Property pro = new Property("updateTime", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ�޸�ʱ��
     * @return���޸�ʱ��
     */
    public String getUpdateTime()
    {
        return (String) this.getProperty("updateTime").getValue();
    }

    /**
     * ��ȡ�������ڵ���ʾ
     * @return String �������ڵ���ʾ
     */
    public String getDisplayUpdateTime()
    {
        try
        {
            String updateTime = this.getUpdateTime();
            String display = updateTime.substring(0, 4) + '-' +
                updateTime.substring(4, 6) + '-'
                + updateTime.substring(6, 8) + ' ' +
                updateTime.substring(8, 10)
                + ':' + updateTime.substring(10, MINUTE_POS)
                + ':' + updateTime.substring(MINUTE_POS, SECOND_POS) ;
            return display;
        }
        catch(Exception e)
        {
            return "";
        }
    }

    /**
     * ������Դ�ļ���չ��
     * @param value����Դ�ļ���չ��
     */
    public void setExt(String value)
    {
        Property pro = new Property("ext", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ��Դ�ļ���չ��
     * @return����Դ�ļ���չ��
     */
    public String getExt()
    {
        return (String) this.getProperty("ext").getValue();
    }

//    public void setContents(byte[] value)
//    {
//        Property pro = new Property("contents",value);
//        this.setProperty(pro);
//    }
//
//    public byte[] getContents()
//    {
//        return (byte[])this.getProperty("contents").getValue();
//    }

    /**
     * ��������
     * @param value������
     */
    public void setContentsType(String value)
    {
        Property pro = new Property("contentsType", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ����
     * @return������
     */
    public String getContentsType()
    {
        return (String) this.getProperty("contentsType").getValue();
    }
    
    /**
     * ��ȡ�������͵����������ַ�
     * @return String ���͵���������
     */
    public String getConstentsTypeDesc()
    {
        String contentType = (String) this.getProperty("contentsType").getValue();
        String result = "";
        if(contentType.equals(Content.TYPE_PIC))
        {
            result = "ͼƬ";
        }
        else if(contentType.equals(Content.TYPE_VIDEO))
        {
            result = "��Ƶ";
        }
        else if(contentType.equals(Content.TYPE_SOUND))
        {
            result = "��Ƶ";
        }
        else if(contentType.equals(Content.TYPE_FLASH))
        {
            result = "flash����";
        }
        else if(contentType.equals(Content.TYPE_CODE))
        {
            result = "����";
        }
        else if(contentType.equals(Content.TYPE_OTHER))
        {
            result = "����";
        }
        return result;
    }

    /**
     * ���ô�С
     * @param value����С
     */
    public void setSize(long value)
    {
       Property pro = new Property("size", new Long(value));
        this.setProperty(pro);
    }

    /**
     * ��ȡ��С
     * @return����С
     */
    public long getSize()
    {
        return ((Long) this.getProperty("size").getValue()).longValue();
    }

    /**
     * ���ÿ�
     * @param value����
     */
    public void setWidth(long value)
    {
        Property pro = new Property("width", new Long(value));
        this.setProperty(pro);
    }

    /**
     * ��ȡ��
     * @return����
     */
    public long getWidth()
    {
        return ((Long) this.getProperty("width").getValue()).longValue();
    }

    /**
     * ���ø�
     * @param value����
     */
    public void setHeight(long value)
    {
        Property pro = new Property("height", new Long(value));
        this.setProperty(pro);
    }

    /**
     * ��ȡ��
     * @return����
     */
    public long getHeight()
    {
        return ((Long) this.getProperty("height").getValue()).longValue();
    }

    /**
     * ����ʹ��ϵͳ���������������ʱ����ʹ��
     * @param value��ʹ��ϵͳ
     */
    public void setSystem(String value)
    {
        Property pro = new Property("system", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡʹ��ϵͳ���������������ʱ����ʹ��
     * @return��ʹ��ϵͳ
     */
    public String getSystem()
    {
        return (String) this.getProperty("system").getValue();
    }

    /**
     * ���������ģ��������������ʱ����ʹ��
     * @param value��������
     */
    public void setContext(String value)
    {
        Property pro = new Property("context", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ�����ģ��������������ʱ����ʹ��
     * @return��������
     */
    public String getContext()
    {
        return (String) this.getProperty("context").getValue();
    }

    /**
     * toString����
     * @return ������Ϣ
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Content[");
        sb.append(this.getProperty("name"));
        sb.append(',');
        sb.append(this.getProperty("source"));
        sb.append(',');
        sb.append(this.getProperty("author"));
        sb.append(',');
        sb.append(this.getProperty("keywords"));
        sb.append(',');
        sb.append(this.getProperty("desc"));
        sb.append(',');
        sb.append(this.getProperty("createTime"));
        sb.append(',');
        sb.append(this.getProperty("updateTime"));
        sb.append(',');
        sb.append(this.getProperty("contentsType"));
        sb.append(',');
        sb.append(this.getProperty("size"));
        sb.append(',');
        sb.append(this.getProperty("width"));
        sb.append(',');
        sb.append(this.getProperty("height"));
        sb.append(',');
        sb.append(super.toString());
        sb.append("]");
        return sb.toString();
    }
}
