package com.aspire.dotcard.appmonitor.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.common.util.StringUtils;
import com.aspire.dotcard.appmonitor.config.AppMonitorConfig;
import com.aspire.dotcard.appmonitor.dao.AppMonitorDAO;
import com.aspire.dotcard.appmonitor.vo.MonitorContentVO;

public class AppMonitorBO {

	/**
	 * 日志对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(AppMonitorBO.class);
	
	private static AppMonitorBO bo = new AppMonitorBO();
	
	private AppMonitorBO()
	{}
	
	public static AppMonitorBO getInstance()
	{
		return bo;
	}
	
	/**
     * 用于存放mm应用监控结果数据集合
     */
    protected List<String[]>  mm = new ArrayList<String[]>();
    
    /**
     * 用于存放汇聚应用监控结果数据集合
     */
    protected List<String[]>  hj = new ArrayList<String[]>();
	
	/**
	 * 执行器
	 */
	private TaskRunner dataSynTaskRunner;
	
	/**
	 * 主键MAP-MM
	 */
	protected static Map<String, String> keyMapMM = Collections
			.synchronizedMap(new HashMap<String, String>());
	
	/**
	 * 主键MAP-汇聚
	 */
	protected static Map<String, String> keyMapHJ = Collections
			.synchronizedMap(new HashMap<String, String>());
	
	/**
	 * 主键MAP-MM结果
	 */
	protected static Map<String, String[]> keyMapMMResult = Collections
			.synchronizedMap(new HashMap<String, String[]>());
	
	/**
	 * 主键MAP-汇聚结果
	 */
	protected static Map<String, String[]> keyMapHJResult = Collections
			.synchronizedMap(new HashMap<String, String[]>());
	
	public void init(){
		
		keyMapMM = AppMonitorDAO.getInstance().getContentIDMapByMM();
		keyMapHJ = AppMonitorDAO.getInstance().getContentIDMapByHJ();
		keyMapMMResult = AppMonitorDAO.getInstance().getResultContentIDMap("1");
		keyMapHJResult = AppMonitorDAO.getInstance().getResultContentIDMap("2");
	}
	
	public void clear(){
		keyMapMMResult.clear();
		keyMapHJResult.clear();
		keyMapMM.clear();
		keyMapHJ.clear();
	}
	
	//重点应用监控-货架
	public void monitorCategory(List<MonitorContentVO> monitorContentList){
		
		dataSynTaskRunner = new TaskRunner(AppMonitorConfig.taskRunnerNum,
				AppMonitorConfig.taskMaxReceivedNum);
		try{
			
			for(int i = 0;i < monitorContentList.size(); i++){
				MonitorContentVO vo = monitorContentList.get(i);
				if(!StringUtils.isEmpty(vo.getAppid())){
					AppMonitorDBOpration amdbo = new AppMonitorDBOpration(vo, this);
					
					ReflectedTask refTask = new ReflectedTask(amdbo,
							"monitorCategory", null, null);
					dataSynTaskRunner.addTask(refTask);
				}
			}
			dataSynTaskRunner.waitToFinished();
			dataSynTaskRunner.stop();
			
		}catch(Exception e){
			logger.error("重点应用监控-货架监控出错",e);
		}finally{
			keyMapMM.clear();
			keyMapHJ.clear();
		}
	}
	
	//重点应用监控-数据中心
	public void monitorDataCenter(List<MonitorContentVO> monitorContentList){
		dataSynTaskRunner = new TaskRunner(AppMonitorConfig.taskRunnerNum,
				AppMonitorConfig.taskMaxReceivedNum);
		
		for(int i = 0;i < monitorContentList.size(); i++){
			MonitorContentVO vo = monitorContentList.get(i);
			if (!StringUtils.isEmpty(vo.getAppid())) {
				AppMonitorDBOpration amdbo = new AppMonitorDBOpration(vo, this);
				
				ReflectedTask refTask = new ReflectedTask(amdbo,
						"monitorDataCenter", null, null);
				dataSynTaskRunner.addTask(refTask);
			}
		}
		dataSynTaskRunner.waitToFinished();
		dataSynTaskRunner.stop();
	}
	
	//重点应用监控-搜索
	public void monitorMMSearch(List<MonitorContentVO> monitorContentList){
		dataSynTaskRunner = new TaskRunner(AppMonitorConfig.taskRunnerNum,
				AppMonitorConfig.taskMaxReceivedNum);
		
		for(int i = 0;i < monitorContentList.size(); i++){
			MonitorContentVO vo = monitorContentList.get(i);
			if (!StringUtils.isEmpty(vo.getAppid())) {
				AppMonitorDBOpration amdbo = new AppMonitorDBOpration(vo, this);
				
				ReflectedTask refTask = new ReflectedTask(amdbo,
						"monitorMMSearch", null, null);
				dataSynTaskRunner.addTask(refTask);
			}
		}
		dataSynTaskRunner.waitToFinished();
		dataSynTaskRunner.stop();
	}
	
