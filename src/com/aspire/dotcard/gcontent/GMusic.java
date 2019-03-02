package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>
 * 资源树中的基地音乐节点类
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author bihui
 * @version 1.0.0.0
 */
public class GMusic extends GContent
{
    /**
     * 资源类型：内容类型，彩铃
     */
    public static final String TYPE_MUSIC = "nt:gcontent:music";

    /**
     * 构造方法
     */
    public GMusic()
    {
        this.type = TYPE_MUSIC;
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public GMusic(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_MUSIC;
    }

    /**
     * 获取歌手名
     * @return Returns the singer.
     */
    public String getSinger()
    {
        return getNoNullString((String) this.getProperty("singer").getValue());
    }


    /**
     * 设置歌手名
     * @param singer The singer to set.
     */
    public void setSinger(String singer)
    {
        Property pro = new Property("singer", singer);
        this.setProperty(pro);
    }
    
    /**
     * 设置 歌曲归属专辑名称
     * @param value， 歌曲归属专辑名称
     */
    public void setAlbum(String album)
    {
        Property pro = new Property("album", album);
        this.setProperty(pro);
    }

    /**
     * 获取 歌曲归属专辑名称
     * @return， 歌曲归属专辑名称
     */
    public String getAlbum()
    {
        return getNoNullString((String) this.getProperty("album").getValue());
    }
    
    /**
     * 变更类型，1：新增；2：更新；3：下线。全量文件中都是1，增量文件中三种值都可能出现。
     * @return Returns the ChangeType.
     */
    public String getChangeType()
    {
        return getNoNullString((String) this.getProperty("changeType").getValue());
    }


    /**
     * 变更类型，1：新增；2：更新；3：下线。全量文件中都是1，增量文件中三种值都可能出现。
     * @param changetype
     */
    public void setChangeType(String changetype)
    {
        Property pro = new Property("changeType", changetype);
        this.setProperty(pro);
    }

}
