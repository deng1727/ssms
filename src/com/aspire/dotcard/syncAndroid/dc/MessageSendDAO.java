package com.aspire.dotcard.syncAndroid.dc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.ponaadmin.web.db.TransactionDB;


public class MessageSendDAO
{
    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(MessageSendDAO.class);
    
    private MessageSendDAO(){
        
    }
    
    private static MessageSendDAO instance = null;
    
    public static MessageSendDAO getInstance()
    {
        if(instance==null)
        {
            instance = new MessageSendDAO();
        }
        return instance;
    }
    
    /**
     * 查询状态为-1(即未发送去数据中心或者发送未成功的数据)
     * @return
     */
    public List getMessageSendByStatus(int status)
    {
        List list = new ArrayList();
        String sqlCode="syncAndroid.dataCenter.getMessageSendByStatus";
        //select * from t_a_messages m where m.status=? order by m.createdate asc,m.transactionid asc,m.id asc
        try
        {
            Object[] paras = {status};
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while(rs.next())
            {
            	MessageVO vo = new MessageVO();
            	vo.setId(rs.getInt("ID"));
            	vo.setType(rs.getString("TYPE"));
            	vo.setMessage(rs.getString("MESSAGE"));
            	vo.setStatus(rs.getInt("STATUS"));
            	vo.setTransactionId(rs.getString("TRANSACTIONID"));
            	vo.setLupdate(rs.getDate("LUPDATE"));
                list.add(vo);
            }
            
        }
        catch (Exception e)
        {
            logger.debug("根据状态-1查询messageSend信息失败...");
        }
        return list;
    }
    
    /**
     * 根据事务id查询Message
     * @param transactionId
     * @return
     */
    public List getMessageSendByTransactionId(String transactionId)
    {
        List list = new ArrayList();
        String sqlCode="syncAndroid.dataCenter.getMessageSendByTransactionId";
        try
        {
            Object[] paras = {transactionId};
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while(rs.next())
            {
                MessageVO vo = new MessageVO();
                vo.setId(rs.getInt("ID"));
                vo.setType(rs.getString("TYPE"));
                vo.setMessage(rs.getString("MESSAGE"));
                vo.setStatus(rs.getInt("STATUS"));
                vo.setTransactionId(rs.getString("TRANSACTIONID"));
                vo.setLupdate(rs.getDate("LUPDATE"));
                list.add(vo);
            }
            
        }
        catch (Exception e)
        {
            logger.debug("根据事务id查询messageSend信息失败...");
        }
        return list;
    }
    
    //将该transactionID 的状态由运行中的-5变成-1,这里是为了货架到EXCEL商品的时候弄的。
    public void changeStatus(String transactionID,int oldStatus,int newStatus)
    {
        Object[] paras = {newStatus,transactionID,oldStatus};
        String sqlCode = "syncAndroid.dataCenter.changeStatus";
        //update t_a_messages m set m.status=?,m.lupdate=sysdate where  m.transactionid=? and m.status = ?
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            logger.error("transactionID="+transactionID+" 将oldStatus="+oldStatus+"newStatus="+newStatus+"出错！" , e);
        }
    }
    
    /**
     * 根据id修改记录状态和最后修改时间
     * @param id
     */
    public void updateStatus(int status,int id)
    {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = sdf.format(new Date());
        Object[] paras = {status,id};
        String sqlCode = "syncAndroid.dataCenter.updateStatusAndLupdate";
        //update t_a_messages m set m.status=?,m.lupdate=sysdate where id = ?
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            logger.error("根据id修改记录状态和最后修改时间,数据库操作失败" + e);
        }
    }
    
    /**
     * 根据transactionid修改t_a_massages表的装条码和最后修改时间
     */
    
    public void updateStatusWithTran(int status,String transactionId)
    {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = sdf.format(new Date());
        Object[] paras = {status,transactionId};
        String sqlCode = "syncAndroid.dataCenter.updateStatusAndLupdate.tran";
        //update t_a_messages m set m.status=?,m.lupdate=sysdate where transactionid = ?
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            logger.error("根据id修改记录状态和最后修改时间,数据库操作失败" + e);
        }
    }
    
    
    /**
     * 根据事务id修改记录状态和最后修改时间
     * @param transactionId         此sql配置内无
     */
    public void updateByTransactionId(int stauts,String transactionId)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        Object[] paras = {stauts,date,transactionId};
        String sqlCode = "syncAndroid.dataCenter.updateByTransactionId";
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            logger.error("根据事务id修改记录状态和最后修改时间,数据库操作失败" + e);
        }
    }
    
	public void handleSingleMessages(String transactionId,String messages,String status,String ifext) throws DAOException {
		// TODO Auto-generated method stub
		//syncAndroid.dataCenter.handleSingleMessages=
			//insert into t_a_messages2(message,createdate,transactionid,status,ifext) values(?,sysdate,?,?,?)
		String sql = "syncAndroid.dataCenter.handleSingleMessages";
		Object[] paras = {messages,transactionId,status,ifext};
		DB.getInstance().executeBySQLCode(sql, paras);
	}

