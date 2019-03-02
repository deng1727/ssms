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
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(VideoReferenceBO.class);

    private static VideoReferenceBO instance = new VideoReferenceBO();

    private VideoReferenceBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static VideoReferenceBO getInstance()
    {
        return instance;
    }
    
    /**
     * ���ڲ�ѯ��ǰ��������Ʒ�б�
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
            // ����BookRefDAO���в�ѯ
            VideoReferenceDAO.getInstance().queryVideoRefList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("������Ƶ�����б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �����Ƴ�ָ��������ָ������Ƶ
     * 
     * @param categoryId ����id
     * @param bookId ��Ƶid��
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
            // ����BookRefDAO���в�ѯ
            VideoReferenceDAO.getInstance()
                          .removeVideoRefs(categoryId, refId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("������Ƶ�����б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ����������Ƶ��������Ƶ��Ʒ����ֵ
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
            // ����BookRefDAO���в�ѯ
            VideoReferenceDAO.getInstance().setVideoSort(categoryId, sort);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("������Ƶ��������Ƶ��Ʒ����ֵʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڲ鿴ָ���������Ƿ����ָ����Ƶ
     * 
     * @param categoryId ����id
     * @param addVideoId ����Ƶid��
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
            
            // �����id��name�ֿ�
            for (int i = 0; i < addVideoIds.length; i++)
            {
                String temp = addVideoIds[i];
                String[] temps = temp.split("_");
                ids[i] = temps[0];
                nodeIds[i] = temps[1];
            }
            
            // ����BookRefDAO���в�ѯ
            return VideoReferenceDAO.getInstance()
                          .isHasVideoRefs(categoryId, ids, nodeIds);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�鿴ָ���������Ƿ����ָ����Ƶʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �������ָ������Ƶ��������
     * 
     * @param categoryId ����id
     * @param addVideoId ��Ƶid��
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

            // ����BookRefDAO��������
            VideoReferenceDAO.getInstance().addVideoRefs(categoryId,
                                                         base,
                                                         addVideoIds);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("������Ƶ�����б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڲ�ѯ��Ƶ�б�
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
            // ����BookRefDAO���в�ѯ
            VideoReferenceDAO.getInstance().queryProgramVOList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ��Ƶ�б�ʱ�������ݿ��쳣��");
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
            // ����EXECL�ļ�����ȡ�ն�����汾�б�
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
            ret.append("�ɹ�����" + count + "����¼.");
          if (temp.length()!=0)
          {
              ret.append("���벻�ɹ�idΪ").append(temp);
          }
            return ret.toString();
        }

    
    /**
     * �ļ�����������Ƶ��Ʒ�ϼ�
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
            // �������ԭ��������Ƶ
            VideoReferenceDAO.getInstance().delVideoRef(categoryId);
            
//            // ����EXECL�ļ�����ȡ�ն�����汾�б�
//            List list = this.paraseDataFile(dataFile);
            
            // ����EXECL�ļ�����ȡ�ն�����汾�б�
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
            ret.append("�ɹ�����" + count + "����¼.");
          if (temp.length()!=0)
          {
              ret.append("���벻�ɹ�idΪ").append(temp);
          }
            return ret.toString();

//            // У��ļ��Д����Ƿ�����Ƶ���д���
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
//            // ����BookRefDAO��������
//            VideoReferenceDAO.getInstance()
//                          .addVideoRefByNotName(categoryId, ids);
//
//            ret.append("�ɹ�����" + list.size() + "����¼.");
//
//            if (!"".equals(temp))
//            {
//                ret.append("���벻�ɹ�idΪ").append(temp);
//            }

//            return ret.toString();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����������ߵ������б�ʱ�������ݿ��쳣��");
        }
    }
    
    
 
    /**
     * ����EXECL�ļ�����ȡҪ��ӵ���Ʒ��Ϣ
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
                    // ����Օr
                    if (!"".equals(value))
                    {
                        // ��������д��ڴ�����
                        if (list.contains(value))
                        {
                            // ɾ��ԭ�����ڵ�����
                            list.remove(value);
                        }
                        
                        list.add(value);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("���������ļ������쳣,fineName:" + dataFile.getFileName(), e);
            throw new BOException("���������ļ������쳣", e);
        }
        finally
        {
            book.close();
        }
        return list;
    }
    /**
     * ���ڲ�ѯ��Ƶ����
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
            // ����VideoReferenceDAO���в�ѯ
            vo =  VideoReferenceDAO.getInstance().queryVideoInfo(videoId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ��Ƶ����ʱ�������ݿ��쳣��");
        }
        
        return vo;
    }
    
    /**
     * ���ڲ�ѯ��Ƶ��չ�ֶ�����
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
            // ����VideoReferenceDAO���в�ѯ
        	list =  VideoReferenceDAO.getInstance().queryVideoKeyResource(videoId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ��Ƶ��չ�ֶ�ʱ�������ݿ��쳣��");
        }
        return list;
    }
    /**
	 * ���ڵ�����Ƶ���ݼ�����
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
		        
		        // �������л�ȡ��������
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
			logger.error("�ڵ�����Ƶ��Ʒ��Ϣ����ʱ�������ݿ��쳣��", e);
		}
		
		generateQueryDataExcel(list, wwb);
	}
	
	/**
	 * ����Ӧ�ø��°汾��excel
	 * 
	 * @param list
	 * @param wwb
	 * @param sheetName
	 */
	private void generateQueryDataExcel(List<VideoRefVO> list,
			WritableWorkbook wwb)
	{
		String sheetName = "Sheet";
		WritableSheet ws = wwb.createSheet(sheetName, 0); // ����sheet
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
		
		label = new Label(col++, row, "��Ŀ���");
		ws.addCell(label);
		label = new Label(col++, row, "��Ŀ����");
		ws.addCell(label);
		label = new Label(col++, row, "��Ŀ����");
		ws.addCell(label);
		label = new Label(col++, row, "���ݱ���");
		ws.addCell(label);
		label = new Label(col++, row, "��Ŀ·��");
		ws.addCell(label);
		label = new Label(col++, row, "�Ƿ�����");
		ws.addCell(label);
		label = new Label(col++, row, "����޸�ʱ��");
		ws.addCell(label);
		label = new Label(col++, row, "�������");
		ws.addCell(label);
	}

}
