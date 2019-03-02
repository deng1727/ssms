package com.aspire.dotcard.baseVideo.bo;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.aspire.dotcard.baseVideo.dao.BlackDAO;
import com.aspire.dotcard.baseVideo.dao.CollectionDAO;
import com.aspire.dotcard.baseVideo.vo.BlackVO;
import com.aspire.dotcard.baseVideo.vo.CollectionResultVO;
import com.aspire.dotcard.baseVideo.vo.CollectionVO;
import com.aspire.dotcard.baseVideo.vo.ProgramBlackVO;
import com.aspire.dotcard.baseVideoNew.dao.BaseVideoDeleteBlackDAO;
import com.aspire.dotcard.basemusic.dao.BaseMusicPicDAO;
import com.aspire.dotcard.basemusic.vo.BaseMusicVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;

/**
 * 视频内容集业务类
 */
public class CollectionBO
{
    /**
     * 日志引用
     */
    protected static final JLogger LOG = LoggerFactory.getLogger(CollectionBO.class);

    private static CollectionBO instance = new CollectionBO();

    public CollectionBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CollectionBO getInstance()
    {
        return instance;
    }

    /**
     * 用于查询视频内容集列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryCollectionList(PageResult page, CollectionVO vo) throws BOException
    {
        try
        {
        	CollectionDAO.getInstance().queryCollectionList(page, vo);
        }
        catch (DAOException e)
        {
        	e.printStackTrace();
            LOG.error("视频内容集发生数据库异常!", e);
            throw new BOException("视频内容集发生数据库异常!");
        }
    }

    @SuppressWarnings("unchecked")
    public List getImportCollectionParaseDataList(FormFile dataFile) throws BOException
    {
        List list = new ArrayList();
        try
        {
        	
            list = new ExcelHandle().paraseDataFile(dataFile, null);
        }

        catch (BOException e)
        {
            LOG.error("视频三级节点导入,Excel解析异常!", e);
            throw new BOException("视频三级节点导入,Excel解析异常!");
        }
        return list;
    }


    /**
     * 添加黑名单
     * 
     * @param contentIdArray
     *            内容ID
     * @return 添加结果信息
     * @throws BOException
     */
    public String addImportCollection(List arrlist) throws BOException
    {
        String result = "";
//        if (arrlist == null || (arrlist != null && arrlist.get("grogramid") == 0))
      if (arrlist.size() == 0 )

        {
            LOG.warn("视频List为空!");
            result = "增量导入视频三级节点0条记录";
            return result;
        }
        try
        {
        	result = CollectionDAO.getInstance().addImCollection(arrlist);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            LOG.error("视频三级节点增加操作发生数据库异常!", e);
            throw new BOException("视频三级节点增加操作发生数据库异常!");
        }
        return result;
    }
    /**
     * 增量操作：解析excel导入到数据库表，如果数据库已经存在，不入库
     * 
     * @param dataFile
     *            数据文件对象
     * @return 导入结果信息
     * @throws BOException
     * @throws BOException
     */
    @SuppressWarnings("unchecked")
    public String importCollectionADD(List arrList) throws BOException
    {
        String result = null;
        try
        {
            result = addImportCollection(arrList);
        }
        catch (BOException e)
        {
            LOG.error("视频三级节点增量导入,入库异常!", e);
            throw new BOException("视频三级节点增量导入,入库异常!");
        }
        return result;
    }

	/**
	 * 用于导出视频内容集数据
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportCollectionData(HttpServletRequest request,
			WritableWorkbook wwb, boolean isAll) throws BOException
	{
		List<CollectionResultVO> list = null;
		
		try
		{
			if (isAll)
			{
				CollectionVO vo = new CollectionVO();
				vo.setCollectionId("");
				vo.setNodeName("");
				vo.setParentNodeId("");
			
				list = CollectionDAO.getInstance().queryCollectionListByExport(vo);
			}
			else
			{
		         String collectionId= request.getParameter("collectionId") == null ? "" : request.getParameter("collectionId");
		         String parentNodeId= request.getParameter("parentNodeId") == null ? "" : request.getParameter("parentNodeId");
		         String nodeName= request.getParameter("nodeName") == null ? "" : request.getParameter("nodeName");

		        CollectionVO vo = new CollectionVO();
		        vo.setCollectionId(collectionId);
		        vo.setNodeName(nodeName);
		        vo.setParentNodeId(parentNodeId);
				list = CollectionDAO.getInstance().queryCollectionListByExport(vo);
			}
		}
		catch (DAOException e)
		{
			LOG.error("于导出视频三级节点数据时发生数据库异常：", e);
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
	private void generateQueryDataExcel(List<CollectionResultVO> list,
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
				
				CollectionResultVO vo = (CollectionResultVO) list.get(i);
				col = 0;
				row++;
				
				label = new Label(col++, row, vo.getCollectionId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getParentNodeId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getNodeName());
				ws.addCell(label);
				if("1".equals(vo.getIsShow())){
					label = new Label(col++, row, "是");
					ws.addCell(label);		
				}else{
					label = new Label(col++, row, "否");
					ws.addCell(label);	
				}

				
				label = new Label(col++, row, vo.getReName());
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
		
		label = new Label(col++, row, "节点ID");
		ws.addCell(label);
		label = new Label(col++, row, "父节点ID");
		ws.addCell(label);
		label = new Label(col++, row, "节点名称");
		ws.addCell(label);
		label = new Label(col++, row, "是否显示");
		ws.addCell(label);
		label = new Label(col++, row, "重命名");
		ws.addCell(label);
	}

    @SuppressWarnings("unchecked")
    public HashMap<String,String[]> getImportBalckParaseDataList(FormFile dataFile) throws BOException
    {
        List list = null;
        HashMap<String,String[]> map = new HashMap<String,String[]>();
        try
        {
        	
            list = new ExcelHandle().paraseDataFile(dataFile, null);
        }

        catch (BOException e)
        {
            LOG.error("视频视频内容集导入,Excel解析异常!", e);
            throw new BOException("视频内容集导入,Excel解析异常!");
        }

        String[] arrList = new String[list.size()];
        String[] arrList2 = new String[list.size()];
        if (list == null || (list != null && list.size() <= 0))
        {
            LOG.warn("视频内容集导入excel解析List为空!");
            map.put("programid", arrList);
            map.put("nodeid", arrList2);
            return map;
        }


        for (int i = 0; i < list.size(); i++)
        {
            Map m = (Map)list.get(i);
            arrList[i] = (String)m.get(0);
            arrList2[i] = (String)m.get(1);
            
            m = null;
        }
        list = null;
        map.put("programid", arrList);
        map.put("nodeid", arrList2);

        return map;
    }
	public boolean updateCollections()
	{
		String  sql1 = "collection.CollectionDAO.updateIshowCollections.update";
		String  sql2 = "collection.CollectionDAO.updateNodebynameCollections.update";
		String  sql3 = "collection.CollectionDAO.updateCollectnameCollections.update";

		boolean isTrue = true;
		
		try
		{
			CollectionDAO.getInstance().updateCollections(sql1, null);
			CollectionDAO.getInstance().updateCollections(sql2, null);
			CollectionDAO.getInstance().updateCollections(sql3, null);

		}
		catch (BOException e)
		{
			isTrue = false;
			LOG.debug("执行删除视频内容表时发生错误！！！", e);
		}
		return isTrue;
	}
}
