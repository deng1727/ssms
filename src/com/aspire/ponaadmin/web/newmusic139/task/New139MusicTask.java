/**
 * SSMS
 * com.aspire.ponaadmin.web.newmusic139.task New139MusicTask.java
 * Jul 6, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.newmusic139.task;


import java.util.TimerTask;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.newmusic139.NewMusic139Config;
import com.aspire.ponaadmin.web.newmusic139.bo.New139AlbumBO;
import com.aspire.ponaadmin.web.newmusic139.bo.New139BaseSynBO;
import com.aspire.ponaadmin.web.newmusic139.bo.New139BillboardBO;
import com.aspire.ponaadmin.web.newmusic139.bo.New139KeyWorldSynBO;
import com.aspire.ponaadmin.web.newmusic139.bo.New139PicSynBO;
import com.aspire.ponaadmin.web.newmusic139.bo.New139TagSynBO;


/**
 * @author tungke
 *
 */
public class New139MusicTask extends TimerTask
{

	private static final JLogger LOG = LoggerFactory.getLogger(New139MusicTask.class);

  
	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	public void run()
	{
		 if (LOG.isDebugEnabled())
	        {
	            LOG.debug("开始导入新139音乐数据文件");
	        }
	
	        
		 String mailTitle = "导入新139音乐数据文件结果邮件";
		 StringBuffer sb = new StringBuffer();
	        
	        	New139AlbumBO album = new New139AlbumBO();
	        Integer[] albumResult;
			try
			{
				albumResult = album.dealAlbumData();
				sb.append(geneResultMail(albumResult,"139新音乐专辑导入："));
			} catch (BOException e)
			{
				e.printStackTrace();
				sb.append("139新音乐专辑导入失败："+e);
			}
	        
	        
	        New139BillboardBO billboard = new New139BillboardBO();
	        try
			{
				Integer[] billboardResult =billboard.dealbillboardData();
				  sb.append(geneResultMail(billboardResult,"139新音乐榜单导入："));
			} catch (BOException e)
			{
				e.printStackTrace();
				sb.append("139新音乐榜单导入失败："+e);
			}
	        
	      
	        
	        New139TagSynBO musicTag = new New139TagSynBO();
	        Integer[] musicTagResult;
			try
			{
				musicTagResult = musicTag.dealMusicTagData();
				sb.append(geneResultMail(musicTagResult,"139新音乐标签导入："));
			} catch (BOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				sb.append("139新音乐标签导入失败："+e);
			}
	        
	        
	        
	        New139KeyWorldSynBO keyWorld = new New139KeyWorldSynBO();
	        Integer[] keyWorldTagResult;
			try
			{
				keyWorldTagResult = keyWorld.dealKeyWorldData();
				 sb.append(geneResultMail(keyWorldTagResult,"139新音乐关键字导入："));
			} catch (BOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				 sb.append("139新音乐关键字导入失败："+e);
			}

	        New139PicSynBO musicPic = new New139PicSynBO();
	        musicPic.handDeal();
	        New139BaseSynBO nb  = new New139BaseSynBO();
	       // 下架过期音乐商品
	        sb.append(nb.delInvalNewBMusicRef());
//	      计算音乐货架商品数量
	        sb.append(nb.updateAllNewCategoryRefSum());
	        
	        // 4,下架全量过期基地音乐商品
	          //  this.delInvalBMusicRef(delNewRefmailInfo);
//	            BaseMusicBO.getInstance().delInvalNewBMusicRef(delRefmailInfo);
//	            if (delNewRefmailInfo != null && delNewRefmailInfo[0] != null)
//	            {
//	                msgInfo.append("<br>");
//	                msgInfo.append("下架过期基地 新 音乐商品数量为：" + delNewRefmailInfo[0].intValue());
//	                msgInfo.append("<br>");
//	            }
	            // 5,更新货架商品数量
	            //this.updateCategoryRefSum(updateNewcrInfo);
//	            BaseMusicBO.getInstance().updateAllNewCategoryRefSum(updatecrInfo);
//	            if (updateNewcrInfo != null && updateNewcrInfo[0] != null)
//	            {
//	                msgInfo.append("<br>");
//	                msgInfo.append("更新 新 音乐货架数量为：" + updateNewcrInfo[0].intValue());
//	                msgInfo.append("<br>");
//	            }
//	        
	        LOG.info(sb.toString());
	        Mail.sendMail(mailTitle, sb.toString(), NewMusic139Config.getInstance().getMailTo());
	}
	  /**
     * 拼装结果邮件
     */
    private StringBuffer geneResultMail(Integer [] result,String title)
    {
        StringBuffer sb = new StringBuffer();   
        if(result != null && result.length==3){
			int success  = result[0].intValue();
			int checkfaild = result[2].intValue();
			int dealfaild = result[1].intValue();
			 sb.append(title);
	           
	            sb.append("。<h4>处理结果：</h4>");
	            sb.append("<p>其中成功处理<b>");
	            sb.append(success);
	            sb.append("条;<p>处理失败<b>");
	            sb.append(dealfaild);
	            sb.append("条;<p>数据不符合规范<b>");
	            sb.append(checkfaild);
	            sb.append("条;<p>");
		}else{
			 sb.append("处理失败");
		}     
       return sb;      
    }
}
