
package com.aspire.dotcard.baseread.vo;

/**
 * 基地图书分类
 * 
 * @author x_zhailiqing
 * 
 */
public class RTypeVO
{

    private String typeId;

    private String typeName;

    public String getTypeId()
    {
        return typeId;
    }

    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public boolean setValue(String[] data)
    {
        if (data.length != 2)
        {
            return false;
        }
        if (null == data[0] || "".equals(data[0].trim()) || null == data[1]
            || "".equals(data[1].trim()))
        {
            return false;
        }
        typeId = data[0].trim();
        typeName = data[1].trim();
        return true;
    }
}
