
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
     * ÿ��д���ļ��Ĵ�С
     */
    final static int BUFFEREDSIZE = 1024;

    /**
     * ��ѹtar.gz�ļ�����ǰĿ¼��ѹ���ļ����ļ�����������tar.gz����Ϊ��ѹ���ļ��ĸ�Ŀ¼
     * 
     * @param gzipFileName ��ѹ���ļ��ľ���Ŀ¼
     * @return ��ѹ����ѹ���ļ��ĸ�Ŀ¼��
     * @exception Exception ��ѹ�������쳣
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
     * ��ѹtar.gz�ļ���ָ��Ŀ¼
     * 
     * @param gzipFileName ѹ���ļ��ľ���·��
     * @param outputDirectory ��ѹ�ļ��ı���Ŀ¼·����
     * @exception Exception ��ѹ�������쳣
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
                    // ��¼���ζ�ȡ����
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
     * ͼƬ���ţ�Ŀǰֻ֧��JPG
     * 
     * @param srcimg String,Դ�ļ���ȫ·��
     * @param toimg String,Ŀ���ļ���ȫ·��
     * @param toWidth int,Ŀ���ļ���ͼƬ��
     * @param toHeight int,Ŀ���ļ���ͼƬ��
     * @param toFormate ת��ͼ��Ŀ���ʽ��
     * @throws IOException 
     * @throws Exception
     */

    public static void scaleIMG(String srcimg, String toimg, int toWidth,
                                int toHeight, String toFormat) throws Exception

    {
        File srcFile = new File(srcimg);
        File toFile = new File(toimg);
        
        boolean isFailure = false;//ImageIO.read(srcFile);�ڶ�������JPG��PNG������쳣�����޸���
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

		
		
        // ����ͼƬ�� �� ���ΪtoWidth toHeight
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
         * ������
         * @param filePath
         *            ��Ҫת�����ļ�·��
         *            ����һ���µ��ļ�·��       
         */
        // ��ͼƬ��ʽȫ��ת����JPG��ʽ
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
            // ת����ɾ��ԭ�ļ�
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
     * ���ڽ�ѹZIP�����ݵ�ͬ���ļ�����
     * 
     * @param zipFileName zip�ļ���
     * @return
     * @throws BOException
     */
    public static String unZip(String zipFileName) throws BOException
    {
        // zip����
        ZipFile zipFile = null;
        ZipEntry zipEntry = null;
        InputStream in = null;
        FileOutputStream out = null;
        File f = null;
        int c;

        // ��ѹ���ļ�����
        String outputDirectory = zipFileName.substring(0,
                                                       zipFileName.indexOf('.'));

        try
        {
            zipFile = new ZipFile(zipFileName);
        }
        catch (IOException e1)
        {
            throw new BOException("������ѹZIP�ļ�����ʱ�����ļ���=" + zipFileName);
        }

        // zip���а������ļ�����
        Enumeration e = zipFile.entries();

        // ������ѹ���ļ���
        createDirectory(outputDirectory);
        
        try
        {
            // ����zip�����ļ�����
            while (e.hasMoreElements())
            {
                // �õ�����ÿһ���ļ�
                zipEntry = ( ZipEntry ) e.nextElement();

                // �����ļ�����
                f = new File(outputDirectory + File.separator
                                  + zipEntry.getName());

                // �����ļ�����
                f.createNewFile();

                // ����
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
                    throw new BOException("��ѹZIP�ļ�ʱ�����ļ���=" + f.getName());
                }
            }
            throw new BOException("��ѹZIP�ļ�ʱ�����ļ���=" + f.getName());
        }
        // ����
        finally
        {
            try
            {
                in.close();
            }
            catch (IOException e1)
            {
                throw new BOException("�ر�������ʱ��������");
            }
            try
            {
                zipFile.close();
            }
            catch (IOException e1)
            {
                throw new BOException("�ر�zip�ļ���ʱ��������");
            }
        }
        return outputDirectory;
    }

    /**
     * ������ѹ�ļ���
     * 
     * @param directory
     */
    public static void createDirectory(String directory)
    {
        File fl = new File(directory);
        try
        {
            // ���û�С��½�
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
