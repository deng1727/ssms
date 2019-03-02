package com.aspire.dotcard.reportdata.gcontent;

import java.sql.ResultSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.TopDataConstants;

public class ImportReportDataDAO
{
	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(ImportReportDataDAO.class);

	protected static JLogger topDatalog = LoggerFactory
			.getLogger("reportimp.client");

	private static ImportReportDataDAO dao = new ImportReportDataDAO();

	private ImportReportDataDAO()
	{
	}

	/**
	 * singletonģʽ,��ȡʵ�� 
	 * @return ʵ��
	 */
	public static final ImportReportDataDAO getInstance()
	{

		return dao;
	}
	
    /**
     * �������ݵ����ݿ�
     */
    public void updateProductValues(ImportReportDataVO vo,Integer[] result)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateProductValues(" + vo.toString() + ")");
        }
        if(null == vo)
        {
        	logger.error("�����ImportReportDataVO����Ϊnull.");
        	topDatalog.error("�����ImportReportDataVO����Ϊnull.");
        	return;
        }
        String sqlCode;
        try
        {
        	//��ʼ��ͳ������
        	if(result[1] == null){
        		result[1] = new Integer(0);
        		
        	}
//        	��ʼ��ͳ������
        	if(result[2] == null){
        		result[2] = new Integer(0);
        		
        	}
            int updateCount= 0;
        	switch(vo.getType())
        	{
        		case TopDataConstants.CONTENT_TYPE_DAY:
        		{
	        		sqlCode = "Reportdata.ImportReportDataDAO.updateProductValues().UPDATEDAY";
                    Object paras[] = { new Long(vo.getSearchCount()),
                                    new Long(vo.getPvCount()),
                                    new Long(vo.getSubsCount()),
                                    new Long(vo.getRemarkCount()),
                                    new Long(vo.getScoreCount()),
                                    new Long(vo.getCommendCount()),
                                    new Long(vo.getCollectionCount()),
                                    //new Long(vo.getAvgScoreCount()),  ȥ���ӱ���ȡֵ�����Ż��� t_pps_comment_grade����
                                    new Long(vo.getSearchCount()),
                                    new Long(vo.getPvCount()),
                                    new Long(vo.getSubsCount()),
                                    new Long(vo.getRemarkCount()),
                                    new Long(vo.getScoreCount()),
                                    new Long(vo.getCommendCount()),
                                    new Long(vo.getCollectionCount()),
                                    new Long(vo.getAdd7DaysOrderCount()),
                                    new Long(vo.getAdd30DaysOrderCount()),
                                    new Long(vo.getDownLoadCount()),
                                    vo.getContentId() };
                    
                    int c1 = DB.getInstance().executeBySQLCode(sqlCode, paras);
                    int c2 = 0;
                    if(c1==0){
	        		sqlCode = "Reportdata.ImportReportDataDAO.updateProductValues().UPDATEDAY_CY";
                    Object paras1[] = { new Long(vo.getSearchCount()),
                                    new Long(vo.getPvCount()),
                                    new Long(vo.getSubsCount()),
                                    new Long(vo.getRemarkCount()),
                                    new Long(vo.getScoreCount()),
                                    new Long(vo.getCommendCount()),
                                    new Long(vo.getCollectionCount()),
                                    //new Long(vo.getAvgScoreCount()),  ȥ���ӱ���ȡֵ�����Ż��� t_pps_comment_grade����
                                    new Long(vo.getSearchCount()),
                                    new Long(vo.getPvCount()),
                                    new Long(vo.getSubsCount()),
                                    new Long(vo.getRemarkCount()),
                                    new Long(vo.getScoreCount()),
                                    new Long(vo.getCommendCount()),
                                    new Long(vo.getCollectionCount()),
                                    new Long(vo.getAdd7DaysOrderCount()),
                                    new Long(vo.getAdd30DaysOrderCount()),
                                    new Long(vo.getDownLoadCount()),
                                    vo.getContentId() };
                    
                    c2 = DB.getInstance().executeBySQLCode(sqlCode, paras1);     
                    }
                    updateCount = c1 + c2;
	        		break;
        		}
        		case TopDataConstants.CONTENT_TYPE_WEEK:
        		{
	        		sqlCode = "Reportdata.ImportReportDataDAO.updateProductValues().UPDATEWEEK";
                    Object paras[] = { new Long(vo.getSearchCount()),
                                    new Long(vo.getPvCount()),
                                    new Long(vo.getSubsCount()),
                                    new Long(vo.getRemarkCount()),
                                    new Long(vo.getScoreCount()),
                                    new Long(vo.getCommendCount()),
                                    new Long(vo.getCollectionCount()),
                                    vo.getContentId() };
                    
                    updateCount = DB.getInstance().executeBySQLCode(sqlCode, paras);
	        		break;
        		}
        		case TopDataConstants.CONTENT_TYPE_MONTH:
        		{
	        		sqlCode = "Reportdata.ImportReportDataDAO.updateProductValues().UPDATEMONTH";
                    Object paras[] = { new Long(vo.getSearchCount()),
                                    new Long(vo.getPvCount()),
                                    new Long(vo.getSubsCount()),
                                    new Long(vo.getRemarkCount()),
                                    new Long(vo.getScoreCount()),
                                    new Long(vo.getCommendCount()),
                                    new Long(vo.getCollectionCount()),
                                    vo.getContentId() };
                    
                    updateCount = DB.getInstance().executeBySQLCode(sqlCode, paras);
	        		break;
        		}
        		default:
	        		topDatalog.error("��������Ӫ���ݵ������:��Ч�ĵ�����������:" + vo.getType());
	        		return ;
        	}
        	
            if(updateCount == 0)
            {
            	topDatalog.error("��������ʧ�ܣ�ָ��������id��" + vo.getContentId() + "�Ͳ�Ʒ����:" + vo.getContentName() +" �����ڣ�");
            	result[2] = new Integer(result[2].intValue() + 1);
            }else{
            	//ִ�гɹ�
            	result[1] = new Integer(result[1].intValue() + 1);
            }
        }
        catch (Exception ex)
        {
            topDatalog.error("���浽���ݿ�ʧ�ܣ�����id��" + vo.getContentId() + " �Ͳ�Ʒ����:" + vo.getContentName());
            
            throw new DAOException(ex);
        }
    }
    
    /**
     * ȡ�����һ�γɹ��������ݵ�����ֵ
     * @param type
     * @return
     */
    public String queryLastDate(int type) throws DAOException
    {
        String sqlCode = "Reportdata.TopDataDAO.updateLastDate().SELECT";
        ResultSet rs = null;
        Object[] paras = { new Integer(type) };
        String result = "";
        
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,paras);
            if (rs != null && rs.next())
            {
                result = rs.getString(1);
            }
        }
        catch (Exception ex)
        {
            throw new DAOException("queryLastDate error!!", ex);
        }
        finally
        {
            DB.close(rs);
        }
        
        return result;
    }
    
    /**
     * �������һ�γɹ��������ݵ�����ֵ,�������û�м�¼,������һ��
     * @param type
     * @return
     */
    public void updateLastDate(int type,String lastDate) throws DAOException
    
    {
        String sqlCode = "Reportdata.TopDataDAO.updateLastDate().UPDATE";
        Object[] paras = {lastDate ,new Integer(type)};
        int result = 0;
        try
        {
            result = DB.getInstance().executeBySQLCode(sqlCode, paras);
            if(result == 0)
            {
                //���û�и�������,�Ͳ���һ����¼
                sqlCode = "Reportdata.TopDataDAO.updateLastDate().INSERT";
                Object[] paras2 = {new Integer(type),lastDate};
                DB.getInstance().executeBySQLCode(sqlCode, paras2);
            }
        }
        catch (Exception ex)
        {
            throw new DAOException("updateLastDate error!!", ex);
        }        
    }
}
