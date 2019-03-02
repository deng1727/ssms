package com.aspire.ponaadmin.web.repository ;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.dotcard.syncData.util.SyncDataConstant;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>内容资源的BO接口类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class ContentBO
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ContentBO.class);

    /**
     * singleton模式的实例
     */
    private static ContentBO instance = new ContentBO();

    /**
     * 构造方法，由singleton模式调用
     */
    private ContentBO ()
    {
    }

    /**
     * 获取实例
     * @return 实例
     */
    public static ContentBO getInstance()
    {
        return instance;
    }

    /**
     * 新增一个内容资源，并放到某个分类下
     *
     * @param categoryID String，目标分类的id
     * @param content Content，要添加的内容
     * @throws BOException
     * @return String
     */
    public String addContent(String categoryID, Content content) throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("addContent(" + categoryID + "," + content + ")") ;
        }
        //设置创建时间和修改时间
        String currentDate = PublicUtil.getCurDateTime("yyyyMMddHHmmss");
        content.setCreateTime(currentDate);
        content.setUpdateTime(currentDate);
        //保存内容资源
        Node rootNode = Repository.getInstance().getNode(
            RepositoryConstants.ROOT_CONTENT_ID) ;
        String nodeID = rootNode.addNode(content) ;
        rootNode.saveNode() ;
        //保存引用节点到目标分类下，不过如果是在未分类下添加，就不用
        if(!categoryID.equals(RepositoryConstants.ROOT_CATEGORY_ID))
        {
            //如果选择的不是根分类和未分类，还要添加到目标分类下
            Category category = (Category) Repository.getInstance().getNode(
                categoryID, RepositoryConstants.TYPE_CATEGORY) ;
            ReferenceNode ref = new ReferenceNode();
            ref.setRefNodeID(nodeID);

            ref.setSortID(0);
            category.addNode(ref);
            category.saveNode();
        }
        return nodeID;
    }

    /**
     * 从页面上传一个文件到资源库的公用方法
     * @param contentFile FormFile，struts上传的FormFile对象
     * @param userID String，操作用户的用户帐号
     * @param categoryID String，要保存的分类id，一般是资讯、新闻、广告的根分类，见RepositoryConstants类
     * @return String，保存的资源内容的ID
     * @throws BOException
     */
    public String addContent(FormFile contentFile, String userID, String categoryID)
                                 throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("addContent(" + contentFile + "," + userID + "," +
                      categoryID + ")") ;
        }
        String contentID = null;
        try
        {
            Content content = RepositoryUtils.parseContent(contentFile);
            content.setAuthor(userID);
            //保存内容到内容的存储分类节点下。
            String currentDate = PublicUtil.getCurDateTime("yyyyMMddHHmmss");
            content.setCreateTime(currentDate);
            content.setUpdateTime(currentDate);
            Node rootNode = Repository.getInstance().getNode(
                RepositoryConstants.ROOT_CONTENT_ID) ;
            contentID = rootNode.addNode(content) ;
            rootNode.saveNode() ;
            //添加引用节点到categoryID分类下
            ReferenceNode ref = new ReferenceNode();
            ref.setRefNodeID(contentID);
            ref.setSortID(0);
            Category category = (Category) Repository.getInstance().getNode(
                categoryID, RepositoryConstants.TYPE_CATEGORY) ;
            category.addNode(ref);
            category.saveNode();
        }
        catch(Exception e)
        {
            throw new BOException("ContentBO.addContent failed!", e);
        }
        return contentID;
    }

    /**
     * 修改资源内容
     * @param content Content，要修改的内容资源
     * @throws BOException
     */
    public void modContent(Content content) throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("modContent(" + content + ")") ;
        }
        String currentDate = PublicUtil.getCurDateTime("yyyyMMddHHmmss");
        content.setUpdateTime(currentDate);
        content.save();
    }

    /**
     * 删除一批内容资源
     * @param contentIDs String[]，要删除的内容资源id的array
     * @return String，被删除的内容的名称
     * @throws BOException
     */
    public String delContent(String[] contentIDs) throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("delContent(" + contentIDs + ")") ;
        }

        StringBuffer delContentNames = new StringBuffer();
        //开始执行删除操作
        for (int i = 0 ; (contentIDs != null) && (i < contentIDs.length) ; i++)
        {
            String contentID = contentIDs[i] ;
            //要删除所有对本内容资源的引用资源
            LOG.debug("found content category...") ;
            Searchor searchor = new Searchor() ;
            searchor.getParams().add(new SearchParam("refNodeID",
                                                     RepositoryConstants.OP_EQUAL,
                                                     contentID)) ;
            searchor.setIsRecursive(true) ;
            Category rootCagetory = ( Category ) Repository.getInstance()
                                                           .getNode(RepositoryConstants.ROOT_CATEGORY_ID,
                                                                    RepositoryConstants.TYPE_CATEGORY);
            List refList = rootCagetory.searchNodes(RepositoryConstants.
                                                    TYPE_REFERENCE,
                                                    searchor, null) ;
            for (int j = 0 ; j < refList.size() ; j++)
            {
                ReferenceNode ref = (ReferenceNode) refList.get(j) ;
                Category category = ( Category ) Repository.getInstance()
                                                           .getNode(ref.getParentID(),
                                                                    RepositoryConstants.TYPE_CATEGORY);
                GContent content = ( GContent ) Repository.getInstance()
                                                          .getNode(ref.getRefNodeID(),
                                                                   "nt:gcontent");
                if (content.getType().equals(RepositoryConstants.TYPE_COLORRING)
                    || content.getType().equals(RepositoryConstants.TYPE_NEWS)
                    || content.getType().equals(RepositoryConstants.TYPE_AUDIO))
                {
                    category.delNode(ref) ;
                    category.saveNode() ;
                }
                else
                {
                    GoodsBO.removeRefContentFromCategory(ref.getId());
                }
            }
            //还要真正删除内容资源
            Category rootSaveNode = new Category(RepositoryConstants.
                                                 ROOT_CONTENT_ID) ;
            GContent content = (GContent)Repository.getInstance().getNode(contentID,GContent.TYPE_CONTENT);
            if(i>0)
            {
                delContentNames.append(",");
            }
            delContentNames.append(content.getName()) ;
            rootSaveNode.delNode(content) ;
            rootSaveNode.saveNode() ;
            try
            {
                DataSyncDAO.getInstance().addContentIdHis(contentID, SyncDataConstant.DEL_CONTENT_TYPE_MM);
            }
            catch (DAOException e)
            {
                throw new BOException("内容下架时，新增下线应用记录表出错！", e);
            }
        }
        return delContentNames.toString();
    }

    /**
     * 找出内容所在的分类
     * @param contentID String，内容id
     * @return List，内容所在的分类的列表
     * @throws BOException
     */
    public List getContentCategory(String contentID) throws BOException
    {
        //找出内容所在的分类
        if (LOG.isDebugEnabled())
        {
            LOG.debug("getContentCategory(" + contentID + ")") ;
        }
        Searchor searchor = new Searchor() ;
        searchor.getParams().add(new SearchParam("refNodeID",
                                                 RepositoryConstants.OP_EQUAL,
                                                 contentID)) ;
        searchor.setIsRecursive(true);
        Category rootCagetory = (Category) Repository.getInstance().getNode(
                                      RepositoryConstants.ROOT_CATEGORY_ID,
                                      RepositoryConstants.TYPE_CATEGORY) ;
        List refList = rootCagetory.searchNodes(RepositoryConstants.TYPE_REFERENCE,
                                                searchor, null) ;
        List refCategoryList = new ArrayList();
        for(int i = 0; i < refList.size(); i++)
        {
            ReferenceNode ref = (ReferenceNode) refList.get(i);
            Category pCategory = (Category) rootCagetory.getNode(ref.getParentID()
            		               , RepositoryConstants.TYPE_CATEGORY, true);
            refCategoryList.add(pCategory);
        }
        return refCategoryList;
    }

}
