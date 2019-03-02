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
				logger.info("开始检查策略,["+entity+"]");
				try {
					if(validate(entity)){
						logger.info("检查通过,["+entity+"]");
						try {
							execute(entity);
						} catch (BOException e) {
							// TODO Auto-generated catch block
							logger.error("当前策略在执行的时候出现错误！",e);
							logger.error(e);
						}
					}else{
						logger.error("这个策略检查不通过["+entity+"]");
					}
				} catch (BOException e) {
					// TODO Auto-generated catch block
					logger.error("验证的时候出现问题了",e);
					logger.error(e);
				}
			}
		//}
	}
	/**add by aiyan 2012-07-06
	 * 验证规则：
	 * 1，不能有drop/trunc这样的词语。
	 * 2，del_sql和insert_sql是一对的，他们只能对同一个表操作。
	 * 3，操作表要在规定的表范围内。
	 * @param entity
	 * @return true:检查通过，false：检查不通过。
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
			logger.error("验证的时候出现问题了"+entity,e);
			throw new BOException("验证的时候出现问题了",e);
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
