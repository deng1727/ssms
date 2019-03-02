/*
 * 
 */

package com.aspire.ponaadmin.web.channeladmin.bo;



import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.channeladmin.dao.CategoryQueryDAO;

/**
 * @author x_wangml
 * 
 */
public class CategoryQueryBO
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryQueryBO.class);

    private static CategoryQueryBO bo = new CategoryQueryBO();

    private CategoryQueryBO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CategoryQueryBO getInstance()
    {

        return bo;
    }

    /**
     * ���ݻ��ܱ��룬�������Ʋ����Ӧ���ܱ���Ϣ
     * 
     * @param categoryId ���ܱ���
     * @param categoryName ��������
     * @return
     * @throws BOException
     */
    public void queryCategoryList(PageResult page, String categoryId,
                                        String categoryName)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryQueryBO.queryCategoryList("+categoryId+","+categoryName+") is start...");
        }

        try
        {
            // ����CategoryQueryDAO���в�ѯ
            CategoryQueryDAO.getInstance()
                                    .queryCategoryList(page,
                                                       categoryId,
                                                       categoryName);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ��ܱ��룬�������Ʋ����Ӧ���ܱ���Ϣʱ�������ݿ��쳣��");
        }
    }
    /**
     * �жϸû���ID�Ƿ��Ѿ��������������Ӧ�Ļ����¡�
     * @param categoryId
     * @return
     * @throws DAOException
     */
    public boolean isExistInCategory(String categoryId)throws BOException{
    	try {
    		 return CategoryQueryDAO.getInstance().isExistInCategory(categoryId);
		} catch (Exception e) {
			throw new BOException("�жϸû���ID�Ƿ��Ѿ��������������Ӧ�Ļ������쳣��");
		}
    }
}
