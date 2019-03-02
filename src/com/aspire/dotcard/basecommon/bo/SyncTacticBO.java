package com.aspire.dotcard.basecommon.bo;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecommon.dao.SyncTacticDAO;
import com.aspire.dotcard.basecommon.vo.SyncTacticEntity;

public class SyncTacticBO {
	protected static JLogger logger = LoggerFactory.getLogger(SyncTacticBO.class);
	
	private static String dropRegex = "^(trunc|drop)\\s.*";
	private static String delRegex = "^delete\\s+from\\s+(\\w+)\\s.*";
	private static String insertRegex = "^insert\\s+into\\s+(\\w+)(\\s|\\().*";

	private static SyncTacticBO instance = null;
	private  SyncTacticBO() {
	}
	
	public static SyncTacticBO getInstance(){
		if(instance==null){
			return new SyncTacticBO();
		}
		return instance;
	}

	
	public void sync(){
		//try {
			List allBaseSyncTactic = SyncTacticDAO.getInstance().getAllBaseSyncTactic();
			for(int i=0;i<allBaseSyncTactic.size();i++){
				SyncTacticEntity entity= (SyncTacticEntity)allBaseSyncTactic.get(i);
				logger.info("��ʼ������,["+entity+"]");
				try {
					if(validate(entity)){
						logger.info("���ͨ��,["+entity+"]");
						try {
							execute(entity);
						} catch (BOException e) {
							// TODO Auto-generated catch block
							logger.error("��ǰ������ִ�е�ʱ����ִ���",e);
							logger.error(e);
						}
					}else{
						logger.error("������Լ�鲻ͨ��["+entity+"]");
					}
				} catch (BOException e) {
					// TODO Auto-generated catch block
					logger.error("��֤��ʱ�����������",e);
					logger.error(e);
				}
			}
		//}
	}
	/**add by aiyan 2012-07-06
	 * ��֤����
	 * 1��������drop/trunc�����Ĵ��
	 * 2��del_sql��insert_sql��һ�Եģ�����ֻ�ܶ�ͬһ���������
	 * 3��������Ҫ�ڹ涨�ı�Χ�ڡ�
	 * @param entity
	 * @return true:���ͨ����false����鲻ͨ����
	 * @throws BOException 
	 */
	private boolean validate(SyncTacticEntity entity) throws BOException {
		// TODO Auto-generated method stub
		try{
			if(entity==null||entity.getDelSql()==null||entity.getInsertSql()==null||"".equals(entity.getDelSql().trim())||"".equals(entity.getInsertSql().trim())){
				return false;
			}
			if(entity.getDelSql().trim().matches(dropRegex)||entity.getInsertSql().trim().matches(dropRegex)){
				return false;
			}
			Pattern p = Pattern.compile(delRegex);  
	        Matcher m = p.matcher(entity.getDelSql()); 
	        String delTable = "",insertTable="";
	        if(m.find()){
	        	delTable = m.group(1);
	        }
	        p = Pattern.compile(insertRegex); 
	        m = p.matcher(entity.getInsertSql());
	        if(m.find()){
	        	insertTable = m.group(1);
	        }
	        return (delTable.equals(insertTable));
		}catch(Exception e){
			logger.error("��֤��ʱ�����������"+entity,e);
			throw new BOException("��֤��ʱ�����������",e);
		}
		
	}

	private void execute(SyncTacticEntity entity) throws BOException {
		// TODO Auto-generated method stub
		SyncTacticDAO.getInstance().execute(entity);
		
	}

	
	public static void main(String[] argv){
		
		 String dropRegex = "^(trunc|drop)\\s.*";
		 String delRegex = "^delete\\s+from\\s+(\\w+)\\s.*";
		 String insertRegex = "^insert\\s+into\\s+(\\w+)(\\s|\\().*";
		
		String drop = "trunc table t_vo_reference heeh...";
		String trunc = "trunc table t_vo_reference heh..";
		String del="delete   from   t_vo_reference r where r.categoryid= '491728'";
		String insert = "insert into t_vo_reference(id, categoryid, baseid, basetype, programid, programname, sortid)select SEQ_VO_REFERENCE_ID.NEXTVAL,c.id,baseid, basetype, p.programid,        p.programname,       rownum  from t_vo_node n, t_vo_category c, t_vo_program p where n.nodeid = c.baseid  and p.nodeid = n.nodeid   and c.basetype = '1' and p.lastuptime>to_char(sysdate-1,'yyyyMMddHHmiss')";
		//System.out.println(drop.toLowerCase().trim().matches(dropRegex));
		System.out.println(del.toLowerCase().trim().matches(delRegex));
		System.out.println(insert.toLowerCase().trim().matches(insertRegex));
//		
//		Pattern p = Pattern.compile(delRegex);  
//        Matcher m = p.matcher(del); 
//        System.out.println(m.find());
//        System.out.println(m.group(1));
//        p = Pattern.compile(insertRegex); 
//        m = p.matcher(insert);
//        System.out.println(m.find());
//        System.out.println(m.group(1));

        
		
	}


	

}
