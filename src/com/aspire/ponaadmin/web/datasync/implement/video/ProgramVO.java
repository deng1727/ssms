package com.aspire.ponaadmin.web.datasync.implement.video;

public class ProgramVO
{
    /**
     * ��ĿID
     */
    private String programId;
    
    /**
     * ��Ŀ��������
     */
    private String programName;
    
    /**
     * ��ƵID
     */
    private String contentId;

    public String getContentId()
    {
        return contentId;
    }

    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }

    public String getProgramId()
    {
        return programId;
    }

    public void setProgramId(String programId)
    {
        this.programId = programId;
    }

    public String getProgramName()
    {
        return programName;
    }

    public void setProgramName(String programName)
    {
        this.programName = programName;
    }
}
