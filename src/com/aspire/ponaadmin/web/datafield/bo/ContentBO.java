
package com.aspire.ponaadmin.web.datafield.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.datafield.dao.ContentDAO;
import com.aspire.ponaadmin.web.datafield.dao.KeyBaseDAO;
import com.aspire.ponaadmin.web.datafield.dao.KeyResourceDAO;
import com.aspire.ponaadmin.web.datafield.vo.ContentVO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicCategoryDAO;

/**
 * <p>
 * 查询栏目下应用内容的BO类
 * </p>
 * 
 * @author biran
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ContentBO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(ContentBO.class);

    /**
     * singleton模式的实例
     */
    private static ContentBO instance = new ContentBO();

    /**
     * 构造方法，由singleton模式调用
     */
    private ContentBO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static ContentBO getInstance()
    {

        return instance;
    }

    /**
     * 根据名称、提供商等字段查询查询应用。
     * 
     * @param page
     * @param name 名称
     * @param spName 提供商
     * @param icpServId 业务代码
     * @param icpCode 企业代码
     * @param contentID 内容ID
     * @param provider 数据类型
     * @param cateName 业务内容分类
     * @param subtype mm自有业务
     * @throws BOException
     */
    public void queryContentList(PageResult page, String name, String spName,
                                 String icpServId, String icpCode,
                                 String contentID, String provider,
                                 String cateName, String subtype)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryContentList(" + name + "," + spName + ","
                         + icpServId + "," + icpCode + "," + contentID + ","
                         + provider + "," + cateName + "," + subtype + ")");
        }
        if (page == null)
        {
            throw new BOException("invalid para , page is null");
        }
        try
        {
            ContentDAO.getInstance().queryContentList(page,
                                                      name,
                                                      spName,
                                                      icpServId,
                                                      icpCode,
                                                      contentID,
                                                      provider,
                                                      cateName,
                                                      subtype);
        }
        catch (DAOException e)
        {
            logger.error("queryContentList...", e);
        }
    }

    /**
     * 根据id查询ContentVO
     * 
     * @param id String
     * @return ContentVO
     * @throws BOException
     */
    public ContentVO getContentByID(String id) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getContentByID(" + id + ")");
        }
        if (id == null)
        {
            throw new BOException("invalid para id");
        }
        try
        {
            return ContentDAO.getInstance().getContentByID(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据contentID查询失败！");
        }
    }

    /**
     * 根据contentID查询应用属性集合
     * 
     * @param contentID
     * @return
     * @throws DAOException
     */
    public Map getResourceList(String contentID) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getResourceList(" + contentID + ")");
        }
        Map map = new HashMap();

        if (contentID == null)
        {
            throw new BOException("invalid para , contentID is null");
        }
        try
        {
            List keytable = KeyBaseDAO.getInstance()
                                      .getKeytable("t_r_gcontent");

            int size = keytable.size();
            if (logger.isDebugEnabled())
            {
                logger.debug("keytable.size()=" + size + ")");
            }
            for (int i = 0; i < size; i++)
            {
                ResourceVO vo = ( ResourceVO ) keytable.get(i);
                Object[] paras = KeyResourceDAO.getInstance()
                                               .getResourceValueByID(vo.getKeyid(),
                                                                     contentID);
                vo.setTid(contentID);
                vo.setValue(( String ) paras[0]);
                vo.setLupdate(( String ) paras[1]);
                map.put(vo.getKeyid(), vo);
            }
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("getResourceList", e);
        }
        return map;
    }

    /**
     * 更新应用属性
     * 
     * @param keyid
     * @param value
     * @param tid
     * @throws BOException
     */
    public void updateResourceValue(String[] keyid, String[] value, String tid)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateResourceValue()  tid= " + tid);
        }
        try
        {
            int size = keyid.length;
            if (logger.isDebugEnabled())
            {
                logger.debug("keyid.size()=" + size + ")");
            }
            for (int i = 0; i < size; i++)
            {
                KeyResourceDAO.getInstance().updateResourceValue(keyid[i],
                                                                 value[i],
                                                                 tid);
            }
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("updateResourceValue", e);
        }
    }
    
    /**********************/
    /**
     * 用于返回新音乐货架扩展字段列表
     * @return
     * @throws BOException
     */
    public List queryContentKeyBaseList(String tid) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ContentBO.queryContentKeyBaseList( ) is start...");
        }

        try
        {
            // 调用ContentDAO进行查询
            return ContentDAO.getInstance()
                                      .queryContentKeyBaseList(tid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回MM应用扩展字段列表时发生数据库异常！",e);
        }
    }
    /**
     * 
     *@desc 保存
     *@author dongke
     *Aug 9, 2011
     * @param keyBaseList
     * @throws BOException
     */
    public void saveKeyResource(List keyBaseList) throws BOException
	{
		for (int i = 0; i < keyBaseList.size(); i++)
		{
			ResourceVO vo = (ResourceVO) keyBaseList.get(i);
			try
			{
				if (vo != null && vo.getValue() != null && !vo.getValue().equals(""))
				{
					KeyResourceDAO.getInstance().updateResourceValue(vo.getKeyid(),
							vo.getValue(), vo.getTid());
				}
			} catch (DAOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BOException("保存扩展值失败", e);
			}
		}
	}
}
