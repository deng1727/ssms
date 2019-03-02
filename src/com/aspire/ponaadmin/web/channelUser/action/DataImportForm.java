package com.aspire.ponaadmin.web.channelUser.action;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class DataImportForm extends ActionForm
{

    FormFile dataFile = null;

    public FormFile getDataFile()
    {

        return dataFile;
    }

    public void setDataFile(FormFile dataFile)
    {

        this.dataFile = dataFile;
    }

    public boolean checkFileNameExtension(String[] type)
    {
        if (dataFile == null || "".equals(dataFile.getFileName()))
        {
            return true;
        }
            
        // �õ��ļ���
        String fileName = dataFile.getFileName();

        // �Ƿ���ں�׺��
        int pos = fileName.lastIndexOf(".");

        if (pos < 0)
        {
            return false;
        }

        // ��ǰ�ļ���׺��
        String fileType = fileName.substring(pos + 1);

        // ѭ���жϺ�׺�����
        for (int i = 0; i < type.length; i++)
        {
            String tempType = type[i];
            
            if (fileType.toUpperCase().equals(tempType.toUpperCase()))
            {
                return true;
            }
        }
        
        // ���û����ͬ������
        return false;
    }

}
