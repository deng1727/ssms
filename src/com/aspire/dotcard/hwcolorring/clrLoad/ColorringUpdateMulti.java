/**
 * <p>
 *  单个彩铃试听更新处理类
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 18, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.dotcard.hwcolorring.clrLoad;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;


import com.aspire.ponaadmin.web.util.StringTool;

/**
 * @author dongke
 *
 */
public class ColorringUpdateMulti
{
	
	 /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(ColorringUpdateMulti.class);
 
   
	private GColorring newVO = null;
	

	
	public ColorringUpdateMulti( GColorring vo)
	{
		this.newVO = vo;
	}
public void updateColorring(){
	try
    {
		if(logger.isDebugEnabled())
		{
			logger.debug("更新彩铃："+this.newVO.getId());
		}
        final GColorring oldVO = ColorContentDAO.getInstance().getGColorringById(newVO.getId());
        	//( GColorring ) Repository.getInstance()
                                 //                   .getNode(newVO.getId(),
                                 //                            GColorring.TYPE_COLORRING);
        oldVO.setName(StringTool.formatByLen(newVO.getName(),47,"..."));
        oldVO.setTonenameletter(newVO.getTonenameletter());
        oldVO.setSinger(newVO.getSinger());
        oldVO.setSingerletter(newVO.getSingerletter());
        oldVO.setIntroduction(newVO.getIntroduction());
        oldVO.setPrice(newVO.getPrice());
        oldVO.setLupdDate(newVO.getLupdDate());
        oldVO.setDownloadtimes(newVO.getDownloadtimes());
        oldVO.setSettimes(newVO.getSettimes());
        oldVO.setAuditionUrl(newVO.getAuditionUrl());
        oldVO.setTonebigtype(newVO.getTonebigtype());
        oldVO.setCateName(newVO.getCateName());
        oldVO.setExpire(newVO.getExpire());
        //oldVO.save();
        ColorContentDAO.getInstance().updateColorring(oldVO);
        
		// 如果同步过来才彩铃数据的最后更新时间大于系统中存在的彩铃数据的最后更新时间，需要重新转换试听文件
		if (newVO.getLupdDate().compareTo(oldVO.getLupdDate()) > 0
				|| "".equals(oldVO.getClientAuditionUrl()))
		{
			//oldVO.setClientAuditionUrl(newVO.getClientAuditionUrl());
			/*new Thread(new Runnable() {
				public void run()
				{
					ColorContentBO.getInstance().convertColorring(oldVO);
				}
			}).start();//需要启动另外一个线程去调用，否则容易产生死锁。
*/			
			ColorContentBO.getInstance().convertColorring(oldVO);
		}
    }
    catch (Exception e)
    {
        logger.debug("更新彩铃："+this.newVO.getId()+" 失败！",e);
    }
}
}
