package com.aspire.ponaadmin.web.repository.persistency.db ;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.ponaadmin.web.repository.persistency.Command;

/**
 * <p>可执行数据库持久化命令继承了Command。</p>
 * <p>其内部封装了一次数据库持久化需要执行的sql命令列表，都是更新、删除、修改等没有返回数据的sql。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class ExecuteDBCommand extends Command
{

    /**
     * sql命令列表
     */
    private List executorList;

    /**
     * 构造方法
     * @param _executorList List，执行sql命令列表
     */
    public ExecuteDBCommand(List _executorList)
    {
        this.executorList = _executorList;
    }

    /**
     * 执行持久化操作的方法，覆盖Command.execute方法，请参见。
     * @return Object，持久化结果
     */
    public Object execute ()
    {
        //调用com.aspire.ponaadmin.common.db. executeMuti方法即可。
        try
        {
            DB.getInstance().executeMuti(this.executorList);
        }
        catch (DAOException e)
        {          
            throw new RuntimeException(e);
        }
        return null ;
    }

}
