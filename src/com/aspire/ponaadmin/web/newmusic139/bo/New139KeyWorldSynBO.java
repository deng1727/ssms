/**
 * com.aspire.ponaadmin.web.newmusic139.bo New139KeyWorldSynBO.java
 * May 1, 2011
 *<p>
 * Copyright (c) 2003-2011 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * @author dongke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.newmusic139.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.newmusic139.NewMusic139Config;
import com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke 139���� �ؼ�����Ϣ�ļ����ݵ��� 3.11 �������ؼ�����Ϣͬ���ӿ�
 * 
 */
public class New139KeyWorldSynBO extends New139BaseSynBO
{
	private static final JLogger logger = LoggerFactory
			.getLogger(New139KeyWorldSynBO.class);

	/**
	 * 
	 *@desc ��139���ֹؼ��ֵ���   �ֶ�ִ�����
	 *@author dongke
	 *Apr 30, 2011
	 */
	public void handDeal()
	{
		Date startDate = new Date();
		Integer[] result = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			result = dealKeyWorldData();
			Date endDate = new Date();
			if (result != null && result.length == 3)
			{
				int success = result[0].intValue();
				int checkfaild = result[2].intValue();
				int dealfaild = result[1].intValue();
				sb.append("��ʼʱ�䣺");
				sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
				sb.append(",����ʱ�䣺");
				sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
				sb.append("��<h4>��������</h4>");
				sb.append("<p>���гɹ�����<b>");
				sb.append(success);
				sb.append("��;<p>����ʧ��<b>");
				sb.append(dealfaild);
				sb.append("��;<p>���ݲ����Ϲ淶<b>");
				sb.append(checkfaild);
				sb.append("��;<p>");
			}
			else
			{
				sb.append("����ʧ��");
			}
		} catch (BOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			sb.append("����ʧ��" + e);
		}

		sendMail(sb.toString(), "������139���ֹؼ�����Ϣ�ļ�����ʼ�");

	}

	/**
	 * 
	 * @desc ��139���ָ�����ǩ��Ϣ���� �Զ��������
	 * @author dongke Apr 30, 2011
	 * @return
	 * @throws BOException
	 * @throws Exception
	 */
	public Integer[] dealKeyWorldData() throws BOException
	{
		String keyWorldFileName = NewMusic139Config.getInstance().getNewKeywordFileName();
		String[] files = { keyWorldFileName };
		fileNameRex = files;

		// result [0] �ɹ�����;result [1] ʧ�ܴ���;result [2] У��ʧ��
		Integer[] result = { new Integer(0), new Integer(0), new Integer(0) };
		List keyWorldList;
		try
		{
			keyWorldList = input139Data();
			this.ChecketAndDealData(keyWorldList, result);
		} catch (Exception e)
		{
			logger.error("��ȡ�ļ�ʧ��");
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException("��ȡ�ļ�ʧ��" + e);
		}
		return result;
	}

	/**
	 * 
	 * @desc ���ݼ�飬���˵��Ƿ����ݣ�������
	 * @author dongke Apr 30, 2011
	 * @param al
	 * @param result
	 */
	private void ChecketAndDealData(List al, Integer[] result)
	{

		int checkfaildnum = 0;
		int success = 0;
		int dealfailed = 0;
		if (al != null && al.size() > 0)
		{
			for (int i = 0; i < al.size(); i++)
			{
				String[] keyWorldLine = (String[]) al.get(i);
				if (keyWorldLine != null)
				{
					if (keyWorldLine.length != 2)
					{
						logger.warn("�����ؼ��� ���ݼ�������ֶ�����Ϊ2;���Ը��У�" + keyWorldLine);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}

					if (keyWorldLine[0] == null || keyWorldLine[0].length() == 0
							|| keyWorldLine[0].length() > 30)
					{
						logger.warn("����һ�����ݲ��������ؼ���Ϊ�ջ򳤶Ȳ����ϱ�׼ 30��" + keyWorldLine[0]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (!"1".equals(keyWorldLine[1]) && !"2".equals(keyWorldLine[1]))
					{
						logger.warn("������������:\r\n" + keyWorldLine[1]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}

					logger.debug("��" + i + "�����ݼ�����,��ʼ��⴦��");
					int dealResult = dealLineKeyWorldData(keyWorldLine);
					if (dealResult == 1)
					{
						success++;
					}
					else
					{
						dealfailed++;
					}
				}
				else
				{
					logger.warn("����һ�����ݲ��������� " + i + "��Ϊnull");
					checkfaildnum++;
				}
			}
		}
		else
		{
			logger.warn("�ļ�Ϊ�� ����null");
		}
		// ������
		if (result != null && result.length == 3)
		{
			if (result[0] == null)
			{
				result[0] = new Integer(success);
			}
			else
			{
				result[0] = new Integer(result[0].intValue() + success);
			}
			if (result[1] == null)
			{
				result[1] = new Integer(dealfailed);
			}
			else
			{
				result[1] = new Integer(result[1].intValue() + dealfailed);
			}
			if (result[2] == null)
			{
				result[2] = new Integer(checkfaildnum);
			}
			else
			{
				result[2] = new Integer(result[2].intValue() + checkfaildnum);
			}
		}
	}

	/**
	 * 
	 * @desc ������ǩ���д�����
	 * @author dongke May 1, 2011
	 * @param musicTagLine
	 * @return
	 */
	private int dealLineKeyWorldData(String[] keyWordLine)
	{
		String keyWorld = keyWordLine[0];
		String changeType = keyWordLine[1];
		int result = 0;
		if (changeType.equals("1"))
		{// ����
			result = New139BaseSyncDAO.getInstance().addUpdateKeyWorld(keyWorld);
		}
		else if (changeType.equals("2"))
		{// ɾ��
			result = New139BaseSyncDAO.getInstance().deleteKeyWorld(keyWorld);
		}
		return result;
	}
}
