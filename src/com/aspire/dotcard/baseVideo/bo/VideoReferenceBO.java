package com.aspire.dotcard.baseVideo.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
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
import com.aspire.dotcard.baseVideo.dao.CollectionDAO;
import com.aspire.dotcard.baseVideo.dao.VideoReferenceDAO;
import com.aspire.dotcard.baseVideo.vo.CollectionResultVO;
import com.aspire.dotcard.baseVideo.vo.CollectionVO;
import com.aspire.dotcard.baseVideo.vo.ProgramVO;
import com.aspire.dotcard.baseVideo.vo.VideoRefVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.comic.dao.ReferenceDAO;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;

public class VideoReferenceBO
{
    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(VideoReferenceBO.class);

    private static VideoReferenceBO instance = new VideoReferenceBO();

    private VideoReferenceBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static VideoReferenceBO getInstance()
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
    public void queryVideoRefList(PageResult page, VideoRefVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("VideoReferenceBO.queryVideoRefList( ) is start...");
        }

        try
        {
            // 调用BookRefDAO进行查询
            VideoReferenceDAO.getInstance().queryVideoRefList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回视频货架列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于移除指定货架下指定的视频
     * 
     * @param categoryId 货架id
     * @param bookId 视频id列
     * @throws BOException
     */
    public void removeVideoRefs(String categoryId, String[] refId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("VideoReferenceBO.removeBookRefs( ) is start...");
        }

        try
        {
            // 调用BookRefDAO进行查询
            VideoReferenceDAO.getInstance()
                          .removeVideoRefs(categoryId, refId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回视频货架列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于设置视频货架下视频商品排序值
     * 
     * @param categoryId
     * @param setSortId 
     * @throws BOException
     */
    public void setVideoSort(String categoryId, String setSortId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("VideoReferenceBO.setVideoSort( ) is start...");
        }

        try
        {
            String[] sort = setSortId.split(";");
            // 调用BookRefDAO进行查询
            VideoReferenceDAO.getInstance().setVideoSort(categoryId, sort);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("设置视频货架下视频商品排序值时发生数据库异常！");
        }
    }
    
    /**
     * 用于查看指定货架中是否存在指定视频
     * 
     * @param categoryId 货架id
     * @param addVideoId 新视频id列
     * @throws BOException
     */
    public String isHasVideoRefs(String categoryId, String addVideoId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("VideoReferenceBO.isHasBookRefs( ) is start...");
        }

        try
        {
            String[] addVideoIds = addVideoId.split(";");
            
            String[] ids = new String[addVideoIds.length];
            
            String[] nodeIds = new String[addVideoIds.length];
            
            // 分离出id与name分开
            for (int i = 0; i < addVideoIds.length; i++)
            {
                String temp = addVideoIds[i];
                String[] temps = temp.split("_");
                ids[i] = temps[0];
                nodeIds[i] = temps[1];
            }
            
            // 调用BookRefDAO进行查询
            return VideoReferenceDAO.getInstance()
                          .isHasVideoRefs(categoryId, ids, nodeIds);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查看指定货架中是否存在指定视频时发生数据库异常！");
        }
    }
    
    /**
     * 用于添加指定的视频至货架中
     * 
     * @param categoryId 货架id
     * @param addVideoId 视频id列
     * @throws BOException
     */
    public void addVideoRefs(String categoryId, String addVideoId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("VideoReferenceBO.addBookRefs( ) is start...");
        }

        try
        {
            String[] base = VideoReferenceDAO.getInstance()
                                             .queryCategoryBase(categoryId);

            String[] addVideoIds = addVideoId.split(";");

            // 调用BookRefDAO进行新增
            VideoReferenceDAO.getInstance().addVideoRefs(categoryId,
                                                         base,
                                                         addVideoIds);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回视频货架列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于查询视频列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryProgramVOList(PageResult page, ProgramVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("VideoReferenceBO.queryProgramVOList( ) is start...");
        }

        try
        {
            // 调用BookRefDAO进行查询
            VideoReferenceDAO.getInstance().queryProgramVOList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询视频列表时发生数据库异常！");
        }
    }
    
    public void approvalCategoryGoods(String categoryId) throws BOException{
    	VideoReferenceDAO.getInstance().approvalCategoryGoods(categoryId);
    }
    
    
    public String importContentADD(FormFile dataFile, String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("VideoReferenceBO.importContentADD...");
        }
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
            	String nodeId = (String)m.get(2);
            	try {
            		VideoReferenceDAO.getInstance().addReference(categoryId, contentId.trim(),nodeId.trim(),(-1*Integer.parseInt(sortId))+"");
    				count++;
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				//e.printStackTrace();
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

    
    /**
     * 文件批量导入视频商品上架
     * 
     * @param dataFile
     * @param categoryId
     * @throws BOException
     */
    public String importContentALL(FormFile dataFile, String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("VideoReferenceBO.importContentExigence( ) is start...");
        }

        StringBuffer ret = new StringBuffer();
        int errorN = 0;
        try
        {
            // 用于清空原货架下视频
            VideoReferenceDAO.getInstance().delVideoRef(categoryId);
            
//            // 解析EXECL文件，获取终端软件版本列表
//            List list = this.paraseDataFile(dataFile);
            
            // 解析EXECL文件，获取终端软件版本列表
            List list = new ExcelHandle().paraseDataFile(dataFile,null);
            
            StringBuffer temp = new StringBuffer();
            int count = 0;
            for(int i =0;i<list.size();i++){
            	Map m = (Map)list.get(i);
            	String sortId = (String)m.get(0);
            	String contentId = (String)m.get(1);
            	String nodeId = (String)m.get(2);
            	try {
            		VideoReferenceDAO.getInstance().addReference(categoryId, contentId.trim(),nodeId.trim(), (-1*Integer.parseInt(sortId))+"");
    				count++;
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				//e.printStackTrace();
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

//            // 校驗文件中數據是否在视频表中存在
//            String temp = VideoReferenceDAO.getInstance()
//                                            .verifyVideo(list);
//
//            String[] ids = new String[list.size()];
//            
//            for (int i = 0; i < list.size(); i++)
//            {
//                ids[i] = (String)list.get(i);
//            }
//            
//            // 调用BookRefDAO进行新增
//            VideoReferenceDAO.getInstance()
//                          .addVideoRefByNotName(categoryId, ids);
//
//            ret.append("成功导入" + list.size() + "条记录.");
//
//            if (!"".equals(temp))
//            {
//                ret.append("导入不成功id为").append(temp);
//            }

//            return ret.toString();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("导入紧急上线的内容列表时发生数据库异常！");
        }
    }
    
    
 
    /**
     * 解析EXECL文件，获取要添加的商品信息
     * 
     * @param dataFile
     * @return
     * @throws BiffException
     * @throws IOException
     */
    public List paraseDataFile(FormFile dataFile) throws BOException
    {
        logger.info("VideoReferenceBO.paraseDataFile() is start!");
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
                int columns = 0;

                for (int j = 0; j < rows; j++)
                {
                    String value = sheets[i].getCell(columns, j)
                                            .getContents()
                                            .trim();
                    // 不為空時
                    if (!"".equals(value))
                    {
                        // 如果集合中存在此数据
                        if (list.contains(value))
                        {
                            // 删除原来存在的数据
                            list.remove(value);
                        }
                        
                        list.add(value);
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
     * 用于查询视频详情
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public ProgramVO queryVideoInfo(String videoId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("VideoReferenceBO.queryVideoInfo( ) is start...");
        }
        ProgramVO vo = null;
        try
        {
            // 调用VideoReferenceDAO进行查询
            vo =  VideoReferenceDAO.getInstance().queryVideoInfo(videoId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询视频详情时发生数据库异常！");
        }
        
        return vo;
    }
    
    /**
     * 用于查询视频扩展字段详情
     * 
     * @param videoId
     * @throws BOException
     */
    public List queryVideoKeyResource(String videoId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("VideoReferenceBO.queryVideoKeyResource( ) is start...");
        }
        List list = null;
        try
        {
            // 调用VideoReferenceDAO进行查询
        	list =  VideoReferenceDAO.getInstance().queryVideoKeyResource(videoId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询视频扩展字段时发生数据库异常！");
        }
        return list;
    }
    /**
	 * 用于导出视频内容集数据
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportVideoReferenceData(HttpServletRequest request,
			WritableWorkbook wwb, boolean isAll) throws BOException
	{
		List<VideoRefVO> list = null;
		
		try
		{
			if (isAll)
			{
		        String categoryId = request.getParameter("categoryId") == null ? "" :request.getParameter("categoryId").trim();

			    VideoRefVO vo = new VideoRefVO();
		        //vo.setRefId(refId);
		        vo.setCategoryId(categoryId);
		        vo.setProgramId("");
		        vo.setProgramName("");
		        vo.setVideoId("");
		        vo.setNodeId("");
		        vo.setVerify_status("1");
			
				list = VideoReferenceDAO.getInstance().queryVideoReferenceListByExport(vo);
			}
			else
			{
		        
		        // 从请求中获取货架内码
		        //String refId = request.getParameter("refId") == null ? "" :request.getParameter("refId").trim();
		        String categoryId = request.getParameter("categoryId") == null ? "" :request.getParameter("categoryId").trim();
		        String programId = request.getParameter("programId") == null ? "" :request.getParameter("programId").trim();
		        String programName = request.getParameter("programName") == null ? "" :request.getParameter("programName").trim();
		        String queryNodeId = request.getParameter("queryNodeId") == null ? "" :request.getParameter("queryNodeId").trim();
		        String videoId = request.getParameter("videoId") == null ? "" :request.getParameter("videoId").trim();


		        VideoRefVO vo = new VideoRefVO();
		        vo.setCategoryId(categoryId);
		        vo.setProgramId(programId);
		        vo.setProgramName(programName);
		        vo.setVideoId(videoId);
		        vo.setNodeId(queryNodeId);
		        vo.setVerify_status("1");
		        
				list = VideoReferenceDAO.getInstance().queryVideoReferenceListByExport(vo);
			}
		}
		catch (DAOException e)
		{
			logger.error("于导出视频商品信息数据时发生数据库异常：", e);
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
	private void generateQueryDataExcel(List<VideoRefVO> list,
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
				
				VideoRefVO vo = (VideoRefVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getProgramId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getProgramName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getNodeId());
				ws.addCell(label);
			    label = new Label(col++, row, vo.getVideoId());
			    ws.addCell(label);		
                label = new Label(col++, row, vo.getFullName());
				ws.addCell(label);	
				label = new Label(col++, row, vo.getIsLink());
				ws.addCell(label);	
				label = new Label(col++, row, vo.getLastUpTime_Y()+""+vo.getLastUpTime_M()+""+vo.getLastUpTime_D());
				ws.addCell(label);
                label = new Label(col++, row, String.valueOf(vo.getSortId()));
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
		
		label = new Label(col++, row, "节目编号");
		ws.addCell(label);
		label = new Label(col++, row, "节目名称");
		ws.addCell(label);
		label = new Label(col++, row, "栏目编码");
		ws.addCell(label);
		label = new Label(col++, row, "内容编码");
		ws.addCell(label);
		label = new Label(col++, row, "栏目路径");
		ws.addCell(label);
		label = new Label(col++, row, "是否链接");
		ws.addCell(label);
		label = new Label(col++, row, "最后修改时间");
		ws.addCell(label);
		label = new Label(col++, row, "排序序号");
		ws.addCell(label);
	}

}
