package com.aspire.dotcard.baseVideo.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.dao.BlackDAO;
import com.aspire.dotcard.baseVideo.vo.BlackVO;
import com.aspire.dotcard.baseVideo.vo.ProgramBlackVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;

/**
 * ��Ƶ������ҵ����
 */
public class BlackBO
{
    /**
     * ��־����
     */
    protected static final JLogger LOG = LoggerFactory.getLogger(BlackBO.class);

    private static BlackBO instance = new BlackBO();

    private BlackBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static BlackBO getInstance()
    {
        return instance;
    }

    /**
     * ���ڲ�ѯ��ǰ�������б�
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryBlackList(PageResult page, ProgramBlackVO vo) throws BOException
    {
        try
        {
            BlackDAO.getInstance().queryBlackList(page, vo);
        }
        catch (DAOException e)
        {
            LOG.error("��Ƶ�������ݿ��쳣!", e);
            throw new BOException("��Ƶ�������ݿ��쳣!");
        }
    }

    /**
     * ���ڲ�ѯ��ǰ�����б�
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryContentList(PageResult page, BlackVO vo) throws BOException
    {
        try
        {
            BlackDAO.getInstance().queryContentList(page, vo);
        }
        catch (DAOException e)
        {
            LOG.error("��ѯ�������ݷ������ݿ��쳣!", e);
            throw new BOException("��ѯ�������ݷ������ݿ��쳣!");
        }
    }
    
    /**
     * ɾ��������
     * 
     * @param id
     * @throws BOException
     */
    public void removeBlack(String[] id) throws BOException
    {
        if (id == null || (id != null && id.length <= 0))
        {
            LOG.warn("ɾ����Ƶ������,ID����Ϊ��!");
            throw new BOException("ɾ����Ƶ������,ID����Ϊ��!");
        }
        try
        {
            BlackDAO.getInstance().removeBlack(id);
        }
        catch (DAOException e)
        {
            LOG.error("������Ƶ�������б�ʱ�������ݿ��쳣!", e);
            throw new BOException("������Ƶ�������б�ʱ�������ݿ��쳣!");
        }
    }

    /**
     * ��Ӻ�����
     * 
     * @param contentIdArray
     *            ����ID
     * @return ��ӽ����Ϣ
     * @throws BOException
     */
    public int addBlack(String[] arrlist,String[] nodeid) throws BOException
    {
        int result = 0;
        if (arrlist == null || (arrlist != null && arrlist.length <= 0))
        {
            LOG.warn("��ƵListΪ��!");
            return result;
        }
        try
        {
            BlackDAO.getInstance().addBlack(arrlist,nodeid);
            result = arrlist.length;
        }
        catch (Exception e)
        {
            LOG.error("��Ƶ���������Ӳ����������ݿ��쳣!", e);
            throw new BOException("��Ƶ���������Ӳ����������ݿ��쳣!");
        }
        return result;
    }
    /**
     * ��Ӻ�����
     * 
     * @param contentIdArray
     *            ����ID
     * @return ��ӽ����Ϣ
     * @throws BOException
     */
    public int addImportBlack(HashMap<String,String[]> arrlist) throws BOException
    {
        int result = 0;
//        if (arrlist == null || (arrlist != null && arrlist.get("grogramid") == 0))
      if (arrlist == null )

        {
            LOG.warn("��ƵListΪ��!");
            return result;
        }
        try
        {
            int number = BlackDAO.getInstance().addImBlack(arrlist);
            result= number;
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            LOG.error("��Ƶ���������Ӳ����������ݿ��쳣!", e);
            throw new BOException("��Ƶ���������Ӳ����������ݿ��쳣!");
        }
        return result;
    }
    /**
     * ��������������excel���뵽���ݿ��������ݿ��Ѿ����ڣ������
     * 
     * @param dataFile
     *            �����ļ�����
     * @return ��������Ϣ
     * @throws BOException
     * @throws BOException
     */
    @SuppressWarnings("unchecked")
    public int importBlackADD(HashMap<String,String[]> arrList) throws BOException
    {
        int result = 0;
        try
        {
            result = addImportBlack(arrList);
        }
        catch (BOException e)
        {
            LOG.error("��Ƶ��������������,����쳣!", e);
            throw new BOException("��Ƶ��������������,����쳣!");
        }
        return result;
    }

    /**
     * @param dataFile
     * @return
     * @throws BOException
     */
//    @SuppressWarnings("unchecked")
//    public int importBlackALL(List<String> list1) throws BOException
//    {
//        int result = 0;
//
//        if (list1 == null || (list1 != null && list1.size() <= 0))
//        {
//            LOG.warn("����arrlist[]����Ϊ��!");
//            return result;
//        }
//        try
//        {
//            BlackDAO.getInstance().allAddBlack(list1);
//            result = list1.size();
//            list1.clear();
//            list1 = null;
//
//        }
//        catch (DAOException e)
//        {
//            LOG.error("����������ȫ����������������ݿ��쳣!", e);
//            throw new BOException("����������ȫ����������������ݿ��쳣��");
//        }
//        return result;
//
//    }

    @SuppressWarnings("unchecked")
    public HashMap<String,String[]> getImportBalckParaseDataList(FormFile dataFile) throws BOException
    {
        List list = null;
        HashMap<String,String[]> map = new HashMap<String,String[]>();
        try
        {
        	
            list = new ExcelHandle().paraseDataFile(dataFile, null);
        }

        catch (BOException e)
        {
            LOG.error("��Ƶ����������,Excel�����쳣!", e);
            throw new BOException("��Ƶ����������,Excel�����쳣!");
        }

        String[] arrList = new String[list.size()];
        String[] arrList2 = new String[list.size()];
        if (list == null || (list != null && list.size() <= 0))
        {
            LOG.warn("��Ƶ����������excel����ListΪ��!");
            map.put("programid", arrList);
            map.put("nodeid", arrList2);
            return map;
        }


        for (int i = 0; i < list.size(); i++)
        {
            Map m = (Map)list.get(i);
            arrList[i] = (String)m.get(0);
            arrList2[i] = (String)m.get(1);
            
            m = null;
        }
        list = null;
        map.put("programid", arrList);
        map.put("nodeid", arrList2);

        return map;
    }

}
