/*
 * 文件名：NewMusicCategoryBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.newmusicsys.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.datafield.dao.KeyResourceDAO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.newmusicsys.WriteActionLogAndEmailTask;
import com.aspire.ponaadmin.web.newmusicsys.dao.CategoryDAO;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicCategoryDAO;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicCategoryVO;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class NewMusicCategoryBO
{
    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(NewMusicCategoryBO.class);

    private static NewMusicCategoryBO instance = new NewMusicCategoryBO();

    private NewMusicCategoryBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static NewMusicCategoryBO getInstance()
    {
        return instance;
    }
    
    /**
     * 用于返回新音乐货架列表
     * @return
     * @throws BOException
     */
    public List queryNewMusicCategoryList() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.queryNewMusicCategoryList( ) is start...");
        }

        try
        {
            // 调用NewMusicCategoryDAO进行查询
            return NewMusicCategoryDAO.getInstance()
                                      .queryNewMusicCategoryList();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回新音乐货架列表时发生数据库异常！");
        }
    }
   
    /**
     * 用于返回新音乐货架信息
     * @return
     * @throws BOException
     */
    public NewMusicCategoryVO queryNewMusicCategoryVO(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.queryNewMusicCategoryVO("+categoryId+") is start...");
        }

        try
        {
            // 调用NewMusicCategoryDAO进行查询
            return NewMusicCategoryDAO.getInstance()
                                      .queryNewMusicCategoryVO(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回新音乐货架信息时发生数据库异常！");
        }
    }
    
    /**
     * 返回当前货架的子货架信息新音乐货架信息
     * @return
     * @throws BOException
     */
    public int hasNewMusicChild(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.hasNewMusicChild("+categoryId+") is start...");
        }

        try
        {
            // 调用NewMusicCategoryDAO进行查询
            return NewMusicCategoryDAO.getInstance()
                                      .hasNewMusicChild(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回当前货架的子货架信息新音乐货架信息时发生数据库异常！");
        }
    }
    
    /**
     * 查看当前货架下是否还存在着商品
     * @param categoryId
     * @return
     * @throws BOException
     */
    public int hasNewMusic(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.hasNewMusic("+categoryId+") is start...");
        }

        try
        {
            // 调用NewMusicRefDAO进行查询
            return NewMusicRefDAO.getInstance()
                                      .hasNewMusic(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回当前货架的子货架信息新音乐货架信息时发生数据库异常！");
        }
    }
    
    /**
     * 用于删除指定货架
     * @param tdb
     * @param categoryId
     * @throws BOException
     */
    public void delNewMusicCategory(TransactionDB tdb,String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.delNewMusicCategory("+categoryId+") is start...");
        }

        try
        {
            // 调用NewMusicCategoryDAO进行删除
            NewMusicCategoryDAO.getInstance()
                                      .delNewMusicCategory(tdb,categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("删除指定货架时发生数据库异常！");
        }
    }
    /**
	 * 用于变更新音乐货架
	 * 
	 * @param newMusicCategory
	 * @throws BOException
	 */
    public void updateNewMusicCategory(NewMusicCategoryVO newMusicCategory) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.updateNewMusicCategory() is start...");
        }

        try
        {
            // 调用NewMusicCategoryDAO进行变更新音乐货架
            NewMusicCategoryDAO.getInstance()
                                      .updateNewMusicCategory(newMusicCategory);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("变更新音乐货架时发生数据库异常！");
        }
    }
    /**
     * 用于新增新音乐货架
     * @param newMusicCategory
     * @throws BOException
     */
    public void saveNewMusicCategory(NewMusicCategoryVO newMusicCategory) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.saveNewMusicCategory() is start...");
        }

        try
        {
            // 调用NewMusicCategoryDAO进行新增新音乐货架
            NewMusicCategoryDAO.getInstance()
                                      .saveNewMusicCategory(newMusicCategory);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("新增新音乐货架时发生数据库异常！");
        }
    }
    
   /**********************/
    /**
     * 用于返回新音乐货架扩展字段列表
     * @return
     * @throws BOException
     */
    public List queryNewMusicCategoryKeyBaseList(String tid) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.queryNewMusicCategoryKeyBaseList( ) is start...");
        }

        try
        {
            // 调用NewMusicCategoryDAO进行查询
            return NewMusicCategoryDAO.getInstance()
                                      .queryNewMusicCategoryKeyBaseList(tid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回新音乐货架扩展字段列表时发生数据库异常！",e);
        }
    }
    
    /**
	 * 用于删除新音乐货架保存扩展字段
	 * 
	 * @param newMusicCategory
	 * @throws BOException
	 */
    public int delNewMusicCategoryKeyResource(TransactionDB tdb,String tid) throws BOException
    {
		if (logger.isDebugEnabled())
		{
			logger
					.debug("NewMusicCategoryBO.delNewMusicCategoryKeyResource() is start...");
		}
		int r = 0;
		try
		{
			// 调用NewMusicCategoryDAO进行新增新音乐货架
			r = NewMusicCategoryDAO.getInstance().delNewMusicCategoryKeyResource(tdb,tid);
		} catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("删除音乐货架扩展字段值时发生异常！", e);
		}
		return r;
	}
    /**
	 * 用于获取新音乐货架ID
	 * 
	 * @param newMusicCategory
	 * @throws BOException
	 */
    public String  getNewMusicCategoryId() throws BOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicCategoryBO.saveNewMusicCategory() is start...");
		}
		String cid = null;
		try
		{
			// 调用NewMusicCategoryDAO进行新增新音乐货架
			cid = NewMusicCategoryDAO.getInstance().getNewMusicCategoryId();
		} catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("新增新音乐货架时发生数据库异常！");
		}
		return cid;
	}

    
    /**
     * 
     *@desc 保存
     *@author dongke
     *Aug 9, 2011
     * @param keyBaseList
     * @throws BOException
     */
    public void saveKeyResource(List keyBaseList) throws BOException
	{
		for (int i = 0; i < keyBaseList.size(); i++)
		{
			ResourceVO vo = (ResourceVO) keyBaseList.get(i);
			try
			{
				if (vo != null && vo.getValue() != null && !vo.getValue().equals(""))
				{
					KeyResourceDAO.getInstance().updateResourceValue(vo.getKeyid(),
							vo.getValue(), vo.getTid());
				}
			} catch (DAOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BOException("保存扩展值失败", e);
			}
		}
	}
    
	/**
     * 用于删除指定货架
     * 
     * @return
     * @throws BOException
     */
    public void delMusicCategoryItem(String categoryId) throws BOException  
    {
    	 if (logger.isDebugEnabled())
         {
             logger.debug("NewMusicCategoryBO.delMusicCategoryItem(" + categoryId
                          + ") is start...");
         }

         try
         {
             // 调用NewMusicCategoryDAO进行删除
        	 NewMusicCategoryDAO.getInstance().delMusicCategoryItem(categoryId);
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
   			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：音乐货架管理;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：;<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式：" + "提交审批";
   			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：音乐货架管理;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 3) + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式：" + "提交审批";
   			map.put("mailContent", value);
   			map.put("status", "0");
   			map.put("categoryId", categoryId);
   			map.put("operation", "50");
   			map.put("operationtype", "音乐货架管理提交审批");
   			map.put("operationobj", "音乐货架管理");
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
