/*
 * 
 */
package com.aspire.ponaadmin.web.repository.web;


/**
 * @author x_wangml
 *
 */
public class XBusValueVo
{
    /**
     * �������з�
     */
    private static final String nextLine = "\r\n";
    
    /**
     * ��Ʒid
     */
    private String id;
    
    /**
     * ��ƷgoodId
     */
    private String goodId;
    
    /**
     * ���������
     */
    private String sortId;
    
    /**
     * ��Ʒ·��
     */
    private String path;
    
    /**
     * ��Ʒ����
     */
    private String type;

    
    public String getGoodId()
    {
    
        return goodId;
    }

    public String getId()
    {
    
        return id;
    }

    
    public void setId(String id)
    {
    
        this.id = id;
    }

    
    public String getSortId()
    {
    
        return sortId;
    }

    
    public void setSortId(String sortId)
    {
    
        this.sortId = sortId;
    }
    
    public String getPath()
    {
    
        return path;
    }

    public void setGoodId(String goodId)
    {
    
        this.goodId = goodId;
    }

    public void setPath(String path)
    {
    
        this.path = path;
    }
    
    public String getType()
    {
    
        return type;
    }

    
    public void setType(String type)
    {
    
        this.type = type;
    }
    
    /**
     * �������ɷ��͵�xml��������
     */
    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("<good>").append(nextLine);
        str.append("<id>").append(this.id).append("</id>").append(nextLine);
        str.append("<path>").append(this.path).append("</path>").append(nextLine);
        str.append("<type>").append(this.type).append("</type>").append(nextLine);
        str.append("<goodId>").append(this.goodId).append("</goodId>").append(nextLine);
        str.append("<sortId>").append(this.sortId).append("</sortId>").append(nextLine);
        str.append("</good>").append(nextLine);
        return str.toString();
    }
}
