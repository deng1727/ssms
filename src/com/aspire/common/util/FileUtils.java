package com.aspire.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FileSet;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.constant.ErrorCode;

/**
 * A collection of file utilities.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public final class FileUtils {

    /** The default size of the copy buffer. */
    public static final int DEFAULT_BUFFER_SIZE = 8192; // 8k

    /**
     * Delete a directory and all of its contents.
     * @param dir   The directory to delete.
     * @return      True -- delete successfully, false if
     */
    public static boolean delete(File dir) {
        boolean success = true;
        File files[] = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    // delete the directory and all of its contents.
                    if (!delete(files[i])) {
                        success = false;
                    }
                }
                // delete each file in the directory
                if (!files[i].delete()) {
                    success = false;
                }
            }
        }
        // finally delete the directory
        if (!dir.delete()) {
            success = false;
        }
        return success;
    }

    /**
     * Delete a directory and all of its contents.
     * @param dirname  The name of the directory to delete.
     * @return         True if all delete operations were successfull.
     */
    public static boolean delete(final String dirname) {
        return delete(new File(dirname));
    }


    /**
     * Copy a file.
     * @param source  Source file to copy.
     * @param target  Destination target file.
     * @param buff    The copy buffer.
     * @throws IOException  Failed to copy file.
     */
    public static void copy(final File source,
                            final File target,
                            final byte buff[]) throws IOException {
        DataInputStream in = new DataInputStream
            (new BufferedInputStream(new FileInputStream(source)));

        DataOutputStream out = new DataOutputStream
            (new BufferedOutputStream(new FileOutputStream(target)));

        int read;

        try {
            while ( (read = in.read(buff)) != -1) {
                out.write(buff, 0, read);
            }
        }
        finally {
            out.flush();
            in.close();
            out.close();
        }
    }

    /**
     * Copy a file.
    * @param source  Source file to copy.
     * @param target  Destination target file.
     * @param size    The size of the copy buffer.
     * @throws IOException  Failed to copy file.
     */
    public static void copy(final File source,
                            final File target,
                            final int size) throws IOException {
        copy(source, target, new byte[size]);
    }

    /**
     * Copy a file.
     * @param source  Source file to copy.
     * @param target  Destination target file.
     * @throws IOException  Failed to copy file.
     */
    public static void copy(final File source, final File target) throws
        IOException {
        copy(source, target, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Calls writeToFile with createDir flag false.
     */
    public static void writeToFile(String fileName, InputStream iStream) throws
        IOException {
        writeToFile(fileName, iStream, false);
    }

    /**
     * write to a file
     */
    public static void writeToFile(String fileName, InputStream iStream,
                                   boolean createDir) throws IOException {
        String me = "FileUtils.WriteToFile";
        if (fileName == null) {
            throw new IOException(me + ": filename is null");
        }
        if (iStream == null) {
            throw new IOException(me + ": InputStream is null");
        }
        File theFile = new File(fileName);
        // Check if a file exists.
        if (theFile.exists()) {
            String msg =
                theFile.isDirectory() ? "directory" :
                (!theFile.canWrite() ? "not writable" : null);
            if (msg != null) {
                throw new IOException(me + ": file '" + fileName + "' is " +
                                      msg);
            }
        }

        // Create directory for the file, if requested.
        if (createDir && theFile.getParentFile() != null) {
            theFile.getParentFile().mkdirs();
        }

        // Save InputStream to the file.
        BufferedOutputStream fOut = null;
        try {
            fOut = new BufferedOutputStream(new FileOutputStream(theFile));
            byte[] buffer = new byte[32 * 1024];
            int bytesRead = 0;
            while ( (bytesRead = iStream.read(buffer)) != -1) {
                fOut.write(buffer, 0, bytesRead);
            }
        }
        catch (Exception e) {
            throw new IOException(me + " failed, got: " + e.toString());
        }
        finally {
            close(iStream, fOut);
        }
    }

    /**
     * Closes InputStream and/or OutputStream.
     */
    protected static void close(InputStream iStream, OutputStream oStream) throws
        IOException {
        try {
            if (iStream != null) {
                iStream.close();
            }
        }
        finally {
            if (oStream != null) {
                oStream.close();
            }
        }
    }

    /**
     *  Aappend Some String To a File
     */
    public static void append(String fileName, String text, boolean newline) throws
        IOException {
        File f = new File(fileName);
        if (f.exists())
            append(f, text, newline);
        else
            throw new FileNotFoundException();
    }
    public static void append(File file, String text, boolean newline) throws
        IOException {
        int buffSize = text.length() - 1;
        buffSize = buffSize > DEFAULT_BUFFER_SIZE ? DEFAULT_BUFFER_SIZE :
            buffSize;
        long len = file.length();
        if (len > 0 && newline)
            text = "\r\n" + text;
        BufferedRandomAccessFile braf = new BufferedRandomAccessFile(file, "rw",
            buffSize);
        braf.seek(len);
        braf.writeBytes(text);
        braf.close();
    }
    
    /**
     * 将list（里面存放的是String对象）中的内容写到文件file中
     * @param file
     * @param list
     * @throws IOException
     */
    public static void writeToFile(File file, List list) throws IOException
    {
        String text = "";       
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true), 2048);
        
        for (int i = 0; i < list.size(); i++)
        {
            text = ( String ) list.get(i);
            bw.write(text);
            bw.write("\r\n");
        }
        bw.close();
    }

    /**create specified dir*/
    public final static void createDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    /** crtate a null file, if parent dir not exists,creare it */
    public final static void createNullFile(String nFile) {
        File file = new File(nFile);
        try {
            if (!file.exists())
                file.createNewFile();
        }
        catch (IOException ex) {
        }
    }

    /** rename to a file **/
    public final static void  rename(File file, String newName){
        File newFile = new File(newName);
        file.renameTo(newFile);
    }
    
    /**
     * 拷贝。语法参考ant的copy命令。
     * (单文件COPY)
     * @param srcDir String 源目录。
     * @param desDir String 目标目录。
     * @param filepath List  需要拷贝的文件。
     * @return boolean 拷贝是否成功。
     * @throws com.aspire.ponaadmin.common.exception.BOException
     */
    public static boolean copy (String srcDir, String desDir, String filepath)
        throws BOException
    {
        try
        {
            FileSet set = new FileSet() ;
            File src = new File(srcDir);
            if(!src.exists()){
                src.mkdirs();
            }
            set.setDir(src) ;
            Copy copy = new Copy() ;
            copy.setProject(new Project()) ;
            set.setIncludes(filepath) ;

            copy.addFileset(set) ;
            File des = new File(desDir);
            if(!des.exists()){
                des.mkdirs();
            }
            copy.setTodir(des) ;
            copy.execute() ;
            return true ;
        }
        catch (BuildException ex)
        {
            throw new BOException(ex, ErrorCode.FILE_COPY_ERR) ;

        }
    }

    public static void main(String[] args) {
        File f = new File("d:/new");
        System.out.println("name is " + f.getAbsolutePath());
    }

}