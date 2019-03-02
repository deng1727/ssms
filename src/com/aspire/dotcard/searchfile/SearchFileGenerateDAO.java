
package com.aspire.dotcard.searchfile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.dotcard.gcontent.GMusic;
import com.aspire.dotcard.gcontent.GRead;

public class SearchFileGenerateDAO
{

    private static SearchFileGenerateDAO DAO = new SearchFileGenerateDAO();

    public static SearchFileGenerateDAO getInstance()
    {

        return DAO;
    }

    public ResultSet getDataByType(int relation, String contentType,
                                   String servAttr) throws BOException
    {

        try
        {
            // wap�ŵ��WWW�ŵ���ͬ
            if (SearchFileConstants.RELATION_W == relation)
            {
                if ("G".equals(servAttr))// ֻ�м��Ű����Ҫ��ѯ�������㶫���Ӧ�á����㶫���ѯ���е�Ӧ�á�
                {
                    Object paras[] = { contentType, servAttr };
                    String sqlCode = "searchfile.SearchFileGenerateDAO.getDataByType().SELECT_Attr.ForWWW";
                    return DB.getInstance().queryBySQLCode(sqlCode, paras);
                }
                else
                {
                    Object paras[] = { contentType };
                    String sqlCode = "searchfile.SearchFileGenerateDAO.getDataByType().SELECT_NOTAttr.ForWWW";
                    return DB.getInstance().queryBySQLCode(sqlCode, paras);
                }
            }
            else if (SearchFileConstants.RELATION_A == relation)
            {
                if ("G".equals(servAttr))// ֻ�м��Ű����Ҫ��ѯ�������㶫���Ӧ�á����㶫���ѯ���е�Ӧ�á�
                {
                    Object paras[] = { contentType, servAttr };
                    String sqlCode = "searchfile.SearchFileGenerateDAO.getDataByType().SELECT_Attr.ForWAP";
                    return DB.getInstance().queryBySQLCode(sqlCode, paras);
                }
                else
                {
                    Object paras[] = { contentType };
                    String sqlCode = "searchfile.SearchFileGenerateDAO.getDataByType().SELECT_NOTAttr.ForWAP";
                    return DB.getInstance().queryBySQLCode(sqlCode, paras);
                }
            }
            else
            // �ն�Ӧ��
            {

                if (contentType.indexOf("nt:gcontent:app") != -1)// Ӧ����
                {
                    if ("G".equals(servAttr))// ֻ�м��Ű����Ҫ��ѯ�������㶫���Ӧ�á����㶫���ѯ���е�Ӧ�á�
                    {
                        Object paras[] = { contentType, servAttr };
                        String sqlCode = "searchfile.SearchFileGenerateDAO.getDataByType().SELECT_Attr.ForMO";
                        return DB.getInstance().queryBySQLCode(sqlCode, paras);
                    }
                    else
                    {
                        Object paras[] = { contentType };
                        String sqlCode = "searchfile.SearchFileGenerateDAO.getDataByType().SELECT_NOTAttr.ForMO";
                        return DB.getInstance().queryBySQLCode(sqlCode, paras);
                    }
                }
                else
                // ��Ӧ����
                {
                    Object paras[] = { contentType };
                    String sqlCode = "searchfile.SearchFileGenerateDAO.getDataByType().SELECT_NOTAPPLY";
                    return DB.getInstance().queryBySQLCode(sqlCode, paras);
                }

            }

        }
        catch (DAOException e)
        {
            throw new BOException("��ȡ�������ͳ���type=" + contentType + ",servAttr="
                                  + servAttr, e);
        }
    }

    /*
     * public ResultSet getDataByNotApplyType(String type,String servAttr)throws
     * BOException { try {
     * if("G".equals(servAttr))//ֻ�м��Ű����Ҫ��ѯ�������㶫���Ӧ�á����㶫���ѯ���е�Ӧ�á� { Object
     * paras[]= {type,servAttr}; String sqlCode =
     * "searchfile.SearchFileGenerateDAO.getDataByType().SELECT_NOTAPPLY_Attr";
     * return DB.getInstance().queryBySQLCode(sqlCode, paras); }else { Object
     * paras[]= {type}; String sqlCode =
     * "searchfile.SearchFileGenerateDAO.getDataByType().SELECT_NOTAPPLY_NOTAttr";
     * return DB.getInstance().queryBySQLCode(sqlCode, paras); } } catch
     * (DAOException e) { throw new
     * BOException("��ȡ�������ͳ���type="+type+",servAttr="+servAttr,e); } }
     */


