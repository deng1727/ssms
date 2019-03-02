
package com.aspire.ponaadmin.web.pivot.bo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.upload.FormFile;


import com.aspire.common.config.ServerInfo;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.book.bo.BookRefBO;
import com.aspire.ponaadmin.web.pivot.dao.PivotContentDAO;
import com.aspire.ponaadmin.web.pivot.vo.PivotContentVO;
import com.aspire.ponaadmin.web.pivot.vo.PivotDownloadVO;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class PivotContentBO
{
    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(PivotContentBO.class);

    private static PivotContentBO instance = new PivotContentBO();

    private PivotContentBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static PivotContentBO getInstance()
    {
        return instance;
    }

    /**
     * 用于查询重点内容列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryPivotContentList(PageResult page, PivotContentVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotContentBO.queryPivotContentList( ) is start...");
        }

        try
        {
            // 调用PivotContentDAO进行查询
            PivotContentDAO.getInstance().queryPivotContentList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询重点内容列表时发生数据库异常！");
        }
    }

    /**
     * 用于移除指定重点内容
     * 
     * @param contentId
     */
    public void removeContentID(String[] contentId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotContentBO.removeContentID( ) is start...");
        }

        try
        {
            // 调用PivotContentDAO
            PivotContentDAO.getInstance().removeContentID(contentId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("移除指定重点内容时发生数据库异常！");
        }
    }

    /**
     * 导入重点内容列表
     * 
     * @param dataFile
     * @throws BOException
     */
    public String importPivotContent(FormFile dataFile) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotContentBO.importPivotContent( ) is start...");
        }

        StringBuffer ret = new StringBuffer();

        try
        {
            // 解析EXECL文件，获取终端软件版本列表
            List list = BookRefBO.getInstance().paraseDataFile(dataFile);

            // 校驗文件中數據是否在内容表中存在
            String temp = PivotContentDAO.getInstance().verifyContentId(list);

            if (!"".equals(temp))
            {
                ret.append("内容编码有误，有误内容编码分别为：").append(temp).append("请修改！");

                throw new BOException(ret.toString(),
                                      RepositoryBOCode.CATEGORY_DEVICE);
            }

            // 检验列表中是否存在原表数据
            PivotContentDAO.getInstance().hasContentId(list);

            String[] ids = new String[list.size()];

            for (int i = 0; i < list.size(); i++)
            {
                ids[i] = ( String ) list.get(i);
            }

            // 调用PivotDeviceDAO进行新增
            PivotContentDAO.getInstance().addContentId(ids);

            ret.append("成功导入" + list.size() + "条记录.");

            return ret.toString();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("导入重点内容列表时发生数据库异常！");
        }
    }

    /**
     * 根据内容编码。得到内容主键.
     * 
     * @param contentId
     * @return
     * @throws BOException
     */
    public String queryContentId(String contentId) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("PivotContentBO.queryContentId( ) is start...");
        }

        try
        {
            // 调用PivotContentDAO
            return PivotContentDAO.getInstance().queryContentId(contentId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据内容编码。得到内容主键时发生数据库异常！");
        }
    }

    /**
     * 下载重点数据
     * 
     * @return
     * @throws BOException
     */
    public String downloadData() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("PivotContentBO.downloadData( ) is start...");
        }

        List list = null;
        String fileName = "";
        
        try
        {
            // 设用存储过程
            PivotContentDAO.getInstance().prepareCallDownload();
            
            // 查询生成下载文件应用的指定表数据
            list = PivotContentDAO.getInstance().queryDownloadData();
            
            // 保存返回数据至文件中
            fileName = writeDataToFile(list);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("下载重点数据时发生数据库异常！");
        }

        return fileName;
    }
    
    /**
     * 用于把数据集合写入至将要被下载的临时文件中
     * @param list
     * @return
     */
    private String writeDataToFile(List list) throws BOException
    {
        // 临时路径处理
        String downloadDir = ServerInfo.getAppRootPath() + File.separator
                         + "download" + File.separator;
        IOUtil.checkAndCreateDir(downloadDir);
        
        // 临时文件名
        String fileName = downloadDir + "download" + PublicUtil.getCurDateTime("yyyy_MM_dd_HH_ss") + ".xls";
        
        writeFile(fileName, list);
        
        return fileName;
    }
    
    /**
     * 写放文件中
     * @param fileName
     * @param list
     */
    private void writeFile(String fileName, List list) throws BOException
    {
        WritableWorkbook workbook = null;
        WritableSheet sheet = null;
        PivotDownloadVO vo = null;

        // 单个sheet最大行数
        int maxSheetRowSize = 65534;
        int sheetNumber = 0;

        String[] titles = new String[] { "内容编码", "内容名称", "AP编码", "AP名称", "平台名称",
                        "机型编码", "机型名称", "品牌名称" };
        try
        {
            File file = new File(fileName);
            
            // 创建文件
            workbook = Workbook.createWorkbook(file);

            for (int i = 0; i < list.size(); i++)
            {
                // 当前sheet的行数。
                int rowNumber = i % maxSheetRowSize;

                // 如果大于sheet最大允许的行数，则顺序创建接下来的sheet
                if (rowNumber == 0)
                {
                    sheet = workbook.createSheet("sheet" + (sheetNumber + 1),
                                                 sheetNumber);
                    sheetNumber++;

                    for (int j = 0; j < titles.length; j++)
                    {
                        // 第一列 列数 第二列 行数 第三列 内容
                        sheet.addCell(new Label(j, 0, titles[j]));
                    }
                }

                vo = ( PivotDownloadVO ) list.get(i);
                sheet.addCell(new Label(0, rowNumber + 1, vo.getContentId()));
                sheet.addCell(new Label(1, rowNumber + 1, vo.getContentName()));
                sheet.addCell(new Label(2, rowNumber + 1, vo.getApId()));
                sheet.addCell(new Label(3, rowNumber + 1, vo.getApName()));
                sheet.addCell(new Label(4, rowNumber + 1, vo.getOsName()));
                sheet.addCell(new Label(5, rowNumber + 1, vo.getDeviceId()));
                sheet.addCell(new Label(6, rowNumber + 1, vo.getDeviceName()));
                sheet.addCell(new Label(7, rowNumber + 1, vo.getBrandName()));
            }
            
            if (sheet == null)
            {
                sheet = workbook.createSheet("sheet" + (sheetNumber + 1),
                                             sheetNumber);
            }
        }
        catch (Exception e)
        {
            throw new BOException("创建excel文件出错", e);
        }
        finally
        {
            try
            {
                workbook.write();
                workbook.close();
            }
            catch (Exception e)
            {
                throw new BOException("保存excel数据有误", e);
            }
        }
    }
    
    public void downloadFile(String fileName, HttpServletResponse response) throws BOException
    {
        File f = new File(fileName);
        
        BufferedInputStream br;
        try
        {
            br = new BufferedInputStream(new FileInputStream(f));
            byte[] buf = new byte[1024];
            int len = 0;
            response.reset(); //非常重要
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
            OutputStream out = response.getOutputStream();
            while((len = br.read(buf)) >0)
            out.write(buf,0,len);
            br.close();
            out.close();
        }
        catch (FileNotFoundException e)
        {
            throw new BOException("源excel文件不存在", e);
        }
        catch (IOException e)
        {
            throw new BOException("写文件出错", e);
        }
    }
}
