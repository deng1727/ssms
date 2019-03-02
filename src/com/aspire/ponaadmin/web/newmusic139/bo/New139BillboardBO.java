/**
 * com.aspire.ponaadmin.web.newmusic139.bo New139billboardBO.java
 * Apr 30, 2011
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.newmusic139.NewMusic139Config;
import com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 *�񵥴�����
 */
public class New139BillboardBO extends New139BaseSynBO
{
	private static final JLogger logger = LoggerFactory
	.getLogger(New139BillboardBO.class);
	private String baseBillbroadCategoryId;
private String billboard_PIC;
	
	/**
	 * 
	 *@desc  ��ʼ�� 
	 *@author dongke
	 *Apr 30, 2011
	 */
	public  New139BillboardBO()
	{
		baseBillbroadCategoryId = NewMusic139Config.getInstance().getBaseBillboardCategoryId();
		billboard_PIC = NewMusic139Config.getInstance().getNewBillboardPicUrl();
	}

	
/**
 * 
 *@desc �ֶ�ִ�����
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
			result = dealbillboardData();
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
		
		sendMail(sb.toString(),"������139���ְ��ļ�����ʼ�");
		

	}
	
	
	/**
	 * 
	 *@desc ��139���ְ񵥵���  �Զ��������
	 *@author dongke
	 *Apr 30, 2011
	 * @return
	 * @throws BOException 
	 * @throws Exception
	 */
	public Integer[] dealbillboardData() throws BOException {
		String bilboradFileName = NewMusic139Config.getInstance().getNewBillboardFileName();
		String [] files = {bilboradFileName};
		fileNameRex = files;
		
		//result [0] �ɹ�����;result [1] ʧ�ܴ���;result [2] У��ʧ��
		Integer [] result = {new Integer(0),new Integer(0),new Integer(0)};
		List billboardList;
		try
		{
			billboardList = input139Data();
			this.ChecketAndDealData(billboardList,result);
		} catch (Exception e)
		{
			logger.error("��ȡ�ļ�ʧ��"+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException("��ȡ���ļ�ʧ��"+e);
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
				String[] billboardline = (String[]) al.get(i);
				if (billboardline != null)
				{
					if (billboardline.length != 6)
					{
						logger.warn("�����ݼ�������ֶ�����Ϊ6;���Ը��У�" + billboardline);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (!"1".equals(billboardline[5]) && !"2".equals(billboardline[5]))
					{
						logger.warn("������������:\r\n" + billboardline[5]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (billboardline[0] == null || billboardline[0].length() == 0
							|| billboardline[0].length() > 30)
					{
						logger.warn("����һ�����ݲ���������IDΪ�ջ򳤶Ȳ����ϱ�׼ 30��" + billboardline[0]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (billboardline[1] == null || billboardline[1].length() == 0
							|| billboardline[1].length() > 100)
					{
						logger.warn("����һ�����ݲ�������������Ϊ�ջ򳤶Ȳ����ϱ�׼ 100 " + billboardline[1]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (billboardline[3] == null || billboardline[3].length() == 0
							|| billboardline[3].length() > 30)
					{
						logger.warn("����һ�����ݲ���������ͼƬΪ�ջ򳤶Ȳ����ϱ�׼ 30 " + billboardline[3]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}if("1".equals(billboardline[5])){
						//�����޸ĵ�ǰ����
						if (billboardline[4] == null || billboardline[4].length() <= 0)
						{
							logger.warn("����һ�����ݲ��������񵥸����б�Ϊ��  " + billboardline[4]);
							al.remove(i);
							i--;
							checkfaildnum++;
							continue;
						}
					}
					
					logger.debug("��" + i + "�����ݼ�����,��ʼ��⴦��");
					int dealResult = dealLinebillboardData(billboardline);
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
    *@desc ���ݴ���,��һ�а��������
    *@author dongke
    *Apr 30, 2011
    * @param line
    * @return 1�� success;0 failed
    */
    
    public int dealLinebillboardData(String[] line) 
	{
		String billboardId = line[0];
		String billboardName = line[1];
		String changeType = line[5];
		 TransactionDB tdb = null;
		try{
			
			 // �����������
	            tdb = TransactionDB.getTransactionInstance();
	            New139BaseSyncDAO dao = New139BaseSyncDAO.getTransactionInstance(tdb);
	            
		if (changeType.equals("1"))
		{// �����޸�
			String categoryId = New139BaseSyncDAO.getInstance().getCategoryId(billboardId,baseBillbroadCategoryId);
			if(categoryId == null || categoryId.equals("")){
				//��ȡ����������
				categoryId =  new Integer(New139BaseSyncDAO.getInstance().getNewCategryId()).toString();
				String [] musicIdList = line[4].split(",");
				
				if(musicIdList != null && musicIdList.length>0){
					Set s = new HashSet();//ȥ������
					for(int i = 0; i < musicIdList.length; i ++){
						if(musicIdList[i] != null){
							if(s.contains(musicIdList[i])){
								//�ظ��������ϼܵ�ͬһ������
								logger.debug("�ظ��������ϼܵ�ͬһ������,billboardId="+billboardId);
								continue;
							}
							s.add(musicIdList[i]);
							String songname = New139BaseSyncDAO.getInstance().getMusicName(musicIdList[i]);
							if(songname == null ||songname.equals("")){
								logger.debug("�Ҳ�������ID="+musicIdList[i]+"��Ӧ����������,billboardId="+billboardId);
								continue;
							}
							Object [] paras = {musicIdList[i],categoryId,songname,new Integer(i+1)};
							dao.insertMusicRefence(paras);
						}
						
					}
//					insert into t_mb_category_new 
					//(CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME,lupddate, CATEGORYDESC, SORTID, SUM, ALBUM_ID, ALBUM_PIC, RATE, ALBUM_SINGER)values 
					//                    (?, ?, ?, '1', '0', to_char(sysdate,'yyyy-MM-dd hh24:mm:ss'),to_char(sysdate,'yyyy-MM-dd hh24:mm:ss'), ?, 0, 0, ?, ?, 0, '');
					Object[] cayInsertParas = {categoryId,billboardName,baseBillbroadCategoryId,line[2],billboardId};
					dao.insertBillboard(cayInsertParas);
					 tdb.commit();
				}
				
			}
				else
				{
					// ��ȡ�����޸�:���¼ܸû�����������
					logger.debug("�޸�:���¼ܸû�����������,billboardId="+billboardId);
					String[] musicIdList = line[4].split(",");
					if (musicIdList != null && musicIdList.length > 0)
					{
						Set s = new HashSet();//ȥ������
						//:���¼ܸû�����������
						dao.deleteMusicCategoryRefence(categoryId);
						for (int i = 0; i < musicIdList.length; i++)
						{
							if (musicIdList[i] != null)
							{
								if(s.contains(musicIdList[i])){
									//�ظ��������ϼܵ�ͬһ������
									logger.debug("�ظ��������ϼܵ�ͬһ������,billboardId="+billboardId);
									continue;
								}
								s.add(musicIdList[i]);
								String songname = New139BaseSyncDAO.getInstance()
										.getMusicName(musicIdList[i]);
								if(songname == null ||songname.equals("")){
									logger.debug("�Ҳ�������ID="+musicIdList[i]+"��Ӧ����������,billboardId="+billboardId);
									continue;
								}
								
									Object[] paras = { musicIdList[i], categoryId,
											songname,new Integer(i+1) };
									dao.insertMusicRefence(paras);
								
							}

						}
						//update  T_MB_CATEGORY_new t set t.lupddate=to_char(sysdate,'yyyy-mm-DD hh24:mm:ss'),
						//t.CATEGORYNAME=?,t.CATEGORYDESC=? where t.CATEGORYID=? and t.billboard_ID=?
						Object[] cayUpdateParas = {billboardName,line[2],categoryId,line[0]};
						dao.updateBillboard(cayUpdateParas);
						 tdb.commit();
					}
				}
		}
		else if (changeType.equals("2"))
		{//ɾ��

			String categoryId = New139BaseSyncDAO.getInstance().getCategoryId(billboardId,baseBillbroadCategoryId);
			if(categoryId != null &&! categoryId.equals("")){
				dao.delete139Category(baseBillbroadCategoryId,billboardId);
				dao.deleteMusicCategoryRefence(categoryId);
				 tdb.commit();
			}			
		}
		return 1;//���سɹ���־
		}catch(Exception e){
			 tdb.rollback();
			 logger.error("����ʧ��",e);
			e.printStackTrace();
			return 0;//����ʧ�ܱ�־
		}finally
        {
            if (tdb != null)
            {
                tdb.close();
            }
        }
	}
	
}
