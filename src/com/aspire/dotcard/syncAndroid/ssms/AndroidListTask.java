package com.aspire.dotcard.syncAndroid.ssms;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.ponaadmin.web.category.intervenor.IntervenorVO;
import com.aspire.ponaadmin.web.category.intervenor.category.IntervenorCategoryBO;
import com.aspire.ponaadmin.web.category.intervenor.gcontent.IntervenorGcontentVO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class AndroidListTask extends TimerTask
{
	
	/**
	 * ��־����
	 */
	protected static JLogger LOG = LoggerFactory
			.getLogger(AndroidListTask.class);
	
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		try
		{
			// ��һ����v_android_list��ͼ�̻� t_a_android_list_tmp
			LOG.info("��ʼ����t_a_android_list_tmp");
			createAndroidListTmp();
			
			LOG.info("��ʼ���t_a_android_list_compare�Ƿ�ȫ");
			LOG
					.info("t_a_android_list_compare�������Ҫ����5�򣬺�t_a_android_list��Ƚϼ�¼���仯��С��5ǧ");
			boolean flag = validate();
			if (!flag)
			{
				return;
			}
			LOG.info("ִ��updateRankNew");
			// add by aiyan 2013-07-02
			updateRankNew();// ͬһ��COMPANYID�����°񵥵��첻�ܳ��ֶ��Ӧ�ã����°�һ��Ӧ����һ�������һ�γ��֡�����
			
			updateIntervene();// ����ط����˹���Ԥ�Ĳ��֡���Ҫ�ǰ�t_a_intervene�����ݷ�Ӧ��t_a_android_list��ȥ��add
			// by aiyan 2013-07-15
			
			LOG.info("�Ƚ� t_a_android_list��t_a_android_list_tmp�ı仯����¼contentid");
			// �ڶ������Ƚ� t_a_android_list��t_a_android_list_tmp�ı仯����¼contentid
			compareChange();
			// ��������ɾ��t_a_android_list��
			// ��t_a_android_list_tmp����t_a_android_list��
			LOG.info("��t_a_android_list_tmp����t_a_android_list��");
			changeTableName();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			LOG.error("AndroidListTask:this error may let timer object stop!!",
					e);
		}
		
	}
	
	/**
	 * �˹���Ԥ��Ʒ�������Ʒ�ö�����
	 * @param androidCategory
	 * @param intervenor
	 * @return
	 */
	private String intervenorTopSyn(String androidCategory, IntervenorVO intervenor)
	{
		List<IntervenorGcontentVO> gcontentList = null;
		
		try
		{
			// �õ���ǰ��Ԥ�����б�
			gcontentList = IntervenorCategoryBO.getInstance().queryGcontentListByIntervenorIdAndroid(String.valueOf(intervenor.getId()));
		}
		catch (BOException e)
		{
			LOG.error("�õ���Ʒ����ܶ�Ӧ���˹���Ԥ�б�ʱ��������",e);
			return "�õ���Ʒ����ܶ�Ӧ���˹���Ԥ�б�ʱ��������";
		}
		
		// �õ���ǰ��Ʒ����������������ֵ
		long  number = getRandValueByCategory(androidCategory, intervenor.getStartSortId());
		if(number == -1L)
		{
			LOG.error("�õ���ǰ��Ʒ����������������ֵʱ��������");
			return "�õ���ǰ��Ʒ����������������ֵʱ��������";
		}
		number = number + 2L + gcontentList.size();
		
		// ���µ�ǰ��Ԥ�����б��Ӧ�Ļ�����Ʒ���� + �������ֵ+1
		for(IntervenorGcontentVO vo : gcontentList)
		{
			number = number-1L;
			updateRandValueByContent(androidCategory, vo.getContentId(), number);
		}
		
		return intervenor.getName()+"ִ�гɹ�";
	}
	
	/**
	 * �˹���Ԥ��Ʒ�������Ʒ�õײ���
	 * @param androidCategory
	 * @param intervenor
	 * @return
	 */
	private String intervenorEndSyn(String androidCategory, IntervenorVO intervenor)
	{
		List<IntervenorGcontentVO> gcontentList = null;
		
		try
		{
			// �õ���ǰ��Ԥ�����б�
			gcontentList = IntervenorCategoryBO.getInstance().queryGcontentListByIntervenorIdAndroid(String.valueOf(intervenor.getId()));
		}
		catch (BOException e)
		{
			LOG.error("�õ���Ʒ����ܶ�Ӧ���˹���Ԥ�б�ʱ��������",e);
			return "�õ���Ʒ����ܶ�Ӧ���˹���Ԥ�б�ʱ��������";
		}
		
		// �õ���ǰ��Ʒ�����������С����ֵ
		long  number = getRandValueByCategory(androidCategory, intervenor.getStartSortId());
		if(number == -1L)
		{
			LOG.error("�õ���ǰ��Ʒ����������������ֵʱ��������");
			return "�õ���ǰ��Ʒ����������������ֵʱ��������";
		}
		
		// ���µ�ǰ��Ԥ�����б��Ӧ�Ļ�����Ʒ���� + ������Сֵ-1
		for(IntervenorGcontentVO vo : gcontentList)
		{
			number = number-1L;
			updateRandValueByContent(androidCategory, vo.getContentId(), number);
		}
		
		return intervenor.getName()+"ִ�гɹ�";
	}
	
	/**
	 * �˹���Ԥ��Ʒ�������Ʒָ��λ�ò���
	 * @param androidCategory
	 * @param intervenor
	 * @return
	 */
	private String intervenorOtherSyn(String androidCategory, IntervenorVO intervenor)
	{
		List<IntervenorGcontentVO> gcontentList = null;
		
		try
		{
			// �õ���ǰ��Ԥ�����б�
			gcontentList = IntervenorCategoryBO.getInstance().queryGcontentListByIntervenorIdAndroid(String.valueOf(intervenor.getId()));
		}
		catch (BOException e)
		{
			LOG.error("�õ���Ʒ����ܶ�Ӧ���˹���Ԥ�б�ʱ��������",e);
			return "�õ���Ʒ����ܶ�Ӧ���˹���Ԥ�б�ʱ��������";
		}
		
		// �õ���ǰ��Ʒ����������������ֵ
		// ���λ
		long  startNumber = getRandValueByCategoryByIntervenor(androidCategory, intervenor.getStartSortId()-1, intervenor.getId());
		// ���λ
		long  endNumber = getRandValueByCategoryByIntervenor(androidCategory, intervenor.getStartSortId(), intervenor.getId());
		
		if(startNumber == -1L || endNumber == -1L)
		{
			LOG.error("�õ���ǰ��Ʒ����������������ֵʱ��������");
			return "�õ���ǰ��Ʒ����������������ֵʱ��������";
		}
		
		// ���µ�ǰ��Ԥ�����б��Ӧ�Ļ�����Ʒ���� + ������Сֵ-1
		for(IntervenorGcontentVO vo : gcontentList)
		{
			if(startNumber > endNumber+1L)
			{
				startNumber = startNumber-1L;
			}
			
			updateRandValueByContent(androidCategory, vo.getContentId(), startNumber);
		}
		
		return intervenor.getName()+"ִ�гɹ�";
	}
	
	/**
	 * �˹���Ԥ��Ʒ�������Ʒ˳��
	 */
	private void updateIntervene()
	{
		LOG.info("t_a_android_list���˹���Ԥ������");
		
		String[] typeCategory = new String[] { "rank_new_appGame",
				"rank_new_appSoftWare", "rank_hot_appGame",
				"rank_hot_appSoftWare", "rank_scores_appGame",
				"rank_scores_appSoftWare",
				"rank_new_appAll", "rank_all_appAll",
				"rank_scores_appAll", "rank_fee_appAllPriceFree",
				"rank_fee_appAllPricePay" };
		
		for (String androidCategory : typeCategory)
		{
			List<IntervenorVO> intervenorList;
			
			try
			{
				// �õ���ִ�и�Ԥ����
				intervenorList = IntervenorCategoryBO.getInstance()
						.getIntervenorCategoryVO(androidCategory);
			}
			catch (BOException e)
			{
				LOG.error("�õ���ǰ��Ʒ����˹���Ԥ�б�ʱ�������ݿ��쳣��", e);
				continue;
			}
			
			for (Iterator<IntervenorVO> iter = intervenorList.iterator(); iter
					.hasNext();)
			{
				// �õ���Ӧ��Ԥ��Ϣ
				IntervenorVO intervenor = (IntervenorVO) iter.next();
				int startid = intervenor.getStartSortId();
				String synText;
				
				// �жϸ�Ԥ����
				switch (startid)
				{
					// �ö�
					case IntervenorVO.TOP:
						synText = intervenorTopSyn(androidCategory,intervenor);
						break;
					// �õ�
					case IntervenorVO.END:
						synText = intervenorEndSyn(androidCategory,intervenor);
						break;
					// ָ��λ��
					default:
						synText = intervenorOtherSyn(androidCategory,intervenor);
						break;
				}
				LOG.info(synText);
			}
		}
		LOG.info("t_a_android_list���˹���Ԥ�Ѿ���ɡ�");
	}
	
	private void updateIntervene_old()
	{
		// TODO Auto-generated method stub
		String[] typeArr = new String[] { "rank_new", "rank_all", "rank_fee",
				"rank_hot", "rank_scores" };
		for (String type : typeArr)
		{
			List<InterveneVO> interveneList = getInterveneList(type);
			int max = 0;
			for (InterveneVO vo : interveneList)
			{
				if (max < vo.getSort())
					max = vo.getSort();
				
			}
			if (interveneList.size() == 0)
			{
				continue;
			}
			long[] a = new long[max + 2];// rank_xxx
			String[] b = new String[max + 2];// contentid
			final long falgA = -1;
			for (InterveneVO vo : interveneList)
			{
				a[vo.getSort()] = falgA;
				b[vo.getSort()] = vo.getContentid();
			}
			int needDBdataNum = 0;
			for (int i = 1; i < a.length; i++)
			{
				if (a[i] != falgA)
					needDBdataNum++;
			}
			List<RankVO> dbdataList = getDBdataList(type, needDBdataNum);
			int j = 0;
			for (int i = 1; i < a.length; i++)
			{
				if (a[i] != falgA)
				{
					RankVO vo = dbdataList.get(j++);
					a[i] = vo.getRank();
					b[i] = vo.getContentid();
				}
			}
			List<Integer> arr = new ArrayList<Integer>();
			for (int i = a.length - 1; i > 1; i--)
			{
				if (a[i - 1] == falgA)
				{
					a[i - 1] = a[i] + 1;
				}
				else if (a[i - 1] <= a[i])
				{
					a[i - 1] = a[i] + 1;
					arr.add(new Integer(i - 1));
				}
			}
			// System.out.println("++++++++++++++++++++++++");
			// for(int i=0;i<a.length;i++){
			// System.out.print(a[i]+"--");
			// }
			// System.out.println("++++++++++++++++++++++++");
			// for(int i=0;i<b.length;i++){
			// System.out.print(b[i]+"--");
			// }
			// System.out.println("++++++++++++++++++++++++");
			for (InterveneVO vo : interveneList)
			{
				updateRankValue(vo, a[vo.getSort()]);
			}
			if (arr.size() > 0)
			{
				for (int i = 0; i < arr.size(); i++)
				{
					SSMSDAO.getInstance().updateRankValue(type, b[arr.get(i)],
							a[arr.get(i)]);
				}
				
			}
			
		}
		LOG.info("t_a_android_list���˹���Ԥ�Ѿ���ɣ�����t_a_intervene������ɵġ�");
	}
	
	private void updateRandValueByContent(String categoryType, String contentId, long number)
	{
		SSMSDAO.getInstance().updateRandValueByContent(categoryType, contentId, number);
	}
	
	private long getRandValueByCategory(String categoryType, int startid)
	{
		return SSMSDAO.getInstance().getRandValueByCategory(categoryType, startid, 0);
	}
	
	private long getRandValueByCategoryByIntervenor(String categoryType, int startid, int intervenorId)
	{
		return SSMSDAO.getInstance().getRandValueByCategory(categoryType, startid, intervenorId);
	}
	
	private List<RankVO> getDBdataList(String type, int needDBdataNum)
	{
		// TODO Auto-generated method stub
		return SSMSDAO.getInstance().getDBdataList(type, needDBdataNum);
	}
	
	private void updateRankValue(InterveneVO vo, long l)
	{
		// TODO Auto-generated method stub
		SSMSDAO.getInstance().updateRankValue(vo.getType(), vo.getContentid(),
				l);
	}
	
	private List<InterveneVO> getInterveneList(String type)
	{
		// TODO Auto-generated method stub
		return SSMSDAO.getInstance().getInterveneList(type);
	}
	
	private void updateRankNew()
	{
		// TODO Auto-generated method stub
		SSMSDAO.getInstance().updateRankNew();
	}
	
	private boolean validate()
	{
		// TODO Auto-generated method stub
		
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"syncAndroid");
		int abs_android_list_num = 5000;// �¾ɱ��¼���ȱ仯5k;
		int min_android_list_num = 50000;// �񵥱���С��¼��5w
		try
		{
			abs_android_list_num = Integer.parseInt(module
					.getItemValue("abs_android_list_num"));
			min_android_list_num = Integer.parseInt(module
					.getItemValue("min_android_list_num"));
		}
		catch (Exception e)
		{};
		
		int rowNum_new = SSMSDAO.getInstance().getRowNum(
				"t_a_android_list_compare");
		int rowNum_old = SSMSDAO.getInstance().getRowNum("t_a_android_list");
		if (rowNum_new < min_android_list_num
				|| Math.abs(rowNum_new - rowNum_old) > abs_android_list_num)
		{
			LOG.error("rowNum_new<min_android_list_num-->"
					+ (rowNum_new < min_android_list_num));
			LOG
					.error("Math.abs(rowNum_new-rowNum_old)>abs_android_list_num-->"
							+ (Math.abs(rowNum_new - rowNum_old) > abs_android_list_num));
			LOG.error("ssms��t_a_android_list_compare�������Ҫ���ڣ�"
					+ min_android_list_num + "������t_a_android_list��Ƚϼ�¼���仯��С��("
					+ abs_android_list_num + ") ���У�鲻ͨ���������񵥽�û�б仯���鷳�����ԭ�򣡣���");
			
			StringBuffer sb = new StringBuffer();
			sb.append("<br>");
			sb.append("����ʱ�䣺");
			sb.append(PublicUtil.getDateString(new Date(),
					"yyyy-MM-dd HH:mm:ss"));
			sb.append("<br>");
			sb.append("<br>");
			sb.append("ssms��t_a_android_list_compare�������Ҫ���ڣ�"
					+ min_android_list_num + "������t_a_android_list��Ƚϼ�¼���仯��С��("
					+ abs_android_list_num + ") ���У�鲻ͨ���������񵥽�û�б仯���鷳�����ԭ�򣡣���");
			sb.append("<br>");
			sb.append("<br>");
			sb
					.append("��������ȷ����v_android_list�ļ�¼���Ƿ�����,ȷ��v_android_list��t_a_android_list�����߲����Ƿ����������ǣ�����߷�ֵ������ϵͳ��");
			LOG.error(sb.toString());
			// �����ʼ�����Ʒ���Ż�������ֵ������ֵ�����������߶�����������
			// String subject =
			// module.getItemValue("SYNC_ANDROID_LIST_SUBJECT");
			String subject = "�����ʼ�����Ʒ���Ż��񵥱��¼��Ŀ��֤��ͨ��������";
			// bihui@aspirecn.com,mm724@aspirecn.com,mm_gd@aspirecn.com,dongke@aspirecn.com,chenzili@aspirecn.com,wangjun@aspirecn.com,aiyan@aspirecn.com
			String[] mailTo = module.getItemValue("SYNC_ANDROID_MAILTO").split(
					",");
			String mailContent = sb.toString();
			Mail.sendMail(subject, mailContent, mailTo);
			
			return false;
		}
		LOG.info("ssms��t_a_android_list_compare�������Ҫ����(" + min_android_list_num
				+ ")����t_a_android_list��Ƚϼ�¼���仯��С��(" + abs_android_list_num
				+ ") ���У��ͨ����");
		LOG.info("t_a_android_list_compare������" + rowNum_new
				+ ",ǰ��仯���ǣ�rowNum_new-rowNum_old��" + (rowNum_new - rowNum_old));
		return true;
	}
	
	private void createAndroidListTmp() throws BOException
	{
		// TODO Auto-generated method stub
		try
		{
			SSMSDAO.getInstance().createAndroidListTmp();
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			throw new BOException("������t_a_android_list_tmp��ʱ����ִ���", e);
		}
	}
	
	private void compareChange()
	{
		// TODO Auto-generated method stub
		List<String> listAdd = SSMSDAO.getInstance().compareChangeAdd();
		List<String> listUpdate = SSMSDAO.getInstance().compareChangeUpdate();
		List<String> listDel = SSMSDAO.getInstance().compareChangeDel();
		// �õ���¼��t_a_messages
		// Contentid ���� String Ӧ��ID
		// Action ���� String 0��������ֱ�Ӹ���
		// 9��ɾ��
		// Transactionid ���� String ��������ID�����ڴ���ͬһ��������ʱʹ�á�
		for (String contentid : listAdd)
		{
			addMessages(contentid, "0");
		}
		for (String contentid : listUpdate)
		{
			addMessages(contentid, "0");
		}
		// ���ﱣ��һ�£�add by aiyan 2013-06-25
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"syncAndroid");
		int down_android_list_num = 5000;// ���߷�ֵĬ��5000;
		try
		{
			down_android_list_num = Integer.parseInt(module
					.getItemValue("down_android_list_num"));
		}
		catch (Exception e)
		{};
		
		if (listDel.size() > down_android_list_num)
		{
			
			LOG.error("ssms�ĵ�ʵʱ���ߴ���" + down_android_list_num + "������������߶�������");
			
			StringBuffer sb = new StringBuffer();
			sb.append("<br>");
			sb.append("����ʱ�䣺");
			sb.append(PublicUtil.getDateString(new Date(),
					"yyyy-MM-dd HH:mm:ss"));
			sb.append("<br>");
			sb.append("<br>");
			sb.append("ssms�ĵ�ʵʱ���ߴ��ڣ�" + down_android_list_num + "��������������߶�������");
			sb.append("��������ȷ���Ƿ���" + (listDel.size() + "���ߣ����ǣ���������߷�ֵ������ϵͳ��"));
			LOG.error("�����ʼ�����Ʒ���Ż�������ֵ������ֵ�����������߶�����������");
			LOG.error(sb.toString());
			// �����ʼ�����Ʒ���Ż�������ֵ������ֵ�����������߶�����������
			// String subject =
			// module.getItemValue("SYNC_ANDROID_LIST_SUBJECT");
			String subject = "�����ʼ�����Ʒ���Ż�������ֵ������ֵ�����������߶�����������";
			// bihui@aspirecn.com,mm724@aspirecn.com,mm_gd@aspirecn.com,dongke@aspirecn.com,chenzili@aspirecn.com,wangjun@aspirecn.com,aiyan@aspirecn.com
			String[] mailTo = module.getItemValue("SYNC_ANDROID_MAILTO").split(
					",");
			String mailContent = sb.toString();
			Mail.sendMail(subject, mailContent, mailTo);
			
		}
		else
		{
			LOG.info(new Date() + " ssms�ĵ�ʵʱ������Ŀ�ǣ�" + listDel.size());
			
			for (String contentid : listDel)
			{
				addMessages(contentid, "9");
			}
		}
		
	}
	
	private void addMessages(String contentid, String action)
	{
		// TODO Auto-generated method stub
		try
		{
			// SSMSDAO.getInstance().addMessages(contentid,action);
			SSMSDAO.getInstance().addMessages(MSGType.CountUpdateReq,
					contentid + ":" + action);
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			LOG.error(e);
		}
	}
	
	private void changeTableName() throws BOException
	{
		// TODO Auto-generated method stub
		try
		{
			SSMSDAO.getInstance().changeTableName();
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			throw new BOException(
					"����t_a_android_list_tmp��Ϊ��t_a_android_list����", e);
		}
		
	}
}
