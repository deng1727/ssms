--------���㷺��-�ն˹�˾�����������˺ű�T_OPEN_CHANNELS�����������������ֶ�----
alter  table T_OPEN_CHANNELS add motype VARCHAR2(2) DEFAULT '1';
comment on column T_OPEN_CHANNELS.motype
  is '���������ͣ�0-��ʾ�ն˹�˾��1-���ն˹�˾';
--------���㷺��-�ն˹�˾���������̸����ܹ�ϵ��(T_OPEN_CHANNELS_CATEGORY)������ҵ(ap)�����ֶ�
alter  table T_OPEN_CHANNELS_CATEGORY add icpcode VARCHAR2(20);
comment on column T_OPEN_CHANNELS_CATEGORY.icpcode
  is '��ҵ(ap)����';
  
---------------------����TAC����ʼ------------
create table t_tac_code_base
(
  id            VARCHAR2(30),
  taccode       VARCHAR2(30),
  brand         VARCHAR2(100),
  device        VARCHAR2(200),
  createtime    DATE
);

-- Add comments to the table 
comment on table t_tac_code_base
  is 'TAC����';
-- Add comments to the columns 
comment on column t_tac_code_base.id
  is '����Id';
comment on column t_tac_code_base.taccode
  is 'TAC��';
comment on column t_tac_code_base.brand
  is '�ֻ�Ʒ��';
comment on column t_tac_code_base.device
  is '�ֻ��ͺ�';
comment on column t_tac_code_base.createtime
  is '����ʱ��';
alter table T_TAC_CODE_BASE
  add constraint PK_T_TAC_CODE_BASE_ID primary key (ID);
---------------------����TAC�������------------
  
---------------------�����������͹���ʼ------------
create table t_content_push_adv
(
  id            VARCHAR2(30),
  contentid     VARCHAR2(30),
  title         VARCHAR2(100),
  subtitle      VARCHAR2(100),
  starttime     VARCHAR2(30),
  endtime       VARCHAR2(30),
  rebrand       VARCHAR2(100),
  createtime    DATE
);

-- Add comments to the table 
comment on table t_content_push_adv
  is '�������͹���';
-- Add comments to the columns 
comment on column t_content_push_adv.id
  is '����Id';
comment on column t_content_push_adv.contentid
  is 'Ӧ��id';
comment on column t_content_push_adv.title
  is '������';
comment on column t_content_push_adv.subtitle
  is '������';
comment on column t_content_push_adv.starttime
  is '��ʼʱ��';
comment on column t_content_push_adv.endtime
  is '����ʱ��';
comment on column t_content_push_adv.rebrand
  is '�Ƽ�Ʒ��';
comment on column t_content_push_adv.createtime
  is '����ʱ��';
alter table T_CONTENT_PUSH_ADV
  add constraint PK_T_CONTENT_PUSH_ADV_ID primary key (ID);
---------------------���������������͹������------------
  
create sequence SEQ_T_TAC_CODE_BASE_ID 
start with 1
increment by 1
maxvalue 999999999999
minvalue 1
nocycle
cache 100;

create sequence SEQ_T_CONTENT_PUSH_ADV_ID 
start with 1
increment by 1
maxvalue 999999999999
minvalue 1
nocycle
cache 100;

---------------------------------ͼ����ܱ�t_rb_category_new ��������ֶ� ��ʼ---------------------------------------------------------
 alter  table t_rb_category_new add read_status VARCHAR2(10) DEFAULT '1';
alter  table t_rb_category_new add goods_status VARCHAR2(10) DEFAULT '1';
alter  table t_rb_category_new add delpro_status number(2) default 2;
comment on column t_rb_category_new.read_status
  is '��������״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
  comment on column t_rb_category_new.goods_status
  is '��Ʒ����״̬--  0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
comment on column t_rb_category_new.delpro_status
  is 'ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;2-����;3-������ͨ��';
-----------------------------------ͼ����ܱ�t_rb_category_new ��������ֶ� ����-------------------------------------------------------

-----------------------------------����ͼ�����������T_RB_CATEGORY_NEW_OPERATION ��ʼ------------------------------------------------
 create table T_RB_CATEGORY_NEW_OPERATION
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
comment on table T_RB_CATEGORY_NEW_OPERATION
  is 'ͼ�����������';
-- Add comments to the columns 
comment on column T_RB_CATEGORY_NEW_OPERATION.categoryid
  is '���ܱ���';
comment on column T_RB_CATEGORY_NEW_OPERATION.operator
  is '������';
comment on column T_RB_CATEGORY_NEW_OPERATION.operator_time
  is '����ʱ��';
comment on column T_RB_CATEGORY_NEW_OPERATION.approval
  is '������';
comment on column T_RB_CATEGORY_NEW_OPERATION.approval_time
  is '����ʱ��';
comment on column T_RB_CATEGORY_NEW_OPERATION.operation
  is '����ʽ: 1 ͼ����ܹ���; 2 ͼ����Ʒ����';
-----------------------------------����ͼ�����������T_RB_CATEGORY_NEW_OPERATION ����------------------------------------------------  

--------------------����ͼ��������������к� SEQ_RB_APPROVAL_ID ��ʼ----------------------------------------------------------------
create sequence SEQ_RB_APPROVAL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------����ͼ��������������к� SEQ_RB_APPROVAL_ID ����----------------------------------------------------------------

