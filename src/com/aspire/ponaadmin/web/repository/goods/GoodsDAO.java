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
 * ��Ʒ������DAO��
 * @author bihui
 *
 */
public class GoodsDAO
{
    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(GoodsDAO.class);
    
    /**
     * ���췽������singletonģʽ����
     */
    private GoodsDAO()
    {

    }

    /**
     * singletonģʽ��ʵ��
     */
    private static GoodsDAO goodsDAO = new GoodsDAO();

    /**
     * ��ȡʵ��
     *
     * @return ʵ��
     */
    public static final GoodsDAO getInstance()
    {

        return goodsDAO;
    }
    
    /**
     * ֧����������ݿ�����������Ϊ�ձ�ʾ�Ƿ��������͵Ĳ���
     */
    private TransactionDB transactionDB;
    
    /**
     * ��ȡ��������TransactionDB��ʵ��
     * ����Ѿ�ָ���ˣ����Ѿ�ָ���ġ����û��ָ�����Լ�����һ����ע���Լ�������ֱ���ò�֧���������͵ļ���
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
     * ��ȡ�������͵�DAOʵ��
     * @return GoodsDAO
     */
    public static GoodsDAO getTransactionInstance(TransactionDB transactionDB)
    {
        GoodsDAO dao = new GoodsDAO();
        dao.transactionDB = transactionDB;
        return dao;
    }
    
    /**
     * ��ӽڵ㣬����GoodsVO�е���Ϣд�뵽���ݿ��е���Ʒ��ʷ��Ϣ����
     * @param vo ��ƷVO
     * @param refNode ���ýڵ�
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
     * ��ӽڵ㣬����GoodsVO�е���Ϣд�뵽���ݿ��е���Ʒ��ʷ��Ϣ����
     * @param vo ��ƷVO
     * @param refNode ���ýڵ�
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
        //����t_r_base��
        String sqlCode1 = "GoodsDAO.addNodeAndInsertGoodsInfo.INSERT1";
        //����t_r_reference��
        String sqlCode2 = "GoodsDAO.addNodeAndInsertGoodsInfo.lock.INSERT2";
        //����t_goods_his
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
     * �Ƴ����ýڵ㲢��GoodsVO�е���Ϣд�뵽���ݿ��е���Ʒ��ʷ��Ϣ����
     * @param vo
     * @throws DAOException
     */
    public void removeRefContentFromCategory(String refID,GoodsVO vo) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsDAO.removeRefContentFromCategory() is beginning...");
        }
        //��Ʒ�¼�
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
     * ͨ��contentID�������е�����ID
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
     * ͨ����Ʒ����ȥ���Ҷ�Ӧ��������Ʒ��Ϣ
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
            //���Ҷ�Ӧ��Ʒ����Ļ��ܱ��롢���ݱ��롢��Ʒ���ơ���ҵ���롢ҵ�����
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
            //�ɻ��ܱ�����һ�������
            rs = DB.getInstance().queryBySQLCode(sqlCode2,paras2);
            if(rs.next())
            {
                vo.setCategoryName(rs.getString("name"));
            }
            Object[] paras3 = {vo.getIcpCode(),vo.getIcpServId()};
            String sqlCode3 = "GoodsDAO.getOtherInfoByGoodsID.SELECT3";
            //����ҵ�����ҵ�������Ҷ�Ӧ��ҵ������
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
     * ͨ��contentID�������е���Ʒ��Ϣ
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
     * ͨ��contentID������Ʒ����
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
     * ������Ʒ���������ƷID����t_r_reference���id��������Ҳ�����˵������Ʒ���벻������ϵͳ��ǰ��Ʒ����
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
//        	Goodsid	����	String	reference���е�goodsid
//        	Categoryid	��ѡ	String	����categoryid���½�ʱ������
//        	Id	��ѡ	String	����categoryid��Ӧ��Id���½�ʱ������
//        	Refnodeid	��ѡ	String	Ӧ��ID���½�ʱ������
//        	Sortid	��ѡ	String	�����ֶΣ��½�ʱ������
//        	Loaddate	��ѡ	String	����ʱ�䣬�½�ʱ������

        	//Ϊ�˷����¼���Ϣ����Ҫ��ȡ������Щ����add by aiyan 2013-04-27
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
     * �ж���Ʒ���Ƿ���ڴ� goodsid
     * @param goodsID ����ѯ�� goodsid
     * @return true ���ڣ�false ������
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
            throw new DAOException("��ѯ��ʷ���Ƿ����goosidΪ:"+goodsID+"ʱ����",ex);
        }
        finally
        {
            DB.close(rs);
        }
    }

}
