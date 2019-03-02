
package com.aspire.dotcard.baseVideo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.baseVideo.vo.CollectionResultVO;
import com.aspire.dotcard.baseVideo.vo.CollectionVO;
import com.aspire.dotcard.baseVideo.vo.ProgramVO;
import com.aspire.dotcard.baseVideo.vo.VideoRefVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class VideoReferenceDAO
{
    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(VideoReferenceDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static VideoReferenceDAO instance = new VideoReferenceDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private VideoReferenceDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static VideoReferenceDAO getInstance()
    {
        return instance;
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class VideoRefPageVO implements PageVOInterface
    {
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            VideoRefVO vo = ( VideoRefVO ) content;

            vo.setRefId(rs.getString("Id"));
            vo.setCategoryId(rs.getString("categoryId"));
            vo.setProgramId(rs.getString("programId"));
            vo.setProgramName(rs.getString("programName"));
            vo.setSortId(rs.getInt("sortId"));
            vo.setExportTime(rs.getDate("exportTime"));
            vo.setNodeId(rs.getString("nodeId"));
            vo.setVideoId(rs.getString("videoId"));
            vo.setLastUpTime(rs.getString("lastuptime"));
            vo.setFullName(rs.getString("fullname"));
            if("1".equals(rs.getString("islink"))){
            	 vo.setIsLink("��");
            }else{
            vo.setIsLink("��");
            }
            vo.setVerify_status(rs.getString("verify_status"));
        }

        public Object createObject()
        {
            return new VideoRefVO();
        }
    }

    /**
     * ���ڲ�ѯ��ǰ��������Ʒ�б�
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryVideoRefList(PageResult page, VideoRefVO vo)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryVideoRefList(" + vo.getRefId()
                         + ") is starting ...");
        }

        // select
		// r.id,r.programid,r.programname,r.categoryid,r.sortid,r.exporttime,p.nodeid,
		// p.videoid, substr(p.lastuptime,0,8) lastuptime from t_vo_reference r,
		// t_vo_program p where r.programid = p.programid
		String sqlCode = "baseVideo.dao.VideoReferenceDAO.queryVideoRefList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //����������sql�Ͳ���

            if (!"".equals(vo.getRefId()))
            {
                //sql += " and r.Id ='" + vo.getRefId() + "'";
            	sqlBuffer.append(" and r.Id = ? ");
            	paras.add(vo.getRefId());
            }
            if (!"".equals(vo.getCategoryId()))
            {
                //sql += " and r.categoryid ='" + vo.getCategoryId() + "'";
            	sqlBuffer.append(" and r.categoryid = ? ");
            	paras.add(vo.getCategoryId());
            }
            if (!"".equals(vo.getProgramId()))
            {
                //sql += " and r.programid ='" + vo.getProgramId() + "'";
            	sqlBuffer.append(" and r.programid = ? ");
            	paras.add(vo.getProgramId());
            }
            if (!"".equals(vo.getProgramName()))
            {
            	sqlBuffer.append(" and r.programname like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getProgramName())+"%");
            }
            if (!"".equals(vo.getNodeId()))
            {
            	sqlBuffer.append(" and p.nodeid = ? ");
            	paras.add(vo.getNodeId());
            }
            if (!"".equals(vo.getVideoId()))
            {
            	sqlBuffer.append(" and p.videoid = ? ");
            	paras.add(vo.getVideoId());
            }

            if (!"".equals(vo.getStartTime()))
            {
            	sqlBuffer.append(" and substr(p.lastuptime ,0,8) >= ? ");
            	paras.add(vo.getStartTime());
            }
            
            if (!"".equals(vo.getEndTime()))
            {
            	sqlBuffer.append(" and substr(p.lastuptime ,0,8) <= ? ");
            	paras.add(vo.getEndTime());
            }
            
            if (!"".equals(vo.getVerify_status())&&!"-1".equals(vo.getVerify_status()))
     		{
    			if(vo.getVerify_status().contains(",")){
    				String[] strs = vo.getVerify_status().split(",");
    				sqlBuffer.append(" and r.VERIFY_STATUS in (" + strs[0] + "," + strs[1] + ")");
    			}else{
    				sqlBuffer.append(" and r.VERIFY_STATUS = " + vo.getVerify_status());
    			}
     		}
            
            sqlBuffer.append(" order by sortid desc");

            page.excute(sqlBuffer.toString(), paras.toArray(), new VideoRefPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * �����Ƴ�ָ��������ָ������Ƶ
     * 
     * @param categoryId ����id
     * @param videoId ��Ƶid��
     * @throws DAOException
     */
    public void removeVideoRefs(String categoryId, String[] videoId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("removeVideoRefs(" + categoryId + ") is starting ...");
        }

        // update t_vo_reference r set r.delflag = r.verify_status,c.goods_status = 0,c.updatetime=sysdate where r.categoryid = ? and r.id = ?
        String sqlCode = "baseVideo.dao.VideoReferenceDAO.removeVideoRefs().remove";

        TransactionDB tdb = null;
        try {
           tdb = TransactionDB.getTransactionInstance();
           for (int i = 0; i < videoId.length; i++)
           {
        	   String[] strs = videoId[i].split("#");
        	   tdb.executeBySQLCode(sqlCode, new Object[]{categoryId,strs[0]});
           }
           this.setCategoryGoodsApproval(tdb, categoryId);
           tdb.commit();
        }
        catch (DAOException e)
        {
        	logger.error("�Ƴ�ָ����������Ƶʱ�����쳣:", e);
            // ִ�лع�
            tdb.rollback();
            throw new DAOException("�Ƴ�ָ����������Ƶʱ�����쳣:", e);
        } finally {
            if (tdb != null) {
                tdb.close();
              }
        }
    }

    /**
     * ����������Ƶ��������Ƶ��Ʒ����ֵ
     * 
     * @param categoryId ����id
     * @param setSortId ��Ƶ����id
     * @throws DAOException
     */
    public void setVideoSort(String categoryId, String[] setSortId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("setVideoSort(" + categoryId + ") is starting ...");
        }

        // update t_vo_reference r set r.sortid=?,r.verify_status=0,r.verify_date=sysdate where r.id=? and r.categoryid=?
        String sqlCode = "baseVideo.dao.VideoReferenceDAO.setVideoSort().set";
        // �����������
        TransactionDB tdb = null;
        try {
       		tdb = TransactionDB.getTransactionInstance();
       		 for (int i = 0; i < setSortId.length; i++)
               {
                   String[] temp = setSortId[i].split("_");
                   tdb.executeBySQLCode(sqlCode, new Object[]{temp[1],temp[0],categoryId});
               }
       		   this.setCategoryGoodsApproval(tdb,categoryId);
              tdb.commit();
          }
          catch (DAOException e)
          {
          	logger.error("������Ƶ��Ʒ�������쳣:", e);
  			// ִ�лع�
  			tdb.rollback();
              throw new DAOException("������Ƶ��Ʒ�������쳣:", e);
          } finally {
  			if (tdb != null) {
  				tdb.close();
  			}
  		}
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class ProgramPageVO implements PageVOInterface
    {
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            ProgramVO vo = ( ProgramVO ) content;

            vo.setProgramId(rs.getString("programId"));
            vo.setProgramName(rs.getString("programName"));
            vo.setDesc(rs.getString("description"));
            vo.setShowTime(rs.getString("showTime"));
            vo.setVideoId(rs.getString("videoId"));
            vo.setNodeName(rs.getString("nodeName"));
            vo.setNodeDesc(rs.getString("nodeDesc"));
            vo.setNodeId(rs.getString("nodeId"));
            vo.setLastUpTime(rs.getString("lastuptime"));
            vo.setFullName(rs.getString("fullname"));
            if("1".equals(rs.getString("islink"))){
           	 vo.setIsLink("��");
           }else{
           vo.setIsLink("��");
           }
        }

        public Object createObject()
        {
            return new ProgramVO();
        }
    }

    /**
     * ���ڲ�ѯ��Ƶ�б�
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryProgramVOList(PageResult page, ProgramVO vo)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryProgramVOList( ) is starting ...");
        }

		// select
		// t.programid,t.programname,t.description,t.showtime,t.videoid,t.nodeid,n.nodename,n.description
		// as nodeDesc, substr(t.lastuptime, 0, 8) lastuptime from t_vo_program
		// t, t_vo_node n where t.nodeid = n.nodeid
		String sqlCode = "baseVideo.dao.VideoReferenceDAO.queryProgramVOList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //����������sql�Ͳ���

            if (!"".equals(vo.getProgramId()))
            {
                sqlBuffer.append(" and t.programid = ? ");
                paras.add(vo.getProgramId());
            }
            if (!"".equals(vo.getProgramName()))
            {
                sqlBuffer.append(" and t.programname like ? ");
                paras.add("%"+SQLUtil.escape(vo.getProgramName())+"%");
            }
            if (!"".equals(vo.getNodeId()))
            {
            	sqlBuffer.append(" and t.nodeid = ? ");
            	paras.add(vo.getNodeId());
            }
            if (!"".equals(vo.getVideoId()))
            {
            	sqlBuffer.append(" and t.videoid = ? ");
            	paras.add(vo.getVideoId());
            }

            sqlBuffer.append(" order by programid");

            page.excute(sqlBuffer.toString(), paras.toArray(), new ProgramPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * ���ڲ鿴ָ���������Ƿ����ָ����Ƶ
     * 
     * @param categoryId ����id
     * @param videoId ��Ƶid��
     * @throws DAOException
     */
    public String isHasVideoRefs(String categoryId, String[] videoId,String[] nodeId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("isHasVideoRefs(" + categoryId + ") is starting ...");
        }

        // select * from t_vo_reference r where 1=1
        String sqlCode = "baseVideo.dao.VideoReferenceDAO.queryVideoRefList().SELECT";
        //StringBuffer sql;
        String sql;
        ResultSet rs = null;
        String ret = "";
        StringBuffer temp = new StringBuffer("");

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //����������sql�Ͳ���

            //sql.append(" and r.categoryid='").append(categoryId).append("' ");
            sqlBuffer.append(" and r.categoryid= ? ");
            paras.add(categoryId);
            
            for (int i = 0; i < videoId.length; i++)
            {
            	if(i > 0){
            		sqlBuffer.append(" union all ");
            		sqlBuffer.append(sql).append(" and r.categoryid= ? ");
            		paras.add(categoryId);
            	}
            	sqlBuffer.append("  and r.programid = ? and r.nodeid = ?");
            	paras.add(videoId[i]);
            	paras.add(nodeId[i]);
            } 
            
           /* for (int i = 0; i < videoId.length; i++)
            {
                temp.append("'").append(videoId[i]).append("'").append(",");
            }

            if (temp.length() > 0)
            {
                temp.deleteCharAt(temp.length() - 1);
                //sql.append(" and r.programid in ( ").append(temp).append(" )");
                sqlBuffer.append(" and r.programid in ( ").append(temp).append(" )");
                //paras.add(temp.toString());
            }*/

            //rs = DB.getInstance().query(sql.toString(), null);
            rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());

            while (rs.next())
            {
                ret += rs.getString("programid")+"|"+ rs.getString("nodeid") + ". ";
            }

        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
        catch (SQLException e)
        {
            throw new DAOException("�鿴ָ���������Ƿ����ָ����Ƶʱ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return ret;
    }

    
    /**
     * ���ڲ�ѯ���ܵ�base��Ϣ
     * @param categoryId
     * @return String []{baseid��basetype}
     * @throws DAOException
     */
    public String[] queryCategoryBase(String categoryId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryCategoryBase(" + categoryId
                         + ") is starting ...");
        }

        // select c.baseid, c.basetype from t_vo_category c where c.id = ?
        String sqlCode = "baseVideo.dao.VideoReferenceDAO.queryCategoryBase().SELECT";
        String sql;
        ResultSet rs = null;
        String[] base = new String[2];

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            rs = DB.getInstance().query(sql, new Object[] { categoryId });

            if (rs.next())
            {
                base[0] = rs.getString("baseid");
                base[1] = rs.getString("basetype");
            }
        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
        catch (SQLException e)
        {
            throw new DAOException("���ڲ�ѯ���ܵ�base��Ϣʱ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return base;
    }
    
    
    /**
     * �������ָ������Ƶ��������
     * 
     * @param categoryId ����id
     * @param videoId ��Ƶid��
     * @throws DAOException
     */
    public void addVideoRefs(String categoryId, String[] base, String[] videoId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addVideoRefs(" + categoryId + ") is starting ...");
        }

        // insert into t_vo_reference (id, categoryid, programid, nodeid, programname, sortid, baseid, basetype,verify_status,verify_date,delflag) 
        //values (SEQ_VO_REFERENCE_ID.NEXTVAL, ?, ?, ?, ?, (select decode(max(sortid), null, 1, max(sortid) + 1) from t_vo_reference n where n.categoryid = ?), ?, ?,0,sysdate,2)
        String sqlCode = "baseVideo.dao.VideoReferenceDAO.addVideoRefs().add";
        TransactionDB tdb = null;
        try
        {
        	 tdb = TransactionDB.getTransactionInstance();
        	 for (int i = 0; i < videoId.length; i++)
             {
        		 String[] temps = videoId[i].split("_");
        		 tdb.executeBySQLCode(sqlCode, new Object[]{categoryId,temps[0],temps[1],temps[2],categoryId,base[0],base[1]});
             }
            this.setCategoryGoodsApproval(tdb, categoryId);
            tdb.commit();
        }
        catch (DAOException e)
        {
        	logger.error("���ָ������Ƶ��������ʱ�����쳣:", e);
            // ִ�лع�
            tdb.rollback();
            throw new DAOException("���ָ������Ƶ��������ʱ�����쳣:", e);
        } finally {
            if (tdb != null) {
                tdb.close();
              }
        }
    }
    
    /**
     * �������ָ������Ƶ��������
     * 
     * @param categoryId ����id
     * @param videoId ��Ƶid��
     * @throws DAOException
     */
    public void addVideoRefByNotName1(String categoryId, String[] videoId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addVideoRefs(" + categoryId + ") is starting ...");
        }

        // insert into t_vo_reference (id, categoryid, programid, programname,
        // sortid) values (SEQ_VO_REFERENCE_ID.NEXTVAL, ?, ?, ?, (select
        // decode(max(sortid), null, 1, max(sortid) + 1) from t_vo_reference n
        // where n.categoryid = ?))
        String sql = "baseVideo.dao.VideoReferenceDAO.addVideoRefByNotName().add";
        String sqlCode[] = new String[videoId.length];
        Object[][] object = new Object[videoId.length][6];
        String[] base = this.queryCategoryBase(categoryId);

        for (int i = 0; i < videoId.length; i++)
        {

            sqlCode[i] = sql;
            object[i][0] = categoryId;
            object[i][1] = videoId[i];
            object[i][2] = videoId[i];
            object[i][3] = categoryId;
            object[i][4] = base[0];
            object[i][5] = base[1];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("���ָ������Ƶ��������ʱ�����쳣:", e);
        }
    }

    /**
     * �鿴��ǰ�������Ƿ񻹴�������Ʒ
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public int hasBook(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasBook(" + categoryId + " ) is starting ...");
        }

        // select count(*) as countNum from T_RB_REFERENCE t where t.cid = ?
        String sqlCode = "baseVideo.dao.VideoReferenceDAO.hasBook().SELECT";
        ResultSet rs = null;
        int countNum = 0;

        rs = DB.getInstance().queryBySQLCode(sqlCode,
                                             new Object[] { categoryId });

        try
        {
            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("���ص�ǰ�������Ƿ񻹴�������Ʒ��Ϣ�����쳣:", e);
        }

        finally
        {
            DB.close(rs);
        }

        return countNum;
    }

    /**
     * �������ԭ��������Ƶ
     * 
     * @param categoryId ����id
     * @throws DAOException
     */
    public void delVideoRef(String categoryId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delVideoRef(" + categoryId + ") is starting ...");
        }

        // delete from t_vo_reference r where r.categoryid=?
        String sql = "baseVideo.dao.VideoReferenceDAO.delVideoRef().remove";

        try
        {
            DB.getInstance().executeBySQLCode(sql, new Object[] { categoryId });
        }
        catch (DAOException e)
        {
            throw new DAOException("�������ԭ��������Ƶʱ�����쳣:", e);
        }
    }

    /**
     * У��ļ��Д����Ƿ�����Ƶ���д���
     * 
     * @param list
     * @throws DAOException
     */
    public String verifyVideo(List list) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("verifyVideo() is starting ...");
        }

        // select * from t_vo_program t where t.programid=?
        String sql = "baseVideo.dao.VideoReferenceDAO.queryVideoInfo().SELECT";
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        // ������ԃ
        for (int i = 0; i < list.size(); i++)
        {
            String temp = ( String ) list.get(i);
            try
            {
                rs = DB.getInstance().queryBySQLCode(sql.toString(),
                                                     new Object[] { temp });
                // �����������������
                if (!rs.next())
                {
                    list.remove(i);
                    i--;
                    sb.append(temp).append(". ");
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("�鿴ָ���������Ƿ����ָ������ʱ�����쳣:", e);
            }
            finally
            {
                DB.close(rs);
            }
        }

        return sb.toString();
    }

    /**
     * ���ڲ�ѯ��Ƶ����
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public ProgramVO queryVideoInfo(String videoId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryVideoInfo( ) is starting ...");
        }
        // select * from t_vo_program t where t.programid=?
        String sqlCode = "baseVideo.dao.VideoReferenceDAO.queryVideoInfo().SELECT";
        ResultSet rs = null;
        Object paras[] = { videoId };
        ProgramVO vo = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs.next())
            {
                vo = new ProgramVO();

                vo.setProgramId(rs.getString("programId"));
                vo.setProgramName(rs.getString("programName"));
                vo.setDesc(rs.getString("description"));
                vo.setShowTime(rs.getString("showTime"));
                vo.setVideoId(rs.getString("videoId"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DAOException("ִ�в�ѯ��Ƶ����ʧ��", e);
        }
        finally
        {
            DB.close(rs);
        }
        return vo;
    }
    
    /**
	 * 
	 * @desc ��ȡ��Ƶ����չ�ֶ�
	 * @author dongke Aug 9, 2011
	 * @param musicId
	 * @return
	 * @throws DAOException
	 */
	public List queryVideoKeyResource(String videoId) throws DAOException
	{
		List keyResourceList = null;
		if (logger.isDebugEnabled())
		{
			logger.debug("queryVideoKeyResource( ) is starting ...");
		}
		
		// select * from t_key_base b, (select * from t_key_resource r where
		// r.tid = ?) y where b.keytable = 't_vo_program' and b.keyid =
		// y.keyid(+)
		String sqlCode = "baseVideo.dao.VideoReferenceDAO.queryVideoKeyResource().SELECT";
		DB db = DB.getInstance();
		ResultSet rs = null;
		Object paras[] =
		{ videoId };
		try
		{
			keyResourceList = new ArrayList();
			rs = db.queryBySQLCode(sqlCode, paras);
			while (rs.next())
			{
				ResourceVO vo = new ResourceVO();
				vo.setKeyid(rs.getString("keyid"));
				vo.setKeyname(rs.getString("keyname"));
				vo.setKeytable(rs.getString("keytable"));
				vo.setKeydesc(rs.getString("keydesc"));
				vo.setKeyType(rs.getString("keytype"));
				vo.setTid(rs.getString("tid"));
				vo.setValue(rs.getString("value"));

				keyResourceList.add(vo);
			}
		}
		catch (SQLException e)
		{

			e.printStackTrace();
			throw new DAOException("ִ�в�ѯ��Ƶ��չ�ֶ�����ʧ��", e);
		}
		finally
		{
			DB.close(rs);
		}
		return keyResourceList;
	}
	
	
