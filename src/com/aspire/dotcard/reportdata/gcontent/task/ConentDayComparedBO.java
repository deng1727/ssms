/*
 * �ļ�����ConentDayComparedBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.reportdata.gcontent.task;

import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

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
public class ConentDayComparedBO
{
    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(ConentDayComparedBO.class);

    private static ConentDayComparedBO bo = new ConentDayComparedBO();

    private ConentDayComparedBO()
    {
    }

    public static ConentDayComparedBO getInstance()
    {
        return bo;
    }

    public void exe()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("���ô洢���̣��������������������ֵ����ʼִ�С�");
        }
        
        // ����p_Binding_sort�洢���̼������������������ֵ
        try
        {
            ConentDayComparedDAO.getInstance().exeProcedure("p_Binding_sort");
        }
        catch (DAOException e)
        {
            logger.error("���ô洢���̣��������������������ֵʱ����", e);
            return ;
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("���ô洢���̣��������������������ֵ��ִ�н�����");
        }
        
        /**
        if (!this.init())
        {
            logger.debug("׼���������e��ִ�н�����");
            return;
        }

        if (!this.newCompared("3"))
        {
            logger.debug("�ó�Ophone����ʱ����ִ�н�����");
            return;
        }

        if (!this.newCompared("9"))
        {
            logger.debug("�ó���׿����ʱ����ִ�н�����");
            return;
        }
        
        if (!this.updateCompared())
        {
            logger.debug("����׶γ���ִ�н�����");
            return;
        }
        **/
    }

    /**
     * ׼������
     */
    public boolean init()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�����ʱ�����ݣ����뱨�����ݣ�������¾ɱ������ʼִ�С�");
        }

        // ���t_r_servenday_temp
        try
        {
            ConentDayComparedDAO.getInstance().delOldRepData();
        }
        catch (DAOException e)
        {
            logger.error("�ڿ�ʼ���뱨������ǰ�����t_r_servenday_temp��ǰһ��Ĺ�������ʱ�����쳣:", e);
            return false;
        }

        // ���뱨����report_servenday������t_r_servenday_temp
        try
        {
            ConentDayComparedDAO.getInstance().insertRepData();
        }
        catch (DAOException e)
        {
            logger.error("���뱨����report_servenday������t_r_servenday_tempʱ�����쳣:", e);
            return false;
        }

        // t_r_sevendayscompared����������ű��Ϊ�������
        try
        {
            ConentDayComparedDAO.getInstance().updateNewNumberToOld();
        }
        catch (DAOException e)
        {
            logger.error("t_r_sevendayscompared����������ű��Ϊ�������ʱ�����쳣:", e);
            return false;
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�����ʱ�����ݣ����뱨�����ݣ�������¾ɱ����ִ�н�����");
        }

        return true;
    }

    /**
     * ͳ��ophone������������
     */
    public boolean newCompared(String osId)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�����ʱͳ�Ʊ����ݣ�ͳ��" + osId + "�����������ݡ���ʼִ�С�");
        }

        Map temp = null;

        // �����ʱͳ�Ʊ�t_r_sevenday
        try
        {
            ConentDayComparedDAO.getInstance().delTempTable();
        }
        catch (DAOException e)
        {
            logger.error("�����ʱͳ�Ʊ�t_r_sevendayʱ�����쳣:", e);
            return false;
        }

        // ͳ�Ʊ�����������ʱͳ�Ʊ�t_r_sevenday��
        try
        {
            ConentDayComparedDAO.getInstance().insertNewToTemp(osId);
        }
        catch (DAOException e)
        {
            logger.error("ͳ�Ʊ�����������ʱͳ�Ʊ�t_r_sevenday��ʱ�����쳣:", e);
            return false;
        }

        // �õ���ʱ����ȫ������
        try
        {
            temp = ConentDayComparedDAO.getInstance().getTempDate();
        }
        catch (DAOException e)
        {
            logger.error("ͳ�Ʊ�����������ʱͳ�Ʊ�t_r_sevenday��ʱ�����쳣:", e);
            return false;
        }

        // ���ͳ�Ʊ������ݸ�������仯����t_r_sevendayscompared���������
        try
        {
            ConentDayComparedDAO.getInstance().updateNewDate(temp, osId);
        }
        catch (DAOException e)
        {
            logger.error("���ͳ�Ʊ������ݸ�������仯����t_r_sevendayscompared���������ʱ�����쳣:", e);
            return false;
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�����ʱͳ�Ʊ����ݣ�ͳ��" + osId + "�����������ݡ�ִ�н�����");
        }

        return true;
    }

    /**
     * ��������������
     * 
     */
    public boolean updateCompared()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("������������������ʼִ�С�");
        }

        // �嶨���մ�������ֵ�Ľ����
        try
        {
            ConentDayComparedDAO.getInstance().delCompared();
        }
        catch (DAOException e)
        {
            logger.error("�嶨���մ�������ֵ�Ľ����ʱ�����쳣:", e);
            return false;
        }

        // �嶨���մ�������ֵ�Ľ����
        try
        {
            ConentDayComparedDAO.getInstance().updateCompared();
        }
        catch (DAOException e)
        {
            logger.error("�嶨���մ�������ֵ�Ľ����ʱ�����쳣:", e);
            return false;
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("����������������ִ�н�����");
        }

        return true;
    }
}