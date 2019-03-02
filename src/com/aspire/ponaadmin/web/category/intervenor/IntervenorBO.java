/*
 * 
 */

package com.aspire.ponaadmin.web.category.intervenor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.category.intervenor.gcontent.IntervenorGcontentDAO;
import com.aspire.ponaadmin.web.category.intervenor.gcontent.IntervenorGcontentVO;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;

/**
 * 人工干预容器逻辑操作类
 * 
 * @author x_wangml
 * 
 */
public class IntervenorBO
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(IntervenorBO.class);

    private static IntervenorBO instance = new IntervenorBO();

    private IntervenorBO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static IntervenorBO getInstance()
    {

        return instance;
    }

    /**
     * 根据容器名称返回容器列表
     * 
     * @param page
     * @param name
     * @throws BOException
     */
    public List queryIntervenorVOList(String name) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.queryIntervenorVOList(" + name
                         + ") is start...");
        }

        try
        {
            // 调用IntervenorDAO进行查询
            return IntervenorDAO.getInstance().queryIntervenorVOList(name);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据容器名称返回容器列表时发生数据库异常！");
        }
    }

    /**
     * 根据容器id得到干预容器
     * 
     * @param id 容器id
     * @return
     * @throws BOException
     */
    public IntervenorVO queryInternorVOById(String id) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.queryIntervenorVOList(" + id
                         + ") is start...");
        }

        try
        {
            // 调用IntervenorDAO进行查询
            return IntervenorDAO.getInstance().queryInternorVOById(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据容器id得到干预容器时发生数据库异常！");
        }
    }

    /**
     * 根据容器id得到容器中内容列表
     * 
     * @param id 容器id
     * @throws BOException
     * @throws DAOException
     */
    public void queryGcontentListByIntervenorId(PageResult page, String id)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.queryGcontentListByIntervenorId(" + id
                         + ") is start...");
        }

        try
        {
            // 调用IntervenorGcontentDAO进行查询
            IntervenorGcontentDAO.getInstance()
                                 .queryGcontentListByIntervenorId(page, id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据容器id得到容器中内容列表时发生数据库异常！");
        }
    }

    /**
     * 修改容器信息
     * 
     * @param vo
     * @return
     */
    public int editInternorVO(IntervenorVO vo) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.editInternorVO(" + vo.toString()
                         + ") is start...");
        }

        try
        {
            // 调用IntervenorDAO进行查询
            return IntervenorDAO.getInstance().editInternorVO(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("修改容器信息时发生数据库异常！");
        }
    }

    /**
     * 用于设置容器内内容的排序
     * 
     * @param id 容器id
     * @param sortId 内容id
     * @param sortNum 排序值
     * @throws BOException
     */
    public void editContentSort(String id, String[] sortValue)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.editContentSort(" + id + ") is start...");
        }

        try
        {
            // 调用IntervenorGcontentDAO进行查询
            IntervenorGcontentDAO.getInstance().editContentSort(id, sortValue);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("设置容器内内容的排序时发生数据库异常！");
        }
    }

    /**
     * 根据容器id删除容器
     * 
     * @param id 容器id
     */
    public void deleteInternorVOById(String id) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.deleteInternorVOById(" + id
                         + ") is start...");
        }

        try
        {
            // 调用IntervenorDAO进行查询
            IntervenorDAO.getInstance().deleteInternorVOById(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据容器id删除容器时发生数据库异常！");
        }
    }

    /**
     * 在指定容器中删除指定内容
     * 
     * @param id 容器id
     * @param contentId 内容id
     * @return
     */
    public int deleteGcontentById(String id, String contentId)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.deleteGcontentById(" + id + ", "
                         + contentId + ") is start...");
        }

        try
        {
            // 调用IntervenorGcontentDAO进行查询
            return IntervenorGcontentDAO.getInstance()
                                        .deleteGcontentById(id, contentId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("在指定容器中删除指定内容时发生数据库异常！");
        }
    }

    /**
     * 新增容器信息
     * 
     * @param vo 容器信息
     * @return
     */
    public int addInternorVO(IntervenorVO vo) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.addInternorVO(" + vo.toString()
                         + ") is start...");
        }

        try
        {
            // 调用IntervenorDAO进行查询
            return IntervenorDAO.getInstance().addInternorVO(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("新增容器信息时发生数据库异常！");
        }
    }

    /**
     * 新增内容至人工干预容器
     * 
     * @param id 容器id
     * @param contentId 内容id
     * @return
     * @throws BOException
     */
    public void addGcontentToIntervenorId(String id, String[] contentId)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.addInternorVO(" + id + ", " + contentId
                         + ") is start...");
        }

        try
        {
            // 调用IntervenorGcontentDAO进行查询
            IntervenorGcontentDAO.getInstance()
                                 .addGcontentToIntervenorId(id, contentId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("新增内容至人工干预容器时发生数据库异常！");
        }
    }

    /**
     * 查询内容是否存在于人工干预容器时
     * 
     * @param id 容器id
     * @param contentId 内容id
     * @return
     * @throws BOException
     */
    public IntervenorGcontentVO getContentVO(String id, String contentId)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.getContentVO(" + id + ", " + contentId
                         + ") is start...");
        }

        try
        {
            return IntervenorGcontentDAO.getInstance()
                                        .queryGcontentVO(id, contentId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询内容是否存在于人工干预容器时发生数据库异常！");
        }
    }

    /**
     * 得到SEQ用以当容器id
     * 
     * @return
     */
    public int getInternorId() throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.getInternorId() is start...");
        }

        try
        {
            // 调用IntervenorDAO进行查询
            return IntervenorDAO.getInstance().getInternorId();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("得到SEQ用以当容器id时发生数据库异常！");
        }
    }

    /**
     * 用于查询内容信息
     * 
     * @param id 内容id
     * @param name 内容名称
     * @param spName 提供商ID
     * @param keywordsDesc 内容keywordsDesc
     * @param contentId 内容contentID
     * @param contentTag 内容contentTag
     * @return
     * @throws BOException
     */
    public void queryGcontentList(PageResult page, String id, String name,
                                  String spName, String keywordsDesc,
                                  String contentId, String contentTag)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.queryGcontentList(" + id + ", " + name
                         + ") is start...");
        }

        Node rootNode = new Node(RepositoryConstants.ROOT_CONTENT_ID);
        Searchor searchor = new Searchor();

        if (!"".equals(id))
        {
            searchor.getParams()
                    .add(new SearchParam("id", RepositoryConstants.OP_EQUAL, id));
        }
        if (!"".equals(name))
        {
            searchor.getParams()
                    .add(new SearchParam("name",
                                         RepositoryConstants.OP_LIKE,
                                         "%" + SQLUtil.escape(name) + "%"));
        }
        if (!"".equals(spName))
        {
            searchor.getParams()
                    .add(new SearchParam("spName",
                                         RepositoryConstants.OP_LIKE,
                                         "%" + SQLUtil.escape(spName) + "%"));
        }
        if (!"".equals(keywordsDesc))
        {
            searchor.getParams()
                    .add(new SearchParam("keywords",
                                         RepositoryConstants.OP_LIKE,
                                         "%" + SQLUtil.escape(keywordsDesc) + "%"));
        }
        if (!"".equals(contentId))
        {
            searchor.getParams()
                    .add(new SearchParam("contentID",
                                         RepositoryConstants.OP_EQUAL,
                                         contentId));
        }
        if (!"".equals(contentTag))
        {
            searchor.getParams()
                    .add(new SearchParam("contentTag",
                                         RepositoryConstants.OP_EQUAL,
                                         contentTag));
        }

        searchor.getParams().add(new SearchParam("type",
                                                 RepositoryConstants.OP_LIKE,
                                                 "nt:gcontent:app%"));
        searchor.getParams()
                .add(new SearchParam("subType",
                                     RepositoryConstants.OP_NOT_EQUAL,
                                     "6"));
        rootNode.searchNodes(page, GContent.TYPE_CONTENT, searchor, null);
    }

    /**
     * 根据容器名称查看是否存在此容器名
     * 
     * @param name 容器名称
     * @return
     */
    public boolean hasInternorName(String name) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.hasInternorName(" + name
                         + ") is start...");
        }

        try
        {
            // 调用IntervenorDAO进行查询
            return IntervenorDAO.getInstance().hasInternorName(name);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据容器名称查看是否存在此容器名时发生数据库异常！");
        }
    }

    /**
     * 释放过期容器
     * 
     * @throws BOException
     */
    public void overdueIntervenor() throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.overdueIntervenor() is start...");
        }

        try
        {
            // 调用IntervenorDAO进行查询
            IntervenorDAO.getInstance().overdueIntervenorId();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("释放过期容器时发生数据库异常！");
        }
    }

    /**
     * 删除失效内容
     * 
     * @throws BOException
     */
    public void delInvalidationContent() throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.delInvalidationContent() is start...");
        }

        try
        {
            IntervenorGcontentDAO.getInstance().delInvalidationContent();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("删除失效内容时发生数据库异常！");
        }
    }

    /**
     * 用于查询榜单对应的容器的内容情况
     * 
     * @param categoryId 榜单id
     * @return
     * @throws BOException
     */
    public List getIntervenorData(String categoryId) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.getIntervenorDate() is start...");
        }

        Map tempMap = new TreeMap();

        List tempList = null;

        List retList = new ArrayList();

        try
        {
            // 调用IntervenorDAO进行查询
            tempList = IntervenorGcontentDAO.getInstance()
                                            .getIntervenorData(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询榜单对应的容器的内容情况时发生数据库异常！");
        }

        for (Iterator iter = tempList.iterator(); iter.hasNext();)
        {
            IntervenorGcontentVO vo = ( IntervenorGcontentVO ) iter.next();

            if (tempMap.containsKey(new Integer(vo.getCategorySort())))
            {
                IntervenorVO intervenor = ( IntervenorVO ) tempMap.get(new Integer(vo.getCategorySort()));

                List list = intervenor.getContentList();

                list.add(vo.getContentId());

                intervenor.setContentList(list);

                tempMap.put(new Integer(vo.getCategorySort()), intervenor);
            }
            else
            {
                IntervenorVO intervenor = new IntervenorVO();

                intervenor.setId(Integer.parseInt(vo.getIntervenorId()));
                intervenor.setName(vo.getName());
                intervenor.setStartSortId(vo.getStartSortid());
                intervenor.setSortId(vo.getCategorySort());

                List list = new ArrayList();

                list.add(vo.getContentId());

                intervenor.setContentList(list);

                tempMap.put(new Integer(vo.getCategorySort()), intervenor);
            }
        }

        Set set = tempMap.keySet();

        for (Iterator iter = set.iterator(); iter.hasNext();)
        {
            Integer element = ( Integer ) iter.next();
            retList.add(tempMap.get(element));
        }

        return retList;
    }

    /**
     * 用于导入内容文件数据至指定容器中
     * 
     * @param id
     * @param dataFile
     * @throws BOException
     */
    public void importFileById(String id, FormFile dataFile) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.importFileById() is start...");
        }

        // 解析导入文件数据
        List tempList = paraseDataFile(dataFile);

        if (logger.isDebugEnabled())
        {
            logger.debug("解析之后去重数据数为" + tempList.size());
        }

        // 删除原容器中的所有内容
        delAllContentById(id);

        // 校验导入文件中的数据的合法性
        List list = checkoutContentId(tempList);

        if (list.size() != 0)
        {
            String[] temp = new String[list.size()];
            
            for (int i = 0; i < list.size(); i++)
            {
                temp[i] = String.valueOf(list.get(i));
            }

            // 写入数据库中存入当前容器中
            addGcontentToIntervenorId(id, temp);
        }
    }

    /**
     * 用于校验导入文件中的数据合法性，同时返回内容id列表
     * 
     * @param list
     * @return
     * @throws BOException
     */
    private List checkoutContentId(List list) throws BOException
    {
        List returnList = new ArrayList();

        for (int i = 0; i < list.size(); i++)
        {
            try
            {
                String temp = IntervenorGcontentDAO.getInstance()
                                                   .queryIdByContentId(String.valueOf(list.get(i)));

                // 如果是空说明数据不合法
                if (!"".equals(temp))
                {
                    returnList.add(temp);
                }
                else
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("不合法数据，id=" + String.valueOf(list.get(i)));
                    }
                }
            }
            catch (DAOException e)
            {
                logger.error(e);
                throw new BOException("校验导入文件中的数据的合法性时发生数据库异常！");
            }
        }

        return returnList;
    }

    /**
     * 删除指定容器下全部内容
     * 
     * @param id 容器id
     * @throws BOException
     */
    private void delAllContentById(String id) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorBO.delAllContentById() is start...");
        }

        try
        {
            IntervenorGcontentDAO.getInstance().delAllContentById(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("删除指定容器下全部内容时发生数据库异常！");
        }
    }

    /**
     * 解析导入文件去重得到返回集合
     * 
     * @param dataFile
     * @return
     * @throws BOException
     */
    private List paraseDataFile(FormFile dataFile) throws BOException
    {
        logger.info("IntervenorBO.paraseDataFile() is start!");

        List list = new ArrayList();

        Workbook book = null;

        try
        {
            book = Workbook.getWorkbook(dataFile.getInputStream());

            Sheet[] sheets = book.getSheets();

            // 只拿第一个sheet
            int rows = sheets[0].getRows();

            if (logger.isDebugEnabled())
            {
                logger.debug("dataFile.rows==" + rows);
            }

            // 循环每一行
            for (int j = 0; j < rows; j++)
            {
                // 只有一个id信息
                String value = sheets[0].getCell(0, j).getContents().trim();

                // 如果集合中存在此数据
                if (list.contains(value))
                {
                    // 删除原来存在的数据
                    list.remove(value);
                }

                // 加入内容数据id
                list.add(value);
            }

        }
        catch (Exception e)
        {
            logger.error("解析导入文件出现异常,fineName:" + dataFile.getFileName(), e);
            throw new BOException("解析导入文件出现异常", e);
        }
        finally
        {
            book.close();
        }

        return list;
    }
}
