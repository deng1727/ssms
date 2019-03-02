

-- Create table
create table T_R_COLORRING
(
  ID                VARCHAR2(30),
  NAME              VARCHAR2(300),
  TONENAMELETTER    VARCHAR2(10),
  SINGER            VARCHAR2(200),
  SINGERLETTER      VARCHAR2(10),
  INTRODUCTION      VARCHAR2(300),
  PRICE             VARCHAR2(10),
  LUPDDATE          VARCHAR2(30),
  DOWNLOADTIMES     NUMBER(10),
  SETTIMES          NUMBER(10),
  AUDITIONURL       VARCHAR2(400),
  TONEBIGTYPE       VARCHAR2(30),
  CATENAME          VARCHAR2(30),
  EXPIRE            VARCHAR2(20),
  CREATEDATE        VARCHAR2(30),
  MARKETDATE        VARCHAR2(30),
  AVERAGEMARK       NUMBER(15),
  CONTENTID         VARCHAR2(30),
  CLIENTAUDITIONURL VARCHAR2(400)
);
alter table T_R_COLORRING
  add constraint PK_T_R_COLORTTING primary key (ID)
  using index ;

-- Add comments to the columns 
comment on column T_R_COLORRING.ID
  is '彩铃ID';
comment on column T_R_COLORRING.NAME
  is '彩铃名称';
comment on column T_R_COLORRING.TONENAMELETTER
  is '铃音名称检索首字母';
comment on column T_R_COLORRING.SINGER
  is '歌手名';
comment on column T_R_COLORRING.SINGERLETTER
  is '歌手名称检索首字母';
comment on column T_R_COLORRING.INTRODUCTION
  is '描述';
comment on column T_R_COLORRING.PRICE
  is '价格';
comment on column T_R_COLORRING.LUPDDATE
  is '最后更新时间';
comment on column T_R_COLORRING.DOWNLOADTIMES
  is '该铃音下载的次数';
comment on column T_R_COLORRING.SETTIMES
  is '该铃音设置次数';
comment on column T_R_COLORRING.AUDITIONURL
  is '获取试听地址';
comment on column T_R_COLORRING.TONEBIGTYPE
  is '铃音大类';
comment on column T_R_COLORRING.CATENAME
  is '彩铃二级分类';
comment on column T_R_COLORRING.EXPIRE
  is '内容的有效日期';
comment on column T_R_COLORRING.CREATEDATE
  is '创建时间';
comment on column T_R_COLORRING.MARKETDATE
  is '上线时间';
comment on column T_R_COLORRING.AVERAGEMARK
  is '对应彩铃、全曲的播放时长（毫秒）';
comment on column T_R_COLORRING.CONTENTID
  is '内容ID';
comment on column T_R_COLORRING.CLIENTAUDITIONURL
  is '获取终端试听地址';



-- Add/modify columns 
alter table T_R_GCONTENT add FULLDEVICENAME clob;
-- Add comments to the columns 
comment on column T_R_GCONTENT.FULLDEVICENAME
  is '全量机型信息';





insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.075_SSMS','MM1.0.2.085_SSMS');
commit;