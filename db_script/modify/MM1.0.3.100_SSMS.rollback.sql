delete DBVERSION where PATCHVERSION = 'MM1.0.3.100_SSMS' and LASTDBVERSION = 'MM1.0.3.095_SSMS';
drop table t_category_single;
drop table t_category_usergroup;

drop table t_r_SevenDaysCompared;
drop table t_r_sevenday;
-- Drop columns 
alter table T_R_GCONTENT drop column comparedNumber;

commit;