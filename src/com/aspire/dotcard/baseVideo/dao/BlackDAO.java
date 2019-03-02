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
     * 记录日志的实例对象
     */
    protected static JLogger LOG = LoggerFactory.getLogger(BlackDAO.class);

    /**
     * singleton模式的实例
     */
    private static BlackDAO instance = new BlackDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private BlackDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static BlackDAO getInstance()
    {
        return instance;
    }

    /**
     * 用于查询当前黑名单列表
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
     * 查询动漫内容信息列表
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
     * 移除黑名单
     * 
     * @param id
     * @throws DAOException
     */
    public void removeBlack(String[] id) throws DAOException
    {
        if (id == null || (id != null && id.length <= 0))
        {
            LOG.warn("移除视频黑名单id[]参数为空!");
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
            LOG.error("移除指定动漫黑名单时发生异常!", e);
            throw new DAOException("移除指定动漫黑名单时发生异常!", e);
        }
    }

    /**
     * 检查黑名单表是否存在内容ID
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
            LOG.warn("检查视频黑名单表programId参数为空!");
            throw new DAOException("检查视频黑名单表programId参数为空!");
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
            LOG.warn("检查视频黑名单表是否存在内容programId:" + programId + " 发生异常:", e);
            throw new DAOException("检查视频黑名单表是否存在内容programId:" + programId + " 发生异常:", e);
        }
        catch (SQLException e)
        {
            LOG.warn("检查视频黑名单表是否存在内容programId:" + programId + " 发生异常:", e);
            throw new DAOException("检查视频黑名单表是否存在内容programId:" + programId + " 发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return result;
    }

    /**
     * 检查内容表t_cb_content是否存在conntentId
     * 
     * @param contentId
     * @return
     * @throws DAOException
     */
    public boolean isExistContent(String contentId) throws DAOException
    {
        if (StringUtils.isEmpty(contentId))
        {
            LOG.warn("检查视频内容表contentid参数为空!");
            throw new DAOException("检查视频内容表contentid参数为空!");
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
            LOG.error("检查动漫内容表是否存在内容contentid:" + contentId + " 发生异常:", e);
            throw new DAOException("检查内容表是否存在内容contentid:" + contentId + " 发生异常:", e);
        }
        catch (SQLException e)
        {
            LOG.error("检查动漫内容表是否存在内容contentid:" + contentId + " 发生异常:", e);
            throw new DAOException("检查内容表是否存在内容contentid:" + contentId + " 发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }
        return result;
    }

    /**
     * 新增黑名单
     * 
     * @param contentId
     * @throws Exception
     */
//    public void addBlack(String contentId) throws Exception
//    {
//        if (StringUtils.isEmpty(contentId))
//        {
//            LOG.warn("新增动漫黑名单contentid参数为空!");
//            throw new DAOException("新增动漫黑名单contentid参数为空!");
//        }
//
//        this.addBlack(new String[] { contentId });
//    }
    /**
     * 新增多条黑名单
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
            LOG.warn("新增视频黑名单programId[]为空!");
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
            LOG.error("新增视频多条黑名单时发生异常!", e);
            throw new DAOException("新增视频多条黑名单时发生异常!", e);
        }
		return number;
    }

    /**
     * 新增多条黑名单
     * 
     * @param contentId
     * @throws DAOException
     */
    public void addBlack(String[] programId,String[] nodeid) throws DAOException
    {
        if (programId == null || (programId != null && programId.length <= 0))
        {
            LOG.warn("新增视频黑名单programId[]为空!");
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
//            LOG.error("新增视频多条黑名单时发生异常!", e);
//            throw new DAOException("新增视频多条黑名单时发生异常!", e);
//        }
    }

    
}
