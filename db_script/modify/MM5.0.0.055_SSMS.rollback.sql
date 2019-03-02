alter table T_V_DPROGRAM
  drop constraint PK_TVDPROGRAM_PROGRAM cascade;


delete DBVERSION where PATCHVERSION = 'MM5.0.0.0.505_SSMS' and LASTDBVERSION = 'MM5.0.0.0.309_SSMS';

commit;