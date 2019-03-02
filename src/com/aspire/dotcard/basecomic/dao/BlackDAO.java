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
 * �������������ݿ����
 */
public class BlackDAO
{
    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger LOG = LoggerFactory.getLogger(BlackDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static BlackDAO instance = new BlackDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private BlackDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static BlackDAO getInstance()
    {
        return instance;
    }

    /**
     * ���ڲ�ѯ��ǰ�������б�
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
     * ��ѯ����������Ϣ�б�
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
     * �Ƴ�������
     * 
     * @param id
     * @throws DAOException
     */
    public void removeBlack(String[] id) throws DAOException
    {
        if (id == null || (id != null && id.length <= 0))
        {
            LOG.warn("�Ƴ�����������id[]����Ϊ��!");
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
            LOG.error("�Ƴ�ָ������������ʱ�����쳣!", e);
            throw new DAOException("�Ƴ�ָ������������ʱ�����쳣!", e);
        }
    }

    /**
     * �����������Ƿ��������ID
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
            LOG.warn("��鶯����������contentid����Ϊ��!");
            throw new DAOException("��鶯����������contentid����Ϊ��!");
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
            LOG.warn("��鶯�����������Ƿ��������contentid:" + contentId + " �����쳣:", e);
            throw new DAOException("��鶯�����������Ƿ��������contentid:" + contentId + " �����쳣:", e);
        }
        catch (SQLException e)
        {
            LOG.warn("��鶯�����������Ƿ��������contentid:" + contentId + " �����쳣:", e);
            throw new DAOException("��鶯�����������Ƿ��������contentid:" + contentId + " �����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return result;
    }

    /**
     * ������ݱ�t_cb_content�Ƿ����conntentId
     * 
     * @param contentId
     * @return
     * @throws DAOException
     */
    public boolean isExistContent(String contentId) throws DAOException
    {
        if (StringUtils.isEmpty(contentId))
        {
            LOG.warn("��鶯�����ݱ�contentid����Ϊ��!");
            throw new DAOException("��鶯�����ݱ�contentid����Ϊ��!");
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
            LOG.error("��鶯�����ݱ��Ƿ��������contentid:" + contentId + " �����쳣:", e);
            throw new DAOException("������ݱ��Ƿ��������contentid:" + contentId + " �����쳣:", e);
        }
        catch (SQLException e)
        {
            LOG.error("��鶯�����ݱ��Ƿ��������contentid:" + contentId + " �����쳣:", e);
            throw new DAOException("������ݱ��Ƿ��������contentid:" + contentId + " �����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }
        return result;
    }

    /**
     * ����������
     * 
     * @param contentId
     * @throws Exception
     */
    public void addBlack(String contentId) throws Exception
    {
        if (StringUtils.isEmpty(contentId))
        {
            LOG.warn("��������������contentid����Ϊ��!");
            throw new DAOException("��������������contentid����Ϊ��!");
        }

        this.addBlack(new String[] { contentId });
    }

    /**
     * ��������������
     * 
     * @param contentId
     * @throws DAOException
     */
    public void addBlack(String[] contentId) throws DAOException
    {
        if (contentId == null || (contentId != null && contentId.length <= 0))
        {
            LOG.warn("��������������contentId[]Ϊ��!");
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
            LOG.error("������������������ʱ�����쳣!", e);
            throw new DAOException("������������������ʱ�����쳣!", e);
        }
    }

    /**
     * ȫ�����룬���Ƴ�������
     * 
     * @param list
     * @throws DAOException
     */
    public void allAddBlack(List<String> list) throws DAOException
    {
        if (list == null || (list != null && list.size() <= 0))
        {
            LOG.warn("ȫ�����붯��������Ϊ��!");
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
            LOG.error("����������ȫ���������", e);
            throw new DAOException("����������ȫ���������:", e);
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
            LOG.error("���ݶ����������б�ɾ����Ʒ�����ݷ����쳣��", e);
            throw new DAOException("���ݶ����������б�ɾ����Ʒ�����ݷ����쳣:", e);
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
