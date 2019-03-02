/*
 * 文件名：SearchFileSubject.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  用于定义发布者方法接口
 */
package com.aspire.ponaadmin.web.dataexport.searchfile;

import java.sql.ResultSet;

import com.aspire.common.exception.BOException;


/**
 * <p>Title: 发布者接口</p>
 * <p>Description: 用于定义发布者方法接口</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author x_wangml
 * @version 
 */
public interface SearchFileSubject
{
    /**
     * 添加订阅者
     * @param o
     */
    public void registerObserver(SearchFileObserver o);
    
    /**
     * 唤醒订阅者
     * @throws BOException 
     */
    public void notifyObserver(ResultSet rs) throws BOException;
    
    
    /**
     * 根据门户的类型，随即返回一个goodsid
     * 
     * @param relation
     * @param id
     * @return
     */
    public String getAppGoodsId(int relation, String id);
}
