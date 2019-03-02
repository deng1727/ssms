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
 * �����ļ���action
 *
 * @author xiaozhijun
 *
 */
public class ExportFileAction extends BaseAction
{

	/**
	 * ��¼��־��ʵ������
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
			this.saveMessages(request, "û�д˵�������");
			//this.actionLog(request, "�������ݵ���", exportInfo, false, "");
		}
		

		return mapping.findForward(forward);
	}
	
	private String exportContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String fileType = request.getParameter("fileType");
		// ���õ������ļ����ļ���
		String exportName = ContentExportBO.getInstance().getExportFileName(fileType);
		log.debug("exportName=" + exportName);

		// ���õ���������
		TypeExportConfig config = ContentExportBO.getInstance().getExportConfig(fileType);

		// ���õ�����ʶ��Ϣ
		String exportInfo = ContentExportBO.getInstance().getExportInfo(fileType);

		// �������ط�ʽ
		response.setContentType("application/vnd.ms-excel;charset=GBK");

		// ����Ĭ�ϱ�����ļ���
		response.setHeader("Content-disposition", "attachment; filename=" + exportName);

		try
		{
			ContentExportBO.getInstance().ExportDate(config, response.getOutputStream());
		} catch (SocketException e)
		{
			//������û�������أ�ѡ��ȡ��ʱ���ֵ��쳣
			return null;
			//return mapping.findForward(null);
		} catch (Exception e)
		{
			forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessages(request, exportInfo + "����ʧ��");
			this.actionLog(request, "�������ݵ���", exportInfo, false, "");
			log.error(e);

		}

		this.actionLog(request, "�������ݵ���", exportInfo, true, "");

		log.debug("end  ExportFileAction");
		return forward;
	}
	private String exportSearchFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String fileType = this.getParameter(request, "fileType");
		
		String actionType="�������ݵ���";
		String actionTarget="";
		
		// ���õ������ļ����ļ���
		try
		{
			if(fileType.equals("software"))
			{

//				actionTarget="����������ݵ���";
//				SearchFileGenerateBO.getInstance().prepareAllData();
//				SearchFileGenerateBO.getInstance().generateSearchFile("���",RepositoryConstants.TYPE_APPSOFTWARE,SearchFileConstants.RELATION_W, "G","appSoftWare.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("���",RepositoryConstants.TYPE_APPSOFTWARE,SearchFileConstants.RELATION_W, "L", "appSoftWare.txt");
//
//				SearchFileGenerateBO.getInstance().generateSearchFile("���",RepositoryConstants.TYPE_APPSOFTWARE,SearchFileConstants.RELATION_A, "G","appSoftWare.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("���",RepositoryConstants.TYPE_APPSOFTWARE,SearchFileConstants.RELATION_A, "L", "appSoftWare.txt");
//
//				SearchFileGenerateBO.getInstance().generateSearchFile("���",RepositoryConstants.TYPE_APPSOFTWARE,SearchFileConstants.RELATION_O, "G","appSoftWare.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("���",RepositoryConstants.TYPE_APPSOFTWARE,SearchFileConstants.RELATION_O, "L", "appSoftWare.txt");
			}else if(fileType.equals("game"))
			{

//				actionTarget="��Ϸ�������ݵ���";
//				SearchFileGenerateBO.getInstance().prepareAllData();
//				SearchFileGenerateBO.getInstance().generateSearchFile("��Ϸ",RepositoryConstants.TYPE_APPGAME,SearchFileConstants.RELATION_W,"G", "appGame.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("��Ϸ",RepositoryConstants.TYPE_APPGAME,SearchFileConstants.RELATION_W, "L", "appGame.txt");
//				
//				SearchFileGenerateBO.getInstance().generateSearchFile("��Ϸ",RepositoryConstants.TYPE_APPGAME,SearchFileConstants.RELATION_A,"G", "appGame.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("��Ϸ",RepositoryConstants.TYPE_APPGAME,SearchFileConstants.RELATION_A, "L", "appGame.txt");
//
//				SearchFileGenerateBO.getInstance().generateSearchFile("��Ϸ",RepositoryConstants.TYPE_APPGAME,SearchFileConstants.RELATION_O,"G", "appGame.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("��Ϸ",RepositoryConstants.TYPE_APPGAME,SearchFileConstants.RELATION_O, "L", "appGame.txt");

			}else if(fileType.equals("theme"))
			{
//				actionTarget="�����������ݵ���";
//				SearchFileGenerateBO.getInstance().prepareAllData();
//				SearchFileGenerateBO.getInstance().generateSearchFile("����",RepositoryConstants.TYPE_APPTHEME,SearchFileConstants.RELATION_W,"G", "appTheme.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("����",RepositoryConstants.TYPE_APPTHEME,SearchFileConstants.RELATION_W, "L", "appTheme.txt");
//		        
//				SearchFileGenerateBO.getInstance().generateSearchFile("����",RepositoryConstants.TYPE_APPTHEME,SearchFileConstants.RELATION_A,"G", "appTheme.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("����",RepositoryConstants.TYPE_APPTHEME,SearchFileConstants.RELATION_A, "L", "appTheme.txt");
//		     
//				SearchFileGenerateBO.getInstance().generateSearchFile("����",RepositoryConstants.TYPE_APPTHEME,SearchFileConstants.RELATION_O,"G", "appTheme.jt.txt");
//				SearchFileGenerateBO.getInstance().generateSearchFile("����",RepositoryConstants.TYPE_APPTHEME,SearchFileConstants.RELATION_O, "L", "appTheme.txt");
			}else if(fileType.equals("audio"))
			{
				actionTarget="A8ȫ���������ݵ���";
				SearchFileGenerateBO.getInstance().prepareDataForO();
				SearchFileGenerateBO.getInstance().generateSearchFile("A8ȫ��",RepositoryConstants.TYPE_AUDIO,"audio.txt");			
				
			}else if(fileType.equals("book"))
			{
				actionTarget="�㶫�������ݵ���";
				SearchFileGenerateBO.getInstance().prepareDataForO();
				SearchFileGenerateBO.getInstance().generateSearchFile("�㶫ͼ��","nt:gcontent:book", "book.txt");
				
			}else if(fileType.equals("colorring"))
			{
				actionTarget="�����������ݵ���";
				SearchFileGenerateBO.getInstance().prepareDataForO();
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("����","nt:gcontent:colorring", "colorring.txt");
				
			}
			//removed by aiyan 2012-10-24
//				else if(fileType.equals("comic"))
//			{
//				actionTarget="�����������ݵ���";
//				SearchFileGenerateBO.getInstance().prepareDataForO();
//				SearchFileGenerateBO.getInstance().generateSearchFile("����","nt:gcontent:comic", "comic.txt");
//				
//			}
			else if(fileType.equals("music"))
			{
				actionTarget="���������������ݵ���";
				//SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("��������","nt:gcontent:music", "music_old.csv");
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("��������","nt:gcontent:music", "music.txt");
			}else if(fileType.equals("read"))
			{
				actionTarget="���ض����������ݵ���";
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("���ض���","nt:gcontent:read", "read.txt");
			}
			else if(fileType.equals("bread"))
			{
				actionTarget="�����Ķ��������ݵ���";
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("�����Ķ�", "nt:gcontent:bread", "bread.txt");
			}
			else if(fileType.equals("dissertation"))
			{
				actionTarget="ר���������ݵ���";
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("�ר��","nt:gcontent:dissertation", "subject.txt");
			}
			else if(fileType.equals("video"))
			{
				actionTarget="��Ƶ��Ŀ���ݵ���";
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("��Ƶ��Ŀ",
		                "video",
                "video.txt");
			}
			else if(fileType.equals("NEWMUSIC"))
			{
				actionTarget="�»��������������ݵ���";
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("�»�������","NEWMUSIC", "music.txt");
			}
            else if (SearchFileConstants.CY_DATA_2010.equals(fileType))
            {
                actionTarget = "2011��ҵ�������ݵ���";
                SearchFileGenerateCYBO.getInstance()
                                    .generateSearchFileByCYDate("2011��ҵ����",
                                                                "cy_data_2010",
                                                                "cy_data_2011.txt");
            }
            else if (SearchFileConstants.CY_DATA.equals(fileType))
            {
                actionTarget = "2012��ҵ�������ݵ���";
                SearchFileGenerateCYBO.getInstance().prepareAllData();
                SearchFileGenerateCYBO.getInstance()
                                    .generateSearchFileByCYDate("2012��ҵ����",
                                                                "cy_data_2012",
                                                                "cy_data_2012.txt");
            }
            else if (SearchFileConstants.CY_DATA.equals(fileType))
            {
                actionTarget = "2013��ҵ�������ݵ���";
                SearchFileGenerateCYBO.getInstance().prepareAllData();
                SearchFileGenerateCYBO.getInstance()
                                    .generateSearchFileByCYDate("2013��ҵ����",
                                                                "cy_data_2013",
                                                                "cy_data_2013.txt");
            }
            else if (SearchFileConstants.MARKET_PK.equals(fileType))
            {
                actionTarget = "�г�PK���ݵ���";
                SearchFileGenerateCYBO.getInstance().prepareAllData();
                SearchFileGenerateCYBO.getInstance()
                                    .generateSearchFileByCYDate("�г�PK",
                                                                "cy_data_market",
                                                                "cy_data_market.txt");
            }
            else if (SearchFileConstants.ULTIMATE_PK.equals(fileType))
            {
                actionTarget = "�ռ�PK���ݵ���";
                SearchFileGenerateCYBO.getInstance().prepareAllData();
                SearchFileGenerateCYBO.getInstance()
                                    .generateSearchFileByCYDate("�ռ�PK",
                                                                "cy_data_ultimate",
                                                                "cy_data_ultimate.txt");
            }
            else if(SearchFileConstants.COMIC.equals(fileType))
            {
            	actionTarget="�����������ݵ���";
				boolean result = SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("������������",SearchFileConstants.COMIC, "comic.txt");
				if(!result) throw new Exception("�����������ݵ�������");
            }
            else if(SearchFileConstants.TEMP.equals(fileType))
            {
            	actionTarget="�������������ݵ�������";
            	new TempExigenceFile().createFile();
            }
            else if (SearchFileConstants.ALLFILE.equals(fileType))
            {
            	actionTarget="�����ֶ�����ȫ��MMӦ�������ļ�";
            	SearchFileGenerateBO.getInstance().generateAllSearchFile();
            }else if(SearchFileConstants.TAG.equals(fileType))
			{
				actionTarget="�ͻ����ص�Ӧ�ñ�ǩ���ݵ���";
				SearchFileGenerateBO.getInstance().generateSearchFileByOtherType("�ͻ����ص�Ӧ�ñ�ǩ����",
		                "tag",
                "content_tag.txt");
			}
		
		} catch (Exception e)
		{
			forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessages(request, actionTarget + "����ʧ��");
			this.actionLog(request, actionType, actionTarget, false, "");
			log.error(e);
			return forward;

		}

		this.actionLog(request, actionType, actionTarget, true, "");
		this.saveMessages(request, actionTarget + "�����ɹ�");
		log.debug("end  ExportFileAction");
		return forward;
	}
	private String exportMarketing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String fileType = this.getParameter(request, "fileType");
		String actionType="����Ӫ�����ݵ���";
		String actionTarget="";

		boolean result=false;
		try
		{
			if(fileType.equals("topList"))
			{
				actionTarget="Ӧ����Ϣ����";
				CommonAppExport appexport=new AppTopExport();
				DataExporter  exporter=new DataExporter();
				result=exporter.export(appexport);
			}else if (fileType.equals("device"))
			{
				actionTarget="�ն�����Ӧ����Ϣ����";
				DataExporter  exporter=new DataExporter();
				CommonAppExport deviceexporter=new DeviceTopAppExport();
				result=exporter.export(deviceexporter);
			}		
		}  catch (Exception e)
		{
			forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessages(request, actionTarget + "����ʧ��");
			this.actionLog(request, actionType, actionTarget, false, "");
			log.error(e);
		}
		if(result)
		{
			forward = Constants.FORWARD_COMMON_SUCCESS;
			this.saveMessages(request, actionTarget + "�����ɹ�");
			this.actionLog(request, actionType, actionTarget, true, "");
		}else
		{
			forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessages(request, actionTarget + "����ʧ��");
			this.actionLog(request, actionType, actionTarget, false, "");
		}

		log.debug("end  ExportFileAction");
		return forward;
	}

}
