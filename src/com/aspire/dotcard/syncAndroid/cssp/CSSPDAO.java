package com.aspire.dotcard.syncAndroid.cssp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.cssp.free.DLReportBean;
import com.aspire.dotcard.syncAndroid.cssp.free.FreeDLOrderBean;
import com.aspire.dotcard.syncAndroid.cssp.free.FreeDLReportBean;
import com.aspire.ponaadmin.web.db.TransactionDB;


public class CSSPDAO
{

    private static final int SIZE_8 = 8;

    private static final int SIZE_41 = 41;

    /**
     * 记录日志的实例对象
     */
    protected static JLogger LOG = LoggerFactory.getLogger(CSSPDAO.class);

    private static CSSPDAO instance = new CSSPDAO();

    public final Queue<FreeDLReportBean> batchQueueReport = new ConcurrentLinkedQueue<FreeDLReportBean>();
    public final Queue<FreeDLOrderBean> batchQueueOrder = new ConcurrentLinkedQueue<FreeDLOrderBean>();

    public static CSSPDAO getInstance()
    {

        return instance;
    }

    public void insertPush(PushBean b)
    {
        String sqlCode = "com.aspire.dotcard.syncAndroid.cssp.insertPush";
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, new String[] { b.getId(), b.getPushid(), b.getContentid(), b.getUa(), b.getFileName() });
        }
        catch (DAOException e)
        {
            // TODO Auto-generated catch block
            LOG.error("insertPush出错：", e);
        }
    }

    public void insertPushReport(PushReportBean b)
    {
        String sqlCode = "com.aspire.dotcard.syncAndroid.cssp.insertPushReport";
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, new String[] { b.getId(), b.getPushid(), b.getStatus(), b.getRecordtime(), b.getFileName() });
        }
        catch (DAOException e)
        {
            // TODO Auto-generated catch block
            LOG.error("insertPushReport出错：", e);
        }
    }

    public void insertReport(ReportBean b)
    {
        // insert into t_a_report(contentid,subsplace,ua,fileName) values(?,?,?,?)
        String sqlCode = "com.aspire.dotcard.syncAndroid.cssp.insertReport";
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, new String[] { b.getContentid(), b.getSubsplace(), b.getUa(), b.getFilename() });
        }
        catch (DAOException e)
        {
            // TODO Auto-generated catch block
            LOG.error("insertReport出错：", e);
        }
    }

    /**
     * @param fileName
     *            记录这个文件已经被入库了，下次要比这个文件新的文件哦。 文件名类似如下： PUSH_20130105_00000001.log PUSHREPORT_20130105_00000001.log
     */
    public void putCsspLog(String fileName)
    {
        String sqlCode = "com.aspire.dotcard.syncAndroid.cssp.putCsspLog";
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, new String[] { fileName });
        }
        catch (DAOException e)
        {
            // TODO Auto-generated catch block
            LOG.error("putCsspLog出错：", e);
        }
    }

    /*
     * 文件类型 1 是 PUSH_20130105_00000001.log, 2 是 PUSHREPORT_20130105_00000001.log
     */
    public String getMaxFileName(int fileType)
    {
        String sqlCode = "com.aspire.dotcard.syncAndroid.cssp.getMaxFileName";
        try
        {
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] { fileType });
            if (rs.next())
            {
                return rs.getString(1);
            }
        }
        catch (DAOException e)
        {
            // TODO Auto-generated catch block
            LOG.error("getMaxFileName出错：", e);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            LOG.error("getMaxFileName出错：", e);
        }
        return null;
    }

    /**
     * fileType: 得到今天这个类型的文件列表 * 文件类型 1 是 PUSH_20130105_00000001.log, 2 是 PUSHREPORT_20130105_00000001.log
     * 
     * @return
     */
    public Set<String> getAllFiles(int fileType)
    {
        Set<String> set = new HashSet<String>();
        // select filename from t_a_cssp_log where filetype = ? and trunc(sysdate)=trunc(lupdate)
        String sqlCode = "com.aspire.dotcard.syncAndroid.cssp.getAllFiles";
        try
        {
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] { fileType });

            while (rs.next())
            {
                set.add(rs.getString(1));
            }
        }
        catch (DAOException e)
        {
            // TODO Auto-generated catch block
            LOG.error("getAllFiles出错：", e);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            LOG.error("getAllFiles出错：", e);
        }
        return set;
    }

    // 记录一下新来的t_a_pushreport的下载量
    public List getDowncountList()
    {
        List list = new ArrayList();
        RowSet rs = null;
        // String sql = "select a.contentid,count(1) from t_a_push a,t_a_pushreport b"+
        // " ,t_device d "+
        // " where a.pushid=b.pushid "+
        // " and a.ua = d.device_name"+
        // " and b.status=0"+
        // " and d.os_id=9 "+
        // " and b.handle_staus=1"+
        // " group by contentid";
        String sqlCode = "CSSPDAO.getDowncountList";
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            while (rs.next())
            {
                list.add(rs.getString(1) + ":" + rs.getInt(2));
            }

        }
        catch (DAOException e)
        {
            LOG.error(e);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    // 因为要不要在这类发送JMS，故不要单个处理CONTENTID.被我注销了。aiyan 2013-04-14
    // public void realtimeDowncount(){
    // TransactionDB tdb = null;
    // //Producer producer = null;
    // try{
    // List<String> list = getDowncountList();
    // tdb = TransactionDB.getTransactionInstance();
    // //producer = ProducerPool.getInstance().getProducer();
    // for(int i = 0;i<list.size();i++){
    // String msg = list.get(i);
    // //"insert into t_a_content_downcount(contentid,downcount) values(?,?)"
    // String sqlCode = "CSSPDAO.realtimeDowncount";
    // tdb.executeBySQLCode(sqlCode, msg.split(":"));
    // //addJMS(new CommitReqMSG(transactionID+":0"));
    // //addJMS(producer,new CountUpdateReqMSG(msg+":1:0"));
    // }
    // tdb.commit();
    // //producer.commit();
    //			
    // } catch (DAOException e) {
    // LOG.error("下载数量出现异常。DB事务回滚。", e);
    // if (tdb != null) {
    // tdb.rollback();
    // }
    // }
    //		
    // // catch (JMSException e) {
    // // LOG.error("JMS的问题要注意啊。。。enen...");
    // // LOG.error("下载数量。JMS事务回滚。", e);
    // // if (tdb != null) {
    // // producer.rollback();
    // // }
    // // }
    //		
    // finally {
    // if (tdb != null) {
    // tdb.close();
    // }
    // // if(producer!=null){
    // // try {
    // // ProducerPool.getInstance().returnObject(producer);
    // // } catch (JMSException e) {
    // // // TODO Auto-generated catch block
    // // LOG.error("归还JMS的producer时出错，可能是JMS连接断掉了。");
    // // }
    // // }
    // }
    //		
    // }

    // private void addJMS(Producer producer,MSG msg) throws JMSException{
    // producer.sendMessage(msg.getData());
    // }

    // 记录一下新来的t_a_pushreport的下载量
    public void realtimeDowncount()
    {
        TransactionDB tdb = null;
        try
        {
            // insert into t_a_downcount(contentid,downcount) select a.contentid,count(1) from t_a_push a,t_a_pushreport
            // b
            // ,t_device d
            // where a.pushid=b.pushid
            // and a.ua = d.device_name
            // and b.status=0
            // and d.os_id=9
            // and b.handle_staus=-1
            // group by contentid
            String sqlCode1 = "com.aspire.dotcard.syncAndroid.cssp.realtimeDowncount.INSERT";
            // sql="update t_a_pushreport c set handle_status=0 where handle_status=-1";
            String sqlCode2 = "com.aspire.dotcard.syncAndroid.cssp.realtimeDowncount.UPDATE";

            // 这个地方要把实时下载量消息送到T_A_MESSAGES中

            // 事务模式
            tdb = TransactionDB.getTransactionInstance();
            tdb.executeBySQLCode(sqlCode1, null);
            tdb.executeBySQLCode(sqlCode2, null);
            tdb.commit();
        }
        catch (DAOException e)
        {
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

    // //需要清理表t_a_push（保留3天内）和t_a_pushreport（保留没有处理的，就是HANDLE_STATUS=-1） add by aiyan 2013-04-25
    // public void clearData() {
    // // TODO Auto-generated method stub
    //		
    // }

    /**
     * 免费应用下载回执日志表 T_free_dl_report add by fanqh
     */
    public synchronized void batchInsertFreeDownloadReport(Queue<Bean> beanList)
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug(Thread.currentThread().getName() + "====> 进入batchInsertFreeDownloadReport()方法");
        }

        if(beanList==null){
            return;
        }
        
        
        String sqlCode = "com.aspire.dotcard.syncAndroid.cssp.insertFreeDownloadReport";
        // "insert into t_free_dl_report(id,sequenceid,pushid,status,outputtime,params,useraccesstype,lupdatetime,flag)
        // values(seq_t_free_dl_report_id.nextval,?,?,?,?,?,?,?,?)";

        Object mutiParas[][] = null;
        int batchsize = 2000;
        int batchCount = 2000;

        if (beanList.size() > batchCount)
        {
            mutiParas = new Object[batchCount][SIZE_8];
        }
        else
        {
            batchCount = beanList.size();
            mutiParas = new Object[batchCount][SIZE_8];
        }
        
        int n = 1;
        for (int i = 0; i < batchCount;)
        {
            FreeDLReportBean bean = (FreeDLReportBean)beanList.poll();
            if (bean == null)
            {
                continue;
            }
            mutiParas[i][0] = bean.getSequenceId();
            mutiParas[i][1] = bean.getPushId();
            mutiParas[i][2] = bean.getStatus();
            mutiParas[i][3] = bean.getOutputTime();
            mutiParas[i][4] = bean.getParams();
            mutiParas[i][5] = bean.getUserAccessType();
            mutiParas[i][6] = bean.getLupdatetime();
            mutiParas[i][7] = bean.getFlag();
            
            i++;
            bean = null;
            
            if (i == batchCount)
            {
                if (beanList.size() >= batchsize)
                {
                    i = 0;
                    batchCount = batchsize;
                    try{
                        DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
                    }
                    catch (Exception e)
                    {
                        LOG.error("导入免费应用下载回执日志表 T_free_dl_report出错：", e);
                    }finally{
                        mutiParas = new Object[batchCount][SIZE_8];
                    }

                }
                else if (beanList.size() > 0 && beanList.size() < batchsize)
                {
                    i = 0;
                    batchCount = beanList.size();
                    try{
                        DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
                    }
                    catch (Exception e)
                    {
                        LOG.error("导入免费应用下载回执日志表 T_free_dl_report出错：", e);
                    }finally{
                        mutiParas = new Object[batchCount][SIZE_8];
                    }
                }
                else
                {
                    try{
                        DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
                    }
                    catch (Exception e)
                    {
                        LOG.error("导入免费应用下载回执日志表 T_free_dl_report出错：", e);
                    }finally{
                        mutiParas = null;
                    }
                    break;
                }
            }
            
            
                if (LOG.isDebugEnabled())
                {
                    if ((n++) % 50000 == 0)
                    {
                        LOG.debug(Thread.currentThread().getName() + "====> 批处理(" + batchCount + ")条, 待处理(" + beanList.size() + ")条");
                    }
                }
          }
       
        if (LOG.isDebugEnabled())
        {
            LOG.debug(Thread.currentThread().getName() + "====> 离开batchInsertFreeDownloadReport()方法");
        }
    }

    /**
     * 免费应用下载回执日志表T_free_dl_order add by fanqh
     */
    public synchronized void batchInsertFreeDownLoadOrder(Queue<Bean> beanList)
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug(Thread.currentThread().getName() + "====> 进入batchInsertFreeDownLoadOrder()方法");
        }
        if(beanList==null){
            return;
        }
        
        String sqlCode = "com.aspire.dotcard.syncAndroid.cssp.insertFreeDownLoadOrder";

        Object mutiParas[][] = null;
        int batchsize = 2000;
        int batchCount = 2000;

        if (beanList.size() > batchCount)
        {
            mutiParas = new Object[batchCount][SIZE_41];
        }
        else
        {
            batchCount = beanList.size();
            mutiParas = new Object[batchCount][SIZE_41];
        }

        
            int n = 1;
            for (int i = 0; i < batchCount;)
            {
                FreeDLOrderBean bean = (FreeDLOrderBean)beanList.poll();
                if (bean == null)
                {
                    continue;
                }
              
                mutiParas[i][0] = bean.getOrderNumber();
                mutiParas[i][1] = bean.getDestUserID();
                mutiParas[i][2] = bean.getFeeUserID();
                mutiParas[i][3] = bean.getContentID();
                mutiParas[i][4] = bean.getPackageCode();
                mutiParas[i][5] = bean.getChannelID();
                mutiParas[i][6] = bean.getSubsPlace();
                mutiParas[i][7] = bean.getUA();
                mutiParas[i][8] = bean.getSpCode();
                mutiParas[i][9] = bean.getServiceCode();
                mutiParas[i][10] = bean.getProductCode();
                mutiParas[i][11] = bean.getOrderStartTime();
                mutiParas[i][12] = bean.getDownloadTime();
                mutiParas[i][13] = bean.getAccessModeID();
                mutiParas[i][14] = bean.getPayWay();
                mutiParas[i][15] = bean.getDiscountRate();
                mutiParas[i][16] = bean.getPrice();
                mutiParas[i][17] = bean.getFee();
                mutiParas[i][18] = bean.getActionType();
                mutiParas[i][19] = bean.getDownChannelID();
                mutiParas[i][20] = bean.getAppName();
                mutiParas[i][21] = bean.getUserType();
                mutiParas[i][22] = bean.getOndemandType();
                mutiParas[i][23] = bean.getApn();
                mutiParas[i][24] = bean.getApnAddr();
                mutiParas[i][25] = bean.getUserAccessType();
                mutiParas[i][26] = bean.getExpireTime();
                mutiParas[i][27] = bean.getOrderFlag();
                mutiParas[i][28] = bean.getAcessPlat();
                mutiParas[i][29] = bean.getInterChannel();
                mutiParas[i][30] = bean.getTouchVersion();
                mutiParas[i][31] = bean.getBrowser();
                mutiParas[i][32] = bean.getSPM();
                mutiParas[i][33] = bean.getMMSource();
                mutiParas[i][34] = bean.getMMInstallId();
                mutiParas[i][35] = bean.getMMVisitorId();
                mutiParas[i][36] = bean.getMMDeviceInfo();
                mutiParas[i][37] = bean.getReferrer();
                mutiParas[i][38] = bean.getFromarea();
                mutiParas[i][39] = bean.getLupdate();
                mutiParas[i][40] = bean.getFlag();

                i++;

                bean = null;
                
                if (i == batchCount)
                {
                    if (beanList.size() >= batchsize)
                    {
                        i = 0;
                        batchCount = batchsize;
                        try
                        {
                            DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
                        }
                        catch (Exception e)
                        {
                            LOG.error("导入免费应用下载回执日志表T_free_dl_order出错：", e);
                        }finally{
                            mutiParas = new Object[batchCount][SIZE_41];
                        }
                    }
                    else if (beanList.size() > 0 && beanList.size() < batchsize)
                    {
                        i = 0;
                        batchCount = beanList.size();
                        try
                        {
                            DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
                        }
                        catch (Exception e)
                        {
                            LOG.error("导入免费应用下载回执日志表T_free_dl_order出错：", e);
                        }finally{
                            mutiParas = new Object[batchCount][SIZE_41];
                        }
                    }
                    else
                    {
                        try
                        {
                            DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
                        }
                        catch (Exception e)
                        {
                            LOG.error("导入免费应用下载回执日志表T_free_dl_order出错：", e);
                        }finally{
                            mutiParas = null;
                        }
                        break;
                    }
                }
                
                if (LOG.isDebugEnabled())
                {
                    if ((n++) % 50000 == 0)
                    {
                        LOG.debug(Thread.currentThread().getName() + "====> 批处理(" + batchCount + ")条, 待处理(" + beanList.size() + ")条");
                    }
                }
            }

            if (LOG.isDebugEnabled())
            {
                LOG.debug(Thread.currentThread().getName() + "====> 离开batchInsertFreeDownLoadOrder()方法");
            }
          
       
    }

   
    //private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");

    public List<DLReportBean> getFreeDlReportOrder(int number)
    {
        if(number<1){
            number = 1024*2;
        }
        List<DLReportBean> list = new ArrayList<DLReportBean>(number);
        String sqlCode = "com.aspire.dotcard.syncAndroid.cssp.getFreeDlReportOrder";

        try
        {
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, new Integer[] { number });
            while (rs.next())
            {
                String orderid = rs.getString("orderid");
                String reportid = rs.getString("reportid");
                String contentid = rs.getString("contentid");
                String subsplace = rs.getString("subsplace");
                String ua = rs.getString("ua");
                DLReportBean r = new DLReportBean();
                r.setReportid(reportid);
                r.setOrderid(orderid);
                r.setContentid(contentid);
                r.setSubsplace(subsplace);
                r.setUa(ua);
                r.setStatus("-1");
                // r.setLupdate(lupdate);
                list.add(r);
            }
        }
        catch (DAOException e)
        {
            LOG.error("查询t_free_dl_order,t_free_dl_report表报错：", e);
        }
        catch (Exception e)
        {
            LOG.error("getFreeDlReportOrder出错：", e);
        }
        return list;
    }

    /**
     * 从t_free_dl_report,t_free_dl_order把数据分批导入到T_A_REPORT表中,
     * 并对t_free_dl_report,t_free_dl_order做flag标志更新
     * add by fanqh
     */
    public synchronized void insertDownloadReport()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug(Thread.currentThread().getName() + "====> 进入insertDownloadReport()方法");
            LOG.debug(Thread.currentThread().getName() + "从t_free_dl_report,t_free_dl_order把数据分批导入到T_A_REPORT表中开始");
        }

        String sqlCode1 = "com.aspire.dotcard.syncAndroid.cssp.insertDownloadReport.INSERT";
        String sqlCode2 = "com.aspire.dotcard.syncAndroid.cssp.freeDownLoadReport.UPDATE";
        String sqlCode3 = "com.aspire.dotcard.syncAndroid.cssp.freeDownLoadOrder.UPDATE";

        int batch = 2000000;//这个值设置每次定时任务处理数据量

        List<DLReportBean> allList = getFreeDlReportOrder(batch);

        if (allList == null || allList.size() <= 0)
        {
            return;
        }
        LOG.info("本次导入到T_A_REPORT表数据量："+allList.size());
        
        Set<String> s1 = new HashSet<String>();
        Set<String> s2 = new HashSet<String>();
        List<DLReportBean> fList = null;
        for (int fromIndex = 0; fromIndex < allList.size(); fromIndex = fromIndex + 2000)
        {
            
            int toIndex = fromIndex + 2000;
            if (allList.size() <= toIndex)
            {
                toIndex = allList.size();
            }
            fList = allList.subList(fromIndex, toIndex);

            if (LOG.isDebugEnabled())
            {
                LOG.debug(fromIndex+"-"+toIndex + ",");
            }
            
            if (fList == null || (fList != null && fList.size() < 1))
            {
                break;
            }

            Object mutiParas[][] = new Object[fList.size()][3];

            for (int i = 0; i < fList.size(); i++)
            {
                DLReportBean bean = fList.get(i);
                if (bean == null)
                {
                    continue;
                }
                mutiParas[i][0] = bean.getContentid();
                mutiParas[i][1] = bean.getSubsplace();
                mutiParas[i][2] = bean.getUa();

                s1.add(bean.getReportid());
                s2.add(bean.getOrderid());
                bean = null;
            }

            try
            {
                if (mutiParas.length > 0)
                {
                    DB.getInstance().executeBatchBySQLCode(sqlCode1, mutiParas);
                }
            }
            catch (Exception e)
            {
                LOG.error("从t_free_dl_report,t_free_dl_order把数据分批导入到T_A_REPORT表中出错：", e);
                e.printStackTrace();
            }
            finally
            {
                mutiParas = null;
            }
        }
        

        if (s1.size() > 0)
        {//更新表状态t_free_dl_report
            Object mutiParasUpdateByReportId[][] = new Object[s1.size()][1];
            int i = 0;
            for (String s : s1)
            {
                mutiParasUpdateByReportId[i++][0] = s;
            }
            try
            {
                DB.getInstance().executeBatchBySQLCode(sqlCode2, mutiParasUpdateByReportId);
                LOG.info("本次更新表状态t_free_dl_report数据量："+s1.size());
            }
            catch (Exception e)
            {
                LOG.error("对t_free_dl_report做flag标志更新出错：", e);
                e.printStackTrace();
            }
            finally
            {
                mutiParasUpdateByReportId = null;
                s1.clear();
                s1 = null;
            }
        }
        
        if (s2.size() > 0)
        {//更新表状态t_free_dl_order
            Object mutiParasUpdateByOrderId[][] = new Object[s2.size()][1];
            int j = 0;
            for (String s : s2)
            {
                mutiParasUpdateByOrderId[j++][0] = s;
            }
            try
            {
                DB.getInstance().executeBatchBySQLCode(sqlCode3, mutiParasUpdateByOrderId);
                LOG.info("本次更新表状态t_free_dl_order数据量："+s2.size());
            }
            catch (Exception e)
            {
                LOG.error("对t_free_dl_order做flag标志更新出错：", e);
                e.printStackTrace();
            }
            finally
            {
                mutiParasUpdateByOrderId = null;
                s2.clear();
                s2 = null;
            }
        }
  
        if (fList != null)
        {
            fList.clear();
            fList = null;
        }
        if (allList != null)
        {
            allList.clear();
            allList = null;
        }
        s1.clear();
        s1 = null;
        s2.clear();
        s2 = null;
        
        if (LOG.isDebugEnabled())
        {
            LOG.debug(Thread.currentThread().getName() + "从t_free_dl_report,t_free_dl_order把数据分批导入到T_A_REPORT表中结束");
            LOG.debug(Thread.currentThread().getName() + "====> 离开insertDownloadReport()方法");
        }
    }

}
