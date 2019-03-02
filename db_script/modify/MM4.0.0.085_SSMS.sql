
 alter  table t_Content_Backlist add blacklist_status VARCHAR2(10) DEFAULT 1;
comment on column t_Content_Backlist.blacklist_status
  is '����״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
  
  alter  table t_Content_Backlist add delpro_status number(2) DEFAULT 2;
comment on column t_Content_Backlist.delpro_status
  is 'ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;2-����;3-������ͨ��';

--------------------------�������ݺ�����������t_Content_Backlist_operation ��ʼ---------------------------------------------
 create table t_Content_Backlist_operation
(
  id            VARCHAR2(30),
  operator      VARCHAR2(30),
  contentId     VARCHAR2(20),
  operator_time DATE,
  approval      VARCHAR2(30),
  approval_time DATE
);

-- Add comments to the table 
comment on table t_Content_Backlist_operation
  is '���ݺ�����������';
-- Add comments to the columns 
comment on column t_Content_Backlist_operation.contentId
  is 'Ӧ������Id';
comment on column t_Content_Backlist_operation.operator
  is '������';
comment on column t_Content_Backlist_operation.operator_time
  is '����ʱ��';
comment on column t_Content_Backlist_operation.approval
  is '������';
comment on column t_Content_Backlist_operation.approval_time
  is '����ʱ��';
--------------------------�������ݺ�����������t_Content_Backlist_operation ����---------------------------------------------

  
--------------------�����������������к� SQL_CONTENT_APPROVAL_ID ��ʼ------------------------------------------------------
create sequence SQL_CONTENT_APPROVAL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------�����������������к� SQL_CONTENT_APPROVAL_ID ����-------------------------------------------------------

  
 insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BLACKLIST_RESULT_001','���ݺ������ύ�����ɹ�','���ݺ������ύ�����ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BLACKLIST_RESULT_002','���ݺ��������������ɹ�','���ݺ��������������ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BLACKLIST_RESULT_003','���ݺ�����������ͨ���ɹ�','���ݺ�����������ͨ���ɹ�');

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.079_SSMS','MM4.0.0.0.085_SSMS');

 
commit;