
package com.aspire.dotcard.syncData.tactic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.DeviceVO;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.CategoryDeviceDAO;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * CMS����ͬ�����Թ���
 * 
 * @author x_liyouli
 * 
 */
public class TacticBO
{

    /**
     * ��־����
     */
    JLogger logger = LoggerFactory.getLogger(TacticBO.class);

    /**
     * ���һ��ͬ������
     * 
     * @param vo
     */
    public void addTactic(TacticVO vo) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.addTactic(). TacticVO=" + vo);
        }
        try
        {
            new TacticDAO().addTactic(vo);
        }
        catch (DAOException e)
        {
            throw new BOException("���һ��ͬ������ʧ�ܣ�", e);
        }
    }

    /**
     * ��ѯ����ͬ������
     * 
     * @param id
     * @return
     */
    public TacticVO queryByID(int id) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryByID(). ID=" + id);
        }
        try
        {
            return new TacticDAO().queryByID(id);
        }
        catch (DAOException e)
        {
            throw new BOException("��ѯ����ͬ������ʧ�ܣ�", e);
        }
    }

    /**
     * ��ѯ���е�ͬ�����ԣ�����Ҫ��ҳ��������޸�ʱ�併������
     * 
     * @return
     */
    public List queryByCategoryID(String categoryID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryByCategoryID(). categoryID="
                         + categoryID);
        }
        try
        {
            return new TacticDAO().queryByCategoryID(categoryID);
        }
        catch (DAOException e)
        {
            throw new BOException("��ѯ���е�ͬ������ʧ�ܣ�", e);
        }
    }

    /**
     * ��ѯ���е�ͬ������
     * 
     * @return
     */
    public List queryAll() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryAll().");
        }
        try
        {
            return new TacticDAO().queryAll();
        }
        catch (DAOException e)
        {
            throw new BOException("��ѯ���е�ͬ������ʧ�ܣ�", e);
        }
    }

    /**
     * ��ѯMOTO���е�ͬ������
     * 
     * @return
     */
    public List queryMOTOAll() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryAll().");
        }
        try
        {
            return new TacticDAO().queryMOTOAll();
        }
        catch (DAOException e)
        {
            throw new BOException("��ѯ���е�ͬ������ʧ�ܣ�", e);
        }
    }
    
    /**
     * ��ѯHTC���е�ͬ������
     * 
     * @return
     */
    public List queryHTCAll() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryHTCAll().");
        }
        try
        {
            return new TacticDAO().queryHTCAll();
        }
        catch (DAOException e)
        {
            throw new BOException("��ѯHTC���е�ͬ������ʧ�ܣ�", e);
        }
    }
    
    
    /**
     * ��ѯHTC���е�ͬ������
     * 
     * @return
     */
    public List queryAndroidAll() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryAndroidAll().");
        }
        try
        {
            return new TacticDAO().queryAndroidAll();
        }
        catch (DAOException e)
        {
            throw new BOException("��ѯHTC���е�ͬ������ʧ�ܣ�", e);
        }
    }

    /**
     * ��ѯ���㷺�����������̶�Ӧ�������б�
     * 
     * @return
     */
    public List queryChannelsCategoryAll() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.queryChannelsCategoryAll().");
        }
        try
        {
            return new TacticDAO().queryChannelsCategoryAll();
        }
        catch (DAOException e)
        {
            throw new BOException("��ѯ���㷺�����������̶�Ӧ�������б�ʧ�ܣ�", e);
        }
    }
    
    /**
     * �޸�һ��ͬ������
     * 
     * @param vo
     */
    public void modifyTactic(TacticVO vo) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.modifyTactic(). TacticVO=" + vo);
        }
        try
        {
            new TacticDAO().modifyTactic(vo);
        }
        catch (DAOException e)
        {
            throw new BOException("�޸�һ��ͬ������ʧ�ܣ�", e);
        }
    }

    /**
     * ɾ��һ��ͬ������
     * 
     * @param id
     */
    public void delTactic(int id) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.delTactic(). id=" + id);
        }
        try
        {
            new TacticDAO().delTactic(id);
        }
        catch (DAOException e)
        {
            throw new BOException("ɾ��һ��ͬ������ʧ�ܣ�", e);
        }
    }

    /**
     * ����ȫ��ִ��ͬ������
     * 
     * @param categoryID
     * @throws BOException
     */
    public void exeTactic(String categoryID) throws BOException
    {
        Category cate = CategoryBO.getInstance().getCategory(categoryID);
        List tacticList = queryByCategoryID(categoryID);
        List contList = null;
        List refList = null;

        // �¼���Ʒ
        downGood(categoryID);
        
        // ����ͬ�����Ի���ϼ���Ʒ�б�
        contList = categoryTactic(tacticList, cate);

        // ����ǻ��ͻ���ɸѡ�ϼ���Ʒ
        if (1 == cate.getDeviceCategory())
        {
            refList = deviceCategoryTactic(contList, cate);
        }
        else
        {
            refList = deviceCategoryTactic(contList);
        }
        
        // �ϼܲ���
        addGood(cate, refList);
    }

    /**
     * ����ͬ�����Ի���ϼ���Ʒ�б�
     * 
     * @param tacticList
     * @param cate
     */
    private List categoryTactic(List tacticList, Category cate)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacticBO.categoryTactic(). cid=" + cate.getId());
        }
        
        try
        {
            return new TacticDAO().categoryTactic(tacticList);
        }
        catch (DAOException e)
        {
            throw new BOException("����ͬ�����Ի���ϼ���Ʒ�б�ʧ�ܣ�", e);
        }
    }
    
    /**
     * ���ͻ���ɸѡ�ϼ���Ʒ
     * 
     * @param contList
     * @param cate
     */
    private List deviceCategoryTactic(List contList, Category cate)
                    throws BOException
    {
        List device = null;
        List retList = new ArrayList();
        try
        {
            // ����CategoryDeviceDAO���в�ѯ���л��ͻ����޶�����
            device = CategoryDeviceDAO.getInstance()
                                      .queryDeviceByCategoryId(cate.getId());
        }
        catch (DAOException e)
        {
            throw new BOException("�õ���ǰ���ܹ�������Щ������Ϣʱ�������ݿ��쳣��", e);
        }
        
        // ɸѡƥ��
        for (Iterator iter = contList.iterator(); iter.hasNext();)
        {
            GContent con = ( GContent ) iter.next();
            String conDeviceId = con.getFulldeviceID();
            
            // ����ͻ��ܻ��ͽ���ƥ��
            for (Iterator iterator = device.iterator(); iterator.hasNext();)
            {
                DeviceVO deviceVO = ( DeviceVO ) iterator.next();
                String categoryDevice = "{"+deviceVO.getDeviceId()+"}";
                
                // �����һ������ƥ�䣬�����ϼ��б���ʼ��һ��
                if(conDeviceId.indexOf(categoryDevice) >= 0)
                {
                    retList.add(con.getId());
                    break;
                }
            }
        }
        
        return retList;
    }
    
    /**
     * ת������
     * 
     * @param contList
     * @param cate
     */
    private List deviceCategoryTactic(List contList)
                    throws BOException
    {
        List retList = new ArrayList();

        for (Iterator iter = contList.iterator(); iter.hasNext();)
        {
            GContent con = ( GContent ) iter.next();
            retList.add(con.getId());
        }

        return retList;
    }



    /**
     * �ϼ���Ʒ
     * 
     * @param cate
     * @param contList
     * @throws BOException
     */
    private void addGood(Category cate, List contList) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("��ʼ�ϼ���Ʒ��");
        }
        
        // ��ʼ����ֵ
        int sortId = 1000;
        
        for (int i = 0; i < contList.size(); i++)
        {
            String contId = ( String ) contList.get(i);
            CategoryTools.addGood(cate,
                                  contId,
                                  sortId--,
                                  RepositoryConstants.VARIATION_NEW,
                                  true,
                                  null);

        }
    }
    
    /**
     * ��ָ�������µ���Ʒ�¼�
     * @param cId
     * @throws BOException
     */
    private void downGood(String cId) throws BOException
    {
        logger.info("�¼���Ʒ");
        CategoryTools.clearCateGoods(cId, false);
    }
}
