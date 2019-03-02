package com.aspire.ponaadmin.web.repository ;

import org.apache.struts.upload.FormFile;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import java.awt.Image;
import javax.imageio.ImageIO;
import com.aspire.common.config.ServerInfo;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.util.PublicUtil;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * <p>资源管理提供的工具类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class RepositoryUtils
{

    /**
     * 工具类的私有化构造方法
     */
    private RepositoryUtils()
    {}

    /**
     * 路径分隔符号
     */
    private static final String PATH_SEP = System.getProperty("file.separator");

    /**
     * 资源库的OS路径的保存变量
     */
    private static String REPOSITORY_OS_PATH = null;

    /**
     * 获取资源库的OS路径
     * @return String，资源库的OS路径
     */
    public static String getRepositoryOSPath()
    {
        if(REPOSITORY_OS_PATH == null)
        {
            String contextPath = ServerInfo.getAppRootPath();
//            String contextPath = "C:\\work\\maxportal_pas\\source\\webapp\\ponaadmin";
            REPOSITORY_OS_PATH = contextPath + PATH_SEP +
                "resource" + PATH_SEP ;
        }
        return REPOSITORY_OS_PATH;
    }

    /**
     * 获取某种类型的资源的资源库的OS保存路径
     * @param contentType String
     * @return String
     */
    public static String getRepositoryOSPathByType(String contentType)
    {
        return RepositoryUtils.getRepositoryOSPath() + "out" +
                PATH_SEP + contentType + PATH_SEP ;

    }

    /**
     * 根据struts上传的FormFile解析出里面的内容对象
     * @param contentFile FormFile，struts上传的FormFile
     * @return Content，内容对象
     * @throws IOException
     */
    public static Content parseContent(FormFile contentFile) throws IOException
    {
        int size = contentFile.getFileSize();
        String filename = contentFile.getFileName();
        String ext = getFileExt(filename).toLowerCase();
        String contentType = getContentType(ext);

        //构造结果内容对象
        Content content = new Content();
        content.setName(filename);
        content.setSize(size);
        content.setExt(ext);
        content.setContentsType(contentType);
        String nodeID = content.getNewAllocateID();
        content.setId(nodeID);
        //读入文件内容
        OutputStream out = null;
        FileOutputStream bout = null;
        InputStream in = null;
        try
        {
            in = contentFile.getInputStream();
            //保存文件
            String resourceDir = getRepositoryOSPathByType(contentType);
            File file = new File(resourceDir);
            if(!file.exists())
            {
                file.mkdirs();
            }
            String resourceFilename = resourceDir + nodeID + '.' + ext;
            bout = new FileOutputStream(resourceFilename) ;
            out = new BufferedOutputStream(bout) ;
            int rc = 0 ;
            byte[] buf = new byte[Constants.TEMP_BUF_SIZE] ;
            while ((rc = in.read(buf, 0, buf.length)) > 0)
            {
                out.write(buf, 0, rc) ;
            }
            out.flush() ;
            //如果是图片，还要设置它的宽和高
            if(contentType.equals(Content.TYPE_PIC))
            {
                int[] imgInfo = getImgProperty(resourceFilename) ;
                content.setHeight(imgInfo[1]) ;
                content.setWidth(imgInfo[0]) ;
            }
            else
            {
                content.setHeight(0);
                content.setWidth(0);
            }
        }
        finally
        {
            PublicUtil.CloseOutStream(out);
            PublicUtil.CloseOutStream(bout);
            PublicUtil.CloseInputStream(in);
        }
        return content;
    }

    /**
     * 根据扩展名得到资源类型
     * @param ext String
     * @return String
     */
    private static String getContentType(String ext)
    {
        String type = Content.TYPE_OTHER;
        if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("gif") ||
            ext.equals("png") || ext.equals("bmp") || ext.equals("wbmp"))
        {
            type = Content.TYPE_PIC;
        }
        else if(ext.equals("wav") || ext.equals("mp3"))
        {
            type = Content.TYPE_SOUND;
        }
        else if (ext.equals("wmv") || ext.equals("asf") || ext.equals("avi") ||
                 ext.equals("mpeg") || ext.equals("mpg"))
        {
            type = Content.TYPE_VIDEO;
        }
        else if(ext.equals("swf"))
        {
            type = Content.TYPE_FLASH;
        }
        else if(ext.equals("js") || ext.equals("html") || ext.equals("htm"))
        {
            type = Content.TYPE_CODE;
        }
        else
        {
            type = Content.TYPE_OTHER;
        }
        return type;
    }

    /**
     * 获取文件的扩展名
     * @param filename String，文件名称
     * @return String，扩展名
     */
    private static String getFileExt(String filename)
    {
        int idx = filename.lastIndexOf(".");
        String ext = "";
        if(idx >= 0)
        {
            ext = filename.substring(idx + 1, filename.length()) ;
        }
        return ext;
    }

    /**
     * 获取图片文件的信息（宽，高）
     * @param imgFilename String，图片文件的url
     * @return int[]，宽、高
     * @throws IOException
     */
    static final int[] getImgProperty(String imgFilename) throws IOException
    {
        int[] result = new int[2] ;
        if (imgFilename.toUpperCase().endsWith(".BMP"))
        {
            //bmp文件
            InputStream in = null ;
            try
            {
                in = new BufferedInputStream(
                    new FileInputStream(imgFilename)) ;
                in.skip(18) ;
                result[0] = (in.read()) | (in.read() << 8) | (in.read() << 16) |
                    (in.read() << 24) ;
                result[1] = (in.read()) | (in.read() << 8) | (in.read() << 16) |
                    (in.read() << 24) ;
            }
            finally
            {
                if (in != null)
                {
                    in.close() ;
                }
            }
        }
        else if(imgFilename.toUpperCase().endsWith(".WBMP"))
        {
            //wbmp文件
            InputStream in = null ;
            try
            {
                in = new BufferedInputStream(
                    new FileInputStream(imgFilename)) ;
                in.skip(2);
                result[0] = readMultiByteInteger(in);
                result[1] = readMultiByteInteger(in);
            }
            finally
            {
                if (in != null)
                {
                    in.close() ;
                }
            }
        }
        else
        {
            //其它的图形文件
            File imgFile = new File(imgFilename) ;
            Image src = ImageIO.read(imgFile) ;
            if (src != null)
            {
                result[0] = src.getWidth(null) ;
                result[1] = src.getHeight(null) ;
            }
        }
        return result ;
    }

    /**
     * 读取wbmp文件的长和宽
     * @param in InputStream，输入流
     * @return int，读取到的int值
     * @throws IOException
     */
    private static int readMultiByteInteger (InputStream in) throws IOException
    {
        StringBuffer sb = new StringBuffer() ;
        while(true)
        {
            String tmp = Integer.toBinaryString(in.read()) ;
            int len = tmp.length() ;
            //不够8位，要补0
            if(len<8)
            {
                for(int i=0;i<8-len;i++)
                {
                    tmp = '0' + tmp;
                }
            }
            for (int j = 1 ; j < 8 ; j++)
            {
                sb.append(tmp.charAt(j)) ;
            }
            if(tmp.charAt(0) == '0')
            {
                break;
            }
        }
        return Integer.parseInt(sb.toString(), 2) ;
    }

}
