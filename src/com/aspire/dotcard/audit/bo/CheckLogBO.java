package com.aspire.dotcard.audit.bo;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.audit.dao.CheckLogDAO;
import com.aspire.dotcard.audit.vo.CheckDetailVO;
import com.aspire.dotcard.audit.vo.CheckLogVO;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * ����ģ��ҵ����
 */
public class CheckLogBO
{
    /**
     * ��־����
     */
    protected static final JLogger LOG = LoggerFactory.getLogger(CheckLogBO.class);

    private static CheckLogBO instance = new CheckLogBO();

    private CheckLogBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CheckLogBO getInstance()
    {
        return instance;
    }

    /**
     * ���ڲ�ѯ��ǰ�����б�
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryCheckLogList(PageResult page, CheckLogVO vo) throws BOException
    {
        try
        {
            CheckLogDAO.getInstance().queryCheckLogList(page, vo);
        }
        catch (DAOException e)
        {
            LOG.error("���˷������ݿ��쳣!", e);
            throw new BOException("���˷������ݿ��쳣!");
        }
    }

    /**
     * ���ڲ�ѯ��ǰ�����б�
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryCheckDetailList(PageResult page, CheckDetailVO vo) throws BOException
    {
        try
        {
            CheckLogDAO.getInstance().queryCheckDetailList(page, vo);
        }
        catch (DAOException e)
        {
            LOG.error("��ѯ�������ݷ������ݿ��쳣!", e);
            throw new BOException("��ѯ�������ݷ������ݿ��쳣!");
        }
    }

}
