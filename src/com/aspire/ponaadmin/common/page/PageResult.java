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
 * <p>查询结果结构</p>
 * <p>根据不同的使用场合，提供了多种构造对象的方式<br/>
 * 1.默认的构造方式,采用默认的页面大小、取首页数据 <br/>
 * 2.指定每页大小，页序的构造方式<br/>
 * 3.从httpRequest对象构造，实现从httpRequest取的记录总数，页序参数 <br/>
 * 4.指定PageSize构造对象，实现按照PageSize取首页的方法,如果PageSize小于等于0则取全量数据<br/>
 * <p>
 * 根据不同的使用场合，提供了多种执行的方式<br/>
 * 1.excute---提供SQL语句的执行方式<br/>
 * 2.excuteBySQLCode---提供SQLCode的执行方式 <br/>
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
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(PageResult.class) ;

    /**
     * 总记录数
     */
    protected int totalRows = 0 ;

    /**
     * 当前页号
     */
    protected int currentPageNo = 1 ;

    /**
     * 总页数
     */
    protected int totalPages = 0 ;

    /**
     * 当前页的记录的开始序号
     */
    protected int startnum = 1 ;

    /**
     * 当前页的记录的结尾序号
     */
    protected int endnum = PageConstants.DEF_PAGE_SIZE;

    /**
     * 每页记录数
     */
    protected int pageSize = PageConstants.DEF_PAGE_SIZE ;

    /**
     * 满足条件的记录结果集
     */
    protected List pageInfo = new ArrayList() ;

    /**
     * 偏移位数
     */
    protected int scrollNum = 0;
    /**
     * 构造函数
     */
    public PageResult ()
    {
        logger.debug("构造了一个默认的首页PageResult对象") ;
    }

    /**
     * 构造函数
     */
    public PageResult (int pageSize)
    {
        this.pageSize = pageSize ;
        if ( 0 >= pageSize)
        {
            logger.debug("构造了取全量数据的PageResult对象") ;
        }
        else
        {
            logger.debug("构造了一个每页取" + pageSize + "条记录取首页的PageResult对象") ;
        }
    }

    /**
     * 构造函数
     * @param PageSize int  每页记录数
     * @param PageNo int    页面序号
     */
    public PageResult (int pageSize, int pageNo)
    {
        this.pageSize = pageSize ;
        this.currentPageNo = pageNo ;
        if (logger.isDebugEnabled())
        {
            logger.debug("构造了一个每页取" + pageSize + "条记录，取第" + currentPageNo +
                         "页数据的PageResult对象") ;
        }
    }

    /**
     *　跟据HttpServletRequest构造一个设定分页数量的PageResult分页对象
     * @param httpServletRequest HttpServletRequest
     * @param pageSize int
     */
    public PageResult (HttpServletRequest httpServletRequest, int pageSize)
    {
        init(httpServletRequest, pageSize) ;
    }

    /**
     * 获取参数
     */
    private void init (HttpServletRequest httpServletRequest, int pageSize)
    {
        //设置每页记录数
        this.pageSize = pageSize ;

        String param = "" ;

        //获取总记录数，如果获取失败数，则赋初值-1认为是初次查询
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

        //从请求参数中得到当前页序号，如果获取失败，则认为取第一页
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
            logger.debug("来自HttpRequest构造了一个：每页取" + pageSize + "条记录，取第" +
                         currentPageNo + "页数据的PageResult对象") ;
        }
    }

    /**
     * 跟据HttpServletRequest构造一个默认每页数量PageResult分页对象
     * @param httpServletRequest HttpServletRequest
     */
    public PageResult (HttpServletRequest httpServletRequest)
    {
        init(httpServletRequest, PageConstants.DEF_PAGE_SIZE) ;
    }

    /**
     * 执行SQL查询，查询结果放导List中，VO对象由pagevo负责创建和从ResultSet赋值
     * @param sql String  提取数据的SQL,同时也用于提取总数的SQL
     * @param paras Object 动态参数数组
     * @param pagevo PageVOInterface
     */
    public void excute (String sql, Object paras[], PageVOInterface pagevo) throws DAOException
    {
        excute(sql, null, paras, pagevo) ;
    }

    /**
     * 执行SQL查询，查询结果VO放导List中，VO对象由pagevo负责创建和从ResultSet赋值
     * @param sql String  提取数据的SQL
     * @param sqlcount String 获取总记录数的SQL
     * @param paras Object 动态参数数组
     * @param pagevo PageVOInterface
     */
    public void excute (String sql, String sqlcount, Object paras[],
                        PageVOInterface pagevo) throws DAOException
    {
        pageInfo.clear();
        ResultSet rs = null ;
        //总记录数小于0，说明请求来自新的查询,否则是来自翻页请求
//        if (0 >= this.totalRows)
        //由于存在翻页后记录集的数量变化可能到导致页面跳转后错误的情况存在，所以每次都计算一下总数吧。
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
                        logger.debug("获取的记录总数目=" + this.totalRows) ;
                    }
                }
            }
            catch (SQLException e)
            {   
            	e.printStackTrace();
                throw new DAOException("执行SQL异常:", e) ;
            }
            finally
            {
                DB.close(rs) ;
            }
        }
        
        // 只有当记录数大于0时,才需要去查数据库
        if (totalRows > 0)
        {
//          处理偏移量,当偏移量大于总记录数或者小于0的情况,需要转化为正数.
            int offset = (scrollNum % totalRows + totalRows) % totalRows;
            //定义偏移起始值和结束值
            int offset_start;
            int offset_end;
            if (logger.isDebugEnabled())
            {
                logger.debug("scrollNum=" + scrollNum + ",offset=" + offset);
            }

            // 计算总页数、当前页的起始序号和结束序号
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
                // pageSize大于0时，根据页面序号计算本次请求的其起始记录和结尾记录
                this.totalPages = this.totalRows
                                  / this.pageSize
                                  + ((this.totalRows % this.pageSize == 0) ? 0
                                                  : 1);
                // 请求页数〉总页数则取最后一页的数据
                if (this.totalPages < this.currentPageNo)
                {
                    this.currentPageNo = this.totalPages;
                }
                //计算当前页的起始值和结束值
                this.startnum = this.pageSize * (this.currentPageNo - 1) + 1;
                this.endnum = startnum + this.pageSize - 1 ;
                // 假如结束值大于总记录,则取最后一条记录
                if (endnum > totalRows)
                {
                    endnum = totalRows;
                }
                
                // 计算偏移起始值
                offset_start = ((startnum + offset) % totalRows) == 0 ? totalRows
                                : (startnum + offset) % totalRows;
                // 计算偏移结束值
                offset_end = ((endnum + offset) % totalRows) == 0 ? totalRows
                                : (endnum + offset) % totalRows;
            }

            if (logger.isDebugEnabled())
            {
                logger.debug("startnum=" + startnum + ";endnum=" + endnum
                             + ";offset_start=" + offset_start + ";ofset_end="
                             + offset_end);
            }

            // 取数据
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
     * 根据范围,从数据库里取数据,放到分页的pageInfo里
     * @param sql SQL语句
     * @param paras 参数
     * @param pagevo 结果集VO
     * @param startnum 起始序号
     * @param endnum 结束序号
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
            throw new DAOException("提取结果集到LIST中发生异常:", e);
        }
        finally
        {
            DB.close(rs, statement, conn);
        }
    }

    /**
     * 使用SQLCODE执行，取总数的SQLCODE和取数据的SQLCODE相同
     * 
     * @param sqlcode String 提取数据的SQLCode,同时也用于提取总数的SQLCode
     * @param paras Object[]
     * @param pagevo PageVOInterface
     */
    public void excuteBySQLCode (String sqlcode, Object[] paras,
                                 PageVOInterface pagevo) throws DAOException
    {
        excuteBySQLCode(sqlcode, null, paras, pagevo) ;
    }

    /**
     * 使用SQLCODE执行，取总数的SQLCODE和取数据的SQLCODE不同
     * @param sqlcode String 提取数据的SQLCode
     * @param sqlcountcode String 用于提取总数的SQLCode
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
        //由于存在翻页后记录集的数量变化可能到导致页面跳转后错误的情况存在，所以每次都计算一下总数吧。
        if (true)
        {
            try
            {
                this.totalRows = pageDAO.getCount(para);
            }
            catch (Exception e)
            {
                logger.error(e);
                throw new DAOException("执行iBatas数据库访问失败:", e) ;
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
            //pageSize大于0时，根据页面序号计算本次请求的其起始记录和结尾记录
            this.totalPages = this.totalRows / this.pageSize +
                ((this.totalRows % this.pageSize == 0) ? 0 : 1) ;
            // 请求页数〉总页数则取最后一页的数据
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
            throw new DAOException("执行iBatas数据库访问失败:", e) ;
        }
    }
    
    /**
     * 把一个list加入到分页中。
     * @param allList List
     */
    public void excute(List allList) {
        if (allList == null || allList.size() == 0) {
            return;
        }
        pageInfo.clear();

        //总记录数小于0，说明请求来自新的查询,否则是来自翻页请求
        if (0 >= this.totalRows) {
            this.totalRows = allList.size();
        }
        if (0 >= pageSize) {
            this.totalPages = 1;
            this.startnum = 1;
            this.endnum = this.totalRows;
            this.currentPageNo = 1;
        } else {
            //pageSize大于0时，根据页面序号计算本次请求的其起始记录和结尾记录
            this.totalPages = this.totalRows / this.pageSize +
                              ((this.totalRows % this.pageSize == 0) ? 0 : 1);
            this.startnum = this.pageSize * (this.currentPageNo - 1) + 1;
            this.endnum = startnum + this.pageSize - 1;
            this.endnum = (this.endnum >= totalRows ? totalRows : this.endnum);
        }
        pageInfo = allList.subList(startnum - 1, endnum);

    }
    //取得物理路径文件列表
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
            //pageSize大于0时，根据页面序号计算本次请求的其起始记录和结尾记录
            this.totalPages = this.totalRows / this.pageSize +
                ((this.totalRows % this.pageSize == 0) ? 0 : 1) ;
            // 请求页数〉总页数则取最后一页的数据
            if (this.totalPages < this.currentPageNo)
            {
                this.currentPageNo = this.totalPages;
            }
            this.startnum = this.pageSize * (this.currentPageNo - 1) ;
            this.endnum = startnum + this.pageSize - 1 ;
        }

        if(totalRows!=0)
        {
            if (totalRows - (endnum + 1) >= 0) //遍历fileList数组
            {
                for (int i = startnum ; i <= endnum ; i++) //遍历fileList数组
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

    //取得物理路径文件列表
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
            //pageSize大于0时，根据页面序号计算本次请求的其起始记录和结尾记录
            this.totalPages = this.totalRows / this.pageSize +
                ((this.totalRows % this.pageSize == 0) ? 0 : 1) ;
            // 请求页数〉总页数则取最后一页的数据
            if (this.totalPages < this.currentPageNo)
            {
                this.currentPageNo = this.totalPages;
            }
            this.startnum = this.pageSize * (this.currentPageNo - 1) ;
            this.endnum = startnum + this.pageSize - 1 ;
        }
        if(totalRows!=0)
        {
            if (totalRows - (endnum + 1) >= 0) //遍历fileList数组
            {
                for (int i = startnum ; i <= endnum ; i++) //遍历fileList数组
                {
                    Object vo = pagevo.createObject() ;
                    File newFile = new File(path,fileList[i]);//构造一个File对象
                    pagevo.CopyValFromFile(vo, newFile) ;
                    pageInfo.add(vo) ;
                }
            }
            else
            {
                for (int i = startnum ; i < totalRows ; i++)
                {
                    Object vo = pagevo.createObject() ;
                    File newFile = new File(path,fileList[i]);//构造一个File对象
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
