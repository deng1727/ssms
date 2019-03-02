-- Drop columns 
alter table T_R_GCONTENT drop column FULLDEVICEID;

-- Drop columns 
alter table T_INTERVENOR drop column ISDEL;

-- Drop columns 
alter table T_MB_MUSIC drop column MUSIC_PIC;

-----wap门户引入基地游戏，数据回滚--
delete  t_category_rule  t where t.CID in ('88332946','88332943','88332947','88332948','88332950','88332951','88332949','88332945','88332944');
delete  t_caterule t where t.ruleid  = '223';
delete  t_caterule_cond t where t.ruleid  = '223';


delete DBVERSION where PATCHVERSION = 'MM1.0.3.020_SSMS' and LASTDBVERSION = 'MM1.0.3.010_SSMS';
commit;