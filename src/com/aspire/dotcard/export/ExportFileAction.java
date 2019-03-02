package com.aspire.dotcard.export;

import java.net.SocketException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.searchfile.SearchFileConstants;
import com.aspire.dotcard.searchfile.SearchFileGenerateBO;
import com.aspire.dotcard.searchfile.SearchFileGenerateCYBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.dataexport.basefile.impl.TempExigenceFile;
import com.aspire.ponaadmin.web.dataexport.marketing.AppTopExport;
import com.aspire.ponaadmin.web.dataexport.marketing.CommonAppExport;
import com.aspire.ponaadmin.web.dataexport.marketing.DataExporter;
import com.aspire.ponaadmin.web.dataexport.marketing.DeviceTopAppExport;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * 导出文件的action
 *
 * @author xiaozhijun
 *
 */
public class ExportFileAction extends BaseAction
{

	/**
	 * 记录日志的实例对象
	 */
	private static JLogger log = LoggerFactory.getLogger(ExportFileAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		log.debug("begin  ExportFileAction");
		String fileType = this.getParameter(request, "exportType");
		String forward;
		if(fileType.equals("content"))
		{
			forward=exportContent(mapping,form,request,response);
		}else if(fileType.equals("search"))
		{
			forward=exportSearchFile(mapping,form,request,response);
		}else if(fileType.equals("market"))
		{
			forward=exportMarketing(mapping,form,request,response);
		}
		else
		{
			forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessages(request, "没有此导出类型");
			//this.actionLog(request, "内容数据导出", exportInfo, false, "");
		}
		

		return mapping.findForward(forward);
	}
	
	private String exportContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String fileType = request.getParameter("fileType");
		// 设置导出的文件的文件名
		String exportName = ContentExportBO.getInstance().getExportFileName(fileType);
		log.debug("exportName=" + exportName);

		// 设置导出配置项
		TypeExportConfig config = ContentExportBO.getInstance().getExportConfig(fileType);

		// 设置导出标识信息
		String exportInfo = ContentExportBO.getInstance().getExportInfo(fileType);

		// 设置下载方式
		response.setContentType("application/vnd.ms-excel;charset=GBK");

		// 设置默认保存的文件名
		response.setHeader("Content-disposition", "attachment; filename=" + exportName);

		try
		{
			ContentExportBO.getInstance().ExportDate(config, response.getOutputStream());
		} catch (SocketException e)
		{
			//解决在用户点击下载，选择取消时出现的异常
			return null;
			//return mapping.findForward(null);
		} catch (Exception e)
		{
			forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessages(request, exportInfo + "操作失败");
			this.actionLog(request, "内容数据导出", exportInfo, false, "");
			log.error(e);

		}

		this.actionLog(request, "内容数据导出", exportInfo, true, "");

