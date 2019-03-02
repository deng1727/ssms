
package com.aspire.ponaadmin.web.datafield.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.datafield.KeyBaseConfig;
import com.aspire.ponaadmin.web.datafield.dao.KeyBaseDAO;
import com.aspire.ponaadmin.web.datafield.dao.KeyResourceDAO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;

/**
 * <p>
 * ����KeyBaseBO���BO��
 * </p>
 * 
 * @author biran
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class KeyBaseBO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(KeyBaseBO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static KeyBaseBO instance = new KeyBaseBO();

    /**
     * ���췽������singletonģʽ����
     */
    private KeyBaseBO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static KeyBaseBO getInstance()
    {

        return instance;
    }

    /**
     * ���ݱ����ơ����ֶε��ֶβ�ѯ��ѯ��չ���ԡ�
     * 
     * @param page
     * @param keyid
     * @param keyname
     * @param keytable
     * @param keydesc
     * @throws BOException
     */
    public void queryKeyBaseList(PageResult page, String keyid, String keyname,
                                 String keytable, String keydesc)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryKeyBaseList(" + keyid + "," + keyname + ","
                         + keytable + "," + keydesc + ")");
        }
        if (page == null)
        {
            throw new BOException("invalid para , page is null");
        }
        try
        {
            KeyBaseDAO.getInstance().queryKeyBaseList(page,
                                                      keyid,
                                                      keyname,
                                                      keytable,
                                                      keydesc);
        }
        catch (DAOException e)
        {
            logger.error("queryKeyBaseList...", e);
        }
    }

    /**
     * ����keyid��ѯ��չ����
     * 
     * @param keyid
     * @return
     * @throws BOException
     */
    public ResourceVO getKeyBaseByID(String keyid) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getKeyBaseByID(" + keyid + ")");
        }
        if (keyid == null)
        {
            throw new BOException("invalid para id");
        }
        try
        {
            return KeyBaseDAO.getInstance().getKeyBaseByID(keyid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����keyid��ѯʧ�ܣ�");
        }
    }

    public void updateKeyBase(String keyid, String keytable, String keyname,
                              String keydesc,String keyType) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateKeyBase( keyid= " + keyid + ")");
        }
        try
        {

            KeyBaseDAO.getInstance().updateKeyBase(keyid,
                                                   keytable,
                                                   keyname,
                                                   keydesc,
                                                   keyType);

        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("updateKeyBase", e);
        }
    }

    /**
     * ����ɾ����չ����
     * 
     * @param keyBaseDels
     * @throws BOException
     */
    public void keyBaseDel(String[] keyBaseDels) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("delContent(" + keyBaseDels + ")");
        }

        // ��ʼִ��ɾ������
        for (int i = 0; (keyBaseDels != null) && (i < keyBaseDels.length); i++)
        {
            String keyid = keyBaseDels[i];
            if (logger.isDebugEnabled())
            {
                logger.debug("keyBaseDel(" + keyid + ")");
            }
            try
            {
                KeyBaseDAO.getInstance().keyBaseDel(keyid);
            }
            catch (DAOException e)
            {
                logger.error("keyid="+keyid ,e);
                throw new BOException("keyBaseDel error", e);
            }
        }

    }

    /**
     * ������չ���Է���
     * @param keytable
     * @param keyname
     * @param keydesc
     * @throws BOException
     */
    public void insertKeyBase(String keytable, String keyname, String keydesc,String keytype)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("insertKeyBase()  keytable= " + keytable
                         + " keyname= " + keyname + " keydesc= " + keydesc+"keytype="+keytype);
        }
        try
        {

            KeyBaseDAO.getInstance().insertKeyBase(keytable, keyname, keydesc,keytype);

        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("insertKeyBase", e);
        }
    }

    /**
     * ������չ�����ظ��ж�
     * @param keytable
     * @param keyname
     * @return
     * @throws BOException
     */
    public boolean isKebBase(String keytable, String keyname)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("isKebBase(" + keyname + "," + keytable + ")");
        }
        if (keytable == null || "".equals(keytable) || keyname == null
            || "".equals(keyname))
        {
            throw new BOException("invalid para , page is null");
        }
        try
        {
            return KeyBaseDAO.getInstance().isKebBase(keyname, keytable);
        }
        catch (DAOException e)
        {
            logger.error("isKebBase...", e);
            throw new BOException("isKebBase", e);
        }
    }

    
    
    /**
     * ɾ����չ����
     * @param keytable
     * @param keyname
     * @return
     * @throws BOException
     */
    public int delKeyResourceListValue(List delKeyResourceList)
                    throws BOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("delKeyResourceListValue(delKeyResourceList="
					+ delKeyResourceList + ",delKeyResourceList.size="
					+ delKeyResourceList.size());
		}
		if (delKeyResourceList == null || delKeyResourceList.size() <= 0)
		{
			logger.debug("delKeyResourceList is null,not extdata to del");
		}
		int r = 0;
		for (int i = 0; i < delKeyResourceList.size(); i++)
		{
			ResourceVO vo = (ResourceVO) delKeyResourceList.get(i);
			if (vo != null && vo.getKeyid() != null && !"".equals(vo.getKeyid())
					&& vo.getTid() != null && !"".equals(vo.getTid()))
			{
				r += this.delKeyResourceValue(vo.getKeyid(), vo.getTid());
			}
		}
		return r;

	}
    /**
	 * ɾ����չ����
	 * 
	 * @param keytable
	 * @param keyname
	 * @return
	 * @throws BOException
	 */
    public int delKeyResourceValue(String keyid, String tid)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delKeyResourceValue(keyid=" + keyid + ",tid=" + tid + ")");
        }
        if (keyid == null || "".equals(keyid) || tid == null
            || "".equals(tid))
        {
            throw new BOException("invalid para , keyid is null");
        }
        try
        {
            return KeyResourceDAO.getInstance().delResourceValue(keyid, tid);
        }
        catch (DAOException e)
        {
            logger.error("delKeyResourceValue...", e);
            throw new BOException("delKeyResourceValue", e);
        }
    }
    
    /**
     * ���ݱ����Ʋ�ѯ����չ�ֶ�
     * 
     * @param keytable
     * @return
     * @throws BOException
     */
    public List getKeytableByText(String keytable) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getKeytable(keytable=" + keytable + ")");
        }
        if (keytable == null || "".equals(keytable))
        {
            throw new BOException("invalid para , keytable is null");
        }
        try
        {
            return KeyBaseDAO.getInstance().getKeytableByText(keytable);
        }
        catch (DAOException e)
        {
            logger.error("getKeytable...", e);
            throw new BOException("getKeytable", e);
        }
    }
    
    /**
     * �ļ�����������չ����������Ϣ
     * @param dataFile
     * @param keytable
     * @param keyid
     * @return
     * @throws BOException
     */
    public String imputKeyBase(FormFile dataFile, String keytable, String keyid) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("imputKeyBase(keyid=" + keyid + ", keytable=" + keytable + "keytable ) is start...");
        }

        StringBuffer ret = new StringBuffer();

        try
        {
            // ����EXECL�ļ�����ȡ�ļ�����������չ����������Ϣ
            Map map = paraseDataFile(dataFile);

            // У�����������ļ���������ȷ��
            String temp = KeyResourceDAO.getInstance().verifyNewMusic(map, getVerifySql(keytable));

            // ����KeyResourceDAO��������
            KeyResourceDAO.getInstance().addKeyResource(keyid, map);

            ret.append("�ɹ�����" + map.size() + "����¼.");

            if (!"".equals(temp))
            {
                ret.append("���벻�ɹ�idΪ").append(temp);
            }

            return ret.toString();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�ļ�����������չ����������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �õ�ָ�������չ�ֶε�У�����ݿ��ѯ���
     * @param keytable
     * @return
     */
    public String getVerifySql(String keytable)
    {
        String sql = "";
        String[] keyTableById = KeyBaseConfig.get("keytablebyid").trim().split(",");
        
        for (int i = 0; i < keyTableById.length; i++)
        {
            String[] temp = keyTableById[i].split("\\|");
            
            if(keytable.equals(temp[0]))
            {
                sql = "select 1 from " + keytable + " where " + temp[1] + " = ?";
            }
        }
        
        return sql;
    }
    
    /**
     * ����EXECL�ļ�����ȡ�ļ�����������չ����������Ϣ
     * 
     * @param dataFile
     * @return
     * @throws BiffException
     * @throws IOException
     */
    public Map paraseDataFile(FormFile dataFile) throws BOException
    {
        logger.info("paraseDataFile() is start!");
        Map map = new HashMap();
        Workbook book = null;

        try
        {
            book = Workbook.getWorkbook(dataFile.getInputStream());
            Sheet[] sheets = book.getSheets();
            int sheetNum = sheets.length;
            if (logger.isDebugEnabled())
            {
                logger.debug("paraseSoftVersion.sheetNum==" + sheetNum);
            }

            for (int i = 0; i < sheetNum; i++)
            {
                int rows = sheets[i].getRows();

                for (int j = 0; j < rows; j++)
                {
                    String id = sheets[i].getCell(0, j)
                                         .getContents()
                                         .trim();
                    String desc = sheets[i].getCell(1, j)
                                           .getContents()
                                           .trim();
                    // ����Օr
                    if (!"".equals(id))
                    {
                        map.put(id, desc);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("���������ļ������쳣,fineName:" + dataFile.getFileName(), e);
            throw new BOException("���������ļ������쳣", e);
        }
        finally
        {
        	if(book != null){
        		book.close();
        	}
            
        }
        return map;
    }
}
