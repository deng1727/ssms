package com.aspire.ponaadmin.web.datasync;


/**
 * ȫ��ͬ����һ�δ������ļ���
 * ���������������а�ͬ���Ĵ���
 * @author shidr
 *
 */
public class DataSyncTask02 extends DataSyncTask
{
	private FtpProcessor ftp;
	private DataReader dataReader;
	private DataChecker dataChecker;
	private DataDealer dataDealer;
	//private static JLogger LOG=LoggerFactory.getLogger(DataSyncTask02.class);
	
	public void init(DataSyncConfig config) throws Exception
	{
		ftp.init(config);
		dataReader.init(config);
		dataChecker.init(config);
		dataDealer.init(config);
	}
	
	public void doTask()
	{
		/*String[] filenameList = null;
		try
		{
			ftp.process();
		}
		catch(Exception e)
		{
			LOG.error(e);
			//��ȡ�ļ�ʧ�ܣ����͸澯�ʼ����˳���
			return;
		}
        String lineText = null;
        BufferedReader reader = null;
        for(int i=0;i<filenameList.length;i++)
        {
            // �����ļ��ɹ����������
            int sCount = 0;
            int lineNumeber = 0;
            try
            {
                reader = new BufferedReader(new FileReader(filenameList[0]));
                while ((lineText = reader.readLine()) != null)
                {
                	DataRecord dr = dataReader.readDataRecord(lineText);
                	if(dataChecker!=null)
                	{
                    	int checkResult = dataChecker.checkDateRecord(dr);
                	}
                	dataDealer.dealDataRecrod(dr);
                    lineNumeber++;
                }
            }
            catch (Exception e)
            {

            }
            finally
            {
                try
                {
                    if (reader != null)
                    {
                        reader.close();
                    }
                }
                catch (IOException e)
                {
                }
            }
        }*/
	}

}
