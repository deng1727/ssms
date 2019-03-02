package com.aspire.common.log.proxy.model;

/**
 * <p>Title: BaseBizLogContent</p>
 * <p>Description: The abstract base business log content for others to extends</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire</p>
 * @author YanFeng
 * @version 1.0
 * history:
 * created at 25/4/2003
 * revised at 4/6/2003 remove abstract constraint for convenient usage
 * change the previous abstract toString method to concrete one that just
 * concatenate all elements in the arraylist
 */
import java.util.ArrayList;
import com.aspire.common.log.constants.LogConstants;
public  class BaseBizLogContent
{
    /**
     * the place to hold log fields
     */
    ArrayList fields=new ArrayList();
    public BaseBizLogContent()
    {
    }
    /**
  * @param index the index of field,start from 0
  * @return the field value
  */
  public Object get(int index)
  {
      return fields.get(index);
  }
  /**
  * @param obj the value of one filed
  */
  public void add(Object obj)
  {
      fields.add(obj);
  }

  /**
  * @return the fields' number
  */
  public int size()
  {
      return fields.size();
  }
  public String toString()
  {
      StringBuffer buf=new StringBuffer(50);
      for(int i=0;i<fields.size();i++)
      {
          buf.append(fields.get(i));
          buf.append(LogConstants.COLON_SEPERATOR);
      }

      return buf.substring(0,buf.length()-1);
  }
}
