

drop table T_V_LIST_PUBLISH;

delete DBVERSION where PATCHVERSION = 'MM4.0.0.0.109_SSMS' and LASTDBVERSION = 'MM4.0.0.0.099_SSMS';

commit;