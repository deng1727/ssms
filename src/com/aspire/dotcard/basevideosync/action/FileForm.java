/**
 * com.aspire.ponaadmin.web.newmusicsys.action FileForm.java
 * Aug 4, 2011
 *<p>
 * Copyright (c) 2003-2011 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * @author dongke
 * @version 1.0
 *
 */
package com.aspire.dotcard.basevideosync.action;

import java.util.Hashtable;
import java.util.Iterator;

import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author tungke
 * 
 */
public class FileForm extends DynaActionForm
{

    // 特殊的form,一个变多个，非扩展字段
    FormFile dataFile = null;

    public FormFile getDataFile()
    {

        return dataFile;
    }

    public void setDataFile(FormFile dataFile)
    {

        this.dataFile = dataFile;
    }

    // FormFile musiccate_pic = null;
    // FormFile musiccate_pic3 = null;
    //
    // public FormFile getMusiccate_pic()
    // {
    //	    
    // return musiccate_pic;
    // }
    //
    //	    
    // public void setMusiccate_pic(FormFile musiccate_pic)
    // {
    //	    
    // this.musiccate_pic = musiccate_pic;
    // }
    //	    
    // public FormFile getMusiccate_pic3()
    // {
    //	    
    // return musiccate_pic3;
    // }
    //
    //	    
    // public void setMusiccate_pic3(FormFile musiccate_pic3)
    // {
    //	    
    // this.musiccate_pic3 = musiccate_pic3;
    // }

    public boolean checkFileNameExtension(String[] type)
    {
        Hashtable files = this.getMultipartRequestHandler().getFileElements();

        for (Iterator iter = this.getMultipartRequestHandler()
                                 .getFileElements()
                                 .keySet()
                                 .iterator(); iter.hasNext();)
        {
            boolean isTrue = false;
            
            String fileNameTemp = ( String ) iter.next();

            FormFile uploadFile = ( FormFile ) files.get(fileNameTemp);

            if (uploadFile == null || "".equals(uploadFile.getFileName()))
            {
                continue;
            }

            // 得到文件名
            String fileName = uploadFile.getFileName();

            // 是否存在后缀名
            int pos = fileName.lastIndexOf(".");

            if (pos < 0)
            {
                return false;
            }

            // 当前文件后缀名
            String fileType = fileName.substring(pos + 1);

            // 循环判断后缀名情况
            for (int i = 0; i < type.length; i++)
            {
                String tempType = type[i];

                if (fileType.toUpperCase().equals(tempType.toUpperCase()))
                {
                    isTrue = true;
                }
            }
            
            if(!isTrue)
            {
                return false;
            }
        }
        
        return true;
    }
}
