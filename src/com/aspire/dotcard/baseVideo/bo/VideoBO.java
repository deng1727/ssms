package com.aspire.dotcard.baseVideo.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.WriteActionLogAndEmailTask;
import com.aspire.dotcard.baseVideo.dao.CategoryDAO;
import com.aspire.dotcard.baseVideo.dao.ReferenceDAO;
import com.aspire.dotcard.baseVideo.dao.VideoDAO;
import com.aspire.dotcard.baseVideo.vo.VideoCategoryVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;

public class VideoBO
{
	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(VideoBO.class);

	private static VideoBO instance = new VideoBO();

	private VideoBO()
	{}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static VideoBO getInstance()
	{
		return instance;
	}

	/**
	 * 用于返回视频货架列表
	 * 
	 * @return
	 * @throws BOException
	 */
	public List queryVideoCategoryList() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.queryVideoCategoryList( ) is start...");
		}

		try
		{
			// 调用VideoDAO进行查询
			return VideoDAO.getInstance().queryVideoCategoryList();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回视频货架列表时发生数据库异常！");
		}
	}

	/**
	 * 用于返回视频产品列表
	 * 
	 * @return
	 * @throws BOException
	 */
	public List queryVideoProductList() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.queryVideoProductList( ) is start...");
		}

		try
		{
			// 调用VideoDAO进行查询
			return VideoDAO.getInstance().queryVideoProductList();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回视频产品列表时发生数据库异常！");
		}
	}

	/**
	 * 用于返回视频货架信息
	 * 
	 * @return
	 * @throws BOException
	 */
	public VideoCategoryVO queryVideoCategoryVO(String videoCategoryId)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.queryVideoCategoryVO(" + videoCategoryId
					+ ") is start...");
		}

		try
		{
			// 调用NewMusicCategoryDAO进行查询
			return VideoDAO.getInstance().queryVideoCategoryVO(videoCategoryId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回视频架信息时发生数据库异常！");
		}
	}

	/**
	 * 返回当前货架的子货架信息新音乐货架信息
	 * 
	 * @return
	 * @throws BOException
	 */
	public int hasChild(String videoCategoryId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.hasChild(" + videoCategoryId
					+ ") is start...");
		}

		try
		{
			// 调用NewMusicCategoryDAO进行查询
			return VideoDAO.getInstance().hasChild(videoCategoryId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回当前货架的子货架信息新音乐货架信息时发生数据库异常！");
		}
	}

	/**
	 * 查看当前货架下是否还存在着商品
	 * 
	 * @param categoryId
	 * @return
	 * @throws BOException
	 */
	public int hasVideo(String videoCategoryId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.hasVideo(" + videoCategoryId
					+ ") is start...");
		}

		try
		{
			// 调用NewMusicRefDAO进行查询
			return ReferenceDAO.getInstance().hasVideo(videoCategoryId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查看当前货架下是否还存在着商品时发生数据库异常！");
		}
	}

	/**
	 * 用于获取视频货架ID
	 * 
	 * @param newMusicCategory
	 * @throws BOException
	 */
	public String getVideoCategoryId() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.getVideoCategoryId() is start...");
		}
		String cid = null;
		try
		{
			// 调用NewMusicCategoryDAO进行新增新音乐货架
			cid = VideoDAO.getInstance().getVideoCategoryId();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("获取视频货架ID时发生数据库异常！");
		}
		return cid;
	}

	/**
	 * 用于新增视频货架
	 * 
	 * @param videoCategoryVO
	 * @throws BOException
	 */
	public void saveVideoCategory(VideoCategoryVO videoCategoryVO)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.saveVideoCategory() is start...");
		}

		try
		{
			// 调用NewMusicCategoryDAO进行新增新音乐货架
			VideoDAO.getInstance().saveVideoCategory(videoCategoryVO);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("新增视频货架时发生数据库异常！");
		}
	}

	/**
	 * 用于变更视频货架
	 * 
	 * @param videoCategoryVO
	 * @throws BOException
	 */
	public void updateVideoCategory(VideoCategoryVO videoCategoryVO)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO..updateNewMusicCategory() is start...");
		}

		try
		{
			// 调用NewMusicCategoryDAO进行变更新音乐货架
			VideoDAO.getInstance().updateVideoCategory(videoCategoryVO);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("变更视频货架时发生数据库异常！");
		}
	}

	public List updateProductFeeDescs(List updateinfo) throws BOException
	{
		List list = new ArrayList();
		// List updateinfo = paraseDataFile(dataFile);
		if (updateinfo != null)
		{
			for (int i = 0; i < updateinfo.size(); i++)
			{
				String[] st = (String[]) updateinfo.get(i);
				int r = 0;
				if (st != null && st.length == 2)
				{
					try
					{
						r = VideoDAO.getInstance().updateProductFeeDesc(st[0],
								st[1]);
					}
					catch (DAOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				if (r == 0)
				{
					list.add(st[0]);
				}
			}
		}

		return list;
	}

	/**
	 * 解析EXECL文件，获取要添加的商品信息
	 * 
	 * @param in
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	public List paraseDataFile(FormFile dataFile) throws BOException

	{
		logger.info("VideoBO.paraseDataFile() is start!");
		List list = new ArrayList();
		Workbook book = null;
		try
		{
			book = Workbook.getWorkbook(dataFile.getInputStream());
			Sheet[] sheets = book.getSheets();
			int sheetNum = sheets.length;
			if (logger.isDebugEnabled())
			{
				logger.debug("paraseSoftVersion.sheetNum==" + sheetNum);
			}

			for (int i = 0; i < sheetNum; i++)
			{
				int rows = sheets[i].getRows();
				// int columns = sheets[i].getColumns();
				// 为了提高效率，支取前两列的值
				// int columns = 2;  del by wml 12.9.10
				for (int j = 0; j < rows; j++)
				{
					// for (int k = 0; k < columns; k++)
					// {
					String productId = sheets[i].getCell(0, j).getContents()
							.trim();
					String feeDesc = sheets[i].getCell(1, j).getContents()
							.trim();
					// switch (k + 1)
					// {
					// case 1:
					// //第一列
					// break;
					// case 2:
					// //第二列

					// break;
					// }
					if (productId != null && !productId.trim().equals(""))
					{ // &&
						// feeDesc != null &&! feeDesc.trim().equals("") ){
						// 非空，才能进入,值为空，则删除或清空
						String[] ps =
						{ productId, feeDesc };
						list.add(ps);
					}
				}
			}

		}
		catch (Exception e)
		{
			logger.error("解析导入文件出现异常,fineName:" + dataFile.getFileName(), e);
			throw new BOException("解析导入文件出现异常", e);
		}

		finally
		{
			book.close();
		}
		return list;
	}

	/**
	 * 根据名称、提供商等字段查询应用活动。
	 * 
	 * @param page
	 * @param contentID
	 *            内容ID
	 * @throws BOException
	 */
	public void queryVideoNodeExtList(PageResult page, String nodeId,
			String name) throws BOException
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("queryVideoNodeExtList(nodeId=" + nodeId + " name="
					+ name + ")");
		}
		if (page == null)
		{
			throw new BOException("invalid para , page is null");
		}
		try
		{
			VideoDAO.getInstance().queryVideoNodeExtList(page, nodeId, name);
		}
		catch (DAOException e)
		{
			logger.error("queryVideoNodeExtList...", e);
			throw new BOException("queryVideoNodeExtList", e);
		}
	}

	public List updateVideoNodeExts(List updateinfo) throws BOException
	{
		List list = new ArrayList();
		// List updateinfo = paraseDataFile(dataFile);
		if (updateinfo != null)
		{
			for (int i = 0; i < updateinfo.size(); i++)
			{
				String[] st = (String[]) updateinfo.get(i);
				int r = 0;
				if (st != null && st.length == 2)
				{
					try
					{
						r = VideoDAO.getInstance().updateVideoNodeExt(st[0],
								st[1]);
					}
					catch (DAOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				if (r == 0)
				{
					list.add(st[0]);
				}
			}
		}

		return list;
	}
	
    /**
     * 用于返回视频货架扩展字段列表
     * 
     * @param tid
     * @return
     * @throws BOException
     */
    public List queryVideoCategoryKeyBaseList(String tid) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryVideoCategoryKeyBaseList( ) is start...");
        }

        try
        {
            // 调用VideoDAO进行查询
            return VideoDAO.getInstance()
                                      .queryVideoCategoryKeyBaseList(tid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回视频货架扩展字段列表时发生数据库异常！",e);
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
			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：视频货架管理;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：;<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式：" + "提交审批";
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：视频货架管理;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 2) + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式：" + "提交审批";
			map.put("mailContent", value);
			map.put("status", "0");
			map.put("categoryId", categoryId);
			map.put("operation", "60");
			map.put("operationtype", "视频货架管理提交审批");
			map.put("operationobj", "视频货架管理");
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
	
	
	/**
	 * 根据货架Id更新视频货架删除状态
	 * 
	 * @param categoryId
	 * @throws BOException
	 */
	public void updateVideoCategoryDelStatus(String videoCategoryId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("VideoBO.updateVideoCategoryDelStatus(" + videoCategoryId
					+ ") is start...");
		}

		try
		{
			VideoDAO.getInstance().updateVideoCategoryDelStatus(videoCategoryId);
		}
		catch (DAOException e)
		{
			logger.error("根据货架Id更新视频货架删除状态时发生异常:",e);
			throw new BOException("根据货架Id更新视频货架删除状态时发生异常:",e);
		}
	}
}
