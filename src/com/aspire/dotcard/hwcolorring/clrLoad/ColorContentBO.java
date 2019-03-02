package com.aspire.dotcard.hwcolorring.clrLoad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.common.util.FileUtils;
import com.aspire.dotcard.hwcolorring.clrConvert.DealOneColorring;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.StringTool;

/**
 * <p>
 * ����ҵ�����ݵ���
 * </p>
 * <p>
 * Copyright (c) ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 *
 * @author zhangrihui
 */

public class ColorContentBO
{

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory.getLogger(ColorContentBO.class);

	/**
	 * ����������־����
	 */
	private static final JLogger synLog = LoggerFactory.getLogger("colorring.syn");
	/**
	 * ��������ת����־����
	 */
	private static final JLogger convertLog = LoggerFactory
			.getLogger("colorring.convert");

	private TaskRunner updateTaskRunner;
	private TaskRunner convertTaskRunner;
	private String RefDataTime;
	/**
	 * ����ת��������ֵ
	 */
	private int convertIndex = 0;
	/**
	 * ������ڵ���ܶ������ڻ��档
	 */
	public static Category rootColorCagetory;
	/**
	 * ���ݴ洢���ڵ���ܶ���
	 */
	public static Category rootContentNode;
	/**
	 * ���β��嵼���ͬ�����
	 */
	public String syncResult;

	/**
	 * ���췽������singletonģʽ����
	 */
	private ColorContentBO()
	{

	}

	/**
	 * singletonģʽ��ʵ��
	 */
	private static ColorContentBO colorContentBO = new ColorContentBO();

	/**
	 * ��ȡʵ��
	 *
	 * @return ʵ��
	 */
	public static final ColorContentBO getInstance()
	{

		return colorContentBO;
	}

	/**
	 * ����ȫ�������ļ�
	 * @param fileName String
	 * @throws Exception
	 * @return List
	 */
	public void colorringFullImport(String fileName, String ftpPath, String bakPath,
			String RefDataTime) throws BOException

