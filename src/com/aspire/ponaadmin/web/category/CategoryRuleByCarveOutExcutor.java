
package com.aspire.ponaadmin.web.category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * 规则执行器。根据创业大赛货架的执行规则，执行单个创业大赛货架的更新任务。
 * 
 * @author zhangwei
 * 
 */
public class CategoryRuleByCarveOutExcutor
{

    /**
     * 该创业大赛货架的信息
     */
    private CategoryRuleByCarveOutVO vo;

    /**
     * 执行该方法的task类
     */
    private CategoryRuleByCarveOutTask task;

    /**
     * 日志对象
     */
    private static JLogger LOG = LoggerFactory.getLogger(CategoryRuleByCarveOutExcutor.class);

    /**
     * 缓存黑名单应用信息
     */
    public static Map BLACKAPPCACHE = Collections.synchronizedMap(new HashMap());
    /**
     * 缓存自动更新基础SQL语句 ADD BY dongke 20110503
     */
    public static Map BASESQLCACHE = Collections.synchronizedMap(new HashMap());


    /**
     * 构造方法
     * 
     * @param vo
     */
    public CategoryRuleByCarveOutExcutor(CategoryRuleByCarveOutVO vo)
    {
        this.vo = vo;
    }

    /**
     * 多线程，全量规则执行任务
     * 
     * @param vo
     * @param task
     */
    public CategoryRuleByCarveOutExcutor(CategoryRuleByCarveOutVO vo,
                                         CategoryRuleByCarveOutTask task)
    {
        this.vo = vo;
        this.task = task;
    }

    public int excucte() throws BOException
    {
        if (vo == null)
        {
            throw new BOException("创业大赛货架更新规则为null");
        }
        Date date = vo.getEffectiveTime();
        if (!isEffective(date))
        {
            LOG.info("创业大赛货架id为" + vo.getCid() + "的货架还没有生效。不需要执行。生效时间:"
                     + PublicUtil.getDateString(date));
            return CategoryRuleByCarveOutTask.INEFFECTIVE;
        }
        LOG.info("开始对创业大赛货架id为：" + vo.getCid() + "的货架进行更新");
        if (LOG.isDebugEnabled())
        {
            LOG.debug("该创业大赛货架的规则为：" + vo.getName());
        }

        // 根据规则中的商品是否需要自动生成和商品是否需要重新计算排序，来判断本次是否需要处理该货架。
        // 如果两个值都是“否”，本次不需要处理该货架。
        Category cate = ( Category ) Repository.getInstance()
                                               .getNode(vo.getCid(),
                                                        RepositoryConstants.TYPE_CATEGORY);
        if (cate == null)
        {
            LOG.info("找不到要更新的创业大赛货架，cateId=" + vo.getCid());
            throw new BOException("找不到要更新的创业大赛货架");
        }
        if (cate.getDelFlag() == 1)//
        {
            LOG.info("该创业大赛货架已经下架，cateId=" + vo.getCid());
            throw new BOException("该创业大赛货架已经下架,货架名称：" + cate.getName());
        }

        // 根据上次执行时间看是否需要本次执行。
        int sortId = 1000;// 默认从1000开始排序。

        if (IsNeedExecution())
        {
            // 先获取该货架下商品
            LOG.info("创业大赛货架更新开始。。。。");

            // 初始化黑名单应用到缓存
            if (BLACKAPPCACHE == null || BLACKAPPCACHE.size() <= 0)
            {
                this.getBlackContentList();
            }
//          初始化黑名单应用到缓存
            if (BASESQLCACHE == null || BASESQLCACHE.size() <= 0)
            {
                this.getBaseSQL();
            }
            // 需要商品重新生成。
            if (vo.getRuleType() == CategoryConstants.RULETYPE_REFRESH)
            {
                // 存放精品库和产品库的商品。
                List productList = new ArrayList();

                // 有获取产品库的条件则执行。
                productList.addAll(getContentIDList(vo));

                // 打印日志
                LOG.info("去重数据");

                // 去重数据
                PublicUtil.removeDuplicateWithOrder(productList);

                // 打印日志
                LOG.info("AP应用黑名单");

                // 去掉AP应用黑名单中的应用
                this.removeConInBlackList(productList);

                // 需要先下架所有商品
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("开始下架本创业大赛货架的所有商品。。。");
                }

                // 打印日志
                LOG.info("下架商品");

                CategoryTools.clearCateGoods(vo.getCid(), false);

                // 上架商品 上架产品库的。
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("开始上架产品库的商品！");
                }

                for (int i = 0; i < productList.size(); i++)
                {
                    String contId = ( String ) productList.get(i);

                    CategoryTools.addGood(cate,
                                          contId,
                                          sortId--,
                                          RepositoryConstants.VARIATION_NEW,
                                          true,
                                          null);
                }

                // 打印日志
                LOG.info("上架完成");
            }
            // 刷新和重新生成商品时互斥操作。
            else if (vo.getRuleType() == CategoryConstants.RULETYPE_REORDER)
            {
                // 获取当前货架下商品，并排好序。
                LOG.info("货架重新排序开始。。。。");

                // 商品排序的规则的条件只有排序一个条件即可。
                if ("".equals(vo.getWSql()) && "".equals(vo.getOSql()))
                {
                    LOG.error("该排序规则没有定义排序条件，本货架不进行排序。");
                    throw new BOException("该排序规则没有排序条件异常");
                }

                // 得到更新排序的排序列表值
                List list = getSortedGoods(cate, vo);

                // 黑名单应用置底
                this.changeToLastBlackApp(list);

                List goodList = new ArrayList();

                // 设置排序号
                for (int i = 0; i < list.size(); i++)
                {
                    ReferenceNode node = new ReferenceNode();
                    node.setId(( String ) list.get(i));
                    node.setSortID(sortId--);
                    goodList.add(node);
                }
                // 更新到数据库中。
                if (goodList.size() > 0)// 只有含有商品才需要更新排序值。
                {
                    updateSortId(goodList);
                }
            }

