package com.aspire.ponaadmin.web.system ;

import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 *     the servlet to show version & patch info at startup
 *     @author   yanfeng
 *     @version  1.6.5
 *     @since    Xportal1.6.5
 * @CheckItem@OPT-yanfeng-20041010 overload the destroy method to avoid NullPoint error
 */

public class StartupVersionShowServlet
    extends HttpServlet
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    JLogger log = LoggerFactory.getLogger(StartupVersionShowServlet.class) ;

    private final String LS = System.getProperty("line.separator") ;

    private final int charLength = 37 ;

    public StartupVersionShowServlet ()
    {
    }

    public void init (ServletConfig config)
        throws ServletException
    {

        try
        {
            //System.out.println("Init StartupVersionShowServlet");
            String info = getVersionString() ;
            System.out.write(info.getBytes("gb2312")) ;
        }
        catch (Exception ex)
        {
            log.error("获得版本/Patch信息出错！" + ex.toString()) ;
        }
    }

    private String getVersionString ()
    {
        StringBuffer str = new StringBuffer() ;
        //print logo
        str.append(makeLogo()) ;
        str.append(LS) ;
        //print owner info
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(ProductInfo.getOwnerInfo()) ;
        str.append(LS) ;
        //print warn info
        String warnInfo = ProductInfo.getWarnInfo() ;
        String[] warns = parseLine(warnInfo) ;
        for (int i = 0 ; i < warns.length ; i++)
        {
            str.append(repeatLetter('#', 2)) ;
            str.append(repeatLetter(' ', 3)) ;
            str.append(warns[i]) ;
            str.append(LS) ;
        }
        //print seperator
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('=', 70)) ;
        str.append(repeatLetter('#', 2)) ;
        str.append(LS) ;
        //print version ID
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append("版本:") ;
        str.append(ProductInfo.getVersionID()) ;
        str.append(LS) ;
        //print patch IDs
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append("更新版本:") ;
        PatchVO[] patch = ProductInfo.queryPatchsArray(true) ;
        if (patch != null)
        {
            for (int i = 0 ; i < patch.length ; i++)
            {
                if (i > 0)
                {
                    str.append(LS) ;
                    str.append(repeatLetter('#', 2)) ;
                    str.append(repeatLetter(' ', 12)) ;
                }
                str.append(patch[i].getPatchID()) ;
            }
        }
        str.append(LS) ;
        return str.toString() ;
    }

    private String makeLogo ()
    {
        StringBuffer str = new StringBuffer() ;
        //line 1
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('=', 70)) ;
        str.append(repeatLetter('#', 2)) ;
        str.append(LS) ;
        //line 2
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('A', 17)) ;
        str.append(repeatLetter(' ', 7)) ;
        str.append(repeatLetter('S', 13)) ;
        str.append(repeatLetter(' ', 7)) ;
        str.append(repeatLetter('P', 13)) ;
        str.append(repeatLetter(' ', 13)) ;
        str.append(LS) ;
        //line 3
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('A', 5)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('A', 1)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('A', 5)) ;
        str.append(repeatLetter(' ', 5)) ;
        str.append(repeatLetter('S', 7)) ;
        str.append(repeatLetter(' ', 15)) ;
        str.append(repeatLetter('P', 5)) ;
        str.append(repeatLetter(' ', 4)) ;
        str.append(repeatLetter('P', 7)) ;
        str.append(LS) ;
        //line 4
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('A', 4)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('A', 3)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('A', 4)) ;
        str.append(repeatLetter(' ', 5)) ;
        str.append(repeatLetter('S', 13)) ;
        str.append(repeatLetter(' ', 9)) ;
        str.append(repeatLetter('P', 5)) ;
        str.append(repeatLetter(' ', 4)) ;
        str.append(repeatLetter('P', 8)) ;
        str.append(LS) ;
        //line 5
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('A', 3)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('A', 5)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('A', 3)) ;
        str.append(repeatLetter(' ', 17)) ;
        str.append(repeatLetter('S', 6)) ;
        str.append(repeatLetter(' ', 4)) ;
        str.append(repeatLetter('P', 13)) ;
        str.append(LS) ;
        //line 6
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('A', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('A', 7)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('A', 2)) ;
        str.append(repeatLetter(' ', 7)) ;
        str.append(repeatLetter('S', 14)) ;
        str.append(repeatLetter(' ', 6)) ;
        str.append(repeatLetter('P', 5)) ;
        str.append(LS) ;
        //line 7
        str.append(repeatLetter('#', 2)) ;
        return str.toString() ;

    }

    private String repeatLetter (char letter, int len)
    {
        StringBuffer str = new StringBuffer() ;
        for (int i = 0 ; i < len ; i++)
        {
            str.append(letter) ;
        }
        return str.toString() ;
    }

    private String[] parseLine (String info)
    {
        ArrayList arr = new ArrayList() ;
        String tmp = info ;
        while (tmp.length() > charLength)
        {
            String line = tmp.substring(0, charLength) ;
            arr.add(line) ;
            tmp = tmp.substring(charLength) ;
        }
        arr.add(tmp) ;
        String[] lines = new String[arr.size()] ;
        for (int i = 0 ; i < arr.size() ; i++)
        {
            lines[i] = (String) arr.get(i) ;
        }
        return lines ;

    }

    public void destroy ()
    {
        log = null ;

        //super.destroy();
    }

}
