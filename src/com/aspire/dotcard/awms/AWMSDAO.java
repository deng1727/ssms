package com.aspire.dotcard.awms;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryDAO;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.goods.GoodsDAO;
import com.aspire.ponaadmin.web.repository.goods.GoodsVO;
import com.aspire.ponaadmin.web.repository.persistency.db.NodePersistencyDB;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;


public class AWMSDAO
{
    /**
     * 日志引用
     */
    protected static JLogger LOG = LoggerFactory.getLogger(AWMSDAO.class);
    
    private AWMSDAO(){
        
    }
    
    private static AWMSDAO instance = null;
    
    public static AWMSDAO getInstance()
    {
        if(instance==null)
        {
            instance = new AWMSDAO();
        }
        return instance;
    }

//	public List<CategoryVO> getAllCategory(String categoryid, String type) {
//		// TODO Auto-generated method stub
//	   	List<CategoryVO> categoryList = new ArrayList<CategoryVO>();
//        try
//        {
//        	String sql = DB.getInstance().getSQLByCode("AWMSDAO.getAllCategory");
//        	if("awms".equals(type)){
//        		sql = sql.replaceAll("TRCATEGORY", "t_r_awms_category");
//        	}else{
//        		sql = sql.replaceAll("TRCATEGORY", "t_r_category");
//        	}
//            RowSet rs = DB.getInstance().query(sql,new String[]{categoryid,categoryid});
//            while(rs.next()){
//            	CategoryVO vo = new CategoryVO();
//            	vo.setName(rs.getString("name"));
//            	vo.setDescs(rs.getString("descs"));
//            	vo.setSortid(rs.getString("sortid"));
//            	vo.setCtype(rs.getString("ctype"));
//            	vo.setCategoryid(rs.getString("categoryid"));
//            	vo.setDelflag(rs.getString("delflag"));
//            	vo.setChangedate(rs.getString("changedate"));
//            	vo.setState(rs.getString("state"));
//            	vo.setParentcategoryid(rs.getString("parentcategoryid"));
//            	vo.setRelation(rs.getString("relation"));
//            	vo.setPicurl(rs.getString("picurl"));
//            	vo.setStatistic(rs.getString("statistic"));
//            	vo.setDevicecategory(rs.getString("devicecategory"));
//            	vo.setPlatform(rs.getString("platform"));
//            	vo.setCityid(rs.getString("cityid"));
//            	vo.setStartdate(rs.getString("startdate"));
//            	vo.setEnddate(rs.getString("enddate"));
//            	vo.setMultiurl(rs.getString("multiurl"));
//            	vo.setOthernet(rs.getString("othernet"));
//            	categoryList.add(vo);
//            }
//            
//        }
//        catch (Exception e)
//        {
//            LOG.error(e);
//        }
//        return categoryList;
//	}

	public void insertCategory(CategoryVO v) {
		// TODO Auto-generated method stub
		TransactionDB tdb = null;
		try{
			String sqlCode0 = "AWMSDAO.insertBase";
			String sqlCode1 = "AWMSDAO.insertCategory";
			String sqlCode2 = "AWMSDAO.insertCategory.mapping";
			NodePersistencyDB nodedb = new NodePersistencyDB(null);
			String id = nodedb.allocateNewNodeID();
			
			String categoryid = String.valueOf(CategoryDAO.getInstance()
					.getSeqCategoryID());
			tdb = TransactionDB.getTransactionInstance();
			tdb.executeBySQLCode(sqlCode0,new String[] { id, " "," ", "nt:category" });
			tdb.executeBySQLCode(sqlCode1,
					new String[]{
					id,
					v.getName(),
					v.getDescs(),
					v.getSortid(),
					v.getCtype(),
					categoryid,
					v.getDelflag(),
					v.getChangedate(),
					v.getState(),
					null,
					v.getRelation(),
					v.getPicurl(),
					v.getStatistic(),
					v.getDevicecategory(),
					v.getPlatform(),
					v.getCityid(),
					v.getStartdate(),
					v.getEnddate(),
					v.getMultiurl(),
					v.getOthernet()});
			tdb.executeBySQLCode(sqlCode2, new String[]{v.getCategoryid(),v.getParentcategoryid(),categoryid,v.getName()});
			tdb.commit();
			PPMSDAO.addMessagesStatic(MSGType.CatogoryModifyReq,null,id+":0");
			
		}catch(Exception e){
			tdb.rollback();
			LOG.error("insertCategory出错！",e);
		}finally{
			if(tdb!=null){
				tdb.close();
			}
		}
	}

