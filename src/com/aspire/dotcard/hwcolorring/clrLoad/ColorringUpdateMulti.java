/**
 * <p>
 *  ���������������´�����
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
     * ��־����
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
			logger.debug("���²��壺"+this.newVO.getId());
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
        
		// ���ͬ�������Ų������ݵ�������ʱ�����ϵͳ�д��ڵĲ������ݵ�������ʱ�䣬��Ҫ����ת�������ļ�
		if (newVO.getLupdDate().compareTo(oldVO.getLupdDate()) > 0
				|| "".equals(oldVO.getClientAuditionUrl()))
		{
			//oldVO.setClientAuditionUrl(newVO.getClientAuditionUrl());
			/*new Thread(new Runnable() {
				public void run()
				{
					ColorContentBO.getInstance().convertColorring(oldVO);
				}
			}).start();//��Ҫ��������һ���߳�ȥ���ã��������ײ���������
*/			
			ColorContentBO.getInstance().convertColorring(oldVO);
		}
    }
    catch (Exception e)
    {
        logger.debug("���²��壺"+this.newVO.getId()+" ʧ�ܣ�",e);
    }
}
}
