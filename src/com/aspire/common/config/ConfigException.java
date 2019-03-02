package com.aspire.common.config;

/**
 * <p>Title: Poralt OAM</p>
 * <p>Description: Portal OAM Program file</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author He Chengshou
 * @version 1.0
 */

public class ConfigException extends java.lang.Exception implements java.io.
        Serializable
{

    private static final long serialVersionUID = 1L;

    public ConfigException(String msg)
    {
        super(msg);
    }

}