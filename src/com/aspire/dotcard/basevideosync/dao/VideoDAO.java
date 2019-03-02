package com.aspire.dotcard.basevideosync.dao;

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
import com.aspire.dotcard.basevideosync.vo.ProgramVO;
import com.aspire.dotcard.basevideosync.vo.VideoCategoryVO;
import com.aspire.dotcard.basevideosync.vo.VideoReferenceVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class VideoDAO {

	protected static JLogger logger = LoggerFactory.getLogger(VideoDAO.class);

	private static VideoDAO dao = new VideoDAO();

	private VideoDAO() {
	}

	public static VideoDAO getInstance() {
		return dao;
	}
	
	/**
     * 用于返回视频货架信息
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public VideoCategoryVO queryVideoCategoryVO(String categoryId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.queryVideoCategoryVO(" + categoryId
                         + ") is starting ...");
        }

        // select c.id,c.categoryid,c.parentcid,c.cname,c.cdesc,c.pic,c.sortid,c.isshow from t_v_category c where c.categoryid = ? and delflag=0
        String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.queryVideoCategoryVO.SELECT";
        ResultSet rs = null;
        VideoCategoryVO vo = new VideoCategoryVO();

        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode(sqlCode, new Object[] { categoryId });

            if (rs.next())
            {
                videoCategoryVOByRs(vo, rs);
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
     * 用于查询当前货架下商品列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryVideoReferenceList(PageResult page, VideoReferenceVO vo)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.queryVideoReferenceList(" + vo.getRefId()
                         + ") is starting ...");
        }

        //select r.id,r.programid,r.categoryid,r.cms_id cmsid,r.pname,r.sortid,r.LUPDATE,r.VERIFY_DATE,r.VERIFY_STATUS,r.DELFLAG,r.FEETYPE,d.displayname
        //from t_v_reference r ,t_v_dprogram d where r.programid = d.programid 
		String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.queryVideoReferenceList.SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            if(!"".equals(vo.getSubcateName())||!"".equals(vo.getTagName())||!"".equals(vo.getKeyName())){
            	sql = sql.replace("{1}", ",t_v_videospropertys p");
            	sql = sql.replace("{2}", " and d.programid = p.programid");
            }else{
            	sql = sql.replace("{1}", "");
            	sql = sql.replace("{2}", "");
            }
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
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
            	sqlBuffer.append(" and r.pname like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getProgramName())+"%");
            }
            if (!"".equals(vo.getCmsId()))
            {
            	sqlBuffer.append(" and r.cms_id = ? ");
            	paras.add(vo.getCmsId());
            }
            if (!"".equals(vo.getSubcateName()))
            {
            	sqlBuffer.append(" and p.propertykey = ? ");
            	paras.add(vo.getSubcateName());
            }
            if(!"".equals(vo.getKeyName())){
            	sqlBuffer.append(" and p.propertyvalue like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getKeyName())+"%");
            }
            if(!"".equals(vo.getVerifyStatus())&&!"-1".equals(vo.getVerifyStatus())){
            	sqlBuffer.append(" and r.VERIFY_STATUS = ? ");
            	paras.add(vo.getVerifyStatus());
            }
            sqlBuffer.append(" order by r.LUPDATE desc");

            page.excute(sqlBuffer.toString(), paras.toArray(), new VideoReferencePageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }
    /**
    * 用于查询当前货架下商品列表
    * 
    * @param page
    * @param vo
    * @throws DAOException
    */
   public void queryHotContentReferenceList(PageResult page, VideoReferenceVO vo)
                   throws DAOException
   {
	   if (logger.isDebugEnabled())
       {
           logger.debug("basevideosync.dao.VideoDAO.queryHotContentReferenceList(" + vo.getCategoryId()
                        + ") is starting ...");
       }
	 //select r.id,r.programid,r.categoryid,r.cms_id cmsid,r.pname,r.sortid,r.LUPDATE,r.VERIFY_DATE,r.VERIFY_STATUS,r.DELFLAG,r.FEETYPE 
       //from t_v_reference r ,t_v_hotcontent_program d{1} where r.programid = d.PRDCONTID{2}
		String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.queryHotContentReferenceList.SELECT";
       String sql = null;

       try
       {
           sql = SQLCode.getInstance().getSQLStatement(sqlCode);
           
           if(!"".equals(vo.getSubcateName())||!"".equals(vo.getTagName())||!"".equals(vo.getKeyName())){
           	sql = sql.replace("{1}", ",t_v_videospropertys p");
           	sql = sql.replace("{2}", " and d.PRDCONTID = p.programid");
           }else{
           	sql = sql.replace("{1}", "");
           	sql = sql.replace("{2}", "");
           }
           
           StringBuffer sqlBuffer = new StringBuffer(sql) ;
           List paras = new ArrayList();
           //构造搜索的sql和参数
           
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
           	sqlBuffer.append(" and r.pname like ? ");
           	paras.add("%"+SQLUtil.escape(vo.getProgramName())+"%");
           }
           if (!"".equals(vo.getCmsId()))
           {
           	sqlBuffer.append(" and r.cms_id = ? ");
           	paras.add(vo.getCmsId());
           }
           if (!"".equals(vo.getSubcateName()))
           {
           	sqlBuffer.append(" and p.propertykey = ? ");
           	paras.add(vo.getSubcateName());
           }
           if(!"".equals(vo.getKeyName())){
           	sqlBuffer.append(" and p.propertyvalue like ? ");
           	paras.add("%"+SQLUtil.escape(vo.getKeyName())+"%");
           }
           if(!"".equals(vo.getVerifyStatus())&&!"-1".equals(vo.getVerifyStatus())){
           	sqlBuffer.append(" and r.VERIFY_STATUS = ? ");
           	paras.add(vo.getVerifyStatus());
           }
           sqlBuffer.append(" order by r.LUPDATE desc");

           page.excute(sqlBuffer.toString(), paras.toArray(), new VideoReferencePageVO());
       }
       catch (DataAccessException e)
       {
           throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                  e);
       }
	   
   }
    /**
     * 用于查询视频节目列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryProgramList(PageResult page, ProgramVO vo)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.queryProgramList( ) is starting ...");
        }

		// select p.programid,p.cmsid,p.name,p.updatetimev from t_v_dprogram p
		String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.queryProgramList.SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数

            if (!"".equals(vo.getProgramId()))
            {
                sqlBuffer.append(" and p.programid = ? ");
                paras.add(vo.getProgramId());
            }
            if (!"".equals(vo.getName()))
            {
                sqlBuffer.append(" and p.name like ? ");
                paras.add("%"+SQLUtil.escape(vo.getName())+"%");
            }
            if (!"".equals(vo.getCMSID()))
            {
            	sqlBuffer.append(" and p.cmsid = ? ");
            	paras.add(vo.getCMSID());
            }

            sqlBuffer.append(" order by p.programid");

            page.excute(sqlBuffer.toString(), paras.toArray(), new ProgramPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }
    
    /**
     * 
     *  用于查询当前货架下二级分类列表
     *  
     * @param categoryId
     * @throws DAOException
     */
    public List<String> getSubcateNameList(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.getSubcateNameList() is starting ...");
        }

        // select t.subcatename from t_v_type t where  t.categoryid = ? group by t.subcatename
        String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.getSubcateNameList.SELECT";

        List<String> subcateNameList = new ArrayList<String>();

        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{categoryId});

            while (rs.next())
            {
            	subcateNameList.add(rs.getString("subcatename"));
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
        return subcateNameList;
    }
    
    /**
     * 
     *  用于查询当前货架下二级分类下一级标签名称列表
     *  
     * @param categoryId
     * @param subcateName
     * @throws DAOException
     */
    public List<String> getTagNameList(String categoryId,String subcateName) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.getTagNameList() is starting ...");
        }

        // select t.tagname from t_v_type t where t.categoryid = ? and t.subcatename = ? group by t.tagname
        String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.getTagNameList.SELECT";

        List<String> tagNameList = new ArrayList<String>();

        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{categoryId,subcateName});

            while (rs.next())
            {
            	tagNameList.add(rs.getString("tagname"));
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
        return tagNameList;
    }
    
    /**
     * 
     *  根据seq 获取新的视频货架编码ID
     *  
     * @return
     * @throws DAOException
     */
    public String getVideoCategoryId() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.getVideoCategoryId() is starting ...");
        }

        // select SEQ_T_V_CATEGORY_CID.nextval from dual
        String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.getVideoCategoryId.SELECT";

        String categoryId = null;

        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            if (rs.next())
            {
            	categoryId = rs.getString(1);
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
        return categoryId;
    }
    
    /**
     * 用于新增视频货架
     * 
     * @param videoCategoryVO
     * @throws BOException
     */
    public void addVideoCategory(VideoCategoryVO videoCategoryVO)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.addVideoCategory() is starting ...");
        }

        //insert into t_v_category (id,categoryid,parentcid,cname,cdesc,pic,sortid,isshow,lupdate,VIDEO_STATUS)
        //   values (Seq_t_v_Category_Id.Nextval,?,?,?,?,?,?,?,sysdate,VIDEO_STATUS)
        String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.addVideoCategory.INSERT";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] {  videoCategoryVO.getCategoryId(),
                                                videoCategoryVO.getParentcId(),
                                                videoCategoryVO.getCname(),
                                                videoCategoryVO.getCdesc(),
                                                videoCategoryVO.getPicture(),
                                                new Integer(videoCategoryVO.getSortId()),
                                                videoCategoryVO.getIsShow(),
                                                videoCategoryVO.getVideoStatus()});
        }
        catch (DAOException e)
        {
            throw new DAOException("新增视频货架时发生异常:", e);
        }
    }
    
    /**
     * 用于添加指定的视频节目至货架中
     * 
     * @param categoryId 货架id
     * @param videoId 视频id列
     * @throws DAOException
     */
    public void addVideoReferences(String categoryId, String[] videoIds)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.addVideoReferences(" + categoryId + ") is starting ...");
        }

        //insert into t_v_reference
        //(id, categoryid, programid, cms_id, pname, sortid,VERIFY_STATUS, lupdate)
        //values (SEQ_T_V_REFERENCE_ID.NEXTVAL, ?, ?, ?, ?,
        //(select decode(max(sortid), null, 1, max(sortid) + 1) from t_v_reference r where r.categoryid = ?),
        // sysdate)
        String sql = "com.aspire.dotcard.basevideosync.dao.VideoDAO.addVideoReferences.INSERT";
        String sqlCode[] = new String[videoIds.length];
        Object[][] object = new Object[videoIds.length][5];

        for (int i = 0; i < videoIds.length; i++)
        {
            String[] temps = videoIds[i].split("_");

            sqlCode[i] = sql;
            object[i][0] = categoryId;
            object[i][1] = temps[0];
            object[i][2] = temps[1];
            object[i][3] = temps[2];
            object[i][4] = categoryId;
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
            NewVideoRefDAO.getInstance().approvalCategoryGoods(categoryId);
        }
        catch (Exception e)
        {
            throw new DAOException("添加指定的视频至货架中时发生异常:", e);
        }
    }
    
    /**
     * 用于更新视频货架
     * 
     * @param videoCategoryVO
     * @throws BOException
     */
    public void updateVideoCategory(VideoCategoryVO videoCategoryVO)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.updateVideoCategory() is starting ...");
        }

        // update t_v_category c set c.cname=?, c.cdesc=?, c.pic = ?, c.sortid=?, c.isshow=?,c.VIDEO_STATUS=?,lupdate = sysdate
        //  where c.categoryid = ?
        String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.updateVideoCategory.UPDATE";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] {  videoCategoryVO.getCname(),
                                                videoCategoryVO.getCdesc(),
                                                videoCategoryVO.getPicture(),
                                                new Integer(videoCategoryVO.getSortId()),
                                                videoCategoryVO.getIsShow(),
                                                videoCategoryVO.getVideoStatus(),
                                                videoCategoryVO.getCategoryId() });
        }
        catch (DAOException e)
        {
            throw new DAOException("更新视频货架时发生异常:", e);
        }
    }
    
    /**
     * 用于删除指定货架
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public void delVideoCategory(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.delVideoCategory(" + categoryId + ") is starting ...");
        }

        // update t_v_category c set c.DELPRO_STATUS \= c.video_status,c.music_status\=0 where c.categoryid\=?
        String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.delVideoCategory.DELETE";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode,
                                              new Object[] { categoryId });
        }
        catch (DAOException e)
        {
            throw new DAOException("根据货架编码删除指定货架时发生异常:", e);
        }
    }
    
    /**
     * 用于删除指定货架下指定的视频节目商品
     * 
     * @param categoryId 货架id
     * @param refId 视频商品id列
     * @throws DAOException
     */
    public void delVideoReferences(String categoryId, String[] refId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.delVideoReferences(" + categoryId + ") is starting ...");
        }

        // update t_v_reference r set DELFLAG=r.VERIFY_STATUS,VERIFY_STATUS=0 where r.categoryid = ? and r.programid = ?
        String sql = "com.aspire.dotcard.basevideosync.dao.VideoDAO.delVideoReferences.DELETE";
        String sqlCode[] = new String[refId.length];
        Object[][] object = new Object[refId.length][2];

        for (int i = 0; i < refId.length; i++)
        {
            sqlCode[i] = sql;
            object[i][0] = categoryId;
            String programid = refId[i].split("#")[0];
            object[i][1] = programid;
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
            NewVideoRefDAO.getInstance().approvalCategoryGoods(categoryId);
        }
        catch (Exception e)
        {
            throw new DAOException("移除指定货架下指定的视频时发生异常:", e);
        }
    }
    
    /**
     * 用于设置视频货架下视频商品排序值
     * 
     * @param categoryId 货架id
     * @param setSortId 视频排序id
     * @throws DAOException
     */
    public void setVideoReferenceSort(String categoryId, String[] setSortId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.setVideoReferenceSort(" + categoryId + ") is starting ...");
        }

        // update t_v_reference r set r.sortid=?,VERIFY_STATUS=0 where r.id=? and r.categoryid=?
        String sql = "com.aspire.dotcard.basevideosync.dao.VideoDAO.setVideoReferenceSort.set";
        String sqlCode[] = new String[setSortId.length];
        Object[][] object = new Object[setSortId.length][3];

        for (int i = 0; i < setSortId.length; i++)
        {
            String[] temp = setSortId[i].split("_");
            sqlCode[i] = sql;
            object[i][0] = temp[1];
            object[i][1] = temp[0];
            object[i][2] = categoryId;
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
            NewVideoRefDAO.getInstance().approvalCategoryGoods(categoryId);
        }
        catch (Exception e)
        {
            throw new DAOException("设置视频货架下视频商品排序值时发生异常:", e);
        }
    }
    
    /**
     * 查看当前货架是否存在子货架
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public int hasChild(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.hasChild(" + categoryId + ") is starting ...");
        }

        // select count(c.id) as countNum from t_v_category c where c.parentcid = ?
        String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.hasChild.SELECT";
        ResultSet rs = null;
        int countNum = 0;

        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode(sqlCode, new Object[] { categoryId });

            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("查看当前货架是否存在子货架发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("查看当前货架是否存在子货架发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }
    
    /**
     * 查看当前货架下是否还存在着商品
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public int hasReference(String categoryId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.hasReference(" + categoryId + ") is starting ...");
        }

        // select count(r.id) as countNum from t_v_reference r where r.categoryid = ?
        String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.hasReference.SELECT";
        ResultSet rs = null;
        int countNum = 0;
        
        rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{categoryId});

        try
        {
            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("查看当前货架下是否还存在着商品信息时发生异常:", e);
        }
        
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }
    
    /**
     * 用于查看指定货架中是否存在指定视频节目
     * 
     * @param categoryId 货架id
     * @param programIds 视频节目id列
     * @throws DAOException
     */
    public String isHasReferences(String categoryId, String[] programIds)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("basevideosync.dao.VideoDAO.isHasReferences(" + categoryId + ") is starting ...");
        }

        // select r.programid,r.cms_id from t_v_reference r where r.categoryid= ?
        String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.isHasReferences.SELECT";
        String sql;
        ResultSet rs = null;
        String ret = "";

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数

            paras.add(categoryId);
            
            for (int i = 0; i < programIds.length; i++)
            {
            	if(i == 0){
            		sqlBuffer.append(" and r.programid in ( ? ");
            	}else{
            		sqlBuffer.append(" , ? ");
            	}
            	if(i == programIds.length-1)
            		sqlBuffer.append(" ) ");
            	paras.add(programIds[i]);
            }
            
            rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());

            while (rs.next())
            {
                ret += rs.getString("programid")+"|"+ rs.getString("cms_id") + ". ";
            }

        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
        catch (SQLException e)
        {
            throw new DAOException("查看指定货架中是否存在指定视频节目时发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return ret;
    }
    
    /**
     * 为对象属性赋值
     * 
     * @param vo
     * @param rs
     * @throws SQLException
     */
    private void videoCategoryVOByRs(VideoCategoryVO vo, ResultSet rs)
                    throws SQLException
    {
        vo.setId(rs.getString("id"));
        vo.setCategoryId(rs.getString("categoryid"));
        vo.setParentcId(rs.getString("parentcid"));
        vo.setCname(rs.getString("cname"));
        vo.setSortId(rs.getInt("sortid"));
        vo.setCdesc(rs.getString("cdesc"));
        vo.setPicture(rs.getString("pic"));
        vo.setIsShow(Integer.parseInt(rs.getString("isshow")));
        vo.setVideoStatus(rs.getString("VIDEO_STATUS"));
        vo.setGoodsStatus(rs.getString("GOODS_STATUS"));
        vo.setDelproStatus(rs.getString("DELPRO_STATUS"));
    }
    
    /**
     * 视频商品分页读取VO的实现类
     */
    private class VideoReferencePageVO implements PageVOInterface
    {
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            VideoReferenceVO vo = ( VideoReferenceVO ) content;

            vo.setRefId(rs.getString("Id"));
            vo.setCategoryId(rs.getString("categoryId"));
            vo.setProgramId(rs.getString("programId"));
            vo.setProgramName(rs.getString("pname"));
            vo.setCmsId(rs.getString("cmsId"));
            vo.setSortId(rs.getInt("sortId"));
            vo.setLastUpTime(rs.getString("LUPDATE"));
            vo.setVerifyStatus(rs.getString("VERIFY_STATUS"));
            vo.setDelflag(rs.getString("DELFLAG"));
            vo.setFeetype(rs.getString("FEETYPE"));
            vo.setVerifyDate(rs.getTimestamp("VERIFY_DATE"));
            String displayname = "";
            try {
            	displayname = rs.getString("displayname");
			} catch (Exception e) {
			}
            vo.setTagName(displayname);
            vo.setBroadcast(rs.getString("broadcast"));
            vo.setCountriy(rs.getString("countriy"));
            vo.setContentType(rs.getString("contentType"));
        }

        public Object createObject()
        {
            return new VideoReferenceVO();
        }
    }
    
    /**
     * 视频节目分页读取VO的实现类
     */
    private class ProgramPageVO implements PageVOInterface
    {
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            ProgramVO vo = ( ProgramVO ) content;

            vo.setProgramId(rs.getString("programId"));
            vo.setCMSID(rs.getString("cmsid"));
            vo.setName(rs.getString("name"));
            vo.setUpdatetimev(rs.getString("updatetimev"));
        }

        public Object createObject()
        {
            return new ProgramVO();
        }
    }
    /**
	 * 提交审批
	 * 
	 * @param tdb
	 * @param categoryId
	 *            货架编号
	 * @throws DAOException
	 */
	public void approvalCategory(TransactionDB tdb, String categoryId)
			throws DAOException {
		String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.approvalCategory";

		try {
			tdb.executeBySQLCode(sqlCode, new Object[] { categoryId });
		} catch (DAOException e) {
			logger.error("根据货架ID审批货架时发生异常:", e);
			throw new DAOException("根据货架ID审批货架时发生异常:", e);
		}
	}
	/**
	 * 音乐货架审批表
	 * 
	 * @param tdb
	 * @param categoryId
	 *            货架编号ID
	 * @param status
	 *            审批状态
	 * @param operation
	 *            操作对象
	 * @param operator
	 *            操作人
	 * @throws BOException
	 */
	public void approvalCategory(TransactionDB tdb, String categoryId,
			String status, String operation, String operator)
			throws BOException {
		ResultSet rs = null;
		try {
			rs = DB.getInstance()
					.queryBySQLCode(
							"com.aspire.dotcard.basevideosync.dao.VideoDAO.approvalCategory.SELECT",
							new Object[] { categoryId, operation });
			if (rs != null && rs.next()) {
				if ("2".equals(status)) {
					tdb.executeBySQLCode(
							"com.aspire.dotcard.basevideosync.dao.VideoDAO.UPDATE1",
							new Object[] { operator, categoryId, operation });
				} else {
					tdb.executeBySQLCode(
							"com.aspire.dotcard.basevideosync.dao.VideoDAO.UPDATE2",
							new Object[] { operator, categoryId, operation });
				}
			} else {
				if ("2".equals(status)) {
					tdb.executeBySQLCode(
							"com.aspire.dotcard.basevideosync.dao.VideoDAO.approvalCategory.INSERT1",
							new Object[] { operator, categoryId, operation });
				} else {
					tdb.executeBySQLCode(
							"com.aspire.dotcard.basevideosync.dao.VideoDAO.approvalCategory.INSERT2",
							new Object[] { operator, categoryId, operation });
				}
			}
		} catch (DAOException e) {
			logger.error("更新视频POMS货架审批表异常", e);
			throw new BOException("更新视频POMS货架审批表异常");
		} catch (SQLException e) {
			logger.error("更新视频POMS货架审批表异常", e);
			throw new BOException("更新视频POMS货架审批表异常");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					logger.error(e);
				}
			}

		}
	}
    
}
