
package com.aspire.dotcard.baseread.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.BaseReadFtpProcess;
import com.aspire.dotcard.baseread.biz.RReadBO;
import com.aspire.dotcard.baseread.config.BaseReadConfig;
import com.aspire.dotcard.baseread.timer.RBookLoadTask;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.mail.Mail;

public class BaseReadAction extends BaseAction
{
    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(BaseReadAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("基地阅读数据同步类开始");
        }

        // 从请求中获取操作类型
        int synReadType = Integer.parseInt(this.getParameter(request,
                                                             "synReadType")
                                               .trim());
        
        String actionType = "基地阅读数据同步";
		boolean actionResult = true;
		String actionDesc = "基地阅读数据同步成功";
		String actionTarget = String.valueOf(synReadType);
		
        BaseReadFtpProcess ftp = new BaseReadFtpProcess();

        StringBuffer msgInfo = new StringBuffer();
        String encoding = BaseReadConfig.get("fileEncoding");
        String sep = BaseReadConfig.get("BBookListSep");
        if (null == encoding)
        {
            encoding = "UTF-8";
        }
        if (null == sep)
        {
            sep = "|";
        }

        // 获取要处理的对象
        if (sep.startsWith("0x"))
        {
            // 0x开头的，表示是16进制的，需要转换
            String s = sep.substring(2, sep.length());
            int i1 = Integer.parseInt(s, 16);
            char c = ( char ) i1;
            sep = String.valueOf(c);
        }

        int[] rs = new int[6];

        RBookLoadTask task = new RBookLoadTask();

        switch (synReadType)
        {
            case 1:
                // 图书分类
                task.synBookType(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 2:
                // 作者信息同步
                rs = new int[6];
                task.synBookAuthor(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 3:
                // 图书信息
                rs = new int[6];
                task.synBookInfo(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 4:
                // 推荐图书信息
                rs = new int[6];
                task.synBookRecommend(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 5:
                // 图书统计信息
                rs = new int[6];
                task.synBookCount(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 6:
                // 图书更新信息
                rs = new int[6];
                task.synBookUpdate(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 7:
                // 书包信息
                rs = new int[6];
                task.synBookMonth(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
			case 16:
		        // 书包地域信息
		        rs = new int[4];
		        task.synBookMonthCity(ftp, msgInfo, encoding, sep, rs);
		        msgInfo.append("<br>");
				break;
            case 8:
                // 终端目录
                rs = new int[6];
                task.synMoDirectory(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 9:
                // 专区信息
                rs = new int[6];
                task.synBookArea(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");

                // 第一步：根据专区信息，删除现货架中多出的货架
                RReadBO.getInstance().cleanOldSimulationDataTree();

                // 第二步：根据专区信息，新增或更新货架中存在的货架
                RReadBO.getInstance().diySimulationDataTree();
                break;
            case 10:
                // 专区内容信息
                rs = new int[6];
                task.synBookAreaReference(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 11:
            	task.setDelRank(false);
            	
            	// 排行榜数据
                rs = new int[6];
                task.synBookTotalRank(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                
        		//月排行
        		rs = new int[6];
        		task.synBookMonthRank(ftp, msgInfo, encoding, sep, rs);	
        		msgInfo.append("<br>");
        		
        		//周排行
        		rs = new int[6];
        		task.synBookWeekRank(ftp, msgInfo, encoding, sep, rs);

                // 第一步：删除商品信息，在根据排行榜信息，删除现货架中多出的货架
                RReadBO.getInstance().cleanOldSimulationDataRank();

                // 第二步：根据排行榜信息，新增或更新货架中存在的货架
                RReadBO.getInstance().diySimulationDataRank();

                // 第三步：根据排行榜信息，加入商品表中
                RReadBO.getInstance().addDataByRankToReference();

                break;
            case 12:
                // 猜你喜欢——历史阅读推荐接口
                rs = new int[6];
                task.synLikeHisRead(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
				break;
			case 13:
		        // 猜你喜欢——名家推荐接口
		        rs = new int[6];
		        task.synLikeAuthor(ftp, msgInfo, encoding, sep, rs);
		        msgInfo.append("<br>");
				break;
			case 14:
		        // 猜你喜欢——图书级阅读关联推荐接口
		        rs = new int[6];
		        task.synLikeReadPercentage(ftp, msgInfo, encoding, sep, rs);
		        msgInfo.append("<br>");
				break;
			case 15:
		        // 猜你喜欢——图书级订购关联推荐接口
		        rs = new int[6];
		        task.synLikeOrderPercentage(ftp, msgInfo, encoding, sep, rs);
		        msgInfo.append("<br>");
				break;
			case 17:
				task.run();
				msgInfo.append("全量导入基地阅读数据完成<br>");
				break;
            default:
                this.saveMessages(request, "当前类型出错，基地阅读数据同步失败");
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 发送邮件
        LOG.info("send mail begin!");
        String[] mailTo = BaseReadConfig.get("BaseBookSynMailto").split(",");
        String synBaseBookSubject = BaseReadConfig.get("synBaseBookSubject");
        Mail.sendMail(synBaseBookSubject, msgInfo.toString(), mailTo);
        LOG.info("send mail end!");
        LOG.debug("import book base end!");

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "基地阅读数据同步完成");
        return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
    }
}
