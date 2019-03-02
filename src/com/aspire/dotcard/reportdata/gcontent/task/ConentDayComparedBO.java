/*
 * 文件名：ConentDayComparedBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.dotcard.reportdata.gcontent.task;

import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class ConentDayComparedBO
{
    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ConentDayComparedBO.class);

    private static ConentDayComparedBO bo = new ConentDayComparedBO();

    private ConentDayComparedBO()
    {
    }

    public static ConentDayComparedBO getInstance()
    {
        return bo;
    }

    public void exe()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("调用存储过程，计算七天下载量排序差值。开始执行。");
        }
        
        // 调用p_Binding_sort存储过程计算七天下载量排序差值
        try
        {
            ConentDayComparedDAO.getInstance().exeProcedure("p_Binding_sort");
        }
        catch (DAOException e)
        {
            logger.error("调用存储过程，计算七天下载量排序差值时出错！", e);
            return ;
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("调用存储过程，计算七天下载量排序差值。执行结束。");
        }
        
        /**
        if (!this.init())
        {
            logger.debug("准备工作出錯。执行结束。");
            return;
        }

        if (!this.newCompared("3"))
        {
            logger.debug("得出Ophone数据时出错。执行结束。");
            return;
        }

        if (!this.newCompared("9"))
        {
            logger.debug("得出安卓数据时出错。执行结束。");
            return;
        }
        
        if (!this.updateCompared())
        {
            logger.debug("计算阶段出错。执行结束。");
            return;
        }
        **/
    }

    /**
     * 准备工作
     */
    public boolean init()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("清空临时表数据，导入报表数据，排序号新旧变更。开始执行。");
        }

        // 清空t_r_servenday_temp
        try
        {
            ConentDayComparedDAO.getInstance().delOldRepData();
        }
        catch (DAOException e)
        {
            logger.error("在开始导入报表数据前，清空t_r_servenday_temp中前一天的过期数据时发生异常:", e);
            return false;
        }

        // 导入报表中report_servenday数据至t_r_servenday_temp
        try
        {
            ConentDayComparedDAO.getInstance().insertRepData();
        }
        catch (DAOException e)
        {
            logger.error("导入报表中report_servenday数据至t_r_servenday_temp时发生异常:", e);
            return false;
        }

        // t_r_sevendayscompared表中新排序号变更为旧排序号
        try
        {
            ConentDayComparedDAO.getInstance().updateNewNumberToOld();
        }
        catch (DAOException e)
        {
            logger.error("t_r_sevendayscompared表中新排序号变更为旧排序号时发生异常:", e);
            return false;
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("清空临时表数据，导入报表数据，排序号新旧变更。执行结束。");
        }

        return true;
    }

    /**
     * 统计ophone下载排行数据
     */
    public boolean newCompared(String osId)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("清空临时统计表数据，统计" + osId + "下载排行数据。开始执行。");
        }

        Map temp = null;

        // 清空临时统计表t_r_sevenday
        try
        {
            ConentDayComparedDAO.getInstance().delTempTable();
        }
        catch (DAOException e)
        {
            logger.error("清空临时统计表t_r_sevenday时发生异常:", e);
            return false;
        }

        // 统计报表数据至临时统计表t_r_sevenday中
        try
        {
            ConentDayComparedDAO.getInstance().insertNewToTemp(osId);
        }
        catch (DAOException e)
        {
            logger.error("统计报表数据至临时统计表t_r_sevenday中时发生异常:", e);
            return false;
        }

        // 得到临时表中全量数据
        try
        {
            temp = ConentDayComparedDAO.getInstance().getTempDate();
        }
        catch (DAOException e)
        {
            logger.error("统计报表数据至临时统计表t_r_sevenday中时发生异常:", e);
            return false;
        }

        // 结合统计表中数据更新七天变化量表t_r_sevendayscompared中新排序号
        try
        {
            ConentDayComparedDAO.getInstance().updateNewDate(temp, osId);
        }
        catch (DAOException e)
        {
            logger.error("结合统计表中数据更新七天变化量表t_r_sevendayscompared中新排序号时发生异常:", e);
            return false;
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("清空临时统计表数据，统计" + osId + "下载排行数据。执行结束。");
        }

        return true;
    }

    /**
     * 处理最后排序差结果
     * 
     */
    public boolean updateCompared()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("处理最后排序差结果。开始执行。");
        }

        // 清定最终存放排序差值的结果表
        try
        {
            ConentDayComparedDAO.getInstance().delCompared();
        }
        catch (DAOException e)
        {
            logger.error("清定最终存放排序差值的结果表时发生异常:", e);
            return false;
        }

        // 清定最终存放排序差值的结果表
        try
        {
            ConentDayComparedDAO.getInstance().updateCompared();
        }
        catch (DAOException e)
        {
            logger.error("清定最终存放排序差值的结果表时发生异常:", e);
            return false;
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("处理最后排序差结果。执行结束。");
        }

        return true;
    }
}