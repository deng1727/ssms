package com.aspire.dotcard.basecomic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.vo.BlackVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.db.TransactionDB;

/**
 * 动漫黑名单数据库操作
 */
public class BlackDAO
{
    /**
     * 记录日志的实例对象
     */
    protected static JLogger LOG = LoggerFactory.getLogger(BlackDAO.class);

    /**
     * singleton模式的实例
     */
    private static BlackDAO instance = new BlackDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private BlackDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static BlackDAO getInstance()
    {
        return instance;
    }

    /**
     * 用于查询当前黑名单列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryBlackList(PageResult page, BlackVO vo) throws DAOException
    {

        StringBuilder sqlsb = new StringBuilder(200);
        String sql = "select id,content_id,content_name,content_type,content_portal,createdate,lupdate,status from t_cb_black where 1=1 ";

        sqlsb.append(sql);

        List<String> paras = new ArrayList<String>(2);

        if (StringUtils.isNotBlank(vo.getContentId()))
        {
            sqlsb.append(" and content_id like ? ");
            paras.add("%" + SQLUtil.escape(vo.getContentId().trim()) + "%");
        }
        if (StringUtils.isNotBlank(vo.getContentName()))
        {
            sqlsb.append(" and content_name like ? ");
            paras.add("%" + SQLUtil.escape(vo.getContentName().trim()) + "%");
        }
        sqlsb.append(" order by content_name desc");

        page.excute(sqlsb.toString(), paras.toArray(), new PageVOInterface()
        {
            public void CopyValFromResultSet(Object vo, ResultSet rs) throws SQLException
            {
                BlackVO blackVO = (BlackVO)vo;
                blackVO.setId(rs.getString("id"));
                blackVO.setContentId(rs.getString("content_id"));
                blackVO.setContentName(rs.getString("content_name"));
                blackVO.setContentType(rs.getInt("content_type"));
                blackVO.setContentPortal(rs.getInt("content_portal"));
                blackVO.setCreateDate(rs.getTimestamp("createdate"));
            }
            public Object createObject()
            {
                return new BlackVO();
            }
        });
    }

    /**
     * 查询动漫内容信息列表
     * @param page
     * @param vo
     * @throws DAOException
     */
    @SuppressWarnings("unchecked")
    public void queryContentList(PageResult page, BlackVO vo) throws DAOException
    {
        String sql = null;
        sql = "select id,name,type,portal from t_cb_content c where to_date(c.expiretime,'yyyyMMddHH24miss')>=trunc(sysdate) ";
        StringBuffer sqlBuffer = new StringBuffer(sql);
        List<String> paras = new ArrayList<String>();
        if (StringUtils.isNotBlank(vo.getContentId()))
        {
            sqlBuffer.append(" and c.id like ? ");
            paras.add("%" + SQLUtil.escape(vo.getContentId()) + "%");
        }
        if (StringUtils.isNotBlank(vo.getContentName()))
        {
            sqlBuffer.append(" and c.name like ? ");
            paras.add("%" + SQLUtil.escape(vo.getContentName()) + "%");
        }
        sqlBuffer.append(" and not exists (select 1 from t_cb_black t where t.content_id=c.id) ");
        sqlBuffer.append(" order by c.name desc");

        page.excute(sqlBuffer.toString(), paras.toArray(), new PageVOInterface()
        {
            public void CopyValFromResultSet(Object vo, ResultSet rs) throws SQLException
            {

                BlackVO blackVO = (BlackVO)vo;
                blackVO.setId(rs.getString("id"));
                blackVO.setContentId(rs.getString("id"));
                blackVO.setContentName(rs.getString("name"));
                blackVO.setContentType(rs.getInt("type"));
                blackVO.setContentPortal(rs.getInt("portal"));
            }
            public Object createObject()
            {
                return new BlackVO();
            }
        });

    }

    /**
     * 移除黑名单
     * 
     * @param id
     * @throws DAOException
     */
    public void removeBlack(String[] id) throws DAOException
    {
        if (id == null || (id != null && id.length <= 0))
        {
            LOG.warn("移除动漫黑名单id[]参数为空!");
            return;
        }
        String sql = "com.aspire.ponaadmin.web.comic.dao.BlackDAO.remove";
        Object[][] object = new Object[id.length][1];

        for (int i = 0; i < id.length; i++)
        {
            object[i][0] = id[i];
        }

        try
        {
            DB.getInstance().executeBatchBySQLCode(sql, object);
        }
        catch (DAOException e)
        {
            LOG.error("移除指定动漫黑名单时发生异常!", e);
            throw new DAOException("移除指定动漫黑名单时发生异常!", e);
        }
    }

