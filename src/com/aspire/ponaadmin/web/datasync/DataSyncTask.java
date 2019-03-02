package com.aspire.ponaadmin.web.datasync;

import java.util.Date;
import java.util.TimerTask;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

public abstract class DataSyncTask extends TimerTask implements DataSyncBuilder
{
	private static JLogger LOG=LoggerFactory.getLogger(DataSyncTask.class);
	
	private String name;
	
	private String desc;
	
	private String startTime;
	
	/**
	 * ����Ӧ����һ����������ĵڼ�������������һ���е��ܼ���һ���еĵڼ���
	 */
	private String startDay;
	
	private String frequency;
	
	private String [] mailTo;
	
	protected String fileEncoding;
	
    /**
     * �ɹ����µĸ���
     */
    private int successUpdate=0;
    /**
     * �ɹ������ĸ���
     */
    private int successAdd=0;
    /**
     * �ɹ�ɾ���ĸ���
     */
    private int successDelete=0;
    /**
     * ʧ�ܴ���ĸ���
     */
    private int failureProcess=0;
    /**
     * У��ʧ�ܵĸ���
     */
    private int failureCheck=0;
    
    /**
     * ��������У��ʧ����Ϣ������
     */
    private StringBuffer checkFailureRow;
    /**
     * �������洦��ʧ����Ϣ������
     */
    private StringBuffer  dealFailureRow;
  
    
    /**
     * �����ʼ���ʾ,����ʼִ��ʱ�䡣
     */
    public Date startDate;
    /**
     * ��ǰ�����״̬�����к����С�
     */
    private int state=0;
    /**
     * ��¼���һ�������ִ�н����true ��ʾ�ɹ���false��ʾʧ�ܡ�
     */
    private boolean excuteResult=false;
    /**
     * ��¼���һ������ͬ��ʧ�ܵ�ԭ��
     */
    private String failureReason;
    
	
	public void init(DataSyncConfig config) throws Exception
	{
		this.name = config.get("task.name");
		this.desc=config.get("task.desc");
		this.frequency = config.get("task.frequency");
		this.startDay=config.get("task.start-day");
		this.startTime = config.get("task.start-time");
		this.mailTo=config.get("task.mail_to").split(",");
		this.fileEncoding=config.get("task.file-encoding");
	}
	
	public void run()
	{	
		this.setState(DataSyncConstants.TASK_RUNNING);
		this.setExcuteResult(false);
		this.setFailureReason(null);
		
		startDate = new Date();
		//��ʼ��ͳ����Ϣ��
		this.successAdd = 0;
		this.successDelete = 0;
		this.successUpdate = 0;
		this.failureCheck=0;
		this.failureProcess=0;
        checkFailureRow = new StringBuffer();
        dealFailureRow = new StringBuffer();
		LOG.info("ͬ������"+this.desc+"��ʼ����");
		try
		{
			doTask();
			this.mailToAdmin(true,0,null);
			this.setExcuteResult(true);
		} catch (BOException e)
		{
			String reason;
			if(e.getErrorCode()==DataSyncConstants.EXCEPTION_FTP)
			{
				reason="ftp���������ļ�����:"+e.getMessage();
			}else if(e.getErrorCode()==DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED)
			{
				reason="û���ҵ�"+this.getDesc()+"����������ļ�";
			}else
			{
				reason=e.getMessage();
			}
			this.mailToAdmin(false,e.getErrorCode(),reason);
			LOG.error(reason,e);
			this.setExcuteResult(false);
			this.setFailureReason(reason);
		}catch(Throwable e)//��������쳣
		{
			LOG.error(e);
			this.mailToAdmin(false,DataSyncConstants.EXCEPTION_INNER_ERR,e.getMessage());
			this.setExcuteResult(false);
			this.setFailureReason(e.getMessage());
		}
		this.setState(DataSyncConstants.TASK_FREE);
	}
	/**
	 * ������������߼�
	 * @throws BOException ���������ʱ������쳣��
	 */
	protected abstract void doTask()throws BOException;
	
