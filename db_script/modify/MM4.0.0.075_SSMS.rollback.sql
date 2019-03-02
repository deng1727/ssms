

alter table t_r_gcontent drop column mapname;

alter table v_cm_content drop column devcompanyname;

delete DBVERSION where PATCHVERSION = 'MM4.0.0.0.075_SSMS' and LASTDBVERSION = 'MM4.0.0.0.069_SSMS';

commit;