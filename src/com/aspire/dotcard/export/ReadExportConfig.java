package com.aspire.dotcard.export;

import com.aspire.dotcard.gcontent.GRead;

/**
 * ���ض��鵼�������
 * @author zhangwei
 *
 */
public class ReadExportConfig extends TypeExportConfig
{
	public ReadExportConfig()
	{
		init();
	}

	public void init()
	{
		type=GRead.TYPE_READ;
		list.add(new ExportItem("id","����ID"));
		list.add(new ExportItem("name","ͼ������"));
		list.add(new ExportItem("singer","��������"));
		list.add(new ExportItem("introduction","ͼ����"));
		list.add(new ExportItem("marketdate","����ʱ��"));		
	}
	

}
