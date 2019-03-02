/**
 * com.aspire.ponaadmin.web.newmusic139.bo New139TagSynBO.java
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
 * @author tungke
 *3.12	�¸�����ǩ��Ϣͬ���ӿ�
 *
 */
public class New139TagSynBO extends New139BaseSynBO
{
	private static final JLogger logger = LoggerFactory
	.getLogger(New139TagSynBO.class);
	
	
	/**
	 * 
	 *@desc ��139�������ֱ�ǩ����   �ֶ�ִ�����
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
				result = dealMusicTagData();
				Date endDate = new Date();
				if(result != null && result.length==3){
					int success  = result[0].intValue();
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
				}else{
					 sb.append("����ʧ��");
				}
			} catch (BOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				 sb.append("����ʧ��"+e);
			}
			
			sendMail(sb.toString(),"������139���ֱ�ǩ�ļ�����ʼ�");
			

		}
	/**
	 * 
	 *@desc ��139���ָ�����ǩ��Ϣ����   �Զ��������
	 *@author dongke
	 *Apr 30, 2011
	 * @return
	 * @throws BOException 
	 * @throws Exception
	 */
	public Integer[] dealMusicTagData() throws BOException {
		String tagFileName = NewMusic139Config.getInstance().getNewTagFileName();
		String [] files = {tagFileName};
		fileNameRex = files;
		
		//result [0] �ɹ�����;result [1] ʧ�ܴ���;result [2] У��ʧ��
		Integer [] result = {new Integer(0),new Integer(0),new Integer(0)};
		List tagList;
		try
		{
			tagList = input139Data();
			this.ChecketAndDealData(tagList,result);
		} catch (Exception e)
		{
			logger.error("��ȡ�ļ�ʧ��");
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException("��ȡ�ļ�ʧ��"+e);
		}
		return result;
	}
	/**
	 * 
	 *@desc ���ݼ�飬���˵��Ƿ����ݣ�������
	 *@author dongke
	 *Apr 30, 2011
	 * @param al
	 * @param result
	 */
    private void ChecketAndDealData(List al,Integer[] result)
	{

		int checkfaildnum = 0;
		int success = 0;
		int dealfailed = 0;
		if (al != null && al.size() > 0)
		{
			for (int i = 0; i < al.size(); i++)
			{
				String[] musicTagLine = (String[]) al.get(i);
				if (musicTagLine != null)
				{
					if (musicTagLine.length != 4)
					{
						logger.warn("������ǩ���ݼ�������ֶ�����Ϊ4;���Ը��У�" + musicTagLine);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					
					if (musicTagLine[0] == null || musicTagLine[0].length() == 0
							|| musicTagLine[0].length() > 30)
					{
						logger.warn("����һ�����ݲ�����������IDΪ�ջ򳤶Ȳ����ϱ�׼ 30��" + musicTagLine[0]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (musicTagLine[3] == null || musicTagLine[3].length() == 0
							|| musicTagLine[1].length() > 400)
					{
						logger.warn("����һ�����ݲ���������ǩ��ϢΪ�ջ򳤶Ȳ����ϱ�׼ 400 " + musicTagLine[3]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					
					
					logger.debug("��" + i + "�����ݼ�����,��ʼ��⴦��");
					int dealResult = dealLineMusicTagData(musicTagLine);
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
     *@desc ������ǩ���д�����
     *@author dongke
     *May 1, 2011
     * @param musicTagLine
     * @return
     */
    private int dealLineMusicTagData(String [] musicTagLine){
    	String musicId = musicTagLine[0];
    	String tags = buildTags(musicTagLine[3]);
    	 int result = New139BaseSyncDAO.getInstance().updateMusicTags(musicId,tags);
    	return result;
    }
	private  String buildTags(String tags) {
		String[] temp = tags.split(",");
		int count = temp.length;
		if (count == 0) {
			return "{" + tags + "}";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			if (i != 0) {
				sb.append(";");
			}
			sb.append("{").append(temp[i]).append("}");
		}
		return sb.toString();
	}
//	public static void main(String args[]){
//		String tags = "�ܹ�,nih,Ӣ��";
//		System.out.println(buildTags(tags));
//	} 
	
}
