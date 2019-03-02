package com.aspire.ponaadmin.web.datasync.implement.game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

public class GameSyncDAO
{
	/**
	 * ��־����
	 */
	private static JLogger logger = LoggerFactory.getLogger(GameSyncDAO.class);

	private static GameSyncDAO dao = new GameSyncDAO();

	public static GameSyncDAO getInstance()
	{
		return dao;
	}
	public boolean isExistedGameService(GameServiceVO vo)throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("isExistedGameService(),icpcode=" + vo.getIcpCode()
					+ ",icpservId=" + vo.getIcpServid());
		}
		Object paras[] = {vo.getIcpServid()};
		String sqlCode = "datasync.implement.game.GameSyncDAO.isExistedGameService().SELECT";
		ResultSet rs= DB.getInstance().queryBySQLCode(sqlCode, paras);
		try
		{
			boolean result=rs.next();
			return result;
		} catch (SQLException e)
		{
			throw new DAOException(e);
		}finally
		{
			DB.close(rs);
		}
	}
	
	public boolean isExistedGameService2(GameServiceVO vo)throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("isExistedGameService(),icpcode=" + vo.getIcpCode()
					+ ",icpservId=" + vo.getIcpServid());
		}
		Object paras[] = {vo.getIcpServid()};
		String sqlCode = "datasync.implement.game.GameSyncDAO.isExistedGameService2";
		ResultSet rs= DB.getInstance().queryBySQLCode(sqlCode, paras);
		try
		{
			return rs.next();
		} catch (SQLException e)
		{
			throw new DAOException(e);
		}finally
		{
			DB.close(rs);
		}
	}

	/**
	 * ������Ϸҵ����Ϣ
	 * 
	 * @param vo
	 *            ��Ϸҵ���vo��
	 * @return �����������
	 */
	public int insertGameService(GameServiceVO vo)throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("insertGameServiceVO(),icpcode=" + vo.getIcpCode()
					+ ",icpservId=" + vo.getIcpServid());
		}
		Object paras[] = {vo.getContentid(),vo.getIcpCode(),vo.getSpName(),vo.getIcpServid(),vo.getServName(),vo.getServDesc(),vo.getChargeType()
				,vo.getChargDesc(),new Integer(vo.getMobilePrice()),new Integer(vo.getServType()),new Integer(vo.getServFlag()),new Integer(vo.getPtypeId())};
		String sqlCode = "datasync.implement.game.GameSyncDAO.insertGameServiceVO().INSERT";
		DB.getInstance().executeBySQLCode(sqlCode, paras);
		return DataSyncConstants.SUCCESS_ADD;
	}
	
	//add by aiyan 2012-09-12
	public int insertGameService2(GameServiceVO vo)throws DAOException
	{
		
		
		if (logger.isDebugEnabled())
		{
			logger.debug("insertGameServiceVO(),icpcode=" + vo.getIcpCode()
					+ ",icpservId=" + vo.getIcpServid());
		}
		Object paras[] = null;
		String sqlCode = null;
		try{
			paras = new Object[]{vo.getContentid(),vo.getIcpCode(),vo.getSpName(),vo.getIcpServid(),vo.getServName(),vo.getServDesc(),vo.getChargeType()
					,vo.getChargDesc(),new Integer(vo.getMobilePrice()),new Integer(vo.getServType()),new Integer(vo.getServFlag()),new Integer(vo.getPtypeId()),
					vo.getOldprice(),vo.getFirsttype(),vo.getContenttag()};
			sqlCode = "datasync.implement.game.GameSyncDAO.insertGameServiceVO2";
			DB.getInstance().executeBySQLCode(sqlCode, paras);

		}catch(DAOException e){
			logger.error("���ݿ����ʧ��" + e);
			try {
				logger.error("�����ִ����䣺"+getDebugSql(SQLCode.getInstance().getSQLStatement(sqlCode),paras));
			} catch (DataAccessException e1) {
				// TODO Auto-generated catch block
				logger.error(e1);
			}
			throw new DAOException("��Ϸҵ��������",e);
		}
		return DataSyncConstants.SUCCESS_ADD;
	}
	
	private  String getDebugSql(String sql,Object[] para){
		if(para!=null){
			for(int i=0;i<para.length;i++){
				sql = sql.replaceFirst("\\?", "'"+para[i]+"'");
			}
		}
		return sql;
	}

	/**
	 * ɾ����Ϸҵ����Ϣ
	 * 
	 * @return
	 */
	public void delGameService(String icpCode, String icpServId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("deleteAuthor");
		}
		String paras[] = { icpServId };
		String sqlCode = "datasync.implement.game.GameSyncDAO.delGameService().DELETE";
		DB.getInstance().executeBySQLCode(sqlCode, paras);

	}

	/**
	 * ������Ϸҵ����Ϣ
	 * 
	 * @return
	 */
	public void updateGameService(GameServiceVO vo) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateGameService,icpcode="+vo.getIcpCode()+",icpservid="+vo.getIcpServid());
		}
		Object paras[] = { vo.getContentid(),vo.getSpName(), vo.getServName(),vo.getServDesc(),vo.getChargeType(),vo.getChargDesc(),new Integer(vo.getMobilePrice()),
				new Integer(vo.getServType()),new Integer(vo.getServFlag()),new Integer(vo.getPtypeId()),vo.getIcpCode(),vo.getIcpServid()};
		String sqlCode = "datasync.implement.game.GameSyncDAO.updateGameServices().UPDATE";
		DB.getInstance().executeBySQLCode(sqlCode, paras);

	}
	
	//add by aiyan 2012-09-12
	public void updateGameService2(GameServiceVO vo) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateGameService,icpcode="+vo.getIcpCode()+",icpservid="+vo.getIcpServid());
		}
		Object paras[] = { vo.getContentid(),vo.getSpName(), vo.getServName(),vo.getServDesc(),vo.getChargeType(),vo.getChargDesc(),new Integer(vo.getMobilePrice()),
				new Integer(vo.getServType()),new Integer(vo.getServFlag()),new Integer(vo.getPtypeId()),vo.getIcpCode(),vo.getOldprice(),
				vo.getFirsttype(),vo.getContenttag(),vo.getIcpServid()};
		String sqlCode = "datasync.implement.game.GameSyncDAO.updateGameServices2";
		DB.getInstance().executeBySQLCode(sqlCode, paras);

	}
	/**
	 * ��ȡ��ǰϵͳ���л�����Ϸ���б�
	 * @return
	 */
	public List getAllBaseGames()throws DAOException
	{
		List gameList=new LinkedList();
		String sqlCode="datasync.implement.game.GameSyncDAO.getAllBaseGames().SELECT";
		ResultSet rs= DB.getInstance().queryBySQLCode(sqlCode, null);
		BaseGameKeyVO game=null;
		try
		{
			while(rs.next())
			{
				game=new BaseGameKeyVO();
				game.setId(rs.getString("id"));
				game.setIcpCode(rs.getString("icpCode"));
				game.setIcpServid(rs.getString("icpservid"));
				game.setExpPrice(rs.getInt("expprice"));
				game.setAppcateName(rs.getString("appcatename"));
				game.setChargeTime(rs.getString("chargetime"));
				game.setOnlineType(rs.getInt("onlinetype"));
				game.setStatus(0);
				gameList.add(game);
			}
		} catch (SQLException e)
		{
			throw new DAOException("��ȡ��ϷUAӳ���ϵ����");
		}finally
		{
			DB.close(rs);
		}
		return gameList;
	}
	/**
	 * ��ȡ��ǰ������Ϸ ������Ʒ��������ͬ������
	 * @return
	 */
	public HashMap getAllBaseGameTactic()throws DAOException
	{
		HashMap hm = new HashMap();
		String sqlCode="datasync.implement.game.GameSyncDAO.getAllBaseGameTactic().SELECT";
		ResultSet rs= DB.getInstance().queryBySQLCode(sqlCode, null);
		
		try
		{
			while(rs.next())
			{
				hm.put(rs.getString("aliasname"),rs.getString("cid"));

			}
		} catch (SQLException e)
		{
			throw new DAOException("��ȡ������Ϸ������Ʒ ��������ͬ�����Թ�ϵ����");
		}finally
		{
			DB.close(rs);
		}
		return hm;
	}
	/**
	 * ɾ��BaseUA��Ϣ
	 * @param list ���д�ɾ����UA��Ϣ�ļ���
	 * @return
	 * @throws DAOException
	 */
	public int delGameInfo(List list)throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			StringBuffer bf=new StringBuffer();
			for(int i=0;i<list.size();i++)
			{
				if(i!=0)
				{
					bf.append(',');
				}
				bf.append(list.get(i));
			}
			logger.debug("��ɾ����baseUAΪ"+bf.toString());
		}
	    Object mutiParas[][] = new Object[list.size()][1];
	    for(int i=0;i<list.size();i++)
		{
	    	mutiParas[i][0]=list.get(i);
		}
		String sqlCode = "datasync.implement.game.GameSyncDAO.delGameInfo().DELETE";
		DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
		return list.size();
	}
	/**
	 * ����BaseUA��Ϣ
	 * @param baseUA
	 * @return int �����Ƿ�ɹ�
	 * @throws DAOException
	 */
	public int addGameInfo(String baseUA)throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��������baseUAΪ"+baseUA);
		}
	    Object paras[] = {baseUA};
		String sqlCode = "datasync.implement.game.GameSyncDAO.addGameInfo().INSERT";
		DB.getInstance().executeBySQLCode(sqlCode, paras);
		return DataSyncConstants.SUCCESS_ADD;
	}
	/**
     * ˢ��v_service �ﻯ��ͼ��
     * 
     */
    public void refreshVService() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("refreshVService()");
        }
        // �����������е��ֶβ�ѯ������
        // дһ��sql��佫һ�ڵ�ҵ����cms�в��������Ϣ�ϳ�һ���ﻯ��ͼ���ɡ�
        
        try{
        DataSyncDAO.getInstance().syncVService();
        }catch(Exception e){
        	
        	logger.error("�ؽ�v_service��ʧ��"+e);
        	throw new DAOException("�ؽ�v_service��ʧ��",e);
        }
//        CallableStatement cs = null;
//        Connection conn = DB.getInstance().getConnection();
//        try
//        {
//            cs = conn.prepareCall("{call dbms_mview.refresh(list => 'v_service')}");
//            cs.execute();
//        }
//        catch (Exception ex)
//        {
//            throw new DAOException("ͬ��������Ϸʱ��ˢ��v_service �ﻯ��ͼ����",ex);
//        }
//        finally
//        {
//            try
//            {
//                cs.close();
//                DB.close(conn);
//            }
//            catch (Exception ex)
//            {
//                logger.error("�ر�CallableStatement=ʧ��");
//            }
//        }
    }

}
