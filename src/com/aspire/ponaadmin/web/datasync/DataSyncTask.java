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
	 * 任务应该在一个周期里面的第几天启动，比如一周中的周几，一月中的第几日
	 */
	private String startDay;
	
	private String frequency;
	
	private String [] mailTo;
	
	protected String fileEncoding;
	
    /**
     * 成功更新的个数
     */
    private int successUpdate=0;
    /**
     * 成功新增的个数
     */
    private int successAdd=0;
    /**
     * 成功删除的个数
     */
    private int successDelete=0;
    /**
     * 失败处理的个数
     */
    private int failureProcess=0;
    /**
     * 校验失败的个数
     */
    private int failureCheck=0;
    
    /**
     * 用来保存校验失败信息所在行
     */
    private StringBuffer checkFailureRow;
    /**
     * 用来保存处理失败信息所在行
     */
    private StringBuffer  dealFailureRow;
  
    
    /**
     * 用于邮件显示,任务开始执行时间。
     */
    public Date startDate;
    /**
     * 当前任务的状态。空闲和运行。
     */
    private int state=0;
    /**
     * 记录最后一次任务的执行结果。true 表示成功，false表示失败。
     */
    private boolean excuteResult=false;
    /**
     * 记录最后一次任务同步失败的原因。
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
		//初始化统计信息。
		this.successAdd = 0;
		this.successDelete = 0;
		this.successUpdate = 0;
		this.failureCheck=0;
		this.failureProcess=0;
        checkFailureRow = new StringBuffer();
        dealFailureRow = new StringBuffer();
		LOG.info("同步任务"+this.desc+"开始运行");
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
				reason="ftp下载数据文件出错:"+e.getMessage();
			}else if(e.getErrorCode()==DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED)
			{
				reason="没有找到"+this.getDesc()+"任务的数据文件";
			}else
			{
				reason=e.getMessage();
			}
			this.mailToAdmin(false,e.getErrorCode(),reason);
			LOG.error(reason,e);
			this.setExcuteResult(false);
			this.setFailureReason(reason);
		}catch(Throwable e)//所有类的异常
		{
			LOG.error(e);
			this.mailToAdmin(false,DataSyncConstants.EXCEPTION_INNER_ERR,e.getMessage());
			this.setExcuteResult(false);
			this.setFailureReason(e.getMessage());
		}
		this.setState(DataSyncConstants.TASK_FREE);
	}
	/**
	 * 处理具体任务逻辑
	 * @throws BOException 处理任务的时候出现异常。
	 */
	protected abstract void doTask()throws BOException;
	
	/**
	 * 发送结果邮件。子类可以重写该发送邮件方法。
	 * @param result 任务执行是否正常
	 * @param errorCode  出错代码。主要用于邮件标题显示。只有任务异常此参数才有意思
	 * @param reason 任务异常的具体原因，只有任务异常此参数才有意思
	 */
	protected void mailToAdmin(boolean result,int errorCode,String reason)
	{

		String mailTitle;
		// 发送邮件表示本次处理结束
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
				mailTitle= this.desc + "完成,没有需要导入的内容数据，详见邮件正文!!";//XX内容导入完成，没有需要导入的内容数据，详见邮件正文!!
			}else if(totalSuccessCount>0&&totalFailureCount==0)
			{
				mailTitle=this.desc + "成功,成功"+totalSuccessCount+"条，失败0条，详见邮件正文!";//XX内容导入成功，成功Y条，失败0条，详见邮件正文!
			}else
			{
				mailTitle=this.desc + "完成,成功"+totalSuccessCount+"条，失败"+totalFailureCount+"条，详见邮件正文!";//XX内容导入完成，成功Y条，失败Z条，详见邮件正文!
			}
			//mailTitle = this.desc + "成功";

			sb.append("开始时间：");
			sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",结束时间：");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("。<h4>处理结果：</h4>");
			sb.append("本次"+this.desc+"任务总共处理<b>");
			sb.append(totalCount);
			sb.append("</b>条。<p>成功处理<b>");
			sb.append(totalSuccessCount);
			sb.append("</b>条。");
			if(totalSuccessCount!=0)
			{
				sb.append("<br>具体为：成功新增");
				sb.append(successAdd);
				sb.append("条，成功更新");
				sb.append(successUpdate);
				sb.append("条，成功删除");
				sb.append(successDelete);
				sb.append("条");
			}
			sb.append("<p>失败处理<b>");
			sb.append(totalFailureCount);
			sb.append("</b>条。");
			if(totalFailureCount!=0)
			{
				sb.append("<br>具体为：失败校验");
				sb.append(this.failureCheck);
				sb.append("条，失败处理");
				sb.append(this.failureProcess);
				sb.append("条。");
			}
            
            if(this.failureCheck > 0)
            {
                sb.append("<br>失败校验所在行具体为：第");
                sb.append(checkFailureRow.substring(0, checkFailureRow.length()-2));
                sb.append("行。");
            }
            if(this.failureProcess > 0 && dealFailureRow.length()>2)
            {
                sb.append("<br>失败处理所在行具体为：第");
                sb.append(dealFailureRow.substring(0, dealFailureRow.length()-2));
                sb.append("行。");
            }
		}
		else
		{
			if(errorCode==DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED)
			{
				mailTitle=this.desc + "失败，没有取到正确的数据文件，详见邮件正文!";//XX内容导入失败，没有取到正确的数据文件，详见邮件正文!
			}else
			{
				mailTitle=this.desc + "失败，详见邮件正文!";
			}
			
			//mailTitle = this.desc + "失败";
			sb.append("开始时间：");
			sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",结束时间：");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("。<p>失败原因：<br>");
			sb.append(reason);

		}
		LOG.info(sb.toString());
		Mail.sendMail(mailTitle, sb.toString(),this.mailTo);
	}
	/**
	 * 增加统计信息。记录成功新增，更新，删除的统计信息。该方法是线程安全的。
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
     * 用于保存当前导入文件中行解析出错信息
     * 
     * @param rowNumber 所在行
     */
    synchronized public void addCheckFiledRow(int rowNumber)
    {
        checkFailureRow.append(rowNumber).append(", ");
    }
    /**
     * 用于保存当前导入文件中行解析出错信息
     * 
     * @param rowNumber 所在行
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
	 * 获取当前任务的状态。
	 * @return
	 */
	public int getState()
	{
		return state;
	}

	/**
	 * 设置任务当前的状态，目前只有空闲和运行两种状态。
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
	 * 增加成功删除个数。
	 * @param successDelete 本次新增的成功删除的个数
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
	 * 增加失败处理的个数。
	 * @param failureProcess 本次增加的失败删除的个数
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
