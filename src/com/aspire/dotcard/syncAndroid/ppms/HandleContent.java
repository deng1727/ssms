/**
 * 
 */
package com.aspire.dotcard.syncAndroid.ppms;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpsURL;
import org.apache.struts.actions.DownloadAction;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.HttpUtil;
import com.aspire.common.util.StringUtils;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.mm.common.client.httpsend.HttpUtils;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.goods.GoodsVO;
import com.aspire.ponaadmin.web.repository.singlecategory.vo.SingleCategoryVO;
import com.aspire.ponaadmin.web.risktag.dao.RiskTagDAO;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.jcraft.jsch.Logger;

/**
 * @author dongke
 */
public class HandleContent
{

    /**
     * 日志引用
     */
    static JLogger LOG = LoggerFactory.getLogger(HandleContent.class);

    private ReceiveChangeVO receiveChangeVO = null;
    
    public HandleContent(ReceiveChangeVO receiveChangeVO)
    {

        if (null == this.receiveChangeVO)
        {
            this.receiveChangeVO = receiveChangeVO;
        }
    }

    public  void handleContent() throws BOException
    {

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
        
        //内容有变更发送时时消息到安全中心,2015-07-20 add
        boolean isSendSafeCenterNotify = false;
        int online = 1;
        // 查询本地t_r_gcontent表，
        
        // 1、存在：则修改，如果二级分类也改变，则做上下架操作
        // 2、不存在，插入到表t_r_gcontent，上架到对应的二级分类下。
        CmContentVO dccVo = null;
        GContentVO vo = null;
        String voAppid = "";
        long starttime2 = System.currentTimeMillis();
        try
        {
            dccVo = APPInfoDAO.getInstance().getCmContent(receiveChangeVO.getEntityid());

        }
        catch (Exception e)
        {
            LOG.error("APPInfoDAO.getInstance().getCmContent(" + receiveChangeVO.getEntityid() + ")出错！"+e.getMessage());
        }
        
        try
        {
            vo = APPInfoDAO.getInstance().getGContentVO(receiveChangeVO.getEntityid());
            voAppid = vo.getAppid();
        }
        catch (Exception e)
        {
            LOG.error("APPInfoDAO.getInstance().getGContentVO(" + receiveChangeVO.getEntityid() + ")出错！"+e.getMessage());
        }

        LOG.debug("exec vo handleContent1 contentid=" + receiveChangeVO.getEntityid() + ";id=" + receiveChangeVO.getId() + ";" + ";costtime=" + (System.currentTimeMillis() - starttime2));
        //2015-09-30 add ,触点合作商渠道分发类型(channeldisptype)：0：未选择自有分发（MM客户端）、1：已选择自有分发（合作商只有客户端）
        //channeldisptype为1:已选择自有分发的应用不上架到MM客户端货架，
        //这里合作商自有应用，不上架到MM，上架到合作商对应的根货架
        if(dccVo != null && "1".equals(dccVo.getChanneldisptype()))
        {
        	 if (!PPMSDAO.validateResource(receiveChangeVO.getEntityid()))
             {
                 try
                 {
                     new PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(), "-2" ,"-2");
                 }
                 catch (DAOException e)
                 {
                     LOG.error("subtype="+dccVo.getSubtype()+"(channeldisptype)：0：未选择自有分发（MM客户端）、1：已选择自有分发（合作商只有客户端）没有资源："+receiveChangeVO.getId());
                 }

                 return;
             }

             LOG.debug(dccVo.getContentid() + " is channeldisptype="+dccVo.getChanneldisptype()+" content... vo!=null" + (vo != null));
             TransactionDB tdb = null;
             try
             {
                 tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。
                 synchronized (tdb) {
                 PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                 // String transactionID = ContextUtil.getTransactionID();
                 if (vo != null)
                 {
                     dccVo.setId(vo.getId());
                     // 修改
                     dao.updateGContent(dccVo);
                     dao.addMessages(MSGType.ContentModifyReq, null, dccVo.getContentid() + ":1");
                     dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "","1");
                 }
                 else
                 {
                     String newId = dao.insertGContent(dccVo);
                     dccVo.setId(newId);
                     //根据合作渠道商配置的根货架上架到对应的根货架
                     dao.addCategroyByCategoryId(dccVo);
                     dao.addMessages(MSGType.ContentModifyReq, null, dccVo.getContentid() + ":0");
                     dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "","0");
                 }

                 dao.updateVService(dccVo.getContentid());// 价格信息。
                 dao.updateDeviceResourseByCid(dccVo.getContentid());// 更新他的适配关系。add
                 // by aiyan
                 // 2013-04-07
                 // 更新程序包的最后更新时间（目的是要改变select * from
                 // v_content_last的内容，在发送榜单消息的地方需要程序包的最后更新时间）
                 dao.updateLastTime(dccVo.getContentid());
                 //dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "");
                 
                 tdb.commit();
            	 
                 if (syncMode == 1)
                 {// 修改的话，把FULLDEVICE弄一下。add
                     // by aiyan 2013-06-19
                     Map<String, List<String>> map = getFullDevice(vo);
                     List<String> fullDeviceIdList = map.get("fullDeviceIdList");
                     List<String> fullDeviceNameList = map.get("fullDeviceNameList");
                     dao.updateFullDevice(vo.getContentId(), fullDeviceIdList, fullDeviceNameList);

                 }
            	 }
                 isSendSafeCenterNotify = true;
             }
             catch (Exception e)
             {
                 if (tdb != null)
                 {
                     tdb.rollback();
                 }
                 LOG.error(e);
             }
             finally
             {
                 if (tdb != null)
                 {
                     tdb.close();
                 }
             }
             return;
        }
        
        // 这里首先要过滤掉HTC应用。因为HTC应用是不上MM的二级分类货架。
        // HTC应用或套餐应用这里处理（HTC的上下架在老的同步完成。而套餐应用是不上二级分类货架。
        // 这两者的处理过程在商品库优化就是相同的了。就是把各个信息通过过来，不考虑上下架事情。）16:HTC应用，11:套餐应用
        //(add 2014-01-07) 定制类App不上架 21：定制类App        
        if (dccVo != null && ( "11".equals(dccVo.getSubtype())|| "21".equals(dccVo.getSubtype())||"1".equals(dccVo.getChanneldisptype())|| "5".equals(dccVo.getApptype())))
        {

            if (!PPMSDAO.validateResource(receiveChangeVO.getEntityid()))
            {
                try
                {
                    new PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(), "-2" ,"-2");
                }
                catch (DAOException e)
                {
                    LOG.error("subtype="+dccVo.getSubtype()+" (16:HTC应用，11:套餐应用，21：定制类App：) 没有资源："+receiveChangeVO.getId());
                }

                return;
            }

            LOG.debug(dccVo.getContentid() + " is subtype="+dccVo.getSubtype()+" content... vo!=null" + (vo != null));
            TransactionDB tdb = null;
            try
            {
                tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。
                synchronized (tdb) {
					
				
                PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                // String transactionID = ContextUtil.getTransactionID();
                if (vo != null)
                {
                    dccVo.setId(vo.getId());
                    // 修改
                    dao.updateGContent(dccVo);
                    dao.addMessages(MSGType.ContentModifyReq, null, dccVo.getContentid() + ":1");
                    dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "","1");
                }
                else
                {
                    String newId = dao.insertGContent(dccVo);
                    dao.addMessages(MSGType.ContentModifyReq, null, dccVo.getContentid() + ":0");
                    dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "","0");
                }

                dao.updateVService(dccVo.getContentid());// 价格信息。
                dao.updateDeviceResourseByCid(dccVo.getContentid());// 更新他的适配关系。add
                // by aiyan
                // 2013-04-07
                // 更新程序包的最后更新时间（目的是要改变select * from
                // v_content_last的内容，在发送榜单消息的地方需要程序包的最后更新时间）
                dao.updateLastTime(dccVo.getContentid());
                //dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "");
                tdb.commit();

                if (syncMode == 1)
                {// 修改的话，把FULLDEVICE弄一下。add
                    // by aiyan 2013-06-19
                    Map<String, List<String>> map = getFullDevice(vo);
                    List<String> fullDeviceIdList = map.get("fullDeviceIdList");
                    List<String> fullDeviceNameList = map.get("fullDeviceNameList");
                    dao.updateFullDevice(vo.getContentId(), fullDeviceIdList, fullDeviceNameList);

                }
                isSendSafeCenterNotify = true;
                //将device表中的risktag放入gcontent表中
                String deviceRiskTag = dao.getDeviceRiskTag(dccVo.getContentid());
                if (!StringUtils.isEmpty(deviceRiskTag)) {
                	 String gcontentRiskTag = dao.getGcontentRiskTag(dccVo.getContentid());
                	 
                	 if (StringUtils.isEmpty(gcontentRiskTag)) {
                		 //如果gcontent表中为空则直接插入
						dao.updateGcontentRiskTag(dccVo.getContentid(), deviceRiskTag);
						tdb.commit();
					}else {
						//如果gcontent表中不为空,则拼接deviceRiskTag;gcontentRiskTag
						String riskTag=deviceRiskTag+";"+gcontentRiskTag;
						String contentid = dccVo.getContentid();
						LOG.debug("请求参数riskTag:"+riskTag);
						LOG.debug("请求参数contentid:"+contentid);
						dao.updateGcontentRiskTag(contentid, riskTag);
						tdb.commit();
					}
				}
                }
            }
            catch (Exception e)
            {
                if (tdb != null)
                {
                    tdb.rollback();
                }
                LOG.error(e);
            }
            finally
            {
                if (tdb != null)
                {
                    tdb.close();
                }
            }
            return;
        }

        if (dccVo == null && vo == null)
        {
            LOG.info("dccVo==null&&vo == null,说明电子流和货架都没有这个应用，有问题的数据啊！！" + receiveChangeVO.getEntityid());

            // 将t_a_ppms_receive_change的status值为-3;
            TransactionDB tdb = null;
            try
            {
            	tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。
                synchronized(tdb){
            	PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                dao.addMessages(MSGType.ContentModifyReq, "", receiveChangeVO.getEntityid() + ":9");//add by tungke 20131206 
                //dao.updateReceiveChangeNoTran(receiveChangeVO.getId(), "-3");
                dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"9");
                dao.deleteContentid(receiveChangeVO.getEntityid());
                dao.deldeviceUpgrade(receiveChangeVO.getEntityid());//应用下线删除应用升级信息表 add by 20140325
                tdb.commit();
                }
            }
            
            catch (Exception e)
            {
                // TODO Auto-generated catch block
            	 if (tdb != null)
                 {
                     tdb.rollback();
                 }
                 LOG.error(e);
              
            }  finally
            {
                if (tdb != null)
                {
                    tdb.close();
                }
            }
            LOG.error("无效的电子流实体：" + vo);

            return;
        }

        if (dccVo == null && vo != null)
        { //电子流没有，货架有，下线
            // 下架ANDROID根货架下所有该CONTENTID的商品。
            ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
            String categoryId = module.getItemValue("ROOT_CATEGORYID");
            String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");// 不在商品库优化根货架下的运营货架
            TransactionDB tdb = null;
            try
            {
                tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。
                synchronized (tdb) {
					
				
                PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                // 下线开始咯。。。。。
                dao.downAllReference(categoryId, vo);// 下线商品（android根货架下要下线）
                dao.downAllOperateReference(operateCategoryId, vo);// 下线商品(指定的运营货架下要下线)
                
                dao.updateDeviceResourseByCid(receiveChangeVO.getEntityid());// 下线他的适配关系。add
                // by
                // aiyan
                // 2013-04-07

                dao.getDelGoodsList(categoryId, vo.getId());
                String transactionID = ContextUtil.getTransactionID();
                // 与数据中心发送消息（上下架）--------------------------------------------
                dao.addMessages(MSGType.ContentModifyReq, transactionID, receiveChangeVO.getEntityid() + ":9");
                
                dao.deldeviceUpgrade(receiveChangeVO.getEntityid());//应用下线删除应用升级信息表 2014-03-25
                dao.addMessages(MSGType.RefModifyReq, transactionID);
                // removed by aiyan 2013-06-01
                // 终端门户以及对适配的处理又把他当做应用变更处理。这样就重复做事情了，故丢掉。
                // dao.addMessages(MSGType.ContentModifyReq,
                // transactionID,receiveChangeVO.getEntityid() +
                // ":2");//下线他的适配关系。add by aiyan 2013-04-07
                dao.addMessages(MSGType.CountUpdateReq, transactionID, receiveChangeVO.getEntityid() + ":9");// 删除榜单！
                // 回修改PPMS的接受数据状态      
                dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"9");
                dao.deleteContentid(receiveChangeVO.getEntityid());
                tdb.commit();
                isSendSafeCenterNotify = true;
                online = 0;
            }
            }
            catch (DAOException e)
            {
                if (tdb != null)
                {
                    tdb.rollback();
                }
                // TODO Auto-generated catch block
                // e.printStackTrace();
                LOG.error(e);
            }
            finally
            {
                if (tdb != null)
                {
                    tdb.close();
                }
            }
        }

        if (dccVo != null && vo != null)
        {//电子流有，货架也有
      
        	
            // /验证没有适配的，下线！！
            long startTime_V = System.currentTimeMillis();
            if (!PPMSDAO.validateResource(receiveChangeVO.getEntityid()))
            {
                LOG.debug("cost time:不存在的时候--从电子流得到是否有资源耗时间：" + (System.currentTimeMillis() - startTime_V));
                // 下架ANDROID根货架下所有该CONTENTID的商品。
                ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
                String categoryId = module.getItemValue("ROOT_CATEGORYID");
                String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");// 不在商品库优化根货架下的运营货架
                TransactionDB tdb = null;
                // Producer producer = null;
                try
                {
                    tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。
                    synchronized (tdb) {
						
					
                    PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                    // 下线开始咯。。。。。
                    dao.downAllReference(categoryId, vo);// 下线商品
                    dao.downAllOperateReference(operateCategoryId, vo);// 下线商品(指定的运营货架下要下线)

                    dao.updateDeviceResourseByCid(receiveChangeVO.getEntityid());// 下线他的适配关系。add by aiyan
                    // 2013-04-07

                    dao.getDelGoodsList(categoryId, vo.getId());
                    String transactionID = ContextUtil.getTransactionID();
                    // 与数据中心发送消息（上下架）--------------------------------------------
                    if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0) {
                    	dao.addMessages(MSGType.ContentModifyReq, transactionID, receiveChangeVO.getEntityid() + ":9");
                        dao.addMessages(MSGType.RefModifyReq, transactionID);
                        // removed by aiyan 2013-06-01
                        // 终端门户以及对适配的处理又把他当做应用变更处理。这样就重复做事情了，故丢掉。
                        // dao.addMessages(MSGType.ContentModifyReq,
                        // transactionID,receiveChangeVO.getEntityid() +
                        // ":2");//下线他的适配关系。add by aiyan 2013-04-07
                        dao.addMessages(MSGType.CountUpdateReq, transactionID, receiveChangeVO.getEntityid() + ":9");// 删除榜单！
					}
                    
                    // 回修改PPMS的接受数据状态
                    dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"9");
                    dao.deleteContentid(receiveChangeVO.getEntityid());
                    dao.deldeviceUpgrade(receiveChangeVO.getEntityid());//应用下线删除应用升级信息表 2014-03-25
                    
                    tdb.commit();
                    
                    if (syncMode == 1)
                    {
                        long startTime = System.currentTimeMillis();
                        Map<String, List<String>> map = getFullDevice(vo);
                        List<String> fullDeviceIdList = map.get("fullDeviceIdList");
                        List<String> fullDeviceNameList = map.get("fullDeviceNameList");
                        dao.updateFullDevice(vo.getContentId(), fullDeviceIdList, fullDeviceNameList);
                        LOG.debug("cost time:getFullDevice耗时间：" + (System.currentTimeMillis() - startTime));
                    }
                    }
                    
                    isSendSafeCenterNotify = true;
                    online = 0;
                }
                catch (DAOException e)
                {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                    if (tdb != null)
                    {
                        tdb.rollback();
                    }
                    LOG.error(e);
                }
                finally
                {
                    if (tdb != null)
                    {
                        tdb.close();
                    }
                }

                //		
                // ///下适配。更新。法消息。
                //		
                // TransactionDB tdb = null;
                // try{
                // LOG.debug("(dccVo!=null&&vo!=null)没有适配哦"+receiveChangeVO.getEntityid());
                //			
                // tdb = TransactionDB.getTransactionInstance();//
                // 一个新的JDBC事务开始了。
                // PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                // dao.updateDeviceResourseByCid(receiveChangeVO.getEntityid());
                // dao.addMessages(MSGType.ContentModifyReq,
                // null,receiveChangeVO.getEntityid() + ":2");//下线他的适配关系。add by
                // aiyan 2013-04-25
                // // 回修改PPMS的接受数据状态
                // dao.updateReceiveChange(receiveChangeVO.getId(),
                // Constant.MESSAGE_HANDLE_STATUS_SUCC + "");
                // tdb.commit();
                // }catch(Exception e){
                // tdb.rollback();
                // LOG.debug("(dccVo!=null&&vo!=null)没有适配哦,这里要下适配，且发消息。出错了！"+receiveChangeVO.getEntityid(),e);
                // }finally{
                // if(tdb!=null){
                // tdb.close();
                // }
                // }
                // 下面是以前的逻辑，被注销了。REMOVED BY AIYAN 2013-04-25
                // try{
                // new
                // PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(),"-2");
                // }catch(Exception e){
                // LOG.error("无效的电子流实体,没有适配！hehe："+vo);
                // }
                // return;
            }
            else
            {
                LOG.debug("cost time:存在的时候--从电子流得到是否有资源耗时间：" + (System.currentTimeMillis() - startTime_V));
                long stime = System.currentTimeMillis();

                TransactionDB tdb = null;
                try
                {
                    String transactionID = ContextUtil.getTransactionID();
                    tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。目标是希望下面两个方法都在这个事务里面完成。
                    synchronized (tdb) {
						
					
                    PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                    if ("1".equals(receiveChangeVO.getOpt())) {
                    	dccVo.setAppid(receiveChangeVO.getAppid());
					}else {
						//如果不是下架,沿用之前的appid,如果trgcontent表内有的话
						if (!StringUtils.isEmpty(vo.getAppid())) {
							dccVo.setAppid(vo.getAppid());
						};
					}
                    dccVo.setId(vo.getId());
                    
                    // 修改
                    dao.updateGContent(dccVo);
                    dao.updateVService(vo.getContentId());// 价格信息。
                    dao.updateDeviceResourseByCid(vo.getContentId());// 更新他的适配关系。add
                    // by
                    // aiyan
                    // 2013-04-07
                    // 更新程序包的最后更新时间（目的是要改变select * from
                    // v_content_last的内容，在发送榜单消息的地方需要程序包的最后更新时间）
                    dao.updateLastTime(vo.getContentId());
                    if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0) {
                    	// 与数据中心发送消息（应用数据变更）--------------------------------------------
                        dao.addMessages(MSGType.ContentModifyReq, transactionID, vo.getContentId() + ":1");
                        //add by tolson
                        if("1".equals(receiveChangeVO.getImagetype())){
    	                    dao.addMessages(MSGType.ContentModifyReq, transactionID,vo.getContentId() + ":4");
                        }
					}
                    
                    // removed by aiyan 2013-05-27大胖说有1（上一个语句）就不用2了
                    // dao.addMessages(MSGType.ContentModifyReq, transactionID,
                    // vo.getContentId()+ ":2");

                    // removed by aiyan 2013-09-06 榜单消息已经以一小时一次处理。
                    // dao.addMessages(MSGType.CountUpdateReq, transactionID,
                    // receiveChangeVO.getEntityid() + ":0");// 新增榜单！//add
                    // // by aiyan
                    // // 2013-05-21

                    // 终端门户那边有时候要搞全量同步，这里就就把模式换成1的。好上下架消息。 add by aiyan
                    // 2013-06-19
                    if (syncMode == 0)
                    {
                        // 这个地方比较特别，因为T_R_GCONTENT表应用在下线的时候没有删除表中的记录，故
                        // 出现dccVo!=null&&vo!=null下的情况，会有ANDROID根货架没有商品。这里弥补
                        boolean isExistRef = dao.isExistRefInAndrodCategory(vo);
                        if (!isExistRef)
                        {
                        	try {
                        		// transactionID = ContextUtil.getTransactionID();
                                // dao.downCategoryByTactic(vo);//这个地方可以注销，也可以不注销，先注销一下吧。。。。
                                dao.addCategroyByTactic(dccVo);
                                // dao.updateDeviceResourseByCid(vo.getContentId());//下线他的适配关系。add
                                // by aiyan 2013-04-07
                                // 与数据中心发送消息（上下架）--------------------------------------------
                                // dao.addMessages(MSGType.ContentModifyReq,
                                // transactionID,receiveChangeVO.getEntityid() +
                                // ":2");//适配关系。add by aiyan 2013-04-07
                                if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0 ) {
                                	 dao.addMessages(MSGType.RefModifyReq, transactionID);
    							}
                               
                                // dao.addMessages(MSGType.CountUpdateReq,
                                // transactionID,receiveChangeVO.getEntityid()+":0");//新增榜单！刚开始到货价，是不会有啥统计数据的，故丢弃榜单消息//remove
                                // by aiyan 2013-05-21
							} catch (Exception e) {
								LOG.debug("二级分类上架失败!",e);
							}
                            
                        }
                        else
                        {
                            if (!vo.getAppCateID().equals(dccVo.getAppCateID()))
                            {
                                // 如2级分类修改：做上下架操作
                            	try {
                            		
                                    // 与数据中心发送消息（上下架）--------------------------------------------
                                    if ( RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0 && receiveChangeVO.getOpt().equals("1")) {
                                    	dao.downCategoryByTactic(vo);
                                        dao.addCategroyByTactic(dccVo); 
                                    	dao.addMessages(MSGType.RefModifyReq, transactionID);
    								}
								} catch (Exception e) {
									LOG.debug("二级分类上架失败..",e);
								}
                                

                            }
                        }
                    }
                    else if (syncMode == 1)	
                    {	
                    	try {
                    		
                            if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0&& receiveChangeVO.getOpt().equals("1") ) {
                            	dao.downCategoryByTactic(vo);
                                dao.addCategroyByTactic(dccVo);
                            	dao.addMessages(MSGType.RefModifyReq, transactionID);
    						}
						} catch (Exception e) {
							LOG.debug("二级分类上架失败!",e);
						}
                        
                        
                    }

                    dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"1");
                    tdb.commit();

                    if (syncMode == 1)
                    {
                        long startTime = System.currentTimeMillis();
                        vo = APPInfoDAO.getInstance().getGContentVO(receiveChangeVO.getEntityid());
                        Map<String, List<String>> map = getFullDevice(vo);
                        List<String> fullDeviceIdList = map.get("fullDeviceIdList");
                        List<String> fullDeviceNameList = map.get("fullDeviceNameList");
                        dao.updateFullDevice(vo.getContentId(), fullDeviceIdList, fullDeviceNameList);
                        LOG.debug("cost time:getFullDevice耗时间：" + (System.currentTimeMillis() - startTime));
                    }
                    //将device表中的risktag放入gcontent表中
                    String deviceRiskTag = dao.getDeviceRiskTag(dccVo.getContentid());
                    if (!StringUtils.isEmpty(deviceRiskTag)) {
                    	 String gcontentRiskTag = dao.getGcontentRiskTag(dccVo.getContentid());
                    	 
                    	 if (StringUtils.isEmpty(gcontentRiskTag)) {
                    		 //如果gcontent表中为空则直接插入
    						dao.updateGcontentRiskTag(dccVo.getContentid(), deviceRiskTag);
    						tdb.commit();
    					}else {
    						String riskTag=deviceRiskTag+";"+gcontentRiskTag;
    						String contentid = dccVo.getContentid();
    						LOG.debug("请求参数riskTag:"+riskTag);
    						LOG.debug("请求参数contentid:"+contentid);
    						//如果gcontent表中不为空,则拼接deviceRiskTag;gcontentRiskTag
    						dao.updateGcontentRiskTag(contentid, riskTag);
    						tdb.commit();
    					}
    				}
                    isSendSafeCenterNotify = true;
                }
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
        }

        if (dccVo != null && vo == null)
        { //电子流有，货架没有，上线      
            // 如果电子流没有适配，则不能入T_R_GCONTENT。
            if (!PPMSDAO.validateResource(receiveChangeVO.getEntityid()))
            {         
                LOG.error("dccVo!=null&&vo==null 没有适配不能入T_R_GCONTENT" + receiveChangeVO.getId());
                // 将t_a_ppms_receive_change的status值为-2;
                try
                {	
                	//PPMSDAO.updateReceiveChangeNoTran(receiveChangeVO.getId(), "-2" ,"-2");
                    new PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(), "-2" ,"-2");
                }
                catch (Exception e)
                {
                    LOG.error("无效的电子流实体：" + vo);
                }
                return;
            }

            TransactionDB tdb = null;
            try
            {
                tdb = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。目标是希望下面两个方法都在这个事务里面完成。
                
                synchronized (tdb) {
				PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                // 在T_R_GCONTENT表不存在，所以要新增内容。
                String transactionID = ContextUtil.getTransactionID();
                // 1、插入到表t_r_gcontent中
                if ("1".equals(receiveChangeVO.getOpt())) {
                	 dccVo.setAppid(receiveChangeVO.getAppid());
				}
                String newId = dao.insertGContent(dccVo);// ////////////////////////////2-27号测试点。。。。呵呵。。。。

                dao.updateVService(dccVo.getContentid());// 价格信息。
                dao.updateDeviceResourseByCid(dccVo.getContentid());// 更新他的适配关系。add
                // by aiyan
                // 2013-04-07
                // 更新程序包的最后更新时间（目的是要改变select * from
                // v_content_last的内容，在发送榜单消息的地方需要程序包的最后更新时间）
                dao.updateLastTime(dccVo.getContentid());

                dccVo.setId(newId);
                // 2上架对应的二级分类
                try {
                	  dao.addCategroyByTactic(dccVo);
				} catch (Exception e) {
					LOG.debug("二级分类上架失败!",e);
				}
              
                
                if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0 ) {
                	// 与数据中心发送消息（货架应用数据变更通知接口-新上线）----------------------------------------------
                    dao.addMessages(MSGType.ContentModifyReq, transactionID, dccVo // 2013-09-06 removed by aiyan
                                                                                    // 因为榜单消息需要已经以一小时一次的形式处理。 add
                                                                                    // 2013-09-09误删除，现在恢复
                            .getContentid() + ":0");
                    //add by tolson
                    if("1".equals(receiveChangeVO.getImagetype())){
                        dao.addMessages(MSGType.ContentModifyReq, transactionID,dccVo.getContentid() + ":4");
                    }
                    // 与数据中心发送消息（上下架）--------------------------------------------
                    dao.addMessages(MSGType.RefModifyReq, transactionID);
				}
                
                // removed by aiyan 2013-05-27大胖说有1（上一个语句）就不用2了
                // dao.addMessages(MSGType.ContentModifyReq, transactionID,receiveChangeVO.getEntityid() +
                // ":2");//下线他的适配关系。add by aiyan 2013-04-07
                // dao.addMessages(MSGType.CountUpdateReq,
                // transactionID,receiveChangeVO.getEntityid()+":0");//新增榜单！刚开始到货价，是不会有啥统计数据的，故丢弃榜单消息//remove by aiyan
                // 2013-05-21
                // 回修改PPMS的接受数据状态
                dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"0");
                
                
               

                tdb.commit();
                
                if (syncMode == 1)
                {// 修改的话，把FULLDEVICE弄一下。add
                    // by aiyan 2013-06-19
                	vo = APPInfoDAO.getInstance().getGContentVO(receiveChangeVO.getEntityid());
                    Map<String, List<String>> map = getFullDevice(vo);
                    List<String> fullDeviceIdList = map.get("fullDeviceIdList");
                    List<String> fullDeviceNameList = map.get("fullDeviceNameList");
                    dao.updateFullDevice(vo.getContentId(), fullDeviceIdList, fullDeviceNameList);

                }
                //将device表中的risktag放入gcontent表中
                String deviceRiskTag = dao.getDeviceRiskTag(dccVo.getContentid());
                if (!StringUtils.isEmpty(deviceRiskTag)) {
                	 String gcontentRiskTag = dao.getGcontentRiskTag(dccVo.getContentid());
                	 
                	 if (StringUtils.isEmpty(gcontentRiskTag)) {
                		 //如果gcontent表中为空则直接插入
						dao.updateGcontentRiskTag(dccVo.getContentid(), deviceRiskTag);
						 tdb.commit();
					}else {
						String riskTag=deviceRiskTag+";"+gcontentRiskTag;
						String contentid = dccVo.getContentid();
						LOG.debug("请求参数riskTag:"+riskTag);
						LOG.debug("请求参数contentid:"+contentid);
						
						//如果gcontent表中不为空,则拼接deviceRiskTag;gcontentRiskTag
						dao.updateGcontentRiskTag(contentid, riskTag);
						 tdb.commit();
					}
				}
                }
                //向安全中心内容变更通知
                isSendSafeCenterNotify = true;

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
        
		if (!StringUtils.isEmpty(receiveChangeVO.getAppid()) && "1".equals(receiveChangeVO.getOpt())) {
			try {
				grounding(receiveChangeVO ,voAppid);
			} catch (DAOException e) {
				LOG.error("上架失败!!"+receiveChangeVO.getEntityid(),e);
			} //  做上架处理
		}else if(!StringUtils.isEmpty(receiveChangeVO.getAppid())){
			try {
				undercarriage(receiveChangeVO,vo);
			} catch (DAOException e) {
				LOG.error("下架失败!"+receiveChangeVO.getEntityid(),e);
			}  //做opt 为2,或者3的处理
		}
		try {
			doGetXpasGAppChangeUrl(receiveChangeVO.getAppid(),receiveChangeVO.getEntityid());
		} catch (Exception e) {
			LOG.error("调用xpas接口GAppChangeUrl失败...",e);
		}
        if(isSendSafeCenterNotify){
        	//添加到后台异步任务执行队列中
        	SendSafeCenterTask task = new SendSafeCenterTask(receiveChangeVO.getEntityid(),online);
        	DaemonTaskRunner.getInstance().addTask(task);
        }
    }

    private Map getFullDevice(GContentVO vo)
    {
        // TODO Auto-generated method stub
        boolean isFree = isFreeContent(vo.getContentId());
        Map map = null;
        if (isFree)
        {
            map = getFullDeviceAll(vo.getContentId());
        }
        else
        {
            map = getFullDeviceMatch(vo.getContentId());
        }
        return map;

    }

    private Map getFullDeviceMatch(String contentId)
    {
        // TODO Auto-generated method stub
        return PPMSDAO.getFullDeviceMatch(contentId);
    }

    private Map getFullDeviceAll(String contentId)
    {
        // TODO Auto-generated method stub
        return PPMSDAO.getFullDeviceAll(contentId);
    }

    private boolean isFreeContent(String contentId)
    {
        // TODO Auto-generated method stub
        return PPMSDAO.isFreeContent(contentId);

    }
    
	// 处理上架的应用
	public static void grounding(ReceiveChangeVO receiveChangeVO ,String oldAppid)
			throws BOException, DAOException {
		TransactionDB tdb = null;
		
		tdb = TransactionDB.getTransactionInstance();
		PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
		List<ReferenceVO> vos = null;
		GContentVO vo = null;
		try {
			vo = APPInfoDAO.getInstance().getGContentVO(
					receiveChangeVO.getEntityid());

		} catch (Exception e) {
			LOG.debug(e);
			LOG.error(e);
		}
		
		if (!StringUtils.isEmpty(oldAppid) && !(oldAppid.equals(receiveChangeVO.getAppid()))) {
			//如果appid有变更,则先根据contentid查出所有在架商品,然后把appid置成最新的appid
			dao.updateAppidByNewAppid(vo.getContentId(), receiveChangeVO.getAppid());
			tdb.commit();
		}
		
		try {
			vos = dao.getReferenceByAppid(receiveChangeVO.getAppid());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		if (vo == null || vos == null) {
			// 商品已经下了,或者不在货架中
			return;
		}
		GContent content = (GContent) Repository.getInstance().getNode(
				vo.getId(), RepositoryConstants.TYPE_GCONTENT);
		if (!StringUtils.isEmpty(receiveChangeVO.getAppid())) {
			content.setAppId(receiveChangeVO.getAppid());
		}
		
		try {
			ReferenceNode oldReference;
			SingleCategoryVO singleCategoryVO = null;
			dao.updateTRGapp(receiveChangeVO.getAppid(), receiveChangeVO.getEntityid(), receiveChangeVO.getOpt());
			//tdb.commit();
			String goodsid;
			ReferenceNode refNode;
			GoodsVO goodsVO;
			for (ReferenceVO referenceVO : vos) {
				try {
					oldReference = (ReferenceNode) Repository.getInstance()
							.getNode(referenceVO.getId(),
									RepositoryConstants.TYPE_REFERENCE);
					singleCategoryVO = dao.getSingleCategoryVo(referenceVO
							.getCategoryid());
					refNode = new ReferenceNode();
					goodsVO = new GoodsVO();
					goodsVO.setIcpCode(content.getIcpCode());
					goodsVO.setIcpServId(content.getIcpServId());
					goodsVO.setContentID(vo.getContentId());
					goodsVO.setCategoryID(referenceVO.getCategoryid());
					goodsVO.setGoodsName(content.getName());
					goodsVO.setState(1);
					goodsVO.setChangeDate(new Date());
					goodsVO.setActionType(1);
					// referenceVO.setRefnodeid(vo.getId());
					goodsid = singleCategoryVO.getCategoryid() + "000000"
							+ PublicUtil.lPad(receiveChangeVO.getAppid(), 12)
							+ PublicUtil.lPad(receiveChangeVO.getEntityid(), 12);
					goodsVO.setGoodsID(goodsid);
					refNode.setVerifyStatus(referenceVO.getVerify_status());
					refNode.setRefNode(content);
					refNode.setParentID(referenceVO.getParentid());
					refNode.setAppId(receiveChangeVO.getAppid());
					refNode.setRefNodeID(vo.getId());
					refNode.setSortID(referenceVO.getSortid());
					refNode.setVariation(referenceVO.getVariation());
					refNode.setGoodsID(goodsid);
					refNode.setCategoryID(singleCategoryVO.getCategoryid());
					refNode.setLoadDate(referenceVO.getLoaddate());
					refNode.setId(referenceVO.getId());
					refNode.setPath(referenceVO.getPath());
					dao.removeRefContentFromCategory(referenceVO.getId());
					LOG.debug(referenceVO.getRefnodeid() + "原有商品下架成功");
					// 上架
					dao.addNodeAndInsertGoodsInfo(refNode, goodsVO,
							singleCategoryVO.getId());
					LOG.debug(content.getContentID() + "上架成功.");
				} catch (Exception e) {
					LOG.debug(referenceVO + "操作失败", e);
					tdb.rollback();
				}
			}
			// 操作完所有货架后提交
			tdb.commit();
		} catch (Exception e) {
			LOG.debug(e);
            if (tdb != null)
            {
                tdb.rollback();
            }
		}finally{
			if ( null != tdb) {
				tdb.close();
			}
		}
		
	}

	// 处理下架或者下线的应用
	public static void undercarriage(ReceiveChangeVO receiveChangeVO,GContentVO vo)
			throws DAOException, BOException {
		TransactionDB tdb = null;
		tdb = TransactionDB.getTransactionInstance();
		PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
		List<ReferenceVO> vos = null;
/*		if ("3".equals(receiveChangeVO.getOpt())) {
			//做下线处理,t_r_gapp表删除字段;  商品下架已在应用下架里做了;所以这里逻辑不多
			dao.deleteTRGappAppid(receiveChangeVO.getAppid());
			// 待确认contentid对应的xpas聚合应用的处理方式?
						// appid对应的contentid 解除关联关系, receiveChangeVO.entityid t_r_gapp(删除)表
						// 和 reference表 t_r_gcontent
						
			//下线的话,在应用下线里就全部下架了; 这里只在t_r_gapp表里做删除
			tdb.commit();
		} else if ("2".equals(receiveChangeVO.getOpt())) {*/
			//下架处理,解除appid和contentid关系
		try {
			dao.updateReferenceAppid(receiveChangeVO.getAppid(),
					receiveChangeVO.getEntityid());
			//dao.updateTDReferenceAppid(receiveChangeVO.getAppid(), receiveChangeVO.getEntityid());
			
			dao.deleteTRGappAppid(receiveChangeVO.getAppid(),receiveChangeVO.getEntityid());
			if ((StringUtils.isEmpty(vo.getAppid())) || receiveChangeVO.getAppid().equals(vo.getAppid())) {
				dao.downCategoryByTactic(vo);
				dao.addMessages(MSGType.RefModifyReq, ContextUtil.getTransactionID());
			}
			tdb.commit();
			//修改xpas的商品表
			dao.updateTRGcontentAppid(receiveChangeVO.getAppid(),
					receiveChangeVO.getEntityid());
			tdb.commit();
		} catch (Exception e) {
			LOG.debug(e);
            if (tdb != null)
            {
                tdb.rollback();
            }
		}finally{
			if ( null != tdb) {
				tdb.close();
			}
		}
			

		}
	
	
	
	private static String getXpasUrl(String value){
    	ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
		"syncAndroid");
        return  module.getItemValue(value) ;
	}
	
	public static void doGetXpasGAppChangeUrl(String appid,String contentid) throws Exception{
		String gAppChangeUrl = getXpasUrl("gAppChange");
		LOG.debug("获取到xpas配置gAppChange=====>" +gAppChangeUrl);
		String newUrl =gAppChangeUrl+"?appid="+appid+"&contentid="+contentid;
		LOG.debug("获取到xpas配置newUrl=====>" +newUrl);
		//String resp = HttpUtil.get(newUrl, 3000);
		//LOG.debug("xpas响应的结果为:"+resp);
		HttpUtils.get(newUrl, null);
	}

}
