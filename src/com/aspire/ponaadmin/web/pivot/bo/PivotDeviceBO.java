package com.aspire.ponaadmin.web.pivot.bo;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.book.bo.BookRefBO;
import com.aspire.ponaadmin.web.pivot.dao.PivotDeviceDAO;
import com.aspire.ponaadmin.web.pivot.vo.PivotDeviceVO;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;

public class PivotDeviceBO
{
    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(PivotDeviceBO.class);

    private static PivotDeviceBO instance = new PivotDeviceBO();

    private PivotDeviceBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static PivotDeviceBO getInstance()
    {
        return instance;
    }
    
    /**
     * 用于查询重点机型列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryPivotDeviceList(PageResult page, PivotDeviceVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotDeviceBO.queryPivotDeviceList( ) is start...");
        }

        try
        {
            // 调用PivotDeviceDAO进行查询
            PivotDeviceDAO.getInstance().queryPivotDeviceList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询重点机型列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于移除指定重点机型
     * 
     * @param deviceId
     */
    public void removeDeviceID(String[] deviceId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotDeviceBO.removeDeviceID( ) is start...");
        }

        try
        {
            // 调用PivotDeviceDAO
            PivotDeviceDAO.getInstance().removeDeviceID(deviceId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("移除指定重点机型时发生数据库异常！");
        }
    }
    
    /**
     * 导入重点机型列表
     * 
     * @param dataFile
     * @throws BOException
     */
    public String importPivotDevice(FormFile dataFile) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotDeviceBO.importPivotDevice( ) is start...");
        }

        StringBuffer ret = new StringBuffer();

        try
        {
            // 解析EXECL文件，获取终端软件版本列表
            List list = BookRefBO.getInstance().paraseDataFile(dataFile);

            // 校驗文件中數據是否在机型表中存在
            String temp = PivotDeviceDAO.getInstance()
                            .verifyDeviceId(list);
            
            if (!"".equals(temp))
            {
                ret.append("机型编码有误，有误机型编码分别为：").append(temp).append("请修改！");
                
                throw new BOException(ret.toString(), RepositoryBOCode.CATEGORY_DEVICE);
            }
            
            // 检验列表中是否存在原表数据
            PivotDeviceDAO.getInstance().hasDeviceId(list);
            
            String[] ids = new String[list.size()];
            
            for (int i = 0; i < list.size(); i++)
            {
                ids[i] = (String)list.get(i);
            }
            
            // 调用PivotDeviceDAO进行新增
            PivotDeviceDAO.getInstance()
                              .addDeviceId(ids);

            ret.append("成功导入" + list.size() + "条记录.");

            return ret.toString();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("导入重点机型列表时发生数据库异常！");
        }
    }
}
