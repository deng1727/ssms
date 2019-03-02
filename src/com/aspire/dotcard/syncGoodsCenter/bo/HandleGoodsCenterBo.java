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
	 * 日志引用
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
        int syncMode = 0;// 同步模式的选择，一般情况应该写0的。0:是正常情况，1:要有上下架消息，要处理FULLDEVICEID字段
        
        try
        {
            ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
            syncMode = Integer.parseInt(module.getItemValue("SYNC_MODE"));
        }
        catch (Exception e)
        {
            LOG.error("获取ModuleConfig配置信息错误出错！"+e.getMessage());
        }
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getTransactionInstance();
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		GContentVO vo = null ;
		GcAppHotInfo goodsCenterApp = null ;
		// 查询商品中心APPID为空的33应用的数据
		goodsCenterApp = SyncDataDAO.findGcAppHotInfoData( receiveChangeVO );
		String voAppid ="";
		
	
		try
        {	
			//查询本地t_r_gcontent表.看是否存在
            vo = APPInfoDAO.getInstance().getGContentVO(receiveChangeVO.getEntityid());
            voAppid = vo.getAppid();
        }
        catch (Exception e)
        {
            LOG.error("APPInfoDAO.getInstance().getGContentVO(" + receiveChangeVO.getEntityid() + ")出错！"+e.getMessage());
        }
		
		SyncDataDAO sysDao = SyncDataDAO.getTransactionInstance(tdb);
		synchronized (tdb) {
			
		
		// 商品中心没有,货架里也没有.这个应用有问题.....
		if (goodsCenterApp == null ) {
			LOG.info("goodsCenterApp==null 商品中心没有了不管怎样,下了吧!！！" + receiveChangeVO.getEntityid());
			
			if (null == vo) {
				try {
					PPMSDAO.getTransactionInstance(null).updateReceiveChangeNoTran(receiveChangeVO.getId(),"0","0");
				} catch (DAOException e) {
					LOG.error("货架没有,商品中心也没有",e);
				}
				return ;
			}
			String transactionID2 = ContextUtil.getTransactionID();
			String gcontentContentid = receiveChangeVO.getEntityid();
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
            String categoryId = module.getItemValue("ROOT_CATEGORYID");
            String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");// 不在商品库优化根货架下的运营货架
            
			try {
				PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
				
                dao.downAllReference(categoryId, vo);// 下线商品（android根货架下要下线）
                dao.downAllOperateReference(operateCategoryId, vo);// 下线商品(指定的运营货架下要下线)
                sysDao.tADCPPMSServiceDelete(gcontentContentid);
                sysDao.tACmDeviceResourceDelete(gcontentContentid);
                sysDao.vserviceDelete(gcontentContentid);
                sysDao.vcontentlastDelete(gcontentContentid);
                dao.deleteContentid(receiveChangeVO.getEntityid());
				dao.getDelGoodsList(categoryId, vo.getId());   //   917
                String transactionID = ContextUtil.getTransactionID();
                
                // 与数据中心发送消息（上下架）数据中心只处理老货架,不处理xpas的商品?--------------------------------------------
                dao.addMessages(MSGType.ContentModifyReq, transactionID, receiveChangeVO.getEntityid() + ":9");
                dao.addMessages(MSGType.RefModifyReq, transactionID);
                //dao.addMessages(MSGType.CountUpdateReq, transactionID, receiveChangeVO.getEntityid() + ":9");// 删除榜单！
                // 回修改PPMS的接受数据状态      
                dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"9");
                
                tdb.commit();
			} catch (Exception e) {
				LOG.error("商品中心没有,货架有的.走下线流程出错" + gcontentContentid,e);
				tdb.rollback();
			}finally{
				tdb.close();
			}
		}else if(goodsCenterApp != null && vo != null){
			LOG.info("goodsCenterApp!=null&&vo != null,商品中心有了,但是我们货架有,走更新流程!！！" + receiveChangeVO.getEntityid());
        	
            long startTime_V = System.currentTimeMillis();
            
                LOG.debug("cost time:存在的时候--从电子流得到是否有资源耗时间：" + (System.currentTimeMillis() - startTime_V));
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
                			//设置新的二级分类
                			goodsCenterApp.setGRADE2_TYPE(getNewSecCategoryType);
    					}
    				} catch (Exception e) {
    					LOG.error("MM二级分类替换失败...",e);
    				}
                    if ("1".equals(receiveChangeVO.getOpt())) {
                    	goodsCenterApp.setAPPID(receiveChangeVO.getAppid());
					}else {
						if (!StringUtils.isEmpty(vo.getAppid())) {
							goodsCenterApp.setAPPID(vo.getAppid());
						}
					}
                    
                    List gcontentIdList1= sysDao.tRGcontentUpdate(goodsCenterApp);
					//价格表的删除更新操作
					List<String> referenceIdList1=sysDao.newStockUpdate(goodsCenterApp,vo.getId());
					String type= sysDao.baseInsert(goodsCenterApp, gcontentIdList1, referenceIdList1);
					sysDao.vserviceDelete(contentid);
					sysDao.vserviceInsert(goodsCenterApp);
                    //机型适配的更新 先删除再添加
                    sysDao.tACmDeviceResourceDelete(contentid); 
                    sysDao.tACmDeviceResourceInsert(goodsCenterApp);
                    tdb.commit();
                    if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0) {
                    	// 与数据中心发送消息（应用数据变更）--------------------------------------------
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
							LOG.debug("二级分类下线失败!!!",e);
						}
                    	try {
                    		dao.add33AppCategroyByTactic(goodsCenterApp,type,vo.getId());
						} catch (Exception e) {
							LOG.debug("二级分类上线失败!!!",e);
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
                    LOG.error("handleContent出现异常。DB事务回滚。" + receiveChangeVO, e);
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
			LOG.debug("goodsCenterApp==null&&vo == null,商品中心有了,但是我们货架还没有,上线!！！" + receiveChangeVO.getEntityid());
            try
            {	
            	PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
            	String secCategoryType = goodsCenterApp.getGRADE2_TYPE();
            	try {
            		String getNewSecCategoryType = dao.getSecCategory(secCategoryType);
            		if (null !=getNewSecCategoryType ) {
            			//设置新的二级分类
            			goodsCenterApp.setGRADE2_TYPE(getNewSecCategoryType);
					}
				} catch (Exception e) {
					LOG.error("MM二级分类替换失败...",e);
				}
            	if ("1".equals(receiveChangeVO.getOpt())) {
            		//有发送应用变更消息
                	goodsCenterApp.setAPPID(receiveChangeVO.getAppid());
				}
            	List<String> gcontentIdList = new ArrayList<String>();
            	try {
            		gcontentIdList =sysDao.tRGcontentInsert(goodsCenterApp);
				} catch (Exception e) {
					LOG.error(receiveChangeVO.getEntityid()+"t_r_gcontent内容已存在.");
				}
            	if (null == gcontentIdList) {
            		vo = APPInfoDAO.getInstance().getGContentVO(receiveChangeVO.getEntityid());
            		gcontentIdList.add(vo.getId());
				}
                sysDao.tADCPPMSServiceInsert(goodsCenterApp);
                sysDao.tACmDeviceResourceInsert(goodsCenterApp);
				List<String> referenceIdList=sysDao.newStockInsert(goodsCenterApp);
				String type = sysDao.baseInsert(goodsCenterApp, gcontentIdList, referenceIdList);
				//插入之前先做删除;避免bug
				sysDao.vserviceDelete(goodsCenterApp.getCONTENT_ID());
				sysDao.vserviceInsert(goodsCenterApp);
				sysDao.vcontentlastInsert(goodsCenterApp);
            	//分类货架的上下架留在货架业务里执行 09-11
				
//                try {
//					if (!StringUtils.isEmpty(dao.getSecCategory(goodsCenterApp.getGRADE2_TYPE()))) {
//						goodsCenterApp.setGRADE2_TYPE(dao.getSecCategory(goodsCenterApp.getGRADE2_TYPE())) ;
//					}
//				} catch (Exception e) {
//					LOG.error("MM二级分类替换失败...",e);
//				}
				try {
					String transactionID = ContextUtil.getTransactionID();
					//先下架
	                //dao.downCategoryByTactic(vo);
	                dao.add33AppCategroyByTactic(goodsCenterApp,type,(String)gcontentIdList.get(0));
	                if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0 ) {
                    	dao.addMessages(MSGType.RefModifyReq, transactionID);
					}
				} catch (Exception e) {
					
					LOG.debug("二级分类上架失败....",e);
					
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
                LOG.error("handleContent出现异常。DB事务回滚。" + receiveChangeVO, e);
                LOG.debug(e);
                if (tdb != null)
                {
                    tdb.rollback();
                }
                throw new BOException("handleContent出现异常。DB事务回滚。" + receiveChangeVO, e);

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
				LOG.error("上架失败!!"+receiveChangeVO.getEntityid(),e);
			} //  做上架处理
		}else if(!StringUtils.isEmpty(receiveChangeVO.getAppid())){
			try {
				HandleContent.undercarriage(receiveChangeVO,vo);
			} catch (DAOException e) {
				LOG.error("下架失败!"+receiveChangeVO.getEntityid(),e);
			}  //做opt 为2,或者3的处理
		}
		
		try {
			HandleContent.doGetXpasGAppChangeUrl(receiveChangeVO.getAppid(),receiveChangeVO.getEntityid());
		} catch (Exception e) {
			LOG.error("调用xpas 接口gappchange失败",e);
		}
	}
}
