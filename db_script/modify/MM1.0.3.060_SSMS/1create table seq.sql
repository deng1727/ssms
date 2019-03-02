drop table SUBS_LOG;

create table SUBS_LOG
(
  LOGID         NUMBER,          -- ΨһID
  RECORDNUM     NUMBER default 0,-- �������в����ļ�¼��
  TASKNAME      VARCHAR2(100), --  �������ƣ����̻�������
  DESCRIPTION   VARCHAR2(100), -- ����������
  TASKBEGINTIME DATE,          --���п�ʼʱ��
  TASKENDTIME   DATE,          --���н���ʱ��
  STATUS        VARCHAR2(10),  --��������״̬��RUNNING(��������),FINISHED�����н�����,EXCEPTION���������⣩��
  ERRMSG        VARCHAR2(300), --�����������ԭ��
   REMARK        VARCHAR2(500)--������Ϣ
); 
-- Add comments to the columns 
comment on column SUBS_LOG.LOGID
  is 'ΨһID';
comment on column SUBS_LOG.RECORDNUM
  is '�������в����ļ�¼��';
comment on column SUBS_LOG.TASKNAME
  is '�������ƣ����̻�������';
comment on column SUBS_LOG.DESCRIPTION
  is '����������';
comment on column SUBS_LOG.TASKBEGINTIME
  is '���п�ʼʱ��';
comment on column SUBS_LOG.TASKENDTIME
  is '���н���ʱ��';
comment on column SUBS_LOG.STATUS
  is '��������״̬��RUNNING(��������),FINISHED�����н�����,EXCEPTION���������⣩��';
comment on column SUBS_LOG.ERRMSG
  is '�����������ԭ��';
comment on column SUBS_LOG.REMARK
  is '������Ϣ';

create table t_config_parentcategoryid(categoryid varchar2(20),
remark varchar2(50));
 comment on table t_config_parentcategoryid.categoryid
  is '������ID';
   comment on table t_config_parentcategoryid.remark
  is '������������Ϣ';

drop sequence LOG_SEQ;
create sequence LOG_SEQ
minvalue 1
maxvalue 99999999999
start with 41
increment by 1
cache 20;