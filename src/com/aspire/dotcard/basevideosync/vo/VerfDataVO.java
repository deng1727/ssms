package com.aspire.dotcard.basevideosync.vo;

public class VerfDataVO {

	/**
     * 文件大小
     */
    private String fileSiz;

    /**
     * 文件有多少条
     */
    private String fileDataNum;

    /**
     * 文件生成时间
     */
    private String fileDate;
    
    /**
     * 接口数据文件名
     */
    private String fileName;
    
    /**
     * 所在行
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
            
        vos.append("第").append(this.lineNum).append("行校验文件信息如下：").append("</d>");;
        vos.append("接口数据文件文件名为: ").append(this.fileName).append("</d>");
        vos.append("接口数据文件大小为: ").append(this.fileSiz).append("</d>");
        vos.append("接口数据文件数据行数为: ").append(this.fileDataNum).append("</d>");
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
