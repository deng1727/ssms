
package com.aspire.dotcard.basemusic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.common.FileUtil;
import com.aspire.dotcard.basemusic.config.BaseMusicConfig;
import com.aspire.dotcard.basemusic.dao.ToneBoxDAO;
import com.aspire.dotcard.basemusic.dao.ToneBoxSongDAO;
import com.aspire.ponaadmin.web.mail.Mail;

public class BaseMusicLoadTask extends TimerTask
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseMusicLoadTask.class);

    /**
     * ����run���з���
     */
    public void run()
    {
        // ��ʼ��ͳ����Ϣ��
        // this.successAdd = 0;
        // this.successDelete = 0;
        // this.successUpdate = 0;
        // this.failureCheck = 0;
        // this.failureProcess = 0;

        String isCloseNotBaseMusicData = BaseMusicConfig.get("isCloseNotBaseMusicData");
        String synBaseMusicSubject = BaseMusicConfig.get("synBaseMusicSubject");
        String[] BaseMusicSynMailto = BaseMusicConfig.get("BaseMusicSynMailto")
                                                     .split(",");
        StringBuffer msgInfo = new StringBuffer();
        /**
         * 0,�������������1���ɹ�������2���ɹ��޸ģ�3���ɹ�ɾ����4�����ݼ�鲻�Ϸ���5�����ݴ���ʧ��
         */
        Integer musicmailInfo[] = new Integer[6];
        /**
         * 0,�������������1���ɹ�������2���ظ��ϼ�ʧ�ܣ�3��Ԫ���ݲ������ϼ�ʧ�ܣ�4�����ݼ�鲻�Ϸ���5�����ݴ���ʧ��
         */
        Integer newRefmailInfo[] = new Integer[6];
        /**
         * 0,�������������1���ɹ�������2���ظ��ϼ�ʧ�ܣ�3��Ԫ���ݲ������ϼ�ʧ�ܣ�4�����ݼ�鲻�Ϸ���5�����ݴ���ʧ��
         */
        Integer charRefmailInfo[] = new Integer[6];

        /**
         * �¼���Ʒ���
         */
        Integer[] delRefmailInfo = new Integer[1];

        /**
         * ���»�����Ʒ����
         */
        Integer[] updatecrInfo = new Integer[1];

        /**
         * ���ڱ���У��ʧ�ܵ���������Ϣ
         */
        StringBuffer checkFailureRow = new StringBuffer();

        /**
         * ���ڱ���������У��ʧ�ܵ���������Ϣ
         */
        StringBuffer checkFailureRowByNew = new StringBuffer();

        /**
         * ���ڱ���У��ʧ�ܵ���������Ϣ
         */
        StringBuffer checkFailureRowByLatest = new StringBuffer();

        /**
         * ���ڱ���������У��ʧ�ܵ���������Ϣ
         */
        StringBuffer checkFailureRowByUp = new StringBuffer();
        
        /**
         * ���ڱ���У��ʧ�ܵ���������Ϣ
         */
        StringBuffer errorFailureRow = new StringBuffer();

        /**
         * ���ڱ���������У��ʧ�ܵ���������Ϣ
         */
        StringBuffer errorFailureRowByNew = new StringBuffer();

        /**
         * ���ڱ���У��ʧ�ܵ���������Ϣ
         */
        StringBuffer errorFailureRowByLatest = new StringBuffer();

        /**
         * ���ڱ���������У��ʧ�ܵ���������Ϣ
         */
        StringBuffer errorFailureRowByUp = new StringBuffer();

        //
      //  BaseMusicFtpProcessor bp = new BaseMusicFtpProcessor();

        // List nameList=new ArrayList(1);
        try
        {
            // �Ѵ��ڵ�ȫ����������id�嵥
            HashMap existsMusic = new HashMap();
            // �Ѵ��ڵ�ȫ������������Ʒ�嵥
            HashMap existsRefMusic = new HashMap();
           /* try
            { // ��ȡ���е��Ѿ����ڵ�����
                existsMusic = BMusicDAO.getInstance().getAllexistMusicID();

            }
            catch (DAOException e1)
            // catch (Exception e1)
            {
                logger.error("�����ݿ��л�ȡ���л�������IDʱ�������ݿ��쳣��", e1);
                throw new BOException("�����ݿ��л�ȡ���л�������IDʱ�������ݿ��쳣", e1);
            }

            // 1,��ȡ�����嵥�ļ�����
            String[] fNameRegex = BaseMusicConfig.get("MusicNameRegex")
                                                 .split(",");
            msgInfo.append("�����������Ԫ���ݽ����");
            msgInfo.append("<br>");
            try
            {
                String localFileNames[] = bp.process(fNameRegex);// ftp ���ص�����
                this.importBaseMusicData(existsMusic,
                                         musicmailInfo,
                                         localFileNames,
                                         checkFailureRow,
                                         errorFailureRow);// ִ�������嵥����

                if (musicmailInfo[0] != null)
                {
                    msgInfo.append("������������" + musicmailInfo[0].intValue());
                    msgInfo.append(";<br>�ɹ�������" + musicmailInfo[1].intValue());
                    msgInfo.append(";<br>�ɹ��޸ģ�" + musicmailInfo[2].intValue());
                    msgInfo.append(";<br>�ɹ�ɾ����" + musicmailInfo[3].intValue());
                    msgInfo.append(";<br>���ݼ�鲻�Ϸ���"
                                   + musicmailInfo[4].intValue());
                    if (musicmailInfo[4].intValue() > 0)
                    {
                        msgInfo.append(";<br>���ݼ���������У�")
                               .append(checkFailureRow);
                    }
                    msgInfo.append(";<br>���ݴ���ʧ�ܣ�" + musicmailInfo[5].intValue());
                    if (musicmailInfo[5].intValue() > 0)
                    {
                        msgInfo.append(";<br>���ݴ���ʧ�ܾ����У���")
                               .append(errorFailureRow.substring(0, errorFailureRow.length()-2))
                               .append("��<br>");
                    }
                    msgInfo.append("<br>");
                }

            }
            catch (BOException e)
            {
                // TODO Auto-generated catch block
                logger.error("�����������Ԫ���ݲ���ʧ��" + e);
                e.printStackTrace();
                msgInfo.append("�����������Ԫ���ݲ���ʧ��" + e);
                msgInfo.append("<br>");
            }*/
            /** ********************������������������ ������Ϣ�������********************** */
            /**
             * 0,�������������1���ɹ�������2���ɹ��޸ģ�3���ɹ�ɾ����4�����ݼ�鲻�Ϸ���5�����ݴ���ʧ��
             */
            // �Ѵ��ڵ�ȫ���������ָ���id�嵥
            HashMap existsNewMusicSinger = new HashMap();
            Integer newmusicSingermailInfo[] = new Integer[6];
            
            String[] newFNamesRegex = BaseMusicConfig.get("NewMusicSingerNameRegex")
            .split(",");
            NewBaseMusicFtpProcessor newbps = new NewBaseMusicFtpProcessor();
           
            try
            { // ��ȡ���е��Ѿ����ڵ����ָ���ID
            	existsNewMusicSinger = BMusicDAO.getInstance()
            					.getAllexistNewMusicSingerID();
            }
            catch (DAOException e1)
            {
            		logger.error("�����ݿ��л�ȡ���л��� �� ���ָ��� ʱ�������ݿ��쳣��", e1);
            		throw new BOException("�����ݿ��л�ȡ���л��� �� ���ָ���ʱ�������ݿ��쳣", e1);
            }
            		msgInfo.append("<br>");
            		msgInfo.append("������� �� ���ָ��� Ԫ���ݽ����");
            		msgInfo.append("<br>");
            		try
            		{
            				String newSingerLocalFileNames[] = newbps.process(newFNamesRegex);// ftp
            				 // ���ص�����
                            this.importNewBaseMusicSingerData(existsNewMusicSinger,
                            							newmusicSingermailInfo,
                                                        newSingerLocalFileNames,
                                                        checkFailureRowByNew,
                                                        errorFailureRowByNew);// ִ�����ָ��ֵ���
                            if (newmusicSingermailInfo[0] != null)
                            {
                                msgInfo.append("������������" + newmusicSingermailInfo[0].intValue());
                                msgInfo.append(";<br>�ɹ�������"
                                               + newmusicSingermailInfo[1].intValue());
                                msgInfo.append(";<br>�ɹ��޸ģ�"
                                               + newmusicSingermailInfo[2].intValue());
                                msgInfo.append(";<br>�ɹ�ɾ����"
                                               + newmusicSingermailInfo[3].intValue());
                                msgInfo.append(";<br>���ݼ�鲻�Ϸ���"
                                               + newmusicSingermailInfo[4].intValue());
                                if (newmusicSingermailInfo[4].intValue() > 0)
                                {
                                    msgInfo.append(";<br>���ݼ���������У�")
                                           .append(checkFailureRowByNew );
                                }
                                msgInfo.append(";<br>���ݴ���ʧ�ܣ�"
                                               + newmusicSingermailInfo[5].intValue());
                                if (newmusicSingermailInfo[5].intValue() > 0)
                                {
                                    msgInfo.append(";<br>���ݴ���ʧ�ܾ����У���")
                                           .append(errorFailureRowByNew.substring(0, errorFailureRowByNew.length()-2))
                                           .append("��<br>");
                                }
                                msgInfo.append("<br>");
                            }

                        }
                        catch (BOException e)
                        {
                            // TODO Auto-generated catch block
                            logger.error("������� �� ���ָ������ݲ���ʧ��" + e);
                            e.printStackTrace();
                            msgInfo.append("������� �� ���ָ������ݲ���ʧ��" + e);
                            msgInfo.append("<br>");
                        }
            
            
            
            /** *******************������������������ ������Ϣ������� ����******************* */
            
            
            
            /** ********************�����������������ݵ�WAP�Ż�********************** */
            /**
             * 0,�������������1���ɹ�������2���ɹ��޸ģ�3���ɹ�ɾ����4�����ݼ�鲻�Ϸ���5�����ݴ���ʧ��
             */
            Integer newmusicmailInfo[] = new Integer[6];
            // 1,��ȡ�������嵥�ļ�����
            /**
             * �¼�����Ʒ���
             */
            Integer[] delNewRefmailInfo = new Integer[1];
            /**
             * ���»�����Ʒ����
             */
            Integer[] updateNewcrInfo = new Integer[1];

            String[] newFNameRegex = BaseMusicConfig.get("NewMusicNameRegex")
                                                    .split(",");
            NewBaseMusicFtpProcessor newbp = new NewBaseMusicFtpProcessor();
            // �Ѵ��ڵ�ȫ����������id�嵥
            HashMap existsNewMusic = new HashMap();
            try
            { // ��ȡ���е��Ѿ����ڵ�����
                existsNewMusic = BMusicDAO.getInstance()
                                          .getAllexistNewMusicID();
            }
            catch (DAOException e1)
            {
                logger.error("�����ݿ��л�ȡ���л��� �� ����IDʱ�������ݿ��쳣��", e1);
                throw new BOException("�����ݿ��л�ȡ���л��� �� ����IDʱ�������ݿ��쳣", e1);
            }
            msgInfo.append("<br>");
            msgInfo.append("������� �� ����Ԫ���ݽ����");
            msgInfo.append("<br>");
            try
            {
                String newLocalFileNames[] = newbp.process(newFNameRegex);// ftp
                // ���ص�����
                this.importNewBaseMusicData(existsNewMusic,existsNewMusicSinger,
                                            newmusicmailInfo,
                                            newLocalFileNames,
                                            checkFailureRowByNew,
                                            errorFailureRowByNew);// ִ�������嵥����
                if (newmusicmailInfo[0] != null)
                {
                    msgInfo.append("������������" + newmusicmailInfo[0].intValue());
                    msgInfo.append(";<br>�ɹ�������"
                                   + newmusicmailInfo[1].intValue());
                    msgInfo.append(";<br>�ɹ��޸ģ�"
                                   + newmusicmailInfo[2].intValue());
                    msgInfo.append(";<br>�ɹ�ɾ����"
                                   + newmusicmailInfo[3].intValue());
                    msgInfo.append(";<br>���ݼ�鲻�Ϸ���"
                                   + newmusicmailInfo[4].intValue());
                    if (newmusicmailInfo[4].intValue() > 0)
                    {
                        msgInfo.append(";<br>���ݼ���������У�")
                               .append(checkFailureRowByNew );
                    }
                    msgInfo.append(";<br>���ݴ���ʧ�ܣ�"
                                   + newmusicmailInfo[5].intValue());
                    if (newmusicmailInfo[5].intValue() > 0)
                    {
                        msgInfo.append(";<br>���ݴ���ʧ�ܾ����У���")
                               .append(errorFailureRowByNew.substring(0, errorFailureRowByNew.length()-2))
                               .append("��<br>");
                    }
                    msgInfo.append("<br>");
                }

            }
            catch (BOException e)
            {
                // TODO Auto-generated catch block
                logger.error("������� �� ����Ԫ���ݲ���ʧ��" + e);
                e.printStackTrace();
                msgInfo.append("������� �� ����Ԫ���ݲ���ʧ��" + e);
                msgInfo.append("<br>");
            }
            // 4,�¼�ȫ�����ڻ���������Ʒ
            // this.delInvalBMusicRef(delNewRefmailInfo);
            BaseMusicBO.getInstance().delInvalNewBMusicRef(delRefmailInfo);
            if (delNewRefmailInfo != null && delNewRefmailInfo[0] != null)
            {
                msgInfo.append("<br>");
                msgInfo.append("�¼ܹ��ڻ��� �� ������Ʒ����Ϊ��"
                               + delNewRefmailInfo[0].intValue());
                msgInfo.append("<br>");
            }
            // 5,���»�����Ʒ����
            // this.updateCategoryRefSum(updateNewcrInfo);
            BaseMusicBO.getInstance().updateAllNewCategoryRefSum(updatecrInfo);
            if (updateNewcrInfo != null && updateNewcrInfo[0] != null)
            {
                msgInfo.append("<br>");
                msgInfo.append("���� �� ���ֻ�������Ϊ��" + updateNewcrInfo[0].intValue());
                msgInfo.append("<br>");
            }

            /** *******************�����������������ݵ�WAP�Ż� ����******************* */

            // �������
            if ("true".equals(isCloseNotBaseMusicData))
            {
            	 /** *******************��������������ר����ר������ �������ݵ����� ��ʼ******************* */
//            	 2,ͬ��ר��
            	/**
                 * 0,�������������1���ɹ�������2���ɹ��޸ģ�3���ɹ�ɾ����4�����ݼ�鲻�Ϸ���5�����ݴ���ʧ��
                 */
                Integer newmusicAlbummailInfo[] = new Integer[6];
                
                List albumNameAndCate = BaseMusicConfig.getArrayValues("albumNameRegex");
                if (albumNameAndCate != null && albumNameAndCate.size() > 0)
                {
                    msgInfo.append("<br>");
                    msgInfo.append("�������������ר�����ݽ����");
                    msgInfo.append("<br>");
                    for (int i = 0; i < albumNameAndCate.size(); i++)
                    {
                        String hm[] = ( String[] ) albumNameAndCate.get(i);
                        if (hm.length == 2)
                        {

                            String newcategoryID = hm[0];
                            String newNameRegexs[] = hm[1].split(",");
                            
                            // �Ѵ��ڵ�ȫ������ר���嵥
                            HashMap existsAlbumCate = new HashMap();
                            try
                            { // ��ȡ�Ѿ����ڵ�����ר��
                            	existsAlbumCate = BMusicDAO.getInstance()
                                                          .getAllexistAlbumMusicCateID(newcategoryID);
                            }
                            catch (DAOException e1)
                            {
                                logger.error("�����ݿ��л�ȡ���л��� �� ����IDʱ�������ݿ��쳣��", e1);
                                throw new BOException("�����ݿ��л�ȡ���л��� �� ����IDʱ�������ݿ��쳣", e1);
                            }
                            
                            try
                            {
                                String localNewNameRegexs[] = newbp.process(newNameRegexs);// ftp
                                // ���ص�����
                                this.importAlbumMusicCate(existsNewMusicSinger,
                                							existsAlbumCate,
                                							newmusicAlbummailInfo,
                                                            newcategoryID,
                                                            localNewNameRegexs,
                                                            checkFailureRowByLatest,
                                                            errorFailureRowByLatest);
                            }
                            catch (BOException e)
                            {
                                // TODO Auto-generated catch block
                                logger.error("ͬ��������ר������ʧ��" + e);
                                e.printStackTrace();
                                msgInfo.append("ͬ��������ר������ʧ��" + e);
                                msgInfo.append("<br>");
                            }
                        }
                    }
                    if (newmusicAlbummailInfo[0] != null)
                    {
                    	msgInfo.append("������������" + newmusicAlbummailInfo[0].intValue());
                        msgInfo.append(";<br>�ɹ�������"
                                       + newmusicAlbummailInfo[1].intValue());
                        msgInfo.append(";<br>�ɹ��޸ģ�"
                                       + newmusicAlbummailInfo[2].intValue());
                        msgInfo.append(";<br>�ɹ�ɾ����"
                                       + newmusicAlbummailInfo[3].intValue());
                        msgInfo.append(";<br>���ݼ�鲻�Ϸ���"
                                       + newmusicAlbummailInfo[4].intValue());
                        if (newmusicAlbummailInfo[4].intValue() > 0)
                        {
                            msgInfo.append(";<br>���ݼ���������У�")
                                   .append(checkFailureRowByNew );
                        }
                        msgInfo.append(";<br>���ݴ���ʧ�ܣ�"
                                       + newmusicAlbummailInfo[5].intValue());
                        if (newmusicAlbummailInfo[5].intValue() > 0)
                        {
                            msgInfo.append(";<br>���ݴ���ʧ�ܾ����У���")
                                   .append(errorFailureRowByNew.substring(0, errorFailureRowByNew.length()-2))
                                   .append("��<br>");
                        }
                        msgInfo.append("<br>");
                    }


                }
            	
            	
                // 2,�ϼ�ר������������Ʒ
                String[] albumRefNameAndCate = BaseMusicConfig.get("albumRefNameRegex").split(",");
                HashMap  existsAlbumCateId = null;
                try
                { // ��ȡ���е��Ѿ����ڵ�ר��
                existsAlbumCateId = BMusicDAO.getInstance()
                .getAllexistAlbumMusicCateID();
                }catch (DAOException e1)
              {
                  logger.error("�����ݿ��л�ȡ���л��� �� ����ר�� ʱ�������ݿ��쳣��", e1);
                  throw new BOException("�����ݿ��л�ȡ���л��� �� ����ר�� ʱ�������ݿ��쳣", e1);
              }
                if (albumRefNameAndCate != null && albumRefNameAndCate.length > 0)
                {
                    msgInfo.append("<br>");
                    msgInfo.append("�����������ר������������Ʒ���ݽ����");
                    msgInfo.append("<br>");
               
                            try
                            {
                                String localNewNameRegexs[] = newbp.process(albumRefNameAndCate);// ftp
                                // ���ص�����
                                this.importNewMusicRefrence( existsAlbumCateId,
                                		 					//existsMusic,
                                							existsNewMusic,
                                                            existsRefMusic,
                                                            newRefmailInfo,
                                                           
                                                            localNewNameRegexs,
                                                            checkFailureRowByLatest,
                                                            errorFailureRowByLatest);
                            }
                            catch (BOException e)
                            {
                                // TODO Auto-generated catch block
                                logger.error("�ϼ�������ר����������ʧ��" + e);
                                e.printStackTrace();
                                msgInfo.append("�ϼ�������ר����������ʧ��" + e);
                                msgInfo.append("<br>");
                            }
           
                    if (newRefmailInfo[0] != null)
                    {
                        msgInfo.append("������������" + newRefmailInfo[0].intValue());
                        msgInfo.append(";<br>�ϼܣ�"
                                       + (newRefmailInfo[1].intValue() - newRefmailInfo[2].intValue()));
                        msgInfo.append(";<br>�ظ��ϼ�ʧ�ܣ�"
                                       + newRefmailInfo[2].intValue());
                        msgInfo.append(";<br>Ԫ���ݲ������ϼ�ʧ�ܣ�"
                                       + newRefmailInfo[3].intValue());
                        msgInfo.append(";<br>���ݼ�鲻�Ϸ���"
                                       + newRefmailInfo[4].intValue());
                        if (newRefmailInfo[4].intValue() > 0)
                        {
                            msgInfo.append(";<br>���ݼ���������У�")
                                   .append(checkFailureRowByLatest );
                        }
                        msgInfo.append(";<br>���ݴ���ʧ�ܣ�"
                                       + newRefmailInfo[5].intValue());
                        if (newRefmailInfo[5].intValue() > 0)
                        {
                            msgInfo.append(";<br>���ݴ���ʧ�ܾ����У���")
                                   .append(errorFailureRowByLatest.substring(0, errorFailureRowByLatest.length()-2))
                                   .append("��<br>");
                        }
                        msgInfo.append("<br>");
                    }

                }
                /** *******************��������������ר����ר������ �������ݵ����� ����******************* */
            }
            // 3���ϼܰ�������Ʒ
            List chartNameAndCate = BaseMusicConfig.getArrayValues("ChartNameRegex");
            if (chartNameAndCate != null && chartNameAndCate.size() > 0)
            {

                for (int i = 0; i < chartNameAndCate.size(); i++)
                {
                    String hm2[] = ( String[] ) chartNameAndCate.get(i);
                    if (hm2.length == 2)
                    {
                        String chartcategoryID = hm2[0];
                        String chartNameRegexs[] = hm2[1].split(",");

                        try
                        {
                            // String localChartNameRegexs[] =
                            // bp.process(chartNameRegexs);// ftp
                            String localChartNameRegexs[] = newbp.process(chartNameRegexs);// ftp
                            // ���ص�����
                            this.importChartsMusicRefrence(existsNewMusic,
                                                           existsRefMusic,
                                                           charRefmailInfo,
                                                           chartcategoryID,
                                                           localChartNameRegexs,
                                                           checkFailureRowByUp,
                                                           errorFailureRowByUp);
                        }
                        catch (BOException e)
                        {
                            // TODO Auto-generated catch block
                            // MUSIC_LIST_~DyyyyMMdd~_�����ܰ�.txt
                            // MUSIC_LIST_~DyyyyMMdd[-1]~_�����ܰ�.txt
                            String filename = hm2[1].substring(hm2[1].lastIndexOf("_") + 1,
                                                               hm2[1].lastIndexOf("."));
                            msgInfo.append("<br>");
                            msgInfo.append("��������������а��ļ�[" + filename + "]���ݽ����");
                            msgInfo.append("<br>");
                            logger.error("�ϼܰ��������а���Ʒ����ʧ��" + e);
                            e.printStackTrace();
                            msgInfo.append("�ϼܰ������а�����Ʒ����ʧ��" + e);
                            msgInfo.append("<br>");
                        }
                        String filename = hm2[1].substring(hm2[1].lastIndexOf("_") + 1,
                                                           hm2[1].lastIndexOf("."));
                        msgInfo.append("<br>");
                        msgInfo.append("��������������а��ļ�[" + filename + "]���ݽ����");
                        msgInfo.append("<br>");
                        if (charRefmailInfo[0] != null)
                        {
                            msgInfo.append("������������"
                                           + charRefmailInfo[0].intValue());
                            msgInfo.append(";<br>�ϼ�:"
                                           + (charRefmailInfo[1].intValue() - charRefmailInfo[2].intValue()));
                            msgInfo.append(";<br>�ظ��ϼ�ʧ��:"
                                           + charRefmailInfo[2].intValue());
                            msgInfo.append(";<br>Ԫ���ݲ������ϼ�ʧ�ܣ�"
                                           + charRefmailInfo[3].intValue());
                            msgInfo.append(";<br>���ݼ�鲻�Ϸ���"
                                           + charRefmailInfo[4].intValue());
                            if (charRefmailInfo[4].intValue() > 0)
                            {
                                msgInfo.append(";<br>���ݼ���������У�")
                                       .append(checkFailureRowByUp );
                            }
                            msgInfo.append(";<br>���ݴ���ʧ�ܣ�"
                                           + charRefmailInfo[5].intValue());
                            if (charRefmailInfo[5].intValue() > 0)
                            {
                                msgInfo.append(";<br>���ݴ���ʧ�ܾ����У���")
                                       .append(errorFailureRowByUp.substring(0, errorFailureRowByUp.length()-2))
                                       .append("��<br>");
                            }
                            msgInfo.append("<br>");
                        }
                        else
                        {
                            // û���ҵ�����
                            msgInfo.append("û���ҵ��ļ�");
                            msgInfo.append("<br>");
                        }
                        // ���³�ʼ������
                        charRefmailInfo = new Integer[6];
                    }
                }
            }
            // 4,�¼�ȫ�����ڻ���������Ʒ
            this.delInvalBMusicRef(delRefmailInfo);
            if (delRefmailInfo != null && delRefmailInfo[0] != null)
            {
                msgInfo.append("<br>");
                msgInfo.append("�¼ܹ��ڻ���������Ʒ����Ϊ��" + delRefmailInfo[0].intValue());
                msgInfo.append("<br>");
            }
            // 5,���»�����Ʒ����
            this.updateCategoryRefSum(updatecrInfo);
            if (updatecrInfo != null && updatecrInfo[0] != null)
            {
                msgInfo.append("<br>");
                msgInfo.append("�������ֻ�������Ϊ��" + updatecrInfo[0].intValue());
                msgInfo.append("<br>");
            }
            
            
            
         // 6��ͬ������������
            /** ********************������������������********************** */
            /**
             * 0,�������������1���ɹ�������2���ɹ��޸ģ�3���ɹ�ɾ����4�����ݼ�鲻�Ϸ���5�����ݴ���ʧ��
             */
            // �Ѵ��ڵ�ȫ��������id�嵥
            HashMap existsTonebox = new HashMap();
            Integer toneboxMailInfo[] = new Integer[6];
            checkFailureRowByNew=new StringBuffer();
            errorFailureRowByNew=new StringBuffer();
            String[] toneboxNameRegex = BaseMusicConfig.get("toneboxNameRegex").split(",");
           
            try
            { // ��ȡ���е��Ѿ����ڵ����ָ���ID
            	existsTonebox = new ToneBoxDAO().getAllExistToneBoxID();
            					
            }
            catch (DAOException e1)
            {
            		logger.error("�����ݿ��л�ȡ����������ʱ�������ݿ��쳣��", e1);
            		throw new BOException("�����ݿ��л�ȡ����������ʱ�������ݿ��쳣", e1);
            }
    		msgInfo.append("<br>");
    		msgInfo.append("����������Ԫ���ݽ����");
    		msgInfo.append("<br>");
    		try
    		{
    				String toneboxLocalFileNames[] = newbps.process(toneboxNameRegex);// ftp
    				
    				String verfFile = FileUtil.getVerfFile(toneboxLocalFileNames);
    				String verfMsg = null;
    				if(verfFile!=null){
    					try{
    						verfMsg = FileUtil.getVerfMsg(verfFile);
    					}catch(Exception e){
    						
    					}
    					
    				}
    				
    				 // ���ص�����
                    this.importToneBox(existsTonebox,
                    							toneboxMailInfo,
                    							toneboxLocalFileNames,
                                                checkFailureRowByNew,
                                                errorFailureRowByNew);// ִ�����ָ��ֵ���
                    if (toneboxMailInfo[0] != null)
                    {
                    	msgInfo.append("У���ļ���Ϣ���£�"+verfMsg);
                        msgInfo.append("������������" + toneboxMailInfo[0].intValue());
                        msgInfo.append(";<br>�ɹ�������"
                                       + toneboxMailInfo[1].intValue());
                        msgInfo.append(";<br>�ɹ��޸ģ�"
                                       + toneboxMailInfo[2].intValue());
                        msgInfo.append(";<br>�ɹ�ɾ����"
                                       + toneboxMailInfo[3].intValue());
                        msgInfo.append(";<br>���ݼ�鲻�Ϸ���"
                                       + toneboxMailInfo[4].intValue());
                        if (toneboxMailInfo[4].intValue() > 0)
                        {
                            msgInfo.append(";<br>���ݼ���������У�")
                                   .append(checkFailureRowByNew );
                        }
                        msgInfo.append(";<br>���ݴ���ʧ�ܣ�"
                                       + toneboxMailInfo[5].intValue());
                        if (toneboxMailInfo[5].intValue() > 0)
                        {
                            msgInfo.append(";<br>���ݴ���ʧ�ܾ����У���")
                                   .append(errorFailureRowByNew.substring(0, errorFailureRowByNew.length()-2))
                                   .append("��<br>");
                        }
                        msgInfo.append("<br>");
                    }

                }
                catch (BOException e)
                {
                    // TODO Auto-generated catch block
                    logger.error("�������������ݲ���ʧ��" + e);
                    e.printStackTrace();
                    msgInfo.append("�������������ݲ���ʧ��" + e);
                    msgInfo.append("<br>");
                }
            
            /** *******************����������������Ϣ������� ����******************* */
            
             // 7��ͬ�����������и���
                /** ********************�������������и�������********************** */
                /**
                 * 0,�������������1���ɹ�������2���ɹ��޸ģ�3���ɹ�ɾ����4�����ݼ�鲻�Ϸ���5�����ݴ���ʧ��
                 */
                // �Ѵ��ڵ�ȫ��������id�嵥
                HashMap existsToneboxSong = new HashMap();
                Integer toneboxSongMailInfo[] = new Integer[6];
                checkFailureRowByNew=new StringBuffer();
                errorFailureRowByNew=new StringBuffer();
                String[] toneboxSongNameRegex = BaseMusicConfig.get("toneboxSongNameRegex").split(",");
               
                try
                { // ��ȡ���е��Ѿ����ڵ����ָ���ID
                	existsToneboxSong = new ToneBoxSongDAO().getAllExistToneBoxSongID();
                					
                }
                catch (DAOException e1)
                {
                		logger.error("�����ݿ��л�ȡ���������и���ʱ�������ݿ��쳣��", e1);
                		throw new BOException("�����ݿ��л�ȡ���������и���ʱ�������ݿ��쳣", e1);
                }
        		msgInfo.append("<br>");
        		msgInfo.append("���������и���Ԫ���ݽ����");
        		msgInfo.append("<br>");
        		try
        		{
        				String toneboxSongLocalFileNames[] = newbps.process(toneboxSongNameRegex);// ftp
        				
        				String verfFile = FileUtil.getVerfFile(toneboxSongLocalFileNames);
        				String verfMsg = null;
        				if(verfFile!=null){
        					try{
        						verfMsg = FileUtil.getVerfMsg(verfFile);
        					}catch(Exception e){
        						
        					}
        					
        				}
        				
        				 // ���ص�����
                        this.importToneBoxSong(existsToneboxSong,
                        							toneboxSongMailInfo,
                        							toneboxSongLocalFileNames,
                                                    checkFailureRowByNew,
                                                    errorFailureRowByNew);// ִ�����ָ��ֵ���
                        if (toneboxSongMailInfo[0] != null)
                        {
                        	msgInfo.append("У���ļ���Ϣ���£�"+verfMsg);
                        	
                            msgInfo.append("������������" + toneboxSongMailInfo[0].intValue());
                            msgInfo.append(";<br>�ɹ�������"
                                           + toneboxSongMailInfo[1].intValue());
                            msgInfo.append(";<br>�ɹ��޸ģ�"
                                           + toneboxSongMailInfo[2].intValue());
                            msgInfo.append(";<br>�ɹ�ɾ����"
                                           + toneboxSongMailInfo[3].intValue());
                            msgInfo.append(";<br>���ݼ�鲻�Ϸ���"
                                           + toneboxSongMailInfo[4].intValue());
                            if (toneboxSongMailInfo[4].intValue() > 0)
                            {
                                msgInfo.append(";<br>���ݼ���������У�")
                                       .append(checkFailureRowByNew );
                            }
                            msgInfo.append(";<br>���ݴ���ʧ�ܣ�"
                                           + toneboxSongMailInfo[5].intValue());
                            if (toneboxSongMailInfo[5].intValue() > 0)
                            {
                                msgInfo.append(";<br>���ݴ���ʧ�ܾ����У���")
                                       .append(errorFailureRowByNew.substring(0, errorFailureRowByNew.length()-2))
                                       .append("��<br>");
                            }
                            msgInfo.append("<br>");
                        }

                    }
                    catch (BOException e)
                    {
                        // TODO Auto-generated catch block
                        logger.error("���������и������ݲ���ʧ��" + e);
                        e.printStackTrace();
                        msgInfo.append("���������и������ݲ���ʧ��" + e);
                        msgInfo.append("<br>");
                    }
                
                /** *******************�������������и�����Ϣ������� ����******************* */   
            

        }
        catch (BOException e)
        {
            // TODO Auto-generated catch block
            logger.error("����ʧ��" + e);
            e.printStackTrace();
            msgInfo.append("����ʧ��" + e);
        }
        finally
        {
            logger.error("�����������ݵ��룺" + msgInfo);
            this.sendMail(msgInfo.toString(),
                          BaseMusicSynMailto,
                          synBaseMusicSubject);
        }
    }

    /**
     * ���������嵥
     * 
     * @param msgInfo
     * @param RefDataTime
     * @param nameList
     * @return
     * @throws BOException
     */
    private int importBaseMusicData(HashMap existsMusic,
                                    Integer musicmailInfo[], String[] nameList,
                                    StringBuffer checkFailureRow, StringBuffer errorFailureRow)
                    throws BOException
    {
        BaseMusicBO.getInstance().importBaseMusic(existsMusic,
                                                  musicmailInfo,
                                                  nameList,
                                                  checkFailureRow, errorFailureRow);
        return 0;
    }

    /**
     * ������ �����嵥
     * 
     * @param msgInfo
     * @param RefDataTime
     * @param nameList
     * @return
     * @throws BOException
     */
    private void importNewBaseMusicData(HashMap existsMusic,
    									HashMap  existsMusicSingers,
                                        Integer musicmailInfo[],
                                        String[] nameList,
                                        StringBuffer checkFailureRowByNew, StringBuffer errorFailureRowByNew)
                    throws BOException
    {
        BaseMusicBO.getInstance().importNewBaseMusic(existsMusic,existsMusicSingers,
                                                     musicmailInfo,
                                                     nameList,
                                                     checkFailureRowByNew, errorFailureRowByNew);
    }

    /**
     * ������ ���ָ���
     * 
     * @param msgInfo
     * @param RefDataTime
     * @param nameList
     * @return
     * @throws BOException
     */
    private void importNewBaseMusicSingerData(HashMap existsNewMusicSinger,
                                        Integer newmusicSingermailInfo[],
                                        String[] nameList,
                                        StringBuffer checkFailureRowByNew, StringBuffer errorFailureRowByNew)
                    throws BOException
    {
        BaseMusicBO.getInstance().importNewBaseMusicSinger(existsNewMusicSinger,
        												newmusicSingermailInfo,
                                                     nameList,
                                                     checkFailureRowByNew, errorFailureRowByNew);
    }
    
    
    /**
     * ����ר������
     * 
     * @return
     */
    private int importAlbumMusicCate(HashMap existsNewMusicSinger,
                                       HashMap existsAlbumCate,
                                       Integer newmusicAlbummailInfo[],
                                       String categoryID, String[] fileName,
                                       StringBuffer checkFailureRowByLatest, StringBuffer errorFailureRowByLatest)
                    throws BOException
    {
        BaseMusicBO.getInstance()
                   .importNewBaseMusicAlbumCate(existsNewMusicSinger,
                		   					existsAlbumCate,
                		   					newmusicAlbummailInfo,
                                           categoryID,
                                           fileName,
                                           checkFailureRowByLatest,
                                           errorFailureRowByLatest);
        return 0;
    }
    
    /**
     * ����ר������������Ʒ
     * 
     * @return
     */
    private int importNewMusicRefrence(HashMap existsAlbum,
    		                              HashMap existsMusic,
                                       HashMap existsRefMusic,
                                       Integer[] newRefmailInfo,
                                        String[] fileName,
                                       StringBuffer checkFailureRowByLatest, StringBuffer errorFailureRowByLatest)
                    throws BOException
    {
        BaseMusicBO.getInstance()
                   .importNewMusicRefrence(existsAlbum,
                		                     existsMusic,
                                           existsRefMusic,
                                           newRefmailInfo,
                                          
                                           fileName,
                                           checkFailureRowByLatest,
                                           errorFailureRowByLatest);
        return 0;
    }

    /**
     * �ϼܰ�������Ʒ
     * 
     * @return
     */
    private int importChartsMusicRefrence(HashMap existsMusic,
                                          HashMap existsRefMusic,
                                          Integer[] charRefmailInfo,
                                          String categoryID, String[] fileName,
                                          StringBuffer checkFailureRowByUp,
                                          StringBuffer errorFailureRowByUp)
                    throws BOException
    {
        BaseMusicBO.getInstance()
                   .importCharNewMusicRefrence(existsMusic,
                                               existsRefMusic,
                                               charRefmailInfo,
                                               categoryID,
                                               fileName,
                                               checkFailureRowByUp,
                                               errorFailureRowByUp);

        return 0;
    }
    
    /**
     * ����������
     * 
     * @return
     */
    private int importToneBox(HashMap existsTonebox,
                                          Integer[] toneboxMailInfo,
                                          String[] toneboxLocalFileNames,
                                          StringBuffer checkFailureRowByNew,
                                          StringBuffer errorFailureRowByNew)
                    throws BOException
    {
        BaseMusicBO.getInstance()
                   .importToneBox(existsTonebox,
							toneboxMailInfo,
							toneboxLocalFileNames,
                            checkFailureRowByNew,
                            errorFailureRowByNew);

        return 0;
    }
    
    /**
     * ���������и���
     * 
     * @return
     */
    private int importToneBoxSong(HashMap existsToneboxSong,
                                          Integer[] toneboxSongMailInfo,
                                          String[] toneboxSongLocalFileNames,
                                          StringBuffer checkFailureRowByNew,
                                          StringBuffer errorFailureRowByNew)
                    throws BOException
    {
        BaseMusicBO.getInstance()
                   .importToneBoxSong(existsToneboxSong,
							toneboxSongMailInfo,
							toneboxSongLocalFileNames,
                            checkFailureRowByNew,
                            errorFailureRowByNew);

        return 0;
    }


    /**
     * �¼�ȫ������������Ʒ
     * 
     * @return
     */
    private int delInvalBMusicRef(Integer[] delRefmailInfo) throws BOException
    {
        // BaseMusicBO.getInstance().importCharMusicRefrence(existsMusic,existsRefMusic,charRefmailInfo,categoryID,fileName);
        BaseMusicBO.getInstance().delInvalBMusicRef(delRefmailInfo);
        return 0;
    }

    private int updateCategoryRefSum(Integer[] updatecrInfo) throws BOException
    {
        BaseMusicBO.getInstance().updateAllCategoryRefSum(updatecrInfo);
        return 0;
    }

    /**
     * �����ʼ�
     * 
     * @param mailContent,�ʼ�����
     */
    private void sendMail(String mailContent, String[] mailTo, String subject)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("sendMail(" + mailContent + ")");
        }
        // �õ��ʼ�����������
        // String[] mailTo = MailConfig.getInstance().getMailToArray();
        // String subject = "�����������ݵ���";
        if (logger.isDebugEnabled())
        {
            logger.debug("mailTo Array is:" + Arrays.asList(mailTo));
            logger.debug("mail subject is:" + subject);
            logger.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailTo);
    }

}
