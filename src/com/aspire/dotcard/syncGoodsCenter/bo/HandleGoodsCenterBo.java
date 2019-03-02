package com.aspire.dotcard.syncGoodsCenter.bo;

import java.util.ArrayList;
import java.util.List;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.StringUtils;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ppms.APPInfoDAO;
import com.aspire.dotcard.syncAndroid.ppms.GContentVO;
import com.aspire.dotcard.syncAndroid.ppms.HandleContent;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;
import com.aspire.dotcard.syncAndroid.ppms.ReceiveChangeVO;
import com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO;
import com.aspire.dotcard.syncGoodsCenter.vo.GcAppHotInfo;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.risktag.dao.RiskTagDAO;

public class HandleGoodsCenterBo {

	/**
	 * ��־����
	 */
	JLogger LOG = LoggerFactory.getLogger(HandleContent.class);

	private ReceiveChangeVO receiveChangeVO = null;

	public HandleGoodsCenterBo(ReceiveChangeVO receiveChangeVO) {
		if (null == this.receiveChangeVO) {
			this.receiveChangeVO = receiveChangeVO;
		}
	}

	@SuppressWarnings("null")
	public void handleGoodsCenterContent() throws BOException {
        int syncMode = 0;// ͬ��ģʽ��ѡ��һ�����Ӧ��д0�ġ�0:�����������1:Ҫ�����¼���Ϣ��Ҫ����FULLDEVICEID�ֶ�
        
        try
        {
            ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
            syncMode = Integer.parseInt(module.getItemValue("SYNC_MODE"));
        }
        catch (Exception e)
        {
            LOG.error("��ȡModuleConfig������Ϣ�������"+e.getMessage());
        }
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getTransactionInstance();
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		GContentVO vo = null ;
		GcAppHotInfo goodsCenterApp = null ;
		// ��ѯ��Ʒ����APPIDΪ�յ�33Ӧ�õ�����
		goodsCenterApp = SyncDataDAO.findGcAppHotInfoData( receiveChangeVO );
		String voAppid ="";
		
	
		try
        {	
			//��ѯ����t_r_gcontent��.���Ƿ����
            vo = APPInfoDAO.getInstance().getGContentVO(receiveChangeVO.getEntityid());
            voAppid = vo.getAppid();
        }
        catch (Exception e)
        {
            LOG.error("APPInfoDAO.getInstance().getGContentVO(" + receiveChangeVO.getEntityid() + ")����"+e.getMessage());
        }
		
		SyncDataDAO sysDao = SyncDataDAO.getTransactionInstance(tdb);
		synchronized (tdb) {
			
		
		// ��Ʒ����û��,������Ҳû��.���Ӧ��������.....
		if (goodsCenterApp == null ) {
			LOG.info("goodsCenterApp==null ��Ʒ����û���˲�������,���˰�!����" + receiveChangeVO.getEntityid());
			
			if (null == vo) {
				try {
					PPMSDAO.getTransactionInstance(null).updateReceiveChangeNoTran(receiveChangeVO.getId(),"0","0");
				} catch (DAOException e) {
					LOG.error("����û��,��Ʒ����Ҳû��",e);
				}
				return ;
			}
			String transactionID2 = ContextUtil.getTransactionID();
			String gcontentContentid = receiveChangeVO.getEntityid();
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
            String categoryId = module.getItemValue("ROOT_CATEGORYID");
            String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");// ������Ʒ���Ż��������µ���Ӫ����
            
			try {
				PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
				
                dao.downAllReference(categoryId, vo);// ������Ʒ��android��������Ҫ���ߣ�
                dao.downAllOperateReference(operateCategoryId, vo);// ������Ʒ(ָ������Ӫ������Ҫ����)
                sysDao.tADCPPMSServiceDelete(gcontentContentid);
                sysDao.tACmDeviceResourceDelete(gcontentContentid);
                sysDao.vserviceDelete(gcontentContentid);
                sysDao.vcontentlastDelete(gcontentContentid);
                dao.deleteContentid(receiveChangeVO.getEntityid());
				dao.getDelGoodsList(categoryId, vo.getId());   //   917
                String transactionID = ContextUtil.getTransactionID();
                
                // ���������ķ�����Ϣ�����¼ܣ���������ֻ�����ϻ���,������xpas����Ʒ?--------------------------------------------
                dao.addMessages(MSGType.ContentModifyReq, transactionID, receiveChangeVO.getEntityid() + ":9");
                dao.addMessages(MSGType.RefModifyReq, transactionID);
                //dao.addMessages(MSGType.CountUpdateReq, transactionID, receiveChangeVO.getEntityid() + ":9");// ɾ���񵥣�
                // ���޸�PPMS�Ľ�������״̬      
                dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"9");
                
                tdb.commit();
			} catch (Exception e) {
				LOG.error("��Ʒ����û��,�����е�.���������̳���" + gcontentContentid,e);
				tdb.rollback();
			}finally{
				tdb.close();
			}
		}else if(goodsCenterApp != null && vo != null){
			LOG.info("goodsCenterApp!=null&&vo != null,��Ʒ��������,�������ǻ�����,�߸�������!����" + receiveChangeVO.getEntityid());
        	
            long startTime_V = System.currentTimeMillis();
            
                LOG.debug("cost time:���ڵ�ʱ��--�ӵ������õ��Ƿ�����Դ��ʱ�䣺" + (System.currentTimeMillis() - startTime_V));
                long stime = System.currentTimeMillis();
                try
                {	
                	String contentid = receiveChangeVO.getEntityid();
                    String transactionID = ContextUtil.getTransactionID();
                    PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                    String secCategoryType = goodsCenterApp.getGRADE2_TYPE();
                	try {
                		String getNewSecCategoryType = dao.getSecCategory(secCategoryType);
                		if (null !=getNewSecCategoryType ) {
                			//�����µĶ�������
                			goodsCenterApp.setGRADE2_TYPE(getNewSecCategoryType);
    					}
    				} catch (Exception e) {
    					LOG.error("MM���������滻ʧ��...",e);
    				}
                    if ("1".equals(receiveChangeVO.getOpt())) {
                    	goodsCenterApp.setAPPID(receiveChangeVO.getAppid());
					}else {
						if (!StringUtils.isEmpty(vo.getAppid())) {
							goodsCenterApp.setAPPID(vo.getAppid());
						}
					}
                    
                    List gcontentIdList1= sysDao.tRGcontentUpdate(goodsCenterApp);
					//�۸���ɾ�����²���
					List<String> referenceIdList1=sysDao.newStockUpdate(goodsCenterApp,vo.getId());
					String type= sysDao.baseInsert(goodsCenterApp, gcontentIdList1, referenceIdList1);
					sysDao.vserviceDelete(contentid);
					sysDao.vserviceInsert(goodsCenterApp);
                    //��������ĸ��� ��ɾ�������
                    sysDao.tACmDeviceResourceDelete(contentid); 
                    sysDao.tACmDeviceResourceInsert(goodsCenterApp);
                    tdb.commit();
                    if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0) {
                    	// ���������ķ�����Ϣ��Ӧ�����ݱ����--------------------------------------------
                        dao.addMessages(MSGType.ContentModifyReq, transactionID, vo.getContentId() + ":1");
                        //add by tolson
                        if("1".equals(receiveChangeVO.getImagetype())){
    	                    //dao.addMessages(MSGType.ContentModifyReq, transactionID,vo.getContentId() + ":4");
                        }
					}
                    

                     if (syncMode == 1)
                    {	
                    	try {
                    		dao.downCategoryByTactic(vo);
                            
						} catch (Exception e) {
							LOG.debug("������������ʧ��!!!",e);
						}
                    	try {
                    		dao.add33AppCategroyByTactic(goodsCenterApp,type,vo.getId());
						} catch (Exception e) {
							LOG.debug("������������ʧ��!!!",e);
						}
                    	
                        if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0 ) {
                        	dao.addMessages(MSGType.RefModifyReq, transactionID);
						}
                        
                    }
                    dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"1");
                    tdb.commit();
                }
                catch (Exception e)
                {
                    LOG.error("handleContent�����쳣��DB����ع���" + receiveChangeVO, e);
                    System.out.println(e);
                    if (tdb != null)
                    {
                        tdb.rollback();
                    }
                }
                finally
                {
                    if (tdb != null)
                    {
                        tdb.close();
                    }
                }
                LOG.debug("exec vo handleContent2 contentid=" + receiveChangeVO.getEntityid() + ";id=" + receiveChangeVO.getId() + ";" + ";costtime=" + (System.currentTimeMillis() - stime));
            }
        
			
			
			
		else if(goodsCenterApp != null && vo == null){
			LOG.debug("goodsCenterApp==null&&vo == null,��Ʒ��������,�������ǻ��ܻ�û��,����!����" + receiveChangeVO.getEntityid());
            try
            {	
            	PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
            	String secCategoryType = goodsCenterApp.getGRADE2_TYPE();
            	try {
            		String getNewSecCategoryType = dao.getSecCategory(secCategoryType);
            		if (null !=getNewSecCategoryType ) {
            			//�����µĶ�������
            			goodsCenterApp.setGRADE2_TYPE(getNewSecCategoryType);
					}
				} catch (Exception e) {
					LOG.error("MM���������滻ʧ��...",e);
				}
            	if ("1".equals(receiveChangeVO.getOpt())) {
            		//�з���Ӧ�ñ����Ϣ
                	goodsCenterApp.setAPPID(receiveChangeVO.getAppid());
				}
            	List<String> gcontentIdList = new ArrayList<String>();
            	try {
            		gcontentIdList =sysDao.tRGcontentInsert(goodsCenterApp);
				} catch (Exception e) {
					LOG.error(receiveChangeVO.getEntityid()+"t_r_gcontent�����Ѵ���.");
				}
            	if (null == gcontentIdList) {
            		vo = APPInfoDAO.getInstance().getGContentVO(receiveChangeVO.getEntityid());
            		gcontentIdList.add(vo.getId());
				}
                sysDao.tADCPPMSServiceInsert(goodsCenterApp);
                sysDao.tACmDeviceResourceInsert(goodsCenterApp);
				List<String> referenceIdList=sysDao.newStockInsert(goodsCenterApp);
				String type = sysDao.baseInsert(goodsCenterApp, gcontentIdList, referenceIdList);
				//����֮ǰ����ɾ��;����bug
				sysDao.vserviceDelete(goodsCenterApp.getCONTENT_ID());
				sysDao.vserviceInsert(goodsCenterApp);
				sysDao.vcontentlastInsert(goodsCenterApp);
            	//������ܵ����¼����ڻ���ҵ����ִ�� 09-11
				
//                try {
//					if (!StringUtils.isEmpty(dao.getSecCategory(goodsCenterApp.getGRADE2_TYPE()))) {
//						goodsCenterApp.setGRADE2_TYPE(dao.getSecCategory(goodsCenterApp.getGRADE2_TYPE())) ;
//					}
//				} catch (Exception e) {
//					LOG.error("MM���������滻ʧ��...",e);
//				}
				try {
					String transactionID = ContextUtil.getTransactionID();
					//���¼�
	                //dao.downCategoryByTactic(vo);
	                dao.add33AppCategroyByTactic(goodsCenterApp,type,(String)gcontentIdList.get(0));
	                if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0 ) {
                    	dao.addMessages(MSGType.RefModifyReq, transactionID);
					}
				} catch (Exception e) {
					
					LOG.debug("���������ϼ�ʧ��....",e);
					
				}

                dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "","1");
                if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0 ) {
                	//dao.addMessages(MSGType.RefModifyReq, transactionID);
                	sysDao.addMessages(MSGType.ContentModifyReq, null,receiveChangeVO.getEntityid() + ":1");
				}

                tdb.commit();
               
            }
            catch (Exception e)
            {
                LOG.error("handleContent�����쳣��DB����ع���" + receiveChangeVO, e);
                LOG.debug(e);
                if (tdb != null)
                {
                    tdb.rollback();
                }
                throw new BOException("handleContent�����쳣��DB����ع���" + receiveChangeVO, e);

            }
            finally
            {
                if (tdb != null)
                {
                    tdb.close();
                }
            }
		}
		}
		if (!StringUtils.isEmpty(receiveChangeVO.getAppid()) && "1".equals(receiveChangeVO.getOpt())) {
			try {
				HandleContent.grounding(receiveChangeVO,voAppid);
			} catch (DAOException e) {
				LOG.error("�ϼ�ʧ��!!"+receiveChangeVO.getEntityid(),e);
			} //  ���ϼܴ���
		}else if(!StringUtils.isEmpty(receiveChangeVO.getAppid())){
			try {
				HandleContent.undercarriage(receiveChangeVO,vo);
			} catch (DAOException e) {
				LOG.error("�¼�ʧ��!"+receiveChangeVO.getEntityid(),e);
			}  //��opt Ϊ2,����3�Ĵ���
		}
		
		try {
			HandleContent.doGetXpasGAppChangeUrl(receiveChangeVO.getAppid(),receiveChangeVO.getEntityid());
		} catch (Exception e) {
			LOG.error("����xpas �ӿ�gappchangeʧ��",e);
		}
	}
}