---------------------------------ͼ�������Ʒ��T_RB_REFERENCE_NEW ��������ֶ� ��ʼ-------------------------------------------------------
alter  table T_RB_REFERENCE_NEW add VERIFY_DATE DATE default sysdate;
alter  table T_RB_REFERENCE_NEW add VERIFY_STATUS number(2) default 1;
alter  table T_RB_REFERENCE_NEW add DELFLAG number(2) default 2;
comment on column T_RB_REFERENCE_NEW.VERIFY_DATE
  is '���ʱ��';
comment on column T_RB_REFERENCE_NEW.VERIFY_STATUS
  is '����״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
comment on column T_RB_REFERENCE_NEW.DELFLAG
  is 'ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;2-����;3-������ͨ��';
---------------------------------ͼ�������Ʒ��T_RB_REFERENCE_NEW ��������ֶ� ����-------------------------------------------------------



alter table t_email_address modify OPERATOREMAIL varchar2(2000);
alter table t_email_address modify APPROVALEMAIL varchar2(2000);

---------------------------------���ֻ��ܱ�t_mb_category_new ��������ֶ� ��ʼ---------------------------------------------------------
alter  table t_mb_category_new add music_status VARCHAR2(10) DEFAULT '1';
alter  table t_mb_category_new add goods_status VARCHAR2(10) DEFAULT '1';
alter  table t_mb_category_new add delpro_status number(2) default 2;
comment on column t_mb_category_new.music_status
  is '��������״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
  comment on column t_mb_category_new.goods_status
  is '��Ʒ����״̬--  0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
comment on column t_mb_category_new.delpro_status
  is 'ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;2-����;3-������ͨ��';
-----------------------------------���ֻ��ܱ�t_mb_category_new ��������ֶ� ����-------------------------------------------------------

-----------------------------------�������ֻ���������T_MB_CATEGORY_NEW_OPERATION ��ʼ------------------------------------------------
create table T_MB_CATEGORY_NEW_OPERATION
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
comment on table T_MB_CATEGORY_NEW_OPERATION
  is '���ֻ���������';
-- Add comments to the columns 
comment on column T_MB_CATEGORY_NEW_OPERATION.categoryid
  is '���ܱ���';
comment on column T_MB_CATEGORY_NEW_OPERATION.operator
  is '������';
comment on column T_MB_CATEGORY_NEW_OPERATION.operator_time
  is '����ʱ��';
comment on column T_MB_CATEGORY_NEW_OPERATION.approval
  is '������';
comment on column T_MB_CATEGORY_NEW_OPERATION.approval_time
  is '����ʱ��';
comment on column T_MB_CATEGORY_NEW_OPERATION.operation
  is '����ʽ: 1 ���ֻ��ܹ���; 2 ������Ʒ����';
-----------------------------------�������ֻ���������T_MB_CATEGORY_NEW_OPERATION ����------------------------------------------------  

--------------------�������ֻ������������к�SEQ_MB_APPROVAL_ID ��ʼ----------------------------------------------------------------
create sequence SEQ_MB_APPROVAL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------�������ֻ������������к� SEQ_MB_APPROVAL_ID ����----------------------------------------------------------------

---------------------------------���ֻ�����Ʒ��T_MB_REFERENCE_NEW ��������ֶ� ��ʼ-------------------------------------------------------
alter  table T_MB_REFERENCE_NEW add VERIFY_DATE DATE default sysdate;
alter  table T_MB_REFERENCE_NEW add VERIFY_STATUS number(2) default 1;
alter  table T_MB_REFERENCE_NEW add DELFLAG number(2) default 2;
comment on column T_MB_REFERENCE_NEW.VERIFY_DATE
  is '���ʱ��';
comment on column T_MB_REFERENCE_NEW.VERIFY_STATUS
  is '����״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
comment on column T_MB_REFERENCE_NEW.DELFLAG
  is 'ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;2-����;3-������ͨ��';
---------------------------------���ֻ�����Ʒ��T_MB_REFERENCE_NEW ��������ֶ� ����-------------------------------------------------------


insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BOOKCATEGORY_RESULT_001','ͼ����ܹ����ύ�����ɹ�','ͼ����ܹ����ύ�����ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BOOKCATEGORY_RESULT_002','ͼ����ܹ������������ɹ�','ͼ����ܹ������������ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BOOKCATEGORY_RESULT_003','ͼ����ܹ���������ͨ���ɹ�','ͼ����ܹ���������ͨ���ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BOOKCATEGORY_RESULT_004','ͼ����Ʒ�����ύ�����ɹ�','ͼ����Ʒ�����ύ�����ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BOOKCATEGORY_RESULT_005','ͼ����Ʒ�������������ɹ�','ͼ����Ʒ�������������ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BOOKCATEGORY_RESULT_006','ͼ����Ʒ����������ͨ���ɹ�','ͼ����Ʒ����������ͨ���ɹ�');


insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_MUSICCATEGORY_RESULT_001','���ֻ��ܹ����ύ�����ɹ�','���ֻ��ܹ����ύ�����ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_MUSICCATEGORY_RESULT_002','���ֻ��ܹ������������ɹ�','���ֻ��ܹ������������ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_MUSICCATEGORY_RESULT_003','���ֻ��ܹ���������ͨ���ɹ�','���ֻ��ܹ���������ͨ���ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_MUSICCATEGORY_RESULT_004','������Ʒ�����ύ�����ɹ�','������Ʒ�����ύ�����ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_MUSICCATEGORY_RESULT_005','������Ʒ�������������ɹ�','������Ʒ�������������ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_MUSICCATEGORY_RESULT_006','������Ʒ����������ͨ���ɹ�','������Ʒ����������ͨ���ɹ�');

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.089_SSMS','MM4.0.0.0.095_SSMS');

 
commit;