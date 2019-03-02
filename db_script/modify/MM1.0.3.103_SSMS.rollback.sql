alter table t_r_gcontent drop column funcdesc;
alter table t_r_category drop column multiurl;

-- Drop columns 
alter table T_CATERULE_COND drop column BASECONDID;
drop table t_caterule_cond_base;

drop table T_R_SEVENDAYSCOMPARED;
drop table t_r_servenday_temp;
drop table T_R_SEVENCOMPARED;

delete DBVERSION where PATCHVERSION = 'MM1.0.3.103_SSMS' and LASTDBVERSION = 'MM1.0.3.100_SSMS';

commit;