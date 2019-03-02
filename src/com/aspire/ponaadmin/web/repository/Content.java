package com.aspire.ponaadmin.web.repository ;

/**
 * <p>资源树中的内容节点类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class Content extends EntityNode
{

    /**
     * 日期字符串中妙的位置
     */
    private static final int SECOND_POS = 14;
    
    /**
     * 日期字符串中分的位置
     */
    private static final int MINUTE_POS = 12;
    
    /**
     * 内容资源类型：图片
     */
    public static final String TYPE_PIC = "pic";

    /**
     * 内容资源类型：声音
     */
    public static final String TYPE_SOUND = "sound";

    /**
     * 内容资源类型：视频
     */
    public static final String TYPE_VIDEO = "video";

    /**
     * 内容资源类型：flash
     */
    public static final String TYPE_FLASH = "flash";

    /**
     * 内容资源类型：代码
     */
    public static final String TYPE_CODE = "code";

    /**
     * 内容资源类型：其它
     */
    public static final String TYPE_OTHER = "other";

    /**
     * 构造方法
     */
    public Content()
    {
        this.type = "nt:content";
        this.setContext("");
        this.setSystem("MAXPortal");
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public Content(String nodeID)
    {
        super(nodeID);
        this.type = "nt:content";
        this.setContext("");
        this.setSystem("MAXPortal");
    }

    /**
     * 内容资源的文件名称
     * @return，内容资源的文件名称
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
     * 设置内容资源名称
     * @param value，内容资源名称
     */
    public void setName(String value)
    {
        Property pro = new Property("name", value);
        this.setProperty(pro);
    }

    /**
     * 设置内容资源名称
     * @return，内容资源名称
     */
    public String getName()
    {
        return (String) this.getProperty("name").getValue();
    }

    /**
     * 设置来源
     * @param value，来源
     */
    public void setSource(String value)
    {
        Property pro = new Property("source", value);
        this.setProperty(pro);
    }

    /**
     * 获取来源
     * @return，来源
     */
    public String getSource()
    {
        return (String) this.getProperty("source").getValue();
    }

    /**
     * 设置作者
     * @param value，作者
     */
    public void setAuthor(String value)
    {
        Property pro = new Property("author", value);
        this.setProperty(pro);
    }

    /**
     * 获取作者
     * @return，作者
     */
    public String getAuthor()
    {
        return (String) this.getProperty("author").getValue();
    }

    /**
     * 设置关键字
     * @param value，关键字
     */
    public void setKeywords(String value)
    {
        Property pro = new Property("keywords", value);
        this.setProperty(pro);
    }

    /**
     * 获取关键字
     * @return，关键字
     */
    public String getKeywords()
    {
        return (String) this.getProperty("keywords").getValue();
    }

    /**
     * 设置描述
     * @param value，描述
     */
    public void setDesc(String value)
    {
        Property pro = new Property("desc", value);
        this.setProperty(pro);
    }

    /**
     * 获取描述
     * @return，描述
     */
    public String getDesc()
    {
        return (String) this.getProperty("desc").getValue();
    }

    /**
     * 设置创建时间
     * @param value，创建时间
     */
    public void setCreateTime(String value)
    {
        Property pro = new Property("createTime", value);
        this.setProperty(pro);
    }

    /**
     * 获取创建时间
     * @return，创建时间
     */
    public String getCreateTime()
    {
        return (String) this.getProperty("createTime").getValue();
    }

    /**
     * 获取操作日期的显示
     * @return String 操作日期的显示
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
     * 设置修改时间
     * @param value，修改时间
     */
    public void setUpdateTime(String value)
    {
        Property pro = new Property("updateTime", value);
        this.setProperty(pro);
    }

    /**
     * 获取修改时间
     * @return，修改时间
     */
    public String getUpdateTime()
    {
        return (String) this.getProperty("updateTime").getValue();
    }

    /**
     * 获取操作日期的显示
     * @return String 操作日期的显示
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
     * 设置资源文件扩展名
     * @param value，资源文件扩展名
     */
    public void setExt(String value)
    {
        Property pro = new Property("ext", value);
        this.setProperty(pro);
    }

    /**
     * 获取资源文件扩展名
     * @return，资源文件扩展名
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
     * 设置类型
     * @param value，类型
     */
    public void setContentsType(String value)
    {
        Property pro = new Property("contentsType", value);
        this.setProperty(pro);
    }

    /**
     * 获取类型
     * @return，类型
     */
    public String getContentsType()
    {
        return (String) this.getProperty("contentsType").getValue();
    }
    
    /**
     * 获取内容类型的中文描述字符
     * @return String 类型的中文描述
     */
    public String getConstentsTypeDesc()
    {
        String contentType = (String) this.getProperty("contentsType").getValue();
        String result = "";
        if(contentType.equals(Content.TYPE_PIC))
        {
            result = "图片";
        }
        else if(contentType.equals(Content.TYPE_VIDEO))
        {
            result = "视频";
        }
        else if(contentType.equals(Content.TYPE_SOUND))
        {
            result = "音频";
        }
        else if(contentType.equals(Content.TYPE_FLASH))
        {
            result = "flash动画";
        }
        else if(contentType.equals(Content.TYPE_CODE))
        {
            result = "代码";
        }
        else if(contentType.equals(Content.TYPE_OTHER))
        {
            result = "其它";
        }
        return result;
    }

    /**
     * 设置大小
     * @param value，大小
     */
    public void setSize(long value)
    {
       Property pro = new Property("size", new Long(value));
        this.setProperty(pro);
    }

    /**
     * 获取大小
     * @return，大小
     */
    public long getSize()
    {
        return ((Long) this.getProperty("size").getValue()).longValue();
    }

    /**
     * 设置宽
     * @param value，宽
     */
    public void setWidth(long value)
    {
        Property pro = new Property("width", new Long(value));
        this.setProperty(pro);
    }

    /**
     * 获取宽
     * @return，宽
     */
    public long getWidth()
    {
        return ((Long) this.getProperty("width").getValue()).longValue();
    }

    /**
     * 设置高
     * @param value，高
     */
    public void setHeight(long value)
    {
        Property pro = new Property("height", new Long(value));
        this.setProperty(pro);
    }

    /**
     * 获取高
     * @return，高
     */
    public long getHeight()
    {
        return ((Long) this.getProperty("height").getValue()).longValue();
    }

    /**
     * 设置使用系统，不过这个属性暂时不会使用
     * @param value，使用系统
     */
    public void setSystem(String value)
    {
        Property pro = new Property("system", value);
        this.setProperty(pro);
    }

    /**
     * 获取使用系统，不过这个属性暂时不会使用
     * @return，使用系统
     */
    public String getSystem()
    {
        return (String) this.getProperty("system").getValue();
    }

    /**
     * 设置上下文，不过这个属性暂时不会使用
     * @param value，上下文
     */
    public void setContext(String value)
    {
        Property pro = new Property("context", value);
        this.setProperty(pro);
    }

    /**
     * 获取上下文，不过这个属性暂时不会使用
     * @return，上下文
     */
    public String getContext()
    {
        return (String) this.getProperty("context").getValue();
    }

    /**
     * toString方法
     * @return 描述信息
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