		log.debug("end  ExportFileAction");
		return forward;
	}
	private String exportSearchFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String fileType = this.getParameter(request, "fileType");
		
		String actionType="搜索数据导出";
		String actionTarget="";
		
		// 设置导出的文件的文件名
		try
		{
			if(fileType.equals("software"))
			{

//				actionTarget="软件类型数据导出";
//				SearchFileGenerateBO.getInstance().prepareAllData();
//				SearchFileGenerateBO.getInstance().generateSearchFile("软件",RepositoryConstants.TYPE_APPSOFTWARE,SearchFileConstants.RELATION_W, "G","appSoftWare.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("软件",RepositoryConstants.TYPE_APPSOFTWARE,SearchFileConstants.RELATION_W, "L", "appSoftWare.txt");
//
//				SearchFileGenerateBO.getInstance().generateSearchFile("软件",RepositoryConstants.TYPE_APPSOFTWARE,SearchFileConstants.RELATION_A, "G","appSoftWare.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("软件",RepositoryConstants.TYPE_APPSOFTWARE,SearchFileConstants.RELATION_A, "L", "appSoftWare.txt");
//
//				SearchFileGenerateBO.getInstance().generateSearchFile("软件",RepositoryConstants.TYPE_APPSOFTWARE,SearchFileConstants.RELATION_O, "G","appSoftWare.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("软件",RepositoryConstants.TYPE_APPSOFTWARE,SearchFileConstants.RELATION_O, "L", "appSoftWare.txt");
			}else if(fileType.equals("game"))
			{

//				actionTarget="游戏类型数据导出";
//				SearchFileGenerateBO.getInstance().prepareAllData();
//				SearchFileGenerateBO.getInstance().generateSearchFile("游戏",RepositoryConstants.TYPE_APPGAME,SearchFileConstants.RELATION_W,"G", "appGame.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("游戏",RepositoryConstants.TYPE_APPGAME,SearchFileConstants.RELATION_W, "L", "appGame.txt");
//				
//				SearchFileGenerateBO.getInstance().generateSearchFile("游戏",RepositoryConstants.TYPE_APPGAME,SearchFileConstants.RELATION_A,"G", "appGame.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("游戏",RepositoryConstants.TYPE_APPGAME,SearchFileConstants.RELATION_A, "L", "appGame.txt");
//
//				SearchFileGenerateBO.getInstance().generateSearchFile("游戏",RepositoryConstants.TYPE_APPGAME,SearchFileConstants.RELATION_O,"G", "appGame.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("游戏",RepositoryConstants.TYPE_APPGAME,SearchFileConstants.RELATION_O, "L", "appGame.txt");

			}else if(fileType.equals("theme"))
			{
//				actionTarget="主题类型数据导出";
//				SearchFileGenerateBO.getInstance().prepareAllData();
//				SearchFileGenerateBO.getInstance().generateSearchFile("主题",RepositoryConstants.TYPE_APPTHEME,SearchFileConstants.RELATION_W,"G", "appTheme.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("主题",RepositoryConstants.TYPE_APPTHEME,SearchFileConstants.RELATION_W, "L", "appTheme.txt");
//		        
//				SearchFileGenerateBO.getInstance().generateSearchFile("主题",RepositoryConstants.TYPE_APPTHEME,SearchFileConstants.RELATION_A,"G", "appTheme.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("主题",RepositoryConstants.TYPE_APPTHEME,SearchFileConstants.RELATION_A, "L", "appTheme.txt");
//		     
//				SearchFileGenerateBO.getInstance().generateSearchFile("主题",RepositoryConstants.TYPE_APPTHEME,SearchFileConstants.RELATION_O,"G", "appTheme.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("主题",RepositoryConstants.TYPE_APPTHEME,SearchFileConstants.RELATION_O, "L", "appTheme.txt");
			}else if(fileType.equals("audio"))
			{
				actionTarget="A8全曲类型数据导出";
				SearchFileGenerateBO.getInstance().prepareDataForO();
				SearchFileGenerateBO.getInstance().generateSearchFile("A8全曲",RepositoryConstants.TYPE_AUDIO,"audio.txt");			
				
			}else if(fileType.equals("book"))
			{
				actionTarget="广东类型数据导出";
				SearchFileGenerateBO.getInstance().prepareDataForO();
				SearchFileGenerateBO.getInstance().generateSearchFile("广东图书","nt:gcontent:book", "book.txt");
				
			}else if(fileType.equals("colorring"))
			{
				actionTarget="彩铃类型数据导出";
				SearchFileGenerateBO.getInstance().prepareDataForO();
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("彩铃","nt:gcontent:colorring", "colorring.txt");
				
			}
			//removed by aiyan 2012-10-24
//				else if(fileType.equals("comic"))
//			{
//				actionTarget="动漫类型数据导出";
//				SearchFileGenerateBO.getInstance().prepareDataForO();
//				SearchFileGenerateBO.getInstance().generateSearchFile("动漫","nt:gcontent:comic", "comic.txt");
//				
//			}
			else if(fileType.equals("music"))
			{
				actionTarget="基地音乐类型数据导出";
				//SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("基地音乐","nt:gcontent:music", "music_old.csv");
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("基地音乐","nt:gcontent:music", "music.txt");
			}else if(fileType.equals("read"))
			{
				actionTarget="基地读书类型数据导出";
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("基地读书","nt:gcontent:read", "read.txt");
			}
			else if(fileType.equals("bread"))
			{
				actionTarget="基地阅读类型数据导出";
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("基地阅读", "nt:gcontent:bread", "bread.txt");
			}
			else if(fileType.equals("dissertation"))
			{
				actionTarget="专题类型数据导出";
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("活动专题","nt:gcontent:dissertation", "subject.txt");
			}
			else if(fileType.equals("video"))
			{
				actionTarget="视频节目数据导出";
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("视频节目",
		                "video",
                "video.txt");
			}
			else if(fileType.equals("NEWMUSIC"))
			{
				actionTarget="新基地音乐类型数据导出";
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("新基地音乐","NEWMUSIC", "music.txt");
			}
            else if (SearchFileConstants.CY_DATA_2010.equals(fileType))
            {
                actionTarget = "2011创业大赛数据导出";
                SearchFileGenerateCYBO.getInstance()
                                    .generateSearchFileByCYDate("2011创业大赛",
                                                                "cy_data_2010",
                                                                "cy_data_2011.txt");
            }
            else if (SearchFileConstants.CY_DATA.equals(fileType))
            {
                actionTarget = "2012创业大赛数据导出";
                SearchFileGenerateCYBO.getInstance().prepareAllData();
                SearchFileGenerateCYBO.getInstance()
                                    .generateSearchFileByCYDate("2012创业大赛",
                                                                "cy_data_2012",
                                                                "cy_data_2012.txt");
            }
            else if (SearchFileConstants.CY_DATA.equals(fileType))
            {
                actionTarget = "2013创业大赛数据导出";
                SearchFileGenerateCYBO.getInstance().prepareAllData();
                SearchFileGenerateCYBO.getInstance()
                                    .generateSearchFileByCYDate("2013创业大赛",
                                                                "cy_data_2013",
                                                                "cy_data_2013.txt");
            }
            else if (SearchFileConstants.MARKET_PK.equals(fileType))
            {
                actionTarget = "市场PK数据导出";
                SearchFileGenerateCYBO.getInstance().prepareAllData();
                SearchFileGenerateCYBO.getInstance()
                                    .generateSearchFileByCYDate("市场PK",
                                                                "cy_data_market",
                                                                "cy_data_market.txt");
            }
            else if (SearchFileConstants.ULTIMATE_PK.equals(fileType))
            {
                actionTarget = "终极PK数据导出";
                SearchFileGenerateCYBO.getInstance().prepareAllData();
                SearchFileGenerateCYBO.getInstance()
                                    .generateSearchFileByCYDate("终极PK",
                                                                "cy_data_ultimate",
                                                                "cy_data_ultimate.txt");
            }
            else if(SearchFileConstants.COMIC.equals(fileType))
            {
            	actionTarget="动漫基地数据导出";
				boolean result = SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("动漫基地数据",SearchFileConstants.COMIC, "comic.txt");
				if(!result) throw new Exception("动漫基地数据导出出错！");
            }
            else if(SearchFileConstants.TEMP.equals(fileType))
            {
            	actionTarget="紧急上下线数据导出导出";
            	new TempExigenceFile().createFile();
            }
            else if (SearchFileConstants.ALLFILE.equals(fileType))
            {
            	actionTarget="用于手动导出全量MM应用搜索文件";
            	SearchFileGenerateBO.getInstance().generateAllSearchFile();
            }else if(SearchFileConstants.TAG.equals(fileType))
			{
				actionTarget="客户端重点应用标签数据导出";
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("客户端重点应用标签数据",
		                "tag",
                "content_tag.txt");
			}
		
		} catch (Exception e)
		{
			forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessages(request, actionTarget + "操作失败");
			this.actionLog(request, actionType, actionTarget, false, "");
			log.error(e);
			return forward;

		}

		this.actionLog(request, actionType, actionTarget, true, "");
		this.saveMessages(request, actionTarget + "操作成功");
		log.debug("end  ExportFileAction");
		return forward;
	}
	private String exportMarketing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String fileType = this.getParameter(request, "fileType");
		String actionType="体验营销数据导出";
		String actionTarget="";

		boolean result=false;
		try
		{
			if(fileType.equals("topList"))
			{
				actionTarget="应用信息导出";
				CommonAppExport appexport=new AppTopExport();
				DataExporter  exporter=new DataExporter();
				result=exporter.export(appexport);
			}else if (fileType.equals("device"))
			{
				actionTarget="终端适配应用信息导出";
				DataExporter  exporter=new DataExporter();
				CommonAppExport deviceexporter=new DeviceTopAppExport();
				result=exporter.export(deviceexporter);
			}		
		}  catch (Exception e)
		{
			forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessages(request, actionTarget + "操作失败");
			this.actionLog(request, actionType, actionTarget, false, "");
			log.error(e);
		}
		if(result)
		{
			forward = Constants.FORWARD_COMMON_SUCCESS;
			this.saveMessages(request, actionTarget + "操作成功");
			this.actionLog(request, actionType, actionTarget, true, "");
		}else
		{
			forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessages(request, actionTarget + "操作失败");
			this.actionLog(request, actionType, actionTarget, false, "");
		}

		log.debug("end  ExportFileAction");
		return forward;
	}

}
