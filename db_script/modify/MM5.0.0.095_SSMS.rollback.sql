--drop table t_r_exportsql;
--RENAME  t_r_exportsql_20160923  TO t_r_exportsql;
--update t_r_exportsql set exportsql

merge into t_r_exportsql t1
using t_r_exportsql_20160923 t2
on (t1.id = t2.id)
when matched then
update set t1.exportsql = t2.exportsql,
t1.exportline = t2.exportline;

commit;

drop table t_r_exportsql_20160923;

delete DBVERSION where PATCHVERSION = 'MM5.0.0.0.095_SSMS' and LASTDBVERSION = 'MM5.0.0.0.079_SSMS';

commit;