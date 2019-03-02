

alter table t_Content_Backlist drop column blacklist_status;
alter table t_Content_Backlist drop column delpro_status;
alter table t_email_address drop column contentid;

drop table t_Content_Backlist_operation;
drop sequence SQL_CONTENT_APPROVAL_ID;

delete DBVERSION where PATCHVERSION = 'MM4.0.0.0.085_SSMS' and LASTDBVERSION = 'MM4.0.0.0.079_SSMS';

commit;