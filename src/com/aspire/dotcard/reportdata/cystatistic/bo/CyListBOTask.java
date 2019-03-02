package com.aspire.dotcard.reportdata.cystatistic.bo;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.cystatistic.dao.CyListDAO;
import com.aspire.dotcard.reportdata.cystatistic.vo.CyListVO;



/**
 * @author tungke
 *
 */
public class CyListBOTask
{

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory.getLogger(CyListBOTask.class);

    /**
     * ����ר����־
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
    
	private CyListVO newVO = null;

	public CyListBOTask(CyListVO vo)
	{
		this.newVO = vo;
	}

	/**
	 * ����
	 * 
	 */
	public void updateCyListVO()
	{
        if (logger.isDebugEnabled())
        {
            logger.debug("updateCyListVO() id=" + newVO.getContentid());
        }

        String[] mutiSQL = { "reportdata.CyListDAO.updateCyListVO.QUERY",
                        "reportdata.CyListDAO.updateCyListVO.INSERT",
                        "reportdata.CyListDAO.updateCyListVO.UPDATE" };
        Object paras[][] = {
                        { newVO.getContentid() },
                        { newVO.getStattime(), newVO.getContentid(),
                                        newVO.getContentname(),
                                        new Long(newVO.getDownloadusernum()),
                                        new Long(newVO.getTestusernum()),
                                        new Long(newVO.getTeststar()),
                                        new Long(newVO.getStarscorecount()),
                                        new Long(newVO.getGlobalscorecount()),
                                        new Long(newVO.getDaydownloadusernum()),
                                        new Long(newVO.getDaytestusernum()),
                                        new Long(newVO.getDayteststar()),
                                        new Long(newVO.getDaystarscorecount()),
                                        new Long(newVO.getDayglobalscorecount())  },
                        { newVO.getStattime(), newVO.getContentname(),
                                        new Long(newVO.getDownloadusernum()),
                                        new Long(newVO.getTestusernum()),
                                        new Long(newVO.getTeststar()),
                                        new Long(newVO.getStarscorecount()),
                                        new Long(newVO.getGlobalscorecount()),
                                        new Long(newVO.getDaydownloadusernum()),
                                        new Long(newVO.getDaytestusernum()),
                                        new Long(newVO.getDayteststar()),
                                        new Long(newVO.getDaystarscorecount()),
                                        new Long(newVO.getDayglobalscorecount()),
                                        newVO.getContentid() } };

        try
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("���봴ҵ������������ start ");
            }
            
            CyListDAO.getInstance().updateCyListVO(mutiSQL, paras);
            REPORT_LOG.debug("���봴ҵ�����������ݣ�contentid=" + this.newVO.getContentid() + " �ɹ���");
        }
        catch (Exception e)
        {
            logger.error("���봴ҵ�����������ݣ�contentid=" + this.newVO.getContentid() + " ʧ�ܣ�", e);
            REPORT_LOG.error("���봴ҵ�����������ݣ�contentid=" + this.newVO.getContentid() + " ʧ�ܣ�",
                             e);
        }

    }
	
}
