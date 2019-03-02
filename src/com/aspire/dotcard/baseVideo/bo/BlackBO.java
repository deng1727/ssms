package com.aspire.dotcard.baseVideo.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.dao.BlackDAO;
import com.aspire.dotcard.baseVideo.vo.BlackVO;
import com.aspire.dotcard.baseVideo.vo.ProgramBlackVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;

/**
 * 视频黑名单业务类
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
    public void queryBlackList(PageResult page, ProgramBlackVO vo) throws BOException
    {
        try
        {
            BlackDAO.getInstance().queryBlackList(page, vo);
        }
        catch (DAOException e)
        {
            LOG.error("视频发生数据库异常!", e);
            throw new BOException("视频发生数据库异常!");
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
            LOG.warn("删除视频黑名单,ID参数为空!");
            throw new BOException("删除视频黑名单,ID参数为空!");
        }
        try
        {
            BlackDAO.getInstance().removeBlack(id);
        }
        catch (DAOException e)
        {
            LOG.error("返回视频黑名单列表时发生数据库异常!", e);
            throw new BOException("返回视频黑名单列表时发生数据库异常!");
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
    public int addBlack(String[] arrlist,String[] nodeid) throws BOException
    {
        int result = 0;
        if (arrlist == null || (arrlist != null && arrlist.length <= 0))
        {
            LOG.warn("视频List为空!");
            return result;
        }
        try
        {
            BlackDAO.getInstance().addBlack(arrlist,nodeid);
            result = arrlist.length;
        }
        catch (Exception e)
        {
            LOG.error("视频黑名单增加操作发生数据库异常!", e);
            throw new BOException("视频黑名单增加操作发生数据库异常!");
        }
        return result;
    }
    /**
     * 添加黑名单
     * 
     * @param contentIdArray
     *            内容ID
     * @return 添加结果信息
     * @throws BOException
     */
    public int addImportBlack(HashMap<String,String[]> arrlist) throws BOException
    {
        int result = 0;
//        if (arrlist == null || (arrlist != null && arrlist.get("grogramid") == 0))
      if (arrlist == null )

        {
            LOG.warn("视频List为空!");
            return result;
        }
        try
        {
            int number = BlackDAO.getInstance().addImBlack(arrlist);
            result= number;
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            LOG.error("视频黑名单增加操作发生数据库异常!", e);
            throw new BOException("视频黑名单增加操作发生数据库异常!");
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
    public int importBlackADD(HashMap<String,String[]> arrList) throws BOException
    {
        int result = 0;
        try
        {
            result = addImportBlack(arrList);
        }
        catch (BOException e)
        {
            LOG.error("视频黑名单增量导入,入库异常!", e);
            throw new BOException("视频黑名单增量导入,入库异常!");
        }
        return result;
    }

    /**
     * @param dataFile
     * @return
     * @throws BOException
     */
//    @SuppressWarnings("unchecked")
//    public int importBlackALL(List<String> list1) throws BOException
//    {
//        int result = 0;
//
//        if (list1 == null || (list1 != null && list1.size() <= 0))
//        {
//            LOG.warn("动漫arrlist[]参数为空!");
//            return result;
//        }
//        try
//        {
//            BlackDAO.getInstance().allAddBlack(list1);
//            result = list1.size();
//            list1.clear();
//            list1 = null;
//
//        }
//        catch (DAOException e)
//        {
//            LOG.error("动漫黑名单全量导入操作发生数据库异常!", e);
//            throw new BOException("动漫黑名单全量导入操作发生数据库异常！");
//        }
//        return result;
//
//    }

    @SuppressWarnings("unchecked")
    public HashMap<String,String[]> getImportBalckParaseDataList(FormFile dataFile) throws BOException
    {
        List list = null;
        HashMap<String,String[]> map = new HashMap<String,String[]>();
        try
        {
        	
            list = new ExcelHandle().paraseDataFile(dataFile, null);
        }

        catch (BOException e)
        {
            LOG.error("视频黑名单导入,Excel解析异常!", e);
            throw new BOException("视频黑名单导入,Excel解析异常!");
        }

        String[] arrList = new String[list.size()];
        String[] arrList2 = new String[list.size()];
        if (list == null || (list != null && list.size() <= 0))
        {
            LOG.warn("视频黑名单导入excel解析List为空!");
            map.put("programid", arrList);
            map.put("nodeid", arrList2);
            return map;
        }


        for (int i = 0; i < list.size(); i++)
        {
            Map m = (Map)list.get(i);
            arrList[i] = (String)m.get(0);
            arrList2[i] = (String)m.get(1);
            
            m = null;
        }
        list = null;
        map.put("programid", arrList);
        map.put("nodeid", arrList2);

        return map;
    }

}
