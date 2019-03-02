/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui;



import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;

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
}
