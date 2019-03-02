/*
 * 文件名：SearchFileSubjectImpl.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  用于实现发布者方法
 */

package com.aspire.ponaadmin.web.dataexport.searchfile.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.searchfile.SearchFileConfig;
import com.aspire.dotcard.searchfile.SearchFileConstants;
import com.aspire.dotcard.searchfile.SearchFileGenerateDAO;
import com.aspire.ponaadmin.web.dataexport.searchfile.SearchFileObserver;
import com.aspire.ponaadmin.web.dataexport.searchfile.SearchFileSubject;

/**
 * <p>
 * Title: 发布者实现类
 * </p>
 * <p>
 * Description: 用于实现发布者方法
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class SearchFileSubjectImpl implements SearchFileSubject
{

    private static final JLogger LOG = LoggerFactory.getLogger(SearchFileSubjectImpl.class);

    /**
     * 订阅者列表
     */
    private List observers;

    private Map goodsidMappingForMO;

    private Map goodsisMappingForWWW;

    private Map goodsisMappingForWAP;
    
    private List sucessList = new ArrayList();

    private List failureList = new ArrayList();

    /**
     * 全部内容列表
     */
    private ResultSet dataList;

    /**
     * 构造方法
     */
    public SearchFileSubjectImpl(Map goodsidMappingForMO,
                                 Map goodsisMappingForWWW,
                                 Map goodsisMappingForWAP)
    {

        observers = new ArrayList();
        this.goodsidMappingForMO = goodsidMappingForMO;
        this.goodsisMappingForWWW = goodsisMappingForWWW;
        this.goodsisMappingForWAP = goodsisMappingForWAP;
    }

    /**
     * 添加订阅者
     * 
     * @param o
     */
    public void registerObserver(SearchFileObserver o)
    {

        observers.add(o);
    }

    /**
     * 唤醒订阅者
     * 
     * @throws BOException
     * 
     * @throws BOException
     */
    public void notifyObserver(ResultSet rs) throws BOException
    {

        try
        {
            // 循环得到内容信息
            while (rs.next())
            {
                // 内容类型
                String type = rs.getString("type");
                // 内容提供者类型 表示自有业务，和基地业务
                String provider = rs.getString("provider");
                // 用户来源（L为广东用户 G为集团用户）
                String servattr = rs.getString("servattr");
                // 组合标识码 type&provider&servattr
                String marker = getMarker(type, provider, servattr);

                for (int i = 0; i < observers.size(); i++)
                {
                    SearchFileObserver element = ( SearchFileObserver ) observers.get(i);

                    if (element.isAppMarker(marker))
                    {
                        try
                        {
                            element.addDate(rs);
                        }
                        catch (Exception e)
                        {
                        	LOG.error("这个导出任务出错了！",e);
                            observers.remove(i);
                            failureList.add(element.getMailContent(false));
                        }
                    }
                }
            }
            rs.close();
        }
        catch (SQLException e)
        {
            throw new BOException("读取数据库异常.", e);
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
            catch (Exception e1)
            {
            }
        }
    }

    /**
     * 主流程
     * 
     * @throws DAOException
     * @throws BOException
     * @throws
     */
    public void prepareAllData() throws DAOException, BOException
    {

        if (goodsidMappingForMO == null)
        {
            goodsidMappingForMO = SearchFileGenerateDAO.getInstance()
                                                       .getAllIDMappingGoodsId();
        }

        if (goodsisMappingForWWW == null)
        {
            goodsisMappingForWWW = SearchFileGenerateDAO.getInstance()
                                                        .getAllIDMappingGoodsIdByWWW();
        }

        if (goodsisMappingForWAP == null)
        {
            goodsisMappingForWAP = SearchFileGenerateDAO.getInstance()
                                                        .getAllIDMappingGoodsIdByWAP();
        }
        
        int sum = SearchFileGenerateDAO.getInstance().getAllContentDataSum();
        
        LOG.info("当前搜索导出全量内容数据数为:" + sum);
        
        int pageContentNum = SearchFileConfig.pageSize;
        
        // 如果要生成文件的全量总数大于十万。就分批的查询得到数据生成。
        if(sum > pageContentNum)
        {
        	// 得到总页数
        	int pageNum = (sum + pageContentNum - 1) / pageContentNum;
        	int start = 0;
        	int end = 0;
        	
        	LOG.info("当前搜索导出分页数据每页为:" + pageContentNum + " 共分" + pageNum + "页查询.");
        	
        	for(int i=0; i<pageNum ;i++)
        	{
        		start = i * pageContentNum;
        		end = (i + 1) * pageContentNum;
        		
        		LOG.info("此次查询搜索导出分页数据为>第:" + start + "条<=第" + end + "条.");
        		
        		dataList = SearchFileGenerateDAO.getInstance().getAllContentDataByPageNum(start, end);
        		notifyObserver(dataList);
        	}
        }
        else
        {
        	dataList = SearchFileGenerateDAO.getInstance().getAllContentData();
        	notifyObserver(dataList);
        }

        this.createFiles();

    }

    /**
     * 生成文件
     */
    public void createFiles()
    {

        for (Iterator iter = observers.iterator(); iter.hasNext();)
        {
            SearchFileObserver element = ( SearchFileObserver ) iter.next();

            element.createFile();
            
            sucessList.add(element.getMailContent(true));
        }
    }

    /**
     * 返回当前内容数据的标记。用以匹配生成文件
     * 
     * @param type 内容类型
     * @param provider 内容提供者类型
     * @param servattr 用户来源
     * @return 组合标识码
     */
    private String getMarker(String type, String provider, String servattr)
    {

        return type + "&" + provider + "&" + servattr;
    }

    /**
     * 根据门户的类型，随即返回一个goodsid
     * 
     * @param relation
     * @param id
     * @return
     */
    public String getAppGoodsId(int relation, String id)
    {

        switch (relation)
        {
            case SearchFileConstants.RELATION_W:
                return ( String ) goodsisMappingForWWW.get(id);
            case SearchFileConstants.RELATION_O:
                return ( String ) goodsidMappingForMO.get(id);
            case SearchFileConstants.RELATION_A:
                return ( String ) goodsisMappingForWAP.get(id);
            default:
                return null;
        }
    }
    
    public List getFailureList()
    {
    
        return failureList;
    }

    
    public List getSucessList()
    {
    
        return sucessList;
    }
}
