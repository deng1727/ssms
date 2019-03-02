package com.aspire.dotcard.basebook.timer;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basebook.BaseBookFtpProcess;
import com.aspire.dotcard.basebook.biz.RBookBO;
import com.aspire.dotcard.basebook.config.BaseBookConfig;
import com.aspire.ponaadmin.web.mail.Mail;

public class RBookLoadTask extends TimerTask {
	protected static JLogger logger = LoggerFactory.getLogger(RBookLoadTask.class);
	public void run() {
		logger.debug("import book base begin!");
		//Ftp服务
		BaseBookFtpProcess ftp = new BaseBookFtpProcess();
		
		StringBuffer msgInfo = new StringBuffer();
		String encoding = BaseBookConfig.get("fileEncoding");
		String sep = BaseBookConfig.get("BBookListSep");
		if(null==encoding){
			encoding = "UTF-8";
		}
		if(null==sep){
			sep = "|";
		}
		
		//获取要处理的对象
    	if (sep.startsWith("0x")) 
		{
			// 0x开头的，表示是16进制的，需要转换
			String s = sep.substring(2,sep.length());
			int i1 = Integer.parseInt(s,16);
			char c = (char)i1;
			sep = String.valueOf(c);
		}		
		
		/**
		 * 0,处理的总行数；1，成功新增；2，成功修改；3，成功删除；4，数据检查不合法；5，数据处理失败
		 */			
		int[] rs = new int[6];		
		synBookType(ftp, msgInfo, encoding, sep, rs);
		
		msgInfo.append("<br>");
		//作者信息同步
		rs = new int[6];
		synBookAuthor(ftp, msgInfo, encoding, sep, rs);		
		msgInfo.append("<br>");
		//图书信息
		rs = new int[6];
		synBookInfo(ftp, msgInfo, encoding, sep, rs);		
		msgInfo.append("<br>");
		//包月信息
		rs = new int[4];
		synBookMonth(ftp, msgInfo, encoding, sep, rs);	
		msgInfo.append("<br>");
		
		//推荐图书信息
		rs = new int[6];
		synBookRecommend(ftp, msgInfo, encoding, sep, rs);	
		msgInfo.append("<br>");
		
		//专区信息
		rs = new int[6];
		synBookArea(ftp, msgInfo, encoding, sep, rs);		
		msgInfo.append("<br>");
		
		//专区内容信息
		rs = new int[6];
		synBookAreaReference(ftp, msgInfo, encoding, sep, rs);		
		msgInfo.append("<br>");
		
		//排行信息
		//总排行  0,处理的总行数；1，畅销；2，点击；3，鲜花；4，搜索；5,校验不合格；6，数据处理失败
		rs = new int[7];
		synBookTotalRank(ftp, msgInfo, encoding, sep, rs);	
		msgInfo.append("<br>");
		//月排行
		rs = new int[7];
		synBookMonthRank(ftp, msgInfo, encoding, sep, rs);	
		msgInfo.append("<br>");
		//周排行
		rs = new int[7];
		synBookWeekRank(ftp, msgInfo, encoding, sep, rs);		
		
		//更新各货架下商品总数
		RBookBO.getInstance().updateCateTotal();
		
		//发送邮件
		logger.info("send mail begin!");
		String[] mailTo = BaseBookConfig.get("BaseBookSynMailto").split(",");
		String synBaseBookSubject = BaseBookConfig.get("synBaseBookSubject");
		Mail.sendMail(synBaseBookSubject, msgInfo.toString(), mailTo);
		logger.info("send mail end!");
		logger.debug("import book base end!");
	}
	
