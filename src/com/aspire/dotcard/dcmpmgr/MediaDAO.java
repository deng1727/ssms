
package com.aspire.dotcard.dcmpmgr;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GNews;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * ͬ��DCMPʱ���Զ�ý���ͷ���Ĳ�����
 * 
 * @author zhangwei
 * 
 */
public class MediaDAO
{

    private JLogger logger = LoggerFactory.getLogger(MediaDAO.class);

    private static JLogger syncDataLog = LoggerFactory.getLogger("FTP-DCMP-Log");

    static MediaDAO instance = new MediaDAO();

    /**
     * ����ģʽ
     * 
     * @return
     */
    public static MediaDAO getInstance()
    {

        return instance;
    }

    /**
     * ��ȡý���б�
     * 
     * @return
     */
    public List getAllMedias()
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getAllMedias() is start ...");
        }

        List list = new LinkedList();
        List refList;
        try
        {
            /*
             * Searchor searchor = new Searchor() ; searchor.getParams().add(new
             * SearchParam("id", RepositoryConstants.OP_LIKE, "dcmp")) ;
             * searchor.setIsRecursive(true) ;
             */
            Category rootCagetory = ( Category ) Repository.getInstance()
                                                           .getNode(RepositoryConstants.ROOT_CONTENT_ID,
                                                                    RepositoryConstants.TYPE_CATEGORY);
            refList = rootCagetory.searchNodes(GNews.TYPE_NEWS, null, null);
            for (int i = 0; i < refList.size(); i++)
            {
                GNews gnews = ( GNews ) refList.get(i);
                MediaVO vo = new MediaVO();
                vo.setId(gnews.getId());
                vo.setName(gnews.getName());
                vo.setIconUrl(gnews.getIconUrl());
                vo.setUrl(gnews.getMediaUrl());
                list.add(vo);
            }
        }
        catch (BOException e)
        {
            logger.error(e);
            syncDataLog.error("ͬ��dcmp�Ĺ����У���ѯ������Ѷ��Ϣ����");
            syncDataLog.error(e);
            return null;

        }

        return list;
    }

    /**
     * ����ý����Ϣ
     * 
     * @param vo ý����Ϣ��vo
     * @return int �ɹ�������Ѷ���ݵĸ���
     * @throws DAOException
     */
    public int updateMedia(List voList)
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateMedio()");
        }
        int count = 0;
        for (int i = 0; i < voList.size(); i++)
        {
            MediaVO vo = ( MediaVO ) voList.get(i);

            GNews gnews=null;
            try
            {
                gnews = ( GNews ) Repository.getInstance()
                                                  .getNode(vo.getId(),
                                                      GNews.TYPE_NEWS);
                if(gnews!=null)    
                {
                    gnews.setName(vo.getName());
                    gnews.setIconUrl(vo.getIconUrl());
                    gnews.setMediaUrl(vo.getUrl());
                    gnews.save();
                    count++;  
                }else
                {
                    throw new BOException("�޷���ȡý��idΪ��"+vo.getId()+"����Ѷ��Ϣ��");
                }
               
            }
            catch (BOException e)
            {
                syncDataLog.error("ͬ��DCMP����ʱ������ý����Ϣ�쳣,ý��idΪ��"+vo.getId());
                logger.error(e);
                syncDataLog.error(e);
            }

        }
        return count;
    }

    /**
     * ����ͷ����Ϣ
     * 
     * @param voList
     * @return int �ɹ����µ�ͷ���ĸ���
     */
    public int updateHot(List voList)
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateHot()");
        }
        int count = 0;

        for (int i = 0; i < voList.size(); i++)
        {
            HotVO vo = ( HotVO ) voList.get(i);
            
            try
            {
                GNews gnews = ( GNews ) Repository.getInstance()
                                                  .getNode(vo.getMediaId(),
                                                           GNews.TYPE_NEWS);
                if (gnews != null)
                {
                    gnews.setContentTag(vo.getId());
                    gnews.setTitle(vo.getTitle());
                    gnews.setImageUrl(vo.getImageUrl());
                    gnews.setIntroduction(PublicUtil.formatByLen(vo.getContent(),1997,"..."));
                    gnews.setMarketDate(vo.getPublishDate());
                    gnews.setHotUrl(vo.getUrl());
                    gnews.save();
                    count++;
                }else
                {
                    new BOException("�޷���ȡ��ѶidΪ��"+vo.getId()+"ý����Ϣ��");
                }
                
            }
            catch (BOException e)
            {
                
               
                syncDataLog.error("ͬ��DCMP����ʱ������ͷ����Ϣ�쳣,��ѶidΪ��"+vo.getMediaId()+".ͷ����ϢΪ:"+vo);
                logger.error(e);
                syncDataLog.error(e);
            }

        }
        return count;

    }

    /**
     * ɾ��ý���ļ����������¼ܲ���
     * 
     * @param voList
     * @return �ɹ�ɾ����ý�����
     */
    public int deleteMedia(List voList)
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("deleteMedia()");
        }
        int count = 0;
        for (int i = 0; i < voList.size(); i++)
        {
            MediaVO vo = ( MediaVO ) voList.get(i);
            try
            {
                // ��ɾ���Ľڵ�
                GNews gnews = ( GNews ) Repository.getInstance()
                                                  .getNode(vo.getId(),
                                                           GNews.TYPE_NEWS);
                if(gnews==null)
                {
                    throw new BOException("�޷���ȡ��ѶidΪ��"+vo.getId()+"ý����Ϣ��");
                }

                Searchor searchor = new Searchor();
                searchor.getParams()
                        .add(new SearchParam("refNodeID",
                                             RepositoryConstants.OP_EQUAL,
                                             vo.getId()));
                searchor.setIsRecursive(true);
                Category rootCagetory = ( Category ) Repository.getInstance()
                                                               .getNode(RepositoryConstants.ROOT_CATEGORY_ID,
                                                                        RepositoryConstants.TYPE_CATEGORY);
                List refList = rootCagetory.searchNodes(RepositoryConstants.TYPE_REFERENCE,
                                                        searchor,
                                                        null);
                for (int j = 0; j < refList.size(); j++)
                {
                    ReferenceNode ref = ( ReferenceNode ) refList.get(j);
                    Category pCategory = new Category(ref.getParentID());
                    pCategory.delNode(ref);
                    pCategory.saveNode();
                }
                // ��Ҫ����ɾ��������Դ
                Category rootSaveNode = new Category(RepositoryConstants.ROOT_CONTENT_ID);
                rootSaveNode.delNode(gnews);
                rootSaveNode.saveNode();

                count++;
                // gnews.
            }
            catch (BOException e)
            {            
                syncDataLog.error("ͬ��DCMP����ʱ��ɾ����Ѷ����ʱ�쳣,��ѶidΪ��"+vo.getId());
                logger.error(e);
                syncDataLog.error(e);
            }

        }
        return count;

    }

    /**
     * ����ý���ļ����������ϼܲ���
     * 
     * @param voList
     * @return �ɹ�������Ѷ���ݵĸ���
     */

    public int addMedia(List voList)
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateMedio()");
        }
        int count = 0;
        Node rootNode;
        try
        {
            rootNode = Repository.getInstance()
                                 .getNode(RepositoryConstants.ROOT_CONTENT_ID);
        }
        catch (BOException e1)
        {
            logger.error(e1);
            syncDataLog.error("�޷���ȡ���ݸ�����" + RepositoryConstants.ROOT_CONTENT_ID);
            syncDataLog.error(e1);
            return 0;

        }
        for (int i = 0; i < voList.size(); i++)
        {
            MediaVO vo = ( MediaVO ) voList.get(i);
            try
            {
                GNews gnews = new GNews();
                gnews.setId(vo.getId());
                gnews.setName(vo.getName());
                gnews.setIconUrl(vo.getIconUrl());
                gnews.setMediaUrl(vo.getUrl());
                gnews.setContentID(vo.getId().substring(4));
                rootNode.addNode(gnews);
                rootNode.saveNode();
                addContent(vo.getId());

                count++;

            }
            catch (BOException e)
            {
                logger.error(e);
                syncDataLog.error("ͬ��DCMP����ʱ��������Ѷ����ʱ�쳣,��ѶidΪ��"+vo.getId());
                syncDataLog.error(e);
            }

        }
        return count;

    }

    /**
     * ִ���ϼܲ���
     * 
     * @param mediaId ���ϼܵ���Ѷid
     */
    private void addContent(String mediaId) throws BOException
    {

        Category category = ( Category ) Repository.getInstance()
                                                   .getNode(RepositoryConstants.ROOT_CATEGORY_GNEWS_ID,
                                                            RepositoryConstants.TYPE_CATEGORY);

        // �ŵ�Ŀ�������
        ReferenceNode refNodeRoot = new ReferenceNode();
        refNodeRoot.setRefNodeID(mediaId);
        refNodeRoot.setSortID(0);
        refNodeRoot.setGoodsID(PublicUtil.rPad(category.getCategoryID() + "|"
                                               + mediaId + "|", 39, "0"));
        refNodeRoot.setCategoryID(category.getCategoryID());
        refNodeRoot.setLoadDate(DateUtil.formatDate(new Date(),
                                                    "yyyy-MM-dd HH:mm:ss"));
        category.addNode(refNodeRoot);
        category.saveNode();

    }
}
