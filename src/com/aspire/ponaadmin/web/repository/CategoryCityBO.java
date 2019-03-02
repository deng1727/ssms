/*
 * 文件名：CategoryDeviceBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryCityBO.class);

    private static CategoryCityBO bo = new CategoryCityBO();

    private CategoryCityBO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryCityBO getInstance()
    {

        return bo;
    }

    /**
     * 根据地域信息。得到地域列表
     * 
     * @param city 地域信息
     * @param categoryID 货架id
     * @param pCategoryID 父货架id
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

            // 调用CategoryDeviceDAO进行查询
            CategoryCityDAO.getInstance().queryCityList(page, city, cityId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据机型信息。得到机型列表时发生数据库异常！");
        }
    }

    /**
     * 根据货架所存城市id组合。得到用于页面显示信息
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

        // 如果为0000，则为全国通用
        if (cityid.length == 1 && Constants.ALL_CITY_PLATFORM.equals(cityid[0]))
        {
            return "全国通用.";
        }

        try
        {
            // 调用CategoryDeviceDAO进行查询
            return CategoryCityDAO.getInstance().queryCityListByCityId(cityid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据货架所存城市id组合。得到用于页面显示信息时发生数据库异常！");
        }
    }

    /**
     * 根据货架数据库中存储城市id组合型式。得到用于页面隐藏的id信息
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

            // 如果是0000，不加入
            if (Constants.ALL_CITY_PLATFORM.equals(temp))
            {
                continue;
            }

            list.add(temp);
        }

        return list;
    }

    /**
     * 用于检查变更货架地域适配信息是否小于子货架地域信息集合
     * 
     * @param categoryId 当前货架id
     * @param pCitys 当前货架地域信息
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
            // 根据父货架id得到子货架所有区域信息集合
            citys = CategoryCityDAO.getInstance()
                                   .queryCityListByPcategoryId(categoryId);

            for (Iterator iter = citys.iterator(); iter.hasNext();)
            {
                String tempCity = ( String ) iter.next();

                // 如果是全国通用。直接招错。因为能进来校验的。都是被修改成非全国的信息
                if (Constants.ALL_CITY_DATA.equals(tempCity))
                {
                    throw new BOException("用于检查变更货架地域适配信息是否小于子货架地域信息集合时发生数据库异常！",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }

                // 把数据库中存储状态改为集合
                tempCityl = this.queryListByCityId(tempCity);

                // 全部加入set中。去重
                zCitys.addAll(tempCityl);
            }

            // 迭代set中子货架所有地域信息，看当前修改后父货架地域信息集合是否满足
            for (Iterator iter = zCitys.iterator(); iter.hasNext();)
            {
                String temp = ( String ) iter.next();
                isHas = false;

                // 迭代父货架地域信息
                for (int i = 0; i < pCitys.length; i++)
                {
                    String ptemp = pCitys[i];

                    // 如果父货架存在
                    if (ptemp.equals(temp))
                    {
                        isHas = true;
                        continue;
                    }
                }

                // 对比过后查看是否存在
                if (!isHas)
                {
                    throw new BOException("用于检查变更货架地域适配信息是否小于子货架地域信息集合时发生数据库异常！",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }
            }
        }
        catch (DAOException e)
        {
            throw new BOException("用于检查变更货架地域适配信息是否小于子货架地域信息集合时发生数据库异常！");
        }
    }

    /**
     * 用于检查判断新增货架地域信息是否为父货架地域信息子集
     * 
     * @param pCategoryID 父货架id
     * @param citys 当前货架地域信息
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

        // 得到父货架地域信息
        Category pCategory = ( Category ) Repository.getInstance()
                                                    .getNode(pCategoryID,
                                                             RepositoryConstants.TYPE_CATEGORY);

        // 如果父货架地域信息为全国通用。则不用比了。
        if (Constants.ALL_CITY_DATA.equals(pCategory.getCityId()))
        {
            return;
        }

        // 如果此处为空，就意味着当前货架为全国通用型。父货架都不满足此条件。
        if (null == citys)
        {
            throw new BOException("新增货架地域适配信息时大于父货架地域信息集合而失败时发生数据库异常！",
                                  RepositoryBOCode.ADD_CATEGORY_CITY);
        }

        // 父货架地域信息处理为集合型式
        List pcityl = this.queryListByCityId(pCategory.getCityId());

        for (int i = 0; i < citys.length; i++)
        {
            String cityId = citys[i];

            // 对比父货架地域信息是否存在。如果不存在。错
            if (!pcityl.contains(cityId))
            {
                throw new BOException("新增货架地域适配信息时大于父货架地域信息集合而失败时发生数据库异常！",
                                      RepositoryBOCode.ADD_CATEGORY_CITY);
            }
        }
    }

    /**
     * 根据地域信息。得到地域列表
     * 
     * @param city 地域信息
     * @param categoryID 货架id
     * @param pCategoryID 父货架id
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

            // 调用CategoryDeviceDAO进行查询
            CategoryCityDAO.getInstance().queryCityList(page, city, cityId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据机型信息。得到机型列表时发生数据库异常！");
        }
    }

    /**
     * 用于检查变更货架地域适配信息是否小于子货架地域信息集合
     * 
     * @param categoryId 当前货架id
     * @param pCitys 当前货架地域信息
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
            // 根据父货架id得到子货架所有区域信息集合
            citys = CategoryCityDAO.getInstance()
                                   .queryCityListByNewMusicPcategoryId(categoryId);;

            for (Iterator iter = citys.iterator(); iter.hasNext();)
            {
                String tempCity = ( String ) iter.next();

                // 如果是全国通用。直接招错。因为能进来校验的。都是被修改成非全国的信息
                if (Constants.ALL_CITY_DATA.equals(tempCity))
                {
                    throw new BOException("用于检查变更货架地域适配信息是否小于子货架地域信息集合时发生数据库异常！",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }

                // 把数据库中存储状态改为集合
                tempCityl = this.queryListByCityId(tempCity);

                // 全部加入set中。去重
                zCitys.addAll(tempCityl);
            }

            // 迭代set中子货架所有地域信息，看当前修改后父货架地域信息集合是否满足
            for (Iterator iter = zCitys.iterator(); iter.hasNext();)
            {
                String temp = ( String ) iter.next();
                isHas = false;

                // 迭代父货架地域信息
                for (int i = 0; i < pCitys.length; i++)
                {
                    String ptemp = pCitys[i];

                    // 如果父货架存在
                    if (ptemp.equals(temp))
                    {
                        isHas = true;
                        continue;
                    }
                }

                // 对比过后查看是否存在
                if (!isHas)
                {
                    throw new BOException("用于检查变更货架地域适配信息是否小于子货架地域信息集合时发生数据库异常！",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }
            }
        }
        catch (DAOException e)
        {
            throw new BOException("用于检查变更货架地域适配信息是否小于子货架地域信息集合时发生数据库异常！");
        }
    }

    /**
     * 用于检查判断新增货架地域信息是否为父货架地域信息子集
     * 
     * @param pCategoryID 父货架id
     * @param citys 当前货架地域信息
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

        // 得到父货架地域信息
        NewMusicCategoryVO newMusicCategory = null;
        
        try
        {
            newMusicCategory = NewMusicCategoryDAO.getInstance().queryNewMusicCategoryVO(pCategoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("得到父货架信息时发生数据库异常！");
        }
        
        // 如果父货架为空。则为根货架
        if(null == newMusicCategory.getCityId())
        {
            return;
        }

        // 如果父货架地域信息为全国通用。则不用比了。
        if (Constants.ALL_CITY_DATA.equals(newMusicCategory.getCityId()))
        {
            return;
        }

        // 如果此处为空，就意味着当前货架为全国通用型。父货架都不满足此条件。
        if (null == citys)
        {
            throw new BOException("新增货架地域适配信息时大于父货架地域信息集合而失败时发生数据库异常！",
                                  RepositoryBOCode.ADD_CATEGORY_CITY);
        }

        // 父货架地域信息处理为集合型式
        List pcityl = this.queryListByCityId(newMusicCategory.getCityId());

        for (int i = 0; i < citys.length; i++)
        {
            String cityId = citys[i];

            // 对比父货架地域信息是否存在。如果不存在。错
            if (!pcityl.contains(cityId))
            {
                throw new BOException("新增货架地域适配信息时大于父货架地域信息集合而失败时发生数据库异常！",
                                      RepositoryBOCode.ADD_CATEGORY_CITY);
            }
        }
    }
    
    
    /**
     * 根据地域信息。得到地域列表
     * 
     * @param city 地域信息
     * @param categoryID 货架id
     * @param pCategoryID 父货架id
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

            // 调用CategoryDeviceDAO进行查询
            CategoryCityDAO.getInstance().queryCityList(page, city, cityId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据机型信息。得到机型列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于检查判断新增货架地域信息是否为父货架地域信息子集
     * 
     * @param pCategoryID 父货架id
     * @param citys 当前货架地域信息
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

        // 得到父货架地域信息
        BookCategoryVO bookCategory = null;
        
        try
        {
            bookCategory = BookCategoryDAO.getInstance().queryBookCategoryVO(pCategoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("得到父货架信息时发生数据库异常！");
        }
        
        // 如果父货架为空。则为根货架
        if(null == bookCategory.getCityId())
        {
            return;
        }

        // 如果父货架地域信息为全国通用。则不用比了。
        if (Constants.ALL_CITY_DATA.equals(bookCategory.getCityId()))
        {
            return;
        }

        // 如果此处为空，就意味着当前货架为全国通用型。父货架都不满足此条件。
        if (null == citys)
        {
            throw new BOException("新增货架地域适配信息时大于父货架地域信息集合而失败时发生数据库异常！",
                                  RepositoryBOCode.ADD_CATEGORY_CITY);
        }

        // 父货架地域信息处理为集合型式
        List pcityl = this.queryListByCityId(bookCategory.getCityId());

        for (int i = 0; i < citys.length; i++)
        {
            String cityId = citys[i];

            // 对比父货架地域信息是否存在。如果不存在。错
            if (!pcityl.contains(cityId))
            {
                throw new BOException("新增货架地域适配信息时大于父货架地域信息集合而失败时发生数据库异常！",
                                      RepositoryBOCode.ADD_CATEGORY_CITY);
            }
        }
    }
    
    /**
     * 用于检查变更货架地域适配信息是否小于子货架地域信息集合
     * 
     * @param categoryId 当前货架id
     * @param pCitys 当前货架地域信息
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
            // 根据父货架id得到子货架所有区域信息集合
            citys = CategoryCityDAO.getInstance()
                                   .queryCityListByBookPcategoryId(categoryId);;

            for (Iterator iter = citys.iterator(); iter.hasNext();)
            {
                String tempCity = ( String ) iter.next();

                // 如果是全国通用。直接招错。因为能进来校验的。都是被修改成非全国的信息
                if (Constants.ALL_CITY_DATA.equals(tempCity))
                {
                    throw new BOException("用于检查变更货架地域适配信息是否小于子货架地域信息集合时发生数据库异常！",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }

                // 把数据库中存储状态改为集合
                tempCityl = this.queryListByCityId(tempCity);

                // 全部加入set中。去重
                zCitys.addAll(tempCityl);
            }

            // 迭代set中子货架所有地域信息，看当前修改后父货架地域信息集合是否满足
            for (Iterator iter = zCitys.iterator(); iter.hasNext();)
            {
                String temp = ( String ) iter.next();
                isHas = false;

                // 迭代父货架地域信息
                for (int i = 0; i < pCitys.length; i++)
                {
                    String ptemp = pCitys[i];

                    // 如果父货架存在
                    if (ptemp.equals(temp))
                    {
                        isHas = true;
                        continue;
                    }
                }

                // 对比过后查看是否存在
                if (!isHas)
                {
                    throw new BOException("用于检查变更货架地域适配信息是否小于子货架地域信息集合时发生数据库异常！",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }
            }
        }
        catch (DAOException e)
        {
            throw new BOException("用于检查变更货架地域适配信息是否小于子货架地域信息集合时发生数据库异常！");
        }
    }
    
    /**
     * 根据地域信息。得到地域列表
     * 
     * @param city 地域信息
     * @param categoryID 货架id
     * @param pCategoryID 父货架id
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

            // 调用CategoryDeviceDAO进行查询
            CategoryCityDAO.getInstance().queryCityList(page, city, cityId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据机型信息。得到机型列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于检查判断新增货架地域信息是否为父货架地域信息子集
     * 
     * @param pCategoryID 父货架id
     * @param citys 当前货架地域信息
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

        // 得到父货架地域信息
        ReadCategoryVO readCategory = null;
        
        try
        {
            readCategory = ReadCategoryDAO.getInstance().queryReadCategoryVO(pCategoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("得到父货架信息时发生数据库异常！");
        }
        
        // 如果父货架为空。则为根货架
        if(null == readCategory.getCityId())
        {
            return;
        }

        // 如果父货架地域信息为全国通用。则不用比了。
        if (Constants.ALL_CITY_DATA.equals(readCategory.getCityId()))
        {
            return;
        }

        // 如果此处为空，就意味着当前货架为全国通用型。父货架都不满足此条件。
        if (null == citys)
        {
            throw new BOException("新增货架地域适配信息时大于父货架地域信息集合而失败时发生数据库异常！",
                                  RepositoryBOCode.ADD_CATEGORY_CITY);
        }

        // 父货架地域信息处理为集合型式
        List pcityl = this.queryListByCityId(readCategory.getCityId());

        for (int i = 0; i < citys.length; i++)
        {
            String cityId = citys[i];

            // 对比父货架地域信息是否存在。如果不存在。错
            if (!pcityl.contains(cityId))
            {
                throw new BOException("新增货架地域适配信息时大于父货架地域信息集合而失败时发生数据库异常！",
                                      RepositoryBOCode.ADD_CATEGORY_CITY);
            }
        }
    }
    
    /**
     * 用于检查变更货架地域适配信息是否小于子货架地域信息集合
     * 
     * @param categoryId 当前货架id
     * @param pCitys 当前货架地域信息
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
            // 根据父货架id得到子货架所有区域信息集合
            citys = CategoryCityDAO.getInstance()
                                   .queryCityListByReadPcategoryId(categoryId);;

            for (Iterator iter = citys.iterator(); iter.hasNext();)
            {
                String tempCity = ( String ) iter.next();

                // 如果是全国通用。直接招错。因为能进来校验的。都是被修改成非全国的信息
                if (Constants.ALL_CITY_DATA.equals(tempCity))
                {
                    throw new BOException("用于检查变更货架地域适配信息是否小于子货架地域信息集合时发生数据库异常！",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }

                // 把数据库中存储状态改为集合
                tempCityl = this.queryListByCityId(tempCity);

                // 全部加入set中。去重
                zCitys.addAll(tempCityl);
            }

            // 迭代set中子货架所有地域信息，看当前修改后父货架地域信息集合是否满足
            for (Iterator iter = zCitys.iterator(); iter.hasNext();)
            {
                String temp = ( String ) iter.next();
                isHas = false;

                // 迭代父货架地域信息
                for (int i = 0; i < pCitys.length; i++)
                {
                    String ptemp = pCitys[i];

                    // 如果父货架存在
                    if (ptemp.equals(temp))
                    {
                        isHas = true;
                        continue;
                    }
                }

                // 对比过后查看是否存在
                if (!isHas)
                {
                    throw new BOException("用于检查变更货架地域适配信息是否小于子货架地域信息集合时发生数据库异常！",
                                          RepositoryBOCode.UPDATE_CATEGORY_CITY);
                }
            }
        }
        catch (DAOException e)
        {
            throw new BOException("用于检查变更货架地域适配信息是否小于子货架地域信息集合时发生数据库异常！");
        }
    }
}
