
package com.aspire.ponaadmin.web.datasync;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basegame.ContentDataDealer;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiWriter;
import com.sun.jimi.core.options.JPGOptions;

public class DataSyncTools
{

	private static JLogger LOG=LoggerFactory.getLogger(DataSyncTools.class);
    /**
     * 每次写入文件的大小
     */
    final static int BUFFEREDSIZE = 1024;

    /**
     * 解压tar.gz文件到当前目录。压缩文件的文件名（不包含tar.gz）作为解压后文件的父目录
     * 
     * @param gzipFileName 待压缩文件的绝对目录
     * @return 解压缩后压缩文件的根目录。
     * @exception Exception 解压缩出现异常
     */
    public static String ungzip(String gzipFileName) throws Exception
    {
        String outputDirectory = gzipFileName.substring(0,
                                                        gzipFileName.indexOf('.'));
        File file = new File(outputDirectory);
        if (!file.exists())
        {
            file.mkdir();
        }
        ungzip(gzipFileName, outputDirectory);
        return outputDirectory;
    }

    /**
     * 解压tar.gz文件到指定目录
     * 
     * @param gzipFileName 压缩文件的绝对路径
     * @param outputDirectory 解压文件的保存目录路径。
     * @exception Exception 解压缩出现异常
     */
    public static void ungzip(String gzipFileName, String outputDirectory)
                    throws Exception
    {
        FileInputStream fis = null;
        ArchiveInputStream in = null;
        BufferedInputStream bufferedInputStream = null;
        try
        {
            fis = new FileInputStream(gzipFileName);
            GZIPInputStream is = new GZIPInputStream(new BufferedInputStream(fis));
            in = new ArchiveStreamFactory().createArchiveInputStream("tar", is);
            bufferedInputStream = new BufferedInputStream(in);
            TarArchiveEntry entry = ( TarArchiveEntry ) in.getNextEntry();
            while (entry != null)
            {
                String name = entry.getName();
                String[] names = name.split("/");
                String fileName = outputDirectory;
                for (int i = 0; i < names.length; i++)
                {
                    fileName = fileName + File.separator + names[i];
                }
                if (name.endsWith("/"))
                {
                    // mkFolder(fileName);
                    IOUtil.checkAndCreateDir(fileName);
                }
                else
                {
                    File file = new File(fileName);
                    //
                    // IOUtil.
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                    // 记录本次读取长度
                    int b;
                    byte[] by = new byte[BUFFEREDSIZE];
                    while ((b = in.read(by)) != -1)
                    {
                        bufferedOutputStream.write(by, 0, b);
                    }

                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
                entry = ( TarArchiveEntry ) in.getNextEntry();
            }

        }
        catch (Exception e)
        {
            throw e;

        }
        finally
        {
            try
            {
                if (bufferedInputStream != null)
                {
                    bufferedInputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * 图片缩放，目前只支持JPG
     * 
     * @param srcimg String,源文件的全路径
     * @param toimg String,目标文件的全路径
     * @param toWidth int,目标文件的图片宽
     * @param toHeight int,目标文件的图片高
     * @param toFormate 转化图的目标格式。
     * @throws IOException 
     * @throws Exception
     */

    public static void scaleIMG(String srcimg, String toimg, int toWidth,
                                int toHeight, String toFormat) throws Exception

    {
        File srcFile = new File(srcimg);
        File toFile = new File(toimg);
        
        boolean isFailure = false;//ImageIO.read(srcFile);在读到坏的JPG、PNG会出现异常。特修复。
        BufferedImage sourceImage = null;
		try {
			sourceImage = ImageIO.read(srcFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.debug(srcimg+" is bad file,need modify!");
			isFailure = true;
		}
		
		String desc = null;
		if(isFailure){
			desc = toJPG(srcimg);
			if(desc==null) throw new Exception("modify bad file is failure,file name:"+srcimg);
			sourceImage = ImageIO.read(new File(desc));
		}

		
		
        // 假设图片宽 高 最大为toWidth toHeight
        Image Itemp = sourceImage.getScaledInstance(toWidth,
                                                    toHeight,
                                                    BufferedImage.SCALE_SMOOTH);
        double RatioW = 0.0;
        double RatioH = 0.0;
        RatioW = toWidth * 1.0 / sourceImage.getWidth();
        RatioH = toHeight * 1.0 / sourceImage.getHeight();
        // System.out.println("RatioW=" + RatioW + ",RatioH=" + RatioH);
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(RatioW,
                                                                                      RatioH),
                                                     null);
        Itemp = op.filter(sourceImage, null);
        ImageIO.write(( BufferedImage ) Itemp, toFormat, toFile);
    }
    
    private  static String toJPG(String filePath) {
        /**
         * 描述：
         * @param filePath
         *            需要转换的文件路径
         *            返回一个新的文件路径       
         */
        // 将图片格式全部转换成JPG格式
        try {
        	LOG.debug("start modify bad file:"+filePath);
            String source = filePath;
            String dest = source.substring(0, source.lastIndexOf(".")) + "jimi.jpg";
            // System.out.println("dest" + dest);
            JPGOptions options = new JPGOptions();
            options.setQuality(72);
            ImageProducer image = Jimi.getImageProducer(source);
            JimiWriter writer = Jimi.createJimiWriter(dest);
            writer.setSource(image);
            writer.setOptions(options);
            writer.putImage(dest);
            //File f = new File(filePath);
            // 转换后删除原文件
            //f.delete();
            LOG.debug("success modify bad file:"+dest);
            return dest;
        } catch (Exception je) {
            //System.err.println("Error: " + je);
            //je.printStackTrace();
        	LOG.error("jimi error:"+je);
            return null;

        }
    }

    /**
     * 用于解压ZIP包内容到同名文件夹中
     * 
     * @param zipFileName zip文件名
     * @return
     * @throws BOException
     */
    public static String unZip(String zipFileName) throws BOException
    {
        // zip对象
        ZipFile zipFile = null;
        ZipEntry zipEntry = null;
        InputStream in = null;
        FileOutputStream out = null;
        File f = null;
        int c;

        // 解压至文件夹名
        String outputDirectory = zipFileName.substring(0,
                                                       zipFileName.indexOf('.'));

        try
        {
            zipFile = new ZipFile(zipFileName);
        }
        catch (IOException e1)
        {
            throw new BOException("创建解压ZIP文件对象时出错，文件名=" + zipFileName);
        }

        // zip包中包含的文件集合
        Enumeration e = zipFile.entries();

        // 创建解压后文件夹
        createDirectory(outputDirectory);
        
        try
        {
            // 迭代zip包中文件集合
            while (e.hasMoreElements())
            {
                // 得到包中每一个文件
                zipEntry = ( ZipEntry ) e.nextElement();

                // 构造文件对象
                f = new File(outputDirectory + File.separator
                                  + zipEntry.getName());

                // 创建文件对象
                f.createNewFile();

                // 读流
                in = zipFile.getInputStream(zipEntry);
                out = new FileOutputStream(f);

                byte[] by = new byte[BUFFEREDSIZE];

                while ((c = in.read(by)) != -1)
                {
                    out.write(by, 0, c);
                }

                out.close();
            }
        }
        catch (Exception ex)
        {
            if (null != out)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                    throw new BOException("解压ZIP文件时出错，文件名=" + f.getName());
                }
            }
            throw new BOException("解压ZIP文件时出错，文件名=" + f.getName());
        }
        // 关流
        finally
        {
            try
            {
                in.close();
            }
            catch (IOException e1)
            {
                throw new BOException("关闭输入流时发生错误");
            }
            try
            {
                zipFile.close();
            }
            catch (IOException e1)
            {
                throw new BOException("关闭zip文件流时发生错误");
            }
        }
        return outputDirectory;
    }

    /**
     * 创建解压文件夹
     * 
     * @param directory
     */
    public static void createDirectory(String directory)
    {
        File fl = new File(directory);
        try
        {
            // 如果没有。新建
            if (!fl.exists())
            {
                fl.mkdir();
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

}
