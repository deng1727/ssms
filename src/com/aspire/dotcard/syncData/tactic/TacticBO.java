
package com.aspire.dotcard.syncData.tactic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.DeviceVO;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.CategoryDeviceDAO;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * CMS内容同步策略管理
 * 
 * @author x_liyouli
 * 
 */
public class TacticBO
{

    /**
     * 日志引用
     */
    JLogger logger = LoggerFactory.getLogger(TacticBO.class);

    /**
     * 添加一个同步策略
     * 
     * @param vo
     */
    public void addTactic(TacticVO vo) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.addTactic(). TacticVO=" + vo);
        }
        try
        {
            new TacticDAO().addTactic(vo);
        }
        catch (DAOException e)
        {
            throw new BOException("添加一个同步策略失败！", e);
        }
    }

    /**
     * 查询单个同步策略
     * 
     * @param id
     * @return
     */
    public TacticVO queryByID(int id) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryByID(). ID=" + id);
        }
        try
        {
            return new TacticDAO().queryByID(id);
        }
        catch (DAOException e)
        {
            throw new BOException("查询单个同步策略失败！", e);
        }
    }

    /**
     * 查询所有的同步策略，不需要分页，按最后修改时间降序排列
     * 
     * @return
     */
    public List queryByCategoryID(String categoryID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryByCategoryID(). categoryID="
                         + categoryID);
        }
        try
        {
            return new TacticDAO().queryByCategoryID(categoryID);
        }
        catch (DAOException e)
        {
            throw new BOException("查询所有的同步策略失败！", e);
        }
    }

    /**
     * 查询所有的同步策略
     * 
     * @return
     */
    public List queryAll() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryAll().");
        }
        try
        {
            return new TacticDAO().queryAll();
        }
        catch (DAOException e)
        {
            throw new BOException("查询所有的同步策略失败！", e);
        }
    }

    /**
     * 查询MOTO所有的同步策略
     * 
     * @return
     */
    public List queryMOTOAll() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryAll().");
        }
        try
        {
            return new TacticDAO().queryMOTOAll();
        }
        catch (DAOException e)
        {
            throw new BOException("查询所有的同步策略失败！", e);
        }
    }
    
    /**
     * 查询HTC所有的同步策略
     * 
     * @return
     */
    public List queryHTCAll() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryHTCAll().");
        }
        try
        {
            return new TacticDAO().queryHTCAll();
        }
        catch (DAOException e)
        {
            throw new BOException("查询HTC所有的同步策略失败！", e);
        }
    }
    
    
    /**
     * 查询HTC所有的同步策略
     * 
     * @return
     */
    public List queryAndroidAll() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryAndroidAll().");
        }
        try
        {
            return new TacticDAO().queryAndroidAll();
        }
        catch (DAOException e)
        {
            throw new BOException("查询HTC所有的同步策略失败！", e);
        }
    }

    /**
     * 查询触点泛化合作渠道商对应根货架列表
     * 
     * @return
     */
    public List queryChannelsCategoryAll() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryChannelsCategoryAll().");
        }
        try
        {
            return new TacticDAO().queryChannelsCategoryAll();
        }
        catch (DAOException e)
        {
            throw new BOException("查询触点泛化合作渠道商对应根货架列表失败！", e);
        }
    }
    
    /**
     * 修改一个同步策略
     * 
     * @param vo
     */
    public void modifyTactic(TacticVO vo) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.modifyTactic(). TacticVO=" + vo);
        }
        try
        {
            new TacticDAO().modifyTactic(vo);
        }
        catch (DAOException e)
        {
            throw new BOException("修改一个同步策略失败！", e);
        }
    }

    /**
     * 删除一个同步策略
     * 
     * @param id
     */
    public void delTactic(int id) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.delTactic(). id=" + id);
        }
        try
        {
            new TacticDAO().delTactic(id);
        }
        catch (DAOException e)
        {
            throw new BOException("删除一个同步策略失败！", e);
        }
    }

    /**
     * 用于全量执行同步策略
     * 
     * @param categoryID
     * @throws BOException
     */
    public void exeTactic(String categoryID) throws BOException
    {
        Category cate = CategoryBO.getInstance().getCategory(categoryID);
        List tacticList = queryByCategoryID(categoryID);
        List contList = null;
        List refList = null;

        // 下架商品
        downGood(categoryID);
        
        // 根据同步策略获得上架商品列表
        contList = categoryTactic(tacticList, cate);

        // 如果是机型货架筛选上架商品
        if (1 == cate.getDeviceCategory())
        {
            refList = deviceCategoryTactic(contList, cate);
        }
        else
        {
            refList = deviceCategoryTactic(contList);
        }
        
        // 上架操作
        addGood(cate, refList);
    }

    /**
     * 根据同步策略获得上架商品列表
     * 
     * @param tacticList
     * @param cate
     */
    private List categoryTactic(List tacticList, Category cate)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.categoryTactic(). cid=" + cate.getId());
        }
        
        try
        {
            return new TacticDAO().categoryTactic(tacticList);
        }
        catch (DAOException e)
        {
            throw new BOException("根据同步策略获得上架商品列表失败！", e);
        }
    }
    
    /**
     * 机型货架筛选上架商品
     * 
     * @param contList
     * @param cate
     */
    private List deviceCategoryTactic(List contList, Category cate)
                    throws BOException
    {
        List device = null;
        List retList = new ArrayList();
        try
        {
            // 调用CategoryDeviceDAO进行查询所有机型货架限定机型
            device = CategoryDeviceDAO.getInstance()
                                      .queryDeviceByCategoryId(cate.getId());
        }
        catch (DAOException e)
        {
            throw new BOException("得到当前货架关联了哪些机型信息时发生数据库异常！", e);
        }
        
        // 筛选匹配
        for (Iterator iter = contList.iterator(); iter.hasNext();)
        {
            GContent con = ( GContent ) iter.next();
            String conDeviceId = con.getFulldeviceID();
            
            // 与机型货架机型进行匹配
            for (Iterator iterator = device.iterator(); iterator.hasNext();)
            {
                DeviceVO deviceVO = ( DeviceVO ) iterator.next();
                String categoryDevice = "{"+deviceVO.getDeviceId()+"}";
                
                // 如果有一个机型匹配，加入上架列表。开始下一个
                if(conDeviceId.indexOf(categoryDevice) >= 0)
                {
                    retList.add(con.getId());
                    break;
                }
            }
        }
        
        return retList;
    }
    
    /**
     * 转换数据
     * 
     * @param contList
     * @param cate
     */
    private List deviceCategoryTactic(List contList)
                    throws BOException
    {
        List retList = new ArrayList();

        for (Iterator iter = contList.iterator(); iter.hasNext();)
        {
            GContent con = ( GContent ) iter.next();
            retList.add(con.getId());
        }

        return retList;
    }



    /**
     * 上架商品
     * 
     * @param cate
     * @param contList
     * @throws BOException
     */
    private void addGood(Category cate, List contList) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("开始上架商品！");
        }
        
        // 起始排序值
        int sortId = 1000;
        
        for (int i = 0; i < contList.size(); i++)
        {
            String contId = ( String ) contList.get(i);
            CategoryTools.addGood(cate,
                                  contId,
                                  sortId--,
                                  RepositoryConstants.VARIATION_NEW,
                                  true,
                                  null);

        }
    }
    
    /**
     * 把指定货架下的商品下架
     * @param cId
     * @throws BOException
     */
    private void downGood(String cId) throws BOException
    {
        logger.info("下架商品");
        CategoryTools.clearCateGoods(cId, false);
    }
}
