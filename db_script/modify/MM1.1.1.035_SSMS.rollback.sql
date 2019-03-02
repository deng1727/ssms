
drop view  v_content_time;

delete from   T_CATERULE_COND_BASE t where t.BASE_ID='35';


delete DBVERSION where PATCHVERSION = 'MM1.1.1.035_SSMS' and LASTDBVERSION = 'MM1.0.3.135_SSMS';
commit;