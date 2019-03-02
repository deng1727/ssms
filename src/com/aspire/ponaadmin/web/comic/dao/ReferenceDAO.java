package com.aspire.ponaadmin.web.comic.dao;


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
import com.aspire.dotcard.baseVideo.vo.VideoRefVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.comic.vo.ComicChapterVO;
import com.aspire.ponaadmin.web.comic.vo.ReferenceVO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO;


public class ReferenceDAO
{
    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(ReferenceDAO.class);

    /**
     * singleton模式的实例
     */
    private static ReferenceDAO instance = new ReferenceDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private ReferenceDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static ReferenceDAO getInstance()
    {
        return instance;
    }

    /**
     * 用于查询当前货架下商品列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryReferenceList(PageResult page, ReferenceVO vo)
                    throws DAOException
    {
        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.queryReferenceList";
        String sql = null;

        //sql = SQLCode.getInstance().getSQLStatement(sqlCode);
		sql = "select r.id,categoryId,contentId,c.name as contentName,r.flow_time,r.type,sortid,c.portal,r.VERIFY_STATUS from t_cb_reference r,t_cb_content c where r.contentid=c.id ";

        StringBuffer sqlBuffer = new StringBuffer(sql) ;
        List paras = new ArrayList();
        //构造搜索的sql和参数

		if (!"".equals(vo.getCategoryId()))
		{
		    //sql += " and r.categoryId ='" + vo.getCategoryId() + "'";
			sqlBuffer.append( " and r.categoryId =? ");
			paras.add(vo.getCategoryId());
		}
		if (!"".equals(vo.getContentId()))
		{
		    //sql += " and r.contentId like('%" + vo.getContentId().trim() + "%')";
			sqlBuffer.append(" and r.contentId like ? ");
			paras.add("%"+SQLUtil.escape(vo.getContentId().trim())+"%");
		}
		if (!"".equals(vo.getContentName()))
		{
		    //sql += " and c.name like('%" + vo.getContentName().trim() + "%')";
			sqlBuffer.append(" and c.name like ? ");
			paras.add("%"+SQLUtil.escape(vo.getContentName().trim())+"%");
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

		//sql += " order by sortid desc";
		sqlBuffer.append(" order by sortid desc");

		//page.excute(sql, null, new PageVOInterface(){
		page.excute(sqlBuffer.toString(), paras.toArray(), new PageVOInterface(){
			public void CopyValFromResultSet(Object vo, ResultSet rs)
					throws SQLException {
				// TODO Auto-generated method stub
				ReferenceVO referenceVO = (ReferenceVO)vo;
				referenceVO.setId(rs.getString("id"));
				referenceVO.setCategoryId(rs.getString("categoryId"));
				referenceVO.setContentId(rs.getString("contentId"));
				referenceVO.setContentName(rs.getString("contentName"));
				referenceVO.setFlowTime(rs.getString("flow_time"));
				referenceVO.setType(rs.getString("type"));
				referenceVO.setSortId(rs.getString("sortid"));
				referenceVO.setPortal(rs.getString("portal"));
				referenceVO.setVerify_status(rs.getString("verify_status"));
			}

			public Object createObject() {
				// TODO Auto-generated method stub
				return new ReferenceVO();
			}});
    }

    public void removeReference(String categoryId, String[] id)
                    throws DAOException
    {
        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.removeReference";

        TransactionDB tdb = null;
        try {
           tdb = TransactionDB.getInstance();
           for (int i = 0; i < id.length; i++)
           {
        	   String[] strs = id[i].split("#");
        	   tdb.executeBySQLCode(sqlCode, new Object[]{categoryId,strs[0]});
           }
           this.setCategoryGoodsApproval(tdb, categoryId);
           tdb.commit();
        }
        catch (DAOException e)
        {
        	logger.error("移除指定货架下商品时发生异常:", e);
            // 执行回滚
            tdb.rollback();
            throw new DAOException("移除指定货架下商品时发生异常:", e);
        } finally {
            if (tdb != null) {
                tdb.close();
              }
        }
    }

    public String isHasReference(String categoryId, String[] contentIdArray)
                    throws DAOException
    {
        // select * from t_mb_reference_new t, t_mb_music_new n where n.musicid=t.musicid
        //String sqlCode = "newmusicsys.NewMusicRefDAO.queryNewMusicRefList().SELECT";
        StringBuffer sql;
        ResultSet rs = null;
        String ret = "";
        
        
        try
        {
           // sql = new StringBuffer(SQLCode.getInstance().getSQLStatement(sqlCode));
            
            sql = new StringBuffer("select * from t_cb_reference r");
            sql.append(" where r.categoryId='").append(categoryId).append("' ");

            
            StringBuffer temp = new StringBuffer("");
            for (int i = 0; i < contentIdArray.length; i++)
            {
                temp.append("'").append(contentIdArray[i]).append("'").append(",");
            }
            
            if(temp.length() > 0)
            {
                temp.deleteCharAt(temp.length()-1);
                sql.append(" and r.contentId in ( ").append(temp).append(" )");
            }
            
            rs = DB.getInstance().query(sql.toString(), null);
            
            while (rs.next())
            {
                ret += rs.getString("contentId") + ". ";
            }
            
        }
        catch (SQLException e)
        {
            throw new DAOException("查看指定货架中是否存在指定内容ID时发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }
        
        return ret;
    }
    
    //add by aiyan 2012-12-21 begin
    public void addReference(String categoryId, String contentId,String sortId)
    throws Exception {
    	
    	if(!isExistContent(contentId)){//在动漫内容表如果没有找到这个内容就抛异常！
    		logger.error("动漫商品导入的时候，内容id在t_cb_content表不存在：contentid:"+contentId);
    		throw new Exception("动漫商品导入的时候，内容id在t_cb_content表不存在：contentid:"+contentId);
    	}
    	String sqlCode = "";
    	String[] paras = null;
    	if(isExistRef(categoryId,contentId)){
    		sqlCode = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.addReference.UPDATE";
    		paras = new String[]{sortId,categoryId,contentId};
    	}else{
    		sqlCode = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.addReference.INSERT";
    		paras = new String[]{categoryId,contentId,sortId,contentId};
    	}
    	
		
		try {
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (DAOException e) {
			logger.error("添加指定的内容至货架中时发生异常:",e);
			throw new DAOException("添加指定的内容至货架中时发生异常:", e);
		}
	}
    
    private boolean isExistRef(String categoryId, String contentId) throws Exception{
		String sqlCode = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.isExistRef";
		String[] paras = new String[]{categoryId,contentId};
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
		return rs.next();
    }
    
    private boolean isExistContent(String contentId) throws Exception{
		String sqlCode = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.verifyContentId";
		String[] paras = new String[]{contentId};
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
		return rs.next();
    }
    
   //add by aiyan 2012-12-21 end
    public void addReference(String categoryId, String[] contentId)
                    throws DAOException
    {
//    	insert into t_cb_reference(id,categoryid,contentid,
//    			sortid,type,portal,flow_time)
//
//    			select seq_cb_id.nextval,'100000089','000000062972',
//    			(select decode(max(sortid), null, 1, max(sortid) + 1) from t_cb_reference where categoryid='100000233') as sortid ,
//    			t.type,t.portal,sysdate from t_cb_content t where t.id='000000062972'
        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.addReference";
        TransactionDB tdb = null;
        try
        {
        	 tdb = TransactionDB.getTransactionInstance();
        	 for (int i = 0; i < contentId.length; i++)
             {
        		 tdb.executeBySQLCode(sqlCode, new Object[]{categoryId,contentId[i],categoryId,contentId[i]});
             }
            this.setCategoryGoodsApproval(tdb, categoryId);
            tdb.commit();
        }
        catch (DAOException e)
        {
        	logger.error("添加指定的内容至货架中时发生异常:", e);
            // 执行回滚
            tdb.rollback();
            throw new DAOException("添加指定的内容至货架中时发生异常:", e);
        } finally {
            if (tdb != null) {
                tdb.close();
              }
        }
    }
    
    public void setSort(String categoryId, String[] setSortId)
                    throws DAOException
    {
        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.setSort";

     // 进行事务操作
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
        	logger.error("设置动漫商品排序发生异常:", e);
			// 执行回滚
			tdb.rollback();
            throw new DAOException("设置动漫商品排序发生异常:", e);
        } finally {
			if (tdb != null) {
				tdb.close();
			}
		}
    }

    /**
     * 用于查询新音乐列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryList(PageResult page, ReferenceVO vo)
                    throws DAOException
    {
        // select * from t_cb_content c  where to_date(c.expiretime,'yyyyMMddHH24miss')>=trunc(sysdate)
        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.queryList";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(vo.getContentId()))
            {
                //sql += " and c.id like('%" + vo.getContentId() + "%')";
            	sqlBuffer.append(" and c.id like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getContentId())+"%");
            }
            if (!"".equals(vo.getContentName()))
            {
                //sql += " and c.name like('%" + vo.getContentName() + "%')";
            	sqlBuffer.append(" and c.name like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getContentName())+"%");
            }

            //sql += " order by c.name asc";
            sqlBuffer.append(" order by c.name asc");

            //page.excute(sql, null, new PageVOInterface(){
            page.excute(sqlBuffer.toString(), paras.toArray(), new PageVOInterface(){

    			public void CopyValFromResultSet(Object vo, ResultSet rs)
    					throws SQLException {
    				// TODO Auto-generated method stub
    				ReferenceVO referenceVO = (ReferenceVO)vo;
    				referenceVO.setContentId(rs.getString("id"));
    				referenceVO.setContentName(rs.getString("name"));
    				referenceVO.setPortal(rs.getString("portal"));
    				referenceVO.setType(rs.getString("type"));
    				
    			}

    			public Object createObject() {
    				// TODO Auto-generated method stub
    				return new ReferenceVO();
    			}});
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }
   
    /**
     * 用于查询动漫章节列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryChapterList(PageResult page, ComicChapterVO vo)
                    throws DAOException
    {
        // select * from t_cb_chapter c  where 1=1 and 
        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.queryChapterList";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(vo.getContentId()))
            {
               
            	sqlBuffer.append(" c.contentid=? ");
            	paras.add(vo.getContentId());
            }
           

            //sql += " order by c.name asc";
            sqlBuffer.append(" order by c.sortid asc");

            //page.excute(sql, null, new PageVOInterface(){
            page.excute(sqlBuffer.toString(), paras.toArray(), new PageVOInterface(){

    			public void CopyValFromResultSet(Object vo, ResultSet rs)
    					throws SQLException {
    				// TODO Auto-generated method stub
    				ComicChapterVO chapterVo = (ComicChapterVO)vo;
    				chapterVo.setChapterId(rs.getString("CHAPTERID"));
    				chapterVo.setChapterName(rs.getString("NAME"));
    				chapterVo.setFee(rs.getInt("FEE"));

    			}

    			public Object createObject() {
    				// TODO Auto-generated method stub
    				return new ComicChapterVO();
    			}});
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }
    /**
	 * 用于查询当前货架下是否有新音乐商品
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
    public int hasNewMusic(String categoryId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryNewMusicList( ) is starting ...");
        }

        // select count(*) as countNum from t_mb_reference_new t where t.categoryId = ?
        String sqlCode = "newmusicsys.NewMusicRefDAO.hasNewMusic().SELECT";
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
            throw new DAOException("返回当前货架的子货架信息新音乐货架信息发生异常:", e);
        }
        
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }
    
    
    public void delReference(String categoryId) throws DAOException
    {
 
        String sql = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.delReference";

        try
        {
            DB.getInstance().executeBySQLCode(sql, new Object[] { categoryId });
        }
        catch (DAOException e)
        {
            throw new DAOException("用于清空原货架下商品时发生异常:", e);
        }
    }
    
    /**
     * 校驗文件中數據是否在内容表中存在
     * 
     * @param list
     * @throws DAOException
     */
    public String verifyContentId(List list) throws DAOException
    {

        // select 1 from t_cb_content c where c.id = ?
    	int j=0;
        String sql = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.verifyContentId";
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        // 迭代查詢
        for (int i = 0; i < list.size(); i++)
        {
            String temp = (String)list.get(i);
            try
            {
                rs = DB.getInstance().queryBySQLCode(sql.toString(),
                                            new Object[] { temp });
                // 如果不存在相應數據
                if (!rs.next())
                {
                    list.remove(i);
                    i--;
                    sb.append(temp).append(". ");
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("查看指定货架中是否存在指定音乐时发生异常:", e);
            }
            finally
            {
                DB.close(rs);
            }
        }
        
        return sb.toString();
    }
    /**
     * 用于查询新音乐详情
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public NewMusicRefVO queryNewMusicInfo(String musicId)
                    throws DAOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("queryNewMusicInfo( ) is starting ...");
		}
		// select * from t_mb_music_new t where t.musicid=?
		String sqlCode = "newmusicsys.NewMusicRefDAO.queryNewMusicInfo().SELECT";
		DB db = DB.getInstance();
		ResultSet rs = null;
		Object paras[] = { musicId };
		NewMusicRefVO vo = null;
		try
		{
			rs = db.queryBySQLCode(sqlCode, paras);
			if (rs.next())
			{
				vo = new NewMusicRefVO();
				vo.setMusicId(musicId);
				vo.setMusicName(rs.getString("SONGNAME"));
				vo.setSinger(rs.getString("SINGER"));
				vo.setValidity(rs.getString("VALIDITY"));
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("执行查询音乐详情失败", e);
		}finally{
			db.close(rs);		
		}
		return vo;
	}
    
    
    /**
     * 
     *@desc 获取音乐的扩展字段
     *@author dongke
     *Aug 9, 2011
     * @param musicId
     * @return
     * @throws DAOException
     */
    public List queryNewMusicKeyResource(String musicId) throws DAOException
	{
		List keyResourceList = null;
		if (logger.isDebugEnabled())
		{
			logger.debug("queryNewMusicKeyResource( ) is starting ...");
		}
		// select *  from t_key_base b,     (select *   from t_key_resource r   where r.tid = ?) y   where b.keytable = 't_mb_music_new' and b.keyid = y.keyid(+)
		String sqlCode = "newmusicsys.NewMusicRefDAO.queryNewMusicKeyResource().SELECT";
		DB db = DB.getInstance();
		ResultSet rs = null;
		Object paras[] = { musicId };
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
		} catch (SQLException e)
		{

			e.printStackTrace();
			throw new DAOException("执行查询音乐扩展字段详情失败", e);
		} finally
		{
			db.close(rs);
		}
		return keyResourceList;
	} 
    /**
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<ReferenceVO> queryComicReferenceListByExport(ReferenceVO vo)
			throws DAOException
	{

        if (logger.isDebugEnabled())
        {
            logger.debug("queryComicReferenceListByExport( ) is starting ...");
        }
		String sqlCode = "baseVideo.dao.VideoReferenceDAO.queryProgramVOList().SELECT";
        String sql = null;

        //sql = SQLCode.getInstance().getSQLStatement(sqlCode);
		sql = "select r.id,categoryId,contentId,c.name as contentName,r.flow_time,r.type,sortid,c.portal from t_cb_reference r,t_cb_content c where r.contentid=c.id and r.verify_status = 1 ";
		ResultSet rs = null;
		List<ReferenceVO> list = new ArrayList<ReferenceVO>();

        StringBuffer sqlBuffer = new StringBuffer(sql) ;
        List paras = new ArrayList();
        //构造搜索的sql和参数
        try
        {
		if (!"".equals(vo.getCategoryId()))
		{
		    //sql += " and r.categoryId ='" + vo.getCategoryId() + "'";
			sqlBuffer.append( " and r.categoryId = ? ");
			paras.add(vo.getCategoryId());
		}
		if (!"".equals(vo.getContentId()))
		{
		    //sql += " and r.contentId like('%" + vo.getContentId().trim() + "%')";
			sqlBuffer.append(" and r.contentId like ? ");
			paras.add("%"+SQLUtil.escape(vo.getContentId().trim())+"%");
		}
		if (!"".equals(vo.getContentName()))
		{
		    //sql += " and c.name like('%" + vo.getContentName().trim() + "%')";
			sqlBuffer.append(" and c.name like ? ");
			paras.add("%"+SQLUtil.escape(vo.getContentName().trim())+"%");
		}

		//sql += " order by sortid desc";
		sqlBuffer.append(" order by sortid desc");
		

	     rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());
			
			while (rs.next())
			{
				list.add(baseComicReferenceVoData(rs));
			}
		}
		catch (SQLException e)
		{
			logger.error("执行获取当前条件下所有查询动漫信息失败", e);
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs);
		}
		
		return list;
    }
	private ReferenceVO baseComicReferenceVoData(ResultSet rs) throws SQLException
	{
		ReferenceVO referenceVO = new ReferenceVO();
		referenceVO.setId(rs.getString("id"));
		referenceVO.setCategoryId(rs.getString("categoryId"));
		referenceVO.setContentId(rs.getString("contentId"));
		referenceVO.setContentName(rs.getString("contentName"));
		referenceVO.setFlowTime(rs.getString("flow_time"));
		referenceVO.setType(rs.getString("type"));
		referenceVO.setSortId(rs.getString("sortid"));
		referenceVO.setPortal(rs.getString("portal"));
		return referenceVO;
    }  
	
	/**
	 * 动漫商品货架提交审批
	 * 
	 * @param tdb
	 * @param categoryId 动漫货架编码
	 * @throws DAOException
	 */
	public void setCategoryGoodsApproval(TransactionDB tdb,String categoryId)
			throws DAOException {
		String sql = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.setCategoryGoodsApproval";
		try {
			tdb.executeBySQLCode(sql, new Object[]{categoryId});
		} catch (DAOException e) {
			logger.error("设置动漫商品货架发生异常:", e);
            throw new DAOException("设置动漫商品货架发生异常:", e);
		}
	}
	
	/**
	 * 编辑动漫商品货架
	 * 
	 * @param categoryId 动漫货架编码
	 * @throws DAOException
	 */
	public void approvalCategoryGoods(String categoryId)
			throws BOException {
		String sql = "com.aspire.ponaadmin.web.comic.dao.ReferenceDAO.approvalCategoryGoods";
		try {
			DB.getInstance().executeBySQLCode(sql, new Object[]{categoryId});
		} catch (DAOException e) {
			logger.error("编辑动漫商品货架发生异常:", e);
            throw new BOException("编辑动漫商品货架发生异常:", e);
		}
	}
    
}
