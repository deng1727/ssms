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
     * ��ȡһ���ļ�������
     * @param fileName String,�ļ���ȫ·��
     * @return String,�ļ�����
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
     * ��Ⲣ����Ŀ¼
     * @param fileName �ļ�·��
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
     * ��Ⲣ����Ŀ¼
     * @param file �ļ�����
     */
    public static void checkAndCreateDir (File file)
    {
        if(file==null)
        {
            return;
        }
        //�����Ŀ¼
        if (file.isDirectory())
        {
            file.mkdirs() ;
        }
        else
        {
            //������ļ�,�����ļ�����һ��
            if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs() ;
            }
        }
    }

    /**
     * ��һ���������и���һ���ı��뷽ʽ��ȡ�������ı�
     * @param in InputStream��������
     * @param encode String�����뷽ʽ
     * @return String �����ı�
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
     * ��һ������д��ָ�����ļ���
     * 
     * @param filename String
     * @param contents byte[]
     * @throws IOException
     */
    public static void writeToFile(String filename, byte[] contents)
                    throws IOException

    {

        // �����ļ�����
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
     * ɾ����ǰĿ¼�����ļ���
     * @param file  ��ɾ��Ŀ¼�����ļ���
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
    		//��Ŀ¼ɾ����ſ���ɾ������Ŀ¼��
    		file.delete();
    	}else
    	{
    		file.delete();
    	}
    	
    }
    /**
     * �����ļ������ö����Ƶ���ʽ������������롣
     * @param source Դ�ļ����ļ�������·��
     * @param dis Ŀ���ļ����ļ�������·����
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
    		return ;//����Ҫ���ơ�
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
     * �����ļ����������ǰĿ¼����destFileName�������ʧ�ܡ�
     * @param source Դ�ļ�
     * @param destFileName ���ĺ��ļ���
     * @return ���ĳɹ�����true ���򷵻�false
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
