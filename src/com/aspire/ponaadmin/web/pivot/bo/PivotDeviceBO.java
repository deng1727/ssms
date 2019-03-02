package com.aspire.ponaadmin.web.pivot.bo;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.book.bo.BookRefBO;
import com.aspire.ponaadmin.web.pivot.dao.PivotDeviceDAO;
import com.aspire.ponaadmin.web.pivot.vo.PivotDeviceVO;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;

public class PivotDeviceBO
{
    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(PivotDeviceBO.class);

    private static PivotDeviceBO instance = new PivotDeviceBO();

    private PivotDeviceBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static PivotDeviceBO getInstance()
    {
        return instance;
    }
    
    /**
     * ���ڲ�ѯ�ص�����б�
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryPivotDeviceList(PageResult page, PivotDeviceVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotDeviceBO.queryPivotDeviceList( ) is start...");
        }

        try
        {
            // ����PivotDeviceDAO���в�ѯ
            PivotDeviceDAO.getInstance().queryPivotDeviceList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ�ص�����б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �����Ƴ�ָ���ص����
     * 
     * @param deviceId
     */
    public void removeDeviceID(String[] deviceId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotDeviceBO.removeDeviceID( ) is start...");
        }

        try
        {
            // ����PivotDeviceDAO
            PivotDeviceDAO.getInstance().removeDeviceID(deviceId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�Ƴ�ָ���ص����ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �����ص�����б�
     * 
     * @param dataFile
     * @throws BOException
     */
    public String importPivotDevice(FormFile dataFile) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotDeviceBO.importPivotDevice( ) is start...");
        }

        StringBuffer ret = new StringBuffer();

        try
        {
            // ����EXECL�ļ�����ȡ�ն�����汾�б�
            List list = BookRefBO.getInstance().paraseDataFile(dataFile);

            // У��ļ��Д����Ƿ��ڻ��ͱ��д���
            String temp = PivotDeviceDAO.getInstance()
                            .verifyDeviceId(list);
            
            if (!"".equals(temp))
            {
                ret.append("���ͱ�������������ͱ���ֱ�Ϊ��").append(temp).append("���޸ģ�");
                
                throw new BOException(ret.toString(), RepositoryBOCode.CATEGORY_DEVICE);
            }
            
            // �����б����Ƿ����ԭ������
            PivotDeviceDAO.getInstance().hasDeviceId(list);
            
            String[] ids = new String[list.size()];
            
            for (int i = 0; i < list.size(); i++)
            {
                ids[i] = (String)list.get(i);
            }
            
            // ����PivotDeviceDAO��������
            PivotDeviceDAO.getInstance()
                              .addDeviceId(ids);

            ret.append("�ɹ�����" + list.size() + "����¼.");

            return ret.toString();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�����ص�����б�ʱ�������ݿ��쳣��");
        }
    }
}
