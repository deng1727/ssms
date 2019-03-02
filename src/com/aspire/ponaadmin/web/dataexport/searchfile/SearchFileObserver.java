/*
 * 文件名：SearchFileObserver.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  用于定义订阅者方法接口
 */
package com.aspire.ponaadmin.web.dataexport.searchfile;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.exception.BOException;


/**
 * <p>Title: 订阅者接口</p>
 * <p>Description: 用于定义订阅者方法接口</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author x_wangml
 * @version 
 */
public interface SearchFileObserver
{
    /**
     * 由发布者传过来数据
     * @param rs
     * @throws BOException 
     * @throws SQLException 
     */
    public void addDate(ResultSet rs) throws BOException, SQLException;
    
    /**
     * 返回当前数据是否是订阅者数据类型
     * @param marker 内容标识
     * @return
     */
    public boolean isAppMarker(String marker);
    
    /**
     * 生成文件
     */
    public void createFile();
    
    /**
     * 返回用于发送邮件时的信息
     * @param isTrue 生成文件是否成功
     * @return
     */
    public String getMailContent(boolean isTrue);
}
