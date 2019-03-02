-- Drop columns 
alter table T_R_CATEGORY drop column DEVICECATEGORY;

--自动更新数据
drop table T_R_DEVICE_CATEGORY;

-- Drop columns 
alter table T_SYNC_TACTIC drop column TACTYPE;



delete DBVERSION where PATCHVERSION = 'MM1.0.3.040_SSMS' and LASTDBVERSION = 'MM1.0.3.035_SSMS';
commit;