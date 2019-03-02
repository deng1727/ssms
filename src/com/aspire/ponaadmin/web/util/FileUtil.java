package com.aspire.ponaadmin.web.util;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 *  关于文件操作的公共方法
 * <p>Copyright:2005 <p>
 * <p>ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 *  @author    dongxk
 *  @version   1.0
 */

public class FileUtil 
{

	/**
	 * Automatically generated method: FileUtil
	 */
	private FileUtil() 
	{
	}

	/**
	 * 日志
	 */
	private static final JLogger LOG = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 取得文件列表,包括路径
	 * @param localDir String
	 * @throws Exception
	 * @return String[]
	 */
	public static String[] getLocalDirList(String localDir) throws Exception 
	{
		LOG.debug("localDir=" + localDir);
		File myDir = new File(localDir);
		File[] dirList = myDir.listFiles();

		List list = new ArrayList();
		for (int i = 0; i < dirList.length; i++) 
		{
			if (dirList[i].isFile()) 
			{
				String fileName = dirList[i].getName().trim();
				list.add(fileName);
			}
		}
		Object[] obj = list.toArray();
		String[] arrayStr = new String[obj.length];
		System.arraycopy(obj, 0, arrayStr, 0, obj.length);

		return arrayStr;
	}
    
    /**
     * 将list（里面存放的是String对象）中的内容写到文件file中
     * @param file
     * @param list
     * @throws IOException
     */
    public static void writeToFile(File file, List list) throws IOException
    {
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new FileWriter(file, true), 2048);
            String text = "";
            for (int i = 0; i < list.size(); i++)
            {
                text = ( String ) list.get(i);
                bw.write(text);
                bw.write("\r\n");
            }
        }
        finally
        {
            if(bw != null)
            {
                try
                {
                    bw.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}