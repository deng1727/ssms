
drop sequence SEQ_MB_KEYWORD;

drop table T_MB_KEYWORD;

alter table T_MB_CATEGORY drop column album_id ;
alter table T_MB_CATEGORY drop column album_pic;
alter table T_MB_CATEGORY drop column rate;
alter table T_MB_CATEGORY modify categorydesc VARCHAR2(500);

comment on column T_R_GCONTENT.DOWNLOADTIMES
  is '�������ش���';
comment on column T_R_GCONTENT.SETTIMES
  is '�������ô���';

delete DBVERSION where PATCHVERSION = 'MM1.0.2.070_SSMS' and LASTDBVERSION = 'MM1.0.2.065_SSMS';
commit;