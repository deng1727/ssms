package com.aspire.ponaadmin.web.lockLocation.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.lockLocation.vo.ContentVO;
import com.aspire.ponaadmin.web.lockLocation.vo.LockLocationVO;
import com.aspire.ponaadmin.web.lockLocation.vo.RefrenceVO;

public class LockLocationDAO {
	private static JLogger LOGGER = LoggerFactory.getLogger(LockLocationDAO.class);
	private static LockLocationDAO instance;
	private LockLocationDAO(){
		
	}
	public static LockLocationDAO getInstance(){
		if(instance == null){
			instance = new LockLocationDAO();
		}
		return instance;
	}

	/**
	 * 分页查询货架锁定列表
	 * 
	 * @param page
	 * @param map
	 * @throws DAOException
	 */
	public void queryLockLocationList(PageResult page, Map map)
			throws DAOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("queryLockLocationList( )");
		}

		//select c.id,c.categoryid,c.name,c.sortid,c.islock,c.locktime from t_r_category c where 1=1 
		String sqlCode = "com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO.queryLockLocationList";

		try {
			// sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			StringBuffer sqlBuffer = new StringBuffer(DB.getInstance()
					.getSQLByCode(sqlCode));
			/**
			 * 货架内码ID
			 */
			String nodeId = (String)map.get("nodeId");
			/**
			 * 货架ID
			 */
			String categoryId = (String) map.get("categoryId");
			/**
			 * 货架名称
			 */
			String categoryName = (String) map.get("categoryName");
			/**
			 * 锁定状态
			 */
			String isLock = (String) map.get("isLock");
			
			// 构造搜索的sql和参数
			if(nodeId!=null&&!"".equals(nodeId)){
				sqlBuffer.append(" and C.id in (" + nodeId + ")");
			}
			if (categoryId!=null&&!"".equals(categoryId)) {
				sqlBuffer.append(" and C.categoryid in (" + categoryId + ")");
			}
			if (categoryName!=null&&!"".equals(categoryName)) {
				sqlBuffer.append(" and C.NAME like '%" + categoryName
						+ "%'");
			}
			if (!"".equals(isLock)&& !"-1".equals(isLock)) {
				sqlBuffer.append(" and C.isLock = ");
				sqlBuffer.append(isLock);
			}
			sqlBuffer.append(" order by locktime desc,categoryId");
		//	System.err.println("调试"+sqlBuffer.toString());
			page.excute(sqlBuffer.toString(), null,new LockLocationPageVO());

		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAOException("分页查询货架商品锁定列表异常。");
		}
	}
	/**
	 * 分页查询商品锁定列表
	 * 
	 * @param page
	 * @param map
	 * @throws DAOException
	 */
	public void queryLockRefList(PageResult page, Map map)
			throws DAOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("queryLockRefList( )");
		}

		//select r.*,g.contentid,g.name from t_r_reference r,t_r_gcontent g where r.islock=1  and g.id=r.refnodeid and r.categoryId=?
		String sqlCode = "com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO.queryLockList";

		try {
			// sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			StringBuffer sqlBuffer = new StringBuffer(DB.getInstance()
					.getSQLByCode(sqlCode));
			/**
			 * 货架ID
			 */
			String categoryId = (String)map.get("categoryId");
			/**
			 * 应用ID
			 */
			String contentId = (String)map.get("contentId");
			/**
			 * 应用名称
			 */
			String name = (String) map.get("name");
			
			if(contentId!=null&&!"".equals(contentId.trim())){
				sqlBuffer.append(" and g.contentid = ");
				sqlBuffer.append(contentId);
			}
			if (name!=null&&!"".equals(name.trim())) {
				sqlBuffer.append(" and g.NAME like '%" + name
						+ "%'");
			}
			sqlBuffer.append(" order by locknum ");
			page.excute(sqlBuffer.toString(),new Object[]{categoryId},new RefrencePageVO());

		} catch (Exception e) {
			LOGGER.error("分页查询货架商品锁定列表异常。",e);
			throw new DAOException("分页查询货架商品锁定列表异常。");
		}
	}
	/**
	 * 分页查询所有的应用
	 * @param page
	 * @param map
	 * @throws DAOException
	 */
	public void queryContentList(PageResult page, Map map)
	throws DAOException {
		//select g.id,g.name,g.contentid from t_r_gcontent g, t_r_base b, v_service v where g.id = b.id and g.icpcode = v.icpcode and g.icpservid = v.icpservid  and g.id not in (select r.refnodeid from t_r_reference r where r.categoryid=? and r.islock=1)
		String sqlCode = "com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO.queryContentList";
		try {
			StringBuffer sqlBuffer = new StringBuffer(DB.getInstance()
					.getSQLByCode(sqlCode));
			String categoryId = (String)map.get("categoryId");
			/**
			 * 应用ID
			 */
			String contentId = (String)map.get("contentId");
			/**
			 * 应用名称
			 */
			String name = (String) map.get("name");
			if(contentId!=null&&!"".equals(contentId.trim())){
				sqlBuffer.append(" and g.contentid = ");
				sqlBuffer.append(contentId);
			}
			if(name!=null&&!"".equals(name)){
				sqlBuffer.append(" and g.name like '%"+name+"%'");
			}
			sqlBuffer.append(" order by g.MARKETDATE desc");
			page.excute(sqlBuffer.toString(), new Object[]{categoryId},new ContentPageVO());
		} catch (Exception e) {
			LOGGER.error("分页查询所有的应用列表异常。",e);
			throw new DAOException("分页查询所有的应用列表异常。");
		}
		
		
	}
	/**
	 * 查询该货架下所有已经锁定的商品
	 * @param categoryId
	 * @return
	 * @throws DAOException
	 */
	public List<RefrenceVO> queryLockList(String categoryId)throws DAOException{
		List<RefrenceVO> locationList = new ArrayList<RefrenceVO>();
		ResultSet rSet = null;
		try {
			//select * from t_r_reference r where r.islock=1 and categoryid=?
			String sqlCode = "com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO.queryLockList";
			rSet = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{categoryId});
			while(rSet!=null&&rSet.next()){
				RefrenceVO vo = new RefrenceVO();
				vo.setContentId(rSet.getString("contentid"));
				vo.setName(rSet.getString("name"));
				vo.setId(rSet.getString("id"));
				vo.setCategoryId(rSet.getString("CATEGORYID"));
				vo.setRefNodeId(rSet.getString("REFNODEID"));
				try {
					vo.setSortId(Integer.parseInt(rSet.getString("SORTID")));
				} catch (Exception e) {
				}
				try {
					vo.setLockNum(Integer.parseInt(rSet.getString("LOCKNUM")));
				} catch (Exception e) {
				}
				
				locationList.add(vo);
			}
		} catch (Exception e) {
			LOGGER.error("查询"+categoryId+"的锁定商品异常",e);
			throw new DAOException("查询"+categoryId+"的锁定商品异常");
		}finally{
			DB.close(rSet);
		}
		return locationList;
	}
	/**
	 * 
	 * @param categoryId
	 * @param refNodeId
	 * @return
	 * @throws DAOException
	 */
	public String getRIdFromCategory(String categoryId,String refNodeId)throws DAOException{
		String rId= null;
		ResultSet rs = null;
		try {
			//select count(1) from t_r_reference r where r.categoryid=? and r.refnodeid=?
			String sqlCode = "com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO.isExistsInCategory";
			rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{categoryId,refNodeId});
			if(rs!=null&&rs.next()){
				rId = rs.getString("ID");
			}
			return rId;
		} catch (Exception e) {
			LOGGER.error("查商品"+refNodeId+"的ID，货架="+categoryId+"中时发生异常",e);
			throw new DAOException("查商品"+refNodeId+"的ID，货架="+categoryId+"中时发生异常");
		}finally{
			DB.close(rs);
		}
	}
	public void updateRefrence(RefrenceVO refrenceVO)throws DAOException{
		//update t_r_reference set islock=? , lockuser=? , locktime=sysdate where categoryid=? and refnodeid=?
		String sqlCode = "com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO.updateRefrence";
		Object paras[] = new Object[]{refrenceVO.getIsLock(),refrenceVO.getLockUser(),refrenceVO.getCategoryId(),refrenceVO.getRefNodeId()};
		try {
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (Exception e) {
			LOGGER.error("更新货架商品异常categoryid="+refrenceVO.getCategoryId()+",refNodeId="+refrenceVO.getRefNodeId(),e);
			throw new DAOException("更新货架商品异常categoryid="+refrenceVO.getCategoryId()+",refNodeId="+refrenceVO.getRefNodeId());
		}
		
	}
	/**
	 * 查询货架的所有锁定位置
	 * @param map
	 * @return
	 * @throws DAOException
	 */
	public String getLockNums(Map<String,Object> map)throws DAOException{
		ResultSet rs = null;
		String lockNums = "";
		String categoryId = (String)map.get("categoryId");
		String nodeId = (String)map.get("nodeId");
		try {
			StringBuffer sql = new StringBuffer("select categoryid,listagg(r.locknum,',')within GROUP (order by r.locknum) as lock_nums from t_r_reference r where r.islock=1 ");
			if(categoryId!=null&&!"".equals(categoryId.trim())){
				sql.append("and r.categoryid="+categoryId);
			}else if(nodeId!=null&&!"".equals(nodeId.trim())){
				sql.append(" and r.categoryid in(select c.categoryid from t_r_category c where c.id="+nodeId+")");
			}
			
			sql.append("group by categoryid");
			rs = DB.getInstance().query(sql.toString(), null);
			while (rs!=null&&rs.next()) {
				lockNums = rs.getString("lock_nums");
			}
			return lockNums;
		} catch (Exception e) {
			LOGGER.error("查询货架"+(categoryId==null?"":categoryId)+"的所有锁定位置异常",e);
			throw new DAOException("查询货架"+(categoryId==null?"":categoryId)+"的所有锁定位置异常");
		}finally{
			DB.close(rs);
		}
	}
	/**
	 * 是否已经被锁定
	 * @param categoryId
	 * @param lockNum
	 * @return
	 * @throws DAOException
	 */
	public boolean isLock(String categoryId,Integer lockNum)throws DAOException{
		boolean islock=false;
		ResultSet rs = null;
		try {
			//select count(*) from t_r_reference r where r.categoryid=? and r.islock=1
			StringBuffer sql = new StringBuffer("select count(*) from t_r_reference r where r.categoryid=? and r.islock=1 ");
			if(lockNum !=null){
				sql.append(" and r.locknum= ");
				sql.append(lockNum);
			}
			rs = DB.getInstance().query(sql.toString(), new Object[]{categoryId});
			if(rs!=null&&rs.next()){
				if(rs.getInt(1)>0){
					islock=true;
				}
			}
			
		} catch (Exception e) {
			LOGGER.error("查询货架"+categoryId+"是否是锁定的异常",e);
			throw new DAOException("查询货架"+categoryId+"是否是锁定的异常");
		}finally{
			DB.close(rs);
		}
		return islock;
	}
	/**
	 * 获取排序号
	 * @param categoryId
	 * @param localNum
	 * @return
	 * @throws DAOException
	 */
	public Integer getSortId(String categoryId,int localNum)throws DAOException{
		ResultSet rSet = null;
		Integer sortId = null;
		int count=0;
		try {
			//select count(1) from t_r_reference where r.categoryid=?
			String getCountSqlCode = "com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO.getSortId";
			rSet = DB.getInstance().queryBySQLCode(getCountSqlCode, new Object[]{categoryId});
			if(rSet!=null&&rSet.next())
				count = rSet.getInt(1);
		} catch (Exception e) {
			LOGGER.error("查询"+categoryId+"商品总数异常",e);
			throw new DAOException("查询"+categoryId+"商品总数异常");
		}finally{
			DB.close(rSet);
		}
		if(count==0){
			sortId=-1;
		}else if(count<=localNum){
			sortId = getMinSortId(categoryId);
		}else{
			sortId = getMaxSortId(categoryId);
			
		}
		return sortId;
	}
	/**
	 * 更新货架的锁定状态
	 * @param categoryId	货架ID
	 * @param lockStatus	锁定状态：0正常；1锁定
	 * @throws DAOException
	 */
	public void updateCategoryLockStatus(String categoryId,Integer lockStatus) throws DAOException{
		try {
			//update t_r_category set islock=?,locktime=? where categoryid=?
			String sqlCode = "com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO.updateCategoryLockStatus";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Object[] paras = {lockStatus,format.format(new Date()),categoryId};
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (Exception e) {
			LOGGER.error("更新货架"+categoryId+"的锁定状态为"+lockStatus+"时异常",e);
			throw new DAOException(e);
		}
	}
	public int getMaxSortId(String categoryId) throws DAOException{
		ResultSet rSet = null;
		int sortId =0;
		try {
			String sqlCode = "com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO.getMaxSortId";
			rSet = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{categoryId});
			if(rSet!=null&&rSet.next()){
				sortId = rSet.getInt(1)+1;
			}
			return sortId;
		} catch (Exception e) {
			LOGGER.error("查询货架"+categoryId+"的最大排序号异常",e);
			throw new DAOException("查询货架"+categoryId+"的最大排序号异常");
		}finally{
			DB.close(rSet);
		}
	}
	/**
	 * 获取货架下最小的排序号+1
	 * @param categoryId
	 * @return
	 * @throws DAOException
	 */
	public int getMinSortId(String categoryId) throws DAOException{
		ResultSet rSet = null;
		int minSortId =0;
		try {
			String sqlCode = "com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO.getMinSortId";
			rSet = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{categoryId});
			if(rSet!=null&&rSet.next()){
				minSortId = rSet.getInt(1)-1;
			}
			return minSortId;
		} catch (Exception e) {
			LOGGER.error("查询"+categoryId+"的最小排序号异常",e);
			throw new DAOException("查询"+categoryId+"的最小排序号异常");
		}finally{
			DB.close(rSet);
		}
		
	}
	/**
	 * 调用存储过程 用以修正排序号
	 * @param categoryId	货架ID
	 * @param rid	商品ID
	 * @param lockNum	锁定位置
	 * @param type	操作类型：DEL删除； UPDATE修改
	 * @return
	 */
	public String callProcedureFixSortid(String categoryId,String rid,int lockNum,String type) throws DAOException
	{
		CallableStatement cs = null;
		
		Connection conn = null;
		try
		{
			LOGGER.info("调用存储过程 用以修正排序号");
			conn = DB.getInstance().getConnection();
			cs = conn.prepareCall("{call P_LOCK_CATEGORY(?,?,?,?,?)}");
			cs.setString(1,categoryId);
			cs.setString(2,rid);
			cs.setInt(3,lockNum);
			cs.setString(4,type);
			cs.registerOutParameter(5, Types.VARCHAR);
			cs.execute();
			String result = cs.getString(5);
			return result;
		}
		catch (Exception e)
		{
			LOGGER.error("调用存储过程P_LOCK_CATEGORY 出错！！！", e);
			throw new DAOException("调用存储过程 出错！！！");
		}finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (Exception e) {
					LOGGER.error("关闭CallableStatement=失败",e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					LOGGER.error("关闭Connection=失败",e);
				}
			}
		}
	}
	public String callProcedureAutoFixSortid(String categoryId) throws DAOException{
		CallableStatement cs = null;
		
		Connection conn = null;
		try
		{
			conn = DB.getInstance().getConnection();
			cs = conn.prepareCall("{call P_LOCK_CATEGORY_AUTO_FIX(?,?)}");
			cs.setString(1,categoryId);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.execute();
			String result = cs.getString(2);
			return result;
		}
		catch (Exception e)
		{
			LOGGER.error("调用存储过程P_LOCK_CATEGORY_AUTO_FIX 出错！！！", e);
			throw new DAOException("调用存储过程 出错！！！");
		}finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (Exception e) {
					LOGGER.error("关闭CallableStatement=失败",e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					LOGGER.error("关闭Connection=失败",e);
				}
			}
		}
	}
	/**
	 * 应用类分页读取VO的实现类
	 */
	private class LockLocationPageVO implements PageVOInterface {

		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException {
			LockLocationVO lockLocationVO = (LockLocationVO) content;
			lockLocationVO.setId(rs.getString("id"));
			lockLocationVO.setCategoryId(rs.getString("categoryid"));
			lockLocationVO.setCategoryName(rs.getString("NAME"));
			lockLocationVO.setSortId(rs.getString("sortid"));
			lockLocationVO.setIsLock(rs.getString("islock"));
			lockLocationVO.setLockTime(rs.getString("locktime"));

		}

		public Object createObject() {

			return new LockLocationVO();
		}
	}
	
	private class RefrencePageVO implements PageVOInterface{

		@Override
		public void CopyValFromResultSet(Object content, ResultSet rSet)
				throws SQLException {
				RefrenceVO vo = (RefrenceVO)content;
				vo.setContentId(rSet.getString("contentid"));
				vo.setName(rSet.getString("name"));
				vo.setId(rSet.getString("id"));
				vo.setCategoryId(rSet.getString("CATEGORYID"));
				vo.setRefNodeId(rSet.getString("REFNODEID"));
				
				try {
					vo.setSortId(Integer.parseInt(rSet.getString("SORTID")));
				} catch (Exception e) {
				}
				try {
					vo.setLockNum(Integer.parseInt(rSet.getString("LOCKNUM")));
				} catch (Exception e) {
				}
			
		}

		@Override
		public Object createObject() {
			// TODO Auto-generated method stub
			return new RefrenceVO();
		}
		
	}
	private class ContentPageVO implements PageVOInterface{

		@Override
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException {
			ContentVO vo = (ContentVO)content;
			vo.setId(rs.getString("id"));
			vo.setContentId(rs.getString("contentid"));
			vo.setName(rs.getString("name"));
			vo.setAppId(rs.getString("appid"));
		}

		@Override
		public Object createObject() {
			// TODO Auto-generated method stub
			return new ContentVO();
		}
		
	}
}
