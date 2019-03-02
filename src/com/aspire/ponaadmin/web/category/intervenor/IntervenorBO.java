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
 * �˹���Ԥ�����߼�������
 * 
 * @author x_wangml
 * 
 */
public class IntervenorBO
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(IntervenorBO.class);

    private static IntervenorBO instance = new IntervenorBO();

    private IntervenorBO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static IntervenorBO getInstance()
    {

        return instance;
    }

    /**
     * �����������Ʒ��������б�
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
            // ����IntervenorDAO���в�ѯ
            return IntervenorDAO.getInstance().queryIntervenorVOList(name);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�����������Ʒ��������б�ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ��������id�õ���Ԥ����
     * 
     * @param id ����id
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
            // ����IntervenorDAO���в�ѯ
            return IntervenorDAO.getInstance().queryInternorVOById(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��������id�õ���Ԥ����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ��������id�õ������������б�
     * 
     * @param id ����id
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
            // ����IntervenorGcontentDAO���в�ѯ
            IntervenorGcontentDAO.getInstance()
                                 .queryGcontentListByIntervenorId(page, id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��������id�õ������������б�ʱ�������ݿ��쳣��");
        }
    }

    /**
     * �޸�������Ϣ
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
            // ����IntervenorDAO���в�ѯ
            return IntervenorDAO.getInstance().editInternorVO(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�޸�������Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * �����������������ݵ�����
     * 
     * @param id ����id
     * @param sortId ����id
     * @param sortNum ����ֵ
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
            // ����IntervenorGcontentDAO���в�ѯ
            IntervenorGcontentDAO.getInstance().editContentSort(id, sortValue);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�������������ݵ�����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ��������idɾ������
     * 
     * @param id ����id
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
            // ����IntervenorDAO���в�ѯ
            IntervenorDAO.getInstance().deleteInternorVOById(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��������idɾ������ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ��ָ��������ɾ��ָ������
     * 
     * @param id ����id
     * @param contentId ����id
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
            // ����IntervenorGcontentDAO���в�ѯ
            return IntervenorGcontentDAO.getInstance()
                                        .deleteGcontentById(id, contentId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ָ��������ɾ��ָ������ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ����������Ϣ
     * 
     * @param vo ������Ϣ
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
            // ����IntervenorDAO���в�ѯ
            return IntervenorDAO.getInstance().addInternorVO(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����������Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * �����������˹���Ԥ����
     * 
     * @param id ����id
     * @param contentId ����id
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
            // ����IntervenorGcontentDAO���в�ѯ
            IntervenorGcontentDAO.getInstance()
                                 .addGcontentToIntervenorId(id, contentId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�����������˹���Ԥ����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ��ѯ�����Ƿ�������˹���Ԥ����ʱ
     * 
     * @param id ����id
     * @param contentId ����id
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
            throw new BOException("��ѯ�����Ƿ�������˹���Ԥ����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * �õ�SEQ���Ե�����id
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
            // ����IntervenorDAO���в�ѯ
            return IntervenorDAO.getInstance().getInternorId();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�õ�SEQ���Ե�����idʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڲ�ѯ������Ϣ
     * 
     * @param id ����id
     * @param name ��������
     * @param spName �ṩ��ID
     * @param keywordsDesc ����keywordsDesc
     * @param contentId ����contentID
     * @param contentTag ����contentTag
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
     * �����������Ʋ鿴�Ƿ���ڴ�������
     * 
     * @param name ��������
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
            // ����IntervenorDAO���в�ѯ
            return IntervenorDAO.getInstance().hasInternorName(name);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�����������Ʋ鿴�Ƿ���ڴ�������ʱ�������ݿ��쳣��");
        }
    }

    /**
     * �ͷŹ�������
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
            // ����IntervenorDAO���в�ѯ
            IntervenorDAO.getInstance().overdueIntervenorId();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�ͷŹ�������ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ɾ��ʧЧ����
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
            throw new BOException("ɾ��ʧЧ����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڲ�ѯ�񵥶�Ӧ���������������
     * 
     * @param categoryId ��id
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
            // ����IntervenorDAO���в�ѯ
            tempList = IntervenorGcontentDAO.getInstance()
                                            .getIntervenorData(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ�񵥶�Ӧ���������������ʱ�������ݿ��쳣��");
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
     * ���ڵ��������ļ�������ָ��������
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

        // ���������ļ�����
        List tempList = paraseDataFile(dataFile);

        if (logger.isDebugEnabled())
        {
            logger.debug("����֮��ȥ��������Ϊ" + tempList.size());
        }

        // ɾ��ԭ�����е���������
        delAllContentById(id);

        // У�鵼���ļ��е����ݵĺϷ���
        List list = checkoutContentId(tempList);

        if (list.size() != 0)
        {
            String[] temp = new String[list.size()];
            
            for (int i = 0; i < list.size(); i++)
            {
                temp[i] = String.valueOf(list.get(i));
            }

            // д�����ݿ��д��뵱ǰ������
            addGcontentToIntervenorId(id, temp);
        }
    }

    /**
     * ����У�鵼���ļ��е����ݺϷ��ԣ�ͬʱ��������id�б�
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

                // ����ǿ�˵�����ݲ��Ϸ�
                if (!"".equals(temp))
                {
                    returnList.add(temp);
                }
                else
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("���Ϸ����ݣ�id=" + String.valueOf(list.get(i)));
                    }
                }
            }
            catch (DAOException e)
            {
                logger.error(e);
                throw new BOException("У�鵼���ļ��е����ݵĺϷ���ʱ�������ݿ��쳣��");
            }
        }

        return returnList;
    }

    /**
     * ɾ��ָ��������ȫ������
     * 
     * @param id ����id
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
            throw new BOException("ɾ��ָ��������ȫ������ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���������ļ�ȥ�صõ����ؼ���
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

            // ֻ�õ�һ��sheet
            int rows = sheets[0].getRows();

            if (logger.isDebugEnabled())
            {
                logger.debug("dataFile.rows==" + rows);
            }

            // ѭ��ÿһ��
            for (int j = 0; j < rows; j++)
            {
                // ֻ��һ��id��Ϣ
                String value = sheets[0].getCell(0, j).getContents().trim();

                // ��������д��ڴ�����
                if (list.contains(value))
                {
                    // ɾ��ԭ�����ڵ�����
                    list.remove(value);
                }

                // ������������id
                list.add(value);
            }

        }
        catch (Exception e)
        {
            logger.error("���������ļ������쳣,fineName:" + dataFile.getFileName(), e);
            throw new BOException("���������ļ������쳣", e);
        }
        finally
        {
            book.close();
        }

        return list;
    }
}
