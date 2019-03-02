package com.aspire.dotcard.baseVideo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.vo.BlackVO;
import com.aspire.dotcard.baseVideo.vo.ProgramBlackVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class BlackDAO
{
    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger LOG = LoggerFactory.getLogger(BlackDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static BlackDAO instance = new BlackDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private BlackDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static BlackDAO getInstance()
    {
        return instance;
    }

    /**
     * ���ڲ�ѯ��ǰ�������б�
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryBlackList(PageResult page, ProgramBlackVO vo) throws DAOException
    {

        StringBuilder sqlsb = new StringBuilder(200);
        String sql = "select b.id,b.nodeid, b.programid, a.programname, a.videoid, b.lupdate from t_vo_black b left outer join t_vo_program a on b.programid=a.programid and b.nodeid =a.nodeid where 1 = 1 ";

        sqlsb.append(sql);

        List<String> paras = new ArrayList<String>(2);

        if (StringUtils.isNotBlank(vo.getNodeId()))
        {
            sqlsb.append(" and b.nodeid like ? ");
            paras.add("%" + SQLUtil.escape(vo.getNodeId().trim()) + "%");
        }
        if (StringUtils.isNotBlank(vo.getProgramId()))
        {
            sqlsb.append(" and b.programid like ? ");
            paras.add("%" + SQLUtil.escape(vo.getProgramId().trim()) + "%");
        }
        if (StringUtils.isNotBlank(vo.getProgramName()))
        {
            sqlsb.append(" and a.programname like ? ");
            paras.add("%" + SQLUtil.escape(vo.getProgramName().trim()) + "%");
        }
        if (StringUtils.isNotBlank(vo.getVideoId()))
        {
            sqlsb.append(" and a.videoid like ? ");
            paras.add("%" + SQLUtil.escape(vo.getVideoId().trim()) + "%");
        }
        sqlsb.append(" order by b.lupdate desc");
        page.excute(sqlsb.toString(), paras.toArray(), new PageVOInterface()
        {
            public void CopyValFromResultSet(Object vo, ResultSet rs) throws SQLException
            {
                BlackVO blackVO = (BlackVO)vo;
                //blackVO.setId(rs.getString("id"));
                blackVO.setId(String.valueOf(rs.getInt("id")));
                blackVO.setNodeId(rs.getString("nodeid"));
                blackVO.setProgramId(rs.getString("programid"));
                blackVO.setProgramName(rs.getString("programname"));
                blackVO.setVideoId(rs.getString("videoid"));
               // blackVO.setLastUpTime(rs.getTimestamp("lupdate"));
                blackVO.setLastUpTime(rs.getTimestamp("lupdate"));

            }
            public Object createObject()
            {
                return new BlackVO();
            }
        });
    }

    /**
     * ��ѯ����������Ϣ�б�
     * @param page
     * @param vo
     * @throws DAOException
     */
    @SuppressWarnings("unchecked")
    public void queryContentList(PageResult page, BlackVO vo) throws DAOException
    {
        String sql = null;
        sql = "select a.nodeid,a.programid,a.programname,a.videoid,a.EXPORTTIME from t_vo_program a where 1=1 ";
        StringBuffer sqlsb = new StringBuffer(sql);
        List<String> paras = new ArrayList<String>();
        if (StringUtils.isNotBlank(vo.getNodeId()))
        {
            sqlsb.append(" and a.nodeid like ? ");
            paras.add("%" + SQLUtil.escape(vo.getNodeId().trim()) + "%");
        }
        if (StringUtils.isNotBlank(vo.getProgramId()))
        {
            sqlsb.append(" and a.programid like ? ");
            paras.add("%" + SQLUtil.escape(vo.getProgramId().trim()) + "%");
        }
        if (StringUtils.isNotBlank(vo.getProgramName()))
        {
            sqlsb.append(" and a.programname like ? ");
            paras.add("%" + SQLUtil.escape(vo.getProgramName().trim()) + "%");
        }
        if (StringUtils.isNotBlank(vo.getVideoId()))
        {
            sqlsb.append(" and a.videoid like ? ");
            paras.add("%" + SQLUtil.escape(vo.getVideoId().trim()) + "%");
        }
        sqlsb.append(" order by a.programname desc");

        page.excute(sqlsb.toString(), paras.toArray(), new PageVOInterface()
        {
            public void CopyValFromResultSet(Object vo, ResultSet rs) throws SQLException
            {

                BlackVO blackVO = (BlackVO)vo;
                //blackVO.setId(rs.getString("id"));
                blackVO.setNodeId(rs.getString("nodeid"));
                blackVO.setProgramId(rs.getString("programid"));
                blackVO.setProgramName(rs.getString("programname"));
                blackVO.setVideoId(rs.getString("videoid"));
               // blackVO.setLastUpTime(rs.getTimestamp("lupdate"));
                blackVO.setLastUpTime(rs.getTimestamp("EXPORTTIME"));
            }
            public Object createObject()
            {
                return new BlackVO();
            }
        });

    }

    /**
     * �Ƴ�������
     * 
     * @param id
     * @throws DAOException
     */
    public void removeBlack(String[] id) throws DAOException
    {
        if (id == null || (id != null && id.length <= 0))
        {
            LOG.warn("�Ƴ���Ƶ������id[]����Ϊ��!");
            return;
        }
        String sql = "videoblack.BlackDAO.removeBlack.DELETE";
        Object[][] object = new Object[id.length][1];

        for (int i = 0; i < id.length; i++)
        {
            object[i][0] = id[i];

        }

        try
        {
            DB.getInstance().executeBatchBySQLCode(sql, object);
        }
        catch (DAOException e)
        {
            LOG.error("�Ƴ�ָ������������ʱ�����쳣!", e);
            throw new DAOException("�Ƴ�ָ������������ʱ�����쳣!", e);
        }
    }

    /**
     * �����������Ƿ��������ID
     * 
     * @param contentId
     * @return
     * @throws DAOException
     * @throws Exception
     */
    public boolean isExistBlack(String programId,String nodeid) throws DAOException
    {
        if (StringUtils.isEmpty(programId))
        {
            LOG.warn("�����Ƶ��������programId����Ϊ��!");
            throw new DAOException("�����Ƶ��������programId����Ϊ��!");
        }

        String sqlCode = "videoblack.BlackDAO.isExistBlack.SELETE";
        String[] paras = new String[] { programId,nodeid };
        ResultSet rs = null;
        boolean result = false;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs != null)
            {
                result = rs.next();
            }
        }
        catch (DAOException e)
        {
        	e.printStackTrace();
            LOG.warn("�����Ƶ���������Ƿ��������programId:" + programId + " �����쳣:", e);
            throw new DAOException("�����Ƶ���������Ƿ��������programId:" + programId + " �����쳣:", e);
        }
        catch (SQLException e)
        {
            LOG.warn("�����Ƶ���������Ƿ��������programId:" + programId + " �����쳣:", e);
            throw new DAOException("�����Ƶ���������Ƿ��������programId:" + programId + " �����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return result;
    }

    /**
     * ������ݱ�t_cb_content�Ƿ����conntentId
     * 
     * @param contentId
     * @return
     * @throws DAOException
     */
    public boolean isExistContent(String contentId) throws DAOException
    {
        if (StringUtils.isEmpty(contentId))
        {
            LOG.warn("�����Ƶ���ݱ�contentid����Ϊ��!");
            throw new DAOException("�����Ƶ���ݱ�contentid����Ϊ��!");
        }

        String sqlCode = "videoblack.BlackDAO.isExistContent.SELETE";
        String[] paras = new String[] { contentId };
        ResultSet rs = null;
        boolean result = false;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs != null)
            {
                result = rs.next();
            }
        }
        catch (DAOException e)
        {
            LOG.error("��鶯�����ݱ��Ƿ��������contentid:" + contentId + " �����쳣:", e);
            throw new DAOException("������ݱ��Ƿ��������contentid:" + contentId + " �����쳣:", e);
        }
        catch (SQLException e)
        {
            LOG.error("��鶯�����ݱ��Ƿ��������contentid:" + contentId + " �����쳣:", e);
            throw new DAOException("������ݱ��Ƿ��������contentid:" + contentId + " �����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }
        return result;
    }

    /**
     * ����������
     * 
     * @param contentId
     * @throws Exception
     */
//    public void addBlack(String contentId) throws Exception
//    {
//        if (StringUtils.isEmpty(contentId))
//        {
//            LOG.warn("��������������contentid����Ϊ��!");
//            throw new DAOException("��������������contentid����Ϊ��!");
//        }
//
//        this.addBlack(new String[] { contentId });
//    }
    /**
     * ��������������
     * 
     * @param contentId
     * @throws DAOException
     */
    @SuppressWarnings("null")
	public int addImBlack(HashMap<String,String[]> programId) throws DAOException
    {
    	int number =0;
        if (programId == null)
        {
            LOG.warn("������Ƶ������programId[]Ϊ��!");
            return number;
        }
        System.out.println(programId.get("programid").toString());
        String[] a =programId.get("programid");
        String[] b =programId.get("nodeid");
        
        String sql = "videoblack.BlackDAO.addBlack.insert";

        try
        {
        	 boolean result=false;
        	 for (int i = 0; i < a.length; i++)
             {
              Object[] object2 = new Object[]{ new String(),new String()};;
              object2[0]=a[i].toString();
              object2[1]=b[i].toString();
 			  result = BlackDAO.getInstance().isExistBlack(a[i].toString(),b[i].toString());
              if(!result){
            	  number++;
              DB.getInstance().executeInsertImBySQLCode(sql, object2);
              }
             }
        }
        catch (DAOException e)
        {
            LOG.error("������Ƶ����������ʱ�����쳣!", e);
            throw new DAOException("������Ƶ����������ʱ�����쳣!", e);
        }
		return number;
    }

    /**
     * ��������������
     * 
     * @param contentId
     * @throws DAOException
     */
    public void addBlack(String[] programId,String[] nodeid) throws DAOException
    {
        if (programId == null || (programId != null && programId.length <= 0))
        {
            LOG.warn("������Ƶ������programId[]Ϊ��!");
            return;
        }

        String sql = "videoblack.BlackDAO.addBlack.ADD";
//        String sqlCode[] = new String[programId.length];
        Object[] object = new Object[]{ new String(),new String()};

        for (int i = 0; i < programId.length; i++)
        {
            object[0] = programId[i];
            object[1]= nodeid[i];
            DB.getInstance().executeInsertIm2BySQLCode(sql, object);

        }


//        catch (DAOException e)
//        {
//        	e.printStackTrace();
//            LOG.error("������Ƶ����������ʱ�����쳣!", e);
//            throw new DAOException("������Ƶ����������ʱ�����쳣!", e);
//        }
    }

    
}
