package com.aspire.dotcard.syncAndroid.autosync.bo;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.autosync.dao.AutoSyncDAO;
import com.aspire.dotcard.syncAndroid.autosync.vo.AutoVO;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

public class AutoSyncBO
{
	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory.getLogger(AutoSyncBO.class);
	
	private static AutoSyncBO instance = new AutoSyncBO();
	
	private AutoSyncBO()
	{}
	
	/**
	 * �õ�����ģʽ
	 * 
	 */
	public static AutoSyncBO getInstance()
	{
		return instance;
	}
	
	/**
	 * ִ�е������ܷ����������ĸ�����Ϣ
	 * 
	 * @param autoVO
	 * @return
	 */
	public String exceAutoCategory(AutoVO autoVO)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("����������ͬ���Զ����»��ܽ���...");
		}
		
		// ������Ϊ��ʱ��֪ͨ�Է�
		if ("1".equals(autoVO.getIsNullByAuto()))
		{
			// ��ѯ��ǰ�������Ƿ������Ʒ
			if (AutoSyncDAO.getInstance().hasRef(autoVO.getCategoryId()))
			{
				exce(autoVO);
			}
			else
			{
				// ��ǰ���ܲ�������Ʒ
				return "��ǰ���ܲ�������Ʒ������ǰ��������÷���֪ͨ��Ϣ��";
			}
		}
		else
		{
			exce(autoVO);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("����������ͬ���Զ����»��ܽ���...");
		}
		
		return "������Ϣ�ɹ���";
	}
	
	/**
	 * ���ɴ������������ĵ���Ϣ
	 * @param autoVO
	 * @throws BOException 
	 */
	public void exce(AutoVO autoVO) 
	{
		// ��������id
		String transactionID = ContextUtil.getTransactionID();
		
		// ���ܱ����Ϣ�ӿ�
		String cid = null;	
			cid = AutoSyncDAO.getInstance().getCategoryCId(autoVO.getCategoryId());
		 if(cid !=  null && !cid.equals("")){
			 PPMSDAO.addMessagesStatic(MSGType.CatogoryModifyReq, transactionID,
						cid+":1"); 
		 }else{
			 logger.error("cid = SSMSDAO.getCategoryCId(autoVO.getCategoryId()) is null"); 
		 }
		
		// ȫ���������¼ܸ��½ӿ�
		PPMSDAO.addMessagesStatic(MSGType.BatchRefModifyReq, transactionID,
				autoVO.getCategoryId());
	}
}
