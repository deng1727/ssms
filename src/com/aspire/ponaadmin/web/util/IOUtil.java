package com.aspire.ponaadmin.web.util ;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class IOUtil
{
    /**
     * 读取一个文件的内容
     * @param fileName String,文件的全路径
     * @return String,文件内容
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String getFileContent(String fileName) throws FileNotFoundException,IOException
    {
        Reader reader = null;
        StringBuffer contentBuf = new StringBuffer();
        try
        {
            reader = new BufferedReader(new FileReader(fileName));
            char[] buf = new char[1024];
            int count = -1;
            while(true)
            {
                count = reader.read(buf);
                if(count==-1)
                {
                    break;
                }
                contentBuf.append(buf,0,count);
            }
        }
        finally
        {
            if(reader!=null)
            {
                try
                {
                    reader.close();
                }
                catch (Exception ex)
                {

                }
            }
        }
        return contentBuf.toString();
    }

    /**
     * 检测并创建目录
     * @param fileName 文件路径
     */
    public static void checkAndCreateDir (String fileName)
    {
        if (fileName == null)
        {
            return ;
        }
        File file = new File(fileName) ;
        file.mkdirs() ;
    }

    /**
     * 检测并创建目录
     * @param file 文件对象
     */
    public static void checkAndCreateDir (File file)
    {
        if(file==null)
        {
            return;
        }
        //如果是目录
        if (file.isDirectory())
        {
            file.mkdirs() ;
        }
        else
        {
            //如果是文件,则检测文件的上一级
            if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs() ;
            }
        }
    }

    /**
     * 从一个输入流中根据一定的编码方式读取出内容文本
     * @param in InputStream，输入流
     * @param encode String，编码方式
     * @return String 内容文本
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static String getInputStreamText (InputStream in ,String encode)
        throws UnsupportedEncodingException, IOException
    {
        StringBuffer contentBuf = new StringBuffer();
        Reader reader = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(in,encode));
            char[] buf = new char[1024];
            int count = -1;
            while(true)
            {
                count = reader.read(buf);
                if(count==-1)
                {
                    break;
                }
                contentBuf.append(buf,0,count);
            }
        }
        finally
        {
            if(reader!=null)
            {
                try
                {
                    reader.close();
                }
                catch (Exception ex)
                {

                }
            }
        }
        return contentBuf.toString();
    }

    /**
     * 把一段内容写到指定的文件中
     * 
     * @param filename String
     * @param contents byte[]
     * @throws IOException
     */
    public static void writeToFile(String filename, byte[] contents)
                    throws IOException

    {

        // 读入文件内容
        OutputStream out = null;
        try
        {
            out = new BufferedOutputStream(new FileOutputStream(filename));
            out.write(contents);
            out.flush();
        }
        finally
        {
            PublicUtil.CloseOutStream(out);
        }
    }
    /**
     * 删除当前目录或者文件。
     * @param file  待删除目录或者文件。
     */
    public static void deleteFile(File file)
    {
    	if(file==null)
    	{
    		return ;
    	}
    	if(file.isDirectory())
    	{
    		File []subFiles= file.listFiles();
    		for(int i=0;i<subFiles.length;i++)
    		{
    			deleteFile(subFiles[i]);
    		}
    		//子目录删除后才可以删除当期目录。
    		file.delete();
    	}else
    	{
    		file.delete();
    	}
    	
    }
    /**
     * 复制文件，采用二进制的形式，不会出现乱码。
     * @param source 源文件的文件名，含路径
     * @param dis 目标文件的文件名，含路径。
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copy(String source, String dest) throws FileNotFoundException,
			IOException
	{
    	if(source==null||dest==null)
    	{
    		throw new FileNotFoundException();
    	}
    	if(source.equals(dest))
    	{
    		return ;//不需要复制。
    	}
		final byte[] buffer = new byte[1024 * 100];// 100KB
		InputStream input = new FileInputStream(source);
		OutputStream output = new FileOutputStream(dest);
		int n = 0;
		try
		{
			while (-1 != (n = input.read(buffer)))
			{
				output.write(buffer, 0, n);
			}
			input.close();
		}finally
		{
			output.close();
		}
		
	}
    /**
     * 更改文件名。如果当前目录存在destFileName，则更改失败。
     * @param source 源文件
     * @param destFileName 更改后文件名
     * @return 更改成功返回true 否则返回false
     */
    public static boolean rename(File source,String destFileName)
    {
    	String destFileNamePath=source.getParentFile().getPath()+File.separator+destFileName;
    	File dest=new File(destFileNamePath);
    	if(dest.exists())
    	{
    		return false;
    	}
    	return source.renameTo(dest);
    	
    }

}