	{
		syncResult=null;
		int addCount = 0;
		int updateCount = 0;
		int delCount = 0;
		int lineNum = 0;
		int snum = 0;
		convertIndex = 0;
		if (logger.isDebugEnabled())
		{
			logger.debug("ColorringFullImport()");
			logger.debug("the file name =" + fileName);
		}
		this.RefDataTime = RefDataTime;
		String fullPathFilename = ftpPath + "/" + fileName;
		initDate();
		// ��ȡ�ļ�����
		//Object[] arrColorContent = readAllFileLine(fullPathFilename);
		//�õ�ϵͳ��ǰʱ��
		String currentTime = PublicUtil.getCurDateTime();
		HashMap existsColor = null;
		try
		{
			existsColor = ColorContentDAO.getInstance().getAllColorringID();
		} catch (DAOException e1)
		{
			logger.error("�����ݿ��л�ȡ���в���IDʱ�������ݿ��쳣��", e1);
			throw new BOException("�����ݿ��л�ȡ���в���IDʱ�������ݿ��쳣");
		}
		FileReader fr = null;
		BufferedReader br = null;
		try
		{

			fr = new FileReader(fullPathFilename);
			br = new BufferedReader(fr);

			String lineText;// = br.readLine();

			while ((lineText = br.readLine()) != null)
			{
				lineText = lineText.trim();
				if (lineText.length() == 0)
				{
					continue;
				}else{
					snum ++;
				}
				lineNum++;
				if(logger.isDebugEnabled())
				{
					logger.debug("#######################��ʼ�����"+lineNum+"������");
				}
				GColorring clrVO=getGColorringVOByline(lineText, currentTime);
				ColorSyncVO syncVO = (ColorSyncVO) existsColor.get(clrVO.getId());
				boolean expirebo = true;
				String expired = clrVO.getExpire();
				String nowdate = PublicUtil.getCurDateTime("yyyyMMdd");
				if(nowdate.compareTo(expired)>0)
				{
					expirebo= false;
				}
				if(expirebo)
				{
					//��Ч��û�й���
					if (syncVO == null  )//����ϵͳ�����ڣ���Ҫ����,����û�й���Ч��
					{
						this.InsertContent(clrVO);
						this.convertColorring(clrVO);
						existsColor.put(clrVO.getId(), new ColorSyncVO(clrVO.getLupdDate(),2));//�������flag
						addCount++;
					}
					else if (syncVO.getFlag() == 0 )//��һ�η��ʡ�
					{
						if(clrVO.getLupdDate().compareTo(syncVO.getLupDate())>0)
						{
							this.updateDBColorring(clrVO, RefDataTime);
							this.convertColorring(clrVO);//����Ҳ��Ҫת�����ݵġ�
							updateCount++;
						}else if(!syncVO.isConvert())//���û��ת���ɹ����ͼ���ת��
						{
							this.convertColorring(clrVO);
						}
						syncVO.setFlag(1);//��ס�Ѿ�����
						
					}
				}
			
			}

		} catch (IOException e)
		{
			throw new BOException("��ȡ�����ļ��쳣��fileName=" + fileName);
		} finally
		{
			try
			{
				if (br != null)
				{
					br.close();
				}
				if (fr != null)
				{
					fr.close();
				}

			} catch (IOException e)
			{
				logger.error("�رղ����ļ��쳣", e);
			}

		}
		//��ֹ���ļ����������������£������е���������ɾ����
		//int processCount=addCount+updateCount;
		int processCount=snum;
		if(processCount>0)
		{
			//������Ҫɾ���ģ�
			for (Iterator ite = existsColor.keySet().iterator(); ite.hasNext();)
			{
				String colorId = (String) ite.next();
				ColorSyncVO temp = (ColorSyncVO) existsColor.get(colorId);
				if (temp.getFlag() == 0)
				{
					this.deleteDBColorring(colorId);
					delCount++;
				}
			}
		}
		updateTaskRunner.waitToFinished();//�ȴ��������ݿ���ϡ�
		updateTaskRunner.end();//����������
		/*  
		// ����������ת�ɶ���������
		List arrColorList = new ArrayList();
		
		//�����ȡ����������Ϣ�ӿ��ļ������ö�Ӧ��������Ϣ�����ݷֽⷽʽ
		int[] p = new int[iDataNum];
		for (int i = 0; i < iDataNum; i++)
		{
		    arrColorList.add(getGColorringVOByline(( String ) arrColorContent[i],
		                                          currentTime));
		}
		//��ѯ���ݿ��д��ڵ����в���ID�������õ�һ��list��
		HashMap existsColor;
		try
		{
		    existsColor = ColorContentDAO.getInstance().getAllColorringID();
		}
		catch (DAOException e1)
		{
		    logger.error("�����ݿ��л�ȡ���в���IDʱ�������ݿ��쳣��", e1);
		    throw new BOException("�����ݿ��л�ȡ���в���IDʱ�������ݿ��쳣");
		}
		//�Ӳ���ƽ̨��ȡ�Ĳ�����Ŀ
		int out = arrColorList.size();
		
		// ���и��£�������ɾ���ĺ˶�
		List addList = new ArrayList();
		List updateList = new ArrayList();
		//��������ת���б�
		List convertList = new ArrayList();
		//���������ݿ��еļ�¼
		GColorring dbColor = null; 
		for (int i = 0; i < out; i++)
		{
		    GColorring clrVO = ( GColorring ) arrColorList.get(i);
		    // ���½��������Ĳ���ID��Ӧ�ļ�¼�Ӵ�ɾ����¼�б����Ƴ�
		    boolean result = existsColor.remove(clrVO.getId());
		    // ���سɹ���˵����Ӧ�Ĳ����¼�����ݿ��д���
		    if (result)
		    {
		        if (logger.isDebugEnabled())
		        {
		            logger.debug("�������� " + clrVO.getContentID() + " ��Ӧ�Ĳ��������ϵͳ�У���Ҫ���£�");
		        }
		        dbColor = ( GColorring ) Repository.getInstance()
		                                           .getNode(clrVO.getId(),
		                                                    GColorring.TYPE_COLORRING);
		     
		        updateList.add(clrVO);
		        if (null == dbColor)
		        {
		            // �����ݿ���û�гɹ���ȡ����Ӧ�Ĳ��壬Ĭ�ϲ���ת�������±Ƚ���һ�ײ���
		            continue;
		        }
		        // ���ͬ�������Ų������ݵ�������ʱ�����ϵͳ�д��ڵĲ������ݵ�������ʱ�䣬��Ҫ����ת�������ļ�
		        if (clrVO.getLupdDate().compareTo(dbColor.getLupdDate()) > 0
		            || "".equals(dbColor.getClientAuditionUrl()))
		        {
		            clrVO.setClientAuditionUrl(dbColor.getClientAuditionUrl());
		            convertList.add(clrVO);
		        }
		    }
		    // ����ʧ��
		    else
		    {
		        addList.add(clrVO);
		    }
		}
		
		

		//��������������Ҳ����ת���б���
		convertList.addAll(addList);
		// ������־ͳ��
		int addCount = addList.size();
		int updateCount = 0;
		int delCount = 0;
		// ��t_r_gcontent�����������ݲ��ϼ�
		p = checkAndInsert(addList);
		int iFailNum = 0;
		for (int i = 0; i < p.length; i++)
		{
		    iFailNum = iFailNum + p[i];
		}
		addCount = addCount - iFailNum;
		
		//���²�����Ϣ
		updateCount = this.updateDBColorring(updateList);
		//ɾ��ȫ������������û�еļ�¼
		delCount = this.deleteDBColorring(existsColor);
		iFailNum = iFailNum + (updateList.size() - updateCount)
		           + (existsColor.size() - delCount);*/

		syncResult=new StringBuffer().append("����ִ�гɹ���һ���ɹ������������� ").append(addCount).append(
		"�������²������� ").append(updateCount).append("����ɾ���������� ").append(delCount)
		.append("����\n").toString();
		synLog.error("���β�������ͬ��������£�\n");
		synLog.error(syncResult);
		try
		{
			// ִ���굼����ļ����Ƶ�����Ŀ¼
			FileUtils.copy(ftpPath, bakPath, fileName);
			// ɾ�����ԭ�ļ�
			new File(fullPathFilename).delete();
		} catch (Exception e)
		{
			logger.debug("�����ļ���ɾ��Դ�ļ�����", e);
		}
		//������Ҫ���в�������ת���������б�
		// return convertList;
	}

