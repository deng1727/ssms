package com.aspire.ponaadmin.web.queryapp.bo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.queryapp.dao.QueryAppDAO;
import com.aspire.ponaadmin.web.queryapp.vo.QueryAppVO;

public class QueryAppBO {
	private static QueryAppBO instance = new QueryAppBO();

    private QueryAppBO()
    {
    }

    /**
     * 得到单例模式
     * 
     */
    public static QueryAppBO getInstance()
    {

        return instance;
    }
    
    //查询MM异常崩溃列表
    public void queryQueryAddList(PageResult page, String contentid,String name, String beginDate,String endDate,String icpcode,String developer,String spname,String syntype,String catename,String id,String servattr,String keywords)

    {
    	QueryAppDAO.getInstance().queryQueryAddList(page,contentid, name, beginDate, endDate,icpcode,developer,spname,syntype,catename,id,servattr,keywords);
    }
	
  //查询MM异常崩溃列表
    public void queryQueryContentList(PageResult page, String contentid,String name, String beginDate,String endDate,String icpcode,String developer,String spname,String catename,String id,String servattr,String keywords)

    {
    	QueryAppDAO.getInstance().queryQueryContentList(page,contentid, name, beginDate, endDate,icpcode,developer,spname,catename,id,servattr,keywords);
    }
    
    
	 /**
     * 导出应用软件版本信息
     * @param request
     * @param wwb
     */
    public void exportQueryApp(HttpServletRequest request, WritableWorkbook wwb) {
    	String id = this.getParameter(request, "id").trim();
		String contentid = this.getParameter(request, "contentid").trim();
		String name = this.getParameter(request, "name").trim();
		String beginDate = this.getParameter(request, "beginDate").trim();
		String endDate = this.getParameter(request, "endDate").trim();
		String icpcode = this.getParameter(request, "icpcode").trim();
		String developer = this.getParameter(request, "developer").trim();
		String spname = this.getParameter(request, "spname").trim();
		String syntype = this.getParameter(request, "syntype").trim();
		String catename = this.getParameter(request, "catename").trim();
		
		String servattr = this.getParameter(request, "servattr").trim();
		String keywords = this.getParameter(request, "keywords").trim();
		
		String type = this.getParameter(request, "type");
		
		
    
        List list = null;
        String sheetName = "Sheet1";
        WritableSheet ws = wwb.createSheet(sheetName, 0); //创建sheet
        
        if ("add".equals(type)) {
        	list=QueryAppDAO.getInstance().queryAppAddList(contentid, name, beginDate, endDate, icpcode, developer, spname, syntype, catename, id, servattr, keywords);
            
		} else if ("content".equals(type)) {
			list=QueryAppDAO.getInstance().queryAppContentList(contentid, name, beginDate, endDate, icpcode, developer, spname, catename, id, servattr, keywords);
		}
        
        generateQueryAppExcel(list, ws,type);
    }
    
    /**
     * 从请求中获取参数，如果为null就返回空字符串""
     * @param request http请求
     * @param key 参数的关键字
     * @return 参数值
     */
    protected String getParameter (HttpServletRequest request,
                                   String key)
    {
        String value = request.getParameter(key) ;
        if (value == null)
        {
            value = "" ;
        }
        return value ;
    }
    
    /**
     * 生成应用更新版本的excel
     * 
     * @param list
     * @param wwb
     * @param sheetName
     */
    private void generateQueryAppExcel(List list, WritableSheet ws,String type)
    {

        Label label = null;
        int row = 0;
        int col = 0;
        try
        {
            label = new Label(col++, row, "应用ID");
            ws.addCell(label);
            label = new Label(col++, row, "应用名称");
            ws.addCell(label);
            label = new Label(col++, row, "内容ID");
            ws.addCell(label);
            label = new Label(col++, row, "SP企业代码");
            ws.addCell(label);
            label = new Label(col++, row, "提供商");
            ws.addCell(label);
            label = new Label(col++, row, "开发者");
            ws.addCell(label);
            label = new Label(col++, row, "业务内容分类");
            ws.addCell(label);
            label = new Label(col++, row, "上线时间");
            ws.addCell(label);
            label = new Label(col++, row, "最后更新时间");
            ws.addCell(label);
            label = new Label(col++, row, "价格(元)");
            ws.addCell(label);
            label = new Label(col++, row, "服务范围");
            ws.addCell(label);
            label = new Label(col++, row, "历史下载量");
            ws.addCell(label);
            label = new Label(col++, row, "内容标签");
            ws.addCell(label);
            label = new Label(col++, row, "应用简介");
            ws.addCell(label);
            
            if ("add".equals(type)) {
            	label = new Label(col++, row, "同步类型");
                ws.addCell(label);
    		} 
           
            for(int i=0;i<list.size();i++)
            {
            	QueryAppVO vo=(QueryAppVO)list.get(i);
                col = 0;
                row++;

                label = new Label(col++, row, vo.getId());
                ws.addCell(label);
                label = new Label(col++, row, vo.getName());
                ws.addCell(label);
                label = new Label(col++, row, vo.getContentid());
                ws.addCell(label);
                label = new Label(col++, row, vo.getIcpcode());
                ws.addCell(label);
                label = new Label(col++, row, vo.getDeveloper());
                ws.addCell(label);
//              MMC-1790
                label = new Label(col++, row, vo.getSpname());
                ws.addCell(label);
                label = new Label(col++, row, vo.getCatename());
                ws.addCell(label);
                label = new Label(col++, row, vo.getMarketdate());
                ws.addCell(label);
                label = new Label(col++, row, vo.getPlupddate());
                ws.addCell(label);
                label = new Label(col++, row, vo.getMobileprice());
                ws.addCell(label);
                
                String servattr=vo.getServattr();
                if("G".equals(servattr))
                {
                	servattr="全网业务";
                }
                else if("L".equals(servattr))
                {
                	servattr="省内业务";
                }
                	
                label = new Label(col++, row, servattr);
                ws.addCell(label);
                label = new Label(col++, row, vo.getOrderTimes());
                ws.addCell(label);
                label = new Label(col++, row, vo.getKeywords());
                ws.addCell(label);
                label = new Label(col++, row, vo.getIntroduction());
                ws.addCell(label);
                
                if ("add".equals(type)) {
                	String syntype=vo.getSyntype();
                	if("1".equals(syntype))
                	{
                		syntype="上线";
                	}
                	else if("2".equals(syntype))
                	{
                		syntype="更新";
                	}
                		
                	label = new Label(col++, row, syntype);
                    ws.addCell(label);
        		} 
                
            }
        }
        catch (RowsExceededException e)
        {
            e.printStackTrace();
        }
        catch (WriteException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