	public void updateParentCategoryid(CategoryVO v) {
		// TODO Auto-generated method stub
		//update t_r_category cc set cc.parentcategoryid = (select bb.categoryid from t_awms_category_mapping aa ,t_awms_category_mapping bb where aa.awmspcategoryid=bb.awmscategoryid and   aa.categoryid='100002932') where cc.categoryid='100002932'
//		
//		update t_r_category cc set 
//		cc.parentcategoryid = (select bb.categoryid from t_awms_category_mapping aa ,t_awms_category_mapping bb where aa.awmspcategoryid=bb.awmscategoryid and   aa.categoryid='100002932')
//		where cc.categoryid='100002932'
		try{
			String sqlCode = "AWMSDAO.updateParentCategoryid";
			Object[] paras = {v.getCategoryid(),v.getCategoryid()};
			DB.getInstance().executeBySQLCode(sqlCode,paras);
		}catch(Exception e){
			LOG.error(e);
		}
	}

	public void updateBase(CategoryVO v) {
		// TODO Auto-generated method stub
	//select * from t_r_base b where b.id in(select c.id from t_r_category c ,t_awms_category_mapping m where c.categoryid=m.categoryid) 
		BaseVO oldV = getOldBaseVO(v.getCategoryid());
		if(oldV==null) {
			LOG.error("updateBase出现异常情况！oldV= "+oldV);
			return ;
			}
		BaseVO newV = getBaseVO(oldV.getId());
		if(newV!=null){
			updateBaseVO(newV);
		}else{
			LOG.error("updateBase出现异常情况！newV="+newV);
		}
	}

