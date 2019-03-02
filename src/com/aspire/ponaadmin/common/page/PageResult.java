package com.aspire.ponaadmin.common.page ;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.util.SQLCode;

/**
 * <p>��ѯ����ṹ</p>
 * <p>���ݲ�ͬ��ʹ�ó��ϣ��ṩ�˶��ֹ������ķ�ʽ<br/>
 * 1.Ĭ�ϵĹ��췽ʽ,����Ĭ�ϵ�ҳ���С��ȡ��ҳ���� <br/>
 * 2.ָ��ÿҳ��С��ҳ��Ĺ��췽ʽ<br/>
 * 3.��httpRequest�����죬ʵ�ִ�httpRequestȡ�ļ�¼������ҳ����� <br/>
 * 4.ָ��PageSize�������ʵ�ְ���PageSizeȡ��ҳ�ķ���,���PageSizeС�ڵ���0��ȡȫ������<br/>
 * <p>
 * ���ݲ�ͬ��ʹ�ó��ϣ��ṩ�˶���ִ�еķ�ʽ<br/>
 * 1.excute---�ṩSQL����ִ�з�ʽ<br/>
 * 2.excuteBySQLCode---�ṩSQLCode��ִ�з�ʽ <br/>
 * </p>
 * <p>Copyright: Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author maobg
 * @version 1.0.0.0
 */
