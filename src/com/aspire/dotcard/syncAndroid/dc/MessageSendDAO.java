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
     * ��־����
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
     * ��ѯ״̬Ϊ-1(��δ����ȥ�������Ļ��߷���δ�ɹ�������)
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
            logger.debug("����״̬-1��ѯmessageSend��Ϣʧ��...");
        }
        return list;
    }
    
    /**
     * ��������id��ѯMessage
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
            logger.debug("��������id��ѯmessageSend��Ϣʧ��...");
        }
        return list;
    }
    
    //����transactionID ��״̬�������е�-5���-1,������Ϊ�˻��ܵ�EXCEL��Ʒ��ʱ��Ū�ġ�
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
            logger.error("transactionID="+transactionID+" ��oldStatus="+oldStatus+"newStatus="+newStatus+"����" , e);
        }
    }
    
    /**
     * ����id�޸ļ�¼״̬������޸�ʱ��
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
            logger.error("����id�޸ļ�¼״̬������޸�ʱ��,���ݿ����ʧ��" + e);
        }
    }
    
    /**
     * ����transactionid�޸�t_a_massages���װ���������޸�ʱ��
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
            logger.error("����id�޸ļ�¼״̬������޸�ʱ��,���ݿ����ʧ��" + e);
        }
    }
    
    
    /**
     * ��������id�޸ļ�¼״̬������޸�ʱ��
     * @param transactionId         ��sql��������
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
            logger.error("��������id�޸ļ�¼״̬������޸�ʱ��,���ݿ����ʧ��" + e);
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
	//ҪT_A_MESSAGES2��t_a_messages2_ext������������д��
	public void handleMultiMessages(String transactionId,List<String> messages,String status,String ifext) throws DAOException {
		// TODO Auto-generated method stub
		TransactionDB tdb = null;
		if(messages==null||messages.size()==0){
			logger.error("handleMultiMessages�����쳣��messages��"+messages);
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
			logger.error("�����Ϣ��Ҫ��չ��ʱ����",e);
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
            logger.debug("����״̬-1��ѯgetNoTranMessage��Ϣʧ��...");
        }
        return list;
	}
	
	/*
	 * ɨ��t_a_massages����status״̬Ϊ-1��
	 * ����massage����      
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
            logger.debug("����״̬-1��ѯgetTranMessage��Ϣʧ��...",e);
        }
        return list;
	}
    
    
}
