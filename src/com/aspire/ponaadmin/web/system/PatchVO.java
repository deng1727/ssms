package com.aspire.ponaadmin.web.system ;

public class PatchVO
{
    private String patchID ;

    private String patchName ;

    private String patchDesc ;

    private String patchAmended ;

    private String patchAdded ;

    private String upTime ;

    public String getPatchAdded ()
    {
        return patchAdded ;
    }

    public void setPatchAdded (String patchAdded)
    {
        this.patchAdded = patchAdded ;
    }

    public void setUpTime (String upTime)
    {
        this.upTime = upTime ;
    }

    public void setPatchName (String patchName)
    {
        this.patchName = patchName ;
    }

    public void setPatchID (String patchID)
    {
        this.patchID = patchID ;
    }

    public void setPatchDesc (String patchDesc)
    {
        this.patchDesc = patchDesc ;
    }

    public void setPatchAmended (String patchAmended)
    {
        this.patchAmended = patchAmended ;
    }

    public String getPatchAmended ()
    {
        return patchAmended ;
    }

    public String getPatchDesc ()
    {
        return patchDesc ;
    }

    public String getPatchID ()
    {
        return patchID ;
    }

    public String getPatchName ()
    {
        return patchName ;
    }

    public String getUpTime ()
    {
        return upTime ;
    }
}
