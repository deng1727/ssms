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
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory.getLogger(CyListBOTask.class);

    /**
     * 报表专用日志
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
    
	private CyListVO newVO = null;

	public CyListBOTask(CyListVO vo)
	{
		this.newVO = vo;
	}

	/**
	 * 插入
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
                logger.debug("插入创业大赛报表数据 start ");
            }
            
            CyListDAO.getInstance().updateCyListVO(mutiSQL, paras);
            REPORT_LOG.debug("插入创业大赛报表数据：contentid=" + this.newVO.getContentid() + " 成功！");
        }
        catch (Exception e)
        {
            logger.error("插入创业大赛报表数据：contentid=" + this.newVO.getContentid() + " 失败！", e);
            REPORT_LOG.error("插入创业大赛报表数据：contentid=" + this.newVO.getContentid() + " 失败！",
                             e);
        }

    }
	
}
