DROP TABLE T_A_PUSH;
-- Create table
create table T_A_PUSH
(
  ID        NUMBER(10),
  PUSHID    VARCHAR2(20),
  CONTENTID VARCHAR2(12),
  UA        VARCHAR2(128),
  LUPDATE   DATE default sysdate,
  FILENAME  VARCHAR2(50)
);
-- Add comments to the columns 
comment on column T_A_PUSH.ID
  is '自然主键ID';
comment on column T_A_PUSH.PUSHID
  is 'pushID';
comment on column T_A_PUSH.CONTENTID
  is '应用内容ID';
comment on column T_A_PUSH.UA
  is 'UA';
comment on column T_A_PUSH.LUPDATE
  is '入库时间';
comment on column T_A_PUSH.FILENAME
  is '文件名';
  
-- Create table
create table T_A_CONTENT_DOWNCOUNT
(
  CONTENTID VARCHAR2(12),
  DOWNCOUNT NUMBER(10),
  LUPDATE   DATE default sysdate
);
-- Add comments to the columns 
comment on column T_A_CONTENT_DOWNCOUNT.CONTENTID
  is '应用内容ID';
comment on column T_A_CONTENT_DOWNCOUNT.DOWNCOUNT
  is '今天下载量';
comment on column T_A_CONTENT_DOWNCOUNT.LUPDATE
  is '入库时间';

create index t_a_push_pushid on t_a_push(pushid);
 
create index t_a_pushreport_pushid on t_a_pushreport(pushid);  

create table t_a_gamecontent_seriesid(contentid varchar2(50),seriesid number(3));
-- Add comments to the columns 
comment on column t_a_gamecontent_seriesid.contentid
  is '导入游戏内容的时候和contentcode';
comment on column t_a_gamecontent_seriesid.seriesid
  is '导入游戏内容的时候和contentcode对应的虚拟机型';

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.045_SSMS','MM2.0.0.0.047_SSMS');


commit;