public class PageResult
    implements Serializable
{

    private static final long serialVersionUID = 1L;

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(PageResult.class) ;

    /**
     * �ܼ�¼��
     */
    protected int totalRows = 0 ;

    /**
     * ��ǰҳ��
     */
    protected int currentPageNo = 1 ;

    /**
     * ��ҳ��
     */
    protected int totalPages = 0 ;

    /**
     * ��ǰҳ�ļ�¼�Ŀ�ʼ���
     */
    protected int startnum = 1 ;

    /**
     * ��ǰҳ�ļ�¼�Ľ�β���
     */
    protected int endnum = PageConstants.DEF_PAGE_SIZE;

    /**
     * ÿҳ��¼��
     */
    protected int pageSize = PageConstants.DEF_PAGE_SIZE ;

    /**
     * ���������ļ�¼�����
     */
    protected List pageInfo = new ArrayList() ;

    /**
     * ƫ��λ��
     */
    protected int scrollNum = 0;
    /**
     * ���캯��
     */
    public PageResult ()
    {
        logger.debug("������һ��Ĭ�ϵ���ҳPageResult����") ;
    }

    /**
     * ���캯��
     */
    public PageResult (int pageSize)
    {
        this.pageSize = pageSize ;
        if ( 0 >= pageSize)
        {
            logger.debug("������ȡȫ�����ݵ�PageResult����") ;
        }
        else
        {
            logger.debug("������һ��ÿҳȡ" + pageSize + "����¼ȡ��ҳ��PageResult����") ;
        }
    }

    /**
     * ���캯��
     * @param PageSize int  ÿҳ��¼��
     * @param PageNo int    ҳ�����
     */
    public PageResult (int pageSize, int pageNo)
    {
        this.pageSize = pageSize ;
        this.currentPageNo = pageNo ;
        if (logger.isDebugEnabled())
        {
            logger.debug("������һ��ÿҳȡ" + pageSize + "����¼��ȡ��" + currentPageNo +
                         "ҳ���ݵ�PageResult����") ;
        }
    }

    /**
     *������HttpServletRequest����һ���趨��ҳ������PageResult��ҳ����
     * @param httpServletRequest HttpServletRequest
     * @param pageSize int
     */
    public PageResult (HttpServletRequest httpServletRequest, int pageSize)
    {
        init(httpServletRequest, pageSize) ;
    }

    /**
     * ��ȡ����
     */
    private void init (HttpServletRequest httpServletRequest, int pageSize)
    {
        //����ÿҳ��¼��
        this.pageSize = pageSize ;

        String param = "" ;

        //��ȡ�ܼ�¼���������ȡʧ�������򸳳�ֵ-1��Ϊ�ǳ��β�ѯ
        param = httpServletRequest.getParameter(PageConstants.
                                                PAGE_RECCOUNT_NAME) ;
        if (null != param && (param.length() != 0))
        {
            this.totalRows = Integer.parseInt(param) ;
        }
        else
        {
            this.totalRows = -1 ;
        }

        //����������еõ���ǰҳ��ţ������ȡʧ�ܣ�����Ϊȡ��һҳ
        param = httpServletRequest.getParameter(PageConstants.PAGE_INDEX_NAME) ;
        if (null != param && (param.length() != 0))
        {
            this.currentPageNo = Integer.parseInt(param) ;
        }
        else
        {
            this.currentPageNo = 1 ;
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("����HttpRequest������һ����ÿҳȡ" + pageSize + "����¼��ȡ��" +
                         currentPageNo + "ҳ���ݵ�PageResult����") ;
        }
    }

    /**
     * ����HttpServletRequest����һ��Ĭ��ÿҳ����PageResult��ҳ����
     * @param httpServletRequest HttpServletRequest
     */
    public PageResult (HttpServletRequest httpServletRequest)
    {
        init(httpServletRequest, PageConstants.DEF_PAGE_SIZE) ;
    }

    /**
     * ִ��SQL��ѯ����ѯ����ŵ�List�У�VO������pagevo���𴴽��ʹ�ResultSet��ֵ
     * @param sql String  ��ȡ���ݵ�SQL,ͬʱҲ������ȡ������SQL
     * @param paras Object ��̬��������
     * @param pagevo PageVOInterface
     */
    public void excute (String sql, Object paras[], PageVOInterface pagevo) throws DAOException
    {
        excute(sql, null, paras, pagevo) ;
    }

    /**
     * ִ��SQL��ѯ����ѯ���VO�ŵ�List�У�VO������pagevo���𴴽��ʹ�ResultSet��ֵ
     * @param sql String  ��ȡ���ݵ�SQL
     * @param sqlcount String ��ȡ�ܼ�¼����SQL
     * @param paras Object ��̬��������
     * @param pagevo PageVOInterface
     */
    public void excute (String sql, String sqlcount, Object paras[],
                        PageVOInterface pagevo) throws DAOException
    {
        pageInfo.clear();
        ResultSet rs = null ;
        //�ܼ�¼��С��0��˵�����������µĲ�ѯ,���������Է�ҳ����
//        if (0 >= this.totalRows)
        //���ڴ��ڷ�ҳ���¼���������仯���ܵ�����ҳ����ת������������ڣ�����ÿ�ζ�����һ�������ɡ�
        if (true)
        {
            if(sqlcount==null)
            {
                sqlcount = "SELECT count(*) FROM (" + sql + ")" ;
            }
            rs = DB.getInstance().query(sqlcount, paras) ;
            try
            {
                if (rs.next())
                {
                    this.totalRows = rs.getInt(1) ;
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("��ȡ�ļ�¼����Ŀ=" + this.totalRows) ;
                    }
                }
            }
            catch (SQLException e)
            {   
            	e.printStackTrace();
                throw new DAOException("ִ��SQL�쳣:", e) ;
            }
            finally
            {
                DB.close(rs) ;
            }
        }
        
        // ֻ�е���¼������0ʱ,����Ҫȥ�����ݿ�
        if (totalRows > 0)
        {
//          ����ƫ����,��ƫ���������ܼ�¼������С��0�����,��Ҫת��Ϊ����.
            int offset = (scrollNum % totalRows + totalRows) % totalRows;
            //����ƫ����ʼֵ�ͽ���ֵ
            int offset_start;
            int offset_end;
            if (logger.isDebugEnabled())
            {
                logger.debug("scrollNum=" + scrollNum + ",offset=" + offset);
            }

            // ������ҳ������ǰҳ����ʼ��źͽ������
            if (0 >= pageSize)
            {
                this.totalPages = 1;
                this.startnum = 1;
                this.endnum = this.totalRows;
                this.currentPageNo = 1;
                offset_start = startnum;
                offset_end = endnum;
            }
            else
            {
                // pageSize����0ʱ������ҳ����ż��㱾�����������ʼ��¼�ͽ�β��¼
                this.totalPages = this.totalRows
                                  / this.pageSize
                                  + ((this.totalRows % this.pageSize == 0) ? 0
                                                  : 1);
                // ����ҳ������ҳ����ȡ���һҳ������
                if (this.totalPages < this.currentPageNo)
                {
                    this.currentPageNo = this.totalPages;
                }
                //���㵱ǰҳ����ʼֵ�ͽ���ֵ
                this.startnum = this.pageSize * (this.currentPageNo - 1) + 1;
                this.endnum = startnum + this.pageSize - 1 ;
                // �������ֵ�����ܼ�¼,��ȡ���һ����¼
                if (endnum > totalRows)
                {
                    endnum = totalRows;
                }
                
                // ����ƫ����ʼֵ
                offset_start = ((startnum + offset) % totalRows) == 0 ? totalRows
                                : (startnum + offset) % totalRows;
                // ����ƫ�ƽ���ֵ
                offset_end = ((endnum + offset) % totalRows) == 0 ? totalRows
                                : (endnum + offset) % totalRows;
            }

            if (logger.isDebugEnabled())
            {
                logger.debug("startnum=" + startnum + ";endnum=" + endnum
                             + ";offset_start=" + offset_start + ";ofset_end="
                             + offset_end);
            }

            // ȡ����
            if (offset_start <= offset_end)
            {
                getRecord(sql,paras,pagevo,offset_start, offset_end);
            }
            else
            {
                getRecord(sql,paras,pagevo,offset_start, totalRows);
                getRecord(sql,paras,pagevo,1, offset_end);
            }
        }
    }
    
    /**
     * ���ݷ�Χ,�����ݿ���ȡ����,�ŵ���ҳ��pageInfo��
     * @param sql SQL���
     * @param paras ����
     * @param pagevo �����VO
     * @param startnum ��ʼ���
     * @param endnum �������
     */
    private void getRecord(String sql, Object paras[], PageVOInterface pagevo,
                           int startnum, int endnum) throws DAOException
    {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try
        {
            // sql = "SELECT * FROM (SELECT rownum num,a.* FROM ("+sql+") a ) b
            // WHERE b.num>="+startnum+" AND b.num<="+endnum;
            conn = DB.getInstance().getConnection();
            statement = conn.prepareStatement(sql,
                                              java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
                                              java.sql.ResultSet.CONCUR_READ_ONLY);
            if (0 < pageSize)
            {
                statement.setFetchSize(this.pageSize);
            }
            statement.setMaxRows(endnum);
            DB.getInstance().prepareParams(statement, paras);
            rs = statement.executeQuery();
            if (rs != null && rs.next())
            {
                rs.absolute(startnum);
                do
                {
                    Object vo = pagevo.createObject();
                    pagevo.CopyValFromResultSet(vo, rs);
                    pageInfo.add(vo);
                }
                while (rs.next());
            }
        }
        catch (Exception e)
        {
            throw new DAOException("��ȡ�������LIST�з����쳣:", e);
        }
        finally
        {
            DB.close(rs, statement, conn);
        }
    }

    /**
     * ʹ��SQLCODEִ�У�ȡ������SQLCODE��ȡ���ݵ�SQLCODE��ͬ
     * 
     * @param sqlcode String ��ȡ���ݵ�SQLCode,ͬʱҲ������ȡ������SQLCode
     * @param paras Object[]
     * @param pagevo PageVOInterface
     */
    public void excuteBySQLCode (String sqlcode, Object[] paras,
                                 PageVOInterface pagevo) throws DAOException
    {
        excuteBySQLCode(sqlcode, null, paras, pagevo) ;
    }

    /**
     * ʹ��SQLCODEִ�У�ȡ������SQLCODE��ȡ���ݵ�SQLCODE��ͬ
     * @param sqlcode String ��ȡ���ݵ�SQLCode
     * @param sqlcountcode String ������ȡ������SQLCode
     * @param paras Object[]
     * @param pagevo PageVOInterface
     */
    public void excuteBySQLCode (String sqlcode, String sqlcountcode,
                                 Object[] paras, PageVOInterface pagevo) throws DAOException
    {
        String sql = "" ;
        String sqlcount = null ;
        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlcode) ;
        }
        catch (Exception e)
        {
            throw new DAOException("get SQLCode 1 failed.", e) ;
        }
        try
        {
            if (sqlcountcode!=null)
            {
                sqlcount = SQLCode.getInstance().getSQLStatement(sqlcountcode) ;
            }
        }
        catch (Exception e)
        {
            throw new DAOException("get SQLCode 2 failed.", e) ;
        }
        excute(sql, sqlcount, paras, pagevo) ;
    }

    public void excute (PageDAO pageDAO, Object para) throws DAOException
    {
        pageInfo.clear();
        //���ڴ��ڷ�ҳ���¼���������仯���ܵ�����ҳ����ת������������ڣ�����ÿ�ζ�����һ�������ɡ�
        if (true)
        {
            try
            {
                this.totalRows = pageDAO.getCount(para);
            }
            catch (Exception e)
            {
                logger.error(e);
                throw new DAOException("ִ��iBatas���ݿ����ʧ��:", e) ;
            }
        }
        if ( 0 >= pageSize)
        {
            this.totalPages = 1;
            this.startnum = 1;
            this.endnum = this.totalRows;
            this.currentPageNo = 1;
        }
        else
        {
            //pageSize����0ʱ������ҳ����ż��㱾�����������ʼ��¼�ͽ�β��¼
            this.totalPages = this.totalRows / this.pageSize +
                ((this.totalRows % this.pageSize == 0) ? 0 : 1) ;
            // ����ҳ������ҳ����ȡ���һҳ������
            if (this.totalPages < this.currentPageNo)
            {
                this.currentPageNo = this.totalPages;
            }
            this.startnum = this.pageSize * (this.currentPageNo - 1) + 1 ;
            this.endnum = startnum + this.pageSize - 1 ;
        }
        try
        {
            List list = pageDAO.getList(para,this.startnum,this.pageSize);
            if(list!=null&&list.size()!=0)
            {
                this.pageInfo.addAll(list);
            }
        }
        catch (Exception e)
        {
            throw new DAOException("ִ��iBatas���ݿ����ʧ��:", e) ;
        }
    }
    
    /**
     * ��һ��list���뵽��ҳ�С�
     * @param allList List
     */
    public void excute(List allList) {
        if (allList == null || allList.size() == 0) {
            return;
        }
        pageInfo.clear();

        //�ܼ�¼��С��0��˵�����������µĲ�ѯ,���������Է�ҳ����
        if (0 >= this.totalRows) {
            this.totalRows = allList.size();
        }
        if (0 >= pageSize) {
            this.totalPages = 1;
            this.startnum = 1;
            this.endnum = this.totalRows;
            this.currentPageNo = 1;
        } else {
            //pageSize����0ʱ������ҳ����ż��㱾�����������ʼ��¼�ͽ�β��¼
            this.totalPages = this.totalRows / this.pageSize +
                              ((this.totalRows % this.pageSize == 0) ? 0 : 1);
            this.startnum = this.pageSize * (this.currentPageNo - 1) + 1;
            this.endnum = startnum + this.pageSize - 1;
            this.endnum = (this.endnum >= totalRows ? totalRows : this.endnum);
        }
        pageInfo = allList.subList(startnum - 1, endnum);

    }
    //ȡ������·���ļ��б�
    public void excute (File[] f,PageVOFileInterface pagevo)
    {
        pageInfo.clear();

        this.totalRows = f.length;

        if ( 0 >= pageSize)
        {
            this.totalPages = 1;
            this.startnum = 1;
            this.endnum = this.totalRows;
            this.currentPageNo = 1;
        }
        else
        {
            //pageSize����0ʱ������ҳ����ż��㱾�����������ʼ��¼�ͽ�β��¼
            this.totalPages = this.totalRows / this.pageSize +
                ((this.totalRows % this.pageSize == 0) ? 0 : 1) ;
            // ����ҳ������ҳ����ȡ���һҳ������
            if (this.totalPages < this.currentPageNo)
            {
                this.currentPageNo = this.totalPages;
            }
            this.startnum = this.pageSize * (this.currentPageNo - 1) ;
            this.endnum = startnum + this.pageSize - 1 ;
        }

        if(totalRows!=0)
        {
            if (totalRows - (endnum + 1) >= 0) //����fileList����
            {
                for (int i = startnum ; i <= endnum ; i++) //����fileList����
                {
                    Object vo = pagevo.createObject() ;
                    pagevo.CopyValFromFile(vo, f[i]) ;
                    pageInfo.add(vo) ;
                }
            }
            else
            {
                for (int i = startnum ; i < totalRows ; i++)
                {
                    Object vo = pagevo.createObject() ;
                    pagevo.CopyValFromFile(vo, f[i]) ;
                    pageInfo.add(vo) ;
                }
            }
        }

    }

    //ȡ������·���ļ��б�
    public void excute (String[] fileList,String path,PageVOFileInterface pagevo)
    {
        pageInfo.clear();

        this.totalRows = fileList.length;

        if ( 0 >= pageSize)
        {
            this.totalPages = 1;
            this.startnum = 1;
            this.endnum = this.totalRows;
            this.currentPageNo = 1;
        }
        else
        {
            //pageSize����0ʱ������ҳ����ż��㱾�����������ʼ��¼�ͽ�β��¼
            this.totalPages = this.totalRows / this.pageSize +
                ((this.totalRows % this.pageSize == 0) ? 0 : 1) ;
            // ����ҳ������ҳ����ȡ���һҳ������
            if (this.totalPages < this.currentPageNo)
            {
                this.currentPageNo = this.totalPages;
            }
            this.startnum = this.pageSize * (this.currentPageNo - 1) ;
            this.endnum = startnum + this.pageSize - 1 ;
        }
        if(totalRows!=0)
        {
            if (totalRows - (endnum + 1) >= 0) //����fileList����
            {
                for (int i = startnum ; i <= endnum ; i++) //����fileList����
                {
                    Object vo = pagevo.createObject() ;
                    File newFile = new File(path,fileList[i]);//����һ��File����
                    pagevo.CopyValFromFile(vo, newFile) ;
                    pageInfo.add(vo) ;
                }
            }
            else
            {
                for (int i = startnum ; i < totalRows ; i++)
                {
                    Object vo = pagevo.createObject() ;
                    File newFile = new File(path,fileList[i]);//����һ��File����
                    pagevo.CopyValFromFile(vo, newFile) ;
                    pageInfo.add(vo) ;
                }
            }
        }

    }

    public List getPageInfo ()
    {
        return pageInfo ;
    }

    public void setPageInfo (List pageInfo)
    {
        this.pageInfo = pageInfo ;
    }

    public int getPageSize ()
    {
        return pageSize ;
    }

    public void setPageSize (int pageSize)
    {
        this.pageSize = pageSize ;
    }

    public int getTotalPages ()
    {
        return totalPages ;
    }

    public void setTotalPages (int totalPages)
    {
        this.totalPages = totalPages ;
    }

    public int getTotalRows ()
    {
        return totalRows ;
    }

    public void setTotalRows (int totalRows)
    {
        this.totalRows = totalRows ;
    }

    public int getCurrentPageNo ()
    {
        return currentPageNo ;
    }

    public void setCurrentPageNo (int currentPageNo)
    {
        this.currentPageNo = currentPageNo ;
    }

    public int getStartnum ()
    {
        return startnum ;
    }

    /**
     * getNextPageNo
     *
     * @return int
     */
    public int getNextPageNo ()
    {
        return (currentPageNo + 1 > totalPages) ? totalPages :
            currentPageNo + 1 ;
    }

    /**
     *
     * @return
     */
    public int getPrevPageNo ()
    {
        return (currentPageNo - 1 < 1) ? 1 :
            currentPageNo - 1 ;
    }

    /**
     * isFirstPage
     *
     * @return boolean
     */
    public boolean isFirstPage ()
    {
        return currentPageNo <= 1 ;
    }

    /**
     * isLastPage
     *
     * @return boolean
     */
    public boolean isLastPage ()
    {
        return currentPageNo >= totalPages ;
    }
    
    /**
     * @param scrollNum The scrollNum to set.
     */
    public void setScrollNum(int scrollNum)
    {
    
        this.scrollNum = scrollNum;
    }
    
}