            // 更新货架的最后一次商品自动处理执行时间。
            CategoryRuleDAO.getInstance()
                           .updateRuleLastExecTimeByCarveOut(vo.getCid(),
                                                             new Date());
            return CategoryRuleByCarveOutTask.UPDATESUCESS;
        }
        else
        {
            LOG.info("本次不需要执行。创业大赛货架内码id=" + vo.getCid());
            return CategoryRuleByCarveOutTask.EXECUTED;
        }
    }

    /**
     * 获取AP刷榜黑名单应用
     * 
     * @return
     * @throws BOException
     */
    private HashMap getBlackContentList() throws BOException
    {
        HashMap blackContentMap = new HashMap();
        String sqlCode = "category.rule.CategoryRuleByCarveOutExcutor.getBlackContentList().SELECT";
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            if (LOG.isDebugEnabled())
            {
                LOG.debug("从数据库获取应用黑名单列表");
            }
            while (rs.next())
            {
                BLACKAPPCACHE.put(rs.getString("cid"), "1");
            }
        }
        catch (DAOException e)
        {
            e.printStackTrace();
            LOG.error("从数据库获取应用黑名单列表,DAO操作错误" + e);
            throw new BOException("DAO操作错误", e);
        }
        catch (SQLException e1)
        {
            e1.printStackTrace();
            LOG.error("从数据库获取应用黑名单列表,DAO操作错误" + e1);
            throw new BOException("SQL操作错误", e1);
        }
        finally
        {
            DB.close(rs);
        }
        return blackContentMap;
    }
    /**
     * 获取AP刷榜黑名单应用
     * 
     * @return
     * @throws BOException
     */
    private HashMap getBaseSQL() throws BOException
    {
        HashMap baseSQLMap = new HashMap();
        String sqlCode = "category.rule.CategoryRuleByCarveOutExcutor.getBaseSQL().SELECT";
        ResultSet rs = null;
        DB db = DB.getInstance();
        try
        {
            rs = db.queryBySQLCode(sqlCode, null);

            if (LOG.isDebugEnabled())
            {
                LOG.debug("从数据库获取创业大赛基础SQL语句");
            }
            while (rs.next())
            {
            	BASESQLCACHE.put(rs.getString("id"), rs.getString("BASESQL"));
            	baseSQLMap.put(rs.getString("id"), rs.getString("BASESQL"));
            }
        }
        catch (DAOException e)
        {
            e.printStackTrace();
            LOG.error("从数据库获取创业大赛自动更新基础语句列表,DAO操作错误", e);
            throw new BOException("DAO操作错误", e);
        }
        catch (SQLException e1)
        {
            e1.printStackTrace();
            LOG.error("从数据库获取创业大赛自动更新基础语句列表,DAO操作错误", e1);
            throw new BOException("SQL操作错误", e1);
        }
        finally
        {
        	db.close(rs);
        }
        return baseSQLMap;
    }
    /**
     * 去掉存在黑名单中应用
     * 
     * @param conId 内容内码
     * @return true 不是黑名单，可以上架，false 是黑名单，不可以上架
     * @throws BOException
     */
    private synchronized void removeConInBlackList(List contentlist)
                    throws BOException
    {
        Map hm = null;
        if (BLACKAPPCACHE != null)
        {
            hm = BLACKAPPCACHE;
        }
        else
        {
            this.getBlackContentList();
            hm = BLACKAPPCACHE;
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug("黑名单应用不上架");
        }
        for (int i = 0; i < contentlist.size(); i++)
        {
            String conId = ( String ) contentlist.get(i);
            String values = ( String ) hm.get(conId);
            // 防止连续的漏掉了
            if (values != null && values.equals("1"))
            {
                LOG.error("conId=" + conId + ",在AP应用黑名单中，不予上架");
                contentlist.remove(i);
                i--;
            }

        }
    }

    /**
     * 
     * @param contentlist
     * @throws BOException
     */
    private void changeToLastBlackApp(List contentlist) throws BOException
    {
        List blackapp = new ArrayList();

        if (contentlist != null && contentlist.size() > 0)
        {
            // 获取黑名单应用
            Map hm = null;
            if (BLACKAPPCACHE != null)
            {
                hm = BLACKAPPCACHE;
            }
            else
            {
                this.getBlackContentList();
                hm = BLACKAPPCACHE;
            }

            if (LOG.isDebugEnabled())
            {
                LOG.debug("黑名单应用置底");
            }

            for (int i = 0; i < contentlist.size(); i++)
            {
                String conId = ( String ) contentlist.get(i);
                ReferenceNode ref = ( ReferenceNode ) Repository.getInstance()
                                                                .getNode(conId,
                                                                         RepositoryConstants.TYPE_REFERENCE);
                String values = ( String ) hm.get(ref.getRefNodeID());
                if (values != null && values.equals("1"))
                {
                    // 防止连续的漏掉了
                    LOG.error("conId=" + conId + ",在AP应用黑名单中，置底显示-move to last");
                    blackapp.add(contentlist.get(i));
                    contentlist.remove(i);
                    i--;
                }
            }
            contentlist.addAll(blackapp);
        }
    }

    /**
     * 判断当前时间,此次任务是否需要执行。
     * 
     * @return
     */
    private boolean IsNeedExecution()// (Date lastExcuteTime, int period)
    {

        Calendar now = Calendar.getInstance();
        if (CategoryConstants.INTERVALTYPE_DAY == vo.getIntervalType())
        {
            if (vo.getLastExcuteTime() == null)
            {
                return true;
            }
            Calendar lastDate = Calendar.getInstance();
            // 需要把Date类型转化为Calendar
            lastDate.setTime(vo.getLastExcuteTime());
            return compareDateDif(now,
                                  lastDate,
                                  Calendar.DAY_OF_MONTH,
                                 // vo.getIntervalCount());
                                  vo.getExcuteInterval());
        }
        else if (vo.getIntervalType() == CategoryConstants.INTERVALTYPE_WEEK)

        {
            // Calendar返回值中，周日是1，周六是7,需要转换成中国的标准。
            // 周一是1，周日是7
            int weekDay = now.get(Calendar.DAY_OF_WEEK);
            weekDay = weekDay - 1;
            if (weekDay == 0)
            {
                weekDay = 7;
            }
            if (weekDay != vo.getExcuteTime())
            
            {
                return false;
            }
            if (vo.getLastExcuteTime() == null)
            {
                return true;
            }
            Calendar lastDate = Calendar.getInstance();
            // 需要把Date类型转化为Calendar
            lastDate.setTime(vo.getLastExcuteTime());
            return compareDateDif(now,
                                  lastDate,
                                  Calendar.WEEK_OF_MONTH,
                                //  vo.getIntervalCount());
                                  vo.getExcuteInterval());
        }
        else if (vo.getIntervalType() == CategoryConstants.INTERVALTYPE_MONTH)
        {
            int monthDay = now.get(Calendar.DAY_OF_MONTH);
            if (monthDay != vo.getExcuteTime())
            
            {
                return false;
            }
            if (vo.getLastExcuteTime() == null)
            {
                return true;
            }
            Calendar lastDate = Calendar.getInstance();
            // 需要把Date类型转化为Calendar
            lastDate.setTime(vo.getLastExcuteTime());
            return compareDateDif(now,
                                  lastDate,
                                  Calendar.MONTH,
                                 // vo.getIntervalCount());
                                  vo.getExcuteInterval());
        }
        else
        {
            LOG.error("目前还不支持此类型，IntervalType=" + vo.getIntervalType());
            return false;
        }

    }

    /**
     * 比较nowDate与lastDate相差时间是否大于等于count个以field 为单位的时间。
     * 
     * @param nowDate 当前时间
     * @param lastDate 上次更新时间
     * @param field 相差的单位，日，周，月
     * @param count 单位时间的个数
     */
    private boolean compareDateDif(Calendar nowDate, Calendar lastDate,
                                   int field, int count)
    {

        nowDate.add(field, -count);
        // 比较字符串判断当前时间是否相同。
        String data1String = PublicUtil.getDateString(nowDate.getTime(),
                                                      "yyyyMMdd");
        String data2String = PublicUtil.getDateString(lastDate.getTime(),
                                                      "yyyyMMdd");

        int result = data1String.compareTo(data2String);
        if (result >= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 判断当前规则是是否已经有效。
     * 
     * @param date 有效日期
     * @return 当前日期大于等于date既表示有效（以天为单位进行比较）。
     */
    private boolean isEffective(Date date)
    {

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        return now.getTime().after(date);
    }

    /**
     * 变更货架排序信息
     * 
     * @param goodList
     * @throws BOException
     */
    private void updateSortId(List goodList) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("开始更新当前货架的排序值。。。");
        }
        String sqlCode = "category.rule.CategoryRuleExcutor.UPDATE.updateSortId";
        Object mutiParas[][] = new Object[goodList.size()][2];
        for (int i = 0; i < goodList.size(); i++)
        {
            ReferenceNode node = ( ReferenceNode ) goodList.get(i);
            mutiParas[i][0] = new Integer(node.getSortID());
            mutiParas[i][1] = node.getId();
        }
        try
        {
            DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
        }
        catch (DAOException e)
        {
            throw new BOException("更新货架商品排序异常");
        }
    }

    /**
     * 货架变更排序列表返回值
     * 
     * @param cate
     * @param condition
     * @return
     * @throws BOException
     */
    private List getSortedGoods(Category cate,
                                CategoryRuleByCarveOutVO condition)
                    throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("开始获取当前货架下的所有商品。。。");
        }
        String sql;
        try
        {
            // select r.id from t_r_gcontent g ,t_r_reference r
            // where r.categoryid=? and r.refnodeid=g.id
        	 String baseSQLId = condition.getBaseSQLId();
             sql = (String)BASESQLCACHE.get(baseSQLId);
             if(sql == null || sql.equals("")){
            	 throw  new BOException("获取不到创业大赛自动更新基础语句"); 
             }
            //sql = ;
            	
            	//DB.getInstance()
                  //  .getSQLByCode("category.rule.CategoryRuleExcutor.SELECT.getSortedGoodsByCarveOut");
        }
        catch (Exception e1)
        {
            throw new BOException("查询sqlcode异常，category.rule.CategoryRuleExcutor.SELECT.getSortedGoodsByCarveOut");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(sql);
        if (condition.getOSql() != null && !condition.getOSql().equals(""))
        {
            sb.append(" order by ");
            sb.append(condition.getOSql());
        }

        LOG.info("getSortedGoods:" + sb.toString());
        LOG.info("paras:" + cate.getCategoryID());

        String paras[] = { cate.getCategoryID() };
        List list = new ArrayList();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().query(sb.toString(), paras);
            while (rs.next())
            {
                list.add(rs.getString("id"));
            }
        }
        catch (Exception e)
        {
            throw new BOException("从内容库获取内容出错.getSortedGoods=" + sb.toString()
                                  + ",paras=" + cate.getCategoryID(), e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    private List getContentIDList(CategoryRuleByCarveOutVO condition)
                    throws BOException
    {

        String sql;
        String paras[] = null;
        String condType;
        try
        {
            condType = "产品库自有业务获取条件sql：";
            /**
             * select b.id from t_r_base b, t_r_gcontent g, v_service
             * v,t_content_count c where b.id = g.id and b.type like
             * 'nt:gcontent:app%' and v.icpcode = g.icpcode and v.icpservid =
             * g.icpservid and g.contentid=c.contentid(+) and g.provider !='B'
             * and g.subtype = '6'
             */
            String baseSQLId = condition.getBaseSQLId();
            sql = (String)BASESQLCACHE.get(baseSQLId);
            if(sql == null || sql.equals("")){
           	 throw  new BOException("获取不到创业大赛自动更新基础语句"); 
            }
            	//DB.getInstance()
                   // .getSQLByCode("category.rule.CategoryRuleByCarveOutExcutor.SELECT.getOwnerContentIDList");

        }
        catch (Exception e1)
        {
            throw new BOException("获取内容出错", e1);
        }
        StringBuffer sb = new StringBuffer();
        sb.append(sql);
        if (condition.getWSql() != null)
        {
            sb.append(" and ");
            sb.append(condition.getWSql());
        }
        if (condition.getOSql() != null
            && !"".equals(condition.getOSql().trim()))
        {
            sb.append(" order by ");
            sb.append(condition.getOSql());
        }
        List list = new ArrayList();
        int count = condition.getCount();
        if (count == -1)// -1表示系统不限制个数,使用配置项中的最大值。
        {
            count = CategoryRuleByCarveOutConfig.CONDITIONMAXVALUE;// default;
        }

        // 最后组装sql，只读取制定的行数据。
        sql = "select * from (" + sb.toString() + ")where rownum <= " + count;

        LOG.info(condType + sql);// 打印sql日志
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().query(sql, paras);
            while (rs.next())
            {
                list.add(rs.getString("id"));
            }
        }
        catch (Exception e)
        {
            LOG.error("获取内容出错:" + condType + sql, e);
            throw new BOException("获取内容出错");
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    public static void clearCache()
    {
        BLACKAPPCACHE.clear();
        BASESQLCACHE.clear();
    }

    public void task() throws Throwable
    {
        try
        {
            int result = excucte();
            if (result == CategoryRuleByCarveOutTask.EXECUTED)
            {
                task.addExecutedCount();
            }
            if (result == CategoryRuleByCarveOutTask.INEFFECTIVE)
            {
                task.addIneffectiveCount();
            }
            if (result == CategoryRuleByCarveOutTask.UPDATESUCESS)
            {
                task.addSuccCount();  
            }
        }
        catch (Exception e)
        {
            LOG.error("创业大赛货架更新失败。" + vo, e);
            task.addError(vo);
        }
    }
}
