package com.aspire.ponaadmin.web.datasync.implement.game;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class GameChecker extends DataCheckerImp implements DataChecker
{
	private static JLogger logger = LoggerFactory.getLogger(DataSyncTaskForGame.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//首先需要先还原字符中含有‘0x1F’为‘\n’
		String field;
		for(int i=1;i<=record.size();i++)
		{
			field=revertCarriageReturn((String)record.get(i));
			record.put(i, field);
		}
		//bookId
		String tmp=(String)record.get(1);
		String icpservId=tmp;
		
		logger.info("开始验证游戏字段格式，icpservId="+icpservId);
			
		if(record.size()!=27 && record.size()!=28)
		{
			logger.error("字段数不等于27并且也不等于28,该条数据的字段数为："+record.size());
			return DataSyncConstants.CHECK_FAILED;
		}
        
		if(!this.checkFieldLength("产品归属cp", tmp, 16, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//2 cpName
		tmp=(String)record.get(2);
		if(!this.checkFieldLength("cp名称", tmp, 200, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//3 cpServiceId
		tmp=(String)record.get(3);
		if(!this.checkFieldLength("产品的业务代码", tmp, 16, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//4 serviceName
		tmp=(String)record.get(4);
		if(!this.checkFieldLength("产品名称", tmp, 400, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//5 serviceShotName
		tmp=(String)record.get(5);
		if(!this.checkFieldLength("产品简称", tmp, 400, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//6 serviceDesc
		tmp=(String)record.get(6);
		if(!this.checkFieldLength("产品简介", tmp, 1000, false))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//7 operationDesc
		tmp=(String)record.get(7);
		if(!this.checkFieldLength(" ", tmp, 1000, false))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//8 业务类型
		tmp=(String)record.get(8);
		if(!this.checkIntegerField("业务类型", tmp, 2, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//9 支付方式
		tmp=(String)record.get(9);
		if(!this.checkIntegerField("支付方式",tmp, 2, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//10 产品生效时间。
		tmp=(String)record.get(10);
		if(!this.checkFieldLength("产品生效时间", tmp, 10, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//12 业务状态
		tmp=(String)record.get(12);
		if(!this.checkIntegerField("业务状态", tmp, 1, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//13 资费
		tmp=(String)record.get(13);
		if(!this.checkIntegerField("资费", tmp, 10, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//14 资费描述
		tmp=(String)record.get(14);
		if(!this.checkFieldLength("资费描述", tmp, 400, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//15 产品logo
		tmp=(String)record.get(15);
		if(!this.checkFieldLength("产品logo", tmp, 200, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//16 下载URL
		tmp=(String)record.get(16);
		if(!this.checkFieldLength("下载URL", tmp, 400, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//17 支持的终端类型
		tmp=(String)record.get(17);
		if(!this.checkFieldLength("支持的终端类型", tmp, 400, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//18 计费方式
		tmp=(String)record.get(18);
		//目前只提供值为2/3/5的。其他的忽略。
		if(!("5".equals(tmp)||"2".equals(tmp)||"3".equals(tmp)))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		/*if(!this.checkIntegerField("计费方式", tmp, 2, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}*/
		//20 游戏分类名称
		tmp=(String)record.get(20);
		if(!this.checkFieldLength("游戏分类名称", tmp, 40, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//21 业务标识
		tmp=(String)record.get(21);
		if(!"0".equals(tmp))//只有为0的业务才可以。
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//22 业务推广方式
		tmp=(String)record.get(22);
		if(!this.checkIntegerField("业务推广方式", tmp, 2, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//23  游戏下载量。
		tmp=(String)record.get(23);
		if(!this.checkIntegerField("游戏下载量", tmp, 10, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}//24  特价资费。
		tmp=(String)record.get(24);
		if(!this.checkIntegerField("特价资费", tmp, 10, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
        
        // 25 上线时间。
        tmp = ( String ) record.get(25);
        if (!this.checkFieldLength("上线时间", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 26 上线天数。
        tmp = ( String ) record.get(26);
        if (!this.checkIntegerField("上线天数", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // 新接口
        if(record.size() == 28)
        {
            //  28 首发类型
            tmp = ( String ) record.get(28);
            if (!this.checkFieldLength("首发类型", tmp, 5, true))
            {
                return DataSyncConstants.CHECK_FAILED;
            }
        }
        
		return DataSyncConstants.CHECK_SUCCESSFUL;
		
	}

	public void init(DataSyncConfig config) throws Exception
	{

	}
	/**
	 * 还原‘\n’字符. 原有字符的‘\n'已经被’字符0x1F 字符替换了
	 * @param field  carriageReturn 
	 * @return
	 */
	private String revertCarriageReturn(String field)
	{
		return field.replace((char)0x1F, '\n'); 
	}

}
