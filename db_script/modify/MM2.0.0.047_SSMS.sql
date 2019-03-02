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
  is '��Ȼ����ID';
comment on column T_A_PUSH.PUSHID
  is 'pushID';
comment on column T_A_PUSH.CONTENTID
  is 'Ӧ������ID';
comment on column T_A_PUSH.UA
  is 'UA';
comment on column T_A_PUSH.LUPDATE
  is '���ʱ��';
comment on column T_A_PUSH.FILENAME
  is '�ļ���';
  
-- Create table
create table T_A_CONTENT_DOWNCOUNT
(
  CONTENTID VARCHAR2(12),
  DOWNCOUNT NUMBER(10),
  LUPDATE   DATE default sysdate
);
-- Add comments to the columns 
comment on column T_A_CONTENT_DOWNCOUNT.CONTENTID
  is 'Ӧ������ID';
comment on column T_A_CONTENT_DOWNCOUNT.DOWNCOUNT
  is '����������';
comment on column T_A_CONTENT_DOWNCOUNT.LUPDATE
  is '���ʱ��';

create index t_a_push_pushid on t_a_push(pushid);
 
create index t_a_pushreport_pushid on t_a_pushreport(pushid);  

create table t_a_gamecontent_seriesid(contentid varchar2(50),seriesid number(3));
-- Add comments to the columns 
comment on column t_a_gamecontent_seriesid.contentid
  is '������Ϸ���ݵ�ʱ���contentcode';
comment on column t_a_gamecontent_seriesid.seriesid
  is '������Ϸ���ݵ�ʱ���contentcode��Ӧ���������';

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.045_SSMS','MM2.0.0.0.047_SSMS');


commit;