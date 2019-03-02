package com.aspire.dotcard.reportdata.appstatistic;

import javax.sql.RowSet;

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
public class AppStatisticImportDAO
{
	 /**
     * ��־����reportimp.client
     */
    protected static JLogger logger = LoggerFactory.getLogger(AppStatisticImportDAO.class);
    /**
     * ����ר����־
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
	private static AppStatisticImportDAO DAO=new AppStatisticImportDAO();
	public static AppStatisticImportDAO getInstance()
	{
		return DAO;
	}
	
	
	
	
	/**
	 * ����Ӧ�ô��
	 * @param contentId ����ID
	 * @param count ͳ������
	 * @throws DAOException
	 */
	public int updateAppBaseStatistic(String updateSql,String insertSql,String contentId,String time,double count)throws DAOException
	{
		
		if (logger.isDebugEnabled())
        {
            logger.debug("updateAppLastStatistic(" + contentId +','+count+ ")");
        }
        String sqlCode=updateSql;//"reportdata.applaststatistic.updateAppLastStatistic.UPDATE";
        Object paras[]= {new Double(count),time,contentId};
        int result=DB.getInstance().executeBySQLCode(sqlCode, paras);
        if(result<1)
        {//���·���Ϊ0��û����ؼ�¼����������
        	 sqlCode = insertSql;//"reportdata.applaststatistic.updateAppLastStatistic.INSERT";
        	 Object  paras2[] = {new Double(count),time,contentId};
            DB.getInstance().executeBySQLCode(sqlCode, paras2);
            result=1;
        }
        return result;
       
	}
	

	/**
	 * ����Ӧ�ô��
	 * @param contentId ����ID
	 * @param count ͳ������
	 * @throws DAOException
	 */
	public int updateAppBaseStatistic(String updateSql,String insertSql,Object updateparas[],Object insertparas[])throws DAOException
	{
		
		if (logger.isDebugEnabled())
        {
            logger.debug("updateAppLastStatistic(" + updateparas +','+insertparas+ ")");
        }
        String sqlCode=updateSql;
        int result=DB.getInstance().executeBySQLCode(sqlCode, updateparas);
        if(result<1)
        {//���·���Ϊ0��û����ؼ�¼����������
        	 sqlCode = insertSql;
        	 result=DB.getInstance().executeBySQLCode(sqlCode, insertparas);
        }
        return result;
       
	}
	/**
	 * ��������Ӧ�ô��
	 * @param contentId ����ID
	 * @param count ͳ������
	 * @throws DAOException
	 */
	public int updateAppLastStatistic(String contentId,String time,double count)throws DAOException
	{
		
		if (logger.isDebugEnabled())
        {
            logger.debug("updateAppLastStatistic(" + contentId +','+count+ ")");
        }
        String sqlCode="reportdata.applaststatistic.updateAppLastStatistic.UPDATE";
        Object paras[]= {new Double(count),time,contentId};
        int result=DB.getInstance().executeBySQLCode(sqlCode, paras);
        if(result<1)
        {//���·���Ϊ0��û����ؼ�¼����������
        	 sqlCode="reportdata.applaststatistic.updateAppLastStatistic.INSERT";
        	 Object  paras2[] = {new Double(count),time,contentId};
            DB.getInstance().executeBySQLCode(sqlCode, paras2);
            result=1;
        }
        return result;
       
	}

	/**
	 * �����Ƽ�Ӧ�ô��
	 * @param contentId ����ID
	 * @param count ͳ������
	 * @throws DAOException
	 */
	public int updateAppRecommondStatistic(String contentId,String time,double count)throws DAOException
	{
		
		if (logger.isDebugEnabled())
        {
            logger.debug("updateAppLastStatistic(" + contentId +','+count+ ")");
        }
        String sqlCode="reportdata.apprecommondstatistic.updateAppRecommondStatistic.UPDATE";
        Object paras[]= {new Double(count),time,contentId};
        int result=DB.getInstance().executeBySQLCode(sqlCode, paras);
        if(result<1)
        {//���·���Ϊ0��û����ؼ�¼����������
        	 sqlCode="reportdata.apprecommondstatistic.updateAppRecommondStatistic.INSERT";
        	 Object  paras2[] = {new Double(count),time,contentId};
            DB.getInstance().executeBySQLCode(sqlCode, paras2);
            result= 1;
        }
        return result;
       
	}
    
    /**
     * ȫ������С���Ƽ����ݣ�dblink��ѯ���£�
     * @return ���¶�������¼
     * @throws DAOException
     */
    public int updateRecommendDate()throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateRecommendDate()");
        }
        String sqlCode = "reportdata.apprecommondstatistic.updateRecommendDate.UPDATE";
        int result = 0;
        Object paras[] = {};
        DB.getInstance().executeBySQLCode(sqlCode, paras);
        
        sqlCode = "reportdata.apprecommondstatistic.insertRecommendDate.INSERT";
        result = DB.getInstance().executeBySQLCode(sqlCode, paras);
        
        if (logger.isDebugEnabled())
        {
            logger.debug("С���Ƽ�����������" + result + "��");
        }

        sqlCode = "reportdata.apprecommondstatistic.queryRecommendDate.SELECT";
        RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
        
        try
        {
            if (rs.next())
            {
                result = rs.getInt(1);
            }
        }
        catch (Exception e)
        {
            logger.error("updateRecommendDate() �õ��Ƽ�������Ϣ����", e);
            throw new DAOException(e);
        }finally{
        	//ADD by tungke for close con & rs
        	DB.close(rs);
        }

        return result;
    }
    
}
