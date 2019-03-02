
package com.aspire.dotcard.hwcolorring.clrLoad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>
 * 彩铃内容管理DAO类
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author bihui
 * @version 1.0.0.0
 */
public class ColorContentDAO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(ColorContentDAO.class);

    /**
     * 构造方法，由singleton模式调用
     */
    private ColorContentDAO()
    {

    }

    /**
     * singleton模式的实例
     */
    private static ColorContentDAO colorContentDAO = new ColorContentDAO();

    /**
     * 获取实例
     * 
     * @return 实例
     */
   public static final ColorContentDAO getInstance()
    {

        return colorContentDAO;
    }
    /**
     * 根据彩铃ID获取彩铃
     * @param id
     * @return
     * @throws DAOException
     */
    public int delGColorringById(String id) throws DAOException{
    	 String sqlCode = "hwcolorring.ColorContentDAO.getGColorringById().DELETE";
    	 int rs = 0;
    	 Object [] parm = {id};
			rs = DB.getInstance().executeBySQLCode(sqlCode, parm);
    	return rs;
    }
    
    /**
     * 根据彩铃ID获取彩铃
     * @param id
     * @return
     * @throws DAOException
     */
    public GColorring getGColorringById(String id) throws DAOException{
    	 String sqlCode = "hwcolorring.ColorContentDAO.getGColorringById().SELECT";
    	 ResultSet rs = null;
    	 GColorring gr = null;
    	 try
		{
    		 Object [] parm = {id};
				rs = DB.getInstance().queryBySQLCode(sqlCode, parm);
				
			 if (rs.next())
	            {
				 gr = new GColorring();
				 gr.setId(id);
				 gr.setName(rs.getString(2));
				 gr.setTonenameletter(rs.getString(3));
				 gr.setSinger(rs.getString(4));
				 gr.setSingerletter(rs.getString(5));
				 gr.setIntroduction(rs.getString(6));
				 gr.setPrice(rs.getString(7));
				 gr.setLupdDate(rs.getString(8));
				 gr.setDownloadtimes(rs.getInt(9));
				 gr.setSettimes(rs.getInt(10));
				 gr.setAuditionUrl(rs.getString(11));
				 gr.setTonebigtype(rs.getString(12));
				 gr.setCateName(rs.getString(13));
				 gr.setExpire(rs.getString(14));
				 gr.setCreateDate(rs.getString(15));
				 gr.setMarketDate(rs.getString(16));
				 gr.setAverageMark(rs.getInt(17));
				 gr.setContentID(rs.getString(18));
				 gr.setClientAuditionUrl(rs.getString(19));
				 
	            }
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException(e);
		}finally
        {
            DB.close(rs);
        }
    	return gr;
    }
    
    public void saveColorring(GColorring gr) throws DAOException{
    	 String sqlCode = "hwcolorring.ColorContentDAO.saveColorring().insert";
    	 //Object [] parm = null;
    	 Object [] parm =	this.exchangeGcolorringToArray(gr);
			int result = DB.getInstance().executeBySQLCode(sqlCode,parm);
			logger.debug("insert into colorring :result="+result);
    }
    public void updateColorring(GColorring gr) throws DAOException{

   	 String sqlCodeupdate = "hwcolorring.ColorContentDAO.updateColorring().update";
   	// Object [] parm = null;
   	 Object [] parm =this.exchangeGcolorringToArray(gr);
			int result = DB.getInstance().executeBySQLCode(sqlCodeupdate,parm);
			logger.debug("update into colorring :result="+result);
   }
    
    /**
     * 
     * @param parm
     * @param gr
     */
    
    public Object [] exchangeGcolorringToArray(GColorring gr){
    	Object [] parm = null;
    	if(gr != null){
    		parm = new Object[19];
    		parm[0] = gr.getName();
    		parm[1] = gr.getTonenameletter();
    		parm[2] = gr.getSinger();
    		parm[3] = gr.getSingerletter();
    		parm[4] = gr.getIntroduction();
    		parm[5] = gr.getPrice();
    		parm[6] = gr.getLupdDate();
    		parm[7] = new Integer(gr.getDownloadtimes());
    		parm[8] = new Integer(gr.getSettimes());
    		parm[9] = gr.getAuditionUrl();
    		parm[10] = gr.getTonebigtype();
    		parm[11] = gr.getCateName();
    		parm[12] = gr.getExpire();
    		parm[13] = gr.getCreateDate();
    		parm[14] = gr.getMarketDate();
    		parm[15] = new Integer(gr.getAverageMark());
    		parm[16] = gr.getContentID();
    		parm[17] = gr.getClientAuditionUrl();
    		parm[18] = gr.getId();

    	}
    	return parm;
    }
    
    
    /**
     * 查询数据库中存在的所有彩铃的ID
     * @return
     * @throws DAOException
     */
    public HashMap getAllColorringID() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getAllColorringID()");
        }
        //select  t.id,t.lupddate,t.clientauditionurl  from t_r_colorring t 
        String sqlCode = "hwcolorring.ColorContentDAO.getAllColorringID().SELECT";
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
        HashMap res = new HashMap(1000000);//初始化话百万
        try
        {
            while (rs.next())
            {
            	ColorSyncVO vo=new ColorSyncVO();
            	vo.setLupDate(rs.getString("lupddate"));
            	vo.setFlag(0);
               
                String url=rs.getString("CLIENTAUDITIONURL");
                if(url==null||url.equals(""))
                {
                	vo.setConvert(false);
                }else
                {
                	vo.setConvert(true);
                }
                res.put(rs.getString("id"), vo);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }

        return res;
    }
}