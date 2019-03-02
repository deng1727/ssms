package com.aspire.dotcard.syncAndroid.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.RowSet;

import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class ContextUtil
{

    static JLogger LOG = LoggerFactory.getLogger(ContextUtil.class);
    // 发起方交易流水号；在发起方唯一标识一个交易的流水号，系统内唯一
    // 定义规则:YYYYMMDDMMMMMMM
    // 其中：YYYY为年，MM为月，DD为日
    // MMMMMMM为0000001到9999999的数字串，从小到大，循环使用
    private static final SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
    // private static final DecimalFormat df2 = new DecimalFormat("0000000");

    

    // private static String preToDay = df.format(new Date());
    public synchronized static String getTransactionID()
    {
        String seq = null;
        String toDay = df.format(new Date());
        // if(!toDay.equals(preToDay)){
        // LOG.debug("change-TransactionID:"+toDay+"--"+preToDay);
        // seq = getSeq(toDay);
        // seq = df2.format((Integer.parseInt(seq)+1));
        // preToDay = toDay;
        // return toDay+seq;
        // }
        // if(seq==null){
        seq = getSeq(toDay);
        LOG.debug("DB seq:" + seq);
        // }
        // seq = df2.format((Integer.parseInt(seq)+1)%9999999);
        // = df2.format((Integer.parseInt(seq)+1));
        // LOG.debug("seq:"+seq);
        // preToDay = toDay;
        
        //(add 2014-01-07) 修改序列号不唯一问题
        if ((toDay + "999").equals(seq))
        {
            LOG.info("每秒产生1000个序列号：0-999，为了保证唯一，需要暂停一秒，当前序列号："+seq);
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
               LOG.error("获取交易流水号暂停1秒时，线程报错!"+e.getMessage());
            }
        }
        
        return seq;
    }

    // public static void main(String[] argv) throws InterruptedException{
    // while(true){
    // String a = getTransactionID();
    // System.out.println(a);
    // Thread.sleep(10000);
    // }
    //
    // }
    // MMMMMMM为0000001到9999999的数字串，从小到大，循环使用
    private synchronized static String getSeq(String toDay)
    {
        // select substr(max(transactionid),9,7) from t_a_messages where to_char(createdate,'yyyyMMdd')=20130129
        String sqlCode = "com.aspire.dotcard.syncAndroid.common.getSeq";
        // select SEQ_a_tid.Nextval from dual

        // String sqlCode = "com.aspire.dotcard.syncAndroid.common.getSeque";

        String dbTransactionID = null;
        RowSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            if (rs.next())
            {
                String s1 = rs.getString(1);
                if (s1.length() == 1)
                {
                    s1 = "00" + s1;
                }
                else if (s1.length() == 2)
                {
                    s1 = "0" + s1;
                }
                dbTransactionID = toDay + s1;

            }
        }
        catch (Exception ex)
        {
            // throw new DAOException("isExistsGContent error!!", ex);
            LOG.error(ex);
        }
        finally
        {
            DB.close(rs);
        }
        return dbTransactionID;
    }

    // public synchronized static String getTransactionID2(){
    // String toDay = df.format(new Date());
    // if(seq2==null){
    // seq2 = getSeq2(toDay);
    // }
    // seq2 = df2.format((Integer.parseInt(seq2)+1)%9999999);
    // return toDay+seq2;
    // }
    //	
    // //MMMMMMM为0000001到9999999的数字串，从小到大，循环使用
    // private static String getSeq2(String toDay){
    // //select substr(max(transactionid),9,7) from t_a_messages2 where to_char(createdate,'yyyyMMdd')=20130129
    // String sqlCode = "com.aspire.dotcard.syncAndroid.common.getSeq2";
    // String dbTransactionID = null;
    // RowSet rs = null;
    // try {
    // rs = DB.getInstance().queryBySQLCode(sqlCode,new Object[]{toDay});
    // if (rs.next()) {
    // dbTransactionID = rs.getString(1);
    // }
    // } catch (Exception ex) {
    // //throw new DAOException("isExistsGContent error!!", ex);
    // LOG.error(ex);
    // } finally {
    // DB.close(rs);
    // }
    // return dbTransactionID;
    // }
}
