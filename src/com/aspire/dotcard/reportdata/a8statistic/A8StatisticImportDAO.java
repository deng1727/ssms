package com.aspire.dotcard.reportdata.a8statistic;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * 
 * ���»���ͳ����Ϣ
 * @author zhangwei
 *
 */
public class A8StatisticImportDAO
{
	 /**
     * ��־����reportimp.client
     */
    protected static JLogger logger = LoggerFactory.getLogger(A8StatisticImportDAO.class);
    /**
     * ����ר����־
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
	private static A8StatisticImportDAO DAO=new A8StatisticImportDAO();
	public static A8StatisticImportDAO getInstance()
	{
		return DAO;
	}
	/**
	 * ���»���ͳ����Ϣ
	 * @param categoryId ���ܱ���
	 * @param count ͳ������
	 * @throws DAOException
	 */
	public int updateCategoryStatistic(String categoryId,long count)throws DAOException
	{
		
		if (logger.isDebugEnabled())
        {
            logger.debug("updateCategoryStatistic(" + categoryId +','+count+ ")");
        }
        String sqlCode="reportdata.a8statistic.updateCategoryStatistic.UPDATE";
        Object paras[]= {new Long(count),categoryId};
        int result=DB.getInstance().executeBySQLCode(sqlCode, paras);
        if(result<1)
        {
        	REPORT_LOG.error("�����ڸû��ܱ��룺"+categoryId+",�������ݵ���ʧ��");
        	logger.error("�����ڸû��ܱ��룺"+categoryId+",�������ݵ���ʧ��");
        }
        return result;
       
	}

}
