package com.aspire.ponaadmin.web.dataexport.apwarn.bo;

import java.io.File;
import java.net.SocketException;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.apwarn.dao.ApWarnDao;
import com.aspire.ponaadmin.web.dataexport.apwarn.vo.ApWarnVo;

public class ApWarnBo {
	protected static JLogger logger = LoggerFactory.getLogger(ApWarnBo.class);

	private static ApWarnBo bo = new ApWarnBo();

	public static ApWarnBo getInstance() {
		return bo;
	}

	/**
	 * 查询下载量波动比较大的 免费商品 预警列表
	 * 
	 * @return
	 */
	public List getFreeWarnApList() throws Exception {
		return ApWarnDao.getInstance().getFreeWarnApList();
	}

	/**
	 * 查询下载量波动比较大的 付费商品预警列表
	 * 
	 * @return
	 */
	public List getPayWarnApList() throws Exception {
		return ApWarnDao.getInstance().getPayWarnApList();
	}

	/**
	 * AP预警数据初始化
	 * 
	 * @throws Exception
	 */
	public void initData() throws Exception {
		ApWarnDao.getInstance().initData();
	}

	/**
	 * 插入到T_AP_WARN表 作为历史记录
	 * @param list
	 */
	public void addApWarnData(List list) {
		try {
			ApWarnDao.getInstance().addApWarnData(list);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 导出excel文件 作为邮件附件
	 * @param list
	 * @param file
	 * @throws Exception
	 */
	public void exportToExcel(List list,File file) throws Exception {
		int maxSheetRowSize = 65534;
		ApWarnVo vo = null;
		WritableWorkbook workbook = null;
		WritableSheet sheet = null;
		int sheetNumber = 0;
		String[] titles = new String[] { "时间", "商品名称", "AP名称", "应用ID","上线时间",
				"预警描述" };
		try {
			workbook = Workbook.createWorkbook(file);
			for (int i = 0; i < list.size(); i++) {
				int rowNumber = i % maxSheetRowSize;// 当前sheet的行数。
				if (rowNumber == 0)// 如果大于sheet最大允许的行数，则顺序创建接下来的sheet
				{
					sheet = workbook.createSheet("sheet" + (sheetNumber + 1),
							sheetNumber);
					sheetNumber++;
					for (int j = 0; j < titles.length; j++) {
						//第一列 列数   第二列 行数    第三列 内容
						sheet.addCell(new Label(j, 0, titles[j]));
					}
				}
				vo = (ApWarnVo) list.get(i);
				// for(int j=0;j<titles.length;j++){
				sheet.addCell(new Label(0, rowNumber + 1, vo.getWarnDate()));
				sheet.addCell(new Label(1, rowNumber + 1, vo.getName()));
				sheet.addCell(new Label(2, rowNumber + 1, vo.getSpName()));
				sheet.addCell(new Label(3, rowNumber + 1, vo.getContentId()));
				sheet.addCell(new Label(4, rowNumber + 1, vo.getMarketDate()));
				sheet.addCell(new Label(5, rowNumber + 1, vo.getWarnDesc()));
				// }
			}

		} catch (SocketException e) {
			throw e;// 当用户选择取消的时候会出现这个异常，该异常不算错误。
		} catch (Exception e) {
			throw new BOException("创建excel文件出错", e);
		} finally {
			try {
				workbook.write();
				workbook.close();
			} catch (Exception e) {
				throw new BOException("保存excel数据有误", e);
			}
		}
	}
}
