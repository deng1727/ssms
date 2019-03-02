
package com.aspire.dotcard.baseVideo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.baseVideo.vo.VideoCategoryVO;
import com.aspire.dotcard.baseVideo.vo.VideoNodeExtVO;
import com.aspire.dotcard.baseVideo.vo.VideoProductVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class VideoDAO
{

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(VideoDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static VideoDAO instance = new VideoDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private VideoDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static VideoDAO getInstance()
    {
        return instance;
    }

    /**
     * Ϊ�������Ը�ֵ
     * 
     * @param vo
     * @param rs
     * @throws SQLException
     */
    private void fromVideoCategoryVOByRs(VideoCategoryVO vo, ResultSet rs)
                    throws SQLException
    {
        vo.setId(rs.getString("id"));
        vo.setParentId(rs.getString("parentId"));
        vo.setBaseId(rs.getString("baseId"));
        vo.setBaseParentId(rs.getString("baseParentId"));
        vo.setBaseName(rs.getString("baseName"));
        vo.setBaseType(rs.getString("baseType"));
        vo.setSortId(rs.getInt("sortid"));
        vo.setDesc(rs.getString("cdesc"));
        vo.setIsShow(Integer.parseInt(rs.getString("isshow")));
        vo.setProductId(rs.getString("productid"));
        vo.setVideo_status(rs.getString("video_status"));
    }

    /**
     * ���ڷ�����Ƶ�����б�
     * 
     * @return
     * @throws BOException
     */
    public List queryVideoCategoryList() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryVideoCategoryList( ) is starting ...");
        }

        // select * from t_vo_category t order by t.sortid
        String sqlCode = "baseVideo.dao.VideoDAO.queryVideoCategoryList.SELECT";
        ResultSet rs = null;
        List list = new ArrayList();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            while (rs.next())
            {
                VideoCategoryVO vo = new VideoCategoryVO();

                fromVideoCategoryVOByRs(vo, rs);

                list.add(vo);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("������Ƶ�����б��ѯ�����쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("������Ƶ�����б��ѯ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }
    
   

    /**
     * ���ڷ�����Ƶ������Ϣ
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public VideoCategoryVO queryVideoCategoryVO(String videoCategoryId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryVideoCategoryVO(" + videoCategoryId
                         + ") is starting ...");
        }

        // select t.*, n.productid from t_vo_category t, t_vo_node n where t.baseid = n.nodeid(+)
        String sqlCode = "baseVideo.dao.VideoDAO.queryVideoCategoryVO.SELECT";
        ResultSet rs = null;
        VideoCategoryVO vo = new VideoCategoryVO();

        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode(sqlCode, new Object[] { videoCategoryId });

            if (rs.next())
            {
                fromVideoCategoryVOByRs(vo, rs);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("������Ƶ������Ϣ��ѯ�����쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("������Ƶ������Ϣ��ѯ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return vo;
    }

    /**
     * ���ص�ǰ���ܵ��ӻ�����Ϣ�����ֻ�����Ϣ
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public int hasChild(String videoCategoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasChild(" + videoCategoryId + ") is starting ...");
        }

        // select count(*) as countNum from t_vo_category t where t.parentid = ?
        String sqlCode = "baseVideo.dao.VideoDAO.hasChild.SELECT";
        ResultSet rs = null;
        int countNum = 0;

        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode(sqlCode, new Object[] { videoCategoryId });

            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("���ص�ǰ���ܵ��ӻ�����Ϣ�����ֻ�����Ϣ�����쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("���ص�ǰ���ܵ��ӻ�����Ϣ�����ֻ�����Ϣ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }

    /**
     * ����ɾ��ָ������
     * 
     * @return
     * @throws BOException
     */
    public void delVideoCategory(TransactionDB tdb,String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("delVideoCategory(" + categoryId + ") is starting ...");
        }

        // delete from t_vo_category c where c.id=?
        String sqlCode = "baseVideo.dao.VideoDAO.delVideoCategory.DEL";

        try
        {
        	tdb.executeBySQLCode(sqlCode,
                                              new Object[] { categoryId });
        }
        catch (DAOException e)
        {
            throw new DAOException("���ݻ�������ɾ��ָ������ʱ�����쳣:", e);
        }
    }

    /**
     * 
     * @desc ����seq ��ȡ�µ���Ƶ����ID
     * @author
     * @return
     * @throws DAOException
     */
    public String getVideoCategoryId() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getVideoCategoryId() is starting ...");
        }

        // select SEQ_VO_CATEGORY_ID.nextval from dual
        String sqlCode = "baseVideo.dao.VideoDAO.getVideoCategoryId.SELECT";

        String newVideoCategoryId = null;

        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            if (rs.next())
            {
                newVideoCategoryId = rs.getString(1);
            }
        }
        catch (Exception e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }
        return newVideoCategoryId;
    }

    /**
     * ����������Ƶ����
     * 
     * @param newMusicCategory
     * @throws BOException
     */
    public void saveVideoCategory(VideoCategoryVO videoCategoryVO)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("saveVideoCategory() is starting ...");
        }

        // insert into t_vo_category
        // (id,parentid,baseid,basetype,basename,baseparentid,sortid,isshow,cdesc)
        // values
        // (?,?,?,?,?,?,?,?,?)
        String sqlCode = "baseVideo.dao.VideoDAO.saveVideoCategory.save";

        int a =0;
        System.out.println("dddd"+a);
        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] {
                                                videoCategoryVO.getId(),
                                                videoCategoryVO.getParentId(),
                                                videoCategoryVO.getBaseType(),
                                                videoCategoryVO.getBaseName(),
                                                videoCategoryVO.getBaseParentId(),
                                                new Integer(videoCategoryVO.getSortId()),
                                                new Integer(videoCategoryVO.getIsShow()),
                                                videoCategoryVO.getDesc() });
        }
        catch (DAOException e)
        {
            throw new DAOException("������Ƶ����ʱ�����쳣:", e);
        }
    }

    /**
     * ���ڱ����Ƶ����
     * 
     * @param newMusicCategory
     * @throws BOException
     */
    public void updateVideoCategory(VideoCategoryVO videoCategoryVO)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateVideoCategory() is starting ...");
        }

        // update t_vo_category c set c.basename=?, c.sortid=?, c.isshow=?,
        // c.cdesc=? where c.id = ?
        String sqlCode = "baseVideo.dao.VideoDAO.updateVideoCategory.update";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] {
                                                videoCategoryVO.getBaseName(),
                                                new Integer(videoCategoryVO.getSortId()),
                                                new Integer(videoCategoryVO.getIsShow()),
                                                videoCategoryVO.getDesc(),
                                                videoCategoryVO.getId() });
        }
        catch (DAOException e)
        {
            throw new DAOException("�����Ƶ����ʱ�����쳣:", e);
        }
    }
    /**
     * ���ڷ�����Ƶ��Ʒ�б�
     * 
     * @return
     * @throws BOException
     */
    public List queryVideoProductList() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryVideoProductList( ) is starting ...");
        }

        // select
		// t.productid,t.productname,t.fee,t.cpid,t.feetype,t.startdate,t.feedesc,t.freetype,t.freeeffectime,t.freetimefail
		// from T_VO_PRODUCT t order by t.STARTDATE desc
		String sqlCode = "baseVideo.dao.VideoDAO.queryVideoProductList.SELECT";
		ResultSet rs = null;
        List list = new ArrayList();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            while (rs.next())
            {
            	VideoProductVO vo = new VideoProductVO();
            	vo.setProductID(rs.getString("productid"));
            	vo.setCpid(rs.getString("cpid"));
            	vo.setProductName(rs.getString("productname"));        	
            	vo.setFeeDesc(rs.getString("feedesc"));       	
            	vo.setFeeType(rs.getString("feetype"));
            	vo.setStartdate(rs.getString("startdate"));
            	vo.setFee(rs.getInt("fee"));
            	vo.setFreeType(rs.getString("freetype"));
            	vo.setFreeEffecTime(rs.getString("freeeffectime"));
            	vo.setFreeTimeFail(rs.getString("freetimefail"));
                list.add(vo);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("������Ƶ��Ʒ�б��ѯ�����쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("������Ƶ��Ʒ�б��ѯ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }
    
    public int updateProductFeeDesc(String productId,String feeDesc) throws DAOException {
    	
    	//update T_VO_PRODUCT t set t.feedesc = ? where t.productid=?
    	String sqlCode = "baseVideo.dao.VideoDAO.updateProductFeeDesc.SELECT";
    	Object [] para = {feeDesc,productId};
    	return  DB.getInstance().executeBySQLCode(sqlCode, para);
 
    }
    
    
    
    /**
     * �������ơ�ID���ֶβ�ѯ��Ƶ��Ŀ
     * 
     * @param page
     * @param nodeId  ��Ƶ��ĿID
     * @param name ��Ƶ��Ŀ����
     * @throws DAOException
     */
    public void queryVideoNodeExtList(PageResult page, String nodeId,
                                    String name) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryVideoNodeExtList(nodeId=" + nodeId
                         + " name=" + name  + ")");
        }

        // select n.nodeid,n.nodename,t.nodedesc from t_vo_node n,t_vo_nodeext t where n.nodeid=t.nodeid(+)
        String sqlCode = "baseVideo.dao.VideoDAO.queryVideoNodeExtList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //����������sql�Ͳ���
            
            if (!"".equals(nodeId))
            {
                //sql += " and n.nodeId ='" + nodeId + "'";
            	sqlBuffer.append(" and n.nodeId = ? ");
            	paras.add(nodeId);
            }
            if (!"".equals(name))
            {
                //sql += " and n.nodename like'%" + name + "%'";
            	sqlBuffer.append(" and n.nodename like ? ");
            	paras.add("%"+SQLUtil.escape(name)+"%");
            }
           
            //sql += " order by n.nodeId";
            sqlBuffer.append(" order by n.nodeId");

            //page.excute(sql, null, new VideoNodeExtVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new VideoNodeExtVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("queryVideoNodeExtList is error", e);
        }
    }
    /**
     * �����ݿ��¼����ȡ���ݲ���װ��һ���¹����ContentVO����
     * 
     * @param vo ContentVO
     * @param rs ���ݿ��¼��
     */
    public final void getVideoNodeExtVOFromRS(VideoNodeExtVO vo, ResultSet rs)
                    throws SQLException
    {
        vo.setNodeId(rs.getString("nodeId"));

        vo.setNodeName(rs.getString("nodename"));
        vo.setNodeDesc(rs.getString("nodedesc"));
     
    }
    
    
    /**
     * 
     *@desc ������Ƶ��Ŀ˵����Ϣ
     *@author dongke
     *Jul 9, 2012
     * @param nodeId
     * @param desc
     * @return
     * @throws DAOException
     */
   public int updateVideoNodeExt(String nodeId, String desc) throws DAOException
	{
		int r = 0;
		if (desc == null || desc.equals(""))
		{// Ϊ�գ���ɾ�������
			// delete from t_vo_nodeext t where t.nodeid=?
			String delSqlCode = "baseVideo.dao.VideoDAO.updateVideoNodeExt.delete";
			Object[] para = { nodeId };
			r = DB.getInstance().executeBySQLCode(delSqlCode, para);
			//r = 1;
		}
		else
		{
			// update t_vo_nodeext t set t.LUPDATE=sysdate, t.nodedesc = ? where t.nodeid=?
			String sqlCode = "baseVideo.dao.VideoDAO.updateVideoNodeExt.update";
			// insert into t_vo_nodeext (nodedesc,nodeid,LUPDATE)
			// values(?,?,sysdate);
			String insertSqlCode = "baseVideo.dao.VideoDAO.updateVideoNodeExt.insert";
			Object[] para = { desc, nodeId };

			r = DB.getInstance().executeBySQLCode(sqlCode, para);
			if (r == 0)
			{
				r = DB.getInstance().executeBySQLCode(insertSqlCode, para);
			}
		}
		return r;
	}
   
   /**
    * 
    * @desc ɾ����Ƶ������չ�ֶ�
    * @author dongke Aug 8, 2011
    * @param vo
    * @return
    * @throws DAOException
    */
   public int delVideoCategoryKeyResource(TransactionDB tdb,String tid) throws DAOException
   {
       if (logger.isDebugEnabled())
       {
           logger.debug("delVideoCategoryKeyResource() is starting ...");
       }
       // delete from t_key_resource where tid=? and keyid in (select keyid
       // from t_key_base b where b.keytable='t_vo_category')
       String sqlCode = "baseVideo.dao.VideoDAO.delVideoCategoryKeyResource().SELECT";
       int r = 0;
       try
       {
           r = tdb
                 .executeBySQLCode(sqlCode, new Object[] { tid });
       }
       catch (DAOException e)
       {
           throw new DAOException("ɾ����Ƶ������չ�ֶ�ֵʱ�����쳣:", e);
       }
       return r;
   }
   
   /**
    * ���ڷ�����Ƶ������չ�ֶ��б�
    * 
    * @return
    * @throws BOException
    */
   public List queryVideoCategoryKeyBaseList(String tid) throws DAOException
   {
       if (logger.isDebugEnabled())
       {
           logger.debug("queryVideoCategoryKeyBaseList( ) is starting ...");
       }
       String sqlCode = null;
       ResultSet rs = null;
       List list = new ArrayList();
       boolean insert = true;
       try
       {
           if (tid != null && !tid.equals(""))
           {
               // select * from t_key_base b, (select * from t_key_resource r
               // where r.tid = ?) y where b.keytable = 't_vo_category' and
               // b.keyid = y.keyid(+)
               sqlCode = "baseVideo.dao.VideoDAO.queryReadCategoryKeyBaseResList().SELECT";
               Object[] paras = { tid };
               rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
               insert = false;
           }
           else
           {
               // select * from t_key_base b where b.keytable = 't_vo_category'
               sqlCode = "baseVideo.dao.VideoDAO.queryReadCategoryKeyBaseList().SELECT";
               rs = DB.getInstance().queryBySQLCode(sqlCode, null);
               insert = true;
           }
           while (rs.next())
           {
               ResourceVO vo = new ResourceVO();
               fromVideoCategoryKeyBaseVOByRs(vo, rs, insert);
               list.add(vo);
           }
       }
       catch (DAOException e)
       {
           throw new DAOException("��ȡ�Ķ�������չ��Ϣ����Ϣ��ѯ�����쳣:", e);
       }
       catch (SQLException e)
       {
           throw new DAOException("��ȡ�Ķ�������չ��Ϣ����Ϣ��ѯ�����쳣:", e);
       }
       finally
       {
           DB.close(rs);
       }

       return list;
   }

   /**
    * Ϊ�������Ը�ֵ
    * 
    * @param vo
    * @param rs
    * @throws SQLException
    */
   private void fromVideoCategoryKeyBaseVOByRs(ResourceVO vo, ResultSet rs,
                                              boolean insert)
                   throws SQLException
   {
       vo.setKeyid(rs.getString("keyid"));
       vo.setKeyname(rs.getString("keyname"));
       vo.setKeytable(rs.getString("keytable"));
       vo.setKeydesc(rs.getString("keydesc"));
       vo.setKeyType(rs.getString("keytype"));
       if (!insert)
       {
           vo.setTid(rs.getString("tid"));
           vo.setValue(rs.getString("value"));
       }

   }
   
   /**
    * ���ݻ���Id������Ƶ����ɾ��״̬
    * 
    * @return
    * @throws BOException
    */
   public void updateVideoCategoryDelStatus(String categoryId) throws DAOException
   {

       if (logger.isDebugEnabled())
       {
           logger.debug("updateVideoCategoryDelStatus(" + categoryId + ") is starting ...");
       }

       // update t_vo_category c set c.delpro_status = c.video_status,c.video_status=0,c.UPDATETIME=sysdate where c.categoryid\=? 
       String sqlCode = "baseVideo.dao.VideoDAO.updateVideoCategoryDelStatus.UPDATE";

       try
       {
           DB.getInstance().executeBySQLCode(sqlCode,
                                             new Object[] { categoryId });
       }
       catch (DAOException e)
       {
           throw new DAOException("���ݻ���Id������Ƶ����ɾ��״̬ʱ�����쳣:", e);
       }
   }
}
