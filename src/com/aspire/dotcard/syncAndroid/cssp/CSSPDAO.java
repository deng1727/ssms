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
     * ��¼��־��ʵ������
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
            LOG.error("insertPush����", e);
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
            LOG.error("insertPushReport����", e);
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
            LOG.error("insertReport����", e);
        }
    }

    /**
     * @param fileName
     *            ��¼����ļ��Ѿ�������ˣ��´�Ҫ������ļ��µ��ļ�Ŷ�� �ļ����������£� PUSH_20130105_00000001.log PUSHREPORT_20130105_00000001.log
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
            LOG.error("putCsspLog����", e);
        }
    }

    /*
     * �ļ����� 1 �� PUSH_20130105_00000001.log, 2 �� PUSHREPORT_20130105_00000001.log
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
            LOG.error("getMaxFileName����", e);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            LOG.error("getMaxFileName����", e);
        }
        return null;
    }

    /**
     * fileType: �õ�����������͵��ļ��б� * �ļ����� 1 �� PUSH_20130105_00000001.log, 2 �� PUSHREPORT_20130105_00000001.log
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
            LOG.error("getAllFiles����", e);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            LOG.error("getAllFiles����", e);
        }
        return set;
    }

    // ��¼һ��������t_a_pushreport��������
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

    // ��ΪҪ��Ҫ�����෢��JMS���ʲ�Ҫ��������CONTENTID.����ע���ˡ�aiyan 2013-04-14
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
    // LOG.error("�������������쳣��DB����ع���", e);
    // if (tdb != null) {
    // tdb.rollback();
    // }
    // }
    //		
    // // catch (JMSException e) {
    // // LOG.error("JMS������Ҫע�Ⱑ������enen...");
    // // LOG.error("����������JMS����ع���", e);
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
    // // LOG.error("�黹JMS��producerʱ����������JMS���Ӷϵ��ˡ�");
    // // }
    // // }
    // }
    //		
    // }

    // private void addJMS(Producer producer,MSG msg) throws JMSException{
    // producer.sendMessage(msg.getData());
    // }

    // ��¼һ��������t_a_pushreport��������
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

            // ����ط�Ҫ��ʵʱ��������Ϣ�͵�T_A_MESSAGES��

            // ����ģʽ
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

    // //��Ҫ�����t_a_push������3���ڣ���t_a_pushreport������û�д���ģ�����HANDLE_STATUS=-1�� add by aiyan 2013-04-25
    // public void clearData() {
    // // TODO Auto-generated method stub
    //		
    // }

    /**
     * ���Ӧ�����ػ�ִ��־�� T_free_dl_report add by fanqh
     */
    public synchronized void batchInsertFreeDownloadReport(Queue<Bean> beanList)
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug(Thread.currentThread().getName() + "====> ����batchInsertFreeDownloadReport()����");
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
                        LOG.error("�������Ӧ�����ػ�ִ��־�� T_free_dl_report����", e);
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
                        LOG.error("�������Ӧ�����ػ�ִ��־�� T_free_dl_report����", e);
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
                        LOG.error("�������Ӧ�����ػ�ִ��־�� T_free_dl_report����", e);
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
                        LOG.debug(Thread.currentThread().getName() + "====> ������(" + batchCount + ")��, ������(" + beanList.size() + ")��");
                    }
                }
          }
       
        if (LOG.isDebugEnabled())
        {
            LOG.debug(Thread.currentThread().getName() + "====> �뿪batchInsertFreeDownloadReport()����");
        }
    }

    /**
     * ���Ӧ�����ػ�ִ��־��T_free_dl_order add by fanqh
     */
    public synchronized void batchInsertFreeDownLoadOrder(Queue<Bean> beanList)
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug(Thread.currentThread().getName() + "====> ����batchInsertFreeDownLoadOrder()����");
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
                            LOG.error("�������Ӧ�����ػ�ִ��־��T_free_dl_order����", e);
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
                            LOG.error("�������Ӧ�����ػ�ִ��־��T_free_dl_order����", e);
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
                            LOG.error("�������Ӧ�����ػ�ִ��־��T_free_dl_order����", e);
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
                        LOG.debug(Thread.currentThread().getName() + "====> ������(" + batchCount + ")��, ������(" + beanList.size() + ")��");
                    }
                }
            }

            if (LOG.isDebugEnabled())
            {
                LOG.debug(Thread.currentThread().getName() + "====> �뿪batchInsertFreeDownLoadOrder()����");
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
            LOG.error("��ѯt_free_dl_order,t_free_dl_report����", e);
        }
        catch (Exception e)
        {
            LOG.error("getFreeDlReportOrder����", e);
        }
        return list;
    }

    /**
     * ��t_free_dl_report,t_free_dl_order�����ݷ������뵽T_A_REPORT����,
     * ����t_free_dl_report,t_free_dl_order��flag��־����
     * add by fanqh
     */
    public synchronized void insertDownloadReport()
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug(Thread.currentThread().getName() + "====> ����insertDownloadReport()����");
            LOG.debug(Thread.currentThread().getName() + "��t_free_dl_report,t_free_dl_order�����ݷ������뵽T_A_REPORT���п�ʼ");
        }

        String sqlCode1 = "com.aspire.dotcard.syncAndroid.cssp.insertDownloadReport.INSERT";
        String sqlCode2 = "com.aspire.dotcard.syncAndroid.cssp.freeDownLoadReport.UPDATE";
        String sqlCode3 = "com.aspire.dotcard.syncAndroid.cssp.freeDownLoadOrder.UPDATE";

        int batch = 2000000;//���ֵ����ÿ�ζ�ʱ������������

        List<DLReportBean> allList = getFreeDlReportOrder(batch);

        if (allList == null || allList.size() <= 0)
        {
            return;
        }
        LOG.info("���ε��뵽T_A_REPORT����������"+allList.size());
        
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
                LOG.error("��t_free_dl_report,t_free_dl_order�����ݷ������뵽T_A_REPORT���г���", e);
                e.printStackTrace();
            }
            finally
            {
                mutiParas = null;
            }
        }
        

        if (s1.size() > 0)
        {//���±�״̬t_free_dl_report
            Object mutiParasUpdateByReportId[][] = new Object[s1.size()][1];
            int i = 0;
            for (String s : s1)
            {
                mutiParasUpdateByReportId[i++][0] = s;
            }
            try
            {
                DB.getInstance().executeBatchBySQLCode(sqlCode2, mutiParasUpdateByReportId);
                LOG.info("���θ��±�״̬t_free_dl_report��������"+s1.size());
            }
            catch (Exception e)
            {
                LOG.error("��t_free_dl_report��flag��־���³���", e);
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
        {//���±�״̬t_free_dl_order
            Object mutiParasUpdateByOrderId[][] = new Object[s2.size()][1];
            int j = 0;
            for (String s : s2)
            {
                mutiParasUpdateByOrderId[j++][0] = s;
            }
            try
            {
                DB.getInstance().executeBatchBySQLCode(sqlCode3, mutiParasUpdateByOrderId);
                LOG.info("���θ��±�״̬t_free_dl_order��������"+s2.size());
            }
            catch (Exception e)
            {
                LOG.error("��t_free_dl_order��flag��־���³���", e);
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
            LOG.debug(Thread.currentThread().getName() + "��t_free_dl_report,t_free_dl_order�����ݷ������뵽T_A_REPORT���н���");
            LOG.debug(Thread.currentThread().getName() + "====> �뿪insertDownloadReport()����");
        }
    }

}