	private BaseVO getOldBaseVO(String categoryid) {
		// TODO Auto-generated method stub
		BaseVO vo = null;
        try
        {
        	String sql = DB.getInstance().getSQLByCode("AWMSDAO.getOldBaseVO");
            RowSet rs = DB.getInstance().query(sql,new String[]{categoryid});
            if(rs.next()){
            	vo = new BaseVO();
            	vo.setId(rs.getString("id"));
            	vo.setParentid(rs.getString("parentid"));
            	vo.setPath(rs.getString("path"));
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return vo;
	}

	private void updateBaseVO(BaseVO v) {
		// TODO Auto-generated method stub
		try{
			String sqlCode = "AWMSDAO.updateBaseVO";
			Object[] paras = {v.getParentid(),v.getPath(),v.getId()};
			DB.getInstance().executeBySQLCode(sqlCode,paras);
		}catch(Exception e){
			LOG.error(e);
		}
	}

	private BaseVO getParentBaseVO(String id) {
		// TODO Auto-generated method stub
		try{
			
			String sqlCode = "AWMSDAO.getParentBaseVO";
			RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, new String[]{id});
			if(rs.next()){
				return new BaseVO(rs.getString("id"),rs.getString("parentid"),rs.getString("path"));
			}
		}catch(Exception e){
			LOG.error(e);
		}
		return null;
	}

	//A B 
	//B.PARENTID=A.ID B.PATH=A.PATH+.{B.ID}
	
	private BaseVO getBaseVO(String id){
		BaseVO vo = getParentBaseVO(id);
		if(vo==null){
			return null;
		}
		if(vo.getPath().trim().equals("")){
			String path = getBaseVO(vo.getParentid())+".{"+id+"}";
			return new BaseVO(id,vo.getId(),path);
		}else{
			String path = vo.getPath()+".{"+id+"}";
			return new BaseVO(id,vo.getId(),path);  
		}
	}

	public List<CategoryMappingVO> getALLCategoryMappingVO(String categoryid,
			String awmsCategoryid) {
		// TODO Auto-generated method stub
	   	List<CategoryMappingVO> categoryMappingList = new ArrayList<CategoryMappingVO>();
        try
        {
            RowSet rs = DB.getInstance().queryBySQLCode("AWMSDAO.getALLCategoryMappingVO",new String[]{categoryid,awmsCategoryid});
            while(rs.next()){
            	CategoryMappingVO vo = new CategoryMappingVO();
            	vo.setAwmsCategoryId(rs.getString("awmsCategoryId"));
            	vo.setAwmsPCategoryId(rs.getString("awmsPCategoryId"));
            	vo.setCategoryId(rs.getString("categoryId"));
            	vo.setName(rs.getString("name"));
            	categoryMappingList.add(vo);
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return categoryMappingList;
	}

	public List<RefVO> getAllRef(String categoryId) {
		// TODO Auto-generated method stub
	   	List<RefVO> refVOList = new ArrayList<RefVO>();
        try
        {
            RowSet rs = DB.getInstance().queryBySQLCode("AWMSDAO.getAllRef",new String[]{categoryId});
            while(rs.next()){
            	RefVO vo = new RefVO();
            	vo.setId(rs.getString("id"));
            	vo.setRefnodeid(rs.getString("refnodeid"));
            	vo.setSortid(rs.getString("sortid"));
            	vo.setGoodsid(rs.getString("goodsid"));
            	vo.setCategoryid(rs.getString("categoryid"));
            	vo.setLoaddate(rs.getString("loaddate"));
            	vo.setVariation(rs.getString("variation"));
            	refVOList.add(vo);
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return refVOList;
	}

	public void RemoveRefVO(RefVO vo) {
		// TODO Auto-generated method stub
		TransactionDB tdb = null;
		try{
			String sqlCode0 = "GoodsDAO.removeRefContentFromCategory.DELETE1";//t_r_base;
			String sqlCode1 = "GoodsDAO.removeRefContentFromCategory.DELETE2";//t_r_reference;
			tdb = TransactionDB.getTransactionInstance();
			tdb.executeBySQLCode(sqlCode0,new String[] {vo.getId()});
			tdb.executeBySQLCode(sqlCode1,new String[] {vo.getId()});
			tdb.commit();
			PPMSDAO.addMessagesStatic(MSGType.RefModifyReq,null,vo.getGoodsid()+"::::::9");//删除这些可选项就可以空字符串了。呵呵
		}catch(Exception e){
			tdb.rollback();
			LOG.error(e);
		}finally{
			if(tdb!=null){
				tdb.close();
			}
		}
		
	}

	public List<RefVO> getAllAWMSRef(String categoryId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
	   	List<RefVO> refVOList = new ArrayList<RefVO>();
        try
        {
            RowSet rs = DB.getInstance().queryBySQLCode("AWMSDAO.getAllAWMSRef",new String[]{categoryId});
            while(rs.next()){
            	RefVO vo = new RefVO();
            	vo.setId(rs.getString("id"));
            	vo.setRefnodeid(rs.getString("refnodeid"));
            	vo.setSortid(rs.getString("sortid"));
            	vo.setGoodsid(rs.getString("goodsid"));
            	vo.setCategoryid(rs.getString("categoryid"));
            	vo.setLoaddate(rs.getString("loaddate"));
            	vo.setVariation(rs.getString("variation"));
            	vo.setSsmsCategoryid(rs.getString("ssmscategoryid"));
            	refVOList.add(vo);
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return refVOList;
	}

	public void addCategory(GContent gc,RefVO vo){
		// TODO Auto-generated method stub
		try{
			String id = getIdByCategoryId(vo.getSsmsCategoryid());
			if(id==null){
				LOG.error("getIdByCategoryId(vo.getSsmsCategoryid()出问题："+vo.getId());
				return;
			}
			Category category = (Category) Repository.getInstance().getNode(
					id, RepositoryConstants.TYPE_CATEGORY);

			// 构造商品编码＝货架编码＋企业内码(6位)＋业务内码(12位)＋内容编码(12位)
			String goodsID = category.getCategoryID()
					+ PublicUtil.lPad(gc.getCompanyID(), 6)
					+ PublicUtil.lPad(gc.getProductID(), 12)
					+ PublicUtil.lPad(gc.getContentID(), 12);
			// 放到目标分类中

			// 分配一个新的资源id
			String newNodeID = category.getNewAllocateID();

			// 创建一个引用节点，并设置所需属性
			ReferenceNode ref = new ReferenceNode();
			ref.setId(newNodeID);
			ref.setParentID(category.getId());
			ref.setPath(category.getPath() + ".{" + newNodeID + "}");
			ref.setRefNodeID(gc.getId());
			//ref.setSortID(0);
			ref.setSortID(Integer.parseInt(vo.getSortid()));
			ref.setGoodsID(goodsID);
			ref.setCategoryID(category.getCategoryID());
			//ref.setVariation(RepositoryConstants.VARIATION_NEW);
			ref.setVariation(Integer.parseInt(vo.getVariation()));

			// 创建商品信息VO类，并设置所需属性
			GoodsVO goodsVO = new GoodsVO();
			goodsVO.setGoodsID(goodsID);
			goodsVO.setIcpCode(gc.getIcpCode());
			goodsVO.setIcpServId(gc.getIcpServId());
			goodsVO.setContentID(gc.getContentID());
			goodsVO.setCategoryID(category.getCategoryID());
			goodsVO.setGoodsName(gc.getName());
			goodsVO.setState(1);
			goodsVO.setChangeDate(new Date());
			goodsVO.setActionType(1);
			
			boolean isExisted = GoodsDAO.getInstance().isExistedGoodsID(goodsID);
			addNodeAndInsertGoodsInfo(ref, goodsVO, id,isExisted);
		}catch(Exception e){
			LOG.error("上货架时候出现错误！contentid:"+gc.getContentID()+",awms vo.getId:"+vo.getId(),e);
		}
		
	}

	private String getIdByCategoryId(String categoryid) {
		// TODO Auto-generated method stub
        try
        {
            RowSet rs = DB.getInstance().queryBySQLCode("AWMSDAO.getIdByCategoryId",new String[]{categoryid});
            if(rs.next()){
            	return rs.getString("id");
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return null;
	}

	/**
	 * 添加节点，并将GoodsVO中的信息写入到数据库中的商品历史信息表中
	 * @param vo 商品VO
	 * @param refNode 引用节点
	 * @throws DAOException
	 */
	public void addNodeAndInsertGoodsInfo(ReferenceNode refNode, GoodsVO vo,
			String id,boolean isExisted) throws DAOException {
		
		TransactionDB tdb = null;
		try{
		tdb = TransactionDB.getTransactionInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String updateTime = sdf.format(new Date());
		if (LOG.isDebugEnabled()) {
			LOG.debug("PPMSDAO.addNodeAndInsertGoodsInfo() is beginning...");
		}
		String sqlCode1 = "PPMSDAO.addNodeAndInsertGoodsInfo.INSERT1";
		String sqlCode2 = "PPMSDAO.addNodeAndInsertGoodsInfo.INSERT2";
		String sqlCode3 = "PPMSDAO.addNodeAndInsertGoodsInfo.INSERT3";
		Object[] paras1 = { refNode.getId(), refNode.getParentID(),
				refNode.getPath(), refNode.getType() };
		Object[] paras2 = { refNode.getId(), refNode.getRefNodeID(),
				new Integer(refNode.getSortID()),
				new Integer(refNode.getVariation()), refNode.getGoodsID(),
				refNode.getCategoryID(), updateTime };
		Object[] paras3 = { vo.getGoodsID(), vo.getIcpCode(),
				vo.getIcpServId(), vo.getContentID(), vo.getCategoryID(),
				vo.getGoodsName(), new Integer(vo.getState()),
				new Timestamp(vo.getChangeDate().getTime()),
				new Integer(vo.getActionType()) };
		tdb.executeBySQLCode(sqlCode1, paras1);
		tdb.executeBySQLCode(sqlCode2, paras2);
		if(!isExisted){//在历史表存在的goodsid，不入历史表。
			tdb.executeBySQLCode(sqlCode3, paras3);
		}
		tdb.commit();
		
//		//MSGUtil.addMSG(MSGType.RefModifyReq,feedback,vo.getGoodsID()+":0");
//		//大胖说要把上下架消息变大,所以注销上面的，改下以下。 add by aiyan 2013-04-27
//		//        Goodsid	必须	String	reference表中的goodsid
//		//        Categoryid	可选	String	货架categoryid，新建时必须有
//		//        Id	可选	String	货架categoryid对应的Id，新建时必须有
//		//        Refnodeid	可选	String	应用ID，新建时必须有
//		//        Sortid	可选	String	排序字段，新建时必须有
//		//        Loaddate	可选	String	更新时间，新建时必须有
//		//        Action	必须	String	0：新建
//		//        9：删除
//		//        Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。
		
		PPMSDAO.addMessagesStatic(MSGType.RefModifyReq, null, vo.getGoodsID() + ":"
				+ vo.getCategoryID() + ":" + id + ":" + refNode.getId()
				+ ":" + refNode.getSortID() + ":"
				+ DateUtil.formatDate(vo.getChangeDate(), "yyyyMMddHHmmss")
				+ ":0");
		}catch(Exception e){
			tdb.rollback();
			LOG.error(e);
		}finally{
			if(tdb!=null){
				tdb.close();
			}
		}
	}
	
	public void syncAWMSTable() {
		// TODO Auto-generated method stub
		TransactionDB tdb = null;
		try{
//			AWMSDAO.syncAWMSTable.category.drop=drop table t_r_awms_category
//			AWMSDAO.syncAWMSTable.category.create=create table t_r_awms_category as select * from awms.t_r_category
//			AWMSDAO.syncAWMSTable.reference.drop=drop table t_r_awms_reference
//			AWMSDAO.syncAWMSTable.reference.create=create table t_r_awms_reference as select * from awms.t_r_reference
			String sqlCode0 = "AWMSDAO.syncAWMSTable.category.drop";
			String sqlCode1 = "AWMSDAO.syncAWMSTable.category.create";
			String sqlCode2 = "AWMSDAO.syncAWMSTable.reference.drop";
			String sqlCode3 = "AWMSDAO.syncAWMSTable.reference.create";
			tdb = TransactionDB.getTransactionInstance();
			tdb.executeBySQLCode(sqlCode0,null);
			tdb.executeBySQLCode(sqlCode1,null);
			tdb.executeBySQLCode(sqlCode2,null);
			tdb.executeBySQLCode(sqlCode3,null);
			tdb.commit();
		}catch(Exception e){
			tdb.rollback();
			LOG.error(e);
		}finally{
			if(tdb!=null){
				tdb.close();
			}
		}
	}
	
//	public static void main(String[] argv){
//		String a = "abcdefg";
//		System.out.println(a.substring(a.length()-2));
//	}

//
//	
//	
//	Category category = (Category) Repository.getInstance().getNode(categoryID,
//			RepositoryConstants.TYPE_CATEGORY);
//
//	// 构造商品编码＝货架编码＋企业内码(6位)＋业务内码(12位)＋内容编码(12位)
//	String goodsID = category.getCategoryID()
//			+ PublicUtil.lPad(gc.getCompanyID(), 6)
//			+ PublicUtil.lPad(gc.getProductID(), 12)
//			+ PublicUtil.lPad(gc.getContentID(), 12);
//	// 放到目标分类中
//
//	// 分配一个新的资源id
//	String newNodeID = category.getNewAllocateID();
//
//	// 创建一个引用节点，并设置所需属性
//	ReferenceNode ref = new ReferenceNode();
//	ref.setId(newNodeID);
//	ref.setParentID(category.getId());
//	ref.setPath(category.getPath() + ".{" + newNodeID + "}");
//	ref.setRefNodeID(gc.getId());
//	ref.setSortID(0);
//	ref.setGoodsID(goodsID);
//	ref.setCategoryID(category.getCategoryID());
//	ref.setVariation(0);
//
//	// 创建商品信息VO类，并设置所需属性
//	GoodsVO goodsVO = new GoodsVO();
//	goodsVO.setGoodsID(goodsID);
//	goodsVO.setIcpCode(gc.getIcpCode());
//	goodsVO.setIcpServId(gc.getIcpServId());
//	goodsVO.setContentID(gc.getContentID());
//	goodsVO.setCategoryID(category.getCategoryID());
//	goodsVO.setGoodsName(gc.getName());
//	goodsVO.setState(1);
//	goodsVO.setChangeDate(new Date());
//	goodsVO.setActionType(1);
//	
	public GContent getGContent(String goodsid) {
		// TODO Auto-generated method stub
		GContent gc = null;
        try
        {
        	String contentid = goodsid.substring(goodsid.length()-12);
            RowSet rs = DB.getInstance().queryBySQLCode("AWMSDAO.getGContent",new String[]{contentid});
            if(rs.next()){
            	gc =  new GContent();
            	gc.setCompanyID(rs.getString("companyID"));
            	gc.setProductID(rs.getString("productID"));
            	gc.setContentID(contentid);
            	gc.setIcpCode(rs.getString("icpCode"));
            	gc.setIcpServId(rs.getString("icpServId"));
            	gc.setName(rs.getString("name"));
            	gc.setId(rs.getString("id"));
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return gc;
	}

	public List<CategoryVO> getInsertCategoryList(String awmsCategoryid) {
		// TODO Auto-generated method stub
	   	List<CategoryVO> categoryList = new ArrayList<CategoryVO>();
        try
        {
            RowSet rs = DB.getInstance().queryBySQLCode("AWMSDAO.getInsertCategoryList",new String[]{awmsCategoryid,awmsCategoryid,awmsCategoryid,awmsCategoryid});
            while(rs.next()){
            	CategoryVO vo = new CategoryVO();
            	vo.setName(rs.getString("name"));
            	vo.setDescs(rs.getString("descs"));
            	vo.setSortid(rs.getString("sortid"));
            	vo.setCtype(rs.getString("ctype"));
            	vo.setCategoryid(rs.getString("categoryid"));
            	vo.setDelflag(rs.getString("delflag"));
            	vo.setChangedate(rs.getString("changedate"));
            	vo.setState(rs.getString("state"));
            	vo.setParentcategoryid(rs.getString("parentcategoryid"));
            	vo.setRelation(rs.getString("relation"));
            	vo.setPicurl(rs.getString("picurl"));
            	vo.setStatistic(rs.getString("statistic"));
            	vo.setDevicecategory(rs.getString("devicecategory"));
            	vo.setPlatform(rs.getString("platform"));
            	vo.setCityid(rs.getString("cityid"));
            	vo.setStartdate(rs.getString("startdate"));
            	vo.setEnddate(rs.getString("enddate"));
            	vo.setMultiurl(rs.getString("multiurl"));
            	vo.setOthernet(rs.getString("othernet"));
            	categoryList.add(vo);
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return categoryList;
	}

	public List<CategoryVO> getUpdateCategoryList(String awmsCategoryid) {
		// TODO Auto-generated method stub
	   	List<CategoryVO> categoryList = new ArrayList<CategoryVO>();
        try
        {
            RowSet rs = DB.getInstance().queryBySQLCode("AWMSDAO.getUpdateCategoryList",new String[]{awmsCategoryid});
            while(rs.next()){
            	CategoryVO vo = new CategoryVO();
            	vo.setName(rs.getString("name"));
            	vo.setDescs(rs.getString("descs"));
            	vo.setSortid(rs.getString("sortid"));
            	vo.setCtype(rs.getString("ctype"));
            	vo.setCategoryid(rs.getString("categoryid"));
            	vo.setDelflag(rs.getString("delflag"));
            	vo.setChangedate(rs.getString("changedate"));
            	vo.setState(rs.getString("state"));
            	vo.setParentcategoryid(rs.getString("parentcategoryid"));
            	vo.setRelation(rs.getString("relation"));
            	vo.setPicurl(rs.getString("picurl"));
            	vo.setStatistic(rs.getString("statistic"));
            	vo.setDevicecategory(rs.getString("devicecategory"));
            	vo.setPlatform(rs.getString("platform"));
            	vo.setCityid(rs.getString("cityid"));
            	vo.setStartdate(rs.getString("startdate"));
            	vo.setEnddate(rs.getString("enddate"));
            	vo.setMultiurl(rs.getString("multiurl"));
            	vo.setOthernet(rs.getString("othernet"));
            	vo.setOthernet(rs.getString("othernet"));
            	vo.setSsmsCategoryid(rs.getString("ssmsCategoryid"));
            	categoryList.add(vo);
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return categoryList;
	}

	public void updateCategory(CategoryVO v) {
		// TODO Auto-generated method stub
		try{
			//update t_r_category c set c.name=?,c.delflag=?,c.sortid=? where c.id=?
			String sqlCode = "AWMSDAO.updateCategory";
			Object[] paras = {v.getName(),v.getDelflag(),v.getSortid(),v.getSsmsCategoryid()};
			DB.getInstance().executeBySQLCode(sqlCode,paras);
			
//			Catogoryid	必须	String	货架ID
//			Action	必须	String	0：新建
//			1：货架描述信息变更（包含扩展字段）
//			9：删除
//			Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。

			if("1".equals(v.getDelflag())){
				PPMSDAO.addMessagesStatic(MSGType.CatogoryModifyReq,null,v.getSsmsCategoryid()+":9");
			}else{
				PPMSDAO.addMessagesStatic(MSGType.CatogoryModifyReq,null,v.getSsmsCategoryid()+":1");
			}
			
		}catch(Exception e){
			LOG.error(e);
		}
	}
    
	/**
	 * 根据categoryId 获取id
	 * @param categoryId
	 * @return
	 */
	   public  String getCategoryCId(String categoryId){
	    	String sqlCode="syncAndroid.AutoSyncDAO.getCategoryCId.SELECT";
	    	// select id from t_r_category t where t.categoryid=?
	    	String cid = null;
	        try
	        {
	            Object[] paras = {categoryId};
	            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
	            if(rs.next()){
	            	cid = rs.getString("id");
	            }
	            
	        }
	        catch (Exception e)
	        {
	        	LOG.error(e);
	            e.printStackTrace();
	        }
	        return cid;
	    	
	    }
    
}
