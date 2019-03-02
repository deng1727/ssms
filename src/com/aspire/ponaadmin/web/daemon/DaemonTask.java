package com.aspire.ponaadmin.web.daemon ;

/**
 * 后台任务类，封装一个后台异步执行任务。这是一个虚类，子类必须覆盖execute方法。
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: aspire</p>
 *
 * @author shidr
 * @version 1.0
 */
public abstract class DaemonTask
{

    /**
     * 任务运行发生错误时的提示信息
     */
    private String errorHint;

    /**
     * 任务执行方法。
     */
    public abstract void execute();

    /**
     * 设置任务运行发生错误时的提示信息
     * @param _hint String 提示信息
     */
    public void setErrorHint(String _hint)
    {
        this.errorHint = _hint;
    }

    /**
     * 获取任务运行发生错误时的提示信息
     * @return String
     */
    public String getErrorHint()
    {
        return this.errorHint == null ? "" : this.errorHint ;
    }
}