	/**
	 * 图书总排行信息
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookTotalRank(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book totalrank info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
		//图书分类信息
		try{
			msgInfo.append("导入图书总排行信息数据结果：");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookTotalRankRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp 下载到本地

			if(lfs.length==0){
				msgInfo.append("没有找到图书总排行信息数据文件");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookTotalRank(encoding, sep, rs, lfs, errorRowNumber);
				msgInfo.append("共处理行数："+rs[0]);
				msgInfo.append(";<br>成功更新畅销总排行榜："+rs[1]);
				msgInfo.append(";<br>成功更新点击总排行榜："+rs[2]);
				msgInfo.append(";<br>成功更新鲜花总排行榜："+rs[3]);
				msgInfo.append(";<br>成功更新搜索总排行榜："+rs[4]);
				msgInfo.append(";<br>数据检查不合法："+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>数据处理失败："+rs[6]);
				msgInfo.append("<br>");					
			}
		}catch(Exception e){
			logger.error("导入图书总排行信息数据操作失败"+e);
			logger.error(e);
			msgInfo.append("导入图书总排行信息数据操作失败"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book totalrank info begin!");
	}
	
	/**
	 * 同步图书月排行信息
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookMonthRank(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book monthrank info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
		//图书分类信息
		try{
			msgInfo.append("导入图书月排行信息数据结果：");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookMonthRankRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp 下载到本地

			if(lfs.length==0){
				msgInfo.append("没有找到图书月排行信息数据文件");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookMonthRank(encoding, sep, rs, lfs, errorRowNumber);
				msgInfo.append("共处理行数："+rs[0]);
				msgInfo.append(";<br>成功更新畅销月排行榜："+rs[1]);
				msgInfo.append(";<br>成功更新点击月排行榜："+rs[2]);
				msgInfo.append(";<br>成功更新鲜花月排行榜："+rs[3]);
				msgInfo.append(";<br>成功更新搜索月排行榜："+rs[4]);
				msgInfo.append(";<br>数据检查不合法："+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>数据处理失败："+rs[6]);
				msgInfo.append("<br>");					
			}
		}catch(Exception e){
			logger.error("导入图书月排行信息数据操作失败"+e);
			logger.error(e);
			msgInfo.append("导入图书月排行信息数据操作失败"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book monthrank info end!");
	}
	
	/**
	 * 同步图书周排行信息
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookWeekRank(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book weekrank info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
		//图书分类信息
		try{
			msgInfo.append("导入图书周排行信息数据结果：");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookWeekRankRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp 下载到本地

			if(lfs.length==0){
				msgInfo.append("没有找到图书周排行信息数据文件");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookWeekRank(encoding, sep, rs, lfs, errorRowNumber);
				msgInfo.append("共处理行数："+rs[0]);
				msgInfo.append(";<br>成功更新畅销周排行榜："+rs[1]);
				msgInfo.append(";<br>成功更新点击周排行榜："+rs[2]);
				msgInfo.append(";<br>成功更新鲜花周排行榜："+rs[3]);
				msgInfo.append(";<br>成功更新搜索周排行榜："+rs[4]);
				msgInfo.append(";<br>数据检查不合法："+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>数据处理失败："+rs[6]);
				msgInfo.append("<br>");					
			}
		}catch(Exception e){
			logger.error("导入图书周排行信息数据操作失败"+e);
			logger.error(e);
			msgInfo.append("导入图书周排行信息数据操作失败"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book weekrank info end!");
	}	
	
	/**
	 * 同步专区内容信息
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookAreaReference(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book area reference info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		//图书分类信息
		try{
			msgInfo.append("导入专区内容信息数据结果：");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookAreaReferenceRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp 下载到本地

			if(lfs.length==0){
				msgInfo.append("没有找到专区内容信息数据文件");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookReference(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("共处理行数："+rs[0]);
				msgInfo.append(";<br>成功新增："+rs[1]);
				msgInfo.append(";<br>成功修改："+rs[2]);
				msgInfo.append(";<br>成功下线："+rs[3]);
				msgInfo.append(";<br>数据检查不合法："+rs[4]);
                if(rs[4] > 0)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>数据处理失败："+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("行。");
                }
				msgInfo.append("<br>");					
			}
		}catch(Exception e){
			logger.error("导入专区内容信息数据操作失败"+e);
			logger.error(e);
			msgInfo.append("导入专区内容信息数据操作失败"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book area reference info end!");
	}	
	
	/**
	 * 同步专区信息
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookArea(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book area info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		//图书分类信息
		try{
			msgInfo.append("导入专区信息数据结果：");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookAreaRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp 下载到本地

			if(lfs.length==0){
				msgInfo.append("没有找到专区信息数据文件");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookArea(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("共处理行数："+rs[0]);
				msgInfo.append(";<br>成功新增："+rs[1]);
				msgInfo.append(";<br>成功修改："+rs[2]);
				msgInfo.append(";<br>成功下线："+rs[3]);
				msgInfo.append(";<br>数据检查不合法："+rs[4]);
                if(rs[4] > 0)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>数据处理失败："+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("行。");
                }
				msgInfo.append("<br>");					
			}
		}catch(Exception e){
			logger.error("导入专区信息数据操作失败"+e);
			logger.error(e);
			msgInfo.append("导入专区信息数据操作失败"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book aren info end!");
	}	
	
	/**
	 * 同步图书推荐信息
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookRecommend(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book recommend info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		//图书分类信息
		try{
			msgInfo.append("导入基地推荐图书信息数据结果：");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookRecommendRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp 下载到本地

			if(lfs.length==0){
				msgInfo.append("没有找到基地推荐图书信息数据文件");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookRecommend(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("共处理行数："+rs[0]);
				msgInfo.append(";<br>成功新增："+rs[1]);
				msgInfo.append(";<br>成功修改："+rs[2]);
				msgInfo.append(";<br>成功下线："+rs[3]);
				msgInfo.append(";<br>数据检查不合法："+rs[4]);
                if(rs[4] > 0)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>数据处理失败："+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("行。");
                }
				msgInfo.append("<br>");					
			}
		}catch(Exception e){
			logger.error("导入基地推荐图书信息数据操作失败"+e);
			logger.error(e);
			msgInfo.append("导入基地推荐图书信息数据操作失败"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book recommend info end!");
	}	
	
	/**
	 * 同步图书包月信息
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookMonth(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book month info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		//图书分类信息
		try{
			msgInfo.append("导入基地图书包月数据结果：");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookMonthRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp 下载到本地

			if(lfs.length==0){
				msgInfo.append("没有找到基地图书包月数据文件");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookMonth(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("共处理行数："+rs[0]);
				msgInfo.append(";<br>成功新增："+rs[1]);
				msgInfo.append(";<br>数据检查不合法："+rs[2]);
                if(rs[2] > 0)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>数据处理失败："+rs[3]);
                if(rs[3] > 0)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("行。");
                }
				msgInfo.append("<br>");				
			}
		}catch(Exception e){
			logger.error("导入基地图书包月数据操作失败"+e);
			logger.error(e);
			msgInfo.append("导入基地图书包月数据操作失败"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book month info end!");
	}	
	
	/**
	 * 同步图书信息
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookInfo(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		try{
			msgInfo.append("导入基地图书信息元数据结果：");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp 下载到本地

			if(lfs.length==0){
				msgInfo.append("没有找到基地图书数据文件");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBook(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("共处理行数："+rs[0]);
				msgInfo.append(";<br>成功新增："+rs[1]);
				msgInfo.append(";<br>成功修改："+rs[2]);
				msgInfo.append(";<br>成功下线："+rs[3]);
				msgInfo.append(";<br>数据检查不合法："+rs[4]);
                if(rs[4] > 0)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>数据处理失败："+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("行。");
                }
				msgInfo.append("<br>");				
			}
		}catch(Exception e){
			logger.error("导入基地图书信息数据操作失败"+e);
			logger.error(e);
			msgInfo.append("导入基地图书信息数据操作失败"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book info end!");
	}
	
	/**
	 * 同步作者信息
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookAuthor(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book author info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		try{
			msgInfo.append("导入基地图书作者元数据结果：");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookAuthorRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp 下载到本地

			if(lfs.length==0){
				msgInfo.append("没有找到基地图书作者数据文件");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookAuthor(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("共处理行数："+rs[0]);
				msgInfo.append(";<br>成功新增："+rs[1]);
				msgInfo.append(";<br>成功修改："+rs[2]);
				msgInfo.append(";<br>成功下线："+rs[3]);
				msgInfo.append(";<br>数据检查不合法："+rs[4]);
                if(rs[4] > 0)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>数据处理失败："+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("行。");
                }
				msgInfo.append("<br>");				
			}
		}catch(Exception e){
			logger.error("导入基地图书作者数据操作失败"+e);
			logger.error(e);
			msgInfo.append("导入基地图书作者数据操作失败"+e);
			msgInfo.append("<br>");			
		}
		logger.info("import base book author info end!");
	}
	
	/**
	 * 同步图书分类信息
	 * @param ftp
	 * @param msgInfo
	 * @param encoding
	 * @param sep
	 * @param rs
	 */
	public void synBookType(BaseBookFtpProcess ftp, StringBuffer msgInfo,
			String encoding, String sep, int[] rs) {
		logger.info("import base book type begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
		//图书分类信息
		try{
			msgInfo.append("导入基地图书分类元数据结果：");
			msgInfo.append("<br>");			
			String[] fNameRegex = BaseBookConfig.get("BookTypeRegex").split(",");
			String lfs[] = ftp.process(fNameRegex);//ftp 下载到本地

			if(lfs.length==0){
				msgInfo.append("没有找到基地图书分类数据文件");
				msgInfo.append("<br>");				
			}else{
				RBookBO.getInstance().dealBaseBookType(encoding, sep, rs, lfs, errorRowNumber, error);
				msgInfo.append("共处理行数："+rs[0]);
				msgInfo.append(";<br>成功新增："+rs[1]);
				msgInfo.append(";<br>成功修改："+rs[2]);
				msgInfo.append(";<br>成功下线："+rs[3]);
				msgInfo.append(";<br>数据检查不合法："+rs[4]);
                if(rs[4] > 0)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
				msgInfo.append(";<br>数据处理失败："+rs[5]);
                if(rs[5] > 0)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length()-2));
                    msgInfo.append("行。");
                }
				msgInfo.append("<br>");				
			}
		}catch(Exception e){
			logger.error("导入基地图书分类数据操作失败"+e);
			logger.error(e);
			msgInfo.append("导入基地图书分类数据操作失败"+e);
			msgInfo.append("<br>");			
		}
		
		logger.info("import base book type end!");
	}

}
