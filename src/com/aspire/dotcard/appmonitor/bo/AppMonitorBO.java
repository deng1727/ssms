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
	 * ��־����
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
     * ���ڴ��mmӦ�ü�ؽ�����ݼ���
     */
    protected List<String[]>  mm = new ArrayList<String[]>();
    
    /**
     * ���ڴ�Ż��Ӧ�ü�ؽ�����ݼ���
     */
    protected List<String[]>  hj = new ArrayList<String[]>();
	
	/**
	 * ִ����
	 */
	private TaskRunner dataSynTaskRunner;
	
	/**
	 * ����MAP-MM
	 */
	protected static Map<String, String> keyMapMM = Collections
			.synchronizedMap(new HashMap<String, String>());
	
	/**
	 * ����MAP-���
	 */
	protected static Map<String, String> keyMapHJ = Collections
			.synchronizedMap(new HashMap<String, String>());
	
	/**
	 * ����MAP-MM���
	 */
	protected static Map<String, String[]> keyMapMMResult = Collections
			.synchronizedMap(new HashMap<String, String[]>());
	
	/**
	 * ����MAP-��۽��
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
	
	//�ص�Ӧ�ü��-����
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
			logger.error("�ص�Ӧ�ü��-���ܼ�س���",e);
		}finally{
			keyMapMM.clear();
			keyMapHJ.clear();
		}
	}
	
	//�ص�Ӧ�ü��-��������
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
	
	//�ص�Ӧ�ü��-����
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
     * ���������ļ�
     */
    public void createFile(String fileName) throws BOException
    {
        // �õ�MMӦ�ü�ؽ�����ݼ��ϡ�
    	mm = AppMonitorDAO.getInstance().getAppMonitorResult("1");
    	// �õ����Ӧ�ü�ؽ�����ݼ��ϡ�
    	hj = AppMonitorDAO.getInstance().getAppMonitorResult("2");
        
    	// ����Ŀ¼
        createFilePath(fileName);

        // д���ļ�
        writeToExcelFile(fileName);
        
        mm.clear();
        hj.clear();
        
    }
	
	/**
	 * ���ڴ���Ŀ¼
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
     * д��excel�ļ���
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
    		sheet = workbook.createSheet("MMӦ��");
            //���MMӦ�ý��
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
    			
    			// �������sheet����������������˳�򴴽���������sheet
    			if(rowNum%maxSheetRowSize == 0)
    			{
    				sheet = workbook.createSheet("MMӦ��" + (sheetNumber++ + 1));
    				rowNum = 0;
    			}
    		}

            //��ӻ��Ӧ�ý��
            sheetNumber = 0;
            rowNum = 0;
            sheet = workbook.createSheet("���Ӧ��");
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
    			
    			// �������sheet����������������˳�򴴽���������sheet
    			if(rowNum%maxSheetRowSize == 0)
    			{
    				sheet = workbook.createSheet("���Ӧ��" + (sheetNumber++ + 1));
    				rowNum = 0;
    			}
    		}
        }
        catch (Exception e)
        {
            throw new BOException("����excel�ļ�����", e);
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
    				logger.error("����excel��������", e);
    				throw new BOException("����excel��������", e);
    			}
    			finally
    			{
    				try
    				{
    					fOut.close();
    				}
    				catch (IOException e)
    				{
    					logger.error("����excel��������", e);
    					throw new BOException("����excel��������", e);
    				}
    			}
    		}
        }
    }

    private void createFirstRow(XSSFWorkbook workbook,Sheet sheet){
    	Row row = sheet.createRow(0);
		// ���Ĳ���������Ԫ�񣬲�����ֵ��ͷ ���ñ�ͷ����  
        XSSFCellStyle style = workbook.createCellStyle();  
        style.setAlignment(CellStyle.ALIGN_CENTER); // ����һ�����и�ʽ  
        Cell cell = row.createCell((short) 0);
        cell.setCellValue("�������");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("Ӧ��id");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("Packagename");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("Ӧ������");
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);
        cell.setCellValue("�汾version name");
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);
        cell.setCellValue("Ӧ�ø���ʱ��");
        cell.setCellStyle(style);
        cell = row.createCell((short) 6);
        cell.setCellValue("�Ƿ�����������ܣ��ǣ���");
        cell.setCellStyle(style);
        cell = row.createCell((short) 7);
        cell.setCellValue("����״̬����ʱ��");
        cell.setCellStyle(style);
        cell = row.createCell((short) 8);
        cell.setCellValue("�Ƿ���������ͻ����Ż����ǣ���");
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);
        cell.setCellValue("�ͻ����Ż�״̬����ʱ��");
        cell.setCellStyle(style);
        cell = row.createCell((short) 10);
        cell.setCellValue("�Ƿ���������������ǣ���");
        cell.setCellStyle(style);
        cell = row.createCell((short) 11);
        cell.setCellValue("����״̬����ʱ��");
        cell.setCellStyle(style);
        cell = row.createCell((short) 12);
        cell.setCellValue("�ۺ�Ӧ��id");
        cell.setCellStyle(style);
    }
    
	public static void main(String[] args){
		StringBuffer sb = new StringBuffer();
		sb.append("skfjsdk,sdflsdkf,");
		System.out.println(sb.substring(0, sb.length()-1));
	}
}