//	create table T_A_MESSAGES2(
//			message varchar2(4000),
//			createdate date default sysdate,
//			lupdate date,
//			TRANSACTIONID varchar2(15) not null,
//			status number(1) not null,
//			type varchar2(50) not null,
//			ifext number(1) not null,
//			primary key(TRANSACTIONID)
//
//			)
//
//			create table t_a_messages2_ext(
//			message varchar2(4000),
//			TRANSACTIONID varchar2(15) not null,
//			sortid number(5) not null,
//			id number(10) not null,
//			primary key(id)
//			)
	//要T_A_MESSAGES2，t_a_messages2_ext把这两个表都填写。
	public void handleMultiMessages(String transactionId,List<String> messages,String status,String ifext) throws DAOException {
		// TODO Auto-generated method stub
		TransactionDB tdb = null;
		if(messages==null||messages.size()==0){
			logger.error("handleMultiMessages出现异常！messages："+messages);
			return;
		}
		try{
			
//syncAndroid.dataCenter.handleMultiMessages1=
			//insert into t_a_messages2(message,createdate,transactionid,status,ifext) values('',sysdate,?,?,?)
//syncAndroid.dataCenter.handleMultiMessages2=
			//insert into t_a_messages2_ext(message,transactionid,sortid,id) values(?,?,?,SEQ_t_a_messages2_ext.NEXTVAL)

			
	        tdb = TransactionDB.getTransactionInstance();
	        Object[] paras = {transactionId,status,ifext};
	        tdb.executeBySQLCode("syncAndroid.dataCenter.handleMultiMessages1", paras);
	        for(int i=0;i<messages.size();i++){
	        	Object[] p = {messages.get(i),transactionId,i};
	        	 tdb.executeBySQLCode("syncAndroid.dataCenter.handleMultiMessages2",p);
	        }
	        tdb.commit();
		}catch(Exception e){
			tdb.rollback();
			logger.error("组合消息需要扩展表时出错",e);
			throw new DAOException(e);
		}finally{
			if(tdb!=null){
				tdb.close();
			}
		}
		
	}

	public List<MessageVO> getNoTranMessage(int noTranLimit) {
		// TODO Auto-generated method stub
        List list = new ArrayList();
        String sqlCode="syncAndroid.dataCenter.getNoTranMessage";
//select * from (select * from t_a_messages m where m.status=-1 and m.transactionid is null order by m.createdate asc,m.transactionid asc,m.id asc) where rownum<?
        try
        {
            Object[] paras = {noTranLimit};
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while(rs.next())
            {
            	MessageVO vo = new MessageVO();
            	vo.setId(rs.getInt("ID"));
            	vo.setType(rs.getString("TYPE"));
            	vo.setMessage(rs.getString("MESSAGE"));
            	vo.setStatus(rs.getInt("STATUS"));
            	vo.setTransactionId(rs.getString("TRANSACTIONID"));
            	vo.setLupdate(rs.getDate("LUPDATE"));
                list.add(vo);
            }
            
        }
        catch (Exception e)
        {
            logger.debug("根据状态-1查询getNoTranMessage信息失败...");
        }
        return list;
	}
	
	/*
	 * 扫描t_a_massages表中status状态为-1的
	 * 并用massage接收      
	 */

	public List<MessageVO> getTranMessage() {
		// TODO Auto-generated method stub
        List list = new ArrayList();
        String sqlCode="syncAndroid.dataCenter.getTranMessage";
        //select * from t_a_messages m where m.status=-1 and m.transactionid is not null order by m.createdate asc,m.transactionid asc,m.id asc
        try
        {
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            while(rs.next())
            {
            	MessageVO vo = new MessageVO();
            	vo.setId(rs.getInt("ID"));
            	vo.setType(rs.getString("TYPE"));
            	vo.setMessage(rs.getString("MESSAGE"));
            	vo.setStatus(rs.getInt("STATUS"));
            	vo.setTransactionId(rs.getString("TRANSACTIONID"));
            	vo.setLupdate(rs.getDate("LUPDATE"));
                list.add(vo);
            }
            
        }
        catch (Exception e)
        {
            logger.debug("根据状态-1查询getTranMessage信息失败...",e);
        }
        return list;
	}
    
    
}
