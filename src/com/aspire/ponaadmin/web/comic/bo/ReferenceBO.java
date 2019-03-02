package com.aspire.ponaadmin.web.comic.bo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.dao.VideoReferenceDAO;
import com.aspire.dotcard.baseVideo.vo.VideoRefVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.book.bo.BookRefBO;
import com.aspire.ponaadmin.web.comic.dao.ReferenceDAO;
import com.aspire.ponaadmin.web.comic.vo.ComicChapterVO;
import com.aspire.ponaadmin.web.comic.vo.ReferenceVO;
import com.aspire.ponaadmin.web.datafield.dao.KeyResourceDAO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;
import com.aspire.ponaadmin.web.repository.web.ExcelRowConvert;

public class ReferenceBO
{
    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(ReferenceBO.class);

    private static ReferenceBO instance = new ReferenceBO();

    private ReferenceBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static ReferenceBO getInstance()
    {
        return instance;
    }

    /**
     * 用于查询当前货架下商品列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryReferenceList(PageResult page, ReferenceVO vo)
                    throws BOException
    {
        try
        {
            ReferenceDAO.getInstance().queryReferenceList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("发生数据库异常！");
        }
    }

    public void removeReference(String categoryId, String[] id)
                    throws BOException
    {
        try
        {
            ReferenceDAO.getInstance()
                          .removeReference(categoryId, id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回货架列表时发生数据库异常！");
        }
    }

    public String isHasReference(String categoryId, String contentId)
                    throws BOException
    {
        try
        {
            String[] contentIdArray = contentId.split(";");
            return ReferenceDAO.getInstance()
                          .isHasReference(categoryId, contentIdArray);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查看指定货架中是否存在指定内容ID时发生数据库异常！");
        }
    }
    

    public void addReference(String categoryId, String contentId)
                    throws BOException
    {
       try
        {
            String[] contentIdArray = contentId.split(";");
            ReferenceDAO.getInstance()
                          .addReference(categoryId, contentIdArray);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回货架列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于查询新音乐列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryList(PageResult page, ReferenceVO vo)
                    throws BOException
    {
        try
        {
            ReferenceDAO.getInstance().queryList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询新音乐列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于查询章节列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryChapterList(PageResult page, ComicChapterVO vo)
                    throws BOException
    {
        try
        {
            ReferenceDAO.getInstance().queryChapterList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询动漫章节列表时发生数据库异常！");
        }
    }
    /**
     * 用于设置新音乐货架下音乐商品排序值
     * 
     * @param categoryId
     * @param setSortId 
     * @throws BOException
     */
    public void setSort(String categoryId, String setSortId)
                    throws BOException
    {
        try
        {
            String[] sort = setSortId.split(";");
            // 调用NewMusicRefDAO进行查询
            ReferenceDAO.getInstance().setSort(categoryId, sort);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("设置新音乐货架下音乐商品排序值时发生数据库异常！");
        }
    }
    
