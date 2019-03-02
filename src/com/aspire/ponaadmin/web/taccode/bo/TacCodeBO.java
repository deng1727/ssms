package com.aspire.ponaadmin.web.taccode.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;
import com.aspire.ponaadmin.web.taccode.dao.TacCodeDAO;
import com.aspire.ponaadmin.web.taccode.vo.TacVO;

public class TacCodeBO {

	/**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(TacCodeBO.class);

    private static TacCodeBO instance = new TacCodeBO();

    private TacCodeBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static TacCodeBO getInstance()
    {
        return instance;
    }
    
    /**
     * ���ڲ�ѯTAC����б�
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryTacCodeList(PageResult page, TacVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacCodeBO.queryTacCodeList() is start...");
        }

        try
        {
            // ����TacCodeDAO���в�ѯ
            TacCodeDAO.getInstance().queryTacCodeList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯTAC����б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ����ɾ��ָ��TAC��
     * 
     * @param id
     * @param tacCode
     */
    public void delByTacCode(String id,String tacCode) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacCodeBO.delByTacCode() is start...");
        }

        try
        {
            // ����TacCodeDAO
        	TacCodeDAO.getInstance().delByTacCode(id,tacCode);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�Ƴ�ָ���ص����ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ����TAC����б�
     * 
     * @param dataFile
     * @throws BOException
     */
    public String importTacCode(FormFile dataFile) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("TacCodeBO.importTacCode() is start...");
        }

        StringBuffer ret = new StringBuffer();

        List list = new ArrayList();
        try
        {
        	
            list = new ExcelHandle().paraseDataFile(dataFile, null);
        }
        catch (BOException e)
        {
        	logger.error("TAC����б���,Excel�����쳣!", e);
            throw new BOException("TAC����б���,Excel�����쳣!");
        }
        
        if(list == null || list.size() < 1)
        	throw new BOException("TAC����б���,Excel�ļ�Ϊ��!");
        
        try
        {
            // ��ȡtacCode�б�
            Map tacCodeMap = TacCodeDAO.getInstance().getTacCodeMap();
            int success = 0;//��¼�ɹ���������
            int fail = 0;//��¼ʧ�ܵ�������
            StringBuffer failRows = new StringBuffer();//��¼ʧ�ܵ���
            ArrayList<Object[]> insertList = new ArrayList<Object[]>();//������������
            ArrayList<Object[]> updateList = new ArrayList<Object[]>();//�����������
            for(int i =0;i < list.size();i++){
            	Map map = (Map)list.get(i);
            	if(null == map || 5 != map.size()){//����������
            		fail++;
            		failRows.append(i+",");
            		continue;
            	}
            	//Excel��5�м�¼������Ϊ��
            	if(null == map.get(0)||"".equals(map.get(0))||null == map.get(1)||"".equals(map.get(1))
            			||null == map.get(2)||"".equals(map.get(2))||null == map.get(3)||"".equals(map.get(3))
            			||null == map.get(4)||"".equals(map.get(4))){
            		fail++;
            		failRows.append(i+",");
            		continue;
            	}
            	//���ھ͸���
            	if(tacCodeMap.containsKey(map.get(2))){
            		//��ӵ�˳���Ǳ���һ��Ϊ����ID,�ڶ���Ϊ������,������ΪTAC��,������Ϊ�ֻ�Ʒ��,������Ϊ�ֻ��ͺ�
            		updateList.add(new Object[]{map.get(1).toString().trim(),map.get(2),map.get(0),map.get(3),map.get(4)});
            	}else{
            		insertList.add(new Object[]{map.get(1).toString().trim(),map.get(2),map.get(0),map.get(3),map.get(4)});
            	}
            	if(insertList.size() >= 5000){//5000��ִ��һ�����ݿ����
            		success += TacCodeDAO.getInstance().patchImportData("1",insertList);
            		insertList.clear();
            		insertList = new ArrayList<Object[]>();
            	}
            	if(updateList.size() >= 5000){//5000��ִ��һ�����ݿ����
            		success += TacCodeDAO.getInstance().patchImportData("2",updateList);
            		updateList.clear();
            		updateList = new ArrayList<Object[]>();
            	}
            }

            //����δִ�е������ڵ���������
            if(null != insertList && insertList.size() > 0){
            	success += TacCodeDAO.getInstance().patchImportData("1",insertList);
            	insertList.clear();
            	insertList = new ArrayList<Object[]>();
            }
            if(null != updateList && updateList.size() > 0){
            	success += TacCodeDAO.getInstance().patchImportData("2",updateList);
        		updateList.clear();
        		updateList = new ArrayList<Object[]>();
            }
            
            ret.append("�ܹ����룺"+list.size()+"����¼,");
            ret.append("�ɹ�����" + success + "����¼,");
            if(fail > 0){
            	ret.append("ʧ�ܵ���" + fail + "����¼,");
            	ret.append("����ʧ���У�"+failRows.toString());
            }
            return ret.toString();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����TAC����б�ʱ�������ݿ��쳣��");
        }
    }
}
