package com.aspire.ponaadmin.common.page;

// Imports
import java.sql.SQLException;
import java.util.List;

public abstract interface PageDAO {

  int getCount(Object object);
  List getList(Object object, int _int, int _int2) throws SQLException;
}