    public String importContentADD(FormFile dataFile, String categoryId) throws BOException
    {
        StringBuffer ret = new StringBuffer();
        int errorN = 0;
        // 解析EXECL文件，获取终端软件版本列表
        List list = new ExcelHandle().paraseDataFile(dataFile,null);
        StringBuffer temp = new StringBuffer();
        int count = 0;
        for(int i =0;i<list.size();i++){
        	Map m = (Map)list.get(i);
        	String sortId = (String)m.get(0);
        	String contentId = (String)m.get(1);
        	try {
				ReferenceDAO.getInstance().addReference(categoryId, contentId.trim(), (-1*Integer.parseInt(sortId))+"");
				count++;
			} catch (Exception e) {
				errorN++;
				if(errorN % 10 == 0)
				{
					temp.append("<br>").append(contentId);
				}
				else
				{
					temp.append(",").append(contentId);
				}
			}
        	
        	
        }
        TransactionDB tdb = null;
        try {
           tdb = TransactionDB.getInstance();
           ReferenceDAO.getInstance().setCategoryGoodsApproval(tdb, categoryId);
           tdb.commit();
        }
        catch (DAOException e)
        {
          logger.error("添加指定的内容至货架中时发生异常:", e);
            // 执行回滚
            tdb.rollback();
            throw new BOException("添加指定的内容至货架中时发生异常:", e);
        } finally {
            if (tdb != null) {
                tdb.close();
              }
        }
        ret.append("成功导入" + count + "条记录.");
        if (temp.length()!=0)
      {
          ret.append("导入不成功id为").append(temp);
      }
        return ret.toString();
        }
    
    
    public String importContentALL(FormFile dataFile, String categoryId) throws BOException
    {
        StringBuffer ret = new StringBuffer();
        int errorN = 0;
        try
        {
            // 用于清空原货架下新音乐
            ReferenceDAO.getInstance().delReference(categoryId);

//            // 解析EXECL文件，获取终端软件版本列表
//            List list = BookRefBO.getInstance().paraseDataFile(dataFile);
         // 解析EXECL文件，获取终端软件版本列表
            List list = new ExcelHandle().paraseDataFile(dataFile,null);
            
            StringBuffer temp = new StringBuffer();
            int count = 0;
            for(int i =0;i<list.size();i++){
            	Map m = (Map)list.get(i);
            	String sortId = (String)m.get(0);
            	String contentId = (String)m.get(1);
            	try {
    				ReferenceDAO.getInstance().addReference(categoryId, contentId.trim(), (-1*Integer.parseInt(sortId))+"");
    				count++;
    			} catch (Exception e) {
    				errorN++;
    				if(errorN % 10 == 0)
    				{
    					temp.append("<br>").append(contentId);
    				}
    				else
    				{
    					temp.append(",").append(contentId);
    				}
    			}
            	
            	
            }
            ret.append("成功导入" + count + "条记录.");
          if (temp.length()!=0)
          {
              ret.append("导入不成功id为").append(temp);
          }
            return ret.toString();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("批量导入列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于查询新音乐详情
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public NewMusicRefVO queryNewMusicInfo(String musicId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicRefBO.queryNewMusicInfo( ) is start...");
        }
        NewMusicRefVO vo = null;
        try
        {
            // 调用NewMusicRefDAO进行查询
        	vo =  NewMusicRefDAO.getInstance().queryNewMusicInfo(musicId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询新音乐列表时发生数据库异常！");
        }
        return vo;
    }
    /**
     * 用于查询新音乐详情
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public List queryNewMusicKeyResource(String musicId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicRefBO.queryNewMusicKeyResource( ) is start...");
        }
        List list = null;
        try
        {
            // 调用NewMusicRefDAO进行查询
        	list =  NewMusicRefDAO.getInstance().queryNewMusicKeyResource(musicId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询新音乐扩展字段时发生数据库异常！");
        }
        return list;
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
	 * 用于导出动漫商品数据
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportComicReferenceData(HttpServletRequest request,
			WritableWorkbook wwb, boolean isAll) throws BOException
	{
		List<ReferenceVO> list = null;
		
		try
		{
			if (isAll)
			{
		    	String categoryId = request.getParameter("categoryId")==null?"":request.getParameter("categoryId");

		        ReferenceVO vo = new ReferenceVO();
		        vo.setCategoryId(categoryId);
		        vo.setContentId("");
		        vo.setContentName("");
			
				list = ReferenceDAO.getInstance().queryComicReferenceListByExport(vo);
			}
			else
			{
		        
		        // 从请求中获取货架内码
		    	String categoryId = request.getParameter("categoryId")==null?"":request.getParameter("categoryId");
		    	String contentId = request.getParameter("contentId")==null?"":request.getParameter("contentId");
		    	String contentName = request.getParameter("contentName")==null?"":request.getParameter("contentName");


		        ReferenceVO vo = new ReferenceVO();
		        vo.setCategoryId(categoryId);
		        vo.setContentId(contentId);
		        vo.setContentName(contentName);
		        
		        
				list = ReferenceDAO.getInstance().queryComicReferenceListByExport(vo);
			}
		}
		catch (DAOException e)
		{
			logger.error("于导出动漫商品信息数据时发生数据库异常：", e);
		}
		
		generateQueryDataExcel(list, wwb);
	}
	
	/**
	 * 生成应用更新版本的excel
	 * 
	 * @param list
	 * @param wwb
	 * @param sheetName
	 */
	private void generateQueryDataExcel(List<ReferenceVO> list,
			WritableWorkbook wwb)
	{
		String sheetName = "Sheet";
		WritableSheet ws = wwb.createSheet(sheetName, 0); // 创建sheet
		Label label = null;
		int row = 0;
		int col = 0;
		int sheet = 0;
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				if (i % 65533 == 0)
				{
					ws = wwb.createSheet(sheetName + sheet, sheet++);
					setTitle(ws);
					row = 0;
				}
				
				ReferenceVO vo = (ReferenceVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getContentId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getContentName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getFlowTime());
				ws.addCell(label);
			    label = new Label(col++, row, vo.getType());
				ws.addCell(label);	
				if("1".equals(vo.getPortal())){
				label = new Label(col++, row, "客户端");
				ws.addCell(label);
				}else if("2".equals(vo.getPortal())){
					label = new Label(col++, row, "WAP门户");
					ws.addCell(label);
			   }else if("3".equals(vo.getPortal())){
					label = new Label(col++, row, "所有");
					ws.addCell(label);
				}else{
					label = new Label(col++, row, "未知");
					ws.addCell(label);
				}
                label = new Label(col++, row, vo.getSortId());
				ws.addCell(label);

			}
		}
		catch (RowsExceededException e)
		{
			e.printStackTrace();
		}
		catch (WriteException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void setTitle(WritableSheet ws) throws RowsExceededException,
			WriteException
	{
		int row = 0;
		int col = 0;
		Label label = null;
		
		label = new Label(col++, row, "内容id");
		ws.addCell(label);
		label = new Label(col++, row, "内容名称");
		ws.addCell(label);
		label = new Label(col++, row, "入库时间");
		ws.addCell(label);
		label = new Label(col++, row, "类型");
		ws.addCell(label);
		label = new Label(col++, row, "门户");
		ws.addCell(label);
		label = new Label(col++, row, "排序序号");
		ws.addCell(label);
	}
	

	/**
	 * 编辑动漫商品货架
	 * 
	 * @param categoryId 动漫货架编码
	 * @throws DAOException
	 */
	public void approvalCategoryGoods(String categoryId)
			throws BOException {
		ReferenceDAO.getInstance().approvalCategoryGoods(categoryId);
	}

}
