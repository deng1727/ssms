package com.aspire.ponaadmin.web.system ;

public class VersionVO
{
    private String versionID ;

    private String upTime ;

    private String ownerInfo ;

    private PatchVO[] patchvos ;

    public String getOwnerInfo ()
    {
        return ownerInfo ;
    }

    public void setOwnerInfo (String ownerInfo)
    {
        this.ownerInfo = ownerInfo ;
    }

    public void setVersionID (String versionID)
    {
        this.versionID = versionID ;
    }

    public void setUpTime (String upTime)
    {
        this.upTime = upTime ;
    }

    public void setPatchvos (PatchVO[] patchvos)
    {
        this.patchvos = patchvos ;
    }

    public PatchVO[] getPatchvos ()
    {
        return patchvos ;
    }

    public String getUpTime ()
    {
        return upTime ;
    }

    public String getVersionID ()
    {
        return versionID ;
    }
}
