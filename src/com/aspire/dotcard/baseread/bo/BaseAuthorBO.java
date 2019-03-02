package com.aspire.dotcard.baseread.bo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.config.BaseReadConfig;
import com.aspire.dotcard.baseread.dao.BaseAuthorDAO;
import com.aspire.dotcard.baseread.vo.BookAuthorVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.system.SystemConfig;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;

/**
 * @author wangminlong
 */
public class BaseAuthorBO
{

	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(BaseAuthorBO.class);

	private static BaseAuthorBO instance = new BaseAuthorBO();

	private BaseAuthorBO()
	{}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static BaseAuthorBO getInstance()
	{
		return instance;
	}

	/**
	 * 查询基地阅读作者信息列表出错
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryBaseAuthorList(PageResult page, BookAuthorVO vo)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseAuthorBO.queryBaseAuthorList( ) is start...");
		}

		try
		{
			BaseAuthorDAO.getInstance().queryBaseAuthorList(page, vo);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回基地阅读作者信息列表时发生数据库异常！");
		}
	}

	/**
	 * 用于查询基地阅读指定作者详情信息
	 * 
	 * @param authorId
	 * @return
	 * @throws BOException
	 */
	public BookAuthorVO queryBaseAuthorVO(String authorId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseAuthorBO.queryBaseAuthorVO(" + authorId
					+ ") is start...");
		}

		try
		{
			return BaseAuthorDAO.getInstance().queryBaseAuthorVO(authorId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查询基地阅读指定作者详情信息时发生数据库异常！");
		}
	}