    public ResultSet getDataByOthreType(String type) throws BOException
    {

        String sqlCode;
        try
        {
            if (GMusic.TYPE_MUSIC.equals(type))
            {
                sqlCode = "searchfile.SearchFileGenerateDAO.getDataByMusic.SELECT";
            }
            else if (GRead.TYPE_READ.equals(type))
            {
                sqlCode = "searchfile.SearchFileGenerateDAO.getDataByRead.SELECT";
            }
            else if (SearchFileConstants.BREAD.equals(type))
            {
                sqlCode = "searchfile.SearchFileGenerateDAO.getDataByBRead.SELECT";
            }
            else if (SearchFileConstants.COLORRING.equals(type))
            {
                sqlCode = "searchfile.SearchFileGenerateDAO.getDataByColorring.SELECT";
            }
            else if (SearchFileConstants.VIDEO.equals(type))
            {
                sqlCode = "searchfile.SearchFileGenerateDAO.getDataByVideo.SELECT";
            }else if (SearchFileConstants.DISSERTATION.equals(type))
            {
                sqlCode = "searchfile.SearchFileGenerateDAO.getDataByDissertation.SELECT";
            }
            else if (SearchFileConstants.NEWMUSIC.equals(type))
            {
                sqlCode = "searchfile.SearchFileGenerateDAO.getDataByNewMusic.SELECT";
            }
            else if(SearchFileConstants.CY_DATA_2010.equals(type))
            {
                sqlCode = "searchfile.SearchFileGenerateDAO.getDataByCYData.2010";
                
                return DB.getInstance().queryBySQLCode(sqlCode, new Object[]{SearchFileConfig.CY_DATA_CATEGORYID});
            }
            else if(SearchFileConstants.CY_DATA.equals(type))
            {
                sqlCode = "searchfile.SearchFileGenerateDAO.getDataByCYData.2012";
            }
            //add by aiyan 2013-06-09
            else if(SearchFileConstants.CY_DATA_2013.equals(type))
            {
                sqlCode = "searchfile.SearchFileGenerateDAO.getDataByCYData.2013";
            }
            
            else if(SearchFileConstants.MARKET_PK.equals(type))
            {
                sqlCode = "searchfile.SearchFileGenerateDAO.getDataByCYData.market";
            }
            else if(SearchFileConstants.ULTIMATE_PK.equals(type))
            {
                sqlCode = "searchfile.SearchFileGenerateDAO.getDataByCYData.ultimate";
            }else if(SearchFileConstants.COMIC.equals(type)){
            	
            	sqlCode = "searchfile.SearchFileGenerateDAO.getDataByComic.SELECT";
            }else if(SearchFileConstants.TAG.equals(type)){
            	
            	sqlCode = "com.aspire.dotcard.searchfile.SearchFileGenerateDAO.getDataByOthreType.SELECT";
            }          
            else
            {
                throw new BOException("�÷���Ŀǰ��֧�ִ����ͣ�type=" + type);
            }

            return DB.getInstance().queryBySQLCode(sqlCode, null);
        }
        catch (DAOException e)
        {
            throw new BOException("��ȡ"+type+"�������ݳ���", e);
        }
    }

