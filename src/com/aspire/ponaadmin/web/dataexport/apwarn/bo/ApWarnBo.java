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
	 * ��ѯ�����������Ƚϴ�� �����Ʒ Ԥ���б�
	 * 
	 * @return
	 */
	public List getFreeWarnApList() throws Exception {
		return ApWarnDao.getInstance().getFreeWarnApList();
	}

	/**
	 * ��ѯ�����������Ƚϴ�� ������ƷԤ���б�
	 * 
	 * @return
	 */
	public List getPayWarnApList() throws Exception {
		return ApWarnDao.getInstance().getPayWarnApList();
	}

	/**
	 * APԤ�����ݳ�ʼ��
	 * 
	 * @throws Exception
	 */
	public void initData() throws Exception {
		ApWarnDao.getInstance().initData();
	}

	/**
	 * ���뵽T_AP_WARN�� ��Ϊ��ʷ��¼
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
	 * ����excel�ļ� ��Ϊ�ʼ�����
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
		String[] titles = new String[] { "ʱ��", "��Ʒ����", "AP����", "Ӧ��ID","����ʱ��",
				"Ԥ������" };
		try {
			workbook = Workbook.createWorkbook(file);
			for (int i = 0; i < list.size(); i++) {
				int rowNumber = i % maxSheetRowSize;// ��ǰsheet��������
				if (rowNumber == 0)// �������sheet����������������˳�򴴽���������sheet
				{
					sheet = workbook.createSheet("sheet" + (sheetNumber + 1),
							sheetNumber);
					sheetNumber++;
					for (int j = 0; j < titles.length; j++) {
						//��һ�� ����   �ڶ��� ����    ������ ����
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
			throw e;// ���û�ѡ��ȡ����ʱ����������쳣�����쳣�������
		} catch (Exception e) {
			throw new BOException("����excel�ļ�����", e);
		} finally {
			try {
				workbook.write();
				workbook.close();
			} catch (Exception e) {
				throw new BOException("����excel��������", e);
			}
		}
	}
}
