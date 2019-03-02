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
package com.aspire.ponaadmin.web.comic.action;



import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;



/**
 * @author tungke
 *
 */
public class FileForm extends DynaActionForm
{
	
	//特殊的form,一个变多个，非扩展字段
	 FormFile dataFile = null;
	 
	 public FormFile getDataFile()
	    {
	    
	        return dataFile;
	    }

	    
	    public void setDataFile(FormFile dataFile)
	    {
	    
	        this.dataFile = dataFile;
	    }
}
