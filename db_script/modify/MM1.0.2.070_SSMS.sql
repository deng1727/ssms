
create sequence SEQ_MB_KEYWORD
minvalue 1
maxvalue 99999999999999999
start with 4001
increment by 1
cache 20;

create table T_MB_KEYWORD
(
  keyword VARCHAR2(30) not null,
  key_id  NUMBER(20) not null
);
-- Add comments to the columns 
comment on column T_MB_KEYWORD.keyword
  is '关键字';
comment on column T_MB_KEYWORD.key_id
  is '关键字序号';
alter table T_MB_KEYWORD
  add constraint PK_T_MB_KEYWORD primary key (KEYWORD);
  
alter table T_MB_CATEGORY add album_id varchar2(30);
alter table T_MB_CATEGORY add album_pic varchar2(30);
alter table T_MB_CATEGORY modify categorydesc VARCHAR2(1000);
alter table T_MB_CATEGORY add rate number;

comment on column T_MB_CATEGORY.album_id
  is '专辑ID';
comment on column T_MB_CATEGORY.album_pic
  is '专辑图片文件名称';
comment on column T_MB_CATEGORY.rate
  is '专辑评分';

  
comment on column T_R_GCONTENT.DOWNLOADTIMES
  is '彩铃下载次数;MM应用，此值为最近7天累计申请订购应用的次数';
comment on column T_R_GCONTENT.SETTIMES
  is '彩铃设置次数;MM应用，此值为最近30天累计申请订购应用的次数';
  
  
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.065_SSMS','MM1.0.2.070_SSMS');
commit;