    /**
     * 检查黑名单表是否存在内容ID
     * 
     * @param contentId
     * @return
     * @throws DAOException
     * @throws Exception
     */
    public boolean isExistBlack(String contentId) throws DAOException
    {
        if (StringUtils.isEmpty(contentId))
        {
            LOG.warn("检查动漫黑名单表contentid参数为空!");
            throw new DAOException("检查动漫黑名单表contentid参数为空!");
        }

        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.BlackDAO.isExistBlack";
        String[] paras = new String[] { contentId };
        ResultSet rs = null;
        boolean result = false;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs != null)
            {
                result = rs.next();
            }
        }
        catch (DAOException e)
        {
            LOG.warn("检查动漫黑名单表是否存在内容contentid:" + contentId + " 发生异常:", e);
            throw new DAOException("检查动漫黑名单表是否存在内容contentid:" + contentId + " 发生异常:", e);
        }
        catch (SQLException e)
        {
            LOG.warn("检查动漫黑名单表是否存在内容contentid:" + contentId + " 发生异常:", e);
            throw new DAOException("检查动漫黑名单表是否存在内容contentid:" + contentId + " 发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return result;
    }

    /**
     * 检查内容表t_cb_content是否存在conntentId
     * 
     * @param contentId
     * @return
     * @throws DAOException
     */
    public boolean isExistContent(String contentId) throws DAOException
    {
        if (StringUtils.isEmpty(contentId))
        {
            LOG.warn("检查动漫内容表contentid参数为空!");
            throw new DAOException("检查动漫内容表contentid参数为空!");
        }

        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.verifyContentId";
        String[] paras = new String[] { contentId };
        ResultSet rs = null;
        boolean result = false;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs != null)
            {
                result = rs.next();
            }
        }
        catch (DAOException e)
        {
            LOG.error("检查动漫内容表是否存在内容contentid:" + contentId + " 发生异常:", e);
            throw new DAOException("检查内容表是否存在内容contentid:" + contentId + " 发生异常:", e);
        }
        catch (SQLException e)
        {
            LOG.error("检查动漫内容表是否存在内容contentid:" + contentId + " 发生异常:", e);
            throw new DAOException("检查内容表是否存在内容contentid:" + contentId + " 发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }
        return result;
    }

    /**
     * 新增黑名单
     * 
     * @param contentId
     * @throws Exception
     */
    public void addBlack(String contentId) throws Exception
    {
        if (StringUtils.isEmpty(contentId))
        {
            LOG.warn("新增动漫黑名单contentid参数为空!");
            throw new DAOException("新增动漫黑名单contentid参数为空!");
        }

        this.addBlack(new String[] { contentId });
    }

    /**
     * 新增多条黑名单
     * 
     * @param contentId
     * @throws DAOException
     */
    public void addBlack(String[] contentId) throws DAOException
    {
        if (contentId == null || (contentId != null && contentId.length <= 0))
        {
            LOG.warn("新增动漫黑名单contentId[]为空!");
            return;
        }

        String sql = "com.aspire.ponaadmin.web.comic.dao.BlackDAO.addBlack";
        String sqlCode[] = new String[contentId.length];
        Object[][] object = new Object[contentId.length][1];

        for (int i = 0; i < contentId.length; i++)
        {
            sqlCode[i] = sql;
            object[i][0] = contentId[i];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            LOG.error("新增动漫多条黑名单时发生异常!", e);
            throw new DAOException("新增动漫多条黑名单时发生异常!", e);
        }
    }

    /**
     * 全量导入，先移除，后导入
     * 
     * @param list
     * @throws DAOException
     */
    public void allAddBlack(List<String> list) throws DAOException
    {
        if (list == null || (list != null && list.size() <= 0))
        {
            LOG.warn("全量导入动漫黑名单为空!");
            return;
        }

        String sqlCode1 = "com.aspire.ponaadmin.web.comic.dao.BlackDAO.truncateBlack";
        String sqlCode2 = "com.aspire.ponaadmin.web.comic.dao.BlackDAO.addBlack";
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance();

            DB.getInstance().executeBySQLCode(sqlCode1, null);

            for (String id : list)
            {
                tdb.executeBySQLCode(sqlCode2, new Object[] { id });
            }

            tdb.commit();
        }
        catch (DAOException e)
        {
            tdb.rollback();
            LOG.error("动漫黑名单全量导入出错！", e);
            throw new DAOException("动漫黑名单全量导入出错:", e);
        }
        finally
        {
            if (tdb != null)
            {
                tdb.close();
            }
        }

    }

    public void delReference() throws DAOException
    {
        String sqlCode1 = "com.aspire.ponaadmin.web.comic.dao.BlackDAO.delReference";
        String sqlCode2 = "com.aspire.ponaadmin.web.comic.dao.BlackDAO.delCbContent";

        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance();

            DB.getInstance().executeBySQLCode(sqlCode1, null);
            DB.getInstance().executeBySQLCode(sqlCode2, null);
            tdb.commit();
        }
        catch (DAOException e)
        {
            tdb.rollback();
            LOG.error("根据动漫黑名单列表删除商品表数据发生异常！", e);
            throw new DAOException("根据动漫黑名单列表删除商品表数据发生异常:", e);
        }
        finally
        {
            if (tdb != null)
            {
                tdb.close();
            }
        }

    }
    
    
}
