
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
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(BaseReadAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�����Ķ�����ͬ���࿪ʼ");
        }

        // �������л�ȡ��������
        int synReadType = Integer.parseInt(this.getParameter(request,
                                                             "synReadType")
                                               .trim());
        
        String actionType = "�����Ķ�����ͬ��";
		boolean actionResult = true;
		String actionDesc = "�����Ķ�����ͬ���ɹ�";
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

        // ��ȡҪ����Ķ���
        if (sep.startsWith("0x"))
        {
            // 0x��ͷ�ģ���ʾ��16���Ƶģ���Ҫת��
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
                // ͼ�����
                task.synBookType(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 2:
                // ������Ϣͬ��
                rs = new int[6];
                task.synBookAuthor(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 3:
                // ͼ����Ϣ
                rs = new int[6];
                task.synBookInfo(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 4:
                // �Ƽ�ͼ����Ϣ
                rs = new int[6];
                task.synBookRecommend(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 5:
                // ͼ��ͳ����Ϣ
                rs = new int[6];
                task.synBookCount(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 6:
                // ͼ�������Ϣ
                rs = new int[6];
                task.synBookUpdate(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 7:
                // �����Ϣ
                rs = new int[6];
                task.synBookMonth(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
			case 16:
		        // ���������Ϣ
		        rs = new int[4];
		        task.synBookMonthCity(ftp, msgInfo, encoding, sep, rs);
		        msgInfo.append("<br>");
				break;
            case 8:
                // �ն�Ŀ¼
                rs = new int[6];
                task.synMoDirectory(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 9:
                // ר����Ϣ
                rs = new int[6];
                task.synBookArea(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");

                // ��һ��������ר����Ϣ��ɾ���ֻ����ж���Ļ���
                RReadBO.getInstance().cleanOldSimulationDataTree();

                // �ڶ���������ר����Ϣ����������»����д��ڵĻ���
                RReadBO.getInstance().diySimulationDataTree();
                break;
            case 10:
                // ר��������Ϣ
                rs = new int[6];
                task.synBookAreaReference(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                break;
            case 11:
            	task.setDelRank(false);
            	
            	// ���а�����
                rs = new int[6];
                task.synBookTotalRank(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
                
        		//������
        		rs = new int[6];
        		task.synBookMonthRank(ftp, msgInfo, encoding, sep, rs);	
        		msgInfo.append("<br>");
        		
        		//������
        		rs = new int[6];
        		task.synBookWeekRank(ftp, msgInfo, encoding, sep, rs);

                // ��һ����ɾ����Ʒ��Ϣ���ڸ������а���Ϣ��ɾ���ֻ����ж���Ļ���
                RReadBO.getInstance().cleanOldSimulationDataRank();

                // �ڶ������������а���Ϣ����������»����д��ڵĻ���
                RReadBO.getInstance().diySimulationDataRank();

                // ���������������а���Ϣ��������Ʒ����
                RReadBO.getInstance().addDataByRankToReference();

                break;
            case 12:
                // ����ϲ��������ʷ�Ķ��Ƽ��ӿ�
                rs = new int[6];
                task.synLikeHisRead(ftp, msgInfo, encoding, sep, rs);
                msgInfo.append("<br>");
				break;
			case 13:
		        // ����ϲ�����������Ƽ��ӿ�
		        rs = new int[6];
		        task.synLikeAuthor(ftp, msgInfo, encoding, sep, rs);
		        msgInfo.append("<br>");
				break;
			case 14:
		        // ����ϲ������ͼ�鼶�Ķ������Ƽ��ӿ�
		        rs = new int[6];
		        task.synLikeReadPercentage(ftp, msgInfo, encoding, sep, rs);
		        msgInfo.append("<br>");
				break;
			case 15:
		        // ����ϲ������ͼ�鼶���������Ƽ��ӿ�
		        rs = new int[6];
		        task.synLikeOrderPercentage(ftp, msgInfo, encoding, sep, rs);
		        msgInfo.append("<br>");
				break;
			case 17:
				task.run();
				msgInfo.append("ȫ����������Ķ��������<br>");
				break;
            default:
                this.saveMessages(request, "��ǰ���ͳ��������Ķ�����ͬ��ʧ��");
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // �����ʼ�
        LOG.info("send mail begin!");
        String[] mailTo = BaseReadConfig.get("BaseBookSynMailto").split(",");
        String synBaseBookSubject = BaseReadConfig.get("synBaseBookSubject");
        Mail.sendMail(synBaseBookSubject, msgInfo.toString(), mailTo);
        LOG.info("send mail end!");
        LOG.debug("import book base end!");

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�����Ķ�����ͬ�����");
        return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
    }
}
