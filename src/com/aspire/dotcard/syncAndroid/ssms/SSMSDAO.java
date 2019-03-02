package com.aspire.dotcard.syncAndroid.ssms;

import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.ponaadmin.web.category.intervenor.IntervenorVO;
import com.aspire.ponaadmin.web.db.TransactionDB;


public class SSMSDAO
{
    /**
     * ��־����
     */
    protected static JLogger LOG = LoggerFactory.getLogger(SSMSDAO.class);
    
    private SSMSDAO(){
        
    }
    
    private static SSMSDAO instance = null;
    
    public static SSMSDAO getInstance()
    {
        if(instance==null)
        {
            instance = new SSMSDAO();
        }
        return instance;
    }
    
    /**
     * �ж�categoryId�Ƿ���ANDROID���ܣ�rootCategoryId��ANDROID���ܵĸ����ܡ�
     * @param categoryId
     * @param rootCategoryId
     * @return
     */
    public static boolean isAndroidCategory(String categoryId,String rootCategoryId){
    	String sqlCode="SSMSDAO.isAndroidCategory.SELECT";
        try
        {
            Object[] paras = {categoryId,rootCategoryId};
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if(rs.next()){
            	return true;
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return false;
    	
    }
    /**
     * �ж��Ƿ���android����Ӫ���ܡ�rootCategoryId��ANDROID���ܵĸ����ܡ�OPERATE
     * @param categoryId
     * @param rootCategoryId
     * @return
     */
//    <ConfigItem>
//    <Name>OPERATE_ROOT_CATEGORYID</Name>
//    <Id>0</Id>
//    <Description>487659139(android��Ʒ�ٵ�)��218637126�ر���218637125ר����1782551ר����</Description>
//    <Value>487659139|218637126|218637125|1782551</Value>
//    <Reserved/>
//</ConfigItem>  
    
    //rootOperateCategoryId��487659139|218637126|218637125|1782551 ��categoryId����Щ���ܻ�������Щ���ܵ��ӻ��ܾͷ���TURE
    public static boolean isOperateCategory(String categoryId,String rootOperateCategoryId){
    	String sqlCode="SSMSDAO.isAndroidCategory.SELECT";//���SQLCODE������������û��ɡ�add by aiyan 2013-05-18
    	
    	if(rootOperateCategoryId==null||rootOperateCategoryId.trim().length()==0){
    		return false;
    	}
    	String[] operateCategoryIdArr = rootOperateCategoryId.split("\\|");
    	for(int i=0;i<operateCategoryIdArr.length;i++){
            try{
                Object[] paras = {categoryId,operateCategoryIdArr[i]};
                RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
                if(rs.next()){
                	return true;
                }
                
            }
            catch (Exception e) {
                LOG.error(e);
            }
    	}
        return false;
    	
    }
    
	public void addMessages(MSGType type,String message) throws DAOException{
		addMessages(type,"",message,-1);
	}
	
	public void addMessages(MSGType type,String transactionID,String message,int status) throws DAOException{
		//PPMSDAO.addMessages.common
		//=insert into t_a_messages(id,status,type,transactionid,message) values(SEQ_T_A_MESSAGES.NEXTVAL,?,?,?,?)
		String sqlCode = "PPMSDAO.addMessages.common";
		Object[] paras = {status,type.toString(),transactionID,message};
		DB.getInstance().executeBySQLCode(sqlCode,paras);
	}
    
//	public void addMessages(MSGType type,String transactionID,String message) throws DAOException{
////		String sqlCode = "PPMSDAO.addMessages";
////		Object[] paras = {type.toString(),transactionID,message};
////		DB.getInstance().executeBySQLCode(sqlCode,paras);
//		addMessages(type,transactionID,message,-1);
//	}
	
//	public void addMessages(String contentid,String action) throws DAOException{
//		addMessages(MSGType.CountUpdateReq,contentid+":"+action);
//	}
	
//ע����2013-04-27 aiyan
////	Catogoryid	����	String	����ID
////	Contentid	����	String	Ӧ��ID
////	Action	����	String	0���½�
////	9��ɾ��
////	Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
//	public void addRefMessages(String categoryId,String contentId,String action){
//	    //����ӵ���Ϣ����,��������½����ܵĶ���Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
//		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
//		String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
//	    if(SSMSDAO.getInstance().isAndroidCategory(categoryId, rootCategoryId)){
////	    	Catogoryid	����	String	����ID
////	    	Action	����	String	0���½�
////	    	1������������Ϣ�����������չ�ֶΣ�
////	    	9��ɾ��
////	    	Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
//
//	    	try {
//				SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq, categoryId+":"+contentId+":"+action);
//			} catch (DAOException e) {
//				// TODO Auto-generated catch block
//				LOG.error("���¼���ӳ���categorgId:"+categoryId+",contentId:"+contentId+",action:"+action);					
//			}
//	    }		
//	}
	
	


	public void createAndroidListTmp() throws DAOException {
		// TODO Auto-generated method stub
		
//		SSMSDAO.createAndroidListTmp.create=create table t_a_android_list_compare as select * from v_android_list
//		SSMSDAO.createAndroidListTmp.drop=drop table t_a_android_list_compare
		
		String sqlCode_drop = "SSMSDAO.createAndroidListTmp.drop";
		String sqlCode_create = "SSMSDAO.createAndroidListTmp.create";
		try{
			DB.getInstance().executeBySQLCode(sqlCode_drop,null);
			LOG.debug("t_a_android_list_compare��Ҫɾ���ģ�");
		}catch(Exception e){
			LOG.error("createAndroidListTmp����",e);
		}
		DB.getInstance().executeBySQLCode(sqlCode_create,null);
	}
	
	public List<String> compareChangeDel() {
		// TODO Auto-generated method stub
		List<String> listDel = new ArrayList<String>();
        try
        {
        	String sqlCode="SSMSDAO.compareChangeDel";
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            while(rs.next()){
            	listDel.add(rs.getString("contentid"));
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return listDel;
	}
	public List<String> compareChangeUpdate() {
		// TODO Auto-generated method stub
		List<String> listUpdate = new ArrayList<String>();
        try
        {
        	String sqlCode="SSMSDAO.compareChangeUpdate";
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            while(rs.next()){
            	listUpdate.add(rs.getString("contentid"));
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return listUpdate;
	}
	public List<String> compareChangeAdd() {
		// TODO Auto-generated method stub
	   	List<String> listAdd = new ArrayList<String>();
        try
        {
        	String sqlCode="SSMSDAO.compareChangeAdd";
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            while(rs.next()){
            	listAdd.add(rs.getString("contentid"));
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return listAdd;
	}

	//ɾ��t_a_android_list��
	//��t_a_android_list_tmp����t_a_android_list��
	public void changeTableName() throws DAOException {
		// TODO Auto-generated method stub
		TransactionDB tdb = null;
		try{
	        if (LOG.isDebugEnabled())
	        {
	        	LOG.debug("changeTableName is beginning...");
	        }
	        tdb = TransactionDB.getTransactionInstance();
	        String sqlCode1 ="SSMSDAO.changeTableName.updateStatus";//-2
	        //update t_a_android_list set status=-2
	        String sqlCode2 ="SSMSDAO.changeTableName.appendTable";
//	        insert into t_a_android_list(contentid,rank_new,rank_all,rank_fee,rank_hot,rank_scores,catename,
//    		name,add_7days_down_count,add_order_count,mobileprice,scores,createtime,daynum,status
//    		)
//    		select c.contentid,c.rank_new,c.rank_all,c.rank_fee,c.rank_hot,c.rank_scores,c.catename,
//    		c.name,c.add_7days_down_count,c.add_order_count,c.mobileprice,c.scores,c.createtime,c.daynum,0 from t_a_android_list_compare c
	        

	        String sqlCode3="SSMSDAO.changeTableName.delRecords";//!=0
	        //delete from t_a_android_list where status !=0
	        
	        String sqlCode4="SSMSDAO.changeTableName.createIndex";
	        //create index index_android_list on t_a_android_list(contentid)
	        
	        String sqlCode5="SSMSDAO.changeTableName.rebuildIndex";
	        //"alter index index_android_list rebuild";
	    
	        
	        tdb.executeBySQLCode(sqlCode1,null);
	        tdb.executeBySQLCode(sqlCode2,null);
	        tdb.executeBySQLCode(sqlCode3,null);
	        tdb.executeBySQLCode(sqlCode5,null);
	        tdb.commit();
		}catch(DAOException e){
			LOG.error("hehe.�����ˣ�����");
			e.printStackTrace();
			tdb.rollback();
			throw new DAOException("����t_a_android_list_tmp��Ϊ��t_a_android_list����",e);
		}finally{
			if(tdb!=null){
				tdb.close();
			}
		}

	}

	public int getRowNum(String tableName) {
		// TODO Auto-generated method stub
		int num = 0;
        try
        {
        	String sql ="select count(1) from "+tableName;
            RowSet rs = DB.getInstance().query(sql, null);
            if(rs.next()){
            	num=rs.getInt(1);
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        
        return num;
        
	}

	public void updateRankNew() {
		// TODO Auto-generated method stub
//		update t_a_android_list_compare_ay kkk set  kkk.rank_new='0' where kkk.rowid in(
//
//				select mmm.rowid from 
//
//				(select a.rowid,a.contentid, a.rank_new,row_number() over(partition by  a.companyid,trunc(a.updatetime) order by to_number(a.rank_new) desc) hh,a.companyid,a.updatetime 
//				from t_a_android_list_compare_ay a ) mmm where mmm.hh!=1)
        try
        {
        	 String sqlCode="SSMSDAO.updateRankNew";
             DB.getInstance().executeBySQLCode(sqlCode, null);
        }
        catch (Exception e)
        {
            LOG.error("�����ɶ� t_a_android_list_compare��updateRankNew������ʱ����ִ���",e);
        }
        
	}

	public List<InterveneVO> getInterveneList(String type) {
		// TODO Auto-generated method stub
	   	List<InterveneVO> interveneList = new ArrayList<InterveneVO>();
        try
        {
        	//select * from t_a_intervene where type=? add by aiyan 2013-07-15
        	String sqlCode="SSMSDAO.getInterveneList";
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, new String[]{type});
            while(rs.next()){
            	InterveneVO vo = new InterveneVO();
            	vo.setId(rs.getLong("id"));
            	vo.setType(rs.getString("type"));
            	vo.setContentid(rs.getString("contentid"));
            	vo.setSort(rs.getInt("sort"));
            	interveneList.add(vo);
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return interveneList;
	}

	public void updateRankValue(String type,String contentid,long rank) {
		// TODO Auto-generated method stub
    	TransactionDB tdb = null;
		try{
			tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
        	String sql = DB.getInstance().getSQLByCode("SSMSDAO.updateRankValue");
        	sql = replaceSql(sql,type);
			tdb.execute(sql, new String[]{rank+"",contentid});
			String sqlCode ="SSMSDAO.updateRankValue.lastexecutetime";
			tdb.executeBySQLCode(sqlCode, null);
			tdb.commit();
		}catch(Exception e){
			LOG.error("updateRankValueʱ����ִ���"+contentid+"--"+type+"--"+rank,e);
			if (tdb != null) {
				tdb.rollback();
			}
			LOG.error(e);
		}finally {
			if (tdb != null) {
				tdb.close();
			}
		}
    }

	private String replaceSql(String sql,String type) {
		// TODO Auto-generated method stub
		if(sql==null||type==null){
			throw new IllegalArgumentException(sql+"--"+type+" �����Ƿ���");
		}
		sql = sql.replaceAll("rank_xxx", type);
		return sql;
	}

	public List<RankVO> getDBdataList(String type, int needDBdataNum) {
		// TODO Auto-generated method stub
	   	List<RankVO> dbdataList = new ArrayList<RankVO>();
        try
        {
        	// select contentid,rank_new from (select contentid,rank_new from t_a_android_list l order by to_number(l.rank_new) desc) a where rownum<6  add by aiyan 2013-07-16
        	String sql = DB.getInstance().getSQLByCode("SSMSDAO.getDBdataList");
        	sql = replaceSql(sql,type);
            RowSet rs = DB.getInstance().query(sql, new String[]{(needDBdataNum+1)+""});
            while(rs.next()){
            	RankVO vo = new RankVO(rs.getString(1),rs.getLong(2));
            	dbdataList.add(vo);
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return dbdataList;
	}
    
	
	public long getRandValueByCategory(String categoryType, int startid, int intervenorId)
	{
		RowSet rs = null;
		String sql = null;
		Object[] object = null;
		long number = -1L;
		try
		{
			if(startid == IntervenorVO.TOP)
			{
				// select max(to_number(l.rank_xxx)) from t_a_android_list_compare l
				sql = DB.getInstance().getSQLByCode("SSMSDAO.getRandValueByCategory.TOP");
			}
			else if(startid == IntervenorVO.END)
			{
				// select min(to_number(l.rank_xxx)) from t_a_android_list_compare l
				sql = DB.getInstance().getSQLByCode("SSMSDAO.getRandValueByCategory.END");
			}
			else
			{
				// select rank_xxx from (select rank_xxx from t_a_android_list_compare l order by to_number(l.rank_xxx) desc) a where rownum <= ? order by rownum desc
				sql = DB.getInstance().getSQLByCode("SSMSDAO.getRandValueByCategory");
				object = new Object[]{intervenorId, startid};
			}
			sql = replaceSqlByCategoryType(sql, categoryType);
			sql = replaceSqlByCategoryTypeWhere(sql, categoryType, startid);
			
			rs = DB.getInstance().query(sql, object);
			if (rs.next())
			{
				number = rs.getLong(1);
			}
		}
		catch (Exception e)
		{
			LOG.error(e);
		}
		return number;
	}
	
	/**
	 * ���µ�ǰ��Ԥ�����б��Ӧ�Ļ�����Ʒ����
	 * @param categoryType
	 * @param contentId
	 * @param number
	 */
	public void updateRandValueByContent(String categoryType, String contentId, long number)
	{
		String sql = null;
		try
		{
			// update  t_a_android_list_compare  set rank_xxx=? where contentid=?
			sql = DB.getInstance().getSQLByCode("SSMSDAO.updateRankValue.updateRandValueByContent");
			sql = replaceSqlByCategoryTypeUpdate(sql, categoryType);
			DB.getInstance().execute(sql, new Object[]{number, contentId});
		}
		catch (Exception e)
		{
			LOG.error(" ���µ�ǰ��Ԥ�����б��Ӧ�Ļ�����Ʒ����ʱ�������� :" + sql,e);
		}
	}
	
	/**
	 * �����滻ָ���ֶ� 
	 * @param sql rank_xxx
	 * @param type rank_aaa_bbb
	 * @return rank_aaa
	 */
	private String replaceSqlByCategoryType(String sql, String type)
	{
		if (sql == null || type == null)
		{
			throw new IllegalArgumentException(sql + "--" + type + " �����Ƿ���");
		}
		sql = sql.replaceAll("rank_xxx", type.substring(0, type
				.lastIndexOf("_")));
		return sql;
	}
	
	/**
	 * �����滻ָ���ֶ� 
	 * @param sql rank_xxx
	 * @param type rank_aaa_bbb
	 * @return rank_aaa
	 */
	private String replaceSqlByCategoryTypeUpdate(String sql, String type)
	{
		if (sql == null || type == null)
		{
			throw new IllegalArgumentException(sql + "--" + type + " �����Ƿ���");
		}
		
		String appType = type.substring(type
				.lastIndexOf("_")+1);
		
		sql = sql.replaceAll("rank_xxx", type.substring(0, type
				.lastIndexOf("_")));
		
		if("appGame".equals(appType))
		{
			sql = sql + " and catename like '%appGame%'";
		}
		else if ("appSoftWare".equals(appType))
		{
			sql = sql + " and (catename like '%appSoftWare%' or catename like '%appTheme%')";
		}
		return sql;
	}
	
	/**
	 * �����滻ָ���ֶ� 
	 * @param sql sql
	 * @param type rank_aaa_bbb
	 * @param startid ��Ԥλ��
	 * @return sql + where 
	 */
	private String replaceSqlByCategoryTypeWhere(String sql, String type, int startid)
	{
		if (sql == null || type == null)
		{
			throw new IllegalArgumentException(sql + "--" + type + " �����Ƿ���");
		}
		
		String appType = type.substring(type
				.lastIndexOf("_")+1);
		String sqlGame = " where l.catename like '%appGame%' ";
		String sqlSoftWare = " where (l.catename like '%appSoftWare%' or l.catename like '%appTheme%') ";
		String sqlappAllPriceFree = " where l.MOBILEPRICE = 0 ";
		String sqlappAllPricePay = " where l.MOBILEPRICE > 0 ";
		String sqlNotIn = " and l.contentid not in (select c.contentid id from t_intervenor_gcontent_map g, t_r_gcontent c where g.intervenorid=? and g.gcontentid=c.id)";
		
		if("appGame".equals(appType))
		{
			switch(startid)
			{
				case IntervenorVO.TOP:
					sql = sql + sqlGame;
					break;
				case IntervenorVO.END:
					sql = sql + sqlGame;
					break;
				default:
					sql = sql.replaceAll("rank_yyy", sqlGame + sqlNotIn);
					break;
			}
		}
		else if ("appSoftWare".equals(appType))
		{
			switch(startid)
			{
				case IntervenorVO.TOP:
					sql = sql + sqlSoftWare;
					break;
				case IntervenorVO.END:
					sql = sql + sqlSoftWare;
					break;
				default:
					
					sql = sql.replaceAll("rank_yyy", sqlSoftWare + sqlNotIn);
					break;
			}
		}
		else if ("appAllPriceFree".equals(appType))
		{
			switch(startid)
			{
				case IntervenorVO.TOP:
					sql = sql + sqlappAllPriceFree;
					break;
				case IntervenorVO.END:
					sql = sql + sqlappAllPriceFree;
					break;
				default:
					
					sql = sql.replaceAll("rank_yyy", sqlappAllPriceFree + sqlNotIn);
					break;
			}
		}
		else if ("appAllPricePay".equals(appType))
		{
			switch(startid)
			{
				case IntervenorVO.TOP:
					sql = sql + sqlappAllPricePay;
					break;
				case IntervenorVO.END:
					sql = sql + sqlappAllPricePay;
					break;
				default:
					
					sql = sql.replaceAll("rank_yyy", sqlappAllPricePay + sqlNotIn);
					break;
			}
		}
		else if ("appAll".equals(appType))
		{
			switch(startid)
			{
				case IntervenorVO.TOP:
					break;
				case IntervenorVO.END:
					break;
				default:
					sql = sql.replaceAll("rank_yyy", " where 1=1 " + sqlNotIn);
					break;
			}
		}
		return sql;
	} 
}
