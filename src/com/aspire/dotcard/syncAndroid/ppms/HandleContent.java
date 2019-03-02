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
     * ��־����
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
        
        //�����б������ʱʱ��Ϣ����ȫ����,2015-07-20 add
        boolean isSendSafeCenterNotify = false;
        int online = 1;
        // ��ѯ����t_r_gcontent��
        
        // 1�����ڣ����޸ģ������������Ҳ�ı䣬�������¼ܲ���
        // 2�������ڣ����뵽��t_r_gcontent���ϼܵ���Ӧ�Ķ��������¡�
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
            LOG.error("APPInfoDAO.getInstance().getCmContent(" + receiveChangeVO.getEntityid() + ")����"+e.getMessage());
        }
        
        try
        {
            vo = APPInfoDAO.getInstance().getGContentVO(receiveChangeVO.getEntityid());
            voAppid = vo.getAppid();
        }
        catch (Exception e)
        {
            LOG.error("APPInfoDAO.getInstance().getGContentVO(" + receiveChangeVO.getEntityid() + ")����"+e.getMessage());
        }

        LOG.debug("exec vo handleContent1 contentid=" + receiveChangeVO.getEntityid() + ";id=" + receiveChangeVO.getId() + ";" + ";costtime=" + (System.currentTimeMillis() - starttime2));
        //2015-09-30 add ,��������������ַ�����(channeldisptype)��0��δѡ�����зַ���MM�ͻ��ˣ���1����ѡ�����зַ���������ֻ�пͻ��ˣ�
        //channeldisptypeΪ1:��ѡ�����зַ���Ӧ�ò��ϼܵ�MM�ͻ��˻��ܣ�
        //�������������Ӧ�ã����ϼܵ�MM���ϼܵ������̶�Ӧ�ĸ�����
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
                     LOG.error("subtype="+dccVo.getSubtype()+"(channeldisptype)��0��δѡ�����зַ���MM�ͻ��ˣ���1����ѡ�����зַ���������ֻ�пͻ��ˣ�û����Դ��"+receiveChangeVO.getId());
                 }

                 return;
             }

             LOG.debug(dccVo.getContentid() + " is channeldisptype="+dccVo.getChanneldisptype()+" content... vo!=null" + (vo != null));
             TransactionDB tdb = null;
             try
             {
                 tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
                 synchronized (tdb) {
                 PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                 // String transactionID = ContextUtil.getTransactionID();
                 if (vo != null)
                 {
                     dccVo.setId(vo.getId());
                     // �޸�
                     dao.updateGContent(dccVo);
                     dao.addMessages(MSGType.ContentModifyReq, null, dccVo.getContentid() + ":1");
                     dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "","1");
                 }
                 else
                 {
                     String newId = dao.insertGContent(dccVo);
                     dccVo.setId(newId);
                     //���ݺ������������õĸ������ϼܵ���Ӧ�ĸ�����
                     dao.addCategroyByCategoryId(dccVo);
                     dao.addMessages(MSGType.ContentModifyReq, null, dccVo.getContentid() + ":0");
                     dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "","0");
                 }

                 dao.updateVService(dccVo.getContentid());// �۸���Ϣ��
                 dao.updateDeviceResourseByCid(dccVo.getContentid());// �������������ϵ��add
                 // by aiyan
                 // 2013-04-07
                 // ���³������������ʱ�䣨Ŀ����Ҫ�ı�select * from
                 // v_content_last�����ݣ��ڷ��Ͱ���Ϣ�ĵط���Ҫ�������������ʱ�䣩
                 dao.updateLastTime(dccVo.getContentid());
                 //dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "");
                 
                 tdb.commit();
            	 
                 if (syncMode == 1)
                 {// �޸ĵĻ�����FULLDEVICEŪһ�¡�add
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
        
        // ��������Ҫ���˵�HTCӦ�á���ΪHTCӦ���ǲ���MM�Ķ���������ܡ�
        // HTCӦ�û��ײ�Ӧ�����ﴦ��HTC�����¼����ϵ�ͬ����ɡ����ײ�Ӧ���ǲ��϶���������ܡ�
        // �����ߵĴ����������Ʒ���Ż�������ͬ���ˡ����ǰѸ�����Ϣͨ�����������������¼����顣��16:HTCӦ�ã�11:�ײ�Ӧ��
        //(add 2014-01-07) ������App���ϼ� 21��������App        
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
                    LOG.error("subtype="+dccVo.getSubtype()+" (16:HTCӦ�ã�11:�ײ�Ӧ�ã�21��������App��) û����Դ��"+receiveChangeVO.getId());
                }

                return;
            }

            LOG.debug(dccVo.getContentid() + " is subtype="+dccVo.getSubtype()+" content... vo!=null" + (vo != null));
            TransactionDB tdb = null;
            try
            {
                tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
                synchronized (tdb) {
					
				
                PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                // String transactionID = ContextUtil.getTransactionID();
                if (vo != null)
                {
                    dccVo.setId(vo.getId());
                    // �޸�
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

                dao.updateVService(dccVo.getContentid());// �۸���Ϣ��
                dao.updateDeviceResourseByCid(dccVo.getContentid());// �������������ϵ��add
                // by aiyan
                // 2013-04-07
                // ���³������������ʱ�䣨Ŀ����Ҫ�ı�select * from
                // v_content_last�����ݣ��ڷ��Ͱ���Ϣ�ĵط���Ҫ�������������ʱ�䣩
                dao.updateLastTime(dccVo.getContentid());
                //dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "");
                tdb.commit();

                if (syncMode == 1)
                {// �޸ĵĻ�����FULLDEVICEŪһ�¡�add
                    // by aiyan 2013-06-19
                    Map<String, List<String>> map = getFullDevice(vo);
                    List<String> fullDeviceIdList = map.get("fullDeviceIdList");
                    List<String> fullDeviceNameList = map.get("fullDeviceNameList");
                    dao.updateFullDevice(vo.getContentId(), fullDeviceIdList, fullDeviceNameList);

                }
                isSendSafeCenterNotify = true;
                //��device���е�risktag����gcontent����
                String deviceRiskTag = dao.getDeviceRiskTag(dccVo.getContentid());
                if (!StringUtils.isEmpty(deviceRiskTag)) {
                	 String gcontentRiskTag = dao.getGcontentRiskTag(dccVo.getContentid());
                	 
                	 if (StringUtils.isEmpty(gcontentRiskTag)) {
                		 //���gcontent����Ϊ����ֱ�Ӳ���
						dao.updateGcontentRiskTag(dccVo.getContentid(), deviceRiskTag);
						tdb.commit();
					}else {
						//���gcontent���в�Ϊ��,��ƴ��deviceRiskTag;gcontentRiskTag
						String riskTag=deviceRiskTag+";"+gcontentRiskTag;
						String contentid = dccVo.getContentid();
						LOG.debug("�������riskTag:"+riskTag);
						LOG.debug("�������contentid:"+contentid);
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
            LOG.info("dccVo==null&&vo == null,˵���������ͻ��ܶ�û�����Ӧ�ã�����������ݰ�����" + receiveChangeVO.getEntityid());

            // ��t_a_ppms_receive_change��statusֵΪ-3;
            TransactionDB tdb = null;
            try
            {
            	tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
                synchronized(tdb){
            	PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                dao.addMessages(MSGType.ContentModifyReq, "", receiveChangeVO.getEntityid() + ":9");//add by tungke 20131206 
                //dao.updateReceiveChangeNoTran(receiveChangeVO.getId(), "-3");
                dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"9");
                dao.deleteContentid(receiveChangeVO.getEntityid());
                dao.deldeviceUpgrade(receiveChangeVO.getEntityid());//Ӧ������ɾ��Ӧ��������Ϣ�� add by 20140325
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
            LOG.error("��Ч�ĵ�����ʵ�壺" + vo);

            return;
        }

        if (dccVo == null && vo != null)
        { //������û�У������У�����
            // �¼�ANDROID�����������и�CONTENTID����Ʒ��
            ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
            String categoryId = module.getItemValue("ROOT_CATEGORYID");
            String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");// ������Ʒ���Ż��������µ���Ӫ����
            TransactionDB tdb = null;
            try
            {
                tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
                synchronized (tdb) {
					
				
                PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                // ���߿�ʼ������������
                dao.downAllReference(categoryId, vo);// ������Ʒ��android��������Ҫ���ߣ�
                dao.downAllOperateReference(operateCategoryId, vo);// ������Ʒ(ָ������Ӫ������Ҫ����)
                
                dao.updateDeviceResourseByCid(receiveChangeVO.getEntityid());// �������������ϵ��add
                // by
                // aiyan
                // 2013-04-07

                dao.getDelGoodsList(categoryId, vo.getId());
                String transactionID = ContextUtil.getTransactionID();
                // ���������ķ�����Ϣ�����¼ܣ�--------------------------------------------
                dao.addMessages(MSGType.ContentModifyReq, transactionID, receiveChangeVO.getEntityid() + ":9");
                
                dao.deldeviceUpgrade(receiveChangeVO.getEntityid());//Ӧ������ɾ��Ӧ��������Ϣ�� 2014-03-25
                dao.addMessages(MSGType.RefModifyReq, transactionID);
                // removed by aiyan 2013-06-01
                // �ն��Ż��Լ�������Ĵ����ְ�������Ӧ�ñ�������������ظ��������ˣ��ʶ�����
                // dao.addMessages(MSGType.ContentModifyReq,
                // transactionID,receiveChangeVO.getEntityid() +
                // ":2");//�������������ϵ��add by aiyan 2013-04-07
                dao.addMessages(MSGType.CountUpdateReq, transactionID, receiveChangeVO.getEntityid() + ":9");// ɾ���񵥣�
                // ���޸�PPMS�Ľ�������״̬      
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
        {//�������У�����Ҳ��
      
        	
            // /��֤û������ģ����ߣ���
            long startTime_V = System.currentTimeMillis();
            if (!PPMSDAO.validateResource(receiveChangeVO.getEntityid()))
            {
                LOG.debug("cost time:�����ڵ�ʱ��--�ӵ������õ��Ƿ�����Դ��ʱ�䣺" + (System.currentTimeMillis() - startTime_V));
                // �¼�ANDROID�����������и�CONTENTID����Ʒ��
                ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
                String categoryId = module.getItemValue("ROOT_CATEGORYID");
                String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");// ������Ʒ���Ż��������µ���Ӫ����
                TransactionDB tdb = null;
                // Producer producer = null;
                try
                {
                    tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
                    synchronized (tdb) {
						
					
                    PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                    // ���߿�ʼ������������
                    dao.downAllReference(categoryId, vo);// ������Ʒ
                    dao.downAllOperateReference(operateCategoryId, vo);// ������Ʒ(ָ������Ӫ������Ҫ����)

                    dao.updateDeviceResourseByCid(receiveChangeVO.getEntityid());// �������������ϵ��add by aiyan
                    // 2013-04-07

                    dao.getDelGoodsList(categoryId, vo.getId());
                    String transactionID = ContextUtil.getTransactionID();
                    // ���������ķ�����Ϣ�����¼ܣ�--------------------------------------------
                    if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0) {
                    	dao.addMessages(MSGType.ContentModifyReq, transactionID, receiveChangeVO.getEntityid() + ":9");
                        dao.addMessages(MSGType.RefModifyReq, transactionID);
                        // removed by aiyan 2013-06-01
                        // �ն��Ż��Լ�������Ĵ����ְ�������Ӧ�ñ�������������ظ��������ˣ��ʶ�����
                        // dao.addMessages(MSGType.ContentModifyReq,
                        // transactionID,receiveChangeVO.getEntityid() +
                        // ":2");//�������������ϵ��add by aiyan 2013-04-07
                        dao.addMessages(MSGType.CountUpdateReq, transactionID, receiveChangeVO.getEntityid() + ":9");// ɾ���񵥣�
					}
                    
                    // ���޸�PPMS�Ľ�������״̬
                    dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"9");
                    dao.deleteContentid(receiveChangeVO.getEntityid());
                    dao.deldeviceUpgrade(receiveChangeVO.getEntityid());//Ӧ������ɾ��Ӧ��������Ϣ�� 2014-03-25
                    
                    tdb.commit();
                    
                    if (syncMode == 1)
                    {
                        long startTime = System.currentTimeMillis();
                        Map<String, List<String>> map = getFullDevice(vo);
                        List<String> fullDeviceIdList = map.get("fullDeviceIdList");
                        List<String> fullDeviceNameList = map.get("fullDeviceNameList");
                        dao.updateFullDevice(vo.getContentId(), fullDeviceIdList, fullDeviceNameList);
                        LOG.debug("cost time:getFullDevice��ʱ�䣺" + (System.currentTimeMillis() - startTime));
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
                // ///�����䡣���¡�����Ϣ��
                //		
                // TransactionDB tdb = null;
                // try{
                // LOG.debug("(dccVo!=null&&vo!=null)û������Ŷ"+receiveChangeVO.getEntityid());
                //			
                // tdb = TransactionDB.getTransactionInstance();//
                // һ���µ�JDBC����ʼ�ˡ�
                // PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                // dao.updateDeviceResourseByCid(receiveChangeVO.getEntityid());
                // dao.addMessages(MSGType.ContentModifyReq,
                // null,receiveChangeVO.getEntityid() + ":2");//�������������ϵ��add by
                // aiyan 2013-04-25
                // // ���޸�PPMS�Ľ�������״̬
                // dao.updateReceiveChange(receiveChangeVO.getId(),
                // Constant.MESSAGE_HANDLE_STATUS_SUCC + "");
                // tdb.commit();
                // }catch(Exception e){
                // tdb.rollback();
                // LOG.debug("(dccVo!=null&&vo!=null)û������Ŷ,����Ҫ�����䣬�ҷ���Ϣ�������ˣ�"+receiveChangeVO.getEntityid(),e);
                // }finally{
                // if(tdb!=null){
                // tdb.close();
                // }
                // }
                // ��������ǰ���߼�����ע���ˡ�REMOVED BY AIYAN 2013-04-25
                // try{
                // new
                // PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(),"-2");
                // }catch(Exception e){
                // LOG.error("��Ч�ĵ�����ʵ��,û�����䣡hehe��"+vo);
                // }
                // return;
            }
            else
            {
                LOG.debug("cost time:���ڵ�ʱ��--�ӵ������õ��Ƿ�����Դ��ʱ�䣺" + (System.currentTimeMillis() - startTime_V));
                long stime = System.currentTimeMillis();

                TransactionDB tdb = null;
                try
                {
                    String transactionID = ContextUtil.getTransactionID();
                    tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�Ŀ����ϣ�������������������������������ɡ�
                    synchronized (tdb) {
						
					
                    PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                    if ("1".equals(receiveChangeVO.getOpt())) {
                    	dccVo.setAppid(receiveChangeVO.getAppid());
					}else {
						//��������¼�,����֮ǰ��appid,���trgcontent�����еĻ�
						if (!StringUtils.isEmpty(vo.getAppid())) {
							dccVo.setAppid(vo.getAppid());
						};
					}
                    dccVo.setId(vo.getId());
                    
                    // �޸�
                    dao.updateGContent(dccVo);
                    dao.updateVService(vo.getContentId());// �۸���Ϣ��
                    dao.updateDeviceResourseByCid(vo.getContentId());// �������������ϵ��add
                    // by
                    // aiyan
                    // 2013-04-07
                    // ���³������������ʱ�䣨Ŀ����Ҫ�ı�select * from
                    // v_content_last�����ݣ��ڷ��Ͱ���Ϣ�ĵط���Ҫ�������������ʱ�䣩
                    dao.updateLastTime(vo.getContentId());
                    if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0) {
                    	// ���������ķ�����Ϣ��Ӧ�����ݱ����--------------------------------------------
                        dao.addMessages(MSGType.ContentModifyReq, transactionID, vo.getContentId() + ":1");
                        //add by tolson
                        if("1".equals(receiveChangeVO.getImagetype())){
    	                    dao.addMessages(MSGType.ContentModifyReq, transactionID,vo.getContentId() + ":4");
                        }
					}
                    
                    // removed by aiyan 2013-05-27����˵��1����һ����䣩�Ͳ���2��
                    // dao.addMessages(MSGType.ContentModifyReq, transactionID,
                    // vo.getContentId()+ ":2");

                    // removed by aiyan 2013-09-06 ����Ϣ�Ѿ���һСʱһ�δ���
                    // dao.addMessages(MSGType.CountUpdateReq, transactionID,
                    // receiveChangeVO.getEntityid() + ":0");// �����񵥣�//add
                    // // by aiyan
                    // // 2013-05-21

                    // �ն��Ż��Ǳ���ʱ��Ҫ��ȫ��ͬ��������;Ͱ�ģʽ����1�ġ������¼���Ϣ�� add by aiyan
                    // 2013-06-19
                    if (syncMode == 0)
                    {
                        // ����ط��Ƚ��ر���ΪT_R_GCONTENT��Ӧ�������ߵ�ʱ��û��ɾ�����еļ�¼����
                        // ����dccVo!=null&&vo!=null�µ����������ANDROID������û����Ʒ�������ֲ�
                        boolean isExistRef = dao.isExistRefInAndrodCategory(vo);
                        if (!isExistRef)
                        {
                        	try {
                        		// transactionID = ContextUtil.getTransactionID();
                                // dao.downCategoryByTactic(vo);//����ط�����ע����Ҳ���Բ�ע������ע��һ�°ɡ�������
                                dao.addCategroyByTactic(dccVo);
                                // dao.updateDeviceResourseByCid(vo.getContentId());//�������������ϵ��add
                                // by aiyan 2013-04-07
                                // ���������ķ�����Ϣ�����¼ܣ�--------------------------------------------
                                // dao.addMessages(MSGType.ContentModifyReq,
                                // transactionID,receiveChangeVO.getEntityid() +
                                // ":2");//�����ϵ��add by aiyan 2013-04-07
                                if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0 ) {
                                	 dao.addMessages(MSGType.RefModifyReq, transactionID);
    							}
                               
                                // dao.addMessages(MSGType.CountUpdateReq,
                                // transactionID,receiveChangeVO.getEntityid()+":0");//�����񵥣��տ�ʼ�����ۣ��ǲ�����ɶͳ�����ݵģ��ʶ�������Ϣ//remove
                                // by aiyan 2013-05-21
							} catch (Exception e) {
								LOG.debug("���������ϼ�ʧ��!",e);
							}
                            
                        }
                        else
                        {
                            if (!vo.getAppCateID().equals(dccVo.getAppCateID()))
                            {
                                // ��2�������޸ģ������¼ܲ���
                            	try {
                            		
                                    // ���������ķ�����Ϣ�����¼ܣ�--------------------------------------------
                                    if ( RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0 && receiveChangeVO.getOpt().equals("1")) {
                                    	dao.downCategoryByTactic(vo);
                                        dao.addCategroyByTactic(dccVo); 
                                    	dao.addMessages(MSGType.RefModifyReq, transactionID);
    								}
								} catch (Exception e) {
									LOG.debug("���������ϼ�ʧ��..",e);
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
							LOG.debug("���������ϼ�ʧ��!",e);
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
                        LOG.debug("cost time:getFullDevice��ʱ�䣺" + (System.currentTimeMillis() - startTime));
                    }
                    //��device���е�risktag����gcontent����
                    String deviceRiskTag = dao.getDeviceRiskTag(dccVo.getContentid());
                    if (!StringUtils.isEmpty(deviceRiskTag)) {
                    	 String gcontentRiskTag = dao.getGcontentRiskTag(dccVo.getContentid());
                    	 
                    	 if (StringUtils.isEmpty(gcontentRiskTag)) {
                    		 //���gcontent����Ϊ����ֱ�Ӳ���
    						dao.updateGcontentRiskTag(dccVo.getContentid(), deviceRiskTag);
    						tdb.commit();
    					}else {
    						String riskTag=deviceRiskTag+";"+gcontentRiskTag;
    						String contentid = dccVo.getContentid();
    						LOG.debug("�������riskTag:"+riskTag);
    						LOG.debug("�������contentid:"+contentid);
    						//���gcontent���в�Ϊ��,��ƴ��deviceRiskTag;gcontentRiskTag
    						dao.updateGcontentRiskTag(contentid, riskTag);
    						tdb.commit();
    					}
    				}
                    isSendSafeCenterNotify = true;
                }
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
        }

        if (dccVo != null && vo == null)
        { //�������У�����û�У�����      
            // ���������û�����䣬������T_R_GCONTENT��
            if (!PPMSDAO.validateResource(receiveChangeVO.getEntityid()))
            {         
                LOG.error("dccVo!=null&&vo==null û�����䲻����T_R_GCONTENT" + receiveChangeVO.getId());
                // ��t_a_ppms_receive_change��statusֵΪ-2;
                try
                {	
                	//PPMSDAO.updateReceiveChangeNoTran(receiveChangeVO.getId(), "-2" ,"-2");
                    new PPMSDAO().updateReceiveChangeNoTran(receiveChangeVO.getId(), "-2" ,"-2");
                }
                catch (Exception e)
                {
                    LOG.error("��Ч�ĵ�����ʵ�壺" + vo);
                }
                return;
            }

            TransactionDB tdb = null;
            try
            {
                tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�Ŀ����ϣ�������������������������������ɡ�
                
                synchronized (tdb) {
				PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
                // ��T_R_GCONTENT�����ڣ�����Ҫ�������ݡ�
                String transactionID = ContextUtil.getTransactionID();
                // 1�����뵽��t_r_gcontent��
                if ("1".equals(receiveChangeVO.getOpt())) {
                	 dccVo.setAppid(receiveChangeVO.getAppid());
				}
                String newId = dao.insertGContent(dccVo);// ////////////////////////////2-27�Ų��Ե㡣�������Ǻǡ�������

                dao.updateVService(dccVo.getContentid());// �۸���Ϣ��
                dao.updateDeviceResourseByCid(dccVo.getContentid());// �������������ϵ��add
                // by aiyan
                // 2013-04-07
                // ���³������������ʱ�䣨Ŀ����Ҫ�ı�select * from
                // v_content_last�����ݣ��ڷ��Ͱ���Ϣ�ĵط���Ҫ�������������ʱ�䣩
                dao.updateLastTime(dccVo.getContentid());

                dccVo.setId(newId);
                // 2�ϼܶ�Ӧ�Ķ�������
                try {
                	  dao.addCategroyByTactic(dccVo);
				} catch (Exception e) {
					LOG.debug("���������ϼ�ʧ��!",e);
				}
              
                
                if (RiskTagDAO.getInstance().doCheckBlack(receiveChangeVO.getEntityid()) == 0 ) {
                	// ���������ķ�����Ϣ������Ӧ�����ݱ��֪ͨ�ӿ�-�����ߣ�----------------------------------------------
                    dao.addMessages(MSGType.ContentModifyReq, transactionID, dccVo // 2013-09-06 removed by aiyan
                                                                                    // ��Ϊ����Ϣ��Ҫ�Ѿ���һСʱһ�ε���ʽ���� add
                                                                                    // 2013-09-09��ɾ�������ڻָ�
                            .getContentid() + ":0");
                    //add by tolson
                    if("1".equals(receiveChangeVO.getImagetype())){
                        dao.addMessages(MSGType.ContentModifyReq, transactionID,dccVo.getContentid() + ":4");
                    }
                    // ���������ķ�����Ϣ�����¼ܣ�--------------------------------------------
                    dao.addMessages(MSGType.RefModifyReq, transactionID);
				}
                
                // removed by aiyan 2013-05-27����˵��1����һ����䣩�Ͳ���2��
                // dao.addMessages(MSGType.ContentModifyReq, transactionID,receiveChangeVO.getEntityid() +
                // ":2");//�������������ϵ��add by aiyan 2013-04-07
                // dao.addMessages(MSGType.CountUpdateReq,
                // transactionID,receiveChangeVO.getEntityid()+":0");//�����񵥣��տ�ʼ�����ۣ��ǲ�����ɶͳ�����ݵģ��ʶ�������Ϣ//remove by aiyan
                // 2013-05-21
                // ���޸�PPMS�Ľ�������״̬
                dao.updateReceiveChange(receiveChangeVO.getId(), Constant.MESSAGE_HANDLE_STATUS_SUCC + "" ,"0");
                
                
               

                tdb.commit();
                
                if (syncMode == 1)
                {// �޸ĵĻ�����FULLDEVICEŪһ�¡�add
                    // by aiyan 2013-06-19
                	vo = APPInfoDAO.getInstance().getGContentVO(receiveChangeVO.getEntityid());
                    Map<String, List<String>> map = getFullDevice(vo);
                    List<String> fullDeviceIdList = map.get("fullDeviceIdList");
                    List<String> fullDeviceNameList = map.get("fullDeviceNameList");
                    dao.updateFullDevice(vo.getContentId(), fullDeviceIdList, fullDeviceNameList);

                }
                //��device���е�risktag����gcontent����
                String deviceRiskTag = dao.getDeviceRiskTag(dccVo.getContentid());
                if (!StringUtils.isEmpty(deviceRiskTag)) {
                	 String gcontentRiskTag = dao.getGcontentRiskTag(dccVo.getContentid());
                	 
                	 if (StringUtils.isEmpty(gcontentRiskTag)) {
                		 //���gcontent����Ϊ����ֱ�Ӳ���
						dao.updateGcontentRiskTag(dccVo.getContentid(), deviceRiskTag);
						 tdb.commit();
					}else {
						String riskTag=deviceRiskTag+";"+gcontentRiskTag;
						String contentid = dccVo.getContentid();
						LOG.debug("�������riskTag:"+riskTag);
						LOG.debug("�������contentid:"+contentid);
						
						//���gcontent���в�Ϊ��,��ƴ��deviceRiskTag;gcontentRiskTag
						dao.updateGcontentRiskTag(contentid, riskTag);
						 tdb.commit();
					}
				}
                }
                //��ȫ�������ݱ��֪ͨ
                isSendSafeCenterNotify = true;

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
        
		if (!StringUtils.isEmpty(receiveChangeVO.getAppid()) && "1".equals(receiveChangeVO.getOpt())) {
			try {
				grounding(receiveChangeVO ,voAppid);
			} catch (DAOException e) {
				LOG.error("�ϼ�ʧ��!!"+receiveChangeVO.getEntityid(),e);
			} //  ���ϼܴ���
		}else if(!StringUtils.isEmpty(receiveChangeVO.getAppid())){
			try {
				undercarriage(receiveChangeVO,vo);
			} catch (DAOException e) {
				LOG.error("�¼�ʧ��!"+receiveChangeVO.getEntityid(),e);
			}  //��opt Ϊ2,����3�Ĵ���
		}
		try {
			doGetXpasGAppChangeUrl(receiveChangeVO.getAppid(),receiveChangeVO.getEntityid());
		} catch (Exception e) {
			LOG.error("����xpas�ӿ�GAppChangeUrlʧ��...",e);
		}
        if(isSendSafeCenterNotify){
        	//��ӵ���̨�첽����ִ�ж�����
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
    
	// �����ϼܵ�Ӧ��
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
			//���appid�б��,���ȸ���contentid��������ڼ���Ʒ,Ȼ���appid�ó����µ�appid
			dao.updateAppidByNewAppid(vo.getContentId(), receiveChangeVO.getAppid());
			tdb.commit();
		}
		
		try {
			vos = dao.getReferenceByAppid(receiveChangeVO.getAppid());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		if (vo == null || vos == null) {
			// ��Ʒ�Ѿ�����,���߲��ڻ�����
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
					LOG.debug(referenceVO.getRefnodeid() + "ԭ����Ʒ�¼ܳɹ�");
					// �ϼ�
					dao.addNodeAndInsertGoodsInfo(refNode, goodsVO,
							singleCategoryVO.getId());
					LOG.debug(content.getContentID() + "�ϼܳɹ�.");
				} catch (Exception e) {
					LOG.debug(referenceVO + "����ʧ��", e);
					tdb.rollback();
				}
			}
			// ���������л��ܺ��ύ
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

	// �����¼ܻ������ߵ�Ӧ��
	public static void undercarriage(ReceiveChangeVO receiveChangeVO,GContentVO vo)
			throws DAOException, BOException {
		TransactionDB tdb = null;
		tdb = TransactionDB.getTransactionInstance();
		PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
		List<ReferenceVO> vos = null;
/*		if ("3".equals(receiveChangeVO.getOpt())) {
			//�����ߴ���,t_r_gapp��ɾ���ֶ�;  ��Ʒ�¼�����Ӧ���¼�������;���������߼�����
			dao.deleteTRGappAppid(receiveChangeVO.getAppid());
			// ��ȷ��contentid��Ӧ��xpas�ۺ�Ӧ�õĴ���ʽ?
						// appid��Ӧ��contentid ���������ϵ, receiveChangeVO.entityid t_r_gapp(ɾ��)��
						// �� reference�� t_r_gcontent
						
			//���ߵĻ�,��Ӧ���������ȫ���¼���; ����ֻ��t_r_gapp������ɾ��
			tdb.commit();
		} else if ("2".equals(receiveChangeVO.getOpt())) {*/
			//�¼ܴ���,���appid��contentid��ϵ
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
			//�޸�xpas����Ʒ��
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
		LOG.debug("��ȡ��xpas����gAppChange=====>" +gAppChangeUrl);
		String newUrl =gAppChangeUrl+"?appid="+appid+"&contentid="+contentid;
		LOG.debug("��ȡ��xpas����newUrl=====>" +newUrl);
		//String resp = HttpUtil.get(newUrl, 3000);
		//LOG.debug("xpas��Ӧ�Ľ��Ϊ:"+resp);
		HttpUtils.get(newUrl, null);
	}

}
