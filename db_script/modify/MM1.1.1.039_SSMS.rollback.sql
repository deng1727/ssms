drop table t_vb_node;
drop table t_vb_program;
drop table t_vb_programDetail;

-- Drop columns 
alter table T_RB_CATEGORY drop column SORTID;

delete DBVERSION where PATCHVERSION = 'MM1.1.1.039_SSMS' and LASTDBVERSION = 'MM1.1.1.035_SSMS';
commit;