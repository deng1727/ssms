alter table T_RB_CATEGORY_NEW drop column DELFLAG;




delete DBVERSION where PATCHVERSION = 'MM1.1.1.105_SSMS' and LASTDBVERSION = 'MM1.1.1.098_SSMS';
commit;