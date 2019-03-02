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
	            LOG.debug("��ʼ������139���������ļ�");
	        }
	
	        
		 String mailTitle = "������139���������ļ�����ʼ�";
		 StringBuffer sb = new StringBuffer();
	        
	        	New139AlbumBO album = new New139AlbumBO();
	        Integer[] albumResult;
			try
			{
				albumResult = album.dealAlbumData();
				sb.append(geneResultMail(albumResult,"139������ר�����룺"));
			} catch (BOException e)
			{
				e.printStackTrace();
				sb.append("139������ר������ʧ�ܣ�"+e);
			}
	        
	        
	        New139BillboardBO billboard = new New139BillboardBO();
	        try
			{
				Integer[] billboardResult =billboard.dealbillboardData();
				  sb.append(geneResultMail(billboardResult,"139�����ְ񵥵��룺"));
			} catch (BOException e)
			{
				e.printStackTrace();
				sb.append("139�����ְ񵥵���ʧ�ܣ�"+e);
			}
	        
	      
	        
	        New139TagSynBO musicTag = new New139TagSynBO();
	        Integer[] musicTagResult;
			try
			{
				musicTagResult = musicTag.dealMusicTagData();
				sb.append(geneResultMail(musicTagResult,"139�����ֱ�ǩ���룺"));
			} catch (BOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				sb.append("139�����ֱ�ǩ����ʧ�ܣ�"+e);
			}
	        
	        
	        
	        New139KeyWorldSynBO keyWorld = new New139KeyWorldSynBO();
	        Integer[] keyWorldTagResult;
			try
			{
				keyWorldTagResult = keyWorld.dealKeyWorldData();
				 sb.append(geneResultMail(keyWorldTagResult,"139�����ֹؼ��ֵ��룺"));
			} catch (BOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				 sb.append("139�����ֹؼ��ֵ���ʧ�ܣ�"+e);
			}

	        New139PicSynBO musicPic = new New139PicSynBO();
	        musicPic.handDeal();
	        New139BaseSynBO nb  = new New139BaseSynBO();
	       // �¼ܹ���������Ʒ
	        sb.append(nb.delInvalNewBMusicRef());
//	      �������ֻ�����Ʒ����
	        sb.append(nb.updateAllNewCategoryRefSum());
	        
	        // 4,�¼�ȫ�����ڻ���������Ʒ
	          //  this.delInvalBMusicRef(delNewRefmailInfo);
//	            BaseMusicBO.getInstance().delInvalNewBMusicRef(delRefmailInfo);
//	            if (delNewRefmailInfo != null && delNewRefmailInfo[0] != null)
//	            {
//	                msgInfo.append("<br>");
//	                msgInfo.append("�¼ܹ��ڻ��� �� ������Ʒ����Ϊ��" + delNewRefmailInfo[0].intValue());
//	                msgInfo.append("<br>");
//	            }
	            // 5,���»�����Ʒ����
	            //this.updateCategoryRefSum(updateNewcrInfo);
//	            BaseMusicBO.getInstance().updateAllNewCategoryRefSum(updatecrInfo);
//	            if (updateNewcrInfo != null && updateNewcrInfo[0] != null)
//	            {
//	                msgInfo.append("<br>");
//	                msgInfo.append("���� �� ���ֻ�������Ϊ��" + updateNewcrInfo[0].intValue());
//	                msgInfo.append("<br>");
//	            }
//	        
	        LOG.info(sb.toString());
	        Mail.sendMail(mailTitle, sb.toString(), NewMusic139Config.getInstance().getMailTo());
	}
	  /**
     * ƴװ����ʼ�
     */
    private StringBuffer geneResultMail(Integer [] result,String title)
    {
        StringBuffer sb = new StringBuffer();   
        if(result != null && result.length==3){
			int success  = result[0].intValue();
			int checkfaild = result[2].intValue();
			int dealfaild = result[1].intValue();
			 sb.append(title);
	           
	            sb.append("��<h4>��������</h4>");
	            sb.append("<p>���гɹ�����<b>");
	            sb.append(success);
	            sb.append("��;<p>����ʧ��<b>");
	            sb.append(dealfaild);
	            sb.append("��;<p>���ݲ����Ϲ淶<b>");
	            sb.append(checkfaild);
	            sb.append("��;<p>");
		}else{
			 sb.append("����ʧ��");
		}     
       return sb;      
    }
}