	/**
	 * ��ÿһ���������ݷֽ⣬���õ��������VO��ȥ
	 */
	private static GColorring getGColorringVOByline(String recordContent,
			String currentTime)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getGColorringVOByline is starting.....");
		}
		String[] arrColorContent = recordContent.split("\\|");
		GColorring gColorringVO = new GColorring();
		// ����VO���ֶ�ֵ��ע��ID�����ر���ǰ������ַ���"clr"
		gColorringVO.setId("clr" + arrColorContent[0]);
		gColorringVO.setName(StringTool.formatByLen(arrColorContent[1], 200, "..."));
		gColorringVO.setTonenameletter(arrColorContent[2]);
		gColorringVO.setSinger(StringTool.formatByLen(arrColorContent[3], 197, "..."));
		gColorringVO.setSingerletter(arrColorContent[4]);
		gColorringVO.setIntroduction(arrColorContent[5]);
		gColorringVO.setPrice(modifyPrice(arrColorContent[6]));
		gColorringVO.setLupdDate(arrColorContent[7]);
		gColorringVO.setDownloadtimes(Integer.parseInt(arrColorContent[8]));
		gColorringVO.setSettimes(Integer.parseInt(arrColorContent[9]));
		gColorringVO.setAuditionUrl(arrColorContent[10]);
		gColorringVO.setTonebigtype(arrColorContent[11]);
		gColorringVO.setCateName(arrColorContent[12]);
		gColorringVO.setExpire(arrColorContent[13]);
		//���������ṩ��������û�е��ֶ�
		gColorringVO.setCreateDate(currentTime);
		gColorringVO.setMarketDate(currentTime);
		//���岥��ʱ��
		gColorringVO.setAverageMark(0);
		//������������Ϊ��������
		gColorringVO.setContentID(arrColorContent[0]);
		return gColorringVO;
	}
	/**
	 * �ʷѳ���10�����ݱ���ĵ�λΪ"��"
	 * @param price
	 * @return
	 */
	private static String modifyPrice(String price)
	{
		int newPrice= Integer.parseInt(price.trim());
		return String.valueOf(newPrice*10);
	}
	/**
	 * ��һ�Ĳ�������¼
	 */
	private void InsertContent(GColorring gColorringVO)
	{
		updateTaskRunner.addTask(new ColorringAddTask(gColorringVO));
	}

	/**
	 * ���²�����Ϣ,���ö��߳�����첽���¡�
	 * 
	 * @param vo ������Ϣ��vo
	 * @return int �ɹ����²������ݵĸ���
	 */
	public void updateDBColorring(GColorring vo, String RefDataTime)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateDBColorring() id=" + vo.getId());
		}
		ColorringUpdateMulti cm = new ColorringUpdateMulti(vo);
		//�����첽����
		ReflectedTask task = new ReflectedTask(cm, "updateColorring", null, null);
		//������ӵ���������
		updateTaskRunner.addTask(task);
	}

	public void convertColorring(GColorring clrVO)
	{
		convertLog.error(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")
				+ "����������ת����ʼ������");
		/*
		//ͳһ��������ƽ̨���ʵ�ַ
		String UCAPUrl = "";
		//��������ת�����ݸ�ʽ������������
		String[] code_abps = null;
		//��Դ����������ת����Ĳ�����Ŀ¼�����ftp��¼���Ŀ¼��
		String clrConvertPath = "";
		//��Դ�������ķ��ʷ�ʽ������������apache����·��
		String sourceServerVisit = "";
		//��������ת���������첽������
		int taskNum = 50;
		try
		{
		    UCAPUrl = ColorringConfig.get("UCAPUrl");
		    code_abps = ConfigFactory.getSystemConfig().getModuleConfig("colorring").getItemValueList("code_abps");
		    clrConvertPath = ColorringConfig.get("clrConvertPath");
		    sourceServerVisit = ColorringConfig.get("sourceServerVisit");
		    taskNum = Integer.parseInt(ColorringConfig.get("ColorringConvertTaskNum"));
		}
		catch (Exception e1)
		{
		    convertLog.error("�Ӳ����������л�ȡ����ת������UCAPUrl��code_abps��ColorringConvertTaskNumʱ�����쳣�����������ļ�ת��ֹͣ��", e1);
		    return;
		}*/
		//��ǰ�����ַ���·���²��������ļ��Ĵ��·������Ϊlinux/unix������ÿ��Ŀ¼�µ��ļ��������Ƶ�
		// int path 
		//�ƻ�ÿ���ļ����������60000�������ļ���һ������Ŀǰ��Ҫת������
		/* if (i % 30000 == 0)
		 {
		     path++;
		 }*/
		convertIndex++;
		int path = convertIndex / 30000;
		//������������ļ����ռ�����Ŀ¼
		String tmp = new StringBuffer().append(ColorringConfig.clrConvertPath).append(
				File.separator).append(RefDataTime).append(File.separator).append(
				PublicUtil.lPad("" + path, 3)).toString();
		if (convertLog.isDebugEnabled())
		{
			convertLog.debug("���������ļ��Ĵ���Ŀ¼�ǣ�" + tmp);
		}
		//�����Դ����������ת����Ĳ�����Ŀ¼����ftp��½���Ŀ¼������Ҫʹ��File.separator�ָ�
		if ("./".equals(ColorringConfig.clrConvertPath))
		{
			tmp = tmp.substring(3);
		}
		//��������ն�����urlΪ�գ�˵���ǳ���ת��
		if ("".equals(clrVO.getClientAuditionUrl()))
		{
			// ֱ�����ò����ն������ļ���ַ
			clrVO.setClientAuditionUrl(new StringBuffer().append(
					ColorringConfig.sourceServerVisit).append(tmp).append(File.separator)
					.append(clrVO.getContentID()).toString());
		}
		//������Ҫ����ת����ת������Ҫ����ԭ����
		else
		{
			String clientAuditionUrl = clrVO.getClientAuditionUrl();
			tmp = clientAuditionUrl.substring(ColorringConfig.sourceServerVisit.length(),
					clientAuditionUrl.lastIndexOf(File.separator));
		}
		if (convertLog.isDebugEnabled())
		{
			convertLog.debug("���������ļ����ռ�����Ŀ¼�ǣ�" + tmp);
		}
		DealOneColorring dealOneColorring = new DealOneColorring(clrVO,
				ColorringConfig.UCAPUrl, ColorringConfig.code_abps, tmp);
		//�����첽����
		ReflectedTask task = new ReflectedTask(dealOneColorring, "colorringConvert",
				null, null);
		//������ӵ���������
		this.convertTaskRunner.addTask(task);

		convertLog.error(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")
				+ "����������ת������������");
	}
	
	/**
	 * 
	 * @param expireDate
	 * @return
	 */
