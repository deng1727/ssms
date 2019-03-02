package com.aspire.ponaadmin.common.rightmanager ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>权限相关的DAO类</p>
 * <p>权限相关的DAO类，对权限数据做了内部缓存</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class RightDAO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(RightDAO.class) ;

    /**
     * 保存所有pageURIVO的map
     */
    private HashMap pageURIMap = null;

    /**
     * 保存所有权限RightVO的map
     */
    private HashMap rightMap = null;

    /**
     * singleton模式的实例
     */
    private static RightDAO instance = null ;

    /**
     * 构造方法，由singleton模式调用
     */
    private RightDAO () throws DAOException
    {
        this.loadAllPageRUI();
        this.loadAllRight();
    }

    /**
     * 获取实例
     * @return 实例
     */
    public static RightDAO getInstance () throws DAOException
    {
        if(instance == null)
        {
            instance = new RightDAO();
        }
        return instance ;
    }

    /**
     * 获取一个http请求uri对应页面所属于的权限
     * @param pageURI http请求uri
     * @return 页面对应的权限
     */
    final RightVO getRightOfPageURI (String pageURI)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRightOfPageURI(" + pageURI + ")") ;
        }
        RightVO right = null;
        PageURIVO page = (PageURIVO) this.pageURIMap.get(pageURI);
        if(page != null)
        {
            right = (RightVO) this.rightMap.get(page.getRightID());

        }
        return right;
    }

    /**
     * 从数据库中载入所有的pageURI
     */
    private void loadAllPageRUI() throws DAOException
    {
        logger.debug("loadAllPageRUI()");
        String sqlCode = "rightmanager.RightDAO.loadAllPageRUI().SELECT";
        this.pageURIMap = new HashMap();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            while(rs.next())
            {
                PageURIVO page = this.getPageURIVO(rs);
                this.pageURIMap.put(page.getPageURI(), page);
            }
        }
        catch(SQLException e)
        {
            throw new DAOException(e);
        }
        finally
        {
            if(rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(SQLException e)
                {
                    logger.error(e);
                }
            }
        }
    }

    /**
     * 从数据库中载入所有的权限RightVO
     */
    private void loadAllRight() throws DAOException
    {
        logger.debug("loadAllRight()");
        String sqlCode = "rightmanager.RightDAO.loadAllRight().SELECT";
        this.rightMap = new HashMap();
        //用来构造复合权限的临时map
        HashMap tmpParentMap = new HashMap();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            while(rs.next())
            {
                RightVO right = null;
                int levels = rs.getInt("levels");
                String rightID = rs.getString("RIGHTID");
                if(levels > 0)
                {
                    //是复合类型
                    right = new CompositeRightVO();
                    //从临时map取出所有复合权限的下属权限id
                    List rightOfParent = (List) tmpParentMap.get(rightID);
                    //到所有已经载入权限map中取出下属权限，并设置到复合权限中
                    List rightListOfParent = new ArrayList();
                    for(int i = 0; i < rightOfParent.size(); i++)
                    {
                        rightListOfParent.add(
                            this.rightMap.get(rightOfParent.get(i))) ;
                    }
                    ((CompositeRightVO) right).setRightList(rightListOfParent);
                }
                else
                {
                    //不是复合类型
                    right = new RightVO();
                }

                //如果这个权限有父权限（属于某个复合权限）
                //把这个权限的id保存到临时map中，等到处理复合权限时就可以取出来。
                String parentID = rs.getString("PARENTID");
                if(parentID != null && parentID.trim().equals(""))
                {
                    parentID = null;
                }
                if(parentID != null)
                {
                    List rightOfParent = (List) tmpParentMap.get(parentID);
                    if(rightOfParent == null)
                    {
                        rightOfParent = new ArrayList();
                        tmpParentMap.put(parentID, rightOfParent);
                    }
                    rightOfParent.add(rightID);
                }

                right.setRightID(rightID) ;
                right.setName(rs.getString("NAME")) ;
                right.setDesc(rs.getString("DESCS")) ;
                right.setParentID(parentID) ;
                this.rightMap.put(right.getRightID(), right) ;
            }
        }
        catch(SQLException e)
        {
            throw new DAOException(e);
        }
        finally
        {
            if(rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(SQLException e)
                {
                    logger.error(e);
                }
            }
        }
    }

    /**
     * 获取系统所有权限列表。不包含站点目录权限。
     * @return list,权限列表
     */
    final List getAllRight ()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getAllRight") ;
        }
        List rightList = new ArrayList();
        rightList.addAll(this.rightMap.values());
        return rightList ;
    }

    /**
     * 通过权限id获取权限信息。
     * @param rightID String 权限id
     * @return RightVO 权限信息
     */
    final RightVO getRightVOByID(String rightID)
    {
        return (RightVO) this.rightMap.get(rightID);
    }

    /**
     * 从记录集获取PageURIVO对象
     * @param rs ResultSet 记录集
     * @return PageURIVO PageURIVO对象
     * @throws SQLException
     */
    private PageURIVO getPageURIVO(ResultSet rs) throws SQLException
    {
        PageURIVO page = new PageURIVO();
        page.setRightID(rs.getString("RIGHTID")) ;
        page.setPageURI(rs.getString("PAGEURI")) ;
        page.setDesc(rs.getString("DESCS")) ;
        return page;
    }
}