	/**
	 * 用于变更基地阅读指定作者详情
	 * 
	 * @param authorVO
	 * @throws BOException
	 */
	public void updateReadAuthor(BookAuthorVO authorVO) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseAuthorBO.updateReadAuthor() is start...");
		}

		try
		{
			BaseAuthorDAO.getInstance().updateBaseAuthorVO(authorVO);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("变更基地阅读指定作者详情时发生数据库异常！");
		}
	}

	/**
	 * 用于导出基地阅读作者数据
	 * 
	 * @param request
	 * @param wwb
	 * @param isAll
	 * @throws BOException
	 */
	public void exportQueryData(HttpServletRequest request,
			WritableWorkbook wwb, boolean isAll) throws BOException
	{
		List list = null;

		try
		{
			if (isAll)
			{
				list = BaseAuthorDAO.getInstance().queryListByExport("", "");
			}
			else
			{
				String authorId = this.getParameter(request, "authorId").trim();
				String authorName = this.getParameter(request, "authorName")
						.trim();
				list = BaseAuthorDAO.getInstance().queryListByExport(authorId,
						authorName);
			}
		}
		catch (DAOException e)
		{
			logger.error("于导出基地阅读作者数据时发生数据库异常：", e);
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
	private void generateQueryDataExcel(List list, WritableWorkbook wwb)
	{

		String sheetName = "Sheet";
		WritableSheet ws = null; // 创建sheet
		Label label = null;
		int row = 0;
		int col = 0;
		int sheet = 0;
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				if(i%65533 == 0)
				{
					ws = wwb.createSheet(sheetName + sheet, sheet++);
					setTitle(ws);
					row = 0;
				}
				
				BookAuthorVO vo = (BookAuthorVO) list.get(i);
				col = 0;
				row++;

				label = new Label(col++, row, vo.getAuthorId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getAuthorName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getNameLetter());
				ws.addCell(label);
				label = new Label(col++, row, vo.getDescription());
				ws.addCell(label);
				label = new Label(col++, row, vo.getIsOriginal());
				ws.addCell(label);
				label = new Label(col++, row, vo.getIsPublish());
				ws.addCell(label);
				label = new Label(col++, row, vo.getRecommendManual());
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
	
	
	private void setTitle(WritableSheet ws) throws RowsExceededException, WriteException
	{
		int row = 0;
		int col = 0;
		Label label = null;
		
		label = new Label(col++, row, "作者ID");
		ws.addCell(label);
		label = new Label(col++, row, "作者名称");
		ws.addCell(label);
		label = new Label(col++, row, "作者名称首字母");
		ws.addCell(label);
		label = new Label(col++, row, "作者简介");
		ws.addCell(label);
		label = new Label(col++, row, "是否原创大神");
		ws.addCell(label);
		label = new Label(col++, row, "是否出版作家");
		ws.addCell(label);
		label = new Label(col++, row, "推荐序号");
		ws.addCell(label);
	}

	/**
	 * 从请求中获取参数，如果为null就返回空字符串""
	 * 
	 * @param request
	 *            http请求
	 * @param key
	 *            参数的关键字
	 * @return 参数值
	 */
	protected String getParameter(HttpServletRequest request, String key)
	{
		String value = request.getParameter(key);
		if (value == null)
		{
			value = "";
		}
		return value;
	}

	/**
	 * 文件批量导入基地阅读作者
	 * 
	 * @param dataFile
	 * @throws BOException
	 */
	public String importAuthorData(FormFile dataFile) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseAuthorBO.importAuthorData( ) is start...");
		}

		StringBuffer ret = new StringBuffer();
		String temp[] = new String[2];

		// 解析EXECL文件，获取终端软件版本列表
		List list = this.paraseDataFile(dataFile);

		// 调用ReadReferenceDAO进行新增
		BaseAuthorDAO.getInstance().updateReadAuthor(list, temp);

		ret.append("成功导入" + temp[0] + "条记录.");

		if (!"".equals(temp[1]))
		{
			ret.append("\n导入不成功id为").append(temp[1]);
		}

		return ret.toString();
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
		logger.info("BaseAuthorBO.paraseDataFile() is start!");
		List list = new ArrayList();
		Workbook book = null;
		boolean isTrue;

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
				int columns = 6;
				for (int j = 0; j < rows; j++)
				{
					isTrue = true;
					BookAuthorVO vo = new BookAuthorVO();

					for (int j2 = 0; j2 < columns; j2++)
					{
						String value = sheets[i].getCell(j2, j)
								.getContents().trim();

						// ID不可以为空
						if ("".equals(value) && j2!=5)
						{
							logger.error("解析导入文件时发现，数据中有为空现象，于文件中第" + j + "行出现，此行不予处理！");
							isTrue = false;
							break;
						}
						
						logger.info("当前值为：" + value + "，J2值为："+ j2);
						
						switch (j2)
						{
							case 0:
								vo.setAuthorId(value);
								break;
							case 1:
								vo.setNameLetter(value);
								break;
							case 2:
								if (!"0".equals(value) && !"1".equals(value))
								{
									logger.error("解析导入文件时发现，是否原创大神为非0、1值，于文件中第"
											+ j + "行出现，此行不予处理！");
									isTrue = false;
								}
								vo.setIsOriginal(value);
								break;
							case 3:
								if (!"0".equals(value) && !"1".equals(value))
								{
									logger.error("解析导入文件时发现，是否出版专家为非0、1值，于文件中第"
											+ j + "行出现，此行不予处理！");
									isTrue = false;
								}
								vo.setIsPublish(value);
								break;
							case 4:
								vo.setRecommendManual(value);
								break;
							case 5:
								vo.setDescription(value);
								break;
						}
					}
					
					if (isTrue)
					{
						list.add(vo);
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
	 * 用于查询基地阅读指定作者详情
	 * 
	 * @param authorId
	 * @throws BOException
	 */
	public List queryAuthorIdKeyResource(String authorId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger
					.debug("BaseAuthorBO.queryAuthorIdKeyResource( ) is start...");
		}
		List list = null;
		try
		{
			list = BaseAuthorDAO.getInstance().queryAuthorKeyResource(authorId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查询基地阅读指定作者详情时发生数据库异常！");
		}
		return list;
	}

	/**
	 * 用于返回基地阅读作者扩展字段列表
	 * 
	 * @return
	 * @throws BOException
	 */
	public List queryAuthorKeyBaseList(String tid) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("BaseAuthorBO.queryAuthorKeyBaseList( ) is start...");
		}

		try
		{
			return BaseAuthorDAO.getInstance().queryAuthorKeyBaseList(tid);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回基地阅读作者扩展字段列表时发生数据库异常！", e);
		}
	}
	
	 /**
     * 上传图书作者的预览图的图片
     * @param request
     * @throws BOException
     */
    public String uploadAuthorPicURL(FormFile uploadFile,String authorId)throws BOException
    {
        String tempDir = ServerInfo.getAppRootPath() + File.separator + "readAuthor"
                         + File.separator;
        String ftpDir = BaseReadConfig.get("bookAuthorPicURl");
        
        IOUtil.checkAndCreateDir(tempDir);

        // 获得文件名，这个文件名包括路径：
        String fileName = uploadFile.getFileName();
        String suffex = fileName.substring(fileName.lastIndexOf('.'));

        // 拼装临时文件名为 temp.png
        String oldFileName = tempDir + "temp" + suffex;
		logger.debug("oldFileName="+oldFileName);
        try
        {
            IOUtil.writeToFile(oldFileName, uploadFile.getFileData());
        }
        catch (Exception e1)
        {
            throw new BOException("写入图书作者图片临时文件失败。tempFilePath=" + oldFileName,
                                  RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
        }

       

        FTPClient ftp = null;
        try
        {
            ftp = PublicUtil.getFTPClient(SystemConfig.SOURCESERVERIP,
                                          SystemConfig.SOURCESERVERPORT,
                                          SystemConfig.SOURCESERVERUSER,
                                          SystemConfig.SOURCESERVERPASSWORD,
                                          ftpDir);
            PublicUtil.checkAndCreateDir(ftp, authorId);
            ftp.chdir(authorId);
            ftp.put(oldFileName, authorId+suffex);
            
        }
        catch (Exception e)
        {
            throw new BOException("上传到资源服务器出现异常。",
                                  e,
                                  RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
        }
        finally
        {
            if (ftp != null)
            {
                try
                {
                    ftp.quit();
                }
                catch (Exception e)
                {
                }
            }
        }
        return SystemConfig.URL_PIC_VISIT + BaseReadConfig.get("bookAuthorPicURl")
                +authorId+"/"+authorId+suffex;
    }
   
}
