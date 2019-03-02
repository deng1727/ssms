package com.aspire.ponaadmin.web.music139;

import java.sql.Connection;

public interface ITransactionHandler {
	public void doInTransaction(Connection conn) throws Exception;
}
