package com.aspire.dotcard.basecomic.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.dao.BlackDAO;
import com.aspire.dotcard.basecomic.vo.BlackVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;

/**
 * 动漫黑名单业务类
 */
public class BlackBO
{
    /**
     * 日志引用
     */
    protected static final JLogger LOG = LoggerFactory.getLogger(BlackBO.class);

    private static BlackBO instance = new BlackBO();

    private BlackBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static BlackBO getInstance()
    {
        return instance;
    }

    /**
     * 用于查询当前黑名单列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryBlackList(PageResult page, BlackVO vo) throws BOException
    {
        try
        {
            BlackDAO.getInstance().queryBlackList(page, vo);
        }
        catch (DAOException e)
        {
            LOG.error("动漫发生数据库异常!", e);
            throw new BOException("动漫发生数据库异常!");
        }
    }

    /**
     * 用于查询当前内容列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryContentList(PageResult page, BlackVO vo) throws BOException
    {
        try
        {
            BlackDAO.getInstance().queryContentList(page, vo);
        }
        catch (DAOException e)
        {
            LOG.error("查询内容数据发生数据库异常!", e);
            throw new BOException("查询内容数据发生数据库异常!");
        }
    }
    
    /**
     * 删除黑名单
     * 
     * @param id
     * @throws BOException
     */
    public void removeBlack(String[] id) throws BOException
    {
        if (id == null || (id != null && id.length <= 0))
        {
            LOG.warn("删除动漫黑名单,ID参数为空!");
            throw new BOException("删除动漫黑名单,ID参数为空!");
        }
        try
        {
            BlackDAO.getInstance().removeBlack(id);
        }
        catch (DAOException e)
        {
            LOG.error("返回动漫黑名单列表时发生数据库异常!", e);
            throw new BOException("返回动漫黑名单列表时发生数据库异常!");
        }
    }

    /**
     * 添加黑名单
     * 
     * @param contentIdArray
     *            内容ID
     * @return 添加结果信息
     * @throws BOException
     */
    public int addBlack(String[] arrlist) throws BOException
    {
        int result = 0;
        if (arrlist == null || (arrlist != null && arrlist.length <= 0))
        {
            LOG.warn("动漫List为空!");
            return result;
        }
        try
        {
            BlackDAO.getInstance().addBlack(arrlist);
            result = arrlist.length;
        }
        catch (Exception e)
        {
            LOG.error("动漫黑名单增加操作发生数据库异常!", e);
            throw new BOException("动漫黑名单增加操作发生数据库异常!");
        }
        return result;
    }

    /**
     * 增量操作：解析excel导入到数据库表，如果数据库已经存在，不入库
     * 
     * @param dataFile
     *            数据文件对象
     * @return 导入结果信息
     * @throws BOException
     * @throws BOException
     */
    @SuppressWarnings("unchecked")
    public int importBlackADD(String[] arrList) throws BOException
    {
        int result = 0;
        try
        {
            result = addBlack(arrList);
        }
        catch (BOException e)
        {
            LOG.error("动漫黑名单增量导入,入库异常!", e);
            throw new BOException("动漫黑名单增量导入,入库异常!");
        }
        return result;
    }

    /**
     * @param dataFile
     * @return
     * @throws BOException
     */
    @SuppressWarnings("unchecked")
    public int importBlackALL(List<String> list1) throws BOException
    {
        int result = 0;

        if (list1 == null || (list1 != null && list1.size() <= 0))
        {
            LOG.warn("动漫arrlist[]参数为空!");
            return result;
        }
        try
        {
            BlackDAO.getInstance().allAddBlack(list1);
            result = list1.size();
            list1.clear();
            list1 = null;

        }
        catch (DAOException e)
        {
            LOG.error("动漫黑名单全量导入操作发生数据库异常!", e);
            throw new BOException("动漫黑名单全量导入操作发生数据库异常！");
        }
        return result;

    }

    @SuppressWarnings("unchecked")
    public String[] getImportBalckParaseDataList(FormFile dataFile) throws BOException
    {
        List list = null;
        try
        {
            list = new ExcelHandle().paraseDataFile(dataFile, null);
        }
        catch (BOException e)
        {
            LOG.error("动漫黑名单导入,Excel解析异常!", e);
            throw new BOException("动漫黑名单导入,Excel解析异常!");
        }

        if (list == null || (list != null && list.size() <= 0))
        {
            LOG.warn("动漫黑名单导入excel解析List为空!");
            return new String[0];
        }

        String[] arrList = new String[list.size()];
        for (int i = 0; i < list.size(); i++)
        {
            Map m = (Map)list.get(i);
            arrList[i] = (String)m.get(0);
            m = null;
        }
        list = null;

        return arrList;
    }

