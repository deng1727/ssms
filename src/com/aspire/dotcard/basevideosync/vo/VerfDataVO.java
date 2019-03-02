package com.aspire.dotcard.basevideosync.vo;

public class VerfDataVO {

	/**
     * �ļ���С
     */
    private String fileSiz;

    /**
     * �ļ��ж�����
     */
    private String fileDataNum;

    /**
     * �ļ�����ʱ��
     */
    private String fileDate;
    
    /**
     * �ӿ������ļ���
     */
    private String fileName;
    
    /**
     * ������
     */
    private int lineNum;

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileDataNum()
    {
        return fileDataNum;
    }

    public void setFileDataNum(String fileDataNum)
    {
        this.fileDataNum = fileDataNum;
    }

    public String getFileSiz()
    {
        return fileSiz;
    }

    public void setFileSiz(String fileSiz)
    {
        this.fileSiz = fileSiz;
    }

    public String getFileDate()
    {
        return fileDate;
    }

    public void setFileDate(String fileDate)
    {
        this.fileDate = fileDate;
    }

    public String toMailText()
    {
        StringBuffer vos = new StringBuffer();
            
        vos.append("��").append(this.lineNum).append("��У���ļ���Ϣ���£�").append("</d>");;
        vos.append("�ӿ������ļ��ļ���Ϊ: ").append(this.fileName).append("</d>");
        vos.append("�ӿ������ļ���СΪ: ").append(this.fileSiz).append("</d>");
        vos.append("�ӿ������ļ���������Ϊ: ").append(this.fileDataNum).append("</d>");
        vos.append("</d>");
        return vos.toString();
    }

    public int getLineNum()
    {
        return lineNum;
    }

    public void setLineNum(int lineNum)
    {
        this.lineNum = lineNum;
    }
}
