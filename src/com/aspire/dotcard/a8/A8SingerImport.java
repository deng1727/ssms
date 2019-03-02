package com.aspire.dotcard.a8;

import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.StringTool;

public class A8SingerImport extends A8DataImport
{
	private static final JLogger logger = LoggerFactory.getLogger(A8SingerImport.class);
	

	public A8SingerImport() throws BOException
	{
		super();
		// TODO Auto-generated constructor stub
	}

	void init()
	{
		this.ftpDir =A8ParameterConfig.A8SingerDir;
		this.localDir=getPathWithSlash(A8ParameterConfig.BackupDir)+A8ParameterConfig.A8SingerDir;
		this.title="A8���ֵ���";
		String date = PublicUtil.getDateString(new Date(), "yyyyMMdd");
		this.regex="A8_singer_"+date+"\\.txt";

	}

	int insertDB(Object object)
	{
		SingerVO vo = ( SingerVO ) object;
        // ���½���������ȫ��ID��Ӧ�ļ�¼�Ӵ�ɾ����¼�б����Ƴ�
        boolean result = dbList.remove(vo.getId());
        // ���سɹ���˵����Ӧ�ĸ��������ݿ��д��ڣ���Ҫ����
        if (result)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("����ID " + vo.getId() + " ������ϵͳ�У���Ҫ���£�");
            }
            return A8ImportDAO.getInstance().updateSinger(vo);
        }
        // ����ʧ�ܣ�˵����Ӧ�ĸ��ּ�¼�����ݿ��в����ڣ���Ҫ����
        else
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("ȫ��ID " + vo.getId() + " ����ϵͳ�У���Ҫ������");
            }
            return A8ImportDAO.getInstance().insertSinger(vo);
        }
	}

	Object transformVOByLineText(String line, int lineNumber)
	{
		if (logger.isDebugEnabled())
        {
            logger.debug("transformVOByLineText is starting.....");
        }
        String[] arrColorContent = line.split("\\|");
        int size = arrColorContent.length;
        if (size != 5)
        {
            logger.error(line + " ��ʽ���󣬴������������޷����룡");
            return null;
        }
        SingerVO vo = new SingerVO();
      //�жϸ���ID�Ƿ�ֻ������ĸ������
        if (!arrColorContent[0].matches("[0-9A-Za-z]{1,25}"))
        {
            logger.error("����ID " + arrColorContent[0] + " ֻ�ܺ������ֺ���ĸ������󲻳���25���ַ������������޷����룡");
            return null;
        }
        if(StringTool.lengthOfHZ(arrColorContent[1])>50)
        {
        	logger.error("��������"+arrColorContent[1]+"���ܳ���50���ַ������������޷�����");
        	return null;
        }
        if(StringTool.lengthOfHZ(arrColorContent[2])>40)
        {
        	logger.error("���ֵ���"+arrColorContent[2]+"���ܳ���40���ַ������������޷�����");
        	return null;
        }
        if(StringTool.lengthOfHZ(arrColorContent[2])>40)
        {
        	logger.error("��������"+arrColorContent[2]+"���ܳ���40���ַ������������޷�����");
        	return null;
        }
        if(StringTool.lengthOfHZ(arrColorContent[2])>50)
        {
        	logger.error("��������ĸ"+arrColorContent[2]+"���ܳ���50���ַ������������޷�����");
        	return null;
        }
        while(--size >= 0)
        {
        	if(size==3)
        	{
        		continue;//�������Ϳ���Ϊ��
        	}
            if("".equals(arrColorContent[size].trim()))
            {
                logger.error(line + " ���п��ֶΣ����������޷����룡");
                return null;
            }
        }
        // ����VO���ֶ�ֵ��ע��ID�����ر���ǰ������ַ���"a8"
        vo.setId(arrColorContent[0].trim());
        vo.setName(arrColorContent[1].trim());
        vo.setRegion(arrColorContent[2].trim());
        vo.setType(arrColorContent[3].trim());
        //����������ԣ�����pps��ѯ����
        vo.setSingerZone(vo.getRegion()+vo.getType());
        vo.setFirstLetter(arrColorContent[4].trim());
        return vo;
	}

	List getDBDate() throws DAOException
	{
		return A8ImportDAO.getInstance().getAllSingerIdS();
	}

	void deleteFromDB(List list)
	{
		int result=A8ImportDAO.getInstance().deleteSinger(list);
		if(result==A8ParameterConfig.success_delete)
		{
			successDelete+=list.size();
		}else
		{
			failureCount=list.size();
		}
	}
	Object getVOKey(Object object)
	{
		return ((SingerVO)object).getId();
	}

}
