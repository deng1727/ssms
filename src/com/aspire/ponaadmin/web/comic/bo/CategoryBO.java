package com.aspire.ponaadmin.web.comic.bo;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.comic.WriteActionLogAndEmailTask;
import com.aspire.ponaadmin.web.comic.dao.CategoryDAO;
import com.aspire.ponaadmin.web.comic.vo.CategoryVO;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicCategoryDAO;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;



public class CategoryBO
{
    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryBO.class);

    private static CategoryBO instance = new CategoryBO();

    private CategoryBO(){
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryBO getInstance()
    {
        return instance;
    }
    public CategoryVO queryCategoryVO(String categoryId) throws BOException
    {
        
        try
        {
            return CategoryDAO.getInstance()
                                      .queryCategoryVO(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回动漫货架信息时发生数据库异常！");
        }
    }

	public String getComicCategoryId() throws BOException {
		// TODO Auto-generated method stub
		String cid = null;
		try
		{
			// 调用NewMusicCategoryDAO进行新增新音乐货架
			cid = CategoryDAO.getInstance().getComicCategoryId();
		} catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("获取动漫货架时发生数据库异常！");
		}
		return cid;
	}

	public void saveCategory(CategoryVO categoryVO) throws BOException {
        try
        {
            CategoryDAO.getInstance()
                                      .saveCategory(categoryVO);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("新增动漫货架时发生数据库异常！");
        }
	}

	public void updateCategory(CategoryVO categoryVO) throws BOException {
		// TODO Auto-generated method stub
        try
        {
            CategoryDAO.getInstance()
                                      .updateCategory(categoryVO);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("变更动漫货架时发生数据库异常！");
        }
	}

	public int hasChild(String categoryId) throws BOException {
		// TODO Auto-generated method stub
        try
        {
            return CategoryDAO.getInstance()
                                      .hasChild(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回当前货架的子货架数目时发生数据库异常！");
        }
	}

	public int hasReference(String categoryId) throws BOException {
		// TODO Auto-generated method stub
        try
        {
            // 调用NewMusicRefDAO进行查询
            return CategoryDAO.getInstance()
                                      .hasReference(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回当前货架的商品数目时发生异常！");
        }
	}

	public void delCategory(String categoryId) throws BOException {
		// TODO Auto-generated method stub
        try
        {
            // 调用NewMusicCategoryDAO进行删除
            CategoryDAO.getInstance()
                                      .delCategory(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("删除指定货架时发生数据库异常！");
        }
	}
	
	/**
	 * 提交审批
	 * 
	 * @param categoryId 货架编号
	 * @throws BOException
	 */
	public void approvalCategory(HttpServletRequest request,String categoryId) throws BOException {
		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}
		// 进行事务操作
		TransactionDB tdb = null;
        try
        {
        	tdb = TransactionDB.getTransactionInstance();
            CategoryDAO.getInstance().approvalCategory(tdb,categoryId);
            CategoryDAO.getInstance().approvalCategory(tdb, categoryId, "2","1", logUser.getName());
            Map<String, Object> map = new HashMap<String, Object>();
			map.put("mailTitle", "待审事项：客户端门户运营内容调整审批");
			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：动漫货架管理;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：;<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式：" + "提交审批";
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：动漫货架管理;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 4) + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式：" + "提交审批";
			map.put("mailContent", value);
			map.put("status", "0");
			map.put("categoryId", categoryId);
			map.put("operation", "70");
			map.put("operationtype", "动漫货架管理提交审批");
			map.put("operationobj", "动漫货架管理");
			map.put("operationobjtype", "货架Id：" + categoryId);
			map.put("operator", logUser.getName());
			WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
			DaemonTaskRunner.getInstance().addTask(task);
			tdb.commit();
        }
        catch (DAOException e)
        {
        	tdb.rollback();
            logger.error(e);
            throw new BOException("审批指定货架时发生数据库异常！");
        }finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
    
}