    /**
     * ��ȡ��ǰϵͳ�� ���ݵ�id����Ʒid��ӳ�䣬Ŀǰֻ�����ն˵���Ʒ��������Ʒid�������
     * 
     * @return
     */
    public HashMap getAllIDMappingGoodsId() throws DAOException
    {

        HashMap map = new HashMap(1000000, ( float ) 0.9);// Ĭ�����ɰ���Ƶ�Ͱ��С��
        String sqlCode = "searchfile.SearchFileGenerateDAO.getAllIDMappingGoodsId().SELECT";
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
        try
        {
            while (rs.next())
            {
                map.put(rs.getString(1), getGoodsIdByVirtual(rs.getString(2)));
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
        return map;
    }

    public String getGoodsIdByVirtual(String goodsId)
    {
    	String virtual  = SearchFileConfig.VIRTUAL_CATEGORY_ID;
    	virtual = virtual + goodsId.substring(9);
    	return virtual;
    }
    
    /**
     * ��ȡ��ǰϵͳ�� ���ݵ�id����Ʒid��ӳ�䣬Ŀǰֻ������ҵ��������Ʒ��������Ʒid�������
     * 
     * @return
     */
    public HashMap getAllIDMappingGoodsIdByCY() throws DAOException
    {

        HashMap map = new HashMap(1000000, ( float ) 0.9);// Ĭ�����ɰ���Ƶ�Ͱ��С��
        String sqlCode = "searchfile.SearchFileGenerateDAO.getAllIDMappingGoodsIdByCY().SELECT";
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
        try
        {
            while (rs.next())
            {
                map.put(rs.getString(1), rs.getString(2));
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
        return map;
    }
    /**
     * ��ȡ��ǰϵͳ�� ���ݵ�id����Ʒid��ӳ�䣬Ŀǰֻ�����ն˵���Ʒ��������Ʒid�������
     * 
     * @return
     */
    public HashMap getAllIDMappingGoodsIdByWWW() throws DAOException
    {

        HashMap map = new HashMap(1000000, ( float ) 0.9);// Ĭ�����ɰ���Ƶ�Ͱ��С��
        String sqlCode = "searchfile.SearchFileGenerateDAO.getAllIDMappingGoodsIdByWWW().SELECT";
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
        try
        {
            while (rs.next())
            {
                map.put(rs.getString(1), rs.getString(2));
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
        return map;
    }

    /**
     * ��ȡ��ǰϵͳ�� ���ݵ�id����Ʒid��ӳ�䣬Ŀǰֻ����WAP�ŵ����Ʒ��������Ʒid�������
     * 
     * @return
     */
    public HashMap getAllIDMappingGoodsIdByWAP() throws DAOException
    {

        HashMap map = new HashMap(1000000, ( float ) 0.9);// Ĭ�����ɰ���Ƶ�Ͱ��С��
        String sqlCode = "searchfile.SearchFileGenerateDAO.getAllIDMappingGoodsIdByWAP().SELECT";
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
        try
        {
            while (rs.next())
            {
                map.put(rs.getString(1), rs.getString(2));
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
        return map;
    }

    /**
     * ���ڻ�ȡȫ��Ӧ��������Ϣ
     * 
     * @return
     * @throws BOException
     */
    public ResultSet getAllContentData() throws DAOException
    {

        try
        {
            // select b.type, g.id, g.name, 1, g.singer, 1, g.spname,
            // (to_clob(translate(g.devicename, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename02, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename03, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename04, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename05, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename06, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename07, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename08, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename09, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename10, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename11, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename12, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename13, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename14, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename15, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename16, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename17, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename18, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename19, 'Z_- ', 'Z')) ||
            // to_clob(translate(g.devicename20, 'Z_- ', 'Z'))) as fullDevice,
            // 'O', s.mobileprice, g.averagemark, g.marketdate, g.keywords,
            // g.introduction, g.scantimes, g.appcateid, g.chargetime,
            // decode(g.servattr, '', 'G', g.servattr) as servattr,
            // decode(g.provider, '', 'O', g.provider) as provider from
            // t_r_gcontent g, t_r_base b, v_service s where g.id = b.id and
            // g.icpcode = s.icpcode and g.icpservid = s.icpservid and b.type
            // like 'nt:gcontent:app%'
            String sqlCode = "searchfile.SearchFileGenerateDAO.getDataByType().SELECT";
            return DB.getInstance().queryBySQLCode(sqlCode, null);

        }
        catch (DAOException e)
        {
            throw new DAOException("��ȡȫ��Ӧ���������ͳ���", e);
        }
    }
    
    /**
     * �õ�Ҫ�����ļ���ȫ�����ݵ���ֵ
     * @return
     * @throws DAOException
     */
    public int getAllContentDataSum() throws DAOException
    {
    	int sum = 0;
    	ResultSet rs = null;
    	
        try
        {
			// select count(1) as countNum from t_r_gcontent g, t_r_base b,
			// v_service s, V_CM_CONTENT_DARKHORSE h where g.id = b.id and
			// g.contentid = h.contentid(+) and g.icpcode = s.icpcode and
			// g.icpservid = s.icpservid and (g.subtype is null or (g.subtype <>
			// '6' and g.subtype <> '11' and g.subtype <> '12' and g.subtype <>
			// '16')) and (g.provider != 'B' or (g.provider = 'B' and
			// g.programid = '-1')) order by g.id
			String sqlCode = "searchfile.SearchFileGenerateDAO.getDataByType().SELECT.sum";
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			
			if(rs.next())
			{
				sum = rs.getInt("countNum");
			}
		}
        catch (DAOException e)
        {
            throw new DAOException("�õ�Ҫ�����ļ���ȫ�����ݵ���ֵ����", e);
        }
        catch (SQLException e)
        {
        	throw new DAOException("�õ�Ҫ�����ļ���ȫ�����ݵ���ֵ����", e);
		}
        
        return sum;
    }
    
    /**
     * ��ҳ��ȡȫ��Ӧ��������Ϣ
     * 
     * @return
     * @throws BOException
     */
    public ResultSet getAllContentDataByPageNum(int start, int end) throws DAOException
    {
    	String sqlCode = "searchfile.SearchFileGenerateDAO.getDataByType().SELECT";
    	StringBuffer sql = new StringBuffer();
    	
        try
        {
        	sql.append("SELECT * FROM (SELECT aa.*, ROWNUM RN FROM (");
        	sql.append(DB.getInstance().getSQLByCode(sqlCode));
        	sql.append(") aa WHERE ROWNUM <= ?) WHERE RN > ?");
        	
            return DB.getInstance().query(sql.toString(), new Object[]{end, start});
        }
        catch (DAOException e)
        {
            throw new DAOException("��ҳ��ȡȫ��Ӧ��������Ϣ����", e);
        }
    }
}
