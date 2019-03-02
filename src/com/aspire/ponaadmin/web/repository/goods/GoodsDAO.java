package com.aspire.ponaadmin.web.repository.goods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.ReferenceNode;

/**
 * 商品操作的DAO类
 * @author bihui
 *
 */
public class GoodsDAO
{
    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(GoodsDAO.class);
    
    /**
     * 构造方法，由singleton模式调用
     */
    private GoodsDAO()
    {

    }

    /**
     * singleton模式的实例
     */
    private static GoodsDAO goodsDAO = new GoodsDAO();

    /**
     * 获取实例
     *
     * @return 实例
     */
    public static final GoodsDAO getInstance()
    {

        return goodsDAO;
    }
    
    /**
     * 支持事务的数据库操作器，如果为空表示是非事务类型的操作
     */
    private TransactionDB transactionDB;
    
    /**
     * 获取事务类型TransactionDB的实例
     * 如果已经指定了，用已经指定的。如果没有指定，自己创建一个，注意自己创建的直接用不支持事务类型的即可
     * @return TransactionDB
     */
    private TransactionDB getTransactionDB()
    {
        if(this.transactionDB!=null)
        {
            return this.transactionDB;
        }
        return TransactionDB.getInstance();
    }
    
    /**
     * 获取事务类型的DAO实例
     * @return GoodsDAO
     */
    public static GoodsDAO getTransactionInstance(TransactionDB transactionDB)
    {
        GoodsDAO dao = new GoodsDAO();
        dao.transactionDB = transactionDB;
        return dao;
    }
    
    /**
     * 添加节点，并将GoodsVO中的信息写入到数据库中的商品历史信息表中
     * @param vo 商品VO
     * @param refNode 引用节点
     * @throws DAOException
     */
    public void addNodeAndInsertGoodsInfo(ReferenceNode refNode, GoodsVO vo) throws DAOException
    {

        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String updateTime = sdf.format(new Date());
        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsDAO.addNodeAndInsertGoodsInfo() is beginning...");
        }
        String sqlCode1 = "GoodsDAO.addNodeAndInsertGoodsInfo.INSERT1";
        String sqlCode2 = "GoodsDAO.addNodeAndInsertGoodsInfo.INSERT2";
        String sqlCode3 = "GoodsDAO.addNodeAndInsertGoodsInfo.INSERT3";
        Object[] paras1 = { refNode.getId(), refNode.getParentID(),
                        refNode.getPath(), refNode.getType() };
        Object[] paras2 = { refNode.getId(), refNode.getRefNodeID(),
                        new Integer(refNode.getSortID()),new Integer(refNode.getVariation()), refNode.getGoodsID(),
                        refNode.getCategoryID(), updateTime,refNode.getVerifyStatus(),refNode.getAppId()};
        Object[] paras3 = { vo.getGoodsID(), vo.getIcpCode(),
                        vo.getIcpServId(), vo.getContentID(),
                        vo.getCategoryID(), vo.getGoodsName(),
                        new Integer(vo.getState()), new Timestamp(vo.getChangeDate().getTime()),
                        new Integer(vo.getActionType())};
        TransactionDB tdb = this.getTransactionDB();
        tdb.executeBySQLCode(sqlCode1,paras1);
        tdb.executeBySQLCode(sqlCode2,paras2);
        tdb.executeBySQLCode(sqlCode3,paras3);       
    }
    /**
     * 添加节点，并将GoodsVO中的信息写入到数据库中的商品历史信息表中
     * @param vo 商品VO
     * @param refNode 引用节点
     * @throws DAOException
     */
    public void addNodeAndInsertGoodsInfoForLock(ReferenceNode refNode, GoodsVO vo) throws DAOException
    {

        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String updateTime = sdf.format(new Date());
        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsDAO.addNodeAndInsertGoodsInfoForLock() is beginning...");
        }
        //插入t_r_base表
        String sqlCode1 = "GoodsDAO.addNodeAndInsertGoodsInfo.INSERT1";
        //插入t_r_reference表
        String sqlCode2 = "GoodsDAO.addNodeAndInsertGoodsInfo.lock.INSERT2";
        //插入t_goods_his
        String sqlCode3 = "GoodsDAO.addNodeAndInsertGoodsInfo.INSERT3";
        Object[] paras1 = { refNode.getId(), refNode.getParentID(),
                        refNode.getPath(), refNode.getType() };
        Object[] paras2 = { refNode.getId(), refNode.getRefNodeID(),
                        new Integer(refNode.getSortID()),new Integer(refNode.getVariation()), refNode.getGoodsID(),
                        refNode.getCategoryID(), updateTime,refNode.getVerifyStatus(),new Integer(refNode.getIsLock()),refNode.getLockUser(),refNode.getAppId()};
        Object[] paras3 = { vo.getGoodsID(), vo.getIcpCode(),
                        vo.getIcpServId(), vo.getContentID(),
                        vo.getCategoryID(), vo.getGoodsName(),
                        new Integer(vo.getState()), new Timestamp(vo.getChangeDate().getTime()),
                        new Integer(vo.getActionType())};
        TransactionDB tdb = this.getTransactionDB();
        tdb.executeBySQLCode(sqlCode1,paras1);
        tdb.executeBySQLCode(sqlCode2,paras2);
        tdb.executeBySQLCode(sqlCode3,paras3);       
    }
    /**
     * 移除引用节点并将GoodsVO中的信息写入到数据库中的商品历史信息表中
     * @param vo
     * @throws DAOException
     */
    public void removeRefContentFromCategory(String refID,GoodsVO vo) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsDAO.removeRefContentFromCategory() is beginning...");
        }
        //商品下架
        String sqlCode1 = "GoodsDAO.removeRefContentFromCategory.DELETE1";
        String sqlCode2 = "GoodsDAO.removeRefContentFromCategory.DELETE2";
        String sqlCode3 = "GoodsDAO.removeRefContentFromCategory.INSERT";
        Object[] paras1 = {refID};
        Object[] paras2 = {refID};
        Object[] paras3 = { vo.getGoodsID(), vo.getIcpCode(),
                        vo.getIcpServId(), vo.getContentID(),
                        vo.getCategoryID(), vo.getGoodsName(),
                        new Integer(vo.getState()), new Timestamp(vo.getChangeDate().getTime()),
                        new Integer(vo.getActionType()),new Integer(vo.getLastState())};
        TransactionDB tdb = this.getTransactionDB();
        tdb.executeBySQLCode(sqlCode1,paras1);
        tdb.executeBySQLCode(sqlCode2,paras2);
        tdb.executeBySQLCode(sqlCode3,paras3);
    }
    
    /**
     * 通过contentID查找所有的引用ID
     * @param contentID
     * @return list 
     * @throws DAOException
     */
    public List getAllRefIDFromContentID(String contentID) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getAllRefIDFromContentID(" + contentID + ") is beginning ...." );
        }
        Object[] paras = { contentID };
        String sqlCode = "GoodsDAO.getAllRefIDFromContentID().SELECT";
        ArrayList list = new ArrayList();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while (rs.next())
            {
                list.add(rs.getString("id"));
            }
        }
        catch (SQLException ex)
        {
            throw new DAOException(ex);
        }
        finally
        {
            DB.close(rs);
        }
        return list;
    }
    
    
    /**
     * 通过商品编码去查找对应的其他商品信息
     * @return goodsID 
     * @throws DAOException
     */
    public GoodsInfoVO getOtherInfoByGoodsID(String goodsID) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsDAO.getOtherInfoByGoodsID(" + goodsID + ") is beginning ...." );
        }
        Object[] paras1 = {goodsID};
        String sqlCode1 = "GoodsDAO.getOtherInfoByGoodsID.SELECT1";
        ResultSet rs = null;
        GoodsInfoVO vo = null;
        try
        {
            //查找对应商品编码的货架编码、内容编码、商品名称、企业代码、业务代码
            rs = DB.getInstance().queryBySQLCode(sqlCode1,paras1);
            if(rs.next())
            {
                vo = new GoodsInfoVO();
                vo.setGoodsID(goodsID);
                vo.setCategoryID(rs.getString("categoryID"));
                vo.setContentID(rs.getString("contentID"));
                vo.setGoodsName(rs.getString("goodsName"));
                vo.setIcpCode(rs.getString("spid"));
                vo.setIcpServId(rs.getString("serviceid"));
            }
            else
            {
                return vo;
            }
            Object[] paras2 = {vo.getCategoryID()};
            String sqlCode2 = "GoodsDAO.getOtherInfoByGoodsID.SELECT2";
            //由货架编码查找货架名称
            rs = DB.getInstance().queryBySQLCode(sqlCode2,paras2);
            if(rs.next())
            {
                vo.setCategoryName(rs.getString("name"));
            }
            Object[] paras3 = {vo.getIcpCode(),vo.getIcpServId()};
            String sqlCode3 = "GoodsDAO.getOtherInfoByGoodsID.SELECT3";
            //由企业代码和业务代码查找对应的业务名称
            rs = DB.getInstance().queryBySQLCode(sqlCode3,paras3);
            if(rs.next())
            {
                vo.setServName(rs.getString("servName"));
            }
        }
        catch (SQLException e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs) ;
        }
        return vo;
    }
    
    /**
     * 通过contentID查找所有的商品信息
     * @param contentID
     * @return list 
     * @throws DAOException
     *//*
    public List getAllGoodsInfoByContentID(String contentID) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getAllGoodsInfoByContentID(" + contentID + ") is beginning ...." );
        }
        Object[] paras = { contentID };
        String sqlCode = "GoodsDAO.getAllGoodsInfoByContentID().SELECT";
        ArrayList list = new ArrayList();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while (rs.next())
            {
                GoodsInfoVO vo = getOtherInfoByGoodsID(rs.getString("goodsid"));
                list.add(vo);
            }
        }
        catch (SQLException ex)
        {
            throw new DAOException(ex);
        }
        finally
        {
            DB.close(rs);
        }
        return list;
    }*/
    
    /**
     * 通过contentID查找商品编码
     * @param contentID
     * @return String 
     * @throws DAOException
     */
    public String getGoodIdByContentID(String contentID) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getGoodIdByContentID(" + contentID + ") is beginning ...." );
        }
        Object[] paras = { contentID };
        String sqlCode = "GoodsDAO.getAllGoodsInfoByContentID().SELECT";
        String goodId = null;
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs.next())
            {
            	goodId = rs.getString("goodsid");
            }
        }
        catch (Exception ex)
        {
            throw new DAOException(ex);
        }
        finally
        {
            DB.close(rs);
        }
        return goodId;
    }
    
    /**
     * 根据商品编码查找商品ID（即t_r_reference表的id），如果找不到则说明此商品编码不存在于系统当前商品表中
     * @param goodsID
     * @return
     * @throws DAOException
     */
    public String getRefIDbyGoodsID(String goodsID) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRefIDbyGoodsID(" + goodsID + ") is beginning ...." );
        }
        Object[] paras = { goodsID };
        String sqlCode = "GoodsDAO.getRefIDbyGoodsID().SELECT";
        String id = null;
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
        try
        {    
            if (rs.next())
            {
                id = rs.getString("id");
            }
        }
        catch (SQLException ex)
        {
            throw new DAOException(ex);
        }
        finally
        {
            DB.close(rs);
        }
        return id;
    }
    
    public Map getRefNodebyRefID(String id) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getRefNodebyRefID(" + id + ") is beginning ...." );
        }
        Map refNode = null;
        Object[] paras = { id };
        String sqlCode = "GoodsDAO.getRefNodebyRefID().SELECT";
        String goodsID = null;
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
        try
        {    
//        	Goodsid	必须	String	reference表中的goodsid
//        	Categoryid	可选	String	货架categoryid，新建时必须有
//        	Id	可选	String	货架categoryid对应的Id，新建时必须有
//        	Refnodeid	可选	String	应用ID，新建时必须有
//        	Sortid	可选	String	排序字段，新建时必须有
//        	Loaddate	可选	String	更新时间，新建时必须有

        	//为了发上下架信息。需要获取以上这些量。add by aiyan 2013-04-27
            if (rs.next())
            {
            	refNode = new HashMap();
            	refNode.put("Goodsid", rs.getString("goodsid"));
            	refNode.put("Categoryid", rs.getString("categoryid"));
            	refNode.put("Id", rs.getString("id"));
            	refNode.put("Refnodeid", rs.getString("refnodeid"));
            	refNode.put("Sortid", rs.getString("sortid"));
            	String loaddate = rs.getString("loaddate");
            	loaddate = loaddate.replaceAll(" ", "");
            	loaddate = loaddate.replaceAll("-", "");
            	loaddate = loaddate.replaceAll(":", "");
            	refNode.put("Loaddate", loaddate);
            	
            }
        }
        catch (SQLException ex)
        {
            throw new DAOException(ex);
        }
        finally
        {
            DB.close(rs);
        }
        return refNode;
    }
    
//    public static void main(String[] argv){
//    	String a = "2009-06-10 10:10:04";
//    	a = a.replaceAll(" ", "");
//    	a = a.replaceAll("-", "");
//    	a = a.replaceAll(":", "");
//    	System.out.println(a);
//    }
//    
    /**
     * 判断商品表是否存在此 goodsid
     * @param goodsID 待查询的 goodsid
     * @return true 存在，false 不存在
     * @throws DAOException 
     */
    public boolean isExistedGoodsID(String goodsID) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("isExistedGoodsID(" + goodsID + ") is beginning ...." );
        }
        Object[] paras = { goodsID };
        String sqlCode = "GoodsDAO.isExistedGoodsID().SELECT";
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
        try
        {    
            if (rs.next())
            {
                return true;
            }else
            {
            	return false;
            }
            	
        }
        catch (SQLException ex)
        {
            throw new DAOException("查询历史表是否存在goosid为:"+goodsID+"时出错",ex);
        }
        finally
        {
            DB.close(rs);
        }
    }

}
