package com.aspire.ponaadmin.web.baserecomm.basegame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;

/**
 * ����������ҵ����
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
    public void queryBlackList(PageResult page, BlackVO vo) throws BOException
    {
        try
        {
            BlackDAO.getInstance().queryBlackList(page, vo);
        }
        catch (DAOException e)
        {
            LOG.error("�������ݿ��쳣!", e);
            throw new BOException("�������ݿ��쳣!");
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
            LOG.warn("ɾ��������,ID����Ϊ��!");
            throw new BOException("ɾ��������,ID����Ϊ��!");
        }
        try
        {
            BlackDAO.getInstance().removeBlack(id);
        }
        catch (DAOException e)
        {
            LOG.error("���غ������б�ʱ�������ݿ��쳣!", e);
            throw new BOException("���غ������б�ʱ�������ݿ��쳣!");
        }
    }

    /**
     * ��Ӻ�����
     * 
     * @param contentIdArray
     *            ��Ϸҵ��ID
     * @return ��ӽ����Ϣ
     * @throws BOException
     */
    public int addBlack(String[] arrlist) throws BOException
    {
        int result = 0;
        if (arrlist == null || (arrlist != null && arrlist.length <= 0))
        {
            LOG.warn("ListΪ��!");
            return result;
        }
        //
        // List<String> list1 = isExistContent(arrlist);
        // List<String> list2 = isExistBlack(list1);
        //
        // String arr[] = new String[list2.size()];
        // list2.toArray(arr);
        //        
        // if (arr.length == 0)
        // {
        // LOG.warn("arrΪ��!");
        // return result;
        // }
        try
        {
            BlackDAO.getInstance().addBlack(arrlist);
            result = arrlist.length;
        }
        catch (Exception e)
        {
            LOG.error("���������Ӳ����������ݿ��쳣!", e);
            throw new BOException("���������Ӳ����������ݿ��쳣!");
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
    public int importBlackADD(String[] arrList) throws BOException
    {
        int result = 0;
        try
        {
            result = addBlack(arrList);
        }
        catch (BOException e)
        {
            LOG.error("��������������,����쳣!", e);
            throw new BOException("��������������,����쳣!");
        }
        return result;
    }

    /**
     * @param dataFile
     * @return
     * @throws BOException
     */
    @SuppressWarnings("unchecked")
    public int importBlackALL(List<String> list1) throws BOException
    {
        int result = 0;

        if (list1 == null || (list1 != null && list1.size() <= 0))
        {
            LOG.warn("arrlist[]����Ϊ��!");
            return result;
        }
        try
        {
            BlackDAO.getInstance().allAddBlack(list1);
            result = list1.size();
            list1.clear();
            list1 = null;

        }
        catch (DAOException e)
        {
            LOG.error("������ȫ����������������ݿ��쳣!", e);
            throw new BOException("������ȫ����������������ݿ��쳣��");
        }
        return result;

    }

    @SuppressWarnings("unchecked")
    public String[] getImportBalckParaseDataList(FormFile dataFile) throws BOException
    {
        List list = null;
        try
        {
            list = new ExcelHandle().paraseDataFile(dataFile, null);
        }
        catch (BOException e)
        {
            LOG.error("����������,Excel�����쳣!", e);
            throw new BOException("����������,Excel�����쳣!");
        }

        if (list == null || (list != null && list.size() <= 0))
        {
            LOG.warn("����������excel����ListΪ��!");
            return new String[0];
        }

        String[] arrList = new String[list.size()];
        for (int i = 0; i < list.size(); i++)
        {
            Map m = (Map)list.get(i);
            arrList[i] = (String)m.get(0);
            m = null;
        }
        list = null;

        return arrList;
    }

    /**
     * ��������Ƿ����t_game_service_new��
     * 
     * @param list
     * @return ����t_game_service_new�����Ϸҵ��ID
     * @throws BOException
     */
    public Map<String, Object> isExistContent(String[] arrlist) throws BOException
    {

        int error1 = 0;
        int error2 = 0;
        StringBuilder msg1 = new StringBuilder(128);
        StringBuilder msg2 = new StringBuilder(128);
        List<String> newList = new ArrayList<String>(arrlist.length);

        for (int i = 0; i < arrlist.length; i++)
        {
            String contentId = arrlist[i];
            if (StringUtils.isNotBlank(contentId))
            {
                try
                {
                    boolean result = BlackDAO.getInstance().isExistContent(contentId);
                    if (result)
                    {
                        newList.add(contentId);
                    }
                    else
                    {// ��Ϸҵ��ID��t_game_service_new������
                        msg1.append(contentId).append(",");
                        error1++;
                        if (error1 % 5 == 0)
                        {
                            msg1.append("<br/>");
                        }
                    }
                }
                catch (DAOException e)
                {
                    error2++;
                    msg2.append(contentId).append(",");
                    if (error2 % 5 == 0)
                    {
                        msg2.append("<br/>");
                    }
                    LOG.error("���ݱ��ѯcontentId:" + contentId + "�����������ݿ��쳣!", e);
                }
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (error1 > 0)
        {
            resultMap.put("msg1", "��Ϸҵ��ID��t_game_service_new�����ڣ�" + error1 + "����¼," + msg1.substring(0, msg1.length() - 1));
        }
        if (error2 > 0)
        {
            resultMap.put("msg2", "�����ݱ��в�ѯ����" + error2 + "����¼," + msg2.substring(0, msg2.length() - 1));
        }
        resultMap.put("list", newList);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("��Ϸҵ��ID��t_game_service_new�����ڣ�" + msg1);
            LOG.debug("�����ݱ��в�ѯ����" + msg2);
        }
        msg1 = null;
        msg2 = null;
        return resultMap;
    }

    /**
     * ��������Ƿ����t_gamebase_black��
     * 
     * @param list
     * @return ������t_gamebase_black�����Ϸҵ��ID
     * @throws BOException
     */
    public Map<String, Object> isExistBlack(List<String> list) throws BOException
    {
        int error1 = 0;
        int error2 = 0;
        StringBuilder msg1 = new StringBuilder(128);
        StringBuilder msg2 = new StringBuilder(128);

        List<String> newList = new ArrayList<String>(list.size());
        for (int i = 0; list != null && i < list.size(); i++)
        {
            String contentId = (String)list.get(i);
            if (StringUtils.isNotBlank(contentId))
            {
                try
                {
                    boolean result = BlackDAO.getInstance().isExistBlack(contentId);
                    if (result)
                    {// ��Ϸҵ��ID��t_gamebase_black�����
                        msg1.append(contentId).append(",");
                        error1++;
                        if (error1 % 5 == 0)
                        {
                            msg1.append("<br/>");
                        }
                    }
                    else
                    {
                        newList.add(contentId);
                    }
                }
                catch (DAOException e)
                {
                    error2++;
                    msg2.append(contentId).append(",");
                    if (error2 % 5 == 0)
                    {
                        msg2.append("<br/>");
                    }
                    LOG.error("��������ѯcontentId:" + contentId + "�����������ݿ��쳣!", e);
                }
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (error1 > 0)
        {
            resultMap.put("msg1", "��Ϸҵ��ID��t_gamebase_black����ڣ�" + error1 + "����¼," + msg1.substring(0, msg1.length() - 1));
        }
        if (error2 > 0)
        {
            resultMap.put("msg2", "�ں�������ѯ��ѯ����" + error2 + "����¼," + msg2.substring(0, msg2.length() - 1));
        }
        resultMap.put("list", newList);
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��Ϸҵ��ID��t_gamebase_black����ڣ�" + msg1);
            LOG.debug("�ں�������ѯ��ѯ����" + msg2);
        }
        msg1 = null;
        msg2 = null;

        return resultMap;
    }

    /**
     * ���ݺ������б�ɾ����Ʒ������T_CB_REFERENCE
     * 
     * @throws BOException
     */
    public void delReference() 
    {
        try
        {
        	
            BlackDAO.getInstance().delReference();
        }
        catch (DAOException e)
        {
            LOG.error("���ݺ������б�ɾ����Ʒ�������쳣!", e);
            //throw new BOException("���ݺ������б�ɾ����Ʒ�������쳣!");
        }
    }
    
    /**
     * ���ڲ�ѯ������Ϸ�б�
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryList(PageResult page, BlackVO vo)
                    throws BOException
    {
        try
        {
        	BlackDAO.getInstance().queryList(page, vo);
        }
        catch (DAOException e)
        {
        	LOG.error(e);
            throw new BOException("��ѯ������Ϸ�б�ʱ�������ݿ��쳣��");
        }
    }
}