    /**
     * 检查内容是否存在t_cb_content表
     * 
     * @param list
     * @return 存在t_cb_content表的内容ID
     * @throws BOException
     */
    public Map<String, Object> isExistContent(String[] arrlist) throws BOException
    {

        int error1 = 0;
        int error2 = 0;
        StringBuilder msg1 = new StringBuilder(128);
        StringBuilder msg2 = new StringBuilder(128);
        List<String> newList = new ArrayList<String>(arrlist.length);

        for (int i = 0; i < arrlist.length; i++)
        {
            String contentId = arrlist[i];
            if (StringUtils.isNotBlank(contentId))
            {
                try
                {
                    boolean result = BlackDAO.getInstance().isExistContent(contentId);
                    if (result)
                    {
                        newList.add(contentId);
                    }
                    else
                    {// 内容ID在t_cb_content表不存在
                        msg1.append(contentId).append(",");
                        error1++;
                        if (error1 % 5 == 0)
                        {
                            msg1.append("<br/>");
                        }
                    }
                }
                catch (DAOException e)
                {
                    error2++;
                    msg2.append(contentId).append(",");
                    if (error2 % 5 == 0)
                    {
                        msg2.append("<br/>");
                    }
                    LOG.error("内容表查询contentId:" + contentId + "操作发生数据库异常!", e);
                }
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (error1 > 0)
        {
            resultMap.put("msg1", "内容ID在t_cb_content表不存在：" + error1 + "条记录," + msg1.substring(0, msg1.length() - 1));
        }
        if (error2 > 0)
        {
            resultMap.put("msg2", "在内容表中查询报错：" + error2 + "条记录," + msg2.substring(0, msg2.length() - 1));
        }
        resultMap.put("list", newList);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("内容ID在t_cb_content表不存在：" + msg1);
            LOG.debug("在内容表中查询报错：" + msg2);
        }
        msg1 = null;
        msg2 = null;
        return resultMap;
    }

    /**
     * 检查内容是否存在t_cb_black表
     * 
     * @param list
     * @return 不存在t_cb_black表的内容ID
     * @throws BOException
     */
    public Map<String, Object> isExistBlack(List<String> list) throws BOException
    {
        int error1 = 0;
        int error2 = 0;
        StringBuilder msg1 = new StringBuilder(128);
        StringBuilder msg2 = new StringBuilder(128);

        List<String> newList = new ArrayList<String>(list.size());
        for (int i = 0; list != null && i < list.size(); i++)
        {
            String contentId = (String)list.get(i);
            if (StringUtils.isNotBlank(contentId))
            {
                try
                {
                    boolean result = BlackDAO.getInstance().isExistBlack(contentId);
                    if (result)
                    {// 内容ID在t_cb_black表存在
                        msg1.append(contentId).append(",");
                        error1++;
                        if (error1 % 5 == 0)
                        {
                            msg1.append("<br/>");
                        }
                    }
                    else
                    {
                        newList.add(contentId);
                    }
                }
                catch (DAOException e)
                {
                    error2++;
                    msg2.append(contentId).append(",");
                    if (error2 % 5 == 0)
                    {
                        msg2.append("<br/>");
                    }
                    LOG.error("动漫黑名单查询contentId:" + contentId + "操作发生数据库异常!", e);
                }
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (error1 > 0)
        {
            resultMap.put("msg1", "内容ID在t_cb_black表存在：" + error1 + "条记录," + msg1.substring(0, msg1.length() - 1));
        }
        if (error2 > 0)
        {
            resultMap.put("msg2", "在黑名单查询查询报错：" + error2 + "条记录," + msg2.substring(0, msg2.length() - 1));
        }
        resultMap.put("list", newList);
        if (LOG.isDebugEnabled())
        {
            LOG.debug("内容ID在t_cb_black表存在：" + msg1);
            LOG.debug("在黑名单查询查询报错：" + msg2);
        }
        msg1 = null;
        msg2 = null;

        return resultMap;
    }

    /**
     * 根据黑名单列表删除商品关联表T_CB_REFERENCE
     * 
     * @throws BOException
     */
    public void delReference() throws BOException
    {
        try
        {
            BlackDAO.getInstance().delReference();
        }
        catch (DAOException e)
        {
            LOG.error("根据动漫黑名单列表删除商品关联表异常!", e);
            throw new BOException("根据动漫黑名单列表删除商品关联表异常!");
        }
    }
}