//	String sql = "baseVideo.dao.VideoReferenceDAO.addVideoRefByNotName().add";
//    String sqlCode[] = new String[videoId.length];
//    Object[][] object = new Object[videoId.length][6];
//    String[] base = this.queryCategoryBase(categoryId);
//
//    for (int i = 0; i < videoId.length; i++)
//    {
//
//        sqlCode[i] = sql;
//        object[i][0] = categoryId;
//        object[i][1] = videoId[i];
//        object[i][2] = videoId[i];
//        object[i][3] = categoryId;
//        object[i][4] = base[0];
//        object[i][5] = base[1];
//    }

	
    //add by aiyan 2012-12-24 begin
    public void addReference(String categoryId, String programId,String nodeId,String sortId)
    throws Exception {
    	
    	if(!isExistContent(programId,nodeId)){//����Ƶ���ݱ�t_vo_program�����û���ҵ�������ݾ����쳣��
    		throw new Exception("��Ƶ��Ʒ�����ʱ������id��t_vo_program�����ڣ�programId:"+programId);
    	}
    	String sqlCode = "";
    	String[] paras = null;
    	if(isExistRef(categoryId,programId,nodeId)){
    		sqlCode = "com.aspire.dotcard.baseVideo.dao.VideoReferenceDAO.addReference.UPDATE";
    		paras = new String[]{sortId,categoryId,programId,nodeId};
    	}else{
    		
//    		insert into t_vo_reference 
//    		(id, categoryid, programid,nodeid, programname, sortid, baseid, basetype) values 
//    		(SEQ_VO_REFERENCE_ID.NEXTVAL, ?, ?, ?, (select distinct p.programname from t_vo_program p where p.programid = ?), 
//    		?
//    		, ?, ?)
    		
    		sqlCode = "com.aspire.dotcard.baseVideo.dao.VideoReferenceDAO.addReference.INSERT";    		
    	    String[] base = this.queryCategoryBase(categoryId);
    	    paras = new String[]{categoryId,programId,nodeId,programId,nodeId,sortId,base[0],base[1]};
    	}
    	
		
		try {
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (DAOException e) {
			logger.error("���ָ����������������ʱ�����쳣:",e);
			throw new DAOException("���ָ����������������ʱ�����쳣:", e);
		}
	}
    
    private boolean isExistRef(String categoryId, String programid,String nodeId) throws Exception{
		String sqlCode = "com.aspire.dotcard.baseVideo.dao.VideoReferenceDAO.isExistRef";
		String[] paras = new String[]{categoryId,programid,nodeId};
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
		return rs.next();
    }
    
    private boolean isExistContent(String programid,String nodeId) throws Exception{
		String sqlCode = "com.aspire.dotcard.baseVideo.dao.VideoReferenceDAO.verifyContentId";
		String[] paras = new String[]{programid,nodeId};
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
		return rs.next();
    }
    
   //add by aiyan 2012-12-24 end
    /**
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<VideoRefVO> queryVideoReferenceListByExport(VideoRefVO vo)
			throws DAOException
	{

        if (logger.isDebugEnabled())
        {
            logger.debug("queryVideoReferenceListByExport( ) is starting ...");
        }

    	String sqlCode = "baseVideo.dao.VideoReferenceDAO.queryVideoRefList().SELECT";
        String sql = null;
		List<VideoRefVO> list = new ArrayList<VideoRefVO>();
		ResultSet rs = null;
        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //����������sql�Ͳ���
//
//            if (!"".equals(vo.getRefId()))
//            {
//                //sql += " and r.Id ='" + vo.getRefId() + "'";
//            	sqlBuffer.append(" and r.Id = ? ");
//            	paras.add(vo.getRefId());
//            }
            if (!"".equals(vo.getCategoryId()))
            {
                //sql += " and r.categoryid ='" + vo.getCategoryId() + "'";
            	sqlBuffer.append(" and r.categoryid = ? ");
            	paras.add(vo.getCategoryId());
            }
            if (!"".equals(vo.getProgramId()))
            {
                //sql += " and r.programid ='" + vo.getProgramId() + "'";
            	sqlBuffer.append(" and r.programid = ? ");
            	paras.add(vo.getProgramId());
            }
            if (!"".equals(vo.getProgramName()))
            {
            	sqlBuffer.append(" and r.programname like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getProgramName())+"%");
            }
            if (!"".equals(vo.getNodeId()))
            {
            	sqlBuffer.append(" and p.nodeid = ? ");
            	paras.add(vo.getNodeId());
            }
            if (!"".equals(vo.getVideoId()))
            {
            	sqlBuffer.append(" and p.videoid = ? ");
            	paras.add(vo.getVideoId());
            }
            
            if (!"".equals(vo.getVerify_status())&&!"-1".equals(vo.getVerify_status()))
     		{
    			if(vo.getVerify_status().contains(",")){
    				String[] strs = vo.getVerify_status().split(",");
    				sqlBuffer.append(" and r.VERIFY_STATUS in (" + strs[0] + "," + strs[1] + ")");
    			}else{
    				sqlBuffer.append(" and r.VERIFY_STATUS = " + vo.getVerify_status());
    			}
     		}

            sqlBuffer.append(" order by sortid desc");


	     rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());
			
			while (rs.next())
			{
				list.add(baseVideoReferenceVoData(rs));
			}
		}
		catch (SQLException e)
		{
			logger.error("ִ�л�ȡ��ǰ���������в�ѯ��Ƶ���ݼ���Ϣʧ��", e);
			e.printStackTrace();
		}
		catch (DataAccessException e)
		{
			logger.error("ִ�л�ȡ��ǰ���������в�ѯ��Ƶ���ݼ���Ϣʧ��", e);
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs);
		}
		
		return list;
    }
	private VideoRefVO baseVideoReferenceVoData(ResultSet rs) throws SQLException
	{
        VideoRefVO vo = new VideoRefVO();

        vo.setRefId(rs.getString("Id"));
        vo.setCategoryId(rs.getString("categoryId"));
        vo.setProgramId(rs.getString("programId"));
        vo.setProgramName(rs.getString("programName"));
        vo.setSortId(rs.getInt("sortId"));
        vo.setExportTime(rs.getDate("exportTime"));
        vo.setNodeId(rs.getString("nodeId"));
        vo.setVideoId(rs.getString("videoId"));
        vo.setLastUpTime(rs.getString("lastuptime"));
        vo.setFullName(rs.getString("fullname"));
        if("1".equals(rs.getString("islink"))){
        	 vo.setIsLink("��");
        }else{
        vo.setIsLink("��");
        }
		return vo;
    }
	/**
	 * ��Ƶ��Ʒ�����ύ����
	 * 
	 * @param tdb
	 * @param categoryId ��Ƶ���ܱ���
	 * @throws DAOException
	 */
	public void setCategoryGoodsApproval(TransactionDB tdb,String categoryId)
			throws DAOException {
		String sql = "com.aspire.dotcard.baseVideo.dao.VideoReferenceDAO.setCategoryGoodsApproval";
		try {
			tdb.executeBySQLCode(sql, new Object[]{categoryId});
		} catch (DAOException e) {
			logger.error("������Ƶ��Ʒ���ܷ����쳣:", e);
            throw new DAOException("������Ƶ��Ʒ���ܷ����쳣:", e);
		}
	}
	
	/**
	 * �༭��Ƶ��Ʒ����
	 * 
	 * @param categoryId ��Ƶ���ܱ���
	 * @throws DAOException
	 */
	public void approvalCategoryGoods(String categoryId)
			throws BOException {
		String sql = "com.aspire.dotcard.baseVideo.dao.VideoReferenceDAO.approvalCategoryGoods";
		try {
			DB.getInstance().executeBySQLCode(sql, new Object[]{categoryId});
		} catch (DAOException e) {
			logger.error("�༭��Ƶ��Ʒ���ܷ����쳣:", e);
            throw new BOException("�༭��Ƶ��Ʒ���ܷ����쳣:", e);
		}
	}
}
