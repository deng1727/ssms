/*
 * 
 */

package com.aspire.ponaadmin.web.category.intervenor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.syncData.bo.DataSyncBO;
import com.aspire.ponaadmin.web.category.intervenor.category.IntervenorCategoryBO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.GoodsChanegHisBO;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.ReferenceTools;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author x_wangml
 * 
 */
public class IntervenorTools
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(IntervenorTools.class);

    private static IntervenorTools instance = new IntervenorTools();

    private TaskRunner updateTaskRunner;

    private StringBuffer errorString = new StringBuffer();
    
    private int errorNum = 0;
    
    private IntervenorTools()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static IntervenorTools getInstance()
    {

        return instance;
    }

    /**
     * ɾ��ʧЧ����
     * 
     * @throws BOException
     */
    private void delInvalidationContent() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ɾ��ʧЧ���� is start...");
        }

        try
        {
            IntervenorBO.getInstance().delInvalidationContent();
        }
        catch (BOException e)
        {
            logger.error(e);

            throw new BOException("ɾ��ʧЧ����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * �ͷŹ�������
     * 
     * @throws BOException
     */
    private void overdueIntervenor() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�ͷŹ������� is start...");
        }

        try
        {
            IntervenorBO.getInstance().overdueIntervenor();
        }
        catch (BOException e)
        {
            logger.error(e);

            throw new BOException("�ͷŹ�������ʱ�������ݿ��쳣��");
        }
    }

    /**
     * �õ����б��˹���Ԥ�İ�
     * 
     * @return
     * @throws BOException
     */
    private List getIntervenorCategory() throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("��ѯ���б��˹���Ԥ�İ� is start...");
        }

        try
        {
            return IntervenorCategoryBO.getInstance().getIntervenorCategory();
        }
        catch (BOException e)
        {
            logger.error(e);

            throw new BOException("�õ����б��˹���Ԥ�İ�ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڲ�ѯ��ǰ�񵥵��ڴ��������
     * 
     * @param categoryId ��id
     * @return
     * @throws BOException
     */
    private List getGoodsByCategory(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("��ѯ��ǰ�񵥵��ڴ�������� is start...");
        }

        Category childCategory = ( Category ) Repository.getInstance()
                                                        .getNode(categoryId,
                                                                 RepositoryConstants.TYPE_CATEGORY);

        Taxis taxis = new Taxis();
        taxis.getParams()
             .add(new TaxisParam("sortID", RepositoryConstants.ORDER_TYPE_DESC));
        List goodsList = childCategory.searchNodes(RepositoryConstants.TYPE_REFERENCE,
                                                   null,
                                                   taxis);

        List resultList = new ArrayList();
        for (int i = 0; i < goodsList.size(); i++)
        {
            ReferenceNode refnode = ( ReferenceNode ) goodsList.get(i);
            GoodChangedVO vo = new GoodChangedVO();
            vo.setId(refnode.getId());
            vo.setRefNodeId(refnode.getRefNodeID());
            vo.setSortId(i);// ����˳��ȷ������ֵ����ҪΪ���Ժ���� variation��ֵ
            vo.setStatus(GoodChangedVO.Status_Existed);
            resultList.add(vo);
        }
        return resultList;
    }

    /**
     * ���ڲ�ѯ�񵥶�Ӧ���������������
     * 
     * @param categoryId ��id
     * @return
     * @throws BOException
     */
    private List getIntervenorData(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("��ѯ�񵥶�Ӧ��������������� is start...");
        }

        try
        {
            return IntervenorBO.getInstance().getIntervenorData(categoryId);
        }
        catch (BOException e)
        {
            logger.error(e);

            throw new BOException("��ѯ�񵥶�Ӧ���������������ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ����ָ���İ�����Ϊ�����б�
     * 
     * @param retList ��Ԥ���б�
     * @param categoryId ��id
     * @param isSyn �Ƿ��ǽ�������Ӧ��
     */
    private void editCategoryData(List retList, String categoryId, String isSyn)
                    throws BOException
    {

        int sortid = 1000;
        Category cate = ( Category ) Repository.getInstance()
                                               .getNode(categoryId,
                                                        RepositoryConstants.TYPE_CATEGORY);

        for (int i = 0; i < retList.size(); i++)
        {
            GoodChangedVO vo = ( GoodChangedVO ) retList.get(i);

            try
            {
                if (vo.getStatus() == GoodChangedVO.Status_ADD)
                {
                    CategoryTools.addGood(cate,
                                          vo.getRefNodeId(),
                                          sortid--,
                                          RepositoryConstants.VARIATION_NEW,
                                          true,null);
                    
                    // ����ǽ�������Ӧ��
                    if(RepositoryConstants.SYN_ACTION_ADD.equals(isSyn))
                    {
                        // �����������Ӧ�ñ�
                        GoodsChanegHisBO.getInstance().addAddHisToList(cate, vo.getRefNodeId());
                    }
                }
                else if (vo.getStatus() == GoodChangedVO.Status_Existed)
                {
                    ReferenceTools.getInstance()
                                  .updateSortid(vo.getId(),
                                                sortid--,
                                                vo.getVariation(),null);
                }
                else if (vo.getStatus() == GoodChangedVO.Status_DEL)
                {
                    // Ŀǰ�������ɾ��ҵ��

                }
            }
            catch (BOException e)
            {
                logger.error("������Ԥ�񵥳��� categoryId=" + cate.getId(), e);
            }

        }
    }

    /**
     * ���ڱȶ԰������������������ݽ��
     * 
     * @param categoryDate ��������Ϣ
     * @param intervenorDate ����������Ϣ
     * @return
     */
    public List intervenorContrast(List goodsList, List intervenorList)
    {
        Map googdsBackup = new HashMap();
        int maxIntervenorSize = 0;// ���������ж����ڰ������λ��
        // ����һ����Ʒ����Ϣ��Ϊ���Ժ�ȥ�������������д��ڵ���Ʒ���Լ�ͳ��variation��ֵ
        for (int i = 0; i < goodsList.size(); i++)
        {
            GoodChangedVO changedVO = ( GoodChangedVO ) goodsList.get(i);
            googdsBackup.put(changedVO.getRefNodeId(), changedVO);
        }
        Map tempMap = new HashMap();// ȥ����������ͬ���󣬴����ȼ��Ӹߵ��׹���
        for (int i = 0; i < intervenorList.size(); i++)
        {
            IntervenorVO vo = ( IntervenorVO ) intervenorList.get(i);

            int startid = vo.getStartSortId();

            if (IntervenorVO.TOP == startid || IntervenorVO.END == startid)// ������ö���Ĭ�ϴӰ񵥵�һ��λ�ò���
            {
                startid = 1;
            }

            if (startid + vo.getContentList().size() > maxIntervenorSize)// Ԥ�������Ĵ�С
            {
                maxIntervenorSize = startid + vo.getContentList().size();
            }

            List contentList = vo.getContentList();
            List turnedContentList = new ArrayList();
            // ��idתΪGoodChangedVO�ĸ�ʽ
            for (int j = 0; j < contentList.size(); j++)
            {
                String id = ( String ) contentList.get(j);
                Object value = tempMap.get(id);
                if (value == null)
                {
                    tempMap.put(id, "");
                    Object isExistedgoosid = googdsBackup.get(id);
                    GoodChangedVO goodvo = null;
                    if (isExistedgoosid == null)// �жϵ�ǰid�Ƿ�����ڵ�ǰ�İ��С���Ҫ��Ϊ�˺�����ϼܵ���Ҫ
                    {
                        goodvo = new GoodChangedVO();
                        goodvo.setRefNodeId(id);
                        goodvo.setStatus(GoodChangedVO.Status_ADD);
                    }
                    else
                    {
                        goodvo = ( GoodChangedVO ) isExistedgoosid;
                    }
                    turnedContentList.add(goodvo);
                }
            }
            vo.setContentList(turnedContentList);//
        }

        // ȥ�������������д��ڵ���Ʒ
        for (int i = 0; i < goodsList.size(); i++)
        {
            GoodChangedVO changedVO = ( GoodChangedVO ) goodsList.get(i);
            // googdsBackup.put(changedVO.getRefNodeId(),changedVO);
            if (tempMap.containsKey(changedVO.getRefNodeId()))
            {
                goodsList.remove(i);
                i--;
            }
        }
        List finalList = new ArrayList(maxIntervenorSize);// ��ʼ���б�
        for (int i = 0; i < maxIntervenorSize; i++)
        {
            finalList.add("");
        }
        // ���հ����ɣ��ȷ��������ڵ���Ʒ�������ȼ��ȷţ������ȼ�����
        // �����Ƚ��п��ܴ��ҵ����ȼ�������˳��
        // List topList=new ArrayList();
        List endList = new ArrayList();

        int start = 0;// ���������������ʼλ��
        for (int i = intervenorList.size() - 1; i >= 0; i--)
        {
            IntervenorVO vo = ( IntervenorVO ) intervenorList.get(i);
            start = vo.getStartSortId() - 1;
            List contentList = vo.getContentList();
            if (vo.getStartSortId() == IntervenorVO.TOP)
            {
                // topList.addAll(contentList);
                start = 0;// �ӵ�һλ��ʼ
            }
            else if (vo.getStartSortId() == IntervenorVO.END)
            {
                endList.addAll(contentList);
                continue;
            }
            for (int j = 0; j < contentList.size(); j++)// �ϼ�����������Ʒ
            {
                Object temp = finalList.get(start);
                if (temp.equals(""))
                {
                    finalList.set(start, contentList.get(j));
                    start++;//
                }
                else
                // ���ھ�������
                {
                    finalList.addAll(start,
                                     contentList.subList(j, contentList.size()));
                    break;//
                }
            }
        }
        // �Ѱ񵥵�����Ʒ���뵽������Ʒ������϶�С�
        start = 0;
        int finallListSize = finalList.size();
        for (int i = 0; i < goodsList.size(); i++)
        {
            if (start < finallListSize)
            {
                for (int j = start; j < finallListSize; j++)
                {
                    Object temp = finalList.get(j);
                    if (temp.equals(""))
                    {
                        finalList.set(j, goodsList.get(i));
                        start = j + 1;// ��һ��ֱ�Ӵ�j+1��ʼ
                        break;
                    }
                }
            }
            else
            {
                // ��������������Ʒ��λ�ú�ֱ�ӷ��뼴��
                finalList.addAll(goodsList.subList(i, goodsList.size()));
                break;
            }

        }
        // ȥ�������б��п��ܴ��ڿ�Ԫ�ء�
        for (int i = 0; i < finalList.size(); i++)
        {
            Object obj = finalList.get(i);
            if (obj instanceof String)
            {
                finalList.remove(i);
                i--;
            }
        }
        // �����õص�Ԫ��
        finalList.addAll(endList);
        // ���ñ��ֵ��
        for (int i = 0; i < finalList.size(); i++)
        {
            GoodChangedVO vo = ( GoodChangedVO ) finalList.get(i);
            GoodChangedVO oldvo = ( GoodChangedVO ) googdsBackup.get(vo.getRefNodeId());
            if (oldvo == null)// ��ʾ����
            {
                vo.setVariation(RepositoryConstants.VARIATION_NEW);
            }
            else
            {
                vo.setVariation(oldvo.getSortId() - i);
            }
        }
        return finalList;
    }

    /**
     * ִ�е�����������Ԥ
     * 
     * @param categoryId ��id
     * @param isSyn �Ƿ��ǽ�������Ӧ��
     * @return
     * @throws BOException
     * @throws BOException
     */
    public void intervenorCategory(String categoryId, String isSyn) throws BOException
    {

        // ���ڴ洢ԭ������������Ϣ
        List goodsList = null;

        try
        {
            goodsList = this.getGoodsByCategory(categoryId);
        }
        catch (BOException e)
        {
            synchronized (this)
            {
                errorString.append(" ����id=" + categoryId + "���˹���Ԥִ��ʧ��<br>");
                errorNum++;
            }
            throw new BOException("�鿴�洢ԭ����������ʱ�������ݿ��쳣��", e);
        }

        // ���ڴ洢����������������Ϣ
        List intervenorList;

        try
        {
            intervenorList = this.getIntervenorData(categoryId);
        }
        catch (BOException e)
        {
            synchronized (this)
            {
                errorString.append(" ����id=" + categoryId + "���˹���Ԥִ��ʧ��<br>");
                errorNum++;
            }
            throw new BOException("��ѯ�񵥶�Ӧ���������������ʱ�������ݿ��쳣��", e);
        }

        // ����������Ԥ֮��������������
        List retList = this.intervenorContrast(goodsList, intervenorList);

        // �����Ԥ�����ݿ��а�����������Ϣ
        try
        {
            this.editCategoryData(retList, categoryId, isSyn);
        }
        catch (BOException e)
        {
            synchronized (this)
            {
                errorString.append(" ����id=" + categoryId + "���˹���Ԥִ��ʧ��");
                errorNum++;
            }
            throw new BOException("�����Ԥ�����ݿ��а���������ʱ�����쳣��", e);
        }
    }
    
    /**
     * ִ��ȫ����������Ԥ
     * 
     * @throws BOException
     */
    public void intervenorCategoryAll()
    {

        Date startDate = new Date();
        errorString = new StringBuffer();
        errorNum = 0;

        logger.info("�˹���Ԥ��ʼ��startTime:"
                    + PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));

        // �ȵ����ͷŹ�������
        try
        {
            this.overdueIntervenor();
        }
        catch (BOException e)
        {
            return;
        }

        // �ȵ����ͷŹ�������
        //try
        //{
        //    this.delInvalidationContent();
        //}
        //catch (BOException e)
        //{
        //   return;
        //}

        // �õ����б��˹���Ԥ�İ�
        List categoryList;

        try
        {
            categoryList = getIntervenorCategory();
        }
        catch (BOException e1)
        {
            return;
        }

        // �˹���Ԥ���ͬ�����߳�����
        updateTaskRunner = new TaskRunner(IntervenorConfig.INTERVENORMAXNUM, 0);

        // �ֱ�ִ�а�
        for (Iterator iter = categoryList.iterator(); iter.hasNext();)
        {
            String categoryId = ( String ) iter.next();

            // ִ�е����񵥸�Ԥ �첽����
            ReflectedTask task = new ReflectedTask(this,
                                                   "intervenorCategory",
                                                   new Object[] { categoryId, RepositoryConstants.SYN_HIS_NO },
                                                   new Class[] { String.class, String.class });
            // ������ӵ���������
            updateTaskRunner.addTask(task);
        }

        updateTaskRunner.waitToFinished();
        updateTaskRunner.stop();

        logger.info("�˹���Ԥ����������Ԥ" + categoryList.size() + "�����ܡ�");

        Date endDate = new Date();
        StringBuffer sb = new StringBuffer();
        sb.append("��ʼʱ�䣺");
        sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append(",����ʱ�䣺");
        sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append("��<p>�˹���Ԥ����������Ԥ" + categoryList.size() + "�����ܡ�<br>");
        sb.append("��<p>ʧ��" + errorNum + "�����ܡ�<br>");
        sb.append("��<p>�ɹ�" + (categoryList.size() - errorNum) + "�����ܡ�<br>");
        if (!"".equals(errorString.toString()))
        {
            sb.append("��<p>��������������" + errorString.toString() + "<br>");
        }
        else
        {
            sb.append("��<p>���������ɹ�<br>");
        }
        Mail.sendMail("�˹���Ԥ�ɹ�", sb.toString(), MailConfig.getInstance()
                                                         .getMailToArray());
        //���ͽ������
        DataSyncBO.getInstance().sendPhoneMsg("�˹���Ԥ",this.getPhoneMsg(categoryList));
    }
    /**
     * ���Ͷ�Ϣ֪ͨ��Ϣ
     * @param categoryList �ܵĸ�Ԥ��
     * @return ��Ϣ��Ϣ
     */
    private String getPhoneMsg( List categoryList){
        StringBuffer sb = new StringBuffer();
        sb.append("�˹���Ԥ����������Ԥ" );
        sb.append(categoryList.size() );
        sb.append("�����ܡ�"); 
        sb.append("��ʧ��" );
        sb.append(errorNum); 
        sb.append("�����ܡ�");
        sb.append("���ɹ�" );
        sb.append((categoryList.size() - errorNum)); 
        sb.append("�����ܡ�");
        return sb.toString();
    }
}