	/**
	 * ���ͽ���ʼ������������д�÷����ʼ�������
	 * @param result ����ִ���Ƿ�����
	 * @param errorCode  ������롣��Ҫ�����ʼ�������ʾ��ֻ�������쳣�˲���������˼
	 * @param reason �����쳣�ľ���ԭ��ֻ�������쳣�˲���������˼
	 */
	protected void mailToAdmin(boolean result,int errorCode,String reason)
	{

		String mailTitle;
		// �����ʼ���ʾ���δ������
		Date endDate = new Date();
		StringBuffer sb = new StringBuffer();
		int totalSuccessCount=this.successAdd+this.successUpdate+this.successDelete;
		int totalFailureCount=this.failureCheck+this.failureProcess;
		int totalCount=totalSuccessCount+totalFailureCount;
		if (result)
		{
			// MailUtil.sen
			if(totalSuccessCount==0&&totalFailureCount==0)
			{
				mailTitle= this.desc + "���,û����Ҫ������������ݣ�����ʼ�����!!";//XX���ݵ�����ɣ�û����Ҫ������������ݣ�����ʼ�����!!
			}else if(totalSuccessCount>0&&totalFailureCount==0)
			{
				mailTitle=this.desc + "�ɹ�,�ɹ�"+totalSuccessCount+"����ʧ��0��������ʼ�����!";//XX���ݵ���ɹ����ɹ�Y����ʧ��0��������ʼ�����!
			}else
			{
				mailTitle=this.desc + "���,�ɹ�"+totalSuccessCount+"����ʧ��"+totalFailureCount+"��������ʼ�����!";//XX���ݵ�����ɣ��ɹ�Y����ʧ��Z��������ʼ�����!
			}
			//mailTitle = this.desc + "�ɹ�";

			sb.append("��ʼʱ�䣺");
			sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",����ʱ�䣺");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("��<h4>��������</h4>");
			sb.append("����"+this.desc+"�����ܹ�����<b>");
			sb.append(totalCount);
			sb.append("</b>����<p>�ɹ�����<b>");
			sb.append(totalSuccessCount);
			sb.append("</b>����");
			if(totalSuccessCount!=0)
			{
				sb.append("<br>����Ϊ���ɹ�����");
				sb.append(successAdd);
				sb.append("�����ɹ�����");
				sb.append(successUpdate);
				sb.append("�����ɹ�ɾ��");
				sb.append(successDelete);
				sb.append("��");
			}
			sb.append("<p>ʧ�ܴ���<b>");
			sb.append(totalFailureCount);
			sb.append("</b>����");
			if(totalFailureCount!=0)
			{
				sb.append("<br>����Ϊ��ʧ��У��");
				sb.append(this.failureCheck);
				sb.append("����ʧ�ܴ���");
				sb.append(this.failureProcess);
				sb.append("����");
			}
            
            if(this.failureCheck > 0)
            {
                sb.append("<br>ʧ��У�������о���Ϊ����");
                sb.append(checkFailureRow.substring(0, checkFailureRow.length()-2));
                sb.append("�С�");
            }
            if(this.failureProcess > 0 && dealFailureRow.length()>2)
            {
                sb.append("<br>ʧ�ܴ��������о���Ϊ����");
                sb.append(dealFailureRow.substring(0, dealFailureRow.length()-2));
                sb.append("�С�");
            }
		}
		else
		{
			if(errorCode==DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED)
			{
				mailTitle=this.desc + "ʧ�ܣ�û��ȡ����ȷ�������ļ�������ʼ�����!";//XX���ݵ���ʧ�ܣ�û��ȡ����ȷ�������ļ�������ʼ�����!
			}else
			{
				mailTitle=this.desc + "ʧ�ܣ�����ʼ�����!";
			}
			
			//mailTitle = this.desc + "ʧ��";
			sb.append("��ʼʱ�䣺");
			sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",����ʱ�䣺");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("��<p>ʧ��ԭ��<br>");
			sb.append(reason);

		}
		LOG.info(sb.toString());
		Mail.sendMail(mailTitle, sb.toString(),this.mailTo);
	}
	/**
	 * ����ͳ����Ϣ����¼�ɹ����������£�ɾ����ͳ����Ϣ���÷������̰߳�ȫ�ġ�
	 * @param flag
	 */
	synchronized public void addStatisticCount(int flag)
	{
		switch(flag)
		{
			case DataSyncConstants.SUCCESS_ADD:this.successAdd++;break;
			case DataSyncConstants.SUCCESS_UPDATE:this.successUpdate++;break;
			case DataSyncConstants.SUCCESS_DEL:this.successDelete++;break;
			case DataSyncConstants.CHECK_FAILED:this.failureCheck++;break;
			default:this.failureProcess++;
		}
	}
    
    /**
     * ���ڱ��浱ǰ�����ļ����н���������Ϣ
     * 
     * @param rowNumber ������
     */
    synchronized public void addCheckFiledRow(int rowNumber)
    {
        checkFailureRow.append(rowNumber).append(", ");
    }
    /**
     * ���ڱ��浱ǰ�����ļ����н���������Ϣ
     * 
     * @param rowNumber ������
     */
    synchronized public void addDealFiledRow(int rowNumber)
    {
        dealFailureRow.append(rowNumber).append(", ");
    }
	public String getName() 
	{
		return name;
	}
	public String getDesc() 
	{
		return desc;
	}

	public String getStartTime() 
	{
		return startTime;
	}

	public String getStartDay() 
	{
		return startDay;
	}

	public String getFrequency() 
	{
		return frequency;
	}
	public String[] getMailTo() 
	{
		return mailTo;
	}	

	/**
	 * ��ȡ��ǰ�����״̬��
	 * @return
	 */
	public int getState()
	{
		return state;
	}

	/**
	 * ��������ǰ��״̬��Ŀǰֻ�п��к���������״̬��
	 * @param state
	 */
	private void setState(int state)
	{
		this.state = state;
	}

	public boolean isExcuteResult()
	{
		return excuteResult;
	}

	public boolean getExcuteResult()
	{
		return excuteResult;
	}

	private void setExcuteResult(boolean excuteResult)
	{
		this.excuteResult = excuteResult;
	}

	public String getFailureReason()
	{
		return failureReason;
	}

	private void setFailureReason(String failureReason)
	{
		this.failureReason = failureReason;
	}

	synchronized public int getSuccessUpdate()
	{
		return successUpdate;
	}

	synchronized public int getSuccessAdd()
	{
		return successAdd;
	}

	synchronized public int getSuccessDelete()
	{
		return successDelete;
	}

	/**
	 * ���ӳɹ�ɾ��������
	 * @param successDelete ���������ĳɹ�ɾ���ĸ���
	 */
	synchronized public void addSuccessDelete(int successDelete)
	{
		this.successDelete += successDelete;
	}

	synchronized public int getFailureProcess()
	{
		return failureProcess;
	}
	/**
	 * ����ʧ�ܴ���ĸ�����
	 * @param failureProcess �������ӵ�ʧ��ɾ���ĸ���
	 */
	synchronized public void addFailureProcess(int failureProcess)
	{
		this.failureProcess += failureProcess;
	}

	synchronized public int getFailureCheck()
	{
		return failureCheck;
	}

}
