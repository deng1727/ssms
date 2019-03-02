--------�������ṩ�����ܵĳ������ͼ��v_dc_cm_device_resource������֤��MD5�ֶ�----
alter  table T_A_CM_DEVICE_RESOURCE add cermd5 VARCHAR2(40);
comment on column T_A_CM_DEVICE_RESOURCE.cermd5
  is '֤��MD5';
  
----------------------���ܱ�t_r_category ��������ֶ� ��ʼ------------------------------------------------------
alter  table t_r_category add classify_status VARCHAR2(10) DEFAULT '1';
alter  table t_r_category add goods_status VARCHAR2(10) DEFAULT '1';
comment on column t_r_category.classify_status
  is '����״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
  comment on column t_r_category.goods_status
  is '����״̬--  0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
  

----------------------���ܱ�t_r_category ��������ֶ� ����--------------------------------------------------------

  ----------------------��Ʒ��t_r_reference ���һ���ֶ� ��ʼ------------------------------------------------------
alter  table t_r_reference add delflag number(2) DEFAULT 0;
comment on column t_r_reference.delflag
  is 'ɾ�����λ�� 0-������1-���ͨ��ɾ����2-������ͨ��ɾ����3-�༭ɾ����';
comment on column T_R_REFERENCE.VERIFY_STATUS
  is '���״̬-- 0 ����У�1 ���ͨ����2 ��˲�ͨ����3-�༭';
----------------------��Ʒ��t_r_reference ���һ���ֶ� ����-------------------------------

alter  table t_r_category add delpro_status number(2);
comment on column t_r_category.delpro_status
  is 'ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;3-������ͨ��';
  
  
--------------------------��������������T_R_CATEGORY_OPERATION ��ʼ---------------------------------------------
create table T_R_CATEGORY_OPERATION
(
  id            VARCHAR2(30),
  operator      VARCHAR2(30),
  categoryid     VARCHAR2(20),
  operator_time DATE default sysdate,
  approval      VARCHAR2(30),
  approval_time DATE default sysdate,
  operation     VARCHAR2(10)
);

-- Add comments to the table 
comment on table T_R_CATEGORY_OPERATION
  is '����������';
-- Add comments to the columns 
comment on column T_R_CATEGORY_OPERATION.categoryid
  is '���ܱ���';
comment on column T_R_CATEGORY_OPERATION.operator
  is '������';
comment on column T_R_CATEGORY_OPERATION.operator_time
  is '����ʱ��';
comment on column T_R_CATEGORY_OPERATION.approval
  is '������';
comment on column T_R_CATEGORY_OPERATION.approval_time
  is '����ʱ��';
comment on column T_R_CATEGORY_OPERATION.operation
  is '����ʽ: 1 ���ܷ������; 2 ������Ʒ����';
--------------------------��������������T_R_CATEGORY_OPERATION ����---------------------------------------------

--------------------�����������������к� SEQ_APPROVAL_ID ��ʼ------------------------------------------------------
create sequence SEQ_APPROVAL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------�����������������к� SEQ_APPROVAL_ID ����-------------------------------------------------------


---------------------������Ӫ������־��T_R_LOGGER ��ʼ--------------------------------------------------------------
-- Create table
create table T_R_LOGGER
(
  id               VARCHAR2(30),
  operationtype    VARCHAR2(30),
  operator         VARCHAR2(20),
  operationobj     VARCHAR2(20),
  operationtime    DATE default sysdate,
  operationobjtype VARCHAR2(200)
);

-- Add comments to the table 
comment on table T_R_LOGGER
  is '��Ӫ������־';
-- Add comments to the columns 
comment on column T_R_LOGGER.operationtype
  is '��������';
comment on column T_R_LOGGER.operator
  is '������';
comment on column T_R_LOGGER.operationobj
  is '��������';
comment on column T_R_LOGGER.operationtime
  is '����ʱ��';
comment on column T_R_LOGGER.operationobjtype
  is '������������';
------------------------������Ӫ������־��T_R_LOGGER ��ʼ----------------------------------------------------------------

  
--------------------������Ӫ������־�����к� SEQ_LOGGER_ID ��ʼ-------------------------------------------------------------
create sequence SEQ_LOGGER_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------������Ӫ������־�����к� SEQ_LOGGER_ID ����-------------------------------------------------------------

---------------------���������������ַ�� T_EMAIL_ADDRESS ��ʼ -------------------------------------------------------------
-- Create table
create table T_EMAIL_ADDRESS
(
  id            VARCHAR2(30),
  operatoremail VARCHAR2(50),
  categoryid    VARCHAR2(20),
  approvalemail VARCHAR2(50),
  status        VARCHAR2(10)
);
-- Add comments to the table 
comment on table T_EMAIL_ADDRESS
  is '�ʼ���ַ';
-- Add comments to the columns 
comment on column T_EMAIL_ADDRESS.operatoremail
  is '�༭��Ա�����ַ';
comment on column T_EMAIL_ADDRESS.categoryid
  is '���ܱ���';
comment on column T_EMAIL_ADDRESS.approvalemail
  is '������Ա�����ַ';
comment on column T_EMAIL_ADDRESS.status
  is '����ʽ,00��Ĭ�ϣ�10:MM���ܣ�20�����ݺ�������30��������Ϸ���ܣ�40�������Ķ����ܣ�50���������ֻ��ܣ�60��������Ƶ���ܣ�70�����ض�������';

---------------------���������������ַ�� T_EMAIL_ADDRESS ���� -------------------------------------------------------------

  
--------------------������Ӫ������־�����к� SEQ_EMAIL_ID ��ʼ-------------------------------------------------------------
create sequence SEQ_EMAIL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------������Ӫ������־�����к� SEQ_EMAIL_ID ����-------------------------------------------------------------


insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_CATE_RESULT_004','������Ʒ�ύ�����ɹ�','������Ʒ�ύ�����ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_CATE_RESULT_005','������Ʒ���������ɹ�','������Ʒ���������ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_CATE_RESULT_006','������Ʒ������ͨ���ɹ�','������Ʒ����������ͨ���ɹ�');


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.075_SSMS','MM4.0.0.0.079_SSMS');

 
commit;