	/**
     * 用于生成文件
     */
    public void createFile(String fileName) throws BOException
    {
        // 得到MM应用监控结果数据集合。
    	mm = AppMonitorDAO.getInstance().getAppMonitorResult("1");
    	// 得到汇聚应用监控结果数据集合。
    	hj = AppMonitorDAO.getInstance().getAppMonitorResult("2");
        
    	// 创建目录
        createFilePath(fileName);

        // 写入文件
        writeToExcelFile(fileName);
        
        mm.clear();
        hj.clear();
        
    }
	
	/**
	 * 用于创建目录
	 */
	private void createFilePath(String fileAllName)
	{
		File file = new File(fileAllName);
		
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
	}
	
	/**
     * 写入excel文件中
     * 
     * @throws BOException
     * @throws SocketException
     */
    public void writeToExcelFile(String fileName) throws BOException
    {
        int maxSheetRowSize = 65535;

        XSSFWorkbook workbook = null;
    	Sheet sheet = null;
    	FileOutputStream fOut = null;
        int sheetNumber = 0;
        int rowNum = 0;
        try
        {
        	fOut = new FileOutputStream(fileName);
        	workbook = new XSSFWorkbook();
    		sheet = workbook.createSheet("MM应用");
            //添加MM应用结果
            Iterator<String[]> it = mm.iterator();
            while (it.hasNext())
    		{
    			String[] temp = it.next();
    			if(rowNum == 0){
    				createFirstRow(workbook,sheet);
    			}
    			Row row = sheet.createRow(rowNum+1);
    			
    			for (int n = 0; n < temp.length; n++)
    			{
    				Cell cell = row.createCell(n);
    				cell.setCellValue(temp[n]);
    			}
    			
    			rowNum++;
    			
    			// 如果大于sheet最大允许的行数，则顺序创建接下来的sheet
    			if(rowNum%maxSheetRowSize == 0)
    			{
    				sheet = workbook.createSheet("MM应用" + (sheetNumber++ + 1));
    				rowNum = 0;
    			}
    		}

            //添加汇聚应用结果
            sheetNumber = 0;
            rowNum = 0;
            sheet = workbook.createSheet("汇聚应用");
            it = hj.iterator();
            while (it.hasNext())
    		{
    			String[] temp = it.next();
    			
    			if(rowNum == 0){
    				createFirstRow(workbook,sheet);
    			}
    			
    			Row row = sheet.createRow(rowNum+1);
    			
    			for (int n = 0; n < temp.length; n++)
    			{
    				Cell cell = row.createCell(n);
    				cell.setCellValue(temp[n]);
    			}
    			
    			rowNum++;
    			
    			// 如果大于sheet最大允许的行数，则顺序创建接下来的sheet
    			if(rowNum%maxSheetRowSize == 0)
    			{
    				sheet = workbook.createSheet("汇聚应用" + (sheetNumber++ + 1));
    				rowNum = 0;
    			}
    		}
        }
        catch (Exception e)
        {
            throw new BOException("创建excel文件出错", e);
        }
        finally
        {
        	if (fOut != null)
    		{
    			try
    			{
    				workbook.write(fOut);
    				fOut.flush();
    			}
    			catch (IOException e)
    			{
    				logger.error("保存excel数据有误", e);
    				throw new BOException("保存excel数据有误", e);
    			}
    			finally
    			{
    				try
    				{
    					fOut.close();
    				}
    				catch (IOException e)
    				{
    					logger.error("保存excel数据有误", e);
    					throw new BOException("保存excel数据有误", e);
    				}
    			}
    		}
        }
    }

    private void createFirstRow(XSSFWorkbook workbook,Sheet sheet){
    	Row row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头 设置表头居中  
        XSSFCellStyle style = workbook.createCellStyle();  
        style.setAlignment(CellStyle.ALIGN_CENTER); // 创建一个居中格式  
        Cell cell = row.createCell((short) 0);
        cell.setCellValue("监控类型");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("应用id");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("Packagename");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("应用名称");
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);
        cell.setCellValue("版本version name");
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);
        cell.setCellValue("应用更新时间");
        cell.setCellStyle(style);
        cell = row.createCell((short) 6);
        cell.setCellValue("是否已输出至货架（是，否）");
        cell.setCellStyle(style);
        cell = row.createCell((short) 7);
        cell.setCellValue("货架状态更新时间");
        cell.setCellStyle(style);
        cell = row.createCell((short) 8);
        cell.setCellValue("是否已输出至客户端门户（是，否）");
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);
        cell.setCellValue("客户端门户状态更新时间");
        cell.setCellStyle(style);
        cell = row.createCell((short) 10);
        cell.setCellValue("是否已输出至搜索（是，否）");
        cell.setCellStyle(style);
        cell = row.createCell((short) 11);
        cell.setCellValue("搜索状态更新时间");
        cell.setCellStyle(style);
        cell = row.createCell((short) 12);
        cell.setCellValue("聚合应用id");
        cell.setCellStyle(style);
    }
    
	public static void main(String[] args){
		StringBuffer sb = new StringBuffer();
		sb.append("skfjsdk,sdflsdkf,");
		System.out.println(sb.substring(0, sb.length()-1));
	}
}
