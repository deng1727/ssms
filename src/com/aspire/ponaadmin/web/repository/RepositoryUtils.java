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
 * <p>��Դ�����ṩ�Ĺ�����</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class RepositoryUtils
{

    /**
     * �������˽�л����췽��
     */
    private RepositoryUtils()
    {}

    /**
     * ·���ָ�����
     */
    private static final String PATH_SEP = System.getProperty("file.separator");

    /**
     * ��Դ���OS·���ı������
     */
    private static String REPOSITORY_OS_PATH = null;

    /**
     * ��ȡ��Դ���OS·��
     * @return String����Դ���OS·��
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
     * ��ȡĳ�����͵���Դ����Դ���OS����·��
     * @param contentType String
     * @return String
     */
    public static String getRepositoryOSPathByType(String contentType)
    {
        return RepositoryUtils.getRepositoryOSPath() + "out" +
                PATH_SEP + contentType + PATH_SEP ;

    }

    /**
     * ����struts�ϴ���FormFile��������������ݶ���
     * @param contentFile FormFile��struts�ϴ���FormFile
     * @return Content�����ݶ���
     * @throws IOException
     */
    public static Content parseContent(FormFile contentFile) throws IOException
    {
        int size = contentFile.getFileSize();
        String filename = contentFile.getFileName();
        String ext = getFileExt(filename).toLowerCase();
        String contentType = getContentType(ext);

        //���������ݶ���
        Content content = new Content();
        content.setName(filename);
        content.setSize(size);
        content.setExt(ext);
        content.setContentsType(contentType);
        String nodeID = content.getNewAllocateID();
        content.setId(nodeID);
        //�����ļ�����
        OutputStream out = null;
        FileOutputStream bout = null;
        InputStream in = null;
        try
        {
            in = contentFile.getInputStream();
            //�����ļ�
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
            //�����ͼƬ����Ҫ�������Ŀ�͸�
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
     * ������չ���õ���Դ����
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
     * ��ȡ�ļ�����չ��
     * @param filename String���ļ�����
     * @return String����չ��
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
     * ��ȡͼƬ�ļ�����Ϣ�����ߣ�
     * @param imgFilename String��ͼƬ�ļ���url
     * @return int[]������
     * @throws IOException
     */
    static final int[] getImgProperty(String imgFilename) throws IOException
    {
        int[] result = new int[2] ;
        if (imgFilename.toUpperCase().endsWith(".BMP"))
        {
            //bmp�ļ�
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
            //wbmp�ļ�
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
            //������ͼ���ļ�
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
     * ��ȡwbmp�ļ��ĳ��Ϳ�
     * @param in InputStream��������
     * @return int����ȡ����intֵ
     * @throws IOException
     */
    private static int readMultiByteInteger (InputStream in) throws IOException
    {
        StringBuffer sb = new StringBuffer() ;
        while(true)
        {
            String tmp = Integer.toBinaryString(in.read()) ;
            int len = tmp.length() ;
            //����8λ��Ҫ��0
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
