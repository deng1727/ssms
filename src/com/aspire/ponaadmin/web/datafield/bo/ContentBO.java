
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
 * ��ѯ��Ŀ��Ӧ�����ݵ�BO��
 * </p>
 * 
 * @author biran
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ContentBO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(ContentBO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static ContentBO instance = new ContentBO();

    /**
     * ���췽������singletonģʽ����
     */
    private ContentBO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static ContentBO getInstance()
    {

        return instance;
    }

    /**
     * �������ơ��ṩ�̵��ֶβ�ѯ��ѯӦ�á�
     * 
     * @param page
     * @param name ����
     * @param spName �ṩ��
     * @param icpServId ҵ�����
     * @param icpCode ��ҵ����
     * @param contentID ����ID
     * @param provider ��������
     * @param cateName ҵ�����ݷ���
     * @param subtype mm����ҵ��
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
     * ����id��ѯContentVO
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
            throw new BOException("����contentID��ѯʧ�ܣ�");
        }
    }

    /**
     * ����contentID��ѯӦ�����Լ���
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
     * ����Ӧ������
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
     * ���ڷ��������ֻ�����չ�ֶ��б�
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
            // ����ContentDAO���в�ѯ
            return ContentDAO.getInstance()
                                      .queryContentKeyBaseList(tid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����MMӦ����չ�ֶ��б�ʱ�������ݿ��쳣��",e);
        }
    }
    /**
     * 
     *@desc ����
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
				throw new BOException("������չֵʧ��", e);
			}
		}
	}
}
