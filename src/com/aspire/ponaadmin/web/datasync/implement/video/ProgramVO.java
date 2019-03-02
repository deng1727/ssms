package com.aspire.ponaadmin.web.datasync.implement.video;

public class ProgramVO
{
    /**
     * 节目ID
     */
    private String programId;
    
    /**
     * 节目中文名称
     */
    private String programName;
    
    /**
     * 视频ID
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
