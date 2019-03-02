/*
 * �ļ�����CategoryDeviceBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.ponaadmin.web.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.dao.ReadCategoryDAO;
import com.aspire.dotcard.baseread.vo.ReadCategoryVO;
import com.aspire.dotcard.gcontent.CityVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.book.dao.BookCategoryDAO;
import com.aspire.ponaadmin.web.book.vo.BookCategoryVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicCategoryDAO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicCategoryVO;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class CategoryCityBO
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryCityBO.class);

    private static CategoryCityBO bo = new CategoryCityBO();

    private CategoryCityBO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CategoryCityBO getInstance()
    {

        return bo;
    }

    /**
     * ���ݵ�����Ϣ���õ������б�
     * 
     * @param city ������Ϣ
     * @param categoryID ����id
     * @param pCategoryID ������id
     * @return
     * @throws BOException
     */
    public void queryCityList(PageResult page, CityVO city, String categoryID,
                              String pCategoryID) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.queryCityList(" + city.toString()
                         + ") is start...");
        }

        Category pCategory = null;
        List cityId = null;

        try
        {
            if (categoryID.equals(RepositoryConstants.ROOT_CATEGORY_ID))
            {
                pCategory = ( Category ) Repository.getInstance()
                                                   .getNode(RepositoryConstants.ROOT_CATEGORY_ID,
                                                            RepositoryConstants.TYPE_CATEGORY);
            }
            else
            {
                pCategory = ( Category ) Repository.getInstance()
                                                   .getNode(pCategoryID,
                                                            RepositoryConstants.TYPE_CATEGORY);
            }

            cityId = queryListByCityId(pCategory.getCityId());

            // ����CategoryDeviceDAO���в�ѯ
            CategoryCityDAO.getInstance().queryCityList(page, city, cityId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ�����Ϣ���õ������б�ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ݻ����������id��ϡ��õ�����ҳ����ʾ��Ϣ
     * 
     * @param cityids
     * @return
     */
    public String queryCityByCityIds(String cityids) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.queryCityByCityIds() is start...");
        }

        String[] cityid = cityids.split(",");

        for (int i = 0; i < cityid.length; i++)
        {
            cityid[i] = cityid[i].substring(1, cityid[i].length() - 1);
        }

        // ���Ϊ0000����Ϊȫ��ͨ��
        if (cityid.length == 1 && Constants.ALL_CITY_PLATFORM.equals(cityid[0]))
        {
            return "ȫ��ͨ��.";
        }

        try
        {
            // ����CategoryDeviceDAO���в�ѯ
            return CategoryCityDAO.getInstance().queryCityListByCityId(cityid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ����������id��ϡ��õ�����ҳ����ʾ��Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ݻ������ݿ��д洢����id�����ʽ���õ�����ҳ�����ص�id��Ϣ
     * 
     * @param cityids
     * @return
     */
    public List queryListByCityId(String cityids)
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.queryListByCityId() is start...");
        }

        List list = new ArrayList();

        String[] cityid = cityids.split(",");

        for (int i = 0; i < cityid.length; i++)
        {
            String temp = cityid[i].substring(1, cityid[i].length() - 1);

            // �����0000��������
            if (Constants.ALL_CITY_PLATFORM.equals(temp))
            {
                continue;
            }

            list.add(temp);
        }

        return list;
    }

    /**
     * ���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����
     * 
     * @param categoryId ��ǰ����id
     * @param pCitys ��ǰ���ܵ�����Ϣ
     * @return
     * @throws BOException
     */
    public void isHasCity(String categoryId, String[] pCitys)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.isHasCity() is start...");
        }

        List citys = null;
        List tempCityl = null;
        Set zCitys = new TreeSet();
        boolean isHas = true;

        try
        {
            // ���ݸ�����id�õ��ӻ�������������Ϣ����
            citys = CategoryCityDAO.getInstance()
                                   .queryCityListByPcategoryId(categoryId);

            for (Iterator iter = citys.iterator(); iter.hasNext();)
            {
                String tempCity = ( String ) iter.next();

                // �����ȫ��ͨ�á�ֱ���д���Ϊ�ܽ���У��ġ����Ǳ��޸ĳɷ�ȫ������Ϣ
                if (Constants.ALL_CITY_DATA.equals(tempCity))
                {
                    throw new BOException("���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����ʱ�������ݿ��쳣��",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }

                // �����ݿ��д洢״̬��Ϊ����
                tempCityl = this.queryListByCityId(tempCity);

                // ȫ������set�С�ȥ��
                zCitys.addAll(tempCityl);
            }

            // ����set���ӻ������е�����Ϣ������ǰ�޸ĺ󸸻��ܵ�����Ϣ�����Ƿ�����
            for (Iterator iter = zCitys.iterator(); iter.hasNext();)
            {
                String temp = ( String ) iter.next();
                isHas = false;

                // ���������ܵ�����Ϣ
                for (int i = 0; i < pCitys.length; i++)
                {
                    String ptemp = pCitys[i];

                    // ��������ܴ���
                    if (ptemp.equals(temp))
                    {
                        isHas = true;
                        continue;
                    }
                }

                // �Աȹ���鿴�Ƿ����
                if (!isHas)
                {
                    throw new BOException("���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����ʱ�������ݿ��쳣��",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }
            }
        }
        catch (DAOException e)
        {
            throw new BOException("���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڼ���ж��������ܵ�����Ϣ�Ƿ�Ϊ�����ܵ�����Ϣ�Ӽ�
     * 
     * @param pCategoryID ������id
     * @param citys ��ǰ���ܵ�����Ϣ
     * @return
     * @throws BOException
     */
    public void isHasPCityIds(String pCategoryID, String[] citys)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.isHasPCityIds() is start...");
        }

        // �õ������ܵ�����Ϣ
        Category pCategory = ( Category ) Repository.getInstance()
                                                    .getNode(pCategoryID,
                                                             RepositoryConstants.TYPE_CATEGORY);

        // ��������ܵ�����ϢΪȫ��ͨ�á����ñ��ˡ�
        if (Constants.ALL_CITY_DATA.equals(pCategory.getCityId()))
        {
            return;
        }

        // ����˴�Ϊ�գ�����ζ�ŵ�ǰ����Ϊȫ��ͨ���͡������ܶ��������������
        if (null == citys)
        {
            throw new BOException("�������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��ʱ�������ݿ��쳣��",
                                  RepositoryBOCode.ADD_CATEGORY_CITY);
        }

        // �����ܵ�����Ϣ����Ϊ������ʽ
        List pcityl = this.queryListByCityId(pCategory.getCityId());

        for (int i = 0; i < citys.length; i++)
        {
            String cityId = citys[i];

            // �Աȸ����ܵ�����Ϣ�Ƿ���ڡ���������ڡ���
            if (!pcityl.contains(cityId))
            {
                throw new BOException("�������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��ʱ�������ݿ��쳣��",
                                      RepositoryBOCode.ADD_CATEGORY_CITY);
            }
        }
    }

    /**
     * ���ݵ�����Ϣ���õ������б�
     * 
     * @param city ������Ϣ
     * @param categoryID ����id
     * @param pCategoryID ������id
     * @return
     * @throws BOException
     */
    public void queryNewMusicCityList(PageResult page, CityVO city,
                                      String categoryID, String pCategoryID)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.queryNewMusicCityList("
                         + city.toString() + ") is start...");
        }

        NewMusicCategoryVO pCategory = null;
        List cityId = new ArrayList();

        try
        {
            if (!pCategoryID.equals(""))
            {
                pCategory = NewMusicCategoryDAO.getInstance().queryNewMusicCategoryVO(pCategoryID);

                cityId = queryListByCityId(pCategory.getCityId());
            }

            // ����CategoryDeviceDAO���в�ѯ
            CategoryCityDAO.getInstance().queryCityList(page, city, cityId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ�����Ϣ���õ������б�ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����
     * 
     * @param categoryId ��ǰ����id
     * @param pCitys ��ǰ���ܵ�����Ϣ
     * @return
     * @throws BOException
     */
    public void isHasNewMusicCity(String categoryId, String[] pCitys)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.isHasCity() is start...");
        }

        List citys = null;
        List tempCityl = null;
        Set zCitys = new TreeSet();
        boolean isHas = true;

        try
        {
            // ���ݸ�����id�õ��ӻ�������������Ϣ����
            citys = CategoryCityDAO.getInstance()
                                   .queryCityListByNewMusicPcategoryId(categoryId);;

            for (Iterator iter = citys.iterator(); iter.hasNext();)
            {
                String tempCity = ( String ) iter.next();

                // �����ȫ��ͨ�á�ֱ���д���Ϊ�ܽ���У��ġ����Ǳ��޸ĳɷ�ȫ������Ϣ
                if (Constants.ALL_CITY_DATA.equals(tempCity))
                {
                    throw new BOException("���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����ʱ�������ݿ��쳣��",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }

                // �����ݿ��д洢״̬��Ϊ����
                tempCityl = this.queryListByCityId(tempCity);

                // ȫ������set�С�ȥ��
                zCitys.addAll(tempCityl);
            }

            // ����set���ӻ������е�����Ϣ������ǰ�޸ĺ󸸻��ܵ�����Ϣ�����Ƿ�����
            for (Iterator iter = zCitys.iterator(); iter.hasNext();)
            {
                String temp = ( String ) iter.next();
                isHas = false;

                // ���������ܵ�����Ϣ
                for (int i = 0; i < pCitys.length; i++)
                {
                    String ptemp = pCitys[i];

                    // ��������ܴ���
                    if (ptemp.equals(temp))
                    {
                        isHas = true;
                        continue;
                    }
                }

                // �Աȹ���鿴�Ƿ����
                if (!isHas)
                {
                    throw new BOException("���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����ʱ�������ݿ��쳣��",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }
            }
        }
        catch (DAOException e)
        {
            throw new BOException("���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڼ���ж��������ܵ�����Ϣ�Ƿ�Ϊ�����ܵ�����Ϣ�Ӽ�
     * 
     * @param pCategoryID ������id
     * @param citys ��ǰ���ܵ�����Ϣ
     * @return
     * @throws BOException
     */
    public void isHasNewMusicPCityIds(String pCategoryId, String[] citys)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.isHasPCityIds() is start...");
        }

        // �õ������ܵ�����Ϣ
        NewMusicCategoryVO newMusicCategory = null;
        
        try
        {
            newMusicCategory = NewMusicCategoryDAO.getInstance().queryNewMusicCategoryVO(pCategoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�õ���������Ϣʱ�������ݿ��쳣��");
        }
        
        // ���������Ϊ�ա���Ϊ������
        if(null == newMusicCategory.getCityId())
        {
            return;
        }

        // ��������ܵ�����ϢΪȫ��ͨ�á����ñ��ˡ�
        if (Constants.ALL_CITY_DATA.equals(newMusicCategory.getCityId()))
        {
            return;
        }

        // ����˴�Ϊ�գ�����ζ�ŵ�ǰ����Ϊȫ��ͨ���͡������ܶ��������������
        if (null == citys)
        {
            throw new BOException("�������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��ʱ�������ݿ��쳣��",
                                  RepositoryBOCode.ADD_CATEGORY_CITY);
        }

        // �����ܵ�����Ϣ����Ϊ������ʽ
        List pcityl = this.queryListByCityId(newMusicCategory.getCityId());

        for (int i = 0; i < citys.length; i++)
        {
            String cityId = citys[i];

            // �Աȸ����ܵ�����Ϣ�Ƿ���ڡ���������ڡ���
            if (!pcityl.contains(cityId))
            {
                throw new BOException("�������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��ʱ�������ݿ��쳣��",
                                      RepositoryBOCode.ADD_CATEGORY_CITY);
            }
        }
    }
    
    
    /**
     * ���ݵ�����Ϣ���õ������б�
     * 
     * @param city ������Ϣ
     * @param categoryID ����id
     * @param pCategoryID ������id
     * @return
     * @throws BOException
     */
    public void queryBookCityList(PageResult page, CityVO city,
                                      String categoryID, String pCategoryID)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.queryBookCityList("
                         + city.toString() + ") is start...");
        }

        BookCategoryVO pCategory = null;
        List cityId = new ArrayList();

        try
        {
            if (!pCategoryID.equals(""))
            {
                pCategory = BookCategoryDAO.getInstance().queryBookCategoryVO(pCategoryID);

                cityId = queryListByCityId(pCategory.getCityId());
            }

            // ����CategoryDeviceDAO���в�ѯ
            CategoryCityDAO.getInstance().queryCityList(page, city, cityId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ�����Ϣ���õ������б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڼ���ж��������ܵ�����Ϣ�Ƿ�Ϊ�����ܵ�����Ϣ�Ӽ�
     * 
     * @param pCategoryID ������id
     * @param citys ��ǰ���ܵ�����Ϣ
     * @return
     * @throws BOException
     */
    public void isHasBookPCityIds(String pCategoryId, String[] citys)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.isHasBookPCityIds() is start...");
        }

        // �õ������ܵ�����Ϣ
        BookCategoryVO bookCategory = null;
        
        try
        {
            bookCategory = BookCategoryDAO.getInstance().queryBookCategoryVO(pCategoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�õ���������Ϣʱ�������ݿ��쳣��");
        }
        
        // ���������Ϊ�ա���Ϊ������
        if(null == bookCategory.getCityId())
        {
            return;
        }

        // ��������ܵ�����ϢΪȫ��ͨ�á����ñ��ˡ�
        if (Constants.ALL_CITY_DATA.equals(bookCategory.getCityId()))
        {
            return;
        }

        // ����˴�Ϊ�գ�����ζ�ŵ�ǰ����Ϊȫ��ͨ���͡������ܶ��������������
        if (null == citys)
        {
            throw new BOException("�������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��ʱ�������ݿ��쳣��",
                                  RepositoryBOCode.ADD_CATEGORY_CITY);
        }

        // �����ܵ�����Ϣ����Ϊ������ʽ
        List pcityl = this.queryListByCityId(bookCategory.getCityId());

        for (int i = 0; i < citys.length; i++)
        {
            String cityId = citys[i];

            // �Աȸ����ܵ�����Ϣ�Ƿ���ڡ���������ڡ���
            if (!pcityl.contains(cityId))
            {
                throw new BOException("�������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��ʱ�������ݿ��쳣��",
                                      RepositoryBOCode.ADD_CATEGORY_CITY);
            }
        }
    }
    
    /**
     * ���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����
     * 
     * @param categoryId ��ǰ����id
     * @param pCitys ��ǰ���ܵ�����Ϣ
     * @return
     * @throws BOException
     */
    public void isHasBookCity(String categoryId, String[] pCitys)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.isHasBookCity() is start...");
        }

        List citys = null;
        List tempCityl = null;
        Set zCitys = new TreeSet();
        boolean isHas = true;

        try
        {
            // ���ݸ�����id�õ��ӻ�������������Ϣ����
            citys = CategoryCityDAO.getInstance()
                                   .queryCityListByBookPcategoryId(categoryId);;

            for (Iterator iter = citys.iterator(); iter.hasNext();)
            {
                String tempCity = ( String ) iter.next();

                // �����ȫ��ͨ�á�ֱ���д���Ϊ�ܽ���У��ġ����Ǳ��޸ĳɷ�ȫ������Ϣ
                if (Constants.ALL_CITY_DATA.equals(tempCity))
                {
                    throw new BOException("���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����ʱ�������ݿ��쳣��",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }

                // �����ݿ��д洢״̬��Ϊ����
                tempCityl = this.queryListByCityId(tempCity);

                // ȫ������set�С�ȥ��
                zCitys.addAll(tempCityl);
            }

            // ����set���ӻ������е�����Ϣ������ǰ�޸ĺ󸸻��ܵ�����Ϣ�����Ƿ�����
            for (Iterator iter = zCitys.iterator(); iter.hasNext();)
            {
                String temp = ( String ) iter.next();
                isHas = false;

                // ���������ܵ�����Ϣ
                for (int i = 0; i < pCitys.length; i++)
                {
                    String ptemp = pCitys[i];

                    // ��������ܴ���
                    if (ptemp.equals(temp))
                    {
                        isHas = true;
                        continue;
                    }
                }

                // �Աȹ���鿴�Ƿ����
                if (!isHas)
                {
                    throw new BOException("���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����ʱ�������ݿ��쳣��",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }
            }
        }
        catch (DAOException e)
        {
            throw new BOException("���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ݵ�����Ϣ���õ������б�
     * 
     * @param city ������Ϣ
     * @param categoryID ����id
     * @param pCategoryID ������id
     * @return
     * @throws BOException
     */
    public void queryReadCityList(PageResult page, CityVO city,
                                      String categoryID, String pCategoryID)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.queryReadCityList("
                         + city.toString() + ") is start...");
        }

        ReadCategoryVO pCategory = null;
        List cityId = new ArrayList();

        try
        {
            if (!pCategoryID.equals(""))
            {
                pCategory = ReadCategoryDAO.getInstance().queryReadCategoryVO(pCategoryID);

                cityId = queryListByCityId(pCategory.getCityId());
            }

            // ����CategoryDeviceDAO���в�ѯ
            CategoryCityDAO.getInstance().queryCityList(page, city, cityId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ�����Ϣ���õ������б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڼ���ж��������ܵ�����Ϣ�Ƿ�Ϊ�����ܵ�����Ϣ�Ӽ�
     * 
     * @param pCategoryID ������id
     * @param citys ��ǰ���ܵ�����Ϣ
     * @return
     * @throws BOException
     */
    public void isHasReadPCityIds(String pCategoryId, String[] citys)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.isHasReadPCityIds() is start...");
        }

        // �õ������ܵ�����Ϣ
        ReadCategoryVO readCategory = null;
        
        try
        {
            readCategory = ReadCategoryDAO.getInstance().queryReadCategoryVO(pCategoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�õ���������Ϣʱ�������ݿ��쳣��");
        }
        
        // ���������Ϊ�ա���Ϊ������
        if(null == readCategory.getCityId())
        {
            return;
        }

        // ��������ܵ�����ϢΪȫ��ͨ�á����ñ��ˡ�
        if (Constants.ALL_CITY_DATA.equals(readCategory.getCityId()))
        {
            return;
        }

        // ����˴�Ϊ�գ�����ζ�ŵ�ǰ����Ϊȫ��ͨ���͡������ܶ��������������
        if (null == citys)
        {
            throw new BOException("�������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��ʱ�������ݿ��쳣��",
                                  RepositoryBOCode.ADD_CATEGORY_CITY);
        }

        // �����ܵ�����Ϣ����Ϊ������ʽ
        List pcityl = this.queryListByCityId(readCategory.getCityId());

        for (int i = 0; i < citys.length; i++)
        {
            String cityId = citys[i];

            // �Աȸ����ܵ�����Ϣ�Ƿ���ڡ���������ڡ���
            if (!pcityl.contains(cityId))
            {
                throw new BOException("�������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��ʱ�������ݿ��쳣��",
                                      RepositoryBOCode.ADD_CATEGORY_CITY);
            }
        }
    }
    
    /**
     * ���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����
     * 
     * @param categoryId ��ǰ����id
     * @param pCitys ��ǰ���ܵ�����Ϣ
     * @return
     * @throws BOException
     */
    public void isHasReadCity(String categoryId, String[] pCitys)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryCityBO.isHasBookCity() is start...");
        }

        List citys = null;
        List tempCityl = null;
        Set zCitys = new TreeSet();
        boolean isHas = true;

        try
        {
            // ���ݸ�����id�õ��ӻ�������������Ϣ����
            citys = CategoryCityDAO.getInstance()
                                   .queryCityListByReadPcategoryId(categoryId);;

            for (Iterator iter = citys.iterator(); iter.hasNext();)
            {
                String tempCity = ( String ) iter.next();

                // �����ȫ��ͨ�á�ֱ���д���Ϊ�ܽ���У��ġ����Ǳ��޸ĳɷ�ȫ������Ϣ
                if (Constants.ALL_CITY_DATA.equals(tempCity))
                {
                    throw new BOException("���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����ʱ�������ݿ��쳣��",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }

                // �����ݿ��д洢״̬��Ϊ����
                tempCityl = this.queryListByCityId(tempCity);

                // ȫ������set�С�ȥ��
                zCitys.addAll(tempCityl);
            }

            // ����set���ӻ������е�����Ϣ������ǰ�޸ĺ󸸻��ܵ�����Ϣ�����Ƿ�����
            for (Iterator iter = zCitys.iterator(); iter.hasNext();)
            {
                String temp = ( String ) iter.next();
                isHas = false;

                // ���������ܵ�����Ϣ
                for (int i = 0; i < pCitys.length; i++)
                {
                    String ptemp = pCitys[i];

                    // ��������ܴ���
                    if (ptemp.equals(temp))
                    {
                        isHas = true;
                        continue;
                    }
                }

                // �Աȹ���鿴�Ƿ����
                if (!isHas)
                {
                    throw new BOException("���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����ʱ�������ݿ��쳣��",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }
            }
        }
        catch (DAOException e)
        {
            throw new BOException("���ڼ�������ܵ���������Ϣ�Ƿ�С���ӻ��ܵ�����Ϣ����ʱ�������ݿ��쳣��");
        }
    }
}
