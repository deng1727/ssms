
drop table T_R_COLORRING;

-- Drop columns 
alter table T_R_GCONTENT drop column FULLDEVICENAME;



delete DBVERSION where PATCHVERSION = 'MM1.0.2.075_SSMS' and LASTDBVERSION = 'MM1.0.2.075_SSMS';
commit;