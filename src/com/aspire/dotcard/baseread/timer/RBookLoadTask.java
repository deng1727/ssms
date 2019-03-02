
package com.aspire.dotcard.baseread.timer;

import java.io.File;
import java.util.TimerTask;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseread.BaseReadFtpProcess;
import com.aspire.dotcard.baseread.biz.RReadBO;
import com.aspire.dotcard.baseread.config.BaseReadConfig;
import com.aspire.ponaadmin.web.mail.Mail;

public class RBookLoadTask extends TimerTask
{
    protected static JLogger logger = LoggerFactory.getLogger(RBookLoadTask.class);

    private boolean isDelRank;
    
    /**
     * �Ƿ���������ϸ��Ϣ
     */
    private boolean isPutAll = false;
    
    public void run()
    {
        logger.debug("import book base begin!");
        // Ftp����
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

        /**
         * 0,�������������1���ɹ�������2���ɹ��޸ģ�3���ɹ�ɾ����4�����ݼ�鲻�Ϸ���5�����ݴ���ʧ��
         */

        // ͼ�����
        int[] rs = new int[6];
        synBookType(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // ������Ϣͬ��
        rs = new int[6];
        synBookAuthor(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // ͼ����Ϣ
        rs = new int[6];
        synBookInfo(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // �Ƽ�ͼ����Ϣ
        rs = new int[6];
        synBookRecommend(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        
        // -------------0930�汾����������ʼ
        // ����ϲ��������ʷ�Ķ��Ƽ��ӿ�
        rs = new int[6];
        synLikeHisRead(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // ����ϲ�����������Ƽ��ӿ�
        rs = new int[6];
        synLikeAuthor(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // ����ϲ������ͼ�鼶�Ķ������Ƽ��ӿ�
        rs = new int[6];
        synLikeReadPercentage(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // ����ϲ������ͼ�鼶���������Ƽ��ӿ�
        rs = new int[6];
        synLikeOrderPercentage(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // --------------0930�汾������������
        
        // ͼ��ͳ����Ϣ
        rs = new int[6];
        synBookCount(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // ͼ�������Ϣ
        rs = new int[6];
        synBookUpdate(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // ר����Ϣ
        rs = new int[6];
        synBookArea(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");

        // ��һ��������ר����Ϣ��ɾ���ֻ����ж���Ļ���
        RReadBO.getInstance().cleanOldSimulationDataTree();
        
        // �ڶ���������ר����Ϣ����������»����д��ڵĻ���
        RReadBO.getInstance().diySimulationDataTree();
        
        // �����Ϣ
        rs = new int[6];
        synBookMonth(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        
        // ���������Ϣ
        rs = new int[4];
        synBookMonthCity(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        
        // �ն�Ŀ¼
        rs = new int[6];
        synMoDirectory(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        
        // ר��������Ϣ
        rs = new int[6];
        synBookAreaReference(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");

        isDelRank = false;
        
        // ���а�����
        rs = new int[6];
        synBookTotalRank(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        
		//������
		rs = new int[6];
		synBookMonthRank(ftp, msgInfo, encoding, sep, rs);	
		msgInfo.append("<br>");
		
		//������
		rs = new int[6];
		synBookWeekRank(ftp, msgInfo, encoding, sep, rs);
        
		
        // ��һ����ɾ����Ʒ��Ϣ���ڸ������а���Ϣ��ɾ���ֻ����ж���Ļ���
        RReadBO.getInstance().cleanOldSimulationDataRank();
        
        // �ڶ������������а���Ϣ����������»����д��ڵĻ���
        RReadBO.getInstance().diySimulationDataRank();
        
        // ���������������а���Ϣ��������Ʒ����
        RReadBO.getInstance().addDataByRankToReference();

        // ���¸���������Ʒ����
        RReadBO.getInstance().updateCateTotal();

        // �����ʼ�
        logger.info("send mail begin!");
        String[] mailTo = BaseReadConfig.get("BaseBookSynMailto").split(",");
        String synBaseBookSubject = BaseReadConfig.get("synBaseBookSubject");
        Mail.sendMail(synBaseBookSubject, msgInfo.toString(), mailTo);
        logger.info("send mail end!");
        logger.debug("import book base end!");
    }

    /**
     * ͼ����������Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookTotalRank(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base read totalrank info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        boolean isDelTable = false;

        // ͼ�������Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("ͼ��������Ϣ",
                                           "BookTotalRankVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }
            msgInfo.append("����ͼ��������Ϣ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookTotalRankRegex")
                                                .split(",");

            // ftp ���ص�����
            String lfs[] = ftp.process(fNameRegex);

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ�ͼ��������Ϣ�����ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                // �ж��ļ������Ƿ�Ϊ0
                if (isNullFile(lfs))
                {
                    // �����ʼ�������Ϣ..............
                    msgInfo.append("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������");
                    msgInfo.append("<br>");
                }
                // �ļ�����������һ����Ϊ�գ��һ�Ҫ���ȫ��ͬ��
                else if (isDelTable && !isDelRank)
                {
                    // ��մ���������û�����ô��
                    delTable("com.aspire.dotcard.baseread.dao.RankDao.delete");
                    isDelRank = true;
                }
                
                RReadBO.getInstance().dealBaseBookTotalRank(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error,"total");
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("����ͼ��������Ϣ���ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("����ͼ��������Ϣ���ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base read totalrank info begin!");
    }
    
    /**
     * ͼ����������Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookMonthRank(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base read monthrank info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        boolean isDelTable = false;

        // ͼ�������Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("ͼ����������Ϣ",
                                           "BookMonthRankVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }
            msgInfo.append("����ͼ����������Ϣ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookMonthRankRegex")
                                                .split(",");

            // ftp ���ص�����
            String lfs[] = ftp.process(fNameRegex);

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ�ͼ����������Ϣ�����ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                // �ж��ļ������Ƿ�Ϊ0
                if (isNullFile(lfs))
                {
                    // �����ʼ�������Ϣ..............
                    msgInfo.append("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������");
                    msgInfo.append("<br>");
                }
                // �ļ�����������һ����Ϊ�գ��һ�Ҫ���ȫ��ͬ��
                else if (isDelTable && !isDelRank)
                {
                    // ��մ���������û�����ô��
                    delTable("com.aspire.dotcard.baseread.dao.RankDao.delete");
                    isDelRank = true;
                }
                
                RReadBO.getInstance().dealBaseBookTotalRank(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error,
                                                            "month");
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("����ͼ����������Ϣ���ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("����ͼ����������Ϣ���ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base read monthrank info begin!");
    }
    
    /**
     * ͼ����������Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookWeekRank(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base read weekrank info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        boolean isDelTable = false;

        // ͼ�������Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("ͼ����������Ϣ",
                                           "BookWeekRankVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }
            msgInfo.append("����ͼ����������Ϣ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookWeekRankRegex")
                                                .split(",");

            // ftp ���ص�����
            String lfs[] = ftp.process(fNameRegex);

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ�ͼ����������Ϣ�����ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                // �ж��ļ������Ƿ�Ϊ0
                if (isNullFile(lfs))
                {
                    // �����ʼ�������Ϣ..............
                    msgInfo.append("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������");
                    msgInfo.append("<br>");
                }
                // �ļ�����������һ����Ϊ�գ��һ�Ҫ���ȫ��ͬ��
                else if (isDelTable && !isDelRank)
                {
                    // ��մ���������û�����ô��
                    delTable("com.aspire.dotcard.baseread.dao.RankDao.delete");
                    isDelRank = true;
                }
                
                RReadBO.getInstance().dealBaseBookTotalRank(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error,
                                                            "week");
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("����ͼ����������Ϣ���ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("����ͼ����������Ϣ���ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base read weekrank info begin!");
    }

    /**
     * ͬ��ר��������Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookAreaReference(BaseReadFtpProcess ftp,
                                     StringBuffer msgInfo, String encoding,
                                     String sep, int[] rs)
    {
        logger.info("import base book area reference info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // ר��������Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("ר��������Ϣ",
                                           "BookAreaReferenceVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }
            msgInfo.append("����ר��������Ϣ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookAreaReferenceRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ�ר��������Ϣ�����ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseBookReference(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("����ר��������Ϣ���ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("����ר��������Ϣ���ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book area reference info end!");
    }

    /**
     * ͬ��ר����Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookArea(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                            String encoding, String sep, int[] rs)
    {
        logger.info("import base book area info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // ר����Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("ר����Ϣ",
                                           "BookAreaVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("����ר����Ϣ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookAreaRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ�ר����Ϣ�����ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseBookArea(encoding,
                                                       sep,
                                                       rs,
                                                       lfs,
                                                       errorRowNumber,
                                                       error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("����ר����Ϣ���ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("����ר����Ϣ���ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book aren info end!");
    }
    

    /**
     * ����ϲ��������ʷ�Ķ��Ƽ��ӿ�
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synLikeHisRead(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base book LikeHisRead info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // ��ʷ�Ķ��Ƽ��ӿ�
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("����ϲ��������ʷ�Ķ��Ƽ�",
                                           "BookLikeHisReadVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("������ز���ϲ��������ʷ�Ķ��Ƽ���Ϣ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookLikeHisReadRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ����ز���ϲ��������ʷ�Ķ��Ƽ���Ϣ�����ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseLikeHisRead(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("������ز���ϲ��������ʷ�Ķ��Ƽ���Ϣ���ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("������ز���ϲ��������ʷ�Ķ��Ƽ���Ϣ���ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book LikeHisRead info end!");
    }

    /**
     * ����ϲ�����������Ƽ��ӿ�
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synLikeAuthor(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base book LikeAuthor info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // ��ʷ�Ķ��Ƽ��ӿ�
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("����ϲ�����������Ƽ�",
                                           "BookLikeAuthorVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("������ز���ϲ�����������Ƽ���Ϣ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookLikeAuthorRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ����ز���ϲ�����������Ƽ���Ϣ�����ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseLikeAuthor(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("������ز���ϲ�����������Ƽ���Ϣ���ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("������ز���ϲ�����������Ƽ���Ϣ���ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book LikeAuthor info end!");
    }

    /**
     * ����ϲ������ͼ�鼶�Ķ������Ƽ��ӿ�
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synLikeReadPercentage(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base book LikeReadPercentage info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // ��ʷ�Ķ��Ƽ��ӿ�
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("����ϲ������ͼ�鼶�Ķ������Ƽ�",
                                           "BookLikeReadPercentageVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("������ز���ϲ������ͼ�鼶�Ķ������Ƽ���Ϣ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookLikeReadPercentageRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ����ز���ϲ������ͼ�鼶�Ķ������Ƽ���Ϣ�����ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseLikeReadPercentage(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("������ز���ϲ������ͼ�鼶�Ķ������Ƽ���Ϣ���ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("������ز���ϲ������ͼ�鼶�Ķ������Ƽ���Ϣ���ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book LikeReadPercentage info end!");
    }
    
    /**
     * ����ϲ������ͼ�鼶���������Ƽ��ӿ�
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synLikeOrderPercentage(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base book LikeOrderPercentage info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // ��ʷ�Ķ��Ƽ��ӿ�
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("����ϲ������ͼ�鼶���������Ƽ�",
                                           "BookLikeOrderPercentageVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("������ز���ϲ������ͼ�鼶���������Ƽ���Ϣ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookLikeOrderPercentageRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ����ز���ϲ������ͼ�鼶���������Ƽ���Ϣ�����ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseLikeOrderPercentage(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("������ز���ϲ������ͼ�鼶���������Ƽ���Ϣ���ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("������ز���ϲ������ͼ�鼶���������Ƽ���Ϣ���ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book LikeOrderPercentage info end!");
    }
    
    /**
     * ͬ��ͼ���Ƽ���Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookRecommend(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base book recommend info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // ͼ���Ƽ�
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("ͼ���Ƽ�",
                                           "BookRecommendVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("��������Ƽ�ͼ����Ϣ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookRecommendRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ������Ƽ�ͼ����Ϣ�����ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseBookRecommend(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("��������Ƽ�ͼ����Ϣ���ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("��������Ƽ�ͼ����Ϣ���ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book recommend info end!");
    }

    /**
     * ͬ��ͼ�������Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookMonth(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                             String encoding, String sep, int[] rs)
    {
        logger.info("import base book month info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // ͼ�������Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("ͼ�����",
                                           "BookMonthVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("�������ͼ��������ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookMonthRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ�����ͼ����������ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseBookMonth(encoding,
                                                        sep,
                                                        rs,
                                                        lfs,
                                                        errorRowNumber,
                                                        error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("�������ͼ��������ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("�������ͼ��������ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book month info end!");
    }

    /**
     * ͬ��ͼ����µ�����Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookMonthCity(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                             String encoding, String sep, int[] rs)
    {
        logger.info("import base book month city info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        int count = 0;
        
        try
        {
        	count = Integer.parseInt(BaseReadConfig.get("BookMonthCityCount"));
        }
        catch(Exception e)
        {
        	count = 500;
        }
        

        // ͼ�������Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("ͼ����µ���",
                                           "BookMonthCityVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("�������ͼ����µ������ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookMonthCityRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ�����ͼ����µ��������ļ�");
                msgInfo.append("<br>");
            }
            else
            {
            	// �ȱ�����ݿ����ִ����ݵ�����״̬Ϊ1����������
            	if(!RReadBO.getInstance().updateBookBagArea("start"))
            	{
            		throw new Exception("ͬ���Ķ����������Ϣ��һ��ʱ���ȱ����������״̬ʱ�������󣬺�������ȡ��!");
            	}
            	
            	
                RReadBO.getInstance().dealBaseBookMonthCity(encoding,
                                                        sep,
                                                        rs, 
                                                        lfs,
                                                        errorRowNumber, error);
				
				// �����ǰ�������ݹ��٣�����ָ����ֵ����ع���ɾ���������ݣ����ԭ����״̬Ϊ0����ʱ��������
				if (RReadBO.getInstance().queryCountBookBagArea() < count)
				{
					if (!RReadBO.getInstance().delBookBagAreaByStart("0"))
					{
						throw new Exception(
								"ͬ���Ķ����������Ϣ��ɺ�ȷ������������Ч��ɾ����������ʱ�����������ֶ�ɾ����ʧЧ����!");
					}
					if (!RReadBO.getInstance().updateBookBagArea("end"))
					{
						throw new Exception(
								"ͬ���Ķ����������Ϣ��ɺ�ȷ������������Ч��ɾ���������ݺ󣬱�ԭ������״̬��������Ϊ����ʱ�����������ֶ������ʧЧ����!");
					}
				}
				// �����ǰ������������ɾ��״̬Ϊ1���������е�ԭ����
				else
				{
					if (!RReadBO.getInstance().delBookBagAreaByStart("1"))
					{
						throw new Exception(
								"ͬ���Ķ����������Ϣ��ɺ�ȷ������������Ч��ɾ��ԭ��״̬Ϊ�������е�����ʱ�����������ֶ�ɾ����ʧЧ����!");
					}
				}
                
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[2]);
                if (rs[2] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[3]);
                if (rs[3] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("�������ͼ����µ������ݲ���ʧ��:" + e.getMessage());
            logger.error(e);
            msgInfo.append("�������ͼ����µ������ݲ���ʧ��:" + e.getMessage());
            msgInfo.append("<br>");
        }
        logger.info("import base book month city info end!");
    }
    
    
    /**
     * ͬ��ͼ�������Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     
    public void synBookBagContent(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                  String encoding, String sep, int[] rs)
    {
        logger.info("import base book bag content info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // ���������Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("�������",
                                           "BookBagContentVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("�����������������ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookBagContentRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ�����������������ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseBookBagContent(encoding,
                                                             sep,
                                                             rs,
                                                             lfs,
                                                             errorRowNumber,
                                                             error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("�����������������ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("�����������������ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book bag content info end!");
    }
	*/
    
    
    /**
     * ͬ���ն�Ŀ¼��Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synMoDirectory(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                  String encoding, String sep, int[] rs)
    {
        logger.info("import base catalogid info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        boolean isDelTable = true;

        // ���������Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("�ն�Ŀ¼",
                                           "MoDirectoryVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("��������ն�Ŀ¼���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("MoDirectoryRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ������ն�Ŀ¼�����ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                // �ж��ļ������Ƿ�Ϊ0
                if (isNullFile(lfs))
                {
                    // �����ʼ�������Ϣ..............
                    msgInfo.append("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������");
                    msgInfo.append("<br>");
                }
                // �ļ�����������һ����Ϊ�գ��һ�Ҫ���ȫ��ͬ��
                else if (isDelTable)
                {
                    // ��մ���������û�����ô��
                    delTable("com.aspire.dotcard.baseread.dao.BookBagContentDao.deleteMoDirectory");
                }
                
                RReadBO.getInstance().dealBaseMoDirectory(encoding,
                                                             sep,
                                                             rs,
                                                             lfs,
                                                             errorRowNumber,
                                                             error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("��������ն�Ŀ¼���ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("��������ն�Ŀ¼���ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base catalogid info end!");
    }
    
    /**
     * ͬ��ͼ����Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookInfo(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                            String encoding, String sep, int[] rs)
    {
        logger.info("import base book info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // ͼ����Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("ͼ����Ϣ",
                                           "BookVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("�������ͼ����ϢԪ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookRegex").split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ�����ͼ�������ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseBook(encoding,
                                                   sep,
                                                   rs,
                                                   lfs,
                                                   errorRowNumber,
                                                   error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("�������ͼ����Ϣ���ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("�������ͼ����Ϣ���ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book info end!");
    }

    /**
     * ͬ��������Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookAuthor(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                              String encoding, String sep, int[] rs)
    {
        logger.info("import base book author info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // ͼ�������Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("������Ϣ",
                                           "BookAuthorVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("�������ͼ������Ԫ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookAuthorRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ�����ͼ�����������ļ�");
                msgInfo.append("<br>");
            }
            else
            {

                RReadBO.getInstance().dealBaseBookAuthor(encoding,
                                                         sep,
                                                         rs,
                                                         lfs,
                                                         errorRowNumber,
                                                         error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("�������ͼ���������ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("�������ͼ���������ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book author info end!");
    }

    /**
     * ͬ��ͼ�������Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookType(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                            String encoding, String sep, int[] rs)
    {
        logger.info("import base book type begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        boolean isDelTable = true;

        // ͼ�������Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("ͼ�����",
                                           "BookTypeVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("�������ͼ�����Ԫ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookTypeRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ�����ͼ����������ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                // �ж��ļ������Ƿ�Ϊ0
                if (isNullFile(lfs))
                {
                    // �����ʼ�������Ϣ..............
                    msgInfo.append("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������");
                    msgInfo.append("<br>");
                }
                // �ļ�����������һ����Ϊ�գ��һ�Ҫ���ȫ��ͬ��
                else if (isDelTable)
                {
                    delTable("com.aspire.dotcard.baseread.dao.TypeDao.delete");
                }

                RReadBO.getInstance().dealBaseBookType(encoding,
                                                       sep,
                                                       rs,
                                                       lfs,
                                                       errorRowNumber,
                                                       error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("�������ͼ��������ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("�������ͼ��������ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }

        logger.info("import base book type end!");
    }

    /**
     * ���ڽ��������Ķ���У���ļ�
     * 
     * @param BaseTypeName ����������������������ʾ���ʼ���
     * @param fileNameRegex ������������У���ļ�������
     * @param ftp FTP
     * @param encoding ����
     * @param sep �ָ���
     * @return ����У���ļ����
     */
    public String synReadVerf(String BaseTypeName, String fileNameRegex,
                              BaseReadFtpProcess ftp, String encoding,
                              String sep)
    {
        String verfSep = BaseReadConfig.get("BBookVerfListSep");

        if (null == verfSep)
        {
        	verfSep = "|";
        }

        // ��ȡҪ����Ķ���
        if (verfSep.startsWith("0x"))
        {
            // 0x��ͷ�ģ���ʾ��16���Ƶģ���Ҫת��
            String s = verfSep.substring(2, verfSep.length());
            int i1 = Integer.parseInt(s, 16);
            char c = ( char ) i1;
            verfSep = String.valueOf(c);
        }
        
        StringBuffer mailText = new StringBuffer();

        mailText.append("�������").append(BaseTypeName).append("У�����ݽ����");
        String[] fNameVerfRegex = BaseReadConfig.get(fileNameRegex).split(",");
        String lvfs[] = null;

        try
        {
            lvfs = ftp.process(fNameVerfRegex);
        }
        catch (BOException e)
        {
            logger.error(e);
            mailText.append("���һ���").append(BaseTypeName).append("У�������ļ�ʱ��������");
        }

        if (lvfs.length == 0)
        {
            mailText.append("û���ҵ�����").append(BaseTypeName).append("У�������ļ�");
            mailText.append("<br>");
        }
        else
        {
			mailText.append(RReadBO.getInstance().dealBaseReadVerf(encoding,
					verfSep, lvfs));
		}
        mailText.append("<br>");

        return mailText.toString();
    }

    /**
	 * У���ļ��Ƿ�ȫΪ���Ծ����Ƿ�ɾ��
	 * 
	 * @param fileList
	 * @return
	 */
    public boolean isNullFile(String[] files)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("У���ļ��Ƿ�ȫΪ���Ծ����Ƿ�ɾ����ʼ");
        }

        boolean isNullFile = true;

        for (int i = 0; i < files.length; i++)
        {
            String tempFileName = String.valueOf(files[i]);

            File file = new File(tempFileName);

            // ����ļ�Ϊ��
            if (file.length() > 0)
            {
                isNullFile = false;
            }
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("У���ļ��Ƿ�ȫΪ���Ծ����Ƿ�ɾ��" + isNullFile);
        }

        return isNullFile;
    }

    /**
     * �������ԭ����Ϣ���Ա�ȫ��ͬ��
     * 
     * @return
     */
    public String delTable(String delSqlCode)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("���ԭ����Ϣ���Ա�ȫ��ͬ��.��ʼ");
        }

        // �õ����ԭ����Ϣ��sql���
        try
        {
            DB.getInstance().executeBySQLCode(delSqlCode, null);
        }
        catch (DAOException e)
        {
            logger.error("ִ�����ԭ����Ϣ���Ա�ȫ��ͬ��ʱʧ�� sql=" + delSqlCode + " ������ϢΪ��" + e);
            return "ִ�����ԭ����Ϣʱ�������ݿ��쳣";
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("���ԭ����Ϣ���Ա�ȫ��ͬ��.����");
        }

        return BaseVideoConfig.CHECK_DATA_SUCCESS;
    }

    /**
     * ͬ��ͼ��ͳ����Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookCount(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                             String encoding, String sep, int[] rs)
    {
        logger.info("import base book count begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        boolean isDelTable = true;

        // ͼ��ͳ����Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("ͼ��ͳ��",
                                           "BookCountVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("�������ͼ��ͳ��Ԫ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookCountRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ�����ͼ��ͳ�������ļ�");
                msgInfo.append("<br>");
            }
            else
            {
                // �ж��ļ������Ƿ�Ϊ0
                if (isNullFile(lfs))
                {
                    // �����ʼ�������Ϣ..............
                    msgInfo.append("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������");
                    msgInfo.append("<br>");
                }
                // �ļ�����������һ����Ϊ�գ��һ�Ҫ���ȫ��ͬ��
                else if (isDelTable)
                {
                    delTable("com.aspire.dotcard.baseread.dao.RCountDao.delete");
                }

                RReadBO.getInstance().dealBaseBookCount(encoding,
                                                        sep,
                                                        rs,
                                                        lfs,
                                                        errorRowNumber,
                                                        error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("�������ͼ��ͳ�����ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("�������ͼ��ͳ�����ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }

        logger.info("import base book count end!");
    }

    /**
     * ͬ��ͼ�������Ϣ
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookUpdate(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                              String encoding, String sep, int[] rs)
    {
        logger.info("import base book update begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // ͼ�������Ϣ
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("ͼ�����",
                                           "BookUpdateVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("�������ͼ�����Ԫ���ݽ����");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookUpdateRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp ���ص�����

            if (lfs.length == 0)
            {
                msgInfo.append("û���ҵ�����ͼ����������ļ�");
                msgInfo.append("<br>");
            }
            else
            {

                RReadBO.getInstance().dealBaseBookUpdate(encoding,
                                                         sep,
                                                         rs,
                                                         lfs,
                                                         errorRowNumber,
                                                         error);
                msgInfo.append("������������" + rs[0]);
                msgInfo.append(";<br>�ɹ�������" + rs[1]);
                msgInfo.append(";<br>�ɹ��޸ģ�" + rs[2]);
                msgInfo.append(";<br>�ɹ����ߣ�" + rs[3]);
                msgInfo.append(";<br>���ݼ�鲻�Ϸ���" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>ʧ��У�������о���Ϊ��");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>����ʧ�������о���Ϊ����");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("�С�");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("�������ͼ��������ݲ���ʧ��" + e);
            logger.error(e);
            msgInfo.append("�������ͼ��������ݲ���ʧ��" + e);
            msgInfo.append("<br>");
        }

        logger.info("import base book update end!");
    }

	public void setDelRank(boolean isDelRank)
	{
		this.isDelRank = isDelRank;
	}

}
