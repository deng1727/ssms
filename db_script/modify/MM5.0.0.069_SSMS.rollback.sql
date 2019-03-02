delete DBVERSION where PATCHVERSION = 'MM5.0.0.0.069_SSMS' and LASTDBVERSION = 'MM5.0.0.0.059_SSMS';
alert table T_R_REFERENCE drop column islock;
alert table T_R_REFERENCE drop column locktime;
alert table T_R_REFERENCE drop column lockuser;
alert table T_R_REFERENCE drop column locknum;
alert table T_R_CATEGORY drop column islock;
alert table T_R_CATEGORY drop column locktime;
delete T_RIGHT where RIGHTID='1_0813_RES_LOCK_LOCATION' and PARENTID='2_0801_RESOURCE'
commit;