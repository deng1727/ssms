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
 * <p>������Դ��BO�ӿ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class ContentBO
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ContentBO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static ContentBO instance = new ContentBO();

    /**
     * ���췽������singletonģʽ����
     */
    private ContentBO ()
    {
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    public static ContentBO getInstance()
    {
        return instance;
    }

    /**
     * ����һ��������Դ�����ŵ�ĳ��������
     *
     * @param categoryID String��Ŀ������id
     * @param content Content��Ҫ��ӵ�����
     * @throws BOException
     * @return String
     */
    public String addContent(String categoryID, Content content) throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("addContent(" + categoryID + "," + content + ")") ;
        }
        //���ô���ʱ����޸�ʱ��
        String currentDate = PublicUtil.getCurDateTime("yyyyMMddHHmmss");
        content.setCreateTime(currentDate);
        content.setUpdateTime(currentDate);
        //����������Դ
        Node rootNode = Repository.getInstance().getNode(
            RepositoryConstants.ROOT_CONTENT_ID) ;
        String nodeID = rootNode.addNode(content) ;
        rootNode.saveNode() ;
        //�������ýڵ㵽Ŀ������£������������δ��������ӣ��Ͳ���
        if(!categoryID.equals(RepositoryConstants.ROOT_CATEGORY_ID))
        {
            //���ѡ��Ĳ��Ǹ������δ���࣬��Ҫ��ӵ�Ŀ�������
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
     * ��ҳ���ϴ�һ���ļ�����Դ��Ĺ��÷���
     * @param contentFile FormFile��struts�ϴ���FormFile����
     * @param userID String�������û����û��ʺ�
     * @param categoryID String��Ҫ����ķ���id��һ������Ѷ�����š����ĸ����࣬��RepositoryConstants��
     * @return String���������Դ���ݵ�ID
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
            //�������ݵ����ݵĴ洢����ڵ��¡�
            String currentDate = PublicUtil.getCurDateTime("yyyyMMddHHmmss");
            content.setCreateTime(currentDate);
            content.setUpdateTime(currentDate);
            Node rootNode = Repository.getInstance().getNode(
                RepositoryConstants.ROOT_CONTENT_ID) ;
            contentID = rootNode.addNode(content) ;
            rootNode.saveNode() ;
            //������ýڵ㵽categoryID������
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
     * �޸���Դ����
     * @param content Content��Ҫ�޸ĵ�������Դ
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
     * ɾ��һ��������Դ
     * @param contentIDs String[]��Ҫɾ����������Դid��array
     * @return String����ɾ�������ݵ�����
     * @throws BOException
     */
    public String delContent(String[] contentIDs) throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("delContent(" + contentIDs + ")") ;
        }

        StringBuffer delContentNames = new StringBuffer();
        //��ʼִ��ɾ������
        for (int i = 0 ; (contentIDs != null) && (i < contentIDs.length) ; i++)
        {
            String contentID = contentIDs[i] ;
            //Ҫɾ�����жԱ�������Դ��������Դ
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
            //��Ҫ����ɾ��������Դ
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
                throw new BOException("�����¼�ʱ����������Ӧ�ü�¼�����", e);
            }
        }
        return delContentNames.toString();
    }

    /**
     * �ҳ��������ڵķ���
     * @param contentID String������id
     * @return List���������ڵķ�����б�
     * @throws BOException
     */
    public List getContentCategory(String contentID) throws BOException
    {
        //�ҳ��������ڵķ���
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
