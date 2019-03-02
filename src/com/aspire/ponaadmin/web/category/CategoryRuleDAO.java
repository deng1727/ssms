
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
     * 记录日志的实例对象
     */
    protected static JLogger LOG = LoggerFactory.getLogger(CategoryRuleDAO.class);

    /**
     * singleton模式的实例
     */
    private static CategoryRuleDAO instance = new CategoryRuleDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryRuleDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryRuleDAO getInstance()
    {

        return instance;
    }

    /**
     * 获取所有需要执行的货架规则
     * 
     * @return
     * @throws DAOException
     */
    public List getAllCategoryRuleName() throws DAOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("获取所有需要执行的自动更新的货架......");
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
            throw new DAOException("提取结果集到String[]中发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug("获取所有需要执行的自动更新的货架:执行结束.");
        }
        return ruleNames;
    }

    /**
     * 更新所有规则上次执行时间
     * 
     * 
     * @param Date lastExecTime 所有货架对应规则的上次执行时间为空
     * @return int 返回结果
     */
    public int updateCheckedRuleLastExecTimeNull(String[] cid)
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("更新规则上次执行时间,开始");
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
            LOG.debug("更新规则上次执行时间,结束");
        }
        // return ErrorCode.SUCC;
        return num;
    }

    /**
     * 获取所有需要执行的货架规则
     * 
     * @return
     * @throws DAOException
     */
    public List getAllAutoUpdateCategory() throws DAOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("获取所有需要执行的货架规则:开始执行......");
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
            throw new DAOException("提取结果集到VO对象中发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug("获取所有需要执行的货架规则:执行结束.");
        }
        return rules;
    }

    /**
     * 更新所有规则上次执行时间
     * 
     * 
     * @param Date lastExecTime 所有货架对应规则的上次执行时间为空
     * @return int 返回结果
     */
    public int updateAllRuleLastExecTimeNull()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("更新规则上次执行时间,开始");
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
            LOG.debug("更新规则上次执行时间,结束");
        }
        return ErrorCode.SUCC;
    }

    /**
     * 更新所有规则上次执行时间
     * 
     * 
     * @param Date lastExecTime 所有货架对应规则的上次执行时间
     * @return int 返回结果
     */
    public int updateAllRuleLastExecTime(Date lastExecTime)
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("更新规则上次执行时间,开始");
        }
        try
        {
            // 框架会忽略date型日期的时间信息。
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
            LOG.debug("更新规则上次执行时间,结束");
        }
        return ErrorCode.SUCC;
    }

    /**
     * 更新规则上次执行时间
     * 
     * @param String cid 要更新上次执行时间的规则对应的货架cid
     * @param Date lastExecTime 货架对应规则的上次执行时间
     * @param long lastexecount 货架对应规则的上次执行时长
     * @return int 返回结果
     */
    public int updateRuleLastExecTime(String cid, long lastexecount,Date lastExecTime)
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("更新规则上次执行时间,开始");
        }
        try
        {
            // 框架会忽略date型日期的时间信息。
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
            LOG.debug("更新规则上次执行时间,结束");
        }
        return ErrorCode.SUCC;
    }

    /**
     * 获取内容的 适配机型。
     * 
     * @param id
     * @return 内容的appcatename字段，如果没有找到则返回null
     */
    public List getDeviceNameListById(String id) throws DAOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("获取内容的适配机型名称,id=" + id);
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
            throw new DAOException("获取内容的appcatename出错。id=" + id, ex);
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
     * 获取内容的 spname信息。
     * 
     * @param id
     * @return 内容的spname字段
     */
    public String getSpNameById(String id) throws DAOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("获取内容的spName,id=" + id);
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
            throw new DAOException("获取内容的spname出错。id=" + id, ex);
        }
        finally
        {
            DB.close(rs);
        }
        return spName;
    }

    /**
     * 更新规则上次执行时间
     * 
     * @param String cid 要更新上次执行时间的规则对应的创业大赛货架cid
     * @param Date lastExecTime 创业大赛货架对应规则的上次执行时间
     * @return int 返回结果
     */
    public int updateRuleLastExecTimeByCarveOut(String cid, Date lastExecTime)
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("更新规则上次执行时间,开始");
        }
        try
        {
            // 框架会忽略date型日期的时间信息。
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
            LOG.debug("更新规则上次执行时间,结束");
        }
        return ErrorCode.SUCC;
    }

    /**
     * 请空规则上次执行时间
     * 
     * @param String cid 要更新上次执行时间的规则对应的创业大赛货架cid
     * @param Date lastExecTime 创业大赛货架对应规则的上次执行时间
     * @return int 返回结果
     */
    public int updateRuleLastExecTimeByCarveOut()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("请空规则上次执行时间,开始");
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
            LOG.debug("请空规则上次执行时间,结束");
        }
        return ErrorCode.SUCC;
    }

    /**
     * 获取所有需要执行的创业大赛货架规则
     * 
     * @return
     * @throws DAOException
     */
    public List getAllAutoUpdateCategoryByCarverOut() throws DAOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("获取所有需要执行的创业大赛货架规则:开始执行......");
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
            throw new DAOException("提取结果集到VO对象中发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug("获取所有需要执行的货架规则:执行结束.");
        }
        return rules;
    }

    /**
     * 用于把精品库中数据同步到指定门户类型货架上
     * 
     * @param categoryId 精品库货架
     * @param type 门户类型
     * @return
     * @throws DAOException
     */
    public int updateSynCategory(String categoryId, String type)
                    throws DAOException
    {
        int num;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("同步精品库" + categoryId + " 中数据到门户类型" + type + " 开始");
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
            LOG.debug("同步精品库" + categoryId + " 中数据到门户类型" + type + " 结束");
        }

        return num;
    }
}
