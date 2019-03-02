/**
 * SSMS
 * com.aspire.dotcard.basemusic BaseMusicDBOpration.java
 * May 8, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.basemusic;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;



/**
 * @author tungke
 *
 */
public class BaseMusicReferenceDBOpration
{

	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory.getLogger(BaseMusicReferenceDBOpration.class);

	private List params = null;

	public BaseMusicReferenceDBOpration(List para)
	{
		this.params = para;
	}
	
	
	
/**
 * 插入
 *
 */
	public void InsertNewBMusicReference()
	{
		String insertSqlCode = "com.aspire.dotcard.basemusic.BaseMusicReferenceDBOpration.InsertNewBMusicReference().insertSqlCode";
		int c = this.params.size();
		//{musicid,musicname,sortid,categoryid}
		String par[] = new String[c];
		
		for(int i =0 ; i < this.params.size();i++){
			par[i]=(String)this.params.get(i);
		}
	try
	{
		int result = BMusicDAO.getInstance().execueSqlCode(insertSqlCode,par);
		logger.debug("上架最新："+params+" 成功！");
	} catch (DAOException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error("插入基地音乐："+params+" 失败！",e);
	}
		
	}

	
}
