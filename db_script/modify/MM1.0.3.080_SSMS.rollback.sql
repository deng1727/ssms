-- Drop columns 
alter table T_GAME_BASE drop column PROVINCECTROL;

delete from t_key_base  t where t.KEYNAME in ('defaultfocus','app_title');

-- Add/modify columns 
alter table T_R_GCONTENT modify PVCID VARCHAR2(10);
alter table T_R_GCONTENT modify CITYID VARCHAR2(20);

-- Add/modify columns 
alter table T_GAME_BASE modify PROVINCECTROL not null;


delete DBVERSION where PATCHVERSION = 'MM1.0.3.080_SSMS' and LASTDBVERSION = 'MM1.0.3.075_SSMS';
commit;