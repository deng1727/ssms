package com.aspire.ponaadmin.web.datasync;


/**
 * 全量同步、一次处理多个文件。
 * 适用于类似于排行榜同步的处理。
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
			//获取文件失败，发送告警邮件并退出。
			return;
		}
        String lineText = null;
        BufferedReader reader = null;
        for(int i=0;i<filenameList.length;i++)
        {
            // 用于文件成功处理的行数
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
