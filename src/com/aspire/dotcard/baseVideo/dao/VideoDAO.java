
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
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(VideoDAO.class);

    /**
     * singleton模式的实例
     */
    private static VideoDAO instance = new VideoDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private VideoDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static VideoDAO getInstance()
    {
        return instance;
    }

    /**
     * 为对象属性赋值
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
     * 用于返回视频货架列表
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
            throw new DAOException("返回视频货架列表查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("返回视频货架列表查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }
    
   

    /**
     * 用于返回视频货架信息
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
            throw new DAOException("返回视频货架信息查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("返回视频货架信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return vo;
    }

    /**
     * 返回当前货架的子货架信息新音乐货架信息
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
            throw new DAOException("返回当前货架的子货架信息新音乐货架信息发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("返回当前货架的子货架信息新音乐货架信息发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }

    /**
     * 用于删除指定货架
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
            throw new DAOException("根据货架内码删除指定货架时发生异常:", e);
        }
    }

    /**
     * 
     * @desc 根据seq 获取新的视频货架ID
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
     * 用于新增视频货架
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
            throw new DAOException("新增视频货架时发生异常:", e);
        }
    }

    /**
     * 用于变更视频货架
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
            throw new DAOException("变更视频货架时发生异常:", e);
        }
    }
    /**
     * 用于返回视频产品列表
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
            throw new DAOException("返回视频产品列表查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("返回视频产品列表查询发生异常:", e);
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
     * 根据名称、ID等字段查询视频栏目
     * 
     * @param page
     * @param nodeId  视频栏目ID
     * @param name 视频栏目名称
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
            //构造搜索的sql和参数
            
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
     * 从数据库记录集获取数据并封装到一个新构造的ContentVO对象
     * 
     * @param vo ContentVO
     * @param rs 数据库记录集
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
     *@desc 更新视频栏目说明信息
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
		{// 为空，则删除或清空
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
    * @desc 删除视频货架扩展字段
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
           throw new DAOException("删除视频货架扩展字段值时发生异常:", e);
       }
       return r;
   }
   
   /**
    * 用于返回视频货架扩展字段列表
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
           throw new DAOException("获取阅读货架扩展信息表信息查询发生异常:", e);
       }
       catch (SQLException e)
       {
           throw new DAOException("获取阅读货架扩展信息表信息查询发生异常:", e);
       }
       finally
       {
           DB.close(rs);
       }

       return list;
   }

   /**
    * 为对象属性赋值
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
    * 根据货架Id更新视频货架删除状态
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
           throw new DAOException("根据货架Id更新视频货架删除状态时发生异常:", e);
       }
   }
}