//private boolean checkExpireDate(String expireDate){
//	Date expired = DateUtil.stringToDate(expireDate,"yyyy-MM-dd HH:mm:ss");
//	Date nowdate = new Date();
//	if(expired.before(nowdate)){
//		return false;	
//	}
	
//	return true;
//}
	/**
	 * ɾ��ȫ�������ļ��в����ڶ��������ݿ�Ĳ������ݣ�ɾ��ǰ��Ҫ�����¼ܲ���
	 * 
	 * @param deleteList
	 * @return �ɹ�ɾ����ý�����
	 */
	private void deleteDBColorring(String clrID)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("deleteDBColorring(clrID)" + clrID);
		}
		updateTaskRunner.addTask(new ColorringDeleteTask(clrID));

	}

	private void initDate()throws BOException
	{
		updateTaskRunner = new TaskRunner(ColorringConfig.ColorringUpdateTaskNum,
				ColorringConfig.ColorringMaxReceivedNum);
		convertTaskRunner =new TaskRunner(ColorringConfig.ColorringConvertTaskNum,ColorringConfig.ColorringMaxReceivedNum);
		rootColorCagetory=(Category) Repository.getInstance().getNode(
				RepositoryConstants.ROOT_CATEGORY_GCOLORRING_ID, RepositoryConstants.TYPE_CATEGORY);
		rootContentNode=(Category) Repository.getInstance().getNode(
				RepositoryConstants.ROOT_CONTENT_ID, RepositoryConstants.TYPE_CATEGORY);
		
	}
	public TaskRunner getConvertTaskRunner()
	{
		return convertTaskRunner;
	}
}
