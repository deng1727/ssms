package com.aspire.ponaadmin.web.dataexport.channel.dao;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.channel.ChannelConfig;

/**
 * 取数据
 * @author x_zhailiqing
 *
 */
public class ThirdChannelMkDao {

	protected static JLogger logger = LoggerFactory
			.getLogger(ThirdChannelMkDao.class);

	private static ThirdChannelMkDao bd = new ThirdChannelMkDao();

	public static ThirdChannelMkDao getInstance() {
		return bd;
	}

	/**
	 * 查询www下货架商品
	 * @param all  所有
	 * @param free 免费
	 * @param pay 付费
	 * @param tb  先体验后付费
	 * @param categoryId
	 * @throws DAOException
	 */
	public void getWWWData(List all,List free,List pay,List tb,String categoryId) throws DAOException{
		ResultSet rs = null;
		String sqlCode = "dataexport.channel.ThirdChannelMkDao.getWwwAll";
		Object paras[] = new Object[]{categoryId,categoryId};
		int i=0,j=0,k = 0,l = 0;
		java.text.NumberFormat  formater  =  java.text.DecimalFormat.getInstance();  
		formater.setMaximumFractionDigits(2);  
		formater.setMinimumFractionDigits(2);  
		String type = null;
		String[] title = new String[]{"序号","内容ID","应用名称","产品类型","推荐指数","产品浏览量",
				"一级分类名称","二级分类名称","关键字","业务介绍",
				"适配机型列表","收费类型","下载资费","应用详情URL地址",
				"30*30图片地址","70*70 图片地址","135*180图片地址"};
		all.add(title);
		free.add(title);
		pay.add(title);
		tb.add(title);
		try{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()){
				String temp[] = new String[17];
				//序号
				temp[0] = ""+(++i);
				//内容id
				temp[1] = rs.getString("contentid");
				// 应用名称
				temp[2] = rs.getString("name");
				//产品类型（普通业务、自有业务和孵化应用）
				temp[3] = rs.getString("subtype");
				if("7".equals(temp[3])){
					temp[3] = "孵化应用";
				}else{
					String icpCode = rs.getString("icpcode");
					if(ChannelConfig.selfIcpCode.equals(icpCode)){
						temp[3] = "自有业务";
					}else{
						temp[3] = "普通业务";
					}					
				}
				//推荐指数				
				temp[4] = rs.getString("averagemark");
				//总下载量
				//浏览量
				temp[5] = rs.getString("scantimes");				
				//一级分类（名称）
				type = rs.getString("type");
				if("nt:gcontent:appGame".equals(type)){
					temp[6] = "游戏";
				}else if("nt:gcontent:appSoftWare".equals(type)){
					temp[6] = "软件";
				}else if("nt:gcontent:appTheme".equals(type)){
					temp[6] = "主题";
				}else{
					temp[6] = "";
				}
				//二级分类（名称）
				temp[7] = rs.getString("appcatename");
				//关键字
				temp[8] = rs.getString("keywords");
				//业务介绍
				temp[9] = rs.getString("introduction");
				//适配机型
				temp[10] = getClobString(rs.getClob("devicename"));
				//收费类型（免费、付费、先体验后付费）
				temp[11] = rs.getString("chargetime");
	        	int pr = rs.getInt("price");
	        	//产品费用（单位为元）
	        	temp[12] = formater.format((double)pr/1000);
	        	//应用详情URL地址：（WWW和WAP需要用各自的URL地址，参考下面的详情URL地址说明）
	        	temp[13] = "http://mm.10086.cn/portal/web/ad.do?adCode=817817&cntid="+temp[1];
	        	//30*30图片地址
	        	temp[14] = rs.getString("logo1");;
	        	//70*70 图片地址
	        	temp[15] = rs.getString("wwwpropapicture2");
	        	//135*180图片地址
	        	temp[16] = rs.getString("wwwpropapicture3");;
	        	all.add(temp);
        		if("2".equals(temp[11])){
        		    temp[11] = "先体验后付费";
        		    temp[12] = "0";
	        		String[] tmpTb = new String[17];
	        		System.arraycopy(temp, 0, tmpTb, 0, temp.length);
	        		tmpTb[0] = ""+(++l);
	        		tb.add(tmpTb);        		    
        		}else{
    	        	if(0==pr){
    	        		temp[11] = "免费";
    	        		temp[12] = "0";
    	        		String[] tmpFree = new String[17];
    	        		System.arraycopy(temp, 0, tmpFree, 0, temp.length);
    	        		tmpFree[0] = ""+(++j);
    	        		free.add(tmpFree);
    	        	}else{
    	        		if("nt:gcontent:appTheme".equals(type)&&pr>1000){
    	        		    temp[11] = "免费";
    	        		    temp[12] = "0";
    		        		String[] tmpFree = new String[17];
    		        		System.arraycopy(temp, 0, tmpFree, 0, temp.length);
    		        		tmpFree[0] = ""+(++j);
    		        		
    		        		free.add(tmpFree);    	        		    
    	        		}else{
    	        			temp[11] = "付费";
        	        		String[] tmpPay = new String[17];
        	        		System.arraycopy(temp, 0, tmpPay, 0, temp.length);
        	        		tmpPay[0] = ""+(++k);
        	        		pay.add(tmpPay);      	        			
    	        		}
    	        	}        			
        		}	        	
	        	
//	        	if(0==pr){
//	        		temp[11] = "免费";
//	        		String[] tmpFree = new String[14];
//	        		System.arraycopy(temp, 0, tmpFree, 0, temp.length);
//	        		tmpFree[0] = ""+(++j);
//	        		free.add(tmpFree);
//	        	}else{
//	        		if("nt:gcontent:appTheme".equals(type)&&pr>1){
//	        			//大于1元的主题按免费使用
//	        		    temp[11] = "先体验后付费";
//	        		}else{
//	        			temp[11] = "付费";
//	        		}
//	        		String[] tmpPay = new String[14];
//	        		System.arraycopy(temp, 0, tmpPay, 0, temp.length);
//	        		tmpPay[0] = ""+(++k);
//	        		pay.add(tmpPay);        		
//	        	}
			}
		}catch (Exception e){
			logger.error("查询WWW最新信息数据出错,"+e);
            throw new DAOException("查询WWW最新信息数据出错", e);			
		}finally{
			DB.close(rs);
		}
	}

	/**
	 * 查询wap下货架商品
	 * @param all
	 * @param free
	 * @param pay
	 * @param categoryId
	 * @throws DAOException
	 */
	public void getWapData(List all,List free,List pay,List tb,String categoryId) throws DAOException{
		ResultSet rs = null;
		String sqlCode = "dataexport.channel.ThirdChannelMkDao.getWapAll";
		Object paras[] = new Object[]{categoryId,categoryId};
		int i=0,j=0,k = 0,l=0;
		java.text.NumberFormat  formater  =  java.text.DecimalFormat.getInstance();  
		formater.setMaximumFractionDigits(2);  
		formater.setMinimumFractionDigits(2);		
		String type = null;
		String[] title = new String[]{"序号","内容ID","应用名称","产品类型","推荐指数","产品浏览量",
				"一级分类名称","二级分类名称","关键字","业务介绍",
				"适配机型列表","收费类型","下载资费","应用详情URL地址",
				"30*30图片地址","70*70 图片地址","135*180图片地址"};
		all.add(title);
		free.add(title);
		pay.add(title);
		tb.add(title);		
		try{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()){
				String temp[] = new String[17];
				//序号
				temp[0] = ""+(++i);
				//内容id
				temp[1] = rs.getString("contentid");
				// 应用名称
				temp[2] = rs.getString("name");
				//产品类型（普通业务、自有业务和孵化应用）
				temp[3] = rs.getString("subtype");
				if("7".equals(temp[3])){
					temp[3] = "孵化应用";
				}else{
					String icpCode = rs.getString("icpcode");
					if(ChannelConfig.selfIcpCode.equals(icpCode)){
						temp[3] = "自有业务";
					}else{
						temp[3] = "普通业务";
					}
				}
				//推荐指数				
				temp[4] = rs.getString("averagemark");
				//总下载量
				//浏览量
				temp[5] = rs.getString("scantimes");				
				//一级分类（名称）
				type = rs.getString("type");
				if("nt:gcontent:appGame".equals(type)){
					temp[6] = "游戏";
				}else if("nt:gcontent:appSoftWare".equals(type)){
					temp[6] = "软件";
				}else if("nt:gcontent:appTheme".equals(type)){
					temp[6] = "主题";
				}else{
					temp[6] = "";
				}
				//二级分类（名称）
				temp[7] = rs.getString("appcatename");
				//关键字
				temp[8] = rs.getString("keywords");
				//业务介绍
				temp[9] = rs.getString("introduction");
				//适配机型
				temp[10] = getClobString(rs.getClob("devicename"));
				//收费类型（免费、付费、先体验后付费）
				temp[11] = rs.getString("chargetime");
	        	int pr = rs.getInt("price");
	        	//产品费用（单位为元）
	        	temp[12] = formater.format((double)pr/1000);
	        	//应用详情URL地址：（WWW和WAP需要用各自的URL地址，参考下面的详情URL地址说明）
	        	temp[13] = "http://wap.mmarket.com/ad.do?adCode=817817&cntid="+temp[1];
	        	//30*30图片地址
	        	temp[14] = rs.getString("logo1");;
	        	//70*70 图片地址
	        	temp[15] = rs.getString("wwwpropapicture2");
	        	//135*180图片地址
	        	temp[16] = rs.getString("wwwpropapicture3");;
	        	all.add(temp);
        		if("2".equals(temp[11])){
        		    temp[11] = "先体验后付费";
        		    temp[12] = "0";
	        		String[] tmpTb = new String[17];
	        		System.arraycopy(temp, 0, tmpTb, 0, temp.length);
	        		tmpTb[0] = ""+(++l);
	        		tb.add(tmpTb);        		    
        		}else{
    	        	if(0==pr){
    	        		temp[11] = "免费";
    	        		temp[12] = "0";
    	        		String[] tmpFree = new String[17];
    	        		System.arraycopy(temp, 0, tmpFree, 0, temp.length);
    	        		tmpFree[0] = ""+(++j);
    	        		free.add(tmpFree);
    	        	}else{
    	        		if("nt:gcontent:appTheme".equals(type)&&pr>1000){
    	        		    temp[11] = "免费";
    	        		    temp[12] = "0";
    		        		String[] tmpFree = new String[17];
    		        		System.arraycopy(temp, 0, tmpFree, 0, temp.length);
    		        		tmpFree[0] = ""+(++j);
    		        		
    		        		free.add(tmpFree);    	        		    
    	        		}else{
    	        			temp[11] = "付费";
        	        		String[] tmpPay = new String[17];
        	        		System.arraycopy(temp, 0, tmpPay, 0, temp.length);
        	        		tmpPay[0] = ""+(++k);
        	        		pay.add(tmpPay);      	        			
    	        		}
    	        	}        			
        		}	  
			}
		}catch (Exception e){
			logger.error("查询WWW最新信息数据出错,"+e);
            throw new DAOException("查询WWW最新信息数据出错", e);			
		}finally{
			DB.close(rs);
		}
	}	
	
    private String getClobString(Clob clob) throws SQLException
    {

        if (clob == null)
        {
            return "";
        }
        long len = clob.length();
        return clob.getSubString(0, ( int ) len);
    }	
}
