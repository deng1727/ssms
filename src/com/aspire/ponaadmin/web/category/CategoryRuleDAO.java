
package com.aspire.ponaadmin.web.category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.constant.ErrorCode;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class CategoryRuleDAO
{

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger LOG = LoggerFactory.getLogger(CategoryRuleDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static CategoryRuleDAO instance = new CategoryRuleDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private CategoryRuleDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CategoryRuleDAO getInstance()
    {

        return instance;
    }

    /**
     * ��ȡ������Ҫִ�еĻ��ܹ���
     * 
     * @return
     * @throws DAOException
     */
    public List getAllCategoryRuleName() throws DAOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ȡ������Ҫִ�е��Զ����µĻ���......");
        }
        ResultSet rs = null;

        List ruleNames = new ArrayList();
        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode("category.rule.categoryRuleDAO.getAllCategoryRuleName().SELECT",
                                   null);

            while (rs.next())
            {

                String cateruleName[] = { "", "", "", "" };
                cateruleName[0] = ( String ) rs.getString("id");
                cateruleName[1] = ( String ) rs.getString("categoryid");
                cateruleName[2] = ( String ) rs.getString("name");
                cateruleName[3] = ( String ) rs.getString("fullname");
                ruleNames.add(cateruleName);
            }

        }
        catch (SQLException e)
        {
            throw new DAOException("��ȡ�������String[]�з����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ȡ������Ҫִ�е��Զ����µĻ���:ִ�н���.");
        }
        return ruleNames;
    }

    /**
     * �������й����ϴ�ִ��ʱ��
     * 
     * 
     * @param Date lastExecTime ���л��ܶ�Ӧ������ϴ�ִ��ʱ��Ϊ��
     * @return int ���ؽ��
     */
    public int updateCheckedRuleLastExecTimeNull(String[] cid)
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("���¹����ϴ�ִ��ʱ��,��ʼ");
        }
        int num = 0;
        try
        {
            Object paras[] = cid;
            for (int i = 0; i < paras.length; i++)
            {
                Object para[] = { paras[i] };
                num = DB.getInstance()
                        .executeBySQLCode("category.rule.categoryRuleDAO.updateCheckedRuleLastExecTimeNull().UPDATE",
                                          para);
                num++;
            }

            if (num <= 0)
            {
                return ErrorCode.FAIL;
            }

        }
        catch (DAOException ex)
        {
            LOG.error(ex);
            return ErrorCode.FAIL;
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug("���¹����ϴ�ִ��ʱ��,����");
        }
        // return ErrorCode.SUCC;
        return num;
    }

    /**
     * ��ȡ������Ҫִ�еĻ��ܹ���
     * 
     * @return
     * @throws DAOException
     */
    public List getAllAutoUpdateCategory() throws DAOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ȡ������Ҫִ�еĻ��ܹ���:��ʼִ��......");
        }
        ResultSet rs = null;
        CategoryRuleVO vo = null;
        List rules = new ArrayList();
        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode("category.rule.categoryRuleDAO.select().GETALLAUTOUPDATECATEGORY",
                                   null);

            while (rs.next())
            {
                vo = new CategoryRuleVO();
                vo.setCid(rs.getString("cid"));
                vo.setRuleId(rs.getInt("ruleId"));
                vo.setLastExcuteTime(rs.getDate("lastExcuteTime"));
                vo.setEffectiveTime(rs.getDate("effectiveTime"));
                rules.add(vo);
            }

        }
        catch (SQLException e)
        {
            throw new DAOException("��ȡ�������VO�����з����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ȡ������Ҫִ�еĻ��ܹ���:ִ�н���.");
        }
        return rules;
    }

    /**
     * �������й����ϴ�ִ��ʱ��
     * 
     * 
     * @param Date lastExecTime ���л��ܶ�Ӧ������ϴ�ִ��ʱ��Ϊ��
     * @return int ���ؽ��
     */
    public int updateAllRuleLastExecTimeNull()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("���¹����ϴ�ִ��ʱ��,��ʼ");
        }
        try
        {

            int num = DB.getInstance()
                        .executeBySQLCode("category.rule.categoryRuleDAO.updateAllRuleLastExecTimeNull().UPDATE",
                                          null);
            if (num <= 0)
            {
                return ErrorCode.FAIL;
            }

        }
        catch (DAOException ex)
        {
            LOG.error(ex);
            return ErrorCode.FAIL;
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug("���¹����ϴ�ִ��ʱ��,����");
        }
        return ErrorCode.SUCC;
    }

    /**
     * �������й����ϴ�ִ��ʱ��
     * 
     * 
     * @param Date lastExecTime ���л��ܶ�Ӧ������ϴ�ִ��ʱ��
     * @return int ���ؽ��
     */
    public int updateAllRuleLastExecTime(Date lastExecTime)
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("���¹����ϴ�ִ��ʱ��,��ʼ");
        }
        try
        {
            // ��ܻ����date�����ڵ�ʱ����Ϣ��
            String lastTime = PublicUtil.getDateString(lastExecTime,
                                                       "yyyy-MM-dd HH:mm:ss");

            Object[] paras = { lastTime };
            int num = DB.getInstance()
                        .executeBySQLCode("category.rule.categoryRuleDAO.update().UPDATEALLRULELASTEXECTIME",
                                          paras);
            if (num <= 0)
            {
                return ErrorCode.FAIL;
            }

        }
        catch (DAOException ex)
        {
            LOG.error(ex);
            return ErrorCode.FAIL;
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug("���¹����ϴ�ִ��ʱ��,����");
        }
        return ErrorCode.SUCC;
    }

    /**
     * ���¹����ϴ�ִ��ʱ��
     * 
     * @param String cid Ҫ�����ϴ�ִ��ʱ��Ĺ����Ӧ�Ļ���cid
     * @param Date lastExecTime ���ܶ�Ӧ������ϴ�ִ��ʱ��
     * @param long lastexecount ���ܶ�Ӧ������ϴ�ִ��ʱ��
     * @return int ���ؽ��
     */
    public int updateRuleLastExecTime(String cid, long lastexecount,Date lastExecTime)
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("���¹����ϴ�ִ��ʱ��,��ʼ");
        }
        try
        {
            // ��ܻ����date�����ڵ�ʱ����Ϣ��
            String lastTime = PublicUtil.getDateString(lastExecTime,
                                                       "yyyy-MM-dd HH:mm:ss");

            Object[] paras = { lastTime, new Long(lastexecount),cid };
            int num = DB.getInstance()
                        .executeBySQLCode("category.rule.categoryRuleDAO.update().UPDATERULELASTEXECTIME",
                                          paras);
            if (num <= 0)
            {
                return ErrorCode.FAIL;
            }

        }
        catch (DAOException ex)
        {
            LOG.error(ex);
            return ErrorCode.FAIL;
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug("���¹����ϴ�ִ��ʱ��,����");
        }
        return ErrorCode.SUCC;
    }

    /**
     * ��ȡ���ݵ� ������͡�
     * 
     * @param id
     * @return ���ݵ�appcatename�ֶΣ����û���ҵ��򷵻�null
     */
    public List getDeviceNameListById(String id) throws DAOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ȡ���ݵ������������,id=" + id);
        }
        StringBuffer bf = new StringBuffer();
        ResultSet rs = null;
        try
        {

            Object[] paras = { id };
            String sqlCode = "category.rule.categoryRuleDAO.select().getDeviceNameListById";
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

            while (rs.next())
            {
                bf.append(PublicUtil.trim(rs.getString(1)));
                bf.append(PublicUtil.trim(rs.getString(2)));
                bf.append(PublicUtil.trim(rs.getString(3)));
                bf.append(PublicUtil.trim(rs.getString(4)));
                bf.append(PublicUtil.trim(rs.getString(5)));
                bf.append(PublicUtil.trim(rs.getString(6)));
                bf.append(PublicUtil.trim(rs.getString(7)));
                bf.append(PublicUtil.trim(rs.getString(8)));
                bf.append(PublicUtil.trim(rs.getString(9)));
                bf.append(PublicUtil.trim(rs.getString(10)));
                bf.append(PublicUtil.trim(rs.getString(11)));
                bf.append(PublicUtil.trim(rs.getString(12)));
                bf.append(PublicUtil.trim(rs.getString(13)));
                bf.append(PublicUtil.trim(rs.getString(14)));
                bf.append(PublicUtil.trim(rs.getString(15)));
                bf.append(PublicUtil.trim(rs.getString(16)));
                bf.append(PublicUtil.trim(rs.getString(17)));
                bf.append(PublicUtil.trim(rs.getString(18)));
                bf.append(PublicUtil.trim(rs.getString(19)));
                bf.append(PublicUtil.trim(rs.getString(20)));
            }
        }
        catch (Exception ex)
        {
            throw new DAOException("��ȡ���ݵ�appcatename����id=" + id, ex);
        }
        finally
        {
            DB.close(rs);

        }

        return getDeviceNameList(bf.toString());
    }

    public List getDeviceNameList(String deviceName)
    {
        List deviceNameList = new ArrayList();
        String deviceNameItem = "";

        String[] vDeviceName = deviceName.split(",");
        for (int i = 0; i < vDeviceName.length; i++)
        {
            deviceNameItem = vDeviceName[i];
            deviceNameItem = deviceNameItem.replaceAll(" ", "");
            if (deviceNameItem.length() == 0)
                continue;
            deviceNameItem = deviceNameItem.substring(deviceNameItem.indexOf("{") + 1,
                                                      deviceNameItem.lastIndexOf("}"));
            deviceNameList.add(deviceNameItem);
        }
        return deviceNameList;
    }

    /**
     * ��ȡ���ݵ� spname��Ϣ��
     * 
     * @param id
     * @return ���ݵ�spname�ֶ�
     */
    public String getSpNameById(String id) throws DAOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ȡ���ݵ�spName,id=" + id);
        }
        String spName = "";
        ResultSet rs = null;
        try
        {
            Object[] paras = { id };
            String sqlCode = "category.rule.categoryRuleDAO.select().getSpNameById.SELECT";
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

            while (rs.next())
            {
                spName = PublicUtil.trim(rs.getString(1));
            }
        }
        catch (Exception ex)
        {
            throw new DAOException("��ȡ���ݵ�spname����id=" + id, ex);
        }
        finally
        {
            DB.close(rs);
        }
        return spName;
    }

    /**
     * ���¹����ϴ�ִ��ʱ��
     * 
     * @param String cid Ҫ�����ϴ�ִ��ʱ��Ĺ����Ӧ�Ĵ�ҵ��������cid
     * @param Date lastExecTime ��ҵ�������ܶ�Ӧ������ϴ�ִ��ʱ��
     * @return int ���ؽ��
     */
    public int updateRuleLastExecTimeByCarveOut(String cid, Date lastExecTime)
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("���¹����ϴ�ִ��ʱ��,��ʼ");
        }
        try
        {
            // ��ܻ����date�����ڵ�ʱ����Ϣ��
            String lastTime = PublicUtil.getDateString(lastExecTime,
                                                       "yyyy-MM-dd HH:mm:ss");

            Object[] paras = { lastTime, cid };

            // update t_category_carveout_rule set
            // lastexcutetime=to_date(?,'yyyy-mm-dd hh24:mi:ss') where cid=?
            int num = DB.getInstance()
                        .executeBySQLCode("category.rule.categoryRuleDAO.updateRuleLastExecTimeByCarveOut().UPDATERULELASTEXECTIME",
                                          paras);
            if (num <= 0)
            {
                return ErrorCode.FAIL;
            }

        }
        catch (DAOException ex)
        {
            LOG.error(ex);
            return ErrorCode.FAIL;
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug("���¹����ϴ�ִ��ʱ��,����");
        }
        return ErrorCode.SUCC;
    }

    /**
     * ��չ����ϴ�ִ��ʱ��
     * 
     * @param String cid Ҫ�����ϴ�ִ��ʱ��Ĺ����Ӧ�Ĵ�ҵ��������cid
     * @param Date lastExecTime ��ҵ�������ܶ�Ӧ������ϴ�ִ��ʱ��
     * @return int ���ؽ��
     */
    public int updateRuleLastExecTimeByCarveOut()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("��չ����ϴ�ִ��ʱ��,��ʼ");
        }
        try
        {
            // update t_category_carveout_rule set lastexcutetime=''
            int num = DB.getInstance()
                        .executeBySQLCode("category.rule.categoryRuleDAO.updateExecTimeByCarveOut().UPDATERULELASTEXECTIME",
                                          null);
            if (num <= 0)
            {
                return ErrorCode.FAIL;
            }
        }
        catch (DAOException ex)
        {
            LOG.error(ex);
            return ErrorCode.FAIL;
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��չ����ϴ�ִ��ʱ��,����");
        }
        return ErrorCode.SUCC;
    }

    /**
     * ��ȡ������Ҫִ�еĴ�ҵ�������ܹ���
     * 
     * @return
     * @throws DAOException
     */
    public List getAllAutoUpdateCategoryByCarverOut() throws DAOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ȡ������Ҫִ�еĴ�ҵ�������ܹ���:��ʼִ��......");
        }
        ResultSet rs = null;
        CategoryRuleByCarveOutVO vo = null;
        List rules = new ArrayList();
        try
        {
            // select * from t_category_carveout_rule
            rs = DB.getInstance()
                   .queryBySQLCode("category.rule.categoryRuleDAO.selectByCarveOut().GETALLAUTOUPDATECATEGORY",
                                   null);

            while (rs.next())
            {
                vo = new CategoryRuleByCarveOutVO();
                vo.setId(rs.getString("id"));
                vo.setCid(rs.getString("cid"));
                vo.setRuleType(rs.getInt("ruleType"));
                vo.setName(rs.getString("name"));
                vo.setWSql(rs.getString("wsql"));
                vo.setOSql(rs.getString("osql"));
                vo.setCount(rs.getInt("count"));
                vo.setSortId(rs.getInt("sortId"));
                vo.setLastExcuteTime(rs.getDate("lastExcuteTime"));
                vo.setEffectiveTime(rs.getDate("effectiveTime"));
                vo.setExcuteInterval(rs.getInt("excuteInterval"));
                //vo.setIntervalCount(rs.getInt("excuteInterval"));
                vo.setExcuteTime(rs.getInt("EXCUTETIME"));
                vo.setIntervalType(rs.getInt("intervalType"));
                vo.setBaseSQLId(rs.getString("SQLBASEID"));
                rules.add(vo);
            }

        }
        catch (SQLException e)
        {
            throw new DAOException("��ȡ�������VO�����з����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ȡ������Ҫִ�еĻ��ܹ���:ִ�н���.");
        }
        return rules;
    }

    /**
     * ���ڰѾ�Ʒ��������ͬ����ָ���Ż����ͻ�����
     * 
     * @param categoryId ��Ʒ�����
     * @param type �Ż�����
     * @return
     * @throws DAOException
     */
    public int updateSynCategory(String categoryId, String type)
                    throws DAOException
    {
        int num;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("ͬ����Ʒ��" + categoryId + " �����ݵ��Ż�����" + type + " ��ʼ");
        }

        // update t_r_reference r set sortid = (select decode(f.sortid, null,
        // r.sortid, f.sortid) from t_r_reference f where f.categoryid = ? and
        // f.refnodeid = r.refnodeid) where r.categoryid in (select c.categoryid
        // from t_sync_tactic t, t_r_category c where c.id = t.categoryid and
        // c.relation = ?) and r.refnodeid in (select ff.refnodeid from
        // t_r_reference ff where ff.categoryid = ?)
        num = DB.getInstance()
                .executeBySQLCode("category.rule.categoryRuleDAO.updateSynCategory()",
                                  new Object[] { categoryId, type, categoryId });
        if (LOG.isDebugEnabled())
        {
            LOG.debug("ͬ����Ʒ��" + categoryId + " �����ݵ��Ż�����" + type + " ����");
        }

        return num;
    }
}
