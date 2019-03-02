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
     * �õ�����ģʽ
     * 
     */
    public static QueryAppBO getInstance()
    {

        return instance;
    }
    
    //��ѯMM�쳣�����б�
    public void queryQueryAddList(PageResult page, String contentid,String name, String beginDate,String endDate,String icpcode,String developer,String spname,String syntype,String catename,String id,String servattr,String keywords)

    {
    	QueryAppDAO.getInstance().queryQueryAddList(page,contentid, name, beginDate, endDate,icpcode,developer,spname,syntype,catename,id,servattr,keywords);
    }
	
  //��ѯMM�쳣�����б�
    public void queryQueryContentList(PageResult page, String contentid,String name, String beginDate,String endDate,String icpcode,String developer,String spname,String catename,String id,String servattr,String keywords)

    {
    	QueryAppDAO.getInstance().queryQueryContentList(page,contentid, name, beginDate, endDate,icpcode,developer,spname,catename,id,servattr,keywords);
    }
    
    
	 /**
     * ����Ӧ������汾��Ϣ
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
        WritableSheet ws = wwb.createSheet(sheetName, 0); //����sheet
        
        if ("add".equals(type)) {
        	list=QueryAppDAO.getInstance().queryAppAddList(contentid, name, beginDate, endDate, icpcode, developer, spname, syntype, catename, id, servattr, keywords);
            
		} else if ("content".equals(type)) {
			list=QueryAppDAO.getInstance().queryAppContentList(contentid, name, beginDate, endDate, icpcode, developer, spname, catename, id, servattr, keywords);
		}
        
        generateQueryAppExcel(list, ws,type);
    }
    
    /**
     * �������л�ȡ���������Ϊnull�ͷ��ؿ��ַ���""
     * @param request http����
     * @param key �����Ĺؼ���
     * @return ����ֵ
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
     * ����Ӧ�ø��°汾��excel
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
            label = new Label(col++, row, "Ӧ��ID");
            ws.addCell(label);
            label = new Label(col++, row, "Ӧ������");
            ws.addCell(label);
            label = new Label(col++, row, "����ID");
            ws.addCell(label);
            label = new Label(col++, row, "SP��ҵ����");
            ws.addCell(label);
            label = new Label(col++, row, "�ṩ��");
            ws.addCell(label);
            label = new Label(col++, row, "������");
            ws.addCell(label);
            label = new Label(col++, row, "ҵ�����ݷ���");
            ws.addCell(label);
            label = new Label(col++, row, "����ʱ��");
            ws.addCell(label);
            label = new Label(col++, row, "������ʱ��");
            ws.addCell(label);
            label = new Label(col++, row, "�۸�(Ԫ)");
            ws.addCell(label);
            label = new Label(col++, row, "����Χ");
            ws.addCell(label);
            label = new Label(col++, row, "��ʷ������");
            ws.addCell(label);
            label = new Label(col++, row, "���ݱ�ǩ");
            ws.addCell(label);
            label = new Label(col++, row, "Ӧ�ü��");
            ws.addCell(label);
            
            if ("add".equals(type)) {
            	label = new Label(col++, row, "ͬ������");
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
                	servattr="ȫ��ҵ��";
                }
                else if("L".equals(servattr))
                {
                	servattr="ʡ��ҵ��";
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
                		syntype="����";
                	}
                	else if("2".equals(syntype))
                	{
                		syntype="����";
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
