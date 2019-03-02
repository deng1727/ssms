package com.aspire.ponaadmin.web.datasync.implement.game;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class TwGameChecker extends DataCheckerImp implements DataChecker
{
	private static JLogger logger = LoggerFactory.getLogger(TwGameChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//首先需要先还原字符中含有‘0x1F’为‘\n’
		String field;
		for(int i=1;i<=record.size();i++)
		{
			field=revertCarriageReturn((String)record.get(i));
			record.put(i, field);
		}
        
		// cpid
		String tmp=(String)record.get(1);
		String cpid=tmp;
		
		logger.info("开始验证图文游戏字段格式，cpid="+cpid);
			
		if(record.size()!=20)
		{
			logger.error("字段数不等于20,该条数据的字段数为："+record.size());
			return DataSyncConstants.CHECK_FAILED;
		}
        
		// 1 cpid
        tmp=(String)record.get(1);
        if(!this.checkFieldLength("产品归属的CP", tmp, 16, true))
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
		if(!this.checkFieldLength("操作简介", tmp, 1000, false))
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
        //11 产品失效日期
        tmp=(String)record.get(11);
        if(!this.checkFieldLength("产品失效时间", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
		//12 业务状态
		tmp=(String)record.get(12);
		if(!this.checkIntegerField("业务状态", tmp, 1, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
        // 目前只支持3:商用类型的业务状态 
        if(!"3".equals(tmp))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
		//13 资费
		tmp=(String)record.get(13);
		if(!this.checkIntegerField("资费", tmp, 10, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//14 servicefeedesc 资费描述
		tmp=(String)record.get(14);
		if(!this.checkFieldLength("资费描述", tmp, 400, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//15 service_url 图文游戏的链接地址
		tmp=(String)record.get(15);
		if(!this.checkFieldLength("图文游戏的链接地址", tmp, 400, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//16 计费方式
		tmp=(String)record.get(16);
		if(!this.checkIntegerField("计费方式", tmp, 2, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
        // 目前只支持0\3\6三种类型的计费方式  
        if(!"0".equals(tmp) && !"3".equals(tmp) && !"6".equals(tmp))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
		//17 游戏分类
		tmp=(String)record.get(17);
        if(!this.checkIntegerField("游戏分类", tmp, 4, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
		//18 游戏分类名称
		tmp=(String)record.get(18);
		if(!this.checkFieldLength("游戏分类名称", tmp, 40, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//19 业务标识
		tmp=(String)record.get(19);
        if(!this.checkIntegerField("业务推广方式", tmp, 1, false))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        if(!"".equals(tmp) && (!"0".equals(tmp) && !"1".equals(tmp)))
        {
            // 只有为0和为1的业务才可以。
            return DataSyncConstants.CHECK_FAILED;
        }
        
		//20 业务推广方式
		tmp=(String)record.get(20);
		if(!this.checkIntegerField("业务推广方式", tmp, 2, true))
		{
			return DataSyncConstants.CHECK_FAILED;
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
