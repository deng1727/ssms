package com.aspire.dotcard.a8;

import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.StringTool;

public class A8SingerImport extends A8DataImport
{
	private static final JLogger logger = LoggerFactory.getLogger(A8SingerImport.class);
	

	public A8SingerImport() throws BOException
	{
		super();
		// TODO Auto-generated constructor stub
	}

	void init()
	{
		this.ftpDir =A8ParameterConfig.A8SingerDir;
		this.localDir=getPathWithSlash(A8ParameterConfig.BackupDir)+A8ParameterConfig.A8SingerDir;
		this.title="A8歌手导入";
		String date = PublicUtil.getDateString(new Date(), "yyyyMMdd");
		this.regex="A8_singer_"+date+"\\.txt";

	}

	int insertDB(Object object)
	{
		SingerVO vo = ( SingerVO ) object;
        // 将新解析出来的全曲ID对应的记录从待删除记录列表中移除
        boolean result = dbList.remove(vo.getId());
        // 返回成功，说明对应的歌手在数据库中存在，需要更新
        if (result)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("歌手ID " + vo.getId() + " 存在于系统中，需要更新！");
            }
            return A8ImportDAO.getInstance().updateSinger(vo);
        }
        // 返回失败，说明对应的歌手记录在数据库中不存在，需要新增
        else
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("全曲ID " + vo.getId() + " 不在系统中，需要新增！");
            }
            return A8ImportDAO.getInstance().insertSinger(vo);
        }
	}

	Object transformVOByLineText(String line, int lineNumber)
	{
		if (logger.isDebugEnabled())
        {
            logger.debug("transformVOByLineText is starting.....");
        }
        String[] arrColorContent = line.split("\\|");
        int size = arrColorContent.length;
        if (size != 5)
        {
            logger.error(line + " 格式错误，此条歌手内容无法导入！");
            return null;
        }
        SingerVO vo = new SingerVO();
      //判断歌手ID是否只包括字母和数字
        if (!arrColorContent[0].matches("[0-9A-Za-z]{1,25}"))
        {
            logger.error("歌曲ID " + arrColorContent[0] + " 只能含有数字和字母，且最大不超过25个字符。此条内容无法导入！");
            return null;
        }
        if(StringTool.lengthOfHZ(arrColorContent[1])>50)
        {
        	logger.error("歌手名称"+arrColorContent[1]+"不能超过50个字符。此条内容无法导入");
        	return null;
        }
        if(StringTool.lengthOfHZ(arrColorContent[2])>40)
        {
        	logger.error("歌手地区"+arrColorContent[2]+"不能超过40个字符。此条内容无法导入");
        	return null;
        }
        if(StringTool.lengthOfHZ(arrColorContent[2])>40)
        {
        	logger.error("歌手类型"+arrColorContent[2]+"不能超过40个字符。此条内容无法导入");
        	return null;
        }
        if(StringTool.lengthOfHZ(arrColorContent[2])>50)
        {
        	logger.error("歌手首字母"+arrColorContent[2]+"不能超过50个字符。此条内容无法导入");
        	return null;
        }
        while(--size >= 0)
        {
        	if(size==3)
        	{
        		continue;//歌手类型可以为空
        	}
            if("".equals(arrColorContent[size].trim()))
            {
                logger.error(line + " 中有空字段，此条内容无法导入！");
                return null;
            }
        }
        // 设置VO的字段值，注意ID做了特别处理，前面加了字符串"a8"
        vo.setId(arrColorContent[0].trim());
        vo.setName(arrColorContent[1].trim());
        vo.setRegion(arrColorContent[2].trim());
        vo.setType(arrColorContent[3].trim());
        //新增组合属性，便于pps查询所用
        vo.setSingerZone(vo.getRegion()+vo.getType());
        vo.setFirstLetter(arrColorContent[4].trim());
        return vo;
	}

	List getDBDate() throws DAOException
	{
		return A8ImportDAO.getInstance().getAllSingerIdS();
	}

	void deleteFromDB(List list)
	{
		int result=A8ImportDAO.getInstance().deleteSinger(list);
		if(result==A8ParameterConfig.success_delete)
		{
			successDelete+=list.size();
		}else
		{
			failureCount=list.size();
		}
	}
	Object getVOKey(Object object)
	{
		return ((SingerVO)object).getId();
	}